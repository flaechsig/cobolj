package de.cobolj.division.data;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.DataDivisionSectionNode;

/**
 * Beschreibt die File Section innerhalt der DataDivision.
 * 
 * Dient "nur" als Container für die einzelnen Dateibeschreibungen
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName="FileSection")
public class FileSectionNode extends DataDivisionSectionNode {
	/** Liste der Dateibeschreibungen */
	@Children
	private final FileDescriptionEntryNode[] entries;

	public FileSectionNode(List<FileDescriptionEntryNode> entries) {
		this.entries = entries.toArray(new FileDescriptionEntryNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object last=null;
		for(FileDescriptionEntryNode node : entries) {
			last = node.executeGeneric(frame);
		}
		return last;
	}

}
