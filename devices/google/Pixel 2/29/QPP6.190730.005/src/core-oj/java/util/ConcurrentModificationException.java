/*
 * Decompiled with CFR 0.145.
 */
package java.util;

public class ConcurrentModificationException
extends RuntimeException {
    private static final long serialVersionUID = -3666751008965953603L;

    public ConcurrentModificationException() {
    }

    public ConcurrentModificationException(String string) {
        super(string);
    }

    public ConcurrentModificationException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public ConcurrentModificationException(Throwable throwable) {
        super(throwable);
    }
}

