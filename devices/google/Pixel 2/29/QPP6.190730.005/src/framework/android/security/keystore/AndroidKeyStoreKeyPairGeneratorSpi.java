/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.org.bouncycastle.asn1.ASN1Encodable
 *  com.android.org.bouncycastle.asn1.ASN1EncodableVector
 *  com.android.org.bouncycastle.asn1.ASN1InputStream
 *  com.android.org.bouncycastle.asn1.ASN1Integer
 *  com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  com.android.org.bouncycastle.asn1.ASN1Primitive
 *  com.android.org.bouncycastle.asn1.DERBitString
 *  com.android.org.bouncycastle.asn1.DERInteger
 *  com.android.org.bouncycastle.asn1.DERNull
 *  com.android.org.bouncycastle.asn1.DERSequence
 *  com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers
 *  com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier
 *  com.android.org.bouncycastle.asn1.x509.Certificate
 *  com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
 *  com.android.org.bouncycastle.asn1.x509.TBSCertificate
 *  com.android.org.bouncycastle.asn1.x509.Time
 *  com.android.org.bouncycastle.asn1.x509.V3TBSCertificateGenerator
 *  com.android.org.bouncycastle.asn1.x509.X509Name
 *  com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers
 *  com.android.org.bouncycastle.jce.X509Principal
 *  com.android.org.bouncycastle.jce.provider.X509CertificateObject
 *  com.android.org.bouncycastle.x509.X509V3CertificateGenerator
 */
package android.security.keystore;

import android.security.Credentials;
import android.security.KeyPairGeneratorSpec;
import android.security.KeyStore;
import android.security.keymaster.KeyCharacteristics;
import android.security.keymaster.KeymasterArguments;
import android.security.keymaster.KeymasterCertificateChain;
import android.security.keystore.AndroidKeyStoreBCWorkaroundProvider;
import android.security.keystore.AndroidKeyStoreProvider;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.security.keystore.KeyStoreCryptoOperationUtils;
import android.security.keystore.KeymasterUtils;
import android.security.keystore.SecureKeyImportUnavailableException;
import android.security.keystore.StrongBoxUnavailableException;
import com.android.internal.util.ArrayUtils;
import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DERInteger;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.Certificate;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x509.TBSCertificate;
import com.android.org.bouncycastle.asn1.x509.Time;
import com.android.org.bouncycastle.asn1.x509.V3TBSCertificateGenerator;
import com.android.org.bouncycastle.asn1.x509.X509Name;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.jce.X509Principal;
import com.android.org.bouncycastle.jce.provider.X509CertificateObject;
import com.android.org.bouncycastle.x509.X509V3CertificateGenerator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGeneratorSpi;
import java.security.PrivateKey;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;

public abstract class AndroidKeyStoreKeyPairGeneratorSpi
extends KeyPairGeneratorSpi {
    private static final int EC_DEFAULT_KEY_SIZE = 256;
    private static final int RSA_DEFAULT_KEY_SIZE = 2048;
    private static final int RSA_MAX_KEY_SIZE = 8192;
    private static final int RSA_MIN_KEY_SIZE = 512;
    private static final List<String> SUPPORTED_EC_NIST_CURVE_NAMES;
    private static final Map<String, Integer> SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE;
    private static final List<Integer> SUPPORTED_EC_NIST_CURVE_SIZES;
    private boolean mEncryptionAtRestRequired;
    private String mEntryAlias;
    private int mEntryUid;
    private String mJcaKeyAlgorithm;
    private int mKeySizeBits;
    private KeyStore mKeyStore;
    private int mKeymasterAlgorithm = -1;
    private int[] mKeymasterBlockModes;
    private int[] mKeymasterDigests;
    private int[] mKeymasterEncryptionPaddings;
    private int[] mKeymasterPurposes;
    private int[] mKeymasterSignaturePaddings;
    private final int mOriginalKeymasterAlgorithm;
    private BigInteger mRSAPublicExponent;
    private SecureRandom mRng;
    private KeyGenParameterSpec mSpec;

    static {
        SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE = new HashMap<String, Integer>();
        SUPPORTED_EC_NIST_CURVE_NAMES = new ArrayList<String>();
        SUPPORTED_EC_NIST_CURVE_SIZES = new ArrayList<Integer>();
        Map<String, Integer> map = SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE;
        Integer n = 224;
        map.put("p-224", n);
        SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE.put("secp224r1", n);
        map = SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE;
        n = 256;
        map.put("p-256", n);
        SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE.put("secp256r1", n);
        SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE.put("prime256v1", n);
        map = SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE;
        n = 384;
        map.put("p-384", n);
        SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE.put("secp384r1", n);
        map = SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE;
        n = 521;
        map.put("p-521", n);
        SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE.put("secp521r1", n);
        SUPPORTED_EC_NIST_CURVE_NAMES.addAll(SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE.keySet());
        Collections.sort(SUPPORTED_EC_NIST_CURVE_NAMES);
        SUPPORTED_EC_NIST_CURVE_SIZES.addAll(new HashSet<Integer>(SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE.values()));
        Collections.sort(SUPPORTED_EC_NIST_CURVE_SIZES);
    }

    protected AndroidKeyStoreKeyPairGeneratorSpi(int n) {
        this.mOriginalKeymasterAlgorithm = n;
    }

    private void addAlgorithmSpecificParameters(KeymasterArguments object) {
        int n = this.mKeymasterAlgorithm;
        if (n != 1) {
            if (n != 3) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unsupported algorithm: ");
                ((StringBuilder)object).append(this.mKeymasterAlgorithm);
                throw new ProviderException(((StringBuilder)object).toString());
            }
        } else {
            ((KeymasterArguments)object).addUnsignedLong(1342177480, this.mRSAPublicExponent);
        }
    }

    private static void checkValidKeySize(int n, int n2, boolean bl) throws InvalidAlgorithmParameterException {
        block9 : {
            block8 : {
                block6 : {
                    block7 : {
                        if (n == 1) break block6;
                        if (n != 3) break block7;
                        if (bl && n2 != 256) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unsupported StrongBox EC key size: ");
                            stringBuilder.append(n2);
                            stringBuilder.append(" bits. Supported: 256");
                            throw new InvalidAlgorithmParameterException(stringBuilder.toString());
                        }
                        if (!SUPPORTED_EC_NIST_CURVE_SIZES.contains(n2)) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unsupported EC key size: ");
                            stringBuilder.append(n2);
                            stringBuilder.append(" bits. Supported: ");
                            stringBuilder.append(SUPPORTED_EC_NIST_CURVE_SIZES);
                            throw new InvalidAlgorithmParameterException(stringBuilder.toString());
                        }
                        break block8;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported algorithm: ");
                    stringBuilder.append(n);
                    throw new ProviderException(stringBuilder.toString());
                }
                if (n2 < 512 || n2 > 8192) break block9;
            }
            return;
        }
        throw new InvalidAlgorithmParameterException("RSA key size must be >= 512 and <= 8192");
    }

    private KeymasterArguments constructKeyGenerationArguments() {
        KeymasterArguments keymasterArguments = new KeymasterArguments();
        keymasterArguments.addUnsignedInt(805306371, this.mKeySizeBits);
        keymasterArguments.addEnum(268435458, this.mKeymasterAlgorithm);
        keymasterArguments.addEnums(536870913, this.mKeymasterPurposes);
        keymasterArguments.addEnums(536870916, this.mKeymasterBlockModes);
        keymasterArguments.addEnums(536870918, this.mKeymasterEncryptionPaddings);
        keymasterArguments.addEnums(536870918, this.mKeymasterSignaturePaddings);
        keymasterArguments.addEnums(536870917, this.mKeymasterDigests);
        KeymasterUtils.addUserAuthArgs(keymasterArguments, this.mSpec);
        keymasterArguments.addDateIfNotNull(1610613136, this.mSpec.getKeyValidityStart());
        keymasterArguments.addDateIfNotNull(1610613137, this.mSpec.getKeyValidityForOriginationEnd());
        keymasterArguments.addDateIfNotNull(1610613138, this.mSpec.getKeyValidityForConsumptionEnd());
        this.addAlgorithmSpecificParameters(keymasterArguments);
        if (this.mSpec.isUniqueIdIncluded()) {
            keymasterArguments.addBoolean(1879048394);
        }
        return keymasterArguments;
    }

    private Iterable<byte[]> createCertificateChain(String string2, KeyPair keyPair) throws ProviderException {
        byte[] arrby = this.mSpec.getAttestationChallenge();
        if (arrby != null) {
            KeymasterArguments keymasterArguments = new KeymasterArguments();
            keymasterArguments.addBytes(-1879047484, arrby);
            return this.getAttestationChain(string2, keyPair, keymasterArguments);
        }
        return Collections.singleton(this.generateSelfSignedCertificateBytes(keyPair));
    }

    private void generateKeystoreKeyPair(String string2, KeymasterArguments keymasterArguments, byte[] arrby, int n) throws ProviderException {
        KeyCharacteristics keyCharacteristics = new KeyCharacteristics();
        if ((n = this.mKeyStore.generateKey(string2, keymasterArguments, arrby, this.mEntryUid, n, keyCharacteristics)) != 1) {
            if (n == -68) {
                throw new StrongBoxUnavailableException("Failed to generate key pair");
            }
            throw new ProviderException("Failed to generate key pair", KeyStore.getKeyStoreException(n));
        }
    }

    private X509Certificate generateSelfSignedCertificate(PrivateKey serializable, PublicKey publicKey) throws CertificateParsingException, IOException {
        String string2 = AndroidKeyStoreKeyPairGeneratorSpi.getCertificateSignatureAlgorithm(this.mKeymasterAlgorithm, this.mKeySizeBits, this.mSpec);
        if (string2 == null) {
            return this.generateSelfSignedCertificateWithFakeSignature(publicKey);
        }
        try {
            serializable = this.generateSelfSignedCertificateWithValidSignature((PrivateKey)serializable, publicKey, string2);
            return serializable;
        }
        catch (Exception exception) {
            return this.generateSelfSignedCertificateWithFakeSignature(publicKey);
        }
    }

    private byte[] generateSelfSignedCertificateBytes(KeyPair arrby) throws ProviderException {
        try {
            arrby = this.generateSelfSignedCertificate(arrby.getPrivate(), arrby.getPublic()).getEncoded();
            return arrby;
        }
        catch (CertificateEncodingException certificateEncodingException) {
            throw new ProviderException("Failed to obtain encoded form of self-signed certificate", certificateEncodingException);
        }
        catch (IOException | CertificateParsingException exception) {
            throw new ProviderException("Failed to generate self-signed certificate", exception);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private X509Certificate generateSelfSignedCertificateWithFakeSignature(PublicKey serializable) throws IOException, CertificateParsingException {
        byte[] arrby;
        AlgorithmIdentifier algorithmIdentifier;
        V3TBSCertificateGenerator v3TBSCertificateGenerator = new V3TBSCertificateGenerator();
        int n = this.mKeymasterAlgorithm;
        if (n != 1) {
            if (n != 3) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Unsupported key algorithm: ");
                ((StringBuilder)serializable).append(this.mKeymasterAlgorithm);
                throw new ProviderException(((StringBuilder)serializable).toString());
            }
            algorithmIdentifier = new AlgorithmIdentifier(X9ObjectIdentifiers.ecdsa_with_SHA256);
            arrby = new ASN1EncodableVector();
            arrby.add((ASN1Encodable)new DERInteger(0L));
            arrby.add((ASN1Encodable)new DERInteger(0L));
            arrby = new DERSequence().getEncoded();
        } else {
            algorithmIdentifier = new AlgorithmIdentifier(PKCSObjectIdentifiers.sha256WithRSAEncryption, (ASN1Encodable)DERNull.INSTANCE);
            arrby = new byte[1];
        }
        serializable = new ASN1InputStream(serializable.getEncoded());
        try {
            v3TBSCertificateGenerator.setSubjectPublicKeyInfo(SubjectPublicKeyInfo.getInstance((Object)serializable.readObject()));
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                try {
                    serializable.close();
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    throwable.addSuppressed(throwable3);
                }
                throw throwable2;
            }
        }
        serializable.close();
        v3TBSCertificateGenerator.setSerialNumber(new ASN1Integer(this.mSpec.getCertificateSerialNumber()));
        serializable = new X509Principal(this.mSpec.getCertificateSubject().getEncoded());
        v3TBSCertificateGenerator.setSubject((X509Name)serializable);
        v3TBSCertificateGenerator.setIssuer((X509Name)serializable);
        v3TBSCertificateGenerator.setStartDate(new Time(this.mSpec.getCertificateNotBefore()));
        v3TBSCertificateGenerator.setEndDate(new Time(this.mSpec.getCertificateNotAfter()));
        v3TBSCertificateGenerator.setSignature(algorithmIdentifier);
        serializable = v3TBSCertificateGenerator.generateTBSCertificate();
        v3TBSCertificateGenerator = new ASN1EncodableVector();
        v3TBSCertificateGenerator.add((ASN1Encodable)serializable);
        v3TBSCertificateGenerator.add((ASN1Encodable)algorithmIdentifier);
        v3TBSCertificateGenerator.add((ASN1Encodable)new DERBitString(arrby));
        return new X509CertificateObject(Certificate.getInstance((Object)new DERSequence((ASN1EncodableVector)v3TBSCertificateGenerator)));
    }

    private X509Certificate generateSelfSignedCertificateWithValidSignature(PrivateKey privateKey, PublicKey publicKey, String string2) throws Exception {
        X509V3CertificateGenerator x509V3CertificateGenerator = new X509V3CertificateGenerator();
        x509V3CertificateGenerator.setPublicKey(publicKey);
        x509V3CertificateGenerator.setSerialNumber(this.mSpec.getCertificateSerialNumber());
        x509V3CertificateGenerator.setSubjectDN(this.mSpec.getCertificateSubject());
        x509V3CertificateGenerator.setIssuerDN(this.mSpec.getCertificateSubject());
        x509V3CertificateGenerator.setNotBefore(this.mSpec.getCertificateNotBefore());
        x509V3CertificateGenerator.setNotAfter(this.mSpec.getCertificateNotAfter());
        x509V3CertificateGenerator.setSignatureAlgorithm(string2);
        return x509V3CertificateGenerator.generate(privateKey);
    }

    private Iterable<byte[]> getAttestationChain(String list, KeyPair object, KeymasterArguments keymasterArguments) throws ProviderException {
        object = new KeymasterCertificateChain();
        int n = this.mKeyStore.attestKey((String)((Object)list), keymasterArguments, (KeymasterCertificateChain)object);
        if (n == 1) {
            list = ((KeymasterCertificateChain)object).getCertificates();
            if (list.size() >= 2) {
                return list;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Attestation certificate chain contained ");
            ((StringBuilder)object).append(list.size());
            ((StringBuilder)object).append(" entries. At least two are required.");
            throw new ProviderException(((StringBuilder)object).toString());
        }
        throw new ProviderException("Failed to generate attestation certificate chain", KeyStore.getKeyStoreException(n));
    }

    private static Set<Integer> getAvailableKeymasterSignatureDigests(String[] object, String[] arrobject) {
        int n;
        HashSet<Integer> hashSet = new HashSet<Integer>();
        object = KeyProperties.Digest.allToKeymaster((String[])object);
        int n2 = ((String[])object).length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            hashSet.add((int)object[n]);
        }
        object = new HashSet();
        arrobject = KeyProperties.Digest.allToKeymaster(arrobject);
        n2 = arrobject.length;
        for (n = n3; n < n2; ++n) {
            object.add((int)arrobject[n]);
        }
        object = new HashSet(object);
        object.retainAll(hashSet);
        return object;
    }

    private static String getCertificateSignatureAlgorithm(int n, int n2, KeyGenParameterSpec object) {
        if ((((KeyGenParameterSpec)object).getPurposes() & 4) == 0) {
            return null;
        }
        if (((KeyGenParameterSpec)object).isUserAuthenticationRequired()) {
            return null;
        }
        if (!((KeyGenParameterSpec)object).isDigestsSpecified()) {
            return null;
        }
        if (n != 1) {
            if (n == 3) {
                object = AndroidKeyStoreKeyPairGeneratorSpi.getAvailableKeymasterSignatureDigests(((KeyGenParameterSpec)object).getDigests(), AndroidKeyStoreBCWorkaroundProvider.getSupportedEcdsaSignatureDigests());
                int n3 = -1;
                int n4 = -1;
                object = object.iterator();
                do {
                    int n5;
                    n = n3;
                    if (!object.hasNext()) break;
                    int n6 = (Integer)object.next();
                    int n7 = KeymasterUtils.getDigestOutputSizeBits(n6);
                    if (n7 == n2) {
                        n = n6;
                        break;
                    }
                    if (n3 == -1) {
                        n = n6;
                        n5 = n7;
                    } else if (n4 < n2) {
                        n = n3;
                        n5 = n4;
                        if (n7 > n4) {
                            n = n6;
                            n5 = n7;
                        }
                    } else {
                        n = n3;
                        n5 = n4;
                        if (n7 < n4) {
                            n = n3;
                            n5 = n4;
                            if (n7 >= n2) {
                                n = n6;
                                n5 = n7;
                            }
                        }
                    }
                    n3 = n;
                    n4 = n5;
                } while (true);
                if (n == -1) {
                    return null;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append(KeyProperties.Digest.fromKeymasterToSignatureAlgorithmDigest(n));
                ((StringBuilder)object).append("WithECDSA");
                return ((StringBuilder)object).toString();
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unsupported algorithm: ");
            ((StringBuilder)object).append(n);
            throw new ProviderException(((StringBuilder)object).toString());
        }
        if (!ArrayUtils.contains(KeyProperties.SignaturePadding.allToKeymaster(((KeyGenParameterSpec)object).getSignaturePaddings()), 5)) {
            return null;
        }
        object = AndroidKeyStoreKeyPairGeneratorSpi.getAvailableKeymasterSignatureDigests(((KeyGenParameterSpec)object).getDigests(), AndroidKeyStoreBCWorkaroundProvider.getSupportedEcdsaSignatureDigests());
        int n8 = -1;
        int n9 = -1;
        object = object.iterator();
        while (object.hasNext()) {
            int n10 = (Integer)object.next();
            int n11 = KeymasterUtils.getDigestOutputSizeBits(n10);
            if (n11 > n2 - 240) continue;
            if (n8 == -1) {
                n8 = n10;
                n = n11;
            } else {
                n = n9;
                if (n11 > n9) {
                    n8 = n10;
                    n = n11;
                }
            }
            n9 = n;
        }
        if (n8 == -1) {
            return null;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(KeyProperties.Digest.fromKeymasterToSignatureAlgorithmDigest(n8));
        ((StringBuilder)object).append("WithRSA");
        return ((StringBuilder)object).toString();
    }

    private static int getDefaultKeySize(int n) {
        if (n != 1) {
            if (n == 3) {
                return 256;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported algorithm: ");
            stringBuilder.append(n);
            throw new ProviderException(stringBuilder.toString());
        }
        return 2048;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void initAlgorithmSpecificParameters() throws InvalidAlgorithmParameterException {
        Object object = this.mSpec.getAlgorithmParameterSpec();
        int n = this.mKeymasterAlgorithm;
        if (n != 1) {
            if (n != 3) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported algorithm: ");
                stringBuilder.append(this.mKeymasterAlgorithm);
                throw new ProviderException(stringBuilder.toString());
            }
            if (!(object instanceof ECGenParameterSpec)) {
                if (object != null) throw new InvalidAlgorithmParameterException("EC may only use ECGenParameterSpec");
                return;
            }
            CharSequence charSequence = ((ECGenParameterSpec)object).getName();
            Integer n2 = SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE.get(((String)charSequence).toLowerCase(Locale.US));
            if (n2 == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unsupported EC curve name: ");
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append(". Supported: ");
                ((StringBuilder)object).append(SUPPORTED_EC_NIST_CURVE_NAMES);
                throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
            }
            n = this.mKeySizeBits;
            if (n == -1) {
                this.mKeySizeBits = n2;
                return;
            }
            if (n == n2) {
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("EC key size must match  between ");
            ((StringBuilder)charSequence).append(this.mSpec);
            ((StringBuilder)charSequence).append(" and ");
            ((StringBuilder)charSequence).append(object);
            ((StringBuilder)charSequence).append(": ");
            ((StringBuilder)charSequence).append(this.mKeySizeBits);
            ((StringBuilder)charSequence).append(" vs ");
            ((StringBuilder)charSequence).append(n2);
            throw new InvalidAlgorithmParameterException(((StringBuilder)charSequence).toString());
        }
        Object object2 = null;
        if (object instanceof RSAKeyGenParameterSpec) {
            object2 = (RSAKeyGenParameterSpec)object;
            n = this.mKeySizeBits;
            if (n == -1) {
                this.mKeySizeBits = ((RSAKeyGenParameterSpec)object2).getKeysize();
            } else if (n != ((RSAKeyGenParameterSpec)object2).getKeysize()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("RSA key size must match  between ");
                stringBuilder.append(this.mSpec);
                stringBuilder.append(" and ");
                stringBuilder.append(object);
                stringBuilder.append(": ");
                stringBuilder.append(this.mKeySizeBits);
                stringBuilder.append(" vs ");
                stringBuilder.append(((RSAKeyGenParameterSpec)object2).getKeysize());
                throw new InvalidAlgorithmParameterException(stringBuilder.toString());
            }
            object2 = ((RSAKeyGenParameterSpec)object2).getPublicExponent();
        } else if (object != null) throw new InvalidAlgorithmParameterException("RSA may only use RSAKeyGenParameterSpec");
        object = object2;
        if (object2 == null) {
            object = RSAKeyGenParameterSpec.F4;
        }
        if (((BigInteger)object).compareTo(BigInteger.ZERO) < 1) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("RSA public exponent must be positive: ");
            ((StringBuilder)object2).append(object);
            throw new InvalidAlgorithmParameterException(((StringBuilder)object2).toString());
        }
        if (((BigInteger)object).compareTo(KeymasterArguments.UINT64_MAX_VALUE) <= 0) {
            this.mRSAPublicExponent = object;
            return;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Unsupported RSA public exponent: ");
        ((StringBuilder)object2).append(object);
        ((StringBuilder)object2).append(". Maximum supported value: ");
        ((StringBuilder)object2).append(KeymasterArguments.UINT64_MAX_VALUE);
        throw new InvalidAlgorithmParameterException(((StringBuilder)object2).toString());
    }

    private KeyPair loadKeystoreKeyPair(String object) throws ProviderException {
        KeyPair keyPair;
        block3 : {
            keyPair = AndroidKeyStoreProvider.loadAndroidKeyStoreKeyPairFromKeystore(this.mKeyStore, (String)object, this.mEntryUid);
            if (!this.mJcaKeyAlgorithm.equalsIgnoreCase(keyPair.getPrivate().getAlgorithm())) break block3;
            return keyPair;
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Generated key pair algorithm does not match requested algorithm: ");
            stringBuilder.append(keyPair.getPrivate().getAlgorithm());
            stringBuilder.append(" vs ");
            stringBuilder.append(this.mJcaKeyAlgorithm);
            object = new ProviderException(stringBuilder.toString());
            throw object;
        }
        catch (KeyPermanentlyInvalidatedException | UnrecoverableKeyException generalSecurityException) {
            throw new ProviderException("Failed to load generated key pair from keystore", generalSecurityException);
        }
    }

    private void resetAll() {
        this.mEntryAlias = null;
        this.mEntryUid = -1;
        this.mJcaKeyAlgorithm = null;
        this.mKeymasterAlgorithm = -1;
        this.mKeymasterPurposes = null;
        this.mKeymasterBlockModes = null;
        this.mKeymasterEncryptionPaddings = null;
        this.mKeymasterSignaturePaddings = null;
        this.mKeymasterDigests = null;
        this.mKeySizeBits = 0;
        this.mSpec = null;
        this.mRSAPublicExponent = null;
        this.mEncryptionAtRestRequired = false;
        this.mRng = null;
        this.mKeyStore = null;
    }

    private void storeCertificate(String string2, byte[] arrby, int n, String string3) throws ProviderException {
        KeyStore keyStore = this.mKeyStore;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(this.mEntryAlias);
        n = keyStore.insert(stringBuilder.toString(), arrby, this.mEntryUid, n);
        if (n == 1) {
            return;
        }
        throw new ProviderException(string3, KeyStore.getKeyStoreException(n));
    }

    private void storeCertificateChain(int n, Iterable<byte[]> object) throws ProviderException {
        object = object.iterator();
        this.storeCertificate("USRCERT_", (byte[])object.next(), n, "Failed to store certificate");
        if (!object.hasNext()) {
            return;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (object.hasNext()) {
            byte[] arrby = (byte[])object.next();
            byteArrayOutputStream.write(arrby, 0, arrby.length);
        }
        this.storeCertificate("CACERT_", byteArrayOutputStream.toByteArray(), n, "Failed to store attestation CA certificate");
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public KeyPair generateKeyPair() {
        Throwable throwable2222;
        Object object2 = this.mKeyStore;
        if (object2 == null) throw new IllegalStateException("Not initialized");
        if (this.mSpec == null) throw new IllegalStateException("Not initialized");
        int n = this.mEncryptionAtRestRequired ? 1 : 0;
        if (n & true) {
            if (((KeyStore)object2).state() != KeyStore.State.UNLOCKED) throw new IllegalStateException("Encryption at rest using secure lock screen credential requested for key pair, but the user has not yet entered the credential");
        }
        int n2 = n;
        if (this.mSpec.isStrongBoxBacked()) {
            n2 = n | 16;
        }
        Object object = KeyStoreCryptoOperationUtils.getRandomBytesToMixIntoKeystoreRng(this.mRng, (this.mKeySizeBits + 7) / 8);
        Credentials.deleteAllTypesForAlias(this.mKeyStore, this.mEntryAlias, this.mEntryUid);
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("USRPKEY_");
        ((StringBuilder)object2).append(this.mEntryAlias);
        object2 = ((StringBuilder)object2).toString();
        this.generateKeystoreKeyPair((String)object2, this.constructKeyGenerationArguments(), (byte[])object, n2);
        object = this.loadKeystoreKeyPair((String)object2);
        this.storeCertificateChain(n2, this.createCertificateChain((String)object2, (KeyPair)object));
        if (true) return object;
        Credentials.deleteAllTypesForAlias(this.mKeyStore, this.mEntryAlias, this.mEntryUid);
        return object;
        {
            catch (Throwable throwable2222) {
            }
            catch (ProviderException providerException) {}
            {
                if ((this.mSpec.getPurposes() & 32) == 0) throw providerException;
                object = new SecureKeyImportUnavailableException(providerException);
                throw object;
            }
        }
        if (false) throw throwable2222;
        Credentials.deleteAllTypesForAlias(this.mKeyStore, this.mEntryAlias, this.mEntryUid);
        throw throwable2222;
    }

    @Override
    public void initialize(int n, SecureRandom serializable) {
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append(KeyGenParameterSpec.class.getName());
        ((StringBuilder)serializable).append(" or ");
        ((StringBuilder)serializable).append(KeyPairGeneratorSpec.class.getName());
        ((StringBuilder)serializable).append(" required to initialize this KeyPairGenerator");
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    /*
     * Exception decompiling
     */
    @Override
    public void initialize(AlgorithmParameterSpec var1_1, SecureRandom var2_7) throws InvalidAlgorithmParameterException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 10[CATCHBLOCK]
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

    public static class EC
    extends AndroidKeyStoreKeyPairGeneratorSpi {
        public EC() {
            super(3);
        }
    }

    public static class RSA
    extends AndroidKeyStoreKeyPairGeneratorSpi {
        public RSA() {
            super(1);
        }
    }

}

