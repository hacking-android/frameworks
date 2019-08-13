/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.okhttp.internal.http;

import com.android.okhttp.Address;
import com.android.okhttp.Authenticator;
import com.android.okhttp.CertificatePinner;
import com.android.okhttp.Connection;
import com.android.okhttp.ConnectionPool;
import com.android.okhttp.ConnectionSpec;
import com.android.okhttp.Dns;
import com.android.okhttp.Handshake;
import com.android.okhttp.Headers;
import com.android.okhttp.HttpUrl;
import com.android.okhttp.Interceptor;
import com.android.okhttp.MediaType;
import com.android.okhttp.OkHttpClient;
import com.android.okhttp.Protocol;
import com.android.okhttp.Request;
import com.android.okhttp.RequestBody;
import com.android.okhttp.Response;
import com.android.okhttp.ResponseBody;
import com.android.okhttp.Route;
import com.android.okhttp.internal.Internal;
import com.android.okhttp.internal.InternalCache;
import com.android.okhttp.internal.Util;
import com.android.okhttp.internal.Version;
import com.android.okhttp.internal.http.CacheRequest;
import com.android.okhttp.internal.http.CacheStrategy;
import com.android.okhttp.internal.http.HttpMethod;
import com.android.okhttp.internal.http.HttpStream;
import com.android.okhttp.internal.http.OkHeaders;
import com.android.okhttp.internal.http.RealResponseBody;
import com.android.okhttp.internal.http.RequestException;
import com.android.okhttp.internal.http.RetryableSink;
import com.android.okhttp.internal.http.RouteException;
import com.android.okhttp.internal.http.StreamAllocation;
import com.android.okhttp.internal.io.RealConnection;
import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.BufferedSink;
import com.android.okhttp.okio.BufferedSource;
import com.android.okhttp.okio.GzipSource;
import com.android.okhttp.okio.Okio;
import com.android.okhttp.okio.Sink;
import com.android.okhttp.okio.Source;
import com.android.okhttp.okio.Timeout;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.Closeable;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

public final class HttpEngine {
    private static final ResponseBody EMPTY_BODY = new ResponseBody(){

        @Override
        public long contentLength() {
            return 0L;
        }

        @Override
        public MediaType contentType() {
            return null;
        }

        @Override
        public BufferedSource source() {
            return new Buffer();
        }
    };
    public static final int MAX_FOLLOW_UPS = 20;
    public final boolean bufferRequestBody;
    private BufferedSink bufferedRequestBody;
    private Response cacheResponse;
    private CacheStrategy cacheStrategy;
    private final boolean callerWritesRequestBody;
    final OkHttpClient client;
    private final boolean forWebSocket;
    @UnsupportedAppUsage
    private HttpStream httpStream;
    @UnsupportedAppUsage
    private Request networkRequest;
    @UnsupportedAppUsage
    private final Response priorResponse;
    private Sink requestBodyOut;
    @UnsupportedAppUsage
    long sentRequestMillis = -1L;
    private CacheRequest storeRequest;
    public final StreamAllocation streamAllocation;
    private boolean transparentGzip;
    private final Request userRequest;
    @UnsupportedAppUsage
    private Response userResponse;

    public HttpEngine(OkHttpClient okHttpClient, Request request, boolean bl, boolean bl2, boolean bl3, StreamAllocation streamAllocation, RetryableSink retryableSink, Response response) {
        this.client = okHttpClient;
        this.userRequest = request;
        this.bufferRequestBody = bl;
        this.callerWritesRequestBody = bl2;
        this.forWebSocket = bl3;
        if (streamAllocation == null) {
            streamAllocation = new StreamAllocation(okHttpClient.getConnectionPool(), HttpEngine.createAddress(okHttpClient, request));
        }
        this.streamAllocation = streamAllocation;
        this.requestBodyOut = retryableSink;
        this.priorResponse = response;
    }

    private Response cacheWritingResponse(CacheRequest object, Response response) throws IOException {
        if (object == null) {
            return response;
        }
        Sink sink = object.body();
        if (sink == null) {
            return response;
        }
        object = new Source(response.body().source(), (CacheRequest)object, Okio.buffer(sink)){
            boolean cacheRequestClosed;
            final /* synthetic */ BufferedSink val$cacheBody;
            final /* synthetic */ CacheRequest val$cacheRequest;
            final /* synthetic */ BufferedSource val$source;
            {
                this.val$source = bufferedSource;
                this.val$cacheRequest = cacheRequest;
                this.val$cacheBody = bufferedSink;
            }

            @Override
            public void close() throws IOException {
                if (!this.cacheRequestClosed && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                    this.cacheRequestClosed = true;
                    this.val$cacheRequest.abort();
                }
                this.val$source.close();
            }

            @Override
            public long read(Buffer buffer, long l) throws IOException {
                block3 : {
                    block4 : {
                        try {
                            l = this.val$source.read(buffer, l);
                            if (l != -1L) break block3;
                            if (this.cacheRequestClosed) break block4;
                            this.cacheRequestClosed = true;
                        }
                        catch (IOException iOException) {
                            if (!this.cacheRequestClosed) {
                                this.cacheRequestClosed = true;
                                this.val$cacheRequest.abort();
                            }
                            throw iOException;
                        }
                        this.val$cacheBody.close();
                    }
                    return -1L;
                }
                buffer.copyTo(this.val$cacheBody.buffer(), buffer.size() - l, l);
                this.val$cacheBody.emitCompleteSegments();
                return l;
            }

            @Override
            public Timeout timeout() {
                return this.val$source.timeout();
            }
        };
        return response.newBuilder().body(new RealResponseBody(response.headers(), Okio.buffer((Source)object))).build();
    }

    private static Headers combine(Headers object, Headers headers) throws IOException {
        int n;
        Headers.Builder builder = new Headers.Builder();
        int n2 = ((Headers)object).size();
        for (n = 0; n < n2; ++n) {
            String string = ((Headers)object).name(n);
            String string2 = ((Headers)object).value(n);
            if ("Warning".equalsIgnoreCase(string) && string2.startsWith("1") || OkHeaders.isEndToEnd(string) && headers.get(string) != null) continue;
            builder.add(string, string2);
        }
        n2 = headers.size();
        for (n = 0; n < n2; ++n) {
            object = headers.name(n);
            if ("Content-Length".equalsIgnoreCase((String)object) || !OkHeaders.isEndToEnd((String)object)) continue;
            builder.add((String)object, headers.value(n));
        }
        return builder.build();
    }

    private HttpStream connect() throws RouteException, RequestException, IOException {
        boolean bl = this.networkRequest.method().equals("GET");
        return this.streamAllocation.newStream(this.client.getConnectTimeout(), this.client.getReadTimeout(), this.client.getWriteTimeout(), this.client.getRetryOnConnectionFailure(), bl ^ true);
    }

    private static Address createAddress(OkHttpClient okHttpClient, Request request) {
        SSLSocketFactory sSLSocketFactory = null;
        HostnameVerifier hostnameVerifier = null;
        CertificatePinner certificatePinner = null;
        if (request.isHttps()) {
            sSLSocketFactory = okHttpClient.getSslSocketFactory();
            hostnameVerifier = okHttpClient.getHostnameVerifier();
            certificatePinner = okHttpClient.getCertificatePinner();
        }
        return new Address(request.httpUrl().host(), request.httpUrl().port(), okHttpClient.getDns(), okHttpClient.getSocketFactory(), sSLSocketFactory, hostnameVerifier, certificatePinner, okHttpClient.getAuthenticator(), okHttpClient.getProxy(), okHttpClient.getProtocols(), okHttpClient.getConnectionSpecs(), okHttpClient.getProxySelector());
    }

    public static boolean hasBody(Response response) {
        if (response.request().method().equals("HEAD")) {
            return false;
        }
        int n = response.code();
        if ((n < 100 || n >= 200) && n != 204 && n != 304) {
            return true;
        }
        return OkHeaders.contentLength(response) != -1L || "chunked".equalsIgnoreCase(response.header("Transfer-Encoding"));
        {
        }
    }

    private void maybeCache() throws IOException {
        InternalCache internalCache = Internal.instance.internalCache(this.client);
        if (internalCache == null) {
            return;
        }
        if (!CacheStrategy.isCacheable(this.userResponse, this.networkRequest)) {
            if (HttpMethod.invalidatesCache(this.networkRequest.method())) {
                try {
                    internalCache.remove(this.networkRequest);
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
            return;
        }
        this.storeRequest = internalCache.put(HttpEngine.stripBody(this.userResponse));
    }

    @UnsupportedAppUsage
    private Request networkRequest(Request request) throws IOException {
        CookieHandler cookieHandler;
        Request.Builder builder = request.newBuilder();
        if (request.header("Host") == null) {
            builder.header("Host", Util.hostHeader(request.httpUrl(), false));
        }
        if (request.header("Connection") == null) {
            builder.header("Connection", "Keep-Alive");
        }
        if (request.header("Accept-Encoding") == null) {
            this.transparentGzip = true;
            builder.header("Accept-Encoding", "gzip");
        }
        if ((cookieHandler = this.client.getCookieHandler()) != null) {
            Map<String, List<String>> map = OkHeaders.toMultimap(builder.build().headers(), null);
            OkHeaders.addCookies(builder, cookieHandler.get(request.uri(), map));
        }
        if (request.header("User-Agent") == null) {
            builder.header("User-Agent", Version.userAgent());
        }
        return builder.build();
    }

    private Response readNetworkResponse() throws IOException {
        Response response;
        this.httpStream.finishRequest();
        Response response2 = response = this.httpStream.readResponseHeaders().request(this.networkRequest).handshake(this.streamAllocation.connection().getHandshake()).header(OkHeaders.SENT_MILLIS, Long.toString(this.sentRequestMillis)).header(OkHeaders.RECEIVED_MILLIS, Long.toString(System.currentTimeMillis())).build();
        if (!this.forWebSocket) {
            response2 = response.newBuilder().body(this.httpStream.openResponseBody(response)).build();
        }
        if ("close".equalsIgnoreCase(response2.request().header("Connection")) || "close".equalsIgnoreCase(response2.header("Connection"))) {
            this.streamAllocation.noNewStreams();
        }
        return response2;
    }

    private static Response stripBody(Response response) {
        block0 : {
            if (response == null || response.body() == null) break block0;
            response = response.newBuilder().body(null).build();
        }
        return response;
    }

    private Response unzip(Response response) throws IOException {
        if (this.transparentGzip && "gzip".equalsIgnoreCase(this.userResponse.header("Content-Encoding"))) {
            if (response.body() == null) {
                return response;
            }
            GzipSource gzipSource = new GzipSource(response.body().source());
            Headers headers = response.headers().newBuilder().removeAll("Content-Encoding").removeAll("Content-Length").build();
            return response.newBuilder().headers(headers).body(new RealResponseBody(headers, Okio.buffer(gzipSource))).build();
        }
        return response;
    }

    private static boolean validate(Response object, Response object2) {
        if (((Response)object2).code() == 304) {
            return true;
        }
        return (object = ((Response)object).headers().getDate("Last-Modified")) != null && (object2 = ((Response)object2).headers().getDate("Last-Modified")) != null && ((Date)object2).getTime() < ((Date)object).getTime();
    }

    public void cancel() {
        this.streamAllocation.cancel();
    }

    public StreamAllocation close() {
        Object object = this.bufferedRequestBody;
        if (object != null) {
            Util.closeQuietly((Closeable)object);
        } else {
            object = this.requestBodyOut;
            if (object != null) {
                Util.closeQuietly((Closeable)object);
            }
        }
        object = this.userResponse;
        if (object != null) {
            Util.closeQuietly(((Response)object).body());
        } else {
            this.streamAllocation.connectionFailed();
        }
        return this.streamAllocation;
    }

    /*
     * Exception decompiling
     */
    public Request followUpRequest() throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
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

    public BufferedSink getBufferedRequestBody() {
        Sink sink = this.bufferedRequestBody;
        if (sink != null) {
            return sink;
        }
        sink = this.getRequestBody();
        if (sink != null) {
            this.bufferedRequestBody = sink = Okio.buffer(sink);
        } else {
            sink = null;
        }
        return sink;
    }

    @UnsupportedAppUsage
    public Connection getConnection() {
        return this.streamAllocation.connection();
    }

    public Request getRequest() {
        return this.userRequest;
    }

    public Sink getRequestBody() {
        if (this.cacheStrategy != null) {
            return this.requestBodyOut;
        }
        throw new IllegalStateException();
    }

    public Response getResponse() {
        Response response = this.userResponse;
        if (response != null) {
            return response;
        }
        throw new IllegalStateException();
    }

    @UnsupportedAppUsage
    public boolean hasResponse() {
        boolean bl = this.userResponse != null;
        return bl;
    }

    boolean permitsRequestBody(Request request) {
        return HttpMethod.permitsRequestBody(request.method());
    }

    @UnsupportedAppUsage
    public void readResponse() throws IOException {
        Object object;
        if (this.userResponse != null) {
            return;
        }
        if (this.networkRequest == null && this.cacheResponse == null) {
            throw new IllegalStateException("call sendRequest() first!");
        }
        Object object2 = this.networkRequest;
        if (object2 == null) {
            return;
        }
        if (this.forWebSocket) {
            this.httpStream.writeRequestHeaders((Request)object2);
            object2 = this.readNetworkResponse();
        } else if (!this.callerWritesRequestBody) {
            object2 = new NetworkInterceptorChain(0, (Request)object2).proceed(this.networkRequest);
        } else {
            object2 = this.bufferedRequestBody;
            if (object2 != null && object2.buffer().size() > 0L) {
                this.bufferedRequestBody.emit();
            }
            if (this.sentRequestMillis == -1L) {
                if (OkHeaders.contentLength(this.networkRequest) == -1L && (object2 = this.requestBodyOut) instanceof RetryableSink) {
                    long l = ((RetryableSink)object2).contentLength();
                    this.networkRequest = this.networkRequest.newBuilder().header("Content-Length", Long.toString(l)).build();
                }
                this.httpStream.writeRequestHeaders(this.networkRequest);
            }
            if ((object = this.requestBodyOut) != null) {
                object2 = this.bufferedRequestBody;
                if (object2 != null) {
                    object2.close();
                } else {
                    object.close();
                }
                object2 = this.requestBodyOut;
                if (object2 instanceof RetryableSink) {
                    this.httpStream.writeRequestBody((RetryableSink)object2);
                }
            }
            object2 = this.readNetworkResponse();
        }
        this.receiveHeaders(((Response)object2).headers());
        object = this.cacheResponse;
        if (object != null) {
            if (HttpEngine.validate((Response)object, (Response)object2)) {
                this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(HttpEngine.stripBody(this.priorResponse)).headers(HttpEngine.combine(this.cacheResponse.headers(), ((Response)object2).headers())).cacheResponse(HttpEngine.stripBody(this.cacheResponse)).networkResponse(HttpEngine.stripBody((Response)object2)).build();
                ((Response)object2).body().close();
                this.releaseStreamAllocation();
                object2 = Internal.instance.internalCache(this.client);
                object2.trackConditionalCacheHit();
                object2.update(this.cacheResponse, HttpEngine.stripBody(this.userResponse));
                this.userResponse = this.unzip(this.userResponse);
                return;
            }
            Util.closeQuietly(this.cacheResponse.body());
        }
        this.userResponse = ((Response)object2).newBuilder().request(this.userRequest).priorResponse(HttpEngine.stripBody(this.priorResponse)).cacheResponse(HttpEngine.stripBody(this.cacheResponse)).networkResponse(HttpEngine.stripBody((Response)object2)).build();
        if (HttpEngine.hasBody(this.userResponse)) {
            this.maybeCache();
            this.userResponse = this.unzip(this.cacheWritingResponse(this.storeRequest, this.userResponse));
        }
    }

    public void receiveHeaders(Headers headers) throws IOException {
        CookieHandler cookieHandler = this.client.getCookieHandler();
        if (cookieHandler != null) {
            cookieHandler.put(this.userRequest.uri(), OkHeaders.toMultimap(headers, null));
        }
    }

    public HttpEngine recover(RouteException object) {
        if (!this.streamAllocation.recover((RouteException)object)) {
            return null;
        }
        if (!this.client.getRetryOnConnectionFailure()) {
            return null;
        }
        object = this.close();
        return new HttpEngine(this.client, this.userRequest, this.bufferRequestBody, this.callerWritesRequestBody, this.forWebSocket, (StreamAllocation)object, (RetryableSink)this.requestBodyOut, this.priorResponse);
    }

    public HttpEngine recover(IOException iOException) {
        return this.recover(iOException, this.requestBodyOut);
    }

    public HttpEngine recover(IOException object, Sink sink) {
        if (!this.streamAllocation.recover((IOException)object, sink)) {
            return null;
        }
        if (!this.client.getRetryOnConnectionFailure()) {
            return null;
        }
        object = this.close();
        return new HttpEngine(this.client, this.userRequest, this.bufferRequestBody, this.callerWritesRequestBody, this.forWebSocket, (StreamAllocation)object, (RetryableSink)sink, this.priorResponse);
    }

    public void releaseStreamAllocation() throws IOException {
        this.streamAllocation.release();
    }

    public boolean sameConnection(HttpUrl httpUrl) {
        HttpUrl httpUrl2 = this.userRequest.httpUrl();
        boolean bl = httpUrl2.host().equals(httpUrl.host()) && httpUrl2.port() == httpUrl.port() && httpUrl2.scheme().equals(httpUrl.scheme());
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public void sendRequest() throws RequestException, RouteException, IOException {
        if (this.cacheStrategy != null) {
            return;
        }
        if (this.httpStream != null) throw new IllegalStateException();
        Request request = this.networkRequest(this.userRequest);
        InternalCache internalCache = Internal.instance.internalCache(this.client);
        Response response = internalCache != null ? internalCache.get(request) : null;
        this.cacheStrategy = new CacheStrategy.Factory(System.currentTimeMillis(), request, response).get();
        this.networkRequest = this.cacheStrategy.networkRequest;
        this.cacheResponse = this.cacheStrategy.cacheResponse;
        if (internalCache != null) {
            internalCache.trackResponse(this.cacheStrategy);
        }
        if (response != null && this.cacheResponse == null) {
            Util.closeQuietly(response.body());
        }
        if (this.networkRequest != null) {
            this.httpStream = this.connect();
            this.httpStream.setHttpEngine(this);
            if (!this.callerWritesRequestBody || !this.permitsRequestBody(this.networkRequest) || this.requestBodyOut != null) return;
            long l = OkHeaders.contentLength(request);
            if (this.bufferRequestBody) {
                if (l > Integer.MAX_VALUE) throw new IllegalStateException("Use setFixedLengthStreamingMode() or setChunkedStreamingMode() for requests larger than 2 GiB.");
                if (l != -1L) {
                    this.httpStream.writeRequestHeaders(this.networkRequest);
                    this.requestBodyOut = new RetryableSink((int)l);
                    return;
                } else {
                    this.requestBodyOut = new RetryableSink();
                }
                return;
            } else {
                this.httpStream.writeRequestHeaders(this.networkRequest);
                this.requestBodyOut = this.httpStream.createRequestBody(this.networkRequest, l);
            }
            return;
        } else {
            response = this.cacheResponse;
            this.userResponse = response != null ? response.newBuilder().request(this.userRequest).priorResponse(HttpEngine.stripBody(this.priorResponse)).cacheResponse(HttpEngine.stripBody(this.cacheResponse)).build() : new Response.Builder().request(this.userRequest).priorResponse(HttpEngine.stripBody(this.priorResponse)).protocol(Protocol.HTTP_1_1).code(504).message("Unsatisfiable Request (only-if-cached)").body(EMPTY_BODY).build();
            this.userResponse = this.unzip(this.userResponse);
        }
    }

    @UnsupportedAppUsage
    public void writingRequestHeaders() {
        if (this.sentRequestMillis == -1L) {
            this.sentRequestMillis = System.currentTimeMillis();
            return;
        }
        throw new IllegalStateException();
    }

    class NetworkInterceptorChain
    implements Interceptor.Chain {
        private int calls;
        private final int index;
        private final Request request;

        NetworkInterceptorChain(int n, Request request) {
            this.index = n;
            this.request = request;
        }

        @Override
        public Connection connection() {
            return HttpEngine.this.streamAllocation.connection();
        }

        @Override
        public Response proceed(Request object) throws IOException {
            Object object2;
            int n;
            Object object3;
            ++this.calls;
            if (this.index > 0) {
                object2 = HttpEngine.this.client.networkInterceptors().get(this.index - 1);
                object3 = this.connection().getRoute().getAddress();
                if (((Request)object).httpUrl().host().equals(((Address)object3).getUriHost()) && ((Request)object).httpUrl().port() == ((Address)object3).getUriPort()) {
                    if (this.calls > 1) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("network interceptor ");
                        ((StringBuilder)object).append(object2);
                        ((StringBuilder)object).append(" must call proceed() exactly once");
                        throw new IllegalStateException(((StringBuilder)object).toString());
                    }
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("network interceptor ");
                    ((StringBuilder)object).append(object2);
                    ((StringBuilder)object).append(" must retain the same host and port");
                    throw new IllegalStateException(((StringBuilder)object).toString());
                }
            }
            if (this.index < HttpEngine.this.client.networkInterceptors().size()) {
                object3 = new NetworkInterceptorChain(this.index + 1, (Request)object);
                object = HttpEngine.this.client.networkInterceptors().get(this.index);
                object2 = object.intercept((Interceptor.Chain)object3);
                if (((NetworkInterceptorChain)object3).calls == 1) {
                    if (object2 != null) {
                        return object2;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("network interceptor ");
                    ((StringBuilder)object2).append(object);
                    ((StringBuilder)object2).append(" returned null");
                    throw new NullPointerException(((StringBuilder)object2).toString());
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("network interceptor ");
                ((StringBuilder)object2).append(object);
                ((StringBuilder)object2).append(" must call proceed() exactly once");
                throw new IllegalStateException(((StringBuilder)object2).toString());
            }
            HttpEngine.this.httpStream.writeRequestHeaders((Request)object);
            HttpEngine.this.networkRequest = (Request)object;
            if (HttpEngine.this.permitsRequestBody((Request)object) && ((Request)object).body() != null) {
                object2 = Okio.buffer(HttpEngine.this.httpStream.createRequestBody((Request)object, ((Request)object).body().contentLength()));
                ((Request)object).body().writeTo((BufferedSink)object2);
                object2.close();
            }
            if ((n = ((Response)(object = HttpEngine.this.readNetworkResponse())).code()) != 204 && n != 205 || ((Response)object).body().contentLength() <= 0L) {
                return object;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("HTTP ");
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append(" had non-zero Content-Length: ");
            ((StringBuilder)object2).append(((Response)object).body().contentLength());
            throw new ProtocolException(((StringBuilder)object2).toString());
        }

        @Override
        public Request request() {
            return this.request;
        }
    }

}

