/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;
import java.io.Reader;

public class StringReader
extends Reader {
    private int length;
    private int mark = 0;
    private int next = 0;
    private String str;

    public StringReader(String string) {
        this.str = string;
        this.length = string.length();
    }

    private void ensureOpen() throws IOException {
        if (this.str != null) {
            return;
        }
        throw new IOException("Stream closed");
    }

    @Override
    public void close() {
        this.str = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void mark(int n) throws IOException {
        if (n >= 0) {
            Object object = this.lock;
            synchronized (object) {
                this.ensureOpen();
                this.mark = this.next;
                return;
            }
        }
        throw new IllegalArgumentException("Read-ahead limit < 0");
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
            if (this.next >= this.length) {
                return -1;
            }
            String string = this.str;
            int n = this.next;
            this.next = n + 1;
            return string.charAt(n);
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
                if (this.next >= this.length) {
                    return -1;
                }
                n2 = Math.min(this.length - this.next, n2);
                this.str.getChars(this.next, this.next + n2, (char[])object, n);
                this.next += n2;
                return n2;
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
            this.next = this.mark;
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
            if (this.next >= this.length) {
                return 0L;
            }
            l = Math.min((long)(this.length - this.next), l);
            l = Math.max((long)(-this.next), l);
            this.next = (int)((long)this.next + l);
            return l;
        }
    }
}

