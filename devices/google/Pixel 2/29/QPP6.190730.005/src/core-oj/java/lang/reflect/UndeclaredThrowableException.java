/*
 * Decompiled with CFR 0.145.
 */
package java.lang.reflect;

public class UndeclaredThrowableException
extends RuntimeException {
    static final long serialVersionUID = 330127114055056639L;
    private Throwable undeclaredThrowable;

    public UndeclaredThrowableException(Throwable throwable) {
        super((Throwable)null);
        this.undeclaredThrowable = throwable;
    }

    public UndeclaredThrowableException(Throwable throwable, String string) {
        super(string, null);
        this.undeclaredThrowable = throwable;
    }

    @Override
    public Throwable getCause() {
        return this.undeclaredThrowable;
    }

    public Throwable getUndeclaredThrowable() {
        return this.undeclaredThrowable;
    }
}

