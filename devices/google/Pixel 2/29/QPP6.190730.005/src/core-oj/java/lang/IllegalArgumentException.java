/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

public class IllegalArgumentException
extends RuntimeException {
    private static final long serialVersionUID = -5365630128856068164L;

    public IllegalArgumentException() {
    }

    public IllegalArgumentException(String string) {
        super(string);
    }

    public IllegalArgumentException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public IllegalArgumentException(Throwable throwable) {
        super(throwable);
    }
}

