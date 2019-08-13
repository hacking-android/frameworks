/*
 * Decompiled with CFR 0.145.
 */
package android.security.net.config;

import android.security.net.config.CertificateSource;
import android.security.net.config.CertificatesEntryRef;
import android.security.net.config.ConfigSource;
import android.security.net.config.Domain;
import android.security.net.config.KeyStoreCertificateSource;
import android.security.net.config.NetworkSecurityConfig;
import android.util.Pair;
import java.security.KeyStore;
import java.util.Set;

class KeyStoreConfigSource
implements ConfigSource {
    private final NetworkSecurityConfig mConfig;

    public KeyStoreConfigSource(KeyStore keyStore) {
        this.mConfig = new NetworkSecurityConfig.Builder().addCertificatesEntryRef(new CertificatesEntryRef(new KeyStoreCertificateSource(keyStore), false)).build();
    }

    @Override
    public NetworkSecurityConfig getDefaultConfig() {
        return this.mConfig;
    }

    @Override
    public Set<Pair<Domain, NetworkSecurityConfig>> getPerDomainConfigs() {
        return null;
    }
}

