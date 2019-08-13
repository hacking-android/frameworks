/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TeeInputStream
extends InputStream {
    private final InputStream input;
    private final OutputStream output;

    public TeeInputStream(InputStream inputStream, OutputStream outputStream) {
        this.input = inputStream;
        this.output = outputStream;
    }

    @Override
    public void close() throws IOException {
        this.input.close();
        this.output.close();
    }

    public OutputStream getOutputStream() {
        return this.output;
    }

    @Override
    public int read() throws IOException {
        int n = this.input.read();
        if (n >= 0) {
            this.output.write(n);
        }
        return n;
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if ((n2 = this.input.read(arrby, n, n2)) > 0) {
            this.output.write(arrby, n, n2);
        }
        return n2;
    }
}

