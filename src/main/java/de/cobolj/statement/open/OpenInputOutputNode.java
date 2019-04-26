package de.cobolj.statement.open;

import java.io.File;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.nodes.CobolNode;
import de.cobolj.parser.FileDescriptionEntryNode;

/**
 * Repräsentation eines einzelen Files bzw. Input-Streams. Demnach liefert die
 * Methode {@link #executeGeneric(VirtualFrame)} einen InputStream oder OutputStream
 * zurück.
 * 
 * @author flaechsig
 *
 */
public abstract class OpenInputOutputNode extends CobolNode {

	private String fileName;

	public OpenInputOutputNode(String fileSlot) {
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
		FileDescriptionEntryNode fd = getContext().getFileDescriptorByName(fileName);
		if(fd.getStream() != null) {
			throw new RuntimeException("File-Descriptor ist bereits geöffnet");
		}
		File file = fd.getFile();
		fd.setStream( getStream(file));
		
		return this;
	}

}
