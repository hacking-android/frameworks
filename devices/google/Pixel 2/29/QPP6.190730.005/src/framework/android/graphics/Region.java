/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pools;

public class Region
implements Parcelable {
    public static final Parcelable.Creator<Region> CREATOR;
    private static final int MAX_POOL_SIZE = 10;
    private static final Pools.SynchronizedPool<Region> sPool;
    @UnsupportedAppUsage
    public long mNativeRegion;

    static {
        sPool = new Pools.SynchronizedPool(10);
        CREATOR = new Parcelable.Creator<Region>(){

            @Override
            public Region createFromParcel(Parcel parcel) {
                long l = Region.nativeCreateFromParcel(parcel);
                if (l != 0L) {
                    return new Region(l);
                }
                throw new RuntimeException();
            }

            public Region[] newArray(int n) {
                return new Region[n];
            }
        };
    }

    public Region() {
        this(Region.nativeConstructor());
    }

    public Region(int n, int n2, int n3, int n4) {
        this.mNativeRegion = Region.nativeConstructor();
        Region.nativeSetRect(this.mNativeRegion, n, n2, n3, n4);
    }

    Region(long l) {
        if (l != 0L) {
            this.mNativeRegion = l;
            return;
        }
        throw new RuntimeException();
    }

    @UnsupportedAppUsage
    private Region(long l, int n) {
        this(l);
    }

    public Region(Rect rect) {
        this.mNativeRegion = Region.nativeConstructor();
        Region.nativeSetRect(this.mNativeRegion, rect.left, rect.top, rect.right, rect.bottom);
    }

    public Region(Region region) {
        this(Region.nativeConstructor());
        Region.nativeSetRegion(this.mNativeRegion, region.mNativeRegion);
    }

    private static native long nativeConstructor();

    private static native long nativeCreateFromParcel(Parcel var0);

    private static native void nativeDestructor(long var0);

    private static native boolean nativeEquals(long var0, long var2);

    private static native boolean nativeGetBoundaryPath(long var0, long var2);

    private static native boolean nativeGetBounds(long var0, Rect var2);

    private static native boolean nativeOp(long var0, int var2, int var3, int var4, int var5, int var6);

    private static native boolean nativeOp(long var0, long var2, long var4, int var6);

    private static native boolean nativeOp(long var0, Rect var2, long var3, int var5);

    private static native boolean nativeSetPath(long var0, long var2, long var4);

    private static native boolean nativeSetRect(long var0, int var2, int var3, int var4, int var5);

    private static native void nativeSetRegion(long var0, long var2);

    private static native String nativeToString(long var0);

    private static native boolean nativeWriteToParcel(long var0, Parcel var2);

    public static Region obtain() {
        Region region = sPool.acquire();
        if (region == null) {
            region = new Region();
        }
        return region;
    }

    public static Region obtain(Region region) {
        Region region2 = Region.obtain();
        region2.set(region);
        return region2;
    }

    public native boolean contains(int var1, int var2);

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object != null && object instanceof Region) {
            object = (Region)object;
            return Region.nativeEquals(this.mNativeRegion, ((Region)object).mNativeRegion);
        }
        return false;
    }

    protected void finalize() throws Throwable {
        try {
            Region.nativeDestructor(this.mNativeRegion);
            this.mNativeRegion = 0L;
            return;
        }
        finally {
            super.finalize();
        }
    }

    public Path getBoundaryPath() {
        Path path = new Path();
        Region.nativeGetBoundaryPath(this.mNativeRegion, path.mutateNI());
        return path;
    }

    public boolean getBoundaryPath(Path path) {
        return Region.nativeGetBoundaryPath(this.mNativeRegion, path.mutateNI());
    }

    public Rect getBounds() {
        Rect rect = new Rect();
        Region.nativeGetBounds(this.mNativeRegion, rect);
        return rect;
    }

    public boolean getBounds(Rect rect) {
        if (rect != null) {
            return Region.nativeGetBounds(this.mNativeRegion, rect);
        }
        throw new NullPointerException();
    }

    public native boolean isComplex();

    public native boolean isEmpty();

    public native boolean isRect();

    final long ni() {
        return this.mNativeRegion;
    }

    public boolean op(int n, int n2, int n3, int n4, Op op) {
        return Region.nativeOp(this.mNativeRegion, n, n2, n3, n4, op.nativeInt);
    }

    public boolean op(Rect rect, Op op) {
        return Region.nativeOp(this.mNativeRegion, rect.left, rect.top, rect.right, rect.bottom, op.nativeInt);
    }

    public boolean op(Rect rect, Region region, Op op) {
        return Region.nativeOp(this.mNativeRegion, rect, region.mNativeRegion, op.nativeInt);
    }

    public boolean op(Region region, Op op) {
        return this.op(this, region, op);
    }

    public boolean op(Region region, Region region2, Op op) {
        return Region.nativeOp(this.mNativeRegion, region.mNativeRegion, region2.mNativeRegion, op.nativeInt);
    }

    public native boolean quickContains(int var1, int var2, int var3, int var4);

    public boolean quickContains(Rect rect) {
        return this.quickContains(rect.left, rect.top, rect.right, rect.bottom);
    }

    public native boolean quickReject(int var1, int var2, int var3, int var4);

    public boolean quickReject(Rect rect) {
        return this.quickReject(rect.left, rect.top, rect.right, rect.bottom);
    }

    public native boolean quickReject(Region var1);

    @UnsupportedAppUsage
    public void recycle() {
        this.setEmpty();
        sPool.release(this);
    }

    @UnsupportedAppUsage
    public void scale(float f) {
        this.scale(f, null);
    }

    public native void scale(float var1, Region var2);

    public boolean set(int n, int n2, int n3, int n4) {
        return Region.nativeSetRect(this.mNativeRegion, n, n2, n3, n4);
    }

    public boolean set(Rect rect) {
        return Region.nativeSetRect(this.mNativeRegion, rect.left, rect.top, rect.right, rect.bottom);
    }

    public boolean set(Region region) {
        Region.nativeSetRegion(this.mNativeRegion, region.mNativeRegion);
        return true;
    }

    public void setEmpty() {
        Region.nativeSetRect(this.mNativeRegion, 0, 0, 0, 0);
    }

    public boolean setPath(Path path, Region region) {
        return Region.nativeSetPath(this.mNativeRegion, path.readOnlyNI(), region.mNativeRegion);
    }

    public String toString() {
        return Region.nativeToString(this.mNativeRegion);
    }

    public void translate(int n, int n2) {
        this.translate(n, n2, null);
    }

    public native void translate(int var1, int var2, Region var3);

    public final boolean union(Rect rect) {
        return this.op(rect, Op.UNION);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (Region.nativeWriteToParcel(this.mNativeRegion, parcel)) {
            return;
        }
        throw new RuntimeException();
    }

    public static enum Op {
        DIFFERENCE(0),
        INTERSECT(1),
        UNION(2),
        XOR(3),
        REVERSE_DIFFERENCE(4),
        REPLACE(5);
        
        @UnsupportedAppUsage
        public final int nativeInt;

        private Op(int n2) {
            this.nativeInt = n2;
        }
    }

}

