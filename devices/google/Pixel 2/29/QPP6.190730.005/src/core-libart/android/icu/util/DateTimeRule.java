/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import java.io.Serializable;

public class DateTimeRule
implements Serializable {
    public static final int DOM = 0;
    public static final int DOW = 1;
    private static final String[] DOWSTR = new String[]{"", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    public static final int DOW_GEQ_DOM = 2;
    public static final int DOW_LEQ_DOM = 3;
    private static final String[] MONSTR = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static final int STANDARD_TIME = 1;
    public static final int UTC_TIME = 2;
    public static final int WALL_TIME = 0;
    private static final long serialVersionUID = 2183055795738051443L;
    private final int dateRuleType;
    private final int dayOfMonth;
    private final int dayOfWeek;
    private final int millisInDay;
    private final int month;
    private final int timeRuleType;
    private final int weekInMonth;

    public DateTimeRule(int n, int n2, int n3, int n4) {
        this.dateRuleType = 0;
        this.month = n;
        this.dayOfMonth = n2;
        this.millisInDay = n3;
        this.timeRuleType = n4;
        this.dayOfWeek = 0;
        this.weekInMonth = 0;
    }

    public DateTimeRule(int n, int n2, int n3, int n4, int n5) {
        this.dateRuleType = 1;
        this.month = n;
        this.weekInMonth = n2;
        this.dayOfWeek = n3;
        this.millisInDay = n4;
        this.timeRuleType = n5;
        this.dayOfMonth = 0;
    }

    public DateTimeRule(int n, int n2, int n3, boolean bl, int n4, int n5) {
        int n6 = bl ? 2 : 3;
        this.dateRuleType = n6;
        this.month = n;
        this.dayOfMonth = n2;
        this.dayOfWeek = n3;
        this.millisInDay = n4;
        this.timeRuleType = n5;
        this.weekInMonth = 0;
    }

    public int getDateRuleType() {
        return this.dateRuleType;
    }

    public int getRuleDayOfMonth() {
        return this.dayOfMonth;
    }

    public int getRuleDayOfWeek() {
        return this.dayOfWeek;
    }

    public int getRuleMillisInDay() {
        return this.millisInDay;
    }

    public int getRuleMonth() {
        return this.month;
    }

    public int getRuleWeekInMonth() {
        return this.weekInMonth;
    }

    public int getTimeRuleType() {
        return this.timeRuleType;
    }

    public String toString() {
        CharSequence charSequence = null;
        String string = null;
        int n = this.dateRuleType;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append(DOWSTR[this.dayOfWeek]);
                        ((StringBuilder)charSequence).append("<=");
                        ((StringBuilder)charSequence).append(Integer.toString(this.dayOfMonth));
                        charSequence = ((StringBuilder)charSequence).toString();
                    }
                } else {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(DOWSTR[this.dayOfWeek]);
                    ((StringBuilder)charSequence).append(">=");
                    ((StringBuilder)charSequence).append(Integer.toString(this.dayOfMonth));
                    charSequence = ((StringBuilder)charSequence).toString();
                }
            } else {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(Integer.toString(this.weekInMonth));
                ((StringBuilder)charSequence).append(DOWSTR[this.dayOfWeek]);
                charSequence = ((StringBuilder)charSequence).toString();
            }
        } else {
            charSequence = Integer.toString(this.dayOfMonth);
        }
        n = this.timeRuleType;
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    string = "UTC";
                }
            } else {
                string = "STD";
            }
        } else {
            string = "WALL";
        }
        int n2 = this.millisInDay;
        n = n2 % 1000;
        int n3 = n2 / 1000;
        n2 = n3 % 60;
        int n4 = n3 / 60;
        n3 = n4 % 60;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("month=");
        stringBuilder.append(MONSTR[this.month]);
        stringBuilder.append(", date=");
        stringBuilder.append((String)charSequence);
        stringBuilder.append(", time=");
        stringBuilder.append(n4 /= 60);
        stringBuilder.append(":");
        stringBuilder.append(n3 / 10);
        stringBuilder.append(n3 % 10);
        stringBuilder.append(":");
        stringBuilder.append(n2 / 10);
        stringBuilder.append(n2 % 10);
        stringBuilder.append(".");
        stringBuilder.append(n / 100);
        stringBuilder.append(n / 10 % 10);
        stringBuilder.append(n % 10);
        stringBuilder.append("(");
        stringBuilder.append(string);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

