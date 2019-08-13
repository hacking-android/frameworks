/*
 * Decompiled with CFR 0.145.
 */
package android.util;

public class AndroidRuntimeException
extends RuntimeException {
    public AndroidRuntimeException() {
    }

    public AndroidRuntimeException(Exception exception) {
        super(exception);
    }

    public AndroidRuntimeException(String string2) {
        super(string2);
    }

    public AndroidRuntimeException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

