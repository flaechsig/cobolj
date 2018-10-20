package de.cobolj.runtime;

import java.util.ArrayList;
import java.util.List;

import com.oracle.truffle.api.interop.ForeignAccess;

import de.cobolj.phrase.SizeOverflowException;

public class PictureGroup extends Picture {

	private List<Picture> children = new ArrayList<>();

	public PictureGroup() {
		super(0);
	}
	
	@Override
	public ForeignAccess getForeignAccess() {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public void setValue(Object object) {
		throw new RuntimeException("Not Implemented");

	}

	@Override
	public void setValue(Object object, boolean sizeCheck) throws SizeOverflowException {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public Object getValue() {
		throw new RuntimeException("Not Implemented");
	}

	/**
	 * Fügt der Gruppe einen weiteren Picture-Eintrag hinzu
	 * 
	 * @param picture Picture-Eintrag, der der Gruppe hinzugefügt wird.
	 */
	public void add(Picture picture) {
		this.children .add(picture);
		
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for(Picture pic : children) {
			buf.append(pic.toString());
		}
		return buf.toString();
	}
}
