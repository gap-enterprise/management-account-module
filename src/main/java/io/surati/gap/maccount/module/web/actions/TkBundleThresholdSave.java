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
import io.surati.gap.maccount.module.ManagementAccountAccess;
import io.surati.gap.maccount.module.domain.api.BundleThreshold;
import io.surati.gap.maccount.module.domain.api.PropBundleThreshold;
import io.surati.gap.web.base.log.RqLog;
import io.surati.gap.web.base.rq.RqUser;
import java.util.logging.Level;
import javax.sql.DataSource;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

/**
 * Take that save bundle threshold.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @since 3.0
 */


public final class TkBundleThresholdSave implements Take {

	/**
	 * Database
	 */
	private final DataSource source;

	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkBundleThresholdSave(final DataSource source) {
		this.source = source;
	}
	
	@Override
	public Response act(final Request req) throws Exception {
		final Log log = new RqLog(this.source, req);
		final BundleThreshold threshold = new PropBundleThreshold();
		final User user = new RqUser(this.source, req);
		if(!user.profile().accesses().has(ManagementAccountAccess.CONFIGURER_SEUILS_ENLIASSEMENT)) {
    		throw new IllegalArgumentException("Vos droits d’accès sont insuffisants pour mener cette action.");
    	}
		final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
		final int partialpayment = threshold.partialPayment();
		final int totalpayment = threshold.totalPayment();
		
		threshold.update(
			Integer.valueOf(form.single("partial_payment")),
			Integer.valueOf(form.single("total_payment"))
		);
		log.info("Modification des seuils d'enliassement de (Règlement partiel=%s, Règlement total=%s) en (Règlement partiel=%s, Règlement total=%s)", partialpayment, totalpayment, threshold.partialPayment(), threshold.totalPayment());
		return new RsForward(
			new RsFlash(
				"Les seuils d'enliassement ont été enregistrés avec succès !",
				Level.INFO
			),
			"/maccount/bundle-threshold/view"
		);	
	}
}
