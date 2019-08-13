/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyFactorySpi;
import com.android.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import com.android.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import java.util.HashMap;
import java.util.Map;

public class RSA {
    private static final String PREFIX = "com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.";
    private static final Map<String, String> generalRsaAttributes = new HashMap<String, String>();

    static {
        generalRsaAttributes.put("SupportedKeyClasses", "javax.crypto.interfaces.RSAPublicKey|javax.crypto.interfaces.RSAPrivateKey");
        generalRsaAttributes.put("SupportedKeyFormats", "PKCS#8|X.509");
    }

    public static class Mappings
    extends AsymmetricAlgorithmProvider {
        private void addDigestSignature(ConfigurableProvider configurableProvider, String charSequence, String charSequence2, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
            CharSequence charSequence3 = new StringBuilder();
            charSequence3.append((String)charSequence);
            charSequence3.append("WITHRSA");
            charSequence3 = charSequence3.toString();
            CharSequence charSequence4 = new StringBuilder();
            charSequence4.append((String)charSequence);
            charSequence4.append("withRSA");
            String string = charSequence4.toString();
            charSequence4 = new StringBuilder();
            charSequence4.append((String)charSequence);
            charSequence4.append("WithRSA");
            String string2 = charSequence4.toString();
            charSequence4 = new StringBuilder();
            charSequence4.append((String)charSequence);
            charSequence4.append("/RSA");
            charSequence4 = charSequence4.toString();
            CharSequence charSequence5 = new StringBuilder();
            charSequence5.append((String)charSequence);
            charSequence5.append("WITHRSAENCRYPTION");
            charSequence5 = charSequence5.toString();
            CharSequence charSequence6 = new StringBuilder();
            charSequence6.append((String)charSequence);
            charSequence6.append("withRSAEncryption");
            charSequence6 = charSequence6.toString();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append("WithRSAEncryption");
            charSequence = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append("Signature.");
            stringBuilder.append((String)charSequence3);
            configurableProvider.addAlgorithm(stringBuilder.toString(), (String)charSequence2);
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("Alg.Alias.Signature.");
            ((StringBuilder)charSequence2).append(string);
            configurableProvider.addAlgorithm(((StringBuilder)charSequence2).toString(), (String)charSequence3);
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("Alg.Alias.Signature.");
            ((StringBuilder)charSequence2).append(string2);
            configurableProvider.addAlgorithm(((StringBuilder)charSequence2).toString(), (String)charSequence3);
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("Alg.Alias.Signature.");
            ((StringBuilder)charSequence2).append((String)charSequence5);
            configurableProvider.addAlgorithm(((StringBuilder)charSequence2).toString(), (String)charSequence3);
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("Alg.Alias.Signature.");
            ((StringBuilder)charSequence2).append((String)charSequence6);
            configurableProvider.addAlgorithm(((StringBuilder)charSequence2).toString(), (String)charSequence3);
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("Alg.Alias.Signature.");
            ((StringBuilder)charSequence2).append((String)charSequence);
            configurableProvider.addAlgorithm(((StringBuilder)charSequence2).toString(), (String)charSequence3);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Alg.Alias.Signature.");
            ((StringBuilder)charSequence).append((String)charSequence4);
            configurableProvider.addAlgorithm(((StringBuilder)charSequence).toString(), (String)charSequence3);
            if (aSN1ObjectIdentifier != null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Alg.Alias.Signature.");
                ((StringBuilder)charSequence).append(aSN1ObjectIdentifier);
                configurableProvider.addAlgorithm(((StringBuilder)charSequence).toString(), (String)charSequence3);
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Alg.Alias.Signature.OID.");
                ((StringBuilder)charSequence).append(aSN1ObjectIdentifier);
                configurableProvider.addAlgorithm(((StringBuilder)charSequence).toString(), (String)charSequence3);
            }
        }

        private void addISO9796Signature(ConfigurableProvider configurableProvider, String string, String string2) {
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Alg.Alias.Signature.");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("withRSA/ISO9796-2");
            charSequence = ((StringBuilder)charSequence).toString();
            CharSequence charSequence2 = new StringBuilder();
            charSequence2.append(string);
            charSequence2.append("WITHRSA/ISO9796-2");
            configurableProvider.addAlgorithm((String)charSequence, charSequence2.toString());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Alg.Alias.Signature.");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("WithRSA/ISO9796-2");
            charSequence2 = ((StringBuilder)charSequence).toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("WITHRSA/ISO9796-2");
            configurableProvider.addAlgorithm((String)charSequence2, ((StringBuilder)charSequence).toString());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Signature.");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("WITHRSA/ISO9796-2");
            configurableProvider.addAlgorithm(((StringBuilder)charSequence).toString(), string2);
        }

        private void addPSSSignature(ConfigurableProvider configurableProvider, String string, String string2) {
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Alg.Alias.Signature.");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("withRSA/PSS");
            charSequence = ((StringBuilder)charSequence).toString();
            CharSequence charSequence2 = new StringBuilder();
            charSequence2.append(string);
            charSequence2.append("WITHRSAANDMGF1");
            configurableProvider.addAlgorithm((String)charSequence, charSequence2.toString());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Alg.Alias.Signature.");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("WithRSA/PSS");
            charSequence2 = ((StringBuilder)charSequence).toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("WITHRSAANDMGF1");
            configurableProvider.addAlgorithm((String)charSequence2, ((StringBuilder)charSequence).toString());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Alg.Alias.Signature.");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("withRSAandMGF1");
            charSequence2 = ((StringBuilder)charSequence).toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("WITHRSAANDMGF1");
            configurableProvider.addAlgorithm((String)charSequence2, ((StringBuilder)charSequence).toString());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Alg.Alias.Signature.");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("WithRSAAndMGF1");
            charSequence2 = ((StringBuilder)charSequence).toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("WITHRSAANDMGF1");
            configurableProvider.addAlgorithm((String)charSequence2, ((StringBuilder)charSequence).toString());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Signature.");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("WITHRSAANDMGF1");
            configurableProvider.addAlgorithm(((StringBuilder)charSequence).toString(), string2);
        }

        private void addX931Signature(ConfigurableProvider configurableProvider, String string, String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.Signature.");
            stringBuilder.append(string);
            stringBuilder.append("withRSA/X9.31");
            String string3 = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append("WITHRSA/X9.31");
            configurableProvider.addAlgorithm(string3, stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.Signature.");
            stringBuilder.append(string);
            stringBuilder.append("WithRSA/X9.31");
            string3 = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append("WITHRSA/X9.31");
            configurableProvider.addAlgorithm(string3, stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("Signature.");
            stringBuilder.append(string);
            stringBuilder.append("WITHRSA/X9.31");
            configurableProvider.addAlgorithm(stringBuilder.toString(), string2);
        }

        @Override
        public void configure(ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("AlgorithmParameters.OAEP", "com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.AlgorithmParametersSpi$OAEP");
            configurableProvider.addAlgorithm("AlgorithmParameters.PSS", "com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.AlgorithmParametersSpi$PSS");
            configurableProvider.addAttributes("Cipher.RSA", generalRsaAttributes);
            configurableProvider.addAlgorithm("Cipher.RSA", "com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.CipherSpi$NoPadding");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.RSA/RAW", "RSA");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.RSA//RAW", "RSA");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.RSA//NOPADDING", "RSA");
            configurableProvider.addAlgorithm("KeyFactory.RSA", "com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyFactorySpi");
            configurableProvider.addAlgorithm("KeyPairGenerator.RSA", "com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyPairGeneratorSpi");
            Object object = new KeyFactorySpi();
            this.registerOid(configurableProvider, PKCSObjectIdentifiers.rsaEncryption, "RSA", (AsymmetricKeyInfoConverter)object);
            this.registerOid(configurableProvider, X509ObjectIdentifiers.id_ea_rsa, "RSA", (AsymmetricKeyInfoConverter)object);
            this.registerOid(configurableProvider, PKCSObjectIdentifiers.id_RSAES_OAEP, "RSA", (AsymmetricKeyInfoConverter)object);
            if (configurableProvider.hasAlgorithm("MessageDigest", "MD5")) {
                this.addDigestSignature(configurableProvider, "MD5", "com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$MD5", PKCSObjectIdentifiers.md5WithRSAEncryption);
            }
            if (configurableProvider.hasAlgorithm("MessageDigest", "SHA1")) {
                this.addDigestSignature(configurableProvider, "SHA1", "com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA1", PKCSObjectIdentifiers.sha1WithRSAEncryption);
                object = new StringBuilder();
                ((StringBuilder)object).append("Alg.Alias.Signature.");
                ((StringBuilder)object).append(OIWObjectIdentifiers.sha1WithRSA);
                configurableProvider.addAlgorithm(((StringBuilder)object).toString(), "SHA1WITHRSA");
                object = new StringBuilder();
                ((StringBuilder)object).append("Alg.Alias.Signature.OID.");
                ((StringBuilder)object).append(OIWObjectIdentifiers.sha1WithRSA);
                configurableProvider.addAlgorithm(((StringBuilder)object).toString(), "SHA1WITHRSA");
            }
            this.addDigestSignature(configurableProvider, "SHA224", "com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA224", PKCSObjectIdentifiers.sha224WithRSAEncryption);
            this.addDigestSignature(configurableProvider, "SHA256", "com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA256", PKCSObjectIdentifiers.sha256WithRSAEncryption);
            this.addDigestSignature(configurableProvider, "SHA384", "com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA384", PKCSObjectIdentifiers.sha384WithRSAEncryption);
            this.addDigestSignature(configurableProvider, "SHA512", "com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA512", PKCSObjectIdentifiers.sha512WithRSAEncryption);
        }
    }

}

