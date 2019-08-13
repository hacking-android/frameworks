/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.InputStream;

@Deprecated
public class StringBufferInputStream
extends InputStream {
    protected String buffer;
    protected int count;
    protected int pos;

    public StringBufferInputStream(String string) {
        this.buffer = string;
        this.count = string.length();
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
    public int read() {
        synchronized (this) {
            int n;
            block4 : {
                block3 : {
                    if (this.pos >= this.count) break block3;
                    String string = this.buffer;
                    n = this.pos;
                    this.pos = n + 1;
                    n = string.charAt(n);
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
            void var2_4;
            int n3;
            if (object == null) {
                NullPointerException nullPointerException = new NullPointerException();
                throw nullPointerException;
            }
            if (var2_4 >= 0 && var2_4 <= ((byte[])object).length && n3 >= 0 && var2_4 + n3 <= ((byte[])object).length && var2_4 + n3 >= 0) {
                int n4 = this.pos;
                int n5 = this.count;
                if (n4 >= n5) {
                    return -1;
                }
                n5 = this.count;
                n4 = this.pos;
                n4 = n5 - n4;
                n5 = n3;
                if (n3 > n4) {
                    n5 = n4;
                }
                if (n5 <= 0) {
                    return 0;
                }
                String string = this.buffer;
                n3 = n5;
                do {
                    if (--n3 < 0) {
                        return n5;
                    }
                    n4 = this.pos;
                    this.pos = n4 + 1;
                    object[var2_4] = (byte)string.charAt(n4);
                    ++var2_4;
                } while (true);
            }
            IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException();
            throw indexOutOfBoundsException;
        }
    }

    @Override
    public void reset() {
        synchronized (this) {
            this.pos = 0;
            return;
        }
    }

    @Override
    public long skip(long l) {
        synchronized (this) {
            if (l < 0L) {
                return 0L;
            }
            long l2 = l;
            if (l > (long)(this.count - this.pos)) {
                l2 = this.count - this.pos;
            }
            this.pos = (int)((long)this.pos + l2);
            return l2;
        }
    }
}

