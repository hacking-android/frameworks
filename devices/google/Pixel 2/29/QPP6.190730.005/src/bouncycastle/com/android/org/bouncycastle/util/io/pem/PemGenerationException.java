/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util.io.pem;

import java.io.IOException;

public class PemGenerationException
extends IOException {
    private Throwable cause;

    public PemGenerationException(String string) {
        super(string);
    }

    public PemGenerationException(String string, Throwable throwable) {
        super(string);
        this.cause = throwable;
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }
}

