/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.http;

import android.annotation.UnsupportedAppUsage;
import android.text.format.Time;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HttpDateTime {
    private static final Pattern HTTP_DATE_ANSIC_PATTERN;
    private static final String HTTP_DATE_ANSIC_REGEXP = "[ ]([A-Za-z]{3,9})[ ]+([0-9]{1,2})[ ]([0-9]{1,2}:[0-9][0-9]:[0-9][0-9])[ ]([0-9]{2,4})";
    private static final Pattern HTTP_DATE_RFC_PATTERN;
    private static final String HTTP_DATE_RFC_REGEXP = "([0-9]{1,2})[- ]([A-Za-z]{3,9})[- ]([0-9]{2,4})[ ]([0-9]{1,2}:[0-9][0-9]:[0-9][0-9])";

    static {
        HTTP_DATE_RFC_PATTERN = Pattern.compile(HTTP_DATE_RFC_REGEXP);
        HTTP_DATE_ANSIC_PATTERN = Pattern.compile(HTTP_DATE_ANSIC_REGEXP);
    }

    private static int getDate(String string2) {
        if (string2.length() == 2) {
            return (string2.charAt(0) - 48) * 10 + (string2.charAt(1) - 48);
        }
        return string2.charAt(0) - 48;
    }

    private static int getMonth(String string2) {
        int n = Character.toLowerCase(string2.charAt(0)) + Character.toLowerCase(string2.charAt(1)) + Character.toLowerCase(string2.charAt(2)) - 291;
        if (n != 9) {
            if (n != 10) {
                if (n != 22) {
                    if (n != 26) {
                        if (n != 29) {
                            if (n != 32) {
                                if (n != 40) {
                                    if (n != 42) {
                                        if (n != 48) {
                                            switch (n) {
                                                default: {
                                                    throw new IllegalArgumentException();
                                                }
                                                case 37: {
                                                    return 8;
                                                }
                                                case 36: {
                                                    return 4;
                                                }
                                                case 35: 
                                            }
                                            return 9;
                                        }
                                        return 10;
                                    }
                                    return 5;
                                }
                                return 6;
                            }
                            return 3;
                        }
                        return 2;
                    }
                    return 7;
                }
                return 0;
            }
            return 1;
        }
        return 11;
    }

    private static TimeOfDay getTime(String string2) {
        int n;
        int n2 = 0 + 1;
        int n3 = n = string2.charAt(0) - 48;
        int n4 = n2;
        if (string2.charAt(n2) != ':') {
            n3 = n * 10 + (string2.charAt(n2) - 48);
            n4 = n2 + 1;
        }
        n = ++n4 + 1;
        n2 = string2.charAt(n4);
        n4 = string2.charAt(n);
        int n5 = n + 1 + 1;
        n = n5 + 1;
        return new TimeOfDay(n3, (n2 - 48) * 10 + (n4 - 48), (string2.charAt(n5) - 48) * 10 + (string2.charAt(n) - 48));
    }

    private static int getYear(String string2) {
        if (string2.length() == 2) {
            int n = (string2.charAt(0) - 48) * 10 + (string2.charAt(1) - 48);
            if (n >= 70) {
                return n + 1900;
            }
            return n + 2000;
        }
        if (string2.length() == 3) {
            return (string2.charAt(0) - 48) * 100 + (string2.charAt(1) - 48) * 10 + (string2.charAt(2) - 48) + 1900;
        }
        if (string2.length() == 4) {
            return (string2.charAt(0) - 48) * 1000 + (string2.charAt(1) - 48) * 100 + (string2.charAt(2) - 48) * 10 + (string2.charAt(3) - 48);
        }
        return 1970;
    }

    @UnsupportedAppUsage
    public static long parse(String object) throws IllegalArgumentException {
        block6 : {
            int n;
            Object object2;
            int n2;
            int n3;
            block5 : {
                block4 : {
                    object2 = HTTP_DATE_RFC_PATTERN.matcher((CharSequence)object);
                    if (!((Matcher)object2).find()) break block4;
                    n = HttpDateTime.getDate(((Matcher)object2).group(1));
                    n2 = HttpDateTime.getMonth(((Matcher)object2).group(2));
                    n3 = HttpDateTime.getYear(((Matcher)object2).group(3));
                    object = HttpDateTime.getTime(((Matcher)object2).group(4));
                    break block5;
                }
                object2 = HTTP_DATE_ANSIC_PATTERN.matcher((CharSequence)object);
                if (!((Matcher)object2).find()) break block6;
                n2 = HttpDateTime.getMonth(((Matcher)object2).group(1));
                n = HttpDateTime.getDate(((Matcher)object2).group(2));
                object = HttpDateTime.getTime(((Matcher)object2).group(3));
                n3 = HttpDateTime.getYear(((Matcher)object2).group(4));
            }
            int n4 = n;
            n = n2;
            n2 = n3;
            if (n3 >= 2038) {
                n2 = 2038;
                n = 0;
                n4 = 1;
            }
            object2 = new Time("UTC");
            ((Time)object2).set(((TimeOfDay)object).second, ((TimeOfDay)object).minute, ((TimeOfDay)object).hour, n4, n, n2);
            return ((Time)object2).toMillis(false);
        }
        throw new IllegalArgumentException();
    }

    private static class TimeOfDay {
        int hour;
        int minute;
        int second;

        TimeOfDay(int n, int n2, int n3) {
            this.hour = n;
            this.minute = n2;
            this.second = n3;
        }
    }

}

