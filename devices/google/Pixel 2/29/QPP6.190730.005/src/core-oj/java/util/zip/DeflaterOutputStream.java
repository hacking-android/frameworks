/*
 * Decompiled with CFR 0.145.
 */
package java.util.zip;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;

public class DeflaterOutputStream
extends FilterOutputStream {
    protected byte[] buf;
    private boolean closed = false;
    protected Deflater def;
    private final boolean syncFlush;
    boolean usesDefaultDeflater = false;

    public DeflaterOutputStream(OutputStream outputStream) {
        this(outputStream, false);
        this.usesDefaultDeflater = true;
    }

    public DeflaterOutputStream(OutputStream outputStream, Deflater deflater) {
        this(outputStream, deflater, 512, false);
    }

    public DeflaterOutputStream(OutputStream outputStream, Deflater deflater, int n) {
        this(outputStream, deflater, n, false);
    }

    public DeflaterOutputStream(OutputStream outputStream, Deflater deflater, int n, boolean bl) {
        super(outputStream);
        if (outputStream != null && deflater != null) {
            if (n > 0) {
                this.def = deflater;
                this.buf = new byte[n];
                this.syncFlush = bl;
                return;
            }
            throw new IllegalArgumentException("buffer size <= 0");
        }
        throw new NullPointerException();
    }

    public DeflaterOutputStream(OutputStream outputStream, Deflater deflater, boolean bl) {
        this(outputStream, deflater, 512, bl);
    }

    public DeflaterOutputStream(OutputStream outputStream, boolean bl) {
        this(outputStream, new Deflater(), 512, bl);
        this.usesDefaultDeflater = true;
    }

    @Override
    public void close() throws IOException {
        if (!this.closed) {
            this.finish();
            if (this.usesDefaultDeflater) {
                this.def.end();
            }
            this.out.close();
            this.closed = true;
        }
    }

    protected void deflate() throws IOException {
        byte[] arrby;
        Deflater deflater;
        int n;
        while ((n = (deflater = this.def).deflate(arrby = this.buf, 0, arrby.length)) > 0) {
            this.out.write(this.buf, 0, n);
        }
    }

    public void finish() throws IOException {
        if (!this.def.finished()) {
            this.def.finish();
            while (!this.def.finished()) {
                this.deflate();
            }
        }
    }

    @Override
    public void flush() throws IOException {
        if (this.syncFlush && !this.def.finished()) {
            byte[] arrby;
            Deflater deflater;
            int n;
            while ((n = (deflater = this.def).deflate(arrby = this.buf, 0, arrby.length, 2)) > 0) {
                this.out.write(this.buf, 0, n);
                if (n >= this.buf.length) continue;
            }
        }
        this.out.flush();
    }

    @Override
    public void write(int n) throws IOException {
        this.write(new byte[]{(byte)(n & 255)}, 0, 1);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        if (!this.def.finished()) {
            if ((n | n2 | n + n2 | arrby.length - (n + n2)) >= 0) {
                if (n2 == 0) {
                    return;
                }
                if (!this.def.finished()) {
                    this.def.setInput(arrby, n, n2);
                    while (!this.def.needsInput()) {
                        this.deflate();
                    }
                }
                return;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IOException("write beyond end of stream");
    }
}

