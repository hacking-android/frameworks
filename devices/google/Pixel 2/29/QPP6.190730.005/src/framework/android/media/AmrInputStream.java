/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.MediaCodec;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.util.Log;
import android.view.Surface;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class AmrInputStream
extends InputStream {
    private static final int SAMPLES_PER_FRAME = 160;
    private static final String TAG = "AmrInputStream";
    private final byte[] mBuf = new byte[320];
    private int mBufIn = 0;
    private int mBufOut = 0;
    MediaCodec mCodec;
    MediaCodec.BufferInfo mInfo;
    private InputStream mInputStream;
    private byte[] mOneByte = new byte[1];
    boolean mSawInputEOS;
    boolean mSawOutputEOS;

    @UnsupportedAppUsage
    public AmrInputStream(InputStream object) {
        Log.w(TAG, "@@@@ AmrInputStream is not a public API @@@@");
        this.mInputStream = object;
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setString("mime", "audio/3gpp");
        mediaFormat.setInteger("sample-rate", 8000);
        mediaFormat.setInteger("channel-count", 1);
        mediaFormat.setInteger("bitrate", 12200);
        object = new MediaCodecList(0).findEncoderForFormat(mediaFormat);
        if (object != null) {
            try {
                this.mCodec = MediaCodec.createByCodecName((String)object);
                this.mCodec.configure(mediaFormat, null, null, 1);
                this.mCodec.start();
            }
            catch (IOException iOException) {
                MediaCodec mediaCodec = this.mCodec;
                if (mediaCodec != null) {
                    mediaCodec.release();
                }
                this.mCodec = null;
            }
        }
        this.mInfo = new MediaCodec.BufferInfo();
    }

    @Override
    public void close() throws IOException {
        try {
            if (this.mInputStream != null) {
                this.mInputStream.close();
            }
            this.mInputStream = null;
        }
        catch (Throwable throwable) {
            this.mInputStream = null;
            try {
                if (this.mCodec != null) {
                    this.mCodec.release();
                }
                throw throwable;
            }
            finally {
                this.mCodec = null;
            }
        }
        try {
            if (this.mCodec != null) {
                this.mCodec.release();
            }
            return;
        }
        finally {
            this.mCodec = null;
        }
    }

    protected void finalize() throws Throwable {
        if (this.mCodec != null) {
            Log.w(TAG, "AmrInputStream wasn't closed");
            this.mCodec.release();
        }
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
        if (this.mCodec != null) {
            int n3;
            int n4;
            if (this.mBufOut >= this.mBufIn && !this.mSawOutputEOS) {
                int n5;
                this.mBufOut = 0;
                this.mBufIn = 0;
                while (!this.mSawInputEOS && (n5 = this.mCodec.dequeueInputBuffer(0L)) >= 0) {
                    for (n3 = 0; n3 < 320; n3 += n4) {
                        n4 = this.mInputStream.read(this.mBuf, n3, 320 - n3);
                        if (n4 != -1) continue;
                        this.mSawInputEOS = true;
                        break;
                    }
                    this.mCodec.getInputBuffer(n5).put(this.mBuf, 0, n3);
                    MediaCodec mediaCodec = this.mCodec;
                    n4 = this.mSawInputEOS ? 4 : 0;
                    mediaCodec.queueInputBuffer(n5, 0, n3, 0L, n4);
                }
                n3 = this.mCodec.dequeueOutputBuffer(this.mInfo, 0L);
                if (n3 >= 0) {
                    this.mBufIn = this.mInfo.size;
                    this.mCodec.getOutputBuffer(n3).get(this.mBuf, 0, this.mBufIn);
                    this.mCodec.releaseOutputBuffer(n3, false);
                    if ((4 & this.mInfo.flags) != 0) {
                        this.mSawOutputEOS = true;
                    }
                }
            }
            if ((n3 = this.mBufOut) < (n4 = this.mBufIn)) {
                if (n2 > n4 - n3) {
                    n2 = n4 - n3;
                }
                System.arraycopy(this.mBuf, this.mBufOut, arrby, n, n2);
                this.mBufOut += n2;
                return n2;
            }
            if (this.mSawInputEOS && this.mSawOutputEOS) {
                return -1;
            }
            return 0;
        }
        throw new IllegalStateException("not open");
    }
}

