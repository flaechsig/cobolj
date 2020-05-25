package de.cobolj.division.data;

import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;
import de.cobolj.nodes.PictureNode;
import de.cobolj.runtime.PictureGroup;

@NodeInfo(shortName = "DataDescriptionEntry")
public abstract class DataDescriptionEntryNode extends CobolNode {

	protected final int level;

	protected final String name;
	/** Array-Definition für den Node */
	protected final DataOccursClause occurs;
	/** Initialwert des Node */
	protected final CobolNode value;
	/** Übergeordnete Node (mit kleineren Level) */
	protected DataDescriptionEntryNode dataDescParent;
	/** Vorgänger-Node auf selber Ebene */
	protected DataDescriptionEntryNode dataDescPresessor;
	@Child
	protected PictureNode dataRedefinesClause;
	protected PictureGroup parent;

	protected Integer subscript;
	protected static byte[] mem;
	protected static int memPointer;

	public DataDescriptionEntryNode(int level, String name, PictureNode dataRedefinesClause,
			DataOccursClause dataOccursClause, CobolNode value) {
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

	public CobolNode getValue() {
		return value;
	}

	public DataDescriptionEntryNode getDataDescParent() {
		return dataDescParent;
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}

	public static void setMem(byte[] mem) {
		DataDescriptionEntryNode.mem = mem;
	}

	public static void setMemPointer(int memPointer) {
		DataDescriptionEntryNode.memPointer = memPointer;
	}

	public void setParent(PictureGroup parent) {
		this.parent = parent;
	}
	
	public void setSubscript(Integer subscript) {
		this.subscript = subscript;
	}
}
