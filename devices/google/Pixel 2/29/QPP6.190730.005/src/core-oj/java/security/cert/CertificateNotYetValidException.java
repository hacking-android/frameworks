/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.cert.CertificateException;

public class CertificateNotYetValidException
extends CertificateException {
    static final long serialVersionUID = 4355919900041064702L;

    public CertificateNotYetValidException() {
    }

    public CertificateNotYetValidException(String string) {
        super(string);
    }
}

