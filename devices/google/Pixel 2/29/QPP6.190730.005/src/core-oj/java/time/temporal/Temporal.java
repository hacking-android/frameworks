/*
 * Decompiled with CFR 0.145.
 */
package java.time.temporal;

import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;

public interface Temporal
extends TemporalAccessor {
    public boolean isSupported(TemporalUnit var1);

    default public Temporal minus(long l, TemporalUnit object) {
        object = l == Long.MIN_VALUE ? this.plus(Long.MAX_VALUE, (TemporalUnit)object).plus(1L, (TemporalUnit)object) : this.plus(-l, (TemporalUnit)object);
        return object;
    }

    default public Temporal minus(TemporalAmount temporalAmount) {
        return temporalAmount.subtractFrom(this);
    }

    public Temporal plus(long var1, TemporalUnit var3);

    default public Temporal plus(TemporalAmount temporalAmount) {
        return temporalAmount.addTo(this);
    }

    public long until(Temporal var1, TemporalUnit var2);

    default public Temporal with(TemporalAdjuster temporalAdjuster) {
        return temporalAdjuster.adjustInto(this);
    }

    public Temporal with(TemporalField var1, long var2);
}

