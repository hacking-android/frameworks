/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.IndefiniteLengthInputStream;
import java.io.InputStream;

abstract class LimitedInputStream
extends InputStream {
    protected final InputStream _in;
    private int _limit;

    LimitedInputStream(InputStream inputStream, int n) {
        this._in = inputStream;
        this._limit = n;
    }

    int getRemaining() {
        return this._limit;
    }

    protected void setParentEofDetect(boolean bl) {
        InputStream inputStream = this._in;
        if (inputStream instanceof IndefiniteLengthInputStream) {
            ((IndefiniteLengthInputStream)inputStream).setEofOn00(bl);
        }
    }
}

