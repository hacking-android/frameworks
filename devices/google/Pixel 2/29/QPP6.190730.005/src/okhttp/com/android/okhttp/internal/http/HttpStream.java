/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.http;

import com.android.okhttp.Request;
import com.android.okhttp.Response;
import com.android.okhttp.ResponseBody;
import com.android.okhttp.internal.http.HttpEngine;
import com.android.okhttp.internal.http.RetryableSink;
import com.android.okhttp.okio.Sink;
import java.io.IOException;

public interface HttpStream {
    public static final int DISCARD_STREAM_TIMEOUT_MILLIS = 100;

    public void cancel();

    public Sink createRequestBody(Request var1, long var2) throws IOException;

    public void finishRequest() throws IOException;

    public ResponseBody openResponseBody(Response var1) throws IOException;

    public Response.Builder readResponseHeaders() throws IOException;

    public void setHttpEngine(HttpEngine var1);

    public void writeRequestBody(RetryableSink var1) throws IOException;

    public void writeRequestHeaders(Request var1) throws IOException;
}

