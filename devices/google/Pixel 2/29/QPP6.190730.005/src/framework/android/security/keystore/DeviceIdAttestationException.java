/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.annotation.SystemApi;

@SystemApi
public class DeviceIdAttestationException
extends Exception {
    public DeviceIdAttestationException(String string2) {
        super(string2);
    }

    public DeviceIdAttestationException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

