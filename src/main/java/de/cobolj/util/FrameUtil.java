package de.cobolj.util;

import java.math.BigDecimal;

import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.runtime.NumericPicture;
import de.cobolj.runtime.Picture;

/**
 * Hilfsklasse für den Umgang mit häufigen Funktionen.
 * 
 * @author flaechsig
 *
 */
public class FrameUtil {
	private FrameUtil() {}
	
	/**
	 * 
	 * @param frame Aktiver Frame
	 * @param slot Slot mit dem Storage.
	 * @return Liefert eine BigDecimal aus dem FrameSlot
	 */
	public static BigDecimal getBigDecimal(Frame frame, FrameSlot slot) {
		return getNumericPicture(frame, slot).getBigDecimal();
	}
	
	/**
	 * Liefert ein NumericPicture anhand des Storage-Slot
	 * 
	 * @param frame Aktiver Frame
	 * @param slot Slot mit dem Storage.
	 */
	public static NumericPicture getNumericPicture(Frame frame, FrameSlot slot) {
			return (NumericPicture) getPicture(frame, slot);
	}

	/**
	 * @see FrameUtil#getNumericPicture(Frame, FrameSlot)
	 */
	public static Picture getPicture(Frame frame, FrameSlot slot) {
		return (Picture)com.oracle.truffle.api.frame.FrameUtil.getObjectSafe(frame, slot);
	}
}
