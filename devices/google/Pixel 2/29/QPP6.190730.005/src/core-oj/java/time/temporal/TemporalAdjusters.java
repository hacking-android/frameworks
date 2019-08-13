/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.temporal.-$
 *  java.time.temporal.-$$Lambda
 *  java.time.temporal.-$$Lambda$TemporalAdjusters
 *  java.time.temporal.-$$Lambda$TemporalAdjusters$8EK8KVP193YLBVkxtkiyg8uZHVo
 *  java.time.temporal.-$$Lambda$TemporalAdjusters$P7_rZO2XINPKRC8_LcPrXpeSbek
 *  java.time.temporal.-$$Lambda$TemporalAdjusters$WAuzLMCz-2-SwnlcREz0tiYj3XA
 *  java.time.temporal.-$$Lambda$TemporalAdjusters$iS4EYkMA1JewgnCHCuVtjW33u14
 *  java.time.temporal.-$$Lambda$TemporalAdjusters$lxLY-2BERW0kc72bWr7fARmx5Z8
 *  java.time.temporal.-$$Lambda$TemporalAdjusters$w9cWh2WC9cZ6gKDdIl4UmC4HmUM
 */
package java.time.temporal;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.-$;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;
import java.time.temporal._$$Lambda$TemporalAdjusters$8EK8KVP193YLBVkxtkiyg8uZHVo;
import java.time.temporal._$$Lambda$TemporalAdjusters$A9OZwfMlHD1vy7_nYt5NssACu7Q;
import java.time.temporal._$$Lambda$TemporalAdjusters$Aa2HtcpQrtdU2tm9_WsArYNyfqM;
import java.time.temporal._$$Lambda$TemporalAdjusters$BioX0XAyDebBQX3h4Lqog9Ofj58;
import java.time.temporal._$$Lambda$TemporalAdjusters$CLbEgdXQzFe17bbP1cAR86Ccar4;
import java.time.temporal._$$Lambda$TemporalAdjusters$Hr69XZXcuTp7qqn22qJMcjGgXGw;
import java.time.temporal._$$Lambda$TemporalAdjusters$P7_rZO2XINPKRC8_LcPrXpeSbek;
import java.time.temporal._$$Lambda$TemporalAdjusters$TKkfUVRu_GUECdXqtmzzXrayVY8;
import java.time.temporal._$$Lambda$TemporalAdjusters$WAuzLMCz_2_SwnlcREz0tiYj3XA;
import java.time.temporal._$$Lambda$TemporalAdjusters$iS4EYkMA1JewgnCHCuVtjW33u14;
import java.time.temporal._$$Lambda$TemporalAdjusters$lxLY_2BERW0kc72bWr7fARmx5Z8;
import java.time.temporal._$$Lambda$TemporalAdjusters$tdo0RtAvE1xjo0CFx2_92T1yRzQ;
import java.time.temporal._$$Lambda$TemporalAdjusters$w9cWh2WC9cZ6gKDdIl4UmC4HmUM;
import java.util.Objects;
import java.util.function.UnaryOperator;

public final class TemporalAdjusters {
    private TemporalAdjusters() {
    }

    public static TemporalAdjuster dayOfWeekInMonth(int n, DayOfWeek dayOfWeek) {
        Objects.requireNonNull(dayOfWeek, "dayOfWeek");
        int n2 = dayOfWeek.getValue();
        if (n >= 0) {
            return new _$$Lambda$TemporalAdjusters$tdo0RtAvE1xjo0CFx2_92T1yRzQ(n2, n);
        }
        return new _$$Lambda$TemporalAdjusters$BioX0XAyDebBQX3h4Lqog9Ofj58(n2, n);
    }

    public static TemporalAdjuster firstDayOfMonth() {
        return _$$Lambda$TemporalAdjusters$8EK8KVP193YLBVkxtkiyg8uZHVo.INSTANCE;
    }

    public static TemporalAdjuster firstDayOfNextMonth() {
        return _$$Lambda$TemporalAdjusters$P7_rZO2XINPKRC8_LcPrXpeSbek.INSTANCE;
    }

    public static TemporalAdjuster firstDayOfNextYear() {
        return _$$Lambda$TemporalAdjusters$lxLY_2BERW0kc72bWr7fARmx5Z8.INSTANCE;
    }

    public static TemporalAdjuster firstDayOfYear() {
        return _$$Lambda$TemporalAdjusters$w9cWh2WC9cZ6gKDdIl4UmC4HmUM.INSTANCE;
    }

    public static TemporalAdjuster firstInMonth(DayOfWeek dayOfWeek) {
        return TemporalAdjusters.dayOfWeekInMonth(1, dayOfWeek);
    }

    static /* synthetic */ Temporal lambda$dayOfWeekInMonth$7(int n, int n2, Temporal temporal) {
        temporal = temporal.with(ChronoField.DAY_OF_MONTH, 1L);
        return temporal.plus((int)((long)((n - temporal.get(ChronoField.DAY_OF_WEEK) + 7) % 7) + ((long)n2 - 1L) * 7L), ChronoUnit.DAYS);
    }

    static /* synthetic */ Temporal lambda$dayOfWeekInMonth$8(int n, int n2, Temporal temporal) {
        block1 : {
            block0 : {
                if ((n -= (temporal = temporal.with(ChronoField.DAY_OF_MONTH, temporal.range(ChronoField.DAY_OF_MONTH).getMaximum())).get(ChronoField.DAY_OF_WEEK)) != 0) break block0;
                n = 0;
                break block1;
            }
            if (n <= 0) break block1;
            n -= 7;
        }
        return temporal.plus((int)((long)n - ((long)(-n2) - 1L) * 7L), ChronoUnit.DAYS);
    }

    static /* synthetic */ Temporal lambda$firstDayOfMonth$1(Temporal temporal) {
        return temporal.with(ChronoField.DAY_OF_MONTH, 1L);
    }

    static /* synthetic */ Temporal lambda$firstDayOfNextMonth$3(Temporal temporal) {
        return temporal.with(ChronoField.DAY_OF_MONTH, 1L).plus(1L, ChronoUnit.MONTHS);
    }

    static /* synthetic */ Temporal lambda$firstDayOfNextYear$6(Temporal temporal) {
        return temporal.with(ChronoField.DAY_OF_YEAR, 1L).plus(1L, ChronoUnit.YEARS);
    }

    static /* synthetic */ Temporal lambda$firstDayOfYear$4(Temporal temporal) {
        return temporal.with(ChronoField.DAY_OF_YEAR, 1L);
    }

    static /* synthetic */ Temporal lambda$lastDayOfMonth$2(Temporal temporal) {
        return temporal.with(ChronoField.DAY_OF_MONTH, temporal.range(ChronoField.DAY_OF_MONTH).getMaximum());
    }

    static /* synthetic */ Temporal lambda$lastDayOfYear$5(Temporal temporal) {
        return temporal.with(ChronoField.DAY_OF_YEAR, temporal.range(ChronoField.DAY_OF_YEAR).getMaximum());
    }

    static /* synthetic */ Temporal lambda$next$9(int n, Temporal temporal) {
        n = temporal.get(ChronoField.DAY_OF_WEEK) - n;
        n = n >= 0 ? 7 - n : -n;
        return temporal.plus(n, ChronoUnit.DAYS);
    }

    static /* synthetic */ Temporal lambda$nextOrSame$10(int n, Temporal temporal) {
        int n2 = temporal.get(ChronoField.DAY_OF_WEEK);
        if (n2 == n) {
            return temporal;
        }
        n = (n = n2 - n) >= 0 ? 7 - n : -n;
        return temporal.plus(n, ChronoUnit.DAYS);
    }

    static /* synthetic */ Temporal lambda$ofDateAdjuster$0(UnaryOperator unaryOperator, Temporal temporal) {
        return temporal.with((LocalDate)unaryOperator.apply(LocalDate.from(temporal)));
    }

    static /* synthetic */ Temporal lambda$previous$11(int n, Temporal temporal) {
        n = (n -= temporal.get(ChronoField.DAY_OF_WEEK)) >= 0 ? 7 - n : -n;
        return temporal.minus(n, ChronoUnit.DAYS);
    }

    static /* synthetic */ Temporal lambda$previousOrSame$12(int n, Temporal temporal) {
        int n2 = temporal.get(ChronoField.DAY_OF_WEEK);
        if (n2 == n) {
            return temporal;
        }
        n = (n -= n2) >= 0 ? 7 - n : -n;
        return temporal.minus(n, ChronoUnit.DAYS);
    }

    public static TemporalAdjuster lastDayOfMonth() {
        return _$$Lambda$TemporalAdjusters$WAuzLMCz_2_SwnlcREz0tiYj3XA.INSTANCE;
    }

    public static TemporalAdjuster lastDayOfYear() {
        return _$$Lambda$TemporalAdjusters$iS4EYkMA1JewgnCHCuVtjW33u14.INSTANCE;
    }

    public static TemporalAdjuster lastInMonth(DayOfWeek dayOfWeek) {
        return TemporalAdjusters.dayOfWeekInMonth(-1, dayOfWeek);
    }

    public static TemporalAdjuster next(DayOfWeek dayOfWeek) {
        return new _$$Lambda$TemporalAdjusters$Aa2HtcpQrtdU2tm9_WsArYNyfqM(dayOfWeek.getValue());
    }

    public static TemporalAdjuster nextOrSame(DayOfWeek dayOfWeek) {
        return new _$$Lambda$TemporalAdjusters$A9OZwfMlHD1vy7_nYt5NssACu7Q(dayOfWeek.getValue());
    }

    public static TemporalAdjuster ofDateAdjuster(UnaryOperator<LocalDate> unaryOperator) {
        Objects.requireNonNull(unaryOperator, "dateBasedAdjuster");
        return new _$$Lambda$TemporalAdjusters$CLbEgdXQzFe17bbP1cAR86Ccar4(unaryOperator);
    }

    public static TemporalAdjuster previous(DayOfWeek dayOfWeek) {
        return new _$$Lambda$TemporalAdjusters$Hr69XZXcuTp7qqn22qJMcjGgXGw(dayOfWeek.getValue());
    }

    public static TemporalAdjuster previousOrSame(DayOfWeek dayOfWeek) {
        return new _$$Lambda$TemporalAdjusters$TKkfUVRu_GUECdXqtmzzXrayVY8(dayOfWeek.getValue());
    }
}

