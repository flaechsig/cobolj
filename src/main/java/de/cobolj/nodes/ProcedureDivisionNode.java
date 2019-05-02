package de.cobolj.nodes;

import java.util.List;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.CobolExec;
import de.cobolj.CobolExec.By;
import de.cobolj.runtime.Picture;
import de.cobolj.statement.exit.ExitStatementNode;

@NodeInfo(shortName = "ProcedureDivision")
public class ProcedureDivisionNode extends CobolNode {

	private final String[] usingDatanames;
	@Child
	private ProcedureDivisionBodyNode body;
	// FIXME: Die restlichen Member ergänzen

	public ProcedureDivisionNode(List<String> usingDatanames, ProcedureDivisionBodyNode body) {
		this.usingDatanames = usingDatanames.toArray(new String[] {});
		this.body = body;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		initUsing(frame);

		Object last = body.executeGeneric(frame);
		if (usingDatanames.length > 0 && !(last instanceof ExitStatementNode)) {
			throw new RuntimeException("Subprogramme müssen mit einem EXIT PROGRAM enden");
		}

		initHost(frame);
		return last;
	}

	private void initHost(VirtualFrame frame) {
		Context ctx = Context.getCurrent();
		Value value = ctx.getPolyglotBindings().getMember(CobolExec.USING_HOST);
		long size = value == null ? 0 : value.getArraySize();

		for (int i = 0; i < size; i++) {
			Picture pic = getContext().getPicture(frame, usingDatanames[i]);
			value.getArrayElement(i).as(By.class).checkAndSetValue(pic.getValue());
		}
	}

	private void initUsing(VirtualFrame frame) {
		Context ctx = Context.getCurrent();
		Value value = ctx.getPolyglotBindings().getMember(CobolExec.USING_HOST);
		long size = value == null ? 0 : value.getArraySize();
		if (usingDatanames.length != size) {
			throw new RuntimeException("Länge der USING-Liste (" + usingDatanames.length
					+ ") stimmt nicht mit den übergebenen Parametern (" + size + ") überein");
		}

		for (int i = 0; i < size; i++) {
			Picture pic = getContext().getPicture(frame, usingDatanames[i]);
			pic.setValue(value.getArrayElement(i).as(By.class).getValue());
		}
	}

}
