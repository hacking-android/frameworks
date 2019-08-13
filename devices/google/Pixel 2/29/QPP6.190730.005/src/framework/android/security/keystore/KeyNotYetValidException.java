/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import java.security.InvalidKeyException;

public class KeyNotYetValidException
extends InvalidKeyException {
    public KeyNotYetValidException() {
        super("Key not yet valid");
    }

    public KeyNotYetValidException(String string2) {
        super(string2);
    }

    public KeyNotYetValidException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

