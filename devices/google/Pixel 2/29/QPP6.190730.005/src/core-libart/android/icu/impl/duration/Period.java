/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration;

import android.icu.impl.duration.TimeUnit;

public final class Period {
    final int[] counts;
    final boolean inFuture;
    final byte timeLimit;

    private Period(int n, boolean bl, float f, TimeUnit timeUnit) {
        this.timeLimit = (byte)n;
        this.inFuture = bl;
        this.counts = new int[TimeUnit.units.length];
        this.counts[timeUnit.ordinal] = (int)(1000.0f * f) + 1;
    }

    Period(int n, boolean bl, int[] arrn) {
        this.timeLimit = (byte)n;
        this.inFuture = bl;
        this.counts = arrn;
    }

    public static Period at(float f, TimeUnit timeUnit) {
        Period.checkCount(f);
        return new Period(0, false, f, timeUnit);
    }

    private static void checkCount(float f) {
        if (!(f < 0.0f)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("count (");
        stringBuilder.append(f);
        stringBuilder.append(") cannot be negative");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static Period lessThan(float f, TimeUnit timeUnit) {
        Period.checkCount(f);
        return new Period(1, false, f, timeUnit);
    }

    public static Period moreThan(float f, TimeUnit timeUnit) {
        Period.checkCount(f);
        return new Period(2, false, f, timeUnit);
    }

    private Period setFuture(boolean bl) {
        if (this.inFuture != bl) {
            return new Period(this.timeLimit, bl, this.counts);
        }
        return this;
    }

    private Period setTimeLimit(byte by) {
        if (this.timeLimit != by) {
            return new Period(by, this.inFuture, this.counts);
        }
        return this;
    }

    private Period setTimeUnitInternalValue(TimeUnit arrn, int n) {
        arrn = this.counts;
        byte by = arrn.ordinal;
        if (arrn[by] != n) {
            int[] arrn2;
            arrn = new int[arrn.length];
            for (int i = 0; i < (arrn2 = this.counts).length; ++i) {
                arrn[i] = arrn2[i];
            }
            arrn[by] = n;
            return new Period(this.timeLimit, this.inFuture, arrn);
        }
        return this;
    }

    private Period setTimeUnitValue(TimeUnit object, float f) {
        if (!(f < 0.0f)) {
            return this.setTimeUnitInternalValue((TimeUnit)object, (int)(1000.0f * f) + 1);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("value: ");
        ((StringBuilder)object).append(f);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public Period and(float f, TimeUnit timeUnit) {
        Period.checkCount(f);
        return this.setTimeUnitValue(timeUnit, f);
    }

    public Period at() {
        return this.setTimeLimit((byte)0);
    }

    public boolean equals(Period period) {
        if (period != null && this.timeLimit == period.timeLimit && this.inFuture == period.inFuture) {
            int[] arrn;
            for (int i = 0; i < (arrn = this.counts).length; ++i) {
                if (arrn[i] == period.counts[i]) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean equals(Object object) {
        try {
            boolean bl = this.equals((Period)object);
            return bl;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public float getCount(TimeUnit arrn) {
        arrn = this.counts;
        byte by = arrn.ordinal;
        if (arrn[by] == 0) {
            return 0.0f;
        }
        return (float)(arrn[by] - 1) / 1000.0f;
    }

    public int hashCode() {
        int[] arrn;
        int n = this.timeLimit << 1 | this.inFuture;
        for (int i = 0; i < (arrn = this.counts).length; ++i) {
            n = n << 2 ^ arrn[i];
        }
        return n;
    }

    public Period inFuture() {
        return this.setFuture(true);
    }

    public Period inFuture(boolean bl) {
        return this.setFuture(bl);
    }

    public Period inPast() {
        return this.setFuture(false);
    }

    public Period inPast(boolean bl) {
        return this.setFuture(bl ^ true);
    }

    public boolean isInFuture() {
        return this.inFuture;
    }

    public boolean isInPast() {
        return this.inFuture ^ true;
    }

    public boolean isLessThan() {
        byte by = this.timeLimit;
        boolean bl = true;
        if (by != 1) {
            bl = false;
        }
        return bl;
    }

    public boolean isMoreThan() {
        boolean bl = this.timeLimit == 2;
        return bl;
    }

    public boolean isSet() {
        int[] arrn;
        for (int i = 0; i < (arrn = this.counts).length; ++i) {
            if (arrn[i] == 0) continue;
            return true;
        }
        return false;
    }

    public boolean isSet(TimeUnit timeUnit) {
        boolean bl = this.counts[timeUnit.ordinal] > 0;
        return bl;
    }

    public Period lessThan() {
        return this.setTimeLimit((byte)1);
    }

    public Period moreThan() {
        return this.setTimeLimit((byte)2);
    }

    public Period omit(TimeUnit timeUnit) {
        return this.setTimeUnitInternalValue(timeUnit, 0);
    }
}

