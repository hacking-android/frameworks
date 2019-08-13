/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.security.GeneralSecurityException;

public class BadPaddingException
extends GeneralSecurityException {
    private static final long serialVersionUID = -5315033893984728443L;

    public BadPaddingException() {
    }

    public BadPaddingException(String string) {
        super(string);
    }
}

