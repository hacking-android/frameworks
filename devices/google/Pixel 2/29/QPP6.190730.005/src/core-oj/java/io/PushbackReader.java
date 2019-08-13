/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public class PushbackReader
extends FilterReader {
    private char[] buf;
    private int pos;

    public PushbackReader(Reader reader) {
        this(reader, 1);
    }

    public PushbackReader(Reader reader, int n) {
        super(reader);
        if (n > 0) {
            this.buf = new char[n];
            this.pos = n;
            return;
        }
        throw new IllegalArgumentException("size <= 0");
    }

    private void ensureOpen() throws IOException {
        if (this.buf != null) {
            return;
        }
        throw new IOException("Stream closed");
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.buf = null;
    }

    @Override
    public void mark(int n) throws IOException {
        throw new IOException("mark/reset not supported");
    }

    @Override
    public boolean markSupported() {
        return false;
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
            if (this.pos >= this.buf.length) return super.read();
            char[] arrc = this.buf;
            int n = this.pos;
            this.pos = n + 1;
            return arrc[n];
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public int read(char[] var1_1, int var2_4, int var3_5) throws IOException {
        block12 : {
            block13 : {
                block11 : {
                    var4_6 = this.lock;
                    // MONITORENTER : var4_6
                    this.ensureOpen();
                    if (var3_5 > 0) ** GOTO lbl18
                    if (var3_5 < 0) ** GOTO lbl16
                    if (var2_4 >= 0) {
                        var3_5 = var1_1 /* !! */ .length;
                        if (var2_4 > var3_5) break block11;
                        return 0;
                    }
                }
                var1_1 /* !! */  = new IndexOutOfBoundsException();
                throw var1_1 /* !! */ ;
lbl16: // 1 sources:
                var1_1 /* !! */  = new IndexOutOfBoundsException();
                throw var1_1 /* !! */ ;
lbl18: // 1 sources:
                var6_8 = var5_7 = this.buf.length - this.pos;
                var7_9 = var2_4;
                var8_10 = var3_5;
                if (var5_7 > 0) {
                    var6_8 = var5_7;
                    if (var3_5 < var5_7) {
                        var6_8 = var3_5;
                    }
                    System.arraycopy((Object)this.buf, this.pos, (Object)var1_1 /* !! */ , var2_4, var6_8);
                    this.pos += var6_8;
                    var7_9 = var2_4 + var6_8;
                    var8_10 = var3_5 - var6_8;
                }
                if (var8_10 <= 0) break block12;
                try {
                    var3_5 = super.read(var1_1 /* !! */ , var7_9, var8_10);
                    var2_4 = -1;
                    if (var3_5 != -1) break block13;
                    if (var6_8 == 0) {
                        return var2_4;
                    }
                    var2_4 = var6_8;
                }
                catch (ArrayIndexOutOfBoundsException var1_2) {
                    var1_3 = new IndexOutOfBoundsException();
                    throw var1_3;
                }
                return var2_4;
            }
            // MONITOREXIT : var4_6
            return var6_8 + var3_5;
        }
        // MONITOREXIT : var4_6
        return var6_8;
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
            if (this.pos < this.buf.length) return true;
            if (!super.ready()) return false;
            return true;
        }
    }

    @Override
    public void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public long skip(long l) throws IOException {
        if (l < 0L) {
            throw new IllegalArgumentException("skip value is negative");
        }
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            int n = this.buf.length - this.pos;
            long l2 = l;
            if (n > 0) {
                if (l <= (long)n) {
                    this.pos = (int)((long)this.pos + l);
                    return l;
                }
                this.pos = this.buf.length;
                l2 = l - (long)n;
            }
            l = n;
            l2 = super.skip(l2);
            return l + l2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unread(int n) throws IOException {
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            if (this.pos != 0) {
                int n2;
                char[] arrc = this.buf;
                this.pos = n2 = this.pos - 1;
                arrc[n2] = (char)n;
                return;
            }
            IOException iOException = new IOException("Pushback buffer overflow");
            throw iOException;
        }
    }

    public void unread(char[] arrc) throws IOException {
        this.unread(arrc, 0, arrc.length);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unread(char[] object, int n, int n2) throws IOException {
        Object object2 = this.lock;
        synchronized (object2) {
            this.ensureOpen();
            if (n2 <= this.pos) {
                this.pos -= n2;
                System.arraycopy(object, n, (Object)this.buf, this.pos, n2);
                return;
            }
            object = new IOException("Pushback buffer overflow");
            throw object;
        }
    }
}

