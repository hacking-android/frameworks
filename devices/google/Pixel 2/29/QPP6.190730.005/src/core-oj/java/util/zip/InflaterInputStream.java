/*
 * Decompiled with CFR 0.145.
 */
package java.util.zip;

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.ZipException;

public class InflaterInputStream
extends FilterInputStream {
    private byte[] b = new byte[512];
    protected byte[] buf;
    @Deprecated
    protected boolean closed = false;
    protected Inflater inf;
    protected int len;
    private boolean reachEOF = false;
    private byte[] singleByteBuf = new byte[1];

    public InflaterInputStream(InputStream inputStream) {
        this(inputStream, new Inflater());
    }

    public InflaterInputStream(InputStream inputStream, Inflater inflater) {
        this(inputStream, inflater, 512);
    }

    public InflaterInputStream(InputStream inputStream, Inflater inflater, int n) {
        super(inputStream);
        if (inputStream != null && inflater != null) {
            if (n > 0) {
                this.inf = inflater;
                this.buf = new byte[n];
                return;
            }
            throw new IllegalArgumentException("buffer size <= 0");
        }
        throw new NullPointerException();
    }

    private void ensureOpen() throws IOException {
        if (!this.closed) {
            return;
        }
        throw new IOException("Stream closed");
    }

    @Override
    public int available() throws IOException {
        this.ensureOpen();
        if (this.reachEOF) {
            return 0;
        }
        if (this.inf.finished()) {
            this.reachEOF = true;
            return 0;
        }
        return 1;
    }

    @Override
    public void close() throws IOException {
        if (!this.closed) {
            this.inf.end();
            this.in.close();
            this.closed = true;
        }
    }

    protected void fill() throws IOException {
        this.ensureOpen();
        InputStream inputStream = this.in;
        byte[] arrby = this.buf;
        int n = this.len = inputStream.read(arrby, 0, arrby.length);
        if (n != -1) {
            this.inf.setInput(this.buf, 0, n);
            return;
        }
        throw new EOFException("Unexpected end of ZLIB input stream");
    }

    /*
     * Enabled aggressive block sorting
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void mark(int n) {
        // MONITORENTER : this
        // MONITOREXIT : this
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public int read() throws IOException {
        this.ensureOpen();
        int n = this.read(this.singleByteBuf, 0, 1);
        int n2 = -1;
        if (n != -1) {
            n2 = Byte.toUnsignedInt(this.singleByteBuf[0]);
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        this.ensureOpen();
        if (arrby == null) throw new NullPointerException();
        if (n < 0) throw new IndexOutOfBoundsException();
        if (n2 < 0) throw new IndexOutOfBoundsException();
        if (n2 > arrby.length - n) throw new IndexOutOfBoundsException();
        if (n2 == 0) {
            return 0;
        }
        try {
            block6 : {
                int n3;
                while ((n3 = this.inf.inflate(arrby, n, n2)) == 0) {
                    if (!this.inf.finished() && !this.inf.needsDictionary()) {
                        if (!this.inf.needsInput()) continue;
                        this.fill();
                        continue;
                    }
                    break block6;
                }
                return n3;
            }
            this.reachEOF = true;
            return -1;
        }
        catch (DataFormatException dataFormatException) {
            String string = dataFormatException.getMessage();
            if (string != null) {
                throw new ZipException(string);
            }
            string = "Invalid ZLIB data format";
            throw new ZipException(string);
        }
    }

    @Override
    public void reset() throws IOException {
        synchronized (this) {
            IOException iOException = new IOException("mark/reset not supported");
            throw iOException;
        }
    }

    @Override
    public long skip(long l) throws IOException {
        if (l >= 0L) {
            int n;
            int n2;
            this.ensureOpen();
            int n3 = (int)Math.min(l, Integer.MAX_VALUE);
            for (n = 0; n < n3; n += n2) {
                int n4 = n3 - n;
                byte[] arrby = this.b;
                n2 = n4;
                if (n4 > arrby.length) {
                    n2 = arrby.length;
                }
                if ((n2 = this.read(this.b, 0, n2)) != -1) continue;
                this.reachEOF = true;
                break;
            }
            return n;
        }
        throw new IllegalArgumentException("negative skip length");
    }
}

