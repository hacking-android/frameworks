/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.okhttp.internal.huc;

import com.android.okhttp.Cache;
import com.android.okhttp.Handshake;
import com.android.okhttp.Headers;
import com.android.okhttp.HttpUrl;
import com.android.okhttp.OkHttpClient;
import com.android.okhttp.Protocol;
import com.android.okhttp.Request;
import com.android.okhttp.RequestBody;
import com.android.okhttp.Response;
import com.android.okhttp.ResponseBody;
import com.android.okhttp.Route;
import com.android.okhttp.internal.Internal;
import com.android.okhttp.internal.InternalCache;
import com.android.okhttp.internal.Platform;
import com.android.okhttp.internal.URLFilter;
import com.android.okhttp.internal.Util;
import com.android.okhttp.internal.Version;
import com.android.okhttp.internal.http.HttpDate;
import com.android.okhttp.internal.http.HttpEngine;
import com.android.okhttp.internal.http.HttpMethod;
import com.android.okhttp.internal.http.OkHeaders;
import com.android.okhttp.internal.http.RetryableSink;
import com.android.okhttp.internal.http.StatusLine;
import com.android.okhttp.internal.http.StreamAllocation;
import com.android.okhttp.okio.BufferedSink;
import com.android.okhttp.okio.Sink;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.SocketPermission;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class HttpURLConnectionImpl
extends HttpURLConnection {
    private static final RequestBody EMPTY_REQUEST_BODY;
    private static final Set<String> METHODS;
    @UnsupportedAppUsage
    final OkHttpClient client;
    private long fixedContentLength = -1L;
    private int followUpCount;
    Handshake handshake;
    @UnsupportedAppUsage
    protected HttpEngine httpEngine;
    protected IOException httpEngineFailure;
    private Headers.Builder requestHeaders = new Headers.Builder();
    private Headers responseHeaders;
    private Route route;
    private URLFilter urlFilter;

    static {
        METHODS = new LinkedHashSet<String>(Arrays.asList("OPTIONS", "GET", "HEAD", "POST", "PUT", "DELETE", "TRACE", "PATCH"));
        EMPTY_REQUEST_BODY = RequestBody.create(null, new byte[0]);
    }

    public HttpURLConnectionImpl(URL uRL, OkHttpClient okHttpClient) {
        super(uRL);
        this.client = okHttpClient;
    }

    public HttpURLConnectionImpl(URL uRL, OkHttpClient okHttpClient, URLFilter uRLFilter) {
        this(uRL, okHttpClient);
        this.urlFilter = uRLFilter;
    }

    private String defaultUserAgent() {
        String string = System.getProperty("http.agent");
        string = string != null ? Util.toHumanReadableAscii(string) : Version.userAgent();
        return string;
    }

    /*
     * Exception decompiling
     */
    private boolean execute(boolean var1_1) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private Headers getHeaders() throws IOException {
        if (this.responseHeaders == null) {
            Response response = this.getResponse().getResponse();
            this.responseHeaders = response.headers().newBuilder().add(OkHeaders.SELECTED_PROTOCOL, response.protocol().toString()).add(OkHeaders.RESPONSE_SOURCE, HttpURLConnectionImpl.responseSourceHeader(response)).build();
        }
        return this.responseHeaders;
    }

    private HttpEngine getResponse() throws IOException {
        Object object;
        this.initHttpEngine();
        if (this.httpEngine.hasResponse()) {
            return this.httpEngine;
        }
        do {
            int n;
            StreamAllocation streamAllocation;
            if (!this.execute(true)) {
                continue;
            }
            Response response = this.httpEngine.getResponse();
            Request request = this.httpEngine.followUpRequest();
            if (request == null) {
                this.httpEngine.releaseStreamAllocation();
                return this.httpEngine;
            }
            this.followUpCount = n = this.followUpCount + 1;
            if (n > 20) break;
            this.url = request.url();
            this.requestHeaders = request.headers().newBuilder();
            object = this.httpEngine.getRequestBody();
            if (!request.method().equals(this.method)) {
                object = null;
            }
            if (object != null && !(object instanceof RetryableSink)) {
                throw new HttpRetryException("Cannot retry streamed HTTP body", this.responseCode);
            }
            StreamAllocation streamAllocation2 = streamAllocation = this.httpEngine.close();
            if (!this.httpEngine.sameConnection(request.httpUrl())) {
                streamAllocation.release();
                streamAllocation2 = null;
            }
            this.httpEngine = this.newHttpEngine(request.method(), streamAllocation2, (RetryableSink)object, response);
        } while (true);
        object = new StringBuilder();
        ((StringBuilder)object).append("Too many follow-up requests: ");
        ((StringBuilder)object).append(this.followUpCount);
        throw new ProtocolException(((StringBuilder)object).toString());
    }

    private void initHttpEngine() throws IOException {
        IOException iOException = this.httpEngineFailure;
        if (iOException == null) {
            if (this.httpEngine != null) {
                return;
            }
            this.connected = true;
            try {
                if (this.doOutput) {
                    if (this.method.equals("GET")) {
                        this.method = "POST";
                    } else if (!HttpMethod.permitsRequestBody(this.method)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(this.method);
                        stringBuilder.append(" does not support writing");
                        iOException = new ProtocolException(stringBuilder.toString());
                        throw iOException;
                    }
                }
                this.httpEngine = this.newHttpEngine(this.method, null, null, null);
                return;
            }
            catch (IOException iOException2) {
                this.httpEngineFailure = iOException2;
                throw iOException2;
            }
        }
        throw iOException;
    }

    private HttpEngine newHttpEngine(String object, StreamAllocation streamAllocation, RetryableSink retryableSink, Response response) throws MalformedURLException, UnknownHostException {
        Object object2 = HttpMethod.requiresRequestBody((String)object) ? EMPTY_REQUEST_BODY : null;
        Object object3 = this.getURL();
        object3 = Internal.instance.getHttpUrlChecked(((URL)object3).toString());
        object3 = new Request.Builder().url((HttpUrl)object3).method((String)object, (RequestBody)object2);
        object2 = this.requestHeaders.build();
        int n = ((Headers)object2).size();
        for (int i = 0; i < n; ++i) {
            ((Request.Builder)object3).addHeader(((Headers)object2).name(i), ((Headers)object2).value(i));
        }
        boolean bl = false;
        boolean bl2 = false;
        if (HttpMethod.permitsRequestBody((String)object)) {
            long l = this.fixedContentLength;
            if (l != -1L) {
                ((Request.Builder)object3).header("Content-Length", Long.toString(l));
            } else if (this.chunkLength > 0) {
                ((Request.Builder)object3).header("Transfer-Encoding", "chunked");
            } else {
                bl2 = true;
            }
            bl = bl2;
            if (((Headers)object2).get("Content-Type") == null) {
                ((Request.Builder)object3).header("Content-Type", "application/x-www-form-urlencoded");
                bl = bl2;
            }
        }
        if (((Headers)object2).get("User-Agent") == null) {
            ((Request.Builder)object3).header("User-Agent", this.defaultUserAgent());
        }
        object2 = ((Request.Builder)object3).build();
        object = this.client;
        if (Internal.instance.internalCache((OkHttpClient)object) != null && !this.getUseCaches()) {
            object = this.client.clone().setCache(null);
        }
        return new HttpEngine((OkHttpClient)object, (Request)object2, bl, true, false, streamAllocation, retryableSink, response);
    }

    private static String responseSourceHeader(Response response) {
        if (response.networkResponse() == null) {
            if (response.cacheResponse() == null) {
                return "NONE";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CACHE ");
            stringBuilder.append(response.code());
            return stringBuilder.toString();
        }
        if (response.cacheResponse() == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("NETWORK ");
            stringBuilder.append(response.code());
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CONDITIONAL_CACHE ");
        stringBuilder.append(response.networkResponse().code());
        return stringBuilder.toString();
    }

    private void setProtocols(String arrstring, boolean bl) {
        ArrayList<Protocol> arrayList = new ArrayList<Protocol>();
        if (bl) {
            arrayList.addAll(this.client.getProtocols());
        }
        for (String string : arrstring.split(",", -1)) {
            try {
                arrayList.add(Protocol.get(string));
            }
            catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }
        this.client.setProtocols(arrayList);
    }

    @Override
    public final void addRequestProperty(String string, String charSequence) {
        if (!this.connected) {
            if (string != null) {
                if (charSequence == null) {
                    Platform platform = Platform.get();
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Ignoring header ");
                    ((StringBuilder)charSequence).append(string);
                    ((StringBuilder)charSequence).append(" because its value was null.");
                    platform.logW(((StringBuilder)charSequence).toString());
                    return;
                }
                if (!"X-Android-Transports".equals(string) && !"X-Android-Protocols".equals(string)) {
                    this.requestHeaders.add(string, (String)charSequence);
                } else {
                    this.setProtocols((String)charSequence, true);
                }
                return;
            }
            throw new NullPointerException("field == null");
        }
        throw new IllegalStateException("Cannot add request property after connection is made");
    }

    @Override
    public final void connect() throws IOException {
        this.initHttpEngine();
        while (!this.execute(false)) {
        }
    }

    @Override
    public final void disconnect() {
        HttpEngine httpEngine = this.httpEngine;
        if (httpEngine == null) {
            return;
        }
        httpEngine.cancel();
    }

    @Override
    public int getConnectTimeout() {
        return this.client.getConnectTimeout();
    }

    @Override
    public final InputStream getErrorStream() {
        try {
            Object object = this.getResponse();
            if (HttpEngine.hasBody(((HttpEngine)object).getResponse()) && ((HttpEngine)object).getResponse().code() >= 400) {
                object = ((HttpEngine)object).getResponse().body().byteStream();
                return object;
            }
            return null;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    @Override
    public final String getHeaderField(int n) {
        try {
            String string = this.getHeaders().value(n);
            return string;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public final String getHeaderField(String string) {
        if (string != null) return this.getHeaders().get(string);
        try {
            return StatusLine.get(this.getResponse().getResponse()).toString();
        }
        catch (IOException iOException) {
            return null;
        }
    }

    @Override
    public final String getHeaderFieldKey(int n) {
        try {
            String string = this.getHeaders().name(n);
            return string;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    @Override
    public final Map<String, List<String>> getHeaderFields() {
        try {
            Map<String, List<String>> map = OkHeaders.toMultimap(this.getHeaders(), StatusLine.get(this.getResponse().getResponse()).toString());
            return map;
        }
        catch (IOException iOException) {
            return Collections.emptyMap();
        }
    }

    @Override
    public final InputStream getInputStream() throws IOException {
        if (this.doInput) {
            HttpEngine httpEngine = this.getResponse();
            if (this.getResponseCode() < 400) {
                return httpEngine.getResponse().body().byteStream();
            }
            throw new FileNotFoundException(this.url.toString());
        }
        throw new ProtocolException("This protocol does not support input");
    }

    @Override
    public boolean getInstanceFollowRedirects() {
        return this.client.getFollowRedirects();
    }

    @Override
    public final OutputStream getOutputStream() throws IOException {
        this.connect();
        Object object = this.httpEngine.getBufferedRequestBody();
        if (object != null) {
            if (!this.httpEngine.hasResponse()) {
                return object.outputStream();
            }
            throw new ProtocolException("cannot write request body after response has been read");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("method does not support a request body: ");
        ((StringBuilder)object).append(this.method);
        throw new ProtocolException(((StringBuilder)object).toString());
    }

    @Override
    public final Permission getPermission() throws IOException {
        Serializable serializable = this.getURL();
        String string = ((URL)serializable).getHost();
        int n = ((URL)serializable).getPort() != -1 ? ((URL)serializable).getPort() : HttpUrl.defaultPort(((URL)serializable).getProtocol());
        if (this.usingProxy()) {
            serializable = (InetSocketAddress)this.client.getProxy().address();
            string = ((InetSocketAddress)serializable).getHostName();
            n = ((InetSocketAddress)serializable).getPort();
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append(":");
        ((StringBuilder)serializable).append(n);
        return new SocketPermission(((StringBuilder)serializable).toString(), "connect, resolve");
    }

    @Override
    public int getReadTimeout() {
        return this.client.getReadTimeout();
    }

    @Override
    public final Map<String, List<String>> getRequestProperties() {
        if (!this.connected) {
            return OkHeaders.toMultimap(this.requestHeaders.build(), null);
        }
        throw new IllegalStateException("Cannot access request header fields after connection is set");
    }

    @Override
    public final String getRequestProperty(String string) {
        if (string == null) {
            return null;
        }
        return this.requestHeaders.get(string);
    }

    @Override
    public final int getResponseCode() throws IOException {
        return this.getResponse().getResponse().code();
    }

    @Override
    public String getResponseMessage() throws IOException {
        return this.getResponse().getResponse().message();
    }

    @Override
    public void setConnectTimeout(int n) {
        this.client.setConnectTimeout(n, TimeUnit.MILLISECONDS);
    }

    @Override
    public void setFixedLengthStreamingMode(int n) {
        this.setFixedLengthStreamingMode((long)n);
    }

    @Override
    public void setFixedLengthStreamingMode(long l) {
        if (!this.connected) {
            if (this.chunkLength <= 0) {
                if (l >= 0L) {
                    this.fixedContentLength = l;
                    ((HttpURLConnection)this).fixedContentLength = (int)Math.min(l, Integer.MAX_VALUE);
                    return;
                }
                throw new IllegalArgumentException("contentLength < 0");
            }
            throw new IllegalStateException("Already in chunked mode");
        }
        throw new IllegalStateException("Already connected");
    }

    @Override
    public void setIfModifiedSince(long l) {
        super.setIfModifiedSince(l);
        if (this.ifModifiedSince != 0L) {
            this.requestHeaders.set("If-Modified-Since", HttpDate.format(new Date(this.ifModifiedSince)));
        } else {
            this.requestHeaders.removeAll("If-Modified-Since");
        }
    }

    @Override
    public void setInstanceFollowRedirects(boolean bl) {
        this.client.setFollowRedirects(bl);
    }

    @Override
    public void setReadTimeout(int n) {
        this.client.setReadTimeout(n, TimeUnit.MILLISECONDS);
    }

    @Override
    public void setRequestMethod(String string) throws ProtocolException {
        if (METHODS.contains(string)) {
            this.method = string;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected one of ");
        stringBuilder.append(METHODS);
        stringBuilder.append(" but was ");
        stringBuilder.append(string);
        throw new ProtocolException(stringBuilder.toString());
    }

    @Override
    public final void setRequestProperty(String string, String object) {
        if (!this.connected) {
            if (string != null) {
                if (object == null) {
                    object = Platform.get();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Ignoring header ");
                    stringBuilder.append(string);
                    stringBuilder.append(" because its value was null.");
                    ((Platform)object).logW(stringBuilder.toString());
                    return;
                }
                if (!"X-Android-Transports".equals(string) && !"X-Android-Protocols".equals(string)) {
                    this.requestHeaders.set(string, (String)object);
                } else {
                    this.setProtocols((String)object, false);
                }
                return;
            }
            throw new NullPointerException("field == null");
        }
        throw new IllegalStateException("Cannot set request property after connection is made");
    }

    @Override
    public final boolean usingProxy() {
        Object object = this.route;
        object = object != null ? ((Route)object).getProxy() : this.client.getProxy();
        boolean bl = object != null && ((Proxy)object).type() != Proxy.Type.DIRECT;
        return bl;
    }
}

