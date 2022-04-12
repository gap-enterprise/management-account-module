package io.surati.gap.maccount.module.report;

import java.io.OutputStream;

public interface Printer {
    void print(OutputStream output) throws Exception;
}
