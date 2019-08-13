/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.util.Printer;

public class StringBuilderPrinter
implements Printer {
    private final StringBuilder mBuilder;

    public StringBuilderPrinter(StringBuilder stringBuilder) {
        this.mBuilder = stringBuilder;
    }

    @Override
    public void println(String string2) {
        this.mBuilder.append(string2);
        int n = string2.length();
        if (n <= 0 || string2.charAt(n - 1) != '\n') {
            this.mBuilder.append('\n');
        }
    }
}

