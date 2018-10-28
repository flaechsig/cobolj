package de.cobolj.parser;

import java.math.BigDecimal;
import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ArithmeticNode;

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
public class PowersNode extends ArithmeticNode {

	/** Basis des Ausdrucks */
	@Child
	private ArithmeticNode basis;
	/** Anzuwendende Exponenten */
	@Children
	private ArithmeticNode[] powers;

	public PowersNode(ArithmeticNode basis, List<ArithmeticNode> powers) {
		this.basis = basis;
		this.powers = powers.toArray(new ArithmeticNode[] {});
	}

	@Override
	public Number executeGeneric(VirtualFrame frame) {
		BigDecimal result = new BigDecimal(basis.executeGeneric(frame).toString());
		for(ArithmeticNode pow : powers ) {
			Number exp = pow.executeGeneric(frame);
			result = result.pow(exp.intValue());
		}
		return result;
	}

}
