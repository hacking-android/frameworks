/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.okhttp.internal.huc;

import com.android.okhttp.Handshake;
import com.android.okhttp.OkHttpClient;
import com.android.okhttp.Response;
import com.android.okhttp.internal.URLFilter;
import com.android.okhttp.internal.http.HttpEngine;
import com.android.okhttp.internal.huc.DelegatingHttpsURLConnection;
import com.android.okhttp.internal.huc.HttpURLConnectionImpl;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.Permission;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;

public final class HttpsURLConnectionImpl
extends DelegatingHttpsURLConnection {
    @UnsupportedAppUsage
    private final HttpURLConnectionImpl delegate;

    public HttpsURLConnectionImpl(HttpURLConnectionImpl httpURLConnectionImpl) {
        super(httpURLConnectionImpl);
        this.delegate = httpURLConnectionImpl;
    }

    public HttpsURLConnectionImpl(URL uRL, OkHttpClient okHttpClient) {
        this(new HttpURLConnectionImpl(uRL, okHttpClient));
    }

    public HttpsURLConnectionImpl(URL uRL, OkHttpClient okHttpClient, URLFilter uRLFilter) {
        this(new HttpURLConnectionImpl(uRL, okHttpClient, uRLFilter));
    }

    @Override
    public long getContentLengthLong() {
        return this.delegate.getContentLengthLong();
    }

    @Override
    public long getHeaderFieldLong(String string, long l) {
        return this.delegate.getHeaderFieldLong(string, l);
    }

    @Override
    public HostnameVerifier getHostnameVerifier() {
        return this.delegate.client.getHostnameVerifier();
    }

    @Override
    public SSLSocketFactory getSSLSocketFactory() {
        return this.delegate.client.getSslSocketFactory();
    }

    @Override
    protected Handshake handshake() {
        if (this.delegate.httpEngine != null) {
            Handshake handshake = this.delegate.httpEngine.hasResponse() ? this.delegate.httpEngine.getResponse().handshake() : this.delegate.handshake;
            return handshake;
        }
        throw new IllegalStateException("Connection has not yet been established");
    }

    @Override
    public void setFixedLengthStreamingMode(long l) {
        this.delegate.setFixedLengthStreamingMode(l);
    }

    @Override
    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.delegate.client.setHostnameVerifier(hostnameVerifier);
    }

    @Override
    public void setSSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
        if (sSLSocketFactory != null) {
            this.delegate.client.setSslSocketFactory(sSLSocketFactory);
            return;
        }
        throw new IllegalArgumentException("sslSocketFactory == null");
    }
}

