/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;
import java.io.Writer;

public class StringWriter
extends Writer {
    private StringBuffer buf;

    public StringWriter() {
        this.buf = new StringBuffer();
        this.lock = this.buf;
    }

    public StringWriter(int n) {
        if (n >= 0) {
            this.buf = new StringBuffer(n);
            this.lock = this.buf;
            return;
        }
        throw new IllegalArgumentException("Negative buffer size");
    }

    @Override
    public StringWriter append(char c) {
        this.write(c);
        return this;
    }

    @Override
    public StringWriter append(CharSequence charSequence) {
        if (charSequence == null) {
            this.write("null");
        } else {
            this.write(charSequence.toString());
        }
        return this;
    }

    @Override
    public StringWriter append(CharSequence charSequence, int n, int n2) {
        if (charSequence == null) {
            charSequence = "null";
        }
        this.write(charSequence.subSequence(n, n2).toString());
        return this;
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public void flush() {
    }

    public StringBuffer getBuffer() {
        return this.buf;
    }

    public String toString() {
        return this.buf.toString();
    }

    @Override
    public void write(int n) {
        this.buf.append((char)n);
    }

    @Override
    public void write(String string) {
        this.buf.append(string);
    }

    @Override
    public void write(String string, int n, int n2) {
        this.buf.append(string.substring(n, n + n2));
    }

    @Override
    public void write(char[] arrc, int n, int n2) {
        if (n >= 0 && n <= arrc.length && n2 >= 0 && n + n2 <= arrc.length && n + n2 >= 0) {
            if (n2 == 0) {
                return;
            }
            this.buf.append(arrc, n, n2);
            return;
        }
        throw new IndexOutOfBoundsException();
    }
}

