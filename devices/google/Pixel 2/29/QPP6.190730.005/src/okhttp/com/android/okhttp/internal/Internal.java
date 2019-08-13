/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.okhttp.internal;

import com.android.okhttp.Address;
import com.android.okhttp.Call;
import com.android.okhttp.Callback;
import com.android.okhttp.ConnectionPool;
import com.android.okhttp.ConnectionSpec;
import com.android.okhttp.Headers;
import com.android.okhttp.HttpUrl;
import com.android.okhttp.OkHttpClient;
import com.android.okhttp.internal.InternalCache;
import com.android.okhttp.internal.RouteDatabase;
import com.android.okhttp.internal.http.StreamAllocation;
import com.android.okhttp.internal.io.RealConnection;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;

public abstract class Internal {
    @UnsupportedAppUsage
    public static Internal instance;
    public static final Logger logger;

    static {
        logger = Logger.getLogger(OkHttpClient.class.getName());
    }

    public static void initializeInstanceForTests() {
        new OkHttpClient();
    }

    @UnsupportedAppUsage
    public abstract void addLenient(Headers.Builder var1, String var2);

    @UnsupportedAppUsage
    public abstract void addLenient(Headers.Builder var1, String var2, String var3);

    @UnsupportedAppUsage
    public abstract void apply(ConnectionSpec var1, SSLSocket var2, boolean var3);

    @UnsupportedAppUsage
    public abstract StreamAllocation callEngineGetStreamAllocation(Call var1);

    @UnsupportedAppUsage
    public abstract void callEnqueue(Call var1, Callback var2, boolean var3);

    @UnsupportedAppUsage
    public abstract boolean connectionBecameIdle(ConnectionPool var1, RealConnection var2);

    @UnsupportedAppUsage
    public abstract RealConnection get(ConnectionPool var1, Address var2, StreamAllocation var3);

    @UnsupportedAppUsage
    public abstract HttpUrl getHttpUrlChecked(String var1) throws MalformedURLException, UnknownHostException;

    @UnsupportedAppUsage
    public abstract InternalCache internalCache(OkHttpClient var1);

    @UnsupportedAppUsage
    public abstract void put(ConnectionPool var1, RealConnection var2);

    @UnsupportedAppUsage
    public abstract RouteDatabase routeDatabase(ConnectionPool var1);

    @UnsupportedAppUsage
    public abstract void setCache(OkHttpClient var1, InternalCache var2);
}

