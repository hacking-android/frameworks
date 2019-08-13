/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 *  dalvik.system.CloseGuard
 *  libcore.util.NativeAllocationRegistry
 */
package android.hardware;

import android.annotation.UnsupportedAppUsage;
import android.graphics.GraphicBuffer;
import android.os.Parcel;
import android.os.Parcelable;
import dalvik.annotation.optimization.FastNative;
import dalvik.system.CloseGuard;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import libcore.util.NativeAllocationRegistry;

public final class HardwareBuffer
implements Parcelable,
AutoCloseable {
    public static final int BLOB = 33;
    public static final Parcelable.Creator<HardwareBuffer> CREATOR = new Parcelable.Creator<HardwareBuffer>(){

        @Override
        public HardwareBuffer createFromParcel(Parcel parcel) {
            long l = HardwareBuffer.nReadHardwareBufferFromParcel(parcel);
            if (l != 0L) {
                return new HardwareBuffer(l);
            }
            return null;
        }

        public HardwareBuffer[] newArray(int n) {
            return new HardwareBuffer[n];
        }
    };
    public static final int DS_24UI8 = 50;
    public static final int DS_FP32UI8 = 52;
    public static final int D_16 = 48;
    public static final int D_24 = 49;
    public static final int D_FP32 = 51;
    private static final long NATIVE_HARDWARE_BUFFER_SIZE = 232L;
    public static final int RGBA_1010102 = 43;
    public static final int RGBA_8888 = 1;
    public static final int RGBA_FP16 = 22;
    public static final int RGBX_8888 = 2;
    public static final int RGB_565 = 4;
    public static final int RGB_888 = 3;
    public static final int S_UI8 = 53;
    public static final long USAGE_CPU_READ_OFTEN = 3L;
    public static final long USAGE_CPU_READ_RARELY = 2L;
    public static final long USAGE_CPU_WRITE_OFTEN = 48L;
    public static final long USAGE_CPU_WRITE_RARELY = 32L;
    public static final long USAGE_GPU_COLOR_OUTPUT = 512L;
    public static final long USAGE_GPU_CUBE_MAP = 0x2000000L;
    public static final long USAGE_GPU_DATA_BUFFER = 0x1000000L;
    public static final long USAGE_GPU_MIPMAP_COMPLETE = 0x4000000L;
    public static final long USAGE_GPU_SAMPLED_IMAGE = 256L;
    public static final long USAGE_PROTECTED_CONTENT = 16384L;
    public static final long USAGE_SENSOR_DIRECT_DATA = 0x800000L;
    public static final long USAGE_VIDEO_ENCODE = 65536L;
    private Runnable mCleaner;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    @UnsupportedAppUsage
    private long mNativeObject;

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private HardwareBuffer(long l) {
        this.mNativeObject = l;
        this.mCleaner = new NativeAllocationRegistry(HardwareBuffer.class.getClassLoader(), HardwareBuffer.nGetNativeFinalizer(), 232L).registerNativeAllocation((Object)this, this.mNativeObject);
        this.mCloseGuard.open("close");
    }

    public static HardwareBuffer create(int n, int n2, int n3, int n4, long l) {
        if (HardwareBuffer.isSupportedFormat(n3)) {
            if (n > 0) {
                if (n2 > 0) {
                    if (n4 > 0) {
                        if (n3 == 33 && n2 != 1) {
                            throw new IllegalArgumentException("Height must be 1 when using the BLOB format");
                        }
                        if ((l = HardwareBuffer.nCreateHardwareBuffer(n, n2, n3, n4, l)) != 0L) {
                            return new HardwareBuffer(l);
                        }
                        throw new IllegalArgumentException("Unable to create a HardwareBuffer, either the dimensions passed were too large, too many image layers were requested, or an invalid set of usage flags or invalid format was passed");
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid layer count ");
                    stringBuilder.append(n4);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid height ");
                stringBuilder.append(n2);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid width ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid pixel format ");
        stringBuilder.append(n3);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static HardwareBuffer createFromGraphicBuffer(GraphicBuffer graphicBuffer) {
        return new HardwareBuffer(HardwareBuffer.nCreateFromGraphicBuffer(graphicBuffer));
    }

    public static boolean isSupported(int n, int n2, int n3, int n4, long l) {
        if (HardwareBuffer.isSupportedFormat(n3)) {
            if (n > 0) {
                if (n2 > 0) {
                    if (n4 > 0) {
                        if (n3 == 33 && n2 != 1) {
                            throw new IllegalArgumentException("Height must be 1 when using the BLOB format");
                        }
                        return HardwareBuffer.nIsSupported(n, n2, n3, n4, l);
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid layer count ");
                    stringBuilder.append(n4);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid height ");
                stringBuilder.append(n2);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid width ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid pixel format ");
        stringBuilder.append(n3);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static boolean isSupportedFormat(int n) {
        if (n != 1 && n != 2 && n != 3 && n != 4 && n != 22 && n != 33 && n != 43) {
            switch (n) {
                default: {
                    return false;
                }
                case 48: 
                case 49: 
                case 50: 
                case 51: 
                case 52: 
                case 53: 
            }
        }
        return true;
    }

    private static native long nCreateFromGraphicBuffer(GraphicBuffer var0);

    private static native long nCreateHardwareBuffer(int var0, int var1, int var2, int var3, long var4);

    @FastNative
    private static native int nGetFormat(long var0);

    @FastNative
    private static native int nGetHeight(long var0);

    @FastNative
    private static native int nGetLayers(long var0);

    private static native long nGetNativeFinalizer();

    @FastNative
    private static native long nGetUsage(long var0);

    @FastNative
    private static native int nGetWidth(long var0);

    private static native boolean nIsSupported(int var0, int var1, int var2, int var3, long var4);

    private static native long nReadHardwareBufferFromParcel(Parcel var0);

    private static native void nWriteHardwareBufferToParcel(long var0, Parcel var2);

    @Override
    public void close() {
        if (!this.isClosed()) {
            this.mCloseGuard.close();
            this.mNativeObject = 0L;
            this.mCleaner.run();
            this.mCleaner = null;
        }
    }

    @Override
    public int describeContents() {
        return 1;
    }

    @Deprecated
    public void destroy() {
        this.close();
    }

    protected void finalize() throws Throwable {
        try {
            this.mCloseGuard.warnIfOpen();
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    public int getFormat() {
        if (!this.isClosed()) {
            return HardwareBuffer.nGetFormat(this.mNativeObject);
        }
        throw new IllegalStateException("This HardwareBuffer has been closed and its format cannot be obtained.");
    }

    public int getHeight() {
        if (!this.isClosed()) {
            return HardwareBuffer.nGetHeight(this.mNativeObject);
        }
        throw new IllegalStateException("This HardwareBuffer has been closed and its height cannot be obtained.");
    }

    public int getLayers() {
        if (!this.isClosed()) {
            return HardwareBuffer.nGetLayers(this.mNativeObject);
        }
        throw new IllegalStateException("This HardwareBuffer has been closed and its layer count cannot be obtained.");
    }

    public long getUsage() {
        if (!this.isClosed()) {
            return HardwareBuffer.nGetUsage(this.mNativeObject);
        }
        throw new IllegalStateException("This HardwareBuffer has been closed and its usage cannot be obtained.");
    }

    public int getWidth() {
        if (!this.isClosed()) {
            return HardwareBuffer.nGetWidth(this.mNativeObject);
        }
        throw new IllegalStateException("This HardwareBuffer has been closed and its width cannot be obtained.");
    }

    public boolean isClosed() {
        boolean bl = this.mNativeObject == 0L;
        return bl;
    }

    @Deprecated
    public boolean isDestroyed() {
        return this.isClosed();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (!this.isClosed()) {
            HardwareBuffer.nWriteHardwareBufferToParcel(this.mNativeObject, parcel);
            return;
        }
        throw new IllegalStateException("This HardwareBuffer has been closed and cannot be written to a parcel.");
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Format {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Usage {
    }

}

