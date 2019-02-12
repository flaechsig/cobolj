package de.cobolj.parser;

import java.util.List;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.DataDivisionSectionNode;
import de.cobolj.statement.WriteElementaryItemNode;

/**
 * Beschreibung eines Dateiformats.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName="FileDescriptionEntry")
public class FileDescriptionEntryNode extends DataDivisionSectionNode {
	/** Beschreibung FD oder SD */
	private String desc;
	/** Symbolischer File-Name */
	private String fileName;
	/** Strukturelle Beschreibung der Datei */
	@Children
	private WriteElementaryItemNode[] dataDescEntry;

	public FileDescriptionEntryNode(String desc, String fileName, List<WriteElementaryItemNode> entries) {
		this.desc = desc;
		this.fileName = fileName;
		this.dataDescEntry = entries.toArray(new WriteElementaryItemNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		FrameSlot dataSlot = StartRuleVisitor.descriptor.findOrAddFrameSlot(fileName+"_DATA");
		frame.setObject(dataSlot, dataDescEntry);
		for(WriteElementaryItemNode node : dataDescEntry)  {
			node.executeGeneric(frame);
		}
		return this;
	}

}
