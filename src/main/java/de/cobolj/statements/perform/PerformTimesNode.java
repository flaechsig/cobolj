package de.cobolj.statements.perform;

import java.math.BigDecimal;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.runtime.NumericPicture;

/**
 * Implementiert das Statement PERFORM XXXX TIMES ... END PERFORM.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName="PerformTimes")
public class PerformTimesNode extends PerformTypeNode {

	/** Schleifenzähler */
	@Child
	private ExpressionNode times;
	public PerformTimesNode(ExpressionNode times, PerformStatementNode perform) {
		super(perform);
		this.times = times;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		long counter = longValue(times,frame);
		for(long i = 0; i<counter; i++)  {
			perform.executeGeneric(frame);
		}
		return null;
	}
	
	/** 
	 * Liefert den long-Wert des Ausdrucks.
	 * 
	 * @param node Zu transformierender Ausdruck.
	 * @return Long-Wert oder Exception
	 */
	private long longValue(ExpressionNode node, VirtualFrame frame) {
		long result;
		Object value = node.executeGeneric(frame);
		if (value instanceof Long) {
			result = (long) value;
		} else if (value instanceof BigDecimal) {
			result = ((BigDecimal) value).longValue();
		} else if (value instanceof NumericPicture) {
			NumericPicture pic = (NumericPicture) value;
			result = ((BigDecimal) pic.getValue()).longValue();
		} else {
			throw new RuntimeException("Unerwarteter Datentyp " + value.getClass().getSimpleName());
		}
		return result;
	}

}
