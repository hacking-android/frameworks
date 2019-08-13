/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.GeneralSecurityException;

public class NoSuchAlgorithmException
extends GeneralSecurityException {
    private static final long serialVersionUID = -7443947487218346562L;

    public NoSuchAlgorithmException() {
    }

    public NoSuchAlgorithmException(String string) {
        super(string);
    }

    public NoSuchAlgorithmException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public NoSuchAlgorithmException(Throwable throwable) {
        super(throwable);
    }
}

