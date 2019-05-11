package de.cobolj.parser;

public class CobolWordVisitor extends Cobol85BaseVisitor<String> {
	
	public static CobolWordVisitor INSTANCE = new CobolWordVisitor();
	
	private CobolWordVisitor() {	}

	@Override
	public String visitCobolWord(Cobol85Parser.CobolWordContext ctx) {
		return ctx.getText().toUpperCase();
	}
}
