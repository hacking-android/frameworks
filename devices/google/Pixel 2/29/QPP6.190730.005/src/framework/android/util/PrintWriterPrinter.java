/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.util.Printer;
import java.io.PrintWriter;

public class PrintWriterPrinter
implements Printer {
    private final PrintWriter mPW;

    public PrintWriterPrinter(PrintWriter printWriter) {
        this.mPW = printWriter;
    }

    @Override
    public void println(String string2) {
        this.mPW.println(string2);
    }
}

