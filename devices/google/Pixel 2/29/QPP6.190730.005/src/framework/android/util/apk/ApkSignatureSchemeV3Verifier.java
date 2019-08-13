/*
 * Decompiled with CFR 0.145.
 */
package android.util.apk;

import android.os.Build;
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
import java.io.Serializable;
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
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ApkSignatureSchemeV3Verifier {
    private static final int APK_SIGNATURE_SCHEME_V3_BLOCK_ID = -262969152;
    private static final int PROOF_OF_ROTATION_ATTR_ID = 1000370060;
    public static final int SF_ATTRIBUTE_ANDROID_APK_SIGNED_ID = 3;

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
        return ApkSigningBlockUtils.findSignature(randomAccessFile, -262969152);
    }

    static byte[] generateApkVerity(String arrby, ByteBufferFactory byteBufferFactory) throws IOException, SignatureNotFoundException, SecurityException, DigestException, NoSuchAlgorithmException {
        RandomAccessFile randomAccessFile = new RandomAccessFile((String)arrby, "r");
        try {
            arrby = VerityBuilder.generateApkVerity((String)arrby, byteBufferFactory, ApkSignatureSchemeV3Verifier.findSignature(randomAccessFile));
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                ApkSignatureSchemeV3Verifier.$closeResource(throwable, randomAccessFile);
                throw throwable2;
            }
        }
        ApkSignatureSchemeV3Verifier.$closeResource(null, randomAccessFile);
        return arrby;
    }

    static byte[] generateApkVerityRootHash(String object) throws NoSuchAlgorithmException, DigestException, IOException, SignatureNotFoundException {
        VerifiedSigner verifiedSigner;
        byte[] arrby;
        block5 : {
            object = new RandomAccessFile((String)object, "r");
            try {
                arrby = ApkSignatureSchemeV3Verifier.findSignature((RandomAccessFile)object);
                verifiedSigner = ApkSignatureSchemeV3Verifier.verify((RandomAccessFile)object, false);
                byte[] arrby2 = verifiedSigner.verityRootHash;
                if (arrby2 != null) break block5;
            }
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    ApkSignatureSchemeV3Verifier.$closeResource(throwable, (AutoCloseable)object);
                    throw throwable2;
                }
            }
            ApkSignatureSchemeV3Verifier.$closeResource(null, (AutoCloseable)object);
            return null;
        }
        arrby = VerityBuilder.generateApkVerityRootHash((RandomAccessFile)object, ByteBuffer.wrap(verifiedSigner.verityRootHash), (SignatureInfo)arrby);
        ApkSignatureSchemeV3Verifier.$closeResource(null, (AutoCloseable)object);
        return arrby;
    }

    static byte[] getVerityRootHash(String object) throws IOException, SignatureNotFoundException, SecurityException {
        byte[] arrby;
        object = new RandomAccessFile((String)object, "r");
        try {
            ApkSignatureSchemeV3Verifier.findSignature((RandomAccessFile)object);
            arrby = ApkSignatureSchemeV3Verifier.verify((RandomAccessFile)object, (boolean)false).verityRootHash;
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                ApkSignatureSchemeV3Verifier.$closeResource(throwable, (AutoCloseable)object);
                throw throwable2;
            }
        }
        ApkSignatureSchemeV3Verifier.$closeResource(null, (AutoCloseable)object);
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

    public static VerifiedSigner unsafeGetCertsWithoutVerification(String string2) throws SignatureNotFoundException, SecurityException, IOException {
        return ApkSignatureSchemeV3Verifier.verify(string2, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static VerifiedSigner verify(RandomAccessFile object, SignatureInfo signatureInfo, boolean bl) throws SecurityException, IOException {
        ByteBuffer byteBuffer;
        byte[] arrby;
        CertificateFactory certificateFactory;
        int n = 0;
        ArrayMap<Integer, byte[]> arrayMap = new ArrayMap<Integer, byte[]>();
        byte[] arrby2 = null;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
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
            try {
                arrby2 = arrby = ApkSignatureSchemeV3Verifier.verifySigner(ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer), arrayMap, certificateFactory);
                ++n;
            }
            catch (IOException | SecurityException | BufferUnderflowException exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to parse/verify signer #");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" block");
                throw new SecurityException(((StringBuilder)object).toString(), exception);
            }
            catch (PlatformNotSupportedException platformNotSupportedException) {
            }
        }
        if (n >= 1 && arrby2 != null) {
            if (n != 1) {
                throw new SecurityException("APK Signature Scheme V3 only supports one signer: multiple signers found.");
            }
            if (arrayMap.isEmpty()) {
                throw new SecurityException("No content digests found");
            }
            if (bl) {
                ApkSigningBlockUtils.verifyIntegrity(arrayMap, (RandomAccessFile)object, signatureInfo);
            }
            if (arrayMap.containsKey(3)) {
                arrby = (byte[])arrayMap.get(3);
                arrby2.verityRootHash = ApkSigningBlockUtils.parseVerityDigestAndVerifySourceLength(arrby, ((RandomAccessFile)object).length(), signatureInfo);
            }
            return arrby2;
        }
        throw new SecurityException("No signers found");
    }

    private static VerifiedSigner verify(RandomAccessFile randomAccessFile, boolean bl) throws SignatureNotFoundException, SecurityException, IOException {
        return ApkSignatureSchemeV3Verifier.verify(randomAccessFile, ApkSignatureSchemeV3Verifier.findSignature(randomAccessFile), bl);
    }

    public static VerifiedSigner verify(String string2) throws SignatureNotFoundException, SecurityException, IOException {
        return ApkSignatureSchemeV3Verifier.verify(string2, true);
    }

    private static VerifiedSigner verify(String object, boolean bl) throws SignatureNotFoundException, SecurityException, IOException {
        VerifiedSigner verifiedSigner;
        object = new RandomAccessFile((String)object, "r");
        try {
            verifiedSigner = ApkSignatureSchemeV3Verifier.verify((RandomAccessFile)object, bl);
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                ApkSignatureSchemeV3Verifier.$closeResource(throwable, (AutoCloseable)object);
                throw throwable2;
            }
        }
        ApkSignatureSchemeV3Verifier.$closeResource(null, (AutoCloseable)object);
        return verifiedSigner;
    }

    private static VerifiedSigner verifyAdditionalAttributes(ByteBuffer object, List<X509Certificate> object2, CertificateFactory certificateFactory) throws IOException {
        X509Certificate[] arrx509Certificate = object2.toArray(new X509Certificate[object2.size()]);
        object2 = null;
        while (((Buffer)object).hasRemaining()) {
            ByteBuffer byteBuffer = ApkSigningBlockUtils.getLengthPrefixedSlice((ByteBuffer)object);
            if (byteBuffer.remaining() >= 4) {
                if (byteBuffer.getInt() != 1000370060) continue;
                if (object2 == null) {
                    object2 = ApkSignatureSchemeV3Verifier.verifyProofOfRotationStruct(byteBuffer, certificateFactory);
                    try {
                        if (((VerifiedProofOfRotation)object2).certs.size() <= 0 || Arrays.equals(((VerifiedProofOfRotation)object2).certs.get(((VerifiedProofOfRotation)object2).certs.size() - 1).getEncoded(), arrx509Certificate[0].getEncoded())) continue;
                        object = new SecurityException("Terminal certificate in Proof-of-rotation record does not match APK signing certificate");
                        throw object;
                    }
                    catch (CertificateEncodingException certificateEncodingException) {
                        throw new SecurityException("Failed to encode certificate when comparing Proof-of-rotation record and signing certificate", certificateEncodingException);
                    }
                }
                throw new SecurityException("Encountered multiple Proof-of-rotation records when verifying APK Signature Scheme v3 signature");
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Remaining buffer too short to contain additional attribute ID. Remaining: ");
            ((StringBuilder)object).append(byteBuffer.remaining());
            throw new IOException(((StringBuilder)object).toString());
        }
        return new VerifiedSigner(arrx509Certificate, (VerifiedProofOfRotation)object2);
    }

    /*
     * Exception decompiling
     */
    private static VerifiedProofOfRotation verifyProofOfRotationStruct(ByteBuffer var0, CertificateFactory var1_10) throws SecurityException, IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 9[UNCONDITIONALDOLOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
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

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static VerifiedSigner verifySigner(ByteBuffer object, Map<Integer, byte[]> arrayList, CertificateFactory certificateFactory) throws SecurityException, IOException, PlatformNotSupportedException {
        block36 : {
            block31 : {
                block32 : {
                    block30 : {
                        byteBuffer = ApkSigningBlockUtils.getLengthPrefixedSlice((ByteBuffer)object);
                        n2 = object.getInt();
                        n = object.getInt();
                        if (Build.VERSION.SDK_INT >= n2 && Build.VERSION.SDK_INT <= n) {
                            arrby = ApkSigningBlockUtils.getLengthPrefixedSlice((ByteBuffer)object);
                            arrby3 = ApkSigningBlockUtils.readLengthPrefixedByteArray((ByteBuffer)object);
                            n6 = -1;
                            arrayList2 = new ArrayList<Integer>();
                            object = null;
                            n5 = 0;
                        } else {
                            object = new StringBuilder();
                            object.append("Signer not supported by this platform version. This platform: ");
                            object.append(Build.VERSION.SDK_INT);
                            object.append(", signer minSdkVersion: ");
                            object.append(n2);
                            object.append(", maxSdkVersion: ");
                            object.append(n);
                            throw new PlatformNotSupportedException(object.toString());
                        }
                        while (arrby.hasRemaining()) {
                            n3 = n5 + 1;
                            try {
                                block35 : {
                                    block34 : {
                                        object2 = ApkSigningBlockUtils.getLengthPrefixedSlice((ByteBuffer)arrby);
                                        if (object2.remaining() < 8) {
                                            object = new SecurityException("Signature record too short");
                                            throw object;
                                        }
                                        n4 = object2.getInt();
                                        arrayList2.add(n4);
                                        if (!ApkSignatureSchemeV3Verifier.isSupportedSignatureAlgorithm(n4)) {
                                            n5 = n3;
                                            continue;
                                        }
                                        if (n6 == -1) break block34;
                                        n5 = n6;
                                        if (ApkSigningBlockUtils.compareSignatureAlgorithm(n4, n6) <= 0) break block35;
                                    }
                                    n5 = n4;
                                    object = ApkSigningBlockUtils.readLengthPrefixedByteArray((ByteBuffer)object2);
                                }
                                n6 = n5;
                                n5 = n3;
                            }
                            catch (IOException | BufferUnderflowException exception) {
                                object = new StringBuilder();
                                object.append("Failed to parse signature record #");
                                object.append(n3);
                                throw new SecurityException(object.toString(), exception);
                            }
                        }
                        if (n6 == -1) {
                            if (n5 != 0) throw new SecurityException("No supported signatures found");
                            throw new SecurityException("No signatures found");
                        }
                        arrby = ApkSigningBlockUtils.getSignatureAlgorithmJcaKeyAlgorithm(n6);
                        arrby2 = ApkSigningBlockUtils.getSignatureAlgorithmJcaSignatureAlgorithm(n6);
                        object2 = (String)arrby2.first;
                        arrby2 = (AlgorithmParameterSpec)arrby2.second;
                        object3 = KeyFactory.getInstance((String)arrby);
                        object4 = new X509EncodedKeySpec(arrby3);
                        object3 = object3.generatePublic((KeySpec)object4);
                        object4 = Signature.getInstance((String)object2);
                        object4.initVerify((PublicKey)object3);
                        if (arrby2 == null) break block30;
                        try {
                            object4.setParameter((AlgorithmParameterSpec)arrby2);
                        }
                        catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException generalSecurityException) {
                            break block31;
                        }
                    }
                    try {
                        object4.update(byteBuffer);
                        bl = object4.verify((byte[])object);
                        if (!bl) break block32;
                    }
                    catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException generalSecurityException) {
                        // empty catch block
                    }
                    byteBuffer.clear();
                    object4 = ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
                    object2 = new ArrayList<Integer>();
                    n3 = 0;
                    arrby2 = null;
                    break block36;
                }
                object = new StringBuilder();
                object.append((String)object2);
                object.append(" signature did not verify");
                throw new SecurityException(object.toString());
            }
            arrayList = new StringBuilder();
            arrayList.append("Failed to verify ");
            arrayList.append((String)object2);
            arrayList.append(" signature");
            throw new SecurityException(arrayList.toString(), (Throwable)var0_8);
        }
        while (object4.hasRemaining()) {
            block33 : {
                ++n3;
                object3 = ApkSigningBlockUtils.getLengthPrefixedSlice((ByteBuffer)object4);
                n4 = object3.remaining();
                if (n4 < 8) ** GOTO lbl123
                n4 = object3.getInt();
                try {
                    object2.add(n4);
                    if (n4 != n6) continue;
                    arrby2 = ApkSigningBlockUtils.readLengthPrefixedByteArray((ByteBuffer)object3);
                    continue;
                    catch (IOException | BufferUnderflowException exception) {
                        break block33;
                    }
lbl123: // 1 sources:
                    object = new IOException("Record too short");
                    throw object;
                }
                catch (IOException | BufferUnderflowException exception) {}
                break block33;
                catch (IOException | BufferUnderflowException exception) {}
                break block33;
                catch (IOException | BufferUnderflowException exception) {
                    // empty catch block
                }
            }
            arrayList = new StringBuilder();
            arrayList.append("Failed to parse digest record #");
            arrayList.append(n3);
            throw new IOException(arrayList.toString(), (Throwable)var0_6);
        }
        if (arrayList2.equals(object2) == false) throw new SecurityException("Signature algorithms don't match between digests and signatures records");
        n5 = ApkSigningBlockUtils.getSignatureAlgorithmContentDigestAlgorithm(n6);
        object = (byte[])arrayList.put(n5, arrby2);
        if (object != null && !MessageDigest.isEqual((byte[])object, arrby2)) {
            object = new StringBuilder();
            object.append(ApkSigningBlockUtils.getContentDigestAlgorithmJcaDigestAlgorithm(n5));
            object.append(" contents digest does not match the digest specified by a preceding signer");
            throw new SecurityException(object.toString());
        }
        object = ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
        arrayList = new ArrayList<E>();
        n3 = 0;
        do lbl-1000: // 2 sources:
        {
            if (!object.hasRemaining()) {
                if (arrayList.isEmpty() != false) throw new SecurityException("No certificates listed");
                if (Arrays.equals(arrby3, ((X509Certificate)arrayList.get(0)).getPublicKey().getEncoded()) == false) throw new SecurityException("Public key mismatch between certificate and signature record");
                if (byteBuffer.getInt() != n2) throw new SecurityException("minSdkVersion mismatch between signed and unsigned in v3 signer block.");
                if (byteBuffer.getInt() != n) throw new SecurityException("maxSdkVersion mismatch between signed and unsigned in v3 signer block.");
                return ApkSignatureSchemeV3Verifier.verifyAdditionalAttributes(ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer), (List<X509Certificate>)arrayList, certificateFactory);
            }
            ++n3;
            arrby = ApkSigningBlockUtils.readLengthPrefixedByteArray((ByteBuffer)object);
            object2 = new ByteArrayInputStream(arrby);
            object2 = (X509Certificate)certificateFactory.generateCertificate((InputStream)object2);
            arrayList.add(new VerbatimX509Certificate((X509Certificate)object2, arrby));
            break;
        } while (true);
        catch (CertificateException certificateException) {
            object = new StringBuilder();
            object.append("Failed to decode certificate #");
            object.append(n3);
            throw new SecurityException(object.toString(), certificateException);
        }
        {
            ** while (true)
        }
    }

    private static class PlatformNotSupportedException
    extends Exception {
        PlatformNotSupportedException(String string2) {
            super(string2);
        }
    }

    public static class VerifiedProofOfRotation {
        public final List<X509Certificate> certs;
        public final List<Integer> flagsList;

        public VerifiedProofOfRotation(List<X509Certificate> list, List<Integer> list2) {
            this.certs = list;
            this.flagsList = list2;
        }
    }

    public static class VerifiedSigner {
        public final X509Certificate[] certs;
        public final VerifiedProofOfRotation por;
        public byte[] verityRootHash;

        public VerifiedSigner(X509Certificate[] arrx509Certificate, VerifiedProofOfRotation verifiedProofOfRotation) {
            this.certs = arrx509Certificate;
            this.por = verifiedProofOfRotation;
        }
    }

}

