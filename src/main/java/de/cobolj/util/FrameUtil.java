package de.cobolj.util;

import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.runtime.NumericPicture;

/**
 * Hilfsklasse für den Umgang mit häufigen Funktionen.
 * 
 * @author flaechsig
 *
 */
public class FrameUtil {
	private FrameUtil() {}
	
	/**
	 * Liefert ein NumericPicture anhand des Storage-Slot
	 * 
	 * @param frame Aktiver Frame
	 * @param slot Slot mit dem Storage.
	 */
	public static NumericPicture getNumericPicture(Frame frame, FrameSlot slot) {
			return (NumericPicture) com.oracle.truffle.api.frame.FrameUtil.getObjectSafe(frame, slot);
	}
}
