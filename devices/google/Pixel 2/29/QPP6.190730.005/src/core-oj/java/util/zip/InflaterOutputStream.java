/*
 * Decompiled with CFR 0.145.
 */
package java.util.zip;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.ZipException;

public class InflaterOutputStream
extends FilterOutputStream {
    protected final byte[] buf;
    private boolean closed = false;
    protected final Inflater inf;
    private boolean usesDefaultInflater = false;
    private final byte[] wbuf = new byte[1];

    public InflaterOutputStream(OutputStream outputStream) {
        this(outputStream, new Inflater());
        this.usesDefaultInflater = true;
    }

    public InflaterOutputStream(OutputStream outputStream, Inflater inflater) {
        this(outputStream, inflater, 512);
    }

    public InflaterOutputStream(OutputStream outputStream, Inflater inflater, int n) {
        super(outputStream);
        if (outputStream != null) {
            if (inflater != null) {
                if (n > 0) {
                    this.inf = inflater;
                    this.buf = new byte[n];
                    return;
                }
                throw new IllegalArgumentException("Buffer size < 1");
            }
            throw new NullPointerException("Null inflater");
        }
        throw new NullPointerException("Null output");
    }

    private void ensureOpen() throws IOException {
        if (!this.closed) {
            return;
        }
        throw new IOException("Stream closed");
    }

    @Override
    public void close() throws IOException {
        if (!this.closed) {
            try {
                this.finish();
            }
            finally {
                this.out.close();
                this.closed = true;
            }
        }
    }

    public void finish() throws IOException {
        this.ensureOpen();
        this.flush();
        if (this.usesDefaultInflater) {
            this.inf.end();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void flush() throws IOException {
        this.ensureOpen();
        if (this.inf.finished()) return;
        try {
            int n;
            while (!this.inf.finished() && !this.inf.needsInput() && (n = this.inf.inflate(this.buf, 0, this.buf.length)) >= 1) {
                this.out.write(this.buf, 0, n);
            }
            super.flush();
            return;
        }
        catch (DataFormatException dataFormatException) {
            String string;
            String string2 = string = dataFormatException.getMessage();
            if (string != null) throw new ZipException(string2);
            string2 = "Invalid ZLIB data format";
            throw new ZipException(string2);
        }
    }

    @Override
    public void write(int n) throws IOException {
        byte[] arrby = this.wbuf;
        arrby[0] = (byte)n;
        this.write(arrby, 0, 1);
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void write(byte[] object, int n, int n2) throws IOException {
        int n3;
        int n4;
        this.ensureOpen();
        if (object == null) throw new NullPointerException("Null buffer for read");
        if (n4 < 0 || n3 < 0 || n3 > ((byte[])object).length - n4) throw new IndexOutOfBoundsException();
        void var4_9 = n4;
        n4 = n3;
        if (n3 == 0) {
            return;
        }
        do {
            void var5_10;
            block11 : {
                block12 : {
                    var5_10 = var4_9;
                    n3 = n4;
                    if (!this.inf.needsInput()) break block11;
                    if (n4 < 1) return;
                    n3 = 512;
                    if (n4 >= 512) break block12;
                    n3 = n4;
                }
                this.inf.setInput((byte[])object, (int)var4_9, n3);
                var5_10 = var4_9 + n3;
                n3 = n4 - n3;
            }
            do {
                n4 = this.inf.inflate(this.buf, 0, this.buf.length);
                if (n4 <= 0) continue;
                this.out.write(this.buf, 0, n4);
            } while (n4 > 0);
            if (this.inf.finished()) {
                return;
            }
            if (this.inf.needsDictionary()) break;
            var4_9 = var5_10;
            n4 = n3;
            continue;
            break;
        } while (true);
        try {
            ZipException zipException = new ZipException("ZLIB dictionary missing");
            throw zipException;
        }
        catch (DataFormatException dataFormatException) {
            String string;
            void var1_6;
            String string2 = string = dataFormatException.getMessage();
            if (string != null) throw new ZipException((String)var1_6);
            String string3 = "Invalid ZLIB data format";
            throw new ZipException((String)var1_6);
        }
    }
}

