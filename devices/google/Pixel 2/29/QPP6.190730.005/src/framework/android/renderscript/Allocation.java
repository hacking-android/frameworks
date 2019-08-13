/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.renderscript;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Trace;
import android.renderscript.AllocationAdapter;
import android.renderscript.BaseObj;
import android.renderscript.Element;
import android.renderscript.FieldPacker;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RSInvalidStateException;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.Type;
import android.util.Log;
import android.view.Surface;
import dalvik.system.CloseGuard;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class Allocation
extends BaseObj {
    private static final int MAX_NUMBER_IO_INPUT_ALLOC = 16;
    public static final int USAGE_GRAPHICS_CONSTANTS = 8;
    public static final int USAGE_GRAPHICS_RENDER_TARGET = 16;
    public static final int USAGE_GRAPHICS_TEXTURE = 2;
    public static final int USAGE_GRAPHICS_VERTEX = 4;
    public static final int USAGE_IO_INPUT = 32;
    public static final int USAGE_IO_OUTPUT = 64;
    public static final int USAGE_SCRIPT = 1;
    public static final int USAGE_SHARED = 128;
    static HashMap<Long, Allocation> mAllocationMap = new HashMap();
    static BitmapFactory.Options mBitmapOptions = new BitmapFactory.Options();
    Allocation mAdaptedAllocation;
    boolean mAutoPadding = false;
    Bitmap mBitmap;
    OnBufferAvailableListener mBufferNotifier;
    private ByteBuffer mByteBuffer = null;
    private long mByteBufferStride = -1L;
    int mCurrentCount;
    int mCurrentDimX;
    int mCurrentDimY;
    int mCurrentDimZ;
    private Surface mGetSurfaceSurface = null;
    MipmapControl mMipmapControl;
    boolean mOwningType = false;
    boolean mReadAllowed = true;
    int[] mSelectedArray;
    Type.CubemapFace mSelectedFace = Type.CubemapFace.POSITIVE_X;
    int mSelectedLOD;
    int mSelectedX;
    int mSelectedY;
    int mSelectedZ;
    int mSize;
    long mTimeStamp = -1L;
    Type mType;
    int mUsage;
    boolean mWriteAllowed = true;

    static {
        Allocation.mBitmapOptions.inScaled = false;
    }

    Allocation(long l, RenderScript renderScript, Type object, int n) {
        super(l, renderScript);
        if ((n & -256) == 0) {
            if ((n & 32) != 0) {
                this.mWriteAllowed = false;
                if ((n & -36) != 0) {
                    throw new RSIllegalArgumentException("Invalid usage combination.");
                }
            }
            this.mType = object;
            this.mUsage = n;
            if (object != null) {
                this.mSize = this.mType.getCount() * this.mType.getElement().getBytesSize();
                this.updateCacheInfo((Type)object);
            }
            try {
                RenderScript.registerNativeAllocation.invoke(RenderScript.sRuntime, this.mSize);
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Couldn't invoke registerNativeAllocation:");
                ((StringBuilder)object).append(exception);
                Log.e("RenderScript_jni", ((StringBuilder)object).toString());
                object = new StringBuilder();
                ((StringBuilder)object).append("Couldn't invoke registerNativeAllocation:");
                ((StringBuilder)object).append(exception);
                throw new RSRuntimeException(((StringBuilder)object).toString());
            }
            this.guard.open("destroy");
            return;
        }
        throw new RSIllegalArgumentException("Unknown usage specified.");
    }

    Allocation(long l, RenderScript renderScript, Type type, boolean bl, int n, MipmapControl mipmapControl) {
        this(l, renderScript, type, n);
        this.mOwningType = bl;
        this.mMipmapControl = mipmapControl;
    }

    private void copy1DRangeFromUnchecked(int n, int n2, Object object, Element.DataType dataType, int n3) {
        Trace.traceBegin(32768L, "copy1DRangeFromUnchecked");
        int n4 = this.mType.mElement.getBytesSize() * n2;
        boolean bl = this.mAutoPadding && this.mType.getElement().getVectorSize() == 3;
        try {
            this.data1DChecks(n, n2, n3 * dataType.mSize, n4, bl);
            this.mRS.nAllocationData1D(this.getIDSafe(), n, this.mSelectedLOD, n2, object, n4, dataType, this.mType.mElement.mType.mSize, bl);
            return;
        }
        finally {
            Trace.traceEnd(32768L);
        }
    }

    private void copy1DRangeToUnchecked(int n, int n2, Object object, Element.DataType dataType, int n3) {
        Trace.traceBegin(32768L, "copy1DRangeToUnchecked");
        int n4 = this.mType.mElement.getBytesSize() * n2;
        boolean bl = this.mAutoPadding && this.mType.getElement().getVectorSize() == 3;
        try {
            this.data1DChecks(n, n2, n3 * dataType.mSize, n4, bl);
            this.mRS.nAllocationRead1D(this.getIDSafe(), n, this.mSelectedLOD, n2, object, n4, dataType, this.mType.mElement.mType.mSize, bl);
            return;
        }
        finally {
            Trace.traceEnd(32768L);
        }
    }

    private void copy3DRangeFromUnchecked(int n, int n2, int n3, int n4, int n5, int n6, Object object, Element.DataType dataType, int n7) {
        boolean bl;
        int n8;
        block8 : {
            block9 : {
                Trace.traceBegin(32768L, "copy3DRangeFromUnchecked");
                this.mRS.validate();
                this.validate3DRange(n, n2, n3, n4, n5, n6);
                n8 = this.mType.mElement.getBytesSize() * n4 * n5 * n6;
                n7 = dataType.mSize * n7;
                bl = this.mAutoPadding;
                if (!bl) break block8;
                if (this.mType.getElement().getVectorSize() != 3) break block8;
                if (n8 / 4 * 3 > n7) break block9;
                bl = true;
                n7 = n8;
            }
            object = new RSIllegalArgumentException("Array too small for allocation type.");
            throw object;
        }
        if (n8 <= n7) {
            bl = false;
            this.mRS.nAllocationData3D(this.getIDSafe(), n, n2, n3, this.mSelectedLOD, n4, n5, n6, object, n7, dataType, this.mType.mElement.mType.mSize, bl);
            return;
        }
        try {
            object = new RSIllegalArgumentException("Array too small for allocation type.");
            throw object;
        }
        finally {
            Trace.traceEnd(32768L);
        }
    }

    private void copy3DRangeToUnchecked(int n, int n2, int n3, int n4, int n5, int n6, Object object, Element.DataType dataType, int n7) {
        boolean bl;
        int n8;
        block8 : {
            block9 : {
                Trace.traceBegin(32768L, "copy3DRangeToUnchecked");
                this.mRS.validate();
                this.validate3DRange(n, n2, n3, n4, n5, n6);
                n8 = this.mType.mElement.getBytesSize() * n4 * n5 * n6;
                n7 = dataType.mSize * n7;
                bl = this.mAutoPadding;
                if (!bl) break block8;
                if (this.mType.getElement().getVectorSize() != 3) break block8;
                if (n8 / 4 * 3 > n7) break block9;
                bl = true;
                n7 = n8;
            }
            object = new RSIllegalArgumentException("Array too small for allocation type.");
            throw object;
        }
        if (n8 <= n7) {
            bl = false;
            this.mRS.nAllocationRead3D(this.getIDSafe(), n, n2, n3, this.mSelectedLOD, n4, n5, n6, object, n7, dataType, this.mType.mElement.mType.mSize, bl);
            return;
        }
        try {
            object = new RSIllegalArgumentException("Array too small for allocation type.");
            throw object;
        }
        finally {
            Trace.traceEnd(32768L);
        }
    }

    private void copyFromUnchecked(Object object, Element.DataType dataType, int n) {
        try {
            Trace.traceBegin(32768L, "copyFromUnchecked");
            this.mRS.validate();
            if (this.mCurrentDimZ > 0) {
                this.copy3DRangeFromUnchecked(0, 0, 0, this.mCurrentDimX, this.mCurrentDimY, this.mCurrentDimZ, object, dataType, n);
            } else if (this.mCurrentDimY > 0) {
                this.copy2DRangeFromUnchecked(0, 0, this.mCurrentDimX, this.mCurrentDimY, object, dataType, n);
            } else {
                this.copy1DRangeFromUnchecked(0, this.mCurrentCount, object, dataType, n);
            }
            return;
        }
        finally {
            Trace.traceEnd(32768L);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void copyTo(Object object, Element.DataType dataType, int n) {
        try {
            block13 : {
                boolean bl;
                block12 : {
                    block11 : {
                        boolean bl2;
                        Trace.traceBegin(32768L, "copyTo");
                        this.mRS.validate();
                        bl = bl2 = false;
                        if (this.mAutoPadding) {
                            int n2 = this.mType.getElement().getVectorSize();
                            bl = bl2;
                            if (n2 == 3) {
                                bl = true;
                            }
                        }
                        if (!bl) break block11;
                        if (dataType.mSize * n < this.mSize / 4 * 3) {
                            object = new RSIllegalArgumentException("Size of output array cannot be smaller than size of allocation.");
                            throw object;
                        }
                        break block12;
                    }
                    if (dataType.mSize * n < this.mSize) break block13;
                }
                this.mRS.nAllocationRead(this.getID(this.mRS), object, dataType, this.mType.mElement.mType.mSize, bl);
                return;
            }
            object = new RSIllegalArgumentException("Size of output array cannot be smaller than size of allocation.");
            throw object;
        }
        finally {
            Trace.traceEnd(32768L);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Allocation[] createAllocations(RenderScript object, Type type, int n, int n2) {
        Allocation[] arrallocation;
        block7 : {
            try {
                Trace.traceBegin(32768L, "createAllocations");
                ((RenderScript)object).validate();
                if (type.getID((RenderScript)object) != 0L) {
                    arrallocation = new Allocation[n2];
                    arrallocation[0] = Allocation.createTyped((RenderScript)object, type, n);
                    if ((n & 32) != 0) {
                        if (n2 > 16) {
                            arrallocation[0].destroy();
                            object = new RSIllegalArgumentException("Exceeds the max number of Allocations allowed: 16");
                            throw object;
                        }
                        arrallocation[0].setupBufferQueue(n2);
                    }
                    break block7;
                }
                object = new RSInvalidStateException("Bad Type");
                throw object;
            }
            catch (Throwable throwable) {
                Trace.traceEnd(32768L);
                throw throwable;
            }
        }
        for (n = 1; n < n2; ++n) {
            arrallocation[n] = Allocation.createFromAllocation((RenderScript)object, arrallocation[0]);
        }
        Trace.traceEnd(32768L);
        return arrallocation;
    }

    public static Allocation createCubemapFromBitmap(RenderScript renderScript, Bitmap bitmap) {
        return Allocation.createCubemapFromBitmap(renderScript, bitmap, MipmapControl.MIPMAP_NONE, 2);
    }

    public static Allocation createCubemapFromBitmap(RenderScript object, Bitmap bitmap, MipmapControl mipmapControl, int n) {
        ((RenderScript)object).validate();
        int n2 = bitmap.getHeight();
        int n3 = bitmap.getWidth();
        if (n3 % 6 == 0) {
            if (n3 / 6 == n2) {
                boolean bl = false;
                n3 = (n2 - 1 & n2) == 0 ? 1 : 0;
                if (n3 != 0) {
                    Element element = Allocation.elementFromBitmap((RenderScript)object, bitmap);
                    Object object2 = new Type.Builder((RenderScript)object, element);
                    ((Type.Builder)object2).setX(n2);
                    ((Type.Builder)object2).setY(n2);
                    ((Type.Builder)object2).setFaces(true);
                    if (mipmapControl == MipmapControl.MIPMAP_FULL) {
                        bl = true;
                    }
                    ((Type.Builder)object2).setMipmaps(bl);
                    object2 = ((Type.Builder)object2).create();
                    long l = ((RenderScript)object).nAllocationCubeCreateFromBitmap(((BaseObj)object2).getID((RenderScript)object), mipmapControl.mID, bitmap, n);
                    if (l != 0L) {
                        return new Allocation(l, (RenderScript)object, (Type)object2, true, n, mipmapControl);
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Load failed for bitmap ");
                    ((StringBuilder)object).append(bitmap);
                    ((StringBuilder)object).append(" element ");
                    ((StringBuilder)object).append(element);
                    throw new RSRuntimeException(((StringBuilder)object).toString());
                }
                throw new RSIllegalArgumentException("Only power of 2 cube faces supported");
            }
            throw new RSIllegalArgumentException("Only square cube map faces supported");
        }
        throw new RSIllegalArgumentException("Cubemap height must be multiple of 6");
    }

    public static Allocation createCubemapFromCubeFaces(RenderScript renderScript, Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, Bitmap bitmap5, Bitmap bitmap6) {
        return Allocation.createCubemapFromCubeFaces(renderScript, bitmap, bitmap2, bitmap3, bitmap4, bitmap5, bitmap6, MipmapControl.MIPMAP_NONE, 2);
    }

    public static Allocation createCubemapFromCubeFaces(RenderScript object, Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, Bitmap bitmap5, Bitmap bitmap6, MipmapControl object2, int n) {
        int n2 = bitmap.getHeight();
        if (bitmap.getWidth() == n2 && bitmap2.getWidth() == n2 && bitmap2.getHeight() == n2 && bitmap3.getWidth() == n2 && bitmap3.getHeight() == n2 && bitmap4.getWidth() == n2 && bitmap4.getHeight() == n2 && bitmap5.getWidth() == n2 && bitmap5.getHeight() == n2 && bitmap6.getWidth() == n2 && bitmap6.getHeight() == n2) {
            boolean bl = false;
            boolean bl2 = (n2 - 1 & n2) == 0;
            if (bl2) {
                Type.Builder builder = new Type.Builder((RenderScript)object, Allocation.elementFromBitmap((RenderScript)object, bitmap));
                builder.setX(n2);
                builder.setY(n2);
                builder.setFaces(true);
                if (object2 == MipmapControl.MIPMAP_FULL) {
                    bl = true;
                }
                builder.setMipmaps(bl);
                object2 = Allocation.createTyped((RenderScript)object, builder.create(), object2, n);
                object = AllocationAdapter.create2D((RenderScript)object, (Allocation)object2);
                ((AllocationAdapter)object).setFace(Type.CubemapFace.POSITIVE_X);
                ((Allocation)object).copyFrom(bitmap);
                ((AllocationAdapter)object).setFace(Type.CubemapFace.NEGATIVE_X);
                ((Allocation)object).copyFrom(bitmap2);
                ((AllocationAdapter)object).setFace(Type.CubemapFace.POSITIVE_Y);
                ((Allocation)object).copyFrom(bitmap3);
                ((AllocationAdapter)object).setFace(Type.CubemapFace.NEGATIVE_Y);
                ((Allocation)object).copyFrom(bitmap4);
                ((AllocationAdapter)object).setFace(Type.CubemapFace.POSITIVE_Z);
                ((Allocation)object).copyFrom(bitmap5);
                ((AllocationAdapter)object).setFace(Type.CubemapFace.NEGATIVE_Z);
                ((Allocation)object).copyFrom(bitmap6);
                return object2;
            }
            throw new RSIllegalArgumentException("Only power of 2 cube faces supported");
        }
        throw new RSIllegalArgumentException("Only square cube map faces supported");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Allocation createFromAllocation(RenderScript object, Allocation allocation) {
        try {
            Trace.traceBegin(32768L, "createFromAllcation");
            ((RenderScript)object).validate();
            if (allocation.getID((RenderScript)object) != 0L) {
                Type type = allocation.getType();
                int n = allocation.getUsage();
                MipmapControl mipmapControl = allocation.getMipmap();
                long l = ((RenderScript)object).nAllocationCreateTyped(type.getID((RenderScript)object), mipmapControl.mID, n, 0L);
                if (l != 0L) {
                    Allocation allocation2 = new Allocation(l, (RenderScript)object, type, false, n, mipmapControl);
                    if ((n & 32) == 0) return allocation2;
                    allocation2.shareBufferQueue(allocation);
                    return allocation2;
                }
                object = new RSRuntimeException("Allocation creation failed.");
                throw object;
            }
            object = new RSInvalidStateException("Bad input Allocation");
            throw object;
        }
        finally {
            Trace.traceEnd(32768L);
        }
    }

    public static Allocation createFromBitmap(RenderScript renderScript, Bitmap bitmap) {
        if (renderScript.getApplicationContext().getApplicationInfo().targetSdkVersion >= 18) {
            return Allocation.createFromBitmap(renderScript, bitmap, MipmapControl.MIPMAP_NONE, 131);
        }
        return Allocation.createFromBitmap(renderScript, bitmap, MipmapControl.MIPMAP_NONE, 2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Allocation createFromBitmap(RenderScript object, Bitmap bitmap, MipmapControl mipmapControl, int n) {
        try {
            Trace.traceBegin(32768L, "createFromBitmap");
            ((RenderScript)object).validate();
            if (bitmap.getConfig() == null) {
                if ((n & 128) == 0) {
                    Bitmap bitmap2 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap2);
                    canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
                    object = Allocation.createFromBitmap((RenderScript)object, bitmap2, mipmapControl, n);
                    return object;
                }
                object = new RSIllegalArgumentException("USAGE_SHARED cannot be used with a Bitmap that has a null config.");
                throw object;
            }
            Type type = Allocation.typeFromBitmap((RenderScript)object, bitmap, mipmapControl);
            MipmapControl mipmapControl2 = MipmapControl.MIPMAP_NONE;
            if (mipmapControl == mipmapControl2 && type.getElement().isCompatible(Element.RGBA_8888((RenderScript)object)) && n == 131) {
                long l = ((RenderScript)object).nAllocationCreateBitmapBackedAllocation(type.getID((RenderScript)object), mipmapControl.mID, bitmap, n);
                if (l != 0L) {
                    Allocation allocation = new Allocation(l, (RenderScript)object, type, true, n, mipmapControl);
                    allocation.setBitmap(bitmap);
                    return allocation;
                }
                object = new RSRuntimeException("Load failed.");
                throw object;
            }
            long l = ((RenderScript)object).nAllocationCreateFromBitmap(type.getID((RenderScript)object), mipmapControl.mID, bitmap, n);
            if (l != 0L) {
                object = new Allocation(l, (RenderScript)object, type, true, n, mipmapControl);
                return object;
            }
            object = new RSRuntimeException("Load failed.");
            throw object;
        }
        finally {
            Trace.traceEnd(32768L);
        }
    }

    public static Allocation createFromBitmapResource(RenderScript renderScript, Resources resources, int n) {
        if (renderScript.getApplicationContext().getApplicationInfo().targetSdkVersion >= 18) {
            return Allocation.createFromBitmapResource(renderScript, resources, n, MipmapControl.MIPMAP_NONE, 3);
        }
        return Allocation.createFromBitmapResource(renderScript, resources, n, MipmapControl.MIPMAP_NONE, 2);
    }

    public static Allocation createFromBitmapResource(RenderScript object, Resources object2, int n, MipmapControl mipmapControl, int n2) {
        ((RenderScript)object).validate();
        if ((n2 & 224) == 0) {
            object2 = BitmapFactory.decodeResource((Resources)object2, n);
            object = Allocation.createFromBitmap((RenderScript)object, (Bitmap)object2, mipmapControl, n2);
            ((Bitmap)object2).recycle();
            return object;
        }
        throw new RSIllegalArgumentException("Unsupported usage specified.");
    }

    public static Allocation createFromString(RenderScript object, String arrby, int n) {
        ((RenderScript)object).validate();
        try {
            arrby = arrby.getBytes("UTF-8");
            object = Allocation.createSized((RenderScript)object, Element.U8((RenderScript)object), arrby.length, n);
            ((Allocation)object).copyFrom(arrby);
            return object;
        }
        catch (Exception exception) {
            throw new RSRuntimeException("Could not convert string to utf-8.");
        }
    }

    public static Allocation createSized(RenderScript renderScript, Element element, int n) {
        return Allocation.createSized(renderScript, element, n, 1);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Allocation createSized(RenderScript object, Element baseObj, int n, int n2) {
        void var0_4;
        block8 : {
            block7 : {
                Trace.traceBegin(32768L, "createSized");
                ((RenderScript)object).validate();
                Type.Builder builder = new Type.Builder((RenderScript)object, (Element)baseObj);
                try {
                    builder.setX(n);
                    baseObj = builder.create();
                    long l = ((RenderScript)object).nAllocationCreateTyped(baseObj.getID((RenderScript)object), MipmapControl.MIPMAP_NONE.mID, n2, 0L);
                    if (l == 0L) break block7;
                    object = new Allocation(l, (RenderScript)object, (Type)baseObj, true, n2, MipmapControl.MIPMAP_NONE);
                }
                catch (Throwable throwable) {}
                Trace.traceEnd(32768L);
                return object;
            }
            object = new RSRuntimeException("Allocation creation failed.");
            throw object;
            break block8;
            catch (Throwable throwable) {
                break block8;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        Trace.traceEnd(32768L);
        throw var0_4;
    }

    public static Allocation createTyped(RenderScript renderScript, Type type) {
        return Allocation.createTyped(renderScript, type, MipmapControl.MIPMAP_NONE, 1);
    }

    public static Allocation createTyped(RenderScript renderScript, Type type, int n) {
        return Allocation.createTyped(renderScript, type, MipmapControl.MIPMAP_NONE, n);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Allocation createTyped(RenderScript var0, Type var1_4, MipmapControl var2_5, int var3_6) {
        block6 : {
            block5 : {
                Trace.traceBegin(32768L, "createTyped");
                var0.validate();
                if (var1_4.getID((RenderScript)var0) == 0L) ** GOTO lbl17
                var4_7 = var1_4.getID((RenderScript)var0);
                try {
                    var4_7 = var0.nAllocationCreateTyped(var4_7, var2_5.mID, var3_6, 0L);
                    if (var4_7 == 0L) break block5;
                    var0 = new Allocation(var4_7, (RenderScript)var0, var1_4, false, var3_6, var2_5);
                }
                catch (Throwable var0_1) {}
                Trace.traceEnd(32768L);
                return var0;
            }
            var0 = new RSRuntimeException("Allocation creation failed.");
            throw var0;
lbl17: // 1 sources:
            var0 = new RSInvalidStateException("Bad Type");
            throw var0;
            break block6;
            catch (Throwable var0_2) {
                // empty catch block
            }
        }
        Trace.traceEnd(32768L);
        throw var0_3;
    }

    private void data1DChecks(int n, int n2, int n3, int n4, boolean bl) {
        block5 : {
            block6 : {
                block7 : {
                    block10 : {
                        block9 : {
                            block8 : {
                                this.mRS.validate();
                                if (n < 0) break block5;
                                if (n2 < 1) break block6;
                                if (n + n2 > this.mCurrentCount) break block7;
                                if (!bl) break block8;
                                if (n3 < n4 / 4 * 3) {
                                    throw new RSIllegalArgumentException("Array too small for allocation type.");
                                }
                                break block9;
                            }
                            if (n3 < n4) break block10;
                        }
                        return;
                    }
                    throw new RSIllegalArgumentException("Array too small for allocation type.");
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Overflow, Available count ");
                stringBuilder.append(this.mCurrentCount);
                stringBuilder.append(", got ");
                stringBuilder.append(n2);
                stringBuilder.append(" at offset ");
                stringBuilder.append(n);
                stringBuilder.append(".");
                throw new RSIllegalArgumentException(stringBuilder.toString());
            }
            throw new RSIllegalArgumentException("Count must be >= 1.");
        }
        throw new RSIllegalArgumentException("Offset must be >= 0.");
    }

    static Element elementFromBitmap(RenderScript object, Bitmap object2) {
        if ((object2 = object2.getConfig()) == Bitmap.Config.ALPHA_8) {
            return Element.A_8((RenderScript)object);
        }
        if (object2 == Bitmap.Config.ARGB_4444) {
            return Element.RGBA_4444((RenderScript)object);
        }
        if (object2 == Bitmap.Config.ARGB_8888) {
            return Element.RGBA_8888((RenderScript)object);
        }
        if (object2 == Bitmap.Config.RGB_565) {
            return Element.RGB_565((RenderScript)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Bad bitmap type: ");
        ((StringBuilder)object).append(object2);
        throw new RSInvalidStateException(((StringBuilder)object).toString());
    }

    private long getIDSafe() {
        Allocation allocation = this.mAdaptedAllocation;
        if (allocation != null) {
            return allocation.getID(this.mRS);
        }
        return this.getID(this.mRS);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void sendBufferNotification(long l) {
        HashMap<Long, Allocation> hashMap = mAllocationMap;
        synchronized (hashMap) {
            Object object = mAllocationMap;
            Object object2 = new Long(l);
            object2 = ((HashMap)object).get(object2);
            if (object2 != null && (object = ((Allocation)object2).mBufferNotifier) != null) {
                object.onBufferAvailable((Allocation)object2);
            }
            return;
        }
    }

    private void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    static Type typeFromBitmap(RenderScript object, Bitmap bitmap, MipmapControl mipmapControl) {
        object = new Type.Builder((RenderScript)object, Allocation.elementFromBitmap((RenderScript)object, bitmap));
        ((Type.Builder)object).setX(bitmap.getWidth());
        ((Type.Builder)object).setY(bitmap.getHeight());
        boolean bl = mipmapControl == MipmapControl.MIPMAP_FULL;
        ((Type.Builder)object).setMipmaps(bl);
        return ((Type.Builder)object).create();
    }

    private void updateCacheInfo(Type type) {
        this.mCurrentDimX = type.getX();
        this.mCurrentDimY = type.getY();
        this.mCurrentDimZ = type.getZ();
        this.mCurrentCount = this.mCurrentDimX;
        int n = this.mCurrentDimY;
        if (n > 1) {
            this.mCurrentCount *= n;
        }
        if ((n = this.mCurrentDimZ) > 1) {
            this.mCurrentCount *= n;
        }
    }

    private void validate2DRange(int n, int n2, int n3, int n4) {
        block3 : {
            block4 : {
                block5 : {
                    block2 : {
                        if (this.mAdaptedAllocation != null) break block2;
                        if (n < 0 || n2 < 0) break block3;
                        if (n4 < 0 || n3 < 0) break block4;
                        if (n + n3 > this.mCurrentDimX || n2 + n4 > this.mCurrentDimY) break block5;
                    }
                    return;
                }
                throw new RSIllegalArgumentException("Updated region larger than allocation.");
            }
            throw new RSIllegalArgumentException("Height or width cannot be negative.");
        }
        throw new RSIllegalArgumentException("Offset cannot be negative.");
    }

    private void validate3DRange(int n, int n2, int n3, int n4, int n5, int n6) {
        block3 : {
            block4 : {
                block5 : {
                    block2 : {
                        if (this.mAdaptedAllocation != null) break block2;
                        if (n < 0 || n2 < 0 || n3 < 0) break block3;
                        if (n5 < 0 || n4 < 0 || n6 < 0) break block4;
                        if (n + n4 > this.mCurrentDimX || n2 + n5 > this.mCurrentDimY || n3 + n6 > this.mCurrentDimZ) break block5;
                    }
                    return;
                }
                throw new RSIllegalArgumentException("Updated region larger than allocation.");
            }
            throw new RSIllegalArgumentException("Height or width cannot be negative.");
        }
        throw new RSIllegalArgumentException("Offset cannot be negative.");
    }

    private void validateBitmapFormat(Bitmap object) {
        block11 : {
            block14 : {
                block13 : {
                    block12 : {
                        if ((object = ((Bitmap)object).getConfig()) == null) break block11;
                        int n = 1.$SwitchMap$android$graphics$Bitmap$Config[((Enum)object).ordinal()];
                        if (n == 1) break block12;
                        if (n != 2) {
                            if (n != 3) {
                                if (n == 4 && (this.mType.getElement().mKind != Element.DataKind.PIXEL_RGBA || this.mType.getElement().getBytesSize() != 2)) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("Allocation kind is ");
                                    stringBuilder.append((Object)this.mType.getElement().mKind);
                                    stringBuilder.append(", type ");
                                    stringBuilder.append((Object)this.mType.getElement().mType);
                                    stringBuilder.append(" of ");
                                    stringBuilder.append(this.mType.getElement().getBytesSize());
                                    stringBuilder.append(" bytes, passed bitmap was ");
                                    stringBuilder.append(object);
                                    throw new RSIllegalArgumentException(stringBuilder.toString());
                                }
                            } else if (this.mType.getElement().mKind != Element.DataKind.PIXEL_RGB || this.mType.getElement().getBytesSize() != 2) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Allocation kind is ");
                                stringBuilder.append((Object)this.mType.getElement().mKind);
                                stringBuilder.append(", type ");
                                stringBuilder.append((Object)this.mType.getElement().mType);
                                stringBuilder.append(" of ");
                                stringBuilder.append(this.mType.getElement().getBytesSize());
                                stringBuilder.append(" bytes, passed bitmap was ");
                                stringBuilder.append(object);
                                throw new RSIllegalArgumentException(stringBuilder.toString());
                            }
                        } else if (this.mType.getElement().mKind != Element.DataKind.PIXEL_RGBA || this.mType.getElement().getBytesSize() != 4) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Allocation kind is ");
                            stringBuilder.append((Object)this.mType.getElement().mKind);
                            stringBuilder.append(", type ");
                            stringBuilder.append((Object)this.mType.getElement().mType);
                            stringBuilder.append(" of ");
                            stringBuilder.append(this.mType.getElement().getBytesSize());
                            stringBuilder.append(" bytes, passed bitmap was ");
                            stringBuilder.append(object);
                            throw new RSIllegalArgumentException(stringBuilder.toString());
                        }
                        break block13;
                    }
                    if (this.mType.getElement().mKind != Element.DataKind.PIXEL_A) break block14;
                }
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Allocation kind is ");
            stringBuilder.append((Object)this.mType.getElement().mKind);
            stringBuilder.append(", type ");
            stringBuilder.append((Object)this.mType.getElement().mType);
            stringBuilder.append(" of ");
            stringBuilder.append(this.mType.getElement().getBytesSize());
            stringBuilder.append(" bytes, passed bitmap was ");
            stringBuilder.append(object);
            throw new RSIllegalArgumentException(stringBuilder.toString());
        }
        throw new RSIllegalArgumentException("Bitmap has an unsupported format for this operation");
    }

    private void validateBitmapSize(Bitmap bitmap) {
        if (this.mCurrentDimX == bitmap.getWidth() && this.mCurrentDimY == bitmap.getHeight()) {
            return;
        }
        throw new RSIllegalArgumentException("Cannot update allocation from bitmap, sizes mismatch");
    }

    private void validateIsFloat32() {
        if (this.mType.mElement.mType == Element.DataType.FLOAT_32) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("32 bit float source does not match allocation type ");
        stringBuilder.append((Object)this.mType.mElement.mType);
        throw new RSIllegalArgumentException(stringBuilder.toString());
    }

    private void validateIsFloat64() {
        if (this.mType.mElement.mType == Element.DataType.FLOAT_64) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("64 bit float source does not match allocation type ");
        stringBuilder.append((Object)this.mType.mElement.mType);
        throw new RSIllegalArgumentException(stringBuilder.toString());
    }

    private void validateIsInt16OrFloat16() {
        if (this.mType.mElement.mType != Element.DataType.SIGNED_16 && this.mType.mElement.mType != Element.DataType.UNSIGNED_16 && this.mType.mElement.mType != Element.DataType.FLOAT_16) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("16 bit integer source does not match allocation type ");
            stringBuilder.append((Object)this.mType.mElement.mType);
            throw new RSIllegalArgumentException(stringBuilder.toString());
        }
    }

    private void validateIsInt32() {
        if (this.mType.mElement.mType != Element.DataType.SIGNED_32 && this.mType.mElement.mType != Element.DataType.UNSIGNED_32) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("32 bit integer source does not match allocation type ");
            stringBuilder.append((Object)this.mType.mElement.mType);
            throw new RSIllegalArgumentException(stringBuilder.toString());
        }
    }

    private void validateIsInt64() {
        if (this.mType.mElement.mType != Element.DataType.SIGNED_64 && this.mType.mElement.mType != Element.DataType.UNSIGNED_64) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("64 bit integer source does not match allocation type ");
            stringBuilder.append((Object)this.mType.mElement.mType);
            throw new RSIllegalArgumentException(stringBuilder.toString());
        }
    }

    private void validateIsInt8() {
        if (this.mType.mElement.mType != Element.DataType.SIGNED_8 && this.mType.mElement.mType != Element.DataType.UNSIGNED_8) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("8 bit integer source does not match allocation type ");
            stringBuilder.append((Object)this.mType.mElement.mType);
            throw new RSIllegalArgumentException(stringBuilder.toString());
        }
    }

    private void validateIsObject() {
        if (this.mType.mElement.mType != Element.DataType.RS_ELEMENT && this.mType.mElement.mType != Element.DataType.RS_TYPE && this.mType.mElement.mType != Element.DataType.RS_ALLOCATION && this.mType.mElement.mType != Element.DataType.RS_SAMPLER && this.mType.mElement.mType != Element.DataType.RS_SCRIPT && this.mType.mElement.mType != Element.DataType.RS_MESH && this.mType.mElement.mType != Element.DataType.RS_PROGRAM_FRAGMENT && this.mType.mElement.mType != Element.DataType.RS_PROGRAM_VERTEX && this.mType.mElement.mType != Element.DataType.RS_PROGRAM_RASTER && this.mType.mElement.mType != Element.DataType.RS_PROGRAM_STORE) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Object source does not match allocation type ");
            stringBuilder.append((Object)this.mType.mElement.mType);
            throw new RSIllegalArgumentException(stringBuilder.toString());
        }
    }

    private Element.DataType validateObjectIsPrimitiveArray(Object class_, boolean bl) {
        if ((class_ = class_.getClass()).isArray()) {
            if ((class_ = class_.getComponentType()).isPrimitive()) {
                if (class_ == Long.TYPE) {
                    if (bl) {
                        this.validateIsInt64();
                        return this.mType.mElement.mType;
                    }
                    return Element.DataType.SIGNED_64;
                }
                if (class_ == Integer.TYPE) {
                    if (bl) {
                        this.validateIsInt32();
                        return this.mType.mElement.mType;
                    }
                    return Element.DataType.SIGNED_32;
                }
                if (class_ == Short.TYPE) {
                    if (bl) {
                        this.validateIsInt16OrFloat16();
                        return this.mType.mElement.mType;
                    }
                    return Element.DataType.SIGNED_16;
                }
                if (class_ == Byte.TYPE) {
                    if (bl) {
                        this.validateIsInt8();
                        return this.mType.mElement.mType;
                    }
                    return Element.DataType.SIGNED_8;
                }
                if (class_ == Float.TYPE) {
                    if (bl) {
                        this.validateIsFloat32();
                    }
                    return Element.DataType.FLOAT_32;
                }
                if (class_ == Double.TYPE) {
                    if (bl) {
                        this.validateIsFloat64();
                    }
                    return Element.DataType.FLOAT_64;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Parameter of type ");
                stringBuilder.append(class_.getSimpleName());
                stringBuilder.append("[] is not compatible with data type ");
                stringBuilder.append(this.mType.mElement.mType.name());
                stringBuilder.append(" of allocation");
                throw new RSIllegalArgumentException(stringBuilder.toString());
            }
            throw new RSIllegalArgumentException("Object passed is not an Array of primitives.");
        }
        throw new RSIllegalArgumentException("Object passed is not an array of primitives.");
    }

    public void copy1DRangeFrom(int n, int n2, Allocation allocation, int n3) {
        Trace.traceBegin(32768L, "copy1DRangeFrom");
        this.mRS.nAllocationData2D(this.getIDSafe(), n, 0, this.mSelectedLOD, this.mSelectedFace.mID, n2, 1, allocation.getID(this.mRS), n3, 0, allocation.mSelectedLOD, allocation.mSelectedFace.mID);
        Trace.traceEnd(32768L);
    }

    public void copy1DRangeFrom(int n, int n2, Object object) {
        this.copy1DRangeFromUnchecked(n, n2, object, this.validateObjectIsPrimitiveArray(object, true), Array.getLength(object));
    }

    public void copy1DRangeFrom(int n, int n2, byte[] arrby) {
        this.validateIsInt8();
        this.copy1DRangeFromUnchecked(n, n2, arrby, Element.DataType.SIGNED_8, arrby.length);
    }

    public void copy1DRangeFrom(int n, int n2, float[] arrf) {
        this.validateIsFloat32();
        this.copy1DRangeFromUnchecked(n, n2, arrf, Element.DataType.FLOAT_32, arrf.length);
    }

    public void copy1DRangeFrom(int n, int n2, int[] arrn) {
        this.validateIsInt32();
        this.copy1DRangeFromUnchecked(n, n2, arrn, Element.DataType.SIGNED_32, arrn.length);
    }

    public void copy1DRangeFrom(int n, int n2, short[] arrs) {
        this.validateIsInt16OrFloat16();
        this.copy1DRangeFromUnchecked(n, n2, arrs, Element.DataType.SIGNED_16, arrs.length);
    }

    public void copy1DRangeFromUnchecked(int n, int n2, Object object) {
        this.copy1DRangeFromUnchecked(n, n2, object, this.validateObjectIsPrimitiveArray(object, false), Array.getLength(object));
    }

    public void copy1DRangeFromUnchecked(int n, int n2, byte[] arrby) {
        this.copy1DRangeFromUnchecked(n, n2, arrby, Element.DataType.SIGNED_8, arrby.length);
    }

    public void copy1DRangeFromUnchecked(int n, int n2, float[] arrf) {
        this.copy1DRangeFromUnchecked(n, n2, arrf, Element.DataType.FLOAT_32, arrf.length);
    }

    public void copy1DRangeFromUnchecked(int n, int n2, int[] arrn) {
        this.copy1DRangeFromUnchecked(n, n2, arrn, Element.DataType.SIGNED_32, arrn.length);
    }

    public void copy1DRangeFromUnchecked(int n, int n2, short[] arrs) {
        this.copy1DRangeFromUnchecked(n, n2, arrs, Element.DataType.SIGNED_16, arrs.length);
    }

    public void copy1DRangeTo(int n, int n2, Object object) {
        this.copy1DRangeToUnchecked(n, n2, object, this.validateObjectIsPrimitiveArray(object, true), Array.getLength(object));
    }

    public void copy1DRangeTo(int n, int n2, byte[] arrby) {
        this.validateIsInt8();
        this.copy1DRangeToUnchecked(n, n2, arrby, Element.DataType.SIGNED_8, arrby.length);
    }

    public void copy1DRangeTo(int n, int n2, float[] arrf) {
        this.validateIsFloat32();
        this.copy1DRangeToUnchecked(n, n2, arrf, Element.DataType.FLOAT_32, arrf.length);
    }

    public void copy1DRangeTo(int n, int n2, int[] arrn) {
        this.validateIsInt32();
        this.copy1DRangeToUnchecked(n, n2, arrn, Element.DataType.SIGNED_32, arrn.length);
    }

    public void copy1DRangeTo(int n, int n2, short[] arrs) {
        this.validateIsInt16OrFloat16();
        this.copy1DRangeToUnchecked(n, n2, arrs, Element.DataType.SIGNED_16, arrs.length);
    }

    public void copy1DRangeToUnchecked(int n, int n2, Object object) {
        this.copy1DRangeToUnchecked(n, n2, object, this.validateObjectIsPrimitiveArray(object, false), Array.getLength(object));
    }

    public void copy1DRangeToUnchecked(int n, int n2, byte[] arrby) {
        this.copy1DRangeToUnchecked(n, n2, arrby, Element.DataType.SIGNED_8, arrby.length);
    }

    public void copy1DRangeToUnchecked(int n, int n2, float[] arrf) {
        this.copy1DRangeToUnchecked(n, n2, arrf, Element.DataType.FLOAT_32, arrf.length);
    }

    public void copy1DRangeToUnchecked(int n, int n2, int[] arrn) {
        this.copy1DRangeToUnchecked(n, n2, arrn, Element.DataType.SIGNED_32, arrn.length);
    }

    public void copy1DRangeToUnchecked(int n, int n2, short[] arrs) {
        this.copy1DRangeToUnchecked(n, n2, arrs, Element.DataType.SIGNED_16, arrs.length);
    }

    public void copy2DRangeFrom(int n, int n2, int n3, int n4, Allocation allocation, int n5, int n6) {
        try {
            Trace.traceBegin(32768L, "copy2DRangeFrom");
            this.mRS.validate();
            this.validate2DRange(n, n2, n3, n4);
            this.mRS.nAllocationData2D(this.getIDSafe(), n, n2, this.mSelectedLOD, this.mSelectedFace.mID, n3, n4, allocation.getID(this.mRS), n5, n6, allocation.mSelectedLOD, allocation.mSelectedFace.mID);
            return;
        }
        finally {
            Trace.traceEnd(32768L);
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void copy2DRangeFrom(int n, int n2, int n3, int n4, Object object) {
        void var5_8;
        block4 : {
            Trace.traceBegin(32768L, "copy2DRangeFrom");
            try {
                this.copy2DRangeFromUnchecked(n, n2, n3, n4, object, this.validateObjectIsPrimitiveArray(object, true), Array.getLength(object));
            }
            catch (Throwable throwable) {
                break block4;
            }
            Trace.traceEnd(32768L);
            return;
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        Trace.traceEnd(32768L);
        throw var5_8;
    }

    public void copy2DRangeFrom(int n, int n2, int n3, int n4, byte[] arrby) {
        this.validateIsInt8();
        this.copy2DRangeFromUnchecked(n, n2, n3, n4, arrby, Element.DataType.SIGNED_8, arrby.length);
    }

    public void copy2DRangeFrom(int n, int n2, int n3, int n4, float[] arrf) {
        this.validateIsFloat32();
        this.copy2DRangeFromUnchecked(n, n2, n3, n4, arrf, Element.DataType.FLOAT_32, arrf.length);
    }

    public void copy2DRangeFrom(int n, int n2, int n3, int n4, int[] arrn) {
        this.validateIsInt32();
        this.copy2DRangeFromUnchecked(n, n2, n3, n4, arrn, Element.DataType.SIGNED_32, arrn.length);
    }

    public void copy2DRangeFrom(int n, int n2, int n3, int n4, short[] arrs) {
        this.validateIsInt16OrFloat16();
        this.copy2DRangeFromUnchecked(n, n2, n3, n4, arrs, Element.DataType.SIGNED_16, arrs.length);
    }

    public void copy2DRangeFrom(int n, int n2, Bitmap bitmap) {
        try {
            Trace.traceBegin(32768L, "copy2DRangeFrom");
            this.mRS.validate();
            if (bitmap.getConfig() == null) {
                Bitmap bitmap2 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap2);
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
                this.copy2DRangeFrom(n, n2, bitmap2);
                return;
            }
            this.validateBitmapFormat(bitmap);
            this.validate2DRange(n, n2, bitmap.getWidth(), bitmap.getHeight());
            this.mRS.nAllocationData2D(this.getIDSafe(), n, n2, this.mSelectedLOD, this.mSelectedFace.mID, bitmap);
            return;
        }
        finally {
            Trace.traceEnd(32768L);
        }
    }

    void copy2DRangeFromUnchecked(int n, int n2, int n3, int n4, Object object, Element.DataType dataType, int n5) {
        int n6;
        boolean bl;
        block8 : {
            block9 : {
                Trace.traceBegin(32768L, "copy2DRangeFromUnchecked");
                this.mRS.validate();
                this.validate2DRange(n, n2, n3, n4);
                n6 = this.mType.mElement.getBytesSize() * n3 * n4;
                n5 = dataType.mSize * n5;
                bl = this.mAutoPadding;
                if (!bl) break block8;
                if (this.mType.getElement().getVectorSize() != 3) break block8;
                if (n6 / 4 * 3 > n5) break block9;
                bl = true;
                n5 = n6;
            }
            object = new RSIllegalArgumentException("Array too small for allocation type.");
            throw object;
        }
        if (n6 <= n5) {
            bl = false;
            this.mRS.nAllocationData2D(this.getIDSafe(), n, n2, this.mSelectedLOD, this.mSelectedFace.mID, n3, n4, object, n5, dataType, this.mType.mElement.mType.mSize, bl);
            return;
        }
        try {
            object = new RSIllegalArgumentException("Array too small for allocation type.");
            throw object;
        }
        finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copy2DRangeTo(int n, int n2, int n3, int n4, Object object) {
        this.copy2DRangeToUnchecked(n, n2, n3, n4, object, this.validateObjectIsPrimitiveArray(object, true), Array.getLength(object));
    }

    public void copy2DRangeTo(int n, int n2, int n3, int n4, byte[] arrby) {
        this.validateIsInt8();
        this.copy2DRangeToUnchecked(n, n2, n3, n4, arrby, Element.DataType.SIGNED_8, arrby.length);
    }

    public void copy2DRangeTo(int n, int n2, int n3, int n4, float[] arrf) {
        this.validateIsFloat32();
        this.copy2DRangeToUnchecked(n, n2, n3, n4, arrf, Element.DataType.FLOAT_32, arrf.length);
    }

    public void copy2DRangeTo(int n, int n2, int n3, int n4, int[] arrn) {
        this.validateIsInt32();
        this.copy2DRangeToUnchecked(n, n2, n3, n4, arrn, Element.DataType.SIGNED_32, arrn.length);
    }

    public void copy2DRangeTo(int n, int n2, int n3, int n4, short[] arrs) {
        this.validateIsInt16OrFloat16();
        this.copy2DRangeToUnchecked(n, n2, n3, n4, arrs, Element.DataType.SIGNED_16, arrs.length);
    }

    void copy2DRangeToUnchecked(int n, int n2, int n3, int n4, Object object, Element.DataType dataType, int n5) {
        int n6;
        boolean bl;
        block8 : {
            block9 : {
                Trace.traceBegin(32768L, "copy2DRangeToUnchecked");
                this.mRS.validate();
                this.validate2DRange(n, n2, n3, n4);
                n6 = this.mType.mElement.getBytesSize() * n3 * n4;
                n5 = dataType.mSize * n5;
                bl = this.mAutoPadding;
                if (!bl) break block8;
                if (this.mType.getElement().getVectorSize() != 3) break block8;
                if (n6 / 4 * 3 > n5) break block9;
                bl = true;
                n5 = n6;
            }
            object = new RSIllegalArgumentException("Array too small for allocation type.");
            throw object;
        }
        if (n6 <= n5) {
            bl = false;
            this.mRS.nAllocationRead2D(this.getIDSafe(), n, n2, this.mSelectedLOD, this.mSelectedFace.mID, n3, n4, object, n5, dataType, this.mType.mElement.mType.mSize, bl);
            return;
        }
        try {
            object = new RSIllegalArgumentException("Array too small for allocation type.");
            throw object;
        }
        finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copy3DRangeFrom(int n, int n2, int n3, int n4, int n5, int n6, Allocation allocation, int n7, int n8, int n9) {
        this.mRS.validate();
        this.validate3DRange(n, n2, n3, n4, n5, n6);
        this.mRS.nAllocationData3D(this.getIDSafe(), n, n2, n3, this.mSelectedLOD, n4, n5, n6, allocation.getID(this.mRS), n7, n8, n9, allocation.mSelectedLOD);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void copy3DRangeFrom(int n, int n2, int n3, int n4, int n5, int n6, Object object) {
        void var7_10;
        block4 : {
            Trace.traceBegin(32768L, "copy3DRangeFrom");
            try {
                this.copy3DRangeFromUnchecked(n, n2, n3, n4, n5, n6, object, this.validateObjectIsPrimitiveArray(object, true), Array.getLength(object));
            }
            catch (Throwable throwable) {
                break block4;
            }
            Trace.traceEnd(32768L);
            return;
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        Trace.traceEnd(32768L);
        throw var7_10;
    }

    public void copy3DRangeTo(int n, int n2, int n3, int n4, int n5, int n6, Object object) {
        this.copy3DRangeToUnchecked(n, n2, n3, n4, n5, n6, object, this.validateObjectIsPrimitiveArray(object, true), Array.getLength(object));
    }

    public void copyFrom(Bitmap bitmap) {
        try {
            Trace.traceBegin(32768L, "copyFrom");
            this.mRS.validate();
            if (bitmap.getConfig() == null) {
                Bitmap bitmap2 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap2);
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
                this.copyFrom(bitmap2);
                return;
            }
            this.validateBitmapSize(bitmap);
            this.validateBitmapFormat(bitmap);
            this.mRS.nAllocationCopyFromBitmap(this.getID(this.mRS), bitmap);
            return;
        }
        finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copyFrom(Allocation object) {
        try {
            Trace.traceBegin(32768L, "copyFrom");
            this.mRS.validate();
            if (this.mType.equals(((Allocation)object).getType())) {
                this.copy2DRangeFrom(0, 0, this.mCurrentDimX, this.mCurrentDimY, (Allocation)object, 0, 0);
                return;
            }
            object = new RSIllegalArgumentException("Types of allocations must match.");
            throw object;
        }
        finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copyFrom(Object object) {
        try {
            Trace.traceBegin(32768L, "copyFrom");
            this.copyFromUnchecked(object, this.validateObjectIsPrimitiveArray(object, true), Array.getLength(object));
            return;
        }
        finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copyFrom(byte[] arrby) {
        this.validateIsInt8();
        this.copyFromUnchecked(arrby, Element.DataType.SIGNED_8, arrby.length);
    }

    public void copyFrom(float[] arrf) {
        this.validateIsFloat32();
        this.copyFromUnchecked(arrf, Element.DataType.FLOAT_32, arrf.length);
    }

    public void copyFrom(int[] arrn) {
        this.validateIsInt32();
        this.copyFromUnchecked(arrn, Element.DataType.SIGNED_32, arrn.length);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void copyFrom(BaseObj[] arrbaseObj) {
        int[] arrn;
        block11 : {
            long[] arrl;
            block10 : {
                block8 : {
                    block9 : {
                        Trace.traceBegin(32768L, "copyFrom");
                        this.mRS.validate();
                        this.validateIsObject();
                        if (arrbaseObj.length != this.mCurrentCount) break block8;
                        if (RenderScript.sPointerSize != 8) break block9;
                        arrl = new long[arrbaseObj.length * 4];
                        break block10;
                    }
                    arrn = new int[arrbaseObj.length];
                    break block11;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Array size mismatch, allocation sizeX = ");
                stringBuilder.append(this.mCurrentCount);
                stringBuilder.append(", array length = ");
                stringBuilder.append(arrbaseObj.length);
                RSIllegalArgumentException rSIllegalArgumentException = new RSIllegalArgumentException(stringBuilder.toString());
                throw rSIllegalArgumentException;
            }
            for (int i = 0; i < arrbaseObj.length; ++i) {
                arrl[i * 4] = arrbaseObj[i].getID(this.mRS);
            }
            this.copy1DRangeFromUnchecked(0, this.mCurrentCount, (Object)arrl);
            return;
        }
        for (int i = 0; i < arrbaseObj.length; ++i) {
            arrn[i] = (int)arrbaseObj[i].getID(this.mRS);
        }
        try {
            this.copy1DRangeFromUnchecked(0, this.mCurrentCount, arrn);
            return;
        }
        finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copyFrom(short[] arrs) {
        this.validateIsInt16OrFloat16();
        this.copyFromUnchecked(arrs, Element.DataType.SIGNED_16, arrs.length);
    }

    public void copyFromUnchecked(Object object) {
        try {
            Trace.traceBegin(32768L, "copyFromUnchecked");
            this.copyFromUnchecked(object, this.validateObjectIsPrimitiveArray(object, false), Array.getLength(object));
            return;
        }
        finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copyFromUnchecked(byte[] arrby) {
        this.copyFromUnchecked(arrby, Element.DataType.SIGNED_8, arrby.length);
    }

    public void copyFromUnchecked(float[] arrf) {
        this.copyFromUnchecked(arrf, Element.DataType.FLOAT_32, arrf.length);
    }

    public void copyFromUnchecked(int[] arrn) {
        this.copyFromUnchecked(arrn, Element.DataType.SIGNED_32, arrn.length);
    }

    public void copyFromUnchecked(short[] arrs) {
        this.copyFromUnchecked(arrs, Element.DataType.SIGNED_16, arrs.length);
    }

    public void copyTo(Bitmap bitmap) {
        try {
            Trace.traceBegin(32768L, "copyTo");
            this.mRS.validate();
            this.validateBitmapFormat(bitmap);
            this.validateBitmapSize(bitmap);
            this.mRS.nAllocationCopyToBitmap(this.getID(this.mRS), bitmap);
            return;
        }
        finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copyTo(Object object) {
        this.copyTo(object, this.validateObjectIsPrimitiveArray(object, true), Array.getLength(object));
    }

    public void copyTo(byte[] arrby) {
        this.validateIsInt8();
        this.copyTo(arrby, Element.DataType.SIGNED_8, arrby.length);
    }

    public void copyTo(float[] arrf) {
        this.validateIsFloat32();
        this.copyTo(arrf, Element.DataType.FLOAT_32, arrf.length);
    }

    public void copyTo(int[] arrn) {
        this.validateIsInt32();
        this.copyTo(arrn, Element.DataType.SIGNED_32, arrn.length);
    }

    public void copyTo(short[] arrs) {
        this.validateIsInt16OrFloat16();
        this.copyTo(arrs, Element.DataType.SIGNED_16, arrs.length);
    }

    public void copyToFieldPacker(int n, int n2, int n3, int n4, FieldPacker object) {
        this.mRS.validate();
        if (n4 < this.mType.mElement.mElements.length) {
            if (n >= 0) {
                if (n2 >= 0) {
                    if (n3 >= 0) {
                        int n5;
                        int n6 = ((byte[])(object = ((FieldPacker)object).getData())).length;
                        if (n6 == (n5 = this.mType.mElement.mElements[n4].getBytesSize() * this.mType.mElement.mArraySizes[n4])) {
                            this.mRS.nAllocationElementRead(this.getIDSafe(), n, n2, n3, this.mSelectedLOD, n4, (byte[])object, n6);
                            return;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Field packer sizelength ");
                        ((StringBuilder)object).append(n6);
                        ((StringBuilder)object).append(" does not match component size ");
                        ((StringBuilder)object).append(n5);
                        ((StringBuilder)object).append(".");
                        throw new RSIllegalArgumentException(((StringBuilder)object).toString());
                    }
                    throw new RSIllegalArgumentException("Offset z must be >= 0.");
                }
                throw new RSIllegalArgumentException("Offset y must be >= 0.");
            }
            throw new RSIllegalArgumentException("Offset x must be >= 0.");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Component_number ");
        ((StringBuilder)object).append(n4);
        ((StringBuilder)object).append(" out of range.");
        throw new RSIllegalArgumentException(((StringBuilder)object).toString());
    }

    @Override
    public void destroy() {
        Type type;
        if ((this.mUsage & 64) != 0) {
            this.setSurface(null);
        }
        if ((type = this.mType) != null && this.mOwningType) {
            type.destroy();
        }
        super.destroy();
    }

    @Override
    protected void finalize() throws Throwable {
        RenderScript.registerNativeFree.invoke(RenderScript.sRuntime, this.mSize);
        super.finalize();
    }

    public void generateMipmaps() {
        this.mRS.nAllocationGenerateMipmaps(this.getID(this.mRS));
    }

    public ByteBuffer getByteBuffer() {
        if (!this.mType.hasFaces()) {
            if (this.mType.getYuv() != 17 && this.mType.getYuv() != 842094169 && this.mType.getYuv() != 35) {
                if (this.mByteBuffer == null || (this.mUsage & 32) != 0) {
                    int n = this.mType.getX();
                    int n2 = this.mType.getElement().getBytesSize();
                    long[] arrl = new long[1];
                    this.mByteBuffer = this.mRS.nAllocationGetByteBuffer(this.getID(this.mRS), arrl, n * n2, this.mType.getY(), this.mType.getZ());
                    this.mByteBufferStride = arrl[0];
                }
                if ((this.mUsage & 32) != 0) {
                    return this.mByteBuffer.asReadOnlyBuffer();
                }
                return this.mByteBuffer;
            }
            throw new RSInvalidStateException("YUV format is not supported for getByteBuffer().");
        }
        throw new RSInvalidStateException("Cubemap is not supported for getByteBuffer().");
    }

    public int getBytesSize() {
        if (this.mType.mDimYuv != 0) {
            return (int)Math.ceil((double)(this.mType.getCount() * this.mType.getElement().getBytesSize()) * 1.5);
        }
        return this.mType.getCount() * this.mType.getElement().getBytesSize();
    }

    public Element getElement() {
        return this.mType.getElement();
    }

    public MipmapControl getMipmap() {
        return this.mMipmapControl;
    }

    public long getStride() {
        if (this.mByteBufferStride == -1L) {
            this.getByteBuffer();
        }
        return this.mByteBufferStride;
    }

    public Surface getSurface() {
        if ((this.mUsage & 32) != 0) {
            if (this.mGetSurfaceSurface == null) {
                this.mGetSurfaceSurface = this.mRS.nAllocationGetSurface(this.getID(this.mRS));
            }
            return this.mGetSurfaceSurface;
        }
        throw new RSInvalidStateException("Allocation is not a surface texture.");
    }

    public long getTimeStamp() {
        return this.mTimeStamp;
    }

    public Type getType() {
        return this.mType;
    }

    public int getUsage() {
        return this.mUsage;
    }

    public void ioReceive() {
        try {
            Trace.traceBegin(32768L, "ioReceive");
            if ((this.mUsage & 32) != 0) {
                this.mRS.validate();
                this.mTimeStamp = this.mRS.nAllocationIoReceive(this.getID(this.mRS));
                return;
            }
            RSIllegalArgumentException rSIllegalArgumentException = new RSIllegalArgumentException("Can only receive if IO_INPUT usage specified.");
            throw rSIllegalArgumentException;
        }
        finally {
            Trace.traceEnd(32768L);
        }
    }

    public void ioSend() {
        try {
            Trace.traceBegin(32768L, "ioSend");
            if ((this.mUsage & 64) != 0) {
                this.mRS.validate();
                this.mRS.nAllocationIoSend(this.getID(this.mRS));
                return;
            }
            RSIllegalArgumentException rSIllegalArgumentException = new RSIllegalArgumentException("Can only send buffer if IO_OUTPUT usage specified.");
            throw rSIllegalArgumentException;
        }
        finally {
            Trace.traceEnd(32768L);
        }
    }

    public void resize(int n) {
        synchronized (this) {
            if (this.mRS.getApplicationContext().getApplicationInfo().targetSdkVersion < 21) {
                if (this.mType.getY() <= 0 && this.mType.getZ() <= 0 && !this.mType.hasFaces() && !this.mType.hasMipmaps()) {
                    Type type;
                    this.mRS.nAllocationResize1D(this.getID(this.mRS), n);
                    this.mRS.finish();
                    long l = this.mRS.nAllocationGetType(this.getID(this.mRS));
                    this.mType.setID(0L);
                    this.mType = type = new Type(l, this.mRS);
                    this.mType.updateFromNative();
                    this.updateCacheInfo(this.mType);
                    return;
                }
                RSInvalidStateException rSInvalidStateException = new RSInvalidStateException("Resize only support for 1D allocations at this time.");
                throw rSInvalidStateException;
            }
            RSRuntimeException rSRuntimeException = new RSRuntimeException("Resize is not allowed in API 21+.");
            throw rSRuntimeException;
        }
    }

    public void setAutoPadding(boolean bl) {
        this.mAutoPadding = bl;
    }

    public void setFromFieldPacker(int n, int n2, int n3, int n4, FieldPacker object) {
        this.mRS.validate();
        if (n4 < this.mType.mElement.mElements.length) {
            if (n >= 0) {
                if (n2 >= 0) {
                    if (n3 >= 0) {
                        int n5;
                        byte[] arrby = ((FieldPacker)object).getData();
                        int n6 = ((FieldPacker)object).getPos();
                        if (n6 == (n5 = this.mType.mElement.mElements[n4].getBytesSize() * this.mType.mElement.mArraySizes[n4])) {
                            this.mRS.nAllocationElementData(this.getIDSafe(), n, n2, n3, this.mSelectedLOD, n4, arrby, n6);
                            return;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Field packer sizelength ");
                        ((StringBuilder)object).append(n6);
                        ((StringBuilder)object).append(" does not match component size ");
                        ((StringBuilder)object).append(n5);
                        ((StringBuilder)object).append(".");
                        throw new RSIllegalArgumentException(((StringBuilder)object).toString());
                    }
                    throw new RSIllegalArgumentException("Offset z must be >= 0.");
                }
                throw new RSIllegalArgumentException("Offset y must be >= 0.");
            }
            throw new RSIllegalArgumentException("Offset x must be >= 0.");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Component_number ");
        ((StringBuilder)object).append(n4);
        ((StringBuilder)object).append(" out of range.");
        throw new RSIllegalArgumentException(((StringBuilder)object).toString());
    }

    public void setFromFieldPacker(int n, int n2, FieldPacker fieldPacker) {
        this.setFromFieldPacker(n, 0, 0, n2, fieldPacker);
    }

    public void setFromFieldPacker(int n, FieldPacker object) {
        this.mRS.validate();
        int n2 = this.mType.mElement.getBytesSize();
        byte[] arrby = ((FieldPacker)object).getData();
        int n3 = ((FieldPacker)object).getPos();
        int n4 = n3 / n2;
        if (n2 * n4 == n3) {
            this.copy1DRangeFromUnchecked(n, n4, arrby);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Field packer length ");
        ((StringBuilder)object).append(n3);
        ((StringBuilder)object).append(" not divisible by element size ");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append(".");
        throw new RSIllegalArgumentException(((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOnBufferAvailableListener(OnBufferAvailableListener onBufferAvailableListener) {
        HashMap<Long, Allocation> hashMap = mAllocationMap;
        synchronized (hashMap) {
            HashMap<Long, Allocation> hashMap2 = mAllocationMap;
            Long l = new Long(this.getID(this.mRS));
            hashMap2.put(l, this);
            this.mBufferNotifier = onBufferAvailableListener;
            return;
        }
    }

    public void setSurface(Surface surface) {
        this.mRS.validate();
        if ((this.mUsage & 64) != 0) {
            this.mRS.nAllocationSetSurface(this.getID(this.mRS), surface);
            return;
        }
        throw new RSInvalidStateException("Allocation is not USAGE_IO_OUTPUT.");
    }

    void setupBufferQueue(int n) {
        this.mRS.validate();
        if ((this.mUsage & 32) != 0) {
            this.mRS.nAllocationSetupBufferQueue(this.getID(this.mRS), n);
            return;
        }
        throw new RSInvalidStateException("Allocation is not USAGE_IO_INPUT.");
    }

    void shareBufferQueue(Allocation allocation) {
        this.mRS.validate();
        if ((this.mUsage & 32) != 0) {
            this.mGetSurfaceSurface = allocation.getSurface();
            this.mRS.nAllocationShareBufferQueue(this.getID(this.mRS), allocation.getID(this.mRS));
            return;
        }
        throw new RSInvalidStateException("Allocation is not USAGE_IO_INPUT.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void syncAll(int n) {
        try {
            Trace.traceBegin(32768L, "syncAll");
            if (n != 1 && n != 2) {
                if (n != 4 && n != 8) {
                    if (n != 128) {
                        RSIllegalArgumentException rSIllegalArgumentException = new RSIllegalArgumentException("Source must be exactly one usage type.");
                        throw rSIllegalArgumentException;
                    }
                    if ((this.mUsage & 128) != 0) {
                        this.copyTo(this.mBitmap);
                    }
                }
            } else if ((this.mUsage & 128) != 0) {
                this.copyFrom(this.mBitmap);
            }
            this.mRS.validate();
            this.mRS.nAllocationSyncAll(this.getIDSafe(), n);
            return;
        }
        finally {
            Trace.traceEnd(32768L);
        }
    }

    @Override
    void updateFromNative() {
        super.updateFromNative();
        long l = this.mRS.nAllocationGetType(this.getID(this.mRS));
        if (l != 0L) {
            this.mType = new Type(l, this.mRS);
            this.mType.updateFromNative();
            this.updateCacheInfo(this.mType);
        }
    }

    public static enum MipmapControl {
        MIPMAP_NONE(0),
        MIPMAP_FULL(1),
        MIPMAP_ON_SYNC_TO_TEXTURE(2);
        
        int mID;

        private MipmapControl(int n2) {
            this.mID = n2;
        }
    }

    public static interface OnBufferAvailableListener {
        public void onBufferAvailable(Allocation var1);
    }

}

