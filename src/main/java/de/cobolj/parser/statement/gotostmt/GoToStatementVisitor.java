package de.cobolj.parser.statement.gotostmt;

import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.IdentifierVisitor;
import de.cobolj.parser.division.procedure.ParagraphNameVisitor;
import de.cobolj.statement.gotostmt.GotoStatementNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * goToStatement : GO TO? (goToStatementSimple | goToDependingOnStatement);
 * goToStatementSimple: procedureName;
 * goToDependingOnStatement: MORE_LABELS | procedureName+ (DEPENDING ON? identifier)?;
 */
public class GoToStatementVisitor extends Cobol85BaseVisitor<GotoStatementNode> {

    @Override
    public GotoStatementNode visitGoToStatement(Cobol85Parser.GoToStatementContext ctx) {
        if(ctx.goToStatementSimple() != null)  {
            return visitGoToStatementSimple((Cobol85Parser.GoToStatementSimpleContext) ctx.goToStatementSimple().getRuleContext());
        } else {
            return visitGoToDependingOnStatement((Cobol85Parser.GoToDependingOnStatementContext) ctx.goToDependingOnStatement().getRuleContext());
        }
    }

    @Override
    public GotoStatementNode visitGoToStatementSimple(Cobol85Parser.GoToStatementSimpleContext ctx) {
        List<String> paragraphs = new ArrayList<>(1);
        paragraphs.add(ctx.procedureName().accept(new ParagraphNameVisitor()));
        return new GotoStatementNode(paragraphs, null);
    }

    @Override
    public GotoStatementNode visitGoToDependingOnStatement(Cobol85Parser.GoToDependingOnStatementContext ctx) {
        List<String> paragraphs = ctx.procedureName().stream()
                .map( procedure -> procedure.accept(new ParagraphNameVisitor()))
                .collect(Collectors.toList());
        PictureNode depending =  ctx.identifier().accept(IdentifierVisitor.INSTANCE);
        return new GotoStatementNode(paragraphs, depending);
    }
}
