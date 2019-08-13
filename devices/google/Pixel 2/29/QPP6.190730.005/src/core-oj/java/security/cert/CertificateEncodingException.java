/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.cert.CertificateException;

public class CertificateEncodingException
extends CertificateException {
    private static final long serialVersionUID = 6219492851589449162L;

    public CertificateEncodingException() {
    }

    public CertificateEncodingException(String string) {
        super(string);
    }

    public CertificateEncodingException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public CertificateEncodingException(Throwable throwable) {
        super(throwable);
    }
}

