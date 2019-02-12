package de.cobolj.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameUtil;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;

/**
 * Repräsentation eines einzelen Files bzw. Input-Streams. Demnach liefert die
 * Methode {@link #executeGeneric(VirtualFrame)} einen InputStream
 * zurück.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "OpenInput")
public class OpenInputNode extends CobolNode {

	private FrameSlot fileName;

	public OpenInputNode(FrameSlot fileSlot) {
		this.fileName = fileSlot;
	}

	/** Legt einen weiteren FrameSlot für den Input-Stream an */
	@Override
	public Object executeGeneric(VirtualFrame frame) {
		File file = (File) FrameUtil.getObjectSafe(frame, fileName);
		FileInputStream is;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			is = null;
		}
		FrameSlot isSlot = StartRuleVisitor.descriptor.findOrAddFrameSlot(fileName.getIdentifier().toString()+"_FS");
		frame.setObject(isSlot, is);
		
		return this;
	}

}
