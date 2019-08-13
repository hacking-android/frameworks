/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import java.util.Calendar;

public class MonthDisplayHelper {
    private Calendar mCalendar;
    private int mNumDaysInMonth;
    private int mNumDaysInPrevMonth;
    private int mOffset;
    private final int mWeekStartDay;

    public MonthDisplayHelper(int n, int n2) {
        this(n, n2, 1);
    }

    public MonthDisplayHelper(int n, int n2, int n3) {
        if (n3 >= 1 && n3 <= 7) {
            this.mWeekStartDay = n3;
            this.mCalendar = Calendar.getInstance();
            this.mCalendar.set(1, n);
            this.mCalendar.set(2, n2);
            this.mCalendar.set(5, 1);
            this.mCalendar.set(11, 0);
            this.mCalendar.set(12, 0);
            this.mCalendar.set(13, 0);
            this.mCalendar.getTimeInMillis();
            this.recalculate();
            return;
        }
        throw new IllegalArgumentException();
    }

    private void recalculate() {
        int n;
        this.mNumDaysInMonth = this.mCalendar.getActualMaximum(5);
        this.mCalendar.add(2, -1);
        this.mNumDaysInPrevMonth = this.mCalendar.getActualMaximum(5);
        this.mCalendar.add(2, 1);
        int n2 = n = this.getFirstDayOfMonth() - this.mWeekStartDay;
        if (n < 0) {
            n2 = n + 7;
        }
        this.mOffset = n2;
    }

    public int getColumnOf(int n) {
        return (this.mOffset + n - 1) % 7;
    }

    public int getDayAt(int n, int n2) {
        block1 : {
            int n3;
            if (n == 0 && n2 < (n3 = this.mOffset)) {
                return this.mNumDaysInPrevMonth + n2 - n3 + 1;
            }
            n = n * 7 + n2 - this.mOffset + 1;
            n2 = this.mNumDaysInMonth;
            if (n <= n2) break block1;
            n -= n2;
        }
        return n;
    }

    public int[] getDigitsForRow(int n) {
        if (n >= 0 && n <= 5) {
            int[] arrn = new int[7];
            for (int i = 0; i < 7; ++i) {
                arrn[i] = this.getDayAt(n, i);
            }
            return arrn;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("row ");
        stringBuilder.append(n);
        stringBuilder.append(" out of range (0-5)");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public int getFirstDayOfMonth() {
        return this.mCalendar.get(7);
    }

    public int getMonth() {
        return this.mCalendar.get(2);
    }

    public int getNumberOfDaysInMonth() {
        return this.mNumDaysInMonth;
    }

    public int getOffset() {
        return this.mOffset;
    }

    public int getRowOf(int n) {
        return (this.mOffset + n - 1) / 7;
    }

    public int getWeekStartDay() {
        return this.mWeekStartDay;
    }

    public int getYear() {
        return this.mCalendar.get(1);
    }

    public boolean isWithinCurrentMonth(int n, int n2) {
        if (n >= 0 && n2 >= 0 && n <= 5 && n2 <= 6) {
            if (n == 0 && n2 < this.mOffset) {
                return false;
            }
            return n * 7 + n2 - this.mOffset + 1 <= this.mNumDaysInMonth;
        }
        return false;
    }

    public void nextMonth() {
        this.mCalendar.add(2, 1);
        this.recalculate();
    }

    public void previousMonth() {
        this.mCalendar.add(2, -1);
        this.recalculate();
    }
}

