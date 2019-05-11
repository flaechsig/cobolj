package de.cobolj.parser.statement.multiply;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.MultiplyGivingStatementContext;
import de.cobolj.parser.statement.CalculationResult;
import de.cobolj.parser.statement.LiteralOrIdentifierVisitor;
import de.cobolj.parser.statement.add.MathImplNode;
import de.cobolj.parser.statement.add.ResultIdentifierVisitor;
import de.cobolj.statement.multiply.MultiplyGivingStatementNode;

/**
 * 
 * multiplyGivingStatement:
 *     MULTIPLY multiplicant=literalOrIdentifier BY multiplicator=literalOrIdentifier GIVING resultIdentifier
 *     onSizeErrorPhrase? notOnSizeErrorPhrase? END_MULTIPLY?
 * 
 * @author flaechsig
 *
 */
public class MultiplyGivingStatementVisitor extends Cobol85BaseVisitor<MathImplNode> {
	@Override
	public MathImplNode visitMultiplyGivingStatement(MultiplyGivingStatementContext ctx) {
		List<ExpressionNode> left = new ArrayList<>();
		ExpressionNode right;
		List<CalculationResult> results;
		List<PictureNode> slots = new ArrayList<>();
		List<Boolean> roundeds = new ArrayList<>();

		LiteralOrIdentifierVisitor literaltOrIdentifierVisitor = new LiteralOrIdentifierVisitor();
		left.add(ctx.multiplicant.accept(literaltOrIdentifierVisitor));
		right = ctx.multiplicator.accept(literaltOrIdentifierVisitor);
		results = ctx.resultIdentifier().stream().map(result -> result.accept(new ResultIdentifierVisitor()))
				.collect(Collectors.toList());

		for (CalculationResult singleResult : results) {
			slots.add(singleResult.slot);
			roundeds.add(singleResult.rounded);
		}
		return new MultiplyGivingStatementNode(left, right, slots, roundeds);
	}
}
