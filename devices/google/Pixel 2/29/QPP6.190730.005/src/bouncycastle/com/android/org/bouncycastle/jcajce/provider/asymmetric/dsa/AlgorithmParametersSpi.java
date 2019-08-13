/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.dsa;

import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.x509.DSAParameter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.DSAParameterSpec;
import java.security.spec.InvalidParameterSpecException;

public class AlgorithmParametersSpi
extends java.security.AlgorithmParametersSpi {
    DSAParameterSpec currentSpec;

    @Override
    protected byte[] engineGetEncoded() {
        byte[] arrby = new DSAParameter(this.currentSpec.getP(), this.currentSpec.getQ(), this.currentSpec.getG());
        try {
            arrby = arrby.getEncoded("DER");
            return arrby;
        }
        catch (IOException iOException) {
            throw new RuntimeException("Error encoding DSAParameters");
        }
    }

    @Override
    protected byte[] engineGetEncoded(String string) {
        if (this.isASN1FormatString(string)) {
            return this.engineGetEncoded();
        }
        return null;
    }

    protected AlgorithmParameterSpec engineGetParameterSpec(Class class_) throws InvalidParameterSpecException {
        if (class_ != null) {
            return this.localEngineGetParameterSpec(class_);
        }
        throw new NullPointerException("argument to getParameterSpec must not be null");
    }

    @Override
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        if (algorithmParameterSpec instanceof DSAParameterSpec) {
            this.currentSpec = (DSAParameterSpec)algorithmParameterSpec;
            return;
        }
        throw new InvalidParameterSpecException("DSAParameterSpec required to initialise a DSA algorithm parameters object");
    }

    @Override
    protected void engineInit(byte[] object) throws IOException {
        try {
            DSAParameter dSAParameter = DSAParameter.getInstance(ASN1Primitive.fromByteArray(object));
            this.currentSpec = object = new DSAParameterSpec(dSAParameter.getP(), dSAParameter.getQ(), dSAParameter.getG());
            return;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new IOException("Not a valid DSA Parameter encoding.");
        }
        catch (ClassCastException classCastException) {
            throw new IOException("Not a valid DSA Parameter encoding.");
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
        return "DSA Parameters";
    }

    protected boolean isASN1FormatString(String string) {
        boolean bl = string == null || string.equals("ASN.1");
        return bl;
    }

    protected AlgorithmParameterSpec localEngineGetParameterSpec(Class class_) throws InvalidParameterSpecException {
        if (class_ != DSAParameterSpec.class && class_ != AlgorithmParameterSpec.class) {
            throw new InvalidParameterSpecException("unknown parameter spec passed to DSA parameters object.");
        }
        return this.currentSpec;
    }
}

