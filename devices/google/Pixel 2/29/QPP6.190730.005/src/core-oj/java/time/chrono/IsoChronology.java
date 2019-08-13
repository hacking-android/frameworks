/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.AbstractChronology;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class IsoChronology
extends AbstractChronology
implements Serializable {
    public static final IsoChronology INSTANCE = new IsoChronology();
    private static final long serialVersionUID = -1440403870442975015L;

    private IsoChronology() {
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    @Override
    public LocalDate date(int n, int n2, int n3) {
        return LocalDate.of(n, n2, n3);
    }

    @Override
    public LocalDate date(Era era, int n, int n2, int n3) {
        return this.date(this.prolepticYear(era, n), n2, n3);
    }

    @Override
    public LocalDate date(TemporalAccessor temporalAccessor) {
        return LocalDate.from(temporalAccessor);
    }

    @Override
    public LocalDate dateEpochDay(long l) {
        return LocalDate.ofEpochDay(l);
    }

    @Override
    public LocalDate dateNow() {
        return this.dateNow(Clock.systemDefaultZone());
    }

    @Override
    public LocalDate dateNow(Clock clock) {
        Objects.requireNonNull(clock, "clock");
        return this.date(LocalDate.now(clock));
    }

    @Override
    public LocalDate dateNow(ZoneId zoneId) {
        return this.dateNow(Clock.system(zoneId));
    }

    @Override
    public LocalDate dateYearDay(int n, int n2) {
        return LocalDate.ofYearDay(n, n2);
    }

    @Override
    public LocalDate dateYearDay(Era era, int n, int n2) {
        return this.dateYearDay(this.prolepticYear(era, n), n2);
    }

    @Override
    public IsoEra eraOf(int n) {
        return IsoEra.of(n);
    }

    @Override
    public List<Era> eras() {
        return Arrays.asList(IsoEra.values());
    }

    @Override
    public String getCalendarType() {
        return "iso8601";
    }

    @Override
    public String getId() {
        return "ISO";
    }

    @Override
    public boolean isLeapYear(long l) {
        boolean bl = (3L & l) == 0L && (l % 100L != 0L || l % 400L == 0L);
        return bl;
    }

    public LocalDateTime localDateTime(TemporalAccessor temporalAccessor) {
        return LocalDateTime.from(temporalAccessor);
    }

    @Override
    public Period period(int n, int n2, int n3) {
        return Period.of(n, n2, n3);
    }

    @Override
    public int prolepticYear(Era era, int n) {
        if (era instanceof IsoEra) {
            if (era != IsoEra.CE) {
                n = 1 - n;
            }
            return n;
        }
        throw new ClassCastException("Era must be IsoEra");
    }

    @Override
    public ValueRange range(ChronoField chronoField) {
        return chronoField.range();
    }

    @Override
    public LocalDate resolveDate(Map<TemporalField, Long> map, ResolverStyle resolverStyle) {
        return (LocalDate)super.resolveDate(map, resolverStyle);
    }

    @Override
    void resolveProlepticMonth(Map<TemporalField, Long> map, ResolverStyle resolverStyle) {
        Long l = map.remove(ChronoField.PROLEPTIC_MONTH);
        if (l != null) {
            if (resolverStyle != ResolverStyle.LENIENT) {
                ChronoField.PROLEPTIC_MONTH.checkValidValue(l);
            }
            this.addFieldValue(map, ChronoField.MONTH_OF_YEAR, Math.floorMod(l, 12L) + 1L);
            this.addFieldValue(map, ChronoField.YEAR, Math.floorDiv(l, 12L));
        }
    }

    @Override
    LocalDate resolveYMD(Map<TemporalField, Long> map, ResolverStyle resolverStyle) {
        int n;
        int n2 = ChronoField.YEAR.checkValidIntValue(map.remove(ChronoField.YEAR));
        if (resolverStyle == ResolverStyle.LENIENT) {
            long l = Math.subtractExact(map.remove(ChronoField.MONTH_OF_YEAR), 1L);
            long l2 = Math.subtractExact(map.remove(ChronoField.DAY_OF_MONTH), 1L);
            return LocalDate.of(n2, 1, 1).plusMonths(l).plusDays(l2);
        }
        int n3 = ChronoField.MONTH_OF_YEAR.checkValidIntValue(map.remove(ChronoField.MONTH_OF_YEAR));
        int n4 = n = ChronoField.DAY_OF_MONTH.checkValidIntValue(map.remove(ChronoField.DAY_OF_MONTH));
        if (resolverStyle == ResolverStyle.SMART) {
            if (n3 != 4 && n3 != 6 && n3 != 9 && n3 != 11) {
                n4 = n;
                if (n3 == 2) {
                    n4 = Math.min(n, Month.FEBRUARY.length(Year.isLeap(n2)));
                }
            } else {
                n4 = Math.min(n, 30);
            }
        }
        return LocalDate.of(n2, n3, n4);
    }

    @Override
    LocalDate resolveYearOfEra(Map<TemporalField, Long> object, ResolverStyle enum_) {
        block7 : {
            block5 : {
                Long l;
                block9 : {
                    Long l2;
                    block8 : {
                        block6 : {
                            l2 = object.remove(ChronoField.YEAR_OF_ERA);
                            if (l2 == null) break block5;
                            if (enum_ != ResolverStyle.LENIENT) {
                                ChronoField.YEAR_OF_ERA.checkValidValue(l2);
                            }
                            if ((l = object.remove(ChronoField.ERA)) != null) break block6;
                            l = object.get(ChronoField.YEAR);
                            if (enum_ == ResolverStyle.STRICT) {
                                if (l != null) {
                                    enum_ = ChronoField.YEAR;
                                    long l3 = l > 0L ? l2 : Math.subtractExact(1L, l2);
                                    this.addFieldValue((Map<TemporalField, Long>)object, (ChronoField)enum_, l3);
                                } else {
                                    object.put((TemporalField)ChronoField.YEAR_OF_ERA, (Long)l2);
                                }
                            } else {
                                enum_ = ChronoField.YEAR;
                                long l4 = l != null && l <= 0L ? Math.subtractExact(1L, l2) : l2;
                                this.addFieldValue((Map<TemporalField, Long>)object, (ChronoField)enum_, l4);
                            }
                            break block7;
                        }
                        if (l != 1L) break block8;
                        this.addFieldValue((Map<TemporalField, Long>)object, ChronoField.YEAR, l2);
                        break block7;
                    }
                    if (l != 0L) break block9;
                    this.addFieldValue((Map<TemporalField, Long>)object, ChronoField.YEAR, Math.subtractExact(1L, l2));
                    break block7;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid value for era: ");
                ((StringBuilder)object).append(l);
                throw new DateTimeException(((StringBuilder)object).toString());
            }
            if (!object.containsKey(ChronoField.ERA)) break block7;
            ChronoField.ERA.checkValidValue(object.get(ChronoField.ERA));
        }
        return null;
    }

    @Override
    Object writeReplace() {
        return super.writeReplace();
    }

    public ZonedDateTime zonedDateTime(Instant instant, ZoneId zoneId) {
        return ZonedDateTime.ofInstant(instant, zoneId);
    }

    public ZonedDateTime zonedDateTime(TemporalAccessor temporalAccessor) {
        return ZonedDateTime.from(temporalAccessor);
    }
}

