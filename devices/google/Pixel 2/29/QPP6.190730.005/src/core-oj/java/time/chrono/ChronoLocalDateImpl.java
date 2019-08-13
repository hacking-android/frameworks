/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.io.Serializable;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.Era;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Objects;

abstract class ChronoLocalDateImpl<D extends ChronoLocalDate>
implements ChronoLocalDate,
Temporal,
TemporalAdjuster,
Serializable {
    private static final long serialVersionUID = 6282433883239719096L;

    ChronoLocalDateImpl() {
    }

    private long daysUntil(ChronoLocalDate chronoLocalDate) {
        return chronoLocalDate.toEpochDay() - this.toEpochDay();
    }

    static <D extends ChronoLocalDate> D ensureValid(Chronology chronology, Temporal temporal) {
        if (chronology.equals((temporal = (ChronoLocalDate)temporal).getChronology())) {
            return (D)temporal;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Chronology mismatch, expected: ");
        stringBuilder.append(chronology.getId());
        stringBuilder.append(", actual: ");
        stringBuilder.append(temporal.getChronology().getId());
        throw new ClassCastException(stringBuilder.toString());
    }

    private long monthsUntil(ChronoLocalDate chronoLocalDate) {
        if (this.getChronology().range(ChronoField.MONTH_OF_YEAR).getMaximum() == 12L) {
            long l = this.getLong(ChronoField.PROLEPTIC_MONTH);
            long l2 = this.get(ChronoField.DAY_OF_MONTH);
            return (chronoLocalDate.getLong(ChronoField.PROLEPTIC_MONTH) * 32L + (long)chronoLocalDate.get(ChronoField.DAY_OF_MONTH) - (l * 32L + l2)) / 32L;
        }
        throw new IllegalStateException("ChronoLocalDateImpl only supports Chronologies with 12 months per year");
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof ChronoLocalDate) {
            if (this.compareTo((ChronoLocalDate)object) != 0) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public int hashCode() {
        long l = this.toEpochDay();
        return this.getChronology().hashCode() ^ (int)(l >>> 32 ^ l);
    }

    public D minus(long l, TemporalUnit temporalUnit) {
        return (D)ChronoLocalDate.super.minus(l, temporalUnit);
    }

    public D minus(TemporalAmount temporalAmount) {
        return (D)ChronoLocalDate.super.minus(temporalAmount);
    }

    D minusDays(long l) {
        D d = l == Long.MIN_VALUE ? ((ChronoLocalDateImpl)this.plusDays(Long.MAX_VALUE)).plusDays(1L) : this.plusDays(-l);
        return d;
    }

    D minusMonths(long l) {
        D d = l == Long.MIN_VALUE ? ((ChronoLocalDateImpl)this.plusMonths(Long.MAX_VALUE)).plusMonths(1L) : this.plusMonths(-l);
        return d;
    }

    D minusWeeks(long l) {
        D d = l == Long.MIN_VALUE ? ((ChronoLocalDateImpl)this.plusWeeks(Long.MAX_VALUE)).plusWeeks(1L) : this.plusWeeks(-l);
        return d;
    }

    D minusYears(long l) {
        D d = l == Long.MIN_VALUE ? ((ChronoLocalDateImpl)this.plusYears(Long.MAX_VALUE)).plusYears(1L) : this.plusYears(-l);
        return d;
    }

    public D plus(long l, TemporalUnit temporalUnit) {
        if (temporalUnit instanceof ChronoUnit) {
            Object object = (ChronoUnit)temporalUnit;
            switch (1.$SwitchMap$java$time$temporal$ChronoUnit[((Enum)object).ordinal()]) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unsupported unit: ");
                    ((StringBuilder)object).append(temporalUnit);
                    throw new UnsupportedTemporalTypeException(((StringBuilder)object).toString());
                }
                case 8: {
                    return (D)this.with(ChronoField.ERA, Math.addExact(this.getLong(ChronoField.ERA), l));
                }
                case 7: {
                    return this.plusYears(Math.multiplyExact(l, 1000L));
                }
                case 6: {
                    return this.plusYears(Math.multiplyExact(l, 100L));
                }
                case 5: {
                    return this.plusYears(Math.multiplyExact(l, 10L));
                }
                case 4: {
                    return this.plusYears(l);
                }
                case 3: {
                    return this.plusMonths(l);
                }
                case 2: {
                    return this.plusDays(Math.multiplyExact(l, 7L));
                }
                case 1: 
            }
            return this.plusDays(l);
        }
        return (D)ChronoLocalDate.super.plus(l, temporalUnit);
    }

    public D plus(TemporalAmount temporalAmount) {
        return (D)ChronoLocalDate.super.plus(temporalAmount);
    }

    abstract D plusDays(long var1);

    abstract D plusMonths(long var1);

    D plusWeeks(long l) {
        return this.plusDays(Math.multiplyExact(l, 7L));
    }

    abstract D plusYears(long var1);

    @Override
    public String toString() {
        long l = this.getLong(ChronoField.YEAR_OF_ERA);
        long l2 = this.getLong(ChronoField.MONTH_OF_YEAR);
        long l3 = this.getLong(ChronoField.DAY_OF_MONTH);
        StringBuilder stringBuilder = new StringBuilder(30);
        stringBuilder.append(this.getChronology().toString());
        stringBuilder.append(" ");
        stringBuilder.append(this.getEra());
        stringBuilder.append(" ");
        stringBuilder.append(l);
        String string = "-0";
        String string2 = l2 < 10L ? "-0" : "-";
        stringBuilder.append(string2);
        stringBuilder.append(l2);
        string2 = l3 < 10L ? string : "-";
        stringBuilder.append(string2);
        stringBuilder.append(l3);
        return stringBuilder.toString();
    }

    @Override
    public long until(Temporal object, TemporalUnit temporalUnit) {
        Objects.requireNonNull(object, "endExclusive");
        object = this.getChronology().date((TemporalAccessor)object);
        if (temporalUnit instanceof ChronoUnit) {
            switch ((ChronoUnit)temporalUnit) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unsupported unit: ");
                    ((StringBuilder)object).append(temporalUnit);
                    throw new UnsupportedTemporalTypeException(((StringBuilder)object).toString());
                }
                case ERAS: {
                    return object.getLong(ChronoField.ERA) - this.getLong(ChronoField.ERA);
                }
                case MILLENNIA: {
                    return this.monthsUntil((ChronoLocalDate)object) / 12000L;
                }
                case CENTURIES: {
                    return this.monthsUntil((ChronoLocalDate)object) / 1200L;
                }
                case DECADES: {
                    return this.monthsUntil((ChronoLocalDate)object) / 120L;
                }
                case YEARS: {
                    return this.monthsUntil((ChronoLocalDate)object) / 12L;
                }
                case MONTHS: {
                    return this.monthsUntil((ChronoLocalDate)object);
                }
                case WEEKS: {
                    return this.daysUntil((ChronoLocalDate)object) / 7L;
                }
                case DAYS: 
            }
            return this.daysUntil((ChronoLocalDate)object);
        }
        Objects.requireNonNull(temporalUnit, "unit");
        return temporalUnit.between(this, (Temporal)object);
    }

    public D with(TemporalAdjuster temporalAdjuster) {
        return (D)ChronoLocalDate.super.with(temporalAdjuster);
    }

    public D with(TemporalField temporalField, long l) {
        return (D)ChronoLocalDate.super.with(temporalField, l);
    }

}

