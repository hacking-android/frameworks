/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.http;

import com.android.okhttp.Headers;
import com.android.okhttp.MediaType;
import com.android.okhttp.ResponseBody;
import com.android.okhttp.internal.http.OkHeaders;
import com.android.okhttp.okio.BufferedSource;

public final class RealResponseBody
extends ResponseBody {
    private final Headers headers;
    private final BufferedSource source;

    public RealResponseBody(Headers headers, BufferedSource bufferedSource) {
        this.headers = headers;
        this.source = bufferedSource;
    }

    @Override
    public long contentLength() {
        return OkHeaders.contentLength(this.headers);
    }

    @Override
    public MediaType contentType() {
        Object object = this.headers.get("Content-Type");
        object = object != null ? MediaType.parse((String)object) : null;
        return object;
    }

    @Override
    public BufferedSource source() {
        return this.source;
    }
}

