/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.okio;

import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.Timeout;
import java.io.Closeable;
import java.io.IOException;

public interface Source
extends Closeable {
    @Override
    public void close() throws IOException;

    public long read(Buffer var1, long var2) throws IOException;

    public Timeout timeout();
}

