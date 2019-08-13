/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.exception;

import com.android.org.bouncycastle.jce.exception.ExtException;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderException;

public class ExtCertPathBuilderException
extends CertPathBuilderException
implements ExtException {
    private Throwable cause;

    public ExtCertPathBuilderException(String string, Throwable throwable) {
        super(string);
        this.cause = throwable;
    }

    public ExtCertPathBuilderException(String string, Throwable throwable, CertPath certPath, int n) {
        super(string, throwable);
        this.cause = throwable;
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }
}

