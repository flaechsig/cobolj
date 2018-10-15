package de.cobolj.parser;

import de.cobolj.nodes.StringNode;

public class CobolWordVisitor extends Cobol85BaseVisitor<StringNode> {
	
	public static CobolWordVisitor INSTANCE = new CobolWordVisitor();
	
	private CobolWordVisitor() {	}

	@Override
	public StringNode visitCobolWord(Cobol85Parser.CobolWordContext ctx) {
		return new StringNode(ctx.getText());
	}
}
