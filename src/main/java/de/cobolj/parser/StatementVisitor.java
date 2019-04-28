package de.cobolj.parser;

import org.antlr.v4.runtime.RuleContext;

import de.cobolj.parser.statement.accept.AcceptStatementVisitor;
import de.cobolj.parser.statement.add.AddStatementVisitor;
import de.cobolj.parser.statement.compute.ComputeStatementVisitor;
import de.cobolj.parser.statement.continuestmt.ContinueStatementVisitor;
import de.cobolj.parser.statement.display.DisplayStatementVisitor;
import de.cobolj.parser.statement.divide.DivideStatementVisitor;
import de.cobolj.parser.statement.ifstmt.IfStatementVisitor;
import de.cobolj.parser.statement.move.MoveStatementVisitor;
import de.cobolj.parser.statement.multiply.MultiplyStatementVisitor;
import de.cobolj.parser.statement.perform.PerformStatementVisitor;
import de.cobolj.parser.statement.stop.StopStatementVisitor;
import de.cobolj.parser.statement.string.StringStatementVisitor;
import de.cobolj.parser.statement.subtract.SubtractStatementVisitor;
import de.cobolj.statement.StatementNode;

/**
 * Das Statement verhält sich wie eine abstrakte Oberklasse zu allen Statements.
 * 
 * statement : acceptStatement | addStatement | alterStatement | callStatement |
 * cancelStatement | closeStatement | computeStatement | continueStatement |
 * deleteStatement | disableStatement | displayStatement | divideStatement |
 * enableStatement | entryStatement | evaluateStatement | exhibitStatement |
 * execCicsStatement | execSqlStatement | execSqlImsStatement | exitStatement |
 * generateStatement | gobackStatement | goToStatement | ifStatement |
 * initializeStatement | initiateStatement | inspectStatement | mergeStatement |
 * moveStatement | multiplyStatement | openStatement | performStatement |
 * purgeStatement | readStatement | receiveStatement | releaseStatement |
 * returnStatement | rewriteStatement | searchStatement | sendStatement |
 * setStatement | sortStatement | startStatement | stopStatement |
 * stringStatement | subtractStatement | terminateStatement | unstringStatement
 * | writeStatement ;
 * 
 * @author flaechsig
 *
 */
public class StatementVisitor extends Cobol85BaseVisitor<StatementNode> {

	/**
	 * Der konkrete Typ eines Statements wird über den Namen ermittelt und anhand
	 * des Namens die korrekte Visitor-Implementierung aufgerufen.
	 * 
	 */
	@Override
	public StatementNode visitStatement(Cobol85Parser.StatementContext ctx) {
		Cobol85BaseVisitor<?> visitor = null;
		// Für jedes konkrete Statement existiert im Context eine Zugriffsmethode.
		// Allerdings liefert exakt nur eine einen Wert ungleich null. Diese ist
		// das einzige (relevante) Child-Element bzw. der Child-Context.
		RuleContext ctx2 = (RuleContext) ctx.getChild(0);
		int rule = ctx2.getRuleIndex();
		String statementName = Cobol85Parser.ruleNames[rule];

		// Anhande des Statement-Namens den korrekten Visitor konstruieren
		// <Statement>Visitor -> Der erste Buchstabe ist groß geschrieben
		// und der Statement-Name bekommte zusätzlich den Suffix "Visitor"
		String visitorName = statementName.substring(0, 1).toUpperCase() + statementName.substring(1) + "Visitor";

		// Visitor instanziieren. Dazu wird in dem bekannten Packages nach der Visitor-Klasse
		// gesucht.
		String statementPackage = statementName.substring(0, statementName.length()-9);
		String packages[] = {
				"de.cobolj.parser.statement."+statementPackage+".",
				"de.cobolj.parser.statement."+statementPackage+"stmt.",
				"de.cobolj.parser."
		};
		try {
			Class visitorClass = null;
			for(String packageName : packages) {
			  try {
				visitorClass = Class.forName(packageName+visitorName);
		      } catch (ClassNotFoundException e){}
			}
			visitor = (Cobol85BaseVisitor<?>) visitorClass.newInstance();
			return (StatementNode) ctx2.accept(visitor);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Unbekanntes Statement: " + statementName);
		}
	}
}
