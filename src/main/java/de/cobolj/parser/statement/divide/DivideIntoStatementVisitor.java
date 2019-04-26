package de.cobolj.parser.statement.divide;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.DivideIntoStatementContext;
import de.cobolj.parser.statement.CalculationResult;
import de.cobolj.parser.statement.LiteralOrIdentifierVisitor;
import de.cobolj.parser.statement.add.MathImplNode;
import de.cobolj.parser.statement.add.ResultIdentifierVisitor;
import de.cobolj.statement.divide.DivideIntoStatementNode;

/**
 * divideIntoStatement:
 *    DIVIDE literalOrIdentifier INTO resultIdentifier+
 *    
 * @author flaechsig
 *
 */
public class DivideIntoStatementVisitor extends Cobol85BaseVisitor<MathImplNode> {
	@Override
	public MathImplNode visitDivideIntoStatement(DivideIntoStatementContext ctx) {
		List<ExpressionNode> left = new ArrayList<>();
		List<CalculationResult> results;
		List<FrameSlot> slots = new ArrayList<>();
		List<Boolean> roundeds = new ArrayList<>();
		
		left.add(ctx.literalOrIdentifier().accept(new LiteralOrIdentifierVisitor()));
		results = ctx.resultIdentifier()
				.stream()
				.map(result -> result.accept(new ResultIdentifierVisitor()))
				.collect(Collectors.toList());
		for(CalculationResult singleResult : results) {
			slots.add(singleResult.slot);
			roundeds.add(singleResult.rounded);
		}
		
		return new DivideIntoStatementNode(left, slots, roundeds);
	}
}
