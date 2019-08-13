/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;
import java.io.Reader;

public class CharArrayReader
extends Reader {
    protected char[] buf;
    protected int count;
    protected int markedPos = 0;
    protected int pos;

    public CharArrayReader(char[] arrc) {
        this.buf = arrc;
        this.pos = 0;
        this.count = arrc.length;
    }

    public CharArrayReader(char[] arrc, int n, int n2) {
        if (n >= 0 && n <= arrc.length && n2 >= 0 && n + n2 >= 0) {
            this.buf = arrc;
            this.pos = n;
            this.count = Math.min(n + n2, arrc.length);
            this.markedPos = n;
            return;
        }
        throw new IllegalArgumentException();
    }

    private void ensureOpen() throws IOException {
        if (this.buf != null) {
            return;
        }
        throw new IOException("Stream closed");
    }

    @Override
    public void close() {
        this.buf = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void mark(int n) throws IOException {
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            this.markedPos = this.pos;
            return;
        }
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int read() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            if (this.pos >= this.count) {
                return -1;
            }
            char[] arrc = this.buf;
            int n = this.pos;
            this.pos = n + 1;
            return arrc[n];
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int read(char[] object, int n, int n2) throws IOException {
        Object object2 = this.lock;
        synchronized (object2) {
            this.ensureOpen();
            if (n >= 0 && n <= ((char[])object).length && n2 >= 0 && n + n2 <= ((char[])object).length && n + n2 >= 0) {
                if (n2 == 0) {
                    return 0;
                }
                if (this.pos >= this.count) {
                    return -1;
                }
                int n3 = this.count - this.pos;
                int n4 = n2;
                if (n2 > n3) {
                    n4 = n3;
                }
                if (n4 <= 0) {
                    return 0;
                }
                System.arraycopy((Object)this.buf, this.pos, object, n, n4);
                this.pos += n4;
                return n4;
            }
            object = new IndexOutOfBoundsException();
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean ready() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            if (this.count - this.pos <= 0) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void reset() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            this.pos = this.markedPos;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public long skip(long l) throws IOException {
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            long l2 = this.count - this.pos;
            long l3 = l;
            if (l > l2) {
                l3 = l2;
            }
            if (l3 < 0L) {
                return 0L;
            }
            this.pos = (int)((long)this.pos + l3);
            return l3;
        }
    }
}

