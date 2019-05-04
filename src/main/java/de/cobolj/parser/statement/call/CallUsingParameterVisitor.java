package de.cobolj.parser.statement.call;

import java.util.List;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.CallUsingParameterContext;
import de.cobolj.parser.Cobol85Parser.IdentifierContext;
import de.cobolj.parser.IdentifierVisitor;
import de.cobolj.parser.ParserHelper;

import de.cobolj.parser.ParserHelper;
import de.cobolj.statement.call.CallUsingParameterNode;
import de.cobolj.statement.call.CallUsingParameterNode.Type;

/**
 * 
 * callUsingParameter : callByReferencePhrase | callByContentPhrase ;
 * 
 * @author flaechsig
 *
 */
public class CallUsingParameterVisitor extends Cobol85BaseVisitor<CallUsingParameterNode> {
	@Override
	public CallUsingParameterNode visitCallUsingParameter(CallUsingParameterContext ctx) {
		List<String> parameterList;
		Type type;
		if (ctx.callByReferencePhrase() != null) {
			type = Type.REFERNCE;
			parameterList = ParserHelper.accept(ctx.callByReferencePhrase().identifier(), IdentifierVisitor.INSTANCE);
		} else {
			type = Type.CONTENT;
			parameterList = ParserHelper.accept(ctx.callByContentPhrase().identifier(), IdentifierVisitor.INSTANCE);
		}
		return new CallUsingParameterNode(type, parameterList);
	}
}
