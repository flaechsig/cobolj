package de.cobolj.statement.string;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.runtime.Picture;
import de.cobolj.statement.StatementNode;

public class StringStatementNode extends StatementNode {

	private StringSendingPhraseNode[] stringSending;
	private String stringIntoPhraseSlot;

	public StringStatementNode(List<StringSendingPhraseNode> stringSending, String stringIntoPhraseSlot) {
		this.stringSending = stringSending.toArray(new StringSendingPhraseNode[] {});
		this.stringIntoPhraseSlot = stringIntoPhraseSlot;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		StringBuffer buf = new StringBuffer();
		
		for(ExpressionNode sending : stringSending) {
			buf.append(sending.executeGeneric(frame));
		}
		
		Picture pic = getContext().getPicture(frame, stringIntoPhraseSlot);
		pic.setValue(buf.toString());
		
		return this;
	}

}
