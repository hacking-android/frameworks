/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.okhttp.internal.http;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class HttpDate {
    private static final DateFormat[] BROWSER_COMPATIBLE_DATE_FORMATS;
    private static final String[] BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS;
    private static final TimeZone GMT;
    private static final ThreadLocal<DateFormat> STANDARD_DATE_FORMAT;

    static {
        GMT = TimeZone.getTimeZone("GMT");
        STANDARD_DATE_FORMAT = new ThreadLocal<DateFormat>(){

            @Override
            protected DateFormat initialValue() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
                simpleDateFormat.setLenient(false);
                simpleDateFormat.setTimeZone(GMT);
                return simpleDateFormat;
            }
        };
        BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS = new String[]{"EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z", "EEE MMM d yyyy HH:mm:ss z"};
        BROWSER_COMPATIBLE_DATE_FORMATS = new DateFormat[BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS.length];
    }

    private HttpDate() {
    }

    @UnsupportedAppUsage
    public static String format(Date date) {
        return STANDARD_DATE_FORMAT.get().format(date);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static Date parse(String string) {
        if (string.length() == 0) {
            return null;
        }
        ParsePosition parsePosition = new ParsePosition(0);
        Cloneable cloneable = STANDARD_DATE_FORMAT.get().parse(string, parsePosition);
        if (parsePosition.getIndex() == string.length()) {
            return cloneable;
        }
        String[] arrstring = BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS;
        synchronized (arrstring) {
            int n = 0;
            int n2 = BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS.length;
            while (n < n2) {
                DateFormat dateFormat = BROWSER_COMPATIBLE_DATE_FORMATS[n];
                cloneable = dateFormat;
                if (dateFormat == null) {
                    cloneable = new SimpleDateFormat(BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS[n], Locale.US);
                    ((DateFormat)cloneable).setTimeZone(GMT);
                    HttpDate.BROWSER_COMPATIBLE_DATE_FORMATS[n] = cloneable;
                }
                parsePosition.setIndex(0);
                cloneable = ((DateFormat)cloneable).parse(string, parsePosition);
                if (parsePosition.getIndex() != 0) {
                    return cloneable;
                }
                ++n;
            }
            return null;
        }
    }

}

