/*
 * Decompiled with CFR 0.145.
 */
package java.text;

import java.util.Calendar;

class CalendarBuilder {
    private static final int COMPUTED = 1;
    public static final int ISO_DAY_OF_WEEK = 1000;
    private static final int MAX_FIELD = 18;
    private static final int MINIMUM_USER_STAMP = 2;
    private static final int UNSET = 0;
    public static final int WEEK_YEAR = 17;
    private final int[] field = new int[36];
    private int maxFieldIndex = -1;
    private int nextStamp = 2;

    CalendarBuilder() {
    }

    static boolean isValidDayOfWeek(int n) {
        boolean bl = n > 0 && n <= 7;
        return bl;
    }

    static int toCalendarDayOfWeek(int n) {
        if (!CalendarBuilder.isValidDayOfWeek(n)) {
            return n;
        }
        n = n == 7 ? 1 : ++n;
        return n;
    }

    static int toISODayOfWeek(int n) {
        n = n == 1 ? 7 : --n;
        return n;
    }

    CalendarBuilder addYear(int n) {
        int[] arrn = this.field;
        arrn[19] = arrn[19] + n;
        arrn[35] = arrn[35] + n;
        return this;
    }

    CalendarBuilder clear(int n) {
        int n2 = n;
        if (n == 1000) {
            n2 = 7;
        }
        int[] arrn = this.field;
        arrn[n2] = 0;
        arrn[n2 + 18] = 0;
        return this;
    }

    Calendar establish(Calendar calendar) {
        int n;
        int[] arrn;
        int n2 = this.isSet(17) && (arrn = this.field)[17] > arrn[1] ? 1 : 0;
        int n3 = n2;
        if (n2 != 0) {
            n3 = n2;
            if (!calendar.isWeekDateSupported()) {
                if (!this.isSet(1)) {
                    this.set(1, this.field[35]);
                }
                n3 = 0;
            }
        }
        calendar.clear();
        block0 : for (n2 = 2; n2 < this.nextStamp; ++n2) {
            for (n = 0; n <= this.maxFieldIndex; ++n) {
                arrn = this.field;
                if (arrn[n] != n2) continue;
                calendar.set(n, arrn[n + 18]);
                continue block0;
            }
        }
        if (n3 != 0) {
            n2 = this.isSet(3) ? this.field[21] : 1;
            n3 = this.isSet(7) ? this.field[25] : calendar.getFirstDayOfWeek();
            int n4 = n2;
            n = n3;
            if (!CalendarBuilder.isValidDayOfWeek(n3)) {
                n4 = n2;
                n = n3;
                if (calendar.isLenient()) {
                    if (n3 >= 8) {
                        n2 += --n3 / 7;
                        n4 = n3 % 7 + 1;
                    } else {
                        n = n2;
                        do {
                            n2 = n--;
                            n4 = n3;
                            if (n3 > 0) break;
                            n3 += 7;
                        } while (true);
                    }
                    n = CalendarBuilder.toCalendarDayOfWeek(n4);
                    n4 = n2;
                }
            }
            calendar.setWeekDate(this.field[35], n4, n);
        }
        return calendar;
    }

    boolean isSet(int n) {
        int n2 = n;
        if (n == 1000) {
            n2 = 7;
        }
        boolean bl = this.field[n2] > 0;
        return bl;
    }

    CalendarBuilder set(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        if (n == 1000) {
            n3 = 7;
            n4 = CalendarBuilder.toCalendarDayOfWeek(n2);
        }
        int[] arrn = this.field;
        n = this.nextStamp;
        this.nextStamp = n + 1;
        arrn[n3] = n;
        arrn[n3 + 18] = n4;
        if (n3 > this.maxFieldIndex && n3 < 17) {
            this.maxFieldIndex = n3;
        }
        return this;
    }

    public String toString() {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CalendarBuilder:[");
        for (n = 0; n < this.field.length; ++n) {
            if (!this.isSet(n)) continue;
            stringBuilder.append(n);
            stringBuilder.append('=');
            stringBuilder.append(this.field[n + 18]);
            stringBuilder.append(',');
        }
        n = stringBuilder.length() - 1;
        if (stringBuilder.charAt(n) == ',') {
            stringBuilder.setLength(n);
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

