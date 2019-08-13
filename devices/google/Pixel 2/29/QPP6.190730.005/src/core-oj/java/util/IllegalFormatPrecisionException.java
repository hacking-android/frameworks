/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.IllegalFormatException;

public class IllegalFormatPrecisionException
extends IllegalFormatException {
    private static final long serialVersionUID = 18711008L;
    private int p;

    public IllegalFormatPrecisionException(int n) {
        this.p = n;
    }

    @Override
    public String getMessage() {
        return Integer.toString(this.p);
    }

    public int getPrecision() {
        return this.p;
    }
}

