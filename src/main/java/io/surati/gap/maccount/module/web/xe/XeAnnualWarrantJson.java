package io.surati.gap.maccount.module.web.xe;

import io.surati.gap.commons.utils.amount.FrAmountInXof;
import io.surati.gap.commons.utils.amount.FrThousandSeparatorAmount;
import io.surati.gap.commons.utils.convert.FrShortDateFormat;
import io.surati.gap.maccount.module.domain.api.AnnualWarrant;
import java.time.format.DateTimeFormatter;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonStructure;
import org.takes.rs.RsJson;

public final class XeAnnualWarrantJson implements RsJson.Source {

	private final Long count;
	private final Iterable<AnnualWarrant> items;

	public XeAnnualWarrantJson(
		final Iterable<AnnualWarrant> items, final Long count
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
	
	private static JsonArray toJson(final Iterable<AnnualWarrant> items) {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		for (final AnnualWarrant item : items) {
			builder.add(Json.createObjectBuilder()
				.add("id", item.id())
				.add("date_view", new FrShortDateFormat().convert(item.date()))
				.add("year", item.year())
				.add("bundle", item.bundle().code())
				.add("title", item.title().fullName())
				.add("section", item.section().fullName())
				.add("imputation", item.imputation())
				.add("reference", item.reference())
				.add("annual_amount_to_pay", item.annualAmountToPay())
				.add("annual_amount_to_pay_in_human", new FrThousandSeparatorAmount(item.annualAmountToPay()).toString())
				.add("annual_amount_paid", item.annualAmountPaid())
				.add("annual_amount_paid_in_human", new FrThousandSeparatorAmount(item.annualAmountPaid()).toString())
				.add("annual_amount_left", item.annualAmountLeft())
				.add("annual_amount_left_in_human", new FrThousandSeparatorAmount(item.annualAmountLeft()).toString())
				.add("total_amount_to_pay", item.amount())
				.add("total_amount_to_pay_in_human", new FrThousandSeparatorAmount(item.amount()).toString())
				.add("beneficiary", item.issuer().name())
           );
		}
		return builder.build();
	}

}
