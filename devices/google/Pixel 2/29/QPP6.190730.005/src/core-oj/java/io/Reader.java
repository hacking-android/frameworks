/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.Closeable;
import java.io.IOException;
import java.nio.CharBuffer;

public abstract class Reader
implements Readable,
Closeable {
    private static final int maxSkipBufferSize = 8192;
    protected Object lock;
    private char[] skipBuffer = null;

    protected Reader() {
        this.lock = this;
    }

    protected Reader(Object object) {
        if (object != null) {
            this.lock = object;
            return;
        }
        throw new NullPointerException();
    }

    @Override
    public abstract void close() throws IOException;

    public void mark(int n) throws IOException {
        throw new IOException("mark() not supported");
    }

    public boolean markSupported() {
        return false;
    }

    public int read() throws IOException {
        char[] arrc = new char[1];
        if (this.read(arrc, 0, 1) == -1) {
            return -1;
        }
        return arrc[0];
    }

    @Override
    public int read(CharBuffer charBuffer) throws IOException {
        int n = charBuffer.remaining();
        char[] arrc = new char[n];
        if ((n = this.read(arrc, 0, n)) > 0) {
            charBuffer.put(arrc, 0, n);
        }
        return n;
    }

    public int read(char[] arrc) throws IOException {
        return this.read(arrc, 0, arrc.length);
    }

    public abstract int read(char[] var1, int var2, int var3) throws IOException;

    public boolean ready() throws IOException {
        return false;
    }

    public void reset() throws IOException {
        throw new IOException("reset() not supported");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long skip(long l) throws IOException {
        if (l < 0L) {
            throw new IllegalArgumentException("skip value is negative");
        }
        int n = (int)Math.min(l, 8192L);
        Object object = this.lock;
        synchronized (object) {
            int n2;
            long l2;
            if (this.skipBuffer == null || this.skipBuffer.length < n) {
                this.skipBuffer = new char[n];
            }
            for (l2 = l; l2 > 0L && (n2 = this.read(this.skipBuffer, 0, (int)Math.min(l2, (long)n))) != -1; l2 -= (long)n2) {
            }
            return l - l2;
        }
    }
}

