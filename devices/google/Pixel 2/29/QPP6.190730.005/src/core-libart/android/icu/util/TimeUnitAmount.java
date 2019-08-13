/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.icu.util.TimeUnit;

public class TimeUnitAmount
extends Measure {
    public TimeUnitAmount(double d, TimeUnit timeUnit) {
        super(new Double(d), timeUnit);
    }

    public TimeUnitAmount(Number number, TimeUnit timeUnit) {
        super(number, timeUnit);
    }

    public TimeUnit getTimeUnit() {
        return (TimeUnit)this.getUnit();
    }
}

