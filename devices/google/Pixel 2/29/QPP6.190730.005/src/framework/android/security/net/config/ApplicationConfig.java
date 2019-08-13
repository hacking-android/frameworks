/*
 * Decompiled with CFR 0.145.
 */
package android.security.net.config;

import android.security.net.config.ConfigSource;
import android.security.net.config.Domain;
import android.security.net.config.NetworkSecurityConfig;
import android.security.net.config.RootTrustManager;
import android.util.Pair;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import javax.net.ssl.X509TrustManager;

public final class ApplicationConfig {
    private static ApplicationConfig sInstance;
    private static Object sLock;
    private ConfigSource mConfigSource;
    private Set<Pair<Domain, NetworkSecurityConfig>> mConfigs;
    private NetworkSecurityConfig mDefaultConfig;
    private boolean mInitialized;
    private final Object mLock = new Object();
    private X509TrustManager mTrustManager;

    static {
        sLock = new Object();
    }

    public ApplicationConfig(ConfigSource configSource) {
        this.mConfigSource = configSource;
        this.mInitialized = false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void ensureInitialized() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mInitialized) {
                return;
            }
            this.mConfigs = this.mConfigSource.getPerDomainConfigs();
            this.mDefaultConfig = this.mConfigSource.getDefaultConfig();
            this.mConfigSource = null;
            RootTrustManager rootTrustManager = new RootTrustManager(this);
            this.mTrustManager = rootTrustManager;
            this.mInitialized = true;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ApplicationConfig getDefaultInstance() {
        Object object = sLock;
        synchronized (object) {
            return sInstance;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setDefaultInstance(ApplicationConfig applicationConfig) {
        Object object = sLock;
        synchronized (object) {
            sInstance = applicationConfig;
            return;
        }
    }

    public NetworkSecurityConfig getConfigForHostname(String pair) {
        this.ensureInitialized();
        if (pair != null && !((String)((Object)pair)).isEmpty() && this.mConfigs != null) {
            if (((String)((Object)pair)).charAt(0) != '.') {
                pair = ((String)((Object)pair)).toLowerCase(Locale.US);
                String string2 = pair;
                if (((String)((Object)pair)).charAt(((String)((Object)pair)).length() - 1) == '.') {
                    string2 = ((String)((Object)pair)).substring(0, ((String)((Object)pair)).length() - 1);
                }
                Pair<Domain, NetworkSecurityConfig> pair2 = null;
                for (Pair<Domain, NetworkSecurityConfig> pair3 : this.mConfigs) {
                    Domain domain = (Domain)pair3.first;
                    pair = (NetworkSecurityConfig)pair3.second;
                    if (domain.hostname.equals(string2)) {
                        return pair;
                    }
                    pair = pair2;
                    if (domain.subdomainsIncluded) {
                        pair = pair2;
                        if (string2.endsWith(domain.hostname)) {
                            pair = pair2;
                            if (string2.charAt(string2.length() - domain.hostname.length() - 1) == '.') {
                                if (pair2 == null) {
                                    pair = pair3;
                                } else {
                                    pair = pair2;
                                    if (domain.hostname.length() > ((Domain)pair2.first).hostname.length()) {
                                        pair = pair3;
                                    }
                                }
                            }
                        }
                    }
                    pair2 = pair;
                }
                if (pair2 != null) {
                    return (NetworkSecurityConfig)pair2.second;
                }
                return this.mDefaultConfig;
            }
            throw new IllegalArgumentException("hostname must not begin with a .");
        }
        return this.mDefaultConfig;
    }

    public X509TrustManager getTrustManager() {
        this.ensureInitialized();
        return this.mTrustManager;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void handleTrustStorageUpdate() {
        Object object = this.mLock;
        synchronized (object) {
            if (!this.mInitialized) {
                return;
            }
            this.mDefaultConfig.handleTrustStorageUpdate();
            if (this.mConfigs != null) {
                HashSet<NetworkSecurityConfig> hashSet = new HashSet<NetworkSecurityConfig>(this.mConfigs.size());
                for (Pair<Domain, NetworkSecurityConfig> pair : this.mConfigs) {
                    if (!hashSet.add((NetworkSecurityConfig)pair.second)) continue;
                    ((NetworkSecurityConfig)pair.second).handleTrustStorageUpdate();
                }
            }
            return;
        }
    }

    public boolean hasPerDomainConfigs() {
        this.ensureInitialized();
        Set<Pair<Domain, NetworkSecurityConfig>> set = this.mConfigs;
        boolean bl = set != null && !set.isEmpty();
        return bl;
    }

    public boolean isCleartextTrafficPermitted() {
        this.ensureInitialized();
        Object object = this.mConfigs;
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                if (((NetworkSecurityConfig)((Pair)object.next()).second).isCleartextTrafficPermitted()) continue;
                return false;
            }
        }
        return this.mDefaultConfig.isCleartextTrafficPermitted();
    }

    public boolean isCleartextTrafficPermitted(String string2) {
        return this.getConfigForHostname(string2).isCleartextTrafficPermitted();
    }
}

