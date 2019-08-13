/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BufferedOutputStream
extends FilterOutputStream {
    protected byte[] buf;
    protected int count;

    public BufferedOutputStream(OutputStream outputStream) {
        this(outputStream, 8192);
    }

    public BufferedOutputStream(OutputStream outputStream, int n) {
        super(outputStream);
        if (n > 0) {
            this.buf = new byte[n];
            return;
        }
        throw new IllegalArgumentException("Buffer size <= 0");
    }

    private void flushBuffer() throws IOException {
        if (this.count > 0) {
            this.out.write(this.buf, 0, this.count);
            this.count = 0;
        }
    }

    @Override
    public void flush() throws IOException {
        synchronized (this) {
            this.flushBuffer();
            this.out.flush();
            return;
        }
    }

    @Override
    public void write(int n) throws IOException {
        synchronized (this) {
            if (this.count >= this.buf.length) {
                this.flushBuffer();
            }
            byte[] arrby = this.buf;
            int n2 = this.count;
            this.count = n2 + 1;
            arrby[n2] = (byte)n;
            return;
        }
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        synchronized (this) {
            if (n2 >= this.buf.length) {
                this.flushBuffer();
                this.out.write(arrby, n, n2);
                return;
            }
            if (n2 > this.buf.length - this.count) {
                this.flushBuffer();
            }
            System.arraycopy(arrby, n, this.buf, this.count, n2);
            this.count += n2;
            return;
        }
    }
}

