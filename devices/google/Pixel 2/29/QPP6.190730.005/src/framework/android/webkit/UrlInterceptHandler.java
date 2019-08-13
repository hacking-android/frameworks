/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.UnsupportedAppUsage;
import android.webkit.CacheManager;
import android.webkit.PluginData;
import java.util.Map;

@Deprecated
public interface UrlInterceptHandler {
    @Deprecated
    @UnsupportedAppUsage
    public PluginData getPluginData(String var1, Map<String, String> var2);

    @Deprecated
    @UnsupportedAppUsage
    public CacheManager.CacheResult service(String var1, Map<String, String> var2);
}

