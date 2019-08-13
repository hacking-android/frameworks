/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.okio;

import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.Timeout;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

public interface Sink
extends Closeable,
Flushable {
    @Override
    public void close() throws IOException;

    @Override
    public void flush() throws IOException;

    public Timeout timeout();

    public void write(Buffer var1, long var2) throws IOException;
}

