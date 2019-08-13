/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PushbackInputStream
extends FilterInputStream {
    protected byte[] buf;
    protected int pos;

    public PushbackInputStream(InputStream inputStream) {
        this(inputStream, 1);
    }

    public PushbackInputStream(InputStream inputStream, int n) {
        super(inputStream);
        if (n > 0) {
            this.buf = new byte[n];
            this.pos = n;
            return;
        }
        throw new IllegalArgumentException("size <= 0");
    }

    private void ensureOpen() throws IOException {
        if (this.in != null) {
            return;
        }
        throw new IOException("Stream closed");
    }

    @Override
    public int available() throws IOException {
        this.ensureOpen();
        int n = this.buf.length - this.pos;
        int n2 = super.available();
        int n3 = Integer.MAX_VALUE;
        if (n <= Integer.MAX_VALUE - n2) {
            n3 = n + n2;
        }
        return n3;
    }

    @Override
    public void close() throws IOException {
        synchronized (this) {
            block4 : {
                InputStream inputStream = this.in;
                if (inputStream != null) break block4;
                return;
            }
            this.in.close();
            this.in = null;
            this.buf = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void mark(int n) {
        // MONITORENTER : this
        // MONITOREXIT : this
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public int read() throws IOException {
        this.ensureOpen();
        int n = this.pos;
        byte[] arrby = this.buf;
        if (n < arrby.length) {
            this.pos = n + 1;
            return arrby[n] & 255;
        }
        return super.read();
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        this.ensureOpen();
        if (arrby != null) {
            if (n >= 0 && n2 >= 0 && n2 <= arrby.length - n) {
                int n3;
                if (n2 == 0) {
                    return 0;
                }
                int n4 = n3 = this.buf.length - this.pos;
                int n5 = n;
                int n6 = n2;
                if (n3 > 0) {
                    n4 = n3;
                    if (n2 < n3) {
                        n4 = n2;
                    }
                    System.arraycopy(this.buf, this.pos, arrby, n, n4);
                    this.pos += n4;
                    n5 = n + n4;
                    n6 = n2 - n4;
                }
                if (n6 > 0) {
                    n2 = super.read(arrby, n5, n6);
                    n = -1;
                    if (n2 == -1) {
                        if (n4 == 0) {
                            n4 = n;
                        }
                        return n4;
                    }
                    return n4 + n2;
                }
                return n4;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new NullPointerException();
    }

    @Override
    public void reset() throws IOException {
        synchronized (this) {
            IOException iOException = new IOException("mark/reset not supported");
            throw iOException;
        }
    }

    @Override
    public long skip(long l) throws IOException {
        long l2;
        this.ensureOpen();
        if (l <= 0L) {
            return 0L;
        }
        long l3 = l2 = (long)(this.buf.length - this.pos);
        long l4 = l;
        if (l2 > 0L) {
            l3 = l2;
            if (l < l2) {
                l3 = l;
            }
            this.pos = (int)((long)this.pos + l3);
            l4 = l - l3;
        }
        l = l3;
        if (l4 > 0L) {
            l = l3 + super.skip(l4);
        }
        return l;
    }

    public void unread(int n) throws IOException {
        this.ensureOpen();
        int n2 = this.pos;
        if (n2 != 0) {
            byte[] arrby = this.buf;
            this.pos = --n2;
            arrby[n2] = (byte)n;
            return;
        }
        throw new IOException("Push back buffer is full");
    }

    public void unread(byte[] arrby) throws IOException {
        this.unread(arrby, 0, arrby.length);
    }

    public void unread(byte[] arrby, int n, int n2) throws IOException {
        this.ensureOpen();
        int n3 = this.pos;
        if (n2 <= n3) {
            this.pos = n3 - n2;
            System.arraycopy(arrby, n, this.buf, this.pos, n2);
            return;
        }
        throw new IOException("Push back buffer is full");
    }
}

