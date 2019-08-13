/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.LimitedInputStream;
import com.android.org.bouncycastle.util.io.Streams;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

class DefiniteLengthInputStream
extends LimitedInputStream {
    private static final byte[] EMPTY_BYTES = new byte[0];
    private final int _originalLength;
    private int _remaining;

    DefiniteLengthInputStream(InputStream inputStream, int n) {
        super(inputStream, n);
        if (n >= 0) {
            this._originalLength = n;
            this._remaining = n;
            if (n == 0) {
                this.setParentEofDetect(true);
            }
            return;
        }
        throw new IllegalArgumentException("negative lengths not allowed");
    }

    @Override
    int getRemaining() {
        return this._remaining;
    }

    @Override
    public int read() throws IOException {
        if (this._remaining == 0) {
            return -1;
        }
        int n = this._in.read();
        if (n >= 0) {
            int n2;
            this._remaining = n2 = this._remaining - 1;
            if (n2 == 0) {
                this.setParentEofDetect(true);
            }
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DEF length ");
        stringBuilder.append(this._originalLength);
        stringBuilder.append(" object truncated by ");
        stringBuilder.append(this._remaining);
        throw new EOFException(stringBuilder.toString());
    }

    @Override
    public int read(byte[] object, int n, int n2) throws IOException {
        int n3 = this._remaining;
        if (n3 == 0) {
            return -1;
        }
        n2 = Math.min(n2, n3);
        if ((n2 = this._in.read((byte[])object, n, n2)) >= 0) {
            this._remaining = n = this._remaining - n2;
            if (n == 0) {
                this.setParentEofDetect(true);
            }
            return n2;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("DEF length ");
        ((StringBuilder)object).append(this._originalLength);
        ((StringBuilder)object).append(" object truncated by ");
        ((StringBuilder)object).append(this._remaining);
        throw new EOFException(((StringBuilder)object).toString());
    }

    byte[] toByteArray() throws IOException {
        int n = this._remaining;
        if (n == 0) {
            return EMPTY_BYTES;
        }
        Object object = new byte[n];
        this._remaining = n -= Streams.readFully(this._in, (byte[])object);
        if (n == 0) {
            this.setParentEofDetect(true);
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("DEF length ");
        ((StringBuilder)object).append(this._originalLength);
        ((StringBuilder)object).append(" object truncated by ");
        ((StringBuilder)object).append(this._remaining);
        throw new EOFException(((StringBuilder)object).toString());
    }
}

