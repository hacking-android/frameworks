/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.OpenSSLBIOInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

final class OpenSSLBIOSource {
    private OpenSSLBIOInputStream source;

    private OpenSSLBIOSource(OpenSSLBIOInputStream openSSLBIOInputStream) {
        this.source = openSSLBIOInputStream;
    }

    private void release() {
        synchronized (this) {
            if (this.source != null) {
                NativeCrypto.BIO_free_all(this.source.getBioContext());
                this.source = null;
            }
            return;
        }
    }

    static OpenSSLBIOSource wrap(ByteBuffer byteBuffer) {
        return new OpenSSLBIOSource(new OpenSSLBIOInputStream(new ByteBufferInputStream(byteBuffer), false));
    }

    protected void finalize() throws Throwable {
        try {
            this.release();
            return;
        }
        finally {
            super.finalize();
        }
    }

    long getContext() {
        return this.source.getBioContext();
    }

    private static class ByteBufferInputStream
    extends InputStream {
        private final ByteBuffer source;

        ByteBufferInputStream(ByteBuffer byteBuffer) {
            this.source = byteBuffer;
        }

        @Override
        public int available() throws IOException {
            return this.source.limit() - this.source.position();
        }

        @Override
        public int read() throws IOException {
            if (this.source.remaining() > 0) {
                return this.source.get();
            }
            return -1;
        }

        @Override
        public int read(byte[] arrby) throws IOException {
            int n = this.source.position();
            this.source.get(arrby);
            return this.source.position() - n;
        }

        @Override
        public int read(byte[] arrby, int n, int n2) throws IOException {
            n2 = Math.min(this.source.remaining(), n2);
            int n3 = this.source.position();
            this.source.get(arrby, n, n2);
            return this.source.position() - n3;
        }

        @Override
        public void reset() throws IOException {
            this.source.reset();
        }

        @Override
        public long skip(long l) throws IOException {
            long l2 = this.source.position();
            this.source.position((int)(l2 + l));
            return (long)this.source.position() - l2;
        }
    }

}

