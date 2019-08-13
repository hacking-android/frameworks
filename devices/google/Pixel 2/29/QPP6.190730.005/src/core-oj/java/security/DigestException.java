/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.GeneralSecurityException;

public class DigestException
extends GeneralSecurityException {
    private static final long serialVersionUID = 5821450303093652515L;

    public DigestException() {
    }

    public DigestException(String string) {
        super(string);
    }

    public DigestException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public DigestException(Throwable throwable) {
        super(throwable);
    }
}

