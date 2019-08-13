/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.DateRule;
import android.icu.util.GregorianCalendar;
import java.util.Date;

class EasterRule
implements DateRule {
    private GregorianCalendar calendar = new GregorianCalendar();
    private int daysAfterEaster;

    public EasterRule(int n, boolean bl) {
        this.daysAfterEaster = n;
        if (bl) {
            this.calendar.setGregorianChange(new Date(Long.MAX_VALUE));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Date computeInYear(Date date, GregorianCalendar gregorianCalendar) {
        GregorianCalendar gregorianCalendar2 = gregorianCalendar;
        if (gregorianCalendar == null) {
            gregorianCalendar2 = this.calendar;
        }
        synchronized (gregorianCalendar2) {
            int n;
            int n2;
            gregorianCalendar2.setTime(date);
            int n3 = gregorianCalendar2.get(1);
            int n4 = n3 % 19;
            if (gregorianCalendar2.getTime().after(gregorianCalendar2.getGregorianChange())) {
                n2 = n3 / 100;
                n = (n2 - n2 / 4 - (n2 * 8 + 13) / 25 + n4 * 19 + 15) % 30;
                n -= n / 28 * (1 - n / 28 * (29 / (n + 1)) * ((21 - n4) / 11));
                n2 = (n3 / 4 + n3 + n + 2 - n2 + n2 / 4) % 7;
            } else {
                n = (n4 * 19 + 15) % 30;
                n2 = (n3 / 4 + n3 + n) % 7;
            }
            n4 = n - n2;
            n2 = (n4 + 40) / 44 + 3;
            n = n2 / 4;
            gregorianCalendar2.clear();
            gregorianCalendar2.set(0, 1);
            gregorianCalendar2.set(1, n3);
            gregorianCalendar2.set(2, n2 - 1);
            gregorianCalendar2.set(5, n4 + 28 - n * 31);
            gregorianCalendar2.getTime();
            gregorianCalendar2.add(5, this.daysAfterEaster);
            return gregorianCalendar2.getTime();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Date doFirstBetween(Date date, Date date2) {
        GregorianCalendar gregorianCalendar = this.calendar;
        synchronized (gregorianCalendar) {
            Date date3;
            Date date4 = date3 = this.computeInYear(date, this.calendar);
            if (date3.before(date)) {
                this.calendar.setTime(date);
                this.calendar.get(1);
                this.calendar.add(1, 1);
                date4 = this.computeInYear(this.calendar.getTime(), this.calendar);
            }
            if (date2 != null && !date4.before(date2)) {
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
        GregorianCalendar gregorianCalendar = this.calendar;
        synchronized (gregorianCalendar) {
            this.calendar.setTime(date);
            int n = this.calendar.get(6);
            this.calendar.setTime(this.computeInYear(this.calendar.getTime(), this.calendar));
            if (this.calendar.get(6) != n) return false;
            return true;
        }
    }
}

