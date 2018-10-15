package de.cobolj.nodes;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.RootNode;

public class EvalRootNode extends RootNode {

	private CallTarget node;

	public EvalRootNode(TruffleLanguage<?> language, CallTarget node) {
		super(language);
		this.node = node;
	}

	@Override
	public Object execute(VirtualFrame frame) {
		CompilerDirectives.transferToInterpreterAndInvalidate();
		DirectCallNode callNode = DirectCallNode.create(node);
		return callNode.call(null);
	}

}
