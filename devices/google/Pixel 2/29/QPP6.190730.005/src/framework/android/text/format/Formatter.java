/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.MeasureFormat
 *  android.icu.text.MeasureFormat$FormatWidth
 *  android.icu.util.Measure
 *  android.icu.util.MeasureUnit
 *  android.icu.util.TimeUnit
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
import android.net.NetworkUtils;
import android.os.LocaleList;
import android.text.BidiFormatter;
import android.text.TextUtils;
import java.util.Locale;

public final class Formatter {
    public static final int FLAG_CALCULATE_ROUNDED = 2;
    public static final int FLAG_IEC_UNITS = 8;
    public static final int FLAG_SHORTER = 1;
    public static final int FLAG_SI_UNITS = 4;
    private static final int MILLIS_PER_MINUTE = 60000;
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;

    private static String bidiWrap(Context context, String string2) {
        if (TextUtils.getLayoutDirectionFromLocale(Formatter.localeFromContext(context)) == 1) {
            return BidiFormatter.getInstance(true).unicodeWrap(string2);
        }
        return string2;
    }

    @UnsupportedAppUsage
    public static BytesResult formatBytes(Resources resources, long l, int n) {
        String string2;
        int n2 = (n & 8) != 0 ? 1024 : 1000;
        long l2 = 0L;
        boolean bl = l < 0L;
        float f = bl ? (float)(-l) : (float)l;
        int n3 = 17039625;
        long l3 = 1L;
        float f2 = f;
        if (f > 900.0f) {
            n3 = 17040223;
            l3 = n2;
            f2 = f / (float)n2;
        }
        float f3 = f2;
        l = l3;
        if (f2 > 900.0f) {
            n3 = 17040401;
            l = l3 * (long)n2;
            f3 = f2 / (float)n2;
        }
        f = f3;
        l3 = l;
        if (f3 > 900.0f) {
            n3 = 17040048;
            l3 = l * (long)n2;
            f = f3 / (float)n2;
        }
        f2 = f;
        l = l3;
        if (f > 900.0f) {
            n3 = 17041121;
            l = l3 * (long)n2;
            f2 = f / (float)n2;
        }
        f = f2;
        int n4 = n3;
        l3 = l;
        if (f2 > 900.0f) {
            n4 = 17040826;
            l3 = l * (long)n2;
            f = f2 / (float)n2;
        }
        if (l3 != 1L && !(f >= 100.0f)) {
            if (f < 1.0f) {
                n3 = 100;
                string2 = "%.2f";
            } else if (f < 10.0f) {
                if ((n & 1) != 0) {
                    n3 = 10;
                    string2 = "%.1f";
                } else {
                    n3 = 100;
                    string2 = "%.2f";
                }
            } else if ((n & 1) != 0) {
                n3 = 1;
                string2 = "%.0f";
            } else {
                n3 = 100;
                string2 = "%.2f";
            }
        } else {
            n3 = 1;
            string2 = "%.0f";
        }
        f2 = f;
        if (bl) {
            f2 = -f;
        }
        string2 = String.format(string2, Float.valueOf(f2));
        l = (n & 2) == 0 ? l2 : (long)Math.round((float)n3 * f2) * l3 / (long)n3;
        return new BytesResult(string2, resources.getString(n4), l);
    }

    public static String formatFileSize(Context context, long l) {
        return Formatter.formatFileSize(context, l, 4);
    }

    public static String formatFileSize(Context context, long l, int n) {
        if (context == null) {
            return "";
        }
        BytesResult bytesResult = Formatter.formatBytes(context.getResources(), l, n);
        return Formatter.bidiWrap(context, context.getString(17040003, bytesResult.value, bytesResult.units));
    }

    @Deprecated
    public static String formatIpAddress(int n) {
        return NetworkUtils.intToInetAddress(n).getHostAddress();
    }

    @UnsupportedAppUsage
    public static String formatShortElapsedTime(Context context, long l) {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        long l2 = l /= 1000L;
        if (l >= 86400L) {
            n = (int)(l / 86400L);
            l2 = l - (long)(86400 * n);
        }
        l = l2;
        if (l2 >= 3600L) {
            n2 = (int)(l2 / 3600L);
            l = l2 - (long)(n2 * 3600);
        }
        l2 = l;
        if (l >= 60L) {
            n3 = (int)(l / 60L);
            l2 = l - (long)(n3 * 60);
        }
        int n4 = (int)l2;
        context = MeasureFormat.getInstance((Locale)Formatter.localeFromContext(context), (MeasureFormat.FormatWidth)MeasureFormat.FormatWidth.SHORT);
        if (n < 2 && (n <= 0 || n2 != 0)) {
            if (n > 0) {
                return context.formatMeasures(new Measure[]{new Measure((Number)n, (MeasureUnit)MeasureUnit.DAY), new Measure((Number)n2, (MeasureUnit)MeasureUnit.HOUR)});
            }
            if (n2 < 2 && (n2 <= 0 || n3 != 0)) {
                if (n2 > 0) {
                    return context.formatMeasures(new Measure[]{new Measure((Number)n2, (MeasureUnit)MeasureUnit.HOUR), new Measure((Number)n3, (MeasureUnit)MeasureUnit.MINUTE)});
                }
                if (n3 < 2 && (n3 <= 0 || n4 != 0)) {
                    if (n3 > 0) {
                        return context.formatMeasures(new Measure[]{new Measure((Number)n3, (MeasureUnit)MeasureUnit.MINUTE), new Measure((Number)n4, (MeasureUnit)MeasureUnit.SECOND)});
                    }
                    return context.format((Object)new Measure((Number)n4, (MeasureUnit)MeasureUnit.SECOND));
                }
                return context.format((Object)new Measure((Number)(n3 + (n4 + 30) / 60), (MeasureUnit)MeasureUnit.MINUTE));
            }
            return context.format((Object)new Measure((Number)(n2 + (n3 + 30) / 60), (MeasureUnit)MeasureUnit.HOUR));
        }
        return context.format((Object)new Measure((Number)(n + (n2 + 12) / 24), (MeasureUnit)MeasureUnit.DAY));
    }

    @UnsupportedAppUsage
    public static String formatShortElapsedTimeRoundingUpToMinutes(Context context, long l) {
        if ((l = (l + 60000L - 1L) / 60000L) != 0L && l != 1L) {
            return Formatter.formatShortElapsedTime(context, 60000L * l);
        }
        return MeasureFormat.getInstance((Locale)Formatter.localeFromContext(context), (MeasureFormat.FormatWidth)MeasureFormat.FormatWidth.SHORT).format((Object)new Measure((Number)l, (MeasureUnit)MeasureUnit.MINUTE));
    }

    public static String formatShortFileSize(Context context, long l) {
        if (context == null) {
            return "";
        }
        BytesResult bytesResult = Formatter.formatBytes(context.getResources(), l, 5);
        return Formatter.bidiWrap(context, context.getString(17040003, bytesResult.value, bytesResult.units));
    }

    private static Locale localeFromContext(Context context) {
        return context.getResources().getConfiguration().getLocales().get(0);
    }

    public static class BytesResult {
        public final long roundedBytes;
        public final String units;
        public final String value;

        public BytesResult(String string2, String string3, long l) {
            this.value = string2;
            this.units = string3;
            this.roundedBytes = l;
        }
    }

}

