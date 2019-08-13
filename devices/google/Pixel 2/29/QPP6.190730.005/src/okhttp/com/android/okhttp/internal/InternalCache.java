/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal;

import com.android.okhttp.Request;
import com.android.okhttp.Response;
import com.android.okhttp.internal.http.CacheRequest;
import com.android.okhttp.internal.http.CacheStrategy;
import java.io.IOException;

public interface InternalCache {
    public Response get(Request var1) throws IOException;

    public CacheRequest put(Response var1) throws IOException;

    public void remove(Request var1) throws IOException;

    public void trackConditionalCacheHit();

    public void trackResponse(CacheStrategy var1);

    public void update(Response var1, Response var2) throws IOException;
}

