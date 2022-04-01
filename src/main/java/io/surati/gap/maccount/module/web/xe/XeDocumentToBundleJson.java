package io.surati.gap.maccount.module.web.xe;

import io.surati.gap.commons.utils.amount.FrAmountInXof;
import io.surati.gap.maccount.module.domain.api.DocumentToBundle;
import java.io.IOException;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonStructure;
import org.takes.rs.RsJson;

public final class XeDocumentToBundleJson implements RsJson.Source {

	private final Long count;
	private final Double amountpaid;
	private final Iterable<DocumentToBundle> items;

	public XeDocumentToBundleJson(final Iterable<DocumentToBundle> items, final Long count, final Double amountpaid) {
		this.count = count;
		this.items = items;
		this.amountpaid = amountpaid;
	}
	
	@Override
	public JsonStructure toJson() throws IOException {
		return Json.createObjectBuilder()
		   .add("items", toJson(this.items))
           .add("count", this.count)
			.add("annual_amount_paid", this.amountpaid)
			.add("annual_amount_paid_in_human", new FrAmountInXof(this.amountpaid).toString())
		   .build();
	}
	
	private static JsonArray toJson(final Iterable<DocumentToBundle> items) throws IOException {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		for (DocumentToBundle item : items) {
			builder.add(Json.createObjectBuilder()
				.add("id", item.id())
				.add("name", item.fullName())
				.add("bundle_id", item.maData().bundle().id())
				.add("bundle", item.maData().bundle().code())
				.add("title_id", item.maData().title().id())
				.add("title", item.maData().title().fullName())
				.add("section_id", item.maData().section().id())
				.add("section", item.maData().section().fullName())
				.add("imputation", item.maData().imputation())
				.add("year_of_payment", item.yearOfPayment())
				.add("annual_amount_paid", item.annualAmountPaid())
				.add("annual_amount_paid_in_human", new FrAmountInXof(item.annualAmountPaid()).toString())
				.add("total_amount", item.totalAmount())
				.add("total_amount_in_human", new FrAmountInXof(item.totalAmount()).toString())
				.add("left_amount", item.leftAmount())
				.add("left_amount_in_human", new FrAmountInXof(item.leftAmount()).toString())
				.add("beneficiary", item.beneficiary().name())
           );
		}
		return builder.build();
	}

}
