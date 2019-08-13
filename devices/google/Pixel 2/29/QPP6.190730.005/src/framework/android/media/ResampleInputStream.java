/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import java.io.IOException;
import java.io.InputStream;

public final class ResampleInputStream
extends InputStream {
    private static final String TAG = "ResampleInputStream";
    private static final int mFirLength = 29;
    private byte[] mBuf;
    private int mBufCount;
    private InputStream mInputStream;
    private final byte[] mOneByte = new byte[1];
    private final int mRateIn;
    private final int mRateOut;

    static {
        System.loadLibrary("media_jni");
    }

    public ResampleInputStream(InputStream inputStream, int n, int n2) {
        if (n == n2 * 2) {
            this.mInputStream = inputStream;
            this.mRateIn = 2;
            this.mRateOut = 1;
            return;
        }
        throw new IllegalArgumentException("only support 2:1 at the moment");
    }

    private static native void fir21(byte[] var0, int var1, byte[] var2, int var3, int var4);

    @Override
    public void close() throws IOException {
        try {
            if (this.mInputStream != null) {
                this.mInputStream.close();
            }
            return;
        }
        finally {
            this.mInputStream = null;
        }
    }

    protected void finalize() throws Throwable {
        if (this.mInputStream == null) {
            return;
        }
        this.close();
        throw new IllegalStateException("someone forgot to close ResampleInputStream");
    }

    @Override
    public int read() throws IOException {
        int n = this.read(this.mOneByte, 0, 1) == 1 ? this.mOneByte[0] & 255 : -1;
        return n;
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if (this.mInputStream != null) {
            byte[] arrby2;
            int n3 = (n2 / 2 * this.mRateIn / this.mRateOut + 29) * 2;
            Object object = this.mBuf;
            if (object == null) {
                this.mBuf = new byte[n3];
            } else if (n3 > ((byte[])object).length) {
                arrby2 = new byte[n3];
                System.arraycopy(object, 0, arrby2, 0, this.mBufCount);
                this.mBuf = arrby2;
            }
            do {
                int n4;
                if ((n3 = ((n4 = this.mBufCount) / 2 - 29) * this.mRateOut / this.mRateIn * 2) > 0) {
                    n2 = n3 < n2 ? n3 : n2 / 2 * 2;
                    ResampleInputStream.fir21(this.mBuf, 0, arrby, n, n2 / 2);
                    n = this.mRateIn * n2 / this.mRateOut;
                    this.mBufCount -= n;
                    n3 = this.mBufCount;
                    if (n3 > 0) {
                        arrby = this.mBuf;
                        System.arraycopy(arrby, n, arrby, 0, n3);
                    }
                    return n2;
                }
                object = this.mInputStream;
                arrby2 = this.mBuf;
                n3 = ((InputStream)object).read(arrby2, n4, arrby2.length - n4);
                if (n3 == -1) {
                    return -1;
                }
                this.mBufCount += n3;
            } while (true);
        }
        throw new IllegalStateException("not open");
    }
}

