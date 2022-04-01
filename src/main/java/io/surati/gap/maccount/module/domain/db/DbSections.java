package io.surati.gap.maccount.module.domain.db;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import com.jcabi.jdbc.SingleOutcome;
import io.surati.gap.database.utils.exceptions.DatabaseException;
import io.surati.gap.maccount.module.domain.api.Section;
import io.surati.gap.maccount.module.domain.api.Sections;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.cactoos.text.Joined;

/**
 * Sections from database.
 * @since 3.0
 */
public final class DbSections implements Sections {

    /**
     * Data source.
     */
    private final DataSource src;
    
    /**
     * Ctor.
     * @param src Data source
     */
    public DbSections(final DataSource src) {
        this.src = src;
    }
    
	@Override
	public boolean has(final String code) {
		try {
			return new JdbcSession(this.src)
				.sql("SELECT COUNT(*) FROM ma_section WHERE code=?")
				.set(code)
				.select(new SingleOutcome<>(Long.class)) > 0;
		} catch(SQLException ex) {
			throw new DatabaseException(ex);
		}
	}

    @Override
    public Section get(final Long key) {
        try(
            final Connection connection = this.src.getConnection();
            final PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM ma_section WHERE id=?");
        ){
            pstmt.setObject(1, key);
            try(final ResultSet rs = pstmt.executeQuery()){
                if(rs.next()) {
                    return new DbSection(this.src, key);
                } else {
                    throw new IllegalArgumentException("La section recherchée est introuvable !");
                }
            }
        } catch(SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Iterable<Section> iterate() {
        try {
            return
                new JdbcSession(this.src)
                    .sql(
                        new Joined(
                            " ",
                            "SELECT id FROM ma_section",
                            "ORDER BY code ASC"
                        ).toString()
                    )
                    .select(
                        new ListOutcome<>(
                            rset ->
                                new DbSection(
                                    this.src,
                                    rset.getLong(1)
                                )
                        )
                    );
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public Section add(final String code, final String name, final String notes) {
        if(StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("Vous devez fournir un code !");
        }
        if(StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Vous devez renseigner le libellé !");
        }
        if(this.has(code)) {
			throw new IllegalArgumentException("Le code est déjà utilisé !");
        }
        try {
            return new DbSection(
                this.src,
                new JdbcSession(this.src)
                    .sql(
                        new Joined(
                            " ",
                            "INSERT INTO ma_section",
                            "(code, name, notes)",
                            "VALUES",
                            "(?, ?, ?)"
                        ).toString()
                    )
                    .set(code.trim())
                    .set(name.trim())
                    .set(notes)
                    .insert(new SingleOutcome<>(Long.class))
            );
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public void remove(final Long key) {
        try {
            new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "DELETE FROM ma_section",
                        "WHERE id=?"
                    ).toString()
                )
                .set(key)
                .execute();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }
}
