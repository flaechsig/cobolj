package de.cobolj.division.data;

import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;
import de.cobolj.runtime.Picture;

@NodeInfo(shortName = "DataDescriptionEntry")
public abstract class DataDescriptionEntryNode extends CobolNode {

	private final Picture picture;
	protected final DataOccursClause occurs;

	public DataDescriptionEntryNode(Picture picture, DataOccursClause dataOccursClause) {
		this.picture = picture;
		this.occurs = dataOccursClause;
	}

	public Picture getPicture() {
		return picture;
	}

	public DataOccursClause getOccurs() {
		return occurs;
	}

}
