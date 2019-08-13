/*
 * Decompiled with CFR 0.145.
 */
package android.security.net.config;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.security.net.config.ConfigSource;
import android.security.net.config.Domain;
import android.security.net.config.NetworkSecurityConfig;
import android.security.net.config.XmlConfigSource;
import android.util.Log;
import android.util.Pair;
import java.util.Set;

public class ManifestConfigSource
implements ConfigSource {
    private static final boolean DBG = true;
    private static final String LOG_TAG = "NetworkSecurityConfig";
    private final ApplicationInfo mApplicationInfo;
    private ConfigSource mConfigSource;
    private final Context mContext;
    private final Object mLock = new Object();

    public ManifestConfigSource(Context context) {
        this.mContext = context;
        this.mApplicationInfo = new ApplicationInfo(context.getApplicationInfo());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ConfigSource getConfigSource() {
        Object object = this.mLock;
        synchronized (object) {
            Object object2;
            if (this.mConfigSource != null) {
                return this.mConfigSource;
            }
            int n = this.mApplicationInfo.networkSecurityConfigRes;
            boolean bl = true;
            boolean bl2 = true;
            if (n != 0) {
                if ((this.mApplicationInfo.flags & 2) == 0) {
                    bl2 = false;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Using Network Security Config from resource ");
                ((StringBuilder)object2).append(this.mContext.getResources().getResourceEntryName(n));
                ((StringBuilder)object2).append(" debugBuild: ");
                ((StringBuilder)object2).append(bl2);
                Log.d(LOG_TAG, ((StringBuilder)object2).toString());
                object2 = new XmlConfigSource(this.mContext, n, this.mApplicationInfo);
                this.mConfigSource = object2;
                return this.mConfigSource;
            } else {
                Log.d(LOG_TAG, "No Network Security Config specified, using platform default");
                bl2 = (this.mApplicationInfo.flags & 134217728) != 0 && !this.mApplicationInfo.isInstantApp() ? bl : false;
                object2 = new DefaultConfigSource(bl2, this.mApplicationInfo);
            }
            this.mConfigSource = object2;
            return this.mConfigSource;
        }
    }

    @Override
    public NetworkSecurityConfig getDefaultConfig() {
        return this.getConfigSource().getDefaultConfig();
    }

    @Override
    public Set<Pair<Domain, NetworkSecurityConfig>> getPerDomainConfigs() {
        return this.getConfigSource().getPerDomainConfigs();
    }

    private static final class DefaultConfigSource
    implements ConfigSource {
        private final NetworkSecurityConfig mDefaultConfig;

        DefaultConfigSource(boolean bl, ApplicationInfo applicationInfo) {
            this.mDefaultConfig = NetworkSecurityConfig.getDefaultBuilder(applicationInfo).setCleartextTrafficPermitted(bl).build();
        }

        @Override
        public NetworkSecurityConfig getDefaultConfig() {
            return this.mDefaultConfig;
        }

        @Override
        public Set<Pair<Domain, NetworkSecurityConfig>> getPerDomainConfigs() {
            return null;
        }
    }

}

