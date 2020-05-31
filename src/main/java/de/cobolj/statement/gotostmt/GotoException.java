package de.cobolj.statement.gotostmt;

import com.oracle.truffle.api.nodes.ControlFlowException;

/**
 *  Diese Exception dient als Marker für eine Goto-Anweisung.
 */
public class GotoException extends ControlFlowException {
    private final String paragraphName;

    public GotoException(String paragraphName) {
        this.paragraphName = paragraphName;
    }

    public String getParagraphName() {
        return paragraphName;
    }
}
