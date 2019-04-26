package de.cobolj.parser.statement.subtract;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.SubtractFromStatementContext;
import de.cobolj.parser.statement.CalculationResult;
import de.cobolj.parser.statement.LiteralOrIdentifierVisitor;
import de.cobolj.parser.statement.add.ResultIdentifierVisitor;
import de.cobolj.statement.subtract.SubtractFromStatementNode;

/**
 * subtractFromStatement:
 *     subtractSubtrahend+ FROM subtractMinuend+
 *     
 * @author flaechsig
 *
 */
public class SubtractFromStatementVisitor extends Cobol85BaseVisitor<SubtractFromStatementNode> {
 @Override
public SubtractFromStatementNode visitSubtractFromStatement(SubtractFromStatementContext ctx) {
		List<ExpressionNode> left;
		List<CalculationResult> results;
		List<FrameSlot> slots = new ArrayList<>();
		List<Boolean> roundeds = new ArrayList<>();
		
		LiteralOrIdentifierVisitor literalOrIdentifierVisitor = new LiteralOrIdentifierVisitor();
		left = ctx.literalOrIdentifier()
				.stream()
				.map(operand -> operand.accept(literalOrIdentifierVisitor))
				.collect(Collectors.toList());
		
		ResultIdentifierVisitor toVisitor = new ResultIdentifierVisitor();
		results = ctx.resultIdentifier()
				.stream()
				.map(result -> result.accept(toVisitor))
				.collect(Collectors.toList());
		
		for(CalculationResult singleResult : results) {
			slots.add(singleResult.slot);
			roundeds.add(singleResult.rounded);
		}
		
		return new SubtractFromStatementNode(left, slots, roundeds);
}
}
