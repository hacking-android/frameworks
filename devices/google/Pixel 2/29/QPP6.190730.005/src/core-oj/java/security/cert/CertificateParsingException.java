/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.cert.CertificateException;

public class CertificateParsingException
extends CertificateException {
    private static final long serialVersionUID = -7989222416793322029L;

    public CertificateParsingException() {
    }

    public CertificateParsingException(String string) {
        super(string);
    }

    public CertificateParsingException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public CertificateParsingException(Throwable throwable) {
        super(throwable);
    }
}

