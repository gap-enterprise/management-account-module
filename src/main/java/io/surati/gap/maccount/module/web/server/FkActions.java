package io.surati.gap.maccount.module.web.server;

import com.minlessika.db.Database;
import com.minlessika.db.TkTransaction;
import io.surati.gap.maccount.module.web.actions.TkBundleDelete;
import io.surati.gap.maccount.module.web.actions.TkBundleSave;
import io.surati.gap.maccount.module.web.actions.TkBundleThresholdSave;
import io.surati.gap.maccount.module.web.actions.TkRefDocumentDataSave;
import io.surati.gap.maccount.module.web.actions.TkSectionDelete;
import io.surati.gap.maccount.module.web.actions.TkSectionSave;
import io.surati.gap.maccount.module.web.actions.TkTitleDelete;
import io.surati.gap.maccount.module.web.actions.TkTitleSave;
import io.surati.gap.web.base.TkSecure;
import org.takes.facets.auth.Pass;
import org.takes.facets.fork.FkChain;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.FkWrap;

/**
 * Front for actions.
 *
 * @since 0.1
 */
public final class FkActions extends FkWrap {

	public FkActions(
		final Database source,
		final Pass pass
	) {
		super(
			new FkChain(
				new FkRegex(
					"/maccount/title/save",
					new TkSecure(
						new TkTransaction(
							new TkTitleSave(source),
							source
						),
						source
					)
				),
				new FkRegex(
					"/maccount/title/delete",
					new TkSecure(
						new TkTransaction(
							new TkTitleDelete(source),
							source
						),
						source
					)
				),
				new FkRegex(
					"/maccount/bundle/save",
					new TkSecure(
						new TkTransaction(
							new TkBundleSave(source),
							source
						),
						source
					)
				),
				new FkRegex(
					"/maccount/bundle/delete",
					new TkSecure(
						new TkTransaction(
							new TkBundleDelete(source),
							source
						),
						source
					)
				),
				new FkRegex(
					"/maccount/section/save",
					new TkSecure(
						new TkTransaction(
							new TkSectionSave(source),
							source
						),
						source
					)
				),
				new FkRegex(
					"/maccount/section/delete",
					new TkSecure(
						new TkTransaction(
							new TkSectionDelete(source),
							source
						),
						source
					)
				),
				new FkRegex(
					"/reference-document/ma-data/save",
					new TkSecure(
						new TkTransaction(
							new TkRefDocumentDataSave(source),
							source
						),
						source
					)
				),
				new FkRegex(
					"/maccount/bundle-threshold/save",
					new TkSecure(
						new TkTransaction(
							new TkBundleThresholdSave(source),
							source
						),
						source
					)
				)
			)
		);
	}
}
