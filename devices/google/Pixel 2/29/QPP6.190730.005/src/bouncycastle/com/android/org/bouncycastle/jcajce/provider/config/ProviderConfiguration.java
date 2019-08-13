/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.config;

import com.android.org.bouncycastle.jce.spec.ECParameterSpec;
import java.security.spec.DSAParameterSpec;
import java.util.Map;
import java.util.Set;
import javax.crypto.spec.DHParameterSpec;

public interface ProviderConfiguration {
    public Set getAcceptableNamedCurves();

    public Map getAdditionalECParameters();

    public DHParameterSpec getDHDefaultParameters(int var1);

    public DSAParameterSpec getDSADefaultParameters(int var1);

    public ECParameterSpec getEcImplicitlyCa();
}

