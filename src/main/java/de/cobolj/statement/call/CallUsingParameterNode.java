package de.cobolj.statement.call;

import static de.cobolj.CobolExec.By.reference;
import static de.cobolj.CobolExec.By.value;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.CobolExec.By;
import de.cobolj.nodes.CobolNode;
import de.cobolj.runtime.Picture;

public class CallUsingParameterNode extends CobolNode {
	public static enum Type {REFERNCE, CONTENT};
	private final Type type;
	private final List<String> parameterList;
	
	public CallUsingParameterNode(Type type, List<String> parameterList) {
		this.type =  type;
		this.parameterList = parameterList;
	}

	@Override
	public LinkedHashMap<String,By> executeGeneric(VirtualFrame frame) {
		LinkedHashMap<String, By> parameters = new LinkedHashMap<>();
		
		for(String param : parameterList) {
			Picture pic = getContext().getPicture(frame, param);
			Object value = pic.getValue();
			if(Type.REFERNCE.equals(type)) {
				parameters.put(param, reference(value));
			} else {
				parameters.put(param, value(value));
			}
		}
		return parameters;
	}

	public List<String> getParameterList() {
		return parameterList;
	}

}
