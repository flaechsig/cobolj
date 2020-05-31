package de.cobolj.nodes;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import de.cobolj.statement.gotostmt.GotoException;

@NodeInfo(shortName = "ProcedureDivisionBody")
public class ProcedureDivisionBodyNode extends CobolNode {
	@Child
	private ParagraphsNode paragraphs;
	@Children
	private final ProcedureSectionNode[] sections;

	public ProcedureDivisionBodyNode(ParagraphsNode paragraphs, List<ProcedureSectionNode> sections) {
		this.paragraphs = paragraphs;
		this.sections = sections.toArray(new ProcedureSectionNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) throws GotoException {
		paragraphs.register();
		for(ProcedureSectionNode section : sections) {
			section.register();
		}

		Object last = null;

		StructureNode[] paras = getContext().getParagraphRegistry().values().toArray( new StructureNode[]{});
		for(int i=0; i<paras.length; i++) {
			try {
				last = paras[i].executeGeneric(frame);
			} catch (GotoException e) {
				String nextParagraph = e.getParagraphName();
				if(!getContext().getParagraphRegistry().containsKey(nextParagraph)) {
					throw e;
				}
				for(int j=0; j<paras.length;j++) {
					if(nextParagraph.equals(paras[j].getName())) {
						i=j-1;
					}
				}
			}
		}
		return last;
	}

}
