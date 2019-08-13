/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.Handshake;
import com.android.okhttp.Headers;
import com.android.okhttp.MediaType;
import com.android.okhttp.Protocol;
import com.android.okhttp.Request;
import com.android.okhttp.RequestBody;
import com.android.okhttp.Response;
import com.android.okhttp.ResponseBody;
import com.android.okhttp.internal.DiskLruCache;
import com.android.okhttp.internal.InternalCache;
import com.android.okhttp.internal.Util;
import com.android.okhttp.internal.http.CacheRequest;
import com.android.okhttp.internal.http.CacheStrategy;
import com.android.okhttp.internal.http.HttpMethod;
import com.android.okhttp.internal.http.OkHeaders;
import com.android.okhttp.internal.http.StatusLine;
import com.android.okhttp.internal.io.FileSystem;
import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.BufferedSink;
import com.android.okhttp.okio.BufferedSource;
import com.android.okhttp.okio.ByteString;
import com.android.okhttp.okio.ForwardingSink;
import com.android.okhttp.okio.ForwardingSource;
import com.android.okhttp.okio.Okio;
import com.android.okhttp.okio.Sink;
import com.android.okhttp.okio.Source;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public final class Cache {
    private static final int ENTRY_BODY = 1;
    private static final int ENTRY_COUNT = 2;
    private static final int ENTRY_METADATA = 0;
    private static final int VERSION = 201105;
    private final DiskLruCache cache;
    private int hitCount;
    public final InternalCache internalCache = new InternalCache(){

        @Override
        public Response get(Request request) throws IOException {
            return Cache.this.get(request);
        }

        @Override
        public CacheRequest put(Response response) throws IOException {
            return Cache.this.put(response);
        }

        @Override
        public void remove(Request request) throws IOException {
            Cache.this.remove(request);
        }

        @Override
        public void trackConditionalCacheHit() {
            Cache.this.trackConditionalCacheHit();
        }

        @Override
        public void trackResponse(CacheStrategy cacheStrategy) {
            Cache.this.trackResponse(cacheStrategy);
        }

        @Override
        public void update(Response response, Response response2) throws IOException {
            Cache.this.update(response, response2);
        }
    };
    private int networkCount;
    private int requestCount;
    private int writeAbortCount;
    private int writeSuccessCount;

    public Cache(File file, long l) {
        this(file, l, FileSystem.SYSTEM);
    }

    Cache(File file, long l, FileSystem fileSystem) {
        this.cache = DiskLruCache.create(fileSystem, file, 201105, 2, l);
    }

    private void abortQuietly(DiskLruCache.Editor editor) {
        block2 : {
            if (editor == null) break block2;
            try {
                editor.abort();
            }
            catch (IOException iOException) {}
        }
    }

    static /* synthetic */ int access$808(Cache cache) {
        int n = cache.writeSuccessCount;
        cache.writeSuccessCount = n + 1;
        return n;
    }

    static /* synthetic */ int access$908(Cache cache) {
        int n = cache.writeAbortCount;
        cache.writeAbortCount = n + 1;
        return n;
    }

    private CacheRequest put(Response object) throws IOException {
        Object object2 = ((Response)object).request().method();
        if (HttpMethod.invalidatesCache(((Response)object).request().method())) {
            try {
                this.remove(((Response)object).request());
            }
            catch (IOException iOException) {
                // empty catch block
            }
            return null;
        }
        if (!((String)object2).equals("GET")) {
            return null;
        }
        if (OkHeaders.hasVaryAll((Response)object)) {
            return null;
        }
        Entry entry = new Entry((Response)object);
        object2 = null;
        try {
            object = this.cache.edit(Cache.urlToKey(((Response)object).request()));
            if (object == null) {
                return null;
            }
            object2 = object;
        }
        catch (IOException iOException) {
            this.abortQuietly((DiskLruCache.Editor)object2);
            return null;
        }
        entry.writeTo((DiskLruCache.Editor)object);
        object2 = object;
        object = new CacheRequestImpl((DiskLruCache.Editor)object);
        return object;
    }

    private static int readInt(BufferedSource object) throws IOException {
        String string;
        long l;
        block4 : {
            try {
                l = object.readDecimalLong();
                string = object.readUtf8LineStrict();
                if (l < 0L || l > Integer.MAX_VALUE) break block4;
            }
            catch (NumberFormatException numberFormatException) {
                throw new IOException(numberFormatException.getMessage());
            }
            if (!string.isEmpty()) break block4;
            return (int)l;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("expected an int but was \"");
        stringBuilder.append(l);
        stringBuilder.append(string);
        stringBuilder.append("\"");
        object = new IOException(stringBuilder.toString());
        throw object;
    }

    private void remove(Request request) throws IOException {
        this.cache.remove(Cache.urlToKey(request));
    }

    private void trackConditionalCacheHit() {
        synchronized (this) {
            ++this.hitCount;
            return;
        }
    }

    private void trackResponse(CacheStrategy cacheStrategy) {
        synchronized (this) {
            ++this.requestCount;
            if (cacheStrategy.networkRequest != null) {
                ++this.networkCount;
            } else if (cacheStrategy.cacheResponse != null) {
                ++this.hitCount;
            }
            return;
        }
    }

    private void update(Response object, Response object2) {
        block4 : {
            Entry entry = new Entry((Response)object2);
            object2 = ((CacheResponseBody)((Response)object).body()).snapshot;
            object = null;
            object2 = ((DiskLruCache.Snapshot)object2).edit();
            if (object2 == null) break block4;
            object = object2;
            entry.writeTo((DiskLruCache.Editor)object2);
            object = object2;
            try {
                ((DiskLruCache.Editor)object2).commit();
            }
            catch (IOException iOException) {
                this.abortQuietly((DiskLruCache.Editor)object);
            }
        }
    }

    private static String urlToKey(Request request) {
        return Util.md5Hex(request.urlString());
    }

    public void close() throws IOException {
        this.cache.close();
    }

    public void delete() throws IOException {
        this.cache.delete();
    }

    public void evictAll() throws IOException {
        this.cache.evictAll();
    }

    public void flush() throws IOException {
        this.cache.flush();
    }

    Response get(Request request) {
        Object object;
        block5 : {
            Object object2;
            block4 : {
                object2 = Cache.urlToKey(request);
                try {
                    object = this.cache.get((String)object2);
                    if (object != null) break block4;
                    return null;
                }
                catch (IOException iOException) {
                    return null;
                }
            }
            try {
                object2 = new Entry(((DiskLruCache.Snapshot)object).getSource(0));
                object = ((Entry)object2).response(request, (DiskLruCache.Snapshot)object);
                if (((Entry)object2).matches(request, (Response)object)) break block5;
            }
            catch (IOException iOException) {
                Util.closeQuietly((Closeable)object);
                return null;
            }
            Util.closeQuietly(((Response)object).body());
            return null;
        }
        return object;
    }

    public File getDirectory() {
        return this.cache.getDirectory();
    }

    public int getHitCount() {
        synchronized (this) {
            int n = this.hitCount;
            return n;
        }
    }

    public long getMaxSize() {
        return this.cache.getMaxSize();
    }

    public int getNetworkCount() {
        synchronized (this) {
            int n = this.networkCount;
            return n;
        }
    }

    public int getRequestCount() {
        synchronized (this) {
            int n = this.requestCount;
            return n;
        }
    }

    public long getSize() throws IOException {
        return this.cache.size();
    }

    public int getWriteAbortCount() {
        synchronized (this) {
            int n = this.writeAbortCount;
            return n;
        }
    }

    public int getWriteSuccessCount() {
        synchronized (this) {
            int n = this.writeSuccessCount;
            return n;
        }
    }

    public void initialize() throws IOException {
        this.cache.initialize();
    }

    public boolean isClosed() {
        return this.cache.isClosed();
    }

    public Iterator<String> urls() throws IOException {
        return new Iterator<String>(){
            boolean canRemove;
            final Iterator<DiskLruCache.Snapshot> delegate;
            String nextUrl;
            {
                this.delegate = Cache.this.cache.snapshots();
            }

            @Override
            public boolean hasNext() {
                if (this.nextUrl != null) {
                    return true;
                }
                this.canRemove = false;
                while (this.delegate.hasNext()) {
                    DiskLruCache.Snapshot snapshot = this.delegate.next();
                    try {
                        this.nextUrl = Okio.buffer(snapshot.getSource(0)).readUtf8LineStrict();
                        return true;
                    }
                    catch (IOException iOException) {}
                    continue;
                    finally {
                        snapshot.close();
                    }
                }
                return false;
            }

            @Override
            public String next() {
                if (this.hasNext()) {
                    String string = this.nextUrl;
                    this.nextUrl = null;
                    this.canRemove = true;
                    return string;
                }
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                if (this.canRemove) {
                    this.delegate.remove();
                    return;
                }
                throw new IllegalStateException("remove() before next()");
            }
        };
    }

    private final class CacheRequestImpl
    implements CacheRequest {
        private Sink body;
        private Sink cacheOut;
        private boolean done;
        private final DiskLruCache.Editor editor;

        public CacheRequestImpl(final DiskLruCache.Editor editor) throws IOException {
            this.editor = editor;
            this.cacheOut = editor.newSink(1);
            this.body = new ForwardingSink(this.cacheOut){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void close() throws IOException {
                    Cache cache = Cache.this;
                    synchronized (cache) {
                        if (CacheRequestImpl.this.done) {
                            return;
                        }
                        CacheRequestImpl.this.done = true;
                        Cache.access$808(Cache.this);
                    }
                    super.close();
                    editor.commit();
                }
            };
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void abort() {
            Cache cache = Cache.this;
            synchronized (cache) {
                if (this.done) {
                    return;
                }
                this.done = true;
                Cache.access$908(Cache.this);
            }
            Util.closeQuietly(this.cacheOut);
            try {
                this.editor.abort();
                return;
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }

        @Override
        public Sink body() {
            return this.body;
        }

    }

    private static class CacheResponseBody
    extends ResponseBody {
        private final BufferedSource bodySource;
        private final String contentLength;
        private final String contentType;
        private final DiskLruCache.Snapshot snapshot;

        public CacheResponseBody(final DiskLruCache.Snapshot snapshot, String string, String string2) {
            this.snapshot = snapshot;
            this.contentType = string;
            this.contentLength = string2;
            this.bodySource = Okio.buffer(new ForwardingSource(snapshot.getSource(1)){

                @Override
                public void close() throws IOException {
                    snapshot.close();
                    super.close();
                }
            });
        }

        @Override
        public long contentLength() {
            long l = -1L;
            try {
                if (this.contentLength != null) {
                    l = Long.parseLong(this.contentLength);
                }
                return l;
            }
            catch (NumberFormatException numberFormatException) {
                return -1L;
            }
        }

        @Override
        public MediaType contentType() {
            Object object = this.contentType;
            object = object != null ? MediaType.parse((String)object) : null;
            return object;
        }

        @Override
        public BufferedSource source() {
            return this.bodySource;
        }

    }

    private static final class Entry {
        private final int code;
        private final Handshake handshake;
        private final String message;
        private final Protocol protocol;
        private final String requestMethod;
        private final Headers responseHeaders;
        private final String url;
        private final Headers varyHeaders;

        public Entry(Response response) {
            this.url = response.request().urlString();
            this.varyHeaders = OkHeaders.varyHeaders(response);
            this.requestMethod = response.request().method();
            this.protocol = response.protocol();
            this.code = response.code();
            this.message = response.message();
            this.responseHeaders = response.headers();
            this.handshake = response.handshake();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public Entry(Source source) throws IOException {
            int n;
            Object object = Okio.buffer(source);
            this.url = object.readUtf8LineStrict();
            this.requestMethod = object.readUtf8LineStrict();
            Object object2 = new Headers.Builder();
            int n2 = Cache.readInt((BufferedSource)object);
            for (n = 0; n < n2; ++n) {
                ((Headers.Builder)object2).addLenient(object.readUtf8LineStrict());
            }
            this.varyHeaders = ((Headers.Builder)object2).build();
            object2 = StatusLine.parse(object.readUtf8LineStrict());
            this.protocol = ((StatusLine)object2).protocol;
            this.code = ((StatusLine)object2).code;
            this.message = ((StatusLine)object2).message;
            object2 = new Headers.Builder();
            n2 = Cache.readInt((BufferedSource)object);
            for (n = 0; n < n2; ++n) {
                ((Headers.Builder)object2).addLenient(object.readUtf8LineStrict());
            }
            try {
                this.responseHeaders = ((Headers.Builder)object2).build();
                if (this.isHttps()) {
                    object2 = object.readUtf8LineStrict();
                    if (((String)object2).length() <= 0) {
                        this.handshake = Handshake.get(object.readUtf8LineStrict(), this.readCertificateList((BufferedSource)object), this.readCertificateList((BufferedSource)object));
                        return;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("expected \"\" but was \"");
                    ((StringBuilder)object).append((String)object2);
                    ((StringBuilder)object).append("\"");
                    IOException iOException = new IOException(((StringBuilder)object).toString());
                    throw iOException;
                }
                this.handshake = null;
                return;
            }
            finally {
                source.close();
            }
        }

        private boolean isHttps() {
            return this.url.startsWith("https://");
        }

        private List<Certificate> readCertificateList(BufferedSource bufferedSource) throws IOException {
            ArrayList<Certificate> arrayList;
            CertificateFactory certificateFactory;
            int n = Cache.readInt(bufferedSource);
            if (n == -1) {
                return Collections.emptyList();
            }
            try {
                certificateFactory = CertificateFactory.getInstance("X.509");
                arrayList = new ArrayList<Certificate>(n);
            }
            catch (CertificateException certificateException) {
                throw new IOException(certificateException.getMessage());
            }
            for (int i = 0; i < n; ++i) {
                String string = bufferedSource.readUtf8LineStrict();
                Buffer buffer = new Buffer();
                buffer.write(ByteString.decodeBase64(string));
                arrayList.add(certificateFactory.generateCertificate(buffer.inputStream()));
                continue;
            }
            return arrayList;
        }

        private void writeCertList(BufferedSink bufferedSink, List<Certificate> list) throws IOException {
            int n;
            bufferedSink.writeDecimalLong(list.size());
            bufferedSink.writeByte(10);
            try {
                n = list.size();
            }
            catch (CertificateEncodingException certificateEncodingException) {
                throw new IOException(certificateEncodingException.getMessage());
            }
            for (int i = 0; i < n; ++i) {
                bufferedSink.writeUtf8(ByteString.of(list.get(i).getEncoded()).base64());
                bufferedSink.writeByte(10);
                continue;
            }
            return;
        }

        public boolean matches(Request request, Response response) {
            boolean bl = this.url.equals(request.urlString()) && this.requestMethod.equals(request.method()) && OkHeaders.varyMatches(response, this.varyHeaders, request);
            return bl;
        }

        public Response response(Request object, DiskLruCache.Snapshot snapshot) {
            object = this.responseHeaders.get("Content-Type");
            String string = this.responseHeaders.get("Content-Length");
            Request request = new Request.Builder().url(this.url).method(this.requestMethod, null).headers(this.varyHeaders).build();
            return new Response.Builder().request(request).protocol(this.protocol).code(this.code).message(this.message).headers(this.responseHeaders).body(new CacheResponseBody(snapshot, (String)object, string)).handshake(this.handshake).build();
        }

        public void writeTo(DiskLruCache.Editor object) throws IOException {
            int n;
            object = Okio.buffer(((DiskLruCache.Editor)object).newSink(0));
            object.writeUtf8(this.url);
            object.writeByte(10);
            object.writeUtf8(this.requestMethod);
            object.writeByte(10);
            object.writeDecimalLong(this.varyHeaders.size());
            object.writeByte(10);
            int n2 = this.varyHeaders.size();
            for (n = 0; n < n2; ++n) {
                object.writeUtf8(this.varyHeaders.name(n));
                object.writeUtf8(": ");
                object.writeUtf8(this.varyHeaders.value(n));
                object.writeByte(10);
            }
            object.writeUtf8(new StatusLine(this.protocol, this.code, this.message).toString());
            object.writeByte(10);
            object.writeDecimalLong(this.responseHeaders.size());
            object.writeByte(10);
            n2 = this.responseHeaders.size();
            for (n = 0; n < n2; ++n) {
                object.writeUtf8(this.responseHeaders.name(n));
                object.writeUtf8(": ");
                object.writeUtf8(this.responseHeaders.value(n));
                object.writeByte(10);
            }
            if (this.isHttps()) {
                object.writeByte(10);
                object.writeUtf8(this.handshake.cipherSuite());
                object.writeByte(10);
                this.writeCertList((BufferedSink)object, this.handshake.peerCertificates());
                this.writeCertList((BufferedSink)object, this.handshake.localCertificates());
            }
            object.close();
        }
    }

}

