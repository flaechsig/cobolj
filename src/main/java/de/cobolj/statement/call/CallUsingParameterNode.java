package de.cobolj.statement.call;

import static de.cobolj.CobolExec.By.reference;
import static de.cobolj.CobolExec.By.value;

import java.util.LinkedHashMap;
import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.CobolExec.By;
import de.cobolj.nodes.CobolNode;
import de.cobolj.nodes.PictureNode;
import de.cobolj.runtime.Picture;

public class CallUsingParameterNode extends CobolNode {
	public static enum Type {REFERNCE, CONTENT};
	private final Type type;
	@Children
	private final PictureNode[] parameterList;
	
	public CallUsingParameterNode(Type type, List<PictureNode> parameterList) {
		this.type =  type;
		this.parameterList = parameterList.toArray(new PictureNode[0]);
	}

	@Override
	public LinkedHashMap<String,By> executeGeneric(VirtualFrame frame) {
		LinkedHashMap<String, By> parameters = new LinkedHashMap<>();
		
		for(PictureNode param : parameterList) {
			Picture pic = param.executeGeneric(frame);
			Object value = pic.getValue();
			if(Type.REFERNCE.equals(type)) {
				parameters.put(pic.getName(), reference(value));
			} else {
				parameters.put(pic.getName(), value(value));
			}
		}
		return parameters;
	}

}
