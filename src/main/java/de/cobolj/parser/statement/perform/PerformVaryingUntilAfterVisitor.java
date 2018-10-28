package de.cobolj.parser.statement.perform;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.PerformUntilContext;
import de.cobolj.parser.condition.ConditionVisitor;

/**
 * @see PerformUntilAfterVisitor
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
public class PerformVaryingUntilAfterVisitor extends Cobol85BaseVisitor<ExpressionNode> {
	/** Auszuführender Perform-Block */
	private ExpressionNode perform;
	/** Speicher für den Schleifenzähler */
	private FrameSlot var;
	/** Startwert des Schleifenzählers */
	private ExpressionNode start;
	/** Schrittweite des Schleifenzählers */
	private ExpressionNode step;
	/** Kennzeichen, ob der Test vor oder nach dem Schleifenrumpf stattfindet */
	private boolean testBefore;

	public PerformVaryingUntilAfterVisitor(ExpressionNode perform, boolean testBefore, FrameSlot var,
			ExpressionNode start, ExpressionNode step) {
		this.perform = perform;
		this.testBefore = testBefore;
		this.var = var;
		this.start = start;
		this.step = step;
	}

	@Override
	public ExpressionNode visitPerformUntil(PerformUntilContext ctx) {
		ExpressionNode condition = ctx.condition().accept(new ConditionVisitor());
		return new PerformAfterNode(testBefore, condition, perform, var, start, step);
	}
}
