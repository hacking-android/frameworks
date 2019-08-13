/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.Calendar;
import android.icu.util.DateRule;
import android.icu.util.GregorianCalendar;
import java.util.Date;

public class SimpleDateRule
implements DateRule {
    private Calendar calendar = new GregorianCalendar();
    private int dayOfMonth;
    private int dayOfWeek;
    private int month;

    public SimpleDateRule(int n, int n2) {
        this.month = n;
        this.dayOfMonth = n2;
        this.dayOfWeek = 0;
    }

    public SimpleDateRule(int n, int n2, int n3, boolean bl) {
        this.month = n;
        this.dayOfMonth = n2;
        n = bl ? n3 : -n3;
        this.dayOfWeek = n;
    }

    SimpleDateRule(int n, int n2, Calendar calendar) {
        this.month = n;
        this.dayOfMonth = n2;
        this.dayOfWeek = 0;
        this.calendar = calendar;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Date computeInYear(int n, Calendar calendar) {
        synchronized (calendar) {
            calendar.clear();
            calendar.set(0, calendar.getMaximum(0));
            calendar.set(1, n);
            calendar.set(2, this.month);
            calendar.set(5, this.dayOfMonth);
            if (this.dayOfWeek == 0) return calendar.getTime();
            calendar.setTime(calendar.getTime());
            n = calendar.get(7);
            n = this.dayOfWeek > 0 ? (this.dayOfWeek - n + 7) % 7 : -((this.dayOfWeek + n + 7) % 7);
            calendar.add(5, n);
            return calendar.getTime();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Date doFirstBetween(Date date, Date date2) {
        Calendar calendar = this.calendar;
        synchronized (calendar) {
            Date date3;
            calendar.setTime(date);
            int n = calendar.get(1);
            int n2 = calendar.get(2);
            int n3 = n;
            if (n2 > this.month) {
                n3 = n + 1;
            }
            Date date4 = date3 = this.computeInYear(n3, calendar);
            if (n2 == this.month) {
                date4 = date3;
                if (date3.before(date)) {
                    date4 = this.computeInYear(n3 + 1, calendar);
                }
            }
            if (date2 != null && date4.after(date2)) {
                return null;
            }
            return date4;
        }
    }

    @Override
    public Date firstAfter(Date date) {
        return this.doFirstBetween(date, null);
    }

    @Override
    public Date firstBetween(Date date, Date date2) {
        return this.doFirstBetween(date, date2);
    }

    @Override
    public boolean isBetween(Date date, Date date2) {
        boolean bl = this.firstBetween(date, date2) != null;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isOn(Date date) {
        Calendar calendar = this.calendar;
        synchronized (calendar) {
            calendar.setTime(date);
            int n = calendar.get(6);
            boolean bl = true;
            calendar.setTime(this.computeInYear(calendar.get(1), calendar));
            if (calendar.get(6) != n) return false;
            return bl;
        }
    }
}

