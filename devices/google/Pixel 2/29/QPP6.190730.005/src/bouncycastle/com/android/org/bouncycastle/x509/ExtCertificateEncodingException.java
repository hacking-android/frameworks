/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.x509;

import java.security.cert.CertificateEncodingException;

class ExtCertificateEncodingException
extends CertificateEncodingException {
    Throwable cause;

    ExtCertificateEncodingException(String string, Throwable throwable) {
        super(string);
        this.cause = throwable;
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }
}

