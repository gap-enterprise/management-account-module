package io.surati.gap.maccount.module.web.server;

import io.surati.gap.maccount.module.web.rest.TkEntireWarrantToBundleSearch;
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
					"/maccount/warrant-to-bundle/entire/search",
					new TkSecure(
						new TkEntireWarrantToBundleSearch(src),
						src
					)
				),
				new FkRegex(
					"/maccount/warrant-to-bundle/partial/search",
					new TkSecure(
						new TkPartialWarrantToBundleSearch(src),
						src
					)
				)
			)
		);
	}
}
