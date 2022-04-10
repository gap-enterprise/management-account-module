/*
 * Copyright (c) 2022 Surati

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.surati.gap.maccount.module.domain.db;

import io.surati.gap.admin.base.api.User;
import io.surati.gap.admin.base.db.DbUser;
import io.surati.gap.database.utils.jooq.JooqContext;
import io.surati.gap.gtp.base.api.Bundle;
import io.surati.gap.gtp.base.api.Section;
import io.surati.gap.gtp.base.api.Title;
import io.surati.gap.gtp.base.api.Warrant;
import io.surati.gap.gtp.base.db.DbBundle;
import io.surati.gap.gtp.base.db.DbSection;
import io.surati.gap.gtp.base.db.DbTitle;
import io.surati.gap.maccount.module.domain.api.SubBundle;
import io.surati.gap.maccount.module.domain.db.jooq.generated.tables.MaSubBundle;
import io.surati.gap.maccount.module.domain.db.jooq.generated.tables.records.MaSubBundleRecord;
import java.time.LocalDateTime;
import javax.sql.DataSource;

/**
 * Sub-bundle from database.
 * @since 0.2
 */
public final class DbSubBundle implements SubBundle {

	/**
	 * Record.
	 */
	private final MaSubBundleRecord record;

	/**
	 * Data source.
	 */
	private final DataSource src;

	/**
	 * Ctor.
	 * @param source Data source
	 * @param id Identifier
	 */
	public DbSubBundle(final DataSource source, final Long id) {
		this.record = new JooqContext(source)
			.fetchOne(MaSubBundle.MA_SUB_BUNDLE, MaSubBundle.MA_SUB_BUNDLE.ID.eq(id));
		this.src = source;
	}
	
    @Override
    public Long id() {
    	return this.record.getId();
    }
    
    @Override
    public int order() {
    	return 0 /*this.record.getNo()*/;
    }
    
    @Override
	public LocalDateTime creationDate() {
		return this.record.getCreationDate();
	}
    
    @Override
	public User author() {
		return new DbUser(this.src, this.record.getAuthorId());
	}
    
    @Override
    public Title title() {
    	return new DbTitle(this.src, "0");
    }
    
    @Override
    public Section section() {
    	return new DbSection(this.src, "0");
    }
    
    @Override
    public Bundle bundle() {
    	return new DbBundle(this.src, "0");
    }
    
    @Override
	public Iterable<Warrant> warrants() {
		return null;
	}
}
