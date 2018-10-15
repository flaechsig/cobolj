package de.cobolj;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;

public class CobolExec {

	/**
	 * Führt ein Cobol-Programm aus.
	 * 
	 * @param file Das Cobol-Programm
	 * @param in   Input-Stream für das Cobol-Programm
	 * @param out  Output-Stream für das Cobol-Programm
	 * @throws IOException Wird geworfen, wenn die Sourcen des Programms nicht
	 *                     gelesen werden können
	 */
	public static void execute(Reader file, InputStream in, OutputStream out) throws IOException {
		Context context = org.graalvm.polyglot.Context.newBuilder(CobolLanguage.ID).in(in).out(out).build();
		Source source = Source.newBuilder(CobolLanguage.ID, file, "static-in").build();
		context.eval(source);
	}
}
