/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.exception;

import com.android.org.bouncycastle.jce.exception.ExtException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;

public class ExtCertPathValidatorException
extends CertPathValidatorException
implements ExtException {
    private Throwable cause;

    public ExtCertPathValidatorException(String string, Throwable throwable) {
        super(string);
        this.cause = throwable;
    }

    public ExtCertPathValidatorException(String string, Throwable throwable, CertPath certPath, int n) {
        super(string, throwable, certPath, n);
        this.cause = throwable;
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }
}

