/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.CertificatePinner;
import com.android.okhttp.ConfigAwareConnectionPool;
import com.android.okhttp.ConnectionPool;
import com.android.okhttp.ConnectionSpec;
import com.android.okhttp.ConnectionSpecs;
import com.android.okhttp.HttpHandler;
import com.android.okhttp.OkHttpClient;
import com.android.okhttp.OkUrlFactories;
import com.android.okhttp.OkUrlFactory;
import com.android.okhttp.Protocol;
import java.net.Proxy;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public final class HttpsHandler
extends HttpHandler {
    private static final List<Protocol> HTTP_1_1_ONLY;
    private static final ConnectionSpec TLS_CONNECTION_SPEC;
    private final ConfigAwareConnectionPool configAwareConnectionPool = ConfigAwareConnectionPool.getInstance();

    static {
        TLS_CONNECTION_SPEC = ConnectionSpecs.builder(true).allEnabledCipherSuites().allEnabledTlsVersions().supportsTlsExtensions(true).build();
        HTTP_1_1_ONLY = Collections.singletonList(Protocol.HTTP_1_1);
    }

    public static OkUrlFactory createHttpsOkUrlFactory(Proxy object) {
        object = HttpHandler.createHttpOkUrlFactory((Proxy)object);
        OkUrlFactories.setUrlFilter((OkUrlFactory)object, null);
        OkHttpClient okHttpClient = ((OkUrlFactory)object).client();
        okHttpClient.setProtocols(HTTP_1_1_ONLY);
        okHttpClient.setConnectionSpecs(Collections.singletonList(TLS_CONNECTION_SPEC));
        okHttpClient.setCertificatePinner(CertificatePinner.DEFAULT);
        ((OkUrlFactory)object).client().setHostnameVerifier(HttpsURLConnection.getDefaultHostnameVerifier());
        okHttpClient.setSslSocketFactory(HttpsURLConnection.getDefaultSSLSocketFactory());
        return object;
    }

    @Override
    protected int getDefaultPort() {
        return 443;
    }

    @Override
    protected OkUrlFactory newOkUrlFactory(Proxy object) {
        object = HttpsHandler.createHttpsOkUrlFactory((Proxy)object);
        ((OkUrlFactory)object).client().setConnectionPool(this.configAwareConnectionPool.get());
        return object;
    }
}

