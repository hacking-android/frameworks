/*
 * Decompiled with CFR 0.145.
 */
package java.lang.invoke;

public class WrongMethodTypeException
extends RuntimeException {
    private static final long serialVersionUID = 292L;

    public WrongMethodTypeException() {
    }

    public WrongMethodTypeException(String string) {
        super(string);
    }

    WrongMethodTypeException(String string, Throwable throwable) {
        super(string, throwable);
    }

    WrongMethodTypeException(Throwable throwable) {
        super(throwable);
    }
}

