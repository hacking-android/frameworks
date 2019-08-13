/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.RSAESOAEPparams;
import com.android.org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.jcajce.provider.util.DigestFactory;
import com.android.org.bouncycastle.jcajce.util.MessageDigestUtils;
import java.io.IOException;
import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

public abstract class AlgorithmParametersSpi
extends java.security.AlgorithmParametersSpi {
    protected AlgorithmParameterSpec engineGetParameterSpec(Class class_) throws InvalidParameterSpecException {
        if (class_ != null) {
            return this.localEngineGetParameterSpec(class_);
        }
        throw new NullPointerException("argument to getParameterSpec must not be null");
    }

    protected boolean isASN1FormatString(String string) {
        boolean bl = string == null || string.equals("ASN.1");
        return bl;
    }

    protected abstract AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException;

    public static class OAEP
    extends AlgorithmParametersSpi {
        OAEPParameterSpec currentSpec;

        @Override
        protected byte[] engineGetEncoded() {
            ASN1Object aSN1Object = new AlgorithmIdentifier(DigestFactory.getOID(this.currentSpec.getDigestAlgorithm()), DERNull.INSTANCE);
            Object object = (MGF1ParameterSpec)this.currentSpec.getMGFParameters();
            object = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, new AlgorithmIdentifier(DigestFactory.getOID(((MGF1ParameterSpec)object).getDigestAlgorithm()), DERNull.INSTANCE));
            PSource.PSpecified pSpecified = (PSource.PSpecified)this.currentSpec.getPSource();
            aSN1Object = new RSAESOAEPparams((AlgorithmIdentifier)aSN1Object, (AlgorithmIdentifier)object, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_pSpecified, new DEROctetString(pSpecified.getValue())));
            try {
                aSN1Object = aSN1Object.getEncoded("DER");
                return aSN1Object;
            }
            catch (IOException iOException) {
                throw new RuntimeException("Error encoding OAEPParameters");
            }
        }

        @Override
        protected byte[] engineGetEncoded(String string) {
            if (!this.isASN1FormatString(string) && !string.equalsIgnoreCase("X.509")) {
                return null;
            }
            return this.engineGetEncoded();
        }

        @Override
        protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
            if (algorithmParameterSpec instanceof OAEPParameterSpec) {
                this.currentSpec = (OAEPParameterSpec)algorithmParameterSpec;
                return;
            }
            throw new InvalidParameterSpecException("OAEPParameterSpec required to initialise an OAEP algorithm parameters object");
        }

        @Override
        protected void engineInit(byte[] object) throws IOException {
            try {
                object = RSAESOAEPparams.getInstance(object);
                if (((RSAESOAEPparams)object).getMaskGenAlgorithm().getAlgorithm().equals(PKCSObjectIdentifiers.id_mgf1)) {
                    OAEPParameterSpec oAEPParameterSpec;
                    String string = MessageDigestUtils.getDigestName(((RSAESOAEPparams)object).getHashAlgorithm().getAlgorithm());
                    String string2 = OAEPParameterSpec.DEFAULT.getMGFAlgorithm();
                    MGF1ParameterSpec mGF1ParameterSpec = new MGF1ParameterSpec(MessageDigestUtils.getDigestName(AlgorithmIdentifier.getInstance(((RSAESOAEPparams)object).getMaskGenAlgorithm().getParameters()).getAlgorithm()));
                    PSource.PSpecified pSpecified = new PSource.PSpecified(ASN1OctetString.getInstance(((RSAESOAEPparams)object).getPSourceAlgorithm().getParameters()).getOctets());
                    this.currentSpec = oAEPParameterSpec = new OAEPParameterSpec(string, string2, mGF1ParameterSpec, pSpecified);
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unknown mask generation function: ");
                stringBuilder.append(((RSAESOAEPparams)object).getMaskGenAlgorithm().getAlgorithm());
                IOException iOException = new IOException(stringBuilder.toString());
                throw iOException;
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                throw new IOException("Not a valid OAEP Parameter encoding.");
            }
            catch (ClassCastException classCastException) {
                throw new IOException("Not a valid OAEP Parameter encoding.");
            }
        }

        @Override
        protected void engineInit(byte[] object, String string) throws IOException {
            if (!string.equalsIgnoreCase("X.509") && !string.equalsIgnoreCase("ASN.1")) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unknown parameter format ");
                ((StringBuilder)object).append(string);
                throw new IOException(((StringBuilder)object).toString());
            }
            this.engineInit((byte[])object);
        }

        @Override
        protected String engineToString() {
            return "OAEP Parameters";
        }

        @Override
        protected AlgorithmParameterSpec localEngineGetParameterSpec(Class class_) throws InvalidParameterSpecException {
            if (class_ != OAEPParameterSpec.class && class_ != AlgorithmParameterSpec.class) {
                throw new InvalidParameterSpecException("unknown parameter spec passed to OAEP parameters object.");
            }
            return this.currentSpec;
        }
    }

    public static class PSS
    extends AlgorithmParametersSpi {
        PSSParameterSpec currentSpec;

        @Override
        protected byte[] engineGetEncoded() throws IOException {
            PSSParameterSpec pSSParameterSpec = this.currentSpec;
            AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(DigestFactory.getOID(pSSParameterSpec.getDigestAlgorithm()), DERNull.INSTANCE);
            MGF1ParameterSpec mGF1ParameterSpec = (MGF1ParameterSpec)pSSParameterSpec.getMGFParameters();
            return new RSASSAPSSparams(algorithmIdentifier, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, new AlgorithmIdentifier(DigestFactory.getOID(mGF1ParameterSpec.getDigestAlgorithm()), DERNull.INSTANCE)), new ASN1Integer(pSSParameterSpec.getSaltLength()), new ASN1Integer(pSSParameterSpec.getTrailerField())).getEncoded("DER");
        }

        @Override
        protected byte[] engineGetEncoded(String string) throws IOException {
            if (!string.equalsIgnoreCase("X.509") && !string.equalsIgnoreCase("ASN.1")) {
                return null;
            }
            return this.engineGetEncoded();
        }

        @Override
        protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
            if (algorithmParameterSpec instanceof PSSParameterSpec) {
                this.currentSpec = (PSSParameterSpec)algorithmParameterSpec;
                return;
            }
            throw new InvalidParameterSpecException("PSSParameterSpec required to initialise an PSS algorithm parameters object");
        }

        @Override
        protected void engineInit(byte[] object) throws IOException {
            try {
                object = RSASSAPSSparams.getInstance(object);
                if (((RSASSAPSSparams)object).getMaskGenAlgorithm().getAlgorithm().equals(PKCSObjectIdentifiers.id_mgf1)) {
                    PSSParameterSpec pSSParameterSpec;
                    String string = MessageDigestUtils.getDigestName(((RSASSAPSSparams)object).getHashAlgorithm().getAlgorithm());
                    String string2 = PSSParameterSpec.DEFAULT.getMGFAlgorithm();
                    MGF1ParameterSpec mGF1ParameterSpec = new MGF1ParameterSpec(MessageDigestUtils.getDigestName(AlgorithmIdentifier.getInstance(((RSASSAPSSparams)object).getMaskGenAlgorithm().getParameters()).getAlgorithm()));
                    this.currentSpec = pSSParameterSpec = new PSSParameterSpec(string, string2, mGF1ParameterSpec, ((RSASSAPSSparams)object).getSaltLength().intValue(), ((RSASSAPSSparams)object).getTrailerField().intValue());
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unknown mask generation function: ");
                stringBuilder.append(((RSASSAPSSparams)object).getMaskGenAlgorithm().getAlgorithm());
                IOException iOException = new IOException(stringBuilder.toString());
                throw iOException;
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                throw new IOException("Not a valid PSS Parameter encoding.");
            }
            catch (ClassCastException classCastException) {
                throw new IOException("Not a valid PSS Parameter encoding.");
            }
        }

        @Override
        protected void engineInit(byte[] object, String string) throws IOException {
            if (!this.isASN1FormatString(string) && !string.equalsIgnoreCase("X.509")) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unknown parameter format ");
                ((StringBuilder)object).append(string);
                throw new IOException(((StringBuilder)object).toString());
            }
            this.engineInit((byte[])object);
        }

        @Override
        protected String engineToString() {
            return "PSS Parameters";
        }

        @Override
        protected AlgorithmParameterSpec localEngineGetParameterSpec(Class class_) throws InvalidParameterSpecException {
            if (class_ != PSSParameterSpec.class && class_ != AlgorithmParameterSpec.class) {
                throw new InvalidParameterSpecException("unknown parameter spec passed to PSS parameters object.");
            }
            return this.currentSpec;
        }
    }

}

