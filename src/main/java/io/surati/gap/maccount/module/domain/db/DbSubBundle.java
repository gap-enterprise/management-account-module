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
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import io.surati.gap.admin.base.api.User;
import io.surati.gap.admin.base.db.DbUser;
import io.surati.gap.maccount.module.domain.api.Bundle;
import io.surati.gap.maccount.module.domain.api.Section;
import io.surati.gap.maccount.module.domain.api.SubBundle;
import io.surati.gap.maccount.module.domain.api.SubBundleDocument;
import io.surati.gap.maccount.module.domain.api.Title;
import io.surati.gap.maccount.module.domain.db.jooq.generated.tables.records.MaSubBundleRecord;

/**
 * Sub-bundle from database.
 * @since 0.5
 */
public final class DbSubBundle implements SubBundle {

	/**
	 * Table of log events.
	 */
	private static final io.surati.gap.maccount.module.domain.db.jooq.generated.tables.MaSubBundle MA_SUB_BUNDLE = 
			io.surati.gap.maccount.module.domain.db.jooq.generated.tables.MaSubBundle.MA_SUB_BUNDLE;

	/**
	 * Record.
	 */
	private final MaSubBundleRecord record;

	/**
	 * Data source.
	 */
	private final DataSource source;

	/**
	 * Ctor.
	 * @param source Data source
	 * @param id Identifier
	 */
	public DbSubBundle(final DataSource source, final Long id) {
		this.record = DSL.using(new DefaultConfiguration().set(source))
			.fetchOne(MA_SUB_BUNDLE, MA_SUB_BUNDLE.ID.eq(id));
		this.source = source;
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
		return new DbUser(source, this.record.getAuthorId());
	}
    
    @Override
    public Title title() {
    	return new DbTitle(source, this.record.getTitleId());
    }
    
    @Override
    public Section section() {
    	return new DbSection(source, this.record.getSectionId());
    }
    
    @Override
    public Bundle bundle() {
    	return new DbBundle(source, this.record.getBundleId());
    }
    
    @Override
	public Iterable<SubBundleDocument> iterate() {
		return null;
	}
}
