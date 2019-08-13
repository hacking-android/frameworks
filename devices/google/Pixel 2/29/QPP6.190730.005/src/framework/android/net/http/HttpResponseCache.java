/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.okhttp.internalandroidapi.AndroidResponseCacheAdapter
 *  com.android.okhttp.internalandroidapi.HasCacheHolder
 *  com.android.okhttp.internalandroidapi.HasCacheHolder$CacheHolder
 */
package android.net.http;

import com.android.okhttp.internalandroidapi.AndroidResponseCacheAdapter;
import com.android.okhttp.internalandroidapi.HasCacheHolder;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public final class HttpResponseCache
extends ResponseCache
implements HasCacheHolder,
Closeable {
    private final AndroidResponseCacheAdapter mDelegate;

    private HttpResponseCache(AndroidResponseCacheAdapter androidResponseCacheAdapter) {
        this.mDelegate = androidResponseCacheAdapter;
    }

    public static HttpResponseCache getInstalled() {
        ResponseCache responseCache = ResponseCache.getDefault();
        if (responseCache instanceof HttpResponseCache) {
            return (HttpResponseCache)responseCache;
        }
        return null;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static HttpResponseCache install(File file, long l) throws IOException {
        synchronized (HttpResponseCache.class) {
            void var1_1;
            ResponseCache responseCache = ResponseCache.getDefault();
            if (responseCache instanceof HttpResponseCache) {
                boolean bl = ((HttpResponseCache)(responseCache = (HttpResponseCache)responseCache)).getCacheHolder().isEquivalent(file, (long)var1_1);
                if (bl) {
                    return responseCache;
                }
                ((HttpResponseCache)responseCache).close();
            }
            responseCache = HasCacheHolder.CacheHolder.create((File)file, (long)var1_1);
            file = new AndroidResponseCacheAdapter((HasCacheHolder.CacheHolder)responseCache);
            responseCache = new HttpResponseCache((AndroidResponseCacheAdapter)file);
            ResponseCache.setDefault(responseCache);
            return responseCache;
        }
    }

    @Override
    public void close() throws IOException {
        if (ResponseCache.getDefault() == this) {
            ResponseCache.setDefault(null);
        }
        this.mDelegate.close();
    }

    public void delete() throws IOException {
        if (ResponseCache.getDefault() == this) {
            ResponseCache.setDefault(null);
        }
        this.mDelegate.delete();
    }

    public void flush() {
        try {
            this.mDelegate.flush();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    @Override
    public CacheResponse get(URI uRI, String string2, Map<String, List<String>> map) throws IOException {
        return this.mDelegate.get(uRI, string2, map);
    }

    public HasCacheHolder.CacheHolder getCacheHolder() {
        return this.mDelegate.getCacheHolder();
    }

    public int getHitCount() {
        return this.mDelegate.getHitCount();
    }

    public int getNetworkCount() {
        return this.mDelegate.getNetworkCount();
    }

    public int getRequestCount() {
        return this.mDelegate.getRequestCount();
    }

    public long maxSize() {
        return this.mDelegate.getMaxSize();
    }

    @Override
    public CacheRequest put(URI uRI, URLConnection uRLConnection) throws IOException {
        return this.mDelegate.put(uRI, uRLConnection);
    }

    public long size() {
        try {
            long l = this.mDelegate.getSize();
            return l;
        }
        catch (IOException iOException) {
            return -1L;
        }
    }
}

