package de.cobolj.parser;

import java.io.File;

import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.nodes.ExpressionNode;

/**
 * Eine Instanz dieser Klasse repräsentiert eine Datei für den In- und Output.
 * 
 * @author flaechsig
 *
 */
public class AssignClauseNode extends FileControlClauseNode {
	@Child
	private ExpressionNode file;
	
	public AssignClauseNode(ExpressionNode file) {
		this.file = file;
	}
	
	@Override
	public File executeGeneric(VirtualFrame frame) {
		return new File((String) file.executeGeneric(frame));
	}

}
