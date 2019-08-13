/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.okhttp;

import com.android.okhttp.CacheControl;
import com.android.okhttp.Headers;
import com.android.okhttp.HttpUrl;
import com.android.okhttp.RequestBody;
import com.android.okhttp.internal.http.HttpMethod;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;

public final class Request {
    private final RequestBody body;
    private volatile CacheControl cacheControl;
    @UnsupportedAppUsage
    private final Headers headers;
    private volatile URI javaNetUri;
    private volatile URL javaNetUrl;
    @UnsupportedAppUsage
    private final String method;
    private final Object tag;
    @UnsupportedAppUsage
    private final HttpUrl url;

    private Request(Builder object) {
        this.url = ((Builder)object).url;
        this.method = ((Builder)object).method;
        this.headers = ((Builder)object).headers.build();
        this.body = ((Builder)object).body;
        object = ((Builder)object).tag != null ? ((Builder)object).tag : this;
        this.tag = object;
    }

    public RequestBody body() {
        return this.body;
    }

    public CacheControl cacheControl() {
        CacheControl cacheControl = this.cacheControl;
        if (cacheControl == null) {
            this.cacheControl = cacheControl = CacheControl.parse(this.headers);
        }
        return cacheControl;
    }

    public String header(String string) {
        return this.headers.get(string);
    }

    public Headers headers() {
        return this.headers;
    }

    public List<String> headers(String string) {
        return this.headers.values(string);
    }

    public HttpUrl httpUrl() {
        return this.url;
    }

    public boolean isHttps() {
        return this.url.isHttps();
    }

    public String method() {
        return this.method;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public Object tag() {
        return this.tag;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Request{method=");
        stringBuilder.append(this.method);
        stringBuilder.append(", url=");
        stringBuilder.append(this.url);
        stringBuilder.append(", tag=");
        Object object = this.tag;
        if (object == this) {
            object = null;
        }
        stringBuilder.append(object);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public URI uri() throws IOException {
        URI uRI;
        block3 : {
            try {
                uRI = this.javaNetUri;
                if (uRI != null) break block3;
            }
            catch (IllegalStateException illegalStateException) {
                throw new IOException(illegalStateException.getMessage());
            }
            this.javaNetUri = uRI = this.url.uri();
        }
        return uRI;
    }

    public URL url() {
        URL uRL = this.javaNetUrl;
        if (uRL == null) {
            this.javaNetUrl = uRL = this.url.url();
        }
        return uRL;
    }

    public String urlString() {
        return this.url.toString();
    }

    public static class Builder {
        private RequestBody body;
        private Headers.Builder headers;
        private String method;
        private Object tag;
        private HttpUrl url;

        public Builder() {
            this.method = "GET";
            this.headers = new Headers.Builder();
        }

        private Builder(Request request) {
            this.url = request.url;
            this.method = request.method;
            this.body = request.body;
            this.tag = request.tag;
            this.headers = request.headers.newBuilder();
        }

        public Builder addHeader(String string, String string2) {
            this.headers.add(string, string2);
            return this;
        }

        public Request build() {
            if (this.url != null) {
                return new Request(this);
            }
            throw new IllegalStateException("url == null");
        }

        public Builder cacheControl(CacheControl object) {
            if (((String)(object = ((CacheControl)object).toString())).isEmpty()) {
                return this.removeHeader("Cache-Control");
            }
            return this.header("Cache-Control", (String)object);
        }

        public Builder delete() {
            return this.delete(RequestBody.create(null, new byte[0]));
        }

        public Builder delete(RequestBody requestBody) {
            return this.method("DELETE", requestBody);
        }

        public Builder get() {
            return this.method("GET", null);
        }

        public Builder head() {
            return this.method("HEAD", null);
        }

        public Builder header(String string, String string2) {
            this.headers.set(string, string2);
            return this;
        }

        public Builder headers(Headers headers) {
            this.headers = headers.newBuilder();
            return this;
        }

        public Builder method(String string, RequestBody object) {
            if (string != null && string.length() != 0) {
                if (object != null && !HttpMethod.permitsRequestBody(string)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("method ");
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append(" must not have a request body.");
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                if (object == null && HttpMethod.requiresRequestBody(string)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("method ");
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append(" must have a request body.");
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                this.method = string;
                this.body = object;
                return this;
            }
            throw new IllegalArgumentException("method == null || method.length() == 0");
        }

        public Builder patch(RequestBody requestBody) {
            return this.method("PATCH", requestBody);
        }

        public Builder post(RequestBody requestBody) {
            return this.method("POST", requestBody);
        }

        public Builder put(RequestBody requestBody) {
            return this.method("PUT", requestBody);
        }

        public Builder removeHeader(String string) {
            this.headers.removeAll(string);
            return this;
        }

        public Builder tag(Object object) {
            this.tag = object;
            return this;
        }

        public Builder url(HttpUrl httpUrl) {
            if (httpUrl != null) {
                this.url = httpUrl;
                return this;
            }
            throw new IllegalArgumentException("url == null");
        }

        public Builder url(String object) {
            if (object != null) {
                CharSequence charSequence;
                if (((String)object).regionMatches(true, 0, "ws:", 0, 3)) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("http:");
                    ((StringBuilder)charSequence).append(((String)object).substring(3));
                    charSequence = ((StringBuilder)charSequence).toString();
                } else {
                    charSequence = object;
                    if (((String)object).regionMatches(true, 0, "wss:", 0, 4)) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("https:");
                        ((StringBuilder)charSequence).append(((String)object).substring(4));
                        charSequence = ((StringBuilder)charSequence).toString();
                    }
                }
                object = HttpUrl.parse((String)charSequence);
                if (object != null) {
                    return this.url((HttpUrl)object);
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("unexpected url: ");
                ((StringBuilder)object).append((String)charSequence);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            throw new IllegalArgumentException("url == null");
        }

        public Builder url(URL uRL) {
            if (uRL != null) {
                Object object = HttpUrl.get(uRL);
                if (object != null) {
                    return this.url((HttpUrl)object);
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("unexpected url: ");
                ((StringBuilder)object).append(uRL);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            throw new IllegalArgumentException("url == null");
        }
    }

}

