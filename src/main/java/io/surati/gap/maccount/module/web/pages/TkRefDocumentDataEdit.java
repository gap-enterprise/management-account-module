package io.surati.gap.maccount.module.web.pages;

import io.surati.gap.admin.base.api.User;
import io.surati.gap.maccount.module.domain.api.RefDocumentData;
import io.surati.gap.maccount.module.domain.db.DbBundles;
import io.surati.gap.maccount.module.domain.db.DbRefDocumentData;
import io.surati.gap.maccount.module.domain.db.DbSections;
import io.surati.gap.maccount.module.domain.db.DbTitles;
import io.surati.gap.maccount.module.web.xe.XeBundles;
import io.surati.gap.maccount.module.web.xe.XeRefDocumentData;
import io.surati.gap.maccount.module.web.xe.XeSections;
import io.surati.gap.maccount.module.web.xe.XeTitles;
import io.surati.gap.payment.base.api.ReferenceDocument;
import io.surati.gap.payment.base.db.DbPaginedReferenceDocuments;
import io.surati.gap.payment.base.module.xe.XeReferenceDocument;
import io.surati.gap.web.base.RsPage;
import io.surati.gap.web.base.rq.RqUser;
import io.surati.gap.web.base.xe.XeRootPage;
import javax.sql.DataSource;
import org.cactoos.collection.Sticky;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqHref;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;

public final class TkRefDocumentDataEdit implements Take {

	/**
	 * DataSource
	 */
	private final DataSource source;

	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkRefDocumentDataEdit(final DataSource source) {
		this.source = source;
	}

	@Override
	public Response act(Request req) throws Exception {
		final Long docid = Long.parseLong(new RqHref.Smart(req).single("document"));
		final ReferenceDocument document = new DbPaginedReferenceDocuments(this.source).get(docid);
		final User user = new RqUser(this.source, req);
		final XeSource xecommon = new XeChain(
			new XeBundles(new DbBundles(this.source)),
			new XeTitles(new DbTitles(this.source)),
			new XeSections(new DbSections(this.source)),
			new XeReferenceDocument("document", document)
		);
		final RefDocumentData data = new DbRefDocumentData(this.source, document);
		final XeSource src;
		if (data.isDefined()) {
			src = new XeChain(
				xecommon,
				new XeRefDocumentData(data)
			);
		} else {
			src = xecommon;
		}
		return new RsPage(
			"/io/surati/gap/maccount/module/xsl/reference_document/define_ma_data.xsl",
			req,
			this.source,
			() -> new Sticky<>(
				new XeAppend("menu", "reference-document-ma-data"),
				src,
				new XeRootPage(req)
			)
		);
	}
}
