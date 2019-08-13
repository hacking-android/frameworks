/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.IllegalFormatException;

public class IllegalFormatWidthException
extends IllegalFormatException {
    private static final long serialVersionUID = 16660902L;
    private int w;

    public IllegalFormatWidthException(int n) {
        this.w = n;
    }

    @Override
    public String getMessage() {
        return Integer.toString(this.w);
    }

    public int getWidth() {
        return this.w;
    }
}

