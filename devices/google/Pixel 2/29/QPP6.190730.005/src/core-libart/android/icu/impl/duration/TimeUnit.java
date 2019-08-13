/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration;

public final class TimeUnit {
    public static final TimeUnit DAY;
    public static final TimeUnit HOUR;
    public static final TimeUnit MILLISECOND;
    public static final TimeUnit MINUTE;
    public static final TimeUnit MONTH;
    public static final TimeUnit SECOND;
    public static final TimeUnit WEEK;
    public static final TimeUnit YEAR;
    static final long[] approxDurations;
    static final TimeUnit[] units;
    final String name;
    final byte ordinal;

    static {
        YEAR = new TimeUnit("year", 0);
        MONTH = new TimeUnit("month", 1);
        WEEK = new TimeUnit("week", 2);
        DAY = new TimeUnit("day", 3);
        HOUR = new TimeUnit("hour", 4);
        MINUTE = new TimeUnit("minute", 5);
        SECOND = new TimeUnit("second", 6);
        MILLISECOND = new TimeUnit("millisecond", 7);
        units = new TimeUnit[]{YEAR, MONTH, WEEK, DAY, HOUR, MINUTE, SECOND, MILLISECOND};
        approxDurations = new long[]{31557600000L, 2630880000L, 604800000L, 86400000L, 3600000L, 60000L, 1000L, 1L};
    }

    private TimeUnit(String string, int n) {
        this.name = string;
        this.ordinal = (byte)n;
    }

    public TimeUnit larger() {
        byte by = this.ordinal;
        TimeUnit timeUnit = by == 0 ? null : units[by - 1];
        return timeUnit;
    }

    public int ordinal() {
        return this.ordinal;
    }

    public TimeUnit smaller() {
        byte by = this.ordinal;
        Object object = units;
        object = by == ((TimeUnit[])object).length - 1 ? null : object[by + 1];
        return object;
    }

    public String toString() {
        return this.name;
    }
}

