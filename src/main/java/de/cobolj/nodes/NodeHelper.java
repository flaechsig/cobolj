package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public class NodeHelper {

	/** Führt die NULL-Prüfung durch */
	public static void excecuteGeneric(CobolNode node, Object result, VirtualFrame frame) {
		if(node != null) {
			result = node.executeGeneric(frame);
		}	
	}

}
