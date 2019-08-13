/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.CriticalNative
 *  dalvik.annotation.optimization.FastNative
 *  libcore.util.NativeAllocationRegistry
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.Region;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import libcore.util.NativeAllocationRegistry;

public class Path {
    static final FillType[] sFillTypeArray;
    private static final NativeAllocationRegistry sRegistry;
    @UnsupportedAppUsage
    public boolean isSimplePath = true;
    private Direction mLastDirection = null;
    public final long mNativePath;
    @UnsupportedAppUsage
    public Region rects;

    static {
        sRegistry = NativeAllocationRegistry.createMalloced((ClassLoader)Path.class.getClassLoader(), (long)Path.nGetFinalizer());
        sFillTypeArray = new FillType[]{FillType.WINDING, FillType.EVEN_ODD, FillType.INVERSE_WINDING, FillType.INVERSE_EVEN_ODD};
    }

    public Path() {
        this.mNativePath = Path.nInit();
        sRegistry.registerNativeAllocation((Object)this, this.mNativePath);
    }

    public Path(Path object) {
        long l = 0L;
        if (object != null) {
            long l2 = ((Path)object).mNativePath;
            this.isSimplePath = ((Path)object).isSimplePath;
            object = ((Path)object).rects;
            l = l2;
            if (object != null) {
                this.rects = new Region((Region)object);
                l = l2;
            }
        }
        this.mNativePath = Path.nInit(l);
        sRegistry.registerNativeAllocation((Object)this, this.mNativePath);
    }

    private void detectSimplePath(float f, float f2, float f3, float f4, Direction direction) {
        if (this.mLastDirection == null) {
            this.mLastDirection = direction;
        }
        if (this.mLastDirection != direction) {
            this.isSimplePath = false;
        } else {
            if (this.rects == null) {
                this.rects = new Region();
            }
            this.rects.op((int)f, (int)f2, (int)f3, (int)f4, Region.Op.UNION);
        }
    }

    private static native void nAddArc(long var0, float var2, float var3, float var4, float var5, float var6, float var7);

    private static native void nAddCircle(long var0, float var2, float var3, float var4, int var5);

    private static native void nAddOval(long var0, float var2, float var3, float var4, float var5, int var6);

    private static native void nAddPath(long var0, long var2);

    private static native void nAddPath(long var0, long var2, float var4, float var5);

    private static native void nAddPath(long var0, long var2, long var4);

    private static native void nAddRect(long var0, float var2, float var3, float var4, float var5, int var6);

    private static native void nAddRoundRect(long var0, float var2, float var3, float var4, float var5, float var6, float var7, int var8);

    private static native void nAddRoundRect(long var0, float var2, float var3, float var4, float var5, float[] var6, int var7);

    private static native float[] nApproximate(long var0, float var2);

    private static native void nArcTo(long var0, float var2, float var3, float var4, float var5, float var6, float var7, boolean var8);

    private static native void nClose(long var0);

    private static native void nComputeBounds(long var0, RectF var2);

    private static native void nCubicTo(long var0, float var2, float var3, float var4, float var5, float var6, float var7);

    @CriticalNative
    private static native int nGetFillType(long var0);

    private static native long nGetFinalizer();

    private static native void nIncReserve(long var0, int var2);

    private static native long nInit();

    private static native long nInit(long var0);

    @CriticalNative
    private static native boolean nIsConvex(long var0);

    @CriticalNative
    private static native boolean nIsEmpty(long var0);

    @FastNative
    private static native boolean nIsRect(long var0, RectF var2);

    private static native void nLineTo(long var0, float var2, float var3);

    private static native void nMoveTo(long var0, float var2, float var3);

    private static native void nOffset(long var0, float var2, float var3);

    private static native boolean nOp(long var0, long var2, int var4, long var5);

    private static native void nQuadTo(long var0, float var2, float var3, float var4, float var5);

    private static native void nRCubicTo(long var0, float var2, float var3, float var4, float var5, float var6, float var7);

    private static native void nRLineTo(long var0, float var2, float var3);

    private static native void nRMoveTo(long var0, float var2, float var3);

    private static native void nRQuadTo(long var0, float var2, float var3, float var4, float var5);

    @CriticalNative
    private static native void nReset(long var0);

    @CriticalNative
    private static native void nRewind(long var0);

    private static native void nSet(long var0, long var2);

    @CriticalNative
    private static native void nSetFillType(long var0, int var2);

    private static native void nSetLastPoint(long var0, float var2, float var3);

    private static native void nTransform(long var0, long var2);

    private static native void nTransform(long var0, long var2, long var4);

    public void addArc(float f, float f2, float f3, float f4, float f5, float f6) {
        this.isSimplePath = false;
        Path.nAddArc(this.mNativePath, f, f2, f3, f4, f5, f6);
    }

    public void addArc(RectF rectF, float f, float f2) {
        this.addArc(rectF.left, rectF.top, rectF.right, rectF.bottom, f, f2);
    }

    public void addCircle(float f, float f2, float f3, Direction direction) {
        this.isSimplePath = false;
        Path.nAddCircle(this.mNativePath, f, f2, f3, direction.nativeInt);
    }

    public void addOval(float f, float f2, float f3, float f4, Direction direction) {
        this.isSimplePath = false;
        Path.nAddOval(this.mNativePath, f, f2, f3, f4, direction.nativeInt);
    }

    public void addOval(RectF rectF, Direction direction) {
        this.addOval(rectF.left, rectF.top, rectF.right, rectF.bottom, direction);
    }

    public void addPath(Path path) {
        this.isSimplePath = false;
        Path.nAddPath(this.mNativePath, path.mNativePath);
    }

    public void addPath(Path path, float f, float f2) {
        this.isSimplePath = false;
        Path.nAddPath(this.mNativePath, path.mNativePath, f, f2);
    }

    public void addPath(Path path, Matrix matrix) {
        if (!path.isSimplePath) {
            this.isSimplePath = false;
        }
        Path.nAddPath(this.mNativePath, path.mNativePath, matrix.native_instance);
    }

    public void addRect(float f, float f2, float f3, float f4, Direction direction) {
        this.detectSimplePath(f, f2, f3, f4, direction);
        Path.nAddRect(this.mNativePath, f, f2, f3, f4, direction.nativeInt);
    }

    public void addRect(RectF rectF, Direction direction) {
        this.addRect(rectF.left, rectF.top, rectF.right, rectF.bottom, direction);
    }

    public void addRoundRect(float f, float f2, float f3, float f4, float f5, float f6, Direction direction) {
        this.isSimplePath = false;
        Path.nAddRoundRect(this.mNativePath, f, f2, f3, f4, f5, f6, direction.nativeInt);
    }

    public void addRoundRect(float f, float f2, float f3, float f4, float[] arrf, Direction direction) {
        if (arrf.length >= 8) {
            this.isSimplePath = false;
            Path.nAddRoundRect(this.mNativePath, f, f2, f3, f4, arrf, direction.nativeInt);
            return;
        }
        throw new ArrayIndexOutOfBoundsException("radii[] needs 8 values");
    }

    public void addRoundRect(RectF rectF, float f, float f2, Direction direction) {
        this.addRoundRect(rectF.left, rectF.top, rectF.right, rectF.bottom, f, f2, direction);
    }

    public void addRoundRect(RectF rectF, float[] arrf, Direction direction) {
        if (rectF != null) {
            this.addRoundRect(rectF.left, rectF.top, rectF.right, rectF.bottom, arrf, direction);
            return;
        }
        throw new NullPointerException("need rect parameter");
    }

    public float[] approximate(float f) {
        if (!(f < 0.0f)) {
            return Path.nApproximate(this.mNativePath, f);
        }
        throw new IllegalArgumentException("AcceptableError must be greater than or equal to 0");
    }

    public void arcTo(float f, float f2, float f3, float f4, float f5, float f6, boolean bl) {
        this.isSimplePath = false;
        Path.nArcTo(this.mNativePath, f, f2, f3, f4, f5, f6, bl);
    }

    public void arcTo(RectF rectF, float f, float f2) {
        this.arcTo(rectF.left, rectF.top, rectF.right, rectF.bottom, f, f2, false);
    }

    public void arcTo(RectF rectF, float f, float f2, boolean bl) {
        this.arcTo(rectF.left, rectF.top, rectF.right, rectF.bottom, f, f2, bl);
    }

    public void close() {
        this.isSimplePath = false;
        Path.nClose(this.mNativePath);
    }

    public void computeBounds(RectF rectF, boolean bl) {
        Path.nComputeBounds(this.mNativePath, rectF);
    }

    public void cubicTo(float f, float f2, float f3, float f4, float f5, float f6) {
        this.isSimplePath = false;
        Path.nCubicTo(this.mNativePath, f, f2, f3, f4, f5, f6);
    }

    public FillType getFillType() {
        return sFillTypeArray[Path.nGetFillType(this.mNativePath)];
    }

    public void incReserve(int n) {
        Path.nIncReserve(this.mNativePath, n);
    }

    public boolean isConvex() {
        return Path.nIsConvex(this.mNativePath);
    }

    public boolean isEmpty() {
        return Path.nIsEmpty(this.mNativePath);
    }

    public boolean isInverseFillType() {
        int n = Path.nGetFillType(this.mNativePath);
        boolean bl = (FillType.INVERSE_WINDING.nativeInt & n) != 0;
        return bl;
    }

    public boolean isRect(RectF rectF) {
        return Path.nIsRect(this.mNativePath, rectF);
    }

    public void lineTo(float f, float f2) {
        this.isSimplePath = false;
        Path.nLineTo(this.mNativePath, f, f2);
    }

    public void moveTo(float f, float f2) {
        Path.nMoveTo(this.mNativePath, f, f2);
    }

    final long mutateNI() {
        this.isSimplePath = false;
        return this.mNativePath;
    }

    public void offset(float f, float f2) {
        if (this.isSimplePath && this.rects == null) {
            return;
        }
        if (this.isSimplePath && (double)f == Math.rint(f) && (double)f2 == Math.rint(f2)) {
            this.rects.translate((int)f, (int)f2);
        } else {
            this.isSimplePath = false;
        }
        Path.nOffset(this.mNativePath, f, f2);
    }

    public void offset(float f, float f2, Path path) {
        if (path != null) {
            path.set(this);
        } else {
            path = this;
        }
        path.offset(f, f2);
    }

    public boolean op(Path path, Op op) {
        return this.op(this, path, op);
    }

    public boolean op(Path path, Path path2, Op op) {
        if (Path.nOp(path.mNativePath, path2.mNativePath, op.ordinal(), this.mNativePath)) {
            this.isSimplePath = false;
            this.rects = null;
            return true;
        }
        return false;
    }

    public void quadTo(float f, float f2, float f3, float f4) {
        this.isSimplePath = false;
        Path.nQuadTo(this.mNativePath, f, f2, f3, f4);
    }

    public void rCubicTo(float f, float f2, float f3, float f4, float f5, float f6) {
        this.isSimplePath = false;
        Path.nRCubicTo(this.mNativePath, f, f2, f3, f4, f5, f6);
    }

    public void rLineTo(float f, float f2) {
        this.isSimplePath = false;
        Path.nRLineTo(this.mNativePath, f, f2);
    }

    public void rMoveTo(float f, float f2) {
        Path.nRMoveTo(this.mNativePath, f, f2);
    }

    public void rQuadTo(float f, float f2, float f3, float f4) {
        this.isSimplePath = false;
        Path.nRQuadTo(this.mNativePath, f, f2, f3, f4);
    }

    public final long readOnlyNI() {
        return this.mNativePath;
    }

    public void reset() {
        this.isSimplePath = true;
        this.mLastDirection = null;
        Object object = this.rects;
        if (object != null) {
            object.setEmpty();
        }
        object = this.getFillType();
        Path.nReset(this.mNativePath);
        this.setFillType((FillType)((Object)object));
    }

    public void rewind() {
        this.isSimplePath = true;
        this.mLastDirection = null;
        Region region = this.rects;
        if (region != null) {
            region.setEmpty();
        }
        Path.nRewind(this.mNativePath);
    }

    public void set(Path object) {
        Region region;
        if (this == object) {
            return;
        }
        this.isSimplePath = ((Path)object).isSimplePath;
        Path.nSet(this.mNativePath, ((Path)object).mNativePath);
        if (!this.isSimplePath) {
            return;
        }
        Region region2 = this.rects;
        if (region2 != null && (region = ((Path)object).rects) != null) {
            region2.set(region);
        } else {
            region = this.rects;
            if (region != null && ((Path)object).rects == null) {
                region.setEmpty();
            } else {
                object = ((Path)object).rects;
                if (object != null) {
                    this.rects = new Region((Region)object);
                }
            }
        }
    }

    public void setFillType(FillType fillType) {
        Path.nSetFillType(this.mNativePath, fillType.nativeInt);
    }

    public void setLastPoint(float f, float f2) {
        this.isSimplePath = false;
        Path.nSetLastPoint(this.mNativePath, f, f2);
    }

    public void toggleInverseFillType() {
        int n = Path.nGetFillType(this.mNativePath);
        int n2 = FillType.INVERSE_WINDING.nativeInt;
        Path.nSetFillType(this.mNativePath, n ^ n2);
    }

    public void transform(Matrix matrix) {
        this.isSimplePath = false;
        Path.nTransform(this.mNativePath, matrix.native_instance);
    }

    public void transform(Matrix matrix, Path path) {
        long l = 0L;
        if (path != null) {
            path.isSimplePath = false;
            l = path.mNativePath;
        }
        Path.nTransform(this.mNativePath, matrix.native_instance, l);
    }

    public static enum Direction {
        CW(0),
        CCW(1);
        
        final int nativeInt;

        private Direction(int n2) {
            this.nativeInt = n2;
        }
    }

    public static enum FillType {
        WINDING(0),
        EVEN_ODD(1),
        INVERSE_WINDING(2),
        INVERSE_EVEN_ODD(3);
        
        final int nativeInt;

        private FillType(int n2) {
            this.nativeInt = n2;
        }
    }

    public static enum Op {
        DIFFERENCE,
        INTERSECT,
        UNION,
        XOR,
        REVERSE_DIFFERENCE;
        
    }

}

