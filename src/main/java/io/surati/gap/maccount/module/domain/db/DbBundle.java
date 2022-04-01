package io.surati.gap.maccount.module.domain.db;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.SingleOutcome;
import io.surati.gap.database.utils.exceptions.DatabaseException;
import io.surati.gap.maccount.module.domain.api.Bundle;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.cactoos.text.Joined;

/**
 * Bundle from database.
 * @since 3.0
 */
public final class DbBundle implements Bundle {

    /**
     * Data source.
     */
    private final DataSource src;

    /**
     * Key.
     */
    private final Long id;
    
    /**
     * Ctor.
     * @param src Data source
     * @param id Identifier
     */
    public DbBundle(final DataSource src, final Long id) {
        this.src = src;
        this.id = id;
    }

    @Override
    public Long id() {
        return this.id;
    }
    
    private boolean codeIsUsed(String code) {
        try {
            return new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "SELECT count(*) FROM ma_bundle",
                        "WHERE code=? AND id<>?"
                    ).toString()
                )
                .set(code)
                .set(this.id)
                .select(new SingleOutcome<>(Long.class)) > 0;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public String code() {
        try {
            return new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "SELECT code FROM ma_bundle",
                        "WHERE id=?"
                    ).toString()
                )
                .set(this.id)
                .select(new SingleOutcome<>(String.class));
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public String notes() {
        try {
            return new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "SELECT notes FROM ma_bundle",
                        "WHERE id=?"
                    ).toString()
                )
                .set(this.id)
                .select(new SingleOutcome<>(String.class));
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public void update(final String code, final String notes) {
    	if(this.codeIsUsed(code))
			throw new IllegalArgumentException("Ce code est déjà utilisé.");
        try {
            new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "UPDATE ma_bundle",
                        "SET code=?,notes=?",
                        "WHERE id=?"
                    ).toString()
                )
                .set(code)
                .set(notes)
                .set(this.id)
                .execute();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }
}
