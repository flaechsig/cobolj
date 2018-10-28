package de.cobolj.parser.statement.add;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.statement.CalculationResult;
import de.cobolj.statements.add.AddFromVisitor;
import de.cobolj.statemtent.add.AddToGivingNode;

/**
 * addToGivingStatement: addFrom+ ( TO addToGiving+ )? GIVING addGiving+
 * 
 * @author flaechsig
 *
 */
public class AddToGivingVisitor extends Cobol85BaseVisitor<AddImplNode> {

	@Override
	public AddImplNode visitAddToGivingStatement(Cobol85Parser.AddToGivingStatementContext ctx) {
		List<ExpressionNode> summands;
		List<CalculationResult> results;
		List<FrameSlot> slots = new ArrayList<>();
		List<Boolean> roundeds = new ArrayList<>();
		
		AddFromVisitor fromVisitor = new AddFromVisitor();
		summands = ctx.addFrom()
				.stream()
				.map(operand -> operand.accept(fromVisitor))
				.collect(Collectors.toList());
		summands.addAll(ctx.addToGiving()
				.stream()
				.map(operand -> operand.accept(new AddToGivenVisitor()))
				.collect(Collectors.toList()));
		
		AddToVisitor toVisitor = new AddToVisitor();
		results = ctx.addGiving()
				.stream()
				.map(result -> result.accept(toVisitor))
				.collect(Collectors.toList());
		
		for(CalculationResult singleResult : results) {
			slots.add(singleResult.slot);
			roundeds.add(singleResult.rounded);
		}
		return new AddToGivingNode(summands, slots, roundeds);
	}
}
