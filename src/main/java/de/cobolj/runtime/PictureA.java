package de.cobolj.runtime;

import org.apache.commons.lang3.StringUtils;

import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.MessageResolution;
import com.oracle.truffle.api.interop.TruffleObject;

import de.cobolj.phrase.SizeOverflowException;


@SuppressWarnings("serial")
@MessageResolution(receiverType = PictureA.class)
public class PictureA extends Picture implements Comparable<PictureA> {
	
//	private String value = "";
	
	public PictureA(int level, String name, int size ) {
		super(level, name, size);
	}

	@Override
	public ForeignAccess getForeignAccess() {
		return PictureAForeign.ACCESS;
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
	public int compareTo(PictureA o) {
//		return value.compareTo(o.value);
		return toString().compareTo(o.toString());
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
//		return value;
		byte[] value = new byte[size];
		System.arraycopy(memory, memPointer, value, 0, size);
		return new String(value);
	}
	
	@Override
	public String toString() {
//		return StringUtils.rightPad(value, size, ' ');
		return (String) getValue();
	}

	@Override
	public void clear() {
		byte[] value = StringUtils.rightPad("", size).getBytes();
		System.arraycopy(value, 0, memory, memPointer, size);
	}

}
