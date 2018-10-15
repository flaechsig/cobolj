package de.cobolj.nodes;

import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.CobolContext;
import de.cobolj.CobolLanguage;
import de.cobolj.CobolTypes;

@TypeSystemReference(CobolTypes.class)
@NodeInfo(language = "Cobol")
public abstract class CobolNode extends Node {
	public abstract Object executeGeneric(VirtualFrame frame);

	/**
	 * @return Liefert den Context für das auszuführende Programm
	 */
	public final CobolContext getContext() {
		return getRootNode().getLanguage(CobolLanguage.class).getContextReference().get();
	}
}
