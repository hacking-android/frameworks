/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.IllegalFormatException;

public class IllegalFormatCodePointException
extends IllegalFormatException {
    private static final long serialVersionUID = 19080630L;
    private int c;

    public IllegalFormatCodePointException(int n) {
        this.c = n;
    }

    public int getCodePoint() {
        return this.c;
    }

    @Override
    public String getMessage() {
        return String.format("Code point = %#x", this.c);
    }
}

