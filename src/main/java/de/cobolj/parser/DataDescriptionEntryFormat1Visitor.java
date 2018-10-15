package de.cobolj.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;

import de.cobolj.nodes.LiteralNode;
import de.cobolj.nodes.PictureNode;
import de.cobolj.nodes.StringNode;
import de.cobolj.statements.WriteElementaryItemNode;
import de.cobolj.statements.WriteElementaryItemNodeGen;

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
public class DataDescriptionEntryFormat1Visitor extends Cobol85BaseVisitor<WriteElementaryItemNode> {

	public static DataDescriptionEntryFormat1Visitor INSTANCE = new DataDescriptionEntryFormat1Visitor();

	private DataDescriptionEntryFormat1Visitor() {
	}

	@Override
	public WriteElementaryItemNode visitDataDescriptionEntryFormat1(
			Cobol85Parser.DataDescriptionEntryFormat1Context ctx) {
		StringNode name = null;
		PictureNode picture = null;
		List<LiteralNode> values = new ArrayList<>();
		// FIXME
		if (ctx.dataName() != null) {
			name = ctx.dataName().accept(DataNameVisitor.INSTANCE);
		}

		if (ctx.dataPictureClause() != null) {
			DataPictureClauseVisitor visitor = new DataPictureClauseVisitor();
			picture = ctx.dataPictureClause().get(0).accept(visitor); // TODO: Bin irritiert, das hier eine List sein
																		// soll
		}

		if (ctx.dataValueClause() != null) {
			values = ctx.dataValueClause().stream().map(node -> node.accept(DataValueClauseVisitor.INSTANCE))
					.collect(Collectors.toList());
			if (values.size() > 1) {
				throw new RuntimeException("Akuell kann exakt nur ein Value-Wert verarbeitet werden");
			}
		}
		if (!values.isEmpty()) {
			picture.getPicture().setValue(values.get(0));
		}
		FrameSlot slot = StartRuleVisitor.descriptor.findOrAddFrameSlot(name.toString(), FrameSlotKind.Object);
		return WriteElementaryItemNodeGen.create(picture, slot);
	}
}
