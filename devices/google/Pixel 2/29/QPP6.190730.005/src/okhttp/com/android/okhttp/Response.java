/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.okhttp;

import com.android.okhttp.CacheControl;
import com.android.okhttp.Challenge;
import com.android.okhttp.Handshake;
import com.android.okhttp.Headers;
import com.android.okhttp.Protocol;
import com.android.okhttp.Request;
import com.android.okhttp.ResponseBody;
import com.android.okhttp.internal.http.OkHeaders;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.util.Collections;
import java.util.List;

public final class Response {
    private final ResponseBody body;
    private volatile CacheControl cacheControl;
    private Response cacheResponse;
    @UnsupportedAppUsage
    private final int code;
    private final Handshake handshake;
    @UnsupportedAppUsage
    private final Headers headers;
    @UnsupportedAppUsage
    private final String message;
    @UnsupportedAppUsage
    private Response networkResponse;
    private final Response priorResponse;
    @UnsupportedAppUsage
    private final Protocol protocol;
    private final Request request;

    private Response(Builder builder) {
        this.request = builder.request;
        this.protocol = builder.protocol;
        this.code = builder.code;
        this.message = builder.message;
        this.handshake = builder.handshake;
        this.headers = builder.headers.build();
        this.body = builder.body;
        this.networkResponse = builder.networkResponse;
        this.cacheResponse = builder.cacheResponse;
        this.priorResponse = builder.priorResponse;
    }

    public ResponseBody body() {
        return this.body;
    }

    public CacheControl cacheControl() {
        CacheControl cacheControl = this.cacheControl;
        if (cacheControl == null) {
            this.cacheControl = cacheControl = CacheControl.parse(this.headers);
        }
        return cacheControl;
    }

    public Response cacheResponse() {
        return this.cacheResponse;
    }

    public List<Challenge> challenges() {
        block4 : {
            String string;
            block3 : {
                int n;
                block2 : {
                    n = this.code;
                    if (n != 401) break block2;
                    string = "WWW-Authenticate";
                    break block3;
                }
                if (n != 407) break block4;
                string = "Proxy-Authenticate";
            }
            return OkHeaders.parseChallenges(this.headers(), string);
        }
        return Collections.emptyList();
    }

    public int code() {
        return this.code;
    }

    public Handshake handshake() {
        return this.handshake;
    }

    public String header(String string) {
        return this.header(string, null);
    }

    public String header(String string, String string2) {
        if ((string = this.headers.get(string)) == null) {
            string = string2;
        }
        return string;
    }

    public Headers headers() {
        return this.headers;
    }

    public List<String> headers(String string) {
        return this.headers.values(string);
    }

    public boolean isRedirect() {
        int n = this.code;
        if (n != 307 && n != 308) {
            switch (n) {
                default: {
                    return false;
                }
                case 300: 
                case 301: 
                case 302: 
                case 303: 
            }
        }
        return true;
    }

    public boolean isSuccessful() {
        int n = this.code;
        boolean bl = n >= 200 && n < 300;
        return bl;
    }

    public String message() {
        return this.message;
    }

    public Response networkResponse() {
        return this.networkResponse;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public Response priorResponse() {
        return this.priorResponse;
    }

    public Protocol protocol() {
        return this.protocol;
    }

    public Request request() {
        return this.request;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Response{protocol=");
        stringBuilder.append((Object)this.protocol);
        stringBuilder.append(", code=");
        stringBuilder.append(this.code);
        stringBuilder.append(", message=");
        stringBuilder.append(this.message);
        stringBuilder.append(", url=");
        stringBuilder.append(this.request.urlString());
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public static class Builder {
        private ResponseBody body;
        private Response cacheResponse;
        private int code = -1;
        private Handshake handshake;
        private Headers.Builder headers;
        private String message;
        private Response networkResponse;
        private Response priorResponse;
        private Protocol protocol;
        private Request request;

        public Builder() {
            this.headers = new Headers.Builder();
        }

        private Builder(Response response) {
            this.request = response.request;
            this.protocol = response.protocol;
            this.code = response.code;
            this.message = response.message;
            this.handshake = response.handshake;
            this.headers = response.headers.newBuilder();
            this.body = response.body;
            this.networkResponse = response.networkResponse;
            this.cacheResponse = response.cacheResponse;
            this.priorResponse = response.priorResponse;
        }

        private void checkPriorResponse(Response response) {
            if (response.body == null) {
                return;
            }
            throw new IllegalArgumentException("priorResponse.body != null");
        }

        private void checkSupportResponse(String string, Response object) {
            if (((Response)object).body == null) {
                if (((Response)object).networkResponse == null) {
                    if (((Response)object).cacheResponse == null) {
                        if (((Response)object).priorResponse == null) {
                            return;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append(string);
                        ((StringBuilder)object).append(".priorResponse != null");
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append(".cacheResponse != null");
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append(".networkResponse != null");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(".body != null");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public Builder addHeader(String string, String string2) {
            this.headers.add(string, string2);
            return this;
        }

        public Builder body(ResponseBody responseBody) {
            this.body = responseBody;
            return this;
        }

        public Response build() {
            if (this.request != null) {
                if (this.protocol != null) {
                    if (this.code >= 0) {
                        return new Response(this);
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("code < 0: ");
                    stringBuilder.append(this.code);
                    throw new IllegalStateException(stringBuilder.toString());
                }
                throw new IllegalStateException("protocol == null");
            }
            throw new IllegalStateException("request == null");
        }

        public Builder cacheResponse(Response response) {
            if (response != null) {
                this.checkSupportResponse("cacheResponse", response);
            }
            this.cacheResponse = response;
            return this;
        }

        public Builder code(int n) {
            this.code = n;
            return this;
        }

        public Builder handshake(Handshake handshake) {
            this.handshake = handshake;
            return this;
        }

        public Builder header(String string, String string2) {
            this.headers.set(string, string2);
            return this;
        }

        public Builder headers(Headers headers) {
            this.headers = headers.newBuilder();
            return this;
        }

        public Builder message(String string) {
            this.message = string;
            return this;
        }

        public Builder networkResponse(Response response) {
            if (response != null) {
                this.checkSupportResponse("networkResponse", response);
            }
            this.networkResponse = response;
            return this;
        }

        public Builder priorResponse(Response response) {
            if (response != null) {
                this.checkPriorResponse(response);
            }
            this.priorResponse = response;
            return this;
        }

        public Builder protocol(Protocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder removeHeader(String string) {
            this.headers.removeAll(string);
            return this;
        }

        public Builder request(Request request) {
            this.request = request;
            return this;
        }
    }

}

