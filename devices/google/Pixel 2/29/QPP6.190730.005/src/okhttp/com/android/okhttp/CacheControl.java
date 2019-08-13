/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.Headers;
import com.android.okhttp.internal.http.HeaderParser;
import java.util.concurrent.TimeUnit;

public final class CacheControl {
    public static final CacheControl FORCE_CACHE;
    public static final CacheControl FORCE_NETWORK;
    String headerValue;
    private final boolean isPrivate;
    private final boolean isPublic;
    private final int maxAgeSeconds;
    private final int maxStaleSeconds;
    private final int minFreshSeconds;
    private final boolean mustRevalidate;
    private final boolean noCache;
    private final boolean noStore;
    private final boolean noTransform;
    private final boolean onlyIfCached;
    private final int sMaxAgeSeconds;

    static {
        FORCE_NETWORK = new Builder().noCache().build();
        FORCE_CACHE = new Builder().onlyIfCached().maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS).build();
    }

    private CacheControl(Builder builder) {
        this.noCache = builder.noCache;
        this.noStore = builder.noStore;
        this.maxAgeSeconds = builder.maxAgeSeconds;
        this.sMaxAgeSeconds = -1;
        this.isPrivate = false;
        this.isPublic = false;
        this.mustRevalidate = false;
        this.maxStaleSeconds = builder.maxStaleSeconds;
        this.minFreshSeconds = builder.minFreshSeconds;
        this.onlyIfCached = builder.onlyIfCached;
        this.noTransform = builder.noTransform;
    }

    private CacheControl(boolean bl, boolean bl2, int n, int n2, boolean bl3, boolean bl4, boolean bl5, int n3, int n4, boolean bl6, boolean bl7, String string) {
        this.noCache = bl;
        this.noStore = bl2;
        this.maxAgeSeconds = n;
        this.sMaxAgeSeconds = n2;
        this.isPrivate = bl3;
        this.isPublic = bl4;
        this.mustRevalidate = bl5;
        this.maxStaleSeconds = n3;
        this.minFreshSeconds = n4;
        this.onlyIfCached = bl6;
        this.noTransform = bl7;
        this.headerValue = string;
    }

    private String headerValue() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.noCache) {
            stringBuilder.append("no-cache, ");
        }
        if (this.noStore) {
            stringBuilder.append("no-store, ");
        }
        if (this.maxAgeSeconds != -1) {
            stringBuilder.append("max-age=");
            stringBuilder.append(this.maxAgeSeconds);
            stringBuilder.append(", ");
        }
        if (this.sMaxAgeSeconds != -1) {
            stringBuilder.append("s-maxage=");
            stringBuilder.append(this.sMaxAgeSeconds);
            stringBuilder.append(", ");
        }
        if (this.isPrivate) {
            stringBuilder.append("private, ");
        }
        if (this.isPublic) {
            stringBuilder.append("public, ");
        }
        if (this.mustRevalidate) {
            stringBuilder.append("must-revalidate, ");
        }
        if (this.maxStaleSeconds != -1) {
            stringBuilder.append("max-stale=");
            stringBuilder.append(this.maxStaleSeconds);
            stringBuilder.append(", ");
        }
        if (this.minFreshSeconds != -1) {
            stringBuilder.append("min-fresh=");
            stringBuilder.append(this.minFreshSeconds);
            stringBuilder.append(", ");
        }
        if (this.onlyIfCached) {
            stringBuilder.append("only-if-cached, ");
        }
        if (this.noTransform) {
            stringBuilder.append("no-transform, ");
        }
        if (stringBuilder.length() == 0) {
            return "";
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        return stringBuilder.toString();
    }

    public static CacheControl parse(Headers headers) {
        boolean bl = false;
        boolean bl2 = false;
        int n = -1;
        int n2 = -1;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        int n3 = -1;
        int n4 = -1;
        boolean bl6 = false;
        boolean bl7 = false;
        boolean bl8 = true;
        String string = null;
        int n5 = 0;
        int n6 = headers.size();
        do {
            block24 : {
                Object object;
                String string2;
                String string3;
                block23 : {
                    block22 : {
                        object = headers;
                        if (n5 >= n6) break;
                        string2 = ((Headers)object).name(n5);
                        string3 = ((Headers)object).value(n5);
                        if (!string2.equalsIgnoreCase("Cache-Control")) break block22;
                        if (string != null) {
                            bl8 = false;
                        } else {
                            string = string3;
                        }
                        break block23;
                    }
                    if (!string2.equalsIgnoreCase("Pragma")) break block24;
                    bl8 = false;
                }
                int n7 = 0;
                while (n7 < string3.length()) {
                    int n8 = HeaderParser.skipUntil(string3, n7, "=,;");
                    string2 = string3.substring(n7, n8).trim();
                    if (n8 != string3.length() && string3.charAt(n8) != ',' && string3.charAt(n8) != ';') {
                        if ((n8 = HeaderParser.skipWhitespace(string3, n8 + 1)) < string3.length() && string3.charAt(n8) == '\"') {
                            n7 = HeaderParser.skipUntil(string3, ++n8, "\"");
                            object = string3.substring(n8, n7);
                            ++n7;
                        } else {
                            n7 = HeaderParser.skipUntil(string3, n8, ",;");
                            object = string3.substring(n8, n7).trim();
                        }
                    } else {
                        n7 = n8 + 1;
                        object = null;
                    }
                    if ("no-cache".equalsIgnoreCase(string2)) {
                        bl = true;
                        continue;
                    }
                    if ("no-store".equalsIgnoreCase(string2)) {
                        bl2 = true;
                        continue;
                    }
                    if ("max-age".equalsIgnoreCase(string2)) {
                        n = HeaderParser.parseSeconds((String)object, -1);
                        continue;
                    }
                    if ("s-maxage".equalsIgnoreCase(string2)) {
                        n2 = HeaderParser.parseSeconds((String)object, -1);
                        continue;
                    }
                    if ("private".equalsIgnoreCase(string2)) {
                        bl3 = true;
                        continue;
                    }
                    if ("public".equalsIgnoreCase(string2)) {
                        bl4 = true;
                        continue;
                    }
                    if ("must-revalidate".equalsIgnoreCase(string2)) {
                        bl5 = true;
                        continue;
                    }
                    if ("max-stale".equalsIgnoreCase(string2)) {
                        n3 = HeaderParser.parseSeconds((String)object, Integer.MAX_VALUE);
                        continue;
                    }
                    if ("min-fresh".equalsIgnoreCase(string2)) {
                        n4 = HeaderParser.parseSeconds((String)object, -1);
                        continue;
                    }
                    if ("only-if-cached".equalsIgnoreCase(string2)) {
                        bl6 = true;
                        continue;
                    }
                    if (!"no-transform".equalsIgnoreCase(string2)) continue;
                    bl7 = true;
                }
            }
            ++n5;
        } while (true);
        if (!bl8) {
            string = null;
        }
        return new CacheControl(bl, bl2, n, n2, bl3, bl4, bl5, n3, n4, bl6, bl7, string);
    }

    public boolean isPrivate() {
        return this.isPrivate;
    }

    public boolean isPublic() {
        return this.isPublic;
    }

    public int maxAgeSeconds() {
        return this.maxAgeSeconds;
    }

    public int maxStaleSeconds() {
        return this.maxStaleSeconds;
    }

    public int minFreshSeconds() {
        return this.minFreshSeconds;
    }

    public boolean mustRevalidate() {
        return this.mustRevalidate;
    }

    public boolean noCache() {
        return this.noCache;
    }

    public boolean noStore() {
        return this.noStore;
    }

    public boolean noTransform() {
        return this.noTransform;
    }

    public boolean onlyIfCached() {
        return this.onlyIfCached;
    }

    public int sMaxAgeSeconds() {
        return this.sMaxAgeSeconds;
    }

    public String toString() {
        String string = this.headerValue;
        if (string == null) {
            this.headerValue = string = this.headerValue();
        }
        return string;
    }

    public static final class Builder {
        int maxAgeSeconds = -1;
        int maxStaleSeconds = -1;
        int minFreshSeconds = -1;
        boolean noCache;
        boolean noStore;
        boolean noTransform;
        boolean onlyIfCached;

        public CacheControl build() {
            return new CacheControl(this);
        }

        public Builder maxAge(int n, TimeUnit object) {
            if (n >= 0) {
                long l = ((TimeUnit)((Object)object)).toSeconds(n);
                n = l > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)l;
                this.maxAgeSeconds = n;
                return this;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("maxAge < 0: ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public Builder maxStale(int n, TimeUnit object) {
            if (n >= 0) {
                long l = ((TimeUnit)((Object)object)).toSeconds(n);
                n = l > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)l;
                this.maxStaleSeconds = n;
                return this;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("maxStale < 0: ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public Builder minFresh(int n, TimeUnit object) {
            if (n >= 0) {
                long l = ((TimeUnit)((Object)object)).toSeconds(n);
                n = l > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)l;
                this.minFreshSeconds = n;
                return this;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("minFresh < 0: ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public Builder noCache() {
            this.noCache = true;
            return this;
        }

        public Builder noStore() {
            this.noStore = true;
            return this;
        }

        public Builder noTransform() {
            this.noTransform = true;
            return this;
        }

        public Builder onlyIfCached() {
            this.onlyIfCached = true;
            return this;
        }
    }

}

