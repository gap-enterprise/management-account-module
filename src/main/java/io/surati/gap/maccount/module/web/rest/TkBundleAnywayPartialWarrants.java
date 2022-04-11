package io.surati.gap.maccount.module.web.rest;

import io.surati.gap.gtp.base.api.Bundle;
import io.surati.gap.gtp.base.api.Section;
import io.surati.gap.gtp.base.api.Title;
import io.surati.gap.gtp.base.db.DbBundles;
import io.surati.gap.gtp.base.db.DbSections;
import io.surati.gap.gtp.base.db.DbTitles;
import io.surati.gap.maccount.module.domain.db.DbPaginatedEntireWarrantsToBundle;
import io.surati.gap.maccount.module.domain.db.DbPaginatedPartialWarrantsToBundle;
import io.surati.gap.web.base.rq.RqUser;
import java.net.HttpURLConnection;
import javax.json.Json;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.RsJson;
import org.takes.rs.RsWithStatus;

public final class TkBundleAnywayPartialWarrants implements Take {

	/**
	 * Database
	 */
	private final DataSource source;

	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkBundleAnywayPartialWarrants(final DataSource source) {
		this.source = source;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		try {
			final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
			final String filter = form.single("filter", "");
			final Long page = Long.parseLong(form.single("page"));
			final Long nbperpage = Long.parseLong(form.single("nbperpage"));
			final short year = Short.parseShort(form.single("year"));
			final String bdlecode = form.single("bundle", "");
			final String ttlecode = form.single("title", "");
			final String sctcode = form.single("section", "");
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
			new DbPaginatedPartialWarrantsToBundle(
				this.source,
				nbperpage,
				page,
				title,
				section,
				bundle,
				year,
				filter
			).bundleAnyway(new RqUser(this.source, req));
			return new RsJson(
				Json.createObjectBuilder().build()
			);
		} catch(final IllegalArgumentException exe) {
			return new RsWithStatus(
				new RsJson(
					Json.createObjectBuilder()
						.add("message", exe.getLocalizedMessage())
						.build()
					),
				HttpURLConnection.HTTP_BAD_REQUEST
			);
		} catch(final Exception exe) {
			return new RsWithStatus(
				new RsJson(
					Json.createObjectBuilder()
					.add("message", exe.getLocalizedMessage())
					.build()
				),
				HttpURLConnection.HTTP_INTERNAL_ERROR
			);
		}
	}

}
