/*
 * Decompiled with CFR 0.145.
 */
package java.util.zip;

import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

class ZipUtils {
    private static final long WINDOWS_EPOCH_IN_MICROSECONDS = -11644473600000000L;

    ZipUtils() {
    }

    private static long dosToJavaTime(long l) {
        return new Date((int)((l >> 25 & 127L) + 80L), (int)((l >> 21 & 15L) - 1L), (int)(l >> 16 & 31L), (int)(l >> 11 & 31L), (int)(l >> 5 & 63L), (int)(l << 1 & 62L)).getTime();
    }

    public static long extendedDosToJavaTime(long l) {
        return (l >> 32) + ZipUtils.dosToJavaTime(l);
    }

    public static final long fileTimeToUnixTime(FileTime fileTime) {
        return fileTime.to(TimeUnit.SECONDS);
    }

    public static final long fileTimeToWinTime(FileTime fileTime) {
        return (fileTime.to(TimeUnit.MICROSECONDS) + 11644473600000000L) * 10L;
    }

    public static final int get16(byte[] arrby, int n) {
        return Byte.toUnsignedInt(arrby[n]) | Byte.toUnsignedInt(arrby[n + 1]) << 8;
    }

    public static final long get32(byte[] arrby, int n) {
        return ((long)ZipUtils.get16(arrby, n) | (long)ZipUtils.get16(arrby, n + 2) << 16) & 0xFFFFFFFFL;
    }

    public static final long get64(byte[] arrby, int n) {
        return ZipUtils.get32(arrby, n) | ZipUtils.get32(arrby, n + 4) << 32;
    }

    private static long javaToDosTime(long l) {
        Date date = new Date(l);
        int n = date.getYear() + 1900;
        if (n < 1980) {
            return 2162688L;
        }
        return (long)(n - 1980 << 25 | date.getMonth() + 1 << 21 | date.getDate() << 16 | date.getHours() << 11 | date.getMinutes() << 5 | date.getSeconds() >> 1) & 0xFFFFFFFFL;
    }

    public static long javaToExtendedDosTime(long l) {
        long l2 = 2162688L;
        if (l < 0L) {
            return 2162688L;
        }
        long l3 = ZipUtils.javaToDosTime(l);
        l = l3 != 2162688L ? l3 + (l % 2000L << 32) : l2;
        return l;
    }

    public static final FileTime unixTimeToFileTime(long l) {
        return FileTime.from(l, TimeUnit.SECONDS);
    }

    public static final FileTime winTimeToFileTime(long l) {
        return FileTime.from(l / 10L - 11644473600000000L, TimeUnit.MICROSECONDS);
    }
}

