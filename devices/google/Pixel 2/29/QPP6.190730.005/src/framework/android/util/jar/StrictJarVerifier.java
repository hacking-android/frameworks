/*
 * Decompiled with CFR 0.145.
 */
package android.util.jar;

import android.util.jar.StrictJarManifest;
import android.util.jar.StrictJarManifestReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.jar.Attributes;
import sun.security.jca.Providers;
import sun.security.pkcs.PKCS7;
import sun.security.pkcs.SignerInfo;

class StrictJarVerifier {
    private static final String[] DIGEST_ALGORITHMS = new String[]{"SHA-512", "SHA-384", "SHA-256", "SHA1"};
    private static final String SF_ATTRIBUTE_ANDROID_APK_SIGNED_NAME = "X-Android-APK-Signed";
    private final Hashtable<String, Certificate[]> certificates = new Hashtable(5);
    private final String jarName;
    private final int mainAttributesEnd;
    private final StrictJarManifest manifest;
    private final HashMap<String, byte[]> metaEntries;
    private final boolean signatureSchemeRollbackProtectionsEnforced;
    private final Hashtable<String, HashMap<String, Attributes>> signatures = new Hashtable(5);
    private final Hashtable<String, Certificate[][]> verifiedEntries = new Hashtable();

    StrictJarVerifier(String string2, StrictJarManifest strictJarManifest, HashMap<String, byte[]> hashMap, boolean bl) {
        this.jarName = string2;
        this.manifest = strictJarManifest;
        this.metaEntries = hashMap;
        this.mainAttributesEnd = strictJarManifest.getMainAttributesEnd();
        this.signatureSchemeRollbackProtectionsEnforced = bl;
    }

    private static SecurityException failedVerification(String string2, String string3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" failed verification of ");
        stringBuilder.append(string3);
        throw new SecurityException(stringBuilder.toString());
    }

    private static SecurityException failedVerification(String string2, String string3, Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" failed verification of ");
        stringBuilder.append(string3);
        throw new SecurityException(stringBuilder.toString(), throwable);
    }

    private static SecurityException invalidDigest(String string2, String string3, String string4) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" has invalid digest for ");
        stringBuilder.append(string3);
        stringBuilder.append(" in ");
        stringBuilder.append(string4);
        throw new SecurityException(stringBuilder.toString());
    }

    private boolean verify(Attributes attributes, String string2, byte[] arrby, int n, int n2, boolean bl, boolean bl2) {
        Object object;
        for (int i = 0; i < ((String[])(object = DIGEST_ALGORITHMS)).length; ++i) {
            object = object[i];
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)object);
            ((StringBuilder)charSequence).append(string2);
            charSequence = attributes.getValue(((StringBuilder)charSequence).toString());
            if (charSequence == null) continue;
            try {
                object = MessageDigest.getInstance((String)object);
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                // empty catch block
            }
            if (bl && arrby[n2 - 1] == 10 && arrby[n2 - 2] == 10) {
                ((MessageDigest)object).update(arrby, n, n2 - 1 - n);
            } else {
                ((MessageDigest)object).update(arrby, n, n2 - n);
            }
            return StrictJarVerifier.verifyMessageDigest(((MessageDigest)object).digest(), ((String)charSequence).getBytes(StandardCharsets.ISO_8859_1));
        }
        return bl2;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static Certificate[] verifyBytes(byte[] var0, byte[] var1_3) throws GeneralSecurityException {
        block6 : {
            block5 : {
                var2_4 = null;
                var3_5 = null;
                var3_5 = var4_6 = Providers.startJarVerification();
                var2_4 = var4_6;
                var3_5 = var4_6;
                var2_4 = var4_6;
                var5_7 = new PKCS7((byte[])var0);
                var3_5 = var4_6;
                var2_4 = var4_6;
                var0 = var5_7.verify(var1_3 /* !! */ );
                if (var0 == null) ** GOTO lbl47
                var3_5 = var4_6;
                var2_4 = var4_6;
                if (((Object)var0).length == 0) ** GOTO lbl47
                var0 = var0[0];
                var3_5 = var4_6;
                var2_4 = var4_6;
                if ((var0 = var0.getCertificateChain(var5_7)) == null) ** GOTO lbl39
                var3_5 = var4_6;
                var2_4 = var4_6;
                if (var0.isEmpty()) break block5;
                var3_5 = var4_6;
                var2_4 = var4_6;
                var0 = var0.toArray(new X509Certificate[var0.size()]);
                Providers.stopJarVerification(var4_6);
                return var0;
            }
            var3_5 = var4_6;
            var2_4 = var4_6;
            try {
                var3_5 = var4_6;
                var2_4 = var4_6;
                var0 = new GeneralSecurityException("Verified SignerInfo certificate chain is emtpy");
                var3_5 = var4_6;
                var2_4 = var4_6;
                throw var0;
lbl39: // 1 sources:
                var3_5 = var4_6;
                var2_4 = var4_6;
                var3_5 = var4_6;
                var2_4 = var4_6;
                var0 = new GeneralSecurityException("Failed to find verified SignerInfo certificate chain");
                var3_5 = var4_6;
                var2_4 = var4_6;
                throw var0;
lbl47: // 2 sources:
                var3_5 = var4_6;
                var2_4 = var4_6;
                var3_5 = var4_6;
                var2_4 = var4_6;
                var0 = new GeneralSecurityException("Failed to verify signature: no verified SignerInfos");
                var3_5 = var4_6;
                var2_4 = var4_6;
                throw var0;
            }
            catch (Throwable var0_1) {
                break block6;
            }
            catch (IOException var0_2) {
                var3_5 = var2_4;
                var3_5 = var2_4;
                var1_3 /* !! */  = new GeneralSecurityException("IO exception verifying jar cert", var0_2);
                var3_5 = var2_4;
                throw var1_3 /* !! */ ;
            }
        }
        Providers.stopJarVerification(var3_5);
        throw var0_1;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void verifyCertificate(String object) {
        int n;
        byte[] arrby;
        Object object3;
        Object object2;
        Object object4;
        String string2;
        HashMap<String, Attributes> hashMap;
        block22 : {
            int n2;
            void var1_5;
            boolean bl;
            boolean bl2;
            block21 : {
                arrby = new StringBuilder();
                arrby.append(((String)object).substring(0, ((String)object).lastIndexOf(46)));
                arrby.append(".SF");
                string2 = arrby.toString();
                object2 = this.metaEntries.get(string2);
                if (object2 == null) {
                    return;
                }
                arrby = this.metaEntries.get("META-INF/MANIFEST.MF");
                if (arrby == null) {
                    return;
                }
                object4 = this.metaEntries.get(object);
                object = StrictJarVerifier.verifyBytes(object4, (byte[])object2);
                if (object == null) break block21;
                try {
                    this.certificates.put(string2, (Certificate[])object);
                }
                catch (GeneralSecurityException generalSecurityException) {
                    throw StrictJarVerifier.failedVerification(this.jarName, string2, (Throwable)var1_5);
                }
            }
            object3 = new Attributes();
            hashMap = new HashMap<String, Attributes>();
            try {
                object = new StrictJarManifestReader((byte[])object2, (Attributes)object3);
                ((StrictJarManifestReader)object).readEntries(hashMap, null);
                if (this.signatureSchemeRollbackProtectionsEnforced && (object = ((Attributes)object3).getValue(SF_ATTRIBUTE_ANDROID_APK_SIGNED_NAME)) != null) {
                    n2 = 0;
                    bl2 = false;
                    object = new StringTokenizer((String)object, ",");
                }
                break block22;
            }
            catch (IOException iOException) {
                return;
            }
            catch (GeneralSecurityException generalSecurityException) {
                // empty catch block
            }
            throw StrictJarVerifier.failedVerification(this.jarName, string2, (Throwable)var1_5);
            do {
                n = n2;
                bl = bl2;
                if (!((StringTokenizer)object).hasMoreTokens()) break;
                object2 = ((StringTokenizer)object).nextToken().trim();
                if (((String)object2).isEmpty()) continue;
                try {
                    n = Integer.parseInt((String)object2);
                    if (n == 2) {
                        n = 1;
                        bl = bl2;
                        break;
                    }
                    if (n != 3) continue;
                    bl = true;
                    n = n2;
                }
                catch (Exception exception) {
                    continue;
                }
                break;
            } while (true);
            if (n != 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" indicates ");
                ((StringBuilder)object).append(this.jarName);
                ((StringBuilder)object).append(" is signed using APK Signature Scheme v2, but no such signature was found. Signature stripped?");
                throw new SecurityException(((StringBuilder)object).toString());
            }
            if (bl) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" indicates ");
                ((StringBuilder)object).append(this.jarName);
                ((StringBuilder)object).append(" is signed using APK Signature Scheme v3, but no such signature was found. Signature stripped?");
                throw new SecurityException(((StringBuilder)object).toString());
            }
        }
        if (((Attributes)object3).get(Attributes.Name.SIGNATURE_VERSION) == null) {
            return;
        }
        boolean bl = false;
        object = ((Attributes)object3).getValue("Created-By");
        if (object != null) {
            bl = ((String)object).indexOf("signtool") != -1;
        }
        if ((n = this.mainAttributesEnd) > 0 && !bl) {
            if (!this.verify((Attributes)object3, "-Digest-Manifest-Main-Attributes", arrby, 0, n, false, true)) throw StrictJarVerifier.failedVerification(this.jarName, string2);
        }
        if (!this.verify((Attributes)object3, (String)(object = bl ? "-Digest" : "-Digest-Manifest"), arrby, 0, arrby.length, false, false)) {
            object2 = hashMap.entrySet().iterator();
            object = arrby;
            arrby = object4;
            object4 = object3;
            while (object2.hasNext()) {
                object3 = (Map.Entry)object2.next();
                StrictJarManifest.Chunk chunk = this.manifest.getChunk((String)object3.getKey());
                if (chunk == null) {
                    return;
                }
                if (!this.verify((Attributes)object3.getValue(), "-Digest", (byte[])object, chunk.start, chunk.end, bl, false)) throw StrictJarVerifier.invalidDigest(string2, (String)object3.getKey(), this.jarName);
            }
        }
        this.metaEntries.put(string2, null);
        this.signatures.put(string2, hashMap);
    }

    private static boolean verifyMessageDigest(byte[] arrby, byte[] arrby2) {
        try {
            arrby2 = Base64.getDecoder().decode(arrby2);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
        return MessageDigest.isEqual(arrby, arrby2);
    }

    void addMetaEntry(String string2, byte[] arrby) {
        this.metaEntries.put(string2.toUpperCase(Locale.US), arrby);
    }

    Certificate[][] getCertificateChains(String string2) {
        return this.verifiedEntries.get(string2);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    VerifierEntry initEntry(String string2) {
        if (this.manifest == null) return null;
        if (this.signatures.isEmpty()) {
            return null;
        }
        Attributes attributes = this.manifest.getAttributes(string2);
        if (attributes == null) {
            return null;
        }
        Object object = new ArrayList();
        for (Map.Entry<String, HashMap<String, Attributes>> entry : this.signatures.entrySet()) {
            String string3;
            Certificate[] arrcertificate;
            if (entry.getValue().get(string2) == null || (arrcertificate = this.certificates.get(string3 = entry.getKey())) == null) continue;
            ((ArrayList)object).add(arrcertificate);
        }
        if (((ArrayList)object).isEmpty()) {
            return null;
        }
        Certificate[][] arrcertificate = (Certificate[][])((ArrayList)object).toArray((T[])new Certificate[((ArrayList)object).size()][]);
        int i = 0;
        while (i < ((String[])(object = DIGEST_ALGORITHMS)).length) {
            block10 : {
                String string4 = object[i];
                object = new StringBuilder();
                ((StringBuilder)object).append(string4);
                ((StringBuilder)object).append("-Digest");
                object = attributes.getValue(((StringBuilder)object).toString());
                if (object != null) {
                    object = ((String)object).getBytes(StandardCharsets.ISO_8859_1);
                    MessageDigest messageDigest = MessageDigest.getInstance(string4);
                    Hashtable<String, Certificate[][]> hashtable = this.verifiedEntries;
                    try {
                        return new VerifierEntry(string2, messageDigest, (byte[])object, arrcertificate, hashtable);
                    }
                    catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                        break block10;
                    }
                    catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                        // empty catch block
                    }
                }
            }
            ++i;
        }
        return null;
    }

    boolean isSignedJar() {
        boolean bl = this.certificates.size() > 0;
        return bl;
    }

    boolean readCertificates() {
        synchronized (this) {
            block5 : {
                boolean bl = this.metaEntries.isEmpty();
                if (!bl) break block5;
                return false;
            }
            Iterator<String> iterator = this.metaEntries.keySet().iterator();
            while (iterator.hasNext()) {
                String string2 = iterator.next();
                if (!string2.endsWith(".DSA") && !string2.endsWith(".RSA") && !string2.endsWith(".EC")) continue;
                this.verifyCertificate(string2);
                iterator.remove();
            }
            return true;
        }
    }

    void removeMetaEntries() {
        this.metaEntries.clear();
    }

    static class VerifierEntry
    extends OutputStream {
        private final Certificate[][] certChains;
        private final MessageDigest digest;
        private final byte[] hash;
        private final String name;
        private final Hashtable<String, Certificate[][]> verifiedEntries;

        VerifierEntry(String string2, MessageDigest messageDigest, byte[] arrby, Certificate[][] arrcertificate, Hashtable<String, Certificate[][]> hashtable) {
            this.name = string2;
            this.digest = messageDigest;
            this.hash = arrby;
            this.certChains = arrcertificate;
            this.verifiedEntries = hashtable;
        }

        void verify() {
            if (StrictJarVerifier.verifyMessageDigest(this.digest.digest(), this.hash)) {
                this.verifiedEntries.put(this.name, this.certChains);
                return;
            }
            String string2 = this.name;
            throw StrictJarVerifier.invalidDigest("META-INF/MANIFEST.MF", string2, string2);
        }

        @Override
        public void write(int n) {
            this.digest.update((byte)n);
        }

        @Override
        public void write(byte[] arrby, int n, int n2) {
            this.digest.update(arrby, n, n2);
        }
    }

}

