package de.cobolj.nodes;

import java.math.BigDecimal;

import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.CobolContext;
import de.cobolj.CobolLanguage;
import de.cobolj.CobolTypes;
import de.cobolj.runtime.NumericPicture;

@TypeSystemReference(CobolTypes.class)
@NodeInfo(language = "Cobol")
public abstract class CobolNode extends Node {
	public abstract Object executeGeneric(VirtualFrame frame);

	/**
	 * @return Liefert den Context für das auszuführende Programm
	 */
	public final CobolContext getContext() {
		return getRootNode().getLanguage(CobolLanguage.class).getContextReference().get();
	}
	
	/**
	 * Diese Methode setzt voraus, dass es sich um einen Numerischen Wert handelt. In dem Fall wird der
	 * entsprechende Wert als BigDecimal zurückgeliefert ansonsten eine Exception geworfen.
	 * 
	 * @param frame Frame, in dem die Methode ausgeführt wird
	 * @param expression Ausdruck, aus dem der Wert ermittelt wird
	 * @return BigDecimal-Wert, der über den Ausdruck beschrieben wird.
	 */
	protected BigDecimal valueOf(VirtualFrame frame, ExpressionNode expression) {
		Object value = expression.executeGeneric(frame);
		if (value instanceof Long) {
			return BigDecimal.valueOf((long) value);
		} else if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		} else if (value instanceof NumericPicture) {
			NumericPicture pic = (NumericPicture) value;
			return pic.getBigDecimal();
		} else {
			throw new RuntimeException("Unerwarteter Datentyp " + value.getClass().getSimpleName());
		}
	}
}
