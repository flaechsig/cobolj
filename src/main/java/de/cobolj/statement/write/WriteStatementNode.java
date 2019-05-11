package de.cobolj.statement.write;

import java.io.IOException;
import java.io.OutputStream;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.division.data.FileDescriptionEntryNode;
import de.cobolj.statement.StatementNode;

@NodeInfo(shortName = "WriteStatement")
public class WriteStatementNode extends StatementNode {
	private String recordName;

	public WriteStatementNode(String recordName) {
		this.recordName = recordName;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		// OutputStream öffnen
		FileDescriptionEntryNode fd = getContext().getFileDescriptorByRecord(recordName);
		Object stream = fd.getStream();
		if (stream == null) {
			throw new RuntimeException("File-Descriptor wurde nicht geöffnet");
		}
		OutputStream os = null;
		try {
			os = (OutputStream) stream;
		} catch (ClassCastException e) {
			throw new RuntimeException("File-Descriptor nicht zum Schreiben geöffnet", e);
		}
		try {
			os.write(getContext().getPicture(frame, recordName).toString().getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return this;
	}

}
