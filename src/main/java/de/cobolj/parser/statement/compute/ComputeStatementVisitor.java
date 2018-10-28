package de.cobolj.parser.statement.compute;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.ComputeStatementContext;
import de.cobolj.parser.arithmetic.ArithmeticExpressionVisitor;
import de.cobolj.parser.statement.CalculationResult;
import de.cobolj.parser.SizePhraseVisitor;
import de.cobolj.phrase.SizePhraseNode;
import de.cobolj.statement.compute.ComputeStatementNode;

/**
 * 
 * computeStatement: COMPUTE computeStore+ (EQUALCHAR | EQUAL)
 * arithmeticExpression onSizeErrorPhrase? notOnSizeErrorPhrase? END_COMPUTE?
 * 
 * @author flaechsig
 *
 */
public class ComputeStatementVisitor extends Cobol85BaseVisitor<ComputeStatementNode> {
	@Override
	public ComputeStatementNode visitComputeStatement(ComputeStatementContext ctx) {
		List<CalculationResult> store = ctx.computeStore()
				.stream()
				.map(item -> item.accept(new ComputeStoreVisitor()))
				.collect(Collectors.toList());
		ExpressionNode arithmeticEx = ctx.arithmeticExpression().accept(new ArithmeticExpressionVisitor());
		List<FrameSlot> slots = new ArrayList<>();
		List<Boolean> roundeds = new ArrayList<>();
		SizePhraseNode successPhrase = null;
		SizePhraseNode errorPhrase = null;
		if(ctx.onSizeErrorPhrase()!=null) {
			errorPhrase = ctx.onSizeErrorPhrase().accept(new SizePhraseVisitor());
		}
		if(ctx.notOnSizeErrorPhrase()!=null) {
			successPhrase = ctx.notOnSizeErrorPhrase().accept(new SizePhraseVisitor());
		}

		for(CalculationResult singleResult : store) {
			slots.add(singleResult.slot);
			roundeds.add(singleResult.rounded);
		}
		return new ComputeStatementNode(arithmeticEx, slots, roundeds, successPhrase, errorPhrase);
	}
}
