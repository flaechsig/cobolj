package de.cobolj.statement.open;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "OpenInput")
public class OpenInputNode extends OpenInputOutputNode {

	public OpenInputNode(FrameSlot fileSlot) {
		super(fileSlot);	}

	@Override
	protected Object getStream(File file) {
		FileInputStream stream;
		try {
			stream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			stream = null;
		}
		return stream;
	}

}
