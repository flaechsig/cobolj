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
import de.cobolj.statement.add.AddToStatementNode;

/**
 * 
 * addToStatement: addFrom+ TO addTo+
 * 
 * Mit diesem Statement können mehrere Summanden auf mehrere andere Summanden
 * gleichzeitig wirken. Die Form ADD sum1 sum2 TO sum3 sum4. führt dazu, dass 
 * Summe von sum1 und sum2 (sum1+sum2) auf sum3 und sum4 hinzugerechnet wird. 
 * Die Operation hat somit zwei Ergebnisse (sum1+sum2+sum3) und (sum1+sum2+sum4).
 * Zusätzlich können die Ergebnisse einzeln noch mit dem Schlüsselwort ROUNDED 
 * versehen werden, um eine kaufmännische Rundung zu erzwingen.
 * 
 * @author flaechsig
 *
 */
public class AddToStatementVisitor extends Cobol85BaseVisitor<MathImplNode> {

	@Override
	public MathImplNode visitAddToStatement(Cobol85Parser.AddToStatementContext ctx) {
		List<ExpressionNode> summands;
		List<CalculationResult> results;
		List<String> slots = new ArrayList<>();
		List<Boolean> roundeds = new ArrayList<>();
		
		LiteralOrIdentifierVisitor fromVisitor = new LiteralOrIdentifierVisitor();
		summands = ctx.literalOrIdentifier()
				.stream()
				.map(operand -> operand.accept(fromVisitor))
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
		
		return new AddToStatementNode(summands, slots, roundeds);
	}

}
