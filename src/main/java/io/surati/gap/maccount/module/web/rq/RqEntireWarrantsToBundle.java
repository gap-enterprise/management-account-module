package io.surati.gap.maccount.module.web.rq;

import io.surati.gap.admin.base.api.User;
import io.surati.gap.gtp.base.api.AnnualWarrant;
import io.surati.gap.gtp.base.api.Bundle;
import io.surati.gap.gtp.base.api.Section;
import io.surati.gap.gtp.base.api.Title;
import io.surati.gap.gtp.base.db.DbBundles;
import io.surati.gap.gtp.base.db.DbSections;
import io.surati.gap.gtp.base.db.DbTitles;
import io.surati.gap.maccount.module.domain.api.WarrantsToBundle;
import io.surati.gap.maccount.module.domain.db.DbPaginatedEntireWarrantsToBundle;
import java.io.IOException;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.takes.Request;
import org.takes.rq.RqHref.Smart;

public final class RqEntireWarrantsToBundle implements WarrantsToBundle {

	private final WarrantsToBundle origin;

	public RqEntireWarrantsToBundle(final DataSource source, final Request req) throws IOException {
		final Smart href = new Smart(req);
		final String filter = href.single("filter", "");					
		final Long page = Long.parseLong(href.single("page"));
		final Long nbperpage = Long.parseLong(href.single("nbperpage"));
		final short year = Short.parseShort(href.single("year"));
		final String bdlecode = href.single("bundle", "");
		final String ttlecode = href.single("title", "");
		final String sctcode = href.single("section", "");
		final Bundle bundle;
		if (StringUtils.isBlank(bdlecode)) {
			bundle = Bundle.EMPTY;
		} else {
			bundle = new DbBundles(source).get(bdlecode);
		}
		final Title title;
		if (StringUtils.isBlank(ttlecode)) {
			title = Title.EMPTY;
		} else {
			title = new DbTitles(source).get(ttlecode);
		}
		final Section section;
		if (StringUtils.isBlank(sctcode)) {
			section = Section.EMPTY;
		} else {
			section = new DbSections(source).get(sctcode);
		}
		this.origin = new DbPaginatedEntireWarrantsToBundle(
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
	public Iterable<AnnualWarrant> iterate() {
		return this.origin.iterate();
	}

	@Override
	public AnnualWarrant get(final Long id) {
		return this.origin.get(id);
	}

	@Override
	public boolean has(final Long id) {
		return this.origin.has(id);
	}

	@Override
	public Long count() {
		return this.origin.count();
	}

	@Override
	public void bundle(final User author) {
		this.origin.bundle(author);
	}

	@Override
	public void bundleAnyway(final User author) {
		this.origin.bundleAnyway(author);
	}


}
