/*
 * Decompiled with CFR 0.145.
 */
package android.util.apk;

import android.util.apk.ByteBufferDataSource;
import android.util.apk.ByteBufferFactory;
import android.util.apk.DataDigester;
import android.util.apk.DataSource;
import android.util.apk.MemoryMappedFileDataSource;
import android.util.apk.SignatureInfo;
import android.util.apk.SignatureNotFoundException;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public abstract class VerityBuilder {
    private static final int CHUNK_SIZE_BYTES = 4096;
    private static final byte[] DEFAULT_SALT = new byte[8];
    private static final int DIGEST_SIZE_BYTES = 32;
    private static final int FSVERITY_HEADER_SIZE_BYTES = 64;
    private static final String JCA_DIGEST_ALGORITHM = "SHA-256";
    private static final int MMAP_REGION_SIZE_BYTES = 1048576;
    private static final int ZIP_EOCD_CENTRAL_DIR_OFFSET_FIELD_OFFSET = 16;
    private static final int ZIP_EOCD_CENTRAL_DIR_OFFSET_FIELD_SIZE = 4;

    private VerityBuilder() {
    }

    private static void assertSigningBlockAlignedAndHasFullPages(SignatureInfo signatureInfo) {
        if (signatureInfo.apkSigningBlockOffset % 4096L == 0L) {
            if ((signatureInfo.centralDirOffset - signatureInfo.apkSigningBlockOffset) % 4096L == 0L) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Size of APK Signing Block is not a multiple of 4096: ");
            stringBuilder.append(signatureInfo.centralDirOffset - signatureInfo.apkSigningBlockOffset);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("APK Signing Block does not start at the page boundary: ");
        stringBuilder.append(signatureInfo.apkSigningBlockOffset);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static int[] calculateVerityLevelOffset(long l) {
        ArrayList<Long> arrayList = new ArrayList<Long>();
        do {
            l = VerityBuilder.divideRoundup(l, 4096L) * 32L;
            arrayList.add(VerityBuilder.divideRoundup(l, 4096L) * 4096L);
        } while (l > 4096L);
        int[] arrn = new int[arrayList.size() + 1];
        arrn[0] = 0;
        for (int i = 0; i < arrayList.size(); ++i) {
            arrn[i + 1] = arrn[i] + Math.toIntExact((Long)arrayList.get(arrayList.size() - i - 1));
        }
        return arrn;
    }

    private static void consumeByChunk(DataDigester dataDigester, DataSource dataSource, int n) throws IOException, DigestException {
        int n2;
        long l = 0L;
        for (long i = dataSource.size(); i > 0L; i -= (long)n2) {
            n2 = (int)Math.min(i, (long)n);
            dataSource.feedIntoDataDigester(dataDigester, l, n2);
            l += (long)n2;
        }
    }

    private static long divideRoundup(long l, long l2) {
        return (l + l2 - 1L) / l2;
    }

    static byte[] generateApkVerity(String object, ByteBufferFactory arrby, SignatureInfo signatureInfo) throws IOException, SignatureNotFoundException, SecurityException, DigestException, NoSuchAlgorithmException {
        object = new RandomAccessFile((String)object, "r");
        try {
            VerityResult verityResult = VerityBuilder.generateVerityTreeInternal((RandomAccessFile)object, (ByteBufferFactory)arrby, signatureInfo, true);
            arrby = VerityBuilder.slice(verityResult.verityData, verityResult.merkleTreeSize, verityResult.verityData.limit());
            VerityBuilder.generateApkVerityFooter((RandomAccessFile)object, signatureInfo, (ByteBuffer)arrby);
            arrby.putInt(arrby.position() + 4);
            verityResult.verityData.limit(verityResult.merkleTreeSize + arrby.position());
            arrby = verityResult.rootHash;
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                try {
                    ((RandomAccessFile)object).close();
                }
                catch (Throwable throwable3) {
                    throwable.addSuppressed(throwable3);
                }
                throw throwable2;
            }
        }
        ((RandomAccessFile)object).close();
        return arrby;
    }

    private static void generateApkVerityDigestAtLeafLevel(RandomAccessFile randomAccessFile, SignatureInfo signatureInfo, byte[] object, ByteBuffer byteBuffer) throws IOException, NoSuchAlgorithmException, DigestException {
        object = new BufferedDigester((byte[])object, byteBuffer);
        VerityBuilder.consumeByChunk((DataDigester)object, new MemoryMappedFileDataSource(randomAccessFile.getFD(), 0L, signatureInfo.apkSigningBlockOffset), 1048576);
        long l = signatureInfo.eocdOffset + 16L;
        VerityBuilder.consumeByChunk((DataDigester)object, new MemoryMappedFileDataSource(randomAccessFile.getFD(), signatureInfo.centralDirOffset, l - signatureInfo.centralDirOffset), 1048576);
        byteBuffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(Math.toIntExact(signatureInfo.apkSigningBlockOffset));
        byteBuffer.flip();
        ((BufferedDigester)object).consume(byteBuffer);
        l = 4L + l;
        VerityBuilder.consumeByChunk((DataDigester)object, new MemoryMappedFileDataSource(randomAccessFile.getFD(), l, randomAccessFile.length() - l), 1048576);
        int n = (int)(randomAccessFile.length() % 4096L);
        if (n != 0) {
            ((BufferedDigester)object).consume(ByteBuffer.allocate(4096 - n));
        }
        ((BufferedDigester)object).assertEmptyBuffer();
        ((BufferedDigester)object).fillUpLastOutputChunk();
    }

    private static ByteBuffer generateApkVerityExtensions(ByteBuffer byteBuffer, long l, long l2, long l3) {
        byteBuffer.putInt(24);
        byteBuffer.putShort((short)1);
        VerityBuilder.skip(byteBuffer, 2);
        byteBuffer.putLong(l);
        byteBuffer.putLong(l2);
        byteBuffer.putInt(20);
        byteBuffer.putShort((short)2);
        VerityBuilder.skip(byteBuffer, 2);
        byteBuffer.putLong(16L + l3);
        byteBuffer.putInt(Math.toIntExact(l));
        int n = 4;
        if (4 == 8) {
            n = 0;
        }
        VerityBuilder.skip(byteBuffer, n);
        return byteBuffer;
    }

    static void generateApkVerityFooter(RandomAccessFile randomAccessFile, SignatureInfo signatureInfo, ByteBuffer byteBuffer) throws IOException {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        VerityBuilder.generateApkVerityHeader(byteBuffer, randomAccessFile.length(), DEFAULT_SALT);
        long l = signatureInfo.centralDirOffset;
        long l2 = signatureInfo.apkSigningBlockOffset;
        VerityBuilder.generateApkVerityExtensions(byteBuffer, signatureInfo.apkSigningBlockOffset, l - l2, signatureInfo.eocdOffset);
    }

    private static ByteBuffer generateApkVerityHeader(ByteBuffer byteBuffer, long l, byte[] arrby) {
        if (arrby.length == 8) {
            byteBuffer.put("TrueBrew".getBytes());
            byteBuffer.put((byte)1);
            byteBuffer.put((byte)0);
            byteBuffer.put((byte)12);
            byteBuffer.put((byte)7);
            byteBuffer.putShort((short)1);
            byteBuffer.putShort((short)1);
            byteBuffer.putInt(0);
            byteBuffer.putInt(0);
            byteBuffer.putLong(l);
            byteBuffer.put((byte)2);
            byteBuffer.put((byte)0);
            byteBuffer.put(arrby);
            VerityBuilder.skip(byteBuffer, 22);
            return byteBuffer;
        }
        throw new IllegalArgumentException("salt is not 8 bytes long");
    }

    static byte[] generateApkVerityRootHash(RandomAccessFile object, ByteBuffer byteBuffer, SignatureInfo signatureInfo) throws NoSuchAlgorithmException, DigestException, IOException {
        VerityBuilder.assertSigningBlockAlignedAndHasFullPages(signatureInfo);
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(4096).order(ByteOrder.LITTLE_ENDIAN);
        VerityBuilder.generateApkVerityFooter((RandomAccessFile)object, signatureInfo, byteBuffer2);
        byteBuffer2.flip();
        object = MessageDigest.getInstance(JCA_DIGEST_ALGORITHM);
        ((MessageDigest)object).update(byteBuffer2);
        ((MessageDigest)object).update(byteBuffer);
        return ((MessageDigest)object).digest();
    }

    public static VerityResult generateApkVerityTree(RandomAccessFile randomAccessFile, SignatureInfo signatureInfo, ByteBufferFactory byteBufferFactory) throws IOException, SecurityException, NoSuchAlgorithmException, DigestException {
        return VerityBuilder.generateVerityTreeInternal(randomAccessFile, byteBufferFactory, signatureInfo, true);
    }

    private static void generateFsVerityDigestAtLeafLevel(RandomAccessFile randomAccessFile, ByteBuffer object) throws IOException, NoSuchAlgorithmException, DigestException {
        object = new BufferedDigester(null, (ByteBuffer)object);
        VerityBuilder.consumeByChunk((DataDigester)object, new MemoryMappedFileDataSource(randomAccessFile.getFD(), 0L, randomAccessFile.length()), 1048576);
        int n = (int)(randomAccessFile.length() % 4096L);
        if (n != 0) {
            ((BufferedDigester)object).consume(ByteBuffer.allocate(4096 - n));
        }
        ((BufferedDigester)object).assertEmptyBuffer();
        ((BufferedDigester)object).fillUpLastOutputChunk();
    }

    public static VerityResult generateFsVerityTree(RandomAccessFile randomAccessFile, ByteBufferFactory byteBufferFactory) throws IOException, SecurityException, NoSuchAlgorithmException, DigestException {
        return VerityBuilder.generateVerityTreeInternal(randomAccessFile, byteBufferFactory, null, false);
    }

    private static VerityResult generateVerityTreeInternal(RandomAccessFile randomAccessFile, ByteBufferFactory object, SignatureInfo signatureInfo, boolean bl) throws IOException, SecurityException, NoSuchAlgorithmException, DigestException {
        long l = randomAccessFile.length();
        if (bl) {
            l -= signatureInfo.centralDirOffset - signatureInfo.apkSigningBlockOffset;
        }
        int[] arrn = VerityBuilder.calculateVerityLevelOffset(l);
        int n = arrn[arrn.length - 1];
        ByteBuffer byteBuffer = object.create(n + 4096);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer byteBuffer2 = VerityBuilder.slice(byteBuffer, 0, n);
        object = bl ? DEFAULT_SALT : null;
        return new VerityResult(byteBuffer, n, VerityBuilder.generateVerityTreeInternal(randomAccessFile, signatureInfo, object, arrn, byteBuffer2, bl));
    }

    private static byte[] generateVerityTreeInternal(RandomAccessFile object, SignatureInfo object2, byte[] arrby, int[] arrn, ByteBuffer byteBuffer, boolean bl) throws IOException, NoSuchAlgorithmException, DigestException {
        if (bl) {
            VerityBuilder.assertSigningBlockAlignedAndHasFullPages((SignatureInfo)object2);
            VerityBuilder.generateApkVerityDigestAtLeafLevel((RandomAccessFile)object, (SignatureInfo)object2, arrby, VerityBuilder.slice(byteBuffer, arrn[arrn.length - 2], arrn[arrn.length - 1]));
        } else {
            VerityBuilder.generateFsVerityDigestAtLeafLevel((RandomAccessFile)object, VerityBuilder.slice(byteBuffer, arrn[arrn.length - 2], arrn[arrn.length - 1]));
        }
        for (int i = arrn.length - 3; i >= 0; --i) {
            object = VerityBuilder.slice(byteBuffer, arrn[i + 1], arrn[i + 2]);
            object2 = VerityBuilder.slice(byteBuffer, arrn[i], arrn[i + 1]);
            object = new ByteBufferDataSource((ByteBuffer)object);
            object2 = new BufferedDigester(arrby, (ByteBuffer)object2);
            VerityBuilder.consumeByChunk((DataDigester)object2, (DataSource)object, 4096);
            ((BufferedDigester)object2).assertEmptyBuffer();
            ((BufferedDigester)object2).fillUpLastOutputChunk();
        }
        object = new byte[32];
        object2 = new BufferedDigester(arrby, ByteBuffer.wrap(object));
        ((BufferedDigester)object2).consume(VerityBuilder.slice(byteBuffer, 0, 4096));
        ((BufferedDigester)object2).assertEmptyBuffer();
        return object;
    }

    private static void skip(ByteBuffer byteBuffer, int n) {
        byteBuffer.position(byteBuffer.position() + n);
    }

    private static ByteBuffer slice(ByteBuffer byteBuffer, int n, int n2) {
        byteBuffer = byteBuffer.duplicate();
        byteBuffer.position(0);
        byteBuffer.limit(n2);
        byteBuffer.position(n);
        return byteBuffer.slice();
    }

    private static class BufferedDigester
    implements DataDigester {
        private static final int BUFFER_SIZE = 4096;
        private int mBytesDigestedSinceReset;
        private final byte[] mDigestBuffer = new byte[32];
        private final MessageDigest mMd;
        private final ByteBuffer mOutput;
        private final byte[] mSalt;

        private BufferedDigester(byte[] arrby, ByteBuffer byteBuffer) throws NoSuchAlgorithmException {
            this.mSalt = arrby;
            this.mOutput = byteBuffer.slice();
            this.mMd = MessageDigest.getInstance(VerityBuilder.JCA_DIGEST_ALGORITHM);
            arrby = this.mSalt;
            if (arrby != null) {
                this.mMd.update(arrby);
            }
            this.mBytesDigestedSinceReset = 0;
        }

        private void fillUpLastOutputChunk() {
            int n = this.mOutput.position() % 4096;
            if (n == 0) {
                return;
            }
            this.mOutput.put(ByteBuffer.allocate(4096 - n));
        }

        public void assertEmptyBuffer() throws DigestException {
            if (this.mBytesDigestedSinceReset == 0) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Buffer is not empty: ");
            stringBuilder.append(this.mBytesDigestedSinceReset);
            throw new IllegalStateException(stringBuilder.toString());
        }

        @Override
        public void consume(ByteBuffer byteBuffer) throws DigestException {
            int n = byteBuffer.position();
            int n2 = byteBuffer.remaining();
            while (n2 > 0) {
                int n3 = Math.min(n2, 4096 - this.mBytesDigestedSinceReset);
                byteBuffer.limit(byteBuffer.position() + n3);
                this.mMd.update(byteBuffer);
                n += n3;
                n2 -= n3;
                this.mBytesDigestedSinceReset += n3;
                if (this.mBytesDigestedSinceReset != 4096) continue;
                MessageDigest messageDigest = this.mMd;
                byte[] arrby = this.mDigestBuffer;
                messageDigest.digest(arrby, 0, arrby.length);
                this.mOutput.put(this.mDigestBuffer);
                arrby = this.mSalt;
                if (arrby != null) {
                    this.mMd.update(arrby);
                }
                this.mBytesDigestedSinceReset = 0;
            }
        }
    }

    public static class VerityResult {
        public final int merkleTreeSize;
        public final byte[] rootHash;
        public final ByteBuffer verityData;

        private VerityResult(ByteBuffer byteBuffer, int n, byte[] arrby) {
            this.verityData = byteBuffer;
            this.merkleTreeSize = n;
            this.rootHash = arrby;
        }
    }

}

