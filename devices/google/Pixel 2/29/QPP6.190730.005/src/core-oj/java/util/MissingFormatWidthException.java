/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.IllegalFormatException;

public class MissingFormatWidthException
extends IllegalFormatException {
    private static final long serialVersionUID = 15560123L;
    private String s;

    public MissingFormatWidthException(String string) {
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
        return this.s;
    }
}

