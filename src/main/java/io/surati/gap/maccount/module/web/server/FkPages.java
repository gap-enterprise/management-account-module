package io.surati.gap.maccount.module.web.server;

import io.surati.gap.maccount.module.web.pages.TkBundleThresholdEdit;
import io.surati.gap.maccount.module.web.pages.TkBundleThresholdView;
import io.surati.gap.maccount.module.web.pages.TkEntireSubBundleList;
import io.surati.gap.maccount.module.web.pages.TkEntireWarrantToBundleList;
import io.surati.gap.maccount.module.web.pages.TkPartialSubBundleList;
import io.surati.gap.maccount.module.web.pages.TkPartialWarrantToBundleList;
import io.surati.gap.maccount.module.web.pages.TkManagementAccountPreview;
import io.surati.gap.maccount.module.web.pages.TkReport;
import io.surati.gap.maccount.module.web.pages.TkSubBundleView;
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
				),
				new FkRegex(
					"/maccount/sub-bundle/entire/list",
					new TkSecure(
						new TkEntireSubBundleList(src),
						src
					)
				),
				new FkRegex(
					"/maccount/sub-bundle/partial/list",
					new TkSecure(
						new TkPartialSubBundleList(src),
						src
					)
				),
				new FkRegex(
					"/maccount/sub-bundle/view",
					new TkSecure(
						new TkSubBundleView(src),
						src
					)
				),
				new FkRegex(
					"/maccount/management-account/preview",
					new TkSecure(
						new TkManagementAccountPreview(src),
						src
					)
				),
				new FkRegex(
					"/maccount/report/list",
					new TkSecure(
						new TkReport(src),
						src
					)
				)
			)
		);
	}
}
