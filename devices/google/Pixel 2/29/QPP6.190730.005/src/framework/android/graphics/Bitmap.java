/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.CriticalNative
 *  libcore.util.NativeAllocationRegistry
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.content.res.ResourcesImpl;
import android.graphics.BaseRecordingCanvas;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.GraphicBuffer;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.RenderNode;
import android.hardware.HardwareBuffer;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;
import android.os.Trace;
import android.util.DisplayMetrics;
import android.util.Half;
import android.util.Log;
import android.view.ThreadedRenderer;
import dalvik.annotation.optimization.CriticalNative;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import libcore.util.NativeAllocationRegistry;

public final class Bitmap
implements Parcelable {
    public static final Parcelable.Creator<Bitmap> CREATOR;
    public static final int DENSITY_NONE = 0;
    private static final long NATIVE_ALLOCATION_SIZE = 32L;
    private static final String TAG = "Bitmap";
    private static final int WORKING_COMPRESS_STORAGE = 4096;
    private static volatile int sDefaultDensity;
    public static volatile int sPreloadTracingNumInstantiatedBitmaps;
    public static volatile long sPreloadTracingTotalBitmapsSize;
    private ColorSpace mColorSpace;
    public int mDensity = Bitmap.getDefaultDensity();
    @UnsupportedAppUsage
    private int mHeight;
    @UnsupportedAppUsage
    private final long mNativePtr;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123769491L)
    private byte[] mNinePatchChunk;
    @UnsupportedAppUsage
    private NinePatch.InsetStruct mNinePatchInsets;
    private boolean mRecycled;
    private boolean mRequestPremultiplied;
    @UnsupportedAppUsage
    private int mWidth;

    static {
        sDefaultDensity = -1;
        CREATOR = new Parcelable.Creator<Bitmap>(){

            @Override
            public Bitmap createFromParcel(Parcel object) {
                if ((object = Bitmap.nativeCreateFromParcel((Parcel)object)) != null) {
                    return object;
                }
                throw new RuntimeException("Failed to unparcel Bitmap");
            }

            public Bitmap[] newArray(int n) {
                return new Bitmap[n];
            }
        };
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    Bitmap(long l, int n, int n2, int n3, boolean bl, byte[] arrby, NinePatch.InsetStruct insetStruct) {
        this(l, n, n2, n3, bl, arrby, insetStruct, true);
    }

    Bitmap(long l, int n, int n2, int n3, boolean bl, byte[] nativeAllocationRegistry, NinePatch.InsetStruct insetStruct, boolean bl2) {
        if (l != 0L) {
            this.mWidth = n;
            this.mHeight = n2;
            this.mRequestPremultiplied = bl;
            this.mNinePatchChunk = nativeAllocationRegistry;
            this.mNinePatchInsets = insetStruct;
            if (n3 >= 0) {
                this.mDensity = n3;
            }
            this.mNativePtr = l;
            n = this.getAllocationByteCount();
            nativeAllocationRegistry = bl2 ? NativeAllocationRegistry.createMalloced((ClassLoader)Bitmap.class.getClassLoader(), (long)Bitmap.nativeGetNativeFinalizer(), (long)n) : NativeAllocationRegistry.createNonmalloced((ClassLoader)Bitmap.class.getClassLoader(), (long)Bitmap.nativeGetNativeFinalizer(), (long)n);
            nativeAllocationRegistry.registerNativeAllocation((Object)this, l);
            if (ResourcesImpl.TRACE_FOR_DETAILED_PRELOAD) {
                ++sPreloadTracingNumInstantiatedBitmaps;
                l = n;
                sPreloadTracingTotalBitmapsSize += l + 32L;
            }
            return;
        }
        throw new RuntimeException("internal error: native bitmap is 0");
    }

    private void checkHardware(String string2) {
        if (this.getConfig() != Config.HARDWARE) {
            return;
        }
        throw new IllegalStateException(string2);
    }

    private void checkPixelAccess(int n, int n2) {
        Bitmap.checkXYSign(n, n2);
        if (n < this.getWidth()) {
            if (n2 < this.getHeight()) {
                return;
            }
            throw new IllegalArgumentException("y must be < bitmap.height()");
        }
        throw new IllegalArgumentException("x must be < bitmap.width()");
    }

    private void checkPixelsAccess(int n, int n2, int n3, int n4, int n5, int n6, int[] arrn) {
        Bitmap.checkXYSign(n, n2);
        if (n3 >= 0) {
            if (n4 >= 0) {
                if (n + n3 <= this.getWidth()) {
                    if (n2 + n4 <= this.getHeight()) {
                        if (Math.abs(n6) >= n3) {
                            n2 = (n4 - 1) * n6 + n5;
                            n = arrn.length;
                            if (n5 >= 0 && n5 + n3 <= n && n2 >= 0 && n2 + n3 <= n) {
                                return;
                            }
                            throw new ArrayIndexOutOfBoundsException();
                        }
                        throw new IllegalArgumentException("abs(stride) must be >= width");
                    }
                    throw new IllegalArgumentException("y + height must be <= bitmap.height()");
                }
                throw new IllegalArgumentException("x + width must be <= bitmap.width()");
            }
            throw new IllegalArgumentException("height must be >= 0");
        }
        throw new IllegalArgumentException("width must be >= 0");
    }

    private void checkRecycled(String string2) {
        if (!this.mRecycled) {
            return;
        }
        throw new IllegalStateException(string2);
    }

    private static void checkWidthHeight(int n, int n2) {
        if (n > 0) {
            if (n2 > 0) {
                return;
            }
            throw new IllegalArgumentException("height must be > 0");
        }
        throw new IllegalArgumentException("width must be > 0");
    }

    private static void checkXYSign(int n, int n2) {
        if (n >= 0) {
            if (n2 >= 0) {
                return;
            }
            throw new IllegalArgumentException("y must be >= 0");
        }
        throw new IllegalArgumentException("x must be >= 0");
    }

    private static float clamp(float f, ColorSpace colorSpace, int n) {
        return Math.max(Math.min(f, colorSpace.getMaxValue(n)), colorSpace.getMinValue(n));
    }

    public static Bitmap createBitmap(int n, int n2, Config config) {
        return Bitmap.createBitmap(n, n2, config, true);
    }

    public static Bitmap createBitmap(int n, int n2, Config config, boolean bl) {
        return Bitmap.createBitmap(null, n, n2, config, bl);
    }

    public static Bitmap createBitmap(int n, int n2, Config config, boolean bl, ColorSpace colorSpace) {
        return Bitmap.createBitmap(null, n, n2, config, bl, colorSpace);
    }

    public static Bitmap createBitmap(Bitmap bitmap) {
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());
    }

    public static Bitmap createBitmap(Bitmap bitmap, int n, int n2, int n3, int n4) {
        return Bitmap.createBitmap(bitmap, n, n2, n3, n4, null, false);
    }

    public static Bitmap createBitmap(Bitmap object, int n, int n2, int n3, int n4, Matrix matrix, boolean bl) {
        Object object2 = object;
        Bitmap.checkXYSign(n, n2);
        Bitmap.checkWidthHeight(n3, n4);
        if (n + n3 <= object.getWidth()) {
            if (n2 + n4 <= object.getHeight()) {
                if (!object.isRecycled()) {
                    if (!object.isMutable() && n == 0 && n2 == 0 && n3 == object.getWidth() && n4 == object.getHeight() && (matrix == null || matrix.isIdentity())) {
                        return object2;
                    }
                    boolean bl2 = object.getConfig() == Config.HARDWARE;
                    Bitmap bitmap = object2;
                    if (bl2) {
                        object.noteHardwareBitmapSlowCall();
                        bitmap = Bitmap.nativeCopyPreserveInternalConfig(object2.mNativePtr);
                    }
                    Rect rect = new Rect(n, n2, n + n3, n2 + n4);
                    RectF rectF = new RectF(0.0f, 0.0f, n3, n4);
                    RectF rectF2 = new RectF();
                    object = Config.ARGB_8888;
                    object2 = bitmap.getConfig();
                    if (object2 != null) {
                        n = 2.$SwitchMap$android$graphics$Bitmap$Config[((Enum)object2).ordinal()];
                        object = n != 1 ? (n != 2 ? (n != 3 ? Config.ARGB_8888 : Config.RGBA_F16) : Config.ALPHA_8) : Config.RGB_565;
                    }
                    Object object3 = bitmap.getColorSpace();
                    if (matrix != null && !matrix.isIdentity()) {
                        n2 = matrix.rectStaysRect() ^ true;
                        matrix.mapRect(rectF2, rectF);
                        n = Math.round(rectF2.width());
                        n3 = Math.round(rectF2.height());
                        if (n2 != 0 && object != Config.ARGB_8888 && object != Config.RGBA_F16) {
                            Config config = Config.ARGB_8888;
                            object = config;
                            object2 = object3;
                            if (object3 == null) {
                                object2 = ColorSpace.get(ColorSpace.Named.SRGB);
                                object = config;
                            }
                        } else {
                            object2 = object3;
                        }
                        boolean bl3 = n2 != 0 || bitmap.hasAlpha();
                        object = Bitmap.createBitmap(null, n, n3, (Config)((Object)object), bl3, (ColorSpace)object2);
                        object2 = new Paint();
                        ((Paint)object2).setFilterBitmap(bl);
                        if (n2 != 0) {
                            ((Paint)object2).setAntiAlias(true);
                        }
                    } else {
                        object = Bitmap.createBitmap(null, n3, n4, (Config)((Object)object), bitmap.hasAlpha(), (ColorSpace)object3);
                        object2 = null;
                    }
                    object.mDensity = bitmap.mDensity;
                    object.setHasAlpha(bitmap.hasAlpha());
                    object.setPremultiplied(bitmap.mRequestPremultiplied);
                    object3 = new Canvas((Bitmap)object);
                    ((Canvas)object3).translate(-rectF2.left, -rectF2.top);
                    ((Canvas)object3).concat(matrix);
                    ((Canvas)object3).drawBitmap(bitmap, rect, rectF, (Paint)object2);
                    ((Canvas)object3).setBitmap(null);
                    if (bl2) {
                        return object.copy(Config.HARDWARE, false);
                    }
                    return object;
                }
                throw new IllegalArgumentException("cannot use a recycled source in createBitmap");
            }
            throw new IllegalArgumentException("y + height must be <= bitmap.height()");
        }
        throw new IllegalArgumentException("x + width must be <= bitmap.width()");
    }

    public static Bitmap createBitmap(Picture picture) {
        return Bitmap.createBitmap(picture, picture.getWidth(), picture.getHeight(), Config.HARDWARE);
    }

    public static Bitmap createBitmap(Picture object, int n, int n2, Config object2) {
        if (n > 0 && n2 > 0) {
            if (object2 != null) {
                ((Picture)object).endRecording();
                if (((Picture)object).requiresHardwareAcceleration() && object2 != Config.HARDWARE) {
                    StrictMode.noteSlowCall("GPU readback");
                }
                if (object2 != Config.HARDWARE && !((Picture)object).requiresHardwareAcceleration()) {
                    object2 = Bitmap.createBitmap(n, n2, (Config)((Object)object2));
                    Canvas canvas = new Canvas((Bitmap)object2);
                    if (((Picture)object).getWidth() != n || ((Picture)object).getHeight() != n2) {
                        canvas.scale((float)n / (float)((Picture)object).getWidth(), (float)n2 / (float)((Picture)object).getHeight());
                    }
                    canvas.drawPicture((Picture)object);
                    canvas.setBitmap(null);
                    ((Bitmap)object2).setImmutable();
                    return object2;
                }
                RenderNode renderNode = RenderNode.create("BitmapTemporary", null);
                renderNode.setLeftTopRightBottom(0, 0, n, n2);
                renderNode.setClipToBounds(false);
                renderNode.setForceDarkAllowed(false);
                Object object3 = renderNode.beginRecording(n, n2);
                if (((Picture)object).getWidth() != n || ((Picture)object).getHeight() != n2) {
                    ((Canvas)object3).scale((float)n / (float)((Picture)object).getWidth(), (float)n2 / (float)((Picture)object).getHeight());
                }
                ((BaseRecordingCanvas)object3).drawPicture((Picture)object);
                renderNode.endRecording();
                object = object3 = ThreadedRenderer.createHardwareBitmap(renderNode, n, n2);
                if (object2 != Config.HARDWARE) {
                    object = ((Bitmap)object3).copy((Config)((Object)object2), false);
                }
                return object;
            }
            throw new IllegalArgumentException("Config must not be null");
        }
        throw new IllegalArgumentException("width & height must be > 0");
    }

    public static Bitmap createBitmap(DisplayMetrics displayMetrics, int n, int n2, Config config) {
        return Bitmap.createBitmap(displayMetrics, n, n2, config, true);
    }

    public static Bitmap createBitmap(DisplayMetrics displayMetrics, int n, int n2, Config config, boolean bl) {
        return Bitmap.createBitmap(displayMetrics, n, n2, config, bl, ColorSpace.get(ColorSpace.Named.SRGB));
    }

    public static Bitmap createBitmap(DisplayMetrics displayMetrics, int n, int n2, Config config, boolean bl, ColorSpace object) {
        if (n > 0 && n2 > 0) {
            if (config != Config.HARDWARE) {
                if (object == null && config != Config.ALPHA_8) {
                    throw new IllegalArgumentException("can't create bitmap without a color space");
                }
                int n3 = config.nativeInt;
                long l = object == null ? 0L : ((ColorSpace)object).getNativeInstance();
                object = Bitmap.nativeCreate(null, 0, n, n, n2, n3, true, l);
                if (displayMetrics != null) {
                    ((Bitmap)object).mDensity = displayMetrics.densityDpi;
                }
                ((Bitmap)object).setHasAlpha(bl);
                if (!(config != Config.ARGB_8888 && config != Config.RGBA_F16 || bl)) {
                    Bitmap.nativeErase(((Bitmap)object).mNativePtr, -16777216);
                }
                return object;
            }
            throw new IllegalArgumentException("can't create mutable bitmap with Config.HARDWARE");
        }
        throw new IllegalArgumentException("width and height must be > 0");
    }

    public static Bitmap createBitmap(DisplayMetrics displayMetrics, int[] object, int n, int n2, int n3, int n4, Config config) {
        Bitmap.checkWidthHeight(n3, n4);
        if (Math.abs(n2) >= n3) {
            int n5 = n + (n4 - 1) * n2;
            int n6 = ((int[])object).length;
            if (n >= 0 && n + n3 <= n6 && n5 >= 0 && n5 + n3 <= n6) {
                if (n3 > 0 && n4 > 0) {
                    ColorSpace colorSpace = ColorSpace.get(ColorSpace.Named.SRGB);
                    object = Bitmap.nativeCreate(object, n, n2, n3, n4, config.nativeInt, false, colorSpace.getNativeInstance());
                    if (displayMetrics != null) {
                        object.mDensity = displayMetrics.densityDpi;
                    }
                    return object;
                }
                throw new IllegalArgumentException("width and height must be > 0");
            }
            throw new ArrayIndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("abs(stride) must be >= width");
    }

    public static Bitmap createBitmap(DisplayMetrics displayMetrics, int[] arrn, int n, int n2, Config config) {
        return Bitmap.createBitmap(displayMetrics, arrn, 0, n, n, n2, config);
    }

    public static Bitmap createBitmap(int[] arrn, int n, int n2, int n3, int n4, Config config) {
        return Bitmap.createBitmap(null, arrn, n, n2, n3, n4, config);
    }

    public static Bitmap createBitmap(int[] arrn, int n, int n2, Config config) {
        return Bitmap.createBitmap(null, arrn, 0, n, n, n2, config);
    }

    public static Bitmap createScaledBitmap(Bitmap bitmap, int n, int n2, boolean bl) {
        Matrix matrix = new Matrix();
        int n3 = bitmap.getWidth();
        int n4 = bitmap.getHeight();
        if (n3 != n || n4 != n2) {
            matrix.setScale((float)n / (float)n3, (float)n2 / (float)n4);
        }
        return Bitmap.createBitmap(bitmap, 0, 0, n3, n4, matrix, bl);
    }

    @UnsupportedAppUsage
    static int getDefaultDensity() {
        if (sDefaultDensity >= 0) {
            return sDefaultDensity;
        }
        sDefaultDensity = DisplayMetrics.DENSITY_DEVICE;
        return sDefaultDensity;
    }

    private static native boolean nativeCompress(long var0, int var2, int var3, OutputStream var4, byte[] var5);

    private static native ColorSpace nativeComputeColorSpace(long var0);

    private static native int nativeConfig(long var0);

    private static native Bitmap nativeCopy(long var0, int var2, boolean var3);

    private static native Bitmap nativeCopyAshmem(long var0);

    private static native Bitmap nativeCopyAshmemConfig(long var0, int var2);

    private static native void nativeCopyPixelsFromBuffer(long var0, Buffer var2);

    private static native void nativeCopyPixelsToBuffer(long var0, Buffer var2);

    private static native Bitmap nativeCopyPreserveInternalConfig(long var0);

    private static native Bitmap nativeCreate(int[] var0, int var1, int var2, int var3, int var4, int var5, boolean var6, long var7);

    private static native Bitmap nativeCreateFromParcel(Parcel var0);

    private static native GraphicBuffer nativeCreateGraphicBufferHandle(long var0);

    private static native void nativeErase(long var0, int var2);

    private static native void nativeErase(long var0, long var2, long var4);

    private static native Bitmap nativeExtractAlpha(long var0, long var2, int[] var4);

    private static native int nativeGenerationId(long var0);

    private static native int nativeGetAllocationByteCount(long var0);

    private static native long nativeGetColor(long var0, int var2, int var3);

    private static native long nativeGetNativeFinalizer();

    private static native int nativeGetPixel(long var0, int var2, int var3);

    private static native void nativeGetPixels(long var0, int[] var2, int var3, int var4, int var5, int var6, int var7, int var8);

    private static native boolean nativeHasAlpha(long var0);

    private static native boolean nativeHasMipMap(long var0);

    @CriticalNative
    private static native boolean nativeIsImmutable(long var0);

    private static native boolean nativeIsPremultiplied(long var0);

    private static native boolean nativeIsSRGB(long var0);

    private static native boolean nativeIsSRGBLinear(long var0);

    private static native void nativePrepareToDraw(long var0);

    @UnsupportedAppUsage
    private static native void nativeReconfigure(long var0, int var2, int var3, int var4, boolean var5);

    private static native void nativeRecycle(long var0);

    private static native int nativeRowBytes(long var0);

    private static native boolean nativeSameAs(long var0, long var2);

    private static native void nativeSetColorSpace(long var0, long var2);

    private static native void nativeSetHasAlpha(long var0, boolean var2, boolean var3);

    private static native void nativeSetHasMipMap(long var0, boolean var2);

    private static native void nativeSetImmutable(long var0);

    private static native void nativeSetPixel(long var0, int var2, int var3, int var4);

    private static native void nativeSetPixels(long var0, int[] var2, int var3, int var4, int var5, int var6, int var7, int var8);

    private static native void nativeSetPremultiplied(long var0, boolean var2);

    private static native Bitmap nativeWrapHardwareBufferBitmap(HardwareBuffer var0, long var1);

    private static native boolean nativeWriteToParcel(long var0, boolean var2, int var3, Parcel var4);

    private void noteHardwareBitmapSlowCall() {
        if (this.getConfig() == Config.HARDWARE) {
            StrictMode.noteSlowCall("Warning: attempt to read pixels from hardware bitmap, which is very slow operation");
        }
    }

    @UnsupportedAppUsage
    public static int scaleFromDensity(int n, int n2, int n3) {
        if (n2 != 0 && n3 != 0 && n2 != n3) {
            return (n * n3 + (n2 >> 1)) / n2;
        }
        return n;
    }

    @UnsupportedAppUsage
    public static void setDefaultDensity(int n) {
        sDefaultDensity = n;
    }

    public static Bitmap wrapHardwareBuffer(GraphicBuffer parcelable, ColorSpace object) {
        parcelable = HardwareBuffer.createFromGraphicBuffer((GraphicBuffer)parcelable);
        try {
            object = Bitmap.wrapHardwareBuffer((HardwareBuffer)parcelable, (ColorSpace)object);
            if (parcelable != null) {
                ((HardwareBuffer)parcelable).close();
            }
            return object;
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (parcelable != null) {
                    try {
                        ((HardwareBuffer)parcelable).close();
                    }
                    catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                }
                throw throwable2;
            }
        }
    }

    public static Bitmap wrapHardwareBuffer(HardwareBuffer hardwareBuffer, ColorSpace colorSpace) {
        if ((hardwareBuffer.getUsage() & 256L) != 0L) {
            hardwareBuffer.getFormat();
            ColorSpace colorSpace2 = colorSpace;
            if (colorSpace == null) {
                colorSpace2 = ColorSpace.get(ColorSpace.Named.SRGB);
            }
            return Bitmap.nativeWrapHardwareBufferBitmap(hardwareBuffer, colorSpace2.getNativeInstance());
        }
        throw new IllegalArgumentException("usage flags must contain USAGE_GPU_SAMPLED_IMAGE.");
    }

    public boolean compress(CompressFormat compressFormat, int n, OutputStream outputStream) {
        this.checkRecycled("Can't compress a recycled bitmap");
        if (outputStream != null) {
            if (n >= 0 && n <= 100) {
                StrictMode.noteSlowCall("Compression of a bitmap is slow");
                Trace.traceBegin(8192L, "Bitmap.compress");
                boolean bl = Bitmap.nativeCompress(this.mNativePtr, compressFormat.nativeInt, n, outputStream, new byte[4096]);
                Trace.traceEnd(8192L);
                return bl;
            }
            throw new IllegalArgumentException("quality must be 0..100");
        }
        throw new NullPointerException();
    }

    public Bitmap copy(Config object, boolean bl) {
        this.checkRecycled("Can't copy a recycled bitmap");
        if (object == Config.HARDWARE && bl) {
            throw new IllegalArgumentException("Hardware bitmaps are always immutable");
        }
        this.noteHardwareBitmapSlowCall();
        object = Bitmap.nativeCopy(this.mNativePtr, ((Config)object).nativeInt, bl);
        if (object != null) {
            ((Bitmap)object).setPremultiplied(this.mRequestPremultiplied);
            ((Bitmap)object).mDensity = this.mDensity;
        }
        return object;
    }

    public void copyPixelsFromBuffer(Buffer buffer) {
        block7 : {
            int n;
            int n2;
            block5 : {
                block6 : {
                    block4 : {
                        this.checkRecycled("copyPixelsFromBuffer called on recycled bitmap");
                        this.checkHardware("unable to copyPixelsFromBuffer, Config#HARDWARE bitmaps are immutable");
                        n = buffer.remaining();
                        if (!(buffer instanceof ByteBuffer)) break block4;
                        n2 = 0;
                        break block5;
                    }
                    if (!(buffer instanceof ShortBuffer)) break block6;
                    n2 = 1;
                    break block5;
                }
                if (!(buffer instanceof IntBuffer)) break block7;
                n2 = 2;
            }
            long l = n;
            long l2 = this.getByteCount();
            if (l << n2 >= l2) {
                Bitmap.nativeCopyPixelsFromBuffer(this.mNativePtr, buffer);
                buffer.position((int)((long)buffer.position() + (l2 >> n2)));
                return;
            }
            throw new RuntimeException("Buffer not large enough for pixels");
        }
        throw new RuntimeException("unsupported Buffer subclass");
    }

    public void copyPixelsToBuffer(Buffer buffer) {
        block7 : {
            int n;
            int n2;
            block5 : {
                block6 : {
                    block4 : {
                        this.checkHardware("unable to copyPixelsToBuffer, pixel access is not supported on Config#HARDWARE bitmaps");
                        n = buffer.remaining();
                        if (!(buffer instanceof ByteBuffer)) break block4;
                        n2 = 0;
                        break block5;
                    }
                    if (!(buffer instanceof ShortBuffer)) break block6;
                    n2 = 1;
                    break block5;
                }
                if (!(buffer instanceof IntBuffer)) break block7;
                n2 = 2;
            }
            long l = n;
            long l2 = this.getByteCount();
            if (l << n2 >= l2) {
                Bitmap.nativeCopyPixelsToBuffer(this.mNativePtr, buffer);
                buffer.position((int)((long)buffer.position() + (l2 >> n2)));
                return;
            }
            throw new RuntimeException("Buffer not large enough for pixels");
        }
        throw new RuntimeException("unsupported Buffer subclass");
    }

    @UnsupportedAppUsage
    public Bitmap createAshmemBitmap() {
        this.checkRecycled("Can't copy a recycled bitmap");
        this.noteHardwareBitmapSlowCall();
        Bitmap bitmap = Bitmap.nativeCopyAshmem(this.mNativePtr);
        if (bitmap != null) {
            bitmap.setPremultiplied(this.mRequestPremultiplied);
            bitmap.mDensity = this.mDensity;
        }
        return bitmap;
    }

    @UnsupportedAppUsage
    public Bitmap createAshmemBitmap(Config object) {
        this.checkRecycled("Can't copy a recycled bitmap");
        this.noteHardwareBitmapSlowCall();
        object = Bitmap.nativeCopyAshmemConfig(this.mNativePtr, ((Config)object).nativeInt);
        if (object != null) {
            ((Bitmap)object).setPremultiplied(this.mRequestPremultiplied);
            ((Bitmap)object).mDensity = this.mDensity;
        }
        return object;
    }

    @UnsupportedAppUsage
    public GraphicBuffer createGraphicBufferHandle() {
        return Bitmap.nativeCreateGraphicBufferHandle(this.mNativePtr);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void eraseColor(int n) {
        this.checkRecycled("Can't erase a recycled bitmap");
        if (this.isMutable()) {
            Bitmap.nativeErase(this.mNativePtr, n);
            return;
        }
        throw new IllegalStateException("cannot erase immutable bitmaps");
    }

    public void eraseColor(long l) {
        this.checkRecycled("Can't erase a recycled bitmap");
        if (this.isMutable()) {
            ColorSpace colorSpace = Color.colorSpace(l);
            Bitmap.nativeErase(this.mNativePtr, colorSpace.getNativeInstance(), l);
            return;
        }
        throw new IllegalStateException("cannot erase immutable bitmaps");
    }

    public Bitmap extractAlpha() {
        return this.extractAlpha(null, null);
    }

    public Bitmap extractAlpha(Paint object, int[] arrn) {
        this.checkRecycled("Can't extractAlpha on a recycled bitmap");
        long l = object != null ? ((Paint)object).getNativeInstance() : 0L;
        this.noteHardwareBitmapSlowCall();
        object = Bitmap.nativeExtractAlpha(this.mNativePtr, l, arrn);
        if (object != null) {
            ((Bitmap)object).mDensity = this.mDensity;
            return object;
        }
        throw new RuntimeException("Failed to extractAlpha on Bitmap");
    }

    public final int getAllocationByteCount() {
        if (this.mRecycled) {
            Log.w("Bitmap", "Called getAllocationByteCount() on a recycle()'d bitmap! This is undefined behavior!");
            return 0;
        }
        return Bitmap.nativeGetAllocationByteCount(this.mNativePtr);
    }

    public final int getByteCount() {
        if (this.mRecycled) {
            Log.w("Bitmap", "Called getByteCount() on a recycle()'d bitmap! This is undefined behavior!");
            return 0;
        }
        return this.getRowBytes() * this.getHeight();
    }

    public Color getColor(int n, int n2) {
        this.checkRecycled("Can't call getColor() on a recycled bitmap");
        this.checkHardware("unable to getColor(), pixel access is not supported on Config#HARDWARE bitmaps");
        this.checkPixelAccess(n, n2);
        ColorSpace colorSpace = this.getColorSpace();
        if (colorSpace.equals(ColorSpace.get(ColorSpace.Named.SRGB))) {
            return Color.valueOf(Bitmap.nativeGetPixel(this.mNativePtr, n, n2));
        }
        long l = Bitmap.nativeGetColor(this.mNativePtr, n, n2);
        float f = Half.toFloat((short)(l >> 0 & 65535L));
        float f2 = Half.toFloat((short)(l >> 16 & 65535L));
        float f3 = Half.toFloat((short)(l >> 32 & 65535L));
        float f4 = Half.toFloat((short)(65535L & l >> 48));
        return Color.valueOf(Bitmap.clamp(f, colorSpace, 0), Bitmap.clamp(f2, colorSpace, 1), Bitmap.clamp(f3, colorSpace, 2), f4, colorSpace);
    }

    public final ColorSpace getColorSpace() {
        this.checkRecycled("getColorSpace called on a recycled bitmap");
        if (this.mColorSpace == null) {
            this.mColorSpace = Bitmap.nativeComputeColorSpace(this.mNativePtr);
        }
        return this.mColorSpace;
    }

    public final Config getConfig() {
        if (this.mRecycled) {
            Log.w("Bitmap", "Called getConfig() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return Config.nativeToConfig(Bitmap.nativeConfig(this.mNativePtr));
    }

    public int getDensity() {
        if (this.mRecycled) {
            Log.w("Bitmap", "Called getDensity() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return this.mDensity;
    }

    public int getGenerationId() {
        if (this.mRecycled) {
            Log.w("Bitmap", "Called getGenerationId() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return Bitmap.nativeGenerationId(this.mNativePtr);
    }

    public final int getHeight() {
        if (this.mRecycled) {
            Log.w("Bitmap", "Called getHeight() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return this.mHeight;
    }

    public long getNativeInstance() {
        return this.mNativePtr;
    }

    public byte[] getNinePatchChunk() {
        return this.mNinePatchChunk;
    }

    public NinePatch.InsetStruct getNinePatchInsets() {
        return this.mNinePatchInsets;
    }

    public void getOpticalInsets(Rect rect) {
        NinePatch.InsetStruct insetStruct = this.mNinePatchInsets;
        if (insetStruct == null) {
            rect.setEmpty();
        } else {
            rect.set(insetStruct.opticalRect);
        }
    }

    public int getPixel(int n, int n2) {
        this.checkRecycled("Can't call getPixel() on a recycled bitmap");
        this.checkHardware("unable to getPixel(), pixel access is not supported on Config#HARDWARE bitmaps");
        this.checkPixelAccess(n, n2);
        return Bitmap.nativeGetPixel(this.mNativePtr, n, n2);
    }

    public void getPixels(int[] arrn, int n, int n2, int n3, int n4, int n5, int n6) {
        this.checkRecycled("Can't call getPixels() on a recycled bitmap");
        this.checkHardware("unable to getPixels(), pixel access is not supported on Config#HARDWARE bitmaps");
        if (n5 != 0 && n6 != 0) {
            this.checkPixelsAccess(n3, n4, n5, n6, n, n2, arrn);
            Bitmap.nativeGetPixels(this.mNativePtr, arrn, n, n2, n3, n4, n5, n6);
            return;
        }
    }

    public final int getRowBytes() {
        if (this.mRecycled) {
            Log.w("Bitmap", "Called getRowBytes() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return Bitmap.nativeRowBytes(this.mNativePtr);
    }

    public int getScaledHeight(int n) {
        return Bitmap.scaleFromDensity(this.getHeight(), this.mDensity, n);
    }

    public int getScaledHeight(Canvas canvas) {
        return Bitmap.scaleFromDensity(this.getHeight(), this.mDensity, canvas.mDensity);
    }

    public int getScaledHeight(DisplayMetrics displayMetrics) {
        return Bitmap.scaleFromDensity(this.getHeight(), this.mDensity, displayMetrics.densityDpi);
    }

    public int getScaledWidth(int n) {
        return Bitmap.scaleFromDensity(this.getWidth(), this.mDensity, n);
    }

    public int getScaledWidth(Canvas canvas) {
        return Bitmap.scaleFromDensity(this.getWidth(), this.mDensity, canvas.mDensity);
    }

    public int getScaledWidth(DisplayMetrics displayMetrics) {
        return Bitmap.scaleFromDensity(this.getWidth(), this.mDensity, displayMetrics.densityDpi);
    }

    public final int getWidth() {
        if (this.mRecycled) {
            Log.w("Bitmap", "Called getWidth() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return this.mWidth;
    }

    public final boolean hasAlpha() {
        if (this.mRecycled) {
            Log.w("Bitmap", "Called hasAlpha() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return Bitmap.nativeHasAlpha(this.mNativePtr);
    }

    public final boolean hasMipMap() {
        if (this.mRecycled) {
            Log.w("Bitmap", "Called hasMipMap() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return Bitmap.nativeHasMipMap(this.mNativePtr);
    }

    public final boolean isMutable() {
        return Bitmap.nativeIsImmutable(this.mNativePtr) ^ true;
    }

    public final boolean isPremultiplied() {
        if (this.mRecycled) {
            Log.w("Bitmap", "Called isPremultiplied() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return Bitmap.nativeIsPremultiplied(this.mNativePtr);
    }

    public final boolean isRecycled() {
        return this.mRecycled;
    }

    public void prepareToDraw() {
        this.checkRecycled("Can't prepareToDraw on a recycled bitmap!");
        Bitmap.nativePrepareToDraw(this.mNativePtr);
    }

    public void reconfigure(int n, int n2, Config config) {
        this.checkRecycled("Can't call reconfigure() on a recycled bitmap");
        if (n > 0 && n2 > 0) {
            if (this.isMutable()) {
                Bitmap.nativeReconfigure(this.mNativePtr, n, n2, config.nativeInt, this.mRequestPremultiplied);
                this.mWidth = n;
                this.mHeight = n2;
                this.mColorSpace = null;
                return;
            }
            throw new IllegalStateException("only mutable bitmaps may be reconfigured");
        }
        throw new IllegalArgumentException("width and height must be > 0");
    }

    public void recycle() {
        if (!this.mRecycled) {
            Bitmap.nativeRecycle(this.mNativePtr);
            this.mNinePatchChunk = null;
            this.mRecycled = true;
        }
    }

    @UnsupportedAppUsage
    void reinit(int n, int n2, boolean bl) {
        this.mWidth = n;
        this.mHeight = n2;
        this.mRequestPremultiplied = bl;
        this.mColorSpace = null;
    }

    public boolean sameAs(Bitmap bitmap) {
        this.checkRecycled("Can't call sameAs on a recycled bitmap!");
        this.noteHardwareBitmapSlowCall();
        if (this == bitmap) {
            return true;
        }
        if (bitmap == null) {
            return false;
        }
        bitmap.noteHardwareBitmapSlowCall();
        if (!bitmap.isRecycled()) {
            return Bitmap.nativeSameAs(this.mNativePtr, bitmap.mNativePtr);
        }
        throw new IllegalArgumentException("Can't compare to a recycled bitmap!");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setColorSpace(ColorSpace object) {
        this.checkRecycled("setColorSpace called on a recycled bitmap");
        if (object == null) {
            throw new IllegalArgumentException("The colorSpace cannot be set to null");
        }
        if (this.getConfig() == Config.ALPHA_8) {
            throw new IllegalArgumentException("Cannot set a ColorSpace on ALPHA_8");
        }
        ColorSpace colorSpace = this.getColorSpace();
        Bitmap.nativeSetColorSpace(this.mNativePtr, ((ColorSpace)object).getNativeInstance());
        this.mColorSpace = null;
        object = this.getColorSpace();
        try {
            if (colorSpace.getComponentCount() == ((ColorSpace)object).getComponentCount()) {
                for (int i = 0; i < colorSpace.getComponentCount(); ++i) {
                    if (!(colorSpace.getMinValue(i) < ((ColorSpace)object).getMinValue(i))) {
                        if (!(colorSpace.getMaxValue(i) > ((ColorSpace)object).getMaxValue(i))) {
                            continue;
                        }
                        object = new IllegalArgumentException("The new ColorSpace cannot decrease the maximum value for any of the components compared to the current ColorSpace/ To perform this type of conversion create a new Bitmap in the desired ColorSpace and draw this Bitmap into it.");
                        throw object;
                    }
                    object = new IllegalArgumentException("The new ColorSpace cannot increase the minimum value for any of the components compared to the current ColorSpace. To perform this type of conversion create a new Bitmap in the desired ColorSpace and draw this Bitmap into it.");
                    throw object;
                }
                return;
            }
            object = new IllegalArgumentException("The new ColorSpace must have the same component count as the current ColorSpace");
            throw object;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            this.mColorSpace = colorSpace;
            Bitmap.nativeSetColorSpace(this.mNativePtr, this.mColorSpace.getNativeInstance());
            throw illegalArgumentException;
        }
    }

    public void setConfig(Config config) {
        this.reconfigure(this.getWidth(), this.getHeight(), config);
    }

    public void setDensity(int n) {
        this.mDensity = n;
    }

    public void setHasAlpha(boolean bl) {
        this.checkRecycled("setHasAlpha called on a recycled bitmap");
        Bitmap.nativeSetHasAlpha(this.mNativePtr, bl, this.mRequestPremultiplied);
    }

    public final void setHasMipMap(boolean bl) {
        this.checkRecycled("setHasMipMap called on a recycled bitmap");
        Bitmap.nativeSetHasMipMap(this.mNativePtr, bl);
    }

    public void setHeight(int n) {
        this.reconfigure(this.getWidth(), n, this.getConfig());
    }

    public void setImmutable() {
        if (this.isMutable()) {
            Bitmap.nativeSetImmutable(this.mNativePtr);
        }
    }

    @UnsupportedAppUsage
    public void setNinePatchChunk(byte[] arrby) {
        this.mNinePatchChunk = arrby;
    }

    public void setPixel(int n, int n2, int n3) {
        this.checkRecycled("Can't call setPixel() on a recycled bitmap");
        if (this.isMutable()) {
            this.checkPixelAccess(n, n2);
            Bitmap.nativeSetPixel(this.mNativePtr, n, n2, n3);
            return;
        }
        throw new IllegalStateException();
    }

    public void setPixels(int[] arrn, int n, int n2, int n3, int n4, int n5, int n6) {
        this.checkRecycled("Can't call setPixels() on a recycled bitmap");
        if (this.isMutable()) {
            if (n5 != 0 && n6 != 0) {
                this.checkPixelsAccess(n3, n4, n5, n6, n, n2, arrn);
                Bitmap.nativeSetPixels(this.mNativePtr, arrn, n, n2, n3, n4, n5, n6);
                return;
            }
            return;
        }
        throw new IllegalStateException();
    }

    public final void setPremultiplied(boolean bl) {
        this.checkRecycled("setPremultiplied called on a recycled bitmap");
        this.mRequestPremultiplied = bl;
        Bitmap.nativeSetPremultiplied(this.mNativePtr, bl);
    }

    public void setWidth(int n) {
        this.reconfigure(n, this.getHeight(), this.getConfig());
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.checkRecycled("Can't parcel a recycled bitmap");
        this.noteHardwareBitmapSlowCall();
        if (Bitmap.nativeWriteToParcel(this.mNativePtr, this.isMutable(), this.mDensity, parcel)) {
            return;
        }
        throw new RuntimeException("native writeToParcel failed");
    }

    public static enum CompressFormat {
        JPEG(0),
        PNG(1),
        WEBP(2);
        
        final int nativeInt;

        private CompressFormat(int n2) {
            this.nativeInt = n2;
        }
    }

    public static final class Config
    extends Enum<Config> {
        private static final /* synthetic */ Config[] $VALUES;
        public static final /* enum */ Config ALPHA_8 = new Config(1);
        @Deprecated
        public static final /* enum */ Config ARGB_4444;
        public static final /* enum */ Config ARGB_8888;
        public static final /* enum */ Config HARDWARE;
        public static final /* enum */ Config RGBA_F16;
        public static final /* enum */ Config RGB_565;
        private static Config[] sConfigs;
        @UnsupportedAppUsage
        final int nativeInt;

        static {
            RGB_565 = new Config(3);
            ARGB_4444 = new Config(4);
            ARGB_8888 = new Config(5);
            RGBA_F16 = new Config(6);
            HARDWARE = new Config(7);
            Config config = ALPHA_8;
            Config config2 = RGB_565;
            Config config3 = ARGB_4444;
            Config config4 = ARGB_8888;
            Config config5 = RGBA_F16;
            Config config6 = HARDWARE;
            $VALUES = new Config[]{config, config2, config3, config4, config5, config6};
            sConfigs = new Config[]{null, config, null, config2, config3, config4, config5, config6};
        }

        private Config(int n2) {
            this.nativeInt = n2;
        }

        @UnsupportedAppUsage
        static Config nativeToConfig(int n) {
            return sConfigs[n];
        }

        public static Config valueOf(String string2) {
            return Enum.valueOf(Config.class, string2);
        }

        public static Config[] values() {
            return (Config[])$VALUES.clone();
        }
    }

}

