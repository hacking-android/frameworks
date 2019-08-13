/*
 * Decompiled with CFR 0.145.
 */
package java.time.temporal;

import java.time.Duration;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

public enum ChronoUnit implements TemporalUnit
{
    NANOS("Nanos", Duration.ofNanos(1L)),
    MICROS("Micros", Duration.ofNanos(1000L)),
    MILLIS("Millis", Duration.ofNanos(1000000L)),
    SECONDS("Seconds", Duration.ofSeconds(1L)),
    MINUTES("Minutes", Duration.ofSeconds(60L)),
    HOURS("Hours", Duration.ofSeconds(3600L)),
    HALF_DAYS("HalfDays", Duration.ofSeconds(43200L)),
    DAYS("Days", Duration.ofSeconds(86400L)),
    WEEKS("Weeks", Duration.ofSeconds(604800L)),
    MONTHS("Months", Duration.ofSeconds(2629746L)),
    YEARS("Years", Duration.ofSeconds(31556952L)),
    DECADES("Decades", Duration.ofSeconds(315569520L)),
    CENTURIES("Centuries", Duration.ofSeconds(3155695200L)),
    MILLENNIA("Millennia", Duration.ofSeconds(31556952000L)),
    ERAS("Eras", Duration.ofSeconds(31556952000000000L)),
    FOREVER("Forever", Duration.ofSeconds(Long.MAX_VALUE, 999999999L));
    
    private final Duration duration;
    private final String name;

    private ChronoUnit(String string2, Duration duration) {
        this.name = string2;
        this.duration = duration;
    }

    @Override
    public <R extends Temporal> R addTo(R r, long l) {
        return (R)r.plus(l, this);
    }

    @Override
    public long between(Temporal temporal, Temporal temporal2) {
        return temporal.until(temporal2, this);
    }

    @Override
    public Duration getDuration() {
        return this.duration;
    }

    @Override
    public boolean isDateBased() {
        boolean bl = this.compareTo(DAYS) >= 0 && this != FOREVER;
        return bl;
    }

    @Override
    public boolean isDurationEstimated() {
        boolean bl = this.compareTo(DAYS) >= 0;
        return bl;
    }

    @Override
    public boolean isSupportedBy(Temporal temporal) {
        return temporal.isSupported(this);
    }

    @Override
    public boolean isTimeBased() {
        boolean bl = this.compareTo(DAYS) < 0;
        return bl;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

