/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import com.android.org.conscrypt.OpenSSLECGroupContext;
import com.android.org.conscrypt.Platform;
import java.io.IOException;
import java.security.AlgorithmParametersSpi;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.InvalidParameterSpecException;

public class ECParameters
extends AlgorithmParametersSpi {
    private OpenSSLECGroupContext curve;

    @Override
    protected byte[] engineGetEncoded() throws IOException {
        return NativeCrypto.EC_KEY_marshal_curve_name(this.curve.getNativeRef());
    }

    @Override
    protected byte[] engineGetEncoded(String string) throws IOException {
        if (string != null && !string.equals("ASN.1")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported format: ");
            stringBuilder.append(string);
            throw new IOException(stringBuilder.toString());
        }
        return this.engineGetEncoded();
    }

    @Override
    protected <T extends AlgorithmParameterSpec> T engineGetParameterSpec(Class<T> class_) throws InvalidParameterSpecException {
        if (class_ == ECParameterSpec.class) {
            return (T)this.curve.getECParameterSpec();
        }
        if (class_ == ECGenParameterSpec.class) {
            return (T)new ECGenParameterSpec(Platform.getCurveName(this.curve.getECParameterSpec()));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported class: ");
        stringBuilder.append(class_);
        throw new InvalidParameterSpecException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void engineInit(AlgorithmParameterSpec object) throws InvalidParameterSpecException {
        if (object instanceof ECGenParameterSpec) {
            Object object2 = OpenSSLECGroupContext.getCurveByName((String)(object = ((ECGenParameterSpec)object).getName()));
            if (object2 != null) {
                this.curve = object2;
                return;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Unknown EC curve name: ");
            ((StringBuilder)object2).append((String)object);
            throw new InvalidParameterSpecException(((StringBuilder)object2).toString());
        }
        if (!(object instanceof ECParameterSpec)) throw new InvalidParameterSpecException("Only ECParameterSpec and ECGenParameterSpec are supported");
        object = (ECParameterSpec)object;
        try {
            Object object3 = OpenSSLECGroupContext.getInstance((ECParameterSpec)object);
            if (object3 != null) {
                this.curve = object3;
                return;
            }
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("Unknown EC curve: ");
            ((StringBuilder)object3).append(object);
            InvalidParameterSpecException invalidParameterSpecException = new InvalidParameterSpecException(((StringBuilder)object3).toString());
            throw invalidParameterSpecException;
        }
        catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
            throw new InvalidParameterSpecException(invalidAlgorithmParameterException.getMessage());
        }
    }

    @Override
    protected void engineInit(byte[] arrby) throws IOException {
        long l = NativeCrypto.EC_KEY_parse_curve_name(arrby);
        if (l != 0L) {
            this.curve = new OpenSSLECGroupContext(new NativeRef.EC_GROUP(l));
            return;
        }
        throw new IOException("Error reading ASN.1 encoding");
    }

    @Override
    protected void engineInit(byte[] object, String string) throws IOException {
        if (string != null && !string.equals("ASN.1")) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unsupported format: ");
            ((StringBuilder)object).append(string);
            throw new IOException(((StringBuilder)object).toString());
        }
        this.engineInit((byte[])object);
    }

    @Override
    protected String engineToString() {
        return "Conscrypt EC AlgorithmParameters";
    }
}

