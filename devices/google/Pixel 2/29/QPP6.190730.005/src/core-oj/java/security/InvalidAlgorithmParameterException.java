/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.GeneralSecurityException;

public class InvalidAlgorithmParameterException
extends GeneralSecurityException {
    private static final long serialVersionUID = 2864672297499471472L;

    public InvalidAlgorithmParameterException() {
    }

    public InvalidAlgorithmParameterException(String string) {
        super(string);
    }

    public InvalidAlgorithmParameterException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public InvalidAlgorithmParameterException(Throwable throwable) {
        super(throwable);
    }
}

