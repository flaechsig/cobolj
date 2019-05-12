package de.cobolj.runtime;

import org.apache.commons.lang3.StringUtils;

import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.MessageResolution;
import com.oracle.truffle.api.interop.TruffleObject;

import de.cobolj.phrase.SizeOverflowException;


@SuppressWarnings("serial")
@MessageResolution(receiverType = PictureA.class)
public class PictureA extends Picture implements Comparable<PictureA> {
	
	private String value = "";
	
	public PictureA(String name, int size, PictureGroup parent) {
		super(name, size, parent);
	}

	@Override
	public ForeignAccess getForeignAccess() {
		return PictureAForeign.ACCESS;
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
	public int compareTo(PictureA o) {
		return value.compareTo(o.value);
	}

	/**
	 * Prüft, ob das übergebene Object ebenfalls vom Typ PictureA ist
	 * 
	 * @param obj Zu prüfendes Objekt
	 * @return true, wenn das übergebene Objekt auch Pic9 ist.
	 */
	static boolean isInstance(TruffleObject obj) {
		return obj instanceof PictureA;
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
