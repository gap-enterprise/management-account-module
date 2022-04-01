package io.surati.gap.maccount.module.web.actions;

import io.surati.gap.admin.base.api.Log;
import io.surati.gap.admin.base.api.User;
import io.surati.gap.maccount.module.domain.api.Bundle;
import io.surati.gap.maccount.module.domain.api.Section;
import io.surati.gap.maccount.module.domain.api.Title;
import io.surati.gap.maccount.module.domain.db.DbBundles;
import io.surati.gap.maccount.module.domain.db.DbRefDocumentData;
import io.surati.gap.maccount.module.domain.db.DbSections;
import io.surati.gap.maccount.module.domain.db.DbTitles;
import io.surati.gap.maccount.module.domain.secure.SecRefDocumentData;
import io.surati.gap.payment.base.api.ReferenceDocument;
import io.surati.gap.payment.base.db.DbPaginedReferenceDocuments;
import io.surati.gap.web.base.log.RqLog;
import io.surati.gap.web.base.rq.RootPageFull;
import io.surati.gap.web.base.rq.RqUser;
import java.util.logging.Level;
import javax.sql.DataSource;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

public final class TkRefDocumentDataSave implements Take {

	/**
	 * Database
	 */
	private final DataSource source;

	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkRefDocumentDataSave(final DataSource source) {
		this.source = source;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		final Log log = new RqLog(this.source, req);
		final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
		final ReferenceDocument document = new DbPaginedReferenceDocuments(this.source).get(
			Long.parseLong(form.single("document_id"))
		);
		final User user = new RqUser(this.source, req);
		final Bundle bundle = new DbBundles(this.source).get(
			Long.parseLong(form.single("bundle_id"))
		);
		final Title title = new DbTitles(this.source).get(
			Long.parseLong(form.single("title_id"))
		);
		final Section section = new DbSections(this.source).get(
			Long.parseLong(form.single("section_id"))
		);
		final String imputation = form.single("imputation");
		new SecRefDocumentData(
			new DbRefDocumentData(this.source, document),
			user
		).update(bundle, title, section, imputation);
		log.info(
			"Définition des données Compte de gestion du document de référence (N°=%s) du tiers (Code=%s)",
			document.reference(), document.issuer().code()
		);
		return new RsForward(
			new RsFlash(
				"Les données Compte de gestion ont été mises à jour avec succès !",
				Level.INFO
			),
			String.format("/reference-document/view?id=%s&%s", document.id(), new RootPageFull(req))
		);	
	}
}
