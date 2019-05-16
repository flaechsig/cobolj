package de.cobolj.division.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

import org.apache.commons.lang3.SerializationUtils;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.runtime.Picture;

@NodeInfo(shortName = "DataDescriptionEntryFormat1")
public class DataDescriptionEntryFormat1Node extends DataDescriptionEntryNode {

	public DataDescriptionEntryFormat1Node(Picture picture, DataOccursClause occurs) {
		super(picture, occurs);
	}

	@Override
	public List<Picture> executeGeneric(VirtualFrame frame) {
		List<Picture> result = new ArrayList<>();
		if (occurs == null) {
			result.add(getPicture());
		} else {
			for (int i = 1; i <= occurs.getOccurs(); i++) {
				Picture tmpPic = SerializationUtils.clone(getPicture());
				tmpPic.setSubscript(i);
				result.add(tmpPic);
			}
		}
		return result;
	}
}
