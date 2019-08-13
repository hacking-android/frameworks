/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util.encoders;

public class DecoderException
extends IllegalStateException {
    private Throwable cause;

    DecoderException(String string, Throwable throwable) {
        super(string);
        this.cause = throwable;
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }
}

