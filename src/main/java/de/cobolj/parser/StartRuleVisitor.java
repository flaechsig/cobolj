package de.cobolj.parser;

import static de.cobolj.parser.ParserHelper.accept;

import java.util.LinkedList;

import de.cobolj.CobolLanguage;
import de.cobolj.nodes.CompilationUnitNode;
import de.cobolj.nodes.StartRuleNode;
import de.cobolj.parser.condition.CompilationUnitVisitor;

/**
 * startRule : compilationUnit EOF ;
 * 
 * @author flaechsig
 *
 */
public class StartRuleVisitor extends Cobol85BaseVisitor<StartRuleNode> {

	/** Zugrundeliegende Sprache für den Truffle-AST */
	static public CobolLanguage language;

	/**
	 * Stack über Level einer Storage-Beschreibung. Wenn der Level eines DataEntry
	 * größer ist als der aktuelle Level, dann handelt sich um ein Kind-Element und
	 * der Stack muss um diesen Level vergrößert werden. Dadurch muss ggf. eine
	 * weitere Gruppe aufgebaut werden.
	 * 
	 * Hat der Level denselben Wert, dann handelt es sich um einen
	 * Geschwister-Knoten und er muss dem selben Parent zugeordnet werden.
	 * 
	 * Letztendlich signalisiert ein kleinerer Level den Abbau einer Hierarchieebene
	 * an und der Stack muss wieder verkleinert werden. Die Reduzierung kann dabei
	 * mehrere Level überspringen. Der Stack muss daher soweit abgebaut werden bis
	 * dieselbe Level-Nummer erreicht wird wie beim akuell untersuchten Element.
	 * 
	 * Nach dieser Beschreibung ist klar, dass sich ausschließlich
	 * Picture-Group-Elemente durch diesen Stack beschrieben werden.
	 */
	public static LinkedList<Integer> PICTURE_GROUP_LEVEL = new LinkedList<>();

	public StartRuleVisitor(CobolLanguage language) {
		StartRuleVisitor.language = language;
	}

	/**
	 * Oberster Knoten, bei dem die Verarbeitung des Syntax-Baums startet.
	 */
	@Override
	public StartRuleNode visitStartRule(Cobol85Parser.StartRuleContext ctx) {
		CompilationUnitNode compilationUnit = accept(ctx.compilationUnit(), new CompilationUnitVisitor());
		return new StartRuleNode(StartRuleVisitor.language, compilationUnit);
	}

}
