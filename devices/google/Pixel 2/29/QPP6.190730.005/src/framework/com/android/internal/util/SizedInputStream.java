/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.Streams
 */
package com.android.internal.util;

import java.io.IOException;
import java.io.InputStream;
import libcore.io.Streams;

public class SizedInputStream
extends InputStream {
    private long mLength;
    private final InputStream mWrapped;

    public SizedInputStream(InputStream inputStream, long l) {
        this.mWrapped = inputStream;
        this.mLength = l;
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.mWrapped.close();
    }

    @Override
    public int read() throws IOException {
        return Streams.readSingleByte((InputStream)this);
    }

    @Override
    public int read(byte[] object, int n, int n2) throws IOException {
        long l = this.mLength;
        if (l <= 0L) {
            return -1;
        }
        int n3 = n2;
        if ((long)n2 > l) {
            n3 = (int)l;
        }
        if ((n = this.mWrapped.read((byte[])object, n, n3)) == -1) {
            if (this.mLength > 0L) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected EOF; expected ");
                ((StringBuilder)object).append(this.mLength);
                ((StringBuilder)object).append(" more bytes");
                throw new IOException(((StringBuilder)object).toString());
            }
        } else {
            this.mLength -= (long)n;
        }
        return n;
    }
}

