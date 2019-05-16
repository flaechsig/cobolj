package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.runtime.Picture;
import de.cobolj.runtime.PictureGroup;

public abstract class DataDivisionSectionNode extends CobolNode {
	/**
	 * Trägt ein Picture und alle Kind-Picture in den Storage ein.
	 * 
	 * @param pic
	 */
	protected void addToStorage(VirtualFrame frame, Picture pic) {
		getContext().putPicture(frame, pic);
		if (pic instanceof PictureGroup) {
			for (Picture child : ((PictureGroup) pic).getChildren()) {
				addToStorage(frame, child);
			}
		}

	}
}
