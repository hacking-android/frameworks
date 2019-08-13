/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore.recovery;

import android.annotation.SystemApi;
import java.security.GeneralSecurityException;

@SystemApi
public class InternalRecoveryServiceException
extends GeneralSecurityException {
    public InternalRecoveryServiceException(String string2) {
        super(string2);
    }

    public InternalRecoveryServiceException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

