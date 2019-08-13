/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.KeyManagerFactoryImpl;
import com.android.org.conscrypt.TrustManagerFactoryImpl;
import com.android.org.conscrypt.TrustedCertificateKeyStoreSpi;
import java.security.Provider;

public final class JSSEProvider
extends Provider {
    private static final long serialVersionUID = 3075686092260669675L;

    public JSSEProvider() {
        super("HarmonyJSSE", 1.0, "Harmony JSSE Provider");
        this.put("KeyManagerFactory.PKIX", KeyManagerFactoryImpl.class.getName());
        this.put("Alg.Alias.KeyManagerFactory.X509", "PKIX");
        this.put("TrustManagerFactory.PKIX", TrustManagerFactoryImpl.class.getName());
        this.put("Alg.Alias.TrustManagerFactory.X509", "PKIX");
        this.put("KeyStore.AndroidCAStore", TrustedCertificateKeyStoreSpi.class.getName());
    }
}

