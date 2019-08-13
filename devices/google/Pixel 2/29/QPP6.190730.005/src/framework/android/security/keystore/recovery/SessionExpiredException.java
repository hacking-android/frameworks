/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore.recovery;

import android.annotation.SystemApi;
import java.security.GeneralSecurityException;

@SystemApi
public class SessionExpiredException
extends GeneralSecurityException {
    public SessionExpiredException(String string2) {
        super(string2);
    }
}

