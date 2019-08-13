/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

public class ASN1ParsingException
extends IllegalStateException {
    private Throwable cause;

    public ASN1ParsingException(String string) {
        super(string);
    }

    public ASN1ParsingException(String string, Throwable throwable) {
        super(string);
        this.cause = throwable;
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }
}

