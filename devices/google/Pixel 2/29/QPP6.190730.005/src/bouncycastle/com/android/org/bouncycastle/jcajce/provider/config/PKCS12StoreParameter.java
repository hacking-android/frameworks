/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.config;

import java.io.OutputStream;
import java.security.KeyStore;

public class PKCS12StoreParameter
extends com.android.org.bouncycastle.jcajce.PKCS12StoreParameter {
    public PKCS12StoreParameter(OutputStream outputStream, KeyStore.ProtectionParameter protectionParameter) {
        super(outputStream, protectionParameter, false);
    }

    public PKCS12StoreParameter(OutputStream outputStream, KeyStore.ProtectionParameter protectionParameter, boolean bl) {
        super(outputStream, protectionParameter, bl);
    }

    public PKCS12StoreParameter(OutputStream outputStream, char[] arrc) {
        super(outputStream, arrc, false);
    }

    public PKCS12StoreParameter(OutputStream outputStream, char[] arrc, boolean bl) {
        super(outputStream, new KeyStore.PasswordProtection(arrc), bl);
    }
}

