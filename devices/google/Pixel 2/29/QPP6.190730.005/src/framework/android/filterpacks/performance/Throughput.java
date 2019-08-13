/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.performance;

public class Throughput {
    private final int mPeriodFrames;
    private final int mPeriodTime;
    private final int mPixels;
    private final int mTotalFrames;

    public Throughput(int n, int n2, int n3, int n4) {
        this.mTotalFrames = n;
        this.mPeriodFrames = n2;
        this.mPeriodTime = n3;
        this.mPixels = n4;
    }

    public float getFramesPerSecond() {
        return (float)this.mPeriodFrames / (float)this.mPeriodTime;
    }

    public float getNanosPerPixel() {
        return (float)((double)this.mPeriodTime / (double)this.mPeriodFrames * 1000000.0 / (double)this.mPixels);
    }

    public int getPeriodFrameCount() {
        return this.mPeriodFrames;
    }

    public int getPeriodTime() {
        return this.mPeriodTime;
    }

    public int getTotalFrameCount() {
        return this.mTotalFrames;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getFramesPerSecond());
        stringBuilder.append(" FPS");
        return stringBuilder.toString();
    }
}

