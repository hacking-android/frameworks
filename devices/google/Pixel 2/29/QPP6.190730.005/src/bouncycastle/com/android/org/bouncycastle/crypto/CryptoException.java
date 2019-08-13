/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto;

public class CryptoException
extends Exception {
    private Throwable cause;

    public CryptoException() {
    }

    public CryptoException(String string) {
        super(string);
    }

    public CryptoException(String string, Throwable throwable) {
        super(string);
        this.cause = throwable;
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }
}

