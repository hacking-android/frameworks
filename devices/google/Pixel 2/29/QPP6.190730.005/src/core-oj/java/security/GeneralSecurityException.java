/*
 * Decompiled with CFR 0.145.
 */
package java.security;

public class GeneralSecurityException
extends Exception {
    private static final long serialVersionUID = 894798122053539237L;

    public GeneralSecurityException() {
    }

    public GeneralSecurityException(String string) {
        super(string);
    }

    public GeneralSecurityException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public GeneralSecurityException(Throwable throwable) {
        super(throwable);
    }
}

