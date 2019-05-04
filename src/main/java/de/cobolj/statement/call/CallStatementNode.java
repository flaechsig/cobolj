package de.cobolj.statement.call;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.CobolContext;
import de.cobolj.CobolExec;
import de.cobolj.CobolExec.By;
import de.cobolj.nodes.ExpressionNode;
import de.cobolj.runtime.Picture;
import de.cobolj.statement.StatementNode;

@NodeInfo(shortName="CallStatement")
public class CallStatementNode extends StatementNode {
	@Child
	private ExpressionNode progName;
	@Child
	private CallUsingPhraseNode callUsingPhrase;

	public CallStatementNode(ExpressionNode progName, CallUsingPhraseNode callUsingPhrase) {
		this.progName = progName;
		this.callUsingPhrase = callUsingPhrase;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		String name = progName.executeGeneric(frame).toString();
		LinkedHashMap<String, By> usingParameter = callUsingPhrase.executeGeneric(frame);
		
		CobolContext ctx = getContext();
		CobolExec.call(ctx.getIn(), ctx.getOut(), name, usingParameter.values());

		for(Entry<String, By> entry : usingParameter.entrySet()) {
			Picture pic = getContext().getPicture(frame, entry.getKey());
			Object value = entry.getValue().getValue();
			pic.setValue(value);
		}
		return this;
	}

}
