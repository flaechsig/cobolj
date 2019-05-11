package de.cobolj.parser.division.data;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;

/** 
 * Bisher eine leere Implementierung.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName=" FileDescriptionEntryClause")
public class FileDescriptionEntryClauseNode extends CobolNode {

	@Override
	public Object executeGeneric(VirtualFrame frame) {

		return this;
	}

}
