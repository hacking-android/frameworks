/*
 * Decompiled with CFR 0.145.
 */
package libcore.net.http;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class HttpDate {
    private static final String[] BROWSER_COMPATIBLE_DATE_FORMATS;
    private static final ThreadLocal<DateFormat> STANDARD_DATE_FORMAT;

    static {
        STANDARD_DATE_FORMAT = new ThreadLocal<DateFormat>(){

            @Override
            protected DateFormat initialValue() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                return simpleDateFormat;
            }
        };
        BROWSER_COMPATIBLE_DATE_FORMATS = new String[]{"EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z", "EEE MMM d yyyy HH:mm:ss z"};
    }

    @UnsupportedAppUsage
    public static String format(Date date) {
        return STANDARD_DATE_FORMAT.get().format(date);
    }

    @UnsupportedAppUsage
    public static Date parse(String string) {
        try {
            Date date = STANDARD_DATE_FORMAT.get().parse(string);
            return date;
        }
        catch (ParseException parseException) {
            for (String string2 : BROWSER_COMPATIBLE_DATE_FORMATS) {
                try {
                    Cloneable cloneable = new SimpleDateFormat(string2, Locale.US);
                    cloneable = cloneable.parse(string);
                    return cloneable;
                }
                catch (ParseException parseException2) {
                }
            }
            return null;
        }
    }

}

