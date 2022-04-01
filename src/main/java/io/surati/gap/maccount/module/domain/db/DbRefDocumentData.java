package io.surati.gap.maccount.module.domain.db;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.Outcome;
import com.jcabi.jdbc.SingleOutcome;
import io.surati.gap.database.utils.exceptions.DatabaseException;
import io.surati.gap.maccount.module.domain.api.Bundle;
import io.surati.gap.maccount.module.domain.api.RefDocumentData;
import io.surati.gap.maccount.module.domain.api.Section;
import io.surati.gap.maccount.module.domain.api.Title;
import io.surati.gap.payment.base.api.ReferenceDocument;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.cactoos.text.Joined;

/**
 * Management account data of reference document from database.
 *
 * @since 3.0
 */
public final class DbRefDocumentData implements RefDocumentData {

    /**
     * Data source.
     */
    private final DataSource src;

    /**
     * Reference document.
     */
    private final ReferenceDocument doc;
    
    /**
     * Ctor.
     * @param src Data source
     * @param doc Reference document
     */
    public DbRefDocumentData(final DataSource src, final ReferenceDocument doc) {
        this.src = src;
        this.doc = doc;
    }

    @Override
    public ReferenceDocument document() {
        return this.doc;
    }

    @Override
    public Bundle bundle() {
        this.check();
        try {
            return new DbBundle(
                this.src,
                new JdbcSession(this.src)
                    .sql(
                        new Joined(
                            " ",
                            "SELECT bundle_id FROM ma_data",
                            "WHERE id=?"
                        ).toString()
                    )
                    .set(this.doc.id())
                    .select(new SingleOutcome<>(Long.class))
            );
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public Title title() {
        this.check();
        try {
            return new DbTitle(
                this.src,
                new JdbcSession(this.src)
                    .sql(
                        new Joined(
                            " ",
                            "SELECT title_id FROM ma_data",
                            "WHERE id=?"
                        ).toString()
                    )
                    .set(this.doc.id())
                    .select(new SingleOutcome<>(Long.class))
            );
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public Section section() {
        this.check();
        try {
            return new DbSection(
                this.src,
                new JdbcSession(this.src)
                    .sql(
                        new Joined(
                            " ",
                            "SELECT section_id FROM ma_data",
                            "WHERE id=?"
                        ).toString()
                    )
                    .set(this.doc.id())
                    .select(new SingleOutcome<>(Long.class))
            );
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public String imputation() {
        this.check();
        try {
            return new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "SELECT imputation FROM ma_data",
                        "WHERE id=?"
                    ).toString()
                )
                .set(this.doc.id())
                .select(new SingleOutcome<>(String.class));
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public void update(final Bundle bundle, final Title title, final Section section, final String imputation) {
        if(StringUtils.isBlank(imputation)) {
            throw new IllegalArgumentException("Vous devez fournir une référence d'imputation !");
        }
        try {
            if (this.isDefined()) {
                new JdbcSession(this.src)
                    .sql(
                        new Joined(
                            " ",
                            "UPDATE ma_data",
                            "SET bundle_id=?,title_id=?,section_id=?,imputation=?",
                            "WHERE id=?"
                        ).toString()
                    )
                    .set(bundle.id())
                    .set(title.id())
                    .set(section.id())
                    .set(imputation)
                    .set(this.doc.id())
                    .execute();
            } else {
                new JdbcSession(this.src)
                    .sql(
                        new Joined(
                            " ",
                            "INSERT INTO ma_data",
                            "(id, bundle_id, title_id, section_id, imputation)",
                            "VALUES",
                            "(?, ?, ?, ?, ?)"
                        ).toString()
                    )
                    .set(this.doc.id())
                    .set(bundle.id())
                    .set(title.id())
                    .set(section.id())
                    .set(imputation.trim())
                    .insert(Outcome.VOID);
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public boolean isDefined() {
        try {
            return new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "SELECT count(*) FROM ma_data",
                        "WHERE id=?"
                    ).toString()
                )
                .set(this.doc.id())
                .select(new SingleOutcome<>(Long.class)) > 0;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    private void check() {
        if (!this.isDefined()) {
            throw new IllegalArgumentException("Data must be defined first !");
        }
    }
}
