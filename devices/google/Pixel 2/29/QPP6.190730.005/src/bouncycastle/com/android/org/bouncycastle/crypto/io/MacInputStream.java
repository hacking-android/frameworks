/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.io;

import com.android.org.bouncycastle.crypto.Mac;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MacInputStream
extends FilterInputStream {
    protected Mac mac;

    public MacInputStream(InputStream inputStream, Mac mac) {
        super(inputStream);
        this.mac = mac;
    }

    public Mac getMac() {
        return this.mac;
    }

    @Override
    public int read() throws IOException {
        int n = this.in.read();
        if (n >= 0) {
            this.mac.update((byte)n);
        }
        return n;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if ((n2 = this.in.read(arrby, n, n2)) >= 0) {
            this.mac.update(arrby, n, n2);
        }
        return n2;
    }
}

