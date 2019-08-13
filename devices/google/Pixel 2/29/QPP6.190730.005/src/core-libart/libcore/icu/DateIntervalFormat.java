/*
 * Decompiled with CFR 0.145.
 */
package libcore.icu;

import android.icu.util.Calendar;
import android.icu.util.ULocale;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.text.FieldPosition;
import java.util.TimeZone;
import libcore.icu.DateUtilsBridge;
import libcore.util.BasicLruCache;

public final class DateIntervalFormat {
    private static final BasicLruCache<String, android.icu.text.DateIntervalFormat> CACHED_FORMATTERS = new BasicLruCache(8);

    private DateIntervalFormat() {
    }

    @UnsupportedAppUsage
    public static String formatDateRange(long l, long l2, int n, String object) {
        if ((n & 8192) != 0) {
            object = "UTC";
        }
        object = object != null ? TimeZone.getTimeZone((String)object) : TimeZone.getDefault();
        object = DateUtilsBridge.icuTimeZone((TimeZone)object);
        return DateIntervalFormat.formatDateRange(ULocale.getDefault(), (android.icu.util.TimeZone)object, l, l2, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String formatDateRange(ULocale object, android.icu.util.TimeZone cloneable, long l, long l2, int n) {
        Calendar calendar = DateUtilsBridge.createIcuCalendar((android.icu.util.TimeZone)cloneable, (ULocale)object, l);
        Calendar calendar2 = l == l2 ? calendar : DateUtilsBridge.createIcuCalendar((android.icu.util.TimeZone)cloneable, (ULocale)object, l2);
        if (DateIntervalFormat.isExactlyMidnight(calendar2)) {
            boolean bl = true;
            boolean bl2 = (n & 1) == 1;
            if (DateUtilsBridge.dayDistance(calendar, calendar2) != 1) {
                bl = false;
            }
            if (!bl2 && l != l2 || bl && !DateUtilsBridge.isDisplayMidnightUsingSkeleton(calendar)) {
                calendar2.add(5, -1);
            }
        }
        Object object2 = DateUtilsBridge.toSkeleton(calendar, calendar2, n);
        BasicLruCache<String, android.icu.text.DateIntervalFormat> basicLruCache = CACHED_FORMATTERS;
        synchronized (basicLruCache) {
            cloneable = DateIntervalFormat.getFormatter((String)object2, (ULocale)object, (android.icu.util.TimeZone)cloneable);
            object = new StringBuffer();
            object2 = new FieldPosition(0);
            return ((android.icu.text.DateIntervalFormat)cloneable).format(calendar, calendar2, (StringBuffer)object, (FieldPosition)object2).toString();
        }
    }

    private static android.icu.text.DateIntervalFormat getFormatter(String object, ULocale uLocale, android.icu.util.TimeZone timeZone) {
        CharSequence charSequence = new StringBuilder();
        charSequence.append((String)object);
        charSequence.append("\t");
        charSequence.append(uLocale);
        charSequence.append("\t");
        charSequence.append(timeZone);
        charSequence = charSequence.toString();
        android.icu.text.DateIntervalFormat dateIntervalFormat = CACHED_FORMATTERS.get((String)charSequence);
        if (dateIntervalFormat != null) {
            return dateIntervalFormat;
        }
        object = android.icu.text.DateIntervalFormat.getInstance((String)object, uLocale);
        ((android.icu.text.DateIntervalFormat)object).setTimeZone(timeZone);
        CACHED_FORMATTERS.put((String)charSequence, (android.icu.text.DateIntervalFormat)object);
        return object;
    }

    private static boolean isExactlyMidnight(Calendar calendar) {
        boolean bl = calendar.get(11) == 0 && calendar.get(12) == 0 && calendar.get(13) == 0 && calendar.get(14) == 0;
        return bl;
    }
}

