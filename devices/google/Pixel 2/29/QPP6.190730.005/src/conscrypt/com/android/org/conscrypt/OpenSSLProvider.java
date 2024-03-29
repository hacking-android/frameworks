/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.Platform;
import com.android.org.conscrypt.TrustManagerFactoryImpl;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.security.Provider;

public final class OpenSSLProvider
extends Provider {
    private static final String PREFIX;
    private static final String STANDARD_EC_PRIVATE_KEY_INTERFACE_CLASS_NAME = "java.security.interfaces.ECPrivateKey";
    private static final String STANDARD_RSA_PRIVATE_KEY_INTERFACE_CLASS_NAME = "java.security.interfaces.RSAPrivateKey";
    private static final String STANDARD_RSA_PUBLIC_KEY_INTERFACE_CLASS_NAME = "java.security.interfaces.RSAPublicKey";
    private static final long serialVersionUID = 2996752495318905136L;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(OpenSSLProvider.class.getPackage().getName());
        stringBuilder.append(".");
        PREFIX = stringBuilder.toString();
    }

    @UnsupportedAppUsage
    public OpenSSLProvider() {
        this(Platform.getDefaultProviderName());
    }

    public OpenSSLProvider(String string) {
        this(string, Platform.provideTrustManagerByDefault());
    }

    OpenSSLProvider(String charSequence, boolean bl) {
        super((String)charSequence, 1.0, "Android's OpenSSL-backed security provider");
        NativeCrypto.checkAvailability();
        Platform.setup();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("OpenSSLContextImpl");
        charSequence = ((StringBuilder)charSequence).toString();
        CharSequence charSequence2 = new StringBuilder();
        charSequence2.append((String)charSequence);
        charSequence2.append("$TLSv13");
        charSequence2 = charSequence2.toString();
        this.put("SSLContext.SSL", charSequence2);
        this.put("SSLContext.TLS", charSequence2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        stringBuilder.append("$TLSv1");
        this.put("SSLContext.TLSv1", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        stringBuilder.append("$TLSv11");
        this.put("SSLContext.TLSv1.1", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        stringBuilder.append("$TLSv12");
        this.put("SSLContext.TLSv1.2", stringBuilder.toString());
        this.put("SSLContext.TLSv1.3", charSequence2);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("DefaultSSLContextImpl");
        this.put("SSLContext.Default", ((StringBuilder)charSequence).toString());
        if (bl) {
            this.put("TrustManagerFactory.PKIX", TrustManagerFactoryImpl.class.getName());
            this.put("Alg.Alias.TrustManagerFactory.X509", "PKIX");
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("IvParameters$AES");
        this.put("AlgorithmParameters.AES", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.1.2", "AES");
        this.put("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.1.22", "AES");
        this.put("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.1.42", "AES");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("IvParameters$ChaCha20");
        this.put("AlgorithmParameters.ChaCha20", ((StringBuilder)charSequence).toString());
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("IvParameters$DESEDE");
        this.put("AlgorithmParameters.DESEDE", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.AlgorithmParameters.TDEA", "DESEDE");
        this.put("Alg.Alias.AlgorithmParameters.1.2.840.113549.3.7", "DESEDE");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("GCMParameters");
        this.put("AlgorithmParameters.GCM", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.1.6", "GCM");
        this.put("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.1.26", "GCM");
        this.put("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.1.46", "GCM");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("OAEPParameters");
        this.put("AlgorithmParameters.OAEP", ((StringBuilder)charSequence).toString());
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("PSSParameters");
        this.put("AlgorithmParameters.PSS", ((StringBuilder)charSequence).toString());
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("ECParameters");
        this.put("AlgorithmParameters.EC", ((StringBuilder)charSequence).toString());
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("OpenSSLMessageDigestJDK$SHA1");
        this.put("MessageDigest.SHA-1", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.MessageDigest.SHA1", "SHA-1");
        this.put("Alg.Alias.MessageDigest.SHA", "SHA-1");
        this.put("Alg.Alias.MessageDigest.1.3.14.3.2.26", "SHA-1");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("OpenSSLMessageDigestJDK$SHA224");
        this.put("MessageDigest.SHA-224", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.MessageDigest.SHA224", "SHA-224");
        this.put("Alg.Alias.MessageDigest.2.16.840.1.101.3.4.2.4", "SHA-224");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("OpenSSLMessageDigestJDK$SHA256");
        this.put("MessageDigest.SHA-256", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.MessageDigest.SHA256", "SHA-256");
        this.put("Alg.Alias.MessageDigest.2.16.840.1.101.3.4.2.1", "SHA-256");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("OpenSSLMessageDigestJDK$SHA384");
        this.put("MessageDigest.SHA-384", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.MessageDigest.SHA384", "SHA-384");
        this.put("Alg.Alias.MessageDigest.2.16.840.1.101.3.4.2.2", "SHA-384");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("OpenSSLMessageDigestJDK$SHA512");
        this.put("MessageDigest.SHA-512", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.MessageDigest.SHA512", "SHA-512");
        this.put("Alg.Alias.MessageDigest.2.16.840.1.101.3.4.2.3", "SHA-512");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("OpenSSLMessageDigestJDK$MD5");
        this.put("MessageDigest.MD5", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.MessageDigest.1.2.840.113549.2.5", "MD5");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("KeyGeneratorImpl$ARC4");
        this.put("KeyGenerator.ARC4", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.KeyGenerator.RC4", "ARC4");
        this.put("Alg.Alias.KeyGenerator.1.2.840.113549.3.4", "ARC4");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("KeyGeneratorImpl$AES");
        this.put("KeyGenerator.AES", ((StringBuilder)charSequence).toString());
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("KeyGeneratorImpl$ChaCha20");
        this.put("KeyGenerator.ChaCha20", ((StringBuilder)charSequence).toString());
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("KeyGeneratorImpl$DESEDE");
        this.put("KeyGenerator.DESEDE", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.KeyGenerator.TDEA", "DESEDE");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("KeyGeneratorImpl$HmacMD5");
        this.put("KeyGenerator.HmacMD5", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.KeyGenerator.1.3.6.1.5.5.8.1.1", "HmacMD5");
        this.put("Alg.Alias.KeyGenerator.HMAC-MD5", "HmacMD5");
        this.put("Alg.Alias.KeyGenerator.HMAC/MD5", "HmacMD5");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("KeyGeneratorImpl$HmacSHA1");
        this.put("KeyGenerator.HmacSHA1", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.KeyGenerator.1.2.840.113549.2.7", "HmacSHA1");
        this.put("Alg.Alias.KeyGenerator.1.3.6.1.5.5.8.1.2", "HmacSHA1");
        this.put("Alg.Alias.KeyGenerator.HMAC-SHA1", "HmacSHA1");
        this.put("Alg.Alias.KeyGenerator.HMAC/SHA1", "HmacSHA1");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("KeyGeneratorImpl$HmacSHA224");
        this.put("KeyGenerator.HmacSHA224", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.KeyGenerator.1.2.840.113549.2.8", "HmacSHA224");
        this.put("Alg.Alias.KeyGenerator.HMAC-SHA224", "HmacSHA224");
        this.put("Alg.Alias.KeyGenerator.HMAC/SHA224", "HmacSHA224");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("KeyGeneratorImpl$HmacSHA256");
        this.put("KeyGenerator.HmacSHA256", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.KeyGenerator.1.2.840.113549.2.9", "HmacSHA256");
        this.put("Alg.Alias.KeyGenerator.2.16.840.1.101.3.4.2.1", "HmacSHA256");
        this.put("Alg.Alias.KeyGenerator.HMAC-SHA256", "HmacSHA256");
        this.put("Alg.Alias.KeyGenerator.HMAC/SHA256", "HmacSHA256");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("KeyGeneratorImpl$HmacSHA384");
        this.put("KeyGenerator.HmacSHA384", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.KeyGenerator.1.2.840.113549.2.10", "HmacSHA384");
        this.put("Alg.Alias.KeyGenerator.HMAC-SHA384", "HmacSHA384");
        this.put("Alg.Alias.KeyGenerator.HMAC/SHA384", "HmacSHA384");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("KeyGeneratorImpl$HmacSHA512");
        this.put("KeyGenerator.HmacSHA512", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.KeyGenerator.1.2.840.113549.2.11", "HmacSHA512");
        this.put("Alg.Alias.KeyGenerator.HMAC-SHA512", "HmacSHA512");
        this.put("Alg.Alias.KeyGenerator.HMAC/SHA512", "HmacSHA512");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("OpenSSLRSAKeyPairGenerator");
        this.put("KeyPairGenerator.RSA", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.KeyPairGenerator.1.2.840.113549.1.1.1", "RSA");
        this.put("Alg.Alias.KeyPairGenerator.1.2.840.113549.1.1.7", "RSA");
        this.put("Alg.Alias.KeyPairGenerator.2.5.8.1.1", "RSA");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("OpenSSLECKeyPairGenerator");
        this.put("KeyPairGenerator.EC", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.KeyPairGenerator.1.2.840.10045.2.1", "EC");
        this.put("Alg.Alias.KeyPairGenerator.1.3.133.16.840.63.0.2", "EC");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("OpenSSLRSAKeyFactory");
        this.put("KeyFactory.RSA", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.KeyFactory.1.2.840.113549.1.1.1", "RSA");
        this.put("Alg.Alias.KeyFactory.1.2.840.113549.1.1.7", "RSA");
        this.put("Alg.Alias.KeyFactory.2.5.8.1.1", "RSA");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("OpenSSLECKeyFactory");
        this.put("KeyFactory.EC", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.KeyFactory.1.2.840.10045.2.1", "EC");
        this.put("Alg.Alias.KeyFactory.1.3.133.16.840.63.0.2", "EC");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("DESEDESecretKeyFactory");
        this.put("SecretKeyFactory.DESEDE", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.SecretKeyFactory.TDEA", "DESEDE");
        this.putECDHKeyAgreementImplClass("OpenSSLECDHKeyAgreement");
        this.putSignatureImplClass("MD5withRSA", "OpenSSLSignature$MD5RSA");
        this.put("Alg.Alias.Signature.MD5withRSAEncryption", "MD5withRSA");
        this.put("Alg.Alias.Signature.MD5/RSA", "MD5withRSA");
        this.put("Alg.Alias.Signature.1.2.840.113549.1.1.4", "MD5withRSA");
        this.put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.4", "MD5withRSA");
        this.put("Alg.Alias.Signature.1.2.840.113549.2.5with1.2.840.113549.1.1.1", "MD5withRSA");
        this.putSignatureImplClass("SHA1withRSA", "OpenSSLSignature$SHA1RSA");
        this.put("Alg.Alias.Signature.SHA1withRSAEncryption", "SHA1withRSA");
        this.put("Alg.Alias.Signature.SHA1/RSA", "SHA1withRSA");
        this.put("Alg.Alias.Signature.SHA-1/RSA", "SHA1withRSA");
        this.put("Alg.Alias.Signature.1.2.840.113549.1.1.5", "SHA1withRSA");
        this.put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.5", "SHA1withRSA");
        this.put("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.113549.1.1.1", "SHA1withRSA");
        this.put("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.113549.1.1.5", "SHA1withRSA");
        this.put("Alg.Alias.Signature.1.3.14.3.2.29", "SHA1withRSA");
        this.put("Alg.Alias.Signature.OID.1.3.14.3.2.29", "SHA1withRSA");
        this.putSignatureImplClass("SHA224withRSA", "OpenSSLSignature$SHA224RSA");
        this.put("Alg.Alias.Signature.SHA224withRSAEncryption", "SHA224withRSA");
        this.put("Alg.Alias.Signature.SHA224/RSA", "SHA224withRSA");
        this.put("Alg.Alias.Signature.1.2.840.113549.1.1.14", "SHA224withRSA");
        this.put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.14", "SHA224withRSA");
        this.put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.4with1.2.840.113549.1.1.1", "SHA224withRSA");
        this.put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.4with1.2.840.113549.1.1.14", "SHA224withRSA");
        this.putSignatureImplClass("SHA256withRSA", "OpenSSLSignature$SHA256RSA");
        this.put("Alg.Alias.Signature.SHA256withRSAEncryption", "SHA256withRSA");
        this.put("Alg.Alias.Signature.SHA256/RSA", "SHA256withRSA");
        this.put("Alg.Alias.Signature.1.2.840.113549.1.1.11", "SHA256withRSA");
        this.put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.11", "SHA256withRSA");
        this.put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.1with1.2.840.113549.1.1.1", "SHA256withRSA");
        this.put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.1with1.2.840.113549.1.1.11", "SHA256withRSA");
        this.putSignatureImplClass("SHA384withRSA", "OpenSSLSignature$SHA384RSA");
        this.put("Alg.Alias.Signature.SHA384withRSAEncryption", "SHA384withRSA");
        this.put("Alg.Alias.Signature.SHA384/RSA", "SHA384withRSA");
        this.put("Alg.Alias.Signature.1.2.840.113549.1.1.12", "SHA384withRSA");
        this.put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.12", "SHA384withRSA");
        this.put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.2with1.2.840.113549.1.1.1", "SHA384withRSA");
        this.putSignatureImplClass("SHA512withRSA", "OpenSSLSignature$SHA512RSA");
        this.put("Alg.Alias.Signature.SHA512withRSAEncryption", "SHA512withRSA");
        this.put("Alg.Alias.Signature.SHA512/RSA", "SHA512withRSA");
        this.put("Alg.Alias.Signature.1.2.840.113549.1.1.13", "SHA512withRSA");
        this.put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.13", "SHA512withRSA");
        this.put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.3with1.2.840.113549.1.1.1", "SHA512withRSA");
        this.putRAWRSASignatureImplClass("OpenSSLSignatureRawRSA");
        this.putSignatureImplClass("NONEwithECDSA", "OpenSSLSignatureRawECDSA");
        this.putSignatureImplClass("SHA1withECDSA", "OpenSSLSignature$SHA1ECDSA");
        this.put("Alg.Alias.Signature.ECDSA", "SHA1withECDSA");
        this.put("Alg.Alias.Signature.ECDSAwithSHA1", "SHA1withECDSA");
        this.put("Alg.Alias.Signature.1.2.840.10045.4.1", "SHA1withECDSA");
        this.put("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.10045.2.1", "SHA1withECDSA");
        this.putSignatureImplClass("SHA224withECDSA", "OpenSSLSignature$SHA224ECDSA");
        this.put("Alg.Alias.Signature.SHA224/ECDSA", "SHA224withECDSA");
        this.put("Alg.Alias.Signature.1.2.840.10045.4.3.1", "SHA224withECDSA");
        this.put("Alg.Alias.Signature.OID.1.2.840.10045.4.3.1", "SHA224withECDSA");
        this.put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.4with1.2.840.10045.2.1", "SHA224withECDSA");
        this.putSignatureImplClass("SHA256withECDSA", "OpenSSLSignature$SHA256ECDSA");
        this.put("Alg.Alias.Signature.SHA256/ECDSA", "SHA256withECDSA");
        this.put("Alg.Alias.Signature.1.2.840.10045.4.3.2", "SHA256withECDSA");
        this.put("Alg.Alias.Signature.OID.1.2.840.10045.4.3.2", "SHA256withECDSA");
        this.put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.1with1.2.840.10045.2.1", "SHA256withECDSA");
        this.putSignatureImplClass("SHA384withECDSA", "OpenSSLSignature$SHA384ECDSA");
        this.put("Alg.Alias.Signature.SHA384/ECDSA", "SHA384withECDSA");
        this.put("Alg.Alias.Signature.1.2.840.10045.4.3.3", "SHA384withECDSA");
        this.put("Alg.Alias.Signature.OID.1.2.840.10045.4.3.3", "SHA384withECDSA");
        this.put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.2with1.2.840.10045.2.1", "SHA384withECDSA");
        this.putSignatureImplClass("SHA512withECDSA", "OpenSSLSignature$SHA512ECDSA");
        this.put("Alg.Alias.Signature.SHA512/ECDSA", "SHA512withECDSA");
        this.put("Alg.Alias.Signature.1.2.840.10045.4.3.4", "SHA512withECDSA");
        this.put("Alg.Alias.Signature.OID.1.2.840.10045.4.3.4", "SHA512withECDSA");
        this.put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.3with1.2.840.10045.2.1", "SHA512withECDSA");
        this.putSignatureImplClass("SHA1withRSA/PSS", "OpenSSLSignature$SHA1RSAPSS");
        this.put("Alg.Alias.Signature.SHA1withRSAandMGF1", "SHA1withRSA/PSS");
        this.putSignatureImplClass("SHA224withRSA/PSS", "OpenSSLSignature$SHA224RSAPSS");
        this.put("Alg.Alias.Signature.SHA224withRSAandMGF1", "SHA224withRSA/PSS");
        this.putSignatureImplClass("SHA256withRSA/PSS", "OpenSSLSignature$SHA256RSAPSS");
        this.put("Alg.Alias.Signature.SHA256withRSAandMGF1", "SHA256withRSA/PSS");
        this.putSignatureImplClass("SHA384withRSA/PSS", "OpenSSLSignature$SHA384RSAPSS");
        this.put("Alg.Alias.Signature.SHA384withRSAandMGF1", "SHA384withRSA/PSS");
        this.putSignatureImplClass("SHA512withRSA/PSS", "OpenSSLSignature$SHA512RSAPSS");
        this.put("Alg.Alias.Signature.SHA512withRSAandMGF1", "SHA512withRSA/PSS");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("OpenSSLRandom");
        this.put("SecureRandom.SHA1PRNG", ((StringBuilder)charSequence).toString());
        this.put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        this.putRSACipherImplClass("RSA/ECB/NoPadding", "OpenSSLCipherRSA$Raw");
        this.put("Alg.Alias.Cipher.RSA/None/NoPadding", "RSA/ECB/NoPadding");
        this.putRSACipherImplClass("RSA/ECB/PKCS1Padding", "OpenSSLCipherRSA$PKCS1");
        this.put("Alg.Alias.Cipher.RSA/None/PKCS1Padding", "RSA/ECB/PKCS1Padding");
        this.putRSACipherImplClass("RSA/ECB/OAEPPadding", "OpenSSLCipherRSA$OAEP$SHA1");
        this.put("Alg.Alias.Cipher.RSA/None/OAEPPadding", "RSA/ECB/OAEPPadding");
        this.putRSACipherImplClass("RSA/ECB/OAEPWithSHA-1AndMGF1Padding", "OpenSSLCipherRSA$OAEP$SHA1");
        this.put("Alg.Alias.Cipher.RSA/None/OAEPWithSHA-1AndMGF1Padding", "RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
        this.putRSACipherImplClass("RSA/ECB/OAEPWithSHA-224AndMGF1Padding", "OpenSSLCipherRSA$OAEP$SHA224");
        this.put("Alg.Alias.Cipher.RSA/None/OAEPWithSHA-224AndMGF1Padding", "RSA/ECB/OAEPWithSHA-224AndMGF1Padding");
        this.putRSACipherImplClass("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", "OpenSSLCipherRSA$OAEP$SHA256");
        this.put("Alg.Alias.Cipher.RSA/None/OAEPWithSHA-256AndMGF1Padding", "RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        this.putRSACipherImplClass("RSA/ECB/OAEPWithSHA-384AndMGF1Padding", "OpenSSLCipherRSA$OAEP$SHA384");
        this.put("Alg.Alias.Cipher.RSA/None/OAEPWithSHA-384AndMGF1Padding", "RSA/ECB/OAEPWithSHA-384AndMGF1Padding");
        this.putRSACipherImplClass("RSA/ECB/OAEPWithSHA-512AndMGF1Padding", "OpenSSLCipherRSA$OAEP$SHA512");
        this.put("Alg.Alias.Cipher.RSA/None/OAEPWithSHA-512AndMGF1Padding", "RSA/ECB/OAEPWithSHA-512AndMGF1Padding");
        this.putSymmetricCipherImplClass("AES/ECB/NoPadding", "OpenSSLCipher$EVP_CIPHER$AES$ECB$NoPadding");
        this.putSymmetricCipherImplClass("AES/ECB/PKCS5Padding", "OpenSSLCipher$EVP_CIPHER$AES$ECB$PKCS5Padding");
        this.put("Alg.Alias.Cipher.AES/ECB/PKCS7Padding", "AES/ECB/PKCS5Padding");
        this.putSymmetricCipherImplClass("AES/CBC/NoPadding", "OpenSSLCipher$EVP_CIPHER$AES$CBC$NoPadding");
        this.putSymmetricCipherImplClass("AES/CBC/PKCS5Padding", "OpenSSLCipher$EVP_CIPHER$AES$CBC$PKCS5Padding");
        this.put("Alg.Alias.Cipher.AES/CBC/PKCS7Padding", "AES/CBC/PKCS5Padding");
        this.putSymmetricCipherImplClass("AES/CTR/NoPadding", "OpenSSLCipher$EVP_CIPHER$AES$CTR");
        this.putSymmetricCipherImplClass("AES_128/ECB/NoPadding", "OpenSSLCipher$EVP_CIPHER$AES_128$ECB$NoPadding");
        this.putSymmetricCipherImplClass("AES_128/ECB/PKCS5Padding", "OpenSSLCipher$EVP_CIPHER$AES_128$ECB$PKCS5Padding");
        this.put("Alg.Alias.Cipher.AES_128/ECB/PKCS7Padding", "AES_128/ECB/PKCS5Padding");
        this.putSymmetricCipherImplClass("AES_128/CBC/NoPadding", "OpenSSLCipher$EVP_CIPHER$AES_128$CBC$NoPadding");
        this.putSymmetricCipherImplClass("AES_128/CBC/PKCS5Padding", "OpenSSLCipher$EVP_CIPHER$AES_128$CBC$PKCS5Padding");
        this.put("Alg.Alias.Cipher.AES_128/CBC/PKCS7Padding", "AES_128/CBC/PKCS5Padding");
        this.put("Alg.Alias.Cipher.PBEWithHmacSHA1AndAES_128", "AES_128/CBC/PKCS5PADDING");
        this.put("Alg.Alias.Cipher.PBEWithHmacSHA224AndAES_128", "AES_128/CBC/PKCS5PADDING");
        this.put("Alg.Alias.Cipher.PBEWithHmacSHA256AndAES_128", "AES_128/CBC/PKCS5PADDING");
        this.put("Alg.Alias.Cipher.PBEWithHmacSHA384AndAES_128", "AES_128/CBC/PKCS5PADDING");
        this.put("Alg.Alias.Cipher.PBEWithHmacSHA512AndAES_128", "AES_128/CBC/PKCS5PADDING");
        this.putSymmetricCipherImplClass("AES_256/ECB/NoPadding", "OpenSSLCipher$EVP_CIPHER$AES_256$ECB$NoPadding");
        this.putSymmetricCipherImplClass("AES_256/ECB/PKCS5Padding", "OpenSSLCipher$EVP_CIPHER$AES_256$ECB$PKCS5Padding");
        this.put("Alg.Alias.Cipher.AES_256/ECB/PKCS7Padding", "AES_256/ECB/PKCS5Padding");
        this.putSymmetricCipherImplClass("AES_256/CBC/NoPadding", "OpenSSLCipher$EVP_CIPHER$AES_256$CBC$NoPadding");
        this.putSymmetricCipherImplClass("AES_256/CBC/PKCS5Padding", "OpenSSLCipher$EVP_CIPHER$AES_256$CBC$PKCS5Padding");
        this.put("Alg.Alias.Cipher.AES_256/CBC/PKCS7Padding", "AES_256/CBC/PKCS5Padding");
        this.put("Alg.Alias.Cipher.PBEWithHmacSHA1AndAES_256", "AES_256/CBC/PKCS5PADDING");
        this.put("Alg.Alias.Cipher.PBEWithHmacSHA224AndAES_256", "AES_256/CBC/PKCS5PADDING");
        this.put("Alg.Alias.Cipher.PBEWithHmacSHA256AndAES_256", "AES_256/CBC/PKCS5PADDING");
        this.put("Alg.Alias.Cipher.PBEWithHmacSHA384AndAES_256", "AES_256/CBC/PKCS5PADDING");
        this.put("Alg.Alias.Cipher.PBEWithHmacSHA512AndAES_256", "AES_256/CBC/PKCS5PADDING");
        this.putSymmetricCipherImplClass("DESEDE/CBC/NoPadding", "OpenSSLCipher$EVP_CIPHER$DESEDE$CBC$NoPadding");
        this.putSymmetricCipherImplClass("DESEDE/CBC/PKCS5Padding", "OpenSSLCipher$EVP_CIPHER$DESEDE$CBC$PKCS5Padding");
        this.put("Alg.Alias.Cipher.DESEDE/CBC/PKCS7Padding", "DESEDE/CBC/PKCS5Padding");
        this.putSymmetricCipherImplClass("ARC4", "OpenSSLCipher$EVP_CIPHER$ARC4");
        this.put("Alg.Alias.Cipher.ARCFOUR", "ARC4");
        this.put("Alg.Alias.Cipher.RC4", "ARC4");
        this.put("Alg.Alias.Cipher.1.2.840.113549.3.4", "ARC4");
        this.put("Alg.Alias.Cipher.OID.1.2.840.113549.3.4", "ARC4");
        this.putSymmetricCipherImplClass("AES/GCM/NoPadding", "OpenSSLCipher$EVP_AEAD$AES$GCM");
        this.put("Alg.Alias.Cipher.GCM", "AES/GCM/NoPadding");
        this.put("Alg.Alias.Cipher.2.16.840.1.101.3.4.1.6", "AES/GCM/NoPadding");
        this.put("Alg.Alias.Cipher.2.16.840.1.101.3.4.1.26", "AES/GCM/NoPadding");
        this.put("Alg.Alias.Cipher.2.16.840.1.101.3.4.1.46", "AES/GCM/NoPadding");
        this.putSymmetricCipherImplClass("AES_128/GCM/NoPadding", "OpenSSLCipher$EVP_AEAD$AES$GCM$AES_128");
        this.putSymmetricCipherImplClass("AES_256/GCM/NoPadding", "OpenSSLCipher$EVP_AEAD$AES$GCM$AES_256");
        this.putSymmetricCipherImplClass("ChaCha20", "OpenSSLCipherChaCha20");
        this.putSymmetricCipherImplClass("ChaCha20/Poly1305/NoPadding", "OpenSSLCipher$EVP_AEAD$ChaCha20");
        this.put("Alg.Alias.Cipher.ChaCha20-Poly1305", "ChaCha20/Poly1305/NoPadding");
        this.putMacImplClass("HmacMD5", "OpenSSLMac$HmacMD5");
        this.put("Alg.Alias.Mac.1.3.6.1.5.5.8.1.1", "HmacMD5");
        this.put("Alg.Alias.Mac.HMAC-MD5", "HmacMD5");
        this.put("Alg.Alias.Mac.HMAC/MD5", "HmacMD5");
        this.putMacImplClass("HmacSHA1", "OpenSSLMac$HmacSHA1");
        this.put("Alg.Alias.Mac.1.2.840.113549.2.7", "HmacSHA1");
        this.put("Alg.Alias.Mac.1.3.6.1.5.5.8.1.2", "HmacSHA1");
        this.put("Alg.Alias.Mac.HMAC-SHA1", "HmacSHA1");
        this.put("Alg.Alias.Mac.HMAC/SHA1", "HmacSHA1");
        this.putMacImplClass("HmacSHA224", "OpenSSLMac$HmacSHA224");
        this.put("Alg.Alias.Mac.1.2.840.113549.2.8", "HmacSHA224");
        this.put("Alg.Alias.Mac.HMAC-SHA224", "HmacSHA224");
        this.put("Alg.Alias.Mac.HMAC/SHA224", "HmacSHA224");
        this.put("Alg.Alias.Mac.PBEWITHHMACSHA224", "HmacSHA224");
        this.putMacImplClass("HmacSHA256", "OpenSSLMac$HmacSHA256");
        this.put("Alg.Alias.Mac.1.2.840.113549.2.9", "HmacSHA256");
        this.put("Alg.Alias.Mac.2.16.840.1.101.3.4.2.1", "HmacSHA256");
        this.put("Alg.Alias.Mac.HMAC-SHA256", "HmacSHA256");
        this.put("Alg.Alias.Mac.HMAC/SHA256", "HmacSHA256");
        this.put("Alg.Alias.Mac.PBEWITHHMACSHA256", "HmacSHA256");
        this.putMacImplClass("HmacSHA384", "OpenSSLMac$HmacSHA384");
        this.put("Alg.Alias.Mac.1.2.840.113549.2.10", "HmacSHA384");
        this.put("Alg.Alias.Mac.HMAC-SHA384", "HmacSHA384");
        this.put("Alg.Alias.Mac.HMAC/SHA384", "HmacSHA384");
        this.put("Alg.Alias.Mac.PBEWITHHMACSHA384", "HmacSHA384");
        this.putMacImplClass("HmacSHA512", "OpenSSLMac$HmacSHA512");
        this.put("Alg.Alias.Mac.1.2.840.113549.2.11", "HmacSHA512");
        this.put("Alg.Alias.Mac.HMAC-SHA512", "HmacSHA512");
        this.put("Alg.Alias.Mac.HMAC/SHA512", "HmacSHA512");
        this.put("Alg.Alias.Mac.PBEWITHHMACSHA512", "HmacSHA512");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append("OpenSSLX509CertificateFactory");
        this.put("CertificateFactory.X509", ((StringBuilder)charSequence).toString());
        this.put("Alg.Alias.CertificateFactory.X.509", "X509");
    }

    private void putECDHKeyAgreementImplClass(String string) {
        CharSequence charSequence = new StringBuilder();
        charSequence.append(PREFIX);
        charSequence.append("OpenSSLKeyHolder|");
        charSequence.append(STANDARD_EC_PRIVATE_KEY_INTERFACE_CLASS_NAME);
        charSequence = charSequence.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PREFIX);
        stringBuilder.append(string);
        this.putImplClassWithKeyConstraints("KeyAgreement.ECDH", stringBuilder.toString(), (String)charSequence, "PKCS#8");
    }

    private void putImplClassWithKeyConstraints(String string, String charSequence, String string2, String string3) {
        this.put(string, charSequence);
        if (string2 != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(" SupportedKeyClasses");
            this.put(((StringBuilder)charSequence).toString(), string2);
        }
        if (string3 != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(" SupportedKeyFormats");
            this.put(((StringBuilder)charSequence).toString(), string3);
        }
    }

    private void putMacImplClass(String charSequence, String string) {
        CharSequence charSequence2 = new StringBuilder();
        charSequence2.append(PREFIX);
        charSequence2.append("OpenSSLKeyHolder");
        charSequence2 = charSequence2.toString();
        CharSequence charSequence3 = new StringBuilder();
        charSequence3.append("Mac.");
        charSequence3.append((String)charSequence);
        charSequence3 = charSequence3.toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append(string);
        this.putImplClassWithKeyConstraints((String)charSequence3, ((StringBuilder)charSequence).toString(), (String)charSequence2, "RAW");
    }

    private void putRAWRSASignatureImplClass(String string) {
        CharSequence charSequence = new StringBuilder();
        charSequence.append(PREFIX);
        charSequence.append("OpenSSLRSAPrivateKey|");
        charSequence.append(STANDARD_RSA_PRIVATE_KEY_INTERFACE_CLASS_NAME);
        charSequence.append("|");
        charSequence.append(PREFIX);
        charSequence.append("OpenSSLRSAPublicKey|");
        charSequence.append(STANDARD_RSA_PUBLIC_KEY_INTERFACE_CLASS_NAME);
        charSequence = charSequence.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PREFIX);
        stringBuilder.append(string);
        this.putImplClassWithKeyConstraints("Signature.NONEwithRSA", stringBuilder.toString(), (String)charSequence, null);
    }

    private void putRSACipherImplClass(String charSequence, String string) {
        CharSequence charSequence2 = new StringBuilder();
        charSequence2.append(PREFIX);
        charSequence2.append("OpenSSLRSAPrivateKey|");
        charSequence2.append(STANDARD_RSA_PRIVATE_KEY_INTERFACE_CLASS_NAME);
        charSequence2.append("|");
        charSequence2.append(PREFIX);
        charSequence2.append("OpenSSLRSAPublicKey|");
        charSequence2.append(STANDARD_RSA_PUBLIC_KEY_INTERFACE_CLASS_NAME);
        charSequence2 = charSequence2.toString();
        CharSequence charSequence3 = new StringBuilder();
        charSequence3.append("Cipher.");
        charSequence3.append((String)charSequence);
        charSequence3 = charSequence3.toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append(string);
        this.putImplClassWithKeyConstraints((String)charSequence3, ((StringBuilder)charSequence).toString(), (String)charSequence2, null);
    }

    private void putSignatureImplClass(String charSequence, String string) {
        CharSequence charSequence2 = new StringBuilder();
        charSequence2.append(PREFIX);
        charSequence2.append("OpenSSLKeyHolder|");
        charSequence2.append(STANDARD_RSA_PRIVATE_KEY_INTERFACE_CLASS_NAME);
        charSequence2.append("|");
        charSequence2.append(STANDARD_EC_PRIVATE_KEY_INTERFACE_CLASS_NAME);
        charSequence2.append("|");
        charSequence2.append(STANDARD_RSA_PUBLIC_KEY_INTERFACE_CLASS_NAME);
        charSequence2 = charSequence2.toString();
        CharSequence charSequence3 = new StringBuilder();
        charSequence3.append("Signature.");
        charSequence3.append((String)charSequence);
        charSequence3 = charSequence3.toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append(string);
        this.putImplClassWithKeyConstraints((String)charSequence3, ((StringBuilder)charSequence).toString(), (String)charSequence2, "PKCS#8|X.509");
    }

    private void putSymmetricCipherImplClass(String charSequence, String string) {
        CharSequence charSequence2 = new StringBuilder();
        charSequence2.append("Cipher.");
        charSequence2.append((String)charSequence);
        charSequence2 = charSequence2.toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(PREFIX);
        ((StringBuilder)charSequence).append(string);
        this.putImplClassWithKeyConstraints((String)charSequence2, ((StringBuilder)charSequence).toString(), null, "RAW");
    }
}

