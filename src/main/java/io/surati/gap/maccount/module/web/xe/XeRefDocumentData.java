/**
MIT License

Copyright (c) 2021 Surati

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package io.surati.gap.maccount.module.web.xe;

import com.minlessika.map.CleanMap;
import io.surati.gap.maccount.module.domain.api.RefDocumentData;
import org.takes.rs.xe.XeDirectives;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directives;

/**
 * Reference document data xml.
 *
 * @since 3.0
 */
public final class XeRefDocumentData extends XeWrap {

	public XeRefDocumentData(final RefDocumentData item) {
		this("item", item);
	}

	public XeRefDocumentData(final String name, final RefDocumentData item) {
		super(
			new XeDirectives(
				new Directives()
				.add(name)
				.add(
					new CleanMap<>()
						.add("document_id", item.document().id())
						.add("bundle_id", item.bundle().id())
						.add("bundle", item.bundle().code())
						.add("title_id", item.title().id())
						.add("title", item.title().name())
						.add("section_id", item.section().id())
						.add("section", item.section().name())
						.add("imputation", item.imputation())
						.add("is_defined", item.isDefined())
				)
				.up()
			)
		);
	}
}
