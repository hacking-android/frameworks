/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.GeneralSecurityException;

public class KeyStoreException
extends GeneralSecurityException {
    private static final long serialVersionUID = -1119353179322377262L;

    public KeyStoreException() {
    }

    public KeyStoreException(String string) {
        super(string);
    }

    public KeyStoreException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public KeyStoreException(Throwable throwable) {
        super(throwable);
    }
}

