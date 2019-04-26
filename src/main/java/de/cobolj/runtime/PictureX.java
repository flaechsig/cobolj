package de.cobolj.runtime;

import org.apache.commons.lang3.StringUtils;

import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.MessageResolution;
import com.oracle.truffle.api.interop.TruffleObject;

import de.cobolj.phrase.SizeOverflowException;


@MessageResolution(receiverType = PictureX.class)
public class PictureX extends Picture implements Comparable<PictureX> {
	
	private String value = "";
	
	public PictureX(String name, int size) {
		super(name, size);
	}

	@Override
	public ForeignAccess getForeignAccess() {
		return PictureXForeign.ACCESS;
	}

	@Override
	public void setValue(Object object) {
		this.value = StringUtils.truncate(object.toString(),size);
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
		return value.compareTo(o.value);
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
	public Object getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return StringUtils.rightPad(value, size, ' ');
	}

	@Override
	public void clear() {
		this.value = ""; 
	}

}
