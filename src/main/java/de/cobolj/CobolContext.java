package de.cobolj;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameUtil;

import de.cobolj.division.data.FileDescriptionEntryNode;
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
	private Scanner in;
	/** File Descriptors */
	private List<FileDescriptionEntryNode> fileDescriptor = new ArrayList<>();
	/** Records */
	private Map<String, List<Picture>> records = new HashMap<>();
	/** Name des Cobol-Programms */
	private String programName;

	public CobolContext(InputStream in, OutputStream out) {
		assert in != null : "in darf nicht null sein";
		assert out != null : "out darf nicht null sein";

		this.in = new Scanner(in);
		this.out = new PrintStream(out);
	}

	/**
	 * @return Liefert den Output-Stream für das Programm.
	 */
	public PrintStream getOut() {
		return out;
	}

	public Scanner getIn() {
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
			for (Picture pic : fd.getPictures()) {
				if (pic.getName().equals(name)) {
					return fd;
				}
			}
		}
		throw new RuntimeException("FileDescriptor nicht gefunden");
	}

	@Deprecated
	public FileDescriptionEntryNode getFileDescriptor(FrameSlot slot) {
		return getFileDescriptorByName(slot.getIdentifier().toString());
	}

	/** Fügt dem Context einen File-Descriptor hinzu. */
	public void addFileDescriptor(FileDescriptionEntryNode fileDescriptionEntryNode) {
		this.fileDescriptor.add(fileDescriptionEntryNode);
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
	public void putPicture(Frame frame, Picture pic) {
		assert frame != null : "frame darf nicht null sein";
		assert pic != null : "Picture muss angegeben werden";

		// Aufbauen aller Zugriffspfade
		List<String> pfade = new ArrayList<>();
		Picture actPic = pic;
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
				frame.setObject(slot, AmbigousPicture.INSTANCE);
			} else {
				slot = frame.getFrameDescriptor().addFrameSlot(pfad);
				frame.setObject(slot, pic);
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
		Picture pic = (Picture) FrameUtil.getObjectSafe(frame, slot);
		if (pic == AmbigousPicture.INSTANCE) {
			throw new RuntimeException("Picture nicht eindeutig. Benötigt Qualifizierung (" + name + ")");
		}
		return pic;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

}
