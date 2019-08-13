/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.Grego;
import android.icu.util.DateTimeRule;
import android.icu.util.TimeZoneRule;
import java.util.Date;

public class AnnualTimeZoneRule
extends TimeZoneRule {
    public static final int MAX_YEAR = Integer.MAX_VALUE;
    private static final long serialVersionUID = -8870666707791230688L;
    private final DateTimeRule dateTimeRule;
    private final int endYear;
    private final int startYear;

    public AnnualTimeZoneRule(String string, int n, int n2, DateTimeRule dateTimeRule, int n3, int n4) {
        super(string, n, n2);
        this.dateTimeRule = dateTimeRule;
        this.startYear = n3;
        this.endYear = n4;
    }

    public int getEndYear() {
        return this.endYear;
    }

    @Override
    public Date getFinalStart(int n, int n2) {
        int n3 = this.endYear;
        if (n3 == Integer.MAX_VALUE) {
            return null;
        }
        return this.getStartInYear(n3, n, n2);
    }

    @Override
    public Date getFirstStart(int n, int n2) {
        return this.getStartInYear(this.startYear, n, n2);
    }

    @Override
    public Date getNextStart(long l, int n, int n2, boolean bl) {
        Date date;
        block4 : {
            int n3;
            block5 : {
                Date date2;
                n3 = Grego.timeToFields(l, null)[0];
                if (n3 < this.startYear) {
                    return this.getFirstStart(n, n2);
                }
                date = date2 = this.getStartInYear(n3, n, n2);
                if (date2 == null) break block4;
                if (date2.getTime() < l) break block5;
                date = date2;
                if (bl) break block4;
                date = date2;
                if (date2.getTime() != l) break block4;
            }
            date = this.getStartInYear(n3 + 1, n, n2);
        }
        return date;
    }

    @Override
    public Date getPreviousStart(long l, int n, int n2, boolean bl) {
        Date date;
        block4 : {
            int n3;
            block5 : {
                Date date2;
                n3 = Grego.timeToFields(l, null)[0];
                if (n3 > this.endYear) {
                    return this.getFinalStart(n, n2);
                }
                date = date2 = this.getStartInYear(n3, n, n2);
                if (date2 == null) break block4;
                if (date2.getTime() > l) break block5;
                date = date2;
                if (bl) break block4;
                date = date2;
                if (date2.getTime() != l) break block4;
            }
            date = this.getStartInYear(n3 - 1, n, n2);
        }
        return date;
    }

    public DateTimeRule getRule() {
        return this.dateTimeRule;
    }

    public Date getStartInYear(int n, int n2, int n3) {
        if (n >= this.startYear && n <= this.endYear) {
            long l;
            long l2;
            int n4 = this.dateTimeRule.getDateRuleType();
            if (n4 == 0) {
                l = Grego.fieldsToDay(n, this.dateTimeRule.getRuleMonth(), this.dateTimeRule.getRuleDayOfMonth());
            } else {
                int n5 = 1;
                int n6 = 1;
                if (n4 == 1) {
                    int n7 = this.dateTimeRule.getRuleWeekInMonth();
                    if (n7 > 0) {
                        l = Grego.fieldsToDay(n, this.dateTimeRule.getRuleMonth(), 1) + (long)((n7 - 1) * 7);
                        n = n6;
                    } else {
                        n5 = 0;
                        l = Grego.fieldsToDay(n, this.dateTimeRule.getRuleMonth(), Grego.monthLength(n, this.dateTimeRule.getRuleMonth())) + (long)((n7 + 1) * 7);
                        n = n5;
                    }
                } else {
                    int n8;
                    int n9 = this.dateTimeRule.getRuleMonth();
                    n6 = n8 = this.dateTimeRule.getRuleDayOfMonth();
                    if (n4 == 3) {
                        n5 = n4 = 0;
                        n6 = n8;
                        if (n9 == 1) {
                            n5 = n4;
                            n6 = n8;
                            if (n8 == 29) {
                                n5 = n4;
                                n6 = n8;
                                if (!Grego.isLeapYear(n)) {
                                    n6 = n8 - 1;
                                    n5 = n4;
                                }
                            }
                        }
                    }
                    l = Grego.fieldsToDay(n, n9, n6);
                    n = n5;
                }
                n5 = Grego.dayOfWeek(l);
                n5 = this.dateTimeRule.getRuleDayOfWeek() - n5;
                n = n != 0 ? (n5 < 0 ? n5 + 7 : n5) : (n5 > 0 ? n5 - 7 : n5);
                l += (long)n;
            }
            l = l2 = 86400000L * l + (long)this.dateTimeRule.getRuleMillisInDay();
            if (this.dateTimeRule.getTimeRuleType() != 2) {
                l = l2 - (long)n2;
            }
            l2 = l;
            if (this.dateTimeRule.getTimeRuleType() == 0) {
                l2 = l - (long)n3;
            }
            return new Date(l2);
        }
        return null;
    }

    public int getStartYear() {
        return this.startYear;
    }

    @Override
    public boolean isEquivalentTo(TimeZoneRule timeZoneRule) {
        if (!(timeZoneRule instanceof AnnualTimeZoneRule)) {
            return false;
        }
        AnnualTimeZoneRule annualTimeZoneRule = (AnnualTimeZoneRule)timeZoneRule;
        if (this.startYear == annualTimeZoneRule.startYear && this.endYear == annualTimeZoneRule.endYear && this.dateTimeRule.equals(annualTimeZoneRule.dateTimeRule)) {
            return super.isEquivalentTo(timeZoneRule);
        }
        return false;
    }

    @Override
    public boolean isTransitionRule() {
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", rule={");
        stringBuilder2.append(this.dateTimeRule);
        stringBuilder2.append("}");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", startYear=");
        stringBuilder2.append(this.startYear);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append(", endYear=");
        int n = this.endYear;
        if (n == Integer.MAX_VALUE) {
            stringBuilder.append("max");
        } else {
            stringBuilder.append(n);
        }
        return stringBuilder.toString();
    }
}

