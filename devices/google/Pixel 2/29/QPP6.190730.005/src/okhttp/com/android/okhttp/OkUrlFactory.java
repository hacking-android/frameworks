/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.OkHttpClient;
import com.android.okhttp.internal.URLFilter;
import com.android.okhttp.internal.huc.HttpURLConnectionImpl;
import com.android.okhttp.internal.huc.HttpsURLConnectionImpl;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public final class OkUrlFactory
implements URLStreamHandlerFactory,
Cloneable {
    private final OkHttpClient client;
    private URLFilter urlFilter;

    public OkUrlFactory(OkHttpClient okHttpClient) {
        this.client = okHttpClient;
    }

    public OkHttpClient client() {
        return this.client;
    }

    public OkUrlFactory clone() {
        return new OkUrlFactory(this.client.clone());
    }

    @Override
    public URLStreamHandler createURLStreamHandler(final String string) {
        if (!string.equals("http") && !string.equals("https")) {
            return null;
        }
        return new URLStreamHandler(){

            @Override
            protected int getDefaultPort() {
                if (string.equals("http")) {
                    return 80;
                }
                if (string.equals("https")) {
                    return 443;
                }
                throw new AssertionError();
            }

            @Override
            protected URLConnection openConnection(URL uRL) {
                return OkUrlFactory.this.open(uRL);
            }

            @Override
            protected URLConnection openConnection(URL uRL, Proxy proxy) {
                return OkUrlFactory.this.open(uRL, proxy);
            }
        };
    }

    public HttpURLConnection open(URL uRL) {
        return this.open(uRL, this.client.getProxy());
    }

    HttpURLConnection open(URL serializable, Proxy proxy) {
        String string = ((URL)serializable).getProtocol();
        OkHttpClient okHttpClient = this.client.copyWithDefaults();
        okHttpClient.setProxy(proxy);
        if (string.equals("http")) {
            return new HttpURLConnectionImpl((URL)serializable, okHttpClient, this.urlFilter);
        }
        if (string.equals("https")) {
            return new HttpsURLConnectionImpl((URL)serializable, okHttpClient, this.urlFilter);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Unexpected protocol: ");
        ((StringBuilder)serializable).append(string);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    void setUrlFilter(URLFilter uRLFilter) {
        this.urlFilter = uRLFilter;
    }

}

