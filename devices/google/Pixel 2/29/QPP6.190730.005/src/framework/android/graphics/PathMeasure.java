/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.Matrix;
import android.graphics.Path;

public class PathMeasure {
    public static final int POSITION_MATRIX_FLAG = 1;
    public static final int TANGENT_MATRIX_FLAG = 2;
    private Path mPath;
    private long native_instance;

    public PathMeasure() {
        this.mPath = null;
        this.native_instance = PathMeasure.native_create(0L, false);
    }

    public PathMeasure(Path path, boolean bl) {
        this.mPath = path;
        long l = path != null ? path.readOnlyNI() : 0L;
        this.native_instance = PathMeasure.native_create(l, bl);
    }

    private static native long native_create(long var0, boolean var2);

    private static native void native_destroy(long var0);

    private static native float native_getLength(long var0);

    private static native boolean native_getMatrix(long var0, float var2, long var3, int var5);

    private static native boolean native_getPosTan(long var0, float var2, float[] var3, float[] var4);

    private static native boolean native_getSegment(long var0, float var2, float var3, long var4, boolean var6);

    private static native boolean native_isClosed(long var0);

    private static native boolean native_nextContour(long var0);

    private static native void native_setPath(long var0, long var2, boolean var4);

    protected void finalize() throws Throwable {
        PathMeasure.native_destroy(this.native_instance);
        this.native_instance = 0L;
    }

    public float getLength() {
        return PathMeasure.native_getLength(this.native_instance);
    }

    public boolean getMatrix(float f, Matrix matrix, int n) {
        return PathMeasure.native_getMatrix(this.native_instance, f, matrix.native_instance, n);
    }

    public boolean getPosTan(float f, float[] arrf, float[] arrf2) {
        if (arrf != null && arrf.length < 2 || arrf2 != null && arrf2.length < 2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return PathMeasure.native_getPosTan(this.native_instance, f, arrf, arrf2);
    }

    public boolean getSegment(float f, float f2, Path path, boolean bl) {
        float f3 = this.getLength();
        float f4 = f;
        if (f < 0.0f) {
            f4 = 0.0f;
        }
        f = f2;
        if (f2 > f3) {
            f = f3;
        }
        if (f4 >= f) {
            return false;
        }
        return PathMeasure.native_getSegment(this.native_instance, f4, f, path.mutateNI(), bl);
    }

    public boolean isClosed() {
        return PathMeasure.native_isClosed(this.native_instance);
    }

    public boolean nextContour() {
        return PathMeasure.native_nextContour(this.native_instance);
    }

    public void setPath(Path path, boolean bl) {
        this.mPath = path;
        long l = this.native_instance;
        long l2 = path != null ? path.readOnlyNI() : 0L;
        PathMeasure.native_setPath(l, l2, bl);
    }
}

