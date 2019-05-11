package de.cobolj.runtime;

import com.oracle.truffle.api.interop.ForeignAccess;

import de.cobolj.phrase.SizeOverflowException;

/** Merker-Objekt ohne Funktionalität, um Uneindeutigkeiten zu markieren. */
public class AmbigousPicture extends Picture {
	
	public static AmbigousPicture INSTANCE = new AmbigousPicture();
	
	private AmbigousPicture() {
		super("AmbigousPicture", 0, null);
	}
	
	@Override
	public ForeignAccess getForeignAccess() {
		return null;
	}

	@Override
	public void setValue(Object object) {

	}

	@Override
	public void setValue(Object object, boolean sizeCheck) throws SizeOverflowException {

	}

	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public void clear() {

	}

}
