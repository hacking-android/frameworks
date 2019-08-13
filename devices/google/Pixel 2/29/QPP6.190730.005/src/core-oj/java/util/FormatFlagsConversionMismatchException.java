/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.IllegalFormatException;

public class FormatFlagsConversionMismatchException
extends IllegalFormatException {
    private static final long serialVersionUID = 19120414L;
    private char c;
    private String f;

    public FormatFlagsConversionMismatchException(String string, char c) {
        if (string != null) {
            this.f = string;
            this.c = c;
            return;
        }
        throw new NullPointerException();
    }

    public char getConversion() {
        return this.c;
    }

    public String getFlags() {
        return this.f;
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Conversion = ");
        stringBuilder.append(this.c);
        stringBuilder.append(", Flags = ");
        stringBuilder.append(this.f);
        return stringBuilder.toString();
    }
}

