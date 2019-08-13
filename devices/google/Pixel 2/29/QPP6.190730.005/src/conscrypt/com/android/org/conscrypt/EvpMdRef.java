/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

final class EvpMdRef {
    static final String MGF1_ALGORITHM_NAME = "MGF1";
    static final String MGF1_OID = "1.2.840.113549.1.1.8";

    private EvpMdRef() {
    }

    static int getDigestSizeBytesByJcaDigestAlgorithmStandardName(String string) throws NoSuchAlgorithmException {
        CharSequence charSequence = string.toUpperCase(Locale.US);
        if ("SHA-256".equals(charSequence)) {
            return SHA256.SIZE_BYTES;
        }
        if ("SHA-512".equals(charSequence)) {
            return SHA512.SIZE_BYTES;
        }
        if ("SHA-1".equals(charSequence)) {
            return SHA1.SIZE_BYTES;
        }
        if ("SHA-384".equals(charSequence)) {
            return SHA384.SIZE_BYTES;
        }
        if ("SHA-224".equals(charSequence)) {
            return SHA224.SIZE_BYTES;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unsupported algorithm: ");
        ((StringBuilder)charSequence).append(string);
        throw new NoSuchAlgorithmException(((StringBuilder)charSequence).toString());
    }

    static long getEVP_MDByJcaDigestAlgorithmStandardName(String string) throws NoSuchAlgorithmException {
        CharSequence charSequence = string.toUpperCase(Locale.US);
        if ("SHA-256".equals(charSequence)) {
            return SHA256.EVP_MD;
        }
        if ("SHA-512".equals(charSequence)) {
            return SHA512.EVP_MD;
        }
        if ("SHA-1".equals(charSequence)) {
            return SHA1.EVP_MD;
        }
        if ("SHA-384".equals(charSequence)) {
            return SHA384.EVP_MD;
        }
        if ("SHA-224".equals(charSequence)) {
            return SHA224.EVP_MD;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unsupported algorithm: ");
        ((StringBuilder)charSequence).append(string);
        throw new NoSuchAlgorithmException(((StringBuilder)charSequence).toString());
    }

    static String getJcaDigestAlgorithmStandardName(String string) {
        if (!"SHA-256".equals(string = string.toUpperCase(Locale.US)) && !"2.16.840.1.101.3.4.2.1".equals(string)) {
            if (!"SHA-512".equals(string) && !"2.16.840.1.101.3.4.2.3".equals(string)) {
                if (!"SHA-1".equals(string) && !"1.3.14.3.2.26".equals(string)) {
                    if (!"SHA-384".equals(string) && !"2.16.840.1.101.3.4.2.2".equals(string)) {
                        if (!"SHA-224".equals(string) && !"2.16.840.1.101.3.4.2.4".equals(string)) {
                            return null;
                        }
                        return "SHA-224";
                    }
                    return "SHA-384";
                }
                return "SHA-1";
            }
            return "SHA-512";
        }
        return "SHA-256";
    }

    static String getJcaDigestAlgorithmStandardNameFromEVP_MD(long l) {
        if (l == MD5.EVP_MD) {
            return "MD5";
        }
        if (l == SHA1.EVP_MD) {
            return "SHA-1";
        }
        if (l == SHA224.EVP_MD) {
            return "SHA-224";
        }
        if (l == SHA256.EVP_MD) {
            return "SHA-256";
        }
        if (l == SHA384.EVP_MD) {
            return "SHA-384";
        }
        if (l == SHA512.EVP_MD) {
            return "SHA-512";
        }
        throw new IllegalArgumentException("Unknown EVP_MD reference");
    }

    static final class MD5 {
        static final long EVP_MD = NativeCrypto.EVP_get_digestbyname("md5");
        static final String JCA_NAME = "MD5";
        static final String OID = "1.2.840.113549.2.5";
        static final int SIZE_BYTES = NativeCrypto.EVP_MD_size(EVP_MD);

        private MD5() {
        }
    }

    static final class SHA1 {
        static final long EVP_MD = NativeCrypto.EVP_get_digestbyname("sha1");
        static final String JCA_NAME = "SHA-1";
        static final String OID = "1.3.14.3.2.26";
        static final int SIZE_BYTES = NativeCrypto.EVP_MD_size(EVP_MD);

        private SHA1() {
        }
    }

    static final class SHA224 {
        static final long EVP_MD = NativeCrypto.EVP_get_digestbyname("sha224");
        static final String JCA_NAME = "SHA-224";
        static final String OID = "2.16.840.1.101.3.4.2.4";
        static final int SIZE_BYTES = NativeCrypto.EVP_MD_size(EVP_MD);

        private SHA224() {
        }
    }

    static final class SHA256 {
        static final long EVP_MD = NativeCrypto.EVP_get_digestbyname("sha256");
        static final String JCA_NAME = "SHA-256";
        static final String OID = "2.16.840.1.101.3.4.2.1";
        static final int SIZE_BYTES = NativeCrypto.EVP_MD_size(EVP_MD);

        private SHA256() {
        }
    }

    static final class SHA384 {
        static final long EVP_MD = NativeCrypto.EVP_get_digestbyname("sha384");
        static final String JCA_NAME = "SHA-384";
        static final String OID = "2.16.840.1.101.3.4.2.2";
        static final int SIZE_BYTES = NativeCrypto.EVP_MD_size(EVP_MD);

        private SHA384() {
        }
    }

    static final class SHA512 {
        static final long EVP_MD = NativeCrypto.EVP_get_digestbyname("sha512");
        static final String JCA_NAME = "SHA-512";
        static final String OID = "2.16.840.1.101.3.4.2.3";
        static final int SIZE_BYTES = NativeCrypto.EVP_MD_size(EVP_MD);

        private SHA512() {
        }
    }

}

