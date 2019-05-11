package de.cobolj.parser.division.environment;

import de.cobolj.division.environment.ConfigurationSectionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.ConfigurationSectionContext;

/**
 * configurationSection : CONFIGURATION SECTION DOT_FS
 * configurationSectionParagraph* ;
 * 
 * @author flaechsig
 *
 */
public class ConfigurationSectionVisitor extends Cobol85BaseVisitor<ConfigurationSectionNode> {
	@Override
	public ConfigurationSectionNode visitConfigurationSection(ConfigurationSectionContext ctx) {
		return new ConfigurationSectionNode();
	}

}
