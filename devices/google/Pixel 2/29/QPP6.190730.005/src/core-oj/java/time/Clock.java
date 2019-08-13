/*
 * Decompiled with CFR 0.145.
 */
package java.time;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAmount;
import java.util.Objects;

public abstract class Clock {
    protected Clock() {
    }

    public static Clock fixed(Instant instant, ZoneId zoneId) {
        Objects.requireNonNull(instant, "fixedInstant");
        Objects.requireNonNull(zoneId, "zone");
        return new FixedClock(instant, zoneId);
    }

    public static Clock offset(Clock clock, Duration duration) {
        Objects.requireNonNull(clock, "baseClock");
        Objects.requireNonNull(duration, "offsetDuration");
        if (duration.equals(Duration.ZERO)) {
            return clock;
        }
        return new OffsetClock(clock, duration);
    }

    public static Clock system(ZoneId zoneId) {
        Objects.requireNonNull(zoneId, "zone");
        return new SystemClock(zoneId);
    }

    public static Clock systemDefaultZone() {
        return new SystemClock(ZoneId.systemDefault());
    }

    public static Clock systemUTC() {
        return new SystemClock(ZoneOffset.UTC);
    }

    public static Clock tick(Clock clock, Duration duration) {
        Objects.requireNonNull(clock, "baseClock");
        Objects.requireNonNull(duration, "tickDuration");
        if (!duration.isNegative()) {
            long l = duration.toNanos();
            if (l % 1000000L == 0L || 1000000000L % l == 0L) {
                if (l <= 1L) {
                    return clock;
                }
                return new TickClock(clock, l);
            }
            throw new IllegalArgumentException("Invalid tick duration");
        }
        throw new IllegalArgumentException("Tick duration must not be negative");
    }

    public static Clock tickMinutes(ZoneId zoneId) {
        return new TickClock(Clock.system(zoneId), 60000000000L);
    }

    public static Clock tickSeconds(ZoneId zoneId) {
        return new TickClock(Clock.system(zoneId), 1000000000L);
    }

    public boolean equals(Object object) {
        return super.equals(object);
    }

    public abstract ZoneId getZone();

    public int hashCode() {
        return super.hashCode();
    }

    public abstract Instant instant();

    public long millis() {
        return this.instant().toEpochMilli();
    }

    public abstract Clock withZone(ZoneId var1);

    static final class FixedClock
    extends Clock
    implements Serializable {
        private static final long serialVersionUID = 7430389292664866958L;
        private final Instant instant;
        private final ZoneId zone;

        FixedClock(Instant instant, ZoneId zoneId) {
            this.instant = instant;
            this.zone = zoneId;
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object instanceof FixedClock;
            boolean bl2 = false;
            if (bl) {
                object = (FixedClock)object;
                bl = bl2;
                if (this.instant.equals(((FixedClock)object).instant)) {
                    bl = bl2;
                    if (this.zone.equals(((FixedClock)object).zone)) {
                        bl = true;
                    }
                }
                return bl;
            }
            return false;
        }

        @Override
        public ZoneId getZone() {
            return this.zone;
        }

        @Override
        public int hashCode() {
            return this.instant.hashCode() ^ this.zone.hashCode();
        }

        @Override
        public Instant instant() {
            return this.instant;
        }

        @Override
        public long millis() {
            return this.instant.toEpochMilli();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("FixedClock[");
            stringBuilder.append(this.instant);
            stringBuilder.append(",");
            stringBuilder.append(this.zone);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public Clock withZone(ZoneId zoneId) {
            if (zoneId.equals(this.zone)) {
                return this;
            }
            return new FixedClock(this.instant, zoneId);
        }
    }

    static final class OffsetClock
    extends Clock
    implements Serializable {
        private static final long serialVersionUID = 2007484719125426256L;
        private final Clock baseClock;
        private final Duration offset;

        OffsetClock(Clock clock, Duration duration) {
            this.baseClock = clock;
            this.offset = duration;
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object instanceof OffsetClock;
            boolean bl2 = false;
            if (bl) {
                object = (OffsetClock)object;
                bl = bl2;
                if (this.baseClock.equals(((OffsetClock)object).baseClock)) {
                    bl = bl2;
                    if (this.offset.equals(((OffsetClock)object).offset)) {
                        bl = true;
                    }
                }
                return bl;
            }
            return false;
        }

        @Override
        public ZoneId getZone() {
            return this.baseClock.getZone();
        }

        @Override
        public int hashCode() {
            return this.baseClock.hashCode() ^ this.offset.hashCode();
        }

        @Override
        public Instant instant() {
            return this.baseClock.instant().plus(this.offset);
        }

        @Override
        public long millis() {
            return Math.addExact(this.baseClock.millis(), this.offset.toMillis());
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("OffsetClock[");
            stringBuilder.append(this.baseClock);
            stringBuilder.append(",");
            stringBuilder.append(this.offset);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public Clock withZone(ZoneId zoneId) {
            if (zoneId.equals(this.baseClock.getZone())) {
                return this;
            }
            return new OffsetClock(this.baseClock.withZone(zoneId), this.offset);
        }
    }

    static final class SystemClock
    extends Clock
    implements Serializable {
        private static final long serialVersionUID = 6740630888130243051L;
        private final ZoneId zone;

        SystemClock(ZoneId zoneId) {
            this.zone = zoneId;
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof SystemClock) {
                return this.zone.equals(((SystemClock)object).zone);
            }
            return false;
        }

        @Override
        public ZoneId getZone() {
            return this.zone;
        }

        @Override
        public int hashCode() {
            return this.zone.hashCode() + 1;
        }

        @Override
        public Instant instant() {
            return Instant.ofEpochMilli(this.millis());
        }

        @Override
        public long millis() {
            return System.currentTimeMillis();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SystemClock[");
            stringBuilder.append(this.zone);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public Clock withZone(ZoneId zoneId) {
            if (zoneId.equals(this.zone)) {
                return this;
            }
            return new SystemClock(zoneId);
        }
    }

    static final class TickClock
    extends Clock
    implements Serializable {
        private static final long serialVersionUID = 6504659149906368850L;
        private final Clock baseClock;
        private final long tickNanos;

        TickClock(Clock clock, long l) {
            this.baseClock = clock;
            this.tickNanos = l;
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object instanceof TickClock;
            boolean bl2 = false;
            if (bl) {
                object = (TickClock)object;
                bl = bl2;
                if (this.baseClock.equals(((TickClock)object).baseClock)) {
                    bl = bl2;
                    if (this.tickNanos == ((TickClock)object).tickNanos) {
                        bl = true;
                    }
                }
                return bl;
            }
            return false;
        }

        @Override
        public ZoneId getZone() {
            return this.baseClock.getZone();
        }

        @Override
        public int hashCode() {
            int n = this.baseClock.hashCode();
            long l = this.tickNanos;
            return n ^ (int)(l ^ l >>> 32);
        }

        @Override
        public Instant instant() {
            if (this.tickNanos % 1000000L == 0L) {
                long l = this.baseClock.millis();
                return Instant.ofEpochMilli(l - Math.floorMod(l, this.tickNanos / 1000000L));
            }
            Instant instant = this.baseClock.instant();
            return instant.minusNanos(Math.floorMod((long)instant.getNano(), this.tickNanos));
        }

        @Override
        public long millis() {
            long l = this.baseClock.millis();
            return l - Math.floorMod(l, this.tickNanos / 1000000L);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TickClock[");
            stringBuilder.append(this.baseClock);
            stringBuilder.append(",");
            stringBuilder.append(Duration.ofNanos(this.tickNanos));
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public Clock withZone(ZoneId zoneId) {
            if (zoneId.equals(this.baseClock.getZone())) {
                return this;
            }
            return new TickClock(this.baseClock.withZone(zoneId), this.tickNanos);
        }
    }

}

