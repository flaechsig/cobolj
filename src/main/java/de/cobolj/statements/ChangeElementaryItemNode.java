package de.cobolj.statements;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.runtime.Picture;

@NodeInfo(shortName = "ChangeElementaryItem")
@NodeField(name = "slot", type = FrameSlot.class)
@NodeChild("value")
@NodeField(name = "rounded", type= Boolean.class)
public abstract class ChangeElementaryItemNode extends ExpressionNode {
	
	/**
	 * @return Liefert den Slot für den Storage.
	 */
	protected abstract FrameSlot getSlot();
	
	/**
	 * @return Kennzeichen, ob der neue Wert kaufmännisch gerundet werden soll (sonsten wird einfach abgeschnitten)
	 */
	protected abstract Boolean getRounded();

	@Specialization
	protected Object writeObject(VirtualFrame frame, String value) {
		try {
			Picture picture = (Picture) frame.getObject(getSlot());
			picture.setValue(value);
			return value;
		} catch (FrameSlotTypeException e) {
			throw new RuntimeException(e);
		}
	}
}
