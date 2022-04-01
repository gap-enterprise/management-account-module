package io.surati.gap.maccount.module.web.rq;

import io.surati.gap.maccount.module.domain.api.Bundle;
import io.surati.gap.maccount.module.domain.api.DocumentToBundle;
import io.surati.gap.maccount.module.domain.api.DocumentsToBundle;
import io.surati.gap.maccount.module.domain.api.Section;
import io.surati.gap.maccount.module.domain.api.Title;
import io.surati.gap.maccount.module.domain.db.DbBundles;
import io.surati.gap.maccount.module.domain.db.DbPaginatedPartialDocumentsToBundle;
import io.surati.gap.maccount.module.domain.db.DbSections;
import io.surati.gap.maccount.module.domain.db.DbTitles;
import java.io.IOException;
import javax.sql.DataSource;
import org.takes.Request;
import org.takes.rq.RqHref.Smart;

public final class RqPartialDocumentsToBundle implements DocumentsToBundle {

	private final DocumentsToBundle origin;

	public RqPartialDocumentsToBundle(final DataSource source, final Request req) throws IOException {
		final Smart href = new Smart(req);
		final String filter = href.single("filter", "");					
		final Long page = Long.parseLong(href.single("page"));
		final Long nbperpage = Long.parseLong(href.single("nbperpage"));
		final Integer year = Integer.parseInt(href.single("year_of_payment"));
		final Long bundleid = Long.parseLong(href.single("bundle_id", "0"));
		final Long titleid = Long.parseLong(href.single("title_id", "0"));
		final Long sectionid = Long.parseLong(href.single("section_id", "0"));
		final Bundle bundle;
		if (bundleid.equals(0L)) {
			bundle = Bundle.EMPTY;
		} else {
			bundle = new DbBundles(source).get(bundleid);
		}
		final Title title;
		if (titleid.equals(0L)) {
			title = Title.EMPTY;
		} else {
			title = new DbTitles(source).get(titleid);
		}
		final Section section;
		if (sectionid.equals(0L)) {
			section = Section.EMPTY;
		} else {
			section = new DbSections(source).get(sectionid);
		}
		this.origin = new DbPaginatedPartialDocumentsToBundle(
		    source,
			nbperpage,
			page,
			title,
			section,
			bundle,
			year,
			filter
		);
	}

	@Override
	public Iterable<DocumentToBundle> iterate() {
		return this.origin.iterate();
	}

	@Override
	public DocumentToBundle get(Long id) {
		return this.origin.get(id);
	}

	@Override
	public boolean has(Long id) {
		return this.origin.has(id);
	}

	@Override
	public Double totalAmount() {
		return this.origin.totalAmount();
	}

	@Override
	public Long count() {
		return this.origin.count();
	}

}
