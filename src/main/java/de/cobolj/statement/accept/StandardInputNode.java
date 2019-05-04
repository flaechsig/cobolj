package de.cobolj.statement.accept;

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
	public String executeGeneric(VirtualFrame frame) {
		Scanner sc = new Scanner(getContext().getIn()); 
		String line = sc.nextLine();
		sc.close();
		return line;
	}

}
