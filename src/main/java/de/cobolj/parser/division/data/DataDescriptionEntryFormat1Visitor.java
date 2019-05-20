package de.cobolj.parser.division.data;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import de.cobolj.division.data.DataDescriptionEntryFormat1Node;
import de.cobolj.division.data.DataOccursClause;
import de.cobolj.nodes.LiteralNode;
import de.cobolj.nodes.PictureNode;
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

	public DataDescriptionEntryFormat1Visitor() {

	}

	private Integer getLevelNumber(Cobol85Parser.DataDescriptionEntryFormat1Context ctx) {
		if (ctx.LEVEL_NUMBER_77() != null) {
			return Integer.valueOf(ctx.LEVEL_NUMBER_77().getText());
		} else {
			return Integer.valueOf(ctx.INTEGERLITERAL().getText());
		}
	}

	/**
	 * dataDescriptionEntryFormat1 : ( INTEGERLITERAL | LEVEL_NUMBER_77 ) ( FILLER |
	 * dataName )? dataRedefinesClause? dataExternalClause? dataBlankWhenZeroClause?
	 * dataFormatClause? dataGlobalClause? dataJustifiedClause? dataOccursClause?
	 * dataPictureClause? dataOccursClause? dataSignClause? dataValueClause?
	 * dataSynchronizedClause? dataTypeClause? dataUsageClause? DOT_FS ;
	 */
	@Override
	public DataDescriptionEntryFormat1Node visitDataDescriptionEntryFormat1(
			Cobol85Parser.DataDescriptionEntryFormat1Context ctx) {
		assert ctx.dataOccursClause()
				.size() <= 1 : "DataOcurrsClaus ist in der Grammatik zweimal vorhanden darf aber nur einmal verwendet werden";

		notImplemented(ctx.LEVEL_NUMBER_77());
		notImplemented(ctx.dataRedefinesClause());
		notImplemented(ctx.dataExternalClause());
		notImplemented(ctx.dataGlobalClause());
		notImplemented(ctx.dataTypeClause());
		// FIXME: notImplemented(ctx.dataUsageClause()); wird im Moment ignoriert
		notImplemented(ctx.dataSignClause());
		notImplemented(ctx.dataSynchronizedClause());
		notImplemented(ctx.dataJustifiedClause());
		notImplemented(ctx.dataBlankWhenZeroClause());

		final int level = getLevelNumber(ctx);
		final String name;
		if (accept(ctx.FILLER())) {
			name = Picture.FILLER;
		} else {
			name = accept(ctx.dataName(), new DataNameVisitor(null)).getSlot();
		}
		PictureNode dataRedefinesClause = accept(ctx.dataRedefinesClause(), new DataRedefinesClauseVistor());
		DataOccursClause dataOccursClause = accept(ctx.dataOccursClause(0), new DataOccursClauseVisitor());
		Picture picture;
		String picutreString = accept(ctx.dataPictureClause(), new DataPictureClauseVisitor2());
		if (ctx.dataPictureClause() == null) {
			picture = new PictureGroup(level, name);
		} else {
			picture = accept(ctx.dataPictureClause(), new DataPictureClauseVisitor(level, name));
		}
		LiteralNode value = accept(ctx.dataValueClause(), DataValueClauseVisitor.INSTANCE);
		return new DataDescriptionEntryFormat1Node(picture, picutreString, dataRedefinesClause, dataOccursClause, value);
	}
}
