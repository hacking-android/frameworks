/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.io;

import com.android.org.bouncycastle.crypto.Digest;
import java.io.IOException;
import java.io.OutputStream;

public class DigestOutputStream
extends OutputStream {
    protected Digest digest;

    public DigestOutputStream(Digest digest) {
        this.digest = digest;
    }

    public byte[] getDigest() {
        byte[] arrby = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(arrby, 0);
        return arrby;
    }

    @Override
    public void write(int n) throws IOException {
        this.digest.update((byte)n);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        this.digest.update(arrby, n, n2);
    }
}

