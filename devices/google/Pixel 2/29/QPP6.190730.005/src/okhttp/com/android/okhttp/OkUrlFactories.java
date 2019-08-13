/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.OkUrlFactory;
import com.android.okhttp.internal.URLFilter;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

public class OkUrlFactories {
    private OkUrlFactories() {
    }

    public static HttpURLConnection open(OkUrlFactory okUrlFactory, URL uRL, Proxy proxy) {
        return okUrlFactory.open(uRL, proxy);
    }

    public static void setUrlFilter(OkUrlFactory okUrlFactory, URLFilter uRLFilter) {
        okUrlFactory.setUrlFilter(uRLFilter);
    }
}

