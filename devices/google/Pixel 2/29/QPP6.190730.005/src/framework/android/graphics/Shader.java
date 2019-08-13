/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.NativeAllocationRegistry
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Matrix;
import libcore.util.NativeAllocationRegistry;

public class Shader {
    private Runnable mCleaner;
    private final ColorSpace mColorSpace;
    private Matrix mLocalMatrix;
    private long mNativeInstance;

    @Deprecated
    public Shader() {
        this.mColorSpace = null;
    }

    public Shader(ColorSpace colorSpace) {
        this.mColorSpace = colorSpace;
        if (colorSpace != null) {
            this.mColorSpace.getNativeInstance();
            return;
        }
        throw new IllegalArgumentException("Use Shader() to create a Shader with no ColorSpace");
    }

    static /* synthetic */ long access$000() {
        return Shader.nativeGetFinalizer();
    }

    public static long[] convertColors(int[] arrn) {
        if (arrn.length >= 2) {
            long[] arrl = new long[arrn.length];
            for (int i = 0; i < arrn.length; ++i) {
                arrl[i] = Color.pack(arrn[i]);
            }
            return arrl;
        }
        throw new IllegalArgumentException("needs >= 2 number of colors");
    }

    public static ColorSpace detectColorSpace(long[] arrl) {
        if (arrl.length >= 2) {
            ColorSpace colorSpace = Color.colorSpace(arrl[0]);
            for (int i = 1; i < arrl.length; ++i) {
                if (Color.colorSpace(arrl[i]) == colorSpace) {
                    continue;
                }
                throw new IllegalArgumentException("All colors must be in the same ColorSpace!");
            }
            return colorSpace;
        }
        throw new IllegalArgumentException("needs >= 2 number of colors");
    }

    private static native long nativeGetFinalizer();

    protected ColorSpace colorSpace() {
        return this.mColorSpace;
    }

    long createNativeInstance(long l) {
        return 0L;
    }

    protected final void discardNativeInstance() {
        if (this.mNativeInstance != 0L) {
            this.mCleaner.run();
            this.mCleaner = null;
            this.mNativeInstance = 0L;
        }
    }

    public boolean getLocalMatrix(Matrix matrix) {
        Matrix matrix2 = this.mLocalMatrix;
        if (matrix2 != null) {
            matrix.set(matrix2);
            return true;
        }
        return false;
    }

    public final long getNativeInstance() {
        this.verifyNativeInstance();
        if (this.mNativeInstance == 0L) {
            Matrix matrix = this.mLocalMatrix;
            long l = matrix == null ? 0L : matrix.native_instance;
            this.mNativeInstance = this.createNativeInstance(l);
            if (this.mNativeInstance != 0L) {
                this.mCleaner = NoImagePreloadHolder.sRegistry.registerNativeAllocation((Object)this, this.mNativeInstance);
            }
        }
        return this.mNativeInstance;
    }

    public void setLocalMatrix(Matrix matrix) {
        if (matrix != null && !matrix.isIdentity()) {
            Matrix matrix2 = this.mLocalMatrix;
            if (matrix2 == null) {
                this.mLocalMatrix = new Matrix(matrix);
                this.discardNativeInstance();
            } else if (!matrix2.equals(matrix)) {
                this.mLocalMatrix.set(matrix);
                this.discardNativeInstance();
            }
        } else if (this.mLocalMatrix != null) {
            this.mLocalMatrix = null;
            this.discardNativeInstance();
        }
    }

    protected void verifyNativeInstance() {
    }

    private static class NoImagePreloadHolder {
        public static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced((ClassLoader)Shader.class.getClassLoader(), (long)Shader.access$000());

        private NoImagePreloadHolder() {
        }
    }

    public static enum TileMode {
        CLAMP(0),
        REPEAT(1),
        MIRROR(2);
        
        @UnsupportedAppUsage
        final int nativeInt;

        private TileMode(int n2) {
            this.nativeInt = n2;
        }
    }

}

