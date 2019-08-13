/*
 * Decompiled with CFR 0.145.
 */
package android.security;

public class KeyChainException
extends Exception {
    public KeyChainException() {
    }

    public KeyChainException(String string2) {
        super(string2);
    }

    public KeyChainException(String string2, Throwable throwable) {
        super(string2, throwable);
    }

    public KeyChainException(Throwable throwable) {
        String string2 = throwable == null ? null : throwable.toString();
        super(string2, throwable);
    }
}

