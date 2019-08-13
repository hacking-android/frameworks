/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.Authenticator;
import com.android.okhttp.CertificatePinner;
import com.android.okhttp.ConnectionSpec;
import com.android.okhttp.Dns;
import com.android.okhttp.HttpUrl;
import com.android.okhttp.Protocol;
import com.android.okhttp.internal.Util;
import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

public final class Address {
    final Authenticator authenticator;
    final CertificatePinner certificatePinner;
    final List<ConnectionSpec> connectionSpecs;
    final Dns dns;
    final HostnameVerifier hostnameVerifier;
    final List<Protocol> protocols;
    final Proxy proxy;
    final ProxySelector proxySelector;
    final SocketFactory socketFactory;
    final SSLSocketFactory sslSocketFactory;
    final HttpUrl url;

    public Address(String string, int n, Dns dns, SocketFactory socketFactory, SSLSocketFactory sSLSocketFactory, HostnameVerifier hostnameVerifier, CertificatePinner certificatePinner, Authenticator authenticator, Proxy proxy, List<Protocol> list, List<ConnectionSpec> list2, ProxySelector proxySelector) {
        HttpUrl.Builder builder = new HttpUrl.Builder();
        String string2 = sSLSocketFactory != null ? "https" : "http";
        this.url = builder.scheme(string2).host(string).port(n).build();
        if (dns != null) {
            this.dns = dns;
            if (socketFactory != null) {
                this.socketFactory = socketFactory;
                if (authenticator != null) {
                    this.authenticator = authenticator;
                    if (list != null) {
                        this.protocols = Util.immutableList(list);
                        if (list2 != null) {
                            this.connectionSpecs = Util.immutableList(list2);
                            if (proxySelector != null) {
                                this.proxySelector = proxySelector;
                                this.proxy = proxy;
                                this.sslSocketFactory = sSLSocketFactory;
                                this.hostnameVerifier = hostnameVerifier;
                                this.certificatePinner = certificatePinner;
                                return;
                            }
                            throw new IllegalArgumentException("proxySelector == null");
                        }
                        throw new IllegalArgumentException("connectionSpecs == null");
                    }
                    throw new IllegalArgumentException("protocols == null");
                }
                throw new IllegalArgumentException("authenticator == null");
            }
            throw new IllegalArgumentException("socketFactory == null");
        }
        throw new IllegalArgumentException("dns == null");
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Address;
        boolean bl2 = false;
        if (bl) {
            object = (Address)object;
            if (this.url.equals(((Address)object).url) && this.dns.equals(((Address)object).dns) && this.authenticator.equals(((Address)object).authenticator) && this.protocols.equals(((Address)object).protocols) && this.connectionSpecs.equals(((Address)object).connectionSpecs) && this.proxySelector.equals(((Address)object).proxySelector) && Util.equal(this.proxy, ((Address)object).proxy) && Util.equal(this.sslSocketFactory, ((Address)object).sslSocketFactory) && Util.equal(this.hostnameVerifier, ((Address)object).hostnameVerifier) && Util.equal(this.certificatePinner, ((Address)object).certificatePinner)) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public Authenticator getAuthenticator() {
        return this.authenticator;
    }

    public CertificatePinner getCertificatePinner() {
        return this.certificatePinner;
    }

    public List<ConnectionSpec> getConnectionSpecs() {
        return this.connectionSpecs;
    }

    public Dns getDns() {
        return this.dns;
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public List<Protocol> getProtocols() {
        return this.protocols;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public ProxySelector getProxySelector() {
        return this.proxySelector;
    }

    public SocketFactory getSocketFactory() {
        return this.socketFactory;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.sslSocketFactory;
    }

    @Deprecated
    public String getUriHost() {
        return this.url.host();
    }

    @Deprecated
    public int getUriPort() {
        return this.url.port();
    }

    public int hashCode() {
        int n = this.url.hashCode();
        int n2 = this.dns.hashCode();
        int n3 = this.authenticator.hashCode();
        int n4 = this.protocols.hashCode();
        int n5 = this.connectionSpecs.hashCode();
        int n6 = this.proxySelector.hashCode();
        Object object = this.proxy;
        int n7 = 0;
        int n8 = object != null ? ((Proxy)object).hashCode() : 0;
        object = this.sslSocketFactory;
        int n9 = object != null ? object.hashCode() : 0;
        object = this.hostnameVerifier;
        int n10 = object != null ? object.hashCode() : 0;
        object = this.certificatePinner;
        if (object != null) {
            n7 = object.hashCode();
        }
        return (((((((((17 * 31 + n) * 31 + n2) * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n8) * 31 + n9) * 31 + n10) * 31 + n7;
    }

    public HttpUrl url() {
        return this.url;
    }
}

