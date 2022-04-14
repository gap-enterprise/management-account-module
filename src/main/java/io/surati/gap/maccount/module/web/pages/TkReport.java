/**
MIT License

Copyright (c) 2021 Surati

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package io.surati.gap.maccount.module.web.pages;

import io.surati.gap.gtp.base.db.DbBudgetYears;
import io.surati.gap.gtp.base.db.DbBundles;
import io.surati.gap.gtp.base.db.DbSections;
import io.surati.gap.gtp.base.db.DbTitles;
import io.surati.gap.gtp.base.db.DbTreasuries;
import io.surati.gap.gtp.base.module.xe.XeBundles;
import io.surati.gap.gtp.base.module.xe.XeSections;
import io.surati.gap.gtp.base.module.xe.XeTitles;
import io.surati.gap.maccount.module.web.xe.XeBudgetYears;
import io.surati.gap.maccount.module.web.xe.XeTreasuries;
import io.surati.gap.web.base.RsPage;
import io.surati.gap.web.base.xe.XeRootPage;
import javax.sql.DataSource;
import org.cactoos.collection.Sticky;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;

/**
 * Take that lists entire documents to bundle.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @since 3.0
 */
public final class TkReport implements Take {

	private final DataSource source;

	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkReport(final DataSource source) {
		this.source = source;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		final XeSource src = new XeChain(
			new XeSections(new DbSections(this.source)),
			new XeTitles(new DbTitles(this.source)),
			new XeBundles(new DbBundles(this.source)),
			new XeBudgetYears(new DbBudgetYears(this.source)),
			new XeTreasuries(new DbTreasuries(this.source))
		);
		return new RsPage(
            "/io/surati/gap/maccount/module/xsl/report/list.xsl",
			req,
			this.source,
			() -> new Sticky<>(
				new XeAppend("menu", "entire-sub-bundle"),
				new XeRootPage(
					"Rapports",
					"Tirage du compte de gestion",
					req
				),
				src
			)
		);
	}

}
