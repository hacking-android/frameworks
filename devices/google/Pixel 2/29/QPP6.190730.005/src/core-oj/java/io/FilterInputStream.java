/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;
import java.io.InputStream;

public class FilterInputStream
extends InputStream {
    protected volatile InputStream in;

    protected FilterInputStream(InputStream inputStream) {
        this.in = inputStream;
    }

    @Override
    public int available() throws IOException {
        return this.in.available();
    }

    @Override
    public void close() throws IOException {
        this.in.close();
    }

    @Override
    public void mark(int n) {
        synchronized (this) {
            this.in.mark(n);
            return;
        }
    }

    @Override
    public boolean markSupported() {
        return this.in.markSupported();
    }

    @Override
    public int read() throws IOException {
        return this.in.read();
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        return this.in.read(arrby, n, n2);
    }

    @Override
    public void reset() throws IOException {
        synchronized (this) {
            this.in.reset();
            return;
        }
    }

    @Override
    public long skip(long l) throws IOException {
        return this.in.skip(l);
    }
}

