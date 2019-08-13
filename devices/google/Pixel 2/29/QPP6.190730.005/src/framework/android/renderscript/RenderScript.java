/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.os.SystemProperties;
import android.renderscript.Allocation;
import android.renderscript.BaseObj;
import android.renderscript.Element;
import android.renderscript.ProgramRaster;
import android.renderscript.ProgramStore;
import android.renderscript.RSDriverException;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RSInvalidStateException;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScriptCacheDir;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.Surface;
import java.io.File;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RenderScript {
    public static final int CREATE_FLAG_LOW_LATENCY = 2;
    public static final int CREATE_FLAG_LOW_POWER = 4;
    public static final int CREATE_FLAG_NONE = 0;
    public static final int CREATE_FLAG_WAIT_FOR_ATTACH = 8;
    static final boolean DEBUG = false;
    static final boolean LOG_ENABLED = false;
    static final String LOG_TAG = "RenderScript_jni";
    static final long TRACE_TAG = 32768L;
    private static String mCachePath;
    private static ArrayList<RenderScript> mProcessContextList;
    static Method registerNativeAllocation;
    static Method registerNativeFree;
    static boolean sInitialized = false;
    static final long sMinorVersion = 1L;
    @UnsupportedAppUsage
    static int sPointerSize;
    static Object sRuntime;
    private Context mApplicationContext;
    long mContext;
    private int mContextFlags = 0;
    private int mContextSdkVersion = 0;
    ContextType mContextType = ContextType.NORMAL;
    private boolean mDestroyed = false;
    volatile Element mElement_ALLOCATION;
    volatile Element mElement_A_8;
    volatile Element mElement_BOOLEAN;
    volatile Element mElement_CHAR_2;
    volatile Element mElement_CHAR_3;
    volatile Element mElement_CHAR_4;
    volatile Element mElement_DOUBLE_2;
    volatile Element mElement_DOUBLE_3;
    volatile Element mElement_DOUBLE_4;
    volatile Element mElement_ELEMENT;
    volatile Element mElement_F16;
    volatile Element mElement_F32;
    volatile Element mElement_F64;
    volatile Element mElement_FLOAT_2;
    volatile Element mElement_FLOAT_3;
    volatile Element mElement_FLOAT_4;
    volatile Element mElement_FONT;
    volatile Element mElement_HALF_2;
    volatile Element mElement_HALF_3;
    volatile Element mElement_HALF_4;
    volatile Element mElement_I16;
    volatile Element mElement_I32;
    volatile Element mElement_I64;
    volatile Element mElement_I8;
    volatile Element mElement_INT_2;
    volatile Element mElement_INT_3;
    volatile Element mElement_INT_4;
    volatile Element mElement_LONG_2;
    volatile Element mElement_LONG_3;
    volatile Element mElement_LONG_4;
    volatile Element mElement_MATRIX_2X2;
    volatile Element mElement_MATRIX_3X3;
    volatile Element mElement_MATRIX_4X4;
    volatile Element mElement_MESH;
    volatile Element mElement_PROGRAM_FRAGMENT;
    volatile Element mElement_PROGRAM_RASTER;
    volatile Element mElement_PROGRAM_STORE;
    volatile Element mElement_PROGRAM_VERTEX;
    volatile Element mElement_RGBA_4444;
    volatile Element mElement_RGBA_5551;
    volatile Element mElement_RGBA_8888;
    volatile Element mElement_RGB_565;
    volatile Element mElement_RGB_888;
    volatile Element mElement_SAMPLER;
    volatile Element mElement_SCRIPT;
    volatile Element mElement_SHORT_2;
    volatile Element mElement_SHORT_3;
    volatile Element mElement_SHORT_4;
    volatile Element mElement_TYPE;
    volatile Element mElement_U16;
    volatile Element mElement_U32;
    volatile Element mElement_U64;
    volatile Element mElement_U8;
    volatile Element mElement_UCHAR_2;
    volatile Element mElement_UCHAR_3;
    volatile Element mElement_UCHAR_4;
    volatile Element mElement_UINT_2;
    volatile Element mElement_UINT_3;
    volatile Element mElement_UINT_4;
    volatile Element mElement_ULONG_2;
    volatile Element mElement_ULONG_3;
    volatile Element mElement_ULONG_4;
    volatile Element mElement_USHORT_2;
    volatile Element mElement_USHORT_3;
    volatile Element mElement_USHORT_4;
    volatile Element mElement_YUV;
    RSErrorHandler mErrorCallback = null;
    private boolean mIsProcessContext = false;
    @UnsupportedAppUsage
    RSMessageHandler mMessageCallback = null;
    MessageThread mMessageThread;
    ProgramRaster mProgramRaster_CULL_BACK;
    ProgramRaster mProgramRaster_CULL_FRONT;
    ProgramRaster mProgramRaster_CULL_NONE;
    ProgramStore mProgramStore_BLEND_ALPHA_DEPTH_NO_DEPTH;
    ProgramStore mProgramStore_BLEND_ALPHA_DEPTH_TEST;
    ProgramStore mProgramStore_BLEND_NONE_DEPTH_NO_DEPTH;
    ProgramStore mProgramStore_BLEND_NONE_DEPTH_TEST;
    ReentrantReadWriteLock mRWLock;
    volatile Sampler mSampler_CLAMP_LINEAR;
    volatile Sampler mSampler_CLAMP_LINEAR_MIP_LINEAR;
    volatile Sampler mSampler_CLAMP_NEAREST;
    volatile Sampler mSampler_MIRRORED_REPEAT_LINEAR;
    volatile Sampler mSampler_MIRRORED_REPEAT_LINEAR_MIP_LINEAR;
    volatile Sampler mSampler_MIRRORED_REPEAT_NEAREST;
    volatile Sampler mSampler_WRAP_LINEAR;
    volatile Sampler mSampler_WRAP_LINEAR_MIP_LINEAR;
    volatile Sampler mSampler_WRAP_NEAREST;

    static {
        mProcessContextList = new ArrayList();
        sInitialized = false;
        if (!SystemProperties.getBoolean("config.disable_renderscript", false)) {
            try {
                Class<?> class_ = Class.forName("dalvik.system.VMRuntime");
                sRuntime = class_.getDeclaredMethod("getRuntime", new Class[0]).invoke(null, new Object[0]);
                registerNativeAllocation = class_.getDeclaredMethod("registerNativeAllocation", Long.TYPE);
                registerNativeFree = class_.getDeclaredMethod("registerNativeFree", Long.TYPE);
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error loading GC methods: ");
                stringBuilder.append(exception);
                Log.e(LOG_TAG, stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("Error loading GC methods: ");
                stringBuilder.append(exception);
                throw new RSRuntimeException(stringBuilder.toString());
            }
            try {
                System.loadLibrary("rs_jni");
                RenderScript._nInit();
                sInitialized = true;
                sPointerSize = RenderScript.rsnSystemGetPointerSize();
            }
            catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error loading RS jni library: ");
                stringBuilder.append(unsatisfiedLinkError);
                Log.e(LOG_TAG, stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("Error loading RS jni library: ");
                stringBuilder.append(unsatisfiedLinkError);
                throw new RSRuntimeException(stringBuilder.toString());
            }
        }
    }

    RenderScript(Context context) {
        if (context != null) {
            this.mApplicationContext = context.getApplicationContext();
        }
        this.mRWLock = new ReentrantReadWriteLock();
        try {
            registerNativeAllocation.invoke(sRuntime, 4194304);
            return;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't invoke registerNativeAllocation:");
            stringBuilder.append(exception);
            Log.e(LOG_TAG, stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't invoke registerNativeAllocation:");
            stringBuilder.append(exception);
            throw new RSRuntimeException(stringBuilder.toString());
        }
    }

    static native void _nInit();

    public static RenderScript create(Context context) {
        return RenderScript.create(context, ContextType.NORMAL);
    }

    @UnsupportedAppUsage
    public static RenderScript create(Context context, int n) {
        return RenderScript.create(context, n, ContextType.NORMAL, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private static RenderScript create(Context object, int n, ContextType contextType, int n2) {
        if (n < 23) {
            return RenderScript.internalCreate((Context)object, n, contextType, n2);
        }
        ArrayList<RenderScript> arrayList = mProcessContextList;
        synchronized (arrayList) {
            RenderScript renderScript;
            Iterator<RenderScript> iterator = mProcessContextList.iterator();
            do {
                if (!iterator.hasNext()) {
                    object = RenderScript.internalCreate((Context)object, n, contextType, n2);
                    ((RenderScript)object).mIsProcessContext = true;
                    mProcessContextList.add((RenderScript)object);
                    return object;
                }
                renderScript = iterator.next();
            } while (renderScript.mContextType != contextType || renderScript.mContextFlags != n2 || renderScript.mContextSdkVersion != n);
            return renderScript;
        }
    }

    public static RenderScript create(Context context, ContextType contextType) {
        return RenderScript.create(context, contextType, 0);
    }

    public static RenderScript create(Context context, ContextType contextType, int n) {
        return RenderScript.create(context, context.getApplicationInfo().targetSdkVersion, contextType, n);
    }

    public static RenderScript createMultiContext(Context context, ContextType contextType, int n, int n2) {
        return RenderScript.internalCreate(context, n2, contextType, n);
    }

    static String getCachePath() {
        synchronized (RenderScript.class) {
            Object object;
            if (mCachePath == null) {
                if (RenderScriptCacheDir.mCacheDir != null) {
                    object = new File(RenderScriptCacheDir.mCacheDir, "com.android.renderscript.cache");
                    mCachePath = ((File)object).getAbsolutePath();
                    ((File)object).mkdirs();
                } else {
                    RSRuntimeException rSRuntimeException = new RSRuntimeException("RenderScript code cache directory uninitialized.");
                    throw rSRuntimeException;
                }
            }
            object = mCachePath;
            return object;
        }
    }

    @UnsupportedAppUsage
    public static long getMinorID() {
        return 1L;
    }

    public static long getMinorVersion() {
        return 1L;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void helpDestroy() {
        boolean bl = false;
        // MONITORENTER : this
        if (!this.mDestroyed) {
            bl = true;
            this.mDestroyed = true;
        }
        // MONITOREXIT : this
        if (!bl) return;
        this.nContextFinish();
        this.nContextDeinitToClient(this.mContext);
        MessageThread messageThread = this.mMessageThread;
        messageThread.mRun = false;
        messageThread.interrupt();
        boolean bl2 = false;
        bl = false;
        while (!bl2) {
            try {
                this.mMessageThread.join();
                bl2 = true;
            }
            catch (InterruptedException interruptedException) {
                bl = true;
            }
        }
        if (bl) {
            Log.v("RenderScript_jni", "Interrupted during wait for MessageThread to join");
            Thread.currentThread().interrupt();
        }
        this.nContextDestroy();
    }

    private static RenderScript internalCreate(Context object, int n, ContextType contextType, int n2) {
        if (!sInitialized) {
            Log.e("RenderScript_jni", "RenderScript.create() called when disabled; someone is likely to crash");
            return null;
        }
        if ((n2 & -15) == 0) {
            object = new RenderScript((Context)object);
            ((RenderScript)object).mContext = ((RenderScript)object).nContextCreate(((RenderScript)object).nDeviceCreate(), n2, n, contextType.mID);
            ((RenderScript)object).mContextType = contextType;
            ((RenderScript)object).mContextFlags = n2;
            ((RenderScript)object).mContextSdkVersion = n;
            if (((RenderScript)object).mContext != 0L) {
                ((RenderScript)object).nContextSetCacheDir(RenderScript.getCachePath());
                ((RenderScript)object).mMessageThread = new MessageThread((RenderScript)object);
                ((RenderScript)object).mMessageThread.start();
                return object;
            }
            throw new RSDriverException("Failed to create RS context.");
        }
        throw new RSIllegalArgumentException("Invalid flags passed.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void releaseAllContexts() {
        Object object;
        ArrayList<RenderScript> arrayList;
        Object object2 = mProcessContextList;
        synchronized (object2) {
            arrayList = mProcessContextList;
            object = new ArrayList();
            mProcessContextList = object;
        }
        object = arrayList.iterator();
        do {
            if (!object.hasNext()) {
                arrayList.clear();
                return;
            }
            object2 = (RenderScript)object.next();
            ((RenderScript)object2).mIsProcessContext = false;
            ((RenderScript)object2).destroy();
        } while (true);
    }

    static native int rsnSystemGetPointerSize();

    public void contextDump() {
        this.validate();
        this.nContextDump(0);
    }

    public void destroy() {
        if (this.mIsProcessContext) {
            return;
        }
        this.validate();
        this.helpDestroy();
    }

    protected void finalize() throws Throwable {
        this.helpDestroy();
        super.finalize();
    }

    public void finish() {
        this.nContextFinish();
    }

    public final Context getApplicationContext() {
        return this.mApplicationContext;
    }

    public RSErrorHandler getErrorHandler() {
        return this.mErrorCallback;
    }

    public RSMessageHandler getMessageHandler() {
        return this.mMessageCallback;
    }

    boolean isAlive() {
        boolean bl = this.mContext != 0L;
        return bl;
    }

    long nAllocationAdapterCreate(long l, long l2) {
        synchronized (this) {
            this.validate();
            l = this.rsnAllocationAdapterCreate(this.mContext, l, l2);
            return l;
        }
    }

    void nAllocationAdapterOffset(long l, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationAdapterOffset(this.mContext, l, n, n2, n3, n4, n5, n6, n7, n8, n9);
            return;
        }
    }

    void nAllocationCopyFromBitmap(long l, Bitmap bitmap) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationCopyFromBitmap(this.mContext, l, bitmap.getNativeInstance());
            return;
        }
    }

    void nAllocationCopyToBitmap(long l, Bitmap bitmap) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationCopyToBitmap(this.mContext, l, bitmap.getNativeInstance());
            return;
        }
    }

    long nAllocationCreateBitmapBackedAllocation(long l, int n, Bitmap bitmap, int n2) {
        synchronized (this) {
            this.validate();
            l = this.rsnAllocationCreateBitmapBackedAllocation(this.mContext, l, n, bitmap.getNativeInstance(), n2);
            return l;
        }
    }

    long nAllocationCreateBitmapRef(long l, Bitmap bitmap) {
        synchronized (this) {
            this.validate();
            l = this.rsnAllocationCreateBitmapRef(this.mContext, l, bitmap.getNativeInstance());
            return l;
        }
    }

    long nAllocationCreateFromAssetStream(int n, int n2, int n3) {
        synchronized (this) {
            this.validate();
            long l = this.rsnAllocationCreateFromAssetStream(this.mContext, n, n2, n3);
            return l;
        }
    }

    long nAllocationCreateFromBitmap(long l, int n, Bitmap bitmap, int n2) {
        synchronized (this) {
            this.validate();
            l = this.rsnAllocationCreateFromBitmap(this.mContext, l, n, bitmap.getNativeInstance(), n2);
            return l;
        }
    }

    long nAllocationCreateTyped(long l, int n, int n2, long l2) {
        synchronized (this) {
            this.validate();
            l = this.rsnAllocationCreateTyped(this.mContext, l, n, n2, l2);
            return l;
        }
    }

    long nAllocationCubeCreateFromBitmap(long l, int n, Bitmap bitmap, int n2) {
        synchronized (this) {
            this.validate();
            l = this.rsnAllocationCubeCreateFromBitmap(this.mContext, l, n, bitmap.getNativeInstance(), n2);
            return l;
        }
    }

    void nAllocationData1D(long l, int n, int n2, int n3, Object object, int n4, Element.DataType dataType, int n5, boolean bl) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationData1D(this.mContext, l, n, n2, n3, object, n4, dataType.mID, n5, bl);
            return;
        }
    }

    void nAllocationData2D(long l, int n, int n2, int n3, int n4, int n5, int n6, long l2, int n7, int n8, int n9, int n10) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationData2D(this.mContext, l, n, n2, n3, n4, n5, n6, l2, n7, n8, n9, n10);
            return;
        }
    }

    void nAllocationData2D(long l, int n, int n2, int n3, int n4, int n5, int n6, Object object, int n7, Element.DataType dataType, int n8, boolean bl) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationData2D(this.mContext, l, n, n2, n3, n4, n5, n6, object, n7, dataType.mID, n8, bl);
            return;
        }
    }

    void nAllocationData2D(long l, int n, int n2, int n3, int n4, Bitmap bitmap) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationData2D(this.mContext, l, n, n2, n3, n4, bitmap);
            return;
        }
    }

    void nAllocationData3D(long l, int n, int n2, int n3, int n4, int n5, int n6, int n7, long l2, int n8, int n9, int n10, int n11) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationData3D(this.mContext, l, n, n2, n3, n4, n5, n6, n7, l2, n8, n9, n10, n11);
            return;
        }
    }

    void nAllocationData3D(long l, int n, int n2, int n3, int n4, int n5, int n6, int n7, Object object, int n8, Element.DataType dataType, int n9, boolean bl) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationData3D(this.mContext, l, n, n2, n3, n4, n5, n6, n7, object, n8, dataType.mID, n9, bl);
            return;
        }
    }

    void nAllocationElementData(long l, int n, int n2, int n3, int n4, int n5, byte[] arrby, int n6) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationElementData(this.mContext, l, n, n2, n3, n4, n5, arrby, n6);
            return;
        }
    }

    void nAllocationElementRead(long l, int n, int n2, int n3, int n4, int n5, byte[] arrby, int n6) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationElementRead(this.mContext, l, n, n2, n3, n4, n5, arrby, n6);
            return;
        }
    }

    void nAllocationGenerateMipmaps(long l) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationGenerateMipmaps(this.mContext, l);
            return;
        }
    }

    ByteBuffer nAllocationGetByteBuffer(long l, long[] object, int n, int n2, int n3) {
        synchronized (this) {
            this.validate();
            object = this.rsnAllocationGetByteBuffer(this.mContext, l, (long[])object, n, n2, n3);
            return object;
        }
    }

    Surface nAllocationGetSurface(long l) {
        synchronized (this) {
            this.validate();
            Surface surface = this.rsnAllocationGetSurface(this.mContext, l);
            return surface;
        }
    }

    long nAllocationGetType(long l) {
        synchronized (this) {
            this.validate();
            l = this.rsnAllocationGetType(this.mContext, l);
            return l;
        }
    }

    long nAllocationIoReceive(long l) {
        synchronized (this) {
            this.validate();
            l = this.rsnAllocationIoReceive(this.mContext, l);
            return l;
        }
    }

    void nAllocationIoSend(long l) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationIoSend(this.mContext, l);
            return;
        }
    }

    void nAllocationRead(long l, Object object, Element.DataType dataType, int n, boolean bl) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationRead(this.mContext, l, object, dataType.mID, n, bl);
            return;
        }
    }

    void nAllocationRead1D(long l, int n, int n2, int n3, Object object, int n4, Element.DataType dataType, int n5, boolean bl) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationRead1D(this.mContext, l, n, n2, n3, object, n4, dataType.mID, n5, bl);
            return;
        }
    }

    void nAllocationRead2D(long l, int n, int n2, int n3, int n4, int n5, int n6, Object object, int n7, Element.DataType dataType, int n8, boolean bl) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationRead2D(this.mContext, l, n, n2, n3, n4, n5, n6, object, n7, dataType.mID, n8, bl);
            return;
        }
    }

    void nAllocationRead3D(long l, int n, int n2, int n3, int n4, int n5, int n6, int n7, Object object, int n8, Element.DataType dataType, int n9, boolean bl) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationRead3D(this.mContext, l, n, n2, n3, n4, n5, n6, n7, object, n8, dataType.mID, n9, bl);
            return;
        }
    }

    void nAllocationResize1D(long l, int n) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationResize1D(this.mContext, l, n);
            return;
        }
    }

    void nAllocationSetSurface(long l, Surface surface) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationSetSurface(this.mContext, l, surface);
            return;
        }
    }

    void nAllocationSetupBufferQueue(long l, int n) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationSetupBufferQueue(this.mContext, l, n);
            return;
        }
    }

    void nAllocationShareBufferQueue(long l, long l2) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationShareBufferQueue(this.mContext, l, l2);
            return;
        }
    }

    void nAllocationSyncAll(long l, int n) {
        synchronized (this) {
            this.validate();
            this.rsnAllocationSyncAll(this.mContext, l, n);
            return;
        }
    }

    void nAssignName(long l, byte[] arrby) {
        synchronized (this) {
            this.validate();
            this.rsnAssignName(this.mContext, l, arrby);
            return;
        }
    }

    long nClosureCreate(long l, long l2, long[] object, long[] arrl, int[] arrn, long[] arrl2, long[] arrl3) {
        synchronized (this) {
            block4 : {
                this.validate();
                l = this.rsnClosureCreate(this.mContext, l, l2, (long[])object, arrl, arrn, arrl2, arrl3);
                if (l == 0L) break block4;
                return l;
            }
            object = new RSRuntimeException("Failed creating closure.");
            throw object;
        }
    }

    void nClosureSetArg(long l, int n, long l2, int n2) {
        synchronized (this) {
            this.validate();
            this.rsnClosureSetArg(this.mContext, l, n, l2, n2);
            return;
        }
    }

    void nClosureSetGlobal(long l, long l2, long l3, int n) {
        synchronized (this) {
            this.validate();
            this.rsnClosureSetGlobal(this.mContext, l, l2, l3, n);
            return;
        }
    }

    void nContextBindProgramFragment(long l) {
        synchronized (this) {
            this.validate();
            this.rsnContextBindProgramFragment(this.mContext, l);
            return;
        }
    }

    void nContextBindProgramRaster(long l) {
        synchronized (this) {
            this.validate();
            this.rsnContextBindProgramRaster(this.mContext, l);
            return;
        }
    }

    void nContextBindProgramStore(long l) {
        synchronized (this) {
            this.validate();
            this.rsnContextBindProgramStore(this.mContext, l);
            return;
        }
    }

    void nContextBindProgramVertex(long l) {
        synchronized (this) {
            this.validate();
            this.rsnContextBindProgramVertex(this.mContext, l);
            return;
        }
    }

    void nContextBindRootScript(long l) {
        synchronized (this) {
            this.validate();
            this.rsnContextBindRootScript(this.mContext, l);
            return;
        }
    }

    void nContextBindSampler(int n, int n2) {
        synchronized (this) {
            this.validate();
            this.rsnContextBindSampler(this.mContext, n, n2);
            return;
        }
    }

    long nContextCreate(long l, int n, int n2, int n3) {
        synchronized (this) {
            l = this.rsnContextCreate(l, n, n2, n3);
            return l;
        }
    }

    long nContextCreateGL(long l, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12, float f, int n13) {
        synchronized (this) {
            l = this.rsnContextCreateGL(l, n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, f, n13);
            return l;
        }
    }

    native void nContextDeinitToClient(long var1);

    void nContextDestroy() {
        synchronized (this) {
            this.validate();
            ReentrantReadWriteLock.WriteLock writeLock = this.mRWLock.writeLock();
            writeLock.lock();
            long l = this.mContext;
            this.mContext = 0L;
            writeLock.unlock();
            this.rsnContextDestroy(l);
            return;
        }
    }

    void nContextDump(int n) {
        synchronized (this) {
            this.validate();
            this.rsnContextDump(this.mContext, n);
            return;
        }
    }

    void nContextFinish() {
        synchronized (this) {
            this.validate();
            this.rsnContextFinish(this.mContext);
            return;
        }
    }

    native String nContextGetErrorMessage(long var1);

    native int nContextGetUserMessage(long var1, int[] var3);

    native void nContextInitToClient(long var1);

    void nContextPause() {
        synchronized (this) {
            this.validate();
            this.rsnContextPause(this.mContext);
            return;
        }
    }

    native int nContextPeekMessage(long var1, int[] var3);

    void nContextResume() {
        synchronized (this) {
            this.validate();
            this.rsnContextResume(this.mContext);
            return;
        }
    }

    void nContextSendMessage(int n, int[] arrn) {
        synchronized (this) {
            this.validate();
            this.rsnContextSendMessage(this.mContext, n, arrn);
            return;
        }
    }

    void nContextSetCacheDir(String string2) {
        synchronized (this) {
            this.validate();
            this.rsnContextSetCacheDir(this.mContext, string2);
            return;
        }
    }

    void nContextSetPriority(int n) {
        synchronized (this) {
            this.validate();
            this.rsnContextSetPriority(this.mContext, n);
            return;
        }
    }

    void nContextSetSurface(int n, int n2, Surface surface) {
        synchronized (this) {
            this.validate();
            this.rsnContextSetSurface(this.mContext, n, n2, surface);
            return;
        }
    }

    void nContextSetSurfaceTexture(int n, int n2, SurfaceTexture surfaceTexture) {
        synchronized (this) {
            this.validate();
            this.rsnContextSetSurfaceTexture(this.mContext, n, n2, surfaceTexture);
            return;
        }
    }

    native long nDeviceCreate();

    native void nDeviceDestroy(long var1);

    native void nDeviceSetConfig(long var1, int var3, int var4);

    long nElementCreate(long l, int n, boolean bl, int n2) {
        synchronized (this) {
            this.validate();
            l = this.rsnElementCreate(this.mContext, l, n, bl, n2);
            return l;
        }
    }

    long nElementCreate2(long[] arrl, String[] arrstring, int[] arrn) {
        synchronized (this) {
            this.validate();
            long l = this.rsnElementCreate2(this.mContext, arrl, arrstring, arrn);
            return l;
        }
    }

    void nElementGetNativeData(long l, int[] arrn) {
        synchronized (this) {
            this.validate();
            this.rsnElementGetNativeData(this.mContext, l, arrn);
            return;
        }
    }

    void nElementGetSubElements(long l, long[] arrl, String[] arrstring, int[] arrn) {
        synchronized (this) {
            this.validate();
            this.rsnElementGetSubElements(this.mContext, l, arrl, arrstring, arrn);
            return;
        }
    }

    long nFileA3DCreateFromAsset(AssetManager assetManager, String string2) {
        synchronized (this) {
            this.validate();
            long l = this.rsnFileA3DCreateFromAsset(this.mContext, assetManager, string2);
            return l;
        }
    }

    long nFileA3DCreateFromAssetStream(long l) {
        synchronized (this) {
            this.validate();
            l = this.rsnFileA3DCreateFromAssetStream(this.mContext, l);
            return l;
        }
    }

    long nFileA3DCreateFromFile(String string2) {
        synchronized (this) {
            this.validate();
            long l = this.rsnFileA3DCreateFromFile(this.mContext, string2);
            return l;
        }
    }

    long nFileA3DGetEntryByIndex(long l, int n) {
        synchronized (this) {
            this.validate();
            l = this.rsnFileA3DGetEntryByIndex(this.mContext, l, n);
            return l;
        }
    }

    void nFileA3DGetIndexEntries(long l, int n, int[] arrn, String[] arrstring) {
        synchronized (this) {
            this.validate();
            this.rsnFileA3DGetIndexEntries(this.mContext, l, n, arrn, arrstring);
            return;
        }
    }

    int nFileA3DGetNumIndexEntries(long l) {
        synchronized (this) {
            this.validate();
            int n = this.rsnFileA3DGetNumIndexEntries(this.mContext, l);
            return n;
        }
    }

    long nFontCreateFromAsset(AssetManager assetManager, String string2, float f, int n) {
        synchronized (this) {
            this.validate();
            long l = this.rsnFontCreateFromAsset(this.mContext, assetManager, string2, f, n);
            return l;
        }
    }

    long nFontCreateFromAssetStream(String string2, float f, int n, long l) {
        synchronized (this) {
            this.validate();
            l = this.rsnFontCreateFromAssetStream(this.mContext, string2, f, n, l);
            return l;
        }
    }

    long nFontCreateFromFile(String string2, float f, int n) {
        synchronized (this) {
            this.validate();
            long l = this.rsnFontCreateFromFile(this.mContext, string2, f, n);
            return l;
        }
    }

    String nGetName(long l) {
        synchronized (this) {
            this.validate();
            String string2 = this.rsnGetName(this.mContext, l);
            return string2;
        }
    }

    long nInvokeClosureCreate(long l, byte[] object, long[] arrl, long[] arrl2, int[] arrn) {
        synchronized (this) {
            block4 : {
                this.validate();
                l = this.rsnInvokeClosureCreate(this.mContext, l, (byte[])object, arrl, arrl2, arrn);
                if (l == 0L) break block4;
                return l;
            }
            object = new RSRuntimeException("Failed creating closure.");
            throw object;
        }
    }

    long nMeshCreate(long[] arrl, long[] arrl2, int[] arrn) {
        synchronized (this) {
            this.validate();
            long l = this.rsnMeshCreate(this.mContext, arrl, arrl2, arrn);
            return l;
        }
    }

    int nMeshGetIndexCount(long l) {
        synchronized (this) {
            this.validate();
            int n = this.rsnMeshGetIndexCount(this.mContext, l);
            return n;
        }
    }

    void nMeshGetIndices(long l, long[] arrl, int[] arrn, int n) {
        synchronized (this) {
            this.validate();
            this.rsnMeshGetIndices(this.mContext, l, arrl, arrn, n);
            return;
        }
    }

    int nMeshGetVertexBufferCount(long l) {
        synchronized (this) {
            this.validate();
            int n = this.rsnMeshGetVertexBufferCount(this.mContext, l);
            return n;
        }
    }

    void nMeshGetVertices(long l, long[] arrl, int n) {
        synchronized (this) {
            this.validate();
            this.rsnMeshGetVertices(this.mContext, l, arrl, n);
            return;
        }
    }

    void nObjDestroy(long l) {
        long l2 = this.mContext;
        if (l2 != 0L) {
            this.rsnObjDestroy(l2, l);
        }
    }

    void nProgramBindConstants(long l, int n, long l2) {
        synchronized (this) {
            this.validate();
            this.rsnProgramBindConstants(this.mContext, l, n, l2);
            return;
        }
    }

    void nProgramBindSampler(long l, int n, long l2) {
        synchronized (this) {
            this.validate();
            this.rsnProgramBindSampler(this.mContext, l, n, l2);
            return;
        }
    }

    void nProgramBindTexture(long l, int n, long l2) {
        synchronized (this) {
            this.validate();
            this.rsnProgramBindTexture(this.mContext, l, n, l2);
            return;
        }
    }

    long nProgramFragmentCreate(String string2, String[] arrstring, long[] arrl) {
        synchronized (this) {
            this.validate();
            long l = this.rsnProgramFragmentCreate(this.mContext, string2, arrstring, arrl);
            return l;
        }
    }

    long nProgramRasterCreate(boolean bl, int n) {
        synchronized (this) {
            this.validate();
            long l = this.rsnProgramRasterCreate(this.mContext, bl, n);
            return l;
        }
    }

    long nProgramStoreCreate(boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, int n, int n2, int n3) {
        synchronized (this) {
            this.validate();
            long l = this.rsnProgramStoreCreate(this.mContext, bl, bl2, bl3, bl4, bl5, bl6, n, n2, n3);
            return l;
        }
    }

    long nProgramVertexCreate(String string2, String[] arrstring, long[] arrl) {
        synchronized (this) {
            this.validate();
            long l = this.rsnProgramVertexCreate(this.mContext, string2, arrstring, arrl);
            return l;
        }
    }

    long nSamplerCreate(int n, int n2, int n3, int n4, int n5, float f) {
        synchronized (this) {
            this.validate();
            long l = this.rsnSamplerCreate(this.mContext, n, n2, n3, n4, n5, f);
            return l;
        }
    }

    void nScriptBindAllocation(long l, long l2, int n) {
        synchronized (this) {
            this.validate();
            this.rsnScriptBindAllocation(this.mContext, l, l2, n);
            return;
        }
    }

    @UnsupportedAppUsage
    long nScriptCCreate(String string2, String string3, byte[] arrby, int n) {
        synchronized (this) {
            this.validate();
            long l = this.rsnScriptCCreate(this.mContext, string2, string3, arrby, n);
            return l;
        }
    }

    long nScriptFieldIDCreate(long l, int n) {
        synchronized (this) {
            this.validate();
            l = this.rsnScriptFieldIDCreate(this.mContext, l, n);
            return l;
        }
    }

    void nScriptForEach(long l, int n, long[] arrl, long l2, byte[] arrby, int[] arrn) {
        synchronized (this) {
            this.validate();
            this.rsnScriptForEach(this.mContext, l, n, arrl, l2, arrby, arrn);
            return;
        }
    }

    double nScriptGetVarD(long l, int n) {
        synchronized (this) {
            this.validate();
            double d = this.rsnScriptGetVarD(this.mContext, l, n);
            return d;
        }
    }

    float nScriptGetVarF(long l, int n) {
        synchronized (this) {
            this.validate();
            float f = this.rsnScriptGetVarF(this.mContext, l, n);
            return f;
        }
    }

    int nScriptGetVarI(long l, int n) {
        synchronized (this) {
            this.validate();
            n = this.rsnScriptGetVarI(this.mContext, l, n);
            return n;
        }
    }

    long nScriptGetVarJ(long l, int n) {
        synchronized (this) {
            this.validate();
            l = this.rsnScriptGetVarJ(this.mContext, l, n);
            return l;
        }
    }

    void nScriptGetVarV(long l, int n, byte[] arrby) {
        synchronized (this) {
            this.validate();
            this.rsnScriptGetVarV(this.mContext, l, n, arrby);
            return;
        }
    }

    long nScriptGroup2Create(String object, String string2, long[] arrl) {
        synchronized (this) {
            block4 : {
                this.validate();
                long l = this.rsnScriptGroup2Create(this.mContext, (String)object, string2, arrl);
                if (l == 0L) break block4;
                return l;
            }
            object = new RSRuntimeException("Failed creating script group.");
            throw object;
        }
    }

    void nScriptGroup2Execute(long l) {
        synchronized (this) {
            this.validate();
            this.rsnScriptGroup2Execute(this.mContext, l);
            return;
        }
    }

    long nScriptGroupCreate(long[] arrl, long[] arrl2, long[] arrl3, long[] arrl4, long[] arrl5) {
        synchronized (this) {
            this.validate();
            long l = this.rsnScriptGroupCreate(this.mContext, arrl, arrl2, arrl3, arrl4, arrl5);
            return l;
        }
    }

    void nScriptGroupExecute(long l) {
        synchronized (this) {
            this.validate();
            this.rsnScriptGroupExecute(this.mContext, l);
            return;
        }
    }

    void nScriptGroupSetInput(long l, long l2, long l3) {
        synchronized (this) {
            this.validate();
            this.rsnScriptGroupSetInput(this.mContext, l, l2, l3);
            return;
        }
    }

    void nScriptGroupSetOutput(long l, long l2, long l3) {
        synchronized (this) {
            this.validate();
            this.rsnScriptGroupSetOutput(this.mContext, l, l2, l3);
            return;
        }
    }

    void nScriptIntrinsicBLAS_BNNM(long l, int n, int n2, int n3, long l2, int n4, long l3, int n5, long l4, int n6, int n7) {
        synchronized (this) {
            this.validate();
            this.rsnScriptIntrinsicBLAS_BNNM(this.mContext, l, n, n2, n3, l2, n4, l3, n5, l4, n6, n7);
            return;
        }
    }

    void nScriptIntrinsicBLAS_Complex(long l, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, float f, float f2, long l2, long l3, float f3, float f4, long l4, int n10, int n11, int n12, int n13) {
        synchronized (this) {
            this.validate();
            this.rsnScriptIntrinsicBLAS_Complex(this.mContext, l, n, n2, n3, n4, n5, n6, n7, n8, n9, f, f2, l2, l3, f3, f4, l4, n10, n11, n12, n13);
            return;
        }
    }

    void nScriptIntrinsicBLAS_Double(long l, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, double d, long l2, long l3, double d2, long l4, int n10, int n11, int n12, int n13) {
        synchronized (this) {
            this.validate();
            this.rsnScriptIntrinsicBLAS_Double(this.mContext, l, n, n2, n3, n4, n5, n6, n7, n8, n9, d, l2, l3, d2, l4, n10, n11, n12, n13);
            return;
        }
    }

    void nScriptIntrinsicBLAS_Single(long l, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, float f, long l2, long l3, float f2, long l4, int n10, int n11, int n12, int n13) {
        synchronized (this) {
            this.validate();
            this.rsnScriptIntrinsicBLAS_Single(this.mContext, l, n, n2, n3, n4, n5, n6, n7, n8, n9, f, l2, l3, f2, l4, n10, n11, n12, n13);
            return;
        }
    }

    void nScriptIntrinsicBLAS_Z(long l, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, double d, double d2, long l2, long l3, double d3, double d4, long l4, int n10, int n11, int n12, int n13) {
        synchronized (this) {
            this.validate();
            this.rsnScriptIntrinsicBLAS_Z(this.mContext, l, n, n2, n3, n4, n5, n6, n7, n8, n9, d, d2, l2, l3, d3, d4, l4, n10, n11, n12, n13);
            return;
        }
    }

    long nScriptIntrinsicCreate(int n, long l) {
        synchronized (this) {
            this.validate();
            l = this.rsnScriptIntrinsicCreate(this.mContext, n, l);
            return l;
        }
    }

    void nScriptInvoke(long l, int n) {
        synchronized (this) {
            this.validate();
            this.rsnScriptInvoke(this.mContext, l, n);
            return;
        }
    }

    long nScriptInvokeIDCreate(long l, int n) {
        synchronized (this) {
            this.validate();
            l = this.rsnScriptInvokeIDCreate(this.mContext, l, n);
            return l;
        }
    }

    void nScriptInvokeV(long l, int n, byte[] arrby) {
        synchronized (this) {
            this.validate();
            this.rsnScriptInvokeV(this.mContext, l, n, arrby);
            return;
        }
    }

    long nScriptKernelIDCreate(long l, int n, int n2) {
        synchronized (this) {
            this.validate();
            l = this.rsnScriptKernelIDCreate(this.mContext, l, n, n2);
            return l;
        }
    }

    void nScriptReduce(long l, int n, long[] arrl, long l2, int[] arrn) {
        synchronized (this) {
            this.validate();
            this.rsnScriptReduce(this.mContext, l, n, arrl, l2, arrn);
            return;
        }
    }

    void nScriptSetTimeZone(long l, byte[] arrby) {
        synchronized (this) {
            this.validate();
            this.rsnScriptSetTimeZone(this.mContext, l, arrby);
            return;
        }
    }

    void nScriptSetVarD(long l, int n, double d) {
        synchronized (this) {
            this.validate();
            this.rsnScriptSetVarD(this.mContext, l, n, d);
            return;
        }
    }

    void nScriptSetVarF(long l, int n, float f) {
        synchronized (this) {
            this.validate();
            this.rsnScriptSetVarF(this.mContext, l, n, f);
            return;
        }
    }

    void nScriptSetVarI(long l, int n, int n2) {
        synchronized (this) {
            this.validate();
            this.rsnScriptSetVarI(this.mContext, l, n, n2);
            return;
        }
    }

    void nScriptSetVarJ(long l, int n, long l2) {
        synchronized (this) {
            this.validate();
            this.rsnScriptSetVarJ(this.mContext, l, n, l2);
            return;
        }
    }

    void nScriptSetVarObj(long l, int n, long l2) {
        synchronized (this) {
            this.validate();
            this.rsnScriptSetVarObj(this.mContext, l, n, l2);
            return;
        }
    }

    void nScriptSetVarV(long l, int n, byte[] arrby) {
        synchronized (this) {
            this.validate();
            this.rsnScriptSetVarV(this.mContext, l, n, arrby);
            return;
        }
    }

    void nScriptSetVarVE(long l, int n, byte[] arrby, long l2, int[] arrn) {
        synchronized (this) {
            this.validate();
            this.rsnScriptSetVarVE(this.mContext, l, n, arrby, l2, arrn);
            return;
        }
    }

    long nTypeCreate(long l, int n, int n2, int n3, boolean bl, boolean bl2, int n4) {
        synchronized (this) {
            this.validate();
            l = this.rsnTypeCreate(this.mContext, l, n, n2, n3, bl, bl2, n4);
            return l;
        }
    }

    void nTypeGetNativeData(long l, long[] arrl) {
        synchronized (this) {
            this.validate();
            this.rsnTypeGetNativeData(this.mContext, l, arrl);
            return;
        }
    }

    native long rsnAllocationAdapterCreate(long var1, long var3, long var5);

    native void rsnAllocationAdapterOffset(long var1, long var3, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13);

    native void rsnAllocationCopyFromBitmap(long var1, long var3, long var5);

    native void rsnAllocationCopyToBitmap(long var1, long var3, long var5);

    native long rsnAllocationCreateBitmapBackedAllocation(long var1, long var3, int var5, long var6, int var8);

    native long rsnAllocationCreateBitmapRef(long var1, long var3, long var5);

    native long rsnAllocationCreateFromAssetStream(long var1, int var3, int var4, int var5);

    native long rsnAllocationCreateFromBitmap(long var1, long var3, int var5, long var6, int var8);

    native long rsnAllocationCreateTyped(long var1, long var3, int var5, int var6, long var7);

    native long rsnAllocationCubeCreateFromBitmap(long var1, long var3, int var5, long var6, int var8);

    native void rsnAllocationData1D(long var1, long var3, int var5, int var6, int var7, Object var8, int var9, int var10, int var11, boolean var12);

    native void rsnAllocationData2D(long var1, long var3, int var5, int var6, int var7, int var8, int var9, int var10, long var11, int var13, int var14, int var15, int var16);

    native void rsnAllocationData2D(long var1, long var3, int var5, int var6, int var7, int var8, int var9, int var10, Object var11, int var12, int var13, int var14, boolean var15);

    native void rsnAllocationData2D(long var1, long var3, int var5, int var6, int var7, int var8, Bitmap var9);

    native void rsnAllocationData3D(long var1, long var3, int var5, int var6, int var7, int var8, int var9, int var10, int var11, long var12, int var14, int var15, int var16, int var17);

    native void rsnAllocationData3D(long var1, long var3, int var5, int var6, int var7, int var8, int var9, int var10, int var11, Object var12, int var13, int var14, int var15, boolean var16);

    native void rsnAllocationElementData(long var1, long var3, int var5, int var6, int var7, int var8, int var9, byte[] var10, int var11);

    native void rsnAllocationElementRead(long var1, long var3, int var5, int var6, int var7, int var8, int var9, byte[] var10, int var11);

    native void rsnAllocationGenerateMipmaps(long var1, long var3);

    native ByteBuffer rsnAllocationGetByteBuffer(long var1, long var3, long[] var5, int var6, int var7, int var8);

    native Surface rsnAllocationGetSurface(long var1, long var3);

    native long rsnAllocationGetType(long var1, long var3);

    native long rsnAllocationIoReceive(long var1, long var3);

    native void rsnAllocationIoSend(long var1, long var3);

    native void rsnAllocationRead(long var1, long var3, Object var5, int var6, int var7, boolean var8);

    native void rsnAllocationRead1D(long var1, long var3, int var5, int var6, int var7, Object var8, int var9, int var10, int var11, boolean var12);

    native void rsnAllocationRead2D(long var1, long var3, int var5, int var6, int var7, int var8, int var9, int var10, Object var11, int var12, int var13, int var14, boolean var15);

    native void rsnAllocationRead3D(long var1, long var3, int var5, int var6, int var7, int var8, int var9, int var10, int var11, Object var12, int var13, int var14, int var15, boolean var16);

    native void rsnAllocationResize1D(long var1, long var3, int var5);

    native void rsnAllocationSetSurface(long var1, long var3, Surface var5);

    native void rsnAllocationSetupBufferQueue(long var1, long var3, int var5);

    native void rsnAllocationShareBufferQueue(long var1, long var3, long var5);

    native void rsnAllocationSyncAll(long var1, long var3, int var5);

    native void rsnAssignName(long var1, long var3, byte[] var5);

    native long rsnClosureCreate(long var1, long var3, long var5, long[] var7, long[] var8, int[] var9, long[] var10, long[] var11);

    native void rsnClosureSetArg(long var1, long var3, int var5, long var6, int var8);

    native void rsnClosureSetGlobal(long var1, long var3, long var5, long var7, int var9);

    native void rsnContextBindProgramFragment(long var1, long var3);

    native void rsnContextBindProgramRaster(long var1, long var3);

    native void rsnContextBindProgramStore(long var1, long var3);

    native void rsnContextBindProgramVertex(long var1, long var3);

    native void rsnContextBindRootScript(long var1, long var3);

    native void rsnContextBindSampler(long var1, int var3, int var4);

    native long rsnContextCreate(long var1, int var3, int var4, int var5);

    native long rsnContextCreateGL(long var1, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, float var15, int var16);

    native void rsnContextDestroy(long var1);

    native void rsnContextDump(long var1, int var3);

    native void rsnContextFinish(long var1);

    native void rsnContextPause(long var1);

    native void rsnContextResume(long var1);

    native void rsnContextSendMessage(long var1, int var3, int[] var4);

    native void rsnContextSetCacheDir(long var1, String var3);

    native void rsnContextSetPriority(long var1, int var3);

    native void rsnContextSetSurface(long var1, int var3, int var4, Surface var5);

    native void rsnContextSetSurfaceTexture(long var1, int var3, int var4, SurfaceTexture var5);

    native long rsnElementCreate(long var1, long var3, int var5, boolean var6, int var7);

    native long rsnElementCreate2(long var1, long[] var3, String[] var4, int[] var5);

    native void rsnElementGetNativeData(long var1, long var3, int[] var5);

    native void rsnElementGetSubElements(long var1, long var3, long[] var5, String[] var6, int[] var7);

    native long rsnFileA3DCreateFromAsset(long var1, AssetManager var3, String var4);

    native long rsnFileA3DCreateFromAssetStream(long var1, long var3);

    native long rsnFileA3DCreateFromFile(long var1, String var3);

    native long rsnFileA3DGetEntryByIndex(long var1, long var3, int var5);

    native void rsnFileA3DGetIndexEntries(long var1, long var3, int var5, int[] var6, String[] var7);

    native int rsnFileA3DGetNumIndexEntries(long var1, long var3);

    native long rsnFontCreateFromAsset(long var1, AssetManager var3, String var4, float var5, int var6);

    native long rsnFontCreateFromAssetStream(long var1, String var3, float var4, int var5, long var6);

    native long rsnFontCreateFromFile(long var1, String var3, float var4, int var5);

    native String rsnGetName(long var1, long var3);

    native long rsnInvokeClosureCreate(long var1, long var3, byte[] var5, long[] var6, long[] var7, int[] var8);

    native long rsnMeshCreate(long var1, long[] var3, long[] var4, int[] var5);

    native int rsnMeshGetIndexCount(long var1, long var3);

    native void rsnMeshGetIndices(long var1, long var3, long[] var5, int[] var6, int var7);

    native int rsnMeshGetVertexBufferCount(long var1, long var3);

    native void rsnMeshGetVertices(long var1, long var3, long[] var5, int var6);

    native void rsnObjDestroy(long var1, long var3);

    native void rsnProgramBindConstants(long var1, long var3, int var5, long var6);

    native void rsnProgramBindSampler(long var1, long var3, int var5, long var6);

    native void rsnProgramBindTexture(long var1, long var3, int var5, long var6);

    native long rsnProgramFragmentCreate(long var1, String var3, String[] var4, long[] var5);

    native long rsnProgramRasterCreate(long var1, boolean var3, int var4);

    native long rsnProgramStoreCreate(long var1, boolean var3, boolean var4, boolean var5, boolean var6, boolean var7, boolean var8, int var9, int var10, int var11);

    native long rsnProgramVertexCreate(long var1, String var3, String[] var4, long[] var5);

    native long rsnSamplerCreate(long var1, int var3, int var4, int var5, int var6, int var7, float var8);

    native void rsnScriptBindAllocation(long var1, long var3, long var5, int var7);

    native long rsnScriptCCreate(long var1, String var3, String var4, byte[] var5, int var6);

    native long rsnScriptFieldIDCreate(long var1, long var3, int var5);

    native void rsnScriptForEach(long var1, long var3, int var5, long[] var6, long var7, byte[] var9, int[] var10);

    native double rsnScriptGetVarD(long var1, long var3, int var5);

    native float rsnScriptGetVarF(long var1, long var3, int var5);

    native int rsnScriptGetVarI(long var1, long var3, int var5);

    native long rsnScriptGetVarJ(long var1, long var3, int var5);

    native void rsnScriptGetVarV(long var1, long var3, int var5, byte[] var6);

    native long rsnScriptGroup2Create(long var1, String var3, String var4, long[] var5);

    native void rsnScriptGroup2Execute(long var1, long var3);

    native long rsnScriptGroupCreate(long var1, long[] var3, long[] var4, long[] var5, long[] var6, long[] var7);

    native void rsnScriptGroupExecute(long var1, long var3);

    native void rsnScriptGroupSetInput(long var1, long var3, long var5, long var7);

    native void rsnScriptGroupSetOutput(long var1, long var3, long var5, long var7);

    native void rsnScriptIntrinsicBLAS_BNNM(long var1, long var3, int var5, int var6, int var7, long var8, int var10, long var11, int var13, long var14, int var16, int var17);

    native void rsnScriptIntrinsicBLAS_Complex(long var1, long var3, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, float var14, float var15, long var16, long var18, float var20, float var21, long var22, int var24, int var25, int var26, int var27);

    native void rsnScriptIntrinsicBLAS_Double(long var1, long var3, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, double var14, long var16, long var18, double var20, long var22, int var24, int var25, int var26, int var27);

    native void rsnScriptIntrinsicBLAS_Single(long var1, long var3, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, float var14, long var15, long var17, float var19, long var20, int var22, int var23, int var24, int var25);

    native void rsnScriptIntrinsicBLAS_Z(long var1, long var3, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, double var14, double var16, long var18, long var20, double var22, double var24, long var26, int var28, int var29, int var30, int var31);

    native long rsnScriptIntrinsicCreate(long var1, int var3, long var4);

    native void rsnScriptInvoke(long var1, long var3, int var5);

    native long rsnScriptInvokeIDCreate(long var1, long var3, int var5);

    native void rsnScriptInvokeV(long var1, long var3, int var5, byte[] var6);

    native long rsnScriptKernelIDCreate(long var1, long var3, int var5, int var6);

    native void rsnScriptReduce(long var1, long var3, int var5, long[] var6, long var7, int[] var9);

    native void rsnScriptSetTimeZone(long var1, long var3, byte[] var5);

    native void rsnScriptSetVarD(long var1, long var3, int var5, double var6);

    native void rsnScriptSetVarF(long var1, long var3, int var5, float var6);

    native void rsnScriptSetVarI(long var1, long var3, int var5, int var6);

    native void rsnScriptSetVarJ(long var1, long var3, int var5, long var6);

    native void rsnScriptSetVarObj(long var1, long var3, int var5, long var6);

    native void rsnScriptSetVarV(long var1, long var3, int var5, byte[] var6);

    native void rsnScriptSetVarVE(long var1, long var3, int var5, byte[] var6, long var7, int[] var9);

    native long rsnTypeCreate(long var1, long var3, int var5, int var6, int var7, boolean var8, boolean var9, int var10);

    native void rsnTypeGetNativeData(long var1, long var3, long[] var5);

    long safeID(BaseObj baseObj) {
        if (baseObj != null) {
            return baseObj.getID(this);
        }
        return 0L;
    }

    public void sendMessage(int n, int[] arrn) {
        this.nContextSendMessage(n, arrn);
    }

    public void setErrorHandler(RSErrorHandler rSErrorHandler) {
        this.mErrorCallback = rSErrorHandler;
    }

    public void setMessageHandler(RSMessageHandler rSMessageHandler) {
        this.mMessageCallback = rSMessageHandler;
    }

    public void setPriority(Priority priority) {
        this.validate();
        this.nContextSetPriority(priority.mID);
    }

    @UnsupportedAppUsage
    void validate() {
        if (this.mContext != 0L) {
            return;
        }
        throw new RSInvalidStateException("Calling RS with no Context active.");
    }

    void validateObject(BaseObj baseObj) {
        if (baseObj != null && baseObj.mRS != this) {
            throw new RSIllegalArgumentException("Attempting to use an object across contexts.");
        }
    }

    public static enum ContextType {
        NORMAL(0),
        DEBUG(1),
        PROFILE(2);
        
        int mID;

        private ContextType(int n2) {
            this.mID = n2;
        }
    }

    static class MessageThread
    extends Thread {
        static final int RS_ERROR_FATAL_DEBUG = 2048;
        static final int RS_ERROR_FATAL_UNKNOWN = 4096;
        static final int RS_MESSAGE_TO_CLIENT_ERROR = 3;
        static final int RS_MESSAGE_TO_CLIENT_EXCEPTION = 1;
        static final int RS_MESSAGE_TO_CLIENT_NEW_BUFFER = 5;
        static final int RS_MESSAGE_TO_CLIENT_NONE = 0;
        static final int RS_MESSAGE_TO_CLIENT_RESIZE = 2;
        static final int RS_MESSAGE_TO_CLIENT_USER = 4;
        int[] mAuxData = new int[2];
        RenderScript mRS;
        boolean mRun = true;

        MessageThread(RenderScript renderScript) {
            super("RSMessageThread");
            this.mRS = renderScript;
        }

        @Override
        public void run() {
            Object object = new int[16];
            Object object2 = this.mRS;
            object2.nContextInitToClient(object2.mContext);
            while (this.mRun) {
                object[0] = 0;
                object2 = this.mRS;
                int n = object2.nContextPeekMessage(object2.mContext, this.mAuxData);
                object2 = this.mAuxData;
                int n2 = object2[1];
                int n3 = object2[0];
                if (n == 4) {
                    object2 = object;
                    if (n2 >> 2 >= ((int[])object).length) {
                        object2 = new int[n2 + 3 >> 2];
                    }
                    if (((RenderScript)(object = this.mRS)).nContextGetUserMessage(((RenderScript)object).mContext, (int[])object2) == 4) {
                        if (this.mRS.mMessageCallback != null) {
                            this.mRS.mMessageCallback.mData = object2;
                            this.mRS.mMessageCallback.mID = n3;
                            this.mRS.mMessageCallback.mLength = n2;
                            this.mRS.mMessageCallback.run();
                            object = object2;
                            continue;
                        }
                        throw new RSInvalidStateException("Received a message from the script with no message handler installed.");
                    }
                    throw new RSDriverException("Error processing message from RenderScript.");
                }
                if (n == 3) {
                    object2 = this.mRS;
                    object2 = object2.nContextGetErrorMessage(object2.mContext);
                    if (n3 < 4096 && (n3 < 2048 || this.mRS.mContextType == ContextType.DEBUG && this.mRS.mErrorCallback != null)) {
                        if (this.mRS.mErrorCallback != null) {
                            this.mRS.mErrorCallback.mErrorMessage = object2;
                            this.mRS.mErrorCallback.mErrorNum = n3;
                            this.mRS.mErrorCallback.run();
                            continue;
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("non fatal RS error, ");
                        stringBuilder.append((String)object2);
                        Log.e(RenderScript.LOG_TAG, stringBuilder.toString());
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Fatal error ");
                    ((StringBuilder)object).append(n3);
                    ((StringBuilder)object).append(", details: ");
                    ((StringBuilder)object).append((String)object2);
                    throw new RSRuntimeException(((StringBuilder)object).toString());
                }
                if (n == 5) {
                    object2 = this.mRS;
                    if (object2.nContextGetUserMessage(object2.mContext, (int[])object) == 5) {
                        Allocation.sendBufferNotification(((long)object[1] << 32) + ((long)object[0] & 0xFFFFFFFFL));
                        continue;
                    }
                    throw new RSDriverException("Error processing message from RenderScript.");
                }
                try {
                    MessageThread.sleep(1L, 0);
                }
                catch (InterruptedException interruptedException) {}
            }
        }
    }

    public static enum Priority {
        LOW(15),
        NORMAL(-8);
        
        int mID;

        private Priority(int n2) {
            this.mID = n2;
        }
    }

    public static class RSErrorHandler
    implements Runnable {
        protected String mErrorMessage;
        protected int mErrorNum;

        @Override
        public void run() {
        }
    }

    public static class RSMessageHandler
    implements Runnable {
        protected int[] mData;
        protected int mID;
        protected int mLength;

        @Override
        public void run() {
        }
    }

}

