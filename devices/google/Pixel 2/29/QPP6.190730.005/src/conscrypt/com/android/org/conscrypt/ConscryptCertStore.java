/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import java.security.cert.X509Certificate;
import java.util.Set;

public interface ConscryptCertStore {
    public Set<X509Certificate> findAllIssuers(X509Certificate var1);

    public X509Certificate getTrustAnchor(X509Certificate var1);
}

