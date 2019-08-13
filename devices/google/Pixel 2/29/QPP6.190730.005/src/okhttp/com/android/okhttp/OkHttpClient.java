/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.okhttp;

import com.android.okhttp.Address;
import com.android.okhttp.Authenticator;
import com.android.okhttp.Cache;
import com.android.okhttp.Call;
import com.android.okhttp.Callback;
import com.android.okhttp.CertificatePinner;
import com.android.okhttp.ConnectionPool;
import com.android.okhttp.ConnectionSpec;
import com.android.okhttp.Dispatcher;
import com.android.okhttp.Dns;
import com.android.okhttp.Headers;
import com.android.okhttp.HttpUrl;
import com.android.okhttp.Interceptor;
import com.android.okhttp.Protocol;
import com.android.okhttp.Request;
import com.android.okhttp.internal.Internal;
import com.android.okhttp.internal.InternalCache;
import com.android.okhttp.internal.RouteDatabase;
import com.android.okhttp.internal.Util;
import com.android.okhttp.internal.http.AuthenticatorAdapter;
import com.android.okhttp.internal.http.HttpEngine;
import com.android.okhttp.internal.http.StreamAllocation;
import com.android.okhttp.internal.io.RealConnection;
import com.android.okhttp.internal.tls.OkHostnameVerifier;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.net.CookieHandler;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class OkHttpClient
implements Cloneable {
    private static final List<ConnectionSpec> DEFAULT_CONNECTION_SPECS;
    @UnsupportedAppUsage
    private static final List<Protocol> DEFAULT_PROTOCOLS;
    private static SSLSocketFactory defaultSslSocketFactory;
    private Authenticator authenticator;
    private Cache cache;
    private CertificatePinner certificatePinner;
    private int connectTimeout = 10000;
    @UnsupportedAppUsage
    private ConnectionPool connectionPool;
    private List<ConnectionSpec> connectionSpecs;
    private CookieHandler cookieHandler;
    private Dispatcher dispatcher;
    @UnsupportedAppUsage
    private Dns dns;
    private boolean followRedirects = true;
    private boolean followSslRedirects = true;
    private HostnameVerifier hostnameVerifier;
    private final List<Interceptor> interceptors = new ArrayList<Interceptor>();
    private InternalCache internalCache;
    private final List<Interceptor> networkInterceptors = new ArrayList<Interceptor>();
    private List<Protocol> protocols;
    private Proxy proxy;
    private ProxySelector proxySelector;
    private int readTimeout = 10000;
    private boolean retryOnConnectionFailure = true;
    private final RouteDatabase routeDatabase;
    private SocketFactory socketFactory;
    private SSLSocketFactory sslSocketFactory;
    private int writeTimeout = 10000;

    static {
        DEFAULT_PROTOCOLS = Util.immutableList(new Protocol[]{Protocol.HTTP_2, Protocol.SPDY_3, Protocol.HTTP_1_1});
        DEFAULT_CONNECTION_SPECS = Util.immutableList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT);
        Internal.instance = new Internal(){

            @Override
            public void addLenient(Headers.Builder builder, String string) {
                builder.addLenient(string);
            }

            @Override
            public void addLenient(Headers.Builder builder, String string, String string2) {
                builder.addLenient(string, string2);
            }

            @Override
            public void apply(ConnectionSpec connectionSpec, SSLSocket sSLSocket, boolean bl) {
                connectionSpec.apply(sSLSocket, bl);
            }

            @Override
            public StreamAllocation callEngineGetStreamAllocation(Call call) {
                return call.engine.streamAllocation;
            }

            @Override
            public void callEnqueue(Call call, Callback callback, boolean bl) {
                call.enqueue(callback, bl);
            }

            @Override
            public boolean connectionBecameIdle(ConnectionPool connectionPool, RealConnection realConnection) {
                return connectionPool.connectionBecameIdle(realConnection);
            }

            @Override
            public RealConnection get(ConnectionPool connectionPool, Address address, StreamAllocation streamAllocation) {
                return connectionPool.get(address, streamAllocation);
            }

            @Override
            public HttpUrl getHttpUrlChecked(String string) throws MalformedURLException, UnknownHostException {
                return HttpUrl.getChecked(string);
            }

            @Override
            public InternalCache internalCache(OkHttpClient okHttpClient) {
                return okHttpClient.internalCache();
            }

            @Override
            public void put(ConnectionPool connectionPool, RealConnection realConnection) {
                connectionPool.put(realConnection);
            }

            @Override
            public RouteDatabase routeDatabase(ConnectionPool connectionPool) {
                return connectionPool.routeDatabase;
            }

            @Override
            public void setCache(OkHttpClient okHttpClient, InternalCache internalCache) {
                okHttpClient.setInternalCache(internalCache);
            }
        };
    }

    @UnsupportedAppUsage
    public OkHttpClient() {
        this.routeDatabase = new RouteDatabase();
        this.dispatcher = new Dispatcher();
    }

    private OkHttpClient(OkHttpClient okHttpClient) {
        this.routeDatabase = okHttpClient.routeDatabase;
        this.dispatcher = okHttpClient.dispatcher;
        this.proxy = okHttpClient.proxy;
        this.protocols = okHttpClient.protocols;
        this.connectionSpecs = okHttpClient.connectionSpecs;
        this.interceptors.addAll(okHttpClient.interceptors);
        this.networkInterceptors.addAll(okHttpClient.networkInterceptors);
        this.proxySelector = okHttpClient.proxySelector;
        this.cookieHandler = okHttpClient.cookieHandler;
        this.cache = okHttpClient.cache;
        Object object = this.cache;
        object = object != null ? ((Cache)object).internalCache : okHttpClient.internalCache;
        this.internalCache = object;
        this.socketFactory = okHttpClient.socketFactory;
        this.sslSocketFactory = okHttpClient.sslSocketFactory;
        this.hostnameVerifier = okHttpClient.hostnameVerifier;
        this.certificatePinner = okHttpClient.certificatePinner;
        this.authenticator = okHttpClient.authenticator;
        this.connectionPool = okHttpClient.connectionPool;
        this.dns = okHttpClient.dns;
        this.followSslRedirects = okHttpClient.followSslRedirects;
        this.followRedirects = okHttpClient.followRedirects;
        this.retryOnConnectionFailure = okHttpClient.retryOnConnectionFailure;
        this.connectTimeout = okHttpClient.connectTimeout;
        this.readTimeout = okHttpClient.readTimeout;
        this.writeTimeout = okHttpClient.writeTimeout;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private SSLSocketFactory getDefaultSSLSocketFactory() {
        synchronized (this) {
            Object object = defaultSslSocketFactory;
            if (object != null) return defaultSslSocketFactory;
            try {
                object = SSLContext.getInstance("TLS");
                ((SSLContext)object).init(null, null, null);
                defaultSslSocketFactory = ((SSLContext)object).getSocketFactory();
                return defaultSslSocketFactory;
            }
            catch (GeneralSecurityException generalSecurityException) {
                AssertionError assertionError = new AssertionError();
                throw assertionError;
            }
        }
    }

    public OkHttpClient cancel(Object object) {
        this.getDispatcher().cancel(object);
        return this;
    }

    public OkHttpClient clone() {
        return new OkHttpClient(this);
    }

    OkHttpClient copyWithDefaults() {
        OkHttpClient okHttpClient = new OkHttpClient(this);
        if (okHttpClient.proxySelector == null) {
            okHttpClient.proxySelector = ProxySelector.getDefault();
        }
        if (okHttpClient.cookieHandler == null) {
            okHttpClient.cookieHandler = CookieHandler.getDefault();
        }
        if (okHttpClient.socketFactory == null) {
            okHttpClient.socketFactory = SocketFactory.getDefault();
        }
        if (okHttpClient.sslSocketFactory == null) {
            okHttpClient.sslSocketFactory = this.getDefaultSSLSocketFactory();
        }
        if (okHttpClient.hostnameVerifier == null) {
            okHttpClient.hostnameVerifier = OkHostnameVerifier.INSTANCE;
        }
        if (okHttpClient.certificatePinner == null) {
            okHttpClient.certificatePinner = CertificatePinner.DEFAULT;
        }
        if (okHttpClient.authenticator == null) {
            okHttpClient.authenticator = AuthenticatorAdapter.INSTANCE;
        }
        if (okHttpClient.connectionPool == null) {
            okHttpClient.connectionPool = ConnectionPool.getDefault();
        }
        if (okHttpClient.protocols == null) {
            okHttpClient.protocols = DEFAULT_PROTOCOLS;
        }
        if (okHttpClient.connectionSpecs == null) {
            okHttpClient.connectionSpecs = DEFAULT_CONNECTION_SPECS;
        }
        if (okHttpClient.dns == null) {
            okHttpClient.dns = Dns.SYSTEM;
        }
        return okHttpClient;
    }

    public Authenticator getAuthenticator() {
        return this.authenticator;
    }

    public Cache getCache() {
        return this.cache;
    }

    public CertificatePinner getCertificatePinner() {
        return this.certificatePinner;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    @UnsupportedAppUsage
    public ConnectionPool getConnectionPool() {
        return this.connectionPool;
    }

    public List<ConnectionSpec> getConnectionSpecs() {
        return this.connectionSpecs;
    }

    @UnsupportedAppUsage
    public CookieHandler getCookieHandler() {
        return this.cookieHandler;
    }

    public Dispatcher getDispatcher() {
        return this.dispatcher;
    }

    public Dns getDns() {
        return this.dns;
    }

    public boolean getFollowRedirects() {
        return this.followRedirects;
    }

    public boolean getFollowSslRedirects() {
        return this.followSslRedirects;
    }

    @UnsupportedAppUsage
    public HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public List<Protocol> getProtocols() {
        return this.protocols;
    }

    @UnsupportedAppUsage
    public Proxy getProxy() {
        return this.proxy;
    }

    @UnsupportedAppUsage
    public ProxySelector getProxySelector() {
        return this.proxySelector;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public boolean getRetryOnConnectionFailure() {
        return this.retryOnConnectionFailure;
    }

    public SocketFactory getSocketFactory() {
        return this.socketFactory;
    }

    @UnsupportedAppUsage
    public SSLSocketFactory getSslSocketFactory() {
        return this.sslSocketFactory;
    }

    public int getWriteTimeout() {
        return this.writeTimeout;
    }

    public List<Interceptor> interceptors() {
        return this.interceptors;
    }

    InternalCache internalCache() {
        return this.internalCache;
    }

    public List<Interceptor> networkInterceptors() {
        return this.networkInterceptors;
    }

    public Call newCall(Request request) {
        return new Call(this, request);
    }

    RouteDatabase routeDatabase() {
        return this.routeDatabase;
    }

    public OkHttpClient setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
        return this;
    }

    public OkHttpClient setCache(Cache cache) {
        this.cache = cache;
        this.internalCache = null;
        return this;
    }

    public OkHttpClient setCertificatePinner(CertificatePinner certificatePinner) {
        this.certificatePinner = certificatePinner;
        return this;
    }

    public void setConnectTimeout(long l, TimeUnit timeUnit) {
        if (l >= 0L) {
            if (timeUnit != null) {
                long l2 = timeUnit.toMillis(l);
                if (l2 <= Integer.MAX_VALUE) {
                    if (l2 == 0L && l > 0L) {
                        throw new IllegalArgumentException("Timeout too small.");
                    }
                    this.connectTimeout = (int)l2;
                    return;
                }
                throw new IllegalArgumentException("Timeout too large.");
            }
            throw new IllegalArgumentException("unit == null");
        }
        throw new IllegalArgumentException("timeout < 0");
    }

    public OkHttpClient setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        return this;
    }

    public OkHttpClient setConnectionSpecs(List<ConnectionSpec> list) {
        this.connectionSpecs = Util.immutableList(list);
        return this;
    }

    public OkHttpClient setCookieHandler(CookieHandler cookieHandler) {
        this.cookieHandler = cookieHandler;
        return this;
    }

    public OkHttpClient setDispatcher(Dispatcher dispatcher) {
        if (dispatcher != null) {
            this.dispatcher = dispatcher;
            return this;
        }
        throw new IllegalArgumentException("dispatcher == null");
    }

    public OkHttpClient setDns(Dns dns) {
        this.dns = dns;
        return this;
    }

    public void setFollowRedirects(boolean bl) {
        this.followRedirects = bl;
    }

    public OkHttpClient setFollowSslRedirects(boolean bl) {
        this.followSslRedirects = bl;
        return this;
    }

    public OkHttpClient setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }

    void setInternalCache(InternalCache internalCache) {
        this.internalCache = internalCache;
        this.cache = null;
    }

    @UnsupportedAppUsage
    public OkHttpClient setProtocols(List<Protocol> list) {
        if ((list = Util.immutableList(list)).contains((Object)Protocol.HTTP_1_1)) {
            if (!list.contains((Object)Protocol.HTTP_1_0)) {
                if (!list.contains(null)) {
                    this.protocols = Util.immutableList(list);
                    return this;
                }
                throw new IllegalArgumentException("protocols must not contain null");
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("protocols must not contain http/1.0: ");
            stringBuilder.append(list);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("protocols doesn't contain http/1.1: ");
        stringBuilder.append(list);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public OkHttpClient setProxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    public OkHttpClient setProxySelector(ProxySelector proxySelector) {
        this.proxySelector = proxySelector;
        return this;
    }

    public void setReadTimeout(long l, TimeUnit timeUnit) {
        if (l >= 0L) {
            if (timeUnit != null) {
                long l2 = timeUnit.toMillis(l);
                if (l2 <= Integer.MAX_VALUE) {
                    if (l2 == 0L && l > 0L) {
                        throw new IllegalArgumentException("Timeout too small.");
                    }
                    this.readTimeout = (int)l2;
                    return;
                }
                throw new IllegalArgumentException("Timeout too large.");
            }
            throw new IllegalArgumentException("unit == null");
        }
        throw new IllegalArgumentException("timeout < 0");
    }

    @UnsupportedAppUsage
    public void setRetryOnConnectionFailure(boolean bl) {
        this.retryOnConnectionFailure = bl;
    }

    public OkHttpClient setSocketFactory(SocketFactory socketFactory) {
        this.socketFactory = socketFactory;
        return this;
    }

    public OkHttpClient setSslSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.sslSocketFactory = sSLSocketFactory;
        return this;
    }

    public void setWriteTimeout(long l, TimeUnit timeUnit) {
        if (l >= 0L) {
            if (timeUnit != null) {
                long l2 = timeUnit.toMillis(l);
                if (l2 <= Integer.MAX_VALUE) {
                    if (l2 == 0L && l > 0L) {
                        throw new IllegalArgumentException("Timeout too small.");
                    }
                    this.writeTimeout = (int)l2;
                    return;
                }
                throw new IllegalArgumentException("Timeout too large.");
            }
            throw new IllegalArgumentException("unit == null");
        }
        throw new IllegalArgumentException("timeout < 0");
    }

}

