package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public class NodeHelper {

	/** Führt eine NULL-Prüfung für das Execute durch.
	 * 
	 * @param node Auszuführender Node (falls nicht NULL)
	 * @param oldResult Bisher erreichter Zustand (Result des vorherigen Aufrufs)
	 * @param frame Der Frame
	 * @return Das Ergebnis des Aufrufs für den übergebenen Node oder das bisherige (der Marker)
	 */
	public static Object excecuteGeneric(CobolNode node, Object oldResult, VirtualFrame frame) {
		if(node != null) {
			return node.executeGeneric(frame);
		}	
		return oldResult;
	}
	

	/** @see #excecuteGeneric(CobolNode, Object, VirtualFrame) */
	public static <T> T excecuteGeneric(CobolNode[] nodeArray, T oldResult, VirtualFrame frame) {
		T result = oldResult;
		for(CobolNode node : nodeArray) {
			result = (T) node.executeGeneric(frame);
		}
		return result;
	}


}
