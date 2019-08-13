/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.bouncycastle.jce.provider;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.ClassUtil;
import com.android.org.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import com.android.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import com.android.org.bouncycastle.jce.provider.BouncyCastleProviderConfiguration;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivateKey;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class BouncyCastleProvider
extends Provider
implements ConfigurableProvider {
    private static final String[] ASYMMETRIC_CIPHERS;
    private static final String[] ASYMMETRIC_GENERIC;
    private static final String ASYMMETRIC_PACKAGE = "com.android.org.bouncycastle.jcajce.provider.asymmetric.";
    public static final ProviderConfiguration CONFIGURATION;
    private static final String[] DIGESTS;
    private static final String DIGEST_PACKAGE = "com.android.org.bouncycastle.jcajce.provider.digest.";
    private static final String[] KEYSTORES;
    private static final String KEYSTORE_PACKAGE = "com.android.org.bouncycastle.jcajce.provider.keystore.";
    public static final String PROVIDER_NAME = "BC";
    private static final String[] SYMMETRIC_CIPHERS;
    private static final String[] SYMMETRIC_GENERIC;
    private static final String[] SYMMETRIC_MACS;
    private static final String SYMMETRIC_PACKAGE = "com.android.org.bouncycastle.jcajce.provider.symmetric.";
    private static String info;
    private static final Map keyInfoConverters;

    static {
        info = "BouncyCastle Security Provider v1.61";
        CONFIGURATION = new BouncyCastleProviderConfiguration();
        keyInfoConverters = new HashMap();
        SYMMETRIC_GENERIC = new String[]{"PBEPBKDF2", "PBEPKCS12", "PBES2AlgorithmParameters"};
        SYMMETRIC_MACS = new String[0];
        SYMMETRIC_CIPHERS = new String[]{"AES", "ARC4", "Blowfish", "DES", "DESede", "RC2", "Twofish"};
        ASYMMETRIC_GENERIC = new String[]{"X509"};
        ASYMMETRIC_CIPHERS = new String[]{"DSA", "DH", "EC", "RSA"};
        DIGESTS = new String[]{"MD5", "SHA1", "SHA224", "SHA256", "SHA384", "SHA512"};
        KEYSTORES = new String[]{PROVIDER_NAME, "BCFKS", "PKCS12"};
    }

    @UnsupportedAppUsage
    public BouncyCastleProvider() {
        super(PROVIDER_NAME, 1.61, info);
        AccessController.doPrivileged(new PrivilegedAction(){

            public Object run() {
                BouncyCastleProvider.this.setup();
                return null;
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static AsymmetricKeyInfoConverter getAsymmetricKeyInfoConverter(ASN1ObjectIdentifier object) {
        Map map = keyInfoConverters;
        synchronized (map) {
            return (AsymmetricKeyInfoConverter)keyInfoConverters.get(object);
        }
    }

    public static PrivateKey getPrivateKey(PrivateKeyInfo privateKeyInfo) throws IOException {
        AsymmetricKeyInfoConverter asymmetricKeyInfoConverter = BouncyCastleProvider.getAsymmetricKeyInfoConverter(privateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm());
        if (asymmetricKeyInfoConverter == null) {
            return null;
        }
        return asymmetricKeyInfoConverter.generatePrivate(privateKeyInfo);
    }

    public static PublicKey getPublicKey(SubjectPublicKeyInfo subjectPublicKeyInfo) throws IOException {
        AsymmetricKeyInfoConverter asymmetricKeyInfoConverter = BouncyCastleProvider.getAsymmetricKeyInfoConverter(subjectPublicKeyInfo.getAlgorithm().getAlgorithm());
        if (asymmetricKeyInfoConverter == null) {
            return null;
        }
        return asymmetricKeyInfoConverter.generatePublic(subjectPublicKeyInfo);
    }

    private void loadAlgorithms(String string, String[] arrstring) {
        for (int i = 0; i != arrstring.length; ++i) {
            Serializable serializable = new StringBuilder();
            ((StringBuilder)serializable).append(string);
            ((StringBuilder)serializable).append(arrstring[i]);
            ((StringBuilder)serializable).append("$Mappings");
            serializable = ClassUtil.loadClass(BouncyCastleProvider.class, ((StringBuilder)serializable).toString());
            if (serializable == null) continue;
            try {
                ((AlgorithmProvider)((Class)serializable).newInstance()).configure(this);
                continue;
            }
            catch (Exception exception) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("cannot create instance of ");
                ((StringBuilder)serializable).append(string);
                ((StringBuilder)serializable).append(arrstring[i]);
                ((StringBuilder)serializable).append("$Mappings : ");
                ((StringBuilder)serializable).append(exception);
                throw new InternalError(((StringBuilder)serializable).toString());
            }
        }
    }

    private void setup() {
        this.loadAlgorithms(DIGEST_PACKAGE, DIGESTS);
        this.loadAlgorithms(SYMMETRIC_PACKAGE, SYMMETRIC_GENERIC);
        this.loadAlgorithms(SYMMETRIC_PACKAGE, SYMMETRIC_MACS);
        this.loadAlgorithms(SYMMETRIC_PACKAGE, SYMMETRIC_CIPHERS);
        this.loadAlgorithms(ASYMMETRIC_PACKAGE, ASYMMETRIC_GENERIC);
        this.loadAlgorithms(ASYMMETRIC_PACKAGE, ASYMMETRIC_CIPHERS);
        this.loadAlgorithms(KEYSTORE_PACKAGE, KEYSTORES);
        this.put("CertPathValidator.PKIX", "com.android.org.bouncycastle.jce.provider.PKIXCertPathValidatorSpi");
        this.put("CertPathBuilder.PKIX", "com.android.org.bouncycastle.jce.provider.PKIXCertPathBuilderSpi");
        this.put("CertStore.Collection", "com.android.org.bouncycastle.jce.provider.CertStoreCollectionSpi");
    }

    @Override
    public void addAlgorithm(String string, ASN1ObjectIdentifier aSN1ObjectIdentifier, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".");
        stringBuilder.append(aSN1ObjectIdentifier);
        this.addAlgorithm(stringBuilder.toString(), string2);
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".OID.");
        stringBuilder.append(aSN1ObjectIdentifier);
        this.addAlgorithm(stringBuilder.toString(), string2);
    }

    @Override
    public void addAlgorithm(String string, String charSequence) {
        if (!this.containsKey(string)) {
            this.put(string, charSequence);
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("duplicate provider key (");
        ((StringBuilder)charSequence).append(string);
        ((StringBuilder)charSequence).append(") found");
        throw new IllegalStateException(((StringBuilder)charSequence).toString());
    }

    @Override
    public void addAttributes(String charSequence, Map<String, String> map) {
        for (String string : map.keySet()) {
            CharSequence charSequence2 = new StringBuilder();
            charSequence2.append((String)charSequence);
            charSequence2.append(" ");
            charSequence2.append(string);
            charSequence2 = charSequence2.toString();
            if (!this.containsKey(charSequence2)) {
                this.put(charSequence2, map.get(string));
                continue;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("duplicate provider attribute key (");
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(") found");
            throw new IllegalStateException(((StringBuilder)charSequence).toString());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void addKeyInfoConverter(ASN1ObjectIdentifier aSN1ObjectIdentifier, AsymmetricKeyInfoConverter asymmetricKeyInfoConverter) {
        Map map = keyInfoConverters;
        synchronized (map) {
            keyInfoConverters.put(aSN1ObjectIdentifier, asymmetricKeyInfoConverter);
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean hasAlgorithm(String string, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".");
        stringBuilder.append(string2);
        if (this.containsKey(stringBuilder.toString())) return true;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Alg.Alias.");
        stringBuilder.append(string);
        stringBuilder.append(".");
        stringBuilder.append(string2);
        if (!this.containsKey(stringBuilder.toString())) return false;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setParameter(String string, Object object) {
        ProviderConfiguration providerConfiguration = CONFIGURATION;
        synchronized (providerConfiguration) {
            ((BouncyCastleProviderConfiguration)CONFIGURATION).setParameter(string, object);
            return;
        }
    }

}

