/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;
import java.io.Reader;

public abstract class FilterReader
extends Reader {
    protected Reader in;

    protected FilterReader(Reader reader) {
        super(reader);
        this.in = reader;
    }

    @Override
    public void close() throws IOException {
        this.in.close();
    }

    @Override
    public void mark(int n) throws IOException {
        this.in.mark(n);
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
    public int read(char[] arrc, int n, int n2) throws IOException {
        return this.in.read(arrc, n, n2);
    }

    @Override
    public boolean ready() throws IOException {
        return this.in.ready();
    }

    @Override
    public void reset() throws IOException {
        this.in.reset();
    }

    @Override
    public long skip(long l) throws IOException {
        return this.in.skip(l);
    }
}

