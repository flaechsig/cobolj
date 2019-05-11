package de.cobolj.parser.division.data;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import de.cobolj.division.data.AssignClauseNode;
import de.cobolj.division.data.FileControlEntryNode;
import de.cobolj.division.environment.OrganizationClause;
import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.FileControlEntryFormat1Context;
import de.cobolj.parser.Cobol85Parser.FileControlEntryFormat4Context;
import de.cobolj.parser.CobolWordVisitor;
import de.cobolj.parser.IdentifierVisitor;
import de.cobolj.parser.division.environment.OrganizationClauseVisitor;

/**
 * Verarbeitet die Formate 1 bis 4 (entsprechend Handbuch)
 * 
 * @author flaechsig
 *
 */
public class FileControlEntryVisitor extends Cobol85BaseVisitor<FileControlEntryNode> {

	/**
	 * fileControlEntryFormat1 : SELECT OPTIONAL? fileName=cobolWord assignClause
	 * reserveClause? organizationClause? paddingCharacterClause?
	 * recordDelimiterClause? accessModeClause? fileStatusClause?
	 */
	@Override
	public FileControlEntryNode visitFileControlEntryFormat1(FileControlEntryFormat1Context ctx) {
		notImplemented(ctx.reserveClause());
		notImplemented(ctx.paddingCharacterClause());
		notImplemented(ctx.recordDelimiterClause());
		notImplemented(ctx.accessModeClause());
		notImplemented(ctx.fileStatusClause());

		String fileName = accept(ctx.fileName, CobolWordVisitor.INSTANCE);
		boolean optional = accept(ctx.OPTIONAL());
		AssignClauseNode assign = accept(ctx.assignClause(), new AssignClauseVisitor());
		OrganizationClause organizationClause = accept(ctx.organizationClause(), new OrganizationClauseVisitor());
		return new FileControlEntryNode(fileName, optional, assign, organizationClause);
	}

	/**
	 * 
	 * fileControlEntryFormat4 : SELECT fileName=identifier assignClause
	 */
	@Override
	public FileControlEntryNode visitFileControlEntryFormat4(FileControlEntryFormat4Context ctx) {
		String fileName = ctx.fileName.accept(CobolWordVisitor.INSTANCE);
		AssignClauseNode assign = ctx.assignClause().accept(new AssignClauseVisitor());

		return new FileControlEntryNode(fileName, false, assign, null);
	}
}
