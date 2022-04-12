package io.surati.gap.maccount.module.web.pages;

import io.surati.gap.web.base.RsPage;
import io.surati.gap.web.base.xe.XeRootPage;
import javax.sql.DataSource;
import org.cactoos.iterable.Sticky;
import org.takes.rq.RqHref;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.takes.tk.TkWrap;

public final class TkManagementAccountPreview extends TkWrap {

    public TkManagementAccountPreview(final DataSource src) {
        super(
            req -> {
                final XeSource srce = new XeChain(
                    new XeAppend(
                        "report_url",
                        String.format(
                            "/maccount/report/management-account/print?year=%s",
                            new RqHref.Smart(req).single("year")
                        )
                    )
                );
                return new RsPage(
                    "/io/surati/gap/maccount/module/xsl/report/report_preview.xsl",
                    req,
                    src,
                    ()-> new Sticky<>(
                        new XeRootPage(req),
                        srce
                    )
                );

            }
        );
    }
}
