package de.cobolj.nodes;

import java.math.BigDecimal;
import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.runtime.NumericPicture;
import de.cobolj.statements.StatementNode;

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
	private ExpressionNode condition;
	/** Schleifen-Anweisungen */
	@Children
	private final StatementNode[] statements;

	public PerformTimesNode(ExpressionNode condition, List<StatementNode> statements) {
		this.condition = condition;
		this.statements = statements.toArray(new StatementNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		long counter = longValue(condition,frame);
		for(long i = 0; i<counter; i++)  {
			for(int j=0; j<statements.length; j++) {
				StatementNode stmt = statements[j];
				stmt.executeGeneric(frame);
			}
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
