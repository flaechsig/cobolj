package de.cobolj.parser.statement.add;

import com.oracle.truffle.api.frame.FrameSlot;

public class AddToResult {

	public FrameSlot slot;
	public boolean rounded;

	public AddToResult(FrameSlot slot, boolean rounded) {
		this.slot = slot;
		this.rounded = rounded;
	}
}
