package de.cobolj.statement.string;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.phrase.PhraseNode;
import de.cobolj.runtime.Picture;
import de.cobolj.statement.StatementNode;

public class StringStatementNode extends StatementNode {

	@Children
	private StringSendingPhraseNode[] stringSending;
	@Child
	private PhraseNode onOverflowPhrase;
	@Child
	private PhraseNode notOnOverflowPhrase;
	private String stringIntoPhraseSlot;

	public StringStatementNode(List<StringSendingPhraseNode> stringSending, String stringIntoPhraseSlot, PhraseNode onOverflowPhrase, PhraseNode notOnOverflowPhrase) {
		this.stringSending = stringSending.toArray(new StringSendingPhraseNode[] {});
		this.stringIntoPhraseSlot = stringIntoPhraseSlot;
		this.onOverflowPhrase = onOverflowPhrase;
		this.notOnOverflowPhrase = notOnOverflowPhrase;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		StringBuffer buf = new StringBuffer();
		
		for(ExpressionNode sending : stringSending) {
			buf.append(sending.executeGeneric(frame));
		}
		
		String resultString = buf.toString();
		Picture pic = getContext().getPicture(frame, stringIntoPhraseSlot);
		pic.setValue(resultString);
		
		if(onOverflowPhrase != null && resultString.length() > pic.getSize()) {
			onOverflowPhrase.executeGeneric(frame);
		}
		if(notOnOverflowPhrase != null && resultString.length() <= pic.getSize()) {
			notOnOverflowPhrase.executeGeneric(frame);
		}
		
		return this;
	}

}
