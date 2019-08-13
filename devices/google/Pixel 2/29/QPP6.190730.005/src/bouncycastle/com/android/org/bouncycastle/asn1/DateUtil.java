/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

class DateUtil {
    static Locale EN_Locale;
    private static Long ZERO;
    private static final Map localeCache;

    static {
        ZERO = DateUtil.longValueOf(0L);
        localeCache = new HashMap();
        EN_Locale = DateUtil.forEN();
    }

    DateUtil() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Date epochAdjust(Date date) throws ParseException {
        Locale locale = Locale.getDefault();
        if (locale == null) {
            return date;
        }
        Map map = localeCache;
        synchronized (map) {
            Comparable<Long> comparable = (Long)localeCache.get(locale);
            Serializable serializable = comparable;
            if (comparable == null) {
                serializable = new SimpleDateFormat("yyyyMMddHHmmssz");
                long l = ((DateFormat)serializable).parse("19700101000000GMT+00:00").getTime();
                serializable = l == 0L ? ZERO : DateUtil.longValueOf(l);
                localeCache.put(locale, serializable);
            }
            if (serializable == ZERO) return date;
            return new Date(date.getTime() - (Long)serializable);
        }
    }

    private static Locale forEN() {
        if ("en".equalsIgnoreCase(Locale.getDefault().getLanguage())) {
            return Locale.getDefault();
        }
        Locale[] arrlocale = Locale.getAvailableLocales();
        for (int i = 0; i != arrlocale.length; ++i) {
            if (!"en".equalsIgnoreCase(arrlocale[i].getLanguage())) continue;
            return arrlocale[i];
        }
        return Locale.getDefault();
    }

    private static Long longValueOf(long l) {
        return l;
    }
}

