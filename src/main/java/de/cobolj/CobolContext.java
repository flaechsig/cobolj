package de.cobolj;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.parser.FileDescriptionEntryNode;
import de.cobolj.runtime.Picture;
import de.cobolj.statement.WriteElementaryItemNode;

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

	/** Liefert den FileDescriptor zu einem Record-Namen */
	public FileDescriptionEntryNode getFileDescriptorByRecord(String recordName) {
		for (FileDescriptionEntryNode fd : fileDescriptor) {
			for (WriteElementaryItemNode pic : fd.getDataDescriptionEntries()) {
				if (pic.getValueNode().getPicture().getName().equals(recordName)) {
					return fd;
				}
			}
		}
		throw new RuntimeException("FileDescriptor nicht gefunden");
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
	
	@Deprecated
	public FileDescriptionEntryNode getFileDescriptor(FrameSlot slot) {
		return getFileDescriptorByName(slot.getIdentifier().toString());
	}

	/** Fügt dem Context einen File-Descriptor hinzu. */
	public void addFileDescriptor(FileDescriptionEntryNode fileDescriptionEntryNode) {
		this.fileDescriptor.add(fileDescriptionEntryNode);
	}

	public void putPicture(Picture pic) {
		List<Picture> pictures = records.get(pic.getName());
		if (pictures == null) {
			pictures = new ArrayList<>();
			records.put(pic.getName(), pictures);
		}
		pictures.add(pic);
	}

	public Picture getPicture(String name) {
		List<Picture> pictures = records.get(name);
		if (pictures == null) {
			throw new RuntimeException("Angefragter Record existiert nicht: " + name);
		}
		return pictures.get(0);
	}

	@Deprecated
	public Picture getPicture(FrameSlot slot) {
		return getPicture(slot.getIdentifier().toString());
	}

}
