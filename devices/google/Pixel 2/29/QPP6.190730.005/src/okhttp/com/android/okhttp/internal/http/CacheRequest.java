/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.http;

import com.android.okhttp.okio.Sink;
import java.io.IOException;

public interface CacheRequest {
    public void abort();

    public Sink body() throws IOException;
}

