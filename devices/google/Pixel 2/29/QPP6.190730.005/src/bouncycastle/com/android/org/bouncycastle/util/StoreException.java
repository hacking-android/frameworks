/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util;

public class StoreException
extends RuntimeException {
    private Throwable _e;

    public StoreException(String string, Throwable throwable) {
        super(string);
        this._e = throwable;
    }

    @Override
    public Throwable getCause() {
        return this._e;
    }
}

