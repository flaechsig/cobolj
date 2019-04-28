package de.cobolj.statement.unstring;

import de.cobolj.parser.statement.unstring.UnstringIntoVisitor;

/**
 * @see UnstringIntoVisitor
 *
 * @author flaechsig
 *
 */
public class UnstringInto {
	final String receiver;
	final String delimiter;
	final String count;

	public UnstringInto(String identifier, String delimiter, String count) {
		this.receiver = identifier;
		this.delimiter = delimiter;
		this.count = count;
	}

}
