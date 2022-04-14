package io.surati.gap.maccount.module.web.actions;

import io.surati.gap.gtp.base.db.DbTreasury;
import io.surati.gap.maccount.module.domain.db.DbManagementAccount;
import io.surati.gap.maccount.module.report.BirtManagementAccountPrinter;
import java.io.ByteArrayOutputStream;
import javax.sql.DataSource;
import org.takes.rq.RqHref;
import org.takes.rs.RsWithBody;
import org.takes.tk.TkWrap;

public final class TkManagementAccountPrint extends TkWrap {

	public TkManagementAccountPrint(final DataSource src) {
		super(
			req -> {

				final ByteArrayOutputStream output = new ByteArrayOutputStream();
				new BirtManagementAccountPrinter(
					new DbManagementAccount(
						src,
						new DbTreasury(
							src,
							Long.parseLong(
								new RqHref.Smart(req).single("treasury")
							)
						),
						Short.parseShort(
							new RqHref.Smart(req).single("year")
						)
					)
				).print(output);
				return new RsWithBody(output.toByteArray());
			}
		);
	}
}
