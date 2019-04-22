package de.cobolj.statement.open;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameUtil;
import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.nodes.CobolNode;
import de.cobolj.parser.StartRuleVisitor;

/**
 * Repräsentation eines einzelen Files bzw. Input-Streams. Demnach liefert die
 * Methode {@link #executeGeneric(VirtualFrame)} einen InputStream oder OutputStream
 * zurück.
 * 
 * @author flaechsig
 *
 */
public abstract class OpenInputOutputNode extends CobolNode {

	private FrameSlot fileName;

	public OpenInputOutputNode(FrameSlot fileSlot) {
		this.fileName = fileSlot;
	}
	
	/** Liefert einen Input- bzw. OutputStream 
	 * 
	 * @param file Zu öffnendes File
	 * @return Stream
	 */
	protected abstract Object getStream(File file);

	/** Legt einen weiteren FrameSlot für den Input-Stream an */
	@Override
	public Object executeGeneric(VirtualFrame frame) {
		File file = (File) FrameUtil.getObjectSafe(frame, fileName);
		FrameSlot isSlot = StartRuleVisitor.descriptor.findOrAddFrameSlot(fileName.getIdentifier().toString()+"_FS");
		frame.setObject(isSlot, getStream(file));
		
		return this;
	}

}
