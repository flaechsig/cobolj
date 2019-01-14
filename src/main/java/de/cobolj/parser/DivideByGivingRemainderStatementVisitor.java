package de.cobolj.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85Parser.DivideByGivingStatementRemainderContext;
import de.cobolj.parser.statement.CalculationResult;
import de.cobolj.parser.statement.LiteralOrIdentifierVisitor;
import de.cobolj.parser.statement.add.MathImplNode;
import de.cobolj.parser.statement.add.ResultIdentifierVisitor;
import de.cobolj.statement.divide.DivideIntoGivingRemainderStatementNode;

/**
 * divideByGivingStatementRemainder:
 *     DIVIDE  dividend=literalOrIdentifier BY divisor=literalOrIdentifier GIVING resultIdentifier REMAINDER resultIdentifier
 *    
 * @author flaechsig
 *
 */
public class DivideByGivingRemainderStatementVisitor extends Cobol85BaseVisitor<MathImplNode> {
	@Override
	public MathImplNode visitDivideByGivingStatementRemainder(DivideByGivingStatementRemainderContext ctx) {
		List<ExpressionNode> left = new ArrayList<>();
		ExpressionNode right;
		List<CalculationResult> results;
		List<FrameSlot> slots = new ArrayList<>();
		List<Boolean> roundeds = new ArrayList<>();
		
		left.add(ctx.divisor.accept(new LiteralOrIdentifierVisitor()));
		right = ctx.dividend.accept(new LiteralOrIdentifierVisitor());
		results = ctx.resultIdentifier()
				.stream()
				.map(result -> result.accept(new ResultIdentifierVisitor()))
				.collect(Collectors.toList());
		for(CalculationResult singleResult : results) {
			slots.add(singleResult.slot);
			roundeds.add(singleResult.rounded);
		}
		
		return new DivideIntoGivingRemainderStatementNode(left, right, slots, roundeds);
	}
}
