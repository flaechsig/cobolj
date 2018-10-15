package de.cobolj.parser;

import com.oracle.truffle.api.frame.FrameDescriptor;

import de.cobolj.CobolLanguage;
import de.cobolj.nodes.CompilationUnitNode;
import de.cobolj.nodes.StartRuleNode;

/**
 * Startknoten des Parsers.
 * 
 * @author flaechsig
 *
 */
public class StartRuleVisitor extends Cobol85BaseVisitor<StartRuleNode>{

	/** Zugrundeliegende Sprache für den Truffle-AST */
	private CobolLanguage language;
	
	/** Wird bereits beim Aufbau des AST benötigt */
	public static FrameDescriptor descriptor = new FrameDescriptor();
	
	public StartRuleVisitor(CobolLanguage language) {
		this.language = language;
	}
	
	/** 
	 * Oberster Knoten, bei dem die Verarbeitung des Syntax-Baums startet.
	 */
	@Override 
	public StartRuleNode visitStartRule(Cobol85Parser.StartRuleContext ctx) { 
		CompilationUnitVisitor unitVisitor = new CompilationUnitVisitor();
		CompilationUnitNode compilationUnit = unitVisitor.visit(ctx.compilationUnit());
		StartRuleNode root = new StartRuleNode(this.language, descriptor, compilationUnit);
		return root;
	}

}
