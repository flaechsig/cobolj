package de.cobolj.nodes;

import java.util.Map;

import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.runtime.Picture;
import de.cobolj.runtime.PictureGroup;

public abstract class DataDivisionSectionNode extends CobolNode {
	/**
	 * Trägt ein Picture und alle Kind-Picture in den Storage ein.
	 * 
	 * @param pic
	 * @param values
	 */
	protected void addToStorage(VirtualFrame frame, Picture pic, Map<String, Object> values) {
		getContext().putPicture(frame, pic);
		Object value = values.get(pic.getQualifiedName());
		if (value != null) {
			pic.setValue(value);
		}
		if (pic instanceof PictureGroup) {
			for (Picture child : ((PictureGroup) pic).getChildren()) {
				addToStorage(frame, child, values);
			}
		}

	}
}
