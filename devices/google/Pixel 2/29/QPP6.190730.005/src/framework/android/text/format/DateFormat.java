/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.icu.ICU
 *  libcore.icu.LocaleData
 */
package android.text.format;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.provider.Settings;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import libcore.icu.ICU;
import libcore.icu.LocaleData;

public class DateFormat {
    @Deprecated
    public static final char AM_PM = 'a';
    @Deprecated
    public static final char CAPITAL_AM_PM = 'A';
    @Deprecated
    public static final char DATE = 'd';
    @Deprecated
    public static final char DAY = 'E';
    @Deprecated
    public static final char HOUR = 'h';
    @Deprecated
    public static final char HOUR_OF_DAY = 'k';
    @Deprecated
    public static final char MINUTE = 'm';
    @Deprecated
    public static final char MONTH = 'M';
    @Deprecated
    public static final char QUOTE = '\'';
    @Deprecated
    public static final char SECONDS = 's';
    @Deprecated
    public static final char STANDALONE_MONTH = 'L';
    @Deprecated
    public static final char TIME_ZONE = 'z';
    @Deprecated
    public static final char YEAR = 'y';
    private static boolean sIs24Hour;
    private static Locale sIs24HourLocale;
    private static final Object sLocaleLock;

    static {
        sLocaleLock = new Object();
    }

    public static int appendQuotedText(SpannableStringBuilder spannableStringBuilder, int n) {
        int n2 = spannableStringBuilder.length();
        if (n + 1 < n2 && spannableStringBuilder.charAt(n + 1) == '\'') {
            spannableStringBuilder.delete(n, n + 1);
            return 1;
        }
        int n3 = 0;
        spannableStringBuilder.delete(n, n + 1);
        --n2;
        while (n < n2) {
            if (spannableStringBuilder.charAt(n) == '\'') {
                if (n + 1 < n2 && spannableStringBuilder.charAt(n + 1) == '\'') {
                    spannableStringBuilder.delete(n, n + 1);
                    --n2;
                    ++n3;
                    ++n;
                    continue;
                }
                spannableStringBuilder.delete(n, n + 1);
                break;
            }
            ++n;
            ++n3;
        }
        return n3;
    }

    public static CharSequence format(CharSequence charSequence, long l) {
        return DateFormat.format(charSequence, new Date(l));
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static CharSequence format(CharSequence var0, Calendar var1_1) {
        var2_2 = new SpannableStringBuilder(var0);
        var3_3 = LocaleData.get((Locale)Locale.getDefault());
        var4_4 = var0.length();
        for (var5_5 = 0; var5_5 < var4_4; var5_5 += var8_8) {
            block12 : {
                block9 : {
                    block10 : {
                        block11 : {
                            var6_6 = 1;
                            var7_7 = var2_2.charAt(var5_5);
                            if (var7_7 == '\'') {
                                var8_8 = DateFormat.appendQuotedText(var2_2, var5_5);
                                var4_4 = var2_2.length();
                                continue;
                            }
                            while (var5_5 + var6_6 < var4_4 && var2_2.charAt(var5_5 + var6_6) == var7_7) {
                                ++var6_6;
                            }
                            if (var7_7 == 'A') break block9;
                            if (var7_7 == 'E') break block10;
                            if (var7_7 == 'H') break block11;
                            if (var7_7 == 'a') break block9;
                            if (var7_7 == 'h') ** GOTO lbl-1000
                            if (var7_7 == 'k') break block11;
                            if (var7_7 == 'm') ** GOTO lbl41
                            if (var7_7 == 's') ** GOTO lbl39
                            if (var7_7 == 'c') break block10;
                            if (var7_7 == 'd') ** GOTO lbl37
                            if (var7_7 == 'y') ** GOTO lbl35
                            if (var7_7 == 'z') ** GOTO lbl33
                            switch (var7_7) {
                                default: {
                                    var9_9 = null;
                                    break;
                                }
                                case 'L': 
                                case 'M': {
                                    var9_9 = DateFormat.getMonthString(var3_3, var1_1.get(2), var6_6, var7_7);
                                    break;
                                }
lbl33: // 1 sources:
                                var9_9 = DateFormat.getTimeZoneString(var1_1, var6_6);
                                break;
lbl35: // 1 sources:
                                var9_9 = DateFormat.getYearString(var1_1.get(1), var6_6);
                                break;
lbl37: // 1 sources:
                                var9_9 = DateFormat.zeroPad(var1_1.get(5), var6_6);
                                break;
lbl39: // 1 sources:
                                var9_9 = DateFormat.zeroPad(var1_1.get(13), var6_6);
                                break;
lbl41: // 1 sources:
                                var9_9 = DateFormat.zeroPad(var1_1.get(12), var6_6);
                                break;
                                case 'K': lbl-1000: // 2 sources:
                                {
                                    var8_8 = var10_10 = var1_1.get(10);
                                    if (var7_7 == 'h') {
                                        var8_8 = var10_10;
                                        if (var10_10 == 0) {
                                            var8_8 = 12;
                                        }
                                    }
                                    var9_9 = DateFormat.zeroPad(var8_8, var6_6);
                                    break;
                                }
                            }
                            break block12;
                        }
                        var9_9 = DateFormat.zeroPad(var1_1.get(11), var6_6);
                        break block12;
                    }
                    var9_9 = DateFormat.getDayOfWeekString(var3_3, var1_1.get(7), var6_6, var7_7);
                    break block12;
                }
                var9_9 = var3_3.amPm[var1_1.get(9) + 0];
            }
            var8_8 = var6_6;
            if (var9_9 == null) continue;
            var2_2.replace(var5_5, var5_5 + var6_6, var9_9);
            var8_8 = var9_9.length();
            var4_4 = var2_2.length();
        }
        if (!(var0 instanceof Spanned)) return var2_2.toString();
        return new SpannedString(var2_2);
    }

    public static CharSequence format(CharSequence charSequence, Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return DateFormat.format(charSequence, gregorianCalendar);
    }

    private static String formatZoneOffset(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        if ((n /= 1000) < 0) {
            stringBuilder.insert(0, "-");
            n = -n;
        } else {
            stringBuilder.insert(0, "+");
        }
        n2 = n / 3600;
        n = n % 3600 / 60;
        stringBuilder.append(DateFormat.zeroPad(n2, 2));
        stringBuilder.append(DateFormat.zeroPad(n, 2));
        return stringBuilder.toString();
    }

    public static String getBestDateTimePattern(Locale locale, String string2) {
        return ICU.getBestDateTimePattern((String)string2, (Locale)locale);
    }

    public static java.text.DateFormat getDateFormat(Context context) {
        return java.text.DateFormat.getDateInstance(3, context.getResources().getConfiguration().locale);
    }

    public static char[] getDateFormatOrder(Context context) {
        return ICU.getDateFormatOrder((String)DateFormat.getDateFormatString(context));
    }

    private static String getDateFormatString(Context object) {
        object = java.text.DateFormat.getDateInstance(3, object.getResources().getConfiguration().locale);
        if (object instanceof SimpleDateFormat) {
            return ((SimpleDateFormat)object).toPattern();
        }
        throw new AssertionError((Object)"!(df instanceof SimpleDateFormat)");
    }

    private static String getDayOfWeekString(LocaleData object, int n, int n2, int n3) {
        n3 = n3 == 99 ? 1 : 0;
        if (n2 == 5) {
            object = n3 != 0 ? object.tinyStandAloneWeekdayNames[n] : object.tinyWeekdayNames[n];
            return object;
        }
        if (n2 == 4) {
            object = n3 != 0 ? object.longStandAloneWeekdayNames[n] : object.longWeekdayNames[n];
            return object;
        }
        object = n3 != 0 ? object.shortStandAloneWeekdayNames[n] : object.shortWeekdayNames[n];
        return object;
    }

    public static java.text.DateFormat getLongDateFormat(Context context) {
        return java.text.DateFormat.getDateInstance(1, context.getResources().getConfiguration().locale);
    }

    public static java.text.DateFormat getMediumDateFormat(Context context) {
        return java.text.DateFormat.getDateInstance(2, context.getResources().getConfiguration().locale);
    }

    private static String getMonthString(LocaleData object, int n, int n2, int n3) {
        n3 = n3 == 76 ? 1 : 0;
        if (n2 == 5) {
            object = n3 != 0 ? object.tinyStandAloneMonthNames[n] : object.tinyMonthNames[n];
            return object;
        }
        if (n2 == 4) {
            object = n3 != 0 ? object.longStandAloneMonthNames[n] : object.longMonthNames[n];
            return object;
        }
        if (n2 == 3) {
            object = n3 != 0 ? object.shortStandAloneMonthNames[n] : object.shortMonthNames[n];
            return object;
        }
        return DateFormat.zeroPad(n + 1, n2);
    }

    public static java.text.DateFormat getTimeFormat(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        return new SimpleDateFormat(DateFormat.getTimeFormatString(context), locale);
    }

    @UnsupportedAppUsage
    public static String getTimeFormatString(Context context) {
        return DateFormat.getTimeFormatString(context, context.getUserId());
    }

    @UnsupportedAppUsage
    public static String getTimeFormatString(Context object, int n) {
        LocaleData localeData = LocaleData.get((Locale)object.getResources().getConfiguration().locale);
        object = DateFormat.is24HourFormat((Context)object, n) ? localeData.timeFormat_Hm : localeData.timeFormat_hm;
        return object;
    }

    private static String getTimeZoneString(Calendar calendar, int n) {
        TimeZone timeZone = calendar.getTimeZone();
        if (n < 2) {
            return DateFormat.formatZoneOffset(calendar.get(16) + calendar.get(15), n);
        }
        boolean bl = calendar.get(16) != 0;
        return timeZone.getDisplayName(bl, 0);
    }

    private static String getYearString(int n, int n2) {
        String string2 = n2 <= 2 ? DateFormat.zeroPad(n % 100, 2) : String.format(Locale.getDefault(), "%d", n);
        return string2;
    }

    @UnsupportedAppUsage
    public static boolean hasDesignator(CharSequence charSequence, char c) {
        if (charSequence == null) {
            return false;
        }
        int n = charSequence.length();
        boolean bl = false;
        for (int i = 0; i < n; ++i) {
            char c2 = charSequence.charAt(i);
            boolean bl2 = true;
            if (c2 == '\'') {
                bl = !bl ? bl2 : false;
                bl2 = bl;
            } else {
                bl2 = bl;
                if (!bl) {
                    bl2 = bl;
                    if (c2 == c) {
                        return true;
                    }
                }
            }
            bl = bl2;
        }
        return false;
    }

    @UnsupportedAppUsage
    public static boolean hasSeconds(CharSequence charSequence) {
        return DateFormat.hasDesignator(charSequence, 's');
    }

    public static boolean is24HourFormat(Context context) {
        return DateFormat.is24HourFormat(context, context.getUserId());
    }

    @UnsupportedAppUsage
    public static boolean is24HourFormat(Context context, int n) {
        String string2 = Settings.System.getStringForUser(context.getContentResolver(), "time_12_24", n);
        if (string2 != null) {
            return string2.equals("24");
        }
        return DateFormat.is24HourLocale(context.getResources().getConfiguration().locale);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean is24HourLocale(Locale locale) {
        Object object = sLocaleLock;
        synchronized (object) {
            if (sIs24HourLocale != null && sIs24HourLocale.equals(locale)) {
                return sIs24Hour;
            }
        }
        object = java.text.DateFormat.getTimeInstance(1, locale);
        boolean bl = object instanceof SimpleDateFormat ? DateFormat.hasDesignator(((SimpleDateFormat)object).toPattern(), 'H') : false;
        object = sLocaleLock;
        synchronized (object) {
            sIs24HourLocale = locale;
            sIs24Hour = bl;
            return bl;
        }
    }

    private static String zeroPad(int n, int n2) {
        Locale locale = Locale.getDefault();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("%0");
        stringBuilder.append(n2);
        stringBuilder.append("d");
        return String.format(locale, stringBuilder.toString(), n);
    }
}

