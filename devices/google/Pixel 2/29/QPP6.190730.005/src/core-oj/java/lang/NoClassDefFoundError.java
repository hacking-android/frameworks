/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

public class NoClassDefFoundError
extends LinkageError {
    private static final long serialVersionUID = 9095859863287012458L;

    public NoClassDefFoundError() {
    }

    public NoClassDefFoundError(String string) {
        super(string);
    }

    private NoClassDefFoundError(String string, Throwable throwable) {
        super(string, throwable);
    }
}

