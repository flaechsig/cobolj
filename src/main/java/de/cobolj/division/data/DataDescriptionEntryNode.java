package de.cobolj.division.data;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;
import de.cobolj.runtime.Picture;

@NodeInfo(shortName="DataDescriptionEntry")
public abstract class DataDescriptionEntryNode extends CobolNode {

	protected final int level;
	protected final String name;
	protected final List<Picture> picture;
	protected final DataOccursClause occurs;

	public DataDescriptionEntryNode(int level, String name, List<Picture> picture, DataOccursClause dataOccursClause) {
		this.level = level;
		this.name = name;
		this.picture = picture;
		this.occurs = dataOccursClause;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		getContext().putDataDescriptionEntry(frame, this);
		return this;
	}

	public List<Picture> getPicture() {
		return picture;
	}

	public DataOccursClause getOccurs() {
		return occurs;
	}

	public String getName() {
		return name;
	}
	
	public String getQualifiedName() {
		return picture.get(0).getQualifiedName();
	}

}
