package io.surati.gap.maccount.module.web.rq;

import io.surati.gap.gtp.base.api.Bundle;
import io.surati.gap.gtp.base.api.Section;
import io.surati.gap.gtp.base.api.Title;
import io.surati.gap.gtp.base.db.DbBundles;
import io.surati.gap.gtp.base.db.DbSections;
import io.surati.gap.gtp.base.db.DbTitles;
import io.surati.gap.gtp.base.db.DbTreasury;
import io.surati.gap.maccount.module.domain.api.SubBundle;
import io.surati.gap.maccount.module.domain.api.SubBundles;
import io.surati.gap.maccount.module.domain.db.DbPaginatedPartialSubBundles;
import java.io.IOException;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.takes.Request;
import org.takes.rq.RqHref.Smart;

public final class RqPartialSubBundles implements SubBundles {

	private final SubBundles origin;

	public RqPartialSubBundles(final DataSource source, final Request req) throws IOException {
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
		this.origin = new DbPaginatedPartialSubBundles(
		    source,
			new DbTreasury(
				source,
				Long.parseLong(href.single("treasury"))
			),
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
	public Iterable<SubBundle> iterate() {
		return this.origin.iterate();
	}

	@Override
	public SubBundle get(final Long id) {
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


}
