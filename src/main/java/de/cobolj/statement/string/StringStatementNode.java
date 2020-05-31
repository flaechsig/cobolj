package de.cobolj.statement.string;

import java.util.List;

import de.cobolj.statement.gotostmt.GotoException;
import org.apache.commons.lang3.StringUtils;

import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.PictureNode;
import de.cobolj.phrase.PhraseNode;
import de.cobolj.runtime.NumericPicture;
import de.cobolj.runtime.Picture;
import de.cobolj.statement.StatementNode;

public class StringStatementNode extends StatementNode {

	@Children
	private StringSendingPhraseNode[] stringSending;
	@Child
	private PhraseNode onOverflowPhrase;
	@Child
	private PhraseNode notOnOverflowPhrase;
	@Child
	private PictureNode stringIntoPhrase;
	@Child
	private ExpressionNode stringWithPointerPhrase;

	public StringStatementNode(List<StringSendingPhraseNode> stringSending, PictureNode stringIntoPhrase, ExpressionNode stringWithPointerPhrase, PhraseNode onOverflowPhrase, PhraseNode notOnOverflowPhrase) {
		this.stringSending = stringSending.toArray(new StringSendingPhraseNode[] {});
		this.stringIntoPhrase = stringIntoPhrase;
		this.stringWithPointerPhrase = stringWithPointerPhrase;
		this.onOverflowPhrase = onOverflowPhrase;
		this.notOnOverflowPhrase = notOnOverflowPhrase;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) throws GotoException {
		StringBuffer buf = new StringBuffer();
		
		NumericPicture pointer = null;
		if(stringWithPointerPhrase != null) {
			pointer = (NumericPicture) stringWithPointerPhrase.executeGeneric(frame);
			String filler = StringUtils.leftPad("", pointer.getBigDecimal().intValue()-1);
			buf.append(filler);
		}
		
		for(ExpressionNode sending : stringSending) {
			buf.append(sending.executeGeneric(frame));
		}
		
		String resultString = buf.toString();
		Picture pic = stringIntoPhrase.executeGeneric(frame);
		pic.setValue(resultString);
		
		if(onOverflowPhrase != null && resultString.length() > pic.getSize()) {
			onOverflowPhrase.executeGeneric(frame);
		}
		if(notOnOverflowPhrase != null && resultString.length() <= pic.getSize()) {
			notOnOverflowPhrase.executeGeneric(frame);
		}
		if(pointer != null) {
			int newPos = Math.min(resultString.length(), pic.getSize());
			pointer.setValue(newPos+1);
		}
		
		return this;
	}

}
