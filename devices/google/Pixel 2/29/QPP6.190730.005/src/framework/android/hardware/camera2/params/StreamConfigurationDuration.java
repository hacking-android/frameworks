/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.params;

import android.hardware.camera2.params.StreamConfigurationMap;
import android.hardware.camera2.utils.HashCodeHelpers;
import android.util.Size;
import com.android.internal.util.Preconditions;

public final class StreamConfigurationDuration {
    private final long mDurationNs;
    private final int mFormat;
    private final int mHeight;
    private final int mWidth;

    public StreamConfigurationDuration(int n, int n2, int n3, long l) {
        this.mFormat = StreamConfigurationMap.checkArgumentFormatInternal(n);
        this.mWidth = Preconditions.checkArgumentPositive(n2, "width must be positive");
        this.mHeight = Preconditions.checkArgumentPositive(n3, "height must be positive");
        this.mDurationNs = Preconditions.checkArgumentNonnegative(l, "durationNs must be non-negative");
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof StreamConfigurationDuration) {
            object = (StreamConfigurationDuration)object;
            boolean bl2 = bl;
            if (this.mFormat == ((StreamConfigurationDuration)object).mFormat) {
                bl2 = bl;
                if (this.mWidth == ((StreamConfigurationDuration)object).mWidth) {
                    bl2 = bl;
                    if (this.mHeight == ((StreamConfigurationDuration)object).mHeight) {
                        bl2 = bl;
                        if (this.mDurationNs == ((StreamConfigurationDuration)object).mDurationNs) {
                            bl2 = true;
                        }
                    }
                }
            }
            return bl2;
        }
        return false;
    }

    public long getDuration() {
        return this.mDurationNs;
    }

    public final int getFormat() {
        return this.mFormat;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public Size getSize() {
        return new Size(this.mWidth, this.mHeight);
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int hashCode() {
        int n = this.mFormat;
        int n2 = this.mWidth;
        int n3 = this.mHeight;
        long l = this.mDurationNs;
        return HashCodeHelpers.hashCode(new int[]{n, n2, n3, (int)l, (int)(l >>> 32)});
    }
}

