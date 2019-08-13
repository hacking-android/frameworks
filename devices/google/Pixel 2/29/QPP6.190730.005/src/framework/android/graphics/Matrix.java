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
import android.graphics.RectF;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import java.io.PrintWriter;
import libcore.util.NativeAllocationRegistry;

public class Matrix {
    @UnsupportedAppUsage
    public static final Matrix IDENTITY_MATRIX = new Matrix(){

        void oops() {
            throw new IllegalStateException("Matrix can not be modified");
        }

        @Override
        public boolean postConcat(Matrix matrix) {
            this.oops();
            return false;
        }

        @Override
        public boolean postRotate(float f) {
            this.oops();
            return false;
        }

        @Override
        public boolean postRotate(float f, float f2, float f3) {
            this.oops();
            return false;
        }

        @Override
        public boolean postScale(float f, float f2) {
            this.oops();
            return false;
        }

        @Override
        public boolean postScale(float f, float f2, float f3, float f4) {
            this.oops();
            return false;
        }

        @Override
        public boolean postSkew(float f, float f2) {
            this.oops();
            return false;
        }

        @Override
        public boolean postSkew(float f, float f2, float f3, float f4) {
            this.oops();
            return false;
        }

        @Override
        public boolean postTranslate(float f, float f2) {
            this.oops();
            return false;
        }

        @Override
        public boolean preConcat(Matrix matrix) {
            this.oops();
            return false;
        }

        @Override
        public boolean preRotate(float f) {
            this.oops();
            return false;
        }

        @Override
        public boolean preRotate(float f, float f2, float f3) {
            this.oops();
            return false;
        }

        @Override
        public boolean preScale(float f, float f2) {
            this.oops();
            return false;
        }

        @Override
        public boolean preScale(float f, float f2, float f3, float f4) {
            this.oops();
            return false;
        }

        @Override
        public boolean preSkew(float f, float f2) {
            this.oops();
            return false;
        }

        @Override
        public boolean preSkew(float f, float f2, float f3, float f4) {
            this.oops();
            return false;
        }

        @Override
        public boolean preTranslate(float f, float f2) {
            this.oops();
            return false;
        }

        @Override
        public void reset() {
            this.oops();
        }

        @Override
        public void set(Matrix matrix) {
            this.oops();
        }

        @Override
        public boolean setConcat(Matrix matrix, Matrix matrix2) {
            this.oops();
            return false;
        }

        @Override
        public boolean setPolyToPoly(float[] arrf, int n, float[] arrf2, int n2, int n3) {
            this.oops();
            return false;
        }

        @Override
        public boolean setRectToRect(RectF rectF, RectF rectF2, ScaleToFit scaleToFit) {
            this.oops();
            return false;
        }

        @Override
        public void setRotate(float f) {
            this.oops();
        }

        @Override
        public void setRotate(float f, float f2, float f3) {
            this.oops();
        }

        @Override
        public void setScale(float f, float f2) {
            this.oops();
        }

        @Override
        public void setScale(float f, float f2, float f3, float f4) {
            this.oops();
        }

        @Override
        public void setSinCos(float f, float f2) {
            this.oops();
        }

        @Override
        public void setSinCos(float f, float f2, float f3, float f4) {
            this.oops();
        }

        @Override
        public void setSkew(float f, float f2) {
            this.oops();
        }

        @Override
        public void setSkew(float f, float f2, float f3, float f4) {
            this.oops();
        }

        @Override
        public void setTranslate(float f, float f2) {
            this.oops();
        }

        @Override
        public void setValues(float[] arrf) {
            this.oops();
        }
    };
    public static final int MPERSP_0 = 6;
    public static final int MPERSP_1 = 7;
    public static final int MPERSP_2 = 8;
    public static final int MSCALE_X = 0;
    public static final int MSCALE_Y = 4;
    public static final int MSKEW_X = 1;
    public static final int MSKEW_Y = 3;
    public static final int MTRANS_X = 2;
    public static final int MTRANS_Y = 5;
    @UnsupportedAppUsage
    public final long native_instance;

    public Matrix() {
        this.native_instance = Matrix.nCreate(0L);
        NoImagePreloadHolder.sRegistry.registerNativeAllocation((Object)this, this.native_instance);
    }

    public Matrix(Matrix matrix) {
        long l = matrix != null ? matrix.native_instance : 0L;
        this.native_instance = Matrix.nCreate(l);
        NoImagePreloadHolder.sRegistry.registerNativeAllocation((Object)this, this.native_instance);
    }

    static /* synthetic */ long access$000() {
        return Matrix.nGetNativeFinalizer();
    }

    private static void checkPointArrays(float[] arrf, int n, float[] arrf2, int n2, int n3) {
        int n4 = (n3 << 1) + n;
        int n5 = (n3 << 1) + n2;
        if ((n3 | n | n2 | n4 | n5) >= 0 && n4 <= arrf.length && n5 <= arrf2.length) {
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    private static native long nCreate(long var0);

    @CriticalNative
    private static native boolean nEquals(long var0, long var2);

    private static native long nGetNativeFinalizer();

    @FastNative
    private static native void nGetValues(long var0, float[] var2);

    @CriticalNative
    private static native boolean nInvert(long var0, long var2);

    @CriticalNative
    private static native boolean nIsAffine(long var0);

    @CriticalNative
    private static native boolean nIsIdentity(long var0);

    @FastNative
    private static native void nMapPoints(long var0, float[] var2, int var3, float[] var4, int var5, int var6, boolean var7);

    @CriticalNative
    private static native float nMapRadius(long var0, float var2);

    @FastNative
    private static native boolean nMapRect(long var0, RectF var2, RectF var3);

    @CriticalNative
    private static native void nPostConcat(long var0, long var2);

    @CriticalNative
    private static native void nPostRotate(long var0, float var2);

    @CriticalNative
    private static native void nPostRotate(long var0, float var2, float var3, float var4);

    @CriticalNative
    private static native void nPostScale(long var0, float var2, float var3);

    @CriticalNative
    private static native void nPostScale(long var0, float var2, float var3, float var4, float var5);

    @CriticalNative
    private static native void nPostSkew(long var0, float var2, float var3);

    @CriticalNative
    private static native void nPostSkew(long var0, float var2, float var3, float var4, float var5);

    @CriticalNative
    private static native void nPostTranslate(long var0, float var2, float var3);

    @CriticalNative
    private static native void nPreConcat(long var0, long var2);

    @CriticalNative
    private static native void nPreRotate(long var0, float var2);

    @CriticalNative
    private static native void nPreRotate(long var0, float var2, float var3, float var4);

    @CriticalNative
    private static native void nPreScale(long var0, float var2, float var3);

    @CriticalNative
    private static native void nPreScale(long var0, float var2, float var3, float var4, float var5);

    @CriticalNative
    private static native void nPreSkew(long var0, float var2, float var3);

    @CriticalNative
    private static native void nPreSkew(long var0, float var2, float var3, float var4, float var5);

    @CriticalNative
    private static native void nPreTranslate(long var0, float var2, float var3);

    @CriticalNative
    private static native boolean nRectStaysRect(long var0);

    @CriticalNative
    private static native void nReset(long var0);

    @CriticalNative
    private static native void nSet(long var0, long var2);

    @CriticalNative
    private static native void nSetConcat(long var0, long var2, long var4);

    @FastNative
    private static native boolean nSetPolyToPoly(long var0, float[] var2, int var3, float[] var4, int var5, int var6);

    @FastNative
    private static native boolean nSetRectToRect(long var0, RectF var2, RectF var3, int var4);

    @CriticalNative
    private static native void nSetRotate(long var0, float var2);

    @CriticalNative
    private static native void nSetRotate(long var0, float var2, float var3, float var4);

    @CriticalNative
    private static native void nSetScale(long var0, float var2, float var3);

    @CriticalNative
    private static native void nSetScale(long var0, float var2, float var3, float var4, float var5);

    @CriticalNative
    private static native void nSetSinCos(long var0, float var2, float var3);

    @CriticalNative
    private static native void nSetSinCos(long var0, float var2, float var3, float var4, float var5);

    @CriticalNative
    private static native void nSetSkew(long var0, float var2, float var3);

    @CriticalNative
    private static native void nSetSkew(long var0, float var2, float var3, float var4, float var5);

    @CriticalNative
    private static native void nSetTranslate(long var0, float var2, float var3);

    @FastNative
    private static native void nSetValues(long var0, float[] var2);

    public boolean equals(Object object) {
        if (!(object instanceof Matrix)) {
            return false;
        }
        return Matrix.nEquals(this.native_instance, ((Matrix)object).native_instance);
    }

    public void getValues(float[] arrf) {
        if (arrf.length >= 9) {
            Matrix.nGetValues(this.native_instance, arrf);
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int hashCode() {
        return 44;
    }

    public boolean invert(Matrix matrix) {
        return Matrix.nInvert(this.native_instance, matrix.native_instance);
    }

    public boolean isAffine() {
        return Matrix.nIsAffine(this.native_instance);
    }

    public boolean isIdentity() {
        return Matrix.nIsIdentity(this.native_instance);
    }

    public void mapPoints(float[] arrf) {
        this.mapPoints(arrf, 0, arrf, 0, arrf.length >> 1);
    }

    public void mapPoints(float[] arrf, int n, float[] arrf2, int n2, int n3) {
        Matrix.checkPointArrays(arrf2, n2, arrf, n, n3);
        Matrix.nMapPoints(this.native_instance, arrf, n, arrf2, n2, n3, true);
    }

    public void mapPoints(float[] arrf, float[] arrf2) {
        if (arrf.length == arrf2.length) {
            this.mapPoints(arrf, 0, arrf2, 0, arrf.length >> 1);
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public float mapRadius(float f) {
        return Matrix.nMapRadius(this.native_instance, f);
    }

    public boolean mapRect(RectF rectF) {
        return this.mapRect(rectF, rectF);
    }

    public boolean mapRect(RectF rectF, RectF rectF2) {
        if (rectF != null && rectF2 != null) {
            return Matrix.nMapRect(this.native_instance, rectF, rectF2);
        }
        throw new NullPointerException();
    }

    public void mapVectors(float[] arrf) {
        this.mapVectors(arrf, 0, arrf, 0, arrf.length >> 1);
    }

    public void mapVectors(float[] arrf, int n, float[] arrf2, int n2, int n3) {
        Matrix.checkPointArrays(arrf2, n2, arrf, n, n3);
        Matrix.nMapPoints(this.native_instance, arrf, n, arrf2, n2, n3, false);
    }

    public void mapVectors(float[] arrf, float[] arrf2) {
        if (arrf.length == arrf2.length) {
            this.mapVectors(arrf, 0, arrf2, 0, arrf.length >> 1);
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public final long ni() {
        return this.native_instance;
    }

    public boolean postConcat(Matrix matrix) {
        Matrix.nPostConcat(this.native_instance, matrix.native_instance);
        return true;
    }

    public boolean postRotate(float f) {
        Matrix.nPostRotate(this.native_instance, f);
        return true;
    }

    public boolean postRotate(float f, float f2, float f3) {
        Matrix.nPostRotate(this.native_instance, f, f2, f3);
        return true;
    }

    public boolean postScale(float f, float f2) {
        Matrix.nPostScale(this.native_instance, f, f2);
        return true;
    }

    public boolean postScale(float f, float f2, float f3, float f4) {
        Matrix.nPostScale(this.native_instance, f, f2, f3, f4);
        return true;
    }

    public boolean postSkew(float f, float f2) {
        Matrix.nPostSkew(this.native_instance, f, f2);
        return true;
    }

    public boolean postSkew(float f, float f2, float f3, float f4) {
        Matrix.nPostSkew(this.native_instance, f, f2, f3, f4);
        return true;
    }

    public boolean postTranslate(float f, float f2) {
        Matrix.nPostTranslate(this.native_instance, f, f2);
        return true;
    }

    public boolean preConcat(Matrix matrix) {
        Matrix.nPreConcat(this.native_instance, matrix.native_instance);
        return true;
    }

    public boolean preRotate(float f) {
        Matrix.nPreRotate(this.native_instance, f);
        return true;
    }

    public boolean preRotate(float f, float f2, float f3) {
        Matrix.nPreRotate(this.native_instance, f, f2, f3);
        return true;
    }

    public boolean preScale(float f, float f2) {
        Matrix.nPreScale(this.native_instance, f, f2);
        return true;
    }

    public boolean preScale(float f, float f2, float f3, float f4) {
        Matrix.nPreScale(this.native_instance, f, f2, f3, f4);
        return true;
    }

    public boolean preSkew(float f, float f2) {
        Matrix.nPreSkew(this.native_instance, f, f2);
        return true;
    }

    public boolean preSkew(float f, float f2, float f3, float f4) {
        Matrix.nPreSkew(this.native_instance, f, f2, f3, f4);
        return true;
    }

    public boolean preTranslate(float f, float f2) {
        Matrix.nPreTranslate(this.native_instance, f, f2);
        return true;
    }

    public void printShortString(PrintWriter printWriter) {
        float[] arrf = new float[9];
        this.getValues(arrf);
        printWriter.print('[');
        printWriter.print(arrf[0]);
        printWriter.print(", ");
        printWriter.print(arrf[1]);
        printWriter.print(", ");
        printWriter.print(arrf[2]);
        printWriter.print("][");
        printWriter.print(arrf[3]);
        printWriter.print(", ");
        printWriter.print(arrf[4]);
        printWriter.print(", ");
        printWriter.print(arrf[5]);
        printWriter.print("][");
        printWriter.print(arrf[6]);
        printWriter.print(", ");
        printWriter.print(arrf[7]);
        printWriter.print(", ");
        printWriter.print(arrf[8]);
        printWriter.print(']');
    }

    public boolean rectStaysRect() {
        return Matrix.nRectStaysRect(this.native_instance);
    }

    public void reset() {
        Matrix.nReset(this.native_instance);
    }

    public void set(Matrix matrix) {
        if (matrix == null) {
            this.reset();
        } else {
            Matrix.nSet(this.native_instance, matrix.native_instance);
        }
    }

    public boolean setConcat(Matrix matrix, Matrix matrix2) {
        Matrix.nSetConcat(this.native_instance, matrix.native_instance, matrix2.native_instance);
        return true;
    }

    public boolean setPolyToPoly(float[] arrf, int n, float[] arrf2, int n2, int n3) {
        if (n3 <= 4) {
            Matrix.checkPointArrays(arrf, n, arrf2, n2, n3);
            return Matrix.nSetPolyToPoly(this.native_instance, arrf, n, arrf2, n2, n3);
        }
        throw new IllegalArgumentException();
    }

    public boolean setRectToRect(RectF rectF, RectF rectF2, ScaleToFit scaleToFit) {
        if (rectF2 != null && rectF != null) {
            return Matrix.nSetRectToRect(this.native_instance, rectF, rectF2, scaleToFit.nativeInt);
        }
        throw new NullPointerException();
    }

    public void setRotate(float f) {
        Matrix.nSetRotate(this.native_instance, f);
    }

    public void setRotate(float f, float f2, float f3) {
        Matrix.nSetRotate(this.native_instance, f, f2, f3);
    }

    public void setScale(float f, float f2) {
        Matrix.nSetScale(this.native_instance, f, f2);
    }

    public void setScale(float f, float f2, float f3, float f4) {
        Matrix.nSetScale(this.native_instance, f, f2, f3, f4);
    }

    public void setSinCos(float f, float f2) {
        Matrix.nSetSinCos(this.native_instance, f, f2);
    }

    public void setSinCos(float f, float f2, float f3, float f4) {
        Matrix.nSetSinCos(this.native_instance, f, f2, f3, f4);
    }

    public void setSkew(float f, float f2) {
        Matrix.nSetSkew(this.native_instance, f, f2);
    }

    public void setSkew(float f, float f2, float f3, float f4) {
        Matrix.nSetSkew(this.native_instance, f, f2, f3, f4);
    }

    public void setTranslate(float f, float f2) {
        Matrix.nSetTranslate(this.native_instance, f, f2);
    }

    public void setValues(float[] arrf) {
        if (arrf.length >= 9) {
            Matrix.nSetValues(this.native_instance, arrf);
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public String toShortString() {
        StringBuilder stringBuilder = new StringBuilder(64);
        this.toShortString(stringBuilder);
        return stringBuilder.toString();
    }

    public void toShortString(StringBuilder stringBuilder) {
        float[] arrf = new float[9];
        this.getValues(arrf);
        stringBuilder.append('[');
        stringBuilder.append(arrf[0]);
        stringBuilder.append(", ");
        stringBuilder.append(arrf[1]);
        stringBuilder.append(", ");
        stringBuilder.append(arrf[2]);
        stringBuilder.append("][");
        stringBuilder.append(arrf[3]);
        stringBuilder.append(", ");
        stringBuilder.append(arrf[4]);
        stringBuilder.append(", ");
        stringBuilder.append(arrf[5]);
        stringBuilder.append("][");
        stringBuilder.append(arrf[6]);
        stringBuilder.append(", ");
        stringBuilder.append(arrf[7]);
        stringBuilder.append(", ");
        stringBuilder.append(arrf[8]);
        stringBuilder.append(']');
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(64);
        stringBuilder.append("Matrix{");
        this.toShortString(stringBuilder);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    private static class NoImagePreloadHolder {
        public static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced((ClassLoader)Matrix.class.getClassLoader(), (long)Matrix.access$000());

        private NoImagePreloadHolder() {
        }
    }

    public static enum ScaleToFit {
        FILL(0),
        START(1),
        CENTER(2),
        END(3);
        
        final int nativeInt;

        private ScaleToFit(int n2) {
            this.nativeInt = n2;
        }
    }

}

