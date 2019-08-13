/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.tls;

import java.security.cert.X509Certificate;

public interface TrustRootIndex {
    public X509Certificate findByIssuerAndSignature(X509Certificate var1);
}

