package io.surati.gap.maccount.module.domain.db;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import com.jcabi.jdbc.SingleOutcome;
import io.surati.gap.database.utils.exceptions.DatabaseException;
import io.surati.gap.maccount.module.domain.api.Bundle;
import io.surati.gap.maccount.module.domain.api.DocumentToBundle;
import io.surati.gap.maccount.module.domain.api.DocumentsToBundle;
import io.surati.gap.maccount.module.domain.api.Section;
import io.surati.gap.maccount.module.domain.api.Title;
import io.surati.gap.payment.base.db.DbReferenceDocument;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.cactoos.text.Joined;

public final class DbPaginatedPartialDocumentsToBundle implements DocumentsToBundle {

    private final DataSource src;

    private final Title title;

    private final Section section;

    private final Bundle pbundle;

    private int year;

    private final String filter;

    private final Long nbperpage;

    private final Long page;

    /**
     *
     * @param src Data source
     * @param title Title
     * @param section Section
     * @param pbundle Parent bundle
     * @param year Year
     * @param filter Filter on reference and imputation
     */
    public DbPaginatedPartialDocumentsToBundle(
        final DataSource src, final Long nbperpage, final Long page, final Title title,
        final Section section, final Bundle pbundle, final int year, final String filter
    ) {
        this.src = src;
        this.nbperpage = nbperpage;
        this.page = page;
        this.title = title;
        this.section = section;
        this.pbundle = pbundle;
        this.year = year;
        this.filter = filter;
    }

    @Override
    public Iterable<DocumentToBundle> iterate() {
        try {
            return
                new JdbcSession(this.src)
                    .sql(
                        new Joined(
                            " ",
                            "SELECT id FROM ma_document_to_bundle_view",
                            "WHERE (reference ILIKE ? OR imputation ILIKE ?)",
                            "AND year_of_payment = ?",
                            "AND bundle_id = ? OR ? = 0",
                            "AND title_id = ? OR ? = 0",
                            "AND section_id = ? OR ? = 0",
                            "AND is_split=true",
                            "ORDER BY id DESC",
                            "LIMIT ? OFFSET ?"
                        ).toString()
                    )
                    .set("%" + this.filter + "%")
                    .set("%" + this.filter + "%")
                    .set(this.year)
                    .set(this.pbundle.id())
                    .set(this.pbundle.id())
                    .set(this.title.id())
                    .set(this.title.id())
                    .set(this.section.id())
                    .set(this.section.id())
                    .set(this.nbperpage)
                    .set(this.nbperpage * (this.page - 1))
                    .select(
                        new ListOutcome<>(
                            rset ->
                                new DbDocumentToBundle(
                                    this.src,
                                    new DbReferenceDocument(this.src, rset.getLong(1))
                                )
                        )
                    );
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public Long count() {
        try {
            return new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "SELECT count(*) FROM ma_document_to_bundle_view",
                        "WHERE (reference ILIKE ? OR imputation ILIKE ?)",
                        "AND year_of_payment = ?",
                        "AND bundle_id = ? OR ? = 0",
                        "AND title_id = ? OR ? = 0",
                        "AND section_id = ? OR ? = 0",
                        "AND is_split=true"
                    ).toString()
                )
                .set("%" + this.filter + "%")
                .set("%" + this.filter + "%")
                .set(this.year)
                .set(this.pbundle.id())
                .set(this.pbundle.id())
                .set(this.title.id())
                .set(this.title.id())
                .set(this.section.id())
                .set(this.section.id())
                .select(new SingleOutcome<>(Long.class));
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public DocumentToBundle get(Long id) {
        if(this.has(id)) {
            return new DbDocumentToBundle(
                this.src,
                new DbReferenceDocument(this.src, id)
            );
        } else {
            throw new IllegalArgumentException("Document à enliasser non trouvé !");
        }
    }

    @Override
    public boolean has(final Long id) {
        try {
            return new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "SELECT count(*) FROM ma_document_to_bundle_view",
                        "WHERE (reference ILIKE ? OR imputation ILIKE ?)",
                        "AND year_of_payment = ?",
                        "AND bundle_id = ? OR ? = 0",
                        "AND title_id = ? OR ? = 0",
                        "AND section_id = ? OR ? = 0",
                        "AND is_split=true",
                        "AND id=?"
                    ).toString()
                )
                .set("%" + this.filter + "%")
                .set("%" + this.filter + "%")
                .set(this.year)
                .set(this.pbundle.id())
                .set(this.pbundle.id())
                .set(this.title.id())
                .set(this.title.id())
                .set(this.section.id())
                .set(this.section.id())
                .set(id)
                .select(new SingleOutcome<>(Long.class)) > 0;
        } catch(SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public Double totalAmount() {
        try {
            return new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "SELECT sum(annual_amount_paid) FROM ma_document_to_bundle_view",
                        "WHERE (reference ILIKE ? OR imputation ILIKE ?)",
                        "AND year_of_payment = ?",
                        "AND bundle_id = ? OR ? = 0",
                        "AND title_id = ? OR ? = 0",
                        "AND section_id = ? OR ? = 0",
                        "AND is_split=true"
                    ).toString()
                )
                .set("%" + this.filter + "%")
                .set("%" + this.filter + "%")
                .set(this.year)
                .set(this.pbundle.id())
                .set(this.pbundle.id())
                .set(this.title.id())
                .set(this.title.id())
                .set(this.section.id())
                .set(this.section.id())
                .select(new SingleOutcome<>(Long.class)).doubleValue();
        } catch(SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

}
