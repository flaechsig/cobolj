package de.cobolj.parser;

import java.io.InputStream;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameUtil;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.phrase.PhraseNode;
import de.cobolj.runtime.Picture;
import de.cobolj.runtime.PictureGroup;
import de.cobolj.statement.StatementNode;
import de.cobolj.statement.WriteElementaryItemNode;

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
		// InputStream Öffnen
		FrameSlot isSlot = StartRuleVisitor.descriptor.findFrameSlot(fileBase + "_FS");
		InputStream is = (InputStream) FrameUtil.getObjectSafe(frame, isSlot);
		// File-Statenstrukturen zum Input-Stream
		FrameSlot dataSlot = StartRuleVisitor.descriptor.findFrameSlot(fileBase + "_DATA");
		WriteElementaryItemNode[] fileData = (WriteElementaryItemNode[]) FrameUtil.getObjectSafe(frame, dataSlot);

		int read = 0;
		do {
			for (WriteElementaryItemNode node : fileData) {
				Picture pic = (Picture) FrameUtil.getObjectSafe(frame, node.getSlot());
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
