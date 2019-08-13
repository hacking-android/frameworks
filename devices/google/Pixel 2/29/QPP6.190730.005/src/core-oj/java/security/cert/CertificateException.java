/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.GeneralSecurityException;

public class CertificateException
extends GeneralSecurityException {
    private static final long serialVersionUID = 3192535253797119798L;

    public CertificateException() {
    }

    public CertificateException(String string) {
        super(string);
    }

    public CertificateException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public CertificateException(Throwable throwable) {
        super(throwable);
    }
}

