/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.util.Base64;
import android.util.Base64DataException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Base64InputStream
extends FilterInputStream {
    private static final int BUFFER_SIZE = 2048;
    private static byte[] EMPTY = new byte[0];
    private final Base64.Coder coder;
    private boolean eof = false;
    private byte[] inputBuffer = new byte[2048];
    private int outputEnd;
    private int outputStart;

    public Base64InputStream(InputStream inputStream, int n) {
        this(inputStream, n, false);
    }

    public Base64InputStream(InputStream object, int n, boolean bl) {
        super((InputStream)object);
        this.coder = bl ? new Base64.Encoder(n, null) : new Base64.Decoder(n, null);
        object = this.coder;
        ((Base64.Coder)object).output = new byte[((Base64.Coder)object).maxOutputSize(2048)];
        this.outputStart = 0;
        this.outputEnd = 0;
    }

    private void refill() throws IOException {
        boolean bl;
        if (this.eof) {
            return;
        }
        int n = this.in.read(this.inputBuffer);
        if (n == -1) {
            this.eof = true;
            bl = this.coder.process(EMPTY, 0, 0, true);
        } else {
            bl = this.coder.process(this.inputBuffer, 0, n, false);
        }
        if (bl) {
            this.outputEnd = this.coder.op;
            this.outputStart = 0;
            return;
        }
        throw new Base64DataException("bad base-64");
    }

    @Override
    public int available() {
        return this.outputEnd - this.outputStart;
    }

    @Override
    public void close() throws IOException {
        this.in.close();
        this.inputBuffer = null;
    }

    @Override
    public void mark(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public int read() throws IOException {
        if (this.outputStart >= this.outputEnd) {
            this.refill();
        }
        if (this.outputStart >= this.outputEnd) {
            return -1;
        }
        byte[] arrby = this.coder.output;
        int n = this.outputStart;
        this.outputStart = n + 1;
        return arrby[n] & 255;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        int n3;
        int n4;
        if (this.outputStart >= this.outputEnd) {
            this.refill();
        }
        if ((n4 = this.outputStart) >= (n3 = this.outputEnd)) {
            return -1;
        }
        n2 = Math.min(n2, n3 - n4);
        System.arraycopy(this.coder.output, this.outputStart, arrby, n, n2);
        this.outputStart += n2;
        return n2;
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long skip(long l) throws IOException {
        int n;
        int n2;
        if (this.outputStart >= this.outputEnd) {
            this.refill();
        }
        if ((n = this.outputStart) >= (n2 = this.outputEnd)) {
            return 0L;
        }
        l = Math.min(l, (long)(n2 - n));
        this.outputStart = (int)((long)this.outputStart + l);
        return l;
    }
}

