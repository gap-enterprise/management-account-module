package io.surati.gap.maccount.module.web.server;

import io.surati.gap.maccount.module.web.rest.TkBundleAnywayEntireWarrants;
import io.surati.gap.maccount.module.web.rest.TkBundleAnywayPartialWarrants;
import io.surati.gap.maccount.module.web.rest.TkBundleEntireWarrants;
import io.surati.gap.maccount.module.web.rest.TkBundlePartialWarrants;
import io.surati.gap.maccount.module.web.rest.TkEntireSubBundleSearch;
import io.surati.gap.maccount.module.web.rest.TkEntireWarrantToBundleSearch;
import io.surati.gap.maccount.module.web.rest.TkPartialSubBundleSearch;
import io.surati.gap.maccount.module.web.rest.TkPartialWarrantToBundleSearch;
import io.surati.gap.web.base.TkSecure;
import javax.sql.DataSource;
import org.takes.facets.fork.FkChain;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.FkWrap;

/**
 * Front for API.
 *
 * @since 0.1
 */
public final class FkApi extends FkWrap {

	public FkApi(final DataSource src) {
		super(
			new FkChain(
				new FkRegex(
					"/api/maccount/warrant-to-bundle/entire/search",
					new TkSecure(
						new TkEntireWarrantToBundleSearch(src),
						src
					)
				),
				new FkRegex(
					"/api/maccount/warrant-to-bundle/partial/search",
					new TkSecure(
						new TkPartialWarrantToBundleSearch(src),
						src
					)
				),
				new FkRegex(
					"/api/maccount/warrant-to-bundle/entire/bundle",
					new TkSecure(
						new TkBundleEntireWarrants(src),
						src
					)
				),
				new FkRegex(
					"/api/maccount/warrant-to-bundle/entire/bundle-anyway",
					new TkSecure(
						new TkBundleAnywayEntireWarrants(src),
						src
					)
				),
				new FkRegex(
					"/api/maccount/warrant-to-bundle/partial/bundle",
					new TkSecure(
						new TkBundlePartialWarrants(src),
						src
					)
				),
				new FkRegex(
					"/api/maccount/warrant-to-bundle/partial/bundle-anyway",
					new TkSecure(
						new TkBundleAnywayPartialWarrants(src),
						src
					)
				),
				new FkRegex(
					"/api/maccount/sub-bundle/entire/search",
					new TkSecure(
						new TkEntireSubBundleSearch(src),
						src
					)
				),
				new FkRegex(
					"/api/maccount/sub-bundle/partial/search",
					new TkSecure(
						new TkPartialSubBundleSearch(src),
						src
					)
				)
			)
		);
	}
}
