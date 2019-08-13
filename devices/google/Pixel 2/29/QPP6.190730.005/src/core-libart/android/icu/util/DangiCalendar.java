/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.ChineseCalendar;
import android.icu.util.InitialTimeZoneRule;
import android.icu.util.RuleBasedTimeZone;
import android.icu.util.TimeArrayTimeZoneRule;
import android.icu.util.TimeZone;
import android.icu.util.TimeZoneRule;
import android.icu.util.ULocale;
import java.io.Serializable;
import java.util.Date;

@Deprecated
public class DangiCalendar
extends ChineseCalendar {
    private static final int DANGI_EPOCH_YEAR = -2332;
    private static final TimeZone KOREA_ZONE;
    private static final long serialVersionUID = 8156297445349501985L;

    static {
        Serializable serializable = new InitialTimeZoneRule("GMT+8", 28800000, 0);
        TimeArrayTimeZoneRule timeArrayTimeZoneRule = new TimeArrayTimeZoneRule("Korean 1897", 25200000, 0, new long[]{-2302128000000L}, 1);
        TimeArrayTimeZoneRule timeArrayTimeZoneRule2 = new TimeArrayTimeZoneRule("Korean 1898-1911", 28800000, 0, new long[]{-2270592000000L}, 1);
        TimeArrayTimeZoneRule timeArrayTimeZoneRule3 = new TimeArrayTimeZoneRule("Korean 1912-", 32400000, 0, new long[]{-1829088000000L}, 1);
        serializable = new RuleBasedTimeZone("KOREA_ZONE", (InitialTimeZoneRule)serializable);
        ((RuleBasedTimeZone)serializable).addTransitionRule(timeArrayTimeZoneRule);
        ((RuleBasedTimeZone)serializable).addTransitionRule(timeArrayTimeZoneRule2);
        ((RuleBasedTimeZone)serializable).addTransitionRule(timeArrayTimeZoneRule3);
        ((RuleBasedTimeZone)serializable).freeze();
        KOREA_ZONE = serializable;
    }

    @Deprecated
    public DangiCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    @Deprecated
    public DangiCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale, -2332, KOREA_ZONE);
    }

    @Deprecated
    public DangiCalendar(Date date) {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.setTime(date);
    }

    @Deprecated
    @Override
    public String getType() {
        return "dangi";
    }
}

