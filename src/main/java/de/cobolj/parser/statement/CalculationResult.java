package de.cobolj.parser.statement;

import de.cobolj.nodes.PictureNode;

public class CalculationResult {
	public PictureNode slot;
	public boolean rounded;

	public CalculationResult(PictureNode slot, boolean rounded) {
		this.slot = slot;
		this.rounded = rounded;
	}
}
