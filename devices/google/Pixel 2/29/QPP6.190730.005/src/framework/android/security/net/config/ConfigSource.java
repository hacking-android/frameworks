/*
 * Decompiled with CFR 0.145.
 */
package android.security.net.config;

import android.security.net.config.Domain;
import android.security.net.config.NetworkSecurityConfig;
import android.util.Pair;
import java.util.Set;

public interface ConfigSource {
    public NetworkSecurityConfig getDefaultConfig();

    public Set<Pair<Domain, NetworkSecurityConfig>> getPerDomainConfigs();
}

