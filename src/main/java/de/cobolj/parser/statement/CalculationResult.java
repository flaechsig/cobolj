package de.cobolj.parser.statement;

import com.oracle.truffle.api.frame.FrameSlot;

public class CalculationResult {

	public String slot;
	public boolean rounded;

	public CalculationResult(String slot, boolean rounded) {
		this.slot = slot;
		this.rounded = rounded;
	}
}
