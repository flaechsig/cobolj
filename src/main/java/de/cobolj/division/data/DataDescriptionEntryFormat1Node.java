package de.cobolj.division.data;

import java.util.List;

import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.runtime.Picture;

@NodeInfo(shortName="DataDescriptionEntryFormat1")
public class DataDescriptionEntryFormat1Node extends DataDescriptionEntryNode {

	public DataDescriptionEntryFormat1Node(int level, String name, List<Picture> picture, DataOccursClause dataOccursClause) {
		super(level, name, picture, dataOccursClause);
		
	}
}
