package de.cobolj.parser;

import java.io.File;
import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.DataDivisionSectionNode;
import de.cobolj.statement.WriteElementaryItemNode;

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
	@Children
	private final WriteElementaryItemNode[] dataDescEntry;
	/** Assoziierte Datei */
	private File file = null;
	/** Assoziierter Stream. Kann sowohl für Input und Output dienen */
	private Object stream = null;

	public FileDescriptionEntryNode(String desc, String fileName, List<WriteElementaryItemNode> dataDescriptionEntries) {
		this.desc = desc;
		this.name = fileName;
		this.dataDescEntry = dataDescriptionEntries.toArray(new WriteElementaryItemNode[0]);
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		getContext().addFileDescriptor(this);
		for(WriteElementaryItemNode entry : dataDescEntry) {
			getContext().putPicture(entry.getValueNode().getPicture());
		}
		return this;
	}

	public String getName() {
		return name;
	}

	public WriteElementaryItemNode[] getDataDescriptionEntries() {
		return dataDescEntry;
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
