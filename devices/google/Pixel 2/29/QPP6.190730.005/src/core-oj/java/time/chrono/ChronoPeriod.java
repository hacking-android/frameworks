/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public interface ChronoPeriod
extends TemporalAmount {
    public static ChronoPeriod between(ChronoLocalDate chronoLocalDate, ChronoLocalDate chronoLocalDate2) {
        Objects.requireNonNull(chronoLocalDate, "startDateInclusive");
        Objects.requireNonNull(chronoLocalDate2, "endDateExclusive");
        return chronoLocalDate.until(chronoLocalDate2);
    }

    @Override
    public Temporal addTo(Temporal var1);

    public boolean equals(Object var1);

    @Override
    public long get(TemporalUnit var1);

    public Chronology getChronology();

    @Override
    public List<TemporalUnit> getUnits();

    public int hashCode();

    default public boolean isNegative() {
        Iterator<TemporalUnit> iterator = this.getUnits().iterator();
        while (iterator.hasNext()) {
            if (this.get(iterator.next()) >= 0L) continue;
            return true;
        }
        return false;
    }

    default public boolean isZero() {
        Iterator<TemporalUnit> iterator = this.getUnits().iterator();
        while (iterator.hasNext()) {
            if (this.get(iterator.next()) == 0L) continue;
            return false;
        }
        return true;
    }

    public ChronoPeriod minus(TemporalAmount var1);

    public ChronoPeriod multipliedBy(int var1);

    default public ChronoPeriod negated() {
        return this.multipliedBy(-1);
    }

    public ChronoPeriod normalized();

    public ChronoPeriod plus(TemporalAmount var1);

    @Override
    public Temporal subtractFrom(Temporal var1);

    public String toString();
}

