/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.os.SystemProperties;
import java.security.Provider;

class AndroidKeyStoreBCWorkaroundProvider
extends Provider {
    private static final String DESEDE_SYSTEM_PROPERTY = "ro.hardware.keystore_desede";
    private static final String KEYSTORE_PRIVATE_KEY_CLASS_NAME = "android.security.keystore.AndroidKeyStorePrivateKey";
    private static final String KEYSTORE_PUBLIC_KEY_CLASS_NAME = "android.security.keystore.AndroidKeyStorePublicKey";
    private static final String KEYSTORE_SECRET_KEY_CLASS_NAME = "android.security.keystore.AndroidKeyStoreSecretKey";
    private static final String PACKAGE_NAME = "android.security.keystore";

    AndroidKeyStoreBCWorkaroundProvider() {
        super("AndroidKeyStoreBCWorkaround", 1.0, "Android KeyStore security provider to work around Bouncy Castle");
        this.putMacImpl("HmacSHA1", "android.security.keystore.AndroidKeyStoreHmacSpi$HmacSHA1");
        this.put("Alg.Alias.Mac.1.2.840.113549.2.7", "HmacSHA1");
        this.put("Alg.Alias.Mac.HMAC-SHA1", "HmacSHA1");
        this.put("Alg.Alias.Mac.HMAC/SHA1", "HmacSHA1");
        this.putMacImpl("HmacSHA224", "android.security.keystore.AndroidKeyStoreHmacSpi$HmacSHA224");
        this.put("Alg.Alias.Mac.1.2.840.113549.2.9", "HmacSHA224");
        this.put("Alg.Alias.Mac.HMAC-SHA224", "HmacSHA224");
        this.put("Alg.Alias.Mac.HMAC/SHA224", "HmacSHA224");
        this.putMacImpl("HmacSHA256", "android.security.keystore.AndroidKeyStoreHmacSpi$HmacSHA256");
        this.put("Alg.Alias.Mac.1.2.840.113549.2.9", "HmacSHA256");
        this.put("Alg.Alias.Mac.HMAC-SHA256", "HmacSHA256");
        this.put("Alg.Alias.Mac.HMAC/SHA256", "HmacSHA256");
        this.putMacImpl("HmacSHA384", "android.security.keystore.AndroidKeyStoreHmacSpi$HmacSHA384");
        this.put("Alg.Alias.Mac.1.2.840.113549.2.10", "HmacSHA384");
        this.put("Alg.Alias.Mac.HMAC-SHA384", "HmacSHA384");
        this.put("Alg.Alias.Mac.HMAC/SHA384", "HmacSHA384");
        this.putMacImpl("HmacSHA512", "android.security.keystore.AndroidKeyStoreHmacSpi$HmacSHA512");
        this.put("Alg.Alias.Mac.1.2.840.113549.2.11", "HmacSHA512");
        this.put("Alg.Alias.Mac.HMAC-SHA512", "HmacSHA512");
        this.put("Alg.Alias.Mac.HMAC/SHA512", "HmacSHA512");
        this.putSymmetricCipherImpl("AES/ECB/NoPadding", "android.security.keystore.AndroidKeyStoreUnauthenticatedAESCipherSpi$ECB$NoPadding");
        this.putSymmetricCipherImpl("AES/ECB/PKCS7Padding", "android.security.keystore.AndroidKeyStoreUnauthenticatedAESCipherSpi$ECB$PKCS7Padding");
        this.putSymmetricCipherImpl("AES/CBC/NoPadding", "android.security.keystore.AndroidKeyStoreUnauthenticatedAESCipherSpi$CBC$NoPadding");
        this.putSymmetricCipherImpl("AES/CBC/PKCS7Padding", "android.security.keystore.AndroidKeyStoreUnauthenticatedAESCipherSpi$CBC$PKCS7Padding");
        this.putSymmetricCipherImpl("AES/CTR/NoPadding", "android.security.keystore.AndroidKeyStoreUnauthenticatedAESCipherSpi$CTR$NoPadding");
        if ("true".equals(SystemProperties.get(DESEDE_SYSTEM_PROPERTY))) {
            this.putSymmetricCipherImpl("DESede/CBC/NoPadding", "android.security.keystore.AndroidKeyStore3DESCipherSpi$CBC$NoPadding");
            this.putSymmetricCipherImpl("DESede/CBC/PKCS7Padding", "android.security.keystore.AndroidKeyStore3DESCipherSpi$CBC$PKCS7Padding");
            this.putSymmetricCipherImpl("DESede/ECB/NoPadding", "android.security.keystore.AndroidKeyStore3DESCipherSpi$ECB$NoPadding");
            this.putSymmetricCipherImpl("DESede/ECB/PKCS7Padding", "android.security.keystore.AndroidKeyStore3DESCipherSpi$ECB$PKCS7Padding");
        }
        this.putSymmetricCipherImpl("AES/GCM/NoPadding", "android.security.keystore.AndroidKeyStoreAuthenticatedAESCipherSpi$GCM$NoPadding");
        this.putAsymmetricCipherImpl("RSA/ECB/NoPadding", "android.security.keystore.AndroidKeyStoreRSACipherSpi$NoPadding");
        this.put("Alg.Alias.Cipher.RSA/None/NoPadding", "RSA/ECB/NoPadding");
        this.putAsymmetricCipherImpl("RSA/ECB/PKCS1Padding", "android.security.keystore.AndroidKeyStoreRSACipherSpi$PKCS1Padding");
        this.put("Alg.Alias.Cipher.RSA/None/PKCS1Padding", "RSA/ECB/PKCS1Padding");
        this.putAsymmetricCipherImpl("RSA/ECB/OAEPPadding", "android.security.keystore.AndroidKeyStoreRSACipherSpi$OAEPWithSHA1AndMGF1Padding");
        this.put("Alg.Alias.Cipher.RSA/None/OAEPPadding", "RSA/ECB/OAEPPadding");
        this.putAsymmetricCipherImpl("RSA/ECB/OAEPWithSHA-1AndMGF1Padding", "android.security.keystore.AndroidKeyStoreRSACipherSpi$OAEPWithSHA1AndMGF1Padding");
        this.put("Alg.Alias.Cipher.RSA/None/OAEPWithSHA-1AndMGF1Padding", "RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
        this.putAsymmetricCipherImpl("RSA/ECB/OAEPWithSHA-224AndMGF1Padding", "android.security.keystore.AndroidKeyStoreRSACipherSpi$OAEPWithSHA224AndMGF1Padding");
        this.put("Alg.Alias.Cipher.RSA/None/OAEPWithSHA-224AndMGF1Padding", "RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        this.putAsymmetricCipherImpl("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", "android.security.keystore.AndroidKeyStoreRSACipherSpi$OAEPWithSHA256AndMGF1Padding");
        this.put("Alg.Alias.Cipher.RSA/None/OAEPWithSHA-256AndMGF1Padding", "RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        this.putAsymmetricCipherImpl("RSA/ECB/OAEPWithSHA-384AndMGF1Padding", "android.security.keystore.AndroidKeyStoreRSACipherSpi$OAEPWithSHA384AndMGF1Padding");
        this.put("Alg.Alias.Cipher.RSA/None/OAEPWithSHA-384AndMGF1Padding", "RSA/ECB/OAEPWithSHA-384AndMGF1Padding");
        this.putAsymmetricCipherImpl("RSA/ECB/OAEPWithSHA-512AndMGF1Padding", "android.security.keystore.AndroidKeyStoreRSACipherSpi$OAEPWithSHA512AndMGF1Padding");
        this.put("Alg.Alias.Cipher.RSA/None/OAEPWithSHA-512AndMGF1Padding", "RSA/ECB/OAEPWithSHA-512AndMGF1Padding");
        this.putSignatureImpl("NONEwithRSA", "android.security.keystore.AndroidKeyStoreRSASignatureSpi$NONEWithPKCS1Padding");
        this.putSignatureImpl("MD5withRSA", "android.security.keystore.AndroidKeyStoreRSASignatureSpi$MD5WithPKCS1Padding");
        this.put("Alg.Alias.Signature.MD5WithRSAEncryption", "MD5withRSA");
        this.put("Alg.Alias.Signature.MD5/RSA", "MD5withRSA");
        this.put("Alg.Alias.Signature.1.2.840.113549.1.1.4", "MD5withRSA");
        this.put("Alg.Alias.Signature.1.2.840.113549.2.5with1.2.840.113549.1.1.1", "MD5withRSA");
        this.putSignatureImpl("SHA1withRSA", "android.security.keystore.AndroidKeyStoreRSASignatureSpi$SHA1WithPKCS1Padding");
        this.put("Alg.Alias.Signature.SHA1WithRSAEncryption", "SHA1withRSA");
        this.put("Alg.Alias.Signature.SHA1/RSA", "SHA1withRSA");
        this.put("Alg.Alias.Signature.SHA-1/RSA", "SHA1withRSA");
        this.put("Alg.Alias.Signature.1.2.840.113549.1.1.5", "SHA1withRSA");
        this.put("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.113549.1.1.1", "SHA1withRSA");
        this.put("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.113549.1.1.5", "SHA1withRSA");
        this.put("Alg.Alias.Signature.1.3.14.3.2.29", "SHA1withRSA");
        this.putSignatureImpl("SHA224withRSA", "android.security.keystore.AndroidKeyStoreRSASignatureSpi$SHA224WithPKCS1Padding");
        this.put("Alg.Alias.Signature.SHA224WithRSAEncryption", "SHA224withRSA");
        this.put("Alg.Alias.Signature.1.2.840.113549.1.1.11", "SHA224withRSA");
        this.put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.4with1.2.840.113549.1.1.1", "SHA224withRSA");
        this.put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.4with1.2.840.113549.1.1.11", "SHA224withRSA");
        this.putSignatureImpl("SHA256withRSA", "android.security.keystore.AndroidKeyStoreRSASignatureSpi$SHA256WithPKCS1Padding");
        this.put("Alg.Alias.Signature.SHA256WithRSAEncryption", "SHA256withRSA");
        this.put("Alg.Alias.Signature.1.2.840.113549.1.1.11", "SHA256withRSA");
        this.put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.1with1.2.840.113549.1.1.1", "SHA256withRSA");
        this.put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.1with1.2.840.113549.1.1.11", "SHA256withRSA");
        this.putSignatureImpl("SHA384withRSA", "android.security.keystore.AndroidKeyStoreRSASignatureSpi$SHA384WithPKCS1Padding");
        this.put("Alg.Alias.Signature.SHA384WithRSAEncryption", "SHA384withRSA");
        this.put("Alg.Alias.Signature.1.2.840.113549.1.1.12", "SHA384withRSA");
        this.put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.2with1.2.840.113549.1.1.1", "SHA384withRSA");
        this.putSignatureImpl("SHA512withRSA", "android.security.keystore.AndroidKeyStoreRSASignatureSpi$SHA512WithPKCS1Padding");
        this.put("Alg.Alias.Signature.SHA512WithRSAEncryption", "SHA512withRSA");
        this.put("Alg.Alias.Signature.1.2.840.113549.1.1.13", "SHA512withRSA");
        this.put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.3with1.2.840.113549.1.1.1", "SHA512withRSA");
        this.putSignatureImpl("SHA1withRSA/PSS", "android.security.keystore.AndroidKeyStoreRSASignatureSpi$SHA1WithPSSPadding");
        this.putSignatureImpl("SHA224withRSA/PSS", "android.security.keystore.AndroidKeyStoreRSASignatureSpi$SHA224WithPSSPadding");
        this.putSignatureImpl("SHA256withRSA/PSS", "android.security.keystore.AndroidKeyStoreRSASignatureSpi$SHA256WithPSSPadding");
        this.putSignatureImpl("SHA384withRSA/PSS", "android.security.keystore.AndroidKeyStoreRSASignatureSpi$SHA384WithPSSPadding");
        this.putSignatureImpl("SHA512withRSA/PSS", "android.security.keystore.AndroidKeyStoreRSASignatureSpi$SHA512WithPSSPadding");
        this.putSignatureImpl("NONEwithECDSA", "android.security.keystore.AndroidKeyStoreECDSASignatureSpi$NONE");
        this.putSignatureImpl("SHA1withECDSA", "android.security.keystore.AndroidKeyStoreECDSASignatureSpi$SHA1");
        this.put("Alg.Alias.Signature.ECDSA", "SHA1withECDSA");
        this.put("Alg.Alias.Signature.ECDSAwithSHA1", "SHA1withECDSA");
        this.put("Alg.Alias.Signature.1.2.840.10045.4.1", "SHA1withECDSA");
        this.put("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.10045.2.1", "SHA1withECDSA");
        this.putSignatureImpl("SHA224withECDSA", "android.security.keystore.AndroidKeyStoreECDSASignatureSpi$SHA224");
        this.put("Alg.Alias.Signature.1.2.840.10045.4.3.1", "SHA224withECDSA");
        this.put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.4with1.2.840.10045.2.1", "SHA224withECDSA");
        this.putSignatureImpl("SHA256withECDSA", "android.security.keystore.AndroidKeyStoreECDSASignatureSpi$SHA256");
        this.put("Alg.Alias.Signature.1.2.840.10045.4.3.2", "SHA256withECDSA");
        this.put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.1with1.2.840.10045.2.1", "SHA256withECDSA");
        this.putSignatureImpl("SHA384withECDSA", "android.security.keystore.AndroidKeyStoreECDSASignatureSpi$SHA384");
        this.put("Alg.Alias.Signature.1.2.840.10045.4.3.3", "SHA384withECDSA");
        this.put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.2with1.2.840.10045.2.1", "SHA384withECDSA");
        this.putSignatureImpl("SHA512withECDSA", "android.security.keystore.AndroidKeyStoreECDSASignatureSpi$SHA512");
        this.put("Alg.Alias.Signature.1.2.840.10045.4.3.4", "SHA512withECDSA");
        this.put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.3with1.2.840.10045.2.1", "SHA512withECDSA");
    }

    public static String[] getSupportedEcdsaSignatureDigests() {
        return new String[]{"NONE", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512"};
    }

    public static String[] getSupportedRsaSignatureWithPkcs1PaddingDigests() {
        return new String[]{"NONE", "MD5", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512"};
    }

    private void putAsymmetricCipherImpl(String string2, String charSequence) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cipher.");
        stringBuilder.append(string2);
        this.put(stringBuilder.toString(), charSequence);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Cipher.");
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(" SupportedKeyClasses");
        this.put(((StringBuilder)charSequence).toString(), "android.security.keystore.AndroidKeyStorePrivateKey|android.security.keystore.AndroidKeyStorePublicKey");
    }

    private void putMacImpl(String string2, String charSequence) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Mac.");
        stringBuilder.append(string2);
        this.put(stringBuilder.toString(), charSequence);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Mac.");
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(" SupportedKeyClasses");
        this.put(((StringBuilder)charSequence).toString(), KEYSTORE_SECRET_KEY_CLASS_NAME);
    }

    private void putSignatureImpl(String string2, String charSequence) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Signature.");
        stringBuilder.append(string2);
        this.put(stringBuilder.toString(), charSequence);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Signature.");
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(" SupportedKeyClasses");
        this.put(((StringBuilder)charSequence).toString(), "android.security.keystore.AndroidKeyStorePrivateKey|android.security.keystore.AndroidKeyStorePublicKey");
    }

    private void putSymmetricCipherImpl(String string2, String charSequence) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cipher.");
        stringBuilder.append(string2);
        this.put(stringBuilder.toString(), charSequence);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Cipher.");
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(" SupportedKeyClasses");
        this.put(((StringBuilder)charSequence).toString(), KEYSTORE_SECRET_KEY_CLASS_NAME);
    }
}

