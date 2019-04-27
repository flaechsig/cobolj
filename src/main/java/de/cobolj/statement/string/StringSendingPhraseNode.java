package de.cobolj.statement.string;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;

@NodeInfo(shortName="StringSendingPhrase")
public class StringSendingPhraseNode extends ExpressionNode {
	public enum DelimiterType {SIZE, IDENTIFIER, LITERAL};
	@Children
	private ExpressionNode[] stringSending;
	private DelimiterType delimiter;
	@Child
	private ExpressionNode referenz;
	
	public StringSendingPhraseNode(List<ExpressionNode> stringSending, DelimiterType delimiter, ExpressionNode referenz) {
		this.stringSending = stringSending.toArray(new ExpressionNode[] {});
		this.delimiter = delimiter;
		this.referenz = referenz;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		StringBuffer buf = new StringBuffer();
		
		for( ExpressionNode sender : stringSending) {
			String tmpText = sender.executeGeneric(frame).toString();
			switch(delimiter) {
			case SIZE:
				buf.append(tmpText);
				break;
			default:
				String refText = referenz.executeGeneric(frame).toString();
				int idx = tmpText.indexOf(refText);
				buf.append(tmpText.substring(0, idx));
			}
		}
		
		return buf.toString();
	}

}
