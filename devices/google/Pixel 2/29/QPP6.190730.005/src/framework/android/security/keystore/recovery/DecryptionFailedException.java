/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore.recovery;

import android.annotation.SystemApi;
import java.security.GeneralSecurityException;

@SystemApi
public class DecryptionFailedException
extends GeneralSecurityException {
    public DecryptionFailedException(String string2) {
        super(string2);
    }
}

