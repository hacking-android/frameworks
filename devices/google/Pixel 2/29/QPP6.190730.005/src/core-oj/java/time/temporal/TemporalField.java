/*
 * Decompiled with CFR 0.145.
 */
package java.time.temporal;

import java.time.format.ResolverStyle;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public interface TemporalField {
    public <R extends Temporal> R adjustInto(R var1, long var2);

    public TemporalUnit getBaseUnit();

    default public String getDisplayName(Locale locale) {
        Objects.requireNonNull(locale, "locale");
        return this.toString();
    }

    public long getFrom(TemporalAccessor var1);

    public TemporalUnit getRangeUnit();

    public boolean isDateBased();

    public boolean isSupportedBy(TemporalAccessor var1);

    public boolean isTimeBased();

    public ValueRange range();

    public ValueRange rangeRefinedBy(TemporalAccessor var1);

    default public TemporalAccessor resolve(Map<TemporalField, Long> map, TemporalAccessor temporalAccessor, ResolverStyle resolverStyle) {
        return null;
    }

    public String toString();
}

