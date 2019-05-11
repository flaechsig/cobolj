package de.cobolj.parser.statement.subtract;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.SubtractFromGivingStatementContext;
import de.cobolj.parser.statement.CalculationResult;
import de.cobolj.parser.statement.LiteralOrIdentifierVisitor;
import de.cobolj.parser.statement.add.MathImplNode;
import de.cobolj.parser.statement.add.ResultIdentifierVisitor;
import de.cobolj.statement.subtract.SubtractFromGivingStatementNode;

/**
 * subtractFromGivingStatement:
 *     literalOrIdentifier+ FROM minuend=literalOrIdentifier GIVING resultIdentifier+
 *         
 * @author flaechsig
 *
 */
public class SubtractFromGivingStatementVisitor extends Cobol85BaseVisitor<MathImplNode> {
	
@Override
public MathImplNode visitSubtractFromGivingStatement(SubtractFromGivingStatementContext ctx) {
	List<ExpressionNode> left;
	ExpressionNode right;
	List<CalculationResult> results;
	List<PictureNode> slots = new ArrayList<>();
	List<Boolean> roundeds = new ArrayList<>();
	
	LiteralOrIdentifierVisitor literaltOrIdentifierVisitor = new LiteralOrIdentifierVisitor();
	left = ctx.subtrahend
			.stream()
			.map(operand -> operand.accept(literaltOrIdentifierVisitor))
			.collect(Collectors.toList());
	right = ctx.minuend.accept(literaltOrIdentifierVisitor);
	results = ctx.resultIdentifier()
			.stream()
			.map(result -> result.accept(new ResultIdentifierVisitor()))
			.collect(Collectors.toList());
	
	for(CalculationResult singleResult : results) {
		slots.add(singleResult.slot);
		roundeds.add(singleResult.rounded);
	}
	return new SubtractFromGivingStatementNode(left, right, slots, roundeds);
}
}
