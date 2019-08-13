/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.params;

import android.hardware.camera2.utils.HashCodeHelpers;
import android.util.Range;
import android.util.Size;
import com.android.internal.util.Preconditions;

public final class HighSpeedVideoConfiguration {
    private static final int HIGH_SPEED_MAX_MINIMAL_FPS = 120;
    private final int mBatchSizeMax;
    private final int mFpsMax;
    private final int mFpsMin;
    private final Range<Integer> mFpsRange;
    private final int mHeight;
    private final Size mSize;
    private final int mWidth;

    public HighSpeedVideoConfiguration(int n, int n2, int n3, int n4, int n5) {
        if (n4 >= 120) {
            this.mFpsMax = n4;
            this.mWidth = Preconditions.checkArgumentPositive(n, "width must be positive");
            this.mHeight = Preconditions.checkArgumentPositive(n2, "height must be positive");
            this.mFpsMin = Preconditions.checkArgumentPositive(n3, "fpsMin must be positive");
            this.mSize = new Size(this.mWidth, this.mHeight);
            this.mBatchSizeMax = Preconditions.checkArgumentPositive(n5, "batchSizeMax must be positive");
            this.mFpsRange = new Range<Integer>(this.mFpsMin, this.mFpsMax);
            return;
        }
        throw new IllegalArgumentException("fpsMax must be at least 120");
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof HighSpeedVideoConfiguration) {
            object = (HighSpeedVideoConfiguration)object;
            boolean bl2 = bl;
            if (this.mWidth == ((HighSpeedVideoConfiguration)object).mWidth) {
                bl2 = bl;
                if (this.mHeight == ((HighSpeedVideoConfiguration)object).mHeight) {
                    bl2 = bl;
                    if (this.mFpsMin == ((HighSpeedVideoConfiguration)object).mFpsMin) {
                        bl2 = bl;
                        if (this.mFpsMax == ((HighSpeedVideoConfiguration)object).mFpsMax) {
                            bl2 = bl;
                            if (this.mBatchSizeMax == ((HighSpeedVideoConfiguration)object).mBatchSizeMax) {
                                bl2 = true;
                            }
                        }
                    }
                }
            }
            return bl2;
        }
        return false;
    }

    public int getBatchSizeMax() {
        return this.mBatchSizeMax;
    }

    public int getFpsMax() {
        return this.mFpsMax;
    }

    public int getFpsMin() {
        return this.mFpsMin;
    }

    public Range<Integer> getFpsRange() {
        return this.mFpsRange;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public Size getSize() {
        return this.mSize;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCode(new int[]{this.mWidth, this.mHeight, this.mFpsMin, this.mFpsMax});
    }
}

