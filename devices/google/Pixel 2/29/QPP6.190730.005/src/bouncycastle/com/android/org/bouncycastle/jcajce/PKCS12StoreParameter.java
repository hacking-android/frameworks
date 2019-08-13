/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce;

import java.io.OutputStream;
import java.security.KeyStore;

public class PKCS12StoreParameter
implements KeyStore.LoadStoreParameter {
    private final boolean forDEREncoding;
    private final OutputStream out;
    private final KeyStore.ProtectionParameter protectionParameter;

    public PKCS12StoreParameter(OutputStream outputStream, KeyStore.ProtectionParameter protectionParameter) {
        this(outputStream, protectionParameter, false);
    }

    public PKCS12StoreParameter(OutputStream outputStream, KeyStore.ProtectionParameter protectionParameter, boolean bl) {
        this.out = outputStream;
        this.protectionParameter = protectionParameter;
        this.forDEREncoding = bl;
    }

    public PKCS12StoreParameter(OutputStream outputStream, char[] arrc) {
        this(outputStream, arrc, false);
    }

    public PKCS12StoreParameter(OutputStream outputStream, char[] arrc, boolean bl) {
        this(outputStream, new KeyStore.PasswordProtection(arrc), bl);
    }

    public OutputStream getOutputStream() {
        return this.out;
    }

    @Override
    public KeyStore.ProtectionParameter getProtectionParameter() {
        return this.protectionParameter;
    }

    public boolean isForDEREncoding() {
        return this.forDEREncoding;
    }
}

