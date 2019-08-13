/*
 * Decompiled with CFR 0.145.
 */
package libcore.icu;

import android.icu.impl.JavaTimeZone;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import java.io.Serializable;

public final class DateUtilsBridge {
    public static final int FORMAT_12HOUR = 64;
    public static final int FORMAT_24HOUR = 128;
    public static final int FORMAT_ABBREV_ALL = 524288;
    public static final int FORMAT_ABBREV_MONTH = 65536;
    public static final int FORMAT_ABBREV_RELATIVE = 262144;
    public static final int FORMAT_ABBREV_TIME = 16384;
    public static final int FORMAT_ABBREV_WEEKDAY = 32768;
    public static final int FORMAT_NO_MONTH_DAY = 32;
    public static final int FORMAT_NO_YEAR = 8;
    public static final int FORMAT_NUMERIC_DATE = 131072;
    public static final int FORMAT_SHOW_DATE = 16;
    public static final int FORMAT_SHOW_TIME = 1;
    public static final int FORMAT_SHOW_WEEKDAY = 2;
    public static final int FORMAT_SHOW_YEAR = 4;
    public static final int FORMAT_UTC = 8192;

    public static Calendar createIcuCalendar(TimeZone cloneable, ULocale uLocale, long l) {
        cloneable = new GregorianCalendar((TimeZone)cloneable, uLocale);
        ((Calendar)cloneable).setTimeInMillis(l);
        return cloneable;
    }

    public static int dayDistance(Calendar calendar, Calendar calendar2) {
        return calendar2.get(20) - calendar.get(20);
    }

    private static boolean fallInSameMonth(Calendar calendar, Calendar calendar2) {
        boolean bl = calendar.get(2) == calendar2.get(2);
        return bl;
    }

    private static boolean fallInSameYear(Calendar calendar, Calendar calendar2) {
        boolean bl = true;
        if (calendar.get(1) != calendar2.get(1)) {
            bl = false;
        }
        return bl;
    }

    private static boolean fallOnDifferentDates(Calendar calendar, Calendar calendar2) {
        boolean bl;
        block0 : {
            bl = true;
            if (calendar.get(1) != calendar2.get(1) || calendar.get(2) != calendar2.get(2) || calendar.get(5) != calendar2.get(5)) break block0;
            bl = false;
        }
        return bl;
    }

    public static TimeZone icuTimeZone(java.util.TimeZone cloneable) {
        cloneable = new JavaTimeZone((java.util.TimeZone)cloneable, null);
        ((JavaTimeZone)cloneable).freeze();
        return cloneable;
    }

    public static boolean isDisplayMidnightUsingSkeleton(Calendar calendar) {
        boolean bl = calendar.get(11) == 0 && calendar.get(12) == 0;
        return bl;
    }

    private static boolean isThisYear(Calendar calendar) {
        Calendar calendar2 = (Calendar)calendar.clone();
        calendar2.setTimeInMillis(System.currentTimeMillis());
        boolean bl = true;
        if (calendar.get(1) != calendar2.get(1)) {
            bl = false;
        }
        return bl;
    }

    private static boolean onTheHour(Calendar calendar) {
        boolean bl = calendar.get(12) == 0 && calendar.get(13) == 0;
        return bl;
    }

    public static String toSkeleton(Calendar calendar, int n) {
        return DateUtilsBridge.toSkeleton(calendar, calendar, n);
    }

    public static String toSkeleton(Calendar serializable, Calendar calendar, int n) {
        CharSequence charSequence;
        String string;
        int n2;
        String string2;
        block26 : {
            block29 : {
                block28 : {
                    block27 : {
                        block25 : {
                            String string3;
                            block23 : {
                                block24 : {
                                    n2 = n;
                                    if ((524288 & n) != 0) {
                                        n2 = n | 114688;
                                    }
                                    string = "MMMM";
                                    if ((131072 & n2) != 0) {
                                        string = "M";
                                    } else if ((65536 & n2) != 0) {
                                        string = "MMM";
                                    }
                                    string2 = "EEEE";
                                    if ((32768 & n2) != 0) {
                                        string2 = "EEE";
                                    }
                                    string3 = "j";
                                    if ((n2 & 128) != 0) {
                                        string3 = "H";
                                    } else if ((n2 & 64) != 0) {
                                        string3 = "h";
                                    }
                                    if ((n2 & 16384) == 0 || (n2 & 128) != 0) break block23;
                                    if (!DateUtilsBridge.onTheHour((Calendar)serializable)) break block24;
                                    charSequence = string3;
                                    if (DateUtilsBridge.onTheHour(calendar)) break block25;
                                }
                                charSequence = new StringBuilder();
                                ((StringBuilder)charSequence).append(string3);
                                ((StringBuilder)charSequence).append("m");
                                charSequence = ((StringBuilder)charSequence).toString();
                                break block25;
                            }
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append(string3);
                            ((StringBuilder)charSequence).append("m");
                            charSequence = ((StringBuilder)charSequence).toString();
                        }
                        n = n2;
                        if (DateUtilsBridge.fallOnDifferentDates((Calendar)serializable, calendar)) {
                            n = n2 | 16;
                        }
                        n2 = n;
                        if (DateUtilsBridge.fallInSameMonth((Calendar)serializable, calendar)) {
                            n2 = n;
                            if ((n & 32) != 0) {
                                n2 = n & -3 & -2;
                            }
                        }
                        n = n2;
                        if ((n2 & 19) == 0) {
                            n = n2 | 16;
                        }
                        n2 = n;
                        if ((n & 16) == 0) break block26;
                        if ((n & 4) == 0) break block27;
                        n2 = n;
                        break block26;
                    }
                    if ((n & 8) == 0) break block28;
                    n2 = n;
                    break block26;
                }
                if (!DateUtilsBridge.fallInSameYear((Calendar)serializable, calendar)) break block29;
                n2 = n;
                if (DateUtilsBridge.isThisYear((Calendar)serializable)) break block26;
            }
            n2 = n | 4;
        }
        serializable = new StringBuilder();
        if ((n2 & 48) != 0) {
            if ((n2 & 4) != 0) {
                ((StringBuilder)serializable).append("y");
            }
            ((StringBuilder)serializable).append(string);
            if ((n2 & 32) == 0) {
                ((StringBuilder)serializable).append("d");
            }
        }
        if ((n2 & 2) != 0) {
            ((StringBuilder)serializable).append(string2);
        }
        if ((n2 & 1) != 0) {
            ((StringBuilder)serializable).append((String)charSequence);
        }
        return ((StringBuilder)serializable).toString();
    }
}

