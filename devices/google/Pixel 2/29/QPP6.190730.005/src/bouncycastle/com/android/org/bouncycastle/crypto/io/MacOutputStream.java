/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.io;

import com.android.org.bouncycastle.crypto.Mac;
import java.io.IOException;
import java.io.OutputStream;

public class MacOutputStream
extends OutputStream {
    protected Mac mac;

    public MacOutputStream(Mac mac) {
        this.mac = mac;
    }

    public byte[] getMac() {
        byte[] arrby = new byte[this.mac.getMacSize()];
        this.mac.doFinal(arrby, 0);
        return arrby;
    }

    @Override
    public void write(int n) throws IOException {
        this.mac.update((byte)n);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        this.mac.update(arrby, n, n2);
    }
}

