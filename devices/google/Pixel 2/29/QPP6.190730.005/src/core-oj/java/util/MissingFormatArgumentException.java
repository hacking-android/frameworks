/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.IllegalFormatException;

public class MissingFormatArgumentException
extends IllegalFormatException {
    private static final long serialVersionUID = 19190115L;
    private String s;

    public MissingFormatArgumentException(String string) {
        if (string != null) {
            this.s = string;
            return;
        }
        throw new NullPointerException();
    }

    public String getFormatSpecifier() {
        return this.s;
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Format specifier '");
        stringBuilder.append(this.s);
        stringBuilder.append("'");
        return stringBuilder.toString();
    }
}

