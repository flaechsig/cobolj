package de.cobolj.statement.call;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.CobolExec.By;
import de.cobolj.nodes.CobolNode;

@NodeInfo(shortName="CallUsingPhrase")
public class CallUsingPhraseNode extends CobolNode {
	@Children
	private final CallUsingParameterNode[] callUsingParameter;

	public CallUsingPhraseNode(List<CallUsingParameterNode> callUsingParameter) {
		this.callUsingParameter = callUsingParameter.toArray(new CallUsingParameterNode[0]);
	}

	@Override
	public LinkedHashMap<String, By> executeGeneric(VirtualFrame frame) {
		LinkedHashMap<String, By> usingParameter = new LinkedHashMap<>();
		for(CallUsingParameterNode parameter : callUsingParameter) {
			usingParameter.putAll(parameter.executeGeneric(frame));
		}
		return usingParameter;
	}
}
