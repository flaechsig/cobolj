package de.cobolj.statement.read;

import java.io.InputStream;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.division.data.DataDescriptionEntryNode;
import de.cobolj.division.data.FileDescriptionEntryNode;
import de.cobolj.nodes.ExpressionNode;
import de.cobolj.phrase.PhraseNode;
import de.cobolj.runtime.Picture;
import de.cobolj.runtime.PictureGroup;
import de.cobolj.statement.StatementNode;

@NodeInfo(shortName = "ReadStatement")
public class ReadStatementNode extends StatementNode {
	private String fileBase;
	@Child
	private PhraseNode atEnd;
	@Child
	private PhraseNode notAtEnd;
	@Child
	private ExpressionNode readInto;

	public ReadStatementNode(String fileBase, ExpressionNode readInto, PhraseNode atEnd, PhraseNode notAtEnd) {
		this.readInto = readInto;
		this.fileBase = fileBase.toUpperCase();
		this.atEnd = atEnd;
		this.notAtEnd = notAtEnd;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		FileDescriptionEntryNode fd = getContext().getFileDescriptorByName(fileBase);
		// InputStream Öffnen
		Object stream = fd.getStream();
		if(stream == null) {
			throw new RuntimeException("File-Descriptor ist nicht geöffnet");
		}
		InputStream is = null;
		try  {
			is = (InputStream) stream;
		} catch( ClassCastException e) {
			throw new RuntimeException("File-Descriptor ist nicht zum LESEN geöffnet", e);
		}

		int read = 0;
		do {
			for (DataDescriptionEntryNode entry : fd.getDataDescriptionEntry()) {
				Picture pic = getContext().getPicture(frame, entry.getPicture().getQualifiedName());
				if (pic instanceof PictureGroup) {
					read = pic.parse(is);
					if (readInto != null && read > -1) {
						Picture destination = (Picture) readInto.executeGeneric(frame);
						destination.setValue(pic);
					}
				}
			}

			if (read > -1) {
				if (this.notAtEnd != null) {
					notAtEnd.executeGeneric(frame);
				}
			} else {
				if (this.atEnd != null) {
					atEnd.executeGeneric(frame);
				}
			}
		} while (read > -1);
		return this;
	}

}
