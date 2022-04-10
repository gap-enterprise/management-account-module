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
package io.surati.gap.maccount.module.web.rest;

import io.surati.gap.maccount.module.domain.api.WarrantsToBundle;
import io.surati.gap.maccount.module.web.rq.RqEntireWarrantsToBundle;
import io.surati.gap.maccount.module.web.xe.XeAnnualWarrantJson;
import javax.sql.DataSource;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsJson;

public final class TkEntireWarrantToBundleSearch implements Take {

	/**
	 * Database
	 */
	private final DataSource source;

	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkEntireWarrantToBundleSearch(final DataSource source) {
		this.source = source;
	}
	
	@Override
	public Response act(Request req) throws Exception {	
		final WarrantsToBundle items = new RqEntireWarrantsToBundle(this.source, req);
		return new RsJson(
			new XeAnnualWarrantJson(
				items.iterate(),
				items.count()
			)
		);
	}
}
