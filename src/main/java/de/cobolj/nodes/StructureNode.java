package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import de.cobolj.statement.gotostmt.GotoException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Oberklasse für alle strukturgebenden Konstrukte. Z.b. Section, Paragraph, Sentence
 * @author flaechsig
 *
 */
public abstract class StructureNode extends CobolNode {

    /**
     * @param frame Kontext der Ausführung
     * @return Ergebnis des als letztes ausgeführten Kommandos
     * @throws GotoException Die Goto-Exception wird geworfen, um die Progarmmablauf zu ändern
     */
    @Override
    public abstract Object executeGeneric(VirtualFrame frame) throws GotoException;

    /** @return liefert den Namen des Struktur-Elements oder "--undefined--", wenn keine Name
     *      existiert
     */
    public String getName() {return "--undefined--";}
}
