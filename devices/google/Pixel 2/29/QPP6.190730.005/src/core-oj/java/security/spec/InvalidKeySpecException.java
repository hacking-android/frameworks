/*
 * Decompiled with CFR 0.145.
 */
package java.security.spec;

import java.security.GeneralSecurityException;

public class InvalidKeySpecException
extends GeneralSecurityException {
    private static final long serialVersionUID = 3546139293998810778L;

    public InvalidKeySpecException() {
    }

    public InvalidKeySpecException(String string) {
        super(string);
    }

    public InvalidKeySpecException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public InvalidKeySpecException(Throwable throwable) {
        super(throwable);
    }
}

