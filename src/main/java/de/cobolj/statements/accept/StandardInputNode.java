package de.cobolj.statements.accept;

import java.util.Scanner;

import com.oracle.truffle.api.frame.VirtualFrame;

/**
 * Die Klasse liefert die nächste Zeile des Standard-Inputs.
 * 
 * @author flaechsig
 *
 */
public class StandardInputNode extends InputNode {

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Scanner sc = getContext().getIn(); 
		return sc.nextLine();
	}

}
