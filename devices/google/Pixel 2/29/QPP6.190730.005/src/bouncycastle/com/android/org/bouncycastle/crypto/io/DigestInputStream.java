/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.io;

import com.android.org.bouncycastle.crypto.Digest;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DigestInputStream
extends FilterInputStream {
    protected Digest digest;

    public DigestInputStream(InputStream inputStream, Digest digest) {
        super(inputStream);
        this.digest = digest;
    }

    public Digest getDigest() {
        return this.digest;
    }

    @Override
    public int read() throws IOException {
        int n = this.in.read();
        if (n >= 0) {
            this.digest.update((byte)n);
        }
        return n;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if ((n2 = this.in.read(arrby, n, n2)) > 0) {
            this.digest.update(arrby, n, n2);
        }
        return n2;
    }
}

