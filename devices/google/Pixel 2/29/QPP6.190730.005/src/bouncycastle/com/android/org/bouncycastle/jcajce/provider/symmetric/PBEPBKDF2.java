/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.symmetric;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.PBE;
import com.android.org.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import com.android.org.bouncycastle.jcajce.spec.PBKDF2KeySpec;
import com.android.org.bouncycastle.util.Integers;
import java.io.Serializable;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;

public class PBEPBKDF2 {
    private static final Map prfCodes = new HashMap();

    static {
        prfCodes.put(PKCSObjectIdentifiers.id_hmacWithSHA1, Integers.valueOf(1));
        prfCodes.put(PKCSObjectIdentifiers.id_hmacWithSHA256, Integers.valueOf(4));
        prfCodes.put(PKCSObjectIdentifiers.id_hmacWithSHA224, Integers.valueOf(7));
        prfCodes.put(PKCSObjectIdentifiers.id_hmacWithSHA384, Integers.valueOf(8));
        prfCodes.put(PKCSObjectIdentifiers.id_hmacWithSHA512, Integers.valueOf(9));
    }

    private PBEPBKDF2() {
    }

    public static class BasePBKDF2
    extends BaseSecretKeyFactory {
        private int defaultDigest;
        private int ivSizeInBits;
        private int keySizeInBits;
        private int scheme;

        public BasePBKDF2(String string, int n) {
            this(string, n, 1);
        }

        private BasePBKDF2(String string, int n, int n2) {
            this(string, n, n2, 0, 0);
        }

        private BasePBKDF2(String string, int n, int n2, int n3, int n4) {
            super(string, PKCSObjectIdentifiers.id_PBKDF2);
            this.scheme = n;
            this.keySizeInBits = n3;
            this.ivSizeInBits = n4;
            this.defaultDigest = n2;
        }

        private int getDigestCode(ASN1ObjectIdentifier aSN1ObjectIdentifier) throws InvalidKeySpecException {
            Serializable serializable = (Integer)prfCodes.get(aSN1ObjectIdentifier);
            if (serializable != null) {
                return (Integer)serializable;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Invalid KeySpec: unknown PRF algorithm ");
            ((StringBuilder)serializable).append(aSN1ObjectIdentifier);
            throw new InvalidKeySpecException(((StringBuilder)serializable).toString());
        }

        @Override
        protected SecretKey engineGenerateSecret(KeySpec keySpec) throws InvalidKeySpecException {
            if (keySpec instanceof PBEKeySpec) {
                if (((PBEKeySpec)(keySpec = (PBEKeySpec)keySpec)).getSalt() == null && ((PBEKeySpec)keySpec).getIterationCount() == 0 && ((PBEKeySpec)keySpec).getKeyLength() == 0 && ((PBEKeySpec)keySpec).getPassword().length > 0 && this.keySizeInBits != 0) {
                    return new BCPBEKey(this.algName, this.algOid, this.scheme, this.defaultDigest, this.keySizeInBits, this.ivSizeInBits, (PBEKeySpec)keySpec, null);
                }
                if (((PBEKeySpec)keySpec).getSalt() != null) {
                    if (((PBEKeySpec)keySpec).getIterationCount() > 0) {
                        if (((PBEKeySpec)keySpec).getKeyLength() > 0) {
                            if (((PBEKeySpec)keySpec).getPassword().length != 0) {
                                if (keySpec instanceof PBKDF2KeySpec) {
                                    int n = this.getDigestCode(((PBKDF2KeySpec)keySpec).getPrf().getAlgorithm());
                                    int n2 = ((PBEKeySpec)keySpec).getKeyLength();
                                    CipherParameters cipherParameters = PBE.Util.makePBEMacParameters((PBEKeySpec)keySpec, this.scheme, n, n2);
                                    return new BCPBEKey(this.algName, this.algOid, this.scheme, n, n2, -1, (PBEKeySpec)keySpec, cipherParameters);
                                }
                                int n = this.defaultDigest;
                                int n3 = ((PBEKeySpec)keySpec).getKeyLength();
                                CipherParameters cipherParameters = PBE.Util.makePBEMacParameters((PBEKeySpec)keySpec, this.scheme, n, n3);
                                return new BCPBEKey(this.algName, this.algOid, this.scheme, n, n3, -1, (PBEKeySpec)keySpec, cipherParameters);
                            }
                            throw new IllegalArgumentException("password empty");
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("positive key length required: ");
                        stringBuilder.append(((PBEKeySpec)keySpec).getKeyLength());
                        throw new InvalidKeySpecException(stringBuilder.toString());
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("positive iteration count required: ");
                    stringBuilder.append(((PBEKeySpec)keySpec).getIterationCount());
                    throw new InvalidKeySpecException(stringBuilder.toString());
                }
                throw new InvalidKeySpecException("missing required salt");
            }
            throw new InvalidKeySpecException("Invalid KeySpec");
        }
    }

    public static class BasePBKDF2WithHmacSHA1
    extends BasePBKDF2 {
        public BasePBKDF2WithHmacSHA1(String string, int n) {
            super(string, n, 1);
        }
    }

    public static class BasePBKDF2WithHmacSHA224
    extends BasePBKDF2 {
        public BasePBKDF2WithHmacSHA224(String string, int n) {
            super(string, n, 7);
        }
    }

    public static class BasePBKDF2WithHmacSHA256
    extends BasePBKDF2 {
        public BasePBKDF2WithHmacSHA256(String string, int n) {
            super(string, n, 4);
        }
    }

    public static class BasePBKDF2WithHmacSHA384
    extends BasePBKDF2 {
        public BasePBKDF2WithHmacSHA384(String string, int n) {
            super(string, n, 8);
        }
    }

    public static class BasePBKDF2WithHmacSHA512
    extends BasePBKDF2 {
        public BasePBKDF2WithHmacSHA512(String string, int n) {
            super(string, n, 9);
        }
    }

    public static class Mappings
    extends AlgorithmProvider {
        private static final String PREFIX = PBEPBKDF2.class.getName();

        @Override
        public void configure(ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2WithHmacSHA1AndUTF8", "PBKDF2WithHmacSHA1");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2with8BIT", "PBKDF2WithHmacSHA1And8BIT");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2withASCII", "PBKDF2WithHmacSHA1And8BIT");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBKDF2WithHmacSHA1UTF8");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBKDF2WithHmacSHA1", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBKDF2WithHmacSHA224UTF8");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBKDF2WithHmacSHA224", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBKDF2WithHmacSHA256UTF8");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBKDF2WithHmacSHA256", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBKDF2WithHmacSHA384UTF8");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBKDF2WithHmacSHA384", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBKDF2WithHmacSHA512UTF8");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBKDF2WithHmacSHA512", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithHmacSHA1AndAES_128");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA1AndAES_128", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithHmacSHA224AndAES_128");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA224AndAES_128", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithHmacSHA256AndAES_128");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA256AndAES_128", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithHmacSHA384AndAES_128");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA384AndAES_128", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithHmacSHA512AndAES_128");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA512AndAES_128", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithHmacSHA1AndAES_256");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA1AndAES_256", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithHmacSHA224AndAES_256");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA224AndAES_256", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithHmacSHA256AndAES_256");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA256AndAES_256", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithHmacSHA384AndAES_256");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA384AndAES_256", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithHmacSHA512AndAES_256");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA512AndAES_256", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBKDF2WithHmacSHA18BIT");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBKDF2WithHmacSHA1And8BIT", stringBuilder.toString());
        }
    }

    public static class PBEWithHmacSHA1AndAES_128
    extends BasePBKDF2 {
        public PBEWithHmacSHA1AndAES_128() {
            super("PBEWithHmacSHA1AndAES_128", 5, 1, 128, 128);
        }
    }

    public static class PBEWithHmacSHA1AndAES_256
    extends BasePBKDF2 {
        public PBEWithHmacSHA1AndAES_256() {
            super("PBEWithHmacSHA1AndAES_256", 5, 1, 256, 128);
        }
    }

    public static class PBEWithHmacSHA224AndAES_128
    extends BasePBKDF2 {
        public PBEWithHmacSHA224AndAES_128() {
            super("PBEWithHmacSHA224AndAES_128", 5, 7, 128, 128);
        }
    }

    public static class PBEWithHmacSHA224AndAES_256
    extends BasePBKDF2 {
        public PBEWithHmacSHA224AndAES_256() {
            super("PBEWithHmacSHA224AndAES_256", 5, 7, 256, 128);
        }
    }

    public static class PBEWithHmacSHA256AndAES_128
    extends BasePBKDF2 {
        public PBEWithHmacSHA256AndAES_128() {
            super("PBEWithHmacSHA256AndAES_128", 5, 4, 128, 128);
        }
    }

    public static class PBEWithHmacSHA256AndAES_256
    extends BasePBKDF2 {
        public PBEWithHmacSHA256AndAES_256() {
            super("PBEWithHmacSHA256AndAES_256", 5, 4, 256, 128);
        }
    }

    public static class PBEWithHmacSHA384AndAES_128
    extends BasePBKDF2 {
        public PBEWithHmacSHA384AndAES_128() {
            super("PBEWithHmacSHA384AndAES_128", 5, 8, 128, 128);
        }
    }

    public static class PBEWithHmacSHA384AndAES_256
    extends BasePBKDF2 {
        public PBEWithHmacSHA384AndAES_256() {
            super("PBEWithHmacSHA384AndAES_256", 5, 8, 256, 128);
        }
    }

    public static class PBEWithHmacSHA512AndAES_128
    extends BasePBKDF2 {
        public PBEWithHmacSHA512AndAES_128() {
            super("PBEWithHmacSHA512AndAES_128", 5, 9, 128, 128);
        }
    }

    public static class PBEWithHmacSHA512AndAES_256
    extends BasePBKDF2 {
        public PBEWithHmacSHA512AndAES_256() {
            super("PBEWithHmacSHA512AndAES_256", 5, 9, 256, 128);
        }
    }

    public static class PBKDF2WithHmacSHA18BIT
    extends BasePBKDF2WithHmacSHA1 {
        public PBKDF2WithHmacSHA18BIT() {
            super("PBKDF2WithHmacSHA1And8bit", 1);
        }
    }

    public static class PBKDF2WithHmacSHA1UTF8
    extends BasePBKDF2WithHmacSHA1 {
        public PBKDF2WithHmacSHA1UTF8() {
            super("PBKDF2WithHmacSHA1", 5);
        }
    }

    public static class PBKDF2WithHmacSHA224UTF8
    extends BasePBKDF2WithHmacSHA224 {
        public PBKDF2WithHmacSHA224UTF8() {
            super("PBKDF2WithHmacSHA224", 5);
        }
    }

    public static class PBKDF2WithHmacSHA256UTF8
    extends BasePBKDF2WithHmacSHA256 {
        public PBKDF2WithHmacSHA256UTF8() {
            super("PBKDF2WithHmacSHA256", 5);
        }
    }

    public static class PBKDF2WithHmacSHA384UTF8
    extends BasePBKDF2WithHmacSHA384 {
        public PBKDF2WithHmacSHA384UTF8() {
            super("PBKDF2WithHmacSHA384", 5);
        }
    }

    public static class PBKDF2WithHmacSHA512UTF8
    extends BasePBKDF2WithHmacSHA512 {
        public PBKDF2WithHmacSHA512UTF8() {
            super("PBKDF2WithHmacSHA512", 5);
        }
    }

}

