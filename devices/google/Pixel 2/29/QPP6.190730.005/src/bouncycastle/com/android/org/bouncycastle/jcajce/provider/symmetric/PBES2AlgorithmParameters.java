/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.symmetric;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.EncryptionScheme;
import com.android.org.bouncycastle.asn1.pkcs.KeyDerivationFunc;
import com.android.org.bouncycastle.asn1.pkcs.PBES2Parameters;
import com.android.org.bouncycastle.asn1.pkcs.PBKDF2Params;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.PBE;
import com.android.org.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Enumeration;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;

public class PBES2AlgorithmParameters {
    private PBES2AlgorithmParameters() {
    }

    private static PBEParameterSpec createPBEParameterSpec(byte[] object, int n, byte[] arrby) {
        try {
            Constructor<?> constructor = PBES2AlgorithmParameters.class.getClassLoader().loadClass("javax.crypto.spec.PBEParameterSpec").getConstructor(byte[].class, Integer.TYPE, AlgorithmParameterSpec.class);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(arrby);
            object = (PBEParameterSpec)constructor.newInstance(object, n, ivParameterSpec);
            return object;
        }
        catch (Exception exception) {
            throw new IllegalStateException("Requested creation PBES2 parameters in an SDK that doesn't support them", exception);
        }
    }

    private static abstract class BasePBEWithHmacAlgorithmParameters
    extends BaseAlgorithmParameters {
        private final ASN1ObjectIdentifier cipherAlgorithm;
        private final String cipherAlgorithmShortName;
        private final AlgorithmIdentifier kdf;
        private final String kdfShortName;
        private final int keySize;
        private PBES2Parameters params;

        private BasePBEWithHmacAlgorithmParameters(ASN1ObjectIdentifier aSN1ObjectIdentifier, String string, int n, ASN1ObjectIdentifier aSN1ObjectIdentifier2, String string2) {
            this.kdf = new AlgorithmIdentifier(aSN1ObjectIdentifier, DERNull.INSTANCE);
            this.kdfShortName = string;
            this.keySize = n;
            this.cipherAlgorithm = aSN1ObjectIdentifier2;
            this.cipherAlgorithmShortName = string2;
        }

        @Override
        protected byte[] engineGetEncoded() {
            try {
                byte[] arrby = new DERSequence(new ASN1Encodable[]{PKCSObjectIdentifiers.id_PBES2, this.params});
                arrby = arrby.getEncoded();
                return arrby;
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to read PBES2 parameters: ");
                stringBuilder.append(iOException.toString());
                throw new RuntimeException(stringBuilder.toString());
            }
        }

        @Override
        protected byte[] engineGetEncoded(String string) {
            if (this.isASN1FormatString(string)) {
                return this.engineGetEncoded();
            }
            return null;
        }

        @Override
        protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
            if (algorithmParameterSpec instanceof PBEParameterSpec) {
                byte[] arrby = PBE.Util.getParameterSpecFromPBEParameterSpec((PBEParameterSpec)(algorithmParameterSpec = (PBEParameterSpec)algorithmParameterSpec));
                if (arrby instanceof IvParameterSpec) {
                    arrby = ((IvParameterSpec)arrby).getIV();
                    this.params = new PBES2Parameters(new KeyDerivationFunc(PKCSObjectIdentifiers.id_PBKDF2, new PBKDF2Params(((PBEParameterSpec)algorithmParameterSpec).getSalt(), ((PBEParameterSpec)algorithmParameterSpec).getIterationCount(), this.keySize, this.kdf)), new EncryptionScheme(this.cipherAlgorithm, new DEROctetString(arrby)));
                    return;
                }
                throw new IllegalArgumentException("Expecting an IV as a parameter");
            }
            throw new InvalidParameterSpecException("PBEParameterSpec required to initialise PBES2 algorithm parameters");
        }

        @Override
        protected void engineInit(byte[] object) throws IOException {
            if (((ASN1ObjectIdentifier)(object = ASN1Sequence.getInstance(ASN1Primitive.fromByteArray((byte[])object)).getObjects()).nextElement()).getId().equals(PKCSObjectIdentifiers.id_PBES2.getId())) {
                this.params = PBES2Parameters.getInstance(object.nextElement());
                return;
            }
            throw new IllegalArgumentException("Invalid PBES2 parameters");
        }

        @Override
        protected void engineInit(byte[] arrby, String string) throws IOException {
            if (this.isASN1FormatString(string)) {
                this.engineInit(arrby);
                return;
            }
            throw new IOException("Unknown parameters format in PBES2 parameters object");
        }

        @Override
        protected String engineToString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PBES2 ");
            stringBuilder.append(this.kdfShortName);
            stringBuilder.append(" ");
            stringBuilder.append(this.cipherAlgorithmShortName);
            stringBuilder.append(" Parameters");
            return stringBuilder.toString();
        }

        @Override
        protected AlgorithmParameterSpec localEngineGetParameterSpec(Class object) throws InvalidParameterSpecException {
            if (object == PBEParameterSpec.class) {
                object = (PBKDF2Params)this.params.getKeyDerivationFunc().getParameters();
                byte[] arrby = ((ASN1OctetString)this.params.getEncryptionScheme().getParameters()).getOctets();
                return PBES2AlgorithmParameters.createPBEParameterSpec(((PBKDF2Params)object).getSalt(), ((PBKDF2Params)object).getIterationCount().intValue(), arrby);
            }
            throw new InvalidParameterSpecException("unknown parameter spec passed to PBES2 parameters object.");
        }
    }

    public static class Mappings
    extends AlgorithmProvider {
        private static final String PREFIX = PBES2AlgorithmParameters.class.getName();

        @Override
        public void configure(ConfigurableProvider configurableProvider) {
            int[] arrn;
            int[] arrn2;
            int[] arrn3 = arrn2 = new int[2];
            arrn3[0] = 128;
            arrn3[1] = 256;
            int[] arrn4 = arrn = new int[5];
            arrn4[0] = 1;
            arrn4[1] = 224;
            arrn4[2] = 256;
            arrn4[3] = 384;
            arrn4[4] = 512;
            for (int n : arrn2) {
                for (int n2 : arrn) {
                    CharSequence charSequence = new StringBuilder();
                    charSequence.append("AlgorithmParameters.PBEWithHmacSHA");
                    charSequence.append(n2);
                    charSequence.append("AndAES_");
                    charSequence.append(n);
                    charSequence = charSequence.toString();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(PREFIX);
                    stringBuilder.append("$PBEWithHmacSHA");
                    stringBuilder.append(n2);
                    stringBuilder.append("AES");
                    stringBuilder.append(n);
                    stringBuilder.append("AlgorithmParameters");
                    configurableProvider.addAlgorithm((String)charSequence, stringBuilder.toString());
                }
            }
        }
    }

    public static class PBEWithHmacSHA1AES128AlgorithmParameters
    extends BasePBEWithHmacAlgorithmParameters {
        public PBEWithHmacSHA1AES128AlgorithmParameters() {
            super(PKCSObjectIdentifiers.id_hmacWithSHA1, "HmacSHA1", 16, NISTObjectIdentifiers.id_aes128_CBC, "AES128");
        }
    }

    public static class PBEWithHmacSHA1AES256AlgorithmParameters
    extends BasePBEWithHmacAlgorithmParameters {
        public PBEWithHmacSHA1AES256AlgorithmParameters() {
            super(PKCSObjectIdentifiers.id_hmacWithSHA1, "HmacSHA1", 32, NISTObjectIdentifiers.id_aes256_CBC, "AES256");
        }
    }

    public static class PBEWithHmacSHA224AES128AlgorithmParameters
    extends BasePBEWithHmacAlgorithmParameters {
        public PBEWithHmacSHA224AES128AlgorithmParameters() {
            super(PKCSObjectIdentifiers.id_hmacWithSHA224, "HmacSHA224", 16, NISTObjectIdentifiers.id_aes128_CBC, "AES128");
        }
    }

    public static class PBEWithHmacSHA224AES256AlgorithmParameters
    extends BasePBEWithHmacAlgorithmParameters {
        public PBEWithHmacSHA224AES256AlgorithmParameters() {
            super(PKCSObjectIdentifiers.id_hmacWithSHA224, "HmacSHA224", 32, NISTObjectIdentifiers.id_aes256_CBC, "AES256");
        }
    }

    public static class PBEWithHmacSHA256AES128AlgorithmParameters
    extends BasePBEWithHmacAlgorithmParameters {
        public PBEWithHmacSHA256AES128AlgorithmParameters() {
            super(PKCSObjectIdentifiers.id_hmacWithSHA256, "HmacSHA256", 16, NISTObjectIdentifiers.id_aes128_CBC, "AES128");
        }
    }

    public static class PBEWithHmacSHA256AES256AlgorithmParameters
    extends BasePBEWithHmacAlgorithmParameters {
        public PBEWithHmacSHA256AES256AlgorithmParameters() {
            super(PKCSObjectIdentifiers.id_hmacWithSHA256, "HmacSHA256", 32, NISTObjectIdentifiers.id_aes256_CBC, "AES256");
        }
    }

    public static class PBEWithHmacSHA384AES128AlgorithmParameters
    extends BasePBEWithHmacAlgorithmParameters {
        public PBEWithHmacSHA384AES128AlgorithmParameters() {
            super(PKCSObjectIdentifiers.id_hmacWithSHA384, "HmacSHA384", 16, NISTObjectIdentifiers.id_aes128_CBC, "AES128");
        }
    }

    public static class PBEWithHmacSHA384AES256AlgorithmParameters
    extends BasePBEWithHmacAlgorithmParameters {
        public PBEWithHmacSHA384AES256AlgorithmParameters() {
            super(PKCSObjectIdentifiers.id_hmacWithSHA384, "HmacSHA384", 32, NISTObjectIdentifiers.id_aes256_CBC, "AES256");
        }
    }

    public static class PBEWithHmacSHA512AES128AlgorithmParameters
    extends BasePBEWithHmacAlgorithmParameters {
        public PBEWithHmacSHA512AES128AlgorithmParameters() {
            super(PKCSObjectIdentifiers.id_hmacWithSHA512, "HmacSHA512", 16, NISTObjectIdentifiers.id_aes128_CBC, "AES128");
        }
    }

    public static class PBEWithHmacSHA512AES256AlgorithmParameters
    extends BasePBEWithHmacAlgorithmParameters {
        public PBEWithHmacSHA512AES256AlgorithmParameters() {
            super(PKCSObjectIdentifiers.id_hmacWithSHA512, "HmacSHA512", 32, NISTObjectIdentifiers.id_aes256_CBC, "AES256");
        }
    }

}

