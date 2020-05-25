package de.cobolj.runtime;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.MessageResolution;

import de.cobolj.phrase.SizeOverflowException;

@SuppressWarnings("serial")
@MessageResolution(receiverType = PictureX.class)
public class PictureGroup extends Picture {

	private List<Picture> children = new ArrayList<>();

	public PictureGroup(int level, String name ) {
		super(level, name, 0);
	}

	@Override
	public void setValue(Object object) {
		PictureGroup group = (PictureGroup) object;
		byte[] tmpArray = new byte[getSize()];
		System.arraycopy(group.getMemory(), group.getMemPointer(), tmpArray, 0, Math.min(group.getSize(), getSize()));
		setValue(tmpArray);
	}

	@Override
	public void setValue(Object object, boolean sizeCheck) throws SizeOverflowException {
		clear();
		setValue(object, false);
	}

	@Override
	public String getValue() {
		StringBuffer buf = new StringBuffer();
		for (Picture pic : children) {
			buf.append(pic.getValue());
		}
		return buf.toString();
	}

	/**
	 * Fügt der Gruppe einen weiteren Picture-Eintrag hinzu
	 * 
	 * @param picture Picture-Eintrag, der der Gruppe hinzugefügt wird.
	 */
	public void add(Picture picture) {
		this.children.add(picture);
	}
	
	public void add(List<Picture> pictures) {
		this.children.addAll(pictures);
	}

	@Override
	public String toString() {
		return getValue();
	}

	@Override
	public void clear() {
		for(Picture pic : children) {
			pic.clear();
		}
	}

	/** 
	 * Allgemeine Implementierung des Parsens muss für die Gruppe angepasst werden.
	 */
	@Override
	public int parse(InputStream is) {
		int result = 0;
		for(Picture pic : children) {
			int readSize = pic.parse(is);
			if(readSize == -1) {
				result = -1;
				break;
			} else {
				result += readSize;
			}
		}
		return result;
	}

	public List<Picture> getChildren() {
		return children;
	}
	
	@Override
	public int getSize() {
		int result = 0; 
		for(Picture pic : getChildren()) {
			result += pic.getSize();
		}
		return result;
	}

	@Override
	public ForeignAccess getForeignAccess() {
		return PictureGroupForeign.ACCESS;
	}
}
