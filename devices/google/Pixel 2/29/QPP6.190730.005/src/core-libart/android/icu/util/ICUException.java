/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

public class ICUException
extends RuntimeException {
    private static final long serialVersionUID = -3067399656455755650L;

    public ICUException() {
    }

    public ICUException(String string) {
        super(string);
    }

    public ICUException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public ICUException(Throwable throwable) {
        super(throwable);
    }
}

