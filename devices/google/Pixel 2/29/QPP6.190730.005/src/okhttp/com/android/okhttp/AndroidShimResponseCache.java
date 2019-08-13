/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.Cache;
import com.android.okhttp.Request;
import com.android.okhttp.Response;
import com.android.okhttp.internal.InternalCache;
import com.android.okhttp.internal.http.CacheRequest;
import com.android.okhttp.internal.huc.JavaApiConverter;
import java.io.File;
import java.io.IOException;
import java.net.CacheResponse;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class AndroidShimResponseCache
extends ResponseCache {
    private final Cache delegate;

    private AndroidShimResponseCache(Cache cache) {
        this.delegate = cache;
    }

    public static AndroidShimResponseCache create(File file, long l) throws IOException {
        return new AndroidShimResponseCache(new Cache(file, l));
    }

    public void close() throws IOException {
        this.delegate.close();
    }

    public void delete() throws IOException {
        this.delegate.delete();
    }

    public void flush() throws IOException {
        this.delegate.flush();
    }

    @Override
    public CacheResponse get(URI object, String string, Map<String, List<String>> map) throws IOException {
        object = JavaApiConverter.createOkRequest((URI)object, string, map);
        if ((object = this.delegate.internalCache.get((Request)object)) == null) {
            return null;
        }
        return JavaApiConverter.createJavaCacheResponse((Response)object);
    }

    public Cache getCache() {
        return this.delegate;
    }

    public int getHitCount() {
        return this.delegate.getHitCount();
    }

    public int getNetworkCount() {
        return this.delegate.getNetworkCount();
    }

    public int getRequestCount() {
        return this.delegate.getRequestCount();
    }

    public boolean isEquivalent(File file, long l) {
        Cache cache = this.getCache();
        boolean bl = cache.getDirectory().equals(file) && cache.getMaxSize() == l && !cache.isClosed();
        return bl;
    }

    public long maxSize() {
        return this.delegate.getMaxSize();
    }

    @Override
    public java.net.CacheRequest put(URI object, URLConnection uRLConnection) throws IOException {
        if ((object = JavaApiConverter.createOkResponseForCachePut((URI)object, uRLConnection)) == null) {
            return null;
        }
        if ((object = this.delegate.internalCache.put((Response)object)) == null) {
            return null;
        }
        return JavaApiConverter.createJavaCacheRequest((CacheRequest)object);
    }

    public long size() throws IOException {
        return this.delegate.getSize();
    }
}

