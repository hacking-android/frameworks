/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.net.NetworkSecurityPolicy
 */
package com.android.okhttp;

import com.android.okhttp.AndroidInternal;
import com.android.okhttp.ConfigAwareConnectionPool;
import com.android.okhttp.ConnectionPool;
import com.android.okhttp.ConnectionSpec;
import com.android.okhttp.OkHttpClient;
import com.android.okhttp.OkUrlFactories;
import com.android.okhttp.OkUrlFactory;
import com.android.okhttp.internal.URLFilter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.ResponseCache;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import libcore.net.NetworkSecurityPolicy;

public class HttpHandler
extends URLStreamHandler {
    private static final CleartextURLFilter CLEARTEXT_FILTER;
    private static final List<ConnectionSpec> CLEARTEXT_ONLY;
    private final ConfigAwareConnectionPool configAwareConnectionPool = ConfigAwareConnectionPool.getInstance();

    static {
        CLEARTEXT_ONLY = Collections.singletonList(ConnectionSpec.CLEARTEXT);
        CLEARTEXT_FILTER = new CleartextURLFilter();
    }

    public static OkUrlFactory createHttpOkUrlFactory(Proxy object) {
        Object object2 = new OkHttpClient();
        ((OkHttpClient)object2).setConnectTimeout(0L, TimeUnit.MILLISECONDS);
        ((OkHttpClient)object2).setReadTimeout(0L, TimeUnit.MILLISECONDS);
        ((OkHttpClient)object2).setWriteTimeout(0L, TimeUnit.MILLISECONDS);
        ((OkHttpClient)object2).setFollowRedirects(HttpURLConnection.getFollowRedirects());
        ((OkHttpClient)object2).setFollowSslRedirects(false);
        ((OkHttpClient)object2).setConnectionSpecs(CLEARTEXT_ONLY);
        if (object != null) {
            ((OkHttpClient)object2).setProxy((Proxy)object);
        }
        object = new OkUrlFactory((OkHttpClient)object2);
        OkUrlFactories.setUrlFilter((OkUrlFactory)object, CLEARTEXT_FILTER);
        object2 = ResponseCache.getDefault();
        if (object2 != null) {
            AndroidInternal.setResponseCache((OkUrlFactory)object, (ResponseCache)object2);
        }
        return object;
    }

    @Override
    protected int getDefaultPort() {
        return 80;
    }

    protected OkUrlFactory newOkUrlFactory(Proxy object) {
        object = HttpHandler.createHttpOkUrlFactory((Proxy)object);
        ((OkUrlFactory)object).client().setConnectionPool(this.configAwareConnectionPool.get());
        return object;
    }

    @Override
    protected URLConnection openConnection(URL uRL) throws IOException {
        return this.newOkUrlFactory(null).open(uRL);
    }

    @Override
    protected URLConnection openConnection(URL uRL, Proxy proxy) throws IOException {
        if (uRL != null && proxy != null) {
            return this.newOkUrlFactory(proxy).open(uRL);
        }
        throw new IllegalArgumentException("url == null || proxy == null");
    }

    private static final class CleartextURLFilter
    implements URLFilter {
        private CleartextURLFilter() {
        }

        @Override
        public void checkURLPermitted(URL object) throws IOException {
            object = ((URL)object).getHost();
            if (NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted((String)object)) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cleartext HTTP traffic to ");
            stringBuilder.append((String)object);
            stringBuilder.append(" not permitted");
            throw new IOException(stringBuilder.toString());
        }
    }

}

