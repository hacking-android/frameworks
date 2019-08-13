/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internalandroidapi;

import com.android.okhttp.ConnectionPool;
import com.android.okhttp.Dns;
import com.android.okhttp.HttpHandler;
import com.android.okhttp.HttpsHandler;
import com.android.okhttp.OkHttpClient;
import com.android.okhttp.OkUrlFactories;
import com.android.okhttp.OkUrlFactory;
import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;

public final class HttpURLConnectionFactory {
    private ConnectionPool connectionPool;
    private Dns dns;

    private URLConnection internalOpenConnection(URL serializable, SocketFactory socketFactory, Proxy proxy) throws IOException {
        Object object;
        block9 : {
            block8 : {
                block7 : {
                    object = ((URL)serializable).getProtocol();
                    if (!((String)object).equals("http")) break block7;
                    object = HttpHandler.createHttpOkUrlFactory(proxy);
                    break block8;
                }
                if (!((String)object).equals("https")) break block9;
                object = HttpsHandler.createHttpsOkUrlFactory(proxy);
            }
            OkHttpClient okHttpClient = ((OkUrlFactory)object).client();
            Object object2 = this.connectionPool;
            if (object2 != null) {
                okHttpClient.setConnectionPool((ConnectionPool)object2);
            }
            if ((object2 = this.dns) != null) {
                okHttpClient.setDns((Dns)object2);
            }
            if (socketFactory != null) {
                okHttpClient.setSocketFactory(socketFactory);
            }
            if (proxy == null) {
                return ((OkUrlFactory)object).open((URL)serializable);
            }
            return OkUrlFactories.open((OkUrlFactory)object, (URL)serializable, proxy);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Invalid URL or unrecognized protocol ");
        ((StringBuilder)serializable).append((String)object);
        throw new MalformedURLException(((StringBuilder)serializable).toString());
    }

    public URLConnection openConnection(URL uRL) throws IOException {
        return this.internalOpenConnection(uRL, null, null);
    }

    public URLConnection openConnection(URL uRL, Proxy proxy) throws IOException {
        Objects.requireNonNull(proxy);
        return this.internalOpenConnection(uRL, null, proxy);
    }

    public URLConnection openConnection(URL uRL, SocketFactory socketFactory) throws IOException {
        Objects.requireNonNull(socketFactory);
        return this.internalOpenConnection(uRL, socketFactory, null);
    }

    public URLConnection openConnection(URL uRL, SocketFactory socketFactory, Proxy proxy) throws IOException {
        Objects.requireNonNull(socketFactory);
        Objects.requireNonNull(proxy);
        return this.internalOpenConnection(uRL, socketFactory, proxy);
    }

    public void setDns(com.android.okhttp.internalandroidapi.Dns dns) {
        Objects.requireNonNull(dns);
        this.dns = new DnsAdapter(dns);
    }

    public void setNewConnectionPool(int n, long l, TimeUnit timeUnit) {
        this.connectionPool = new ConnectionPool(n, l, timeUnit);
    }

    static final class DnsAdapter
    implements Dns {
        private final com.android.okhttp.internalandroidapi.Dns adaptee;

        DnsAdapter(com.android.okhttp.internalandroidapi.Dns dns) {
            this.adaptee = Objects.requireNonNull(dns);
        }

        public boolean equals(Object object) {
            if (!(object instanceof DnsAdapter)) {
                return false;
            }
            return this.adaptee.equals(((DnsAdapter)object).adaptee);
        }

        public int hashCode() {
            return DnsAdapter.class.hashCode() * 31 + this.adaptee.hashCode();
        }

        @Override
        public List<InetAddress> lookup(String string) throws UnknownHostException {
            return this.adaptee.lookup(string);
        }

        public String toString() {
            return this.adaptee.toString();
        }
    }

}

