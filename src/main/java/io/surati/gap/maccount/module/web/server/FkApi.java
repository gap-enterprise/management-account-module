package io.surati.gap.maccount.module.web.server;

import com.minlessika.db.Database;
import io.surati.gap.maccount.module.web.rest.TkEntireDocumentToBundleSearch;
import io.surati.gap.maccount.module.web.rest.TkPartialDocumentToBundleSearch;
import io.surati.gap.web.base.TkSecure;
import org.takes.facets.fork.FkChain;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.FkWrap;

/**
 * Front for API.
 *
 * @since 0.1
 */
public final class FkApi extends FkWrap {

	public FkApi(final Database source) {
		super(
			new FkChain(
				new FkRegex(
					"/maccount/document-to-bundle/entire/search",
					new TkSecure(
						new TkEntireDocumentToBundleSearch(source),
						source
					)
				),
				new FkRegex(
					"/maccount/document-to-bundle/partial/search",
					new TkSecure(
						new TkPartialDocumentToBundleSearch(source),
						source
					)
				)
			)
		);
	}
}
