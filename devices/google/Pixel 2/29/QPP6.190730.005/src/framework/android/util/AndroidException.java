/*
 * Decompiled with CFR 0.145.
 */
package android.util;

public class AndroidException
extends Exception {
    public AndroidException() {
    }

    public AndroidException(Exception exception) {
        super(exception);
    }

    public AndroidException(String string2) {
        super(string2);
    }

    public AndroidException(String string2, Throwable throwable) {
        super(string2, throwable);
    }

    protected AndroidException(String string2, Throwable throwable, boolean bl, boolean bl2) {
        super(string2, throwable, bl, bl2);
    }
}

