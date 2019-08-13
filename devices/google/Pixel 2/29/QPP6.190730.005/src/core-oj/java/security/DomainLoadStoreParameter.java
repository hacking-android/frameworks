/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.net.URI;
import java.security.KeyStore;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class DomainLoadStoreParameter
implements KeyStore.LoadStoreParameter {
    private final URI configuration;
    private final Map<String, KeyStore.ProtectionParameter> protectionParams;

    public DomainLoadStoreParameter(URI uRI, Map<String, KeyStore.ProtectionParameter> map) {
        if (uRI != null && map != null) {
            this.configuration = uRI;
            this.protectionParams = Collections.unmodifiableMap(new HashMap<String, KeyStore.ProtectionParameter>(map));
            return;
        }
        throw new NullPointerException("invalid null input");
    }

    public URI getConfiguration() {
        return this.configuration;
    }

    @Override
    public KeyStore.ProtectionParameter getProtectionParameter() {
        return null;
    }

    public Map<String, KeyStore.ProtectionParameter> getProtectionParams() {
        return this.protectionParams;
    }
}

