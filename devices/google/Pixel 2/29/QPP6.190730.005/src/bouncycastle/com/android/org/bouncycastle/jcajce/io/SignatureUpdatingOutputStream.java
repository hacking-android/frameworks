/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.io;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Signature;
import java.security.SignatureException;

class SignatureUpdatingOutputStream
extends OutputStream {
    private Signature sig;

    SignatureUpdatingOutputStream(Signature signature) {
        this.sig = signature;
    }

    @Override
    public void write(int n) throws IOException {
        try {
            this.sig.update((byte)n);
            return;
        }
        catch (SignatureException signatureException) {
            throw new IOException(signatureException.getMessage());
        }
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        try {
            this.sig.update(arrby);
            return;
        }
        catch (SignatureException signatureException) {
            throw new IOException(signatureException.getMessage());
        }
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        try {
            this.sig.update(arrby, n, n2);
            return;
        }
        catch (SignatureException signatureException) {
            throw new IOException(signatureException.getMessage());
        }
    }
}

