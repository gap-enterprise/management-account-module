package io.surati.gap.maccount.module.report;

import java.io.OutputStream;

public abstract class PrinterWrap implements Printer {

    private final Printer origin;

    public PrinterWrap(final Printer origin) {
        this.origin = origin;
    }

    @Override
    public void print(final OutputStream output) throws Exception {
        this.origin.print(output);
    }
}
