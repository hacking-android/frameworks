/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.GeneralSecurityException;

public class KeyException
extends GeneralSecurityException {
    private static final long serialVersionUID = -7483676942812432108L;

    public KeyException() {
    }

    public KeyException(String string) {
        super(string);
    }

    public KeyException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public KeyException(Throwable throwable) {
        super(throwable);
    }
}

