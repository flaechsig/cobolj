package de.cobolj.parser.statement.add;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.statement.CalculationResult;
import de.cobolj.parser.statement.LiteralOrIdentifierVisitor;
import de.cobolj.statement.add.AddToGivingStatementNode;

/**
 * addToGivingStatement: literalOrIdentifier+ ( TO literalOrIdentifier )? GIVING resultIdentifier+
 * 
 * @author flaechsig
 *
 */
public class AddToGivingStatementVisitor extends Cobol85BaseVisitor<MathImplNode> {

	@Override
	public MathImplNode visitAddToGivingStatement(Cobol85Parser.AddToGivingStatementContext ctx) {
		List<ExpressionNode> left;
		ExpressionNode right;
		List<CalculationResult> results;
		List<String> slots = new ArrayList<>();
		List<Boolean> roundeds = new ArrayList<>();
		
		LiteralOrIdentifierVisitor fromVisitor = new LiteralOrIdentifierVisitor();
		left = ctx.addSummand
				.stream()
				.map(operand -> operand.accept(fromVisitor))
				.collect(Collectors.toList());
		right = ctx.toSummand.accept(fromVisitor);
		results = ctx.resultIdentifier()
				.stream()
				.map(result -> result.accept(new ResultIdentifierVisitor()))
				.collect(Collectors.toList());
		
		for(CalculationResult singleResult : results) {
			slots.add(singleResult.slot);
			roundeds.add(singleResult.rounded);
		}
		return new AddToGivingStatementNode(left, right, slots, roundeds);
	}
}
