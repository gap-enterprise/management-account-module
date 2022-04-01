package io.surati.gap.maccount.module.web.server;

import com.minlessika.db.Database;
import io.surati.gap.maccount.module.web.pages.TkBundleEdit;
import io.surati.gap.maccount.module.web.pages.TkBundleList;
import io.surati.gap.maccount.module.web.pages.TkBundleThresholdEdit;
import io.surati.gap.maccount.module.web.pages.TkBundleThresholdView;
import io.surati.gap.maccount.module.web.pages.TkBundleView;
import io.surati.gap.maccount.module.web.pages.TkEntireDocumentToBundleList;
import io.surati.gap.maccount.module.web.pages.TkPartialDocumentToBundleList;
import io.surati.gap.maccount.module.web.pages.TkRefDocumentDataEdit;
import io.surati.gap.maccount.module.web.pages.TkSectionEdit;
import io.surati.gap.maccount.module.web.pages.TkSectionList;
import io.surati.gap.maccount.module.web.pages.TkSectionView;
import io.surati.gap.maccount.module.web.pages.TkTitleEdit;
import io.surati.gap.maccount.module.web.pages.TkTitleList;
import io.surati.gap.maccount.module.web.pages.TkTitleView;
import io.surati.gap.web.base.TkSecure;
import org.takes.facets.fork.FkChain;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.FkWrap;

/**
 * Front for pages.
 *
 * @since 0.1
 */
public final class FkPages extends FkWrap {

	public FkPages(
		final Database source
	) {
		super(
			new FkChain(
				new FkRegex(
					"/maccount/title/edit",
					new TkSecure(
						new TkTitleEdit(source),
						source
					)
				),
				new FkRegex(
					"/maccount/title",
					new TkSecure(
						new TkTitleList(source),
						source
					)
				),
				new FkRegex(
					"/maccount/title/view",
					new TkSecure(
						new TkTitleView(source),
						source
					)
				),
				new FkRegex(
					"/maccount/bundle/edit",
					new TkSecure(
						new TkBundleEdit(source),
						source
					)
				),
				new FkRegex(
					"/maccount/bundle",
					new TkSecure(
						new TkBundleList(source),
						source
					)
				),
				new FkRegex(
					"/maccount/bundle/view",
					new TkSecure(
						new TkBundleView(source),
						source
					)
				),
				new FkRegex(
					"/maccount/section/edit",
					new TkSecure(
						new TkSectionEdit(source),
						source
					)
				),
				new FkRegex(
					"/maccount/section",
					new TkSecure(
						new TkSectionList(source),
						source
					)
				),
				new FkRegex(
					"/maccount/section/view",
					new TkSecure(
						new TkSectionView(source),
						source
					)
				),
				new FkRegex(
					"/reference-document/ma-data/edit",
					new TkSecure(
						new TkRefDocumentDataEdit(source),
						source
					)
				),
				new FkRegex(
					"/maccount/bundle-threshold/view",
					new TkSecure(
						new TkBundleThresholdView(source),
						source
					)
				),
				new FkRegex(
					"/maccount/bundle-threshold/edit",
					new TkSecure(
						new TkBundleThresholdEdit(source),
						source
					)
				),
				new FkRegex(
					"/maccount/document-to-bundle/entire/list",
					new TkSecure(
						new TkEntireDocumentToBundleList(source),
						source
					)
				),
				new FkRegex(
					"/maccount/document-to-bundle/partial/list",
					new TkSecure(
						new TkPartialDocumentToBundleList(source),
						source
					)
				)
			)
		);
	}
}
