/*
 * Decompiled with CFR 0.145.
 */
package java.util.zip;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipException;

public class GZIPInputStream
extends InflaterInputStream {
    private static final int FCOMMENT = 16;
    private static final int FEXTRA = 4;
    private static final int FHCRC = 2;
    private static final int FNAME = 8;
    private static final int FTEXT = 1;
    public static final int GZIP_MAGIC = 35615;
    private boolean closed = false;
    protected CRC32 crc = new CRC32();
    protected boolean eos;
    private byte[] tmpbuf = new byte[128];

    public GZIPInputStream(InputStream inputStream) throws IOException {
        this(inputStream, 512);
    }

    public GZIPInputStream(InputStream inputStream, int n) throws IOException {
        super(inputStream, new Inflater(true), n);
        try {
            this.readHeader(inputStream);
            return;
        }
        catch (Exception exception) {
            this.inf.end();
            throw exception;
        }
    }

    private void ensureOpen() throws IOException {
        if (!this.closed) {
            return;
        }
        throw new IOException("Stream closed");
    }

    private int readHeader(InputStream inputStream) throws IOException {
        inputStream = new CheckedInputStream(inputStream, this.crc);
        this.crc.reset();
        if (this.readUShort(inputStream) == 35615) {
            if (this.readUByte(inputStream) == 8) {
                int n;
                int n2 = this.readUByte(inputStream);
                this.skipBytes(inputStream, 6);
                int n3 = 10;
                if ((n2 & 4) == 4) {
                    n = this.readUShort(inputStream);
                    this.skipBytes(inputStream, n);
                    n3 = 10 + (n + 2);
                }
                n = n3;
                if ((n2 & 8) == 8) {
                    do {
                        n3 = n = n3 + 1;
                    } while (this.readUByte(inputStream) != 0);
                }
                n3 = n;
                if ((n2 & 16) == 16) {
                    do {
                        n = n3 = n + 1;
                    } while (this.readUByte(inputStream) != 0);
                }
                n = n3;
                if ((n2 & 2) == 2) {
                    n = (int)this.crc.getValue();
                    if (this.readUShort(inputStream) == (n & 65535)) {
                        n = n3 + 2;
                    } else {
                        throw new ZipException("Corrupt GZIP header");
                    }
                }
                this.crc.reset();
                return n;
            }
            throw new ZipException("Unsupported compression method");
        }
        throw new ZipException("Not in GZIP format");
    }

    private boolean readTrailer() throws IOException {
        InputStream inputStream = this.in;
        int n = this.inf.getRemaining();
        InputStream inputStream2 = inputStream;
        if (n > 0) {
            inputStream2 = new SequenceInputStream(new ByteArrayInputStream(this.buf, this.len - n, n), new FilterInputStream(inputStream){

                @Override
                public void close() throws IOException {
                }
            });
        }
        if (this.readUInt(inputStream2) == this.crc.getValue() && this.readUInt(inputStream2) == (this.inf.getBytesWritten() & 0xFFFFFFFFL)) {
            if (this.in.available() <= 0 && n <= 26) {
                return true;
            }
            try {
                int n2 = this.readHeader(inputStream2);
                this.inf.reset();
                if (n > (n2 += 8)) {
                    this.inf.setInput(this.buf, this.len - n + n2, n - n2);
                }
                return false;
            }
            catch (IOException iOException) {
                return true;
            }
        }
        throw new ZipException("Corrupt GZIP trailer");
    }

    private int readUByte(InputStream object) throws IOException {
        int n = ((InputStream)object).read();
        if (n != -1) {
            if (n >= -1 && n <= 255) {
                return n;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(this.in.getClass().getName());
            ((StringBuilder)object).append(".read() returned value out of range -1..255: ");
            ((StringBuilder)object).append(n);
            throw new IOException(((StringBuilder)object).toString());
        }
        throw new EOFException();
    }

    private long readUInt(InputStream inputStream) throws IOException {
        long l = this.readUShort(inputStream);
        return (long)this.readUShort(inputStream) << 16 | l;
    }

    private int readUShort(InputStream inputStream) throws IOException {
        int n = this.readUByte(inputStream);
        return this.readUByte(inputStream) << 8 | n;
    }

    private void skipBytes(InputStream inputStream, int n) throws IOException {
        while (n > 0) {
            byte[] arrby = this.tmpbuf;
            int n2 = n < arrby.length ? n : arrby.length;
            if ((n2 = inputStream.read(arrby, 0, n2)) != -1) {
                n -= n2;
                continue;
            }
            throw new EOFException();
        }
    }

    @Override
    public void close() throws IOException {
        if (!this.closed) {
            super.close();
            this.eos = true;
            this.closed = true;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        this.ensureOpen();
        if (this.eos) {
            return -1;
        }
        int n3 = super.read(arrby, n, n2);
        if (n3 == -1) {
            if (!this.readTrailer()) return this.read(arrby, n, n2);
            this.eos = true;
            return n3;
        } else {
            this.crc.update(arrby, n, n3);
        }
        return n3;
    }

}

