/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.params;

import android.hardware.camera2.utils.HashCodeHelpers;
import com.android.internal.util.Preconditions;

public final class OisSample {
    private final long mTimestampNs;
    private final float mXShift;
    private final float mYShift;

    public OisSample(long l, float f, float f2) {
        this.mTimestampNs = l;
        this.mXShift = Preconditions.checkArgumentFinite(f, "xShift must be finite");
        this.mYShift = Preconditions.checkArgumentFinite(f2, "yShift must be finite");
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof OisSample) {
            object = (OisSample)object;
            boolean bl2 = bl;
            if (this.mTimestampNs == ((OisSample)object).mTimestampNs) {
                bl2 = bl;
                if (this.mXShift == ((OisSample)object).mXShift) {
                    bl2 = bl;
                    if (this.mYShift == ((OisSample)object).mYShift) {
                        bl2 = true;
                    }
                }
            }
            return bl2;
        }
        return false;
    }

    public long getTimestamp() {
        return this.mTimestampNs;
    }

    public float getXshift() {
        return this.mXShift;
    }

    public float getYshift() {
        return this.mYShift;
    }

    public int hashCode() {
        int n = HashCodeHelpers.hashCode(this.mTimestampNs);
        return HashCodeHelpers.hashCode(this.mXShift, this.mYShift, n);
    }

    public String toString() {
        return String.format("OisSample{timestamp:%d, shift_x:%f, shift_y:%f}", this.mTimestampNs, Float.valueOf(this.mXShift), Float.valueOf(this.mYShift));
    }
}

