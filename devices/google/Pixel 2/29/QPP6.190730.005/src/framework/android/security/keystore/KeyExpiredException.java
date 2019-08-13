/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import java.security.InvalidKeyException;

public class KeyExpiredException
extends InvalidKeyException {
    public KeyExpiredException() {
        super("Key expired");
    }

    public KeyExpiredException(String string2) {
        super(string2);
    }

    public KeyExpiredException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

