/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.annotation.UnsupportedAppUsage;
import android.util.Base64;
import android.util.Base64DataException;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Base64OutputStream
extends FilterOutputStream {
    private static byte[] EMPTY = new byte[0];
    private int bpos = 0;
    private byte[] buffer = null;
    private final Base64.Coder coder;
    private final int flags;

    public Base64OutputStream(OutputStream outputStream, int n) {
        this(outputStream, n, true);
    }

    @UnsupportedAppUsage
    public Base64OutputStream(OutputStream outputStream, int n, boolean bl) {
        super(outputStream);
        this.flags = n;
        this.coder = bl ? new Base64.Encoder(n, null) : new Base64.Decoder(n, null);
    }

    private byte[] embiggen(byte[] arrby, int n) {
        if (arrby != null && arrby.length >= n) {
            return arrby;
        }
        return new byte[n];
    }

    private void flushBuffer() throws IOException {
        int n = this.bpos;
        if (n > 0) {
            this.internalWrite(this.buffer, 0, n, false);
            this.bpos = 0;
        }
    }

    private void internalWrite(byte[] arrby, int n, int n2, boolean bl) throws IOException {
        Base64.Coder coder = this.coder;
        coder.output = this.embiggen(coder.output, this.coder.maxOutputSize(n2));
        if (this.coder.process(arrby, n, n2, bl)) {
            this.out.write(this.coder.output, 0, this.coder.op);
            return;
        }
        throw new Base64DataException("bad base-64");
    }

    @Override
    public void close() throws IOException {
        IOException iOException = null;
        try {
            this.flushBuffer();
            this.internalWrite(EMPTY, 0, 0, true);
        }
        catch (IOException iOException2) {
            // empty catch block
        }
        try {
            if ((this.flags & 16) == 0) {
                this.out.close();
            } else {
                this.out.flush();
            }
        }
        catch (IOException iOException3) {
            if (iOException == null) {
                iOException = iOException3;
            }
            iOException.addSuppressed(iOException3);
        }
        if (iOException == null) {
            return;
        }
        throw iOException;
    }

    @Override
    public void write(int n) throws IOException {
        int n2;
        byte[] arrby;
        if (this.buffer == null) {
            this.buffer = new byte[1024];
        }
        if ((n2 = this.bpos) >= (arrby = this.buffer).length) {
            this.internalWrite(arrby, 0, n2, false);
            this.bpos = 0;
        }
        arrby = this.buffer;
        n2 = this.bpos;
        this.bpos = n2 + 1;
        arrby[n2] = (byte)n;
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        if (n2 <= 0) {
            return;
        }
        this.flushBuffer();
        this.internalWrite(arrby, n, n2, false);
    }
}

