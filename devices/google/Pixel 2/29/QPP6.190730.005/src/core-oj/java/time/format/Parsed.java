/*
 * Decompiled with CFR 0.145.
 */
package java.time.format;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Chronology;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

final class Parsed
implements TemporalAccessor {
    Chronology chrono;
    private ChronoLocalDate date;
    Period excessDays = Period.ZERO;
    final Map<TemporalField, Long> fieldValues = new HashMap<TemporalField, Long>();
    boolean leapSecond;
    private ResolverStyle resolverStyle;
    private LocalTime time;
    ZoneId zone;

    Parsed() {
    }

    private void crossCheck() {
        Comparable<ChronoLocalDate> comparable = this.date;
        if (comparable != null) {
            this.crossCheck((TemporalAccessor)((Object)comparable));
        }
        if ((comparable = this.time) != null) {
            this.crossCheck((TemporalAccessor)((Object)comparable));
            if (this.date != null && this.fieldValues.size() > 0) {
                this.crossCheck(this.date.atTime(this.time));
            }
        }
    }

    private void crossCheck(TemporalAccessor temporalAccessor) {
        Object object = this.fieldValues.entrySet().iterator();
        while (object.hasNext()) {
            long l;
            Map.Entry<TemporalField, Long> entry = object.next();
            TemporalField temporalField = entry.getKey();
            if (!temporalAccessor.isSupported(temporalField)) continue;
            try {
                l = temporalAccessor.getLong(temporalField);
            }
            catch (RuntimeException runtimeException) {
                continue;
            }
            long l2 = entry.getValue();
            if (l == l2) {
                object.remove();
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Conflict found: Field ");
            ((StringBuilder)object).append(temporalField);
            ((StringBuilder)object).append(" ");
            ((StringBuilder)object).append(l);
            ((StringBuilder)object).append(" differs from ");
            ((StringBuilder)object).append(temporalField);
            ((StringBuilder)object).append(" ");
            ((StringBuilder)object).append(l2);
            ((StringBuilder)object).append(" derived from ");
            ((StringBuilder)object).append(temporalAccessor);
            throw new DateTimeException(((StringBuilder)object).toString());
        }
    }

    private void resolveDateFields() {
        this.updateCheckConflict(this.chrono.resolveDate(this.fieldValues, this.resolverStyle));
    }

    private void resolveFields() {
        this.resolveInstantFields();
        this.resolveDateFields();
        this.resolveTimeFields();
        if (this.fieldValues.size() > 0) {
            int n = 0;
            block0 : while (n < 50) {
                Iterator<Map.Entry<TemporalField, Long>> iterator = this.fieldValues.entrySet().iterator();
                while (iterator.hasNext()) {
                    Object object = iterator.next().getKey();
                    TemporalAccessor temporalAccessor = object.resolve(this.fieldValues, this, this.resolverStyle);
                    if (temporalAccessor != null) {
                        object = temporalAccessor;
                        if (temporalAccessor instanceof ChronoZonedDateTime) {
                            temporalAccessor = (ChronoZonedDateTime)temporalAccessor;
                            object = this.zone;
                            if (object == null) {
                                this.zone = temporalAccessor.getZone();
                            } else if (!((ZoneId)object).equals(temporalAccessor.getZone())) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("ChronoZonedDateTime must use the effective parsed zone: ");
                                ((StringBuilder)object).append(this.zone);
                                throw new DateTimeException(((StringBuilder)object).toString());
                            }
                            object = temporalAccessor.toLocalDateTime();
                        }
                        if (object instanceof ChronoLocalDateTime) {
                            object = (ChronoLocalDateTime)object;
                            this.updateCheckConflict(object.toLocalTime(), Period.ZERO);
                            this.updateCheckConflict((ChronoLocalDate)object.toLocalDate());
                            ++n;
                            continue block0;
                        }
                        if (object instanceof ChronoLocalDate) {
                            this.updateCheckConflict((ChronoLocalDate)object);
                            ++n;
                            continue block0;
                        }
                        if (object instanceof LocalTime) {
                            this.updateCheckConflict((LocalTime)object, Period.ZERO);
                            ++n;
                            continue block0;
                        }
                        throw new DateTimeException("Method resolve() can only return ChronoZonedDateTime, ChronoLocalDateTime, ChronoLocalDate or LocalTime");
                    }
                    if (this.fieldValues.containsKey(object)) continue;
                    ++n;
                    continue block0;
                }
                break block0;
            }
            if (n != 50) {
                if (n > 0) {
                    this.resolveInstantFields();
                    this.resolveDateFields();
                    this.resolveTimeFields();
                }
            } else {
                throw new DateTimeException("One of the parsed fields has an incorrectly implemented resolve method");
            }
        }
    }

    private void resolveFractional() {
        if (this.time == null && (this.fieldValues.containsKey(ChronoField.INSTANT_SECONDS) || this.fieldValues.containsKey(ChronoField.SECOND_OF_DAY) || this.fieldValues.containsKey(ChronoField.SECOND_OF_MINUTE))) {
            if (this.fieldValues.containsKey(ChronoField.NANO_OF_SECOND)) {
                long l = this.fieldValues.get(ChronoField.NANO_OF_SECOND);
                this.fieldValues.put(ChronoField.MICRO_OF_SECOND, l / 1000L);
                this.fieldValues.put(ChronoField.MILLI_OF_SECOND, l / 1000000L);
            } else {
                this.fieldValues.put(ChronoField.NANO_OF_SECOND, 0L);
                this.fieldValues.put(ChronoField.MICRO_OF_SECOND, 0L);
                this.fieldValues.put(ChronoField.MILLI_OF_SECOND, 0L);
            }
        }
    }

    private void resolveInstant() {
        LocalTime localTime;
        Comparable<ChronoLocalDate> comparable = this.date;
        if (comparable != null && (localTime = this.time) != null) {
            if (this.zone != null) {
                long l = comparable.atTime(localTime).atZone(this.zone).getLong(ChronoField.INSTANT_SECONDS);
                this.fieldValues.put(ChronoField.INSTANT_SECONDS, l);
            } else {
                comparable = this.fieldValues.get(ChronoField.OFFSET_SECONDS);
                if (comparable != null) {
                    comparable = ZoneOffset.ofTotalSeconds(((Long)comparable).intValue());
                    long l = this.date.atTime(this.time).atZone((ZoneId)((Object)comparable)).getLong(ChronoField.INSTANT_SECONDS);
                    this.fieldValues.put(ChronoField.INSTANT_SECONDS, l);
                }
            }
        }
    }

    private void resolveInstantFields() {
        if (this.fieldValues.containsKey(ChronoField.INSTANT_SECONDS)) {
            Serializable serializable = this.zone;
            if (serializable != null) {
                this.resolveInstantFields0((ZoneId)serializable);
            } else {
                serializable = this.fieldValues.get(ChronoField.OFFSET_SECONDS);
                if (serializable != null) {
                    this.resolveInstantFields0(ZoneOffset.ofTotalSeconds(((Long)serializable).intValue()));
                }
            }
        }
    }

    private void resolveInstantFields0(ZoneId object) {
        Instant instant = Instant.ofEpochSecond(this.fieldValues.remove(ChronoField.INSTANT_SECONDS));
        object = this.chrono.zonedDateTime(instant, (ZoneId)object);
        this.updateCheckConflict((ChronoLocalDate)object.toLocalDate());
        this.updateCheckConflict(ChronoField.INSTANT_SECONDS, ChronoField.SECOND_OF_DAY, Long.valueOf(object.toLocalTime().toSecondOfDay()));
    }

    private void resolvePeriod() {
        if (this.date != null && this.time != null && !this.excessDays.isZero()) {
            this.date = this.date.plus(this.excessDays);
            this.excessDays = Period.ZERO;
        }
    }

    private void resolveTime(long l, long l2, long l3, long l4) {
        if (this.resolverStyle == ResolverStyle.LENIENT) {
            l = Math.addExact(Math.addExact(Math.addExact(Math.multiplyExact(l, 3600000000000L), Math.multiplyExact(l2, 60000000000L)), Math.multiplyExact(l3, 1000000000L)), l4);
            int n = (int)Math.floorDiv(l, 86400000000000L);
            this.updateCheckConflict(LocalTime.ofNanoOfDay(Math.floorMod(l, 86400000000000L)), Period.ofDays(n));
        } else {
            int n = ChronoField.MINUTE_OF_HOUR.checkValidIntValue(l2);
            int n2 = ChronoField.NANO_OF_SECOND.checkValidIntValue(l4);
            if (this.resolverStyle == ResolverStyle.SMART && l == 24L && n == 0 && l3 == 0L && n2 == 0) {
                this.updateCheckConflict(LocalTime.MIDNIGHT, Period.ofDays(1));
            } else {
                this.updateCheckConflict(LocalTime.of(ChronoField.HOUR_OF_DAY.checkValidIntValue(l), n, ChronoField.SECOND_OF_MINUTE.checkValidIntValue(l3), n2), Period.ZERO);
            }
        }
    }

    private void resolveTimeFields() {
        ChronoField chronoField;
        long l;
        ChronoField chronoField2;
        boolean bl = this.fieldValues.containsKey(ChronoField.CLOCK_HOUR_OF_DAY);
        long l2 = 0L;
        if (bl) {
            l = this.fieldValues.remove(ChronoField.CLOCK_HOUR_OF_DAY);
            if (this.resolverStyle == ResolverStyle.STRICT || this.resolverStyle == ResolverStyle.SMART && l != 0L) {
                ChronoField.CLOCK_HOUR_OF_DAY.checkValidValue(l);
            }
            chronoField = ChronoField.CLOCK_HOUR_OF_DAY;
            chronoField2 = ChronoField.HOUR_OF_DAY;
            if (l == 24L) {
                l = 0L;
            }
            this.updateCheckConflict(chronoField, chronoField2, l);
        }
        if (this.fieldValues.containsKey(ChronoField.CLOCK_HOUR_OF_AMPM)) {
            l = this.fieldValues.remove(ChronoField.CLOCK_HOUR_OF_AMPM);
            if (this.resolverStyle == ResolverStyle.STRICT || this.resolverStyle == ResolverStyle.SMART && l != 0L) {
                ChronoField.CLOCK_HOUR_OF_AMPM.checkValidValue(l);
            }
            chronoField2 = ChronoField.CLOCK_HOUR_OF_AMPM;
            chronoField = ChronoField.HOUR_OF_AMPM;
            if (l == 12L) {
                l = l2;
            }
            this.updateCheckConflict(chronoField2, chronoField, l);
        }
        if (this.fieldValues.containsKey(ChronoField.AMPM_OF_DAY) && this.fieldValues.containsKey(ChronoField.HOUR_OF_AMPM)) {
            l = this.fieldValues.remove(ChronoField.AMPM_OF_DAY);
            l2 = this.fieldValues.remove(ChronoField.HOUR_OF_AMPM);
            if (this.resolverStyle == ResolverStyle.LENIENT) {
                this.updateCheckConflict(ChronoField.AMPM_OF_DAY, ChronoField.HOUR_OF_DAY, Math.addExact(Math.multiplyExact(l, 12L), l2));
            } else {
                ChronoField.AMPM_OF_DAY.checkValidValue(l);
                ChronoField.HOUR_OF_AMPM.checkValidValue(l);
                this.updateCheckConflict(ChronoField.AMPM_OF_DAY, ChronoField.HOUR_OF_DAY, 12L * l + l2);
            }
        }
        if (this.fieldValues.containsKey(ChronoField.NANO_OF_DAY)) {
            l = this.fieldValues.remove(ChronoField.NANO_OF_DAY);
            if (this.resolverStyle != ResolverStyle.LENIENT) {
                ChronoField.NANO_OF_DAY.checkValidValue(l);
            }
            this.updateCheckConflict(ChronoField.NANO_OF_DAY, ChronoField.HOUR_OF_DAY, l / 3600000000000L);
            this.updateCheckConflict(ChronoField.NANO_OF_DAY, ChronoField.MINUTE_OF_HOUR, l / 60000000000L % 60L);
            this.updateCheckConflict(ChronoField.NANO_OF_DAY, ChronoField.SECOND_OF_MINUTE, l / 1000000000L % 60L);
            this.updateCheckConflict(ChronoField.NANO_OF_DAY, ChronoField.NANO_OF_SECOND, l % 1000000000L);
        }
        if (this.fieldValues.containsKey(ChronoField.MICRO_OF_DAY)) {
            l = this.fieldValues.remove(ChronoField.MICRO_OF_DAY);
            if (this.resolverStyle != ResolverStyle.LENIENT) {
                ChronoField.MICRO_OF_DAY.checkValidValue(l);
            }
            this.updateCheckConflict(ChronoField.MICRO_OF_DAY, ChronoField.SECOND_OF_DAY, l / 1000000L);
            this.updateCheckConflict(ChronoField.MICRO_OF_DAY, ChronoField.MICRO_OF_SECOND, l % 1000000L);
        }
        if (this.fieldValues.containsKey(ChronoField.MILLI_OF_DAY)) {
            l = this.fieldValues.remove(ChronoField.MILLI_OF_DAY);
            if (this.resolverStyle != ResolverStyle.LENIENT) {
                ChronoField.MILLI_OF_DAY.checkValidValue(l);
            }
            this.updateCheckConflict(ChronoField.MILLI_OF_DAY, ChronoField.SECOND_OF_DAY, l / 1000L);
            this.updateCheckConflict(ChronoField.MILLI_OF_DAY, ChronoField.MILLI_OF_SECOND, l % 1000L);
        }
        if (this.fieldValues.containsKey(ChronoField.SECOND_OF_DAY)) {
            l = this.fieldValues.remove(ChronoField.SECOND_OF_DAY);
            if (this.resolverStyle != ResolverStyle.LENIENT) {
                ChronoField.SECOND_OF_DAY.checkValidValue(l);
            }
            this.updateCheckConflict(ChronoField.SECOND_OF_DAY, ChronoField.HOUR_OF_DAY, l / 3600L);
            this.updateCheckConflict(ChronoField.SECOND_OF_DAY, ChronoField.MINUTE_OF_HOUR, l / 60L % 60L);
            this.updateCheckConflict(ChronoField.SECOND_OF_DAY, ChronoField.SECOND_OF_MINUTE, l % 60L);
        }
        if (this.fieldValues.containsKey(ChronoField.MINUTE_OF_DAY)) {
            l = this.fieldValues.remove(ChronoField.MINUTE_OF_DAY);
            if (this.resolverStyle != ResolverStyle.LENIENT) {
                ChronoField.MINUTE_OF_DAY.checkValidValue(l);
            }
            this.updateCheckConflict(ChronoField.MINUTE_OF_DAY, ChronoField.HOUR_OF_DAY, l / 60L);
            this.updateCheckConflict(ChronoField.MINUTE_OF_DAY, ChronoField.MINUTE_OF_HOUR, l % 60L);
        }
        if (this.fieldValues.containsKey(ChronoField.NANO_OF_SECOND)) {
            l2 = this.fieldValues.get(ChronoField.NANO_OF_SECOND);
            if (this.resolverStyle != ResolverStyle.LENIENT) {
                ChronoField.NANO_OF_SECOND.checkValidValue(l2);
            }
            l = l2;
            if (this.fieldValues.containsKey(ChronoField.MICRO_OF_SECOND)) {
                l = this.fieldValues.remove(ChronoField.MICRO_OF_SECOND);
                if (this.resolverStyle != ResolverStyle.LENIENT) {
                    ChronoField.MICRO_OF_SECOND.checkValidValue(l);
                }
                l = l * 1000L + l2 % 1000L;
                this.updateCheckConflict(ChronoField.MICRO_OF_SECOND, ChronoField.NANO_OF_SECOND, l);
            }
            if (this.fieldValues.containsKey(ChronoField.MILLI_OF_SECOND)) {
                l2 = this.fieldValues.remove(ChronoField.MILLI_OF_SECOND);
                if (this.resolverStyle != ResolverStyle.LENIENT) {
                    ChronoField.MILLI_OF_SECOND.checkValidValue(l2);
                }
                this.updateCheckConflict(ChronoField.MILLI_OF_SECOND, ChronoField.NANO_OF_SECOND, l2 * 1000000L + l % 1000000L);
            }
        }
        if (this.fieldValues.containsKey(ChronoField.HOUR_OF_DAY) && this.fieldValues.containsKey(ChronoField.MINUTE_OF_HOUR) && this.fieldValues.containsKey(ChronoField.SECOND_OF_MINUTE) && this.fieldValues.containsKey(ChronoField.NANO_OF_SECOND)) {
            this.resolveTime(this.fieldValues.remove(ChronoField.HOUR_OF_DAY), this.fieldValues.remove(ChronoField.MINUTE_OF_HOUR), this.fieldValues.remove(ChronoField.SECOND_OF_MINUTE), this.fieldValues.remove(ChronoField.NANO_OF_SECOND));
        }
    }

    private void resolveTimeLenient() {
        Object object;
        if (this.time == null) {
            long l;
            if (this.fieldValues.containsKey(ChronoField.MILLI_OF_SECOND)) {
                l = this.fieldValues.remove(ChronoField.MILLI_OF_SECOND);
                if (this.fieldValues.containsKey(ChronoField.MICRO_OF_SECOND)) {
                    l = l * 1000L + this.fieldValues.get(ChronoField.MICRO_OF_SECOND) % 1000L;
                    this.updateCheckConflict(ChronoField.MILLI_OF_SECOND, ChronoField.MICRO_OF_SECOND, l);
                    this.fieldValues.remove(ChronoField.MICRO_OF_SECOND);
                    this.fieldValues.put(ChronoField.NANO_OF_SECOND, 1000L * l);
                } else {
                    this.fieldValues.put(ChronoField.NANO_OF_SECOND, 1000000L * l);
                }
            } else if (this.fieldValues.containsKey(ChronoField.MICRO_OF_SECOND)) {
                l = this.fieldValues.remove(ChronoField.MICRO_OF_SECOND);
                this.fieldValues.put(ChronoField.NANO_OF_SECOND, 1000L * l);
            }
            Long l2 = this.fieldValues.get(ChronoField.HOUR_OF_DAY);
            if (l2 != null) {
                Long l3 = this.fieldValues.get(ChronoField.MINUTE_OF_HOUR);
                Map.Entry<TemporalField, Long> entry = this.fieldValues.get(ChronoField.SECOND_OF_MINUTE);
                object = this.fieldValues.get(ChronoField.NANO_OF_SECOND);
                if (l3 == null && (entry != null || object != null) || l3 != null && entry == null && object != null) {
                    return;
                }
                long l4 = 0L;
                l = l3 != null ? l3 : 0L;
                long l5 = entry != null ? (Long)((Object)entry) : 0L;
                if (object != null) {
                    l4 = (Long)object;
                }
                this.resolveTime(l2, l, l5, l4);
                this.fieldValues.remove(ChronoField.HOUR_OF_DAY);
                this.fieldValues.remove(ChronoField.MINUTE_OF_HOUR);
                this.fieldValues.remove(ChronoField.SECOND_OF_MINUTE);
                this.fieldValues.remove(ChronoField.NANO_OF_SECOND);
            }
        }
        if (this.resolverStyle != ResolverStyle.LENIENT && this.fieldValues.size() > 0) {
            for (Map.Entry<TemporalField, Long> entry : this.fieldValues.entrySet()) {
                object = entry.getKey();
                if (!(object instanceof ChronoField) || !object.isTimeBased()) continue;
                ((ChronoField)object).checkValidValue((Long)entry.getValue());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateCheckConflict(LocalTime serializable, Period serializable2) {
        LocalTime localTime = this.time;
        if (localTime == null) {
            this.time = serializable;
            this.excessDays = serializable2;
            return;
        }
        if (!localTime.equals(serializable)) {
            serializable2 = new StringBuilder();
            ((StringBuilder)serializable2).append("Conflict found: Fields resolved to different times: ");
            ((StringBuilder)serializable2).append(this.time);
            ((StringBuilder)serializable2).append(" ");
            ((StringBuilder)serializable2).append(serializable);
            throw new DateTimeException(((StringBuilder)serializable2).toString());
        }
        if (!(this.excessDays.isZero() || ((Period)serializable2).isZero() || this.excessDays.equals(serializable2))) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Conflict found: Fields resolved to different excess periods: ");
            ((StringBuilder)serializable).append(this.excessDays);
            ((StringBuilder)serializable).append(" ");
            ((StringBuilder)serializable).append(serializable2);
            throw new DateTimeException(((StringBuilder)serializable).toString());
        }
        this.excessDays = serializable2;
    }

    private void updateCheckConflict(ChronoLocalDate object) {
        Object object2 = this.date;
        if (object2 != null) {
            if (object != null && !object2.equals(object)) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Conflict found: Fields resolved to two different dates: ");
                ((StringBuilder)object2).append(this.date);
                ((StringBuilder)object2).append(" ");
                ((StringBuilder)object2).append(object);
                throw new DateTimeException(((StringBuilder)object2).toString());
            }
        } else if (object != null) {
            if (this.chrono.equals(object.getChronology())) {
                this.date = object;
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("ChronoLocalDate must use the effective parsed chronology: ");
                ((StringBuilder)object).append(this.chrono);
                throw new DateTimeException(((StringBuilder)object).toString());
            }
        }
    }

    private void updateCheckConflict(TemporalField temporalField, TemporalField temporalField2, Long l) {
        Long l2 = this.fieldValues.put(temporalField2, l);
        if (l2 != null && l2.longValue() != l.longValue()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Conflict found: ");
            stringBuilder.append(temporalField2);
            stringBuilder.append(" ");
            stringBuilder.append(l2);
            stringBuilder.append(" differs from ");
            stringBuilder.append(temporalField2);
            stringBuilder.append(" ");
            stringBuilder.append(l);
            stringBuilder.append(" while resolving  ");
            stringBuilder.append(temporalField);
            throw new DateTimeException(stringBuilder.toString());
        }
    }

    Parsed copy() {
        Parsed parsed = new Parsed();
        parsed.fieldValues.putAll(this.fieldValues);
        parsed.zone = this.zone;
        parsed.chrono = this.chrono;
        parsed.leapSecond = this.leapSecond;
        return parsed;
    }

    @Override
    public long getLong(TemporalField temporalField) {
        Objects.requireNonNull(temporalField, "field");
        Object object = this.fieldValues.get(temporalField);
        if (object != null) {
            return (Long)object;
        }
        object = this.date;
        if (object != null && object.isSupported(temporalField)) {
            return this.date.getLong(temporalField);
        }
        object = this.time;
        if (object != null && ((LocalTime)object).isSupported(temporalField)) {
            return this.time.getLong(temporalField);
        }
        if (!(temporalField instanceof ChronoField)) {
            return temporalField.getFrom(this);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unsupported field: ");
        ((StringBuilder)object).append(temporalField);
        throw new UnsupportedTemporalTypeException(((StringBuilder)object).toString());
    }

    @Override
    public boolean isSupported(TemporalField temporalField) {
        Comparable<ChronoLocalDate> comparable;
        boolean bl = this.fieldValues.containsKey(temporalField);
        boolean bl2 = true;
        if (!(bl || (comparable = this.date) != null && comparable.isSupported(temporalField) || (comparable = this.time) != null && ((LocalTime)comparable).isSupported(temporalField))) {
            if (temporalField == null || temporalField instanceof ChronoField || !temporalField.isSupportedBy(this)) {
                bl2 = false;
            }
            return bl2;
        }
        return true;
    }

    @Override
    public <R> R query(TemporalQuery<R> object) {
        if (object == TemporalQueries.zoneId()) {
            return (R)this.zone;
        }
        if (object == TemporalQueries.chronology()) {
            return (R)this.chrono;
        }
        Object object2 = TemporalQueries.localDate();
        Object var3_3 = null;
        if (object == object2) {
            object2 = this.date;
            object = var3_3;
            if (object2 != null) {
                object = LocalDate.from((TemporalAccessor)object2);
            }
            return (R)object;
        }
        if (object == TemporalQueries.localTime()) {
            return (R)this.time;
        }
        if (object != TemporalQueries.zone() && object != TemporalQueries.offset()) {
            if (object == TemporalQueries.precision()) {
                return null;
            }
            return object.queryFrom(this);
        }
        return object.queryFrom(this);
    }

    TemporalAccessor resolve(ResolverStyle resolverStyle, Set<TemporalField> set) {
        if (set != null) {
            this.fieldValues.keySet().retainAll(set);
        }
        this.resolverStyle = resolverStyle;
        this.resolveFields();
        this.resolveTimeLenient();
        this.crossCheck();
        this.resolvePeriod();
        this.resolveFractional();
        this.resolveInstant();
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(64);
        stringBuilder.append(this.fieldValues);
        stringBuilder.append(',');
        stringBuilder.append(this.chrono);
        if (this.zone != null) {
            stringBuilder.append(',');
            stringBuilder.append(this.zone);
        }
        if (this.date != null || this.time != null) {
            stringBuilder.append(" resolved to ");
            ChronoLocalDate chronoLocalDate = this.date;
            if (chronoLocalDate != null) {
                stringBuilder.append(chronoLocalDate);
                if (this.time != null) {
                    stringBuilder.append('T');
                    stringBuilder.append(this.time);
                }
            } else {
                stringBuilder.append(this.time);
            }
        }
        return stringBuilder.toString();
    }
}

