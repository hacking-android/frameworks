/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

public abstract class Writer
implements Appendable,
Closeable,
Flushable {
    private static final int WRITE_BUFFER_SIZE = 1024;
    protected Object lock;
    private char[] writeBuffer;

    protected Writer() {
        this.lock = this;
    }

    protected Writer(Object object) {
        if (object != null) {
            this.lock = object;
            return;
        }
        throw new NullPointerException();
    }

    @Override
    public Writer append(char c) throws IOException {
        this.write(c);
        return this;
    }

    @Override
    public Writer append(CharSequence charSequence) throws IOException {
        if (charSequence == null) {
            this.write("null");
        } else {
            this.write(charSequence.toString());
        }
        return this;
    }

    @Override
    public Writer append(CharSequence charSequence, int n, int n2) throws IOException {
        if (charSequence == null) {
            charSequence = "null";
        }
        this.write(charSequence.subSequence(n, n2).toString());
        return this;
    }

    @Override
    public abstract void close() throws IOException;

    @Override
    public abstract void flush() throws IOException;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void write(int n) throws IOException {
        Object object = this.lock;
        synchronized (object) {
            if (this.writeBuffer == null) {
                this.writeBuffer = new char[1024];
            }
            this.writeBuffer[0] = (char)n;
            this.write(this.writeBuffer, 0, 1);
            return;
        }
    }

    public void write(String string) throws IOException {
        this.write(string, 0, string.length());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void write(String string, int n, int n2) throws IOException {
        Object object = this.lock;
        synchronized (object) {
            char[] arrc;
            if (n2 <= 1024) {
                if (this.writeBuffer == null) {
                    this.writeBuffer = new char[1024];
                }
                arrc = this.writeBuffer;
            } else {
                arrc = new char[n2];
            }
            string.getChars(n, n + n2, arrc, 0);
            this.write(arrc, 0, n2);
            return;
        }
    }

    public void write(char[] arrc) throws IOException {
        this.write(arrc, 0, arrc.length);
    }

    public abstract void write(char[] var1, int var2, int var3) throws IOException;
}

