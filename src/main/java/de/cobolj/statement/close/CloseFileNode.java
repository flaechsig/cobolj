package de.cobolj.statement.close;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.division.data.FileDescriptionEntryNode;
import de.cobolj.nodes.CobolNode;
import de.cobolj.nodes.PictureNode;

@NodeInfo(shortName="CloseFile")
public class CloseFileNode extends CobolNode {
	private String fileName;

	public CloseFileNode(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		FileDescriptionEntryNode fd = getContext().getFileDescriptorByName(fileName);
		Object stream = fd.getStream();
		if(stream == null) {
			throw new RuntimeException("File not open");
		}
		if(stream instanceof InputStream) {
			try {
				((InputStream)stream).close();
				fd.setStream(null);
			} catch (IOException e) {
				// Exception beim schließen kann ignoriert werden
			}
		} else if(stream instanceof OutputStream) {
			try {
				((OutputStream)stream).close();
				fd.setStream(null);
			} catch (IOException e) {
				// Exception beim schließen kann ignoriert werden
			}
		} else {
			throw new RuntimeException("Unknown file type");
		}
		return this;
	}

}
