/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.AlgorithmParameters;
import java.security.CodeSigner;
import java.security.CryptoPrimitive;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.Timestamp;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarException;
import java.util.jar.Manifest;
import sun.security.jca.Providers;
import sun.security.pkcs.ContentInfo;
import sun.security.pkcs.PKCS7;
import sun.security.pkcs.SignerInfo;
import sun.security.util.Debug;
import sun.security.util.DisabledAlgorithmConstraints;
import sun.security.util.ManifestDigester;

public class SignatureFileVerifier {
    private static final String ATTR_DIGEST;
    private static final Set<CryptoPrimitive> DIGEST_PRIMITIVE_SET;
    private static final DisabledAlgorithmConstraints JAR_DISABLED_CHECK;
    private static final Debug debug;
    private static final char[] hexc;
    private PKCS7 block;
    private CertificateFactory certificateFactory = null;
    private HashMap<String, MessageDigest> createdDigests;
    private ManifestDigester md;
    private String name;
    private byte[] sfBytes;
    private ArrayList<CodeSigner[]> signerCache;
    private boolean workaround = false;

    static {
        debug = Debug.getInstance("jar");
        DIGEST_PRIMITIVE_SET = Collections.unmodifiableSet(EnumSet.of(CryptoPrimitive.MESSAGE_DIGEST));
        JAR_DISABLED_CHECK = new DisabledAlgorithmConstraints("jdk.jar.disabledAlgorithms");
        ATTR_DIGEST = "-DIGEST-Manifest-Main-Attributes".toUpperCase(Locale.ENGLISH);
        hexc = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    }

    public SignatureFileVerifier(ArrayList<CodeSigner[]> arrayList, ManifestDigester manifestDigester, String string, byte[] arrby) throws IOException, CertificateException {
        Object object;
        Object object2 = null;
        try {
            object2 = object = Providers.startJarVerification();
            object2 = object;
        }
        catch (Throwable throwable) {
            Providers.stopJarVerification(object2);
            throw throwable;
        }
        PKCS7 pKCS7 = new PKCS7(arrby);
        object2 = object;
        this.block = pKCS7;
        object2 = object;
        this.sfBytes = this.block.getContentInfo().getData();
        object2 = object;
        this.certificateFactory = CertificateFactory.getInstance("X509");
        Providers.stopJarVerification(object);
        this.name = string.substring(0, string.lastIndexOf(".")).toUpperCase(Locale.ENGLISH);
        this.md = manifestDigester;
        this.signerCache = arrayList;
    }

    static boolean contains(CodeSigner[] arrcodeSigner, CodeSigner codeSigner) {
        for (int i = 0; i < arrcodeSigner.length; ++i) {
            if (!arrcodeSigner[i].equals(codeSigner)) continue;
            return true;
        }
        return false;
    }

    private MessageDigest getDigest(String string) throws SignatureException {
        if (JAR_DISABLED_CHECK.permits(DIGEST_PRIMITIVE_SET, string, null)) {
            MessageDigest messageDigest;
            if (this.createdDigests == null) {
                this.createdDigests = new HashMap();
            }
            MessageDigest messageDigest2 = messageDigest = this.createdDigests.get(string);
            if (messageDigest == null) {
                messageDigest2 = messageDigest;
                messageDigest2 = messageDigest = MessageDigest.getInstance(string);
                try {
                    this.createdDigests.put(string, messageDigest);
                    messageDigest2 = messageDigest;
                }
                catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    // empty catch block
                }
            }
            return messageDigest2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SignatureFile check failed. Disabled algorithm used: ");
        stringBuilder.append(string);
        throw new SignatureException(stringBuilder.toString());
    }

    private CodeSigner[] getSigners(SignerInfo[] arrsignerInfo, PKCS7 pKCS7) throws IOException, NoSuchAlgorithmException, SignatureException, CertificateException {
        ArrayList<CodeSigner> arrayList = null;
        for (int i = 0; i < arrsignerInfo.length; ++i) {
            Object object = arrsignerInfo[i];
            ArrayList<X509Certificate> arrayList2 = ((SignerInfo)object).getCertificateChain(pKCS7);
            CertPath certPath = this.certificateFactory.generateCertPath(arrayList2);
            ArrayList<CodeSigner> arrayList3 = arrayList;
            if (arrayList == null) {
                arrayList3 = new ArrayList<CodeSigner>();
            }
            arrayList3.add(new CodeSigner(certPath, ((SignerInfo)object).getTimestamp()));
            arrayList = debug;
            if (arrayList != null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Signature Block Certificate: ");
                ((StringBuilder)object).append(arrayList2.get(0));
                ((Debug)((Object)arrayList)).println(((StringBuilder)object).toString());
            }
            arrayList = arrayList3;
        }
        if (arrayList != null) {
            return arrayList.toArray(new CodeSigner[arrayList.size()]);
        }
        return null;
    }

    public static boolean isBlockOrSF(String string) {
        return string.endsWith(".SF") || string.endsWith(".DSA") || string.endsWith(".RSA") || string.endsWith(".EC");
        {
        }
    }

    public static boolean isSigningRelated(String string) {
        if (!(string = string.toUpperCase(Locale.ENGLISH)).startsWith("META-INF/")) {
            return false;
        }
        if ((string = string.substring(9)).indexOf(47) != -1) {
            return false;
        }
        if (!SignatureFileVerifier.isBlockOrSF(string) && !string.equals("MANIFEST.MF")) {
            if (string.startsWith("SIG-")) {
                int n = string.lastIndexOf(46);
                if (n != -1) {
                    if ((string = string.substring(n + 1)).length() <= 3 && string.length() >= 1) {
                        for (n = 0; n < string.length(); ++n) {
                            char c = string.charAt(n);
                            if (c >= 'A' && c <= 'Z' || c >= '0' && c <= '9') {
                                continue;
                            }
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        return true;
    }

    static boolean isSubSet(CodeSigner[] arrcodeSigner, CodeSigner[] arrcodeSigner2) {
        if (arrcodeSigner2 == arrcodeSigner) {
            return true;
        }
        for (int i = 0; i < arrcodeSigner.length; ++i) {
            if (SignatureFileVerifier.contains(arrcodeSigner2, arrcodeSigner[i])) continue;
            return false;
        }
        return true;
    }

    static boolean matches(CodeSigner[] arrcodeSigner, CodeSigner[] arrcodeSigner2, CodeSigner[] arrcodeSigner3) {
        if (arrcodeSigner2 == null && arrcodeSigner == arrcodeSigner3) {
            return true;
        }
        if (arrcodeSigner2 != null && !SignatureFileVerifier.isSubSet(arrcodeSigner2, arrcodeSigner)) {
            return false;
        }
        if (!SignatureFileVerifier.isSubSet(arrcodeSigner3, arrcodeSigner)) {
            return false;
        }
        for (int i = 0; i < arrcodeSigner.length; ++i) {
            boolean bl = arrcodeSigner2 != null && SignatureFileVerifier.contains(arrcodeSigner2, arrcodeSigner[i]) || SignatureFileVerifier.contains(arrcodeSigner3, arrcodeSigner[i]);
            if (bl) continue;
            return false;
        }
        return true;
    }

    private void processImpl(Hashtable<String, CodeSigner[]> serializable, List<Object> object) throws IOException, SignatureException, NoSuchAlgorithmException, JarException, CertificateException {
        Object object2 = new Manifest();
        ((Manifest)object2).read(new ByteArrayInputStream(this.sfBytes));
        Object[] arrobject = ((Manifest)object2).getMainAttributes().getValue(Attributes.Name.SIGNATURE_VERSION);
        if (arrobject != null && arrobject.equalsIgnoreCase("1.0")) {
            arrobject = this.block.verify(this.sfBytes);
            if (arrobject != null) {
                if ((arrobject = this.getSigners((SignerInfo[])arrobject, this.block)) == null) {
                    return;
                }
                Iterator<Map.Entry<String, Attributes>> iterator = ((Manifest)object2).getEntries().entrySet().iterator();
                boolean bl = this.verifyManifestHash((Manifest)object2, this.md, (List<Object>)object);
                if (!bl && !this.verifyManifestMainAttrs((Manifest)object2, this.md)) {
                    throw new SecurityException("Invalid signature file digest for Manifest main attributes");
                }
                while (iterator.hasNext()) {
                    Debug debug;
                    object = iterator.next();
                    object2 = (String)object.getKey();
                    if (!bl && !this.verifySection((Attributes)object.getValue(), (String)object2, this.md)) {
                        debug = SignatureFileVerifier.debug;
                        if (debug == null) continue;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("processSignature unsigned name = ");
                        ((StringBuilder)object).append((String)object2);
                        debug.println(((StringBuilder)object).toString());
                        continue;
                    }
                    object = object2;
                    if (((String)object2).startsWith("./")) {
                        object = ((String)object2).substring(2);
                    }
                    object2 = object;
                    if (((String)object).startsWith("/")) {
                        object2 = ((String)object).substring(1);
                    }
                    this.updateSigners((CodeSigner[])arrobject, (Hashtable<String, CodeSigner[]>)serializable, (String)object2);
                    debug = SignatureFileVerifier.debug;
                    if (debug == null) continue;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("processSignature signed name = ");
                    ((StringBuilder)object).append((String)object2);
                    debug.println(((StringBuilder)object).toString());
                }
                this.updateSigners((CodeSigner[])arrobject, (Hashtable<String, CodeSigner[]>)serializable, "META-INF/MANIFEST.MF");
                return;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("cannot verify signature block file ");
            ((StringBuilder)serializable).append(this.name);
            throw new SecurityException(((StringBuilder)serializable).toString());
        }
    }

    static String toHex(byte[] arrby) {
        StringBuffer stringBuffer = new StringBuffer(arrby.length * 2);
        for (int i = 0; i < arrby.length; ++i) {
            stringBuffer.append(hexc[arrby[i] >> 4 & 15]);
            stringBuffer.append(hexc[arrby[i] & 15]);
        }
        return stringBuffer.toString();
    }

    private boolean verifyManifestHash(Manifest object, ManifestDigester manifestDigester, List<Object> list) throws IOException, SignatureException {
        object = ((Manifest)((Object)object)).getMainAttributes();
        boolean bl = false;
        for (Map.Entry<Object, Object> entry : ((Attributes)((Object)object)).entrySet()) {
            Object object2 = entry.getKey().toString();
            boolean bl2 = bl;
            if (((String)object2).toUpperCase(Locale.ENGLISH).endsWith("-DIGEST-MANIFEST")) {
                byte[] arrby = ((String)object2).substring(0, ((String)object2).length() - 16);
                list.add(object2);
                list.add(entry.getValue());
                object2 = this.getDigest((String)arrby);
                bl2 = bl;
                if (object2 != null) {
                    arrby = manifestDigester.manifestDigest((MessageDigest)object2);
                    byte[] arrby2 = Base64.getMimeDecoder().decode((String)entry.getValue());
                    Debug debug = SignatureFileVerifier.debug;
                    if (debug != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Signature File: Manifest digest ");
                        stringBuilder.append(((MessageDigest)object2).getAlgorithm());
                        debug.println(stringBuilder.toString());
                        debug = SignatureFileVerifier.debug;
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("  sigfile  ");
                        ((StringBuilder)object2).append(SignatureFileVerifier.toHex(arrby2));
                        debug.println(((StringBuilder)object2).toString());
                        debug = SignatureFileVerifier.debug;
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("  computed ");
                        ((StringBuilder)object2).append(SignatureFileVerifier.toHex(arrby));
                        debug.println(((StringBuilder)object2).toString());
                        SignatureFileVerifier.debug.println();
                    }
                    bl2 = bl;
                    if (MessageDigest.isEqual(arrby, arrby2)) {
                        bl2 = true;
                    }
                }
            }
            bl = bl2;
        }
        return bl;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private boolean verifyManifestMainAttrs(Manifest var1_1, ManifestDigester var2_2) throws IOException, SignatureException {
        var1_1 = var1_1.getMainAttributes();
        var3_3 = true;
        var1_1 = var1_1.entrySet().iterator();
        do lbl-1000: // 3 sources:
        {
            var4_4 = var3_3;
            if (var1_1.hasNext() == false) return var4_4;
            var5_5 = var1_1.next();
            var6_6 = var5_5.getKey().toString();
            if (!var6_6.toUpperCase(Locale.ENGLISH).endsWith(SignatureFileVerifier.ATTR_DIGEST) || (var7_7 = this.getDigest(var6_6.substring(0, var6_6.length() - SignatureFileVerifier.ATTR_DIGEST.length()))) == null) ** GOTO lbl-1000
            var6_6 = var2_2.get("Manifest-Main-Attributes", false).digest((MessageDigest)var7_7);
            var5_5 = Base64.getMimeDecoder().decode((String)var5_5.getValue());
            var8_8 = SignatureFileVerifier.debug;
            if (var8_8 == null) continue;
            var9_9 = new StringBuilder();
            var9_9.append("Signature File: Manifest Main Attributes digest ");
            var9_9.append(var7_7.getAlgorithm());
            var8_8.println(var9_9.toString());
            var9_9 = SignatureFileVerifier.debug;
            var7_7 = new StringBuilder();
            var7_7.append("  sigfile  ");
            var7_7.append(SignatureFileVerifier.toHex(var5_5));
            var9_9.println(var7_7.toString());
            var7_7 = SignatureFileVerifier.debug;
            var9_9 = new StringBuilder();
            var9_9.append("  computed ");
            var9_9.append(SignatureFileVerifier.toHex(var6_6));
            var7_7.println(var9_9.toString());
            SignatureFileVerifier.debug.println();
        } while (MessageDigest.isEqual(var6_6, var5_5));
        var3_3 = false;
        var1_1 = SignatureFileVerifier.debug;
        var4_4 = var3_3;
        if (var1_1 == null) return var4_4;
        var1_1.println("Verification of Manifest main attributes failed");
        SignatureFileVerifier.debug.println();
        return var3_3;
    }

    private boolean verifySection(Attributes object, String string, ManifestDigester object2) throws IOException, SignatureException {
        boolean bl = false;
        boolean bl2 = false;
        if ((object2 = ((ManifestDigester)object2).get(string, this.block.isOldStyle())) != null) {
            if (object != null) {
                Iterator<Map.Entry<Object, Object>> iterator = ((Attributes)object).entrySet().iterator();
                do {
                    bl = bl2;
                    if (!iterator.hasNext()) break;
                    object = iterator.next();
                    Object object3 = object.getKey().toString();
                    bl = bl2;
                    if (((String)object3).toUpperCase(Locale.ENGLISH).endsWith("-DIGEST")) {
                        object3 = this.getDigest(((String)object3).substring(0, ((String)object3).length() - 7));
                        bl = bl2;
                        if (object3 != null) {
                            boolean bl3;
                            Object object4;
                            boolean bl4 = false;
                            Object object5 = Base64.getMimeDecoder().decode((String)object.getValue());
                            object = this.workaround ? ((ManifestDigester.Entry)object2).digestWorkaround((MessageDigest)object3) : ((ManifestDigester.Entry)object2).digest((MessageDigest)object3);
                            Object object6 = debug;
                            if (object6 != null) {
                                object4 = new StringBuilder();
                                ((StringBuilder)object4).append("Signature Block File: ");
                                ((StringBuilder)object4).append(string);
                                ((StringBuilder)object4).append(" digest=");
                                ((StringBuilder)object4).append(((MessageDigest)object3).getAlgorithm());
                                ((Debug)object6).println(((StringBuilder)object4).toString());
                                object6 = debug;
                                object4 = new StringBuilder();
                                ((StringBuilder)object4).append("  expected ");
                                ((StringBuilder)object4).append(SignatureFileVerifier.toHex((byte[])object5));
                                ((Debug)object6).println(((StringBuilder)object4).toString());
                                object4 = debug;
                                object6 = new StringBuilder();
                                ((StringBuilder)object6).append("  computed ");
                                ((StringBuilder)object6).append(SignatureFileVerifier.toHex((byte[])object));
                                ((Debug)object4).println(((StringBuilder)object6).toString());
                                debug.println();
                            }
                            if (MessageDigest.isEqual((byte[])object, (byte[])object5)) {
                                bl = true;
                                bl3 = true;
                            } else {
                                bl = bl2;
                                bl3 = bl4;
                                if (!this.workaround) {
                                    object = ((ManifestDigester.Entry)object2).digestWorkaround((MessageDigest)object3);
                                    bl = bl2;
                                    bl3 = bl4;
                                    if (MessageDigest.isEqual((byte[])object, (byte[])object5)) {
                                        object5 = debug;
                                        if (object5 != null) {
                                            object4 = new StringBuilder();
                                            ((StringBuilder)object4).append("  re-computed ");
                                            ((StringBuilder)object4).append(SignatureFileVerifier.toHex((byte[])object));
                                            ((Debug)object5).println(((StringBuilder)object4).toString());
                                            debug.println();
                                        }
                                        this.workaround = true;
                                        bl = true;
                                        bl3 = true;
                                    }
                                }
                            }
                            if (!bl3) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("invalid ");
                                ((StringBuilder)object).append(((MessageDigest)object3).getAlgorithm());
                                ((StringBuilder)object).append(" signature file digest for ");
                                ((StringBuilder)object).append(string);
                                throw new SecurityException(((StringBuilder)object).toString());
                            }
                        }
                    }
                    bl2 = bl;
                } while (true);
            }
            return bl;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("no manifest section for signature file entry ");
        ((StringBuilder)object).append(string);
        throw new SecurityException(((StringBuilder)object).toString());
    }

    public boolean needSignatureFile(String string) {
        return this.name.equalsIgnoreCase(string);
    }

    public boolean needSignatureFileBytes() {
        boolean bl = this.sfBytes == null;
        return bl;
    }

    public void process(Hashtable<String, CodeSigner[]> hashtable, List<Object> list) throws IOException, SignatureException, NoSuchAlgorithmException, JarException, CertificateException {
        Object object;
        Object object2 = null;
        try {
            object2 = object = Providers.startJarVerification();
        }
        catch (Throwable throwable) {
            Providers.stopJarVerification(object2);
            throw throwable;
        }
        this.processImpl(hashtable, list);
        Providers.stopJarVerification(object);
    }

    public void setSignatureFile(byte[] arrby) {
        this.sfBytes = arrby;
    }

    void updateSigners(CodeSigner[] arrcodeSigner, Hashtable<String, CodeSigner[]> hashtable, String string) {
        CodeSigner[] arrcodeSigner2;
        CodeSigner[] arrcodeSigner3 = hashtable.get(string);
        for (int i = this.signerCache.size() - 1; i != -1; --i) {
            arrcodeSigner2 = this.signerCache.get(i);
            if (!SignatureFileVerifier.matches(arrcodeSigner2, arrcodeSigner3, arrcodeSigner)) continue;
            hashtable.put(string, arrcodeSigner2);
            return;
        }
        if (arrcodeSigner3 != null) {
            arrcodeSigner2 = new CodeSigner[arrcodeSigner3.length + arrcodeSigner.length];
            System.arraycopy(arrcodeSigner3, 0, arrcodeSigner2, 0, arrcodeSigner3.length);
            System.arraycopy(arrcodeSigner, 0, arrcodeSigner2, arrcodeSigner3.length, arrcodeSigner.length);
            arrcodeSigner = arrcodeSigner2;
        }
        this.signerCache.add(arrcodeSigner);
        hashtable.put(string, arrcodeSigner);
    }
}

