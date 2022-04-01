package io.surati.gap.maccount.module.domain.db;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.SingleOutcome;
import io.surati.gap.database.utils.exceptions.DatabaseException;
import io.surati.gap.maccount.module.domain.api.Title;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.cactoos.text.Joined;

/**
 * Title from database.
 * @since 3.0
 */
public final class DbTitle implements Title {

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
    public DbTitle(final DataSource src, final Long id) {
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
                        "SELECT count(*) FROM ma_title",
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
                        "SELECT code FROM ma_title",
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
    public String name() {
        try {
            return new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "SELECT name FROM ma_title",
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
                        "SELECT notes FROM ma_title",
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
    public void update(final String code, final String name, final String notes) {
    	if(this.codeIsUsed(code))
			  throw new IllegalArgumentException("Ce code est déjà utilisé.");
        try {
            new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "UPDATE ma_title",
                        "SET code=?,name=?,notes=?",
                        "WHERE id=?"
                    ).toString()
                )
                .set(code)
                .set(name)
                .set(notes)
                .set(this.id)
                .execute();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

	@Override
	public String fullName() {
		return String.format("%s - %s", this.code(), this.name());
	}
}
