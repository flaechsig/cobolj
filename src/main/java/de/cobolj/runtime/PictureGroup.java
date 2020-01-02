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
		for(Picture otherPicture : other.children) {
			for(Picture childPicture : children) {
				if(otherPicture.name.equals(childPicture.getName()))  {
					childPicture.setValue(otherPicture.getValue());
					break;
				}
			}
		}
	}

	@Override
	public Collection<Picture> getValue() {
		return children;
	}

	/**
	 * Fügt der Gruppe einen weiteren Picture-Eintrag hinzu
	 * 
	 * @param name Name des Picture-Elements
	 * @param picture Picture-Eintrag, der der Gruppe hinzugefügt wird.
	 */
	public void add(String name, Picture picture) {
		// FIXME: durch die Einführung von OCCURS wieder rausgenommen. Überlegen was richtig ist.
//		for(Picture childPic : children) {
//			if(childPic.name.equals(name) && !childPic.isFiller()) {
//				throw new IllegalArgumentException("PictureGroup :"+name+" bereits vorhanden");
//			}
//		}
		this.children.add(picture);
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
}
