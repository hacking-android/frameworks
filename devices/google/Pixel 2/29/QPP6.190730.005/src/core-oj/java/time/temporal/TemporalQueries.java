/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.temporal.-$
 *  java.time.temporal.-$$Lambda
 *  java.time.temporal.-$$Lambda$TemporalQueries
 *  java.time.temporal.-$$Lambda$TemporalQueries$IZUinmsZUz98YXPe0ftAd27ByiE
 *  java.time.temporal.-$$Lambda$TemporalQueries$JPrXwgedeqexYxypO8VpPKV4l3c
 *  java.time.temporal.-$$Lambda$TemporalQueries$PBpYKRiwkxqQNlcU-BOJfaQoONg
 *  java.time.temporal.-$$Lambda$TemporalQueries$WGGw7SkRcanjtxRiTk5p0dKf_jc
 *  java.time.temporal.-$$Lambda$TemporalQueries$bI5NESEXE4DqyC7TnOvbkx1GlvM
 *  java.time.temporal.-$$Lambda$TemporalQueries$okxqZ6ZoOhHd_zSzW7k5qRIaLxM
 *  java.time.temporal.-$$Lambda$TemporalQueries$thd4JmExRUYKd7nNlE7b5oT19ms
 */
package java.time.temporal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.Chronology;
import java.time.temporal.-$;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQuery;
import java.time.temporal.TemporalUnit;
import java.time.temporal._$$Lambda$TemporalQueries$IZUinmsZUz98YXPe0ftAd27ByiE;
import java.time.temporal._$$Lambda$TemporalQueries$JPrXwgedeqexYxypO8VpPKV4l3c;
import java.time.temporal._$$Lambda$TemporalQueries$PBpYKRiwkxqQNlcU_BOJfaQoONg;
import java.time.temporal._$$Lambda$TemporalQueries$WGGw7SkRcanjtxRiTk5p0dKf_jc;
import java.time.temporal._$$Lambda$TemporalQueries$bI5NESEXE4DqyC7TnOvbkx1GlvM;
import java.time.temporal._$$Lambda$TemporalQueries$okxqZ6ZoOhHd_zSzW7k5qRIaLxM;
import java.time.temporal._$$Lambda$TemporalQueries$thd4JmExRUYKd7nNlE7b5oT19ms;

public final class TemporalQueries {
    static final TemporalQuery<Chronology> CHRONO;
    static final TemporalQuery<LocalDate> LOCAL_DATE;
    static final TemporalQuery<LocalTime> LOCAL_TIME;
    static final TemporalQuery<ZoneOffset> OFFSET;
    static final TemporalQuery<TemporalUnit> PRECISION;
    static final TemporalQuery<ZoneId> ZONE;
    static final TemporalQuery<ZoneId> ZONE_ID;

    static {
        ZONE_ID = _$$Lambda$TemporalQueries$IZUinmsZUz98YXPe0ftAd27ByiE.INSTANCE;
        CHRONO = _$$Lambda$TemporalQueries$thd4JmExRUYKd7nNlE7b5oT19ms.INSTANCE;
        PRECISION = _$$Lambda$TemporalQueries$okxqZ6ZoOhHd_zSzW7k5qRIaLxM.INSTANCE;
        OFFSET = _$$Lambda$TemporalQueries$bI5NESEXE4DqyC7TnOvbkx1GlvM.INSTANCE;
        ZONE = _$$Lambda$TemporalQueries$PBpYKRiwkxqQNlcU_BOJfaQoONg.INSTANCE;
        LOCAL_DATE = _$$Lambda$TemporalQueries$JPrXwgedeqexYxypO8VpPKV4l3c.INSTANCE;
        LOCAL_TIME = _$$Lambda$TemporalQueries$WGGw7SkRcanjtxRiTk5p0dKf_jc.INSTANCE;
    }

    private TemporalQueries() {
    }

    public static TemporalQuery<Chronology> chronology() {
        return CHRONO;
    }

    static /* synthetic */ ZoneId lambda$static$0(TemporalAccessor temporalAccessor) {
        return temporalAccessor.query(ZONE_ID);
    }

    static /* synthetic */ Chronology lambda$static$1(TemporalAccessor temporalAccessor) {
        return temporalAccessor.query(CHRONO);
    }

    static /* synthetic */ TemporalUnit lambda$static$2(TemporalAccessor temporalAccessor) {
        return temporalAccessor.query(PRECISION);
    }

    static /* synthetic */ ZoneOffset lambda$static$3(TemporalAccessor temporalAccessor) {
        if (temporalAccessor.isSupported(ChronoField.OFFSET_SECONDS)) {
            return ZoneOffset.ofTotalSeconds(temporalAccessor.get(ChronoField.OFFSET_SECONDS));
        }
        return null;
    }

    static /* synthetic */ ZoneId lambda$static$4(TemporalAccessor object) {
        ZoneId zoneId = object.query(ZONE_ID);
        object = zoneId != null ? zoneId : (ZoneId)object.query(OFFSET);
        return object;
    }

    static /* synthetic */ LocalDate lambda$static$5(TemporalAccessor temporalAccessor) {
        if (temporalAccessor.isSupported(ChronoField.EPOCH_DAY)) {
            return LocalDate.ofEpochDay(temporalAccessor.getLong(ChronoField.EPOCH_DAY));
        }
        return null;
    }

    static /* synthetic */ LocalTime lambda$static$6(TemporalAccessor temporalAccessor) {
        if (temporalAccessor.isSupported(ChronoField.NANO_OF_DAY)) {
            return LocalTime.ofNanoOfDay(temporalAccessor.getLong(ChronoField.NANO_OF_DAY));
        }
        return null;
    }

    public static TemporalQuery<LocalDate> localDate() {
        return LOCAL_DATE;
    }

    public static TemporalQuery<LocalTime> localTime() {
        return LOCAL_TIME;
    }

    public static TemporalQuery<ZoneOffset> offset() {
        return OFFSET;
    }

    public static TemporalQuery<TemporalUnit> precision() {
        return PRECISION;
    }

    public static TemporalQuery<ZoneId> zone() {
        return ZONE;
    }

    public static TemporalQuery<ZoneId> zoneId() {
        return ZONE_ID;
    }
}

