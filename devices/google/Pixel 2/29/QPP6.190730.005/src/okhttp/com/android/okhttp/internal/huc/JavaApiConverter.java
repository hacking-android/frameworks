/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.huc;

import com.android.okhttp.Handshake;
import com.android.okhttp.Headers;
import com.android.okhttp.HttpUrl;
import com.android.okhttp.MediaType;
import com.android.okhttp.Protocol;
import com.android.okhttp.Request;
import com.android.okhttp.RequestBody;
import com.android.okhttp.Response;
import com.android.okhttp.ResponseBody;
import com.android.okhttp.internal.Internal;
import com.android.okhttp.internal.Util;
import com.android.okhttp.internal.http.CacheRequest;
import com.android.okhttp.internal.http.HttpMethod;
import com.android.okhttp.internal.http.OkHeaders;
import com.android.okhttp.internal.http.StatusLine;
import com.android.okhttp.internal.huc.DelegatingHttpsURLConnection;
import com.android.okhttp.okio.BufferedSource;
import com.android.okhttp.okio.Okio;
import com.android.okhttp.okio.Sink;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CacheResponse;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.SecureCacheResponse;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;

public final class JavaApiConverter {
    private static final RequestBody EMPTY_REQUEST_BODY = RequestBody.create(null, new byte[0]);

    private JavaApiConverter() {
    }

    private static Headers createHeaders(Map<String, List<String>> object) {
        Headers.Builder builder = new Headers.Builder();
        for (Map.Entry entry : object.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) continue;
            String string = ((String)entry.getKey()).trim();
            Iterator iterator = ((List)entry.getValue()).iterator();
            while (iterator.hasNext()) {
                String string2 = ((String)iterator.next()).trim();
                Internal.instance.addLenient(builder, string, string2);
            }
        }
        return builder.build();
    }

    public static java.net.CacheRequest createJavaCacheRequest(final CacheRequest cacheRequest) {
        return new java.net.CacheRequest(){

            @Override
            public void abort() {
                cacheRequest.abort();
            }

            @Override
            public OutputStream getBody() throws IOException {
                Sink sink = cacheRequest.body();
                if (sink == null) {
                    return null;
                }
                return Okio.buffer(sink).outputStream();
            }
        };
    }

    public static CacheResponse createJavaCacheResponse(final Response response) {
        final Headers headers = response.headers();
        final ResponseBody responseBody = response.body();
        if (response.request().isHttps()) {
            return new SecureCacheResponse(response.handshake(), headers, response, responseBody){
                final /* synthetic */ ResponseBody val$body;
                final /* synthetic */ Handshake val$handshake;
                final /* synthetic */ Headers val$headers;
                final /* synthetic */ Response val$response;
                {
                    this.val$handshake = handshake;
                    this.val$headers = headers;
                    this.val$response = response;
                    this.val$body = responseBody;
                }

                @Override
                public InputStream getBody() throws IOException {
                    ResponseBody responseBody = this.val$body;
                    if (responseBody == null) {
                        return null;
                    }
                    return responseBody.byteStream();
                }

                @Override
                public String getCipherSuite() {
                    Object object = this.val$handshake;
                    object = object != null ? ((Handshake)object).cipherSuite() : null;
                    return object;
                }

                @Override
                public Map<String, List<String>> getHeaders() throws IOException {
                    return OkHeaders.toMultimap(this.val$headers, StatusLine.get(this.val$response).toString());
                }

                @Override
                public List<Certificate> getLocalCertificateChain() {
                    Object object = this.val$handshake;
                    Object object2 = null;
                    if (object == null) {
                        return null;
                    }
                    if ((object = ((Handshake)object).localCertificates()).size() > 0) {
                        object2 = object;
                    }
                    return object2;
                }

                @Override
                public Principal getLocalPrincipal() {
                    Handshake handshake = this.val$handshake;
                    if (handshake == null) {
                        return null;
                    }
                    return handshake.localPrincipal();
                }

                @Override
                public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
                    Handshake handshake = this.val$handshake;
                    if (handshake == null) {
                        return null;
                    }
                    return handshake.peerPrincipal();
                }

                @Override
                public List<Certificate> getServerCertificateChain() throws SSLPeerUnverifiedException {
                    Object object = this.val$handshake;
                    Object object2 = null;
                    if (object == null) {
                        return null;
                    }
                    if ((object = ((Handshake)object).peerCertificates()).size() > 0) {
                        object2 = object;
                    }
                    return object2;
                }
            };
        }
        return new CacheResponse(){

            @Override
            public InputStream getBody() throws IOException {
                ResponseBody responseBody2 = responseBody;
                if (responseBody2 == null) {
                    return null;
                }
                return responseBody2.byteStream();
            }

            @Override
            public Map<String, List<String>> getHeaders() throws IOException {
                return OkHeaders.toMultimap(headers, StatusLine.get(response).toString());
            }
        };
    }

    static HttpURLConnection createJavaUrlConnectionForCachePut(Response response) {
        if (response.request().isHttps()) {
            return new CacheHttpsURLConnection(new CacheHttpURLConnection(response));
        }
        return new CacheHttpURLConnection(response);
    }

    private static ResponseBody createOkBody(final Headers headers, final CacheResponse cacheResponse) {
        return new ResponseBody(){
            private BufferedSource body;

            @Override
            public long contentLength() {
                return OkHeaders.contentLength(headers);
            }

            @Override
            public MediaType contentType() {
                Object object = headers.get("Content-Type");
                object = object == null ? null : MediaType.parse((String)object);
                return object;
            }

            @Override
            public BufferedSource source() throws IOException {
                if (this.body == null) {
                    this.body = Okio.buffer(Okio.source(cacheResponse.getBody()));
                }
                return this.body;
            }
        };
    }

    private static ResponseBody createOkBody(final URLConnection uRLConnection) {
        if (!uRLConnection.getDoInput()) {
            return null;
        }
        return new ResponseBody(){
            private BufferedSource body;

            @Override
            public long contentLength() {
                return JavaApiConverter.stringToLong(uRLConnection.getHeaderField("Content-Length"));
            }

            @Override
            public MediaType contentType() {
                Object object = uRLConnection.getContentType();
                object = object == null ? null : MediaType.parse((String)object);
                return object;
            }

            @Override
            public BufferedSource source() throws IOException {
                if (this.body == null) {
                    this.body = Okio.buffer(Okio.source(uRLConnection.getInputStream()));
                }
                return this.body;
            }
        };
    }

    public static Request createOkRequest(URI object, String string, Map<String, List<String>> map) {
        RequestBody requestBody = HttpMethod.requiresRequestBody(string) ? EMPTY_REQUEST_BODY : null;
        object = new Request.Builder().url(((URI)object).toString()).method(string, requestBody);
        if (map != null) {
            ((Request.Builder)object).headers(JavaApiConverter.extractOkHeaders(map));
        }
        return ((Request.Builder)object).build();
    }

    static Response createOkResponseForCacheGet(Request list, CacheResponse list2) throws IOException {
        Object object = JavaApiConverter.createHeaders(((CacheResponse)((Object)list2)).getHeaders());
        object = OkHeaders.hasVaryAll((Headers)object) ? new Headers.Builder().build() : OkHeaders.varyHeaders(((Request)((Object)list)).headers(), (Headers)object);
        list = new Request.Builder().url(((Request)((Object)list)).httpUrl()).method(((Request)((Object)list)).method(), null).headers((Headers)object).build();
        Response.Builder builder = new Response.Builder();
        builder.request((Request)((Object)list));
        list = StatusLine.parse(JavaApiConverter.extractStatusLine((CacheResponse)((Object)list2)));
        builder.protocol(((StatusLine)list).protocol);
        builder.code(((StatusLine)list).code);
        builder.message(((StatusLine)list).message);
        list = JavaApiConverter.extractOkHeaders((CacheResponse)((Object)list2));
        builder.headers((Headers)((Object)list));
        builder.body(JavaApiConverter.createOkBody((Headers)((Object)list), (CacheResponse)((Object)list2)));
        if (list2 instanceof SecureCacheResponse) {
            SecureCacheResponse secureCacheResponse = (SecureCacheResponse)((Object)list2);
            try {
                list = secureCacheResponse.getServerCertificateChain();
            }
            catch (SSLPeerUnverifiedException sSLPeerUnverifiedException) {
                list = Collections.emptyList();
            }
            object = secureCacheResponse.getLocalCertificateChain();
            list2 = object;
            if (object == null) {
                list2 = Collections.emptyList();
            }
            builder.handshake(Handshake.get(secureCacheResponse.getCipherSuite(), list, list2));
        }
        return builder.build();
    }

    public static Response createOkResponseForCachePut(URI arrcertificate, URLConnection uRLConnection) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection)uRLConnection;
        Response.Builder builder = new Response.Builder();
        Headers headers = JavaApiConverter.varyHeaders(uRLConnection, JavaApiConverter.createHeaders(uRLConnection.getHeaderFields()));
        Certificate[] arrcertificate2 = null;
        if (headers == null) {
            return null;
        }
        String string = httpURLConnection.getRequestMethod();
        if (HttpMethod.requiresRequestBody(string)) {
            arrcertificate2 = EMPTY_REQUEST_BODY;
        }
        builder.request(new Request.Builder().url(arrcertificate.toString()).method(string, (RequestBody)arrcertificate2).headers(headers).build());
        arrcertificate = StatusLine.parse(JavaApiConverter.extractStatusLine(httpURLConnection));
        builder.protocol(arrcertificate.protocol);
        builder.code(arrcertificate.code);
        builder.message(arrcertificate.message);
        builder.networkResponse(builder.build());
        builder.headers(JavaApiConverter.extractOkResponseHeaders(httpURLConnection));
        builder.body(JavaApiConverter.createOkBody(uRLConnection));
        if (httpURLConnection instanceof HttpsURLConnection) {
            uRLConnection = (HttpsURLConnection)httpURLConnection;
            try {
                arrcertificate = ((HttpsURLConnection)uRLConnection).getServerCertificates();
            }
            catch (SSLPeerUnverifiedException sSLPeerUnverifiedException) {
                arrcertificate = null;
            }
            arrcertificate2 = ((HttpsURLConnection)uRLConnection).getLocalCertificates();
            builder.handshake(Handshake.get(((HttpsURLConnection)uRLConnection).getCipherSuite(), JavaApiConverter.nullSafeImmutableList(arrcertificate), JavaApiConverter.nullSafeImmutableList(arrcertificate2)));
        }
        return builder.build();
    }

    static Map<String, List<String>> extractJavaHeaders(Request request) {
        return OkHeaders.toMultimap(request.headers(), null);
    }

    private static Headers extractOkHeaders(CacheResponse cacheResponse) throws IOException {
        return JavaApiConverter.extractOkHeaders(cacheResponse.getHeaders());
    }

    static Headers extractOkHeaders(Map<String, List<String>> object) {
        Headers.Builder builder = new Headers.Builder();
        for (Map.Entry entry : object.entrySet()) {
            String string = (String)entry.getKey();
            if (string == null) continue;
            for (String string2 : (List)entry.getValue()) {
                Internal.instance.addLenient(builder, string, string2);
            }
        }
        return builder.build();
    }

    private static Headers extractOkResponseHeaders(HttpURLConnection httpURLConnection) {
        return JavaApiConverter.extractOkHeaders(httpURLConnection.getHeaderFields());
    }

    private static String extractStatusLine(CacheResponse cacheResponse) throws IOException {
        return JavaApiConverter.extractStatusLine(cacheResponse.getHeaders());
    }

    private static String extractStatusLine(HttpURLConnection httpURLConnection) {
        return httpURLConnection.getHeaderField(null);
    }

    static String extractStatusLine(Map<String, List<String>> map) throws ProtocolException {
        List<String> list = map.get(null);
        if (list != null && list.size() != 0) {
            return list.get(0);
        }
        list = new StringBuilder();
        ((StringBuilder)((Object)list)).append("CacheResponse is missing a 'null' header containing the status line. Headers=");
        ((StringBuilder)((Object)list)).append(map);
        throw new ProtocolException(((StringBuilder)((Object)list)).toString());
    }

    private static <T> List<T> nullSafeImmutableList(T[] object) {
        object = object == null ? Collections.emptyList() : Util.immutableList(object);
        return object;
    }

    private static long stringToLong(String string) {
        if (string == null) {
            return -1L;
        }
        try {
            long l = Long.parseLong(string);
            return l;
        }
        catch (NumberFormatException numberFormatException) {
            return -1L;
        }
    }

    private static RuntimeException throwRequestHeaderAccessException() {
        throw new UnsupportedOperationException("ResponseCache cannot access request headers");
    }

    private static RuntimeException throwRequestModificationException() {
        throw new UnsupportedOperationException("ResponseCache cannot modify the request.");
    }

    private static RuntimeException throwRequestSslAccessException() {
        throw new UnsupportedOperationException("ResponseCache cannot access SSL internals");
    }

    private static RuntimeException throwResponseBodyAccessException() {
        throw new UnsupportedOperationException("ResponseCache cannot access the response body.");
    }

    private static Headers varyHeaders(URLConnection object, Headers object2) {
        if (OkHeaders.hasVaryAll((Headers)object2)) {
            return null;
        }
        Object object3 = OkHeaders.varyFields((Headers)object2);
        if (object3.isEmpty()) {
            return new Headers.Builder().build();
        }
        if (!(object instanceof CacheHttpURLConnection) && !(object instanceof CacheHttpsURLConnection)) {
            return null;
        }
        object2 = ((URLConnection)object).getRequestProperties();
        object = new Headers.Builder();
        Iterator<String> iterator = object3.iterator();
        while (iterator.hasNext()) {
            object3 = iterator.next();
            Object object4 = (List)object2.get(object3);
            if (object4 == null) {
                if (!((String)object3).equals("Accept-Encoding")) continue;
                ((Headers.Builder)object).add("Accept-Encoding", "gzip");
                continue;
            }
            Iterator iterator2 = object4.iterator();
            while (iterator2.hasNext()) {
                object4 = (String)iterator2.next();
                Internal.instance.addLenient((Headers.Builder)object, (String)object3, (String)object4);
            }
        }
        return ((Headers.Builder)object).build();
    }

    private static final class CacheHttpURLConnection
    extends HttpURLConnection {
        private final Request request;
        private final Response response;

        public CacheHttpURLConnection(Response response) {
            super(response.request().url());
            this.request = response.request();
            this.response = response;
            this.connected = true;
            boolean bl = this.request.body() != null;
            this.doOutput = bl;
            this.doInput = true;
            this.useCaches = true;
            this.method = this.request.method();
        }

        @Override
        public void addRequestProperty(String string, String string2) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void connect() throws IOException {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void disconnect() {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public boolean getAllowUserInteraction() {
            return false;
        }

        @Override
        public int getConnectTimeout() {
            return 0;
        }

        @Override
        public Object getContent() throws IOException {
            throw JavaApiConverter.throwResponseBodyAccessException();
        }

        @Override
        public Object getContent(Class[] arrclass) throws IOException {
            throw JavaApiConverter.throwResponseBodyAccessException();
        }

        @Override
        public boolean getDefaultUseCaches() {
            return super.getDefaultUseCaches();
        }

        @Override
        public boolean getDoInput() {
            return this.doInput;
        }

        @Override
        public boolean getDoOutput() {
            return this.doOutput;
        }

        @Override
        public InputStream getErrorStream() {
            return null;
        }

        @Override
        public String getHeaderField(int n) {
            if (n >= 0) {
                if (n == 0) {
                    return StatusLine.get(this.response).toString();
                }
                return this.response.headers().value(n - 1);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid header index: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        @Override
        public String getHeaderField(String string) {
            string = string == null ? StatusLine.get(this.response).toString() : this.response.headers().get(string);
            return string;
        }

        @Override
        public String getHeaderFieldKey(int n) {
            if (n >= 0) {
                if (n == 0) {
                    return null;
                }
                return this.response.headers().name(n - 1);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid header index: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        @Override
        public Map<String, List<String>> getHeaderFields() {
            return OkHeaders.toMultimap(this.response.headers(), StatusLine.get(this.response).toString());
        }

        @Override
        public long getIfModifiedSince() {
            return JavaApiConverter.stringToLong(this.request.headers().get("If-Modified-Since"));
        }

        @Override
        public InputStream getInputStream() throws IOException {
            throw JavaApiConverter.throwResponseBodyAccessException();
        }

        @Override
        public boolean getInstanceFollowRedirects() {
            return super.getInstanceFollowRedirects();
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public int getReadTimeout() {
            return 0;
        }

        @Override
        public String getRequestMethod() {
            return this.request.method();
        }

        @Override
        public Map<String, List<String>> getRequestProperties() {
            return OkHeaders.toMultimap(this.request.headers(), null);
        }

        @Override
        public String getRequestProperty(String string) {
            return this.request.header(string);
        }

        @Override
        public int getResponseCode() throws IOException {
            return this.response.code();
        }

        @Override
        public String getResponseMessage() throws IOException {
            return this.response.message();
        }

        @Override
        public boolean getUseCaches() {
            return super.getUseCaches();
        }

        @Override
        public void setAllowUserInteraction(boolean bl) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setChunkedStreamingMode(int n) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setConnectTimeout(int n) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setDefaultUseCaches(boolean bl) {
            super.setDefaultUseCaches(bl);
        }

        @Override
        public void setDoInput(boolean bl) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setDoOutput(boolean bl) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setFixedLengthStreamingMode(int n) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setFixedLengthStreamingMode(long l) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setIfModifiedSince(long l) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setInstanceFollowRedirects(boolean bl) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setReadTimeout(int n) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setRequestMethod(String string) throws ProtocolException {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setRequestProperty(String string, String string2) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setUseCaches(boolean bl) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public boolean usingProxy() {
            return false;
        }
    }

    private static final class CacheHttpsURLConnection
    extends DelegatingHttpsURLConnection {
        private final CacheHttpURLConnection delegate;

        public CacheHttpsURLConnection(CacheHttpURLConnection cacheHttpURLConnection) {
            super(cacheHttpURLConnection);
            this.delegate = cacheHttpURLConnection;
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
            throw JavaApiConverter.throwRequestSslAccessException();
        }

        @Override
        public SSLSocketFactory getSSLSocketFactory() {
            throw JavaApiConverter.throwRequestSslAccessException();
        }

        @Override
        protected Handshake handshake() {
            return this.delegate.response.handshake();
        }

        @Override
        public void setFixedLengthStreamingMode(long l) {
            this.delegate.setFixedLengthStreamingMode(l);
        }

        @Override
        public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setSSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
            throw JavaApiConverter.throwRequestModificationException();
        }
    }

}

