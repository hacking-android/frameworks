/*
 * Decompiled with CFR 0.145.
 */
package java.time.format;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DecimalStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.ValueRange;
import java.time.zone.ZoneRules;
import java.util.Locale;
import java.util.Objects;

final class DateTimePrintContext {
    private DateTimeFormatter formatter;
    private int optional;
    private TemporalAccessor temporal;

    DateTimePrintContext(TemporalAccessor temporalAccessor, DateTimeFormatter dateTimeFormatter) {
        this.temporal = DateTimePrintContext.adjust(temporalAccessor, dateTimeFormatter);
        this.formatter = dateTimeFormatter;
    }

    private static TemporalAccessor adjust(TemporalAccessor temporalAccessor, DateTimeFormatter object) {
        Object object2 = ((DateTimeFormatter)object).getChronology();
        Object object3 = ((DateTimeFormatter)object).getZone();
        if (object2 == null && object3 == null) {
            return temporalAccessor;
        }
        ChronoField[] arrchronoField = temporalAccessor.query(TemporalQueries.chronology());
        ZoneId object42 = temporalAccessor.query(TemporalQueries.zoneId());
        object = object2;
        if (Objects.equals(object2, arrchronoField)) {
            object = null;
        }
        object2 = object3;
        if (Objects.equals(object3, object42)) {
            object2 = null;
        }
        if (object == null && object2 == null) {
            return temporalAccessor;
        }
        object3 = object != null ? object : arrchronoField;
        if (object2 != null) {
            if (temporalAccessor.isSupported(ChronoField.INSTANT_SECONDS)) {
                if (object3 == null) {
                    object3 = IsoChronology.INSTANCE;
                }
                return object3.zonedDateTime(Instant.from(temporalAccessor), (ZoneId)object2);
            }
            if (((ZoneId)object2).normalized() instanceof ZoneOffset && temporalAccessor.isSupported(ChronoField.OFFSET_SECONDS) && temporalAccessor.get(ChronoField.OFFSET_SECONDS) != ((ZoneId)object2).getRules().getOffset(Instant.EPOCH).getTotalSeconds()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unable to apply override zone '");
                ((StringBuilder)object).append(object2);
                ((StringBuilder)object).append("' because the temporal object being formatted has a different offset but does not represent an instant: ");
                ((StringBuilder)object).append(temporalAccessor);
                throw new DateTimeException(((StringBuilder)object).toString());
            }
        }
        if (object2 == null) {
            object2 = object42;
        }
        if (object != null) {
            if (temporalAccessor.isSupported(ChronoField.EPOCH_DAY)) {
                object = object3.date(temporalAccessor);
            } else {
                if (object != IsoChronology.INSTANCE || arrchronoField != null) {
                    for (ChronoField chronoField : ChronoField.values()) {
                        if (!chronoField.isDateBased() || !temporalAccessor.isSupported(chronoField)) continue;
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("Unable to apply override chronology '");
                        ((StringBuilder)object3).append(object);
                        ((StringBuilder)object3).append("' because the temporal object being formatted contains date fields but does not represent a whole date: ");
                        ((StringBuilder)object3).append(temporalAccessor);
                        throw new DateTimeException(((StringBuilder)object3).toString());
                    }
                }
                object = null;
            }
        } else {
            object = null;
        }
        return new TemporalAccessor((ChronoLocalDate)object, temporalAccessor, (Chronology)object3, (ZoneId)object2){
            final /* synthetic */ Chronology val$effectiveChrono;
            final /* synthetic */ ChronoLocalDate val$effectiveDate;
            final /* synthetic */ ZoneId val$effectiveZone;
            final /* synthetic */ TemporalAccessor val$temporal;
            {
                this.val$effectiveDate = chronoLocalDate;
                this.val$temporal = temporalAccessor;
                this.val$effectiveChrono = chronology;
                this.val$effectiveZone = zoneId;
            }

            @Override
            public long getLong(TemporalField temporalField) {
                if (this.val$effectiveDate != null && temporalField.isDateBased()) {
                    return this.val$effectiveDate.getLong(temporalField);
                }
                return this.val$temporal.getLong(temporalField);
            }

            @Override
            public boolean isSupported(TemporalField temporalField) {
                if (this.val$effectiveDate != null && temporalField.isDateBased()) {
                    return this.val$effectiveDate.isSupported(temporalField);
                }
                return this.val$temporal.isSupported(temporalField);
            }

            @Override
            public <R> R query(TemporalQuery<R> temporalQuery) {
                if (temporalQuery == TemporalQueries.chronology()) {
                    return (R)this.val$effectiveChrono;
                }
                if (temporalQuery == TemporalQueries.zoneId()) {
                    return (R)this.val$effectiveZone;
                }
                if (temporalQuery == TemporalQueries.precision()) {
                    return this.val$temporal.query(temporalQuery);
                }
                return temporalQuery.queryFrom(this);
            }

            @Override
            public ValueRange range(TemporalField temporalField) {
                if (this.val$effectiveDate != null && temporalField.isDateBased()) {
                    return this.val$effectiveDate.range(temporalField);
                }
                return this.val$temporal.range(temporalField);
            }
        };
    }

    void endOptional() {
        --this.optional;
    }

    DecimalStyle getDecimalStyle() {
        return this.formatter.getDecimalStyle();
    }

    Locale getLocale() {
        return this.formatter.getLocale();
    }

    TemporalAccessor getTemporal() {
        return this.temporal;
    }

    Long getValue(TemporalField temporalField) {
        long l;
        try {
            l = this.temporal.getLong(temporalField);
        }
        catch (DateTimeException dateTimeException) {
            if (this.optional > 0) {
                return null;
            }
            throw dateTimeException;
        }
        return l;
    }

    <R> R getValue(TemporalQuery<R> object) {
        if ((object = this.temporal.query(object)) == null && this.optional == 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to extract value: ");
            ((StringBuilder)object).append(this.temporal.getClass());
            throw new DateTimeException(((StringBuilder)object).toString());
        }
        return (R)object;
    }

    void startOptional() {
        ++this.optional;
    }

    public String toString() {
        return this.temporal.toString();
    }

}

