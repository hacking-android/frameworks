/*
 * Decompiled with CFR 0.145.
 */
package android.view;

public abstract class FrameStats {
    public static final long UNDEFINED_TIME_NANO = -1L;
    protected long[] mFramesPresentedTimeNano;
    protected long mRefreshPeriodNano;

    public final long getEndTimeNano() {
        if (this.getFrameCount() <= 0) {
            return -1L;
        }
        long[] arrl = this.mFramesPresentedTimeNano;
        return arrl[arrl.length - 1];
    }

    public final int getFrameCount() {
        long[] arrl = this.mFramesPresentedTimeNano;
        int n = arrl != null ? arrl.length : 0;
        return n;
    }

    public final long getFramePresentedTimeNano(int n) {
        long[] arrl = this.mFramesPresentedTimeNano;
        if (arrl != null) {
            return arrl[n];
        }
        throw new IndexOutOfBoundsException();
    }

    public final long getRefreshPeriodNano() {
        return this.mRefreshPeriodNano;
    }

    public final long getStartTimeNano() {
        if (this.getFrameCount() <= 0) {
            return -1L;
        }
        return this.mFramesPresentedTimeNano[0];
    }
}

