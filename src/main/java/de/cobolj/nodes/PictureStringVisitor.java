package de.cobolj.nodes;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.PictureCarinalityVisitor;
import de.cobolj.parser.PictureCharsVisitor;
import de.cobolj.runtime.Picture;
import de.cobolj.runtime.Picture9;
import de.cobolj.runtime.Picture9V;
import de.cobolj.runtime.PictureX;

/**
 * 
 * pictureString: (pictureChars+ pictureCardinality?)+
 * 
 * @author flaechsig
 *
 */
public class PictureStringVisitor extends Cobol85BaseVisitor<PictureNode> {

	@Override
	public PictureNode visitPictureString(Cobol85Parser.PictureStringContext ctx) {
		// Fixme: Vervollständigen
		// Typ ermitteln
		String pictureTyp = null;
		Picture picture = null;
		List<Integer> cardinality = null;
		PictureCharsVisitor visitor = new PictureCharsVisitor();
		pictureTyp = ctx.pictureChars().stream().map(chars -> chars.accept(visitor)).collect(Collectors.joining());

		if (ctx.pictureCardinality() != null) {
			PictureCarinalityVisitor cardinalityVisitor = new PictureCarinalityVisitor();
			cardinality = ctx.pictureCardinality().stream()
					.map(card -> card.accept(cardinalityVisitor)).collect(Collectors.toList());
		}
		if ("9".equals(pictureTyp)) {
			picture = new Picture9(cardinality.get(0));
		} else if ("S9".equals(pictureTyp)) {
			picture = new Picture9(cardinality.get(0), true);
		} else if ("9V9".equals(pictureTyp)) {
			picture = new Picture9V(cardinality.get(0), cardinality.get(1), false);
		} else if ("S9V9".equals(pictureTyp)) {
			picture = new Picture9V(cardinality.get(0), cardinality.get(1), true);
		} else if ("A".equals(pictureTyp)) {
			picture = new PictureX(cardinality.get(0));
		} else if ("X".equals(pictureTyp)) {
			picture = new PictureX(cardinality.get(0)); // TODO: Vielleicht A schärfer formulieren und PictureX einführen.
		}
		return new PictureNode(picture);
	}
}
