package de.cobolj.parser.statement;

import com.oracle.truffle.api.frame.FrameSlot;

public class CalculationResult {

	public FrameSlot slot;
	public boolean rounded;

	public CalculationResult(FrameSlot slot, boolean rounded) {
		this.slot = slot;
		this.rounded = rounded;
	}
}
