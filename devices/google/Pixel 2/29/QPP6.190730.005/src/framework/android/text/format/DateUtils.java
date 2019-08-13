/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.MeasureFormat
 *  android.icu.text.MeasureFormat$FormatWidth
 *  android.icu.util.Measure
 *  android.icu.util.MeasureUnit
 *  android.icu.util.TimeUnit
 *  libcore.icu.DateIntervalFormat
 *  libcore.icu.LocaleData
 *  libcore.icu.RelativeDateTimeFormatter
 */
package android.text.format;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.icu.text.MeasureFormat;
import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.icu.util.TimeUnit;
import android.text.format.DateFormat;
import android.text.format.Time;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import libcore.icu.DateIntervalFormat;
import libcore.icu.LocaleData;
import libcore.icu.RelativeDateTimeFormatter;

public class DateUtils {
    @Deprecated
    public static final String ABBREV_MONTH_FORMAT = "%b";
    public static final String ABBREV_WEEKDAY_FORMAT = "%a";
    public static final long DAY_IN_MILLIS = 86400000L;
    @Deprecated
    public static final int FORMAT_12HOUR = 64;
    @Deprecated
    public static final int FORMAT_24HOUR = 128;
    public static final int FORMAT_ABBREV_ALL = 524288;
    public static final int FORMAT_ABBREV_MONTH = 65536;
    public static final int FORMAT_ABBREV_RELATIVE = 262144;
    public static final int FORMAT_ABBREV_TIME = 16384;
    public static final int FORMAT_ABBREV_WEEKDAY = 32768;
    @Deprecated
    public static final int FORMAT_CAP_AMPM = 256;
    @Deprecated
    public static final int FORMAT_CAP_MIDNIGHT = 4096;
    @Deprecated
    public static final int FORMAT_CAP_NOON = 1024;
    @Deprecated
    public static final int FORMAT_CAP_NOON_MIDNIGHT = 5120;
    public static final int FORMAT_NO_MIDNIGHT = 2048;
    public static final int FORMAT_NO_MONTH_DAY = 32;
    public static final int FORMAT_NO_NOON = 512;
    @Deprecated
    public static final int FORMAT_NO_NOON_MIDNIGHT = 2560;
    public static final int FORMAT_NO_YEAR = 8;
    public static final int FORMAT_NUMERIC_DATE = 131072;
    public static final int FORMAT_SHOW_DATE = 16;
    public static final int FORMAT_SHOW_TIME = 1;
    public static final int FORMAT_SHOW_WEEKDAY = 2;
    public static final int FORMAT_SHOW_YEAR = 4;
    @Deprecated
    public static final int FORMAT_UTC = 8192;
    public static final long HOUR_IN_MILLIS = 3600000L;
    @Deprecated
    public static final String HOUR_MINUTE_24 = "%H:%M";
    @Deprecated
    public static final int LENGTH_LONG = 10;
    @Deprecated
    public static final int LENGTH_MEDIUM = 20;
    @Deprecated
    public static final int LENGTH_SHORT = 30;
    @Deprecated
    public static final int LENGTH_SHORTER = 40;
    @Deprecated
    public static final int LENGTH_SHORTEST = 50;
    public static final long MINUTE_IN_MILLIS = 60000L;
    public static final String MONTH_DAY_FORMAT = "%-d";
    public static final String MONTH_FORMAT = "%B";
    public static final String NUMERIC_MONTH_FORMAT = "%m";
    public static final long SECOND_IN_MILLIS = 1000L;
    public static final String WEEKDAY_FORMAT = "%A";
    public static final long WEEK_IN_MILLIS = 604800000L;
    public static final String YEAR_FORMAT = "%Y";
    public static final String YEAR_FORMAT_TWO_DIGITS = "%g";
    public static final long YEAR_IN_MILLIS = 31449600000L;
    private static String sElapsedFormatHMMSS;
    private static String sElapsedFormatMMSS;
    private static Configuration sLastConfig;
    private static final Object sLock;
    private static Time sNowTime;
    private static Time sThenTime;
    @Deprecated
    public static final int[] sameMonthTable;
    @Deprecated
    public static final int[] sameYearTable;

    static {
        sLock = new Object();
        sameYearTable = null;
        sameMonthTable = null;
    }

    public static String formatDateRange(Context context, long l, long l2, int n) {
        return DateUtils.formatDateRange(context, new Formatter(new StringBuilder(50), Locale.getDefault()), l, l2, n).toString();
    }

    public static Formatter formatDateRange(Context context, Formatter formatter, long l, long l2, int n) {
        return DateUtils.formatDateRange(context, formatter, l, l2, n, null);
    }

    public static Formatter formatDateRange(Context object, Formatter formatter, long l, long l2, int n, String string2) {
        int n2 = n;
        if ((n & 193) == 1) {
            n2 = DateFormat.is24HourFormat((Context)object) ? 128 : 64;
            n2 = n | n2;
        }
        object = DateIntervalFormat.formatDateRange((long)l, (long)l2, (int)n2, (String)string2);
        try {
            formatter.out().append((CharSequence)object);
            return formatter;
        }
        catch (IOException iOException) {
            throw new AssertionError(iOException);
        }
    }

    public static String formatDateTime(Context context, long l, int n) {
        return DateUtils.formatDateRange(context, l, l, n);
    }

    @UnsupportedAppUsage
    public static CharSequence formatDuration(long l) {
        return DateUtils.formatDuration(l, 10);
    }

    @UnsupportedAppUsage
    public static CharSequence formatDuration(long l, int n) {
        MeasureFormat.FormatWidth formatWidth = n != 10 ? (n != 20 && n != 30 && n != 40 ? (n != 50 ? MeasureFormat.FormatWidth.WIDE : MeasureFormat.FormatWidth.NARROW) : MeasureFormat.FormatWidth.SHORT) : MeasureFormat.FormatWidth.WIDE;
        formatWidth = MeasureFormat.getInstance((Locale)Locale.getDefault(), (MeasureFormat.FormatWidth)formatWidth);
        if (l >= 3600000L) {
            return formatWidth.format((Object)new Measure((Number)((int)((1800000L + l) / 3600000L)), (MeasureUnit)MeasureUnit.HOUR));
        }
        if (l >= 60000L) {
            return formatWidth.format((Object)new Measure((Number)((int)((30000L + l) / 60000L)), (MeasureUnit)MeasureUnit.MINUTE));
        }
        return formatWidth.format((Object)new Measure((Number)((int)((500L + l) / 1000L)), (MeasureUnit)MeasureUnit.SECOND));
    }

    public static String formatElapsedTime(long l) {
        return DateUtils.formatElapsedTime(null, l);
    }

    public static String formatElapsedTime(StringBuilder object, long l) {
        long l2 = 0L;
        long l3 = 0L;
        if (l >= 3600L) {
            l2 = l / 3600L;
            l -= 3600L * l2;
        }
        long l4 = l;
        if (l >= 60L) {
            l3 = l / 60L;
            l4 = l - 60L * l3;
        }
        if (object == null) {
            object = new StringBuilder(8);
        } else {
            ((StringBuilder)object).setLength(0);
        }
        object = new Formatter((Appendable)object, Locale.getDefault());
        DateUtils.initFormatStrings();
        if (l2 > 0L) {
            return ((Formatter)object).format(sElapsedFormatHMMSS, l2, l3, l4).toString();
        }
        return ((Formatter)object).format(sElapsedFormatMMSS, l3, l4).toString();
    }

    public static final CharSequence formatSameDayTime(long l, long l2, int n, int n2) {
        Cloneable cloneable = new GregorianCalendar();
        ((Calendar)cloneable).setTimeInMillis(l);
        Date date = ((Calendar)cloneable).getTime();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(l2);
        cloneable = ((Calendar)cloneable).get(1) == gregorianCalendar.get(1) && ((Calendar)cloneable).get(2) == gregorianCalendar.get(2) && ((Calendar)cloneable).get(5) == gregorianCalendar.get(5) ? java.text.DateFormat.getTimeInstance(n2) : java.text.DateFormat.getDateInstance(n);
        return ((java.text.DateFormat)cloneable).format(date);
    }

    @Deprecated
    public static String getAMPMString(int n) {
        return LocaleData.get((Locale)Locale.getDefault()).amPm[n + 0];
    }

    @Deprecated
    public static String getDayOfWeekString(int n, int n2) {
        String[] arrstring = LocaleData.get((Locale)Locale.getDefault());
        arrstring = n2 != 10 ? (n2 != 20 ? (n2 != 30 ? (n2 != 40 ? (n2 != 50 ? arrstring.shortWeekdayNames : arrstring.tinyWeekdayNames) : arrstring.shortWeekdayNames) : arrstring.shortWeekdayNames) : arrstring.shortWeekdayNames) : arrstring.longWeekdayNames;
        return arrstring[n];
    }

    @Deprecated
    public static String getMonthString(int n, int n2) {
        String[] arrstring = LocaleData.get((Locale)Locale.getDefault());
        arrstring = n2 != 10 ? (n2 != 20 ? (n2 != 30 ? (n2 != 40 ? (n2 != 50 ? arrstring.shortMonthNames : arrstring.tinyMonthNames) : arrstring.shortMonthNames) : arrstring.shortMonthNames) : arrstring.shortMonthNames) : arrstring.longMonthNames;
        return arrstring[n];
    }

    public static CharSequence getRelativeDateTimeString(Context context, long l, long l2, long l3, int n) {
        int n2 = n;
        if ((n & 193) == 1) {
            n2 = DateFormat.is24HourFormat(context) ? 128 : 64;
            n2 = n | n2;
        }
        return RelativeDateTimeFormatter.getRelativeDateTimeString((Locale)Locale.getDefault(), (TimeZone)TimeZone.getDefault(), (long)l, (long)System.currentTimeMillis(), (long)l2, (long)l3, (int)n2);
    }

    public static CharSequence getRelativeTimeSpanString(long l) {
        return DateUtils.getRelativeTimeSpanString(l, System.currentTimeMillis(), 60000L);
    }

    public static CharSequence getRelativeTimeSpanString(long l, long l2, long l3) {
        return DateUtils.getRelativeTimeSpanString(l, l2, l3, 65556);
    }

    public static CharSequence getRelativeTimeSpanString(long l, long l2, long l3, int n) {
        return RelativeDateTimeFormatter.getRelativeTimeSpanString((Locale)Locale.getDefault(), (TimeZone)TimeZone.getDefault(), (long)l, (long)l2, (long)l3, (int)n);
    }

    public static CharSequence getRelativeTimeSpanString(Context context, long l) {
        return DateUtils.getRelativeTimeSpanString(context, l, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static CharSequence getRelativeTimeSpanString(Context context, long l, boolean bl) {
        long l2 = System.currentTimeMillis();
        long l3 = Math.abs(l2 - l);
        synchronized (DateUtils.class) {
            int n;
            Object object;
            if (sNowTime == null) {
                object = new Time();
                sNowTime = object;
            }
            if (sThenTime == null) {
                object = new Time();
                sThenTime = object;
            }
            sNowTime.set(l2);
            sThenTime.set(l);
            if (l3 < 86400000L && DateUtils.sNowTime.weekDay == DateUtils.sThenTime.weekDay) {
                object = DateUtils.formatDateRange(context, l, l, 1);
                n = 17040880;
            } else if (DateUtils.sNowTime.year != DateUtils.sThenTime.year) {
                object = DateUtils.formatDateRange(context, l, l, 131092);
                n = 17040879;
            } else {
                object = DateUtils.formatDateRange(context, l, l, 65552);
                n = 17040879;
            }
            Object object2 = object;
            if (!bl) return object2;
            return context.getResources().getString(n, object);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void initFormatStrings() {
        Object object = sLock;
        synchronized (object) {
            DateUtils.initFormatStringsLocked();
            return;
        }
    }

    private static void initFormatStringsLocked() {
        Resources resources = Resources.getSystem();
        Configuration configuration = resources.getConfiguration();
        Configuration configuration2 = sLastConfig;
        if (configuration2 == null || !configuration2.equals(configuration)) {
            sLastConfig = configuration;
            sElapsedFormatMMSS = resources.getString(17039887);
            sElapsedFormatHMMSS = resources.getString(17039886);
        }
    }

    public static boolean isToday(long l) {
        Time time = new Time();
        time.set(l);
        int n = time.year;
        int n2 = time.month;
        int n3 = time.monthDay;
        time.set(System.currentTimeMillis());
        boolean bl = n == time.year && n2 == time.month && n3 == time.monthDay;
        return bl;
    }
}

