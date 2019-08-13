/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import java.security.InvalidKeyException;

public class UserNotAuthenticatedException
extends InvalidKeyException {
    public UserNotAuthenticatedException() {
        super("User not authenticated");
    }

    public UserNotAuthenticatedException(String string2) {
        super(string2);
    }

    public UserNotAuthenticatedException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

