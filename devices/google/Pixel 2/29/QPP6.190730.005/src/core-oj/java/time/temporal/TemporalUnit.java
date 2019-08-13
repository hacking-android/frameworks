/*
 * Decompiled with CFR 0.145.
 */
package java.time.temporal;

import java.time.Duration;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.UnsupportedTemporalTypeException;

public interface TemporalUnit {
    public <R extends Temporal> R addTo(R var1, long var2);

    public long between(Temporal var1, Temporal var2);

    public Duration getDuration();

    public boolean isDateBased();

    public boolean isDurationEstimated();

    default public boolean isSupportedBy(Temporal temporal) {
        if (temporal instanceof LocalTime) {
            return this.isTimeBased();
        }
        if (temporal instanceof ChronoLocalDate) {
            return this.isDateBased();
        }
        if (!(temporal instanceof ChronoLocalDateTime) && !(temporal instanceof ChronoZonedDateTime)) {
            try {
                temporal.plus(1L, this);
                return true;
            }
            catch (RuntimeException runtimeException) {
                try {
                    temporal.plus(-1L, this);
                    return true;
                }
                catch (RuntimeException runtimeException2) {
                    return false;
                }
            }
            catch (UnsupportedTemporalTypeException unsupportedTemporalTypeException) {
                return false;
            }
        }
        return true;
    }

    public boolean isTimeBased();

    public String toString();
}

