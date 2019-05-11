package de.cobolj.division.environment;

public class OrganizationClause {
	public static enum RecordForm {LINE, RECORD, RECORD_BINARY, BINARY};
	public static enum FileForm{SEQUENTIAL, RELATIV, INDEXED};
	
	private final RecordForm recordForm;
	private final FileForm fileForm;

	public OrganizationClause(RecordForm recordForm, FileForm fileForm) {
		this.recordForm = recordForm;
		this.fileForm = fileForm;
	}

	public RecordForm getRecordForm() {
		return recordForm;
	}

	public FileForm getFileForm() {
		return fileForm;
	}
	
}
