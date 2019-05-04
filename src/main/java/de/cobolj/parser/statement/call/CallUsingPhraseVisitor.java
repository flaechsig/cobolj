package de.cobolj.parser.statement.call;

import de.cobolj.parser.Cobol85BaseVisitor;
import static de.cobolj.parser.ParserHelper.*;

import java.util.List;

import de.cobolj.parser.Cobol85Parser.CallUsingPhraseContext;
import de.cobolj.statement.call.CallUsingParameterNode;
import de.cobolj.statement.call.CallUsingPhraseNode;

/**
 * callUsingPhrase : USING callUsingParameter+ ;
 * 
 * @author flaechsig
 *
 */
public class CallUsingPhraseVisitor extends Cobol85BaseVisitor<CallUsingPhraseNode> {

	@Override
	public CallUsingPhraseNode visitCallUsingPhrase(CallUsingPhraseContext ctx) {
		List<CallUsingParameterNode> callUsingParameter = accept(ctx.callUsingParameter(), new CallUsingParameterVisitor());
		return new CallUsingPhraseNode(callUsingParameter);
	}
}
