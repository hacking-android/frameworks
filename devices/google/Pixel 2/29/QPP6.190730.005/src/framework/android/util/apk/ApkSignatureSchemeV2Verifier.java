/*
 * Decompiled with CFR 0.145.
 */
package android.util.apk;

import android.util.ArrayMap;
import android.util.Pair;
import android.util.apk.ApkSigningBlockUtils;
import android.util.apk.ByteBufferFactory;
import android.util.apk.SignatureInfo;
import android.util.apk.SignatureNotFoundException;
import android.util.apk.VerbatimX509Certificate;
import android.util.apk.VerityBuilder;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.DigestException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class ApkSignatureSchemeV2Verifier {
    private static final int APK_SIGNATURE_SCHEME_V2_BLOCK_ID = 1896449818;
    public static final int SF_ATTRIBUTE_ANDROID_APK_SIGNED_ID = 2;
    private static final int STRIPPING_PROTECTION_ATTR_ID = -1091571699;

    private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    private static SignatureInfo findSignature(RandomAccessFile randomAccessFile) throws IOException, SignatureNotFoundException {
        return ApkSigningBlockUtils.findSignature(randomAccessFile, 1896449818);
    }

    static byte[] generateApkVerity(String arrby, ByteBufferFactory byteBufferFactory) throws IOException, SignatureNotFoundException, SecurityException, DigestException, NoSuchAlgorithmException {
        RandomAccessFile randomAccessFile = new RandomAccessFile((String)arrby, "r");
        try {
            arrby = VerityBuilder.generateApkVerity((String)arrby, byteBufferFactory, ApkSignatureSchemeV2Verifier.findSignature(randomAccessFile));
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                ApkSignatureSchemeV2Verifier.$closeResource(throwable, randomAccessFile);
                throw throwable2;
            }
        }
        ApkSignatureSchemeV2Verifier.$closeResource(null, randomAccessFile);
        return arrby;
    }

    static byte[] generateApkVerityRootHash(String object) throws IOException, SignatureNotFoundException, DigestException, NoSuchAlgorithmException {
        VerifiedSigner verifiedSigner;
        byte[] arrby;
        block5 : {
            object = new RandomAccessFile((String)object, "r");
            try {
                arrby = ApkSignatureSchemeV2Verifier.findSignature((RandomAccessFile)object);
                verifiedSigner = ApkSignatureSchemeV2Verifier.verify((RandomAccessFile)object, false);
                byte[] arrby2 = verifiedSigner.verityRootHash;
                if (arrby2 != null) break block5;
            }
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    ApkSignatureSchemeV2Verifier.$closeResource(throwable, (AutoCloseable)object);
                    throw throwable2;
                }
            }
            ApkSignatureSchemeV2Verifier.$closeResource(null, (AutoCloseable)object);
            return null;
        }
        arrby = VerityBuilder.generateApkVerityRootHash((RandomAccessFile)object, ByteBuffer.wrap(verifiedSigner.verityRootHash), (SignatureInfo)arrby);
        ApkSignatureSchemeV2Verifier.$closeResource(null, (AutoCloseable)object);
        return arrby;
    }

    static byte[] getVerityRootHash(String object) throws IOException, SignatureNotFoundException, SecurityException {
        byte[] arrby;
        object = new RandomAccessFile((String)object, "r");
        try {
            ApkSignatureSchemeV2Verifier.findSignature((RandomAccessFile)object);
            arrby = ApkSignatureSchemeV2Verifier.verify((RandomAccessFile)object, (boolean)false).verityRootHash;
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                ApkSignatureSchemeV2Verifier.$closeResource(throwable, (AutoCloseable)object);
                throw throwable2;
            }
        }
        ApkSignatureSchemeV2Verifier.$closeResource(null, (AutoCloseable)object);
        return arrby;
    }

    /*
     * Exception decompiling
     */
    public static boolean hasSignature(String var0) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private static boolean isSupportedSignatureAlgorithm(int n) {
        if (n != 513 && n != 514 && n != 769 && n != 1057 && n != 1059 && n != 1061) {
            switch (n) {
                default: {
                    return false;
                }
                case 257: 
                case 258: 
                case 259: 
                case 260: 
            }
        }
        return true;
    }

    public static X509Certificate[][] unsafeGetCertsWithoutVerification(String string2) throws SignatureNotFoundException, SecurityException, IOException {
        return ApkSignatureSchemeV2Verifier.verify((String)string2, (boolean)false).certs;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static VerifiedSigner verify(RandomAccessFile object, SignatureInfo signatureInfo, boolean bl) throws SecurityException, IOException {
        ByteBuffer byteBuffer;
        byte[] arrby;
        int n = 0;
        ArrayMap<Integer, byte[]> arrayMap = new ArrayMap<Integer, byte[]>();
        ArrayList<X509Certificate[]> arrayList = new ArrayList<X509Certificate[]>();
        try {
            arrby = CertificateFactory.getInstance("X.509");
        }
        catch (CertificateException certificateException) {
            throw new RuntimeException("Failed to obtain X.509 CertificateFactory", certificateException);
        }
        try {
            byteBuffer = ApkSigningBlockUtils.getLengthPrefixedSlice(signatureInfo.signatureBlock);
        }
        catch (IOException iOException) {
            throw new SecurityException("Failed to read list of signers", iOException);
        }
        while (byteBuffer.hasRemaining()) {
            ++n;
            try {
                arrayList.add(ApkSignatureSchemeV2Verifier.verifySigner(ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer), arrayMap, (CertificateFactory)arrby));
            }
            catch (IOException | SecurityException | BufferUnderflowException exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to parse/verify signer #");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" block");
                throw new SecurityException(((StringBuilder)object).toString(), exception);
            }
        }
        if (n < 1) {
            throw new SecurityException("No signers found");
        }
        if (arrayMap.isEmpty()) {
            throw new SecurityException("No content digests found");
        }
        if (bl) {
            ApkSigningBlockUtils.verifyIntegrity(arrayMap, (RandomAccessFile)object, signatureInfo);
        }
        arrby = null;
        if (arrayMap.containsKey(3)) {
            arrby = (byte[])arrayMap.get(3);
            arrby = ApkSigningBlockUtils.parseVerityDigestAndVerifySourceLength(arrby, ((RandomAccessFile)object).length(), signatureInfo);
        }
        return new VerifiedSigner((X509Certificate[][])arrayList.toArray((T[])new X509Certificate[arrayList.size()][]), arrby);
    }

    private static VerifiedSigner verify(RandomAccessFile randomAccessFile, boolean bl) throws SignatureNotFoundException, SecurityException, IOException {
        return ApkSignatureSchemeV2Verifier.verify(randomAccessFile, ApkSignatureSchemeV2Verifier.findSignature(randomAccessFile), bl);
    }

    private static VerifiedSigner verify(String object, boolean bl) throws SignatureNotFoundException, SecurityException, IOException {
        VerifiedSigner verifiedSigner;
        object = new RandomAccessFile((String)object, "r");
        try {
            verifiedSigner = ApkSignatureSchemeV2Verifier.verify((RandomAccessFile)object, bl);
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                ApkSignatureSchemeV2Verifier.$closeResource(throwable, (AutoCloseable)object);
                throw throwable2;
            }
        }
        ApkSignatureSchemeV2Verifier.$closeResource(null, (AutoCloseable)object);
        return verifiedSigner;
    }

    public static X509Certificate[][] verify(String string2) throws SignatureNotFoundException, SecurityException, IOException {
        return ApkSignatureSchemeV2Verifier.verify((String)string2, (boolean)true).certs;
    }

    private static void verifyAdditionalAttributes(ByteBuffer object) throws SecurityException, IOException {
        while (((Buffer)object).hasRemaining()) {
            ByteBuffer byteBuffer = ApkSigningBlockUtils.getLengthPrefixedSlice((ByteBuffer)object);
            if (byteBuffer.remaining() >= 4) {
                if (byteBuffer.getInt() != -1091571699) continue;
                if (byteBuffer.remaining() >= 4) {
                    if (byteBuffer.getInt() != 3) continue;
                    throw new SecurityException("V2 signature indicates APK is signed using APK Signature Scheme v3, but none was found. Signature stripped?");
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("V2 Signature Scheme Stripping Protection Attribute  value too small.  Expected 4 bytes, but found ");
                ((StringBuilder)object).append(byteBuffer.remaining());
                throw new IOException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Remaining buffer too short to contain additional attribute ID. Remaining: ");
            ((StringBuilder)object).append(byteBuffer.remaining());
            throw new IOException(((StringBuilder)object).toString());
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static X509Certificate[] verifySigner(ByteBuffer object, Map<Integer, byte[]> object2, CertificateFactory certificateFactory) throws SecurityException, IOException {
        block31 : {
            block34 : {
                block27 : {
                    block28 : {
                        block26 : {
                            byteBuffer = ApkSigningBlockUtils.getLengthPrefixedSlice((ByteBuffer)object);
                            object4 = ApkSigningBlockUtils.getLengthPrefixedSlice((ByteBuffer)object);
                            arrby = ApkSigningBlockUtils.readLengthPrefixedByteArray((ByteBuffer)object);
                            n = -1;
                            arrby3 = new ArrayList();
                            object = null;
                            n2 = 0;
                            while (object4.hasRemaining()) {
                                n3 = n2 + 1;
                                try {
                                    block33 : {
                                        block32 : {
                                            arrby2 = ApkSigningBlockUtils.getLengthPrefixedSlice((ByteBuffer)object4);
                                            if (arrby2.remaining() < 8) {
                                                object = new SecurityException("Signature record too short");
                                                throw object;
                                            }
                                            n4 = arrby2.getInt();
                                            arrby3.add(n4);
                                            if (!ApkSignatureSchemeV2Verifier.isSupportedSignatureAlgorithm(n4)) {
                                                n2 = n3;
                                                continue;
                                            }
                                            if (n == -1) break block32;
                                            n2 = n;
                                            if (ApkSigningBlockUtils.compareSignatureAlgorithm(n4, n) <= 0) break block33;
                                        }
                                        n2 = n4;
                                        object = ApkSigningBlockUtils.readLengthPrefixedByteArray((ByteBuffer)arrby2);
                                    }
                                    n = n2;
                                    n2 = n3;
                                }
                                catch (IOException | BufferUnderflowException exception) {
                                    object = new StringBuilder();
                                    object.append("Failed to parse signature record #");
                                    object.append(n3);
                                    throw new SecurityException(object.toString(), exception);
                                }
                            }
                            if (n == -1) {
                                if (n2 != 0) throw new SecurityException("No supported signatures found");
                                throw new SecurityException("No signatures found");
                            }
                            object3 = ApkSigningBlockUtils.getSignatureAlgorithmJcaKeyAlgorithm(n);
                            arrayList = ApkSigningBlockUtils.getSignatureAlgorithmJcaSignatureAlgorithm(n);
                            arrby2 = (String)arrayList.first;
                            arrayList = (AlgorithmParameterSpec)arrayList.second;
                            object3 = KeyFactory.getInstance((String)object3);
                            object5 = new X509EncodedKeySpec(arrby);
                            object3 = object3.generatePublic((KeySpec)object5);
                            object5 = Signature.getInstance((String)arrby2);
                            object5.initVerify((PublicKey)object3);
                            if (arrayList == null) break block26;
                            try {
                                object5.setParameter((AlgorithmParameterSpec)arrayList);
                            }
                            catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException generalSecurityException) {
                                break block27;
                            }
                        }
                        try {
                            object5.update(byteBuffer);
                            bl = object5.verify((byte[])object);
                            if (!bl) break block28;
                        }
                        catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException generalSecurityException) {
                            // empty catch block
                        }
                        byteBuffer.clear();
                        object3 = ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
                        arrayList = new ArrayList<Integer>();
                        n2 = 0;
                        arrby2 = null;
                        object = object4;
                        break block34;
                    }
                    object = new StringBuilder();
                    object.append((String)arrby2);
                    object.append(" signature did not verify");
                    throw new SecurityException(object.toString());
                }
                object2 = new StringBuilder();
                object2.append("Failed to verify ");
                object2.append((String)arrby2);
                object2.append(" signature");
                throw new SecurityException(object2.toString(), (Throwable)var0_9);
            }
            while (object3.hasRemaining()) {
                block30 : {
                    block29 : {
                        ++n2;
                        object4 = ApkSigningBlockUtils.getLengthPrefixedSlice((ByteBuffer)object3);
                        n3 = object4.remaining();
                        if (n3 < 8) break block29;
                        try {
                            n3 = object4.getInt();
                            arrayList.add(n3);
                            if (n3 != n) continue;
                            arrby2 = ApkSigningBlockUtils.readLengthPrefixedByteArray((ByteBuffer)object4);
                            continue;
                        }
                        catch (IOException | BufferUnderflowException exception) {
                            break block30;
                        }
                    }
                    object = new IOException("Record too short");
                    throw object;
                    catch (IOException | BufferUnderflowException exception) {
                        // empty catch block
                    }
                }
                object2 = new StringBuilder();
                object2.append("Failed to parse digest record #");
                object2.append(n2);
                throw new IOException(object2.toString(), (Throwable)var0_4);
            }
            if (arrby3.equals(arrayList) == false) throw new SecurityException("Signature algorithms don't match between digests and signatures records");
            object = (byte[])object2.put(n = ApkSigningBlockUtils.getSignatureAlgorithmContentDigestAlgorithm(n), arrby2);
            if (object != null && !MessageDigest.isEqual((byte[])object, arrby2)) {
                object = new StringBuilder();
                object.append(ApkSigningBlockUtils.getContentDigestAlgorithmJcaDigestAlgorithm(n));
                object.append(" contents digest does not match the digest specified by a preceding signer");
                throw new SecurityException(object.toString());
            }
            object2 = ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
            arrby2 = new ArrayList();
            n2 = 0;
            object = arrby3;
            do lbl-1000: // 2 sources:
            {
                if (!object2.hasRemaining()) {
                    if (arrby2.isEmpty() != false) throw new SecurityException("No certificates listed");
                    if (Arrays.equals(arrby, ((X509Certificate)arrby2.get(0)).getPublicKey().getEncoded()) == false) throw new SecurityException("Public key mismatch between certificate and signature record");
                    ApkSignatureSchemeV2Verifier.verifyAdditionalAttributes(ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer));
                    return arrby2.toArray(new X509Certificate[arrby2.size()]);
                }
                ++n2;
                arrby3 = ApkSigningBlockUtils.readLengthPrefixedByteArray((ByteBuffer)object2);
                object4 = new ByteArrayInputStream(arrby3);
                object4 = (X509Certificate)certificateFactory.generateCertificate((InputStream)object4);
                arrby2.add(new VerbatimX509Certificate((X509Certificate)object4, arrby3));
                break;
            } while (true);
            catch (CertificateException certificateException) {
                break block31;
            }
            {
                ** while (true)
            }
            catch (CertificateException certificateException) {
                // empty catch block
            }
        }
        object2 = new StringBuilder();
        object2.append("Failed to decode certificate #");
        object2.append(n2);
        throw new SecurityException(object2.toString(), (Throwable)var0_7);
    }

    public static class VerifiedSigner {
        public final X509Certificate[][] certs;
        public final byte[] verityRootHash;

        public VerifiedSigner(X509Certificate[][] arrx509Certificate, byte[] arrby) {
            this.certs = arrx509Certificate;
            this.verityRootHash = arrby;
        }
    }

}

