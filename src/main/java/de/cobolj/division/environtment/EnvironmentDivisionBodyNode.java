package de.cobolj.division.environtment;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import de.cobolj.division.environment.ConfigurationSectionNode;
import de.cobolj.division.environment.SpecialNamesParagraphNode;
import de.cobolj.nodes.CobolNode;
import de.cobolj.statement.open.InputOutputSectionNode;

@NodeInfo(shortName="EnvironmentDivisionBody")
public class EnvironmentDivisionBodyNode extends CobolNode {
	@Child
	private ConfigurationSectionNode configurationSection;
	@Child
	private SpecialNamesParagraphNode specialNamesParagraph;
	@Child
	private InputOutputSectionNode inputOutputSection;

	public EnvironmentDivisionBodyNode(ConfigurationSectionNode configurationSection,
			SpecialNamesParagraphNode specialNamesParagraph, InputOutputSectionNode inputOutputSection) {
		this.configurationSection = configurationSection;
		this.specialNamesParagraph = specialNamesParagraph;
		this.inputOutputSection = inputOutputSection;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object result = null;
		if(configurationSection != null) {
			result = configurationSection.executeGeneric(frame);
		}
		if(specialNamesParagraph!=null) {
			result = specialNamesParagraph.executeGeneric(frame);
		}
		if(inputOutputSection!=null) {
			inputOutputSection.executeGeneric(frame);
		}

		return null;
	}

}
