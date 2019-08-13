/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.os.SystemClock;

public class Interpolator {
    private int mFrameCount;
    private int mValueCount;
    private long native_instance;

    public Interpolator(int n) {
        this.mValueCount = n;
        this.mFrameCount = 2;
        this.native_instance = Interpolator.nativeConstructor(n, 2);
    }

    public Interpolator(int n, int n2) {
        this.mValueCount = n;
        this.mFrameCount = n2;
        this.native_instance = Interpolator.nativeConstructor(n, n2);
    }

    private static native long nativeConstructor(int var0, int var1);

    private static native void nativeDestructor(long var0);

    private static native void nativeReset(long var0, int var2, int var3);

    private static native void nativeSetKeyFrame(long var0, int var2, int var3, float[] var4, float[] var5);

    private static native void nativeSetRepeatMirror(long var0, float var2, boolean var3);

    private static native int nativeTimeToValues(long var0, int var2, float[] var3);

    protected void finalize() throws Throwable {
        Interpolator.nativeDestructor(this.native_instance);
        this.native_instance = 0L;
    }

    public final int getKeyFrameCount() {
        return this.mFrameCount;
    }

    public final int getValueCount() {
        return this.mValueCount;
    }

    public void reset(int n) {
        this.reset(n, 2);
    }

    public void reset(int n, int n2) {
        this.mValueCount = n;
        this.mFrameCount = n2;
        Interpolator.nativeReset(this.native_instance, n, n2);
    }

    public void setKeyFrame(int n, int n2, float[] arrf) {
        this.setKeyFrame(n, n2, arrf, null);
    }

    public void setKeyFrame(int n, int n2, float[] arrf, float[] arrf2) {
        if (n >= 0 && n < this.mFrameCount) {
            if (arrf.length >= this.mValueCount) {
                if (arrf2 != null && arrf2.length < 4) {
                    throw new ArrayStoreException();
                }
                Interpolator.nativeSetKeyFrame(this.native_instance, n, n2, arrf, arrf2);
                return;
            }
            throw new ArrayStoreException();
        }
        throw new IndexOutOfBoundsException();
    }

    public void setRepeatMirror(float f, boolean bl) {
        if (f >= 0.0f) {
            Interpolator.nativeSetRepeatMirror(this.native_instance, f, bl);
        }
    }

    public Result timeToValues(int n, float[] arrf) {
        if (arrf != null && arrf.length < this.mValueCount) {
            throw new ArrayStoreException();
        }
        if ((n = Interpolator.nativeTimeToValues(this.native_instance, n, arrf)) != 0) {
            if (n != 1) {
                return Result.FREEZE_END;
            }
            return Result.FREEZE_START;
        }
        return Result.NORMAL;
    }

    public Result timeToValues(float[] arrf) {
        return this.timeToValues((int)SystemClock.uptimeMillis(), arrf);
    }

    public static enum Result {
        NORMAL,
        FREEZE_START,
        FREEZE_END;
        
    }

}

