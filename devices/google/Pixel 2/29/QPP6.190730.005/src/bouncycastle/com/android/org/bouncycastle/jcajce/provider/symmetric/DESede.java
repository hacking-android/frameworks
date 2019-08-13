/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.symmetric;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.CipherKeyGenerator;
import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.KeyGenerationParameters;
import com.android.org.bouncycastle.crypto.Mac;
import com.android.org.bouncycastle.crypto.Wrapper;
import com.android.org.bouncycastle.crypto.engines.DESedeEngine;
import com.android.org.bouncycastle.crypto.engines.DESedeWrapEngine;
import com.android.org.bouncycastle.crypto.generators.DESedeKeyGenerator;
import com.android.org.bouncycastle.crypto.macs.CBCBlockCipherMac;
import com.android.org.bouncycastle.crypto.modes.CBCBlockCipher;
import com.android.org.bouncycastle.crypto.paddings.BlockCipherPadding;
import com.android.org.bouncycastle.crypto.paddings.ISO7816d4Padding;
import com.android.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.org.bouncycastle.jcajce.provider.symmetric.DES;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import com.android.org.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

public final class DESede {
    private DESede() {
    }

    public static class CBC
    extends BaseBlockCipher {
        public CBC() {
            super(new CBCBlockCipher(new DESedeEngine()), 64);
        }
    }

    public static class CBCMAC
    extends BaseMac {
        public CBCMAC() {
            super(new CBCBlockCipherMac(new DESedeEngine()));
        }
    }

    public static class DESede64
    extends BaseMac {
        public DESede64() {
            super(new CBCBlockCipherMac((BlockCipher)new DESedeEngine(), 64));
        }
    }

    public static class DESede64with7816d4
    extends BaseMac {
        public DESede64with7816d4() {
            super(new CBCBlockCipherMac(new DESedeEngine(), 64, new ISO7816d4Padding()));
        }
    }

    public static class ECB
    extends BaseBlockCipher {
        public ECB() {
            super(new DESedeEngine());
        }
    }

    public static class KeyFactory
    extends BaseSecretKeyFactory {
        public KeyFactory() {
            super("DESede", null);
        }

        @Override
        protected SecretKey engineGenerateSecret(KeySpec keySpec) throws InvalidKeySpecException {
            if (keySpec instanceof DESedeKeySpec) {
                return new SecretKeySpec(((DESedeKeySpec)keySpec).getKey(), "DESede");
            }
            return super.engineGenerateSecret(keySpec);
        }

        @Override
        protected KeySpec engineGetKeySpec(SecretKey object, Class arrby) throws InvalidKeySpecException {
            if (arrby != null) {
                if (object != null) {
                    if (SecretKeySpec.class.isAssignableFrom((Class<?>)arrby)) {
                        return new SecretKeySpec(object.getEncoded(), this.algName);
                    }
                    if (DESedeKeySpec.class.isAssignableFrom((Class<?>)arrby)) {
                        arrby = object.getEncoded();
                        try {
                            if (arrby.length == 16) {
                                object = new byte[24];
                                System.arraycopy((byte[])arrby, (int)0, (byte[])object, (int)0, (int)16);
                                System.arraycopy((byte[])arrby, (int)0, (byte[])object, (int)16, (int)8);
                                return new DESedeKeySpec((byte[])object);
                            }
                            object = new DESedeKeySpec(arrby);
                            return object;
                        }
                        catch (Exception exception) {
                            throw new InvalidKeySpecException(exception.toString());
                        }
                    }
                    throw new InvalidKeySpecException("Invalid KeySpec");
                }
                throw new InvalidKeySpecException("key parameter is null");
            }
            throw new InvalidKeySpecException("keySpec parameter is null");
        }
    }

    public static class KeyGenerator
    extends BaseKeyGenerator {
        private boolean keySizeSet = false;

        public KeyGenerator() {
            super("DESede", 192, new DESedeKeyGenerator());
        }

        @Override
        protected SecretKey engineGenerateKey() {
            if (this.uninitialised) {
                this.engine.init(new KeyGenerationParameters(CryptoServicesRegistrar.getSecureRandom(), this.defaultKeySize));
                this.uninitialised = false;
            }
            if (!this.keySizeSet) {
                byte[] arrby = this.engine.generateKey();
                System.arraycopy((byte[])arrby, (int)0, (byte[])arrby, (int)16, (int)8);
                return new SecretKeySpec(arrby, this.algName);
            }
            return new SecretKeySpec(this.engine.generateKey(), this.algName);
        }

        @Override
        protected void engineInit(int n, SecureRandom secureRandom) {
            super.engineInit(n, secureRandom);
            this.keySizeSet = true;
        }
    }

    public static class KeyGenerator3
    extends BaseKeyGenerator {
        public KeyGenerator3() {
            super("DESede3", 192, new DESedeKeyGenerator());
        }
    }

    public static class Mappings
    extends AlgorithmProvider {
        private static final String PACKAGE = "com.android.org.bouncycastle.jcajce.provider.symmetric";
        private static final String PREFIX = DESede.class.getName();

        @Override
        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$ECB");
            configurableProvider.addAlgorithm("Cipher.DESEDE", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$Wrap");
            configurableProvider.addAlgorithm("Cipher.DESEDEWRAP", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.Cipher.");
            stringBuilder.append(PKCSObjectIdentifiers.id_alg_CMS3DESwrap);
            configurableProvider.addAlgorithm(stringBuilder.toString(), "DESEDEWRAP");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.TDEA", "DESEDE");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.TDEAWRAP", "DESEDEWRAP");
            configurableProvider.addAlgorithm("Alg.Alias.KeyGenerator.TDEA", "DESEDE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.TDEA", "DESEDE");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.TDEA", "DESEDE");
            if (configurableProvider.hasAlgorithm("MessageDigest", "SHA-1")) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(PREFIX);
                stringBuilder.append("$PBEWithSHAAndDES3Key");
                configurableProvider.addAlgorithm("Cipher.PBEWITHSHAAND3-KEYTRIPLEDES-CBC", stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append(PREFIX);
                stringBuilder.append("$PBEWithSHAAndDES2Key");
                configurableProvider.addAlgorithm("Cipher.PBEWITHSHAAND2-KEYTRIPLEDES-CBC", stringBuilder.toString());
                configurableProvider.addAlgorithm("Alg.Alias.Cipher", PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC, "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
                configurableProvider.addAlgorithm("Alg.Alias.Cipher", PKCSObjectIdentifiers.pbeWithSHAAnd2_KeyTripleDES_CBC, "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
                configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1ANDDESEDE", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
                configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND3-KEYTRIPLEDES-CBC", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
                configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND2-KEYTRIPLEDES-CBC", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
                configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHAAND3-KEYDESEDE-CBC", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
                configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHAAND2-KEYDESEDE-CBC", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
                configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND3-KEYDESEDE-CBC", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
                configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND2-KEYDESEDE-CBC", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
                configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1ANDDESEDE-CBC", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$KeyGenerator");
            configurableProvider.addAlgorithm("KeyGenerator.DESEDE", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$KeyFactory");
            configurableProvider.addAlgorithm("SecretKeyFactory.DESEDE", stringBuilder.toString());
            configurableProvider.addAlgorithm("AlgorithmParameters.DESEDE", "com.android.org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters");
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.AlgorithmParameters.");
            stringBuilder.append(PKCSObjectIdentifiers.des_EDE3_CBC);
            configurableProvider.addAlgorithm(stringBuilder.toString(), "DESEDE");
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithSHAAndDES3KeyFactory");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND3-KEYTRIPLEDES-CBC", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithSHAAndDES2KeyFactory");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND2-KEYTRIPLEDES-CBC", stringBuilder.toString());
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA1ANDDESEDE", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND3-KEYTRIPLEDES", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND2-KEYTRIPLEDES", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND3-KEYTRIPLEDES-CBC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND2-KEYTRIPLEDES-CBC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDDES3KEY-CBC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDDES2KEY-CBC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBE", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.3", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.4", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWithSHAAnd3KeyTripleDES", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.3", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.4", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWithSHAAnd3KeyTripleDES", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
        }
    }

    public static class PBEWithSHAAndDES2Key
    extends BaseBlockCipher {
        public PBEWithSHAAndDES2Key() {
            super(new CBCBlockCipher(new DESedeEngine()), 2, 1, 128, 8);
        }
    }

    public static class PBEWithSHAAndDES2KeyFactory
    extends DES.DESPBEKeyFactory {
        public PBEWithSHAAndDES2KeyFactory() {
            super("PBEwithSHAandDES2Key-CBC", PKCSObjectIdentifiers.pbeWithSHAAnd2_KeyTripleDES_CBC, true, 2, 1, 128, 64);
        }
    }

    public static class PBEWithSHAAndDES3Key
    extends BaseBlockCipher {
        public PBEWithSHAAndDES3Key() {
            super(new CBCBlockCipher(new DESedeEngine()), 2, 1, 192, 8);
        }
    }

    public static class PBEWithSHAAndDES3KeyFactory
    extends DES.DESPBEKeyFactory {
        public PBEWithSHAAndDES3KeyFactory() {
            super("PBEwithSHAandDES3Key-CBC", PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC, true, 2, 1, 192, 64);
        }
    }

    public static class Wrap
    extends BaseWrapCipher {
        public Wrap() {
            super(new DESedeWrapEngine());
        }
    }

}

