/*
 * Decompiled with CFR 0.145.
 */
package java.util.zip;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Deflater;

public class DeflaterInputStream
extends FilterInputStream {
    protected final byte[] buf;
    protected final Deflater def;
    private byte[] rbuf = new byte[1];
    private boolean reachEOF = false;
    private boolean usesDefaultDeflater = false;

    public DeflaterInputStream(InputStream inputStream) {
        this(inputStream, new Deflater());
        this.usesDefaultDeflater = true;
    }

    public DeflaterInputStream(InputStream inputStream, Deflater deflater) {
        this(inputStream, deflater, 512);
    }

    public DeflaterInputStream(InputStream inputStream, Deflater deflater, int n) {
        super(inputStream);
        if (inputStream != null) {
            if (deflater != null) {
                if (n >= 1) {
                    this.def = deflater;
                    this.buf = new byte[n];
                    return;
                }
                throw new IllegalArgumentException("Buffer size < 1");
            }
            throw new NullPointerException("Null deflater");
        }
        throw new NullPointerException("Null input");
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
        return !this.reachEOF;
    }

    @Override
    public void close() throws IOException {
        if (this.in != null) {
            try {
                if (this.usesDefaultDeflater) {
                    this.def.end();
                }
                this.in.close();
            }
            finally {
                this.in = null;
            }
        }
    }

    @Override
    public void mark(int n) {
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public int read() throws IOException {
        if (this.read(this.rbuf, 0, 1) <= 0) {
            return -1;
        }
        return this.rbuf[0] & 255;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        this.ensureOpen();
        if (arrby != null) {
            if (n >= 0 && n2 >= 0 && n2 <= arrby.length - n) {
                if (n2 == 0) {
                    return 0;
                }
                int n3 = 0;
                int n4 = n;
                n = n3;
                while (n2 > 0 && !this.def.finished()) {
                    if (this.def.needsInput()) {
                        InputStream inputStream = this.in;
                        byte[] arrby2 = this.buf;
                        n3 = inputStream.read(arrby2, 0, arrby2.length);
                        if (n3 < 0) {
                            this.def.finish();
                        } else if (n3 > 0) {
                            this.def.setInput(this.buf, 0, n3);
                        }
                    }
                    n3 = this.def.deflate(arrby, n4, n2);
                    n += n3;
                    n4 += n3;
                    n2 -= n3;
                }
                n2 = n;
                if (this.def.finished()) {
                    this.reachEOF = true;
                    n2 = n;
                    if (n == 0) {
                        n2 = -1;
                    }
                }
                return n2;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new NullPointerException("Null buffer for read");
    }

    @Override
    public void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }

    @Override
    public long skip(long l) throws IOException {
        if (l >= 0L) {
            this.ensureOpen();
            if (this.rbuf.length < 512) {
                this.rbuf = new byte[512];
            }
            int n = (int)Math.min(l, Integer.MAX_VALUE);
            l = 0L;
            while (n > 0) {
                byte[] arrby = this.rbuf;
                int n2 = n <= arrby.length ? n : arrby.length;
                if ((n2 = this.read(arrby, 0, n2)) < 0) break;
                l += (long)n2;
                n -= n2;
            }
            return l;
        }
        throw new IllegalArgumentException("negative skip length");
    }
}

