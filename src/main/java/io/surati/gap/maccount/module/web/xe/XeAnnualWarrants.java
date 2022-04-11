package io.surati.gap.maccount.module.web.xe;

import io.surati.gap.maccount.module.domain.api.AnnualWarrant;
import org.cactoos.collection.Mapped;
import org.cactoos.iterable.Joined;
import org.takes.rs.xe.XeDirectives;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directives;

public final class XeAnnualWarrants extends XeWrap {

	public XeAnnualWarrants(final Iterable<AnnualWarrant> items) {
		super(
			new XeDirectives(
				new Directives()
					.add("annual_warrants")
					.append(
						new Joined<>(
							new Mapped<>(
								item -> new XeAnnualWarrant("annual_warrant", item).toXembly(),
								items
							)
						)
					).up()
			)
		);
	}
}
