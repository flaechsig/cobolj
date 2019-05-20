package de.cobolj.division.data;

import java.io.File;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.division.environment.OrganizationClause;
import de.cobolj.division.environment.OrganizationClause.FileForm;
import de.cobolj.division.environment.OrganizationClause.RecordForm;
import de.cobolj.nodes.CobolNode;

@NodeInfo(shortName = "FileControlEntry")
public class FileControlEntryNode extends CobolNode {
	private String fileName;
	private final boolean optional;
	private final RecordForm recordForm;
	private final FileForm fileForm;

	@Child
	private AssignClauseNode assign;

	public FileControlEntryNode(String fileName, boolean optional, AssignClauseNode assign,
			OrganizationClause organizationClause) {
		this.fileName = fileName;
		this.optional = optional;
		this.assign = assign;
		if (organizationClause != null) {
			this.recordForm = organizationClause.getRecordForm();
			this.fileForm = organizationClause.getFileForm();
		} else {
			this.recordForm = RecordForm.RECORD;
			this.fileForm = FileForm.SEQUENTIAL;
		}
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		File file = assign.executeGeneric(frame);
		FileDescriptionEntryNode fd = getContext().getFileDescriptorByName(fileName);
		fd.setFile(file);
		fd.setOptional(optional);
		fd.setFileForm(fileForm);
		fd.setRecordForm(recordForm);
		return this;
	}

}
