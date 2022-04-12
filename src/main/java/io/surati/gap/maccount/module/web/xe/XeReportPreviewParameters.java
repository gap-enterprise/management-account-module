package io.surati.gap.maccount.module.web.xe;

import com.minlessika.map.CleanMap;
import java.io.IOException;
import org.cactoos.collection.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

public final class XeReportPreviewParameters extends XeWrap {

    public XeReportPreviewParameters(final RqFormSmart form) throws IOException {
        this(new Directives()
            .add("parameters")
            .append(
                new Joined<>(
                    new Mapped<>(
                        name -> transform("parameter", name, form),
                        form.names()
                    )
                )
            ).up());
    }

    public XeReportPreviewParameters(final Iterable<Directive> dir) {
        super(() -> dir);
    }

    private static Iterable<Directive> transform(final String root, final String name, final RqFormSmart form) throws IOException {

        return new Directives()
            .add(root)
            .add(
                new CleanMap<String, Object>()
                    .add("name", name)
                    .add("value", form.single(name))
            )
            .up();
    }
}
