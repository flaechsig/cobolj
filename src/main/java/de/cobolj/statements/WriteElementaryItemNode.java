package de.cobolj.statements;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.PictureNode;

/**
 * Legt ein PictureNode als FrameSlot an.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName="WriteElementaryItem")
@NodeChild(value="valueNode", type=PictureNode.class)
@NodeField(name = "slot", type = FrameSlot.class)
public abstract class WriteElementaryItemNode extends ExpressionNode {
	
	/** 
	 * @return Liefert den Slot für den Storage.
	 */
    protected abstract FrameSlot getSlot();

    /**
     * Speichert das Elementry Item als Object
     * TODO: Muss noch optimiert werden, da sich viele Fälle wohl als Primary abbilden lassen
     * 
     * @param frame Frame der Anwendung
     * @param value Wert, der gespeichert wird
     * @return value, dass übergeben wurde
     */
    @Specialization
    protected Object write(VirtualFrame frame, Object value) {
        frame.setObject(getSlot(), value);
        return value;
    }


}
