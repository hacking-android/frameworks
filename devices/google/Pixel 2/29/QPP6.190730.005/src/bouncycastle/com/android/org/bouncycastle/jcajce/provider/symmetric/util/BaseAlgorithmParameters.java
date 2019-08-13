/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.symmetric.util;

import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

public abstract class BaseAlgorithmParameters
extends AlgorithmParametersSpi {
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
}

