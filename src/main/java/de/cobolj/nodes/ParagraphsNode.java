package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import de.cobolj.parser.statement.nextsentence.NextSentenceExcetion;
import de.cobolj.statement.gotostmt.GotoException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Klammer um Sentence und Pragraph
 */
@NodeInfo(shortName = "Paragraphs")
public class ParagraphsNode extends StructureNode {
    @Children
    private final ParagraphNode[] paragraphs;

    public ParagraphsNode(List<ParagraphNode> paragraphList) {
        this.paragraphs = paragraphList.toArray(new ParagraphNode[]{});
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) throws GotoException {
        Object last = null;

        last = processParagraphs(frame, last);
        return last;
    }

    /**
     * Verarbeitet die Paragraphen.
     *
     * @param last Bisheriger Wert des zu letzt ausgeführten Kommandos
     * @throws GotoException Wird geworfen, wenn ein Paragraph aufgerufen wird, der nicht
	 * 						 im Context vorhanden ist.
     */
    private Object processParagraphs(VirtualFrame frame, Object last) throws GotoException {
        Object result = last;
    	int i = findParagraphIndex(null);

		for(int j=i; j<paragraphs.length; j++) {
		    try {
                result = paragraphs[j].executeGeneric(frame);
            } catch (GotoException e) {
                j = findParagraphIndex(e.getParagraphName());
            } catch (NextSentenceExcetion e) {
		        continue;
            }
        }
		return result;
    }

    /**
     * @param paragraphName Name des gesuchten Pargraphen
     * @throws GotoException Wird geworfen, wenn der Name nicht gefunden werden konnte
     * @return Index des Paragraphen
     */
    private int findParagraphIndex(String paragraphName) throws GotoException {
        int i = 0;
        if(!StringUtils.isEmpty(paragraphName)) {
            i=-1;
            for(int j=0; j<=paragraphs.length;j++){
                if(paragraphName.equals(paragraphs[j].getName())) {
                    i = j;
                    break;
                }
            }
            if(i==-1) {
                throw new GotoException(paragraphName);
            }
        }
        return i;
    }

    public void register() {
        for (ParagraphNode paragraph : paragraphs) {
            paragraph.register();
        }
    }
}
