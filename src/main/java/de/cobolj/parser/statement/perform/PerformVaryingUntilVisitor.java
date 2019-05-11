package de.cobolj.parser.statement.perform;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.PerformUntilContext;
import de.cobolj.parser.condition.ConditionVisitor;
import de.cobolj.statement.perform.PerformVaryingExpressionNode;

/**
 * @see PerformUntilVisitor
 * 
 *      Diese Klasse ist sehr ähnlich zu der PerformUntilVisitor und
 *      interpretiert auch dieselbe Grammatik-Regel. Allerdings werden hier
 *      zusätzliche Parameter für die Schleifenvariablen berücksichtigt.
 * 
 *      TODO: Für Varying scheint die Grammatik nicht komplett korrekt zu sein
 *      TEST BEFORE / TEST AFTER ist hier nicht zugelassen.
 * 
 * @author flaechsig
 *
 */
public class PerformVaryingUntilVisitor extends Cobol85BaseVisitor<ExpressionNode> {
	/** Auszuführender Perform-Block */
	private ExpressionNode perform;
	/** Speicher für den Schleifenzähler */
	private PictureNode var;
	/** Startwert des Schleifenzählers */
	private ExpressionNode start;
	/** Schrittweite des Schleifenzählers */
	private ExpressionNode step;
	/** Kennzeichen, ob der Test vor oder nach dem Schleifenrumpf stattfindet */
	private boolean testBefore;

	public PerformVaryingUntilVisitor(ExpressionNode perform, boolean testBefore, PictureNode var, ExpressionNode start,
			ExpressionNode step) {
		this.perform = perform;
		this.testBefore = testBefore;
		this.var = var;
		this.start = start;
		this.step = step;
	}

	@Override
	public ExpressionNode visitPerformUntil(PerformUntilContext ctx) {
		ExpressionNode condition = ctx.condition().accept(new ConditionVisitor());
		return new PerformVaryingExpressionNode(testBefore, condition, perform, var, start, step);
	}
}
