/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internalandroidapi;

import com.android.okhttp.Cache;
import com.android.okhttp.Request;
import com.android.okhttp.Response;
import com.android.okhttp.internal.InternalCache;
import com.android.okhttp.internal.http.CacheRequest;
import com.android.okhttp.internal.huc.JavaApiConverter;
import com.android.okhttp.internalandroidapi.HasCacheHolder;
import java.io.IOException;
import java.net.CacheResponse;
import java.net.URI;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public final class AndroidResponseCacheAdapter {
    private final HasCacheHolder.CacheHolder cacheHolder;
    private final Cache okHttpCache;

    public AndroidResponseCacheAdapter(HasCacheHolder.CacheHolder cacheHolder) {
        this.cacheHolder = cacheHolder;
        this.okHttpCache = cacheHolder.getCache();
    }

    public void close() throws IOException {
        this.okHttpCache.close();
    }

    public void delete() throws IOException {
        this.okHttpCache.delete();
    }

    public void flush() throws IOException {
        this.okHttpCache.flush();
    }

    public CacheResponse get(URI object, String string, Map<String, List<String>> map) throws IOException {
        object = JavaApiConverter.createOkRequest((URI)object, string, map);
        if ((object = this.okHttpCache.internalCache.get((Request)object)) == null) {
            return null;
        }
        return JavaApiConverter.createJavaCacheResponse((Response)object);
    }

    public HasCacheHolder.CacheHolder getCacheHolder() {
        return this.cacheHolder;
    }

    public int getHitCount() {
        return this.okHttpCache.getHitCount();
    }

    public long getMaxSize() {
        return this.okHttpCache.getMaxSize();
    }

    public int getNetworkCount() {
        return this.okHttpCache.getNetworkCount();
    }

    public int getRequestCount() {
        return this.okHttpCache.getRequestCount();
    }

    public long getSize() throws IOException {
        return this.okHttpCache.getSize();
    }

    public java.net.CacheRequest put(URI object, URLConnection uRLConnection) throws IOException {
        if ((object = JavaApiConverter.createOkResponseForCachePut((URI)object, uRLConnection)) == null) {
            return null;
        }
        if ((object = this.okHttpCache.internalCache.put((Response)object)) == null) {
            return null;
        }
        return JavaApiConverter.createJavaCacheRequest((CacheRequest)object);
    }
}

