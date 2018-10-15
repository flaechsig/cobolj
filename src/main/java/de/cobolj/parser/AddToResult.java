package de.cobolj.parser;

import com.oracle.truffle.api.frame.FrameSlot;

public class AddToResult {

	public FrameSlot slot;
	public boolean rounded;

	AddToResult(FrameSlot slot, boolean rounded) {
		this.slot = slot;
		this.rounded = rounded;
	}
}
