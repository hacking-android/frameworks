/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.http;

import com.android.okhttp.CacheControl;
import com.android.okhttp.Handshake;
import com.android.okhttp.Headers;
import com.android.okhttp.HttpUrl;
import com.android.okhttp.Request;
import com.android.okhttp.Response;
import com.android.okhttp.internal.http.HeaderParser;
import com.android.okhttp.internal.http.HttpDate;
import com.android.okhttp.internal.http.OkHeaders;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class CacheStrategy {
    public final Response cacheResponse;
    public final Request networkRequest;

    private CacheStrategy(Request request, Response response) {
        this.networkRequest = request;
        this.cacheResponse = response;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static boolean isCacheable(Response var0, Request var1_1) {
        block6 : {
            var2_2 = var0.code();
            var3_3 = false;
            if (var2_2 == 200 || var2_2 == 410 || var2_2 == 414 || var2_2 == 501 || var2_2 == 203 || var2_2 == 204) break block6;
            if (var2_2 == 307) ** GOTO lbl-1000
            if (var2_2 == 308 || var2_2 == 404 || var2_2 == 405) break block6;
            switch (var2_2) {
                default: {
                    return false;
                }
                case 302: lbl-1000: // 2 sources:
                {
                    if (var0.header("Expires") != null || var0.cacheControl().maxAgeSeconds() != -1 || var0.cacheControl().isPublic()) break;
                    if (var0.cacheControl().isPrivate() == false) return false;
                    break;
                }
                case 300: 
                case 301: 
            }
        }
        var4_4 = var3_3;
        if (var0.cacheControl().noStore() != false) return var4_4;
        var4_4 = var3_3;
        if (var1_1.cacheControl().noStore() != false) return var4_4;
        return true;
    }

    public static class Factory {
        private int ageSeconds = -1;
        final Response cacheResponse;
        private String etag;
        private Date expires;
        private Date lastModified;
        private String lastModifiedString;
        final long nowMillis;
        private long receivedResponseMillis;
        final Request request;
        private long sentRequestMillis;
        private Date servedDate;
        private String servedDateString;

        public Factory(long l, Request object, Response object2) {
            this.nowMillis = l;
            this.request = object;
            this.cacheResponse = object2;
            if (object2 != null) {
                Headers headers = ((Response)object2).headers();
                int n = headers.size();
                for (int i = 0; i < n; ++i) {
                    object2 = headers.name(i);
                    object = headers.value(i);
                    if ("Date".equalsIgnoreCase((String)object2)) {
                        this.servedDate = HttpDate.parse((String)object);
                        this.servedDateString = object;
                        continue;
                    }
                    if ("Expires".equalsIgnoreCase((String)object2)) {
                        this.expires = HttpDate.parse((String)object);
                        continue;
                    }
                    if ("Last-Modified".equalsIgnoreCase((String)object2)) {
                        this.lastModified = HttpDate.parse((String)object);
                        this.lastModifiedString = object;
                        continue;
                    }
                    if ("ETag".equalsIgnoreCase((String)object2)) {
                        this.etag = object;
                        continue;
                    }
                    if ("Age".equalsIgnoreCase((String)object2)) {
                        this.ageSeconds = HeaderParser.parseSeconds((String)object, -1);
                        continue;
                    }
                    if (OkHeaders.SENT_MILLIS.equalsIgnoreCase((String)object2)) {
                        this.sentRequestMillis = Long.parseLong((String)object);
                        continue;
                    }
                    if (!OkHeaders.RECEIVED_MILLIS.equalsIgnoreCase((String)object2)) continue;
                    this.receivedResponseMillis = Long.parseLong((String)object);
                }
            }
        }

        private long cacheResponseAge() {
            Date date = this.servedDate;
            long l = 0L;
            if (date != null) {
                l = Math.max(0L, this.receivedResponseMillis - date.getTime());
            }
            if (this.ageSeconds != -1) {
                l = Math.max(l, TimeUnit.SECONDS.toMillis(this.ageSeconds));
            }
            long l2 = this.receivedResponseMillis;
            return l + (l2 - this.sentRequestMillis) + (this.nowMillis - l2);
        }

        private long computeFreshnessLifetime() {
            Object object = this.cacheResponse.cacheControl();
            if (((CacheControl)object).maxAgeSeconds() != -1) {
                return TimeUnit.SECONDS.toMillis(((CacheControl)object).maxAgeSeconds());
            }
            object = this.expires;
            long l = 0L;
            if (object != null) {
                object = this.servedDate;
                long l2 = object != null ? ((Date)object).getTime() : this.receivedResponseMillis;
                l2 = this.expires.getTime() - l2;
                if (l2 > 0L) {
                    l = l2;
                }
                return l;
            }
            if (this.lastModified != null && this.cacheResponse.request().httpUrl().query() == null) {
                object = this.servedDate;
                long l3 = object != null ? ((Date)object).getTime() : this.sentRequestMillis;
                if ((l3 -= this.lastModified.getTime()) > 0L) {
                    l = l3 / 10L;
                }
                return l;
            }
            return 0L;
        }

        private CacheStrategy getCandidate() {
            if (this.cacheResponse == null) {
                return new CacheStrategy(this.request, null);
            }
            if (this.request.isHttps() && this.cacheResponse.handshake() == null) {
                return new CacheStrategy(this.request, null);
            }
            if (!CacheStrategy.isCacheable(this.cacheResponse, this.request)) {
                return new CacheStrategy(this.request, null);
            }
            Object object = this.request.cacheControl();
            if (!((CacheControl)object).noCache() && !Factory.hasConditions(this.request)) {
                long l;
                long l2 = this.cacheResponseAge();
                long l3 = l = this.computeFreshnessLifetime();
                if (((CacheControl)object).maxAgeSeconds() != -1) {
                    l3 = Math.min(l, TimeUnit.SECONDS.toMillis(((CacheControl)object).maxAgeSeconds()));
                }
                l = 0L;
                if (((CacheControl)object).minFreshSeconds() != -1) {
                    l = TimeUnit.SECONDS.toMillis(((CacheControl)object).minFreshSeconds());
                }
                long l4 = 0L;
                Object object2 = this.cacheResponse.cacheControl();
                long l5 = l4;
                if (!((CacheControl)object2).mustRevalidate()) {
                    l5 = l4;
                    if (((CacheControl)object).maxStaleSeconds() != -1) {
                        l5 = TimeUnit.SECONDS.toMillis(((CacheControl)object).maxStaleSeconds());
                    }
                }
                if (!((CacheControl)object2).noCache() && l2 + l < l3 + l5) {
                    object = this.cacheResponse.newBuilder();
                    if (l2 + l >= l3) {
                        ((Response.Builder)object).addHeader("Warning", "110 HttpURLConnection \"Response is stale\"");
                    }
                    if (l2 > 86400000L && this.isFreshnessLifetimeHeuristic()) {
                        ((Response.Builder)object).addHeader("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
                    }
                    return new CacheStrategy(null, ((Response.Builder)object).build());
                }
                object2 = this.request.newBuilder();
                object = this.etag;
                if (object != null) {
                    ((Request.Builder)object2).header("If-None-Match", (String)object);
                } else if (this.lastModified != null) {
                    ((Request.Builder)object2).header("If-Modified-Since", this.lastModifiedString);
                } else if (this.servedDate != null) {
                    ((Request.Builder)object2).header("If-Modified-Since", this.servedDateString);
                }
                object = ((Request.Builder)object2).build();
                object = Factory.hasConditions((Request)object) ? new CacheStrategy((Request)object, this.cacheResponse) : new CacheStrategy((Request)object, null);
                return object;
            }
            return new CacheStrategy(this.request, null);
        }

        private static boolean hasConditions(Request request) {
            boolean bl = request.header("If-Modified-Since") != null || request.header("If-None-Match") != null;
            return bl;
        }

        private boolean isFreshnessLifetimeHeuristic() {
            boolean bl = this.cacheResponse.cacheControl().maxAgeSeconds() == -1 && this.expires == null;
            return bl;
        }

        public CacheStrategy get() {
            CacheStrategy cacheStrategy = this.getCandidate();
            if (cacheStrategy.networkRequest != null && this.request.cacheControl().onlyIfCached()) {
                return new CacheStrategy(null, null);
            }
            return cacheStrategy;
        }
    }

}

