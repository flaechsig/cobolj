package de.cobolj.parser.statement.call;

import static de.cobolj.parser.ParserHelper.accept;

import java.util.List;

import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.CallUsingParameterContext;
import de.cobolj.parser.IdentifierVisitor;
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
		List<PictureNode> parameterList;
		Type type;
		if (ctx.callByReferencePhrase() != null) {
			type = Type.REFERNCE;
			parameterList = accept(ctx.callByReferencePhrase().identifier(), IdentifierVisitor.INSTANCE);
		} else {
			type = Type.CONTENT;
			parameterList = accept(ctx.callByContentPhrase().identifier(), IdentifierVisitor.INSTANCE);
		}
		return new CallUsingParameterNode(type, parameterList);
	}
}
