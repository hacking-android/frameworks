/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration;

import android.icu.impl.duration.TimeUnit;

public interface TimeUnitConstants {
    public static final TimeUnit DAY;
    public static final TimeUnit HOUR;
    public static final TimeUnit MILLISECOND;
    public static final TimeUnit MINUTE;
    public static final TimeUnit MONTH;
    public static final TimeUnit SECOND;
    public static final TimeUnit WEEK;
    public static final TimeUnit YEAR;

    static {
        YEAR = TimeUnit.YEAR;
        MONTH = TimeUnit.MONTH;
        WEEK = TimeUnit.WEEK;
        DAY = TimeUnit.DAY;
        HOUR = TimeUnit.HOUR;
        MINUTE = TimeUnit.MINUTE;
        SECOND = TimeUnit.SECOND;
        MILLISECOND = TimeUnit.MILLISECOND;
    }
}

