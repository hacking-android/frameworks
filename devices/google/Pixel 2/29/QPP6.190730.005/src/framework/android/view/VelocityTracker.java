/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.util.Pools;
import android.view.MotionEvent;

public final class VelocityTracker {
    private static final int ACTIVE_POINTER_ID = -1;
    private static final Pools.SynchronizedPool<VelocityTracker> sPool = new Pools.SynchronizedPool(2);
    private long mPtr;
    private final String mStrategy;

    private VelocityTracker(String string2) {
        this.mPtr = VelocityTracker.nativeInitialize(string2);
        this.mStrategy = string2;
    }

    private static native void nativeAddMovement(long var0, MotionEvent var2);

    private static native void nativeClear(long var0);

    private static native void nativeComputeCurrentVelocity(long var0, int var2, float var3);

    private static native void nativeDispose(long var0);

    private static native boolean nativeGetEstimator(long var0, int var2, Estimator var3);

    private static native float nativeGetXVelocity(long var0, int var2);

    private static native float nativeGetYVelocity(long var0, int var2);

    private static native long nativeInitialize(String var0);

    public static VelocityTracker obtain() {
        VelocityTracker velocityTracker = sPool.acquire();
        if (velocityTracker == null) {
            velocityTracker = new VelocityTracker(null);
        }
        return velocityTracker;
    }

    @UnsupportedAppUsage
    public static VelocityTracker obtain(String string2) {
        if (string2 == null) {
            return VelocityTracker.obtain();
        }
        return new VelocityTracker(string2);
    }

    public void addMovement(MotionEvent motionEvent) {
        if (motionEvent != null) {
            VelocityTracker.nativeAddMovement(this.mPtr, motionEvent);
            return;
        }
        throw new IllegalArgumentException("event must not be null");
    }

    public void clear() {
        VelocityTracker.nativeClear(this.mPtr);
    }

    public void computeCurrentVelocity(int n) {
        VelocityTracker.nativeComputeCurrentVelocity(this.mPtr, n, Float.MAX_VALUE);
    }

    public void computeCurrentVelocity(int n, float f) {
        VelocityTracker.nativeComputeCurrentVelocity(this.mPtr, n, f);
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mPtr != 0L) {
                VelocityTracker.nativeDispose(this.mPtr);
                this.mPtr = 0L;
            }
            return;
        }
        finally {
            super.finalize();
        }
    }

    public boolean getEstimator(int n, Estimator estimator) {
        if (estimator != null) {
            return VelocityTracker.nativeGetEstimator(this.mPtr, n, estimator);
        }
        throw new IllegalArgumentException("outEstimator must not be null");
    }

    public float getXVelocity() {
        return VelocityTracker.nativeGetXVelocity(this.mPtr, -1);
    }

    public float getXVelocity(int n) {
        return VelocityTracker.nativeGetXVelocity(this.mPtr, n);
    }

    public float getYVelocity() {
        return VelocityTracker.nativeGetYVelocity(this.mPtr, -1);
    }

    public float getYVelocity(int n) {
        return VelocityTracker.nativeGetYVelocity(this.mPtr, n);
    }

    public void recycle() {
        if (this.mStrategy == null) {
            this.clear();
            sPool.release(this);
        }
    }

    public static final class Estimator {
        private static final int MAX_DEGREE = 4;
        @UnsupportedAppUsage
        public float confidence;
        @UnsupportedAppUsage
        public int degree;
        @UnsupportedAppUsage
        public final float[] xCoeff = new float[5];
        @UnsupportedAppUsage
        public final float[] yCoeff = new float[5];

        private float estimate(float f, float[] arrf) {
            float f2 = 0.0f;
            float f3 = 1.0f;
            for (int i = 0; i <= this.degree; ++i) {
                f2 += arrf[i] * f3;
                f3 *= f;
            }
            return f2;
        }

        public float estimateX(float f) {
            return this.estimate(f, this.xCoeff);
        }

        public float estimateY(float f) {
            return this.estimate(f, this.yCoeff);
        }

        public float getXCoeff(int n) {
            float f = n <= this.degree ? this.xCoeff[n] : 0.0f;
            return f;
        }

        public float getYCoeff(int n) {
            float f = n <= this.degree ? this.yCoeff[n] : 0.0f;
            return f;
        }
    }

}

