/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.LimitedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

class IndefiniteLengthInputStream
extends LimitedInputStream {
    private int _b1;
    private int _b2;
    private boolean _eofOn00 = true;
    private boolean _eofReached = false;

    IndefiniteLengthInputStream(InputStream inputStream, int n) throws IOException {
        super(inputStream, n);
        this._b1 = inputStream.read();
        this._b2 = inputStream.read();
        if (this._b2 >= 0) {
            this.checkForEof();
            return;
        }
        throw new EOFException();
    }

    private boolean checkForEof() {
        if (!this._eofReached && this._eofOn00 && this._b1 == 0 && this._b2 == 0) {
            this._eofReached = true;
            this.setParentEofDetect(true);
        }
        return this._eofReached;
    }

    @Override
    public int read() throws IOException {
        if (this.checkForEof()) {
            return -1;
        }
        int n = this._in.read();
        if (n >= 0) {
            int n2 = this._b1;
            this._b1 = this._b2;
            this._b2 = n;
            return n2;
        }
        throw new EOFException();
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if (!this._eofOn00 && n2 >= 3) {
            if (this._eofReached) {
                return -1;
            }
            if ((n2 = this._in.read(arrby, n + 2, n2 - 2)) >= 0) {
                arrby[n] = (byte)this._b1;
                arrby[n + 1] = (byte)this._b2;
                this._b1 = this._in.read();
                this._b2 = this._in.read();
                if (this._b2 >= 0) {
                    return n2 + 2;
                }
                throw new EOFException();
            }
            throw new EOFException();
        }
        return super.read(arrby, n, n2);
    }

    void setEofOn00(boolean bl) {
        this._eofOn00 = bl;
        this.checkForEof();
    }
}

