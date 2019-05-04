package de.cobolj.nodes;

import java.util.List;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.CobolExec;
import de.cobolj.division.data.LinkageSectionNode;

@NodeInfo(shortName="Data Division")
public class DataDivisionNode extends CobolNode {

	@Children
	private final DataDivisionSectionNode[] sections;
	
	public DataDivisionNode(List<DataDivisionSectionNode> sections) {
		this.sections = sections.toArray(new DataDivisionSectionNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object last = null;
		boolean isSubprogramm = false;
		for(DataDivisionSectionNode section : sections) {
			last = section.executeGeneric(frame);
			if(section instanceof LinkageSectionNode) {
				isSubprogramm = true;
			}
		}
		
		// Programm-Typ ermitteln
		Value bindings = Context.getCurrent().getPolyglotBindings();
		String progTyp = bindings.getMember(CobolExec.PROG_TYP).as(String.class);
		if(isSubprogramm) {
			if(!CobolExec.ProgramType.SUB.toString().equals(progTyp)) {
				throw new RuntimeException("Sub-Programme müssen über CALL aufgerufen werden (nicht execute)");
			}
		} else {
			if(!CobolExec.ProgramType.MAIN.toString().equals(progTyp)) {
				throw new RuntimeException("Hauptprogramme-Programme müssen über execute aufgerufen werden (nicht call)");
			}
		}
		return last;
	}

}
