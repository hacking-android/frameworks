/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.framed;

import com.android.okhttp.Protocol;
import com.android.okhttp.internal.framed.FrameReader;
import com.android.okhttp.internal.framed.FrameWriter;
import com.android.okhttp.okio.BufferedSink;
import com.android.okhttp.okio.BufferedSource;

public interface Variant {
    public Protocol getProtocol();

    public FrameReader newReader(BufferedSource var1, boolean var2);

    public FrameWriter newWriter(BufferedSink var1, boolean var2);
}

