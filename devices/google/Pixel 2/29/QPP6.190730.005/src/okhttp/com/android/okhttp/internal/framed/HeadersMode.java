/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.framed;

public enum HeadersMode {
    SPDY_SYN_STREAM,
    SPDY_REPLY,
    SPDY_HEADERS,
    HTTP_20_HEADERS;
    

    public boolean failIfHeadersAbsent() {
        boolean bl = this == SPDY_HEADERS;
        return bl;
    }

    public boolean failIfHeadersPresent() {
        boolean bl = this == SPDY_REPLY;
        return bl;
    }

    public boolean failIfStreamAbsent() {
        boolean bl = this == SPDY_REPLY || this == SPDY_HEADERS;
        return bl;
    }

    public boolean failIfStreamPresent() {
        boolean bl = this == SPDY_SYN_STREAM;
        return bl;
    }
}

