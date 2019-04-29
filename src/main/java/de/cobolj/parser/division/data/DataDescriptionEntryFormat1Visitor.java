package de.cobolj.parser.division.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;

import de.cobolj.nodes.LiteralNode;
import de.cobolj.nodes.PictureNode;
import de.cobolj.nodes.StringNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.Cobol85Parser.DataDescriptionEntryFormat1Context;
import de.cobolj.parser.Cobol85Parser.GenerateStatementContext;
import de.cobolj.runtime.Picture;
import de.cobolj.runtime.PictureGroup;

/**
 * 
 * dataDescriptionEntryFormat1: ( INTEGERLITERAL | LEVEL_NUMBER_77 ) ( FILLER |
 * dataName )? ( dataRedefinesClause | dataIntegerStringClause |
 * dataExternalClause | dataGlobalClause | dataTypeDefClause |
 * dataThreadLocalClause | dataPictureClause | dataCommonOwnLocalClause |
 * dataTypeClause | dataUsingClause | dataUsageClause | dataValueClause |
 * dataReceivedByClause | dataOccursClause | dataSignClause |
 * dataSynchronizedClause | dataJustifiedClause | dataBlankWhenZeroClause |
 * dataWithLowerBoundsClause | dataAlignedClause | dataRecordAreaClause )*
 * DOT_FS
 * 
 * @author flaechsig
 *
 */
public class DataDescriptionEntryFormat1Visitor extends Cobol85BaseVisitor<Picture> {
	private static LinkedList<Integer> levelStack = new LinkedList<>();
	private static LinkedList<PictureGroup> groupStack = new LinkedList<>();

	public static DataDescriptionEntryFormat1Visitor INSTANCE = new DataDescriptionEntryFormat1Visitor();

	private DataDescriptionEntryFormat1Visitor() {
	}

	private Integer getLevelNumber(Cobol85Parser.DataDescriptionEntryFormat1Context ctx) {
		if (ctx.LEVEL_NUMBER_77() != null) {
			return Integer.valueOf(ctx.LEVEL_NUMBER_77().getText());
		} else {
			return Integer.valueOf(ctx.INTEGERLITERAL().getText());
		}
	}

	/**
	 * Fügt der aktuellen Gruppe (falls eine exitiert) ein weiteres Picture-Element
	 * hinzu.
	 * 
	 * @param pic Das Element, das dieser Gruppe untergeordnet ist.
	 */
	private void addToGroup(String name, Picture pic) {
		if (groupStack.isEmpty()) {
			return;
		}
		groupStack.peek().add(name, pic);
	}

	/**
	 * Prüft den Level-Stack und korrigiert ihn. 
	 * 
	 * <ul>
	 * <item>  Level-Stack ist leer -> Nichts zu tun, da noch nie eine Gruppe angelegt wurde
	 * <item>  Element auf dem Level-Stack ist kleiner -> nicht zu tun, da es sich um ein Element der aktuellen Gruppe handelt
	 * <item>  Element auf dem Stack ist gleich dem aktuellen Element -> Der Stack muss abgaut werden bis die beiden Elemente 
	 *         selben Wert haben.
	 * <item>  Level-Stack ist größer als das aktuelle Element -> auch in diesem Fall muss der Stack abgebaut werden, da eine Gruppe
	 *         beendet wurde.
	 * </ul>
	 * @param ctx
	 */
	private void checkLevel(Cobol85Parser.DataDescriptionEntryFormat1Context ctx) {
		int elementLevel = getLevelNumber(ctx);
		
		if(!levelStack.isEmpty() && levelStack.peek() >= elementLevel) {
			while(!levelStack.isEmpty() && levelStack.peek() >= elementLevel) {
				levelStack.pop();
				groupStack.pop();
			}
		}
	}

	/**
	 * FIXME: Nach Umstellung auf Picture muss hier noch einmal überarbeitet werden.
	 */
	@Override
	public Picture visitDataDescriptionEntryFormat1(Cobol85Parser.DataDescriptionEntryFormat1Context ctx) {
		String name = null; // Name des Picture
		Picture picture = null;
		List<LiteralNode> values = new ArrayList<>();
		// FIXME: Vervollständigen
		
		checkLevel(ctx);
		
		if (ctx.dataName() != null) {
			name = ctx.dataName().accept(DataNameVisitor.INSTANCE).toString();
		}

		if (ctx.dataPictureClause().size() > 0) {
			DataPictureClauseVisitor visitor = new DataPictureClauseVisitor(name, groupStack.peek());
			picture = ctx.dataPictureClause().get(0).accept(visitor); // TODO: Bin irritiert, das hier eine List sein soll
			addToGroup(name, picture);
		} else {
			// Es muss sich um eine PictureGroup handeln, da kein Picture angegeben ist
			PictureGroup group = new PictureGroup(name, groupStack.peek());
			picture = group;
			levelStack.push(getLevelNumber(ctx));
			addToGroup(name, group);
			groupStack.push(group);
		}

		if (ctx.dataValueClause() != null) {
			values = ctx.dataValueClause().stream().map(node -> node.accept(DataValueClauseVisitor.INSTANCE))
					.collect(Collectors.toList());
			if (values.size() > 1) {

				throw new RuntimeException("Akuell kann exakt nur ein Value-Wert verarbeitet werden. " + ctx.getText());
			}
		}
		if (!values.isEmpty()) {
			// FIXME: Hier wird nur das erste Element verarbeitet. War
			picture.setValue(values.get(0));
		}
		return picture;
	}
}
