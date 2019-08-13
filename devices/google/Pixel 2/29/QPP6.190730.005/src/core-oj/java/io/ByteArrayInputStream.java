/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;
import java.io.InputStream;

public class ByteArrayInputStream
extends InputStream {
    protected byte[] buf;
    protected int count;
    protected int mark = 0;
    protected int pos;

    public ByteArrayInputStream(byte[] arrby) {
        this.buf = arrby;
        this.pos = 0;
        this.count = arrby.length;
    }

    public ByteArrayInputStream(byte[] arrby, int n, int n2) {
        this.buf = arrby;
        this.pos = n;
        this.count = Math.min(n + n2, arrby.length);
        this.mark = n;
    }

    @Override
    public int available() {
        synchronized (this) {
            int n = this.count;
            int n2 = this.pos;
            return n - n2;
        }
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public void mark(int n) {
        this.mark = this.pos;
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public int read() {
        synchronized (this) {
            int n;
            block4 : {
                block3 : {
                    if (this.pos >= this.count) break block3;
                    byte[] arrby = this.buf;
                    n = this.pos;
                    this.pos = n + 1;
                    n = arrby[n];
                    n &= 255;
                    break block4;
                }
                n = -1;
            }
            return n;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int read(byte[] object, int n, int n2) {
        synchronized (this) {
            void var3_5;
            void var2_4;
            if (object == null) {
                NullPointerException nullPointerException = new NullPointerException();
                throw nullPointerException;
            }
            if (var2_4 >= 0 && var3_5 >= 0 && var3_5 <= ((byte[])object).length - var2_4) {
                int n3 = this.pos;
                int n4 = this.count;
                if (n3 >= n4) {
                    return -1;
                }
                n4 = this.count;
                n3 = this.pos;
                n4 -= n3;
                n3 = var3_5;
                if (var3_5 > n4) {
                    n3 = n4;
                }
                if (n3 <= 0) {
                    return 0;
                }
                System.arraycopy(this.buf, this.pos, object, (int)var2_4, n3);
                this.pos += n3;
                return n3;
            }
            IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException();
            throw indexOutOfBoundsException;
        }
    }

    @Override
    public void reset() {
        synchronized (this) {
            this.pos = this.mark;
            return;
        }
    }

    @Override
    public long skip(long l) {
        synchronized (this) {
            long l2;
            block5 : {
                long l3;
                l2 = l3 = (long)(this.count - this.pos);
                if (l >= l3) break block5;
                l2 = 0L;
                if (l < 0L) {
                    l = l2;
                }
                l2 = l;
            }
            this.pos = (int)((long)this.pos + l2);
            return l2;
        }
    }
}

