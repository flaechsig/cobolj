package de.cobolj.statement.gotostmt;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import de.cobolj.division.data.IntegerNode;
import de.cobolj.nodes.PictureNode;
import de.cobolj.nodes.StringNode;
import de.cobolj.statement.StatementNode;

import java.util.List;

/**
 * Implementierung des Goto-Befehls.
 * <p>
 * Es existieren drei Varianten des Goto-Befehls
 * <ul>
 *     <item>Nicht Konditional<br/>
 *     Die Form ist GO TO <code>PARA</code>. Es Erfolgt ein direkter Sprung zum angegebenen Paragraphen
 *     </item>
 *     <item>Konditional<br/>
 *     Die Form ist GO TO <code>PARA-1 PARA-2 ....</code> ON DEPENDING <code>N</code>. Es wird in den
 *     N-ten Paragraph gesprungn, der mit N angebeben ist. N ist hierbei 1-basiert. Wenn es keinen N-ten
 *     Eintrag gibt, dann wir zum nächsten Eintrag fortgefahren.
 *     </item>
 *     <item>Altered (deprecated)</item>
 * </ul>
 */
@NodeInfo(shortName = "Goto")
public class GotoStatementNode extends StatementNode {
    /**
     * Liste der angegebenen Paragraphen
     */
    private String[] paragraphName;
    /**
     * Referenz auf das N-te Element der Paragraphen-Liste
     */
    @Child
    private PictureNode depending;


    public GotoStatementNode(List<String> paragraphs, PictureNode depending) {
        this.paragraphName = paragraphs.toArray(new String[]{});
        this.depending = depending;
    }

    /**
     * Führt den GoTo-Befehl aus. Bei erfolgreichem Sprung wird eine Goto-Exception geworfen, um
     * den Kontrollfluss zu ändern
     *
     * @param frame Kontext des Aufrufs
     * @return null, wenn kein Sprung durchgeführt werden kann
     * @throws GotoException Sprungbefehl zum nächsten Paragraph.
     */
    @Override
    public Object executeGeneric(VirtualFrame frame) throws GotoException {
        if (depending != null) {
            Integer depend = new Integer(depending.executeGeneric(frame).toString());
            if (depend < 1 || depend > paragraphName.length ){
                return null;
            } else{
                throw new GotoException(paragraphName[depend-1]);
            }
        }
        throw new GotoException(paragraphName[0]);
    }
}
