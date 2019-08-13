/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util.io;

import java.io.IOException;
import java.io.OutputStream;

public class TeeOutputStream
extends OutputStream {
    private OutputStream output1;
    private OutputStream output2;

    public TeeOutputStream(OutputStream outputStream, OutputStream outputStream2) {
        this.output1 = outputStream;
        this.output2 = outputStream2;
    }

    @Override
    public void close() throws IOException {
        this.output1.close();
        this.output2.close();
    }

    @Override
    public void flush() throws IOException {
        this.output1.flush();
        this.output2.flush();
    }

    @Override
    public void write(int n) throws IOException {
        this.output1.write(n);
        this.output2.write(n);
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.output1.write(arrby);
        this.output2.write(arrby);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        this.output1.write(arrby, n, n2);
        this.output2.write(arrby, n, n2);
    }
}

