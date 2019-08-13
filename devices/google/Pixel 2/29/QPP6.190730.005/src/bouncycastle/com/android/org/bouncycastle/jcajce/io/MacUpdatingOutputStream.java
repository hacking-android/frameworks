/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.io;

import java.io.IOException;
import java.io.OutputStream;
import javax.crypto.Mac;

class MacUpdatingOutputStream
extends OutputStream {
    private Mac mac;

    MacUpdatingOutputStream(Mac mac) {
        this.mac = mac;
    }

    @Override
    public void write(int n) throws IOException {
        this.mac.update((byte)n);
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.mac.update(arrby);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        this.mac.update(arrby, n, n2);
    }
}

