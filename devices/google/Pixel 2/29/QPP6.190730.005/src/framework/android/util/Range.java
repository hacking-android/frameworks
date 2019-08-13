/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.hardware.camera2.utils.HashCodeHelpers;
import com.android.internal.util.Preconditions;

public final class Range<T extends Comparable<? super T>> {
    private final T mLower;
    private final T mUpper;

    public Range(T t, T t2) {
        this.mLower = (Comparable)Preconditions.checkNotNull(t, "lower must not be null");
        this.mUpper = (Comparable)Preconditions.checkNotNull(t2, "upper must not be null");
        if (t.compareTo(t2) <= 0) {
            return;
        }
        throw new IllegalArgumentException("lower must be less than or equal to upper");
    }

    public static <T extends Comparable<? super T>> Range<T> create(T t, T t2) {
        return new Range<T>(t, t2);
    }

    public T clamp(T t) {
        Preconditions.checkNotNull(t, "value must not be null");
        if (t.compareTo(this.mLower) < 0) {
            return this.mLower;
        }
        if (t.compareTo(this.mUpper) > 0) {
            return this.mUpper;
        }
        return t;
    }

    public boolean contains(Range<T> range) {
        Preconditions.checkNotNull(range, "value must not be null");
        int n = range.mLower.compareTo(this.mLower);
        boolean bl = true;
        n = n >= 0 ? 1 : 0;
        boolean bl2 = range.mUpper.compareTo(this.mUpper) <= 0;
        if (n == 0 || !bl2) {
            bl = false;
        }
        return bl;
    }

    public boolean contains(T t) {
        Preconditions.checkNotNull(t, "value must not be null");
        int n = t.compareTo(this.mLower);
        boolean bl = true;
        n = n >= 0 ? 1 : 0;
        boolean bl2 = t.compareTo(this.mUpper) <= 0;
        if (n == 0 || !bl2) {
            bl = false;
        }
        return bl;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof Range) {
            object = (Range)object;
            boolean bl2 = bl;
            if (this.mLower.equals(((Range)object).mLower)) {
                bl2 = bl;
                if (this.mUpper.equals(((Range)object).mUpper)) {
                    bl2 = true;
                }
            }
            return bl2;
        }
        return false;
    }

    public Range<T> extend(Range<T> range) {
        Preconditions.checkNotNull(range, "range must not be null");
        int n = range.mLower.compareTo(this.mLower);
        int n2 = range.mUpper.compareTo(this.mUpper);
        if (n <= 0 && n2 >= 0) {
            return range;
        }
        if (n >= 0 && n2 <= 0) {
            return this;
        }
        T t = n >= 0 ? this.mLower : range.mLower;
        range = n2 <= 0 ? this.mUpper : range.mUpper;
        return Range.create(t, range);
    }

    public Range<T> extend(T t) {
        Preconditions.checkNotNull(t, "value must not be null");
        return this.extend(t, t);
    }

    public Range<T> extend(T t, T t2) {
        block2 : {
            Preconditions.checkNotNull(t, "lower must not be null");
            Preconditions.checkNotNull(t2, "upper must not be null");
            int n = t.compareTo(this.mLower);
            int n2 = t2.compareTo(this.mUpper);
            if (n >= 0 && n2 <= 0) {
                return this;
            }
            if (n >= 0) {
                t = this.mLower;
            }
            if (n2 > 0) break block2;
            t2 = this.mUpper;
        }
        return Range.create(t, t2);
    }

    public T getLower() {
        return this.mLower;
    }

    public T getUpper() {
        return this.mUpper;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCodeGeneric(this.mLower, this.mUpper);
    }

    public Range<T> intersect(Range<T> range) {
        Preconditions.checkNotNull(range, "range must not be null");
        int n = range.mLower.compareTo(this.mLower);
        int n2 = range.mUpper.compareTo(this.mUpper);
        if (n <= 0 && n2 >= 0) {
            return this;
        }
        if (n >= 0 && n2 <= 0) {
            return range;
        }
        T t = n <= 0 ? this.mLower : range.mLower;
        range = n2 >= 0 ? this.mUpper : range.mUpper;
        return Range.create(t, range);
    }

    public Range<T> intersect(T t, T t2) {
        block2 : {
            Preconditions.checkNotNull(t, "lower must not be null");
            Preconditions.checkNotNull(t2, "upper must not be null");
            int n = t.compareTo(this.mLower);
            int n2 = t2.compareTo(this.mUpper);
            if (n <= 0 && n2 >= 0) {
                return this;
            }
            if (n <= 0) {
                t = this.mLower;
            }
            if (n2 < 0) break block2;
            t2 = this.mUpper;
        }
        return Range.create(t, t2);
    }

    public String toString() {
        return String.format("[%s, %s]", this.mLower, this.mUpper);
    }
}

