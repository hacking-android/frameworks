/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.symmetric;

import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import com.android.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import com.android.org.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import java.io.IOException;
import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.PBEParameterSpec;

public class PBEPKCS12 {
    private PBEPKCS12() {
    }

    public static class AlgParams
    extends BaseAlgorithmParameters {
        PKCS12PBEParams params;

        @Override
        protected byte[] engineGetEncoded() {
            try {
                byte[] arrby = this.params.getEncoded("DER");
                return arrby;
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Oooops! ");
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
                algorithmParameterSpec = (PBEParameterSpec)algorithmParameterSpec;
                this.params = new PKCS12PBEParams(((PBEParameterSpec)algorithmParameterSpec).getSalt(), ((PBEParameterSpec)algorithmParameterSpec).getIterationCount());
                return;
            }
            throw new InvalidParameterSpecException("PBEParameterSpec required to initialise a PKCS12 PBE parameters algorithm parameters object");
        }

        @Override
        protected void engineInit(byte[] arrby) throws IOException {
            this.params = PKCS12PBEParams.getInstance(ASN1Primitive.fromByteArray(arrby));
        }

        @Override
        protected void engineInit(byte[] arrby, String string) throws IOException {
            if (this.isASN1FormatString(string)) {
                this.engineInit(arrby);
                return;
            }
            throw new IOException("Unknown parameters format in PKCS12 PBE parameters object");
        }

        @Override
        protected String engineToString() {
            return "PKCS12 PBE Parameters";
        }

        @Override
        protected AlgorithmParameterSpec localEngineGetParameterSpec(Class class_) throws InvalidParameterSpecException {
            if (class_ == PBEParameterSpec.class) {
                return new PBEParameterSpec(this.params.getIV(), this.params.getIterations().intValue());
            }
            throw new InvalidParameterSpecException("unknown parameter spec passed to PKCS12 PBE parameters object.");
        }
    }

    public static class Mappings
    extends AlgorithmProvider {
        private static final String PREFIX = PBEPKCS12.class.getName();

        @Override
        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$AlgParams");
            configurableProvider.addAlgorithm("AlgorithmParameters.PKCS12PBE", stringBuilder.toString());
        }
    }

}

