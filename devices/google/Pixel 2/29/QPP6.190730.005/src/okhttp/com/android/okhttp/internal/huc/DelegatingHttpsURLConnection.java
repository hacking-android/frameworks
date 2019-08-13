/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.huc;

import com.android.okhttp.Handshake;
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
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;

abstract class DelegatingHttpsURLConnection
extends HttpsURLConnection {
    private final HttpURLConnection delegate;

    public DelegatingHttpsURLConnection(HttpURLConnection httpURLConnection) {
        super(httpURLConnection.getURL());
        this.delegate = httpURLConnection;
    }

    @Override
    public void addRequestProperty(String string, String string2) {
        this.delegate.addRequestProperty(string, string2);
    }

    @Override
    public void connect() throws IOException {
        this.connected = true;
        this.delegate.connect();
    }

    @Override
    public void disconnect() {
        this.delegate.disconnect();
    }

    @Override
    public boolean getAllowUserInteraction() {
        return this.delegate.getAllowUserInteraction();
    }

    @Override
    public String getCipherSuite() {
        Object object = this.handshake();
        object = object != null ? ((Handshake)object).cipherSuite() : null;
        return object;
    }

    @Override
    public int getConnectTimeout() {
        return this.delegate.getConnectTimeout();
    }

    @Override
    public Object getContent() throws IOException {
        return this.delegate.getContent();
    }

    @Override
    public Object getContent(Class[] arrclass) throws IOException {
        return this.delegate.getContent(arrclass);
    }

    @Override
    public String getContentEncoding() {
        return this.delegate.getContentEncoding();
    }

    @Override
    public int getContentLength() {
        return this.delegate.getContentLength();
    }

    @Override
    public String getContentType() {
        return this.delegate.getContentType();
    }

    @Override
    public long getDate() {
        return this.delegate.getDate();
    }

    @Override
    public boolean getDefaultUseCaches() {
        return this.delegate.getDefaultUseCaches();
    }

    @Override
    public boolean getDoInput() {
        return this.delegate.getDoInput();
    }

    @Override
    public boolean getDoOutput() {
        return this.delegate.getDoOutput();
    }

    @Override
    public InputStream getErrorStream() {
        return this.delegate.getErrorStream();
    }

    @Override
    public long getExpiration() {
        return this.delegate.getExpiration();
    }

    @Override
    public String getHeaderField(int n) {
        return this.delegate.getHeaderField(n);
    }

    @Override
    public String getHeaderField(String string) {
        return this.delegate.getHeaderField(string);
    }

    @Override
    public long getHeaderFieldDate(String string, long l) {
        return this.delegate.getHeaderFieldDate(string, l);
    }

    @Override
    public int getHeaderFieldInt(String string, int n) {
        return this.delegate.getHeaderFieldInt(string, n);
    }

    @Override
    public String getHeaderFieldKey(int n) {
        return this.delegate.getHeaderFieldKey(n);
    }

    @Override
    public Map<String, List<String>> getHeaderFields() {
        return this.delegate.getHeaderFields();
    }

    @Override
    public abstract HostnameVerifier getHostnameVerifier();

    @Override
    public long getIfModifiedSince() {
        return this.delegate.getIfModifiedSince();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.delegate.getInputStream();
    }

    @Override
    public boolean getInstanceFollowRedirects() {
        return this.delegate.getInstanceFollowRedirects();
    }

    @Override
    public long getLastModified() {
        return this.delegate.getLastModified();
    }

    @Override
    public Certificate[] getLocalCertificates() {
        Object object = this.handshake();
        Certificate[] arrcertificate = null;
        if (object == null) {
            return null;
        }
        if (!(object = ((Handshake)object).localCertificates()).isEmpty()) {
            arrcertificate = object.toArray(new Certificate[object.size()]);
        }
        return arrcertificate;
    }

    @Override
    public Principal getLocalPrincipal() {
        Object object = this.handshake();
        object = object != null ? ((Handshake)object).localPrincipal() : null;
        return object;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return this.delegate.getOutputStream();
    }

    @Override
    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        Object object = this.handshake();
        object = object != null ? ((Handshake)object).peerPrincipal() : null;
        return object;
    }

    @Override
    public Permission getPermission() throws IOException {
        return this.delegate.getPermission();
    }

    @Override
    public int getReadTimeout() {
        return this.delegate.getReadTimeout();
    }

    @Override
    public String getRequestMethod() {
        return this.delegate.getRequestMethod();
    }

    @Override
    public Map<String, List<String>> getRequestProperties() {
        return this.delegate.getRequestProperties();
    }

    @Override
    public String getRequestProperty(String string) {
        return this.delegate.getRequestProperty(string);
    }

    @Override
    public int getResponseCode() throws IOException {
        return this.delegate.getResponseCode();
    }

    @Override
    public String getResponseMessage() throws IOException {
        return this.delegate.getResponseMessage();
    }

    @Override
    public abstract SSLSocketFactory getSSLSocketFactory();

    @Override
    public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
        Object object = this.handshake();
        Certificate[] arrcertificate = null;
        if (object == null) {
            return null;
        }
        if (!(object = ((Handshake)object).peerCertificates()).isEmpty()) {
            arrcertificate = object.toArray(new Certificate[object.size()]);
        }
        return arrcertificate;
    }

    @Override
    public URL getURL() {
        return this.delegate.getURL();
    }

    @Override
    public boolean getUseCaches() {
        return this.delegate.getUseCaches();
    }

    protected abstract Handshake handshake();

    @Override
    public void setAllowUserInteraction(boolean bl) {
        this.delegate.setAllowUserInteraction(bl);
    }

    @Override
    public void setChunkedStreamingMode(int n) {
        this.delegate.setChunkedStreamingMode(n);
    }

    @Override
    public void setConnectTimeout(int n) {
        this.delegate.setConnectTimeout(n);
    }

    @Override
    public void setDefaultUseCaches(boolean bl) {
        this.delegate.setDefaultUseCaches(bl);
    }

    @Override
    public void setDoInput(boolean bl) {
        this.delegate.setDoInput(bl);
    }

    @Override
    public void setDoOutput(boolean bl) {
        this.delegate.setDoOutput(bl);
    }

    @Override
    public void setFixedLengthStreamingMode(int n) {
        this.delegate.setFixedLengthStreamingMode(n);
    }

    @Override
    public abstract void setHostnameVerifier(HostnameVerifier var1);

    @Override
    public void setIfModifiedSince(long l) {
        this.delegate.setIfModifiedSince(l);
    }

    @Override
    public void setInstanceFollowRedirects(boolean bl) {
        this.delegate.setInstanceFollowRedirects(bl);
    }

    @Override
    public void setReadTimeout(int n) {
        this.delegate.setReadTimeout(n);
    }

    @Override
    public void setRequestMethod(String string) throws ProtocolException {
        this.delegate.setRequestMethod(string);
    }

    @Override
    public void setRequestProperty(String string, String string2) {
        this.delegate.setRequestProperty(string, string2);
    }

    @Override
    public abstract void setSSLSocketFactory(SSLSocketFactory var1);

    @Override
    public void setUseCaches(boolean bl) {
        this.delegate.setUseCaches(bl);
    }

    @Override
    public String toString() {
        return this.delegate.toString();
    }

    @Override
    public boolean usingProxy() {
        return this.delegate.usingProxy();
    }
}

