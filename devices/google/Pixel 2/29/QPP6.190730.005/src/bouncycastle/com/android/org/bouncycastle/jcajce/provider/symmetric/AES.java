/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.symmetric;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.bc.BCObjectIdentifiers;
import com.android.org.bouncycastle.asn1.cms.GCMParameters;
import com.android.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.BufferedBlockCipher;
import com.android.org.bouncycastle.crypto.CipherKeyGenerator;
import com.android.org.bouncycastle.crypto.Wrapper;
import com.android.org.bouncycastle.crypto.engines.AESEngine;
import com.android.org.bouncycastle.crypto.engines.AESWrapEngine;
import com.android.org.bouncycastle.crypto.modes.AEADBlockCipher;
import com.android.org.bouncycastle.crypto.modes.CBCBlockCipher;
import com.android.org.bouncycastle.crypto.modes.CFBBlockCipher;
import com.android.org.bouncycastle.crypto.modes.GCMBlockCipher;
import com.android.org.bouncycastle.crypto.modes.OFBBlockCipher;
import com.android.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.org.bouncycastle.jcajce.provider.symmetric.GcmSpecUtil;
import com.android.org.bouncycastle.jcajce.provider.symmetric.SymmetricAlgorithmProvider;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BlockCipherProvider;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;
import com.android.org.bouncycastle.jcajce.spec.AEADParameterSpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

public final class AES {
    private static final Map<String, String> generalAesAttributes = new HashMap<String, String>();

    static {
        generalAesAttributes.put("SupportedKeyClasses", "javax.crypto.SecretKey");
        generalAesAttributes.put("SupportedKeyFormats", "RAW");
    }

    private AES() {
    }

    public static class AlgParams
    extends IvAlgorithmParameters {
        @Override
        protected String engineToString() {
            return "AES IV";
        }
    }

    public static class AlgParamsGCM
    extends BaseAlgorithmParameters {
        private GCMParameters gcmParams;

        @Override
        protected byte[] engineGetEncoded() throws IOException {
            return this.gcmParams.getEncoded();
        }

        @Override
        protected byte[] engineGetEncoded(String string) throws IOException {
            if (this.isASN1FormatString(string)) {
                return this.gcmParams.getEncoded();
            }
            throw new IOException("unknown format specified");
        }

        @Override
        protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
            block4 : {
                block3 : {
                    block2 : {
                        if (!GcmSpecUtil.isGcmSpec(algorithmParameterSpec)) break block2;
                        this.gcmParams = GcmSpecUtil.extractGcmParameters(algorithmParameterSpec);
                        break block3;
                    }
                    if (!(algorithmParameterSpec instanceof AEADParameterSpec)) break block4;
                    this.gcmParams = new GCMParameters(((AEADParameterSpec)algorithmParameterSpec).getNonce(), ((AEADParameterSpec)algorithmParameterSpec).getMacSizeInBits() / 8);
                }
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AlgorithmParameterSpec class not recognized: ");
            stringBuilder.append(algorithmParameterSpec.getClass().getName());
            throw new InvalidParameterSpecException(stringBuilder.toString());
        }

        @Override
        protected void engineInit(byte[] arrby) throws IOException {
            this.gcmParams = GCMParameters.getInstance(arrby);
        }

        @Override
        protected void engineInit(byte[] arrby, String string) throws IOException {
            if (this.isASN1FormatString(string)) {
                this.gcmParams = GCMParameters.getInstance(arrby);
                return;
            }
            throw new IOException("unknown format specified");
        }

        @Override
        protected String engineToString() {
            return "GCM";
        }

        @Override
        protected AlgorithmParameterSpec localEngineGetParameterSpec(Class class_) throws InvalidParameterSpecException {
            if (class_ != AlgorithmParameterSpec.class && !GcmSpecUtil.isGcmSpec(class_)) {
                if (class_ == AEADParameterSpec.class) {
                    return new AEADParameterSpec(this.gcmParams.getNonce(), this.gcmParams.getIcvLen() * 8);
                }
                if (class_ == IvParameterSpec.class) {
                    return new IvParameterSpec(this.gcmParams.getNonce());
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("AlgorithmParameterSpec not recognized: ");
                stringBuilder.append(class_.getName());
                throw new InvalidParameterSpecException(stringBuilder.toString());
            }
            if (GcmSpecUtil.gcmSpecExists()) {
                return GcmSpecUtil.extractGcmSpec(this.gcmParams.toASN1Primitive());
            }
            return new AEADParameterSpec(this.gcmParams.getNonce(), this.gcmParams.getIcvLen() * 8);
        }
    }

    public static class CBC
    extends BaseBlockCipher {
        public CBC() {
            super(new CBCBlockCipher(new AESEngine()), 128);
        }
    }

    public static class CFB
    extends BaseBlockCipher {
        public CFB() {
            super(new BufferedBlockCipher(new CFBBlockCipher(new AESEngine(), 128)), 128);
        }
    }

    public static class ECB
    extends BaseBlockCipher {
        public ECB() {
            super(new BlockCipherProvider(){

                @Override
                public BlockCipher get() {
                    return new AESEngine();
                }
            });
        }

    }

    public static class GCM
    extends BaseBlockCipher {
        public GCM() {
            super(new GCMBlockCipher(new AESEngine()));
            try {
                this.engineSetMode("GCM");
                this.engineSetPadding("NoPadding");
                return;
            }
            catch (NoSuchAlgorithmException | NoSuchPaddingException generalSecurityException) {
                throw new RuntimeException("Could not set mode or padding for GCM mode", generalSecurityException);
            }
        }
    }

    public static class KeyGen
    extends BaseKeyGenerator {
        public KeyGen() {
            this(128);
        }

        public KeyGen(int n) {
            super("AES", n, new CipherKeyGenerator());
        }
    }

    public static class Mappings
    extends SymmetricAlgorithmProvider {
        private static final String PREFIX = AES.class.getName();
        private static final String wrongAES128 = "2.16.840.1.101.3.4.2";
        private static final String wrongAES192 = "2.16.840.1.101.3.4.22";
        private static final String wrongAES256 = "2.16.840.1.101.3.4.42";

        @Override
        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$AlgParams");
            configurableProvider.addAlgorithm("AlgorithmParameters.AES", stringBuilder.toString());
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.2", "AES");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.22", "AES");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.42", "AES");
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.AlgorithmParameters.");
            stringBuilder.append(NISTObjectIdentifiers.id_aes128_CBC);
            configurableProvider.addAlgorithm(stringBuilder.toString(), "AES");
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.AlgorithmParameters.");
            stringBuilder.append(NISTObjectIdentifiers.id_aes192_CBC);
            configurableProvider.addAlgorithm(stringBuilder.toString(), "AES");
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.AlgorithmParameters.");
            stringBuilder.append(NISTObjectIdentifiers.id_aes256_CBC);
            configurableProvider.addAlgorithm(stringBuilder.toString(), "AES");
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$AlgParamsGCM");
            configurableProvider.addAlgorithm("AlgorithmParameters.GCM", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.AlgorithmParameters.");
            stringBuilder.append(NISTObjectIdentifiers.id_aes128_GCM);
            configurableProvider.addAlgorithm(stringBuilder.toString(), "GCM");
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.AlgorithmParameters.");
            stringBuilder.append(NISTObjectIdentifiers.id_aes192_GCM);
            configurableProvider.addAlgorithm(stringBuilder.toString(), "GCM");
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.AlgorithmParameters.");
            stringBuilder.append(NISTObjectIdentifiers.id_aes256_GCM);
            configurableProvider.addAlgorithm(stringBuilder.toString(), "GCM");
            configurableProvider.addAttributes("Cipher.AES", generalAesAttributes);
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$ECB");
            configurableProvider.addAlgorithm("Cipher.AES", stringBuilder.toString());
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.2.16.840.1.101.3.4.2", "AES");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.2.16.840.1.101.3.4.22", "AES");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.2.16.840.1.101.3.4.42", "AES");
            configurableProvider.addAttributes("Cipher.AESWRAP", generalAesAttributes);
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$Wrap");
            configurableProvider.addAlgorithm("Cipher.AESWRAP", stringBuilder.toString());
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", NISTObjectIdentifiers.id_aes128_wrap, "AESWRAP");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", NISTObjectIdentifiers.id_aes192_wrap, "AESWRAP");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", NISTObjectIdentifiers.id_aes256_wrap, "AESWRAP");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.AESKW", "AESWRAP");
            configurableProvider.addAttributes("Cipher.AES/GCM/NOPADDING", generalAesAttributes);
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$GCM");
            configurableProvider.addAlgorithm("Cipher.AES/GCM/NOPADDING", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.Cipher.");
            stringBuilder.append(NISTObjectIdentifiers.id_aes128_GCM);
            configurableProvider.addAlgorithm(stringBuilder.toString(), "AES/GCM/NOPADDING");
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.Cipher.");
            stringBuilder.append(NISTObjectIdentifiers.id_aes192_GCM);
            configurableProvider.addAlgorithm(stringBuilder.toString(), "AES/GCM/NOPADDING");
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.Cipher.");
            stringBuilder.append(NISTObjectIdentifiers.id_aes256_GCM);
            configurableProvider.addAlgorithm(stringBuilder.toString(), "AES/GCM/NOPADDING");
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$KeyGen");
            configurableProvider.addAlgorithm("KeyGenerator.AES", stringBuilder.toString());
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes128_cbc, "PBEWITHSHAAND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes192_cbc, "PBEWITHSHAAND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes256_cbc, "PBEWITHSHAAND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes128_cbc, "PBEWITHSHA256AND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes192_cbc, "PBEWITHSHA256AND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes256_cbc, "PBEWITHSHA256AND256BITAES-CBC-BC");
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithSHA1AESCBC128");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHAAND128BITAES-CBC-BC", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithSHA1AESCBC192");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHAAND192BITAES-CBC-BC", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithSHA1AESCBC256");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHAAND256BITAES-CBC-BC", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithSHA256AESCBC128");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHA256AND128BITAES-CBC-BC", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithSHA256AESCBC192");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHA256AND192BITAES-CBC-BC", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithSHA256AESCBC256");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHA256AND256BITAES-CBC-BC", stringBuilder.toString());
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND128BITAES-CBC-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND192BITAES-CBC-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND256BITAES-CBC-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-1AND128BITAES-CBC-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-1AND192BITAES-CBC-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-1AND256BITAES-CBC-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHAAND128BITAES-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHAAND192BITAES-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHAAND256BITAES-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND128BITAES-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND192BITAES-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND256BITAES-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-1AND128BITAES-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-1AND192BITAES-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-1AND256BITAES-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-256AND128BITAES-CBC-BC", "PBEWITHSHA256AND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-256AND192BITAES-CBC-BC", "PBEWITHSHA256AND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-256AND256BITAES-CBC-BC", "PBEWITHSHA256AND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA256AND128BITAES-BC", "PBEWITHSHA256AND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA256AND192BITAES-BC", "PBEWITHSHA256AND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA256AND256BITAES-BC", "PBEWITHSHA256AND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-256AND128BITAES-BC", "PBEWITHSHA256AND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-256AND192BITAES-BC", "PBEWITHSHA256AND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-256AND256BITAES-BC", "PBEWITHSHA256AND256BITAES-CBC-BC");
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithAESCBC");
            configurableProvider.addAlgorithm("Cipher.PBEWITHMD5AND128BITAES-CBC-OPENSSL", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithAESCBC");
            configurableProvider.addAlgorithm("Cipher.PBEWITHMD5AND192BITAES-CBC-OPENSSL", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithAESCBC");
            configurableProvider.addAlgorithm("Cipher.PBEWITHMD5AND256BITAES-CBC-OPENSSL", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithMD5And128BitAESCBCOpenSSL");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHMD5AND128BITAES-CBC-OPENSSL", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithMD5And192BitAESCBCOpenSSL");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHMD5AND192BITAES-CBC-OPENSSL", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithMD5And256BitAESCBCOpenSSL");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHMD5AND256BITAES-CBC-OPENSSL", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithSHAAnd128BitAESBC");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND128BITAES-CBC-BC", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithSHAAnd192BitAESBC");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND192BITAES-CBC-BC", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithSHAAnd256BitAESBC");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND256BITAES-CBC-BC", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithSHA256And128BitAESBC");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHA256AND128BITAES-CBC-BC", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithSHA256And192BitAESBC");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHA256AND192BITAES-CBC-BC", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithSHA256And256BitAESBC");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHA256AND256BITAES-CBC-BC", stringBuilder.toString());
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA1AND128BITAES-CBC-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA1AND192BITAES-CBC-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA1AND256BITAES-CBC-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-1AND128BITAES-CBC-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-1AND192BITAES-CBC-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-1AND256BITAES-CBC-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND128BITAES-CBC-BC", "PBEWITHSHA256AND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND192BITAES-CBC-BC", "PBEWITHSHA256AND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND256BITAES-CBC-BC", "PBEWITHSHA256AND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND128BITAES-BC", "PBEWITHSHA256AND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND192BITAES-BC", "PBEWITHSHA256AND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND256BITAES-BC", "PBEWITHSHA256AND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory", BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes128_cbc, "PBEWITHSHAAND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory", BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes192_cbc, "PBEWITHSHAAND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory", BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes256_cbc, "PBEWITHSHAAND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory", BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes128_cbc, "PBEWITHSHA256AND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory", BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes192_cbc, "PBEWITHSHA256AND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory", BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes256_cbc, "PBEWITHSHA256AND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND128BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND192BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND256BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA256AND128BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA256AND192BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA256AND256BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA1AND128BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA1AND192BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA1AND256BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-1AND128BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-1AND192BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-1AND256BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-256AND128BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-256AND192BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-256AND256BITAES-CBC-BC", "PKCS12PBE");
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.AlgorithmParameters.");
            stringBuilder.append(BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes128_cbc.getId());
            configurableProvider.addAlgorithm(stringBuilder.toString(), "PKCS12PBE");
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.AlgorithmParameters.");
            stringBuilder.append(BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes192_cbc.getId());
            configurableProvider.addAlgorithm(stringBuilder.toString(), "PKCS12PBE");
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.AlgorithmParameters.");
            stringBuilder.append(BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes256_cbc.getId());
            configurableProvider.addAlgorithm(stringBuilder.toString(), "PKCS12PBE");
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.AlgorithmParameters.");
            stringBuilder.append(BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes128_cbc.getId());
            configurableProvider.addAlgorithm(stringBuilder.toString(), "PKCS12PBE");
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.AlgorithmParameters.");
            stringBuilder.append(BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes192_cbc.getId());
            configurableProvider.addAlgorithm(stringBuilder.toString(), "PKCS12PBE");
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.AlgorithmParameters.");
            stringBuilder.append(BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes256_cbc.getId());
            configurableProvider.addAlgorithm(stringBuilder.toString(), "PKCS12PBE");
        }
    }

    public static class OFB
    extends BaseBlockCipher {
        public OFB() {
            super(new BufferedBlockCipher(new OFBBlockCipher(new AESEngine(), 128)), 128);
        }
    }

    public static class PBEWithAESCBC
    extends BaseBlockCipher {
        public PBEWithAESCBC() {
            super(new CBCBlockCipher(new AESEngine()));
        }
    }

    public static class PBEWithMD5And128BitAESCBCOpenSSL
    extends PBESecretKeyFactory {
        public PBEWithMD5And128BitAESCBCOpenSSL() {
            super("PBEWithMD5And128BitAES-CBC-OpenSSL", null, true, 3, 0, 128, 128);
        }
    }

    public static class PBEWithMD5And192BitAESCBCOpenSSL
    extends PBESecretKeyFactory {
        public PBEWithMD5And192BitAESCBCOpenSSL() {
            super("PBEWithMD5And192BitAES-CBC-OpenSSL", null, true, 3, 0, 192, 128);
        }
    }

    public static class PBEWithMD5And256BitAESCBCOpenSSL
    extends PBESecretKeyFactory {
        public PBEWithMD5And256BitAESCBCOpenSSL() {
            super("PBEWithMD5And256BitAES-CBC-OpenSSL", null, true, 3, 0, 256, 128);
        }
    }

    public static class PBEWithSHA1AESCBC128
    extends BaseBlockCipher {
        public PBEWithSHA1AESCBC128() {
            super(new CBCBlockCipher(new AESEngine()), 2, 1, 128, 16);
        }
    }

    public static class PBEWithSHA1AESCBC192
    extends BaseBlockCipher {
        public PBEWithSHA1AESCBC192() {
            super(new CBCBlockCipher(new AESEngine()), 2, 1, 192, 16);
        }
    }

    public static class PBEWithSHA1AESCBC256
    extends BaseBlockCipher {
        public PBEWithSHA1AESCBC256() {
            super(new CBCBlockCipher(new AESEngine()), 2, 1, 256, 16);
        }
    }

    public static class PBEWithSHA256AESCBC128
    extends BaseBlockCipher {
        public PBEWithSHA256AESCBC128() {
            super(new CBCBlockCipher(new AESEngine()), 2, 4, 128, 16);
        }
    }

    public static class PBEWithSHA256AESCBC192
    extends BaseBlockCipher {
        public PBEWithSHA256AESCBC192() {
            super(new CBCBlockCipher(new AESEngine()), 2, 4, 192, 16);
        }
    }

    public static class PBEWithSHA256AESCBC256
    extends BaseBlockCipher {
        public PBEWithSHA256AESCBC256() {
            super(new CBCBlockCipher(new AESEngine()), 2, 4, 256, 16);
        }
    }

    public static class PBEWithSHA256And128BitAESBC
    extends PBESecretKeyFactory {
        public PBEWithSHA256And128BitAESBC() {
            super("PBEWithSHA256And128BitAES-CBC-BC", null, true, 2, 4, 128, 128);
        }
    }

    public static class PBEWithSHA256And192BitAESBC
    extends PBESecretKeyFactory {
        public PBEWithSHA256And192BitAESBC() {
            super("PBEWithSHA256And192BitAES-CBC-BC", null, true, 2, 4, 192, 128);
        }
    }

    public static class PBEWithSHA256And256BitAESBC
    extends PBESecretKeyFactory {
        public PBEWithSHA256And256BitAESBC() {
            super("PBEWithSHA256And256BitAES-CBC-BC", null, true, 2, 4, 256, 128);
        }
    }

    public static class PBEWithSHAAnd128BitAESBC
    extends PBESecretKeyFactory {
        public PBEWithSHAAnd128BitAESBC() {
            super("PBEWithSHA1And128BitAES-CBC-BC", null, true, 2, 1, 128, 128);
        }
    }

    public static class PBEWithSHAAnd192BitAESBC
    extends PBESecretKeyFactory {
        public PBEWithSHAAnd192BitAESBC() {
            super("PBEWithSHA1And192BitAES-CBC-BC", null, true, 2, 1, 192, 128);
        }
    }

    public static class PBEWithSHAAnd256BitAESBC
    extends PBESecretKeyFactory {
        public PBEWithSHAAnd256BitAESBC() {
            super("PBEWithSHA1And256BitAES-CBC-BC", null, true, 2, 1, 256, 128);
        }
    }

    public static class Wrap
    extends BaseWrapCipher {
        public Wrap() {
            super(new AESWrapEngine());
        }
    }

}

