package de.cobolj.statements.stop;

import com.oracle.truffle.api.nodes.ControlFlowException;

/**
 * Durch diese Exception wird das Ende der Verarbeitung 
 * eingeleitet. Im Stop-Statment wird diese Exception
 * geworfen, um im obersten Knoten abgefangen zu werden
 * 
 * @author flaechsig
 *
 */
@SuppressWarnings("serial")
public class StopStatementException extends ControlFlowException {

    public static final StopStatementException SINGLETON = new StopStatementException();

}
