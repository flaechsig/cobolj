package de.cobolj;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.SerializationUtils;

import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameUtil;

import de.cobolj.division.data.DataDescriptionEntryNode;
import de.cobolj.division.data.DataOccursClause;
import de.cobolj.division.data.FileDescriptionEntryNode;
import de.cobolj.parser.ParserHelper;
import de.cobolj.parser.StartRuleVisitor;
import de.cobolj.runtime.AmbigousPicture;
import de.cobolj.runtime.Picture;

/**
 * Context, der zur Ausführungszeit eines Cobol-Programms benötigt wird. Er
 * enthält den Status des Programms und bildet Schnittstellen zur Außenwelt.
 * 
 * @author flaechsig
 *
 */
public class CobolContext {
	/** Output-Stream für das Cobol-Programm */
	private PrintStream out;
	/** Input-Stream für das Cobol-Programm */
	private InputStream in;
	/** File Descriptors */
	private List<FileDescriptionEntryNode> fileDescriptor = new ArrayList<>();
	/** Records */
	private Map<String, List<Picture>> records = new HashMap<>();
	/** Name des Cobol-Programms */
	private String programName;

	public CobolContext(InputStream in, OutputStream out) {
		assert in != null : "in darf nicht null sein";
		assert out != null : "out darf nicht null sein";

		this.in = in;
		this.out = new PrintStream(out);
	}

	/**
	 * @return Liefert den Output-Stream für das Programm.
	 */
	public PrintStream getOut() {
		return out;
	}

	public InputStream getIn() {
		return in;
	}

	/** Liefert den FileDescriptor zu einen Namen */
	public FileDescriptionEntryNode getFileDescriptorByName(String name) {
		for (FileDescriptionEntryNode fd : fileDescriptor) {
			if (fd.getName().equals(name)) {
				return fd;
			}
		}
		throw new RuntimeException("FileDescriptor nicht gefunden");
	}

	/** Liefert den FileDescriptor zu einen Namen */
	public FileDescriptionEntryNode getFileDescriptorByRecord(String name) {
		for (FileDescriptionEntryNode fd : fileDescriptor) {
			for (DataDescriptionEntryNode entry : fd.getDataDescriptionEntry()) {
				if (entry.getName().equals(name)) {
					return fd;
				}
			}
		}
		throw new RuntimeException("FileDescriptor nicht gefunden");
	}

	/** Fügt dem Context einen File-Descriptor hinzu. */
	public void addFileDescriptor(FileDescriptionEntryNode fileDescriptionEntryNode) {
		this.fileDescriptor.add(fileDescriptionEntryNode);
	}
	
	public void putDataDescriptionEntry(Frame frame, DataDescriptionEntryNode entry ) {
		putPicture(frame, entry.getPicture(), entry.getOccurs());
	}

	/**
	 * Fügt ein Picture dem Datenspeicher hinzu. Dabei werden ggf. mehrere Slots
	 * angelegt, um die unterschiedlichen Zugriffspfade abzubilden. Dabei ist es
	 * möglich, dass ein Element auf Teilpfaden nicht eindeutig ist. Dies führt beim
	 * Zugriff auf das Picture zu einer Uneindeutigkeit, die mit einer Exception
	 * quitiert wird.
	 * 
	 * @param frame
	 * @param pic
	 */
	private void putPicture(Frame frame, Picture picTemplate, DataOccursClause occurs) {
		assert frame != null : "frame darf nicht null sein";
		assert picTemplate != null : "Picture muss angegeben werden";
		final Picture[] storage = new Picture[occurs!=null?occurs.getOccurs():1];

		if(picTemplate.isFiller()) {
			// Filler sind nicht zugreifbar
			return;
		}
		
		// FIXME: Das Anlegen der Arrays arbeitet mit PictureGroup noch nicht 
		// sauber zusammen. Vermutlich wird eine Trennung zwischen Picture und 
		// Speicher notwendig
		storage[0] = picTemplate; // 
		for(int i=1; i<storage.length; i++) { 
			storage[i] = SerializationUtils.clone(picTemplate);
		}
		

		// Aufbauen aller Zugriffspfade
		List<String> pfade = new ArrayList<>();
		Picture actPic = storage[0];
		String actPfad = actPic.getName();
		pfade.add(actPfad);
		while (actPic.getParent() != null) {
			actPic = actPic.getParent();
			actPfad += " OF ";
			actPfad += actPic.getName();
			pfade.add(actPfad);
		}

		// Für jeden Pfad einen Slot anlegen. Vorher prüfen, ob er bereits existiert und
		// ggf.
		// durch ein AmbigousPicture (Merker) ersetzen.
		for (String pfad : pfade) {
			FrameSlot slot = frame.getFrameDescriptor().findFrameSlot(pfad);
			if (slot != null) {
				Picture[] ambigius = {AmbigousPicture.INSTANCE};
				frame.setObject(slot, ambigius);
			} else {
				slot = frame.getFrameDescriptor().addFrameSlot(pfad);
				frame.setObject(slot, storage);
			}
		}
	}

	/**
	 * Liefert das Picture zum Namen. Wenn der Name nicht eindeutig aufgelöst
	 * wereden kann, wird eine Exception geworfen.
	 * 
	 * @param name
	 * @return
	 */
	public Picture getPicture(Frame frame, String name) {
		assert frame != null : "frame darf nicht null sein";
		assert name != null : "Name muss angegeben werden";

		FrameSlot slot = frame.getFrameDescriptor().findFrameSlot(name);
		if (slot == null) {
			throw new RuntimeException("Picture existiert nicht (" + name + ")");
		}
		Picture[] pic = (Picture[]) FrameUtil.getObjectSafe(frame, slot);
		if (pic[0] == AmbigousPicture.INSTANCE) {
			throw new RuntimeException("Picture nicht eindeutig. Benötigt Qualifizierung (" + name + ")");
		}
		if(pic.length > 1) {
			throw new RuntimeException("'"+name+"' requires one subscript");
		}
		return pic[0];
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

}
