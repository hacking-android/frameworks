/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

public class CharArrayWriter
extends Writer {
    protected char[] buf;
    protected int count;

    public CharArrayWriter() {
        this(32);
    }

    public CharArrayWriter(int n) {
        if (n >= 0) {
            this.buf = new char[n];
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Negative initial size: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public CharArrayWriter append(char c) {
        this.write(c);
        return this;
    }

    @Override
    public CharArrayWriter append(CharSequence charSequence) {
        charSequence = charSequence == null ? "null" : charSequence.toString();
        this.write((String)charSequence, 0, ((String)charSequence).length());
        return this;
    }

    @Override
    public CharArrayWriter append(CharSequence charSequence, int n, int n2) {
        if (charSequence == null) {
            charSequence = "null";
        }
        charSequence = charSequence.subSequence(n, n2).toString();
        this.write((String)charSequence, 0, ((String)charSequence).length());
        return this;
    }

    @Override
    public void close() {
    }

    @Override
    public void flush() {
    }

    public void reset() {
        this.count = 0;
    }

    public int size() {
        return this.count;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public char[] toCharArray() {
        Object object = this.lock;
        synchronized (object) {
            return Arrays.copyOf(this.buf, this.count);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString() {
        Object object = this.lock;
        synchronized (object) {
            return new String(this.buf, 0, this.count);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void write(int n) {
        Object object = this.lock;
        synchronized (object) {
            int n2 = this.count + 1;
            if (n2 > this.buf.length) {
                this.buf = Arrays.copyOf(this.buf, Math.max(this.buf.length << 1, n2));
            }
            this.buf[this.count] = (char)n;
            this.count = n2;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void write(String string, int n, int n2) {
        Object object = this.lock;
        synchronized (object) {
            int n3 = this.count + n2;
            if (n3 > this.buf.length) {
                this.buf = Arrays.copyOf(this.buf, Math.max(this.buf.length << 1, n3));
            }
            string.getChars(n, n + n2, this.buf, this.count);
            this.count = n3;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void write(char[] arrc, int n, int n2) {
        if (n >= 0 && n <= arrc.length && n2 >= 0 && n + n2 <= arrc.length && n + n2 >= 0) {
            if (n2 == 0) {
                return;
            }
            Object object = this.lock;
            synchronized (object) {
                int n3 = this.count + n2;
                if (n3 > this.buf.length) {
                    this.buf = Arrays.copyOf(this.buf, Math.max(this.buf.length << 1, n3));
                }
                System.arraycopy((Object)arrc, n, (Object)this.buf, this.count, n2);
                this.count = n3;
                return;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void writeTo(Writer writer) throws IOException {
        Object object = this.lock;
        synchronized (object) {
            writer.write(this.buf, 0, this.count);
            return;
        }
    }
}

