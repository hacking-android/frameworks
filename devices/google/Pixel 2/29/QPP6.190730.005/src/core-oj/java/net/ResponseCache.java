/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.IOException;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.NetPermission;
import java.net.URI;
import java.net.URLConnection;
import java.security.Permission;
import java.util.List;
import java.util.Map;
import sun.security.util.SecurityConstants;

public abstract class ResponseCache {
    private static ResponseCache theResponseCache;

    public static ResponseCache getDefault() {
        synchronized (ResponseCache.class) {
            Object object = System.getSecurityManager();
            if (object != null) {
                ((SecurityManager)object).checkPermission(SecurityConstants.GET_RESPONSECACHE_PERMISSION);
            }
            object = theResponseCache;
            return object;
        }
    }

    public static void setDefault(ResponseCache responseCache) {
        synchronized (ResponseCache.class) {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkPermission(SecurityConstants.SET_RESPONSECACHE_PERMISSION);
            }
            theResponseCache = responseCache;
            return;
        }
    }

    public abstract CacheResponse get(URI var1, String var2, Map<String, List<String>> var3) throws IOException;

    public abstract CacheRequest put(URI var1, URLConnection var2) throws IOException;
}

