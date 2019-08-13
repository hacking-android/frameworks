/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.GeneralSecurityException;

public class CertStoreException
extends GeneralSecurityException {
    private static final long serialVersionUID = 2395296107471573245L;

    public CertStoreException() {
    }

    public CertStoreException(String string) {
        super(string);
    }

    public CertStoreException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public CertStoreException(Throwable throwable) {
        super(throwable);
    }
}

