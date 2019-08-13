/*
 * Decompiled with CFR 0.145.
 */
package libcore.icu;

import android.icu.text.DateFormat;
import android.icu.text.DateTimePatternGenerator;
import android.icu.text.DisplayContext;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import java.io.Serializable;
import java.text.Format;
import libcore.icu.DateUtilsBridge;
import libcore.util.BasicLruCache;

public class DateTimeFormat {
    private static final FormatterCache CACHED_FORMATTERS = new FormatterCache();

    private DateTimeFormat() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String format(ULocale object, Calendar calendar, int n, DisplayContext displayContext) {
        String string = DateUtilsBridge.toSkeleton(calendar, n);
        Serializable serializable = new StringBuilder();
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append("\t");
        ((StringBuilder)serializable).append(object);
        ((StringBuilder)serializable).append("\t");
        ((StringBuilder)serializable).append(calendar.getTimeZone());
        String string2 = ((StringBuilder)serializable).toString();
        FormatterCache formatterCache = CACHED_FORMATTERS;
        synchronized (formatterCache) {
            Cloneable cloneable = (DateFormat)CACHED_FORMATTERS.get(string2);
            serializable = cloneable;
            if (cloneable == null) {
                cloneable = DateTimePatternGenerator.getInstance((ULocale)object);
                serializable = new SimpleDateFormat(((DateTimePatternGenerator)cloneable).getBestPattern(string), (ULocale)object);
                CACHED_FORMATTERS.put(string2, serializable);
            }
            ((DateFormat)serializable).setContext(displayContext);
            return ((Format)serializable).format(calendar);
        }
    }

    static class FormatterCache
    extends BasicLruCache<String, DateFormat> {
        FormatterCache() {
            super(8);
        }
    }

}

