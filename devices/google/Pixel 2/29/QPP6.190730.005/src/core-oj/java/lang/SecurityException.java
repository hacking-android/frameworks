/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

public class SecurityException
extends RuntimeException {
    private static final long serialVersionUID = 6878364983674394167L;

    public SecurityException() {
    }

    public SecurityException(String string) {
        super(string);
    }

    public SecurityException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SecurityException(Throwable throwable) {
        super(throwable);
    }
}

