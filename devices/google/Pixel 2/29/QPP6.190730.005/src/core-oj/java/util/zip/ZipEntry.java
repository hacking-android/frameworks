/*
 * Decompiled with CFR 0.145.
 */
package java.util.zip;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.FileTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipConstants;
import java.util.zip.ZipUtils;

public class ZipEntry
implements ZipConstants,
Cloneable {
    public static final int DEFLATED = 8;
    static final long DOSTIME_BEFORE_1980 = 2162688L;
    public static final int STORED = 0;
    public static final long UPPER_DOSTIME_BOUND = 4036608000000L;
    FileTime atime;
    String comment;
    long crc = -1L;
    long csize = -1L;
    FileTime ctime;
    long dataOffset;
    byte[] extra;
    int flag = 0;
    int method = -1;
    FileTime mtime;
    String name;
    long size = -1L;
    long xdostime = -1L;

    ZipEntry() {
    }

    public ZipEntry(String string) {
        Objects.requireNonNull(string, "name");
        if (string.getBytes(StandardCharsets.UTF_8).length <= 65535) {
            this.name = string;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" too long: ");
        stringBuilder.append(string.getBytes(StandardCharsets.UTF_8).length);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public ZipEntry(String string, String string2, long l, long l2, long l3, int n, int n2, byte[] arrby, long l4) {
        this.name = string;
        this.comment = string2;
        this.crc = l;
        this.csize = l2;
        this.size = l3;
        this.method = n;
        this.xdostime = n2;
        this.dataOffset = l4;
        this.setExtra0(arrby, false);
    }

    public ZipEntry(ZipEntry zipEntry) {
        Objects.requireNonNull(zipEntry, "entry");
        this.name = zipEntry.name;
        this.xdostime = zipEntry.xdostime;
        this.mtime = zipEntry.mtime;
        this.atime = zipEntry.atime;
        this.ctime = zipEntry.ctime;
        this.crc = zipEntry.crc;
        this.size = zipEntry.size;
        this.csize = zipEntry.csize;
        this.method = zipEntry.method;
        this.flag = zipEntry.flag;
        this.extra = zipEntry.extra;
        this.comment = zipEntry.comment;
        this.dataOffset = zipEntry.dataOffset;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Object clone() {
        try {
            ZipEntry zipEntry = (ZipEntry)super.clone();
            byte[] arrby = this.extra == null ? null : (byte[])this.extra.clone();
            zipEntry.extra = arrby;
            return zipEntry;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException);
        }
    }

    public String getComment() {
        return this.comment;
    }

    public long getCompressedSize() {
        return this.csize;
    }

    public long getCrc() {
        return this.crc;
    }

    public FileTime getCreationTime() {
        return this.ctime;
    }

    public long getDataOffset() {
        return this.dataOffset;
    }

    public byte[] getExtra() {
        return this.extra;
    }

    public FileTime getLastAccessTime() {
        return this.atime;
    }

    public FileTime getLastModifiedTime() {
        FileTime fileTime = this.mtime;
        if (fileTime != null) {
            return fileTime;
        }
        if (this.xdostime == -1L) {
            return null;
        }
        return FileTime.from(this.getTime(), TimeUnit.MILLISECONDS);
    }

    public int getMethod() {
        return this.method;
    }

    public String getName() {
        return this.name;
    }

    public long getSize() {
        return this.size;
    }

    public long getTime() {
        FileTime fileTime = this.mtime;
        if (fileTime != null) {
            return fileTime.toMillis();
        }
        long l = this.xdostime;
        long l2 = -1L;
        if (l != -1L) {
            l2 = ZipUtils.extendedDosToJavaTime(l);
        }
        return l2;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public boolean isDirectory() {
        return this.name.endsWith("/");
    }

    public void setComment(String string) {
        if (string != null && string.getBytes(StandardCharsets.UTF_8).length > 65535) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" too long: ");
            stringBuilder.append(string.getBytes(StandardCharsets.UTF_8).length);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.comment = string;
    }

    public void setCompressedSize(long l) {
        this.csize = l;
    }

    public void setCrc(long l) {
        if (l >= 0L && l <= 0xFFFFFFFFL) {
            this.crc = l;
            return;
        }
        throw new IllegalArgumentException("invalid entry crc-32");
    }

    public ZipEntry setCreationTime(FileTime fileTime) {
        this.ctime = Objects.requireNonNull(fileTime, "creationTime");
        return this;
    }

    public void setExtra(byte[] arrby) {
        this.setExtra0(arrby, false);
    }

    void setExtra0(byte[] arrby, boolean bl) {
        if (arrby != null) {
            if (arrby.length <= 65535) {
                int n = 0;
                int n2 = arrby.length;
                while (n + 4 < n2) {
                    int n3 = ZipUtils.get16(arrby, n);
                    int n4 = n + 4;
                    int n5 = ZipUtils.get16(arrby, n + 2);
                    if (n4 + n5 > n2) break;
                    if (n3 != 1) {
                        if (n3 != 10) {
                            if (n3 == 21589) {
                                int n6 = Byte.toUnsignedInt(arrby[n4]);
                                n = n3 = 1;
                                if ((n6 & 1) != 0) {
                                    n = n3;
                                    if (1 + 4 <= n5) {
                                        this.mtime = ZipUtils.unixTimeToFileTime(ZipUtils.get32(arrby, n4 + 1));
                                        n = 1 + 4;
                                    }
                                }
                                n3 = n;
                                if ((n6 & 2) != 0) {
                                    n3 = n;
                                    if (n + 4 <= n5) {
                                        this.atime = ZipUtils.unixTimeToFileTime(ZipUtils.get32(arrby, n4 + n));
                                        n3 = n + 4;
                                    }
                                }
                                if ((n6 & 4) != 0 && n3 + 4 <= n5) {
                                    this.ctime = ZipUtils.unixTimeToFileTime(ZipUtils.get32(arrby, n4 + n3));
                                }
                            }
                        } else if (n5 >= 32 && ZipUtils.get16(arrby, n = n4 + 4) == 1 && ZipUtils.get16(arrby, n + 2) == 24) {
                            this.mtime = ZipUtils.winTimeToFileTime(ZipUtils.get64(arrby, n + 4));
                            this.atime = ZipUtils.winTimeToFileTime(ZipUtils.get64(arrby, n + 12));
                            this.ctime = ZipUtils.winTimeToFileTime(ZipUtils.get64(arrby, n + 20));
                        }
                    } else if (bl && n5 >= 16) {
                        this.size = ZipUtils.get64(arrby, n4);
                        this.csize = ZipUtils.get64(arrby, n4 + 8);
                    }
                    n = n4 + n5;
                }
            } else {
                throw new IllegalArgumentException("invalid extra field length");
            }
        }
        this.extra = arrby;
    }

    public ZipEntry setLastAccessTime(FileTime fileTime) {
        this.atime = Objects.requireNonNull(fileTime, "lastAccessTime");
        return this;
    }

    public ZipEntry setLastModifiedTime(FileTime fileTime) {
        this.mtime = Objects.requireNonNull(fileTime, "lastModifiedTime");
        this.xdostime = ZipUtils.javaToExtendedDosTime(fileTime.to(TimeUnit.MILLISECONDS));
        return this;
    }

    public void setMethod(int n) {
        if (n != 0 && n != 8) {
            throw new IllegalArgumentException("invalid compression method");
        }
        this.method = n;
    }

    public void setSize(long l) {
        if (l >= 0L) {
            this.size = l;
            return;
        }
        throw new IllegalArgumentException("invalid entry size");
    }

    public void setTime(long l) {
        this.xdostime = ZipUtils.javaToExtendedDosTime(l);
        this.mtime = this.xdostime != 2162688L && l <= 4036608000000L ? null : FileTime.from(l, TimeUnit.MILLISECONDS);
    }

    public String toString() {
        return this.getName();
    }
}

