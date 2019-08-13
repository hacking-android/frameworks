/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.digest;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.org.bouncycastle.jcajce.provider.util.AlgorithmProvider;

abstract class DigestAlgorithmProvider
extends AlgorithmProvider {
    DigestAlgorithmProvider() {
    }

    protected void addHMACAlgorithm(ConfigurableProvider configurableProvider, String string, String charSequence, String string2) {
        CharSequence charSequence2 = new StringBuilder();
        charSequence2.append("HMAC");
        charSequence2.append(string);
        charSequence2 = charSequence2.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Mac.");
        stringBuilder.append((String)charSequence2);
        configurableProvider.addAlgorithm(stringBuilder.toString(), (String)charSequence);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Alg.Alias.Mac.HMAC-");
        ((StringBuilder)charSequence).append(string);
        configurableProvider.addAlgorithm(((StringBuilder)charSequence).toString(), (String)charSequence2);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Alg.Alias.Mac.HMAC/");
        ((StringBuilder)charSequence).append(string);
        configurableProvider.addAlgorithm(((StringBuilder)charSequence).toString(), (String)charSequence2);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("KeyGenerator.");
        ((StringBuilder)charSequence).append((String)charSequence2);
        configurableProvider.addAlgorithm(((StringBuilder)charSequence).toString(), string2);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Alg.Alias.KeyGenerator.HMAC-");
        ((StringBuilder)charSequence).append(string);
        configurableProvider.addAlgorithm(((StringBuilder)charSequence).toString(), (String)charSequence2);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Alg.Alias.KeyGenerator.HMAC/");
        ((StringBuilder)charSequence).append(string);
        configurableProvider.addAlgorithm(((StringBuilder)charSequence).toString(), (String)charSequence2);
    }

    protected void addHMACAlias(ConfigurableProvider configurableProvider, String string, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HMAC");
        stringBuilder.append(string);
        string = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append("Alg.Alias.Mac.");
        stringBuilder.append(aSN1ObjectIdentifier);
        configurableProvider.addAlgorithm(stringBuilder.toString(), string);
        stringBuilder = new StringBuilder();
        stringBuilder.append("Alg.Alias.KeyGenerator.");
        stringBuilder.append(aSN1ObjectIdentifier);
        configurableProvider.addAlgorithm(stringBuilder.toString(), string);
    }
}

