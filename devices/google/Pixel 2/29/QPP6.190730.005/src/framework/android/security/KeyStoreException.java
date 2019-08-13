/*
 * Decompiled with CFR 0.145.
 */
package android.security;

public class KeyStoreException
extends Exception {
    private final int mErrorCode;

    public KeyStoreException(int n, String string2) {
        super(string2);
        this.mErrorCode = n;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }
}

