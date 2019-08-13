/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.GeneralSecurityException;

public class CertPathBuilderException
extends GeneralSecurityException {
    private static final long serialVersionUID = 5316471420178794402L;

    public CertPathBuilderException() {
    }

    public CertPathBuilderException(String string) {
        super(string);
    }

    public CertPathBuilderException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public CertPathBuilderException(Throwable throwable) {
        super(throwable);
    }
}

