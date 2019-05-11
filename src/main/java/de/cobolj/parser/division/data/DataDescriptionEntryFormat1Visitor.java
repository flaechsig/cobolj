package de.cobolj.parser.division.data;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import java.util.LinkedList;

import de.cobolj.division.data.DataDescriptionEntryFormat1Node;
import de.cobolj.division.data.DataDescriptionEntryNode;
import de.cobolj.division.data.DataOccursClause;
import de.cobolj.nodes.LiteralNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.runtime.Picture;
import de.cobolj.runtime.PictureGroup;

/**
 * 
 * dataDescriptionEntryFormat1 : ( INTEGERLITERAL | LEVEL_NUMBER_77 ) ( FILLER |
 * dataName )? dataRedefinesClause? dataExternalClause? dataBlankWhenZeroClause?
 * dataFormatClause? dataGlobalClause? dataJustifiedClause? dataOccursClause?
 * dataPictureClause? dataSignClause? dataValueClause? dataSynchronizedClause?
 * dataTypeClause? dataUsageClause? DOT_FS ;
 * 
 * @author flaechsig
 *
 */
public class DataDescriptionEntryFormat1Visitor extends Cobol85BaseVisitor<DataDescriptionEntryFormat1Node> {
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
	 * <item> Level-Stack ist leer -> Nichts zu tun, da noch nie eine Gruppe
	 * angelegt wurde <item> Element auf dem Level-Stack ist kleiner -> nicht zu
	 * tun, da es sich um ein Element der aktuellen Gruppe handelt <item> Element
	 * auf dem Stack ist gleich dem aktuellen Element -> Der Stack muss abgaut
	 * werden bis die beiden Elemente selben Wert haben. <item> Level-Stack ist
	 * größer als das aktuelle Element -> auch in diesem Fall muss der Stack
	 * abgebaut werden, da eine Gruppe beendet wurde.
	 * </ul>
	 * 
	 * @param ctx
	 */
	private int checkLevel(Cobol85Parser.DataDescriptionEntryFormat1Context ctx) {
		int elementLevel = getLevelNumber(ctx);

		if (!levelStack.isEmpty() && levelStack.peek() >= elementLevel) {
			while (!levelStack.isEmpty() && levelStack.peek() >= elementLevel) {
				levelStack.pop();
				groupStack.pop();
			}
		}
		return elementLevel;
	}

	/**
	 * dataDescriptionEntryFormat1 : ( INTEGERLITERAL | LEVEL_NUMBER_77 ) ( FILLER |
	 * dataName )? dataRedefinesClause? dataExternalClause? dataBlankWhenZeroClause?
	 * dataFormatClause? dataGlobalClause? dataJustifiedClause? dataOccursClause?
	 * dataPictureClause? dataOccursClause? dataSignClause? dataValueClause? dataSynchronizedClause?
	 * dataTypeClause? dataUsageClause? DOT_FS ;
	 */
	@Override
	public DataDescriptionEntryFormat1Node visitDataDescriptionEntryFormat1(Cobol85Parser.DataDescriptionEntryFormat1Context ctx) {
		notImplemented(ctx.LEVEL_NUMBER_77());
		notImplemented(ctx.dataRedefinesClause());
		notImplemented(ctx.dataExternalClause());
		notImplemented(ctx.dataGlobalClause());
		notImplemented(ctx.dataTypeClause());
		notImplemented(ctx.dataUsageClause());
		notImplemented(ctx.dataSignClause());
		notImplemented(ctx.dataSynchronizedClause());
		notImplemented(ctx.dataJustifiedClause());
		notImplemented(ctx.dataBlankWhenZeroClause());

		int level = checkLevel(ctx);

		// Es ist entweder FILLER oder der Name gesetzt
		String name;
		if(accept(ctx.FILLER())) {
			name = Picture.FILLER;
		} else {
			name = accept(ctx.dataName(), DataNameVisitor.INSTANCE).getSlot();
		}
		DataOccursClause dataOccursClause = accept(ctx.dataOccursClause(0), new DataOccursClauseVisitor());
		Picture picture = accept(ctx.dataPictureClause(), new DataPictureClauseVisitor(name, groupStack.peek(), dataOccursClause));
		LiteralNode value = accept(ctx.dataValueClause(), DataValueClauseVisitor.INSTANCE);

		if (picture != null) {
			addToGroup(name, picture);
			if (value != null) {
				picture.setValue(value);
			}
		} else {
			// Es muss sich um eine PictureGroup handeln, da kein Picture angegeben ist
			PictureGroup group = new PictureGroup(name, groupStack.peek());
			picture = group;
			levelStack.push(getLevelNumber(ctx));
			addToGroup(name, group);
			groupStack.push(group);
		}

		return new DataDescriptionEntryFormat1Node(level, name, picture, dataOccursClause);
	}
}
