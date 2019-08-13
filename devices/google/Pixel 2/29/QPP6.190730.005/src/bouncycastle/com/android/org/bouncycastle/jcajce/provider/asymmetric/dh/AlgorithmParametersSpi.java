/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.dh;

import com.android.org.bouncycastle.asn1.pkcs.DHParameter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.DHParameterSpec;

public class AlgorithmParametersSpi
extends java.security.AlgorithmParametersSpi {
    DHParameterSpec currentSpec;

    @Override
    protected byte[] engineGetEncoded() {
        byte[] arrby = new DHParameter(this.currentSpec.getP(), this.currentSpec.getG(), this.currentSpec.getL());
        try {
            arrby = arrby.getEncoded("DER");
            return arrby;
        }
        catch (IOException iOException) {
            throw new RuntimeException("Error encoding DHParameters");
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
        if (algorithmParameterSpec instanceof DHParameterSpec) {
            this.currentSpec = (DHParameterSpec)algorithmParameterSpec;
            return;
        }
        throw new InvalidParameterSpecException("DHParameterSpec required to initialise a Diffie-Hellman algorithm parameters object");
    }

    @Override
    protected void engineInit(byte[] object) throws IOException {
        try {
            DHParameterSpec dHParameterSpec;
            DHParameterSpec dHParameterSpec2;
            object = DHParameter.getInstance(object);
            this.currentSpec = ((DHParameter)object).getL() != null ? (dHParameterSpec2 = new DHParameterSpec(((DHParameter)object).getP(), ((DHParameter)object).getG(), ((DHParameter)object).getL().intValue())) : (dHParameterSpec = new DHParameterSpec(((DHParameter)object).getP(), ((DHParameter)object).getG()));
            return;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new IOException("Not a valid DH Parameter encoding.");
        }
        catch (ClassCastException classCastException) {
            throw new IOException("Not a valid DH Parameter encoding.");
        }
    }

    @Override
    protected void engineInit(byte[] object, String string) throws IOException {
        if (this.isASN1FormatString(string)) {
            this.engineInit((byte[])object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown parameter format ");
        ((StringBuilder)object).append(string);
        throw new IOException(((StringBuilder)object).toString());
    }

    @Override
    protected String engineToString() {
        return "Diffie-Hellman Parameters";
    }

    protected boolean isASN1FormatString(String string) {
        boolean bl = string == null || string.equals("ASN.1");
        return bl;
    }

    protected AlgorithmParameterSpec localEngineGetParameterSpec(Class class_) throws InvalidParameterSpecException {
        if (class_ != DHParameterSpec.class && class_ != AlgorithmParameterSpec.class) {
            throw new InvalidParameterSpecException("unknown parameter spec passed to DH parameters object.");
        }
        return this.currentSpec;
    }
}

