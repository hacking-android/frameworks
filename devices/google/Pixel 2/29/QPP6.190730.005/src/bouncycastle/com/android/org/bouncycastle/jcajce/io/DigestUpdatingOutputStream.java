/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.io;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;

class DigestUpdatingOutputStream
extends OutputStream {
    private MessageDigest digest;

    DigestUpdatingOutputStream(MessageDigest messageDigest) {
        this.digest = messageDigest;
    }

    @Override
    public void write(int n) throws IOException {
        this.digest.update((byte)n);
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.digest.update(arrby);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        this.digest.update(arrby, n, n2);
    }
}

