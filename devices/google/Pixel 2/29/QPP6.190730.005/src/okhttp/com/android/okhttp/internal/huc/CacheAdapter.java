/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.huc;

import com.android.okhttp.Request;
import com.android.okhttp.Response;
import com.android.okhttp.internal.InternalCache;
import com.android.okhttp.internal.http.CacheRequest;
import com.android.okhttp.internal.http.CacheStrategy;
import com.android.okhttp.internal.huc.JavaApiConverter;
import com.android.okhttp.okio.Okio;
import com.android.okhttp.okio.Sink;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.CacheResponse;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public final class CacheAdapter
implements InternalCache {
    private final ResponseCache delegate;

    public CacheAdapter(ResponseCache responseCache) {
        this.delegate = responseCache;
    }

    private CacheResponse getJavaCachedResponse(Request request) throws IOException {
        Map<String, List<String>> map = JavaApiConverter.extractJavaHeaders(request);
        return this.delegate.get(request.uri(), request.method(), map);
    }

    @Override
    public Response get(Request request) throws IOException {
        CacheResponse cacheResponse = this.getJavaCachedResponse(request);
        if (cacheResponse == null) {
            return null;
        }
        return JavaApiConverter.createOkResponseForCacheGet(request, cacheResponse);
    }

    public ResponseCache getDelegate() {
        return this.delegate;
    }

    @Override
    public CacheRequest put(Response object) throws IOException {
        URI uRI = ((Response)object).request().uri();
        object = JavaApiConverter.createJavaUrlConnectionForCachePut((Response)object);
        if ((object = this.delegate.put(uRI, (URLConnection)object)) == null) {
            return null;
        }
        return new CacheRequest((java.net.CacheRequest)object){
            final /* synthetic */ java.net.CacheRequest val$request;
            {
                this.val$request = cacheRequest;
            }

            @Override
            public void abort() {
                this.val$request.abort();
            }

            @Override
            public Sink body() throws IOException {
                Closeable closeable = this.val$request.getBody();
                closeable = closeable != null ? Okio.sink(closeable) : null;
                return closeable;
            }
        };
    }

    @Override
    public void remove(Request request) throws IOException {
    }

    @Override
    public void trackConditionalCacheHit() {
    }

    @Override
    public void trackResponse(CacheStrategy cacheStrategy) {
    }

    @Override
    public void update(Response response, Response response2) throws IOException {
    }

}

