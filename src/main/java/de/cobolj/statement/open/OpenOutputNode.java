package de.cobolj.statement.open;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.management.RuntimeErrorException;

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
			if(!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			stream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			stream = null;
		} catch (IOException e) {
			throw new RuntimeException("Datei kann nicht angelegt werden. ("+file.getAbsolutePath()+")", e);
		}
		return stream;
	}

}
