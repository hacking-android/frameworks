/*
 * Decompiled with CFR 0.145.
 */
package java.time.temporal;

import java.time.DateTimeException;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;
import java.util.Map;

public final class JulianFields {
    public static final TemporalField JULIAN_DAY = Field.JULIAN_DAY;
    private static final long JULIAN_DAY_OFFSET = 2440588L;
    public static final TemporalField MODIFIED_JULIAN_DAY = Field.MODIFIED_JULIAN_DAY;
    public static final TemporalField RATA_DIE = Field.RATA_DIE;

    private JulianFields() {
        throw new AssertionError((Object)"Not instantiable");
    }

    private static enum Field implements TemporalField
    {
        JULIAN_DAY("JulianDay", ChronoUnit.DAYS, ChronoUnit.FOREVER, 2440588L),
        MODIFIED_JULIAN_DAY("ModifiedJulianDay", ChronoUnit.DAYS, ChronoUnit.FOREVER, 40587L),
        RATA_DIE("RataDie", ChronoUnit.DAYS, ChronoUnit.FOREVER, 719163L);
        
        private static final long serialVersionUID = -7501623920830201812L;
        private final transient TemporalUnit baseUnit;
        private final transient String name;
        private final transient long offset;
        private final transient ValueRange range;
        private final transient TemporalUnit rangeUnit;

        private Field(String string2, TemporalUnit temporalUnit, TemporalUnit temporalUnit2, long l) {
            this.name = string2;
            this.baseUnit = temporalUnit;
            this.rangeUnit = temporalUnit2;
            this.range = ValueRange.of(-365243219162L + l, 365241780471L + l);
            this.offset = l;
        }

        @Override
        public <R extends Temporal> R adjustInto(R object, long l) {
            if (this.range().isValidValue(l)) {
                return (R)object.with(ChronoField.EPOCH_DAY, Math.subtractExact(l, this.offset));
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid value: ");
            ((StringBuilder)object).append(this.name);
            ((StringBuilder)object).append(" ");
            ((StringBuilder)object).append(l);
            throw new DateTimeException(((StringBuilder)object).toString());
        }

        @Override
        public TemporalUnit getBaseUnit() {
            return this.baseUnit;
        }

        @Override
        public long getFrom(TemporalAccessor temporalAccessor) {
            return temporalAccessor.getLong(ChronoField.EPOCH_DAY) + this.offset;
        }

        @Override
        public TemporalUnit getRangeUnit() {
            return this.rangeUnit;
        }

        @Override
        public boolean isDateBased() {
            return true;
        }

        @Override
        public boolean isSupportedBy(TemporalAccessor temporalAccessor) {
            return temporalAccessor.isSupported(ChronoField.EPOCH_DAY);
        }

        @Override
        public boolean isTimeBased() {
            return false;
        }

        @Override
        public ValueRange range() {
            return this.range;
        }

        @Override
        public ValueRange rangeRefinedBy(TemporalAccessor object) {
            if (this.isSupportedBy((TemporalAccessor)object)) {
                return this.range();
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unsupported field: ");
            ((StringBuilder)object).append(this);
            throw new DateTimeException(((StringBuilder)object).toString());
        }

        @Override
        public ChronoLocalDate resolve(Map<TemporalField, Long> object, TemporalAccessor temporalAccessor, ResolverStyle resolverStyle) {
            long l = object.remove(this);
            object = Chronology.from(temporalAccessor);
            if (resolverStyle == ResolverStyle.LENIENT) {
                return object.dateEpochDay(Math.subtractExact(l, this.offset));
            }
            this.range().checkValidValue(l, this);
            return object.dateEpochDay(l - this.offset);
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

}

