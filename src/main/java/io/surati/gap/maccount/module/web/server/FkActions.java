package io.surati.gap.maccount.module.web.server;

import io.surati.gap.maccount.module.web.actions.TkBudgetExpenditureDetailsPrint;
import io.surati.gap.maccount.module.web.actions.TkBundleThresholdSave;
import io.surati.gap.maccount.module.web.actions.TkManagementAccountPrint;
import io.surati.gap.web.base.TkSecure;
import javax.sql.DataSource;
import org.takes.facets.fork.FkChain;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.FkWrap;

/**
 * Front for actions.
 *
 * @since 0.1
 */
public final class FkActions extends FkWrap {

	public FkActions(final DataSource src) {
		super(
			new FkChain(
				new FkRegex(
					"/maccount/bundle-threshold/save",
					new TkSecure(
						new TkBundleThresholdSave(src),
						src
					)
				),
				new FkRegex(
					"/maccount/report/management-account/print",
					new TkSecure(
						new TkManagementAccountPrint(src),
						src
					)
				),
				new FkRegex(
					"/maccount/report/budget-expenditure-details/print",
					new TkSecure(
						new TkBudgetExpenditureDetailsPrint(src),
						src
					)
				)
			)
		);
	}
}
