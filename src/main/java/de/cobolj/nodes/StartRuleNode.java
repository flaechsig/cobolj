package de.cobolj.nodes;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RootNode;

import de.cobolj.statement.stop.StopStatementException;

@NodeInfo(shortName = "Start")
public class StartRuleNode extends RootNode {

	@Child
	private CompilationUnitNode compilationUnit;

	public StartRuleNode(TruffleLanguage<?> language, CompilationUnitNode compilationUnit) {
		super(language);
		this.compilationUnit = compilationUnit;
	}

	@Override
	public Object execute(VirtualFrame frame) {
		try {
			compilationUnit.executeGeneric(frame);
			return true;
		} catch (StopStatementException e) {
			// Fängt das Stop-Signal und beendet die Anwendung
			return true;
		}
	}

}
