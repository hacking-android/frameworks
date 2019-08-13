/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.http;

import com.android.okhttp.Headers;
import com.android.okhttp.HttpUrl;
import com.android.okhttp.OkHttpClient;
import com.android.okhttp.Protocol;
import com.android.okhttp.Request;
import com.android.okhttp.Response;
import com.android.okhttp.ResponseBody;
import com.android.okhttp.internal.Util;
import com.android.okhttp.internal.framed.ErrorCode;
import com.android.okhttp.internal.framed.FramedConnection;
import com.android.okhttp.internal.framed.FramedStream;
import com.android.okhttp.internal.framed.Header;
import com.android.okhttp.internal.http.HttpEngine;
import com.android.okhttp.internal.http.HttpStream;
import com.android.okhttp.internal.http.RealResponseBody;
import com.android.okhttp.internal.http.RequestLine;
import com.android.okhttp.internal.http.RetryableSink;
import com.android.okhttp.internal.http.StatusLine;
import com.android.okhttp.internal.http.StreamAllocation;
import com.android.okhttp.okio.BufferedSource;
import com.android.okhttp.okio.ByteString;
import com.android.okhttp.okio.ForwardingSource;
import com.android.okhttp.okio.Okio;
import com.android.okhttp.okio.Sink;
import com.android.okhttp.okio.Source;
import com.android.okhttp.okio.Timeout;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public final class Http2xStream
implements HttpStream {
    private static final ByteString CONNECTION = ByteString.encodeUtf8("connection");
    private static final ByteString ENCODING;
    private static final ByteString HOST;
    private static final List<ByteString> HTTP_2_SKIPPED_REQUEST_HEADERS;
    private static final List<ByteString> HTTP_2_SKIPPED_RESPONSE_HEADERS;
    private static final ByteString KEEP_ALIVE;
    private static final ByteString PROXY_CONNECTION;
    private static final List<ByteString> SPDY_3_SKIPPED_REQUEST_HEADERS;
    private static final List<ByteString> SPDY_3_SKIPPED_RESPONSE_HEADERS;
    private static final ByteString TE;
    private static final ByteString TRANSFER_ENCODING;
    private static final ByteString UPGRADE;
    private final FramedConnection framedConnection;
    private HttpEngine httpEngine;
    private FramedStream stream;
    private final StreamAllocation streamAllocation;

    static {
        HOST = ByteString.encodeUtf8("host");
        KEEP_ALIVE = ByteString.encodeUtf8("keep-alive");
        PROXY_CONNECTION = ByteString.encodeUtf8("proxy-connection");
        TRANSFER_ENCODING = ByteString.encodeUtf8("transfer-encoding");
        TE = ByteString.encodeUtf8("te");
        ENCODING = ByteString.encodeUtf8("encoding");
        UPGRADE = ByteString.encodeUtf8("upgrade");
        SPDY_3_SKIPPED_REQUEST_HEADERS = Util.immutableList(CONNECTION, HOST, KEEP_ALIVE, PROXY_CONNECTION, TRANSFER_ENCODING, Header.TARGET_METHOD, Header.TARGET_PATH, Header.TARGET_SCHEME, Header.TARGET_AUTHORITY, Header.TARGET_HOST, Header.VERSION);
        SPDY_3_SKIPPED_RESPONSE_HEADERS = Util.immutableList(CONNECTION, HOST, KEEP_ALIVE, PROXY_CONNECTION, TRANSFER_ENCODING);
        HTTP_2_SKIPPED_REQUEST_HEADERS = Util.immutableList(CONNECTION, HOST, KEEP_ALIVE, PROXY_CONNECTION, TE, TRANSFER_ENCODING, ENCODING, UPGRADE, Header.TARGET_METHOD, Header.TARGET_PATH, Header.TARGET_SCHEME, Header.TARGET_AUTHORITY, Header.TARGET_HOST, Header.VERSION);
        HTTP_2_SKIPPED_RESPONSE_HEADERS = Util.immutableList(CONNECTION, HOST, KEEP_ALIVE, PROXY_CONNECTION, TE, TRANSFER_ENCODING, ENCODING, UPGRADE);
    }

    public Http2xStream(StreamAllocation streamAllocation, FramedConnection framedConnection) {
        this.streamAllocation = streamAllocation;
        this.framedConnection = framedConnection;
    }

    public static List<Header> http2HeadersList(Request object) {
        Headers headers = ((Request)object).headers();
        ArrayList<Header> arrayList = new ArrayList<Header>(headers.size() + 4);
        arrayList.add(new Header(Header.TARGET_METHOD, ((Request)object).method()));
        arrayList.add(new Header(Header.TARGET_PATH, RequestLine.requestPath(((Request)object).httpUrl())));
        arrayList.add(new Header(Header.TARGET_AUTHORITY, Util.hostHeader(((Request)object).httpUrl(), false)));
        arrayList.add(new Header(Header.TARGET_SCHEME, ((Request)object).httpUrl().scheme()));
        int n = headers.size();
        for (int i = 0; i < n; ++i) {
            object = ByteString.encodeUtf8(headers.name(i).toLowerCase(Locale.US));
            if (HTTP_2_SKIPPED_REQUEST_HEADERS.contains(object)) continue;
            arrayList.add(new Header((ByteString)object, headers.value(i)));
        }
        return arrayList;
    }

    private static String joinOnNull(String charSequence, String string) {
        charSequence = new StringBuilder((String)charSequence);
        ((StringBuilder)charSequence).append('\u0000');
        ((StringBuilder)charSequence).append(string);
        return ((StringBuilder)charSequence).toString();
    }

    public static Response.Builder readHttp2HeadersList(List<Header> object) throws IOException {
        String string = null;
        Headers.Builder builder = new Headers.Builder();
        int n = object.size();
        for (int i = 0; i < n; ++i) {
            String string2;
            ByteString byteString = object.get((int)i).name;
            String string3 = ((Header)object.get((int)i)).value.utf8();
            if (byteString.equals(Header.RESPONSE_STATUS)) {
                string2 = string3;
            } else {
                string2 = string;
                if (!HTTP_2_SKIPPED_RESPONSE_HEADERS.contains(byteString)) {
                    builder.add(byteString.utf8(), string3);
                    string2 = string;
                }
            }
            string = string2;
        }
        if (string != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("HTTP/1.1 ");
            ((StringBuilder)object).append(string);
            object = StatusLine.parse(((StringBuilder)object).toString());
            return new Response.Builder().protocol(Protocol.HTTP_2).code(((StatusLine)object).code).message(((StatusLine)object).message).headers(builder.build());
        }
        throw new ProtocolException("Expected ':status' header not present");
    }

    public static Response.Builder readSpdy3HeadersList(List<Header> object) throws IOException {
        String string = null;
        String string2 = "HTTP/1.1";
        Headers.Builder builder = new Headers.Builder();
        int n = object.size();
        for (int i = 0; i < n; ++i) {
            ByteString byteString = object.get((int)i).name;
            String string3 = ((Header)object.get((int)i)).value.utf8();
            int n2 = 0;
            while (n2 < string3.length()) {
                String string4;
                int n3;
                String string5;
                int n4 = n3 = string3.indexOf(0, n2);
                if (n3 == -1) {
                    n4 = string3.length();
                }
                String string6 = string3.substring(n2, n4);
                if (byteString.equals(Header.RESPONSE_STATUS)) {
                    string4 = string6;
                    string5 = string2;
                } else if (byteString.equals(Header.VERSION)) {
                    string4 = string;
                    string5 = string6;
                } else {
                    string4 = string;
                    string5 = string2;
                    if (!SPDY_3_SKIPPED_RESPONSE_HEADERS.contains(byteString)) {
                        builder.add(byteString.utf8(), string6);
                        string5 = string2;
                        string4 = string;
                    }
                }
                n2 = n4 + 1;
                string = string4;
                string2 = string5;
            }
        }
        if (string != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" ");
            ((StringBuilder)object).append(string);
            object = StatusLine.parse(((StringBuilder)object).toString());
            return new Response.Builder().protocol(Protocol.SPDY_3).code(((StatusLine)object).code).message(((StatusLine)object).message).headers(builder.build());
        }
        throw new ProtocolException("Expected ':status' header not present");
    }

    public static List<Header> spdy3HeadersList(Request object) {
        Headers headers = ((Request)object).headers();
        ArrayList<Header> arrayList = new ArrayList<Header>(headers.size() + 5);
        arrayList.add(new Header(Header.TARGET_METHOD, ((Request)object).method()));
        arrayList.add(new Header(Header.TARGET_PATH, RequestLine.requestPath(((Request)object).httpUrl())));
        arrayList.add(new Header(Header.VERSION, "HTTP/1.1"));
        arrayList.add(new Header(Header.TARGET_HOST, Util.hostHeader(((Request)object).httpUrl(), false)));
        arrayList.add(new Header(Header.TARGET_SCHEME, ((Request)object).httpUrl().scheme()));
        LinkedHashSet<ByteString> linkedHashSet = new LinkedHashSet<ByteString>();
        int n = headers.size();
        block0 : for (int i = 0; i < n; ++i) {
            ByteString byteString = ByteString.encodeUtf8(headers.name(i).toLowerCase(Locale.US));
            if (SPDY_3_SKIPPED_REQUEST_HEADERS.contains(byteString)) continue;
            object = headers.value(i);
            if (linkedHashSet.add(byteString)) {
                arrayList.add(new Header(byteString, (String)object));
                continue;
            }
            for (int j = 0; j < arrayList.size(); ++j) {
                if (!((Header)arrayList.get((int)j)).name.equals(byteString)) continue;
                arrayList.set(j, new Header(byteString, Http2xStream.joinOnNull(((Header)arrayList.get((int)j)).value.utf8(), (String)object)));
                continue block0;
            }
        }
        return arrayList;
    }

    @Override
    public void cancel() {
        FramedStream framedStream = this.stream;
        if (framedStream != null) {
            framedStream.closeLater(ErrorCode.CANCEL);
        }
    }

    @Override
    public Sink createRequestBody(Request request, long l) throws IOException {
        return this.stream.getSink();
    }

    @Override
    public void finishRequest() throws IOException {
        this.stream.getSink().close();
    }

    @Override
    public ResponseBody openResponseBody(Response response) throws IOException {
        StreamFinishingSource streamFinishingSource = new StreamFinishingSource(this.stream.getSource());
        return new RealResponseBody(response.headers(), Okio.buffer(streamFinishingSource));
    }

    @Override
    public Response.Builder readResponseHeaders() throws IOException {
        Response.Builder builder = this.framedConnection.getProtocol() == Protocol.HTTP_2 ? Http2xStream.readHttp2HeadersList(this.stream.getResponseHeaders()) : Http2xStream.readSpdy3HeadersList(this.stream.getResponseHeaders());
        return builder;
    }

    @Override
    public void setHttpEngine(HttpEngine httpEngine) {
        this.httpEngine = httpEngine;
    }

    @Override
    public void writeRequestBody(RetryableSink retryableSink) throws IOException {
        retryableSink.writeToSocket(this.stream.getSink());
    }

    @Override
    public void writeRequestHeaders(Request list) throws IOException {
        if (this.stream != null) {
            return;
        }
        this.httpEngine.writingRequestHeaders();
        boolean bl = this.httpEngine.permitsRequestBody((Request)((Object)list));
        list = this.framedConnection.getProtocol() == Protocol.HTTP_2 ? Http2xStream.http2HeadersList((Request)((Object)list)) : Http2xStream.spdy3HeadersList((Request)((Object)list));
        this.stream = this.framedConnection.newStream(list, bl, true);
        this.stream.readTimeout().timeout(this.httpEngine.client.getReadTimeout(), TimeUnit.MILLISECONDS);
        this.stream.writeTimeout().timeout(this.httpEngine.client.getWriteTimeout(), TimeUnit.MILLISECONDS);
    }

    class StreamFinishingSource
    extends ForwardingSource {
        public StreamFinishingSource(Source source) {
            super(source);
        }

        @Override
        public void close() throws IOException {
            Http2xStream.this.streamAllocation.streamFinished(Http2xStream.this);
            super.close();
        }
    }

}

