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
package io.surati.gap.maccount.module.domain.api;

import io.surati.gap.admin.base.api.User;
import io.surati.gap.gtp.base.api.AnnualWarrant;
import io.surati.gap.gtp.base.api.Bundle;
import io.surati.gap.gtp.base.api.Section;
import io.surati.gap.gtp.base.api.Title;
import io.surati.gap.gtp.base.api.Treasury;
import java.time.LocalDateTime;

/**
 * Sub-bundle.
 * @since 0.5
 */
public interface SubBundle {

    /**
     * Identifier.
     * @return Number
     */
	Long id();

	/**
	 * Get treasury.
	 * @return Treasury
	 */
	Treasury treasury();

    /**
     * Order number.
     * @return order
     */
	int order();

	/**
	 * Year.
	 * @return Year
	 */
	short year();

	/**
	 * Get date of sub bundlde.
	 * @return Datetime
	 */
	LocalDateTime creationDate();

    /**
     * Get author.
     * @return login
     */
	User author();

    /**
     * Get title.
     * @return title
     */
	Title title();

    /**
     * Get section.
     * @return section
     */
	Section section();

    /**
     * Get bundle.
     * @return bundle
     */
	Bundle bundle();

    /**
     * Iterate all warrants bundled.
     * @return List of Warrants
     */
    Iterable<AnnualWarrant> warrants();

	int numberOfWarrants();

	/**
	 * Total amount paid.
	 * @return Amount
	 */
	Double totalAmountPaid();
}
