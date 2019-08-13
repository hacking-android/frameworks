/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.chrono.AbstractChronology;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoLocalDateTimeImpl;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.ChronoPeriodImpl;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.ChronoZonedDateTimeImpl;
import java.time.chrono.Era;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public interface Chronology
extends Comparable<Chronology> {
    public static Chronology from(TemporalAccessor object) {
        Objects.requireNonNull(object, "temporal");
        object = object.query(TemporalQueries.chronology());
        if (object == null) {
            object = IsoChronology.INSTANCE;
        }
        return object;
    }

    public static Set<Chronology> getAvailableChronologies() {
        return AbstractChronology.getAvailableChronologies();
    }

    public static Chronology of(String string) {
        return AbstractChronology.of(string);
    }

    public static Chronology ofLocale(Locale locale) {
        return AbstractChronology.ofLocale(locale);
    }

    @Override
    public int compareTo(Chronology var1);

    public ChronoLocalDate date(int var1, int var2, int var3);

    default public ChronoLocalDate date(Era era, int n, int n2, int n3) {
        return this.date(this.prolepticYear(era, n), n2, n3);
    }

    public ChronoLocalDate date(TemporalAccessor var1);

    public ChronoLocalDate dateEpochDay(long var1);

    default public ChronoLocalDate dateNow() {
        return this.dateNow(Clock.systemDefaultZone());
    }

    default public ChronoLocalDate dateNow(Clock clock) {
        Objects.requireNonNull(clock, "clock");
        return this.date(LocalDate.now(clock));
    }

    default public ChronoLocalDate dateNow(ZoneId zoneId) {
        return this.dateNow(Clock.system(zoneId));
    }

    public ChronoLocalDate dateYearDay(int var1, int var2);

    default public ChronoLocalDate dateYearDay(Era era, int n, int n2) {
        return this.dateYearDay(this.prolepticYear(era, n), n2);
    }

    public boolean equals(Object var1);

    public Era eraOf(int var1);

    public List<Era> eras();

    public String getCalendarType();

    default public String getDisplayName(TextStyle textStyle, Locale locale) {
        TemporalAccessor temporalAccessor = new TemporalAccessor(){

            @Override
            public long getLong(TemporalField temporalField) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported field: ");
                stringBuilder.append(temporalField);
                throw new UnsupportedTemporalTypeException(stringBuilder.toString());
            }

            @Override
            public boolean isSupported(TemporalField temporalField) {
                return false;
            }

            @Override
            public <R> R query(TemporalQuery<R> temporalQuery) {
                if (temporalQuery == TemporalQueries.chronology()) {
                    return (R)Chronology.this;
                }
                return TemporalAccessor.super.query(temporalQuery);
            }
        };
        return new DateTimeFormatterBuilder().appendChronologyText(textStyle).toFormatter(locale).format(temporalAccessor);
    }

    public String getId();

    public int hashCode();

    public boolean isLeapYear(long var1);

    default public ChronoLocalDateTime<? extends ChronoLocalDate> localDateTime(TemporalAccessor temporalAccessor) {
        try {
            ChronoLocalDateTime<?> chronoLocalDateTime = this.date(temporalAccessor).atTime(LocalTime.from(temporalAccessor));
            return chronoLocalDateTime;
        }
        catch (DateTimeException dateTimeException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to obtain ChronoLocalDateTime from TemporalAccessor: ");
            stringBuilder.append(temporalAccessor.getClass());
            throw new DateTimeException(stringBuilder.toString(), dateTimeException);
        }
    }

    default public ChronoPeriod period(int n, int n2, int n3) {
        return new ChronoPeriodImpl(this, n, n2, n3);
    }

    public int prolepticYear(Era var1, int var2);

    public ValueRange range(ChronoField var1);

    public ChronoLocalDate resolveDate(Map<TemporalField, Long> var1, ResolverStyle var2);

    public String toString();

    default public ChronoZonedDateTime<? extends ChronoLocalDate> zonedDateTime(Instant instant, ZoneId zoneId) {
        return ChronoZonedDateTimeImpl.ofInstant(this, instant, zoneId);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    default public ChronoZonedDateTime<? extends ChronoLocalDate> zonedDateTime(TemporalAccessor temporalAccessor) {
        ZoneId object = ZoneId.from(temporalAccessor);
        {
            catch (DateTimeException dateTimeException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to obtain ChronoZonedDateTime from TemporalAccessor: ");
                stringBuilder.append(temporalAccessor.getClass());
                throw new DateTimeException(stringBuilder.toString(), dateTimeException);
            }
        }
        try {
            return this.zonedDateTime(Instant.from(temporalAccessor), object);
        }
        catch (DateTimeException dateTimeException) {
            return ChronoZonedDateTimeImpl.ofBest(ChronoLocalDateTimeImpl.ensureValid(this, this.localDateTime(temporalAccessor)), object, null);
        }
    }

}

