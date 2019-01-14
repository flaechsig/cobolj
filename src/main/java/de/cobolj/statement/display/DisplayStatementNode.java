package de.cobolj.statement.display;

import java.util.List;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.statement.StatementNode;

/** Node-Repräsentation für ein Display-Statement.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName="Display")
@NodeChild(value="displayOperand", type=DisplayOperandNode.class)
public class DisplayStatementNode extends StatementNode {
	
	@Children
	private final DisplayOperandNode[] displayOperand;
	private Boolean displayWith;

	public DisplayStatementNode(List<DisplayOperandNode> displayOperand, Boolean displayWith) {
		this.displayOperand = displayOperand.toArray(new DisplayOperandNode[] {});
		this.displayWith = displayWith;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		StringBuffer buf = new StringBuffer();
		for(DisplayOperandNode child : displayOperand) {
			buf.append(child.executeGeneric(frame));
		}
		if(displayWith) {
			buf.append(System.lineSeparator()); // TODO: zumindest mal hintfragen, ob das mit dem Line-Separator OK ist
		}
		getContext().getOut().print(buf.toString());
		return buf.toString();
	}
	
}
