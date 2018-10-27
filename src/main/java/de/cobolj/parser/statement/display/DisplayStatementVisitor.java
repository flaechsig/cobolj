package de.cobolj.parser.statement.display;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.Cobol85Parser.DisplayStatementContext;
import de.cobolj.statements.display.DisplayOperandNode;
import de.cobolj.statements.display.DisplayStatementNode;

/**
 * displayStatement:    DISPLAY displayOperand+ displayAt? displayUpon? displayWith?
 * 
 * @author flaechsig
 *
 */
public class DisplayStatementVisitor extends Cobol85BaseVisitor<DisplayStatementNode> {

	@Override
	public DisplayStatementNode visitDisplayStatement(Cobol85Parser.DisplayStatementContext ctx) {
		DisplayOperandVisitor operandVisitor = new DisplayOperandVisitor();
		List<DisplayOperandNode> operands = ctx.displayOperand()
				.stream()
				.map(operand -> operand.accept(operandVisitor))
				.collect(Collectors.toList());
		
		// Wenn displayWith nicht gesetzt wurde, dann Zeilenumbruch anhängen
		boolean displayWith = ctx.displayWith()==null?true:false;
		return new DisplayStatementNode(operands, displayWith);
	}
}
