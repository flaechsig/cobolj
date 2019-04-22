package de.cobolj.runtime;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.MessageResolution;

import de.cobolj.phrase.SizeOverflowException;

@MessageResolution(receiverType = PictureX.class)
public class PictureGroup extends Picture {

	private LinkedHashMap<String, Picture> children = new LinkedHashMap<>();

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
		for(Entry<String, Picture> entry :  children.entrySet()) {
			Picture otherPic = other.children.get(entry.getKey());
			if(otherPic != null) {
				entry.getValue().setValue(otherPic);
			}	
		}
	}

	@Override
	public Collection<Picture> getValue() {
		return children.values();
	}

	/**
	 * Fügt der Gruppe einen weiteren Picture-Eintrag hinzu
	 * 
	 * @param name Name des Picture-Elements
	 * @param picture Picture-Eintrag, der der Gruppe hinzugefügt wird.
	 */
	public void add(String name, Picture picture) {
		if(children.containsKey(name)) {
			throw new IllegalArgumentException("PictureGroup :"+name+" bereits vorhanden");
		}
		this.children.put(name, picture);
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (Picture pic : getValue()) {
			buf.append(pic.toString());
		}
		return buf.toString();
	}

	@Override
	public void clear() {
		for(Picture pic : getValue()) {
			pic.clear();
		}
	}

	/** 
	 * Allgemeine Implementierung des Parsens muss für die Gruppe angepasst werden.
	 */
	@Override
	public int parse(InputStream is) {
		int result = 0;
		for(Picture pic : getValue()) {
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
