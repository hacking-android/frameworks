/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.IllegalFormatException;

public class UnknownFormatConversionException
extends IllegalFormatException {
    private static final long serialVersionUID = 19060418L;
    private String s;

    public UnknownFormatConversionException(String string) {
        if (string != null) {
            this.s = string;
            return;
        }
        throw new NullPointerException();
    }

    public String getConversion() {
        return this.s;
    }

    @Override
    public String getMessage() {
        return String.format("Conversion = '%s'", this.s);
    }
}

