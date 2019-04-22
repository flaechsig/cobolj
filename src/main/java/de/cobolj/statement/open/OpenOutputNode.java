package de.cobolj.statement.open;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "OpenOutput")
public class OpenOutputNode extends OpenInputOutputNode {

	public OpenOutputNode(FrameSlot fileSlot) {
		super(fileSlot);	}

	@Override
	protected Object getStream(File file) {
		FileOutputStream stream;
		try {
			stream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			stream = null;
		}
		return stream;
	}

}
