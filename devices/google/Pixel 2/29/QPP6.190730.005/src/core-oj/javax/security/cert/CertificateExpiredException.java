/*
 * Decompiled with CFR 0.145.
 */
package javax.security.cert;

import javax.security.cert.CertificateException;

public class CertificateExpiredException
extends CertificateException {
    private static final long serialVersionUID = 5091601212177261883L;

    public CertificateExpiredException() {
    }

    public CertificateExpiredException(String string) {
        super(string);
    }
}

