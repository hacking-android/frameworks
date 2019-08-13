/*
 * Decompiled with CFR 0.145.
 */
package javax.security.cert;

import javax.security.cert.CertificateException;

public class CertificateNotYetValidException
extends CertificateException {
    private static final long serialVersionUID = -8976172474266822818L;

    public CertificateNotYetValidException() {
    }

    public CertificateNotYetValidException(String string) {
        super(string);
    }
}

