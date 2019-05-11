package de.cobolj.statement.unstring;

import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.statement.unstring.UnstringIntoVisitor;

/**
 * @see UnstringIntoVisitor
 *
 * @author flaechsig
 *
 */
public class UnstringInto {
	PictureNode receiver;
	PictureNode delimiter;
	PictureNode count;

	public UnstringInto(PictureNode identifier, PictureNode delimiter, PictureNode count) {
		this.receiver = identifier;
		this.delimiter = delimiter;
		this.count = count;
	}
}
