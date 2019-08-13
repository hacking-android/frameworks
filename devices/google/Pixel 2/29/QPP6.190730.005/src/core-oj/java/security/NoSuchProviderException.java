/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.GeneralSecurityException;

public class NoSuchProviderException
extends GeneralSecurityException {
    private static final long serialVersionUID = 8488111756688534474L;

    public NoSuchProviderException() {
    }

    public NoSuchProviderException(String string) {
        super(string);
    }
}

