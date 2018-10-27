package de.cobolj.parser;

import java.math.BigDecimal;
import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.runtime.NumericPicture;

/**
 * Implementierung des Exponenten. 
 * 
 * Es kann direkt eine Liste von Exponenten
 * angegeben werden. Dann wird der Exponent auf das Ergebnis der Reihe nach auf
 * das jeweilige Ergebnis ausgeführt.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName="Powers")
public class PowersNode extends ExpressionNode {

	/** Basis des Ausdrucks */
	@Child
	private ExpressionNode basis;
	/** Anzuwendende Exponenten */
	@Children
	private ExpressionNode[] powers;

	public PowersNode(ExpressionNode basis, List<ExpressionNode> powers) {
		this.basis = basis;
		this.powers = powers.toArray(new ExpressionNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		NumericPicture pic = (NumericPicture) basis.executeGeneric(frame);
		BigDecimal result = pic.getBigDecimal();
		for(ExpressionNode pow : powers ) {
			NumericPicture powPic = (NumericPicture) pow.executeGeneric(frame);
			BigDecimal exp = powPic.getBigDecimal();
			result = result.pow(exp.intValue());
		}
		return result;
	}

}
