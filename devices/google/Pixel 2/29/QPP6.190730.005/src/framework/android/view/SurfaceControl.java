/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 *  libcore.util.NativeAllocationRegistry
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.graphics.GraphicBuffer;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.display.DisplayedContentSample;
import android.hardware.display.DisplayedContentSamplingAttributes;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.proto.ProtoOutputStream;
import android.view.Display;
import android.view.InputWindowHandle;
import android.view.Surface;
import android.view.SurfaceSession;
import android.view.WindowAnimationFrameStats;
import android.view.WindowContentFrameStats;
import com.android.internal.annotations.GuardedBy;
import dalvik.system.CloseGuard;
import java.io.Closeable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;
import libcore.util.NativeAllocationRegistry;

public final class SurfaceControl
implements Parcelable {
    public static final Parcelable.Creator<SurfaceControl> CREATOR;
    public static final int CURSOR_WINDOW = 8192;
    public static final int FX_SURFACE_CONTAINER = 524288;
    public static final int FX_SURFACE_DIM = 131072;
    public static final int FX_SURFACE_MASK = 983040;
    public static final int FX_SURFACE_NORMAL = 0;
    @UnsupportedAppUsage
    public static final int HIDDEN = 4;
    private static final int INTERNAL_DATASPACE_DISPLAY_P3 = 143261696;
    private static final int INTERNAL_DATASPACE_SCRGB = 411107328;
    private static final int INTERNAL_DATASPACE_SRGB = 142671872;
    public static final int METADATA_OWNER_UID = 1;
    public static final int METADATA_TASK_ID = 3;
    public static final int METADATA_WINDOW_TYPE = 2;
    public static final int NON_PREMULTIPLIED = 256;
    public static final int OPAQUE = 1024;
    public static final int POWER_MODE_DOZE = 1;
    public static final int POWER_MODE_DOZE_SUSPEND = 3;
    public static final int POWER_MODE_NORMAL = 2;
    public static final int POWER_MODE_OFF = 0;
    public static final int POWER_MODE_ON_SUSPEND = 4;
    public static final int PROTECTED_APP = 2048;
    public static final int SECURE = 128;
    private static final int SURFACE_HIDDEN = 1;
    private static final int SURFACE_OPAQUE = 2;
    private static final String TAG = "SurfaceControl";
    public static final int WINDOW_TYPE_DONT_SCREENSHOT = 441731;
    static Transaction sGlobalTransaction;
    static long sTransactionNestCount;
    private final CloseGuard mCloseGuard;
    @GuardedBy(value={"mSizeLock"})
    private int mHeight;
    private String mName;
    long mNativeObject;
    private final Object mSizeLock;
    @GuardedBy(value={"mSizeLock"})
    private int mWidth;

    static {
        sTransactionNestCount = 0L;
        CREATOR = new Parcelable.Creator<SurfaceControl>(){

            @Override
            public SurfaceControl createFromParcel(Parcel parcel) {
                return new SurfaceControl(parcel);
            }

            public SurfaceControl[] newArray(int n) {
                return new SurfaceControl[n];
            }
        };
    }

    public SurfaceControl() {
        this.mCloseGuard = CloseGuard.get();
        this.mSizeLock = new Object();
        this.mCloseGuard.open("release");
    }

    private SurfaceControl(Parcel parcel) {
        this.mCloseGuard = CloseGuard.get();
        this.mSizeLock = new Object();
        this.readFromParcel(parcel);
        this.mCloseGuard.open("release");
    }

    public SurfaceControl(SurfaceControl surfaceControl) {
        this.mCloseGuard = CloseGuard.get();
        this.mSizeLock = new Object();
        this.mName = surfaceControl.mName;
        this.mWidth = surfaceControl.mWidth;
        this.mHeight = surfaceControl.mHeight;
        this.mNativeObject = surfaceControl.mNativeObject;
        surfaceControl.mCloseGuard.close();
        surfaceControl.mNativeObject = 0L;
        this.mCloseGuard.open("release");
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private SurfaceControl(SurfaceSession surfaceSession, String string2, int n, int n2, int n3, int n4, SurfaceControl surfaceControl, SparseIntArray sparseIntArray) throws Surface.OutOfResourcesException, IllegalArgumentException {
        void var1_4;
        Object object;
        block10 : {
            block11 : {
                block9 : {
                    this.mCloseGuard = CloseGuard.get();
                    this.mSizeLock = new Object();
                    if (string2 == null) {
                        throw new IllegalArgumentException("name must not be null");
                    }
                    if ((n4 & 4) == 0) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Surfaces should always be created with the HIDDEN flag set to ensure that they are not made visible prematurely before all of the surface's properties have been configured.  Set the other properties and make the surface visible within a transaction.  New surface name: ");
                        ((StringBuilder)object).append(string2);
                        Log.w(TAG, ((StringBuilder)object).toString(), new Throwable());
                    }
                    this.mName = string2;
                    this.mWidth = n;
                    this.mHeight = n2;
                    object = Parcel.obtain();
                    if (sparseIntArray != null) {
                        try {
                            if (sparseIntArray.size() <= 0) break block9;
                            ((Parcel)object).writeInt(sparseIntArray.size());
                            for (int i = 0; i < sparseIntArray.size(); ++i) {
                                ((Parcel)object).writeInt(sparseIntArray.keyAt(i));
                                ((Parcel)object).writeByteArray(ByteBuffer.allocate(4).order(ByteOrder.nativeOrder()).putInt(sparseIntArray.valueAt(i)).array());
                            }
                            ((Parcel)object).setDataPosition(0);
                        }
                        catch (Throwable throwable) {
                            break block10;
                        }
                    }
                }
                long l = surfaceControl != null ? surfaceControl.mNativeObject : 0L;
                try {
                    this.mNativeObject = SurfaceControl.nativeCreate(surfaceSession, string2, n, n2, n3, n4, l, (Parcel)object);
                    ((Parcel)object).recycle();
                    if (this.mNativeObject == 0L) break block11;
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
                this.mCloseGuard.open("release");
                return;
            }
            throw new Surface.OutOfResourcesException("Couldn't allocate SurfaceControl native object");
        }
        ((Parcel)object).recycle();
        throw var1_4;
    }

    static /* synthetic */ long access$200() {
        return SurfaceControl.nativeGetNativeTransactionFinalizer();
    }

    static /* synthetic */ long access$300() {
        return SurfaceControl.nativeCreateTransaction();
    }

    private void assignNativeObject(long l) {
        if (this.mNativeObject != 0L) {
            this.release();
        }
        this.mNativeObject = l;
    }

    public static ScreenshotGraphicBuffer captureLayers(IBinder iBinder, Rect rect, float f) {
        return SurfaceControl.nativeCaptureLayers(SurfaceControl.getInternalDisplayToken(), iBinder, rect, f, null);
    }

    public static ScreenshotGraphicBuffer captureLayersExcluding(IBinder iBinder, Rect rect, float f, IBinder[] arriBinder) {
        return SurfaceControl.nativeCaptureLayers(SurfaceControl.getInternalDisplayToken(), iBinder, rect, f, arriBinder);
    }

    private void checkNotReleased() {
        if (this.mNativeObject != 0L) {
            return;
        }
        throw new NullPointerException("mNativeObject is null. Have you called release() already?");
    }

    public static boolean clearAnimationFrameStats() {
        return SurfaceControl.nativeClearAnimationFrameStats();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static void closeTransaction() {
        synchronized (SurfaceControl.class) {
            if (sTransactionNestCount == 0L) {
                Log.e(TAG, "Call to SurfaceControl.closeTransaction without matching openTransaction");
            } else {
                long l;
                sTransactionNestCount = l = sTransactionNestCount - 1L;
                if (l > 0L) {
                    return;
                }
            }
            sGlobalTransaction.apply();
            return;
        }
    }

    @UnsupportedAppUsage
    public static IBinder createDisplay(String string2, boolean bl) {
        if (string2 != null) {
            return SurfaceControl.nativeCreateDisplay(string2, bl);
        }
        throw new IllegalArgumentException("name must not be null");
    }

    @UnsupportedAppUsage
    public static void destroyDisplay(IBinder iBinder) {
        if (iBinder != null) {
            SurfaceControl.nativeDestroyDisplay(iBinder);
            return;
        }
        throw new IllegalArgumentException("displayToken must not be null");
    }

    public static int getActiveColorMode(IBinder iBinder) {
        if (iBinder != null) {
            return SurfaceControl.nativeGetActiveColorMode(iBinder);
        }
        throw new IllegalArgumentException("displayToken must not be null");
    }

    public static int getActiveConfig(IBinder iBinder) {
        if (iBinder != null) {
            return SurfaceControl.nativeGetActiveConfig(iBinder);
        }
        throw new IllegalArgumentException("displayToken must not be null");
    }

    public static int[] getAllowedDisplayConfigs(IBinder iBinder) {
        if (iBinder != null) {
            return SurfaceControl.nativeGetAllowedDisplayConfigs(iBinder);
        }
        throw new IllegalArgumentException("displayToken must not be null");
    }

    public static boolean getAnimationFrameStats(WindowAnimationFrameStats windowAnimationFrameStats) {
        return SurfaceControl.nativeGetAnimationFrameStats(windowAnimationFrameStats);
    }

    public static ColorSpace[] getCompositionColorSpaces() {
        int[] arrn = SurfaceControl.nativeGetCompositionDataspaces();
        ColorSpace colorSpace = ColorSpace.get(ColorSpace.Named.SRGB);
        ColorSpace[] arrcolorSpace = new ColorSpace[]{colorSpace, colorSpace};
        if (arrn.length == 2) {
            for (int i = 0; i < 2; ++i) {
                int n = arrn[i];
                if (n != 143261696) {
                    if (n != 411107328) continue;
                    arrcolorSpace[i] = ColorSpace.get(ColorSpace.Named.EXTENDED_SRGB);
                    continue;
                }
                arrcolorSpace[i] = ColorSpace.get(ColorSpace.Named.DISPLAY_P3);
            }
        }
        return arrcolorSpace;
    }

    public static boolean getDisplayBrightnessSupport(IBinder iBinder) {
        return SurfaceControl.nativeGetDisplayBrightnessSupport(iBinder);
    }

    public static int[] getDisplayColorModes(IBinder iBinder) {
        if (iBinder != null) {
            return SurfaceControl.nativeGetDisplayColorModes(iBinder);
        }
        throw new IllegalArgumentException("displayToken must not be null");
    }

    @UnsupportedAppUsage
    public static PhysicalDisplayInfo[] getDisplayConfigs(IBinder iBinder) {
        if (iBinder != null) {
            return SurfaceControl.nativeGetDisplayConfigs(iBinder);
        }
        throw new IllegalArgumentException("displayToken must not be null");
    }

    public static DisplayPrimaries getDisplayNativePrimaries(IBinder iBinder) {
        if (iBinder != null) {
            return SurfaceControl.nativeGetDisplayNativePrimaries(iBinder);
        }
        throw new IllegalArgumentException("displayToken must not be null");
    }

    public static DisplayedContentSample getDisplayedContentSample(IBinder iBinder, long l, long l2) {
        if (iBinder != null) {
            return SurfaceControl.nativeGetDisplayedContentSample(iBinder, l, l2);
        }
        throw new IllegalArgumentException("displayToken must not be null");
    }

    public static DisplayedContentSamplingAttributes getDisplayedContentSamplingAttributes(IBinder iBinder) {
        if (iBinder != null) {
            return SurfaceControl.nativeGetDisplayedContentSamplingAttributes(iBinder);
        }
        throw new IllegalArgumentException("displayToken must not be null");
    }

    public static Display.HdrCapabilities getHdrCapabilities(IBinder iBinder) {
        if (iBinder != null) {
            return SurfaceControl.nativeGetHdrCapabilities(iBinder);
        }
        throw new IllegalArgumentException("displayToken must not be null");
    }

    public static IBinder getInternalDisplayToken() {
        long[] arrl = SurfaceControl.getPhysicalDisplayIds();
        if (arrl.length == 0) {
            return null;
        }
        return SurfaceControl.getPhysicalDisplayToken(arrl[0]);
    }

    public static long[] getPhysicalDisplayIds() {
        return SurfaceControl.nativeGetPhysicalDisplayIds();
    }

    public static IBinder getPhysicalDisplayToken(long l) {
        return SurfaceControl.nativeGetPhysicalDisplayToken(l);
    }

    public static boolean getProtectedContentSupport() {
        return SurfaceControl.nativeGetProtectedContentSupport();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public static void mergeToGlobalTransaction(Transaction transaction) {
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.merge(transaction);
            return;
        }
    }

    private static native void nativeApplyTransaction(long var0, boolean var2);

    private static native ScreenshotGraphicBuffer nativeCaptureLayers(IBinder var0, IBinder var1, Rect var2, float var3, IBinder[] var4);

    private static native boolean nativeClearAnimationFrameStats();

    private static native boolean nativeClearContentFrameStats(long var0);

    private static native long nativeCopyFromSurfaceControl(long var0);

    private static native long nativeCreate(SurfaceSession var0, String var1, int var2, int var3, int var4, int var5, long var6, Parcel var8) throws Surface.OutOfResourcesException;

    private static native IBinder nativeCreateDisplay(String var0, boolean var1);

    private static native long nativeCreateTransaction();

    private static native void nativeDeferTransactionUntil(long var0, long var2, IBinder var4, long var5);

    private static native void nativeDeferTransactionUntilSurface(long var0, long var2, long var4, long var6);

    private static native void nativeDestroy(long var0);

    private static native void nativeDestroyDisplay(IBinder var0);

    private static native void nativeDisconnect(long var0);

    private static native int nativeGetActiveColorMode(IBinder var0);

    private static native int nativeGetActiveConfig(IBinder var0);

    private static native int[] nativeGetAllowedDisplayConfigs(IBinder var0);

    private static native boolean nativeGetAnimationFrameStats(WindowAnimationFrameStats var0);

    private static native int[] nativeGetCompositionDataspaces();

    private static native boolean nativeGetContentFrameStats(long var0, WindowContentFrameStats var2);

    private static native boolean nativeGetDisplayBrightnessSupport(IBinder var0);

    private static native int[] nativeGetDisplayColorModes(IBinder var0);

    private static native PhysicalDisplayInfo[] nativeGetDisplayConfigs(IBinder var0);

    private static native DisplayPrimaries nativeGetDisplayNativePrimaries(IBinder var0);

    private static native DisplayedContentSample nativeGetDisplayedContentSample(IBinder var0, long var1, long var3);

    private static native DisplayedContentSamplingAttributes nativeGetDisplayedContentSamplingAttributes(IBinder var0);

    private static native IBinder nativeGetHandle(long var0);

    private static native Display.HdrCapabilities nativeGetHdrCapabilities(IBinder var0);

    private static native long nativeGetNativeTransactionFinalizer();

    private static native long[] nativeGetPhysicalDisplayIds();

    private static native IBinder nativeGetPhysicalDisplayToken(long var0);

    private static native boolean nativeGetProtectedContentSupport();

    private static native boolean nativeGetTransformToDisplayInverse(long var0);

    private static native void nativeMergeTransaction(long var0, long var2);

    private static native long nativeReadFromParcel(Parcel var0);

    private static native void nativeRelease(long var0);

    private static native void nativeReparent(long var0, long var2, long var4);

    private static native void nativeReparentChildren(long var0, long var2, IBinder var4);

    private static native ScreenshotGraphicBuffer nativeScreenshot(IBinder var0, Rect var1, int var2, int var3, boolean var4, int var5, boolean var6);

    private static native boolean nativeSetActiveColorMode(IBinder var0, int var1);

    private static native boolean nativeSetActiveConfig(IBinder var0, int var1);

    private static native boolean nativeSetAllowedDisplayConfigs(IBinder var0, int[] var1);

    private static native void nativeSetAlpha(long var0, long var2, float var4);

    private static native void nativeSetAnimationTransaction(long var0);

    private static native void nativeSetColor(long var0, long var2, float[] var4);

    private static native void nativeSetColorSpaceAgnostic(long var0, long var2, boolean var4);

    private static native void nativeSetColorTransform(long var0, long var2, float[] var4, float[] var5);

    private static native void nativeSetCornerRadius(long var0, long var2, float var4);

    private static native boolean nativeSetDisplayBrightness(IBinder var0, float var1);

    private static native void nativeSetDisplayLayerStack(long var0, IBinder var2, int var3);

    private static native void nativeSetDisplayPowerMode(IBinder var0, int var1);

    private static native void nativeSetDisplayProjection(long var0, IBinder var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11);

    private static native void nativeSetDisplaySize(long var0, IBinder var2, int var3, int var4);

    private static native void nativeSetDisplaySurface(long var0, IBinder var2, long var3);

    private static native boolean nativeSetDisplayedContentSamplingEnabled(IBinder var0, boolean var1, int var2, int var3);

    private static native void nativeSetEarlyWakeup(long var0);

    private static native void nativeSetFlags(long var0, long var2, int var4, int var5);

    private static native void nativeSetGeometry(long var0, long var2, Rect var4, Rect var5, long var6);

    private static native void nativeSetGeometryAppliesWithResize(long var0, long var2);

    private static native void nativeSetInputWindowInfo(long var0, long var2, InputWindowHandle var4);

    private static native void nativeSetLayer(long var0, long var2, int var4);

    private static native void nativeSetLayerStack(long var0, long var2, int var4);

    private static native void nativeSetMatrix(long var0, long var2, float var4, float var5, float var6, float var7);

    private static native void nativeSetMetadata(long var0, long var2, int var4, Parcel var5);

    private static native void nativeSetOverrideScalingMode(long var0, long var2, int var4);

    private static native void nativeSetPosition(long var0, long var2, float var4, float var5);

    private static native void nativeSetRelativeLayer(long var0, long var2, IBinder var4, int var5);

    private static native void nativeSetSize(long var0, long var2, int var4, int var5);

    private static native void nativeSetTransparentRegionHint(long var0, long var2, Region var4);

    private static native void nativeSetWindowCrop(long var0, long var2, int var4, int var5, int var6, int var7);

    private static native void nativeSeverChildren(long var0, long var2);

    private static native void nativeSyncInputWindows(long var0);

    private static native void nativeTransferTouchFocus(long var0, IBinder var2, IBinder var3);

    private static native void nativeWriteToParcel(long var0, Parcel var2);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public static void openTransaction() {
        // MONITORENTER : android.view.SurfaceControl.class
        if (sGlobalTransaction == null) {
            Transaction transaction;
            sGlobalTransaction = transaction = new Transaction();
        }
        // MONITORENTER : android.view.SurfaceControl.class
        ++sTransactionNestCount;
        // MONITOREXIT : android.view.SurfaceControl.class
        // MONITOREXIT : android.view.SurfaceControl.class
    }

    private static void rotateCropForSF(Rect rect, int n) {
        if (n == 1 || n == 3) {
            n = rect.top;
            rect.top = rect.left;
            rect.left = n;
            n = rect.right;
            rect.right = rect.bottom;
            rect.bottom = n;
        }
    }

    @UnsupportedAppUsage
    public static Bitmap screenshot(Rect rect, int n, int n2, int n3) {
        return SurfaceControl.screenshot(rect, n, n2, false, n3);
    }

    @UnsupportedAppUsage
    public static Bitmap screenshot(Rect object, int n, int n2, boolean bl, int n3) {
        int n4;
        IBinder iBinder;
        block6 : {
            int n5;
            block5 : {
                iBinder = SurfaceControl.getInternalDisplayToken();
                if (iBinder == null) {
                    Log.w("SurfaceControl", "Failed to take screenshot because internal display is disconnected");
                    return null;
                }
                n5 = 3;
                if (n3 == 1) break block5;
                n4 = n3;
                if (n3 != 3) break block6;
            }
            n3 = n3 == 1 ? n5 : 1;
            n4 = n3;
        }
        SurfaceControl.rotateCropForSF((Rect)object, n4);
        object = SurfaceControl.screenshotToBuffer(iBinder, (Rect)object, n, n2, bl, n4);
        if (object == null) {
            Log.w("SurfaceControl", "Failed to take screenshot");
            return null;
        }
        return Bitmap.wrapHardwareBuffer(((ScreenshotGraphicBuffer)object).getGraphicBuffer(), ((ScreenshotGraphicBuffer)object).getColorSpace());
    }

    public static void screenshot(IBinder iBinder, Surface surface) {
        SurfaceControl.screenshot(iBinder, surface, new Rect(), 0, 0, false, 0);
    }

    public static void screenshot(IBinder object, Surface surface, Rect rect, int n, int n2, boolean bl, int n3) {
        if (surface != null) {
            object = SurfaceControl.screenshotToBuffer((IBinder)object, rect, n, n2, bl, n3);
            try {
                surface.attachAndQueueBuffer(((ScreenshotGraphicBuffer)object).getGraphicBuffer());
            }
            catch (RuntimeException runtimeException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to take screenshot - ");
                ((StringBuilder)object).append(runtimeException.getMessage());
                Log.w("SurfaceControl", ((StringBuilder)object).toString());
            }
            return;
        }
        throw new IllegalArgumentException("consumer must not be null");
    }

    public static ScreenshotGraphicBuffer screenshotToBuffer(IBinder iBinder, Rect rect, int n, int n2, boolean bl, int n3) {
        if (iBinder != null) {
            return SurfaceControl.nativeScreenshot(iBinder, rect, n, n2, bl, n3, false);
        }
        throw new IllegalArgumentException("displayToken must not be null");
    }

    public static ScreenshotGraphicBuffer screenshotToBufferWithSecureLayersUnsafe(IBinder iBinder, Rect rect, int n, int n2, boolean bl, int n3) {
        if (iBinder != null) {
            return SurfaceControl.nativeScreenshot(iBinder, rect, n, n2, bl, n3, true);
        }
        throw new IllegalArgumentException("displayToken must not be null");
    }

    public static boolean setActiveColorMode(IBinder iBinder, int n) {
        if (iBinder != null) {
            return SurfaceControl.nativeSetActiveColorMode(iBinder, n);
        }
        throw new IllegalArgumentException("displayToken must not be null");
    }

    public static boolean setActiveConfig(IBinder iBinder, int n) {
        if (iBinder != null) {
            return SurfaceControl.nativeSetActiveConfig(iBinder, n);
        }
        throw new IllegalArgumentException("displayToken must not be null");
    }

    public static boolean setAllowedDisplayConfigs(IBinder iBinder, int[] arrn) {
        if (iBinder != null) {
            if (arrn != null) {
                return SurfaceControl.nativeSetAllowedDisplayConfigs(iBinder, arrn);
            }
            throw new IllegalArgumentException("allowedConfigs must not be null");
        }
        throw new IllegalArgumentException("displayToken must not be null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setAnimationTransaction() {
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setAnimationTransaction();
            return;
        }
    }

    public static boolean setDisplayBrightness(IBinder iBinder, float f) {
        Objects.requireNonNull(iBinder);
        if (!(Float.isNaN(f) || f > 1.0f || f < 0.0f && f != -1.0f)) {
            return SurfaceControl.nativeSetDisplayBrightness(iBinder, f);
        }
        throw new IllegalArgumentException("brightness must be a number between 0.0f and 1.0f, or -1 to turn the backlight off.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static void setDisplayLayerStack(IBinder iBinder, int n) {
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setDisplayLayerStack(iBinder, n);
            return;
        }
    }

    public static void setDisplayPowerMode(IBinder iBinder, int n) {
        if (iBinder != null) {
            SurfaceControl.nativeSetDisplayPowerMode(iBinder, n);
            return;
        }
        throw new IllegalArgumentException("displayToken must not be null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static void setDisplayProjection(IBinder iBinder, int n, Rect rect, Rect rect2) {
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setDisplayProjection(iBinder, n, rect, rect2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setDisplaySize(IBinder iBinder, int n, int n2) {
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setDisplaySize(iBinder, n, n2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static void setDisplaySurface(IBinder iBinder, Surface surface) {
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setDisplaySurface(iBinder, surface);
            return;
        }
    }

    public static boolean setDisplayedContentSamplingEnabled(IBinder iBinder, boolean bl, int n, int n2) {
        if (iBinder != null) {
            if (n >> 4 == 0) {
                return SurfaceControl.nativeSetDisplayedContentSamplingEnabled(iBinder, bl, n, n2);
            }
            throw new IllegalArgumentException("invalid componentMask when enabling sampling");
        }
        throw new IllegalArgumentException("displayToken must not be null");
    }

    public boolean clearContentFrameStats() {
        this.checkNotReleased();
        return SurfaceControl.nativeClearContentFrameStats(this.mNativeObject);
    }

    public void copyFrom(SurfaceControl surfaceControl) {
        this.mName = surfaceControl.mName;
        this.mWidth = surfaceControl.mWidth;
        this.mHeight = surfaceControl.mHeight;
        this.assignNativeObject(SurfaceControl.nativeCopyFromSurfaceControl(surfaceControl.mNativeObject));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void deferTransactionUntil(IBinder iBinder, long l) {
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.deferTransactionUntil(this, iBinder, l);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void deferTransactionUntil(Surface surface, long l) {
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.deferTransactionUntilSurface(this, surface, l);
            return;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void detachChildren() {
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.detachChildren(this);
            return;
        }
    }

    public void disconnect() {
        long l = this.mNativeObject;
        if (l != 0L) {
            SurfaceControl.nativeDisconnect(l);
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            if (this.mNativeObject != 0L) {
                SurfaceControl.nativeRelease(this.mNativeObject);
            }
            return;
        }
        finally {
            super.finalize();
        }
    }

    public boolean getContentFrameStats(WindowContentFrameStats windowContentFrameStats) {
        this.checkNotReleased();
        return SurfaceControl.nativeGetContentFrameStats(this.mNativeObject, windowContentFrameStats);
    }

    public IBinder getHandle() {
        return SurfaceControl.nativeGetHandle(this.mNativeObject);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getHeight() {
        Object object = this.mSizeLock;
        synchronized (object) {
            return this.mHeight;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getWidth() {
        Object object = this.mSizeLock;
        synchronized (object) {
            return this.mWidth;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void hide() {
        this.checkNotReleased();
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.hide(this);
            return;
        }
    }

    public boolean isValid() {
        boolean bl = this.mNativeObject != 0L;
        return bl;
    }

    public void readFromParcel(Parcel parcel) {
        if (parcel != null) {
            this.mName = parcel.readString();
            this.mWidth = parcel.readInt();
            this.mHeight = parcel.readInt();
            long l = 0L;
            if (parcel.readInt() != 0) {
                l = SurfaceControl.nativeReadFromParcel(parcel);
            }
            this.assignNativeObject(l);
            return;
        }
        throw new IllegalArgumentException("source must not be null");
    }

    public void release() {
        long l = this.mNativeObject;
        if (l != 0L) {
            SurfaceControl.nativeRelease(l);
            this.mNativeObject = 0L;
        }
        this.mCloseGuard.close();
    }

    public void remove() {
        long l = this.mNativeObject;
        if (l != 0L) {
            SurfaceControl.nativeDestroy(l);
            this.mNativeObject = 0L;
        }
        this.mCloseGuard.close();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void reparent(SurfaceControl surfaceControl) {
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.reparent(this, surfaceControl);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void reparentChildren(IBinder iBinder) {
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.reparentChildren(this, iBinder);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setAlpha(float f) {
        this.checkNotReleased();
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setAlpha(this, f);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setBufferSize(int n, int n2) {
        this.checkNotReleased();
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setBufferSize(this, n, n2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setColor(float[] arrf) {
        this.checkNotReleased();
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setColor(this, arrf);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setColorSpaceAgnostic(boolean bl) {
        this.checkNotReleased();
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setColorSpaceAgnostic(this, bl);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setColorTransform(float[] arrf, float[] arrf2) {
        this.checkNotReleased();
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setColorTransform(this, arrf, arrf2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setCornerRadius(float f) {
        this.checkNotReleased();
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setCornerRadius(this, f);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setGeometryAppliesWithResize() {
        this.checkNotReleased();
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setGeometryAppliesWithResize(this);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void setLayer(int n) {
        this.checkNotReleased();
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setLayer(this, n);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setLayerStack(int n) {
        this.checkNotReleased();
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setLayerStack(this, n);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setMatrix(float f, float f2, float f3, float f4) {
        this.checkNotReleased();
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setMatrix(this, f, f2, f3, f4);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setMatrix(Matrix matrix, float[] arrf) {
        this.checkNotReleased();
        matrix.getValues(arrf);
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setMatrix(this, arrf[0], arrf[3], arrf[1], arrf[4]);
            sGlobalTransaction.setPosition(this, arrf[2], arrf[5]);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOpaque(boolean bl) {
        this.checkNotReleased();
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setOpaque(this, bl);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOverrideScalingMode(int n) {
        this.checkNotReleased();
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setOverrideScalingMode(this, n);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void setPosition(float f, float f2) {
        this.checkNotReleased();
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setPosition(this, f, f2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setRelativeLayer(SurfaceControl surfaceControl, int n) {
        this.checkNotReleased();
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setRelativeLayer(this, surfaceControl, n);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setSecure(boolean bl) {
        this.checkNotReleased();
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setSecure(this, bl);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setTransparentRegionHint(Region region) {
        this.checkNotReleased();
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setTransparentRegionHint(this, region);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setWindowCrop(int n, int n2) {
        this.checkNotReleased();
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setWindowCrop(this, n, n2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setWindowCrop(Rect rect) {
        this.checkNotReleased();
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.setWindowCrop(this, rect);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void show() {
        this.checkNotReleased();
        synchronized (SurfaceControl.class) {
            sGlobalTransaction.show(this);
            return;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Surface(name=");
        stringBuilder.append(this.mName);
        stringBuilder.append(")/@0x");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mName);
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mHeight);
        if (this.mNativeObject == 0L) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
        }
        SurfaceControl.nativeWriteToParcel(this.mNativeObject, parcel);
        if ((n & 1) != 0) {
            this.release();
        }
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1120986464257L, System.identityHashCode(this));
        protoOutputStream.write(1138166333442L, this.mName);
        protoOutputStream.end(l);
    }

    public static class Builder {
        private int mFlags = 4;
        private int mFormat = -1;
        private int mHeight;
        private SparseIntArray mMetadata;
        private String mName;
        private SurfaceControl mParent;
        private SurfaceSession mSession;
        private int mWidth;

        public Builder() {
        }

        public Builder(SurfaceSession surfaceSession) {
            this.mSession = surfaceSession;
        }

        private boolean isColorLayerSet() {
            boolean bl = (this.mFlags & 131072) == 131072;
            return bl;
        }

        private boolean isContainerLayerSet() {
            boolean bl = (this.mFlags & 524288) == 524288;
            return bl;
        }

        private Builder setFlags(int n, int n2) {
            this.mFlags = this.mFlags & n2 | n;
            return this;
        }

        private void unsetBufferSize() {
            this.mWidth = 0;
            this.mHeight = 0;
        }

        public SurfaceControl build() {
            int n;
            int n2 = this.mWidth;
            if (n2 >= 0 && (n = this.mHeight) >= 0) {
                if (n2 <= 0 && n <= 0 || !this.isColorLayerSet() && !this.isContainerLayerSet()) {
                    return new SurfaceControl(this.mSession, this.mName, this.mWidth, this.mHeight, this.mFormat, this.mFlags, this.mParent, this.mMetadata);
                }
                throw new IllegalStateException("Only buffer layers can set a valid buffer size.");
            }
            throw new IllegalStateException("width and height must be positive or unset");
        }

        public Builder setBufferSize(int n, int n2) {
            if (n >= 0 && n2 >= 0) {
                this.mWidth = n;
                this.mHeight = n2;
                return this.setFlags(0, 983040);
            }
            throw new IllegalArgumentException("width and height must be positive");
        }

        public Builder setColorLayer() {
            this.unsetBufferSize();
            return this.setFlags(131072, 983040);
        }

        public Builder setContainerLayer() {
            this.unsetBufferSize();
            return this.setFlags(524288, 983040);
        }

        public Builder setFlags(int n) {
            this.mFlags = n;
            return this;
        }

        public Builder setFormat(int n) {
            this.mFormat = n;
            return this;
        }

        public Builder setMetadata(int n, int n2) {
            if (this.mMetadata == null) {
                this.mMetadata = new SparseIntArray();
            }
            this.mMetadata.put(n, n2);
            return this;
        }

        public Builder setName(String string2) {
            this.mName = string2;
            return this;
        }

        public Builder setOpaque(boolean bl) {
            this.mFlags = bl ? (this.mFlags |= 1024) : (this.mFlags &= -1025);
            return this;
        }

        public Builder setParent(SurfaceControl surfaceControl) {
            this.mParent = surfaceControl;
            return this;
        }

        public Builder setProtected(boolean bl) {
            this.mFlags = bl ? (this.mFlags |= 2048) : (this.mFlags &= -2049);
            return this;
        }

        public Builder setSecure(boolean bl) {
            this.mFlags = bl ? (this.mFlags |= 128) : (this.mFlags &= -129);
            return this;
        }
    }

    public static final class CieXyz {
        public float X;
        public float Y;
        public float Z;
    }

    public static final class DisplayPrimaries {
        public CieXyz blue;
        public CieXyz green;
        public CieXyz red;
        public CieXyz white;
    }

    public static final class PhysicalDisplayInfo {
        @UnsupportedAppUsage
        public long appVsyncOffsetNanos;
        @UnsupportedAppUsage
        public float density;
        @UnsupportedAppUsage
        public int height;
        @UnsupportedAppUsage
        public long presentationDeadlineNanos;
        @UnsupportedAppUsage
        public float refreshRate;
        @UnsupportedAppUsage
        public boolean secure;
        @UnsupportedAppUsage
        public int width;
        @UnsupportedAppUsage
        public float xDpi;
        @UnsupportedAppUsage
        public float yDpi;

        @UnsupportedAppUsage
        public PhysicalDisplayInfo() {
        }

        public PhysicalDisplayInfo(PhysicalDisplayInfo physicalDisplayInfo) {
            this.copyFrom(physicalDisplayInfo);
        }

        public void copyFrom(PhysicalDisplayInfo physicalDisplayInfo) {
            this.width = physicalDisplayInfo.width;
            this.height = physicalDisplayInfo.height;
            this.refreshRate = physicalDisplayInfo.refreshRate;
            this.density = physicalDisplayInfo.density;
            this.xDpi = physicalDisplayInfo.xDpi;
            this.yDpi = physicalDisplayInfo.yDpi;
            this.secure = physicalDisplayInfo.secure;
            this.appVsyncOffsetNanos = physicalDisplayInfo.appVsyncOffsetNanos;
            this.presentationDeadlineNanos = physicalDisplayInfo.presentationDeadlineNanos;
        }

        public boolean equals(PhysicalDisplayInfo physicalDisplayInfo) {
            boolean bl = physicalDisplayInfo != null && this.width == physicalDisplayInfo.width && this.height == physicalDisplayInfo.height && this.refreshRate == physicalDisplayInfo.refreshRate && this.density == physicalDisplayInfo.density && this.xDpi == physicalDisplayInfo.xDpi && this.yDpi == physicalDisplayInfo.yDpi && this.secure == physicalDisplayInfo.secure && this.appVsyncOffsetNanos == physicalDisplayInfo.appVsyncOffsetNanos && this.presentationDeadlineNanos == physicalDisplayInfo.presentationDeadlineNanos;
            return bl;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof PhysicalDisplayInfo && this.equals((PhysicalDisplayInfo)object);
            return bl;
        }

        public int hashCode() {
            return 0;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PhysicalDisplayInfo{");
            stringBuilder.append(this.width);
            stringBuilder.append(" x ");
            stringBuilder.append(this.height);
            stringBuilder.append(", ");
            stringBuilder.append(this.refreshRate);
            stringBuilder.append(" fps, density ");
            stringBuilder.append(this.density);
            stringBuilder.append(", ");
            stringBuilder.append(this.xDpi);
            stringBuilder.append(" x ");
            stringBuilder.append(this.yDpi);
            stringBuilder.append(" dpi, secure ");
            stringBuilder.append(this.secure);
            stringBuilder.append(", appVsyncOffset ");
            stringBuilder.append(this.appVsyncOffsetNanos);
            stringBuilder.append(", bufferDeadline ");
            stringBuilder.append(this.presentationDeadlineNanos);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    public static class ScreenshotGraphicBuffer {
        private final ColorSpace mColorSpace;
        private final boolean mContainsSecureLayers;
        private final GraphicBuffer mGraphicBuffer;

        public ScreenshotGraphicBuffer(GraphicBuffer graphicBuffer, ColorSpace colorSpace, boolean bl) {
            this.mGraphicBuffer = graphicBuffer;
            this.mColorSpace = colorSpace;
            this.mContainsSecureLayers = bl;
        }

        private static ScreenshotGraphicBuffer createFromNative(int n, int n2, int n3, int n4, long l, int n5, boolean bl) {
            return new ScreenshotGraphicBuffer(GraphicBuffer.createFromExisting(n, n2, n3, n4, l), ColorSpace.get(ColorSpace.Named.values()[n5]), bl);
        }

        public boolean containsSecureLayers() {
            return this.mContainsSecureLayers;
        }

        public ColorSpace getColorSpace() {
            return this.mColorSpace;
        }

        public GraphicBuffer getGraphicBuffer() {
            return this.mGraphicBuffer;
        }
    }

    public static class Transaction
    implements Closeable {
        public static final NativeAllocationRegistry sRegistry = new NativeAllocationRegistry(Transaction.class.getClassLoader(), SurfaceControl.access$200(), 512L);
        Runnable mFreeNativeResources = sRegistry.registerNativeAllocation((Object)this, this.mNativeObject);
        private long mNativeObject = SurfaceControl.access$300();
        private final ArrayMap<SurfaceControl, Point> mResizedSurfaces = new ArrayMap();

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void applyResizedSurfaces() {
            int n = this.mResizedSurfaces.size() - 1;
            do {
                if (n < 0) {
                    this.mResizedSurfaces.clear();
                    return;
                }
                Point point = this.mResizedSurfaces.valueAt(n);
                SurfaceControl surfaceControl = this.mResizedSurfaces.keyAt(n);
                Object object = surfaceControl.mSizeLock;
                synchronized (object) {
                    surfaceControl.mWidth = point.x;
                    surfaceControl.mHeight = point.y;
                }
                --n;
            } while (true);
        }

        public void apply() {
            this.apply(false);
        }

        public void apply(boolean bl) {
            this.applyResizedSurfaces();
            SurfaceControl.nativeApplyTransaction(this.mNativeObject, bl);
        }

        @Override
        public void close() {
            this.mFreeNativeResources.run();
            this.mNativeObject = 0L;
        }

        @UnsupportedAppUsage
        public Transaction deferTransactionUntil(SurfaceControl surfaceControl, IBinder iBinder, long l) {
            if (l < 0L) {
                return this;
            }
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeDeferTransactionUntil(this.mNativeObject, surfaceControl.mNativeObject, iBinder, l);
            return this;
        }

        @UnsupportedAppUsage
        public Transaction deferTransactionUntilSurface(SurfaceControl surfaceControl, Surface surface, long l) {
            if (l < 0L) {
                return this;
            }
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeDeferTransactionUntilSurface(this.mNativeObject, surfaceControl.mNativeObject, surface.mNativeObject, l);
            return this;
        }

        public Transaction detachChildren(SurfaceControl surfaceControl) {
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeSeverChildren(this.mNativeObject, surfaceControl.mNativeObject);
            return this;
        }

        @UnsupportedAppUsage
        public Transaction hide(SurfaceControl surfaceControl) {
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 1, 1);
            return this;
        }

        public Transaction merge(Transaction transaction) {
            if (this == transaction) {
                return this;
            }
            this.mResizedSurfaces.putAll(transaction.mResizedSurfaces);
            transaction.mResizedSurfaces.clear();
            SurfaceControl.nativeMergeTransaction(this.mNativeObject, transaction.mNativeObject);
            return this;
        }

        public Transaction remove(SurfaceControl surfaceControl) {
            this.reparent(surfaceControl, null);
            surfaceControl.release();
            return this;
        }

        public Transaction reparent(SurfaceControl surfaceControl, SurfaceControl surfaceControl2) {
            surfaceControl.checkNotReleased();
            long l = 0L;
            if (surfaceControl2 != null) {
                surfaceControl2.checkNotReleased();
                l = surfaceControl2.mNativeObject;
            }
            SurfaceControl.nativeReparent(this.mNativeObject, surfaceControl.mNativeObject, l);
            return this;
        }

        public Transaction reparentChildren(SurfaceControl surfaceControl, IBinder iBinder) {
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeReparentChildren(this.mNativeObject, surfaceControl.mNativeObject, iBinder);
            return this;
        }

        public Transaction setAlpha(SurfaceControl surfaceControl, float f) {
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeSetAlpha(this.mNativeObject, surfaceControl.mNativeObject, f);
            return this;
        }

        public Transaction setAnimationTransaction() {
            SurfaceControl.nativeSetAnimationTransaction(this.mNativeObject);
            return this;
        }

        public Transaction setBufferSize(SurfaceControl surfaceControl, int n, int n2) {
            surfaceControl.checkNotReleased();
            this.mResizedSurfaces.put(surfaceControl, new Point(n, n2));
            SurfaceControl.nativeSetSize(this.mNativeObject, surfaceControl.mNativeObject, n, n2);
            return this;
        }

        @UnsupportedAppUsage
        public Transaction setColor(SurfaceControl surfaceControl, float[] arrf) {
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeSetColor(this.mNativeObject, surfaceControl.mNativeObject, arrf);
            return this;
        }

        public Transaction setColorSpaceAgnostic(SurfaceControl surfaceControl, boolean bl) {
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeSetColorSpaceAgnostic(this.mNativeObject, surfaceControl.mNativeObject, bl);
            return this;
        }

        public Transaction setColorTransform(SurfaceControl surfaceControl, float[] arrf, float[] arrf2) {
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeSetColorTransform(this.mNativeObject, surfaceControl.mNativeObject, arrf, arrf2);
            return this;
        }

        @UnsupportedAppUsage
        public Transaction setCornerRadius(SurfaceControl surfaceControl, float f) {
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeSetCornerRadius(this.mNativeObject, surfaceControl.mNativeObject, f);
            return this;
        }

        public Transaction setDisplayLayerStack(IBinder iBinder, int n) {
            if (iBinder != null) {
                SurfaceControl.nativeSetDisplayLayerStack(this.mNativeObject, iBinder, n);
                return this;
            }
            throw new IllegalArgumentException("displayToken must not be null");
        }

        public Transaction setDisplayProjection(IBinder iBinder, int n, Rect rect, Rect rect2) {
            if (iBinder != null) {
                if (rect != null) {
                    if (rect2 != null) {
                        SurfaceControl.nativeSetDisplayProjection(this.mNativeObject, iBinder, n, rect.left, rect.top, rect.right, rect.bottom, rect2.left, rect2.top, rect2.right, rect2.bottom);
                        return this;
                    }
                    throw new IllegalArgumentException("displayRect must not be null");
                }
                throw new IllegalArgumentException("layerStackRect must not be null");
            }
            throw new IllegalArgumentException("displayToken must not be null");
        }

        public Transaction setDisplaySize(IBinder iBinder, int n, int n2) {
            if (iBinder != null) {
                if (n > 0 && n2 > 0) {
                    SurfaceControl.nativeSetDisplaySize(this.mNativeObject, iBinder, n, n2);
                    return this;
                }
                throw new IllegalArgumentException("width and height must be positive");
            }
            throw new IllegalArgumentException("displayToken must not be null");
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public Transaction setDisplaySurface(IBinder iBinder, Surface surface) {
            if (iBinder == null) throw new IllegalArgumentException("displayToken must not be null");
            if (surface != null) {
                Object object = surface.mLock;
                synchronized (object) {
                    SurfaceControl.nativeSetDisplaySurface(this.mNativeObject, iBinder, surface.mNativeObject);
                    return this;
                }
            }
            SurfaceControl.nativeSetDisplaySurface(this.mNativeObject, iBinder, 0L);
            return this;
        }

        public Transaction setEarlyWakeup() {
            SurfaceControl.nativeSetEarlyWakeup(this.mNativeObject);
            return this;
        }

        public Transaction setGeometry(SurfaceControl surfaceControl, Rect rect, Rect rect2, int n) {
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeSetGeometry(this.mNativeObject, surfaceControl.mNativeObject, rect, rect2, n);
            return this;
        }

        public Transaction setGeometryAppliesWithResize(SurfaceControl surfaceControl) {
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeSetGeometryAppliesWithResize(this.mNativeObject, surfaceControl.mNativeObject);
            return this;
        }

        public Transaction setInputWindowInfo(SurfaceControl surfaceControl, InputWindowHandle inputWindowHandle) {
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeSetInputWindowInfo(this.mNativeObject, surfaceControl.mNativeObject, inputWindowHandle);
            return this;
        }

        public Transaction setLayer(SurfaceControl surfaceControl, int n) {
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeSetLayer(this.mNativeObject, surfaceControl.mNativeObject, n);
            return this;
        }

        @UnsupportedAppUsage(maxTargetSdk=26)
        public Transaction setLayerStack(SurfaceControl surfaceControl, int n) {
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeSetLayerStack(this.mNativeObject, surfaceControl.mNativeObject, n);
            return this;
        }

        @UnsupportedAppUsage
        public Transaction setMatrix(SurfaceControl surfaceControl, float f, float f2, float f3, float f4) {
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeSetMatrix(this.mNativeObject, surfaceControl.mNativeObject, f, f2, f3, f4);
            return this;
        }

        @UnsupportedAppUsage
        public Transaction setMatrix(SurfaceControl surfaceControl, Matrix matrix, float[] arrf) {
            matrix.getValues(arrf);
            this.setMatrix(surfaceControl, arrf[0], arrf[3], arrf[1], arrf[4]);
            this.setPosition(surfaceControl, arrf[2], arrf[5]);
            return this;
        }

        public Transaction setMetadata(SurfaceControl surfaceControl, int n, int n2) {
            Parcel parcel = Parcel.obtain();
            parcel.writeInt(n2);
            try {
                this.setMetadata(surfaceControl, n, parcel);
                return this;
            }
            finally {
                parcel.recycle();
            }
        }

        public Transaction setMetadata(SurfaceControl surfaceControl, int n, Parcel parcel) {
            SurfaceControl.nativeSetMetadata(this.mNativeObject, surfaceControl.mNativeObject, n, parcel);
            return this;
        }

        public Transaction setOpaque(SurfaceControl surfaceControl, boolean bl) {
            surfaceControl.checkNotReleased();
            if (bl) {
                SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 2, 2);
            } else {
                SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 0, 2);
            }
            return this;
        }

        public Transaction setOverrideScalingMode(SurfaceControl surfaceControl, int n) {
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeSetOverrideScalingMode(this.mNativeObject, surfaceControl.mNativeObject, n);
            return this;
        }

        @UnsupportedAppUsage
        public Transaction setPosition(SurfaceControl surfaceControl, float f, float f2) {
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeSetPosition(this.mNativeObject, surfaceControl.mNativeObject, f, f2);
            return this;
        }

        public Transaction setRelativeLayer(SurfaceControl surfaceControl, SurfaceControl surfaceControl2, int n) {
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeSetRelativeLayer(this.mNativeObject, surfaceControl.mNativeObject, surfaceControl2.getHandle(), n);
            return this;
        }

        public Transaction setSecure(SurfaceControl surfaceControl, boolean bl) {
            surfaceControl.checkNotReleased();
            if (bl) {
                SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 128, 128);
            } else {
                SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 0, 128);
            }
            return this;
        }

        public Transaction setTransparentRegionHint(SurfaceControl surfaceControl, Region region) {
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeSetTransparentRegionHint(this.mNativeObject, surfaceControl.mNativeObject, region);
            return this;
        }

        public Transaction setVisibility(SurfaceControl surfaceControl, boolean bl) {
            surfaceControl.checkNotReleased();
            if (bl) {
                return this.show(surfaceControl);
            }
            return this.hide(surfaceControl);
        }

        public Transaction setWindowCrop(SurfaceControl surfaceControl, int n, int n2) {
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeSetWindowCrop(this.mNativeObject, surfaceControl.mNativeObject, 0, 0, n, n2);
            return this;
        }

        @UnsupportedAppUsage
        public Transaction setWindowCrop(SurfaceControl surfaceControl, Rect rect) {
            surfaceControl.checkNotReleased();
            if (rect != null) {
                SurfaceControl.nativeSetWindowCrop(this.mNativeObject, surfaceControl.mNativeObject, rect.left, rect.top, rect.right, rect.bottom);
            } else {
                SurfaceControl.nativeSetWindowCrop(this.mNativeObject, surfaceControl.mNativeObject, 0, 0, 0, 0);
            }
            return this;
        }

        @UnsupportedAppUsage
        public Transaction show(SurfaceControl surfaceControl) {
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 0, 1);
            return this;
        }

        public Transaction syncInputWindows() {
            SurfaceControl.nativeSyncInputWindows(this.mNativeObject);
            return this;
        }

        public Transaction transferTouchFocus(IBinder iBinder, IBinder iBinder2) {
            SurfaceControl.nativeTransferTouchFocus(this.mNativeObject, iBinder, iBinder2);
            return this;
        }
    }

}

