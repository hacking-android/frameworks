/*
 * Decompiled with CFR 0.145.
 */
package libcore.icu;

import android.icu.text.DisplayContext;
import android.icu.text.RelativeDateTimeFormatter;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import java.util.Locale;
import libcore.icu.DateTimeFormat;
import libcore.icu.DateUtilsBridge;
import libcore.util.BasicLruCache;

public final class RelativeDateTimeFormatter {
    private static final FormatterCache CACHED_FORMATTERS = new FormatterCache();
    public static final long DAY_IN_MILLIS = 86400000L;
    private static final int DAY_IN_MS = 86400000;
    private static final int EPOCH_JULIAN_DAY = 2440588;
    public static final long HOUR_IN_MILLIS = 3600000L;
    public static final long MINUTE_IN_MILLIS = 60000L;
    public static final long SECOND_IN_MILLIS = 1000L;
    public static final long WEEK_IN_MILLIS = 604800000L;
    public static final long YEAR_IN_MILLIS = 31449600000L;

    private RelativeDateTimeFormatter() {
    }

    private static int dayDistance(TimeZone timeZone, long l, long l2) {
        return RelativeDateTimeFormatter.julianDay(timeZone, l2) - RelativeDateTimeFormatter.julianDay(timeZone, l);
    }

    private static android.icu.text.RelativeDateTimeFormatter getFormatter(ULocale uLocale, RelativeDateTimeFormatter.Style style, DisplayContext displayContext) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append(uLocale);
        ((StringBuilder)object).append("\t");
        ((StringBuilder)object).append((Object)style);
        ((StringBuilder)object).append("\t");
        ((StringBuilder)object).append((Object)displayContext);
        String string = ((StringBuilder)object).toString();
        android.icu.text.RelativeDateTimeFormatter relativeDateTimeFormatter = (android.icu.text.RelativeDateTimeFormatter)CACHED_FORMATTERS.get(string);
        object = relativeDateTimeFormatter;
        if (relativeDateTimeFormatter == null) {
            object = android.icu.text.RelativeDateTimeFormatter.getInstance(uLocale, null, style, displayContext);
            CACHED_FORMATTERS.put(string, object);
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getRelativeDateTimeString(Locale object, java.util.TimeZone object2, long l, long l2, long l3, long l4, int n) {
        if (object == null) {
            throw new NullPointerException("locale == null");
        }
        if (object2 == null) {
            throw new NullPointerException("tz == null");
        }
        ULocale uLocale = ULocale.forLocale(object);
        object2 = DateUtilsBridge.icuTimeZone((java.util.TimeZone)object2);
        long l5 = Math.abs(l2 - l);
        if (l4 > 604800000L) {
            l4 = 604800000L;
        }
        object = (n & 786432) != 0 ? RelativeDateTimeFormatter.Style.SHORT : RelativeDateTimeFormatter.Style.LONG;
        Object object3 = DateUtilsBridge.createIcuCalendar((TimeZone)object2, uLocale, l);
        Object object4 = DateUtilsBridge.createIcuCalendar((TimeZone)object2, uLocale, l2);
        int n2 = Math.abs(DateUtilsBridge.dayDistance((Calendar)object3, object4));
        if (l5 < l4) {
            if (n2 > 0 && l3 < 86400000L) {
                l3 = 86400000L;
            }
            object2 = RelativeDateTimeFormatter.getRelativeTimeSpanString(uLocale, (TimeZone)object2, l, l2, l3, n, DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE);
        } else {
            object2 = object3;
            n = 1;
            n = ((Calendar)object2).get(n) != object4.get(n) ? 131092 : 65560;
            object2 = DateTimeFormat.format(uLocale, (Calendar)object2, n, DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE);
        }
        String string = DateTimeFormat.format(uLocale, (Calendar)object3, 1, DisplayContext.CAPITALIZATION_NONE);
        object4 = DisplayContext.CAPITALIZATION_NONE;
        object3 = CACHED_FORMATTERS;
        synchronized (object3) {
            return RelativeDateTimeFormatter.getFormatter(uLocale, (RelativeDateTimeFormatter.Style)((Object)object), (DisplayContext)((Object)object4)).combineDateAndTime((String)object2, string);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String getRelativeTimeSpanString(ULocale var0, TimeZone var1_5, long var2_6, long var4_7, long var6_8, int var8_9, DisplayContext var9_10) {
        block31 : {
            block25 : {
                block28 : {
                    block29 : {
                        block23 : {
                            block30 : {
                                block27 : {
                                    block26 : {
                                        block24 : {
                                            var10_11 = Math.abs(var4_7 - var2_6);
                                            var12_12 = var4_7 >= var2_6;
                                            var13_13 = (var8_9 & 786432) != 0 ? RelativeDateTimeFormatter.Style.SHORT : RelativeDateTimeFormatter.Style.LONG;
                                            var14_14 /* !! */  = var12_12 != false ? RelativeDateTimeFormatter.Direction.LAST : RelativeDateTimeFormatter.Direction.NEXT;
                                            if (var10_11 >= 60000L || var6_8 >= 60000L) break block24;
                                            var8_9 = (int)(var10_11 / 1000L);
                                            var15_15 /* !! */  = RelativeDateTimeFormatter.RelativeUnit.SECONDS;
                                            var16_16 /* !! */  = null;
                                            var1_5 /* !! */  = var14_14 /* !! */ ;
                                            var12_12 = true;
                                            var14_14 /* !! */  = var16_16 /* !! */ ;
                                            break block25;
                                        }
                                        if (var10_11 >= 3600000L || var6_8 >= 3600000L) break block26;
                                        var8_9 = (int)(var10_11 / 60000L);
                                        var15_15 /* !! */  = RelativeDateTimeFormatter.RelativeUnit.MINUTES;
                                        var16_16 /* !! */  = null;
                                        var1_5 /* !! */  = var14_14 /* !! */ ;
                                        var12_12 = true;
                                        var14_14 /* !! */  = var16_16 /* !! */ ;
                                        break block25;
                                    }
                                    if (var10_11 >= 86400000L || var6_8 >= 86400000L) break block27;
                                    var8_9 = (int)(var10_11 / 3600000L);
                                    var15_15 /* !! */  = RelativeDateTimeFormatter.RelativeUnit.HOURS;
                                    var16_16 /* !! */  = null;
                                    var1_5 /* !! */  = var14_14 /* !! */ ;
                                    var12_12 = true;
                                    var14_14 /* !! */  = var16_16 /* !! */ ;
                                    break block25;
                                }
                                if (var10_11 >= 604800000L || var6_8 >= 604800000L) break block28;
                                var8_9 = Math.abs(RelativeDateTimeFormatter.dayDistance(var1_5 /* !! */ , var2_6, var4_7));
                                var15_15 /* !! */  = RelativeDateTimeFormatter.RelativeUnit.DAYS;
                                if (var8_9 != 2) break block29;
                                if (!var12_12) break block30;
                                var16_16 /* !! */  = RelativeDateTimeFormatter.CACHED_FORMATTERS;
                                synchronized (var16_16 /* !! */ ) {
                                    var1_5 /* !! */  = RelativeDateTimeFormatter.getFormatter((ULocale)var0, var13_13, var9_10);
                                    var17_17 = RelativeDateTimeFormatter.Direction.LAST_2;
                                    {
                                        catch (Throwable var0_1) {}
                                    }
                                    try {
                                        var1_5 /* !! */  = var1_5 /* !! */ .format(var17_17, RelativeDateTimeFormatter.AbsoluteUnit.DAY);
                                        break block23;
                                    }
                                    catch (Throwable var0_3) {}
                                    {
                                        throw var0_2;
                                    }
                                }
                            }
                            var16_16 /* !! */  = RelativeDateTimeFormatter.CACHED_FORMATTERS;
                            synchronized (var16_16 /* !! */ ) {
                                var1_5 /* !! */  = RelativeDateTimeFormatter.getFormatter((ULocale)var0, var13_13, var9_10).format(RelativeDateTimeFormatter.Direction.NEXT_2, RelativeDateTimeFormatter.AbsoluteUnit.DAY);
                            }
                        }
                        if (var1_5 /* !! */  != null && !var1_5 /* !! */ .isEmpty()) {
                            return var1_5 /* !! */ ;
                        }
                        ** GOTO lbl-1000
                    }
                    var1_5 /* !! */  = var15_15 /* !! */ ;
                    if (var8_9 == 1) {
                        var16_16 /* !! */  = RelativeDateTimeFormatter.AbsoluteUnit.DAY;
                        var12_12 = false;
                        var15_15 /* !! */  = var1_5 /* !! */ ;
                        var1_5 /* !! */  = var14_14 /* !! */ ;
                        var14_14 /* !! */  = var16_16 /* !! */ ;
                    } else if (var8_9 == 0) {
                        var14_14 /* !! */  = RelativeDateTimeFormatter.AbsoluteUnit.DAY;
                        var15_15 /* !! */  = RelativeDateTimeFormatter.Direction.THIS;
                        var12_12 = false;
                        var16_16 /* !! */  = var1_5 /* !! */ ;
                        var1_5 /* !! */  = var15_15 /* !! */ ;
                        var15_15 /* !! */  = var16_16 /* !! */ ;
                    } else lbl-1000: // 2 sources:
                    {
                        var16_16 /* !! */  = null;
                        var1_5 /* !! */  = var14_14 /* !! */ ;
                        var12_12 = true;
                        var14_14 /* !! */  = var16_16 /* !! */ ;
                    }
                    break block25;
                }
                if (var6_8 != 604800000L) break block31;
                var8_9 = (int)(var10_11 / 604800000L);
                var15_15 /* !! */  = RelativeDateTimeFormatter.RelativeUnit.WEEKS;
                var16_16 /* !! */  = null;
                var1_5 /* !! */  = var14_14 /* !! */ ;
                var12_12 = true;
                var14_14 /* !! */  = var16_16 /* !! */ ;
            }
            var16_16 /* !! */  = RelativeDateTimeFormatter.CACHED_FORMATTERS;
            synchronized (var16_16 /* !! */ ) {
                var0 = RelativeDateTimeFormatter.getFormatter((ULocale)var0, var13_13, var9_10);
                if (var12_12 == false) return var0.format((RelativeDateTimeFormatter.Direction)var1_5 /* !! */ , (RelativeDateTimeFormatter.AbsoluteUnit)var14_14 /* !! */ );
                var18_18 = var8_9;
                try {
                    return var0.format(var18_18, (RelativeDateTimeFormatter.Direction)var1_5 /* !! */ , var15_15 /* !! */ );
                }
                catch (Throwable var0_4) {}
                throw var0_4;
            }
        }
        var14_14 /* !! */  = DateUtilsBridge.createIcuCalendar(var1_5 /* !! */ , (ULocale)var0, var2_6);
        if ((var8_9 & 12) != 0) return DateTimeFormat.format((ULocale)var0, (Calendar)var14_14 /* !! */ , var8_9, var9_10);
        var1_5 /* !! */  = DateUtilsBridge.createIcuCalendar(var1_5 /* !! */ , (ULocale)var0, var4_7);
        if (var14_14 /* !! */ .get(1) != var1_5 /* !! */ .get(1)) {
            return DateTimeFormat.format((ULocale)var0, (Calendar)var14_14 /* !! */ , var8_9 |= 4, var9_10);
        }
        return DateTimeFormat.format((ULocale)var0, (Calendar)var14_14 /* !! */ , var8_9 |= 8, var9_10);
    }

    public static String getRelativeTimeSpanString(Locale locale, java.util.TimeZone timeZone, long l, long l2, long l3, int n) {
        return RelativeDateTimeFormatter.getRelativeTimeSpanString(locale, timeZone, l, l2, l3, n, DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE);
    }

    public static String getRelativeTimeSpanString(Locale locale, java.util.TimeZone timeZone, long l, long l2, long l3, int n, DisplayContext displayContext) {
        if (locale != null) {
            if (timeZone != null) {
                return RelativeDateTimeFormatter.getRelativeTimeSpanString(ULocale.forLocale(locale), DateUtilsBridge.icuTimeZone(timeZone), l, l2, l3, n, displayContext);
            }
            throw new NullPointerException("tz == null");
        }
        throw new NullPointerException("locale == null");
    }

    private static int julianDay(TimeZone timeZone, long l) {
        return (int)(((long)timeZone.getOffset(l) + l) / 86400000L) + 2440588;
    }

    static class FormatterCache
    extends BasicLruCache<String, android.icu.text.RelativeDateTimeFormatter> {
        FormatterCache() {
            super(8);
        }
    }

}

