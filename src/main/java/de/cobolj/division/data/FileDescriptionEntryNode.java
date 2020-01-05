package de.cobolj.division.data;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.division.environment.OrganizationClause.FileForm;
import de.cobolj.division.environment.OrganizationClause.RecordForm;
import de.cobolj.nodes.CobolNode;
import de.cobolj.parser.division.data.FileDescriptionEntryClauseNode;
import de.cobolj.runtime.Picture;
import de.cobolj.runtime.PictureGroup;

/**
 * Beschreibung eines Dateiformats.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName="FileDescriptionEntry")
public class FileDescriptionEntryNode extends CobolNode {
	/** Beschreibung FD oder SD */
	private String desc;
	/** Symbolischer File-Name */
	private String name;
	/** Strukturelle Beschreibung der Datei */
	@Children
	private final DataDescriptionEntryNode[] dataDescriptionEntry;
	/** Assoziierte Datei */
	private File file = null;
	/** Assoziierter Stream. Kann sowohl für Input und Output dienen */
	private Object stream = null;
	/** Kennzeichen, ob das File optional ist (und ggf. erzeugt wird) */
	private boolean optional;
	private FileForm fileForm;
	private RecordForm recordForm;
	@Children
	private FileDescriptionEntryClauseNode[] fileDescriptionEntryClause;

	public FileDescriptionEntryNode(String desc, String fileName, List<FileDescriptionEntryClauseNode> fileDescriptionEntryClause, List<DataDescriptionEntryNode> dataDescriptionEntry) {
		this.desc = desc;
		this.name = fileName;
		this.fileDescriptionEntryClause = fileDescriptionEntryClause.toArray(new FileDescriptionEntryClauseNode[0]);
		this.dataDescriptionEntry = dataDescriptionEntry.toArray(new DataDescriptionEntryNode[0]);
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		getContext().addFileDescriptor(this);
		DataDescriptionEntryNode.buildHierarchie(dataDescriptionEntry);
		
		AtomicInteger pos = new AtomicInteger(0);
		byte[] mem = new byte[1000];

		DataDescriptionEntryNode.setMem(mem);
		DataDescriptionEntryNode.setMemPointer(0);
		while (pos.get() < dataDescriptionEntry.length) {
			initializeMemory(frame, null, pos);
		}
		return this;
	}

	private void initializeMemory(VirtualFrame frame, PictureGroup parent, AtomicInteger pos) {
		DataDescriptionEntryNode act = dataDescriptionEntry[pos.get()];
		DataDescriptionEntryNode next = null;
		Picture pic = null;
		
		if (pos.get() + 1 < dataDescriptionEntry.length) {
			next = dataDescriptionEntry[pos.get() + 1];
		}
		
		int occurs = act.getOccurs() == null ? 1 : act.getOccurs().getOccurs();
		int oldPos = pos.get();
		for (int i = 1; i <= occurs; i++) {
			act.setParent(parent);
			act.setSubscript(act.getOccurs()==null?null:i);
			pic = (Picture) act.executeGeneric(frame);
			
			pos.set(pos.get() + 1);
			
			while (pos.get() < dataDescriptionEntry.length && next.getLevel() > act.getLevel()) {
				PictureGroup group = (PictureGroup) pic;
				initializeMemory(frame, group, pos);
				if (pos.get() < dataDescriptionEntry.length) {
					next = dataDescriptionEntry[pos.get()];
				}
			}
			
			// Pos zurücksetzen, wenn Occurs noch nicht abgearbeitet ist
			if(act.getOccurs() != null && i < occurs) {
				pos.set(oldPos);
			}
		}
	}
	
	public String getName() {
		return name;
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

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setFileForm(FileForm fileForm) {
		this.fileForm = fileForm;
	}
	
	public void setRecordForm(RecordForm recordForm) {
		this.recordForm = recordForm;
	}

	public FileForm getFileForm() {
		return fileForm;
	}

	public DataDescriptionEntryNode[] getDataDescriptionEntry() {
		return dataDescriptionEntry;
	}
	
	/**
	 * Trägt ein Picture und alle Kind-Picture in den Storage ein.
	 * 
	 * @param pic
	 */
	public void addToStorage(VirtualFrame frame, Picture pic) {
		getContext().putPicture(frame, pic);
		if (pic instanceof PictureGroup) {
			for (Picture child : ((PictureGroup) pic).getChildren()) {
				addToStorage(frame, child);
			}
		}
	}
}
