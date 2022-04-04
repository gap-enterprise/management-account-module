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

import java.time.LocalDateTime;
import javax.sql.DataSource;
import io.surati.gap.admin.base.api.User;
import io.surati.gap.admin.base.db.DbUser;
import io.surati.gap.database.utils.jooq.JooqContext;
import io.surati.gap.maccount.module.domain.api.Bundle;
import io.surati.gap.maccount.module.domain.api.Section;
import io.surati.gap.maccount.module.domain.api.SubBundle;
import io.surati.gap.maccount.module.domain.api.SubBundleDocument;
import io.surati.gap.maccount.module.domain.api.Title;
import io.surati.gap.maccount.module.domain.db.jooq.generated.tables.MaSubBundle;
import io.surati.gap.maccount.module.domain.db.jooq.generated.tables.records.MaSubBundleRecord;

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
    public Long order() {
    	return this.record.getNo();
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
    	return new DbTitle(this.src, this.record.getTitleId());
    }
    
    @Override
    public Section section() {
    	return new DbSection(this.src, this.record.getSectionId());
    }
    
    @Override
    public Bundle bundle() {
    	return new DbBundle(this.src, this.record.getBundleId());
    }
    
    @Override
	public Iterable<SubBundleDocument> iterate() {
		return null;
	}
}
