/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Locale;

public interface Era
extends TemporalAccessor,
TemporalAdjuster {
    @Override
    default public Temporal adjustInto(Temporal temporal) {
        return temporal.with(ChronoField.ERA, this.getValue());
    }

    @Override
    default public int get(TemporalField temporalField) {
        if (temporalField == ChronoField.ERA) {
            return this.getValue();
        }
        return TemporalAccessor.super.get(temporalField);
    }

    default public String getDisplayName(TextStyle textStyle, Locale locale) {
        return new DateTimeFormatterBuilder().appendText((TemporalField)ChronoField.ERA, textStyle).toFormatter(locale).format(this);
    }

    @Override
    default public long getLong(TemporalField temporalField) {
        if (temporalField == ChronoField.ERA) {
            return this.getValue();
        }
        if (!(temporalField instanceof ChronoField)) {
            return temporalField.getFrom(this);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported field: ");
        stringBuilder.append(temporalField);
        throw new UnsupportedTemporalTypeException(stringBuilder.toString());
    }

    public int getValue();

    @Override
    default public boolean isSupported(TemporalField temporalField) {
        boolean bl = temporalField instanceof ChronoField;
        boolean bl2 = true;
        boolean bl3 = true;
        if (bl) {
            if (temporalField != ChronoField.ERA) {
                bl3 = false;
            }
            return bl3;
        }
        bl3 = temporalField != null && temporalField.isSupportedBy(this) ? bl2 : false;
        return bl3;
    }

    @Override
    default public <R> R query(TemporalQuery<R> temporalQuery) {
        if (temporalQuery == TemporalQueries.precision()) {
            return (R)ChronoUnit.ERAS;
        }
        return TemporalAccessor.super.query(temporalQuery);
    }

    @Override
    default public ValueRange range(TemporalField temporalField) {
        return TemporalAccessor.super.range(temporalField);
    }
}

