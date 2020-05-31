package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import de.cobolj.statement.gotostmt.GotoException;


public class ProcedureSectionNode extends StructureNode {
	private final String name;
	@Child
	private ParagraphsNode paragraphs;

	public ProcedureSectionNode(String sectionName, ParagraphsNode paragraphs) {
		this.name = sectionName;
		this.paragraphs = paragraphs;
	}

	@Override
	public String getName() {
		return name;
	}

	public void register() {
		getContext().registerParagraph(name, this);
		paragraphs.register();
	}

	/**
	 * Führt die Pargraphen der Section aus
	 * @param frame Kontext des Aufrufs
	 * @return Letzte Ausgeführte Komando
	 * @throws GotoException Wird geworfen, wenn ein Goto den Paragraphen nicht im Kontext finden konnte
	 */
	@Override
	public Object executeGeneric(VirtualFrame frame) throws GotoException {
		return paragraphs.executeGeneric(frame);
	}

}
