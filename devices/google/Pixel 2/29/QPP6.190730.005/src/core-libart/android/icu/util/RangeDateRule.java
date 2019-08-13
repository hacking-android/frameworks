/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.DateRule;
import android.icu.util.Range;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RangeDateRule
implements DateRule {
    List<Range> ranges = new ArrayList<Range>(2);

    private Range rangeAt(int n) {
        Range range = n < this.ranges.size() ? this.ranges.get(n) : null;
        return range;
    }

    private int startIndex(Date date) {
        int n = this.ranges.size();
        int n2 = 0;
        while (n2 < this.ranges.size() && !date.before(this.ranges.get((int)n2).start)) {
            n = n2++;
        }
        return n;
    }

    public void add(DateRule dateRule) {
        this.add(new Date(Long.MIN_VALUE), dateRule);
    }

    public void add(Date date, DateRule dateRule) {
        this.ranges.add(new Range(date, dateRule));
    }

    @Override
    public Date firstAfter(Date date) {
        int n;
        int n2 = n = this.startIndex(date);
        if (n == this.ranges.size()) {
            n2 = 0;
        }
        Date date2 = null;
        Range range = this.rangeAt(n2);
        Range range2 = this.rangeAt(n2 + 1);
        Date date3 = date2;
        if (range != null) {
            date3 = date2;
            if (range.rule != null) {
                date3 = range2 != null ? range.rule.firstBetween(date, range2.start) : range.rule.firstAfter(date);
            }
        }
        return date3;
    }

    @Override
    public Date firstBetween(Date date, Date date2) {
        if (date2 == null) {
            return this.firstAfter(date);
        }
        int n = this.startIndex(date);
        Date date3 = null;
        Object object = this.rangeAt(n);
        do {
            Range range = object;
            if (date3 != null || range == null || range.start.after(date2)) break;
            Range range2 = this.rangeAt(n + 1);
            if (range.rule != null) {
                object = range2 != null && !range2.start.after(date2) ? range2.start : date2;
                date3 = range.rule.firstBetween(date, (Date)object);
            }
            object = range2;
        } while (true);
        return date3;
    }

    @Override
    public boolean isBetween(Date date, Date date2) {
        boolean bl = this.firstBetween(date, date2) == null;
        return bl;
    }

    @Override
    public boolean isOn(Date date) {
        Range range = this.rangeAt(this.startIndex(date));
        boolean bl = range != null && range.rule != null && range.rule.isOn(date);
        return bl;
    }
}

