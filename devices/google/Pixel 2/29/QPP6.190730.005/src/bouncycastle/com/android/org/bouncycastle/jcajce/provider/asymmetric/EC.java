/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi;
import com.android.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import com.android.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import java.util.HashMap;
import java.util.Map;

public class EC {
    private static final String PREFIX = "com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.";
    private static final Map<String, String> generalEcAttributes = new HashMap<String, String>();

    static {
        generalEcAttributes.put("SupportedKeyClasses", "java.security.interfaces.ECPublicKey|java.security.interfaces.ECPrivateKey");
        generalEcAttributes.put("SupportedKeyFormats", "PKCS#8|X.509");
    }

    public static class Mappings
    extends AsymmetricAlgorithmProvider {
        @Override
        public void configure(ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("AlgorithmParameters.EC", "com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.AlgorithmParametersSpi");
            configurableProvider.addAttributes("KeyAgreement.ECDH", generalEcAttributes);
            configurableProvider.addAlgorithm("KeyAgreement.ECDH", "com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DH");
            this.registerOid(configurableProvider, X9ObjectIdentifiers.id_ecPublicKey, "EC", new KeyFactorySpi.EC());
            this.registerOid(configurableProvider, X9ObjectIdentifiers.dhSinglePass_stdDH_sha1kdf_scheme, "EC", new KeyFactorySpi.EC());
            configurableProvider.addAlgorithm("KeyFactory.EC", "com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi$EC");
            configurableProvider.addAlgorithm("KeyPairGenerator.EC", "com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.KeyPairGeneratorSpi$EC");
            configurableProvider.addAlgorithm("Signature.SHA1withECDSA", "com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDSA");
            configurableProvider.addAlgorithm("Signature.NONEwithECDSA", "com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDSAnone");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.ECDSA", "SHA1withECDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.ECDSAwithSHA1", "SHA1withECDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.SHA1WITHECDSA", "SHA1withECDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.ECDSAWITHSHA1", "SHA1withECDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.SHA1WithECDSA", "SHA1withECDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.ECDSAWithSHA1", "SHA1withECDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.1.2.840.10045.4.1", "SHA1withECDSA");
            this.addSignatureAlgorithm(configurableProvider, "SHA224", "ECDSA", "com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDSA224", X9ObjectIdentifiers.ecdsa_with_SHA224);
            this.addSignatureAlgorithm(configurableProvider, "SHA256", "ECDSA", "com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDSA256", X9ObjectIdentifiers.ecdsa_with_SHA256);
            this.addSignatureAlgorithm(configurableProvider, "SHA384", "ECDSA", "com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDSA384", X9ObjectIdentifiers.ecdsa_with_SHA384);
            this.addSignatureAlgorithm(configurableProvider, "SHA512", "ECDSA", "com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi$ecDSA512", X9ObjectIdentifiers.ecdsa_with_SHA512);
        }
    }

}

