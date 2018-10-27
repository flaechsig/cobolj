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
public class PerformUntilNode extends PerformTypeNode {
	/** Kennzeichen, ob die Schleifenbedingung vor dem Schleifen-Body geprüft werden soll */
	private final boolean testBefore;
	/** Schleifenzähler */
	@Child
	private ExpressionNode until;
	
	public PerformUntilNode(boolean testBefore, ExpressionNode until, PerformStatementNode perform) {
		super(perform);
		this.until = until;
		this.testBefore = testBefore;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		while(!(boolean) until.executeGeneric(frame))  {
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
