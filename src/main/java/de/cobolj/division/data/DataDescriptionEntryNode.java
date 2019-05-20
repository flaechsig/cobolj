package de.cobolj.division.data;

import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.lang3.SerializationUtils;

import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;
import de.cobolj.nodes.PictureNode;
import de.cobolj.runtime.Picture;
import de.cobolj.runtime.PictureGroup;

@NodeInfo(shortName = "DataDescriptionEntry")
public abstract class DataDescriptionEntryNode extends CobolNode {

	protected final int level;
	protected final String name;
	private final Picture picture;
	protected final DataOccursClause occurs;
	protected final Object value;
	protected DataDescriptionEntryNode dataDescParent;
	@Child
	private PictureNode dataRedefinesClause;

	public DataDescriptionEntryNode(Picture picture, PictureNode dataRedefinesClause, DataOccursClause dataOccursClause, Object value) {
		this.level = picture.getLevel();
		this.name = picture.getName();
		this.picture = picture;
		this.dataRedefinesClause = dataRedefinesClause;
		this.occurs = dataOccursClause;
		this.value = value;
	}

	public Picture getPicture() {
		return picture;
	}

	public DataOccursClause getOccurs() {
		return occurs;
	}
	

	public static LinkedList<Picture> buildPictureListTree(DataDescriptionEntryNode[] dataDescriptionEntries, int pos) {
		LinkedList<Picture> result = new LinkedList<>();
		if (dataDescriptionEntries.length == 0) {
			return result;
		}
		if (pos == dataDescriptionEntries.length - 1) {
			// Rekursionsausstieg
			result = expand(dataDescriptionEntries[pos].getPicture(), dataDescriptionEntries[pos].getOccurs());
		} else {
			result = buildPictureListTree(dataDescriptionEntries, pos + 1);
			int firstPosLevel = result.get(0).getLevel();
			Picture actPicture = dataDescriptionEntries[pos].getPicture();
			Iterator<Picture> iter = result.iterator();

			if (actPicture.getLevel() < firstPosLevel) {
				// Alle untergeordneten Elemente als Kinder hinzufügen
				while (iter.hasNext() && actPicture.getLevel() < firstPosLevel) {
					Picture subPic = iter.next();
					if (actPicture.getLevel() >= subPic.getLevel()) {
						break; // Abbruch, da Ende der Sub-Struktur erreicht
					}
					subPic.setParent((PictureGroup) actPicture);
					iter.remove();
				}
				LinkedList<Picture> occursList = expand(actPicture, dataDescriptionEntries[pos].getOccurs());
				occursList.addAll(result);
				result = occursList;
			} else {
				LinkedList<Picture> occursList = expand(actPicture, dataDescriptionEntries[pos].getOccurs());
				occursList.addAll(result);
				result = occursList;
			}
		}
		return result;
	}

	/**
	 * Expandiert ein einzelnes Picture auf die Anzahl notwendiger Occurs.
	 * 
	 * @param pic    Picture, das überprüft wird
	 * @param occurs Anzahl der notwendigen Wiederholungen
	 * @return Liste mit der Anzahl Pictures entsprechend Occurs
	 */
	private static LinkedList<Picture> expand(Picture pic, DataOccursClause occurs) {
		LinkedList<Picture> result = new LinkedList<Picture>();
		int count = occurs != null ? occurs.getOccurs() : 1;
		if (count == 1) {
			result.add(pic);
		} else {
			for (int i = 1; i <= count; i++) {
				Picture clonePic = SerializationUtils.clone(pic);
				clonePic.setSubscript(i);
				result.add(clonePic);
			}
		}
		return result;
	}
	
	public static void buildHierarchie(DataDescriptionEntryNode[] entries) {
		if(entries.length == 0) {
			return;
		}
		entries[0].dataDescParent = null; // Per Definition hat das erste Element kein Parent
		for(int i=1; i<entries.length; i++) {
			DataDescriptionEntryNode actNode = entries[i];
			for(int j=i-1; j>=0; j--) {
				DataDescriptionEntryNode prevNode = entries[j];
				if(actNode.level == prevNode.level) {
					actNode.dataDescParent = prevNode.dataDescParent;
					break;
				} else if (prevNode.level < actNode.level) {
					actNode.dataDescParent = prevNode;
					break;
				} else /* prevNode.level < actNode.level */ {
					continue;
				}
			}
		}
	}
	
	public String getQualifiedName() {
		return picture.getName() + (dataDescParent!=null?(" OF " +dataDescParent.getQualifiedName() ):"");
	}

	public Object getValue() {
		return value;
	}
	
	public DataDescriptionEntryNode getDataDescParent() {
		return dataDescParent;
	}
}
