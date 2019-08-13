/*
 * Decompiled with CFR 0.145.
 */
package java.util.zip;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class GZIPOutputStream
extends DeflaterOutputStream {
    private static final int GZIP_MAGIC = 35615;
    private static final int TRAILER_SIZE = 8;
    protected CRC32 crc = new CRC32();

    public GZIPOutputStream(OutputStream outputStream) throws IOException {
        this(outputStream, 512, false);
    }

    public GZIPOutputStream(OutputStream outputStream, int n) throws IOException {
        this(outputStream, n, false);
    }

    public GZIPOutputStream(OutputStream outputStream, int n, boolean bl) throws IOException {
        super(outputStream, new Deflater(-1, true), n, bl);
        this.usesDefaultDeflater = true;
        this.writeHeader();
        this.crc.reset();
    }

    public GZIPOutputStream(OutputStream outputStream, boolean bl) throws IOException {
        this(outputStream, 512, bl);
    }

    private void writeHeader() throws IOException {
        this.out.write(new byte[]{31, -117, 8, 0, 0, 0, 0, 0, 0, 0});
    }

    private void writeInt(int n, byte[] arrby, int n2) throws IOException {
        this.writeShort(n & 65535, arrby, n2);
        this.writeShort(65535 & n >> 16, arrby, n2 + 2);
    }

    private void writeShort(int n, byte[] arrby, int n2) throws IOException {
        arrby[n2] = (byte)(n & 255);
        arrby[n2 + 1] = (byte)(n >> 8 & 255);
    }

    private void writeTrailer(byte[] arrby, int n) throws IOException {
        this.writeInt((int)this.crc.getValue(), arrby, n);
        this.writeInt(this.def.getTotalIn(), arrby, n + 4);
    }

    @Override
    public void finish() throws IOException {
        if (!this.def.finished()) {
            this.def.finish();
            while (!this.def.finished()) {
                int n = this.def.deflate(this.buf, 0, this.buf.length);
                if (this.def.finished() && n <= this.buf.length - 8) {
                    this.writeTrailer(this.buf, n);
                    this.out.write(this.buf, 0, n + 8);
                    return;
                }
                if (n <= 0) continue;
                this.out.write(this.buf, 0, n);
            }
            byte[] arrby = new byte[8];
            this.writeTrailer(arrby, 0);
            this.out.write(arrby);
        }
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        synchronized (this) {
            super.write(arrby, n, n2);
            this.crc.update(arrby, n, n2);
            return;
        }
    }
}

