/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.symmetric.util;

import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import com.android.org.bouncycastle.util.Arrays;
import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.IvParameterSpec;

public class IvAlgorithmParameters
extends BaseAlgorithmParameters {
    private byte[] iv;

    @Override
    protected byte[] engineGetEncoded() throws IOException {
        return this.engineGetEncoded("ASN.1");
    }

    @Override
    protected byte[] engineGetEncoded(String string) throws IOException {
        if (this.isASN1FormatString(string)) {
            return new DEROctetString(this.engineGetEncoded("RAW")).getEncoded();
        }
        if (string.equals("RAW")) {
            return Arrays.clone(this.iv);
        }
        return null;
    }

    @Override
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        if (algorithmParameterSpec instanceof IvParameterSpec) {
            this.iv = ((IvParameterSpec)algorithmParameterSpec).getIV();
            return;
        }
        throw new InvalidParameterSpecException("IvParameterSpec required to initialise a IV parameters algorithm parameters object");
    }

    @Override
    protected void engineInit(byte[] arrby) throws IOException {
        byte[] arrby2 = arrby;
        if (arrby.length % 8 != 0) {
            arrby2 = arrby;
            if (arrby[0] == 4) {
                arrby2 = arrby;
                if (arrby[1] == arrby.length - 2) {
                    arrby2 = ((ASN1OctetString)ASN1Primitive.fromByteArray(arrby)).getOctets();
                }
            }
        }
        this.iv = Arrays.clone(arrby2);
    }

    @Override
    protected void engineInit(byte[] arrby, String charSequence) throws IOException {
        if (this.isASN1FormatString((String)charSequence)) {
            try {
                this.engineInit(((ASN1OctetString)ASN1Primitive.fromByteArray(arrby)).getOctets());
                return;
            }
            catch (Exception exception) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Exception decoding: ");
                ((StringBuilder)charSequence).append(exception);
                throw new IOException(((StringBuilder)charSequence).toString());
            }
        }
        if (((String)charSequence).equals("RAW")) {
            this.engineInit(arrby);
            return;
        }
        throw new IOException("Unknown parameters format in IV parameters object");
    }

    @Override
    protected String engineToString() {
        return "IV Parameters";
    }

    @Override
    protected AlgorithmParameterSpec localEngineGetParameterSpec(Class class_) throws InvalidParameterSpecException {
        if (class_ != IvParameterSpec.class && class_ != AlgorithmParameterSpec.class) {
            throw new InvalidParameterSpecException("unknown parameter spec passed to IV parameters object.");
        }
        return new IvParameterSpec(this.iv);
    }
}

