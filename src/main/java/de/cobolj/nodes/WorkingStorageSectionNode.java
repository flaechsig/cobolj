package de.cobolj.nodes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;

import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.division.data.DataDescriptionEntryNode;
import de.cobolj.division.data.DataOccursClause;
import de.cobolj.runtime.Picture;
import de.cobolj.runtime.PictureGroup;

@NodeInfo(shortName = "WorkingStorageSection")
public class WorkingStorageSectionNode extends DataDivisionSectionNode {
	@Children
	private final DataDescriptionEntryNode[] dataDescriptionEntries;

	public WorkingStorageSectionNode(List<DataDescriptionEntryNode> dataDescriptionEntryNodes) {
		this.dataDescriptionEntries = dataDescriptionEntryNodes.toArray(new DataDescriptionEntryNode[0]);
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		List<Picture> rootLevelPictures = buildPictureListTree(0);
		// Die Liste enhält die First-Level-Elemente mit den Kindelementen,
		// die in dem Speicher angelegt werden müssen
		for (Picture pic : rootLevelPictures) {
			addToStorage(frame, pic);
		}
		return this;
	}

	/**
	 * Trägt ein Picture und alle Kind-Picture in den Storage ein.
	 * 
	 * @param pic
	 */
	private void addToStorage(VirtualFrame frame, Picture pic) {
		getContext().putPicture(frame, pic);
		if (pic instanceof PictureGroup) {
			for (Picture child : ((PictureGroup) pic).getChildren()) {
				addToStorage(frame, child);
			}
		}

	}

	private LinkedList<Picture> buildPictureListTree(int pos) {
		LinkedList<Picture> result = new LinkedList<>();
		if (dataDescriptionEntries.length == 0) {
			return result;
		}
		if (pos == dataDescriptionEntries.length - 1) {
			// Rekursionsausstieg
			result = expand(dataDescriptionEntries[pos].getPicture(), dataDescriptionEntries[pos].getOccurs());
		} else {
			result = buildPictureListTree(pos + 1);
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
	private LinkedList<Picture> expand(Picture pic, DataOccursClause occurs) {
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

}
