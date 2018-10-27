package de.cobolj.parser;

import java.util.Optional;

import org.antlr.v4.runtime.tree.TerminalNode;

import de.cobolj.nodes.CombinableCondition;
import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85Parser.CombinableConditionContext;

/**
 * 
 * combinableCondition: NOT? simpleCondition
 * 
 * @author flaechsig
 *
 */
public class CombinableConditionVisitor extends Cobol85BaseVisitor<ExpressionNode> {
	@Override
	public ExpressionNode visitCombinableCondition(CombinableConditionContext ctx) {
		Optional<TerminalNode> checkType = Optional.ofNullable(ctx.NOT());
		
		ExpressionNode child = ctx.accept(new SimpleConditionVisitor());
		return new CombinableCondition(!checkType.isPresent(), child);
	}
}
