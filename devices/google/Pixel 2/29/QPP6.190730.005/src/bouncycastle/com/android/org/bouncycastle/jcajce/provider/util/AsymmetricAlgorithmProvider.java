/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.util;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.org.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import com.android.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;

public abstract class AsymmetricAlgorithmProvider
extends AlgorithmProvider {
    protected void addSignatureAlgorithm(ConfigurableProvider configurableProvider, String string, String charSequence, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Signature.");
        stringBuilder.append(string);
        configurableProvider.addAlgorithm(stringBuilder.toString(), (String)charSequence);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Alg.Alias.Signature.");
        ((StringBuilder)charSequence).append(aSN1ObjectIdentifier);
        configurableProvider.addAlgorithm(((StringBuilder)charSequence).toString(), string);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Alg.Alias.Signature.OID.");
        ((StringBuilder)charSequence).append(aSN1ObjectIdentifier);
        configurableProvider.addAlgorithm(((StringBuilder)charSequence).toString(), string);
    }

    protected void addSignatureAlgorithm(ConfigurableProvider configurableProvider, String charSequence, String charSequence2, String string, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        CharSequence charSequence3 = new StringBuilder();
        charSequence3.append((String)charSequence);
        charSequence3.append("WITH");
        charSequence3.append((String)charSequence2);
        charSequence3 = charSequence3.toString();
        CharSequence charSequence4 = new StringBuilder();
        charSequence4.append((String)charSequence);
        charSequence4.append("with");
        charSequence4.append((String)charSequence2);
        String string2 = charSequence4.toString();
        charSequence4 = new StringBuilder();
        charSequence4.append((String)charSequence);
        charSequence4.append("With");
        charSequence4.append((String)charSequence2);
        charSequence4 = charSequence4.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        stringBuilder.append("/");
        stringBuilder.append((String)charSequence2);
        charSequence = stringBuilder.toString();
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("Signature.");
        ((StringBuilder)charSequence2).append((String)charSequence3);
        configurableProvider.addAlgorithm(((StringBuilder)charSequence2).toString(), string);
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("Alg.Alias.Signature.");
        ((StringBuilder)charSequence2).append(string2);
        configurableProvider.addAlgorithm(((StringBuilder)charSequence2).toString(), (String)charSequence3);
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("Alg.Alias.Signature.");
        ((StringBuilder)charSequence2).append((String)charSequence4);
        configurableProvider.addAlgorithm(((StringBuilder)charSequence2).toString(), (String)charSequence3);
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("Alg.Alias.Signature.");
        ((StringBuilder)charSequence2).append((String)charSequence);
        configurableProvider.addAlgorithm(((StringBuilder)charSequence2).toString(), (String)charSequence3);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Alg.Alias.Signature.");
        ((StringBuilder)charSequence).append(aSN1ObjectIdentifier);
        configurableProvider.addAlgorithm(((StringBuilder)charSequence).toString(), (String)charSequence3);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Alg.Alias.Signature.OID.");
        ((StringBuilder)charSequence).append(aSN1ObjectIdentifier);
        configurableProvider.addAlgorithm(((StringBuilder)charSequence).toString(), (String)charSequence3);
    }

    protected void registerOid(ConfigurableProvider configurableProvider, ASN1ObjectIdentifier aSN1ObjectIdentifier, String string, AsymmetricKeyInfoConverter asymmetricKeyInfoConverter) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Alg.Alias.KeyFactory.");
        stringBuilder.append(aSN1ObjectIdentifier);
        configurableProvider.addAlgorithm(stringBuilder.toString(), string);
        stringBuilder = new StringBuilder();
        stringBuilder.append("Alg.Alias.KeyPairGenerator.");
        stringBuilder.append(aSN1ObjectIdentifier);
        configurableProvider.addAlgorithm(stringBuilder.toString(), string);
        configurableProvider.addKeyInfoConverter(aSN1ObjectIdentifier, asymmetricKeyInfoConverter);
    }

    protected void registerOidAlgorithmParameterGenerator(ConfigurableProvider configurableProvider, ASN1ObjectIdentifier aSN1ObjectIdentifier, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Alg.Alias.AlgorithmParameterGenerator.");
        stringBuilder.append(aSN1ObjectIdentifier);
        configurableProvider.addAlgorithm(stringBuilder.toString(), string);
        stringBuilder = new StringBuilder();
        stringBuilder.append("Alg.Alias.AlgorithmParameters.");
        stringBuilder.append(aSN1ObjectIdentifier);
        configurableProvider.addAlgorithm(stringBuilder.toString(), string);
    }

    protected void registerOidAlgorithmParameters(ConfigurableProvider configurableProvider, ASN1ObjectIdentifier aSN1ObjectIdentifier, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Alg.Alias.AlgorithmParameters.");
        stringBuilder.append(aSN1ObjectIdentifier);
        configurableProvider.addAlgorithm(stringBuilder.toString(), string);
    }
}

