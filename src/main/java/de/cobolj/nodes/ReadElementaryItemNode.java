package de.cobolj.nodes;

import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "ReadElementaryItem")
@NodeField(name = "slot", type = FrameSlot.class)
public abstract class ReadElementaryItemNode extends ExpressionNode {

	/**
	 * @return Liefert den Slot für den Storage.
	 */
	protected abstract FrameSlot getSlot();

	@Specialization
	protected Object readObject(VirtualFrame frame) {
//		return frame.getValue(getSlot());
		return getContext().getPicture(getSlot());
	}
}
