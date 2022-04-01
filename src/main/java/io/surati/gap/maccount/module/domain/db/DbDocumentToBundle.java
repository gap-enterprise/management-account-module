package io.surati.gap.maccount.module.domain.db;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.SingleOutcome;
import io.surati.gap.database.utils.exceptions.DatabaseException;
import io.surati.gap.maccount.module.domain.api.DocumentToBundle;
import io.surati.gap.maccount.module.domain.api.RefDocumentData;
import io.surati.gap.payment.base.api.ReferenceDocument;
import io.surati.gap.payment.base.api.ThirdParty;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.cactoos.text.Joined;

public final class DbDocumentToBundle implements DocumentToBundle {

    private final DataSource src;

    private final ReferenceDocument document;

    public DbDocumentToBundle(final DataSource src, final ReferenceDocument document) {
        this.src = src;
        this.document = document;
    }

    @Override
    public Long id() {
        return this.document.id();
    }

    @Override
    public String reference() {
        return this.document.reference();
    }

    @Override
    public String fullName() {
        return String.format("%s N°%s", this.document.type().toString(), this.reference());
    }

    @Override
    public int yearOfPayment() {
        try {
            return new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "SELECT year_of_payment FROM ma_document_to_bundle_view",
                        "WHERE id=?"
                    ).toString()
                )
                .set(this.document.id())
                .select(new SingleOutcome<>(Long.class)).intValue();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public Double totalAmount() {
        return this.document.amount();
    }

    @Override
    public Double annualAmountPaid() {
        try {
            return new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "SELECT annual_amount_paid FROM ma_document_to_bundle_view",
                        "WHERE id=?"
                    ).toString()
                )
                .set(this.document.id())
                .select(new SingleOutcome<>(Long.class)).doubleValue();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public Double leftAmount() {
        try {
            return new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "SELECT left_amount FROM ma_document_to_bundle_view",
                        "WHERE id=?"
                    ).toString()
                )
                .set(this.document.id())
                .select(new SingleOutcome<>(Long.class)).doubleValue();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public ThirdParty beneficiary() {
        return this.document.issuer();
    }

    @Override
    public RefDocumentData maData() {
        return new DbRefDocumentData(this.src, this.document);
    }
}
