package io.surati.gap.maccount.module.report;

import io.surati.gap.gtp.base.api.AnnualWarrant;
import io.surati.gap.gtp.base.api.Treasury;
import io.surati.gap.maccount.module.domain.api.ManagementAccount;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.cactoos.list.ListOf;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.eclipse.core.internal.registry.RegistryProviderFactory;

public final class BirtBudgetExpenditureDetailsPrinter extends PrinterWrap {

	public BirtBudgetExpenditureDetailsPrinter(
		final Iterable<AnnualWarrant> warrants,
		final Treasury treasury,
		final int year
	) {
		super(
			output -> {
				final Map<String, Object> context = new HashMap<>();
				context.put("lines", new ListOf<>(warrants));
				IReportEngine engine = null;
				try {
					final EngineConfig config = new EngineConfig();
					Platform.startup(config);
					final IReportEngineFactory fact = (IReportEngineFactory) Platform
						.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
					engine = fact.createReportEngine(config);
					final InputStream rptres = BirtBudgetExpenditureDetailsPrinter
						.class.getClassLoader().getResourceAsStream("io/surati/gap/maccount/module/report/ma_budget_expenditure_details.rptdesign");
					final IReportRunnable runnable = engine.openReportDesign(rptres);
					final IRunAndRenderTask task = engine.createRunAndRenderTask(runnable);
					final RenderOption pdfOptions = new PDFRenderOption();
					pdfOptions.setOutputFormat("PDF");
					pdfOptions.setOutputStream(output);
					task.setLocale(Locale.FRENCH);
					task.setRenderOption(pdfOptions);
					task.setAppContext(context);
					task.setParameterValue("Treasury", treasury.name());
					task.setParameterValue("FiscalYear", year);
					task.run();
					task.close();
				} finally {
					try
					{
						engine.destroy();
						Platform.shutdown();
						//Bugzilla 351052
						RegistryProviderFactory.releaseDefault();
					}catch ( Exception e1 ){
						e1.printStackTrace();
					}
				}
			}
		);
	}
}
