/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.params;

import com.android.internal.util.Preconditions;
import java.util.Arrays;

public final class BlackLevelPattern {
    public static final int COUNT = 4;
    private final int[] mCfaOffsets;

    public BlackLevelPattern(int[] arrn) {
        if (arrn != null) {
            if (arrn.length >= 4) {
                this.mCfaOffsets = Arrays.copyOf(arrn, 4);
                return;
            }
            throw new IllegalArgumentException("Invalid offsets array length");
        }
        throw new NullPointerException("Null offsets array passed to constructor");
    }

    public void copyTo(int[] arrn, int n) {
        Preconditions.checkNotNull(arrn, "destination must not be null");
        if (n >= 0) {
            if (arrn.length - n >= 4) {
                for (int i = 0; i < 4; ++i) {
                    arrn[n + i] = this.mCfaOffsets[i];
                }
                return;
            }
            throw new ArrayIndexOutOfBoundsException("destination too small to fit elements");
        }
        throw new IllegalArgumentException("Null offset passed to copyTo");
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof BlackLevelPattern) {
            return Arrays.equals(((BlackLevelPattern)object).mCfaOffsets, this.mCfaOffsets);
        }
        return false;
    }

    public int getOffsetForIndex(int n, int n2) {
        if (n2 >= 0 && n >= 0) {
            return this.mCfaOffsets[(n2 & 1) << 1 | n & 1];
        }
        throw new IllegalArgumentException("column, row arguments must be positive");
    }

    public int hashCode() {
        return Arrays.hashCode(this.mCfaOffsets);
    }

    public String toString() {
        return String.format("BlackLevelPattern([%d, %d], [%d, %d])", this.mCfaOffsets[0], this.mCfaOffsets[1], this.mCfaOffsets[2], this.mCfaOffsets[3]);
    }
}

