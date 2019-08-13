/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.MediaCodec;
import android.media.MediaFormat;
import dalvik.system.CloseGuard;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Set;

public final class MediaMuxer {
    private static final int MUXER_STATE_INITIALIZED = 0;
    @UnsupportedAppUsage
    private static final int MUXER_STATE_STARTED = 1;
    @UnsupportedAppUsage
    private static final int MUXER_STATE_STOPPED = 2;
    @UnsupportedAppUsage
    private static final int MUXER_STATE_UNINITIALIZED = -1;
    @UnsupportedAppUsage
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private int mLastTrackIndex = -1;
    @UnsupportedAppUsage
    private long mNativeObject;
    @UnsupportedAppUsage
    private int mState = -1;

    static {
        System.loadLibrary("media_jni");
    }

    public MediaMuxer(FileDescriptor fileDescriptor, int n) throws IOException {
        this.setUpMediaMuxer(fileDescriptor, n);
    }

    public MediaMuxer(String object, int n) throws IOException {
        if (object != null) {
            RandomAccessFile randomAccessFile = null;
            Object object2 = randomAccessFile;
            object2 = randomAccessFile;
            try {
                RandomAccessFile randomAccessFile2 = new RandomAccessFile((String)object, "rws");
                object2 = object = randomAccessFile2;
            }
            catch (Throwable throwable) {
                if (object2 != null) {
                    ((RandomAccessFile)object2).close();
                }
                throw throwable;
            }
            ((RandomAccessFile)object).setLength(0L);
            object2 = object;
            this.setUpMediaMuxer(((RandomAccessFile)object).getFD(), n);
            ((RandomAccessFile)object).close();
            return;
        }
        throw new IllegalArgumentException("path must not be null");
    }

    private static native int nativeAddTrack(long var0, String[] var2, Object[] var3);

    @UnsupportedAppUsage
    private static native void nativeRelease(long var0);

    private static native void nativeSetLocation(long var0, int var2, int var3);

    private static native void nativeSetOrientationHint(long var0, int var2);

    @UnsupportedAppUsage
    private static native long nativeSetup(FileDescriptor var0, int var1) throws IllegalArgumentException, IOException;

    private static native void nativeStart(long var0);

    private static native void nativeStop(long var0);

    private static native void nativeWriteSampleData(long var0, int var2, ByteBuffer var3, int var4, int var5, long var6, int var8);

    private void setUpMediaMuxer(FileDescriptor object, int n) throws IOException {
        if (n >= 0 && n <= 4) {
            this.mNativeObject = MediaMuxer.nativeSetup((FileDescriptor)object, n);
            this.mState = 0;
            this.mCloseGuard.open("release");
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("format: ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" is invalid");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public int addTrack(MediaFormat arrstring) {
        if (arrstring != null) {
            if (this.mState == 0) {
                if (this.mNativeObject != 0L) {
                    Map<String, Object> map = arrstring.getMap();
                    int n = map.size();
                    if (n > 0) {
                        arrstring = new String[n];
                        Object[] arrobject = new Object[n];
                        n = 0;
                        for (Map.Entry entry : map.entrySet()) {
                            arrstring[n] = (String)entry.getKey();
                            arrobject[n] = entry.getValue();
                            ++n;
                        }
                        n = MediaMuxer.nativeAddTrack(this.mNativeObject, arrstring, arrobject);
                        if (this.mLastTrackIndex < n) {
                            this.mLastTrackIndex = n;
                            return n;
                        }
                        throw new IllegalArgumentException("Invalid format.");
                    }
                    throw new IllegalArgumentException("format must not be empty.");
                }
                throw new IllegalStateException("Muxer has been released!");
            }
            throw new IllegalStateException("Muxer is not initialized.");
        }
        throw new IllegalArgumentException("format must not be null.");
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            if (this.mNativeObject != 0L) {
                MediaMuxer.nativeRelease(this.mNativeObject);
                this.mNativeObject = 0L;
            }
            return;
        }
        finally {
            super.finalize();
        }
    }

    public void release() {
        long l;
        if (this.mState == 1) {
            this.stop();
        }
        if ((l = this.mNativeObject) != 0L) {
            MediaMuxer.nativeRelease(l);
            this.mNativeObject = 0L;
            this.mCloseGuard.close();
        }
        this.mState = -1;
    }

    public void setLocation(float f, float f2) {
        int n = (int)((double)(f * 10000.0f) + 0.5);
        int n2 = (int)((double)(10000.0f * f2) + 0.5);
        if (n <= 900000 && n >= -900000) {
            if (n2 <= 1800000 && n2 >= -1800000) {
                long l;
                if (this.mState == 0 && (l = this.mNativeObject) != 0L) {
                    MediaMuxer.nativeSetLocation(l, n, n2);
                    return;
                }
                throw new IllegalStateException("Can't set location due to wrong state.");
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Longitude: ");
            stringBuilder.append(f2);
            stringBuilder.append(" out of range");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Latitude: ");
        stringBuilder.append(f);
        stringBuilder.append(" out of range.");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setOrientationHint(int n) {
        if (n != 0 && n != 90 && n != 180 && n != 270) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported angle: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (this.mState == 0) {
            MediaMuxer.nativeSetOrientationHint(this.mNativeObject, n);
            return;
        }
        throw new IllegalStateException("Can't set rotation degrees due to wrong state.");
    }

    public void start() {
        long l = this.mNativeObject;
        if (l != 0L) {
            if (this.mState == 0) {
                MediaMuxer.nativeStart(l);
                this.mState = 1;
                return;
            }
            throw new IllegalStateException("Can't start due to wrong state.");
        }
        throw new IllegalStateException("Muxer has been released!");
    }

    public void stop() {
        if (this.mState == 1) {
            MediaMuxer.nativeStop(this.mNativeObject);
            this.mState = 2;
            return;
        }
        throw new IllegalStateException("Can't stop due to wrong state.");
    }

    public void writeSampleData(int n, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        if (n >= 0 && n <= this.mLastTrackIndex) {
            if (byteBuffer != null) {
                if (bufferInfo != null) {
                    if (bufferInfo.size >= 0 && bufferInfo.offset >= 0 && bufferInfo.offset + bufferInfo.size <= byteBuffer.capacity() && bufferInfo.presentationTimeUs >= 0L) {
                        long l = this.mNativeObject;
                        if (l != 0L) {
                            if (this.mState == 1) {
                                MediaMuxer.nativeWriteSampleData(l, n, byteBuffer, bufferInfo.offset, bufferInfo.size, bufferInfo.presentationTimeUs, bufferInfo.flags);
                                return;
                            }
                            throw new IllegalStateException("Can't write, muxer is not started");
                        }
                        throw new IllegalStateException("Muxer has been released!");
                    }
                    throw new IllegalArgumentException("bufferInfo must specify a valid buffer offset, size and presentation time");
                }
                throw new IllegalArgumentException("bufferInfo must not be null");
            }
            throw new IllegalArgumentException("byteBuffer must not be null");
        }
        throw new IllegalArgumentException("trackIndex is invalid");
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Format {
    }

    public static final class OutputFormat {
        public static final int MUXER_OUTPUT_3GPP = 2;
        public static final int MUXER_OUTPUT_FIRST = 0;
        public static final int MUXER_OUTPUT_HEIF = 3;
        public static final int MUXER_OUTPUT_LAST = 4;
        public static final int MUXER_OUTPUT_MPEG_4 = 0;
        public static final int MUXER_OUTPUT_OGG = 4;
        public static final int MUXER_OUTPUT_WEBM = 1;

        private OutputFormat() {
        }
    }

}

