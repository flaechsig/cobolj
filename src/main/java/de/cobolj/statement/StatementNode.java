package de.cobolj.statement;

import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;

/**
 * Abstrakte Oberklasse für alle Cobol-Statements.
 */
@NodeInfo(shortName="Statement")
public abstract class StatementNode extends CobolNode {

}
