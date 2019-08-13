/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.GeneralSecurityException;

public class SignatureException
extends GeneralSecurityException {
    private static final long serialVersionUID = 7509989324975124438L;

    public SignatureException() {
    }

    public SignatureException(String string) {
        super(string);
    }

    public SignatureException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SignatureException(Throwable throwable) {
        super(throwable);
    }
}

