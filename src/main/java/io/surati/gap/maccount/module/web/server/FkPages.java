package io.surati.gap.maccount.module.web.server;

import io.surati.gap.maccount.module.web.pages.TkBundleThresholdEdit;
import io.surati.gap.maccount.module.web.pages.TkBundleThresholdView;
import io.surati.gap.maccount.module.web.pages.TkEntireWarrantToBundleList;
import io.surati.gap.maccount.module.web.pages.TkPartialWarrantToBundleList;
import io.surati.gap.web.base.TkSecure;
import javax.sql.DataSource;
import org.takes.facets.fork.FkChain;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.FkWrap;

/**
 * Front for pages.
 *
 * @since 0.1
 */
public final class FkPages extends FkWrap {

	public FkPages(final DataSource src) {
		super(
			new FkChain(
				new FkRegex(
					"/maccount/bundle-threshold/view",
					new TkSecure(
						new TkBundleThresholdView(src),
						src
					)
				),
				new FkRegex(
					"/maccount/bundle-threshold/edit",
					new TkSecure(
						new TkBundleThresholdEdit(src),
						src
					)
				),
				new FkRegex(
					"/maccount/warrant-to-bundle/entire/list",
					new TkSecure(
						new TkEntireWarrantToBundleList(src),
						src
					)
				),
				new FkRegex(
					"/maccount/warrant-to-bundle/partial/list",
					new TkSecure(
						new TkPartialWarrantToBundleList(src),
						src
					)
				)
			)
		);
	}
}
