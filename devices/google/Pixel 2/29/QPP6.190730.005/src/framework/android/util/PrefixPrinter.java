/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.util.Printer;

public class PrefixPrinter
implements Printer {
    private final String mPrefix;
    private final Printer mPrinter;

    private PrefixPrinter(Printer printer, String string2) {
        this.mPrinter = printer;
        this.mPrefix = string2;
    }

    public static Printer create(Printer printer, String string2) {
        if (string2 != null && !string2.equals("")) {
            return new PrefixPrinter(printer, string2);
        }
        return printer;
    }

    @Override
    public void println(String string2) {
        Printer printer = this.mPrinter;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mPrefix);
        stringBuilder.append(string2);
        printer.println(stringBuilder.toString());
    }
}

