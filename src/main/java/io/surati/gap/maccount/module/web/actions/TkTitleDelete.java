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
package io.surati.gap.maccount.module.web.actions;

import io.surati.gap.admin.base.api.Log;
import io.surati.gap.admin.base.api.User;
import io.surati.gap.maccount.module.domain.api.Title;
import io.surati.gap.maccount.module.domain.api.Titles;
import io.surati.gap.maccount.module.domain.db.DbTitles;
import io.surati.gap.maccount.module.domain.secure.SecTitles;
import io.surati.gap.web.base.log.RqLog;
import io.surati.gap.web.base.rq.RqUser;
import java.util.logging.Level;
import javax.sql.DataSource;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

/**
 * Take that deletes a title.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @since 3.0
 */


public final class TkTitleDelete implements Take {

	/**
	 * Database
	 */
	private final DataSource source;

	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkTitleDelete(final DataSource source) {
		this.source = source;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		final Log log = new RqLog(this.source, req);
		final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));
		final User user = new RqUser(this.source, req);
		final Titles items = new SecTitles(
			new DbTitles(source),
			user
		);
		final Title item = items.get(id);
		final String name = item.name();
		final String fullname = item.fullName();
		items.remove(id);
		log.info("Suppression du titre %s)", fullname);
		return new RsForward(
			new RsFlash(
				String.format("Le titre %s a été supprimé avec succès !", name),
				Level.INFO
			),
			"/maccount/title"
		);	
	}
}
