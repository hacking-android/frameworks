/*
 * Decompiled with CFR 0.145.
 */
package java.util.zip;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipCoder;
import java.util.zip.ZipConstants;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipUtils;

public class ZipInputStream
extends InflaterInputStream
implements ZipConstants {
    private static final int DEFLATED = 8;
    private static final int STORED = 0;
    private byte[] b = new byte[256];
    private boolean closed = false;
    private CRC32 crc = new CRC32();
    private ZipEntry entry;
    private boolean entryEOF = false;
    private int flag;
    private long remaining;
    private byte[] tmpbuf = new byte[512];
    private ZipCoder zc;

    public ZipInputStream(InputStream inputStream) {
        this(inputStream, StandardCharsets.UTF_8);
    }

    public ZipInputStream(InputStream inputStream, Charset charset) {
        super(new PushbackInputStream(inputStream, 512), new Inflater(true), 512);
        if (inputStream != null) {
            if (charset != null) {
                this.zc = ZipCoder.get(charset);
                return;
            }
            throw new NullPointerException("charset is null");
        }
        throw new NullPointerException("in is null");
    }

    private void ensureOpen() throws IOException {
        if (!this.closed) {
            return;
        }
        throw new IOException("Stream closed");
    }

    private void readEnd(ZipEntry zipEntry) throws IOException {
        int n = this.inf.getRemaining();
        if (n > 0) {
            ((PushbackInputStream)this.in).unread(this.buf, this.len - n, n);
        }
        if ((this.flag & 8) == 8) {
            if (this.inf.getBytesWritten() <= 0xFFFFFFFFL && this.inf.getBytesRead() <= 0xFFFFFFFFL) {
                this.readFully(this.tmpbuf, 0, 16);
                long l = ZipUtils.get32(this.tmpbuf, 0);
                if (l != 134695760L) {
                    zipEntry.crc = l;
                    zipEntry.csize = ZipUtils.get32(this.tmpbuf, 4);
                    zipEntry.size = ZipUtils.get32(this.tmpbuf, 8);
                    ((PushbackInputStream)this.in).unread(this.tmpbuf, 11, 4);
                } else {
                    zipEntry.crc = ZipUtils.get32(this.tmpbuf, 4);
                    zipEntry.csize = ZipUtils.get32(this.tmpbuf, 8);
                    zipEntry.size = ZipUtils.get32(this.tmpbuf, 12);
                }
            } else {
                this.readFully(this.tmpbuf, 0, 24);
                long l = ZipUtils.get32(this.tmpbuf, 0);
                if (l != 134695760L) {
                    zipEntry.crc = l;
                    zipEntry.csize = ZipUtils.get64(this.tmpbuf, 4);
                    zipEntry.size = ZipUtils.get64(this.tmpbuf, 12);
                    ((PushbackInputStream)this.in).unread(this.tmpbuf, 19, 4);
                } else {
                    zipEntry.crc = ZipUtils.get32(this.tmpbuf, 4);
                    zipEntry.csize = ZipUtils.get64(this.tmpbuf, 8);
                    zipEntry.size = ZipUtils.get64(this.tmpbuf, 16);
                }
            }
        }
        if (zipEntry.size == this.inf.getBytesWritten()) {
            if (zipEntry.csize == this.inf.getBytesRead()) {
                if (zipEntry.crc == this.crc.getValue()) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("invalid entry CRC (expected 0x");
                stringBuilder.append(Long.toHexString(zipEntry.crc));
                stringBuilder.append(" but got 0x");
                stringBuilder.append(Long.toHexString(this.crc.getValue()));
                stringBuilder.append(")");
                throw new ZipException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid entry compressed size (expected ");
            stringBuilder.append(zipEntry.csize);
            stringBuilder.append(" but got ");
            stringBuilder.append(this.inf.getBytesRead());
            stringBuilder.append(" bytes)");
            throw new ZipException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid entry size (expected ");
        stringBuilder.append(zipEntry.size);
        stringBuilder.append(" but got ");
        stringBuilder.append(this.inf.getBytesWritten());
        stringBuilder.append(" bytes)");
        throw new ZipException(stringBuilder.toString());
    }

    private void readFully(byte[] arrby, int n, int n2) throws IOException {
        while (n2 > 0) {
            int n3 = this.in.read(arrby, n, n2);
            if (n3 != -1) {
                n += n3;
                n2 -= n3;
                continue;
            }
            throw new EOFException();
        }
    }

    private ZipEntry readLOC() throws IOException {
        int n;
        Object object = this.tmpbuf;
        boolean bl = false;
        try {
            this.readFully((byte[])object, 0, 30);
        }
        catch (EOFException eOFException) {
            return null;
        }
        if (ZipUtils.get32(this.tmpbuf, 0) != 67324752L) {
            return null;
        }
        this.flag = ZipUtils.get16(this.tmpbuf, 6);
        int n2 = ZipUtils.get16(this.tmpbuf, 26);
        if (n2 > (n = this.b.length)) {
            int n3;
            do {
                n = n3 = n * 2;
            } while (n2 > n3);
            this.b = new byte[n3];
        }
        this.readFully(this.b, 0, n2);
        object = (this.flag & 2048) != 0 ? this.zc.toStringUTF8(this.b, n2) : this.zc.toString(this.b, n2);
        object = this.createZipEntry((String)object);
        if ((this.flag & 1) != 1) {
            ((ZipEntry)object).method = ZipUtils.get16(this.tmpbuf, 8);
            ((ZipEntry)object).xdostime = ZipUtils.get32(this.tmpbuf, 10);
            if ((this.flag & 8) == 8) {
                if (((ZipEntry)object).method != 8) {
                    throw new ZipException("only DEFLATED entries can have EXT descriptor");
                }
            } else {
                ((ZipEntry)object).crc = ZipUtils.get32(this.tmpbuf, 14);
                ((ZipEntry)object).csize = ZipUtils.get32(this.tmpbuf, 18);
                ((ZipEntry)object).size = ZipUtils.get32(this.tmpbuf, 22);
            }
            n = ZipUtils.get16(this.tmpbuf, 28);
            if (n > 0) {
                byte[] arrby = new byte[n];
                this.readFully(arrby, 0, n);
                if (((ZipEntry)object).csize == 0xFFFFFFFFL || ((ZipEntry)object).size == 0xFFFFFFFFL) {
                    bl = true;
                }
                ((ZipEntry)object).setExtra0(arrby, bl);
            }
            return object;
        }
        throw new ZipException("encrypted ZIP entry not supported");
    }

    @Override
    public int available() throws IOException {
        this.ensureOpen();
        return !(this.entryEOF || this.entry != null && this.remaining == 0L);
        {
        }
    }

    @Override
    public void close() throws IOException {
        if (!this.closed) {
            super.close();
            this.closed = true;
        }
    }

    public void closeEntry() throws IOException {
        byte[] arrby;
        this.ensureOpen();
        while (this.read(arrby = this.tmpbuf, 0, arrby.length) != -1) {
        }
        this.entryEOF = true;
    }

    protected ZipEntry createZipEntry(String string) {
        return new ZipEntry(string);
    }

    public ZipEntry getNextEntry() throws IOException {
        ZipEntry zipEntry;
        this.ensureOpen();
        if (this.entry != null) {
            this.closeEntry();
        }
        this.crc.reset();
        this.inf.reset();
        this.entry = zipEntry = this.readLOC();
        if (zipEntry == null) {
            return null;
        }
        if (this.entry.method == 0 || this.entry.method == 8) {
            this.remaining = this.entry.size;
        }
        this.entryEOF = false;
        return this.entry;
    }

    @Override
    public int read(byte[] object, int n, int n2) throws IOException {
        this.ensureOpen();
        if (n >= 0 && n2 >= 0 && n <= ((byte[])object).length - n2) {
            if (n2 == 0) {
                return 0;
            }
            ZipEntry zipEntry = this.entry;
            if (zipEntry == null) {
                return -1;
            }
            int n3 = zipEntry.method;
            if (n3 != 0) {
                if (n3 == 8) {
                    if ((n2 = super.read((byte[])object, n, n2)) == -1) {
                        this.readEnd(this.entry);
                        this.entryEOF = true;
                        this.entry = null;
                    } else {
                        this.crc.update((byte[])object, n, n2);
                        this.remaining -= (long)n2;
                    }
                    return n2;
                }
                throw new ZipException("invalid compression method");
            }
            long l = this.remaining;
            if (l <= 0L) {
                this.entryEOF = true;
                this.entry = null;
                return -1;
            }
            n3 = n2;
            if ((long)n2 > l) {
                n3 = (int)l;
            }
            if ((n2 = this.in.read((byte[])object, n, n3)) != -1) {
                this.crc.update((byte[])object, n, n2);
                this.remaining -= (long)n2;
                if (this.remaining == 0L && this.entry.crc != this.crc.getValue()) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("invalid entry CRC (expected 0x");
                    ((StringBuilder)object).append(Long.toHexString(this.entry.crc));
                    ((StringBuilder)object).append(" but got 0x");
                    ((StringBuilder)object).append(Long.toHexString(this.crc.getValue()));
                    ((StringBuilder)object).append(")");
                    throw new ZipException(((StringBuilder)object).toString());
                }
                return n2;
            }
            throw new ZipException("unexpected EOF");
        }
        throw new IndexOutOfBoundsException();
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
                byte[] arrby = this.tmpbuf;
                n2 = n4;
                if (n4 > arrby.length) {
                    n2 = arrby.length;
                }
                if ((n2 = this.read(this.tmpbuf, 0, n2)) != -1) continue;
                this.entryEOF = true;
                break;
            }
            return n;
        }
        throw new IllegalArgumentException("negative skip length");
    }
}

