package de.cobolj.statement.open;

import java.io.File;
import java.io.IOException;

import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.division.data.FileDescriptionEntryNode;
import de.cobolj.nodes.CobolNode;
import de.cobolj.nodes.PictureNode;

/**
 * Repräsentation eines einzelen Files bzw. Input-Streams. Demnach liefert die
 * Methode {@link #executeGeneric(VirtualFrame)} einen InputStream oder
 * OutputStream zurück.
 * 
 * @author flaechsig
 *
 */
public abstract class OpenInputOutputNode extends CobolNode {
	private String fileName;

	public OpenInputOutputNode(String fileSlot) {
		this.fileName = fileSlot;
	}

	/**
	 * Liefert einen Input- bzw. OutputStream
	 * 
	 * @param file Zu öffnendes File
	 * @return Stream
	 */
	protected abstract Object getStream(File file);

	/** Legt einen weiteren FrameSlot für den Input-Stream an */
	@Override
	public Object executeGeneric(VirtualFrame frame) {
		FileDescriptionEntryNode fd = getContext().getFileDescriptorByName(fileName);
		if (fd.getStream() != null) {
			throw new RuntimeException("File-Descriptor ist bereits geöffnet");
		}
		File file = fd.getFile();
		if (!file.exists()) {
			if (fd.isOptional()) {
				file.getParentFile().mkdirs();
				try {
					file.createNewFile();
					fd.getStream();
				} catch (IOException e) {
					throw new RuntimeException("Datei "+file+" kann nicht angelegt werden",e);
				}
			} else {
				// FIXME: FileStatus auf 35 setzen.
			}
		} else {
			fd.setStream(getStream(file));
		}
		return this;
	}

}
