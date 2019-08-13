/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

public class RuntimeException
extends Exception {
    static final long serialVersionUID = -7034897190745766939L;

    public RuntimeException() {
    }

    public RuntimeException(String string) {
        super(string);
    }

    public RuntimeException(String string, Throwable throwable) {
        super(string, throwable);
    }

    protected RuntimeException(String string, Throwable throwable, boolean bl, boolean bl2) {
        super(string, throwable, bl, bl2);
    }

    public RuntimeException(Throwable throwable) {
        super(throwable);
    }
}

