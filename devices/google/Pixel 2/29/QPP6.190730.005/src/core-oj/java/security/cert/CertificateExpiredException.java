/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.cert.CertificateException;

public class CertificateExpiredException
extends CertificateException {
    private static final long serialVersionUID = 9071001339691533771L;

    public CertificateExpiredException() {
    }

    public CertificateExpiredException(String string) {
        super(string);
    }
}

