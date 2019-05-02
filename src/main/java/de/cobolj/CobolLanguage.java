package de.cobolj;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleLanguage.ContextPolicy;

import de.cobolj.nodes.CobolNode;
import de.cobolj.nodes.EvalRootNode;
import de.cobolj.nodes.StartRuleNode;
import de.cobolj.parser.Cobol85Lexer;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.StartRuleVisitor;

/**
 * Definition der Sprache.
 * 
 * Diese Klasse bietet den Einstiegspunkt in die Ausführung eines Programms. Die
 * führt das Parsing für eine spezielle Sprache durch und wird durch das
 * Truffle-Framework aufgerufen.
 * 
 * @author flaechsig
 *
 */
@TruffleLanguage.Registration(id = CobolLanguage.ID, name = "COBOL",version="85", characterMimeTypes = CobolLanguage.MIME_TYPE)
public class CobolLanguage extends TruffleLanguage<CobolContext> {
	public final static String ID = "cob";
	public final static String MIME_TYPE = "application/x-cobol";

	/**
	 * Erstellt den Kontext des Cobol-Programms. Dieser Context ist an
	 * die Ausführung eines einzelnen Programms gebunden.
	 * 
	 */
	@Override
	protected CobolContext createContext(Env env) {
		return new CobolContext(env.in(), env.out());
	}

	/**
	 * Prüfmethode, ob es sich bei dem übergebenen Objekt um ein 
	 * Objekt der Sprache Cobol handelt.
	 * 
	 * @param object Zu überprüfendes Objekt
	 * @return true, wenn es sich um ein Objekt von Cobol handelt. Sonst false
	 */
	@Override
	protected boolean isObjectOfLanguage(Object object) {
		return object instanceof CobolNode;
	}

	/**
	 * Parsing des Cobol-Quellcodes.
	 * 
	 * Der Quellcode wird hier mit Hilfe ANTLR-Parsers in die Truffle-Struktur
	 * überführt. Dazu wird das Visitor-Pattern von ANTLR verwendet und auf
	 * den einzelenen Ebenen des Syntax-Baums in Truffle-Nodes überführt.
	 * 
	 */
	@Override
	protected CallTarget parse(ParsingRequest request) throws Exception {
		CharStream cs = CharStreams.fromString(request.getSource().getCharacters().toString());
		Cobol85Lexer lexer = new Cobol85Lexer(cs);
		CommonTokenStream ts = new CommonTokenStream(lexer);
		de.cobolj.parser.Cobol85Parser parser = new Cobol85Parser(ts);

		// AST mit Truffle-Nodes aufbauen. Dafür für das ANTLR4-Visitor-Pattern verwendet
		StartRuleVisitor visitor = new StartRuleVisitor(this);
		StartRuleNode rootNode = visitor.visit(parser.startRule());

		// Es exitiert nur ein Node. Dieser ist somit der Root-Node
		RootCallTarget main = Truffle.getRuntime().createCallTarget(rootNode);
		return Truffle.getRuntime().createCallTarget(new EvalRootNode(this, main));
	}

}
