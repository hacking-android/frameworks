/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.AbstractChronology;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.IsoChronology;
import java.time.chrono.MinguoDate;
import java.time.chrono.MinguoEra;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class MinguoChronology
extends AbstractChronology
implements Serializable {
    public static final MinguoChronology INSTANCE = new MinguoChronology();
    static final int YEARS_DIFFERENCE = 1911;
    private static final long serialVersionUID = 1039765215346859963L;

    private MinguoChronology() {
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    @Override
    public MinguoDate date(int n, int n2, int n3) {
        return new MinguoDate(LocalDate.of(n + 1911, n2, n3));
    }

    @Override
    public MinguoDate date(Era era, int n, int n2, int n3) {
        return this.date(this.prolepticYear(era, n), n2, n3);
    }

    @Override
    public MinguoDate date(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof MinguoDate) {
            return (MinguoDate)temporalAccessor;
        }
        return new MinguoDate(LocalDate.from(temporalAccessor));
    }

    @Override
    public MinguoDate dateEpochDay(long l) {
        return new MinguoDate(LocalDate.ofEpochDay(l));
    }

    @Override
    public MinguoDate dateNow() {
        return this.dateNow(Clock.systemDefaultZone());
    }

    @Override
    public MinguoDate dateNow(Clock clock) {
        return this.date(LocalDate.now(clock));
    }

    @Override
    public MinguoDate dateNow(ZoneId zoneId) {
        return this.dateNow(Clock.system(zoneId));
    }

    @Override
    public MinguoDate dateYearDay(int n, int n2) {
        return new MinguoDate(LocalDate.ofYearDay(n + 1911, n2));
    }

    @Override
    public MinguoDate dateYearDay(Era era, int n, int n2) {
        return this.dateYearDay(this.prolepticYear(era, n), n2);
    }

    @Override
    public MinguoEra eraOf(int n) {
        return MinguoEra.of(n);
    }

    @Override
    public List<Era> eras() {
        return Arrays.asList(MinguoEra.values());
    }

    @Override
    public String getCalendarType() {
        return "roc";
    }

    @Override
    public String getId() {
        return "Minguo";
    }

    @Override
    public boolean isLeapYear(long l) {
        return IsoChronology.INSTANCE.isLeapYear(1911L + l);
    }

    public ChronoLocalDateTime<MinguoDate> localDateTime(TemporalAccessor temporalAccessor) {
        return super.localDateTime(temporalAccessor);
    }

    @Override
    public int prolepticYear(Era era, int n) {
        if (era instanceof MinguoEra) {
            if (era != MinguoEra.ROC) {
                n = 1 - n;
            }
            return n;
        }
        throw new ClassCastException("Era must be MinguoEra");
    }

    @Override
    public ValueRange range(ChronoField object) {
        int n = 1.$SwitchMap$java$time$temporal$ChronoField[((Enum)object).ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    return ((ChronoField)object).range();
                }
                object = ChronoField.YEAR.range();
                return ValueRange.of(((ValueRange)object).getMinimum() - 1911L, ((ValueRange)object).getMaximum() - 1911L);
            }
            object = ChronoField.YEAR.range();
            return ValueRange.of(1L, ((ValueRange)object).getMaximum() - 1911L, -((ValueRange)object).getMinimum() + 1L + 1911L);
        }
        object = ChronoField.PROLEPTIC_MONTH.range();
        return ValueRange.of(((ValueRange)object).getMinimum() - 22932L, ((ValueRange)object).getMaximum() - 22932L);
    }

    @Override
    public MinguoDate resolveDate(Map<TemporalField, Long> map, ResolverStyle resolverStyle) {
        return (MinguoDate)super.resolveDate(map, resolverStyle);
    }

    @Override
    Object writeReplace() {
        return super.writeReplace();
    }

    public ChronoZonedDateTime<MinguoDate> zonedDateTime(Instant instant, ZoneId zoneId) {
        return super.zonedDateTime(instant, zoneId);
    }

    public ChronoZonedDateTime<MinguoDate> zonedDateTime(TemporalAccessor temporalAccessor) {
        return super.zonedDateTime(temporalAccessor);
    }

}

