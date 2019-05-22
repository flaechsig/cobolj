package de.cobolj.division.data;

import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;
import de.cobolj.nodes.PictureNode;

@NodeInfo(shortName = "DataDescriptionEntry")
public abstract class DataDescriptionEntryNode extends CobolNode {

	protected final int level;
	protected final String name;
	/** Array-Definition für den Node */
	protected final DataOccursClause occurs;
	/** Initialwert des Node */
	protected final Object value;
	/** Übergeordnete Node (mit kleineren Level) */
	protected DataDescriptionEntryNode dataDescParent;
	/** Vorgänger-Node auf selber Ebene */
	protected DataDescriptionEntryNode dataDescPresessor;
	@Child
	private PictureNode dataRedefinesClause;

	public DataDescriptionEntryNode(int level, String name, PictureNode dataRedefinesClause,
			DataOccursClause dataOccursClause, Object value) {
		this.level = level;
		this.name = name;
		this.dataRedefinesClause = dataRedefinesClause;
		this.occurs = dataOccursClause;
		this.value = value;
	}

	public DataOccursClause getOccurs() {
		return occurs;
	}

	public static void buildHierarchie(DataDescriptionEntryNode[] entries) {
		if (entries.length == 0) {
			return;
		}
		entries[0].dataDescParent = null; // Per Definition hat das erste Element kein Parent
		for (int i = 1; i < entries.length; i++) {
			DataDescriptionEntryNode actNode = entries[i];
			for (int j = i - 1; j >= 0; j--) {
				DataDescriptionEntryNode prevNode = entries[j];
				if (actNode.level == prevNode.level) {
					actNode.dataDescParent = prevNode.dataDescParent;
					actNode.dataDescPresessor = prevNode;
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
		return name + (dataDescParent != null ? (" OF " + dataDescParent.getQualifiedName()) : "");
	}

	public Object getValue() {
		return value;
	}

	public DataDescriptionEntryNode getDataDescParent() {
		return dataDescParent;
	}

	public String getName() {
		return name;
	}
}
