/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import java.security.InvalidKeyException;

public class UserPresenceUnavailableException
extends InvalidKeyException {
    public UserPresenceUnavailableException() {
        super("No Strong Box available.");
    }

    public UserPresenceUnavailableException(String string2) {
        super(string2);
    }

    public UserPresenceUnavailableException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

