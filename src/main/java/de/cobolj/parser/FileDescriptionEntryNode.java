package de.cobolj.parser;

import java.io.File;
import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.DataDivisionSectionNode;
import de.cobolj.runtime.Picture;

/**
 * Beschreibung eines Dateiformats.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName="FileDescriptionEntry")
public class FileDescriptionEntryNode extends DataDivisionSectionNode {
	/** Beschreibung FD oder SD */
	private String desc;
	/** Symbolischer File-Name */
	private String name;
	/** Strukturelle Beschreibung der Datei */
	private final Picture[] pictures;
	/** Assoziierte Datei */
	private File file = null;
	/** Assoziierter Stream. Kann sowohl für Input und Output dienen */
	private Object stream = null;

	public FileDescriptionEntryNode(String desc, String fileName, List<Picture> pictures) {
		this.desc = desc;
		this.name = fileName;
		this.pictures = pictures.toArray(new Picture[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		getContext().addFileDescriptor(this);
		for(Picture entry : pictures) {
			getContext().putPicture(frame, entry);
		}
		return this;
	}

	public String getName() {
		return name;
	}

	public Picture[] getPictures() {
		return pictures;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setStream(Object stream) {
		this.stream = stream;
	}

	public Object getStream() {
		return stream;
	}
}
