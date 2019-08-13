/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.KeyException;

public class InvalidKeyException
extends KeyException {
    private static final long serialVersionUID = 5698479920593359816L;

    public InvalidKeyException() {
    }

    public InvalidKeyException(String string) {
        super(string);
    }

    public InvalidKeyException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public InvalidKeyException(Throwable throwable) {
        super(throwable);
    }
}

