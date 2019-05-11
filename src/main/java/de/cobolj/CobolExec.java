package de.cobolj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import de.cobolj.CobolExec.By.Type;

public class CobolExec {
	/** Kennzeichnung, ob der Aufruf für ein Main- oder Sub-Programm erfolgte */
	public enum ProgramType { MAIN, SUB };
	
	/** Speicherplatz für die zu übergebenden Parameter */
	public static final String USING_HOST = "usingHost";
	
	/** Speicherplatz für die Programm-Kennzeichnung */
	public static final String PROG_TYP = "progType";
	
	/**
	 * Map mit allen registrierten Cobol-Programmen
	 */
	private static final Map<String, Source> PROGRAM_MAP = new HashMap<>();

	/** Unterscheidung zwischen Referenz und Value-Calls */
	public static class By {
		enum Type {
			REFERENCE, VALUE
		};

		private final Type type;
		private Object value;

		public By(Type type, Object value) {
			this.type = type;
			this.value = value;
		}

		public Object getValue() {
			return value;
		}

		public <T> T getValue(T clazz) {
			return (T) value;
		}

		/**
		 * Prüft, ob der Wert übernommen werden darf. Bei Referenz ist dies möglich und
		 * bei einem Call-by-Value verboten. Wenn die Übernahme erlaub ist, dann wird
		 * der wert auch übernommen.
		 * 
		 * @param obj Ein String oder BigDecimmal
		 */
		public void checkAndSetValue(Object obj) {
			if (type == Type.VALUE) {
				return;
			}
			if (obj instanceof BigDecimal) {
				BigDecimal number = (BigDecimal) obj;
				if (Integer.class == value.getClass()) {
					value = number.intValue();
				} else if (Long.class == value.getClass()) {
					value = number.longValue();
				} else if (Float.class == value.getClass()) {
					value = number.floatValue();
				} else if (Double.class == value.getClass()) {
					value = number.doubleValue();
				} else {
					value = number;
				}
			}
		}

		public static By reference(Object value) {
			return new By(Type.REFERENCE, value);
		}

		public static By value(Object value) {
			return new By(Type.VALUE, value);
		}
	}

	/**
	 * Führt ein Cobol-Main-Programm aus.
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

		Value val = context.getPolyglotBindings();
		val.putMember(PROG_TYP, ProgramType.MAIN.toString());

		context.eval(source);
		context.close();
	}
	

	public static void execute(InputStream in, OutputStream out, String name) {
		Context context = org.graalvm.polyglot.Context.newBuilder(CobolLanguage.ID).in(in).out(out).build();
		Source source = PROGRAM_MAP.get(name);

		Value val = context.getPolyglotBindings();
		val.putMember(PROG_TYP, ProgramType.MAIN.toString());

		context.eval(source);
		context.close();
	}

	/**
	 * @see #call(InputStream, OutputStream, String, Object...)
	 */
	public static void callByValue(String name, Object... using) {
		List<By> byValue = new ArrayList<>();
		for (Object u : using) {
			byValue.add(new By(Type.VALUE, u));
		}
		call(System.in, System.out, name, byValue.toArray(new By[] {}));
	}

	/**
	 * Ruft ein Cobol-Sub-Programm auf.
	 * <p>
	 * Ein Cobol-Subprogramm definiert seine Schnittstelle über den Namen und die
	 * LINKAGE SECTION. Innerhalb eines Cobol-Programms wird ein Sub-Programm über
	 * den Befehl <code><b>CALL</b> Prg-Name [<b>USING</b> var_1, ...,var_n]</code>.
	 * Dieses Prinzip wird mit dieser Methode nachgebildet.
	 * </p>
	 * <p>
	 * Da es sich um einen Call bei Referenz handelt dürfen als <code>using</code>
	 * nur Variablen und keine Literale übergeben werden.
	 * </p>
	 * <p>
	 * <b>Abbildung von Picture auf Java-Objekte</b></br>
	 * <ul>
	 * <li>Picture A -> String
	 * <li>Picture X -> Stirng
	 * <li>Picture 9 -> Integer, Long
	 * <li>Picture 9V9 -> BigDecimal
	 * </ul>
	 * </p>
	 * 
	 * @param in    Zu verwendender InputStream
	 * @param out   Zu verwendeneder OutputStream
	 * @param name  Name des aufzurufenden Cobol-Subprogramms
	 * @param using Die Parameter, die an das Cobol-Subprogramm übergeben werden.
	 *              Entsprechend der Cobol-Standards muss die Reihenfolge und die
	 *              Typen der LINKAGE SECTION im Subprogramm entsprechen. Da
	 *              Javatypen mit Pictures nicht kompatibel sind muss sichergestellt
	 *              sein, dass die übergebenen Objekte in den Ziel-Pictures
	 *              augenommen werden können.
	 * @return Bei dieser Aufrufart werden alle <code>using</using> als Referenz
	 *         übergeben. D.h. das Cobol-Subprogramm ändert direkt die Werte und
	 *         nach der Rückkehr stehen die neuen Werte in den Parametern.
	 * @throws RuntimeException, wenn die <code>using</code> nicht zu den Picture
	 *                           der LINKAGE SEKTION kompatibel sind.
	 * 
	 */
	public static void call(InputStream in, OutputStream out, String name, By... using) {
		Context context = org.graalvm.polyglot.Context.newBuilder(CobolLanguage.ID).in(in).out(out).build();
		Source program = PROGRAM_MAP.get(name.toUpperCase());
		assert program != null : "Das angegebene Sub-Programm " + name + " wurde nicht registriert.";

		Value val = context.getPolyglotBindings();
		val.putMember(USING_HOST, using);
		val.putMember(PROG_TYP, ProgramType.SUB.toString());

		context.eval(program);
	}

	/**
	 * @see #call(InputStream, OutputStream, String, By[]) 
	 */
	public static void call(InputStream in, OutputStream out, String name, Collection<By> using) {
		call(in, out, name, using.toArray(new By[0]));
	}

	/**
	 * Registriert die übergebenen Cobol-Programme.
	 * 
	 * Bei den Programmen wird davon ausgegangen, dass sie in der übergebenen Form 
	 * 
	 * Es wird eine interne Map der Cobol-Programme zu den zugehörigen
	 * Source-Dateien aufgebaut.
	 * 
	 * @param programs Liste mit voll qualifizierten Cobol-Dateinamen (Classpath)
	 */
	public static void register(String... programs) {
		assert programs != null : "Die übergebene Liste darf nicht NULL sein";
		// FIXME: Unschöne Methode, da die ersten Zeilen des Programms angeschaut werden
		// müssen um den Programmnamen zu ermitteln. Hintergrund ist, dass Truffle eine
		// API bietet, um Programme nur zu parsen. Es besteht nur die Möglichkeit über
		// Context.eval(...) ein Programm auszuführen, was zu diesem Zeitpunkt aber noch
		// nicht erfolgen soll
		for (String program : programs) {
			assert program != null : "Der Classpath darf nicht NULL sein";
			InputStream is = CobolExec.class.getResourceAsStream(program);
			if (is == null) {
				throw new RuntimeException("Angegebenes Cobol-Programm existiert nicht");
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			// FIXME: Ressourcen aufräumen
			try {
				String line;
				String progName = null;
				while ((line = br.readLine()) != null) {
					progName = null;
					int idxStart = line.indexOf("PROGRAM-ID.");
					if (idxStart == -1) {
						// Zeile überlesen
						continue;
					}
					idxStart += 11; // Länge der gefundenen Zeichenkette aufaddieren
					int idxEnd = line.indexOf('.', idxStart); // Nach der Program-ID das Ende suchen
					if (idxEnd != -1) {
						progName = line.substring(idxStart, idxEnd);
					} else {
						// Programmname nicht in der selben Zeile -> weitersuchen
						while((line = br.readLine()) != null) {
							if(line.startsWith("*")) {
								// Kommentarzeile überpringen
								continue;
							}
							if(line.trim().equals(""))  {
								// leere Zeile überspringen
								continue;
							}
							idxEnd = line.indexOf('.');
							progName = line.substring(0, idxEnd);
							break;
						}
					}
					break;
				}
				if (progName == null) {
					throw new RuntimeException("Es wurde kein Cobol-Programmname gefunden");
				}
				progName = progName.trim().toUpperCase();
				CobolExec.PROGRAM_MAP
						.put(progName,
								Source.newBuilder(CobolLanguage.ID,
										new InputStreamReader(CobolExec.class.getResourceAsStream(program)), progName)
										.build());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
