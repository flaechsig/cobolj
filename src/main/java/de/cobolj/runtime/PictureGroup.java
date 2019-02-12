package de.cobolj.runtime;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.MessageResolution;

import de.cobolj.phrase.SizeOverflowException;

@MessageResolution(receiverType = PictureX.class)
public class PictureGroup extends Picture {

	private ArrayList<Picture> children = new ArrayList<>();

	public PictureGroup() {
		super(0);
	}

	@Override
	public ForeignAccess getForeignAccess() {
		return PictureGroupForeign.ACCESS;
	}

	@Override
	public void setValue(Object object) {
		setValue(object, false);
	}

	@Override
	public void setValue(Object object, boolean sizeCheck) throws SizeOverflowException {
		PictureGroup other = (PictureGroup) object;
		
		clear();
		for(int i=0; i<children.size(); i++) {
			Picture self = children.get(i);
			Picture othter = other.children.get(i);
			try {
				self.setValue(othter);
			} catch(NumberFormatException e) {
				// FIXME: Verhalten in dies Fall ist nicht sauber definiert. Verschiede Compiler verhalten sich unterschiedlich
				self.clear();
			}
		}
	}

	@Override
	public List<Picture> getValue() {
		return children;
	}

	/**
	 * Fügt der Gruppe einen weiteren Picture-Eintrag hinzu
	 * 
	 * @param picture Picture-Eintrag, der der Gruppe hinzugefügt wird.
	 */
	public void add(Picture picture) {
		this.children.add(picture);
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (Picture pic : children) {
			buf.append(pic.toString());
		}
		return buf.toString();
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
		for(Picture pic : this.children) {
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
}
