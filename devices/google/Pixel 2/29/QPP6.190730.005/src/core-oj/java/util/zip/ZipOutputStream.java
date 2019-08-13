/*
 * Decompiled with CFR 0.145.
 */
package java.util.zip;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.FileTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.ZipCoder;
import java.util.zip.ZipConstants;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipUtils;

public class ZipOutputStream
extends DeflaterOutputStream
implements ZipConstants {
    public static final int DEFLATED = 8;
    public static final int STORED = 0;
    private static final boolean inhibitZip64 = false;
    private boolean closed = false;
    private byte[] comment;
    private CRC32 crc = new CRC32();
    private XEntry current;
    private boolean finished;
    private long locoff = 0L;
    private int method = 8;
    private HashSet<String> names = new HashSet();
    private long written = 0L;
    private Vector<XEntry> xentries = new Vector();
    private final ZipCoder zc;

    public ZipOutputStream(OutputStream outputStream) {
        this(outputStream, StandardCharsets.UTF_8);
    }

    public ZipOutputStream(OutputStream outputStream, Charset charset) {
        super(outputStream, new Deflater(-1, true));
        if (charset != null) {
            this.zc = ZipCoder.get(charset);
            this.usesDefaultDeflater = true;
            return;
        }
        throw new NullPointerException("charset is null");
    }

    private void ensureOpen() throws IOException {
        if (!this.closed) {
            return;
        }
        throw new IOException("Stream closed");
    }

    private int getExtraLen(byte[] arrby) {
        if (arrby == null) {
            return 0;
        }
        int n = 0;
        int n2 = arrby.length;
        int n3 = 0;
        while (n3 + 4 <= n2) {
            int n4;
            int n5;
            block6 : {
                block5 : {
                    int n6 = ZipUtils.get16(arrby, n3);
                    n4 = ZipUtils.get16(arrby, n3 + 2);
                    if (n4 < 0 || n3 + 4 + n4 > n2) break;
                    if (n6 == 21589) break block5;
                    n5 = n;
                    if (n6 != 1) break block6;
                }
                n5 = n + (n4 + 4);
            }
            n3 += n4 + 4;
            n = n5;
        }
        return n2 - n;
    }

    private static int version(ZipEntry zipEntry) throws ZipException {
        int n = zipEntry.method;
        if (n != 0) {
            if (n == 8) {
                return 20;
            }
            throw new ZipException("unsupported compression method");
        }
        return 10;
    }

    private void writeByte(int n) throws IOException {
        this.out.write(n & 255);
        ++this.written;
    }

    private void writeBytes(byte[] arrby, int n, int n2) throws IOException {
        this.out.write(arrby, n, n2);
        this.written += (long)n2;
    }

    private void writeCEN(XEntry xEntry) throws IOException {
        byte[] arrby;
        ZipEntry zipEntry = xEntry.entry;
        int n = zipEntry.flag;
        int n2 = ZipOutputStream.version(zipEntry);
        long l = zipEntry.csize;
        long l2 = zipEntry.size;
        long l3 = xEntry.offset;
        int n3 = 0;
        int n4 = 0;
        if (zipEntry.csize >= 0xFFFFFFFFL) {
            l = 0xFFFFFFFFL;
            n3 = 0 + 8;
            n4 = 1;
        }
        int n5 = n3;
        if (zipEntry.size >= 0xFFFFFFFFL) {
            l2 = 0xFFFFFFFFL;
            n5 = n3 + 8;
            n4 = 1;
        }
        int n6 = n5;
        int n7 = n4;
        if (xEntry.offset >= 0xFFFFFFFFL) {
            l3 = 0xFFFFFFFFL;
            n6 = n5 + 8;
            n7 = 1;
        }
        this.writeInt(33639248L);
        if (n7 != 0) {
            this.writeShort(45);
            this.writeShort(45);
        } else {
            this.writeShort(n2);
            this.writeShort(n2);
        }
        this.writeShort(n);
        this.writeShort(zipEntry.method);
        this.writeInt(zipEntry.xdostime);
        this.writeInt(zipEntry.crc);
        this.writeInt(l);
        this.writeInt(l2);
        byte[] arrby2 = this.zc.getBytes(zipEntry.name);
        this.writeShort(arrby2.length);
        n4 = n5 = this.getExtraLen(zipEntry.extra);
        if (n7 != 0) {
            n4 = n5 + (n6 + 4);
        }
        n3 = 0;
        n5 = n4;
        if (zipEntry.mtime != null) {
            n5 = n4 + 4;
            n3 = false | true;
        }
        n4 = n3;
        if (zipEntry.atime != null) {
            n4 = n3 | 2;
        }
        if (zipEntry.ctime != null) {
            n4 |= 4;
        }
        n3 = n5;
        if (n4 != 0) {
            n3 = n5 + 5;
        }
        this.writeShort(n3);
        if (zipEntry.comment != null) {
            arrby = this.zc.getBytes(zipEntry.comment);
            this.writeShort(Math.min(arrby.length, 65535));
        } else {
            arrby = null;
            this.writeShort(0);
        }
        this.writeShort(0);
        this.writeShort(0);
        this.writeInt(0L);
        this.writeInt(l3);
        this.writeBytes(arrby2, 0, arrby2.length);
        if (n7 != 0) {
            this.writeShort(1);
            this.writeShort(n6);
            if (l2 == 0xFFFFFFFFL) {
                this.writeLong(zipEntry.size);
            }
            if (l == 0xFFFFFFFFL) {
                this.writeLong(zipEntry.csize);
            }
            if (l3 == 0xFFFFFFFFL) {
                this.writeLong(xEntry.offset);
            }
        }
        if (n4 != 0) {
            this.writeShort(21589);
            if (zipEntry.mtime != null) {
                this.writeShort(5);
                this.writeByte(n4);
                this.writeInt(ZipUtils.fileTimeToUnixTime(zipEntry.mtime));
            } else {
                this.writeShort(1);
                this.writeByte(n4);
            }
        }
        this.writeExtra(zipEntry.extra);
        if (arrby != null) {
            this.writeBytes(arrby, 0, Math.min(arrby.length, 65535));
        }
    }

    private void writeEND(long l, long l2) throws IOException {
        boolean bl = false;
        long l3 = l2;
        long l4 = l;
        long l5 = l3;
        if (l3 >= 0xFFFFFFFFL) {
            l5 = 0xFFFFFFFFL;
            bl = true;
        }
        l3 = l4;
        if (l4 >= 0xFFFFFFFFL) {
            l3 = 0xFFFFFFFFL;
            bl = true;
        }
        int n = this.xentries.size();
        boolean bl2 = bl;
        int n2 = n;
        if (n >= 65535) {
            bl2 = bl |= true;
            n2 = n;
            if (bl) {
                n2 = 65535;
                bl2 = bl;
            }
        }
        if (bl2) {
            l4 = this.written;
            this.writeInt(101075792L);
            this.writeLong(44L);
            this.writeShort(45);
            this.writeShort(45);
            this.writeInt(0L);
            this.writeInt(0L);
            this.writeLong(this.xentries.size());
            this.writeLong(this.xentries.size());
            this.writeLong(l2);
            this.writeLong(l);
            this.writeInt(117853008L);
            this.writeInt(0L);
            this.writeLong(l4);
            this.writeInt(1L);
        }
        this.writeInt(101010256L);
        this.writeShort(0);
        this.writeShort(0);
        this.writeShort(n2);
        this.writeShort(n2);
        this.writeInt(l5);
        this.writeInt(l3);
        byte[] arrby = this.comment;
        if (arrby != null) {
            this.writeShort(arrby.length);
            arrby = this.comment;
            this.writeBytes(arrby, 0, arrby.length);
        } else {
            this.writeShort(0);
        }
    }

    private void writeEXT(ZipEntry zipEntry) throws IOException {
        this.writeInt(134695760L);
        this.writeInt(zipEntry.crc);
        if (zipEntry.csize < 0xFFFFFFFFL && zipEntry.size < 0xFFFFFFFFL) {
            this.writeInt(zipEntry.csize);
            this.writeInt(zipEntry.size);
        } else {
            this.writeLong(zipEntry.csize);
            this.writeLong(zipEntry.size);
        }
    }

    private void writeExtra(byte[] arrby) throws IOException {
        if (arrby != null) {
            int n = arrby.length;
            int n2 = 0;
            while (n2 + 4 <= n) {
                int n3 = ZipUtils.get16(arrby, n2);
                int n4 = ZipUtils.get16(arrby, n2 + 2);
                if (n4 >= 0 && n2 + 4 + n4 <= n) {
                    if (n3 != 21589 && n3 != 1) {
                        this.writeBytes(arrby, n2, n4 + 4);
                    }
                    n2 += n4 + 4;
                    continue;
                }
                this.writeBytes(arrby, n2, n - n2);
                return;
            }
            if (n2 < n) {
                this.writeBytes(arrby, n2, n - n2);
            }
        }
    }

    private void writeInt(long l) throws IOException {
        OutputStream outputStream = this.out;
        outputStream.write((int)(l >>> 0 & 255L));
        outputStream.write((int)(l >>> 8 & 255L));
        outputStream.write((int)(l >>> 16 & 255L));
        outputStream.write((int)(l >>> 24 & 255L));
        this.written += 4L;
    }

    private void writeLOC(XEntry object) throws IOException {
        object = ((XEntry)object).entry;
        int n = ((ZipEntry)object).flag;
        int n2 = 0;
        int n3 = 0;
        int n4 = this.getExtraLen(((ZipEntry)object).extra);
        this.writeInt(67324752L);
        if ((n & 8) == 8) {
            this.writeShort(ZipOutputStream.version((ZipEntry)object));
            this.writeShort(n);
            this.writeShort(((ZipEntry)object).method);
            this.writeInt(((ZipEntry)object).xdostime);
            this.writeInt(0L);
            this.writeInt(0L);
            this.writeInt(0L);
        } else {
            if (((ZipEntry)object).csize < 0xFFFFFFFFL && ((ZipEntry)object).size < 0xFFFFFFFFL) {
                this.writeShort(ZipOutputStream.version((ZipEntry)object));
            } else {
                n3 = 1;
                this.writeShort(45);
            }
            this.writeShort(n);
            this.writeShort(((ZipEntry)object).method);
            this.writeInt(((ZipEntry)object).xdostime);
            this.writeInt(((ZipEntry)object).crc);
            if (n3 != 0) {
                this.writeInt(0xFFFFFFFFL);
                this.writeInt(0xFFFFFFFFL);
                n4 += 20;
                n2 = n3;
            } else {
                this.writeInt(((ZipEntry)object).csize);
                this.writeInt(((ZipEntry)object).size);
                n2 = n3;
            }
        }
        byte[] arrby = this.zc.getBytes(((ZipEntry)object).name);
        this.writeShort(arrby.length);
        int n5 = 0;
        n = 0;
        if (((ZipEntry)object).mtime != null) {
            n5 = 0 + 4;
            n = false | true;
        }
        int n6 = n5;
        n3 = n;
        if (((ZipEntry)object).atime != null) {
            n6 = n5 + 4;
            n3 = n | 2;
        }
        n5 = n6;
        n = n3;
        if (((ZipEntry)object).ctime != null) {
            n5 = n6 + 4;
            n = n3 | 4;
        }
        n3 = n4;
        if (n != 0) {
            n3 = n4 + (n5 + 5);
        }
        this.writeShort(n3);
        this.writeBytes(arrby, 0, arrby.length);
        if (n2 != 0) {
            this.writeShort(1);
            this.writeShort(16);
            this.writeLong(((ZipEntry)object).size);
            this.writeLong(((ZipEntry)object).csize);
        }
        if (n != 0) {
            this.writeShort(21589);
            this.writeShort(n5 + 1);
            this.writeByte(n);
            if (((ZipEntry)object).mtime != null) {
                this.writeInt(ZipUtils.fileTimeToUnixTime(((ZipEntry)object).mtime));
            }
            if (((ZipEntry)object).atime != null) {
                this.writeInt(ZipUtils.fileTimeToUnixTime(((ZipEntry)object).atime));
            }
            if (((ZipEntry)object).ctime != null) {
                this.writeInt(ZipUtils.fileTimeToUnixTime(((ZipEntry)object).ctime));
            }
        }
        this.writeExtra(((ZipEntry)object).extra);
        this.locoff = this.written;
    }

    private void writeLong(long l) throws IOException {
        OutputStream outputStream = this.out;
        outputStream.write((int)(l >>> 0 & 255L));
        outputStream.write((int)(l >>> 8 & 255L));
        outputStream.write((int)(l >>> 16 & 255L));
        outputStream.write((int)(l >>> 24 & 255L));
        outputStream.write((int)(l >>> 32 & 255L));
        outputStream.write((int)(l >>> 40 & 255L));
        outputStream.write((int)(l >>> 48 & 255L));
        outputStream.write((int)(l >>> 56 & 255L));
        this.written += 8L;
    }

    private void writeShort(int n) throws IOException {
        OutputStream outputStream = this.out;
        outputStream.write(n >>> 0 & 255);
        outputStream.write(n >>> 8 & 255);
        this.written += 2L;
    }

    @Override
    public void close() throws IOException {
        if (!this.closed) {
            super.close();
            this.closed = true;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void closeEntry() throws IOException {
        this.ensureOpen();
        Object object = this.current;
        if (object == null) return;
        object = ((XEntry)object).entry;
        int n = ((ZipEntry)object).method;
        if (n != 0) {
            if (n != 8) throw new ZipException("invalid compression method");
            this.def.finish();
            while (!this.def.finished()) {
                this.deflate();
            }
            if ((((ZipEntry)object).flag & 8) == 0) {
                if (((ZipEntry)object).size != this.def.getBytesRead()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("invalid entry size (expected ");
                    stringBuilder.append(((ZipEntry)object).size);
                    stringBuilder.append(" but got ");
                    stringBuilder.append(this.def.getBytesRead());
                    stringBuilder.append(" bytes)");
                    throw new ZipException(stringBuilder.toString());
                }
                if (((ZipEntry)object).csize != this.def.getBytesWritten()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("invalid entry compressed size (expected ");
                    stringBuilder.append(((ZipEntry)object).csize);
                    stringBuilder.append(" but got ");
                    stringBuilder.append(this.def.getBytesWritten());
                    stringBuilder.append(" bytes)");
                    throw new ZipException(stringBuilder.toString());
                }
                if (((ZipEntry)object).crc != this.crc.getValue()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("invalid entry CRC-32 (expected 0x");
                    stringBuilder.append(Long.toHexString(((ZipEntry)object).crc));
                    stringBuilder.append(" but got 0x");
                    stringBuilder.append(Long.toHexString(this.crc.getValue()));
                    stringBuilder.append(")");
                    throw new ZipException(stringBuilder.toString());
                }
            } else {
                ((ZipEntry)object).size = this.def.getBytesRead();
                ((ZipEntry)object).csize = this.def.getBytesWritten();
                ((ZipEntry)object).crc = this.crc.getValue();
                this.writeEXT((ZipEntry)object);
            }
            this.def.reset();
            this.written += ((ZipEntry)object).csize;
        } else {
            if (((ZipEntry)object).size != this.written - this.locoff) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("invalid entry size (expected ");
                stringBuilder.append(((ZipEntry)object).size);
                stringBuilder.append(" but got ");
                stringBuilder.append(this.written - this.locoff);
                stringBuilder.append(" bytes)");
                throw new ZipException(stringBuilder.toString());
            }
            if (((ZipEntry)object).crc != this.crc.getValue()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("invalid entry crc-32 (expected 0x");
                stringBuilder.append(Long.toHexString(((ZipEntry)object).crc));
                stringBuilder.append(" but got 0x");
                stringBuilder.append(Long.toHexString(this.crc.getValue()));
                stringBuilder.append(")");
                throw new ZipException(stringBuilder.toString());
            }
        }
        this.crc.reset();
        this.current = null;
    }

    @Override
    public void finish() throws IOException {
        this.ensureOpen();
        if (this.finished) {
            return;
        }
        if (this.current != null) {
            this.closeEntry();
        }
        long l = this.written;
        Iterator<XEntry> iterator = this.xentries.iterator();
        while (iterator.hasNext()) {
            this.writeCEN(iterator.next());
        }
        this.writeEND(l, this.written - l);
        this.finished = true;
    }

    public void putNextEntry(ZipEntry zipEntry) throws IOException {
        block16 : {
            block17 : {
                block12 : {
                    block14 : {
                        block15 : {
                            block13 : {
                                block10 : {
                                    block11 : {
                                        this.ensureOpen();
                                        if (this.current != null) {
                                            this.closeEntry();
                                        }
                                        if (zipEntry.xdostime == -1L) {
                                            zipEntry.setTime(System.currentTimeMillis());
                                        }
                                        if (zipEntry.method == -1) {
                                            zipEntry.method = this.method;
                                        }
                                        zipEntry.flag = 0;
                                        int n = zipEntry.method;
                                        if (n == 0) break block10;
                                        if (n != 8) break block11;
                                        if (zipEntry.size == -1L || zipEntry.csize == -1L || zipEntry.crc == -1L) {
                                            zipEntry.flag = 8;
                                        }
                                        break block12;
                                    }
                                    throw new ZipException("unsupported compression method");
                                }
                                if (zipEntry.size != -1L) break block13;
                                zipEntry.size = zipEntry.csize;
                                break block14;
                            }
                            if (zipEntry.csize != -1L) break block15;
                            zipEntry.csize = zipEntry.size;
                            break block14;
                        }
                        if (zipEntry.size != zipEntry.csize) break block16;
                    }
                    if (zipEntry.size == -1L || zipEntry.crc == -1L) break block17;
                }
                if (this.names.add(zipEntry.name)) {
                    if (this.zc.isUTF8()) {
                        zipEntry.flag |= 2048;
                    }
                    this.current = new XEntry(zipEntry, this.written);
                    this.xentries.add(this.current);
                    this.writeLOC(this.current);
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("duplicate entry: ");
                stringBuilder.append(zipEntry.name);
                throw new ZipException(stringBuilder.toString());
            }
            throw new ZipException("STORED entry missing size, compressed size, or crc-32");
        }
        throw new ZipException("STORED entry where compressed != uncompressed size");
    }

    public void setComment(String string) {
        if (string != null) {
            this.comment = this.zc.getBytes(string);
            if (this.comment.length > 65535) {
                throw new IllegalArgumentException("ZIP file comment too long.");
            }
        }
    }

    public void setLevel(int n) {
        this.def.setLevel(n);
    }

    public void setMethod(int n) {
        if (n != 8 && n != 0) {
            throw new IllegalArgumentException("invalid compression method");
        }
        this.method = n;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void write(byte[] object, int n, int n2) throws IOException {
        synchronized (this) {
            void var3_7;
            int n3;
            void var2_6;
            this.ensureOpen();
            if (var2_6 >= 0 && var3_7 >= 0 && var2_6 <= (n3 = ((byte[])object).length) - var3_7) {
                if (var3_7 == false) {
                    return;
                }
                if (this.current == null) {
                    ZipException zipException = new ZipException("no current ZIP entry");
                    throw zipException;
                }
                ZipEntry zipEntry = this.current.entry;
                n3 = zipEntry.method;
                if (n3 != 0) {
                    if (n3 != 8) {
                        ZipException zipException = new ZipException("invalid compression method");
                        throw zipException;
                    }
                    super.write((byte[])object, (int)var2_6, (int)var3_7);
                } else {
                    this.written += (long)var3_7;
                    if (this.written - this.locoff > zipEntry.size) {
                        ZipException zipException = new ZipException("attempt to write past end of STORED entry");
                        throw zipException;
                    }
                    this.out.write((byte[])object, (int)var2_6, (int)var3_7);
                }
                this.crc.update((byte[])object, (int)var2_6, (int)var3_7);
                return;
            }
            IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException();
            throw indexOutOfBoundsException;
        }
    }

    private static class XEntry {
        final ZipEntry entry;
        final long offset;

        public XEntry(ZipEntry zipEntry, long l) {
            this.entry = zipEntry;
            this.offset = l;
        }
    }

}

