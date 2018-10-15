package de.cobolj.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.nodes.ExpressionNode;

/**
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
public class AddToStatementVisitor extends Cobol85BaseVisitor<AddImplNode> {

	@Override
	public AddImplNode visitAddToStatement(Cobol85Parser.AddToStatementContext ctx) {
		List<ExpressionNode> summands;
		List<AddToResult> results;
		List<FrameSlot> slots = new ArrayList<>();
		List<Boolean> roundeds = new ArrayList<>();
		
		AddFromVisitor fromVisitor = new AddFromVisitor();
		summands = ctx.addFrom()
				.stream()
				.map(operand -> operand.accept(fromVisitor))
				.collect(Collectors.toList());
		
		AddToVisitor toVisitor = new AddToVisitor();
		results = ctx.addTo()
				.stream()
				.map(result -> result.accept(toVisitor))
				.collect(Collectors.toList());
		
		for(AddToResult singleResult : results) {
			slots.add(singleResult.slot);
			roundeds.add(singleResult.rounded);
		}
		
		return new AddToStatementNode(summands, slots, roundeds);
	}

}
