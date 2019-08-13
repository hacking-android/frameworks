/*
 * Decompiled with CFR 0.145.
 */
package android.util.apk;

import android.util.ArrayMap;
import android.util.Pair;
import android.util.apk.ByteBufferDataSource;
import android.util.apk.ByteBufferFactory;
import android.util.apk.DataDigester;
import android.util.apk.DataSource;
import android.util.apk.MemoryMappedFileDataSource;
import android.util.apk.SignatureInfo;
import android.util.apk.SignatureNotFoundException;
import android.util.apk.VerityBuilder;
import android.util.apk.ZipUtils;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

final class ApkSigningBlockUtils {
    private static final long APK_SIG_BLOCK_MAGIC_HI = 3617552046287187010L;
    private static final long APK_SIG_BLOCK_MAGIC_LO = 2334950737559900225L;
    private static final int APK_SIG_BLOCK_MIN_SIZE = 32;
    private static final int CHUNK_SIZE_BYTES = 1048576;
    static final int CONTENT_DIGEST_CHUNKED_SHA256 = 1;
    static final int CONTENT_DIGEST_CHUNKED_SHA512 = 2;
    static final int CONTENT_DIGEST_VERITY_CHUNKED_SHA256 = 3;
    static final int SIGNATURE_DSA_WITH_SHA256 = 769;
    static final int SIGNATURE_ECDSA_WITH_SHA256 = 513;
    static final int SIGNATURE_ECDSA_WITH_SHA512 = 514;
    static final int SIGNATURE_RSA_PKCS1_V1_5_WITH_SHA256 = 259;
    static final int SIGNATURE_RSA_PKCS1_V1_5_WITH_SHA512 = 260;
    static final int SIGNATURE_RSA_PSS_WITH_SHA256 = 257;
    static final int SIGNATURE_RSA_PSS_WITH_SHA512 = 258;
    static final int SIGNATURE_VERITY_DSA_WITH_SHA256 = 1061;
    static final int SIGNATURE_VERITY_ECDSA_WITH_SHA256 = 1059;
    static final int SIGNATURE_VERITY_RSA_PKCS1_V1_5_WITH_SHA256 = 1057;

    private ApkSigningBlockUtils() {
    }

    private static void checkByteOrderLittleEndian(ByteBuffer byteBuffer) {
        if (byteBuffer.order() == ByteOrder.LITTLE_ENDIAN) {
            return;
        }
        throw new IllegalArgumentException("ByteBuffer byte order must be little endian");
    }

    private static int compareContentDigestAlgorithm(int n, int n2) {
        if (n != 1) {
            if (n != 2) {
                if (n == 3) {
                    if (n2 != 1) {
                        if (n2 != 2) {
                            if (n2 == 3) {
                                return 0;
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unknown digestAlgorithm2: ");
                            stringBuilder.append(n2);
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                        return -1;
                    }
                    return 1;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown digestAlgorithm1: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 != 3) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unknown digestAlgorithm2: ");
                        stringBuilder.append(n2);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                } else {
                    return 0;
                }
            }
            return 1;
        }
        if (n2 != 1) {
            if (n2 != 2 && n2 != 3) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown digestAlgorithm2: ");
                stringBuilder.append(n2);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            return -1;
        }
        return 0;
    }

    static int compareSignatureAlgorithm(int n, int n2) {
        return ApkSigningBlockUtils.compareContentDigestAlgorithm(ApkSigningBlockUtils.getSignatureAlgorithmContentDigestAlgorithm(n), ApkSigningBlockUtils.getSignatureAlgorithmContentDigestAlgorithm(n2));
    }

    private static byte[][] computeContentDigestsPer1MbChunk(int[] object, DataSource[] object2) throws DigestException {
        int n;
        Object object3 = object;
        Object object4 = object2;
        int n2 = ((DataSource[])object4).length;
        int n3 = 0;
        long l = 0L;
        for (n = 0; n < n2; ++n) {
            l += ApkSigningBlockUtils.getChunkCount(object4[n].size());
        }
        if (l < 0x1FFFFFL) {
            Object object5;
            Object object6;
            int n4 = (int)l;
            byte[][] arrarrby = new byte[((int[])object3).length][];
            for (n = 0; n < ((int[])object3).length; ++n) {
                object5 = new byte[n4 * ApkSigningBlockUtils.getContentDigestAlgorithmOutputSizeBytes(object3[n]) + 5];
                object5[0] = (byte)90;
                ApkSigningBlockUtils.setUnsignedInt32LittleEndian(n4, (byte[])object5, 1);
                arrarrby[n] = object5;
            }
            object5 = new byte[5];
            object5[0] = (byte)-91;
            int n5 = 0;
            Object object7 = new MessageDigest[((int[])object3).length];
            for (n = 0; n < ((int[])object3).length; ++n) {
                object6 = ApkSigningBlockUtils.getContentDigestAlgorithmJcaDigestAlgorithm(object3[n]);
                try {
                    object7[n] = MessageDigest.getInstance((String)object6);
                    continue;
                }
                catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append((String)object6);
                    ((StringBuilder)object2).append(" digest not supported");
                    throw new RuntimeException(((StringBuilder)object2).toString(), noSuchAlgorithmException);
                }
            }
            MultipleDigestDataDigester multipleDigestDataDigester = new MultipleDigestDataDigester((MessageDigest[])object7);
            n2 = ((DataSource[])object4).length;
            int n6 = 0;
            object4 = object7;
            n = n5;
            while (n3 < n2) {
                object6 = object2[n3];
                long l2 = object6.size();
                long l3 = 0L;
                object7 = object3;
                while (l2 > 0L) {
                    int n7 = (int)Math.min(l2, 0x100000L);
                    ApkSigningBlockUtils.setUnsignedInt32LittleEndian(n7, (byte[])object5, 1);
                    for (n5 = 0; n5 < ((Object[])object4).length; ++n5) {
                        ((MessageDigest)object4[n5]).update((byte[])object5);
                    }
                    try {
                        object6.feedIntoDataDigester(multipleDigestDataDigester, l3, n7);
                        object3 = object4;
                        object4 = object6;
                    }
                    catch (IOException iOException) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Failed to digest chunk #");
                        ((StringBuilder)object2).append(n);
                        ((StringBuilder)object2).append(" of section #");
                        ((StringBuilder)object2).append(n6);
                        throw new DigestException(((StringBuilder)object2).toString(), iOException);
                    }
                    for (n5 = 0; n5 < ((Object)object).length; ++n5) {
                        Object object8 = object[n5];
                        object7 = object3[n5];
                        object6 = arrarrby[n5];
                        int n8 = ((MessageDigest)object7).digest((byte[])object6, n * (object8 = (Object)ApkSigningBlockUtils.getContentDigestAlgorithmOutputSizeBytes((int)object8)) + 5, (int)object8);
                        if (n8 == object8) {
                            continue;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Unexpected output size of ");
                        ((StringBuilder)object).append(((MessageDigest)object7).getAlgorithm());
                        ((StringBuilder)object).append(" digest: ");
                        ((StringBuilder)object).append(n8);
                        throw new RuntimeException(((StringBuilder)object).toString());
                    }
                    l3 += (long)n7;
                    l2 -= (long)n7;
                    ++n;
                    object7 = object;
                    object6 = object4;
                    object4 = object3;
                }
                ++n6;
                ++n3;
                object3 = object7;
            }
            object2 = new byte[((int[])object3).length][];
            for (n = 0; n < ((int[])object3).length; ++n) {
                n2 = object3[n];
                object5 = arrarrby[n];
                object = ApkSigningBlockUtils.getContentDigestAlgorithmJcaDigestAlgorithm(n2);
                try {
                    object4 = MessageDigest.getInstance((String)object);
                    object2[n] = ((MessageDigest)object4).digest((byte[])object5);
                    continue;
                }
                catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    object5 = new StringBuilder();
                    ((StringBuilder)object5).append((String)object);
                    ((StringBuilder)object5).append(" digest not supported");
                    throw new RuntimeException(((StringBuilder)object5).toString(), noSuchAlgorithmException);
                }
            }
            return object2;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Too many chunks: ");
        ((StringBuilder)object).append(l);
        throw new DigestException(((StringBuilder)object).toString());
    }

    static ByteBuffer findApkSignatureSchemeBlock(ByteBuffer object, int n) throws SignatureNotFoundException {
        ApkSigningBlockUtils.checkByteOrderLittleEndian((ByteBuffer)object);
        object = ApkSigningBlockUtils.sliceFromTo((ByteBuffer)object, 8, ((Buffer)object).capacity() - 24);
        int n2 = 0;
        while (((Buffer)object).hasRemaining()) {
            ++n2;
            if (((Buffer)object).remaining() >= 8) {
                long l = ((ByteBuffer)object).getLong();
                if (l >= 4L && l <= Integer.MAX_VALUE) {
                    int n3 = (int)l;
                    int n4 = ((Buffer)object).position();
                    if (n3 <= ((Buffer)object).remaining()) {
                        if (((ByteBuffer)object).getInt() == n) {
                            return ApkSigningBlockUtils.getByteBuffer((ByteBuffer)object, n3 - 4);
                        }
                        ((Buffer)object).position(n4 + n3);
                        continue;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("APK Signing Block entry #");
                    stringBuilder.append(n2);
                    stringBuilder.append(" size out of range: ");
                    stringBuilder.append(n3);
                    stringBuilder.append(", available: ");
                    stringBuilder.append(((Buffer)object).remaining());
                    throw new SignatureNotFoundException(stringBuilder.toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("APK Signing Block entry #");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(" size out of range: ");
                ((StringBuilder)object).append(l);
                throw new SignatureNotFoundException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Insufficient data to read size of APK Signing Block entry #");
            ((StringBuilder)object).append(n2);
            throw new SignatureNotFoundException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("No block with ID ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" in APK Signing Block.");
        throw new SignatureNotFoundException(((StringBuilder)object).toString());
    }

    static Pair<ByteBuffer, Long> findApkSigningBlock(RandomAccessFile object, long l) throws IOException, SignatureNotFoundException {
        if (l >= 32L) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(24);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            ((RandomAccessFile)object).seek(l - (long)byteBuffer.capacity());
            ((RandomAccessFile)object).readFully(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.capacity());
            if (byteBuffer.getLong(8) == 2334950737559900225L && byteBuffer.getLong(16) == 3617552046287187010L) {
                long l2 = byteBuffer.getLong(0);
                if (l2 >= (long)byteBuffer.capacity() && l2 <= 0x7FFFFFF7L) {
                    int n = (int)(8L + l2);
                    if ((l -= (long)n) >= 0L) {
                        byteBuffer = ByteBuffer.allocate(n);
                        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                        ((RandomAccessFile)object).seek(l);
                        ((RandomAccessFile)object).readFully(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.capacity());
                        long l3 = byteBuffer.getLong(0);
                        if (l3 == l2) {
                            return Pair.create(byteBuffer, l);
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("APK Signing Block sizes in header and footer do not match: ");
                        ((StringBuilder)object).append(l3);
                        ((StringBuilder)object).append(" vs ");
                        ((StringBuilder)object).append(l2);
                        throw new SignatureNotFoundException(((StringBuilder)object).toString());
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("APK Signing Block offset out of range: ");
                    ((StringBuilder)object).append(l);
                    throw new SignatureNotFoundException(((StringBuilder)object).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("APK Signing Block size out of range: ");
                ((StringBuilder)object).append(l2);
                throw new SignatureNotFoundException(((StringBuilder)object).toString());
            }
            throw new SignatureNotFoundException("No APK Signing Block before ZIP Central Directory");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("APK too small for APK Signing Block. ZIP Central Directory offset: ");
        ((StringBuilder)object).append(l);
        throw new SignatureNotFoundException(((StringBuilder)object).toString());
    }

    static SignatureInfo findSignature(RandomAccessFile object, int n) throws IOException, SignatureNotFoundException {
        Object object2 = ApkSigningBlockUtils.getEocd((RandomAccessFile)object);
        ByteBuffer byteBuffer = (ByteBuffer)((Pair)object2).first;
        long l = (Long)((Pair)object2).second;
        if (!ZipUtils.isZip64EndOfCentralDirectoryLocatorPresent((RandomAccessFile)object, l)) {
            long l2 = ApkSigningBlockUtils.getCentralDirOffset(byteBuffer, l);
            object = ApkSigningBlockUtils.findApkSigningBlock((RandomAccessFile)object, l2);
            object2 = (ByteBuffer)((Pair)object).first;
            long l3 = (Long)((Pair)object).second;
            return new SignatureInfo(ApkSigningBlockUtils.findApkSignatureSchemeBlock((ByteBuffer)object2, n), l3, l2, l, byteBuffer);
        }
        throw new SignatureNotFoundException("ZIP64 APK not supported");
    }

    static ByteBuffer getByteBuffer(ByteBuffer object, int n) throws BufferUnderflowException {
        if (n >= 0) {
            int n2 = ((Buffer)object).limit();
            int n3 = ((Buffer)object).position();
            n = n3 + n;
            if (n >= n3 && n <= n2) {
                ((Buffer)object).limit(n);
                try {
                    ByteBuffer byteBuffer = ((ByteBuffer)object).slice();
                    byteBuffer.order(((ByteBuffer)object).order());
                    ((Buffer)object).position(n);
                    return byteBuffer;
                }
                finally {
                    ((Buffer)object).limit(n2);
                }
            }
            throw new BufferUnderflowException();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("size: ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    static long getCentralDirOffset(ByteBuffer object, long l) throws SignatureNotFoundException {
        long l2 = ZipUtils.getZipEocdCentralDirectoryOffset((ByteBuffer)object);
        if (l2 <= l) {
            if (l2 + ZipUtils.getZipEocdCentralDirectorySizeBytes((ByteBuffer)object) == l) {
                return l2;
            }
            throw new SignatureNotFoundException("ZIP Central Directory is not immediately followed by End of Central Directory");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("ZIP Central Directory offset out of range: ");
        ((StringBuilder)object).append(l2);
        ((StringBuilder)object).append(". ZIP End of Central Directory offset: ");
        ((StringBuilder)object).append(l);
        throw new SignatureNotFoundException(((StringBuilder)object).toString());
    }

    private static long getChunkCount(long l) {
        return (l + 0x100000L - 1L) / 0x100000L;
    }

    static String getContentDigestAlgorithmJcaDigestAlgorithm(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unknown content digest algorthm: ");
                    stringBuilder.append(n);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            } else {
                return "SHA-512";
            }
        }
        return "SHA-256";
    }

    private static int getContentDigestAlgorithmOutputSizeBytes(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unknown content digest algorthm: ");
                    stringBuilder.append(n);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            } else {
                return 64;
            }
        }
        return 32;
    }

    static Pair<ByteBuffer, Long> getEocd(RandomAccessFile object) throws IOException, SignatureNotFoundException {
        if ((object = ZipUtils.findZipEndOfCentralDirectoryRecord((RandomAccessFile)object)) != null) {
            return object;
        }
        throw new SignatureNotFoundException("Not an APK file: ZIP End of Central Directory record not found");
    }

    static ByteBuffer getLengthPrefixedSlice(ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer.remaining() >= 4) {
            int n = byteBuffer.getInt();
            if (n >= 0) {
                if (n <= byteBuffer.remaining()) {
                    return ApkSigningBlockUtils.getByteBuffer(byteBuffer, n);
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Length-prefixed field longer than remaining buffer. Field length: ");
                stringBuilder.append(n);
                stringBuilder.append(", remaining: ");
                stringBuilder.append(byteBuffer.remaining());
                throw new IOException(stringBuilder.toString());
            }
            throw new IllegalArgumentException("Negative length");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Remaining buffer too short to contain length of length-prefixed field. Remaining: ");
        stringBuilder.append(byteBuffer.remaining());
        throw new IOException(stringBuilder.toString());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static int getSignatureAlgorithmContentDigestAlgorithm(int n) {
        if (n == 513) return 1;
        if (n == 514) return 2;
        if (n == 769) return 1;
        if (n == 1057 || n == 1059 || n == 1061) return 3;
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown signature algorithm: 0x");
                stringBuilder.append(Long.toHexString(n & -1));
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case 258: 
            case 260: {
                return 2;
            }
            case 257: 
            case 259: 
        }
        return 1;
    }

    static String getSignatureAlgorithmJcaKeyAlgorithm(int n) {
        block5 : {
            block6 : {
                block7 : {
                    if (n == 513 || n == 514) break block5;
                    if (n == 769) break block6;
                    if (n == 1057) break block7;
                    if (n == 1059) break block5;
                    if (n == 1061) break block6;
                    switch (n) {
                        default: {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unknown signature algorithm: 0x");
                            stringBuilder.append(Long.toHexString(n & -1));
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                        case 257: 
                        case 258: 
                        case 259: 
                        case 260: 
                    }
                }
                return "RSA";
            }
            return "DSA";
        }
        return "EC";
    }

    static Pair<String, ? extends AlgorithmParameterSpec> getSignatureAlgorithmJcaSignatureAlgorithm(int n) {
        block8 : {
            block9 : {
                block10 : {
                    block11 : {
                        if (n == 513) break block8;
                        if (n == 514) break block9;
                        if (n == 769) break block10;
                        if (n == 1057) break block11;
                        if (n == 1059) break block8;
                        if (n == 1061) break block10;
                        switch (n) {
                            default: {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Unknown signature algorithm: 0x");
                                stringBuilder.append(Long.toHexString(n & -1));
                                throw new IllegalArgumentException(stringBuilder.toString());
                            }
                            case 260: {
                                return Pair.create("SHA512withRSA", null);
                            }
                            case 258: {
                                return Pair.create("SHA512withRSA/PSS", new PSSParameterSpec("SHA-512", "MGF1", MGF1ParameterSpec.SHA512, 64, 1));
                            }
                            case 257: {
                                return Pair.create("SHA256withRSA/PSS", new PSSParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, 32, 1));
                            }
                            case 259: 
                        }
                    }
                    return Pair.create("SHA256withRSA", null);
                }
                return Pair.create("SHA256withDSA", null);
            }
            return Pair.create("SHA512withECDSA", null);
        }
        return Pair.create("SHA256withECDSA", null);
    }

    static byte[] parseVerityDigestAndVerifySourceLength(byte[] arrby, long l, SignatureInfo object) throws SecurityException {
        if (arrby.length == 32 + 8) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(arrby).order(ByteOrder.LITTLE_ENDIAN);
            byteBuffer.position(32);
            if (byteBuffer.getLong() == l - (((SignatureInfo)object).centralDirOffset - ((SignatureInfo)object).apkSigningBlockOffset)) {
                return Arrays.copyOfRange(arrby, 0, 32);
            }
            throw new SecurityException("APK content size did not verify");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Verity digest size is wrong: ");
        ((StringBuilder)object).append(arrby.length);
        throw new SecurityException(((StringBuilder)object).toString());
    }

    static byte[] readLengthPrefixedByteArray(ByteBuffer byteBuffer) throws IOException {
        int n = byteBuffer.getInt();
        if (n >= 0) {
            if (n <= byteBuffer.remaining()) {
                byte[] arrby = new byte[n];
                byteBuffer.get(arrby);
                return arrby;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Underflow while reading length-prefixed value. Length: ");
            stringBuilder.append(n);
            stringBuilder.append(", available: ");
            stringBuilder.append(byteBuffer.remaining());
            throw new IOException(stringBuilder.toString());
        }
        throw new IOException("Negative length");
    }

    static void setUnsignedInt32LittleEndian(int n, byte[] arrby, int n2) {
        arrby[n2] = (byte)(n & 255);
        arrby[n2 + 1] = (byte)(n >>> 8 & 255);
        arrby[n2 + 2] = (byte)(n >>> 16 & 255);
        arrby[n2 + 3] = (byte)(n >>> 24 & 255);
    }

    static ByteBuffer sliceFromTo(ByteBuffer object, int n, int n2) {
        if (n >= 0) {
            if (n2 >= n) {
                int n3 = ((Buffer)object).capacity();
                if (n2 <= ((Buffer)object).capacity()) {
                    int n4 = ((Buffer)object).limit();
                    n3 = ((Buffer)object).position();
                    try {
                        ((Buffer)object).position(0);
                        ((Buffer)object).limit(n2);
                        ((Buffer)object).position(n);
                        ByteBuffer byteBuffer = ((ByteBuffer)object).slice();
                        byteBuffer.order(((ByteBuffer)object).order());
                        return byteBuffer;
                    }
                    finally {
                        ((Buffer)object).position(0);
                        ((Buffer)object).limit(n4);
                        ((Buffer)object).position(n3);
                    }
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("end > capacity: ");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(" > ");
                ((StringBuilder)object).append(n3);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("end < start: ");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" < ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("start: ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    static void verifyIntegrity(Map<Integer, byte[]> map, RandomAccessFile randomAccessFile, SignatureInfo signatureInfo) throws SecurityException {
        if (!map.isEmpty()) {
            boolean bl = true;
            ArrayMap<Integer, byte[]> arrayMap = new ArrayMap<Integer, byte[]>();
            if (map.containsKey(1)) {
                arrayMap.put(1, map.get(1));
            }
            if (map.containsKey(2)) {
                arrayMap.put(2, map.get(2));
            }
            if (!arrayMap.isEmpty()) {
                try {
                    ApkSigningBlockUtils.verifyIntegrityFor1MbChunkBasedAlgorithm(arrayMap, randomAccessFile.getFD(), signatureInfo);
                    bl = false;
                }
                catch (IOException iOException) {
                    throw new SecurityException("Cannot get FD", iOException);
                }
            }
            if (map.containsKey(3)) {
                ApkSigningBlockUtils.verifyIntegrityForVerityBasedAlgorithm(map.get(3), randomAccessFile, signatureInfo);
                bl = false;
            }
            if (!bl) {
                return;
            }
            throw new SecurityException("No known digest exists for integrity check");
        }
        throw new SecurityException("No digests provided");
    }

    private static void verifyIntegrityFor1MbChunkBasedAlgorithm(Map<Integer, byte[]> object, FileDescriptor arrby, SignatureInfo arrn) throws SecurityException {
        MemoryMappedFileDataSource memoryMappedFileDataSource = new MemoryMappedFileDataSource((FileDescriptor)arrby, 0L, arrn.apkSigningBlockOffset);
        arrby = new MemoryMappedFileDataSource((FileDescriptor)arrby, arrn.centralDirOffset, arrn.eocdOffset - arrn.centralDirOffset);
        Object object2 = arrn.eocd.duplicate();
        ((ByteBuffer)object2).order(ByteOrder.LITTLE_ENDIAN);
        ZipUtils.setZipEocdCentralDirectoryOffset((ByteBuffer)object2, arrn.apkSigningBlockOffset);
        ByteBufferDataSource byteBufferDataSource = new ByteBufferDataSource((ByteBuffer)object2);
        arrn = new int[object.size()];
        object2 = object.keySet().iterator();
        int n = 0;
        while (object2.hasNext()) {
            arrn[n] = (Integer)object2.next();
            ++n;
        }
        try {
            arrby = ApkSigningBlockUtils.computeContentDigestsPer1MbChunk(arrn, new DataSource[]{memoryMappedFileDataSource, arrby, byteBufferDataSource});
        }
        catch (DigestException digestException) {
            throw new SecurityException("Failed to compute digest(s) of contents", digestException);
        }
        for (n = 0; n < arrn.length; ++n) {
            int n2 = arrn[n];
            if (MessageDigest.isEqual(object.get(n2), arrby[n])) {
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(ApkSigningBlockUtils.getContentDigestAlgorithmJcaDigestAlgorithm(n2));
            ((StringBuilder)object).append(" digest of contents did not verify");
            throw new SecurityException(((StringBuilder)object).toString());
        }
        return;
    }

    private static void verifyIntegrityForVerityBasedAlgorithm(byte[] object, RandomAccessFile randomAccessFile, SignatureInfo signatureInfo) throws SecurityException {
        try {
            object = ApkSigningBlockUtils.parseVerityDigestAndVerifySourceLength(object, randomAccessFile.length(), signatureInfo);
            ByteBufferFactory byteBufferFactory = new ByteBufferFactory(){

                @Override
                public ByteBuffer create(int n) {
                    return ByteBuffer.allocate(n);
                }
            };
            if (Arrays.equals(object, VerityBuilder.generateApkVerityTree((RandomAccessFile)randomAccessFile, (SignatureInfo)signatureInfo, (ByteBufferFactory)byteBufferFactory).rootHash)) {
                return;
            }
            object = new SecurityException("APK verity digest of contents did not verify");
            throw object;
        }
        catch (IOException | DigestException | NoSuchAlgorithmException exception) {
            throw new SecurityException("Error during verification", exception);
        }
    }

    private static class MultipleDigestDataDigester
    implements DataDigester {
        private final MessageDigest[] mMds;

        MultipleDigestDataDigester(MessageDigest[] arrmessageDigest) {
            this.mMds = arrmessageDigest;
        }

        @Override
        public void consume(ByteBuffer byteBuffer) {
            byteBuffer = byteBuffer.slice();
            for (MessageDigest messageDigest : this.mMds) {
                byteBuffer.position(0);
                messageDigest.update(byteBuffer);
            }
        }
    }

}

