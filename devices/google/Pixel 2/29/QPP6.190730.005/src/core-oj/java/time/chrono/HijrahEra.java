/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.time.DateTimeException;
import java.time.chrono.Era;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;

public enum HijrahEra implements Era
{
    AH;
    

    public static HijrahEra of(int n) {
        if (n == 1) {
            return AH;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid era: ");
        stringBuilder.append(n);
        throw new DateTimeException(stringBuilder.toString());
    }

    @Override
    public int getValue() {
        return 1;
    }

    @Override
    public ValueRange range(TemporalField temporalField) {
        if (temporalField == ChronoField.ERA) {
            return ValueRange.of(1L, 1L);
        }
        return Era.super.range(temporalField);
    }
}

