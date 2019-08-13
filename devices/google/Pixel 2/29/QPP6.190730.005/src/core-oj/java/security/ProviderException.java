/*
 * Decompiled with CFR 0.145.
 */
package java.security;

public class ProviderException
extends RuntimeException {
    private static final long serialVersionUID = 5256023526693665674L;

    public ProviderException() {
    }

    public ProviderException(String string) {
        super(string);
    }

    public ProviderException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public ProviderException(Throwable throwable) {
        super(throwable);
    }
}

