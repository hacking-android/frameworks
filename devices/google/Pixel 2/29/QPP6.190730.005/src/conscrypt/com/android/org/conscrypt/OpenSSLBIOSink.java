/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import java.io.ByteArrayOutputStream;

final class OpenSSLBIOSink {
    private final ByteArrayOutputStream buffer;
    private final long ctx;
    private int position;

    private OpenSSLBIOSink(ByteArrayOutputStream byteArrayOutputStream) {
        this.ctx = NativeCrypto.create_BIO_OutputStream(byteArrayOutputStream);
        this.buffer = byteArrayOutputStream;
    }

    static OpenSSLBIOSink create() {
        return new OpenSSLBIOSink(new ByteArrayOutputStream());
    }

    int available() {
        return this.buffer.size() - this.position;
    }

    protected void finalize() throws Throwable {
        try {
            NativeCrypto.BIO_free_all(this.ctx);
            return;
        }
        finally {
            super.finalize();
        }
    }

    long getContext() {
        return this.ctx;
    }

    int position() {
        return this.position;
    }

    void reset() {
        this.buffer.reset();
        this.position = 0;
    }

    long skip(long l) {
        int n = Math.min(this.available(), (int)l);
        this.position += n;
        if (this.position == this.buffer.size()) {
            this.reset();
        }
        return n;
    }

    byte[] toByteArray() {
        return this.buffer.toByteArray();
    }
}

