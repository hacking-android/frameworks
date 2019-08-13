/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.Cache;
import com.android.okhttp.OkCacheContainer;
import com.android.okhttp.OkHttpClient;
import com.android.okhttp.OkUrlFactory;
import com.android.okhttp.internal.InternalCache;
import com.android.okhttp.internal.huc.CacheAdapter;
import com.android.okhttp.internalandroidapi.HasCacheHolder;
import java.net.ResponseCache;

public class AndroidInternal {
    private AndroidInternal() {
    }

    public static void setResponseCache(OkUrlFactory object, ResponseCache responseCache) {
        OkHttpClient okHttpClient = ((OkUrlFactory)object).client();
        if (responseCache instanceof OkCacheContainer) {
            okHttpClient.setCache(((OkCacheContainer)((Object)responseCache)).getCache());
        } else if (responseCache instanceof HasCacheHolder) {
            okHttpClient.setCache(((HasCacheHolder)((Object)responseCache)).getCacheHolder().getCache());
        } else {
            object = responseCache != null ? new CacheAdapter(responseCache) : null;
            okHttpClient.setInternalCache((InternalCache)object);
        }
    }
}

