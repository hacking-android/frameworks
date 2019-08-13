/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import java.security.InvalidKeyException;

public class KeyPermanentlyInvalidatedException
extends InvalidKeyException {
    public KeyPermanentlyInvalidatedException() {
        super("Key permanently invalidated");
    }

    public KeyPermanentlyInvalidatedException(String string2) {
        super(string2);
    }

    public KeyPermanentlyInvalidatedException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

