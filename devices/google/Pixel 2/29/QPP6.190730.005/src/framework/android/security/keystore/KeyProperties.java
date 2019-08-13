/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.security.keystore;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import libcore.util.EmptyArray;

public abstract class KeyProperties {
    public static final String BLOCK_MODE_CBC = "CBC";
    public static final String BLOCK_MODE_CTR = "CTR";
    public static final String BLOCK_MODE_ECB = "ECB";
    public static final String BLOCK_MODE_GCM = "GCM";
    public static final String DIGEST_MD5 = "MD5";
    public static final String DIGEST_NONE = "NONE";
    public static final String DIGEST_SHA1 = "SHA-1";
    public static final String DIGEST_SHA224 = "SHA-224";
    public static final String DIGEST_SHA256 = "SHA-256";
    public static final String DIGEST_SHA384 = "SHA-384";
    public static final String DIGEST_SHA512 = "SHA-512";
    public static final String ENCRYPTION_PADDING_NONE = "NoPadding";
    public static final String ENCRYPTION_PADDING_PKCS7 = "PKCS7Padding";
    public static final String ENCRYPTION_PADDING_RSA_OAEP = "OAEPPadding";
    public static final String ENCRYPTION_PADDING_RSA_PKCS1 = "PKCS1Padding";
    @Deprecated
    public static final String KEY_ALGORITHM_3DES = "DESede";
    public static final String KEY_ALGORITHM_AES = "AES";
    public static final String KEY_ALGORITHM_EC = "EC";
    public static final String KEY_ALGORITHM_HMAC_SHA1 = "HmacSHA1";
    public static final String KEY_ALGORITHM_HMAC_SHA224 = "HmacSHA224";
    public static final String KEY_ALGORITHM_HMAC_SHA256 = "HmacSHA256";
    public static final String KEY_ALGORITHM_HMAC_SHA384 = "HmacSHA384";
    public static final String KEY_ALGORITHM_HMAC_SHA512 = "HmacSHA512";
    public static final String KEY_ALGORITHM_RSA = "RSA";
    public static final int ORIGIN_GENERATED = 1;
    public static final int ORIGIN_IMPORTED = 2;
    public static final int ORIGIN_SECURELY_IMPORTED = 8;
    public static final int ORIGIN_UNKNOWN = 4;
    public static final int PURPOSE_DECRYPT = 2;
    public static final int PURPOSE_ENCRYPT = 1;
    public static final int PURPOSE_SIGN = 4;
    public static final int PURPOSE_VERIFY = 8;
    public static final int PURPOSE_WRAP_KEY = 32;
    public static final String SIGNATURE_PADDING_RSA_PKCS1 = "PKCS1";
    public static final String SIGNATURE_PADDING_RSA_PSS = "PSS";

    private KeyProperties() {
    }

    private static int getSetBitCount(int n) {
        if (n == 0) {
            return 0;
        }
        int n2 = 0;
        while (n != 0) {
            int n3 = n2;
            if ((n & 1) != 0) {
                n3 = n2 + 1;
            }
            n >>>= 1;
            n2 = n3;
        }
        return n2;
    }

    private static int[] getSetFlags(int n) {
        if (n == 0) {
            return EmptyArray.INT;
        }
        int[] arrn = new int[KeyProperties.getSetBitCount(n)];
        int n2 = 0;
        int n3 = 1;
        while (n != 0) {
            int n4 = n2;
            if ((n & 1) != 0) {
                arrn[n2] = n3;
                n4 = n2 + 1;
            }
            n >>>= 1;
            n3 <<= 1;
            n2 = n4;
        }
        return arrn;
    }

    public static abstract class BlockMode {
        private BlockMode() {
        }

        public static String[] allFromKeymaster(Collection<Integer> object) {
            if (object != null && !object.isEmpty()) {
                String[] arrstring = new String[object.size()];
                int n = 0;
                object = object.iterator();
                while (object.hasNext()) {
                    arrstring[n] = BlockMode.fromKeymaster((Integer)object.next());
                    ++n;
                }
                return arrstring;
            }
            return EmptyArray.STRING;
        }

        public static int[] allToKeymaster(String[] arrstring) {
            if (arrstring != null && arrstring.length != 0) {
                int[] arrn = new int[arrstring.length];
                for (int i = 0; i < arrstring.length; ++i) {
                    arrn[i] = BlockMode.toKeymaster(arrstring[i]);
                }
                return arrn;
            }
            return EmptyArray.INT;
        }

        public static String fromKeymaster(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 32) {
                            return KeyProperties.BLOCK_MODE_GCM;
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unsupported block mode: ");
                        stringBuilder.append(n);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                    return KeyProperties.BLOCK_MODE_CTR;
                }
                return KeyProperties.BLOCK_MODE_CBC;
            }
            return KeyProperties.BLOCK_MODE_ECB;
        }

        public static int toKeymaster(String string2) {
            if (KeyProperties.BLOCK_MODE_ECB.equalsIgnoreCase(string2)) {
                return 1;
            }
            if (KeyProperties.BLOCK_MODE_CBC.equalsIgnoreCase(string2)) {
                return 2;
            }
            if (KeyProperties.BLOCK_MODE_CTR.equalsIgnoreCase(string2)) {
                return 3;
            }
            if (KeyProperties.BLOCK_MODE_GCM.equalsIgnoreCase(string2)) {
                return 32;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported block mode: ");
            stringBuilder.append(string2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface BlockModeEnum {
    }

    public static abstract class Digest {
        private Digest() {
        }

        public static String[] allFromKeymaster(Collection<Integer> object) {
            if (object.isEmpty()) {
                return EmptyArray.STRING;
            }
            String[] arrstring = new String[object.size()];
            int n = 0;
            object = object.iterator();
            while (object.hasNext()) {
                arrstring[n] = Digest.fromKeymaster((Integer)object.next());
                ++n;
            }
            return arrstring;
        }

        public static int[] allToKeymaster(String[] arrstring) {
            if (arrstring != null && arrstring.length != 0) {
                int[] arrn = new int[arrstring.length];
                int n = 0;
                int n2 = arrstring.length;
                for (int i = 0; i < n2; ++i) {
                    arrn[n] = Digest.toKeymaster(arrstring[i]);
                    ++n;
                }
                return arrn;
            }
            return EmptyArray.INT;
        }

        public static String fromKeymaster(int n) {
            switch (n) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported digest algorithm: ");
                    stringBuilder.append(n);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                case 6: {
                    return KeyProperties.DIGEST_SHA512;
                }
                case 5: {
                    return KeyProperties.DIGEST_SHA384;
                }
                case 4: {
                    return KeyProperties.DIGEST_SHA256;
                }
                case 3: {
                    return KeyProperties.DIGEST_SHA224;
                }
                case 2: {
                    return KeyProperties.DIGEST_SHA1;
                }
                case 1: {
                    return KeyProperties.DIGEST_MD5;
                }
                case 0: 
            }
            return KeyProperties.DIGEST_NONE;
        }

        public static String fromKeymasterToSignatureAlgorithmDigest(int n) {
            switch (n) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported digest algorithm: ");
                    stringBuilder.append(n);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                case 6: {
                    return "SHA512";
                }
                case 5: {
                    return "SHA384";
                }
                case 4: {
                    return "SHA256";
                }
                case 3: {
                    return "SHA224";
                }
                case 2: {
                    return "SHA1";
                }
                case 1: {
                    return KeyProperties.DIGEST_MD5;
                }
                case 0: 
            }
            return KeyProperties.DIGEST_NONE;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        public static int toKeymaster(String var0) {
            block18 : {
                var1_1 = var0.toUpperCase(Locale.US);
                switch (var1_1.hashCode()) {
                    case 78861104: {
                        if (!var1_1.equals("SHA-1")) break;
                        var2_2 = 0;
                        break block18;
                    }
                    case 2402104: {
                        if (!var1_1.equals("NONE")) break;
                        var2_2 = 5;
                        break block18;
                    }
                    case 76158: {
                        if (!var1_1.equals("MD5")) break;
                        var2_2 = 6;
                        break block18;
                    }
                    case -1523884971: {
                        if (!var1_1.equals("SHA-512")) break;
                        var2_2 = 4;
                        break block18;
                    }
                    case -1523886674: {
                        if (!var1_1.equals("SHA-384")) break;
                        var2_2 = 3;
                        break block18;
                    }
                    case -1523887726: {
                        if (!var1_1.equals("SHA-256")) break;
                        var2_2 = 2;
                        break block18;
                    }
                    case -1523887821: {
                        if (!var1_1.equals("SHA-224")) break;
                        var2_2 = 1;
                        break block18;
                    }
                }
                ** break;
lbl32: // 1 sources:
                var2_2 = -1;
            }
            switch (var2_2) {
                default: {
                    var1_1 = new StringBuilder();
                    var1_1.append("Unsupported digest algorithm: ");
                    var1_1.append(var0);
                    throw new IllegalArgumentException(var1_1.toString());
                }
                case 6: {
                    return 1;
                }
                case 5: {
                    return 0;
                }
                case 4: {
                    return 6;
                }
                case 3: {
                    return 5;
                }
                case 2: {
                    return 4;
                }
                case 1: {
                    return 3;
                }
                case 0: 
            }
            return 2;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DigestEnum {
    }

    public static abstract class EncryptionPadding {
        private EncryptionPadding() {
        }

        public static int[] allToKeymaster(String[] arrstring) {
            if (arrstring != null && arrstring.length != 0) {
                int[] arrn = new int[arrstring.length];
                for (int i = 0; i < arrstring.length; ++i) {
                    arrn[i] = EncryptionPadding.toKeymaster(arrstring[i]);
                }
                return arrn;
            }
            return EmptyArray.INT;
        }

        public static String fromKeymaster(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 4) {
                        if (n == 64) {
                            return KeyProperties.ENCRYPTION_PADDING_PKCS7;
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unsupported encryption padding: ");
                        stringBuilder.append(n);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                    return KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1;
                }
                return KeyProperties.ENCRYPTION_PADDING_RSA_OAEP;
            }
            return KeyProperties.ENCRYPTION_PADDING_NONE;
        }

        public static int toKeymaster(String string2) {
            if (KeyProperties.ENCRYPTION_PADDING_NONE.equalsIgnoreCase(string2)) {
                return 1;
            }
            if (KeyProperties.ENCRYPTION_PADDING_PKCS7.equalsIgnoreCase(string2)) {
                return 64;
            }
            if (KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1.equalsIgnoreCase(string2)) {
                return 4;
            }
            if (KeyProperties.ENCRYPTION_PADDING_RSA_OAEP.equalsIgnoreCase(string2)) {
                return 2;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported encryption padding scheme: ");
            stringBuilder.append(string2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EncryptionPaddingEnum {
    }

    public static abstract class KeyAlgorithm {
        private KeyAlgorithm() {
        }

        public static String fromKeymasterAsymmetricKeyAlgorithm(int n) {
            if (n != 1) {
                if (n == 3) {
                    return KeyProperties.KEY_ALGORITHM_EC;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported key algorithm: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            return KeyProperties.KEY_ALGORITHM_RSA;
        }

        public static String fromKeymasterSecretKeyAlgorithm(int n, int n2) {
            if (n != 32) {
                if (n != 33) {
                    if (n == 128) {
                        if (n2 != 2) {
                            if (n2 != 3) {
                                if (n2 != 4) {
                                    if (n2 != 5) {
                                        if (n2 == 6) {
                                            return KeyProperties.KEY_ALGORITHM_HMAC_SHA512;
                                        }
                                        StringBuilder stringBuilder = new StringBuilder();
                                        stringBuilder.append("Unsupported HMAC digest: ");
                                        stringBuilder.append(Digest.fromKeymaster(n2));
                                        throw new IllegalArgumentException(stringBuilder.toString());
                                    }
                                    return KeyProperties.KEY_ALGORITHM_HMAC_SHA384;
                                }
                                return KeyProperties.KEY_ALGORITHM_HMAC_SHA256;
                            }
                            return KeyProperties.KEY_ALGORITHM_HMAC_SHA224;
                        }
                        return KeyProperties.KEY_ALGORITHM_HMAC_SHA1;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported key algorithm: ");
                    stringBuilder.append(n);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                return KeyProperties.KEY_ALGORITHM_3DES;
            }
            return KeyProperties.KEY_ALGORITHM_AES;
        }

        public static int toKeymasterAsymmetricKeyAlgorithm(String string2) {
            if (KeyProperties.KEY_ALGORITHM_EC.equalsIgnoreCase(string2)) {
                return 3;
            }
            if (KeyProperties.KEY_ALGORITHM_RSA.equalsIgnoreCase(string2)) {
                return 1;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported key algorithm: ");
            stringBuilder.append(string2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        public static int toKeymasterDigest(String var0) {
            block8 : {
                var0 = var0.toUpperCase(Locale.US);
                var1_1 = var0.startsWith("HMAC");
                var2_2 = -1;
                if (var1_1 == false) return -1;
                var3_3 = var0.substring("HMAC".length());
                switch (var3_3.hashCode()) {
                    case 2543909: {
                        if (!var3_3.equals("SHA1")) break;
                        var2_2 = 0;
                        ** break;
                    }
                    case -1850265334: {
                        if (!var3_3.equals("SHA512")) break;
                        var2_2 = 4;
                        ** break;
                    }
                    case -1850267037: {
                        if (!var3_3.equals("SHA384")) break;
                        var2_2 = 3;
                        ** break;
                    }
                    case -1850268089: {
                        if (!var3_3.equals("SHA256")) break;
                        var2_2 = 2;
                        ** break;
                    }
                    case -1850268184: {
                        if (!var3_3.equals("SHA224")) break;
                        var2_2 = 1;
                        break block8;
                    }
                }
                ** break;
            }
            if (var2_2 == 0) return 2;
            if (var2_2 == 1) return 3;
            if (var2_2 == 2) return 4;
            if (var2_2 == 3) return 5;
            if (var2_2 == 4) {
                return 6;
            }
            var0 = new StringBuilder();
            var0.append("Unsupported HMAC digest: ");
            var0.append(var3_3);
            throw new IllegalArgumentException(var0.toString());
        }

        public static int toKeymasterSecretKeyAlgorithm(String string2) {
            if (KeyProperties.KEY_ALGORITHM_AES.equalsIgnoreCase(string2)) {
                return 32;
            }
            if (KeyProperties.KEY_ALGORITHM_3DES.equalsIgnoreCase(string2)) {
                return 33;
            }
            if (string2.toUpperCase(Locale.US).startsWith("HMAC")) {
                return 128;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported secret key algorithm: ");
            stringBuilder.append(string2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface KeyAlgorithmEnum {
    }

    public static abstract class Origin {
        private Origin() {
        }

        public static int fromKeymaster(int n) {
            if (n != 0) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 4) {
                            return 8;
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unknown origin: ");
                        stringBuilder.append(n);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                    return 4;
                }
                return 2;
            }
            return 1;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface OriginEnum {
    }

    public static abstract class Purpose {
        private Purpose() {
        }

        public static int allFromKeymaster(Collection<Integer> object) {
            int n = 0;
            object = object.iterator();
            while (object.hasNext()) {
                n |= Purpose.fromKeymaster((Integer)object.next());
            }
            return n;
        }

        public static int[] allToKeymaster(int n) {
            int[] arrn = KeyProperties.getSetFlags(n);
            for (n = 0; n < arrn.length; ++n) {
                arrn[n] = Purpose.toKeymaster(arrn[n]);
            }
            return arrn;
        }

        public static int fromKeymaster(int n) {
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            if (n == 5) {
                                return 32;
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unknown purpose: ");
                            stringBuilder.append(n);
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                        return 8;
                    }
                    return 4;
                }
                return 2;
            }
            return 1;
        }

        public static int toKeymaster(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 4) {
                        if (n != 8) {
                            if (n == 32) {
                                return 5;
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unknown purpose: ");
                            stringBuilder.append(n);
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                        return 3;
                    }
                    return 2;
                }
                return 1;
            }
            return 0;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PurposeEnum {
    }

    static abstract class SignaturePadding {
        private SignaturePadding() {
        }

        static int[] allToKeymaster(String[] arrstring) {
            if (arrstring != null && arrstring.length != 0) {
                int[] arrn = new int[arrstring.length];
                for (int i = 0; i < arrstring.length; ++i) {
                    arrn[i] = SignaturePadding.toKeymaster(arrstring[i]);
                }
                return arrn;
            }
            return EmptyArray.INT;
        }

        static String fromKeymaster(int n) {
            if (n != 3) {
                if (n == 5) {
                    return KeyProperties.SIGNATURE_PADDING_RSA_PKCS1;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported signature padding: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            return KeyProperties.SIGNATURE_PADDING_RSA_PSS;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        static int toKeymaster(String string2) {
            CharSequence charSequence = string2.toUpperCase(Locale.US);
            int n = ((String)charSequence).hashCode();
            if (n != 79536) {
                if (n == 76183014 && ((String)charSequence).equals(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)) {
                    return 5;
                }
            } else if (((String)charSequence).equals(KeyProperties.SIGNATURE_PADDING_RSA_PSS)) {
                return 3;
            }
            n = -1;
            if (n == 0) return 5;
            if (n == 1) {
                return 3;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Unsupported signature padding scheme: ");
            ((StringBuilder)charSequence).append(string2);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SignaturePaddingEnum {
    }

}

