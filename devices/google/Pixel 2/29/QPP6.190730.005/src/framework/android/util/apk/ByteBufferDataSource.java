/*
 * Decompiled with CFR 0.145.
 */
package android.util.apk;

import android.util.apk.DataDigester;
import android.util.apk.DataSource;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.DigestException;

class ByteBufferDataSource
implements DataSource {
    private final ByteBuffer mBuf;

    ByteBufferDataSource(ByteBuffer byteBuffer) {
        this.mBuf = byteBuffer.slice();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void feedIntoDataDigester(DataDigester dataDigester, long l, int n) throws IOException, DigestException {
        ByteBuffer byteBuffer;
        ByteBuffer byteBuffer2 = this.mBuf;
        synchronized (byteBuffer2) {
            this.mBuf.position(0);
            this.mBuf.limit((int)l + n);
            this.mBuf.position((int)l);
            byteBuffer = this.mBuf.slice();
        }
        dataDigester.consume(byteBuffer);
    }

    @Override
    public long size() {
        return this.mBuf.capacity();
    }
}

