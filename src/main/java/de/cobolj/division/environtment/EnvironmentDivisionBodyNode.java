package de.cobolj.division.environtment;

import static de.cobolj.nodes.NodeHelper.excecuteGeneric;

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
		result = excecuteGeneric(configurationSection, result, frame);
		result = excecuteGeneric(specialNamesParagraph, result, frame);
		result = excecuteGeneric(inputOutputSection, result, frame);
		return null;
	}

}
