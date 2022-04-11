package io.surati.gap.maccount.module.web.xe;

import io.surati.gap.commons.utils.amount.FrAmountInXof;
import io.surati.gap.commons.utils.convert.FrShortDateFormat;
import io.surati.gap.maccount.module.domain.api.SubBundle;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonStructure;
import org.takes.rs.RsJson;

public final class XeSubBundleJson implements RsJson.Source {

	private final Long count;
	private final Iterable<SubBundle> items;

	public XeSubBundleJson(
		final Iterable<SubBundle> items, final Long count
	) {
		this.count = count;
		this.items = items;
	}
	
	@Override
	public JsonStructure toJson() {
		return Json.createObjectBuilder()
		   .add("items", toJson(this.items))
           .add("count", this.count)
		   .build();
	}
	
	private static JsonArray toJson(final Iterable<SubBundle> items) {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		for (final SubBundle item : items) {
			builder.add(Json.createObjectBuilder()
				.add("id", item.id())
				.add("order", item.order())
				.add("date_view", new FrShortDateFormat().convert(item.creationDate().toLocalDate()))
				.add("year", item.year())
				.add("bundle", item.bundle().code())
				.add("title", item.title().code())
				.add("section", item.section().code())
				.add("number_of_warrants", item.numberOfWarrants())
				.add("total_amount_paid_in_human", new FrAmountInXof(item.totalAmountPaid()).toString())
           );
		}
		return builder.build();
	}

}
