/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.util.Printer;
import java.io.PrintStream;

public class PrintStreamPrinter
implements Printer {
    private final PrintStream mPS;

    public PrintStreamPrinter(PrintStream printStream) {
        this.mPS = printStream;
    }

    @Override
    public void println(String string2) {
        this.mPS.println(string2);
    }
}

