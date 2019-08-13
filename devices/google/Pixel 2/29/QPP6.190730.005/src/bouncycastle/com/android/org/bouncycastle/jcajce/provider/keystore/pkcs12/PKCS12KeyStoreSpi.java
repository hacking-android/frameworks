/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.keystore.pkcs12;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1Set;
import com.android.org.bouncycastle.asn1.DERBMPString;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.AuthenticatedSafe;
import com.android.org.bouncycastle.asn1.pkcs.CertBag;
import com.android.org.bouncycastle.asn1.pkcs.ContentInfo;
import com.android.org.bouncycastle.asn1.pkcs.EncryptedData;
import com.android.org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo;
import com.android.org.bouncycastle.asn1.pkcs.EncryptionScheme;
import com.android.org.bouncycastle.asn1.pkcs.KeyDerivationFunc;
import com.android.org.bouncycastle.asn1.pkcs.MacData;
import com.android.org.bouncycastle.asn1.pkcs.PBES2Parameters;
import com.android.org.bouncycastle.asn1.pkcs.PBKDF2Params;
import com.android.org.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.Pfx;
import com.android.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.org.bouncycastle.asn1.pkcs.SafeBag;
import com.android.org.bouncycastle.asn1.util.ASN1Dump;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import com.android.org.bouncycastle.asn1.x509.DigestInfo;
import com.android.org.bouncycastle.asn1.x509.Extension;
import com.android.org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.digests.AndroidDigestFactory;
import com.android.org.bouncycastle.jcajce.PKCS12Key;
import com.android.org.bouncycastle.jcajce.PKCS12StoreParameter;
import com.android.org.bouncycastle.jcajce.spec.PBKDF2KeySpec;
import com.android.org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import com.android.org.bouncycastle.jcajce.util.JcaJceHelper;
import com.android.org.bouncycastle.jce.interfaces.BCKeyStore;
import com.android.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import com.android.org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.android.org.bouncycastle.jce.provider.JDKPKCS12StoreParameter;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Integers;
import com.android.org.bouncycastle.util.Properties;
import com.android.org.bouncycastle.util.Strings;
import com.android.org.bouncycastle.util.encoders.Hex;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class PKCS12KeyStoreSpi
extends KeyStoreSpi
implements PKCSObjectIdentifiers,
X509ObjectIdentifiers,
BCKeyStore {
    static final int CERTIFICATE = 1;
    static final int KEY = 2;
    static final int KEY_PRIVATE = 0;
    static final int KEY_PUBLIC = 1;
    static final int KEY_SECRET = 2;
    private static final int MIN_ITERATIONS = 51200;
    static final int NULL = 0;
    static final String PKCS12_MAX_IT_COUNT_PROPERTY = "com.android.org.bouncycastle.pkcs12.max_it_count";
    private static final int SALT_SIZE = 20;
    static final int SEALED = 4;
    static final int SECRET = 3;
    private static final DefaultSecretKeyProvider keySizeProvider = new DefaultSecretKeyProvider();
    private ASN1ObjectIdentifier certAlgorithm;
    private CertificateFactory certFact;
    private IgnoresCaseHashtable certs = new IgnoresCaseHashtable();
    private Hashtable chainCerts = new Hashtable();
    private final JcaJceHelper helper = new DefaultJcaJceHelper();
    private int itCount = 102400;
    private ASN1ObjectIdentifier keyAlgorithm;
    private Hashtable keyCerts = new Hashtable();
    private IgnoresCaseHashtable keys = new IgnoresCaseHashtable();
    private Hashtable localIds = new Hashtable();
    private AlgorithmIdentifier macAlgorithm = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE);
    protected SecureRandom random = CryptoServicesRegistrar.getSecureRandom();
    private int saltLength = 20;

    public PKCS12KeyStoreSpi(JcaJceHelper jcaJceHelper, ASN1ObjectIdentifier object, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.keyAlgorithm = object;
        this.certAlgorithm = aSN1ObjectIdentifier;
        try {
            this.certFact = jcaJceHelper.createCertificateFactory("X.509");
            return;
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("can't create cert factory - ");
            ((StringBuilder)object).append(exception.toString());
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
    }

    private byte[] calculatePbeMac(ASN1ObjectIdentifier object, byte[] object2, int n, char[] arrc, boolean bl, byte[] arrby) throws Exception {
        object2 = new PBEParameterSpec((byte[])object2, n);
        object = this.helper.createMac(((ASN1ObjectIdentifier)object).getId());
        ((Mac)object).init(new PKCS12Key(arrc, bl), (AlgorithmParameterSpec)object2);
        ((Mac)object).update(arrby);
        return ((Mac)object).doFinal();
    }

    private Cipher createCipher(int n, char[] object, AlgorithmIdentifier aSN1Encodable) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchProviderException {
        aSN1Encodable = PBES2Parameters.getInstance(aSN1Encodable.getParameters());
        Object object2 = PBKDF2Params.getInstance(((PBES2Parameters)aSN1Encodable).getKeyDerivationFunc().getParameters());
        AlgorithmIdentifier algorithmIdentifier = AlgorithmIdentifier.getInstance(((PBES2Parameters)aSN1Encodable).getEncryptionScheme());
        SecretKeyFactory secretKeyFactory = this.helper.createSecretKeyFactory(((PBES2Parameters)aSN1Encodable).getKeyDerivationFunc().getAlgorithm().getId());
        object = ((PBKDF2Params)object2).isDefaultPrf() ? secretKeyFactory.generateSecret(new PBEKeySpec((char[])object, ((PBKDF2Params)object2).getSalt(), this.validateIterationCount(((PBKDF2Params)object2).getIterationCount()), keySizeProvider.getKeySize(algorithmIdentifier))) : secretKeyFactory.generateSecret(new PBKDF2KeySpec((char[])object, ((PBKDF2Params)object2).getSalt(), this.validateIterationCount(((PBKDF2Params)object2).getIterationCount()), keySizeProvider.getKeySize(algorithmIdentifier), ((PBKDF2Params)object2).getPrf()));
        object2 = Cipher.getInstance(((PBES2Parameters)aSN1Encodable).getEncryptionScheme().getAlgorithm().getId());
        if ((aSN1Encodable = ((PBES2Parameters)aSN1Encodable).getEncryptionScheme().getParameters()) instanceof ASN1OctetString) {
            ((Cipher)object2).init(n, (Key)object, new IvParameterSpec(ASN1OctetString.getInstance(aSN1Encodable).getOctets()));
        }
        return object2;
    }

    private SubjectKeyIdentifier createSubjectKeyId(PublicKey object) {
        try {
            object = new SubjectKeyIdentifier(PKCS12KeyStoreSpi.getDigest(SubjectPublicKeyInfo.getInstance(object.getEncoded())));
            return object;
        }
        catch (Exception exception) {
            throw new RuntimeException("error creating key");
        }
    }

    /*
     * Exception decompiling
     */
    private void doStore(OutputStream var1_1, char[] var2_22, boolean var3_23) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [37[WHILELOOP]], but top level block is 8[TRYBLOCK]
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

    private static byte[] getDigest(SubjectPublicKeyInfo arrby) {
        Digest digest = AndroidDigestFactory.getSHA1();
        byte[] arrby2 = new byte[digest.getDigestSize()];
        arrby = arrby.getPublicKeyData().getBytes();
        digest.update(arrby, 0, arrby.length);
        digest.doFinal(arrby2, 0);
        return arrby2;
    }

    private Set getUsedCertificateSet() {
        HashSet<Certificate> hashSet = new HashSet<Certificate>();
        Enumeration enumeration = this.keys.keys();
        while (enumeration.hasMoreElements()) {
            Certificate[] arrcertificate = this.engineGetCertificateChain((String)enumeration.nextElement());
            for (int i = 0; i != arrcertificate.length; ++i) {
                hashSet.add(arrcertificate[i]);
            }
        }
        enumeration = this.certs.keys();
        while (enumeration.hasMoreElements()) {
            hashSet.add(this.engineGetCertificate((String)enumeration.nextElement()));
        }
        return hashSet;
    }

    private int validateIterationCount(BigInteger serializable) {
        int n = ((BigInteger)serializable).intValue();
        if (n >= 0) {
            BigInteger bigInteger = Properties.asBigInteger(PKCS12_MAX_IT_COUNT_PROPERTY);
            if (bigInteger != null && bigInteger.intValue() < n) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("iteration count ");
                ((StringBuilder)serializable).append(n);
                ((StringBuilder)serializable).append(" greater than ");
                ((StringBuilder)serializable).append(bigInteger.intValue());
                throw new IllegalStateException(((StringBuilder)serializable).toString());
            }
            return n;
        }
        throw new IllegalStateException("negative iteration count found");
    }

    protected byte[] cryptData(boolean bl, AlgorithmIdentifier arrby, char[] object, boolean bl2, byte[] arrby2) throws IOException {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = arrby.getAlgorithm();
        int n = bl ? 1 : 2;
        if (aSN1ObjectIdentifier.on(PKCSObjectIdentifiers.pkcs_12PbeIds)) {
            Object object2 = PKCS12PBEParams.getInstance(arrby.getParameters());
            try {
                arrby = new PBEParameterSpec(((PKCS12PBEParams)object2).getIV(), ((PKCS12PBEParams)object2).getIterations().intValue());
                object2 = new PKCS12Key((char[])object, bl2);
                object = this.helper.createCipher(aSN1ObjectIdentifier.getId());
                ((Cipher)object).init(n, (Key)object2, (AlgorithmParameterSpec)arrby);
                arrby = ((Cipher)object).doFinal(arrby2);
                return arrby;
            }
            catch (Exception exception) {
                arrby = new StringBuilder();
                arrby.append("exception decrypting data - ");
                arrby.append(exception.toString());
                throw new IOException(arrby.toString());
            }
        }
        if (aSN1ObjectIdentifier.equals(PKCSObjectIdentifiers.id_PBES2)) {
            try {
                arrby = this.createCipher(n, (char[])object, (AlgorithmIdentifier)arrby).doFinal(arrby2);
                return arrby;
            }
            catch (Exception exception) {
                arrby = new StringBuilder();
                arrby.append("exception decrypting data - ");
                arrby.append(exception.toString());
                throw new IOException(arrby.toString());
            }
        }
        arrby = new StringBuilder();
        arrby.append("unknown PBE algorithm: ");
        arrby.append(aSN1ObjectIdentifier);
        throw new IOException(arrby.toString());
    }

    public Enumeration engineAliases() {
        Hashtable<Object, String> hashtable = new Hashtable<Object, String>();
        Object object = this.certs.keys();
        while (object.hasMoreElements()) {
            hashtable.put(object.nextElement(), "cert");
        }
        Enumeration enumeration = this.keys.keys();
        while (enumeration.hasMoreElements()) {
            object = (String)enumeration.nextElement();
            if (hashtable.get(object) != null) continue;
            hashtable.put(object, "key");
        }
        return hashtable.keys();
    }

    @Override
    public boolean engineContainsAlias(String string) {
        boolean bl = this.certs.get(string) != null || this.keys.get(string) != null;
        return bl;
    }

    @Override
    public void engineDeleteEntry(String object) throws KeyStoreException {
        Object object2 = (Key)this.keys.remove((String)object);
        Certificate certificate = (Certificate)this.certs.remove((String)object);
        if (certificate != null) {
            this.chainCerts.remove(new CertId(certificate.getPublicKey()));
        }
        if (object2 != null) {
            object2 = (String)this.localIds.remove(object);
            object = certificate;
            if (object2 != null) {
                object = (Certificate)this.keyCerts.remove(object2);
            }
            if (object != null) {
                this.chainCerts.remove(new CertId(((Certificate)object).getPublicKey()));
            }
        }
    }

    @Override
    public Certificate engineGetCertificate(String string) {
        if (string != null) {
            Certificate certificate = (Certificate)this.certs.get(string);
            Object object = certificate;
            if (certificate == null) {
                object = (String)this.localIds.get(string);
                object = object != null ? (Certificate)this.keyCerts.get(object) : (Certificate)this.keyCerts.get(string);
            }
            return object;
        }
        throw new IllegalArgumentException("null alias passed to getCertificate.");
    }

    @Override
    public String engineGetCertificateAlias(Certificate certificate) {
        Certificate certificate2;
        Object object;
        Object object2 = this.certs.elements();
        Enumeration enumeration = this.certs.keys();
        while (object2.hasMoreElements()) {
            certificate2 = (Certificate)object2.nextElement();
            object = (String)enumeration.nextElement();
            if (!certificate2.equals(certificate)) continue;
            return object;
        }
        enumeration = this.keyCerts.elements();
        object = this.keyCerts.keys();
        while (enumeration.hasMoreElements()) {
            certificate2 = (Certificate)enumeration.nextElement();
            object2 = (String)object.nextElement();
            if (!certificate2.equals(certificate)) continue;
            return object2;
        }
        return null;
    }

    @Override
    public Certificate[] engineGetCertificateChain(String object) {
        if (object != null) {
            if (!this.engineIsKeyEntry((String)object)) {
                return null;
            }
            Object object2 = this.engineGetCertificate((String)object);
            if (object2 != null) {
                Vector<Certificate> vector = new Vector<Certificate>();
                while (object2 != null) {
                    X509Certificate x509Certificate = (X509Certificate)object2;
                    object = null;
                    Object object3 = null;
                    Object object4 = x509Certificate.getExtensionValue(Extension.authorityKeyIdentifier.getId());
                    if (object4 != null) {
                        try {
                            object = new ASN1InputStream((byte[])object4);
                            object = ((ASN1OctetString)((ASN1InputStream)object).readObject()).getOctets();
                            object4 = new ASN1InputStream((byte[])object);
                            object4 = AuthorityKeyIdentifier.getInstance(((ASN1InputStream)object4).readObject());
                            object = object3;
                        }
                        catch (IOException iOException) {
                            throw new RuntimeException(iOException.toString());
                        }
                        if (((AuthorityKeyIdentifier)object4).getKeyIdentifier() != null) {
                            object = this.chainCerts;
                            object3 = new CertId(((AuthorityKeyIdentifier)object4).getKeyIdentifier());
                            object = (Certificate)((Hashtable)object).get(object3);
                        }
                    }
                    object3 = object;
                    if (object == null) {
                        object4 = x509Certificate.getIssuerDN();
                        object3 = object;
                        if (!object4.equals(x509Certificate.getSubjectDN())) {
                            Enumeration enumeration = this.chainCerts.keys();
                            do {
                                object3 = object;
                                if (!enumeration.hasMoreElements()) break;
                                object3 = (X509Certificate)this.chainCerts.get(enumeration.nextElement());
                                if (!((X509Certificate)object3).getSubjectDN().equals(object4)) continue;
                                try {
                                    x509Certificate.verify(((Certificate)object3).getPublicKey());
                                }
                                catch (Exception exception) {
                                    continue;
                                }
                                break;
                            } while (true);
                        }
                    }
                    if (vector.contains(object2)) {
                        object = null;
                    } else {
                        vector.addElement((Certificate)object2);
                        object = object3 != object2 ? object3 : null;
                    }
                    object2 = object;
                }
                object = new Certificate[vector.size()];
                for (int i = 0; i != ((Certificate[])object).length; ++i) {
                    object[i] = (Certificate)vector.elementAt(i);
                }
                return object;
            }
            return null;
        }
        throw new IllegalArgumentException("null alias passed to getCertificateChain.");
    }

    @Override
    public Date engineGetCreationDate(String string) {
        if (string != null) {
            if (this.keys.get(string) == null && this.certs.get(string) == null) {
                return null;
            }
            return new Date();
        }
        throw new NullPointerException("alias == null");
    }

    @Override
    public Key engineGetKey(String string, char[] arrc) throws NoSuchAlgorithmException, UnrecoverableKeyException {
        if (string != null) {
            return (Key)this.keys.get(string);
        }
        throw new IllegalArgumentException("null alias passed to getKey.");
    }

    @Override
    public boolean engineIsCertificateEntry(String string) {
        boolean bl = this.certs.get(string) != null && this.keys.get(string) == null;
        return bl;
    }

    @Override
    public boolean engineIsKeyEntry(String string) {
        boolean bl = this.keys.get(string) != null;
        return bl;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void engineLoad(InputStream var1_1, char[] var2_12) throws IOException {
        block68 : {
            block69 : {
                block70 : {
                    block74 : {
                        block66 : {
                            block65 : {
                                block67 : {
                                    var3_13 = var2_12;
                                    if (var1_1 == null) {
                                        return;
                                    }
                                    if (var3_13 == null) throw new NullPointerException("No password supplied for PKCS#12 KeyStore.");
                                    var1_1 = new BufferedInputStream((InputStream)var1_1);
                                    var1_1.mark(10);
                                    var4_14 = var1_1.read();
                                    if (var4_14 != 48) throw new IOException("stream does not represent a PKCS12 key store");
                                    var1_1.reset();
                                    var1_1 = new ASN1InputStream((InputStream)var1_1);
                                    try {
                                        var5_15 = Pfx.getInstance(var1_1.readObject());
                                        var6_16 = var5_15.getAuthSafe();
                                        var7_17 = new Vector<Object>();
                                        var8_18 = 0;
                                        var9_19 = 0;
                                        var10_20 = false;
                                        if (var5_15.getMacData() == null) break block65;
                                        var11_21 = var5_15.getMacData();
                                        var12_22 = var11_21.getMac();
                                        this.macAlgorithm = var12_22.getAlgorithmId();
                                        var1_1 = var11_21.getSalt();
                                        this.itCount = this.validateIterationCount(var11_21.getIterationCount());
                                    }
                                    catch (Exception var1_11) {
                                        throw new IOException(var1_11.getMessage());
                                    }
                                    this.saltLength = ((Object)var1_1).length;
                                    var11_21 = ((ASN1OctetString)var6_16.getContent()).getOctets();
                                    var13_23 = this.macAlgorithm.getAlgorithm();
                                    var14_24 = this.itCount;
                                    var13_23 = this.calculatePbeMac((ASN1ObjectIdentifier)var13_23, (byte[])var1_1, var14_24, (char[])var2_12, false, (byte[])var11_21);
                                    var12_22 = var12_22.getDigest();
                                    if (Arrays.constantTimeAreEqual((byte[])var13_23, (byte[])var12_22)) break block66;
                                    var14_24 = ((Object)var3_13).length;
                                    if (var14_24 > 0) ** GOTO lbl44
                                    var3_13 = this.macAlgorithm.getAlgorithm();
                                    var14_24 = this.itCount;
                                    try {
                                        block73 : {
                                            if (!Arrays.constantTimeAreEqual(this.calculatePbeMac((ASN1ObjectIdentifier)var3_13, (byte[])var1_1, var14_24, (char[])var2_12, true, (byte[])var11_21), (byte[])var12_22)) {
                                                var1_1 = new IOException("PKCS12 key store mac invalid - wrong password or corrupted file.");
                                                throw var1_1;
                                            }
                                            break block73;
lbl44: // 1 sources:
                                            var1_1 = new IOException("PKCS12 key store mac invalid - wrong password or corrupted file.");
                                            throw var1_1;
                                        }
                                        var10_20 = true;
                                        break block66;
                                    }
                                    catch (Exception var1_2) {
                                        break block67;
                                    }
                                    catch (IOException var1_3) {
                                        throw var1_9;
                                    }
                                    catch (Exception var1_4) {
                                    }
                                    catch (IOException var1_5) {
                                        throw var1_9;
                                    }
                                    catch (Exception var1_6) {
                                        // empty catch block
                                    }
                                }
                                var2_12 = new StringBuilder();
                                var2_12.append("error constructing MAC: ");
                                var2_12.append(var1_7.toString());
                                throw new IOException(var2_12.toString());
                                catch (IOException var1_8) {
                                    // empty catch block
                                }
                                throw var1_9;
                            }
                            var10_20 = false;
                        }
                        this.keys = new IgnoresCaseHashtable();
                        this.localIds = new Hashtable<K, V>();
                        var15_25 = var6_16.getContentType().equals(PKCS12KeyStoreSpi.data);
                        var11_21 = "unmarked";
                        var1_1 = "attempt to add existing attribute with different value";
                        if (var15_25) break block74;
                        var5_15 = "attempt to add existing attribute with different value";
                        var1_1 = "unmarked";
                        var9_19 = var8_18;
                        break block68;
                    }
                    var3_13 = new ASN1InputStream(((ASN1OctetString)var6_16.getContent()).getOctets());
                    var6_16 = AuthenticatedSafe.getInstance(var3_13.readObject());
                    var12_22 = var6_16.getContentInfo();
                    var8_18 = 0;
                    block13 : do {
                        block80 : {
                            block79 : {
                                block77 : {
                                    block75 : {
                                        block78 : {
                                            block76 : {
                                                if (var8_18 == ((ContentInfo[])var12_22).length) break block75;
                                                if (!var12_22[var8_18].getContentType().equals(PKCS12KeyStoreSpi.data)) break block76;
                                                var16_26 = new ASN1InputStream(((ASN1OctetString)var12_22[var8_18].getContent()).getOctets());
                                                var17_27 /* !! */  = (ASN1Sequence)var16_26.readObject();
                                                break block77;
                                            }
                                            var13_23 = var6_16;
                                            var22_32 = var3_13;
                                            var17_27 /* !! */  = var5_15;
                                            if (!var12_22[var8_18].getContentType().equals(PKCS12KeyStoreSpi.encryptedData)) break block78;
                                            var6_16 = EncryptedData.getInstance(var12_22[var8_18].getContent());
                                            var5_15 = var6_16.getEncryptionAlgorithm();
                                            var3_13 = var6_16.getContent().getOctets();
                                            var3_13 = this.cryptData(false, (AlgorithmIdentifier)var5_15, (char[])var2_12, var10_20, (byte[])var3_13);
                                            var5_15 = (ASN1Sequence)ASN1Primitive.fromByteArray((byte[])var3_13);
                                            var14_24 = 0;
                                            break block79;
                                        }
                                        var14_24 = var8_18;
                                        var5_15 = var12_22;
                                        var3_13 = System.out;
                                        var6_16 = new StringBuilder();
                                        var6_16.append("extra ");
                                        var6_16.append(var5_15[var14_24].getContentType().getId());
                                        var3_13.println(var6_16.toString());
                                        var6_16 = System.out;
                                        var3_13 = new StringBuilder();
                                        var3_13.append("extra ");
                                        var3_13.append(ASN1Dump.dumpAsString(var5_15[var14_24].getContent()));
                                        var6_16.println(var3_13.toString());
                                        var5_15 = var17_27 /* !! */ ;
                                        var3_13 = var22_32;
                                        var6_16 = var13_23;
                                        break block80;
                                    }
                                    var5_15 = var1_1;
                                    var1_1 = var11_21;
                                    break block68;
                                }
                                for (var14_24 = 0; var14_24 != var17_27 /* !! */ .size(); ++var14_24) {
                                    block84 : {
                                        block83 : {
                                            block81 : {
                                                block82 : {
                                                    var18_28 = SafeBag.getInstance(var17_27 /* !! */ .getObjectAt(var14_24));
                                                    if (!var18_28.getBagId().equals(PKCS12KeyStoreSpi.pkcs8ShroudedKeyBag)) break block81;
                                                    var19_29 = EncryptedPrivateKeyInfo.getInstance(var18_28.getBagValue());
                                                    var20_30 = this.unwrapKey(var19_29.getEncryptionAlgorithm(), var19_29.getEncryptedData(), (char[])var2_12, var10_20);
                                                    var21_31 = null;
                                                    var22_32 = null;
                                                    var13_23 = null;
                                                    if (var18_28.getBagAttributes() == null) break block82;
                                                    var18_28 = var18_28.getBagAttributes().getObjects();
                                                    var22_32 = var13_23;
                                                    var13_23 = var5_15;
                                                    var5_15 = var21_31;
                                                    break block83;
                                                }
                                                var19_29 = null;
                                                break block84;
                                            }
                                            if (var18_28.getBagId().equals(PKCS12KeyStoreSpi.certBag)) {
                                                var7_17.addElement(var18_28);
                                                continue;
                                            }
                                            var22_32 = System.out;
                                            var13_23 = new StringBuilder();
                                            var13_23.append("extra in data ");
                                            var13_23.append(var18_28.getBagId());
                                            var22_32.println(var13_23.toString());
                                            System.out.println(ASN1Dump.dumpAsString(var18_28));
                                            continue;
                                        }
                                        while (var18_28.hasMoreElements()) {
                                            var21_31 = (ASN1Sequence)var18_28.nextElement();
                                            var23_33 = (ASN1ObjectIdentifier)var21_31.getObjectAt(0);
                                            if ((var21_31 = (ASN1Set)var21_31.getObjectAt(1)).size() > 0) {
                                                var21_31 = (ASN1Primitive)var21_31.getObjectAt(0);
                                                if (var20_30 instanceof PKCS12BagAttributeCarrier) {
                                                    var24_34 = (PKCS12BagAttributeCarrier)var20_30;
                                                    var25_35 = var24_34.getBagAttribute((ASN1ObjectIdentifier)var23_33);
                                                    if (var25_35 != null) {
                                                        if (var25_35.toASN1Primitive().equals(var21_31) == false) throw new IOException((String)var1_1);
                                                    } else {
                                                        var24_34.setBagAttribute((ASN1ObjectIdentifier)var23_33, (ASN1Encodable)var21_31);
                                                    }
                                                }
                                            } else {
                                                var21_31 = null;
                                            }
                                            if (var23_33.equals(PKCS12KeyStoreSpi.pkcs_9_at_friendlyName)) {
                                                var5_15 = ((DERBMPString)var21_31).getString();
                                                this.keys.put((String)var5_15, var20_30);
                                                continue;
                                            }
                                            if (!var23_33.equals(PKCS12KeyStoreSpi.pkcs_9_at_localKeyId)) continue;
                                            var22_32 = (ASN1OctetString)var21_31;
                                        }
                                        var19_29 = var5_15;
                                        var5_15 = var13_23;
                                    }
                                    if (var22_32 != null) {
                                        var13_23 = new String(Hex.encode(var22_32.getOctets()));
                                        if (var19_29 == null) {
                                            this.keys.put((String)var13_23, var20_30);
                                            continue;
                                        }
                                        this.localIds.put(var19_29, var13_23);
                                        continue;
                                    }
                                    var9_19 = 1;
                                    this.keys.put((String)var11_21, var20_30);
                                }
                                break block80;
                            }
lbl199: // 2 sources:
                            do {
                                if (var14_24 != var5_15.size()) {
                                    var20_30 = SafeBag.getInstance(var5_15.getObjectAt(var14_24));
                                    if (var20_30.getBagId().equals(PKCS12KeyStoreSpi.certBag)) {
                                        var7_17.addElement(var20_30);
                                        var16_26 = var5_15;
                                        var5_15 = var3_13;
                                        var3_13 = var6_16;
                                        var6_16 = var16_26;
                                        break block69;
                                    }
                                    if (var20_30.getBagId().equals(PKCS12KeyStoreSpi.pkcs8ShroudedKeyBag)) {
                                        var21_31 = EncryptedPrivateKeyInfo.getInstance(var20_30.getBagValue());
                                        var23_33 = this.unwrapKey(var21_31.getEncryptionAlgorithm(), var21_31.getEncryptedData(), (char[])var2_12, var10_20);
                                        var24_34 = (PKCS12BagAttributeCarrier)var23_33;
                                        var19_29 = null;
                                        var25_35 = var20_30.getBagAttributes().getObjects();
                                        var16_26 = null;
                                        break block13;
                                    }
                                    var16_26 = var6_16;
                                    var19_29 = var3_13;
                                    var6_16 = var5_15;
                                    if (var20_30.getBagId().equals(PKCS12KeyStoreSpi.keyBag)) {
                                        var21_31 = PrivateKeyInfo.getInstance(var20_30.getBagValue());
                                        var18_28 = BouncyCastleProvider.getPrivateKey((PrivateKeyInfo)var21_31);
                                        var23_33 = (PKCS12BagAttributeCarrier)var18_28;
                                        var5_15 = null;
                                        var3_13 = null;
                                        var24_34 = var20_30.getBagAttributes().getObjects();
                                        break block70;
                                    }
                                    var5_15 = System.out;
                                    var3_13 = new StringBuilder();
                                    var3_13.append("extra in encryptedData ");
                                    var3_13.append(var20_30.getBagId());
                                    var5_15.println(var3_13.toString());
                                    System.out.println(ASN1Dump.dumpAsString(var20_30));
                                    var3_13 = var16_26;
                                    var5_15 = var19_29;
                                    break block69;
                                }
                                var6_16 = var13_23;
                                var3_13 = var22_32;
                                var5_15 = var17_27 /* !! */ ;
                                break;
                            } while (true);
                        }
                        ++var8_18;
                    } while (true);
                    while (var25_35.hasMoreElements()) {
                        var20_30 = (ASN1Sequence)var25_35.nextElement();
                        var26_37 = (ASN1ObjectIdentifier)var20_30.getObjectAt(0);
                        if ((var20_30 = (ASN1Set)var20_30.getObjectAt(1)).size() > 0) {
                            var18_28 = (ASN1Primitive)var20_30.getObjectAt(0);
                            var20_30 = var24_34.getBagAttribute(var26_37);
                            if (var20_30 != null) {
                                if (var20_30.toASN1Primitive().equals(var18_28) == false) throw new IOException((String)var1_1);
                            } else {
                                var24_34.setBagAttribute(var26_37, (ASN1Encodable)var18_28);
                            }
                        } else {
                            var18_28 = null;
                        }
                        if (var26_37.equals(PKCS12KeyStoreSpi.pkcs_9_at_friendlyName)) {
                            var20_30 = ((DERBMPString)var18_28).getString();
                            this.keys.put((String)var20_30, var23_33);
                        } else {
                            var20_30 = var16_26;
                            if (var26_37.equals(PKCS12KeyStoreSpi.pkcs_9_at_localKeyId)) {
                                var19_29 = (ASN1OctetString)var18_28;
                                var20_30 = var16_26;
                            }
                        }
                        var16_26 = var20_30;
                    }
                    var21_31 = var5_15;
                    var5_15 = new String(Hex.encode(var19_29.getOctets()));
                    if (var16_26 == null) {
                        this.keys.put((String)var5_15, var23_33);
                    } else {
                        this.localIds.put(var16_26, var5_15);
                    }
                    var5_15 = var3_13;
                    var3_13 = var6_16;
                    var6_16 = var21_31;
                    break block69;
                }
                while (var24_34.hasMoreElements()) {
                    var20_30 = ASN1Sequence.getInstance(var24_34.nextElement());
                    var25_35 = ASN1ObjectIdentifier.getInstance(var20_30.getObjectAt(0));
                    if ((var20_30 = ASN1Set.getInstance(var20_30.getObjectAt(1))).size() > 0) {
                        var26_38 = (ASN1Primitive)var20_30.getObjectAt(0);
                        var20_30 = var23_33.getBagAttribute((ASN1ObjectIdentifier)var25_35);
                        if (var20_30 != null) {
                            if (var20_30.toASN1Primitive().equals(var26_38) == false) throw new IOException((String)var1_1);
                        } else {
                            var23_33.setBagAttribute((ASN1ObjectIdentifier)var25_35, var26_38);
                        }
                        if (var25_35.equals(PKCS12KeyStoreSpi.pkcs_9_at_friendlyName)) {
                            var20_30 = ((DERBMPString)var26_38).getString();
                            this.keys.put((String)var20_30, var18_28);
                        } else {
                            var20_30 = var5_15;
                            if (var25_35.equals(PKCS12KeyStoreSpi.pkcs_9_at_localKeyId)) {
                                var3_13 = (ASN1OctetString)var26_38;
                                var20_30 = var5_15;
                            }
                        }
                    } else {
                        var20_30 = var5_15;
                    }
                    var5_15 = var20_30;
                }
                var3_13 = new String(Hex.encode(var3_13.getOctets()));
                if (var5_15 == null) {
                    this.keys.put((String)var3_13, var18_28);
                } else {
                    this.localIds.put(var5_15, var3_13);
                }
                var5_15 = var19_29;
                var3_13 = var16_26;
            }
            ++var14_24;
            var16_26 = var6_16;
            var6_16 = var3_13;
            var3_13 = var5_15;
            var5_15 = var16_26;
            ** while (true)
        }
        this.certs = new IgnoresCaseHashtable();
        this.chainCerts = new Hashtable<K, V>();
        this.keyCerts = new Hashtable<K, V>();
        var4_14 = 0;
        var3_13 = var1_1;
        while (var4_14 != var7_17.size()) {
            block72 : {
                block71 : {
                    var6_16 = (SafeBag)var7_17.elementAt(var4_14);
                    var1_1 = CertBag.getInstance(var6_16.getBagValue());
                    if (!var1_1.getCertId().equals(PKCS12KeyStoreSpi.x509Certificate)) {
                        var2_12 = new StringBuilder();
                        var2_12.append("Unsupported certificate type: ");
                        var2_12.append(var1_1.getCertId());
                        throw new RuntimeException(var2_12.toString());
                    }
                    try {
                        var2_12 = new ByteArrayInputStream(((ASN1OctetString)var1_1.getCertValue()).getOctets());
                        var13_23 = this.certFact.generateCertificate((InputStream)var2_12);
                        var11_21 = null;
                        var1_1 = null;
                        var12_22 = null;
                        var2_12 = null;
                        if (var6_16.getBagAttributes() != null) {
                            var11_21 = var6_16.getBagAttributes().getObjects();
                            break block71;
                        }
                        var2_12 = var12_22;
                        var1_1 = var11_21;
                        break block72;
                    }
                    catch (Exception var1_10) {
                        throw new RuntimeException(var1_10.toString());
                    }
                }
                while (var11_21.hasMoreElements()) {
                    var22_32 = ASN1Sequence.getInstance(var11_21.nextElement());
                    var12_22 = ASN1ObjectIdentifier.getInstance(var22_32.getObjectAt(0));
                    if ((var22_32 = ASN1Set.getInstance(var22_32.getObjectAt(1))).size() <= 0) continue;
                    var17_27 /* !! */  = (ASN1Primitive)var22_32.getObjectAt(0);
                    if (var13_23 instanceof PKCS12BagAttributeCarrier) {
                        var16_26 = (PKCS12BagAttributeCarrier)var13_23;
                        var22_32 = var16_26.getBagAttribute((ASN1ObjectIdentifier)var12_22);
                        if (var22_32 != null) {
                            if (var22_32.toASN1Primitive().equals(var17_27 /* !! */ ) == false) throw new IOException((String)var5_15);
                        } else {
                            var16_26.setBagAttribute((ASN1ObjectIdentifier)var12_22, (ASN1Encodable)var17_27 /* !! */ );
                        }
                    }
                    if (var12_22.equals(PKCS12KeyStoreSpi.pkcs_9_at_friendlyName)) {
                        var2_12 = ((DERBMPString)var17_27 /* !! */ ).getString();
                        continue;
                    }
                    if (!var12_22.equals(PKCS12KeyStoreSpi.pkcs_9_at_localKeyId)) continue;
                    var1_1 = (ASN1OctetString)var17_27 /* !! */ ;
                }
            }
            this.chainCerts.put(new CertId(var13_23.getPublicKey()), var13_23);
            if (var9_19 != 0) {
                if (this.keyCerts.isEmpty()) {
                    var1_1 = new String(Hex.encode(this.createSubjectKeyId(var13_23.getPublicKey()).getKeyIdentifier()));
                    this.keyCerts.put(var1_1, var13_23);
                    var2_12 = this.keys;
                    var2_12.put((String)var1_1, var2_12.remove((String)var3_13));
                }
            } else {
                if (var1_1 != null) {
                    var1_1 = new String(Hex.encode(var1_1.getOctets()));
                    this.keyCerts.put(var1_1, var13_23);
                }
                if (var2_12 != null) {
                    this.certs.put((String)var2_12, var13_23);
                }
            }
            ++var4_14;
        }
    }

    @Override
    public void engineSetCertificateEntry(String string, Certificate serializable) throws KeyStoreException {
        if (this.keys.get(string) == null) {
            this.certs.put(string, serializable);
            this.chainCerts.put(new CertId(((Certificate)serializable).getPublicKey()), serializable);
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("There is a key entry with the name ");
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append(".");
        throw new KeyStoreException(((StringBuilder)serializable).toString());
    }

    @Override
    public void engineSetKeyEntry(String string, Key key, char[] arrc, Certificate[] arrcertificate) throws KeyStoreException {
        if (key instanceof PrivateKey) {
            if (key instanceof PrivateKey && arrcertificate == null) {
                throw new KeyStoreException("no certificate chain for private key");
            }
            if (this.keys.get(string) != null) {
                this.engineDeleteEntry(string);
            }
            this.keys.put(string, key);
            if (arrcertificate != null) {
                this.certs.put(string, arrcertificate[0]);
                for (int i = 0; i != arrcertificate.length; ++i) {
                    this.chainCerts.put(new CertId(arrcertificate[i].getPublicKey()), arrcertificate[i]);
                }
            }
            return;
        }
        throw new KeyStoreException("PKCS12 does not support non-PrivateKeys");
    }

    @Override
    public void engineSetKeyEntry(String string, byte[] arrby, Certificate[] arrcertificate) throws KeyStoreException {
        throw new RuntimeException("operation not supported");
    }

    @Override
    public int engineSize() {
        Hashtable<Object, String> hashtable = new Hashtable<Object, String>();
        Object object = this.certs.keys();
        while (object.hasMoreElements()) {
            hashtable.put(object.nextElement(), "cert");
        }
        Enumeration enumeration = this.keys.keys();
        while (enumeration.hasMoreElements()) {
            object = (String)enumeration.nextElement();
            if (hashtable.get(object) != null) continue;
            hashtable.put(object, "key");
        }
        return hashtable.size();
    }

    @Override
    public void engineStore(OutputStream outputStream, char[] arrc) throws IOException {
        this.doStore(outputStream, arrc, false);
    }

    @Override
    public void engineStore(KeyStore.LoadStoreParameter arrc) throws IOException, NoSuchAlgorithmException, CertificateException {
        block4 : {
            Object object;
            block7 : {
                block6 : {
                    block5 : {
                        if (arrc == null) break block4;
                        if (!(arrc instanceof PKCS12StoreParameter) && !(arrc instanceof JDKPKCS12StoreParameter)) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("No support for 'param' of type ");
                            stringBuilder.append(arrc.getClass().getName());
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                        object = arrc instanceof PKCS12StoreParameter ? (PKCS12StoreParameter)arrc : new PKCS12StoreParameter(((JDKPKCS12StoreParameter)arrc).getOutputStream(), arrc.getProtectionParameter(), ((JDKPKCS12StoreParameter)arrc).isUseDEREncoding());
                        arrc = arrc.getProtectionParameter();
                        if (arrc != null) break block5;
                        arrc = null;
                        break block6;
                    }
                    if (!(arrc instanceof KeyStore.PasswordProtection)) break block7;
                    arrc = ((KeyStore.PasswordProtection)arrc).getPassword();
                }
                this.doStore(((PKCS12StoreParameter)object).getOutputStream(), arrc, ((PKCS12StoreParameter)object).isForDEREncoding());
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("No support for protection parameter of type ");
            ((StringBuilder)object).append(arrc.getClass().getName());
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("'param' arg cannot be null");
    }

    @Override
    public void setRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected PrivateKey unwrapKey(AlgorithmIdentifier object, byte[] object2, char[] arrc, boolean bl) throws IOException {
        Object object3 = ((AlgorithmIdentifier)object).getAlgorithm();
        try {
            boolean bl2 = ((ASN1ObjectIdentifier)object3).on(PKCSObjectIdentifiers.pkcs_12PbeIds);
            if (bl2) {
                Object object4 = PKCS12PBEParams.getInstance(((AlgorithmIdentifier)object).getParameters());
                object = new PBEParameterSpec(((PKCS12PBEParams)object4).getIV(), this.validateIterationCount(((PKCS12PBEParams)object4).getIterations()));
                object4 = this.helper.createCipher(((ASN1ObjectIdentifier)object3).getId());
                object3 = new PKCS12Key(arrc, bl);
                ((Cipher)object4).init(4, (Key)object3, (AlgorithmParameterSpec)object);
                return (PrivateKey)((Cipher)object4).unwrap((byte[])object2, "", 2);
            }
            if (((ASN1Primitive)object3).equals(PKCSObjectIdentifiers.id_PBES2)) {
                return (PrivateKey)this.createCipher(4, arrc, (AlgorithmIdentifier)object).unwrap((byte[])object2, "", 2);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("exception unwrapping private key - cannot recognise: ");
        }
        catch (Exception exception) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("exception unwrapping private key - ");
            ((StringBuilder)object2).append(exception.toString());
            throw new IOException(((StringBuilder)object2).toString());
        }
        ((StringBuilder)object).append(object3);
        throw new IOException(((StringBuilder)object).toString());
    }

    protected byte[] wrapKey(String arrby, Key key, PKCS12PBEParams pKCS12PBEParams, char[] object) throws IOException {
        PBEKeySpec pBEKeySpec = new PBEKeySpec((char[])object);
        try {
            object = this.helper.createSecretKeyFactory((String)arrby);
            PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(pKCS12PBEParams.getIV(), pKCS12PBEParams.getIterations().intValue());
            arrby = this.helper.createCipher((String)arrby);
            arrby.init(3, (Key)((SecretKeyFactory)object).generateSecret(pBEKeySpec), pBEParameterSpec);
            arrby = arrby.wrap(key);
            return arrby;
        }
        catch (Exception exception) {
            arrby = new StringBuilder();
            arrby.append("exception encrypting data - ");
            arrby.append(exception.toString());
            throw new IOException(arrby.toString());
        }
    }

    public static class BCPKCS12KeyStore
    extends PKCS12KeyStoreSpi {
        public BCPKCS12KeyStore() {
            super(new DefaultJcaJceHelper(), pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd40BitRC2_CBC);
        }
    }

    private class CertId {
        byte[] id;

        CertId(PublicKey publicKey) {
            this.id = PKCS12KeyStoreSpi.this.createSubjectKeyId(publicKey).getKeyIdentifier();
        }

        CertId(byte[] arrby) {
            this.id = arrby;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof CertId)) {
                return false;
            }
            object = (CertId)object;
            return Arrays.areEqual(this.id, ((CertId)object).id);
        }

        public int hashCode() {
            return Arrays.hashCode(this.id);
        }
    }

    private static class DefaultSecretKeyProvider {
        private final Map KEY_SIZES;

        DefaultSecretKeyProvider() {
            HashMap<ASN1ObjectIdentifier, Integer> hashMap = new HashMap<ASN1ObjectIdentifier, Integer>();
            hashMap.put(new ASN1ObjectIdentifier("1.2.840.113533.7.66.10"), Integers.valueOf(128));
            hashMap.put(PKCSObjectIdentifiers.des_EDE3_CBC, Integers.valueOf(192));
            hashMap.put(NISTObjectIdentifiers.id_aes128_CBC, Integers.valueOf(128));
            hashMap.put(NISTObjectIdentifiers.id_aes192_CBC, Integers.valueOf(192));
            hashMap.put(NISTObjectIdentifiers.id_aes256_CBC, Integers.valueOf(256));
            this.KEY_SIZES = Collections.unmodifiableMap(hashMap);
        }

        public int getKeySize(AlgorithmIdentifier object) {
            if ((object = (Integer)this.KEY_SIZES.get(((AlgorithmIdentifier)object).getAlgorithm())) != null) {
                return (Integer)object;
            }
            return -1;
        }
    }

    private static class IgnoresCaseHashtable {
        private Hashtable keys = new Hashtable();
        private Hashtable orig = new Hashtable();

        private IgnoresCaseHashtable() {
        }

        public Enumeration elements() {
            return this.orig.elements();
        }

        public Object get(String string) {
            Hashtable hashtable = this.keys;
            string = string == null ? null : Strings.toLowerCase(string);
            if ((string = (String)hashtable.get(string)) == null) {
                return null;
            }
            return this.orig.get(string);
        }

        public Enumeration keys() {
            return this.orig.keys();
        }

        public void put(String string, Object object) {
            String string2 = string == null ? null : Strings.toLowerCase(string);
            String string3 = (String)this.keys.get(string2);
            if (string3 != null) {
                this.orig.remove(string3);
            }
            this.keys.put(string2, string);
            this.orig.put(string, object);
        }

        public Object remove(String string) {
            Hashtable hashtable = this.keys;
            string = string == null ? null : Strings.toLowerCase(string);
            if ((string = (String)hashtable.remove(string)) == null) {
                return null;
            }
            return this.orig.remove(string);
        }
    }

}

