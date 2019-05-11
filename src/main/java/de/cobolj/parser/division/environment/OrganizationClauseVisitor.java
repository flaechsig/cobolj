package de.cobolj.parser.division.environment;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import org.antlr.runtime.RecognitionException;

import de.cobolj.division.environment.OrganizationClause;
import de.cobolj.division.environment.OrganizationClause.FileForm;
import de.cobolj.division.environment.OrganizationClause.RecordForm;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.OrganizationClauseContext;

/**
 * 
 * organizationClause : ( ORGANIZATION IS? )? ( LINE | RECORD BINARY | RECORD |
 * BINARY )? ( SEQUENTIAL | RELATIVE | INDEXED ) ;
 * 
 * @author flaechsig
 *
 */
public class OrganizationClauseVisitor extends Cobol85BaseVisitor<OrganizationClause> {
	@Override
	public OrganizationClause visitOrganizationClause(OrganizationClauseContext ctx) {
		notImplemented(ctx.LINE());
		notImplemented(ctx.BINARY());
		notImplemented(ctx.RELATIVE());
		notImplemented(ctx.INDEXED());
		
		RecordForm recordForm = null;
		if(accept(ctx.RECORD()) && accept(ctx.BINARY())) {
			recordForm = RecordForm.RECORD_BINARY;
		} else if(accept(ctx.BINARY())) {
			recordForm = RecordForm.BINARY;
		} else if(accept(ctx.LINE())) {
			recordForm = RecordForm.LINE;
		} else {
			recordForm = RecordForm.RECORD;
		}
		
		FileForm fileForm = null;
		if (accept(ctx.RELATIVE()) ) {
			fileForm = FileForm.RELATIV;
		} else if(accept(ctx.INDEXED())) {
			fileForm = FileForm.INDEXED;
		} else {
			fileForm = FileForm.SEQUENTIAL;
		}
		return new OrganizationClause(recordForm, fileForm);
	}

}
