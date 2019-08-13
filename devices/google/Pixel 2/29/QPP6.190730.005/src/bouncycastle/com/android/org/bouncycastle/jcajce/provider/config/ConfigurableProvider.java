/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.config;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import java.util.Map;

public interface ConfigurableProvider {
    public static final String ACCEPTABLE_EC_CURVES = "acceptableEcCurves";
    public static final String ADDITIONAL_EC_PARAMETERS = "additionalEcParameters";
    public static final String DH_DEFAULT_PARAMS = "DhDefaultParams";
    public static final String EC_IMPLICITLY_CA = "ecImplicitlyCa";
    public static final String THREAD_LOCAL_DH_DEFAULT_PARAMS = "threadLocalDhDefaultParams";
    public static final String THREAD_LOCAL_EC_IMPLICITLY_CA = "threadLocalEcImplicitlyCa";

    public void addAlgorithm(String var1, ASN1ObjectIdentifier var2, String var3);

    public void addAlgorithm(String var1, String var2);

    public void addAttributes(String var1, Map<String, String> var2);

    public void addKeyInfoConverter(ASN1ObjectIdentifier var1, AsymmetricKeyInfoConverter var2);

    public boolean hasAlgorithm(String var1, String var2);

    public void setParameter(String var1, Object var2);
}

