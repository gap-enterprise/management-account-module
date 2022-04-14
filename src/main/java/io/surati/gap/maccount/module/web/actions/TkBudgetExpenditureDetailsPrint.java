package io.surati.gap.maccount.module.web.actions;

import io.surati.gap.gtp.base.api.Treasury;
import io.surati.gap.gtp.base.db.DbPaginatedAnnualWarrants;
import io.surati.gap.gtp.base.db.DbTreasury;
import io.surati.gap.maccount.module.report.BirtBudgetExpenditureDetailsPrinter;
import java.io.ByteArrayOutputStream;
import javax.sql.DataSource;
import org.takes.rq.RqHref;
import org.takes.rs.RsWithBody;
import org.takes.tk.TkWrap;

public final class TkBudgetExpenditureDetailsPrint extends TkWrap {

	public TkBudgetExpenditureDetailsPrint(final DataSource src) {
		super(
			req -> {

				final ByteArrayOutputStream output = new ByteArrayOutputStream();
				final Treasury treasury = new DbTreasury(
					src,
					Long.parseLong(
						new RqHref.Smart(req).single("treasury")
					)
				);
				final short year = Short.parseShort(
					new RqHref.Smart(req).single("year")
				);
				new BirtBudgetExpenditureDetailsPrinter(
					new DbPaginatedAnnualWarrants(
						src, treasury, year
					).iterate(),
					treasury,
					year
				).print(output);
				return new RsWithBody(output.toByteArray());
			}
		);
	}
}
