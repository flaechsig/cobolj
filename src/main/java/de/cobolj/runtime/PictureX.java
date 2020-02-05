package de.cobolj.runtime;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.MessageResolution;
import com.oracle.truffle.api.interop.TruffleObject;

import de.cobolj.phrase.SizeOverflowException;


@SuppressWarnings("serial")
@MessageResolution(receiverType = PictureX.class)
public class PictureX extends Picture implements Comparable<PictureX> {
	private String format; 
	
	public PictureX(int level, String name, String picture ) {
		super(level, name, picture.replace("B", "").length());
		
		String tmpFormat = picture.replace("X", "%c");
		tmpFormat = tmpFormat.replace("B", " ");
		format = tmpFormat;
	}

	@Override
	public ForeignAccess getForeignAccess() {
		return PictureXForeign.ACCESS;
	}

	@Override
	public void setValue(Object object) {
		byte[] value = StringUtils.rightPad(object.toString(), size, " ").getBytes();
		System.arraycopy(value, 0, memory, memPointer, size);
	}
	

	@Override
	public void setValue(Object object, boolean sizeCheck) throws SizeOverflowException {
		if(sizeCheck && object.toString().length() > size) {
			throw new SizeOverflowException();
		}
		setValue(object);
	}

	@Override
	public int compareTo(PictureX o) {
		return toString().compareTo(o.toString());
	}

	/**
	 * Prüft, ob das übergebene Object ebenfalls vom Typ PictureA ist
	 * 
	 * @param obj Zu prüfendes Objekt
	 * @return true, wenn das übergebene Objekt auch Pic9 ist.
	 */
	static boolean isInstance(TruffleObject obj) {
		return obj instanceof PictureX;
	}

	@Override
	public String getValue() {
		byte[] value = new byte[size];
		System.arraycopy(memory, memPointer, value, 0, size);
		String tmpValue = new String(value);
		return String.format(format, ArrayUtils.toObject(tmpValue.toCharArray()));
	}
	
	@Override
	public String toString() {
		return getValue();
	}

	@Override
	public void clear() {
		setValue("");
	}

}
