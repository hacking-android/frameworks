/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.IoUtils
 */
package android.util.apk;

import android.content.pm.PackageParser;
import android.content.pm.Signature;
import android.os.Trace;
import android.util.apk.ApkSignatureSchemeV2Verifier;
import android.util.apk.ApkSignatureSchemeV3Verifier;
import android.util.apk.ByteBufferFactory;
import android.util.apk.SignatureNotFoundException;
import android.util.jar.StrictJarFile;
import com.android.internal.util.ArrayUtils;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipEntry;
import libcore.io.IoUtils;

public class ApkSignatureVerifier {
    private static final AtomicReference<byte[]> sBuffer = new AtomicReference();

    private static void closeQuietly(StrictJarFile strictJarFile) {
        if (strictJarFile != null) {
            try {
                strictJarFile.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public static Signature[] convertToSignatures(Certificate[][] arrcertificate) throws CertificateEncodingException {
        Signature[] arrsignature = new Signature[arrcertificate.length];
        for (int i = 0; i < arrcertificate.length; ++i) {
            arrsignature[i] = new Signature(arrcertificate[i]);
        }
        return arrsignature;
    }

    public static byte[] generateApkVerity(String string2, ByteBufferFactory byteBufferFactory) throws IOException, SignatureNotFoundException, SecurityException, DigestException, NoSuchAlgorithmException {
        try {
            byte[] arrby = ApkSignatureSchemeV3Verifier.generateApkVerity(string2, byteBufferFactory);
            return arrby;
        }
        catch (SignatureNotFoundException signatureNotFoundException) {
            return ApkSignatureSchemeV2Verifier.generateApkVerity(string2, byteBufferFactory);
        }
    }

    public static byte[] generateApkVerityRootHash(String arrby) throws NoSuchAlgorithmException, DigestException, IOException {
        try {
            byte[] arrby2 = ApkSignatureSchemeV3Verifier.generateApkVerityRootHash((String)arrby);
            return arrby2;
        }
        catch (SignatureNotFoundException signatureNotFoundException) {
            try {
                arrby = ApkSignatureSchemeV2Verifier.generateApkVerityRootHash((String)arrby);
                return arrby;
            }
            catch (SignatureNotFoundException signatureNotFoundException2) {
                return null;
            }
        }
    }

    public static byte[] getVerityRootHash(String arrby) throws IOException, SecurityException {
        try {
            byte[] arrby2 = ApkSignatureSchemeV3Verifier.getVerityRootHash((String)arrby);
            return arrby2;
        }
        catch (SignatureNotFoundException signatureNotFoundException) {
            try {
                arrby = ApkSignatureSchemeV2Verifier.getVerityRootHash((String)arrby);
                return arrby;
            }
            catch (SignatureNotFoundException signatureNotFoundException2) {
                return null;
            }
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static Certificate[][] loadCertificates(StrictJarFile strictJarFile, ZipEntry zipEntry) throws PackageParser.PackageParserException {
        Throwable throwable2222;
        InputStream inputStream;
        InputStream inputStream2 = null;
        InputStream inputStream3 = null;
        inputStream3 = inputStream = strictJarFile.getInputStream(zipEntry);
        inputStream2 = inputStream;
        ApkSignatureVerifier.readFullyIgnoringContents(inputStream);
        inputStream3 = inputStream;
        inputStream2 = inputStream;
        Certificate[][] arrcertificate = strictJarFile.getCertificateChains(zipEntry);
        IoUtils.closeQuietly((AutoCloseable)inputStream);
        return arrcertificate;
        {
            catch (Throwable throwable2222) {
            }
            catch (IOException | RuntimeException exception) {}
            inputStream3 = inputStream2;
            {
                inputStream3 = inputStream2;
                inputStream3 = inputStream2;
                StringBuilder stringBuilder = new StringBuilder();
                inputStream3 = inputStream2;
                stringBuilder.append("Failed reading ");
                inputStream3 = inputStream2;
                stringBuilder.append(zipEntry.getName());
                inputStream3 = inputStream2;
                stringBuilder.append(" in ");
                inputStream3 = inputStream2;
                stringBuilder.append(strictJarFile);
                inputStream3 = inputStream2;
                PackageParser.PackageParserException packageParserException = new PackageParser.PackageParserException(-102, stringBuilder.toString(), exception);
                inputStream3 = inputStream2;
                throw packageParserException;
            }
        }
        IoUtils.closeQuietly(inputStream3);
        throw throwable2222;
    }

    private static void readFullyIgnoringContents(InputStream inputStream) throws IOException {
        int n;
        byte[] arrby;
        byte[] arrby2 = arrby = (byte[])sBuffer.getAndSet(null);
        if (arrby == null) {
            arrby2 = new byte[4096];
        }
        int n2 = 0;
        while ((n = inputStream.read(arrby2, 0, arrby2.length)) != -1) {
            n2 += n;
        }
        sBuffer.set(arrby2);
    }

    /*
     * Exception decompiling
     */
    public static PackageParser.SigningDetails unsafeGetCertsWithoutVerification(String var0, int var1_3) throws PackageParser.PackageParserException {
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

    /*
     * Exception decompiling
     */
    public static PackageParser.SigningDetails verify(String var0, @PackageParser.SigningDetails.SignatureSchemeVersion int var1_3) throws PackageParser.PackageParserException {
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

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static PackageParser.SigningDetails verifyV1Signature(String var0, boolean var1_2) throws PackageParser.PackageParserException {
        block12 : {
            block10 : {
                block11 : {
                    var2_3 = null;
                    var3_4 = null;
                    var5_8 = var4_5 = null;
                    var6_9 = var2_3;
                    var7_10 = var3_4;
                    Trace.traceBegin(262144L, "strictJarFileCtor");
                    var5_8 = var4_5;
                    var6_9 = var2_3;
                    var7_10 = var3_4;
                    var5_8 = var4_5;
                    var6_9 = var2_3;
                    var7_10 = var3_4;
                    var5_8 = var4_5 = (var8_11 = new StrictJarFile(var0, true, var1_2));
                    var6_9 = var4_5;
                    var7_10 = var4_5;
                    var5_8 = var4_5;
                    var6_9 = var4_5;
                    var7_10 = var4_5;
                    var8_11 = new ArrayList();
                    var5_8 = var4_5;
                    var6_9 = var4_5;
                    var7_10 = var4_5;
                    var2_3 = var4_5.findEntry("AndroidManifest.xml");
                    if (var2_3 == null) ** GOTO lbl225
                    var5_8 = var4_5;
                    var6_9 = var4_5;
                    var7_10 = var4_5;
                    var2_3 = ApkSignatureVerifier.loadCertificates((StrictJarFile)var4_5, (ZipEntry)var2_3);
                    var5_8 = var4_5;
                    var6_9 = var4_5;
                    var7_10 = var4_5;
                    var9_12 = ArrayUtils.isEmpty(var2_3);
                    if (var9_12) break block10;
                    var5_8 = var4_5;
                    var6_9 = var4_5;
                    var7_10 = var4_5;
                    var2_3 = ApkSignatureVerifier.convertToSignatures((Certificate[][])var2_3);
                    if (var1_2) {
                        var5_8 = var4_5;
                        var6_9 = var4_5;
                        var7_10 = var4_5;
                        var10_13 = var4_5.iterator();
                        do {
                            var5_8 = var4_5;
                            var6_9 = var4_5;
                            var7_10 = var4_5;
                            if (!var10_13.hasNext()) break;
                            var5_8 = var4_5;
                            var6_9 = var4_5;
                            var7_10 = var4_5;
                            var3_4 = var10_13.next();
                            var5_8 = var4_5;
                            var6_9 = var4_5;
                            var7_10 = var4_5;
                            if (var3_4.isDirectory()) continue;
                            var5_8 = var4_5;
                            var6_9 = var4_5;
                            var7_10 = var4_5;
                            var11_14 = var3_4.getName();
                            var5_8 = var4_5;
                            var6_9 = var4_5;
                            var7_10 = var4_5;
                            if (var11_14.startsWith("META-INF/")) continue;
                            var5_8 = var4_5;
                            var6_9 = var4_5;
                            var7_10 = var4_5;
                            if (var11_14.equals("AndroidManifest.xml")) continue;
                            var5_8 = var4_5;
                            var6_9 = var4_5;
                            var7_10 = var4_5;
                            var8_11.add(var3_4);
                        } while (true);
                        var5_8 = var4_5;
                        var6_9 = var4_5;
                        var7_10 = var4_5;
                        var3_4 = var8_11.iterator();
                        do {
                            var5_8 = var4_5;
                            var6_9 = var4_5;
                            var7_10 = var4_5;
                            if (!var3_4.hasNext()) break block11;
                            var5_8 = var4_5;
                            var6_9 = var4_5;
                            var7_10 = var4_5;
                            var8_11 = (ZipEntry)var3_4.next();
                            var5_8 = var4_5;
                            var6_9 = var4_5;
                            var7_10 = var4_5;
                            var10_13 = ApkSignatureVerifier.loadCertificates((StrictJarFile)var4_5, (ZipEntry)var8_11);
                            var5_8 = var4_5;
                            var6_9 = var4_5;
                            var7_10 = var4_5;
                            if (ArrayUtils.isEmpty(var10_13)) {
                                var5_8 = var4_5;
                                var6_9 = var4_5;
                                var7_10 = var4_5;
                                var5_8 = var4_5;
                                var6_9 = var4_5;
                                var7_10 = var4_5;
                                var5_8 = var4_5;
                                var6_9 = var4_5;
                                var7_10 = var4_5;
                                var3_4 = new StringBuilder();
                                var5_8 = var4_5;
                                var6_9 = var4_5;
                                var7_10 = var4_5;
                                var3_4.append("Package ");
                                var5_8 = var4_5;
                                var6_9 = var4_5;
                                var7_10 = var4_5;
                                var3_4.append(var0);
                                var5_8 = var4_5;
                                var6_9 = var4_5;
                                var7_10 = var4_5;
                                var3_4.append(" has no certificates at entry ");
                                var5_8 = var4_5;
                                var6_9 = var4_5;
                                var7_10 = var4_5;
                                var3_4.append(var8_11.getName());
                                var5_8 = var4_5;
                                var6_9 = var4_5;
                                var7_10 = var4_5;
                                var2_3 = new PackageParser.PackageParserException(-103, var3_4.toString());
                                var5_8 = var4_5;
                                var6_9 = var4_5;
                                var7_10 = var4_5;
                                throw var2_3;
                            }
                            var5_8 = var4_5;
                            var6_9 = var4_5;
                            var7_10 = var4_5;
                        } while (Signature.areExactMatch((Signature[])var2_3, ApkSignatureVerifier.convertToSignatures(var10_13)));
                        var5_8 = var4_5;
                        var6_9 = var4_5;
                        var7_10 = var4_5;
                        var5_8 = var4_5;
                        var6_9 = var4_5;
                        var7_10 = var4_5;
                        var5_8 = var4_5;
                        var6_9 = var4_5;
                        var7_10 = var4_5;
                        var2_3 = new StringBuilder();
                        var5_8 = var4_5;
                        var6_9 = var4_5;
                        var7_10 = var4_5;
                        var2_3.append("Package ");
                        var5_8 = var4_5;
                        var6_9 = var4_5;
                        var7_10 = var4_5;
                        var2_3.append(var0);
                        var5_8 = var4_5;
                        var6_9 = var4_5;
                        var7_10 = var4_5;
                        var2_3.append(" has mismatched certificates at entry ");
                        var5_8 = var4_5;
                        var6_9 = var4_5;
                        var7_10 = var4_5;
                        var2_3.append(var8_11.getName());
                        var5_8 = var4_5;
                        var6_9 = var4_5;
                        var7_10 = var4_5;
                        var3_4 = new PackageParser.PackageParserException(-104, var2_3.toString());
                        var5_8 = var4_5;
                        var6_9 = var4_5;
                        var7_10 = var4_5;
                        throw var3_4;
                    }
                }
                var5_8 = var4_5;
                var6_9 = var4_5;
                var7_10 = var4_5;
                var8_11 = new PackageParser.SigningDetails((Signature[])var2_3, 1);
                Trace.traceEnd(262144L);
                ApkSignatureVerifier.closeQuietly((StrictJarFile)var4_5);
                return var8_11;
            }
            var5_8 = var4_5;
            var6_9 = var4_5;
            var7_10 = var4_5;
            try {
                var5_8 = var4_5;
                var6_9 = var4_5;
                var7_10 = var4_5;
                var5_8 = var4_5;
                var6_9 = var4_5;
                var7_10 = var4_5;
                var8_11 = new StringBuilder();
                var5_8 = var4_5;
                var6_9 = var4_5;
                var7_10 = var4_5;
                var8_11.append("Package ");
                var5_8 = var4_5;
                var6_9 = var4_5;
                var7_10 = var4_5;
                var8_11.append(var0);
                var5_8 = var4_5;
                var6_9 = var4_5;
                var7_10 = var4_5;
                var8_11.append(" has no certificates at entry ");
                var5_8 = var4_5;
                var6_9 = var4_5;
                var7_10 = var4_5;
                var8_11.append("AndroidManifest.xml");
                var5_8 = var4_5;
                var6_9 = var4_5;
                var7_10 = var4_5;
                var2_3 = new PackageParser.PackageParserException(-103, var8_11.toString());
                var5_8 = var4_5;
                var6_9 = var4_5;
                var7_10 = var4_5;
                throw var2_3;
lbl225: // 1 sources:
                var5_8 = var4_5;
                var6_9 = var4_5;
                var7_10 = var4_5;
                var5_8 = var4_5;
                var6_9 = var4_5;
                var7_10 = var4_5;
                var5_8 = var4_5;
                var6_9 = var4_5;
                var7_10 = var4_5;
                var2_3 = new StringBuilder();
                var5_8 = var4_5;
                var6_9 = var4_5;
                var7_10 = var4_5;
                var2_3.append("Package ");
                var5_8 = var4_5;
                var6_9 = var4_5;
                var7_10 = var4_5;
                var2_3.append(var0);
                var5_8 = var4_5;
                var6_9 = var4_5;
                var7_10 = var4_5;
                var2_3.append(" has no manifest");
                var5_8 = var4_5;
                var6_9 = var4_5;
                var7_10 = var4_5;
                var8_11 = new PackageParser.PackageParserException(-101, var2_3.toString());
                var5_8 = var4_5;
                var6_9 = var4_5;
                var7_10 = var4_5;
                throw var8_11;
            }
            catch (Throwable var0_1) {
                break block12;
            }
            catch (IOException | RuntimeException var4_6) {
                var5_8 = var6_9;
                var5_8 = var6_9;
                var5_8 = var6_9;
                var8_11 = new StringBuilder();
                var5_8 = var6_9;
                var8_11.append("Failed to collect certificates from ");
                var5_8 = var6_9;
                var8_11.append(var0);
                var5_8 = var6_9;
                var7_10 = new PackageParser.PackageParserException(-103, var8_11.toString(), var4_6);
                var5_8 = var6_9;
                throw var7_10;
            }
            catch (GeneralSecurityException var4_7) {
                var5_8 = var7_10;
                var5_8 = var7_10;
                var5_8 = var7_10;
                var6_9 = new StringBuilder();
                var5_8 = var7_10;
                var6_9.append("Failed to collect certificates from ");
                var5_8 = var7_10;
                var6_9.append(var0);
                var5_8 = var7_10;
                var8_11 = new PackageParser.PackageParserException(-105, var6_9.toString(), var4_7);
                var5_8 = var7_10;
                throw var8_11;
            }
        }
        Trace.traceEnd(262144L);
        ApkSignatureVerifier.closeQuietly((StrictJarFile)var5_8);
        throw var0_1;
    }

    public static class Result {
        public final Certificate[][] certs;
        public final int signatureSchemeVersion;
        public final Signature[] sigs;

        public Result(Certificate[][] arrcertificate, Signature[] arrsignature, int n) {
            this.certs = arrcertificate;
            this.sigs = arrsignature;
            this.signatureSchemeVersion = n;
        }
    }

}

