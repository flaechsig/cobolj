package de.cobolj;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameUtil;

import de.cobolj.division.data.DataDescriptionEntryNode;
import de.cobolj.division.data.FileDescriptionEntryNode;
import de.cobolj.runtime.AmbigousPicture;
import de.cobolj.runtime.Picture;
import de.cobolj.runtime.PictureGroup;

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
				if (entry.getPicture().getName().equals(name)) {
					return fd;
				}
			}
		}
		throw new RuntimeException("FileDescriptor (" + name + ") nicht gefunden");
	}

	/** Fügt dem Context einen File-Descriptor hinzu. */
	public void addFileDescriptor(FileDescriptionEntryNode fileDescriptionEntryNode) {
		this.fileDescriptor.add(fileDescriptionEntryNode);
	}

//	public void putDataDescriptionEntry(Frame frame, DataDescriptionEntryNode entry) {
//		putPicture(frame, entry.getPicture(), entry.getOccurs());
//	}

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
	public void putPicture(Frame frame, Picture picture) {
		assert frame != null : "frame darf nicht null sein";
		assert picture != null : "Picture muss angegeben werden";

		if (picture.isFiller()) {
			// Filler sind nicht zugreifbar
			return;
		}

		// Pictures mit allen Zugriffspfaden eintragen
		String actPfad = picture.getName();
		String subscript = picture.getSubscript();
		addToSlot(frame, actPfad + subscript, picture);

		Picture actPicture = picture;
		while (actPicture.getParent() != null) {
			actPicture = actPicture.getParent();
			actPfad += " OF ";
			actPfad += actPicture.getName();

			addToSlot(frame, actPfad + subscript, picture);
		}
	}

	private void addToSlot(Frame frame, String name, Picture pic) {
		FrameSlot slot = null;
		try {
			slot = frame.getFrameDescriptor().addFrameSlot(name);
			frame.setObject(slot, pic);
			if(!pic.isMemInitialized()) {
				int top = getRamTop(frame);
				byte[] ram = getRam(frame);
				pic.setMemory(ram);
				pic.setMemoryPointer(top);
				if(!(pic instanceof PictureGroup)) {
					setRamTop(frame, top+pic.getSize());
					pic.clear();
				}
			}
		} catch (IllegalArgumentException e) {
			slot = frame.getFrameDescriptor().findFrameSlot(name);
			frame.setObject(slot, AmbigousPicture.INSTANCE);
		}
	}

	/**
	 * Liefert das Picture zum Namen. Wenn der Name nicht eindeutig aufgelöst
	 * wereden kann, wird eine Exception geworfen.
	 * 
	 * @param frame     Ausführender Frame
	 * @param name      Name des Pictures, wie es in der Data Devision deklariert
	 *                  wurde
	 * @param subscript Tabellen-Index (mit 1 startend)
	 * @return
	 */
	public Picture getPicture(Frame frame, String name, Number... subscript) {
		assert frame != null : "frame darf nicht null sein";
		assert name != null : "Name muss angegeben werden";
		assert subscript.length <= 1 : "Zur Zeit werden nur Arrays unterstützt";

		String fullname = name;
		if (subscript.length > 0) {
			fullname += "(" + subscript[0] + ")";
		}
		FrameSlot slot = frame.getFrameDescriptor().findFrameSlot(fullname);
		if (slot == null) {
			throw new RuntimeException("Picture existiert nicht (" + fullname + ")");
		}
		Picture pic = (Picture) FrameUtil.getObjectSafe(frame, slot);
		if (pic == AmbigousPicture.INSTANCE) {
			throw new RuntimeException("Picture nicht eindeutig. Benötigt Qualifizierung (" + name + ")");
		}
		return pic;
	}

	/**
	 * @see #getPicture(Frame, String, int).
	 * 
	 *      Subscript wird hierbei mit 1 belegt.
	 * 
	 */
	public Picture getPicture(Frame frame, String name) {
		return getPicture(frame, name, new Number[0]);
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}
	
	/** 
	 * 
	 * @return Liefert die nächste freie Speicheradresse.
	 */
	private int getRamTop(Frame frame) {
		int result = 0;
		FrameSlot slot = frame.getFrameDescriptor().findFrameSlot("RAMTOP");
		if(slot != null) {
			result = FrameUtil.getIntSafe(frame, slot);
		} else {
			slot = frame.getFrameDescriptor().addFrameSlot("RAMTOP");
			frame.setInt(slot, result);
		}
		return result;
	}
	
	/**
	 * Setzt das aktuell obere Speicherende. Damit ist die Position, die hier angegeben wird
	 * die nächste mögliche Speicherstelle.
	 * 
	 * @param frame aktueller Frame
	 * @param ramtop neue Position
	 */
	private void setRamTop(Frame frame, int ramtop) {
		FrameSlot slot = frame.getFrameDescriptor().findOrAddFrameSlot("RAMTOP");
		frame.setInt(slot, ramtop);
	}

	private byte[] getRam(Frame frame) {
		byte[] result;
		FrameSlot slot = frame.getFrameDescriptor().findFrameSlot("RAM");
		if(slot != null) {
			result = (byte[]) FrameUtil.getObjectSafe(frame, slot);
		} else {
			result = new byte[1000]; // FIXME: Muss im Vorlauf bestimmt werden (DataDivision?)
			slot = frame.getFrameDescriptor().addFrameSlot("RAM");
			frame.setObject(slot, result);
		}
		return result;
		
	}
}
