package de.cobolj;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/** 
 * Context, der zur Ausführungszeit eines Cobol-Programms benötigt wird.
 * Er enthält den Status des Programms und bildet Schnittstellen zur
 * Außenwelt.
 * 
 * @author flaechsig
 *
 */
public class CobolContext {
	/** Output-Stream für das Cobol-Programm */
	private PrintStream out;
	/** Input-Stream für das Cobol-Programm */
	private Scanner in;

	public CobolContext(InputStream in, OutputStream out) {
		assert in  != null: "in darf nicht null sein";
		assert out != null: "out darf nicht null sein";
		
		this.in = new Scanner(in);
		this.out = new PrintStream(out);
	}
	
	/**
	 * @return Liefert den Output-Stream für das Programm.
	 */
	public PrintStream getOut() {
		return out;
	}

	public Scanner getIn() {
		return in;
	}

}
