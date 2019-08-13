/*
 * Decompiled with CFR 0.145.
 */
package java.time.temporal;

import java.time.DateTimeException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Objects;

public interface TemporalAccessor {
    default public int get(TemporalField temporalField) {
        ValueRange valueRange = this.range(temporalField);
        if (valueRange.isIntValue()) {
            long l = this.getLong(temporalField);
            if (valueRange.isValidValue(l)) {
                return (int)l;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid value for ");
            stringBuilder.append(temporalField);
            stringBuilder.append(" (valid values ");
            stringBuilder.append(valueRange);
            stringBuilder.append("): ");
            stringBuilder.append(l);
            throw new DateTimeException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid field ");
        stringBuilder.append(temporalField);
        stringBuilder.append(" for get() method, use getLong() instead");
        throw new UnsupportedTemporalTypeException(stringBuilder.toString());
    }

    public long getLong(TemporalField var1);

    public boolean isSupported(TemporalField var1);

    default public <R> R query(TemporalQuery<R> temporalQuery) {
        if (temporalQuery != TemporalQueries.zoneId() && temporalQuery != TemporalQueries.chronology() && temporalQuery != TemporalQueries.precision()) {
            return temporalQuery.queryFrom(this);
        }
        return null;
    }

    default public ValueRange range(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            if (this.isSupported(temporalField)) {
                return temporalField.range();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported field: ");
            stringBuilder.append(temporalField);
            throw new UnsupportedTemporalTypeException(stringBuilder.toString());
        }
        Objects.requireNonNull(temporalField, "field");
        return temporalField.rangeRefinedBy(this);
    }
}

