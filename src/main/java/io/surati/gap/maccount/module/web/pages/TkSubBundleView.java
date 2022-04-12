package io.surati.gap.maccount.module.web.pages;

import io.surati.gap.gtp.base.module.xe.XeAnnualWarrants;
import io.surati.gap.maccount.module.domain.api.SubBundle;
import io.surati.gap.maccount.module.domain.db.DbSubBundle;
import io.surati.gap.maccount.module.web.xe.XeSubBundle;
import io.surati.gap.web.base.RsPage;
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

public final class TkSubBundleView implements Take {

	/**
	 * DataSource
	 */
	private final DataSource source;

	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkSubBundleView(final DataSource source) {
		this.source = source;
	}

	@Override
	public Response act(Request req) throws Exception {
		final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));
		final SubBundle item = new DbSubBundle(this.source, id);
		final XeSource src = new XeChain(
			new XeSubBundle(item),
			new XeAnnualWarrants(item.warrants())
		);
		return new RsPage(
			"/io/surati/gap/maccount/module/xsl/sub_bundle/view.xsl",
			req,
			this.source,
			() -> new Sticky<>(
				new XeAppend("menu", "sub-bundle"),
				src,
				new XeRootPage(req)
			)
		);
	}

}
