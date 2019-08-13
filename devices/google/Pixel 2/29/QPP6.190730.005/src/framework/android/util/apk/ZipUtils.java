/*
 * Decompiled with CFR 0.145.
 */
package android.util.apk;

import android.util.Pair;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

abstract class ZipUtils {
    private static final int UINT16_MAX_VALUE = 65535;
    private static final int ZIP64_EOCD_LOCATOR_SIG_REVERSE_BYTE_ORDER = 1347094023;
    private static final int ZIP64_EOCD_LOCATOR_SIZE = 20;
    private static final int ZIP_EOCD_CENTRAL_DIR_OFFSET_FIELD_OFFSET = 16;
    private static final int ZIP_EOCD_CENTRAL_DIR_SIZE_FIELD_OFFSET = 12;
    private static final int ZIP_EOCD_COMMENT_LENGTH_FIELD_OFFSET = 20;
    private static final int ZIP_EOCD_REC_MIN_SIZE = 22;
    private static final int ZIP_EOCD_REC_SIG = 101010256;

    private ZipUtils() {
    }

    private static void assertByteOrderLittleEndian(ByteBuffer byteBuffer) {
        if (byteBuffer.order() == ByteOrder.LITTLE_ENDIAN) {
            return;
        }
        throw new IllegalArgumentException("ByteBuffer byte order must be little endian");
    }

    private static int findZipEndOfCentralDirectoryRecord(ByteBuffer byteBuffer) {
        ZipUtils.assertByteOrderLittleEndian(byteBuffer);
        int n = byteBuffer.capacity();
        if (n < 22) {
            return -1;
        }
        int n2 = Math.min(n - 22, 65535);
        for (int i = 0; i <= n2; ++i) {
            int n3 = n - 22 - i;
            if (byteBuffer.getInt(n3) != 101010256 || ZipUtils.getUnsignedInt16(byteBuffer, n3 + 20) != i) continue;
            return n3;
        }
        return -1;
    }

    static Pair<ByteBuffer, Long> findZipEndOfCentralDirectoryRecord(RandomAccessFile randomAccessFile) throws IOException {
        if (randomAccessFile.length() < 22L) {
            return null;
        }
        Pair<ByteBuffer, Long> pair = ZipUtils.findZipEndOfCentralDirectoryRecord(randomAccessFile, 0);
        if (pair != null) {
            return pair;
        }
        return ZipUtils.findZipEndOfCentralDirectoryRecord(randomAccessFile, 65535);
    }

    private static Pair<ByteBuffer, Long> findZipEndOfCentralDirectoryRecord(RandomAccessFile object, int n) throws IOException {
        if (n >= 0 && n <= 65535) {
            long l = ((RandomAccessFile)object).length();
            if (l < 22L) {
                return null;
            }
            ByteBuffer byteBuffer = ByteBuffer.allocate((int)Math.min((long)n, l - 22L) + 22);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            ((RandomAccessFile)object).seek(l -= (long)byteBuffer.capacity());
            ((RandomAccessFile)object).readFully(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.capacity());
            n = ZipUtils.findZipEndOfCentralDirectoryRecord(byteBuffer);
            if (n == -1) {
                return null;
            }
            byteBuffer.position(n);
            object = byteBuffer.slice();
            ((ByteBuffer)object).order(ByteOrder.LITTLE_ENDIAN);
            return Pair.create(object, (long)n + l);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("maxCommentSize: ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private static int getUnsignedInt16(ByteBuffer byteBuffer, int n) {
        return byteBuffer.getShort(n) & 65535;
    }

    private static long getUnsignedInt32(ByteBuffer byteBuffer, int n) {
        return (long)byteBuffer.getInt(n) & 0xFFFFFFFFL;
    }

    public static long getZipEocdCentralDirectoryOffset(ByteBuffer byteBuffer) {
        ZipUtils.assertByteOrderLittleEndian(byteBuffer);
        return ZipUtils.getUnsignedInt32(byteBuffer, byteBuffer.position() + 16);
    }

    public static long getZipEocdCentralDirectorySizeBytes(ByteBuffer byteBuffer) {
        ZipUtils.assertByteOrderLittleEndian(byteBuffer);
        return ZipUtils.getUnsignedInt32(byteBuffer, byteBuffer.position() + 12);
    }

    public static final boolean isZip64EndOfCentralDirectoryLocatorPresent(RandomAccessFile randomAccessFile, long l) throws IOException {
        boolean bl = false;
        if ((l -= 20L) < 0L) {
            return false;
        }
        randomAccessFile.seek(l);
        if (randomAccessFile.readInt() == 1347094023) {
            bl = true;
        }
        return bl;
    }

    private static void setUnsignedInt32(ByteBuffer object, int n, long l) {
        if (l >= 0L && l <= 0xFFFFFFFFL) {
            ((ByteBuffer)object).putInt(((Buffer)object).position() + n, (int)l);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("uint32 value of out range: ");
        ((StringBuilder)object).append(l);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static void setZipEocdCentralDirectoryOffset(ByteBuffer byteBuffer, long l) {
        ZipUtils.assertByteOrderLittleEndian(byteBuffer);
        ZipUtils.setUnsignedInt32(byteBuffer, byteBuffer.position() + 16, l);
    }
}

