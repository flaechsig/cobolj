package de.cobolj.parser;

import java.io.IOException;
import java.io.InputStream;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;

@NodeInfo(shortName="CloseFile")
public class CloseFileNode extends CobolNode {
	
	private FrameSlot fileName;

	public CloseFileNode(FrameSlot fileName) {
		this.fileName = fileName;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {

		FrameSlot fsSlot = StartRuleVisitor.descriptor.findFrameSlot(fileName.getIdentifier().toString()+"_FS");
		Object stream = com.oracle.truffle.api.frame.FrameUtil.getObjectSafe(frame, fsSlot);
		if(stream == null) {
			throw new RuntimeException("File not open");
		}
		if(stream instanceof InputStream) {
			try {
				((InputStream)stream).close();
			} catch (IOException e) {
				// Exception beim schließen kann ignoriert werden
			}
		} else {
			throw new RuntimeException("Unknown file type");
		}
		return this;
	}

}
