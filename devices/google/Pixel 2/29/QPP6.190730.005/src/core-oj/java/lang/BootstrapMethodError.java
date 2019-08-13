/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

public class BootstrapMethodError
extends LinkageError {
    private static final long serialVersionUID = 292L;

    public BootstrapMethodError() {
    }

    public BootstrapMethodError(String string) {
        super(string);
    }

    public BootstrapMethodError(String string, Throwable throwable) {
        super(string, throwable);
    }

    public BootstrapMethodError(Throwable throwable) {
        String string = throwable == null ? null : throwable.toString();
        super(string);
        this.initCause(throwable);
    }
}

