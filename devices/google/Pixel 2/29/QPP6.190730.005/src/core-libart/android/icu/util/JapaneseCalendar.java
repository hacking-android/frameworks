/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.CalType;
import android.icu.impl.EraRules;
import android.icu.util.GregorianCalendar;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import java.util.Date;
import java.util.Locale;

public class JapaneseCalendar
extends GregorianCalendar {
    @Deprecated
    public static final int CURRENT_ERA;
    private static final EraRules ERA_RULES;
    private static final int GREGORIAN_EPOCH = 1970;
    public static final int HEISEI;
    public static final int MEIJI;
    public static final int REIWA;
    public static final int SHOWA;
    public static final int TAISHO;
    private static final long serialVersionUID = -2977189902603704691L;

    static {
        String string;
        String string2 = string = System.getProperty("ICU_ENABLE_TENTATIVE_ERA");
        if (string == null) {
            string2 = System.getenv("ICU_ENABLE_TENTATIVE_ERA");
        }
        boolean bl = string2 != null ? string2.equalsIgnoreCase("true") : System.getProperty("jdk.calendar.japanese.supplemental.era") != null;
        ERA_RULES = EraRules.getInstance(CalType.JAPANESE, bl);
        MEIJI = 232;
        TAISHO = 233;
        SHOWA = 234;
        HEISEI = 235;
        CURRENT_ERA = REIWA = 236;
    }

    public JapaneseCalendar() {
    }

    public JapaneseCalendar(int n, int n2, int n3) {
        super(n, n2, n3);
        this.set(0, CURRENT_ERA);
    }

    public JapaneseCalendar(int n, int n2, int n3, int n4) {
        super(n2, n3, n4);
        this.set(0, n);
    }

    public JapaneseCalendar(int n, int n2, int n3, int n4, int n5, int n6) {
        super(n, n2, n3, n4, n5, n6);
        this.set(0, CURRENT_ERA);
    }

    public JapaneseCalendar(TimeZone timeZone) {
        super(timeZone);
    }

    public JapaneseCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
    }

    public JapaneseCalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
    }

    public JapaneseCalendar(ULocale uLocale) {
        super(uLocale);
    }

    public JapaneseCalendar(Date date) {
        this();
        this.setTime(date);
    }

    public JapaneseCalendar(Locale locale) {
        super(locale);
    }

    @Override
    public int getActualMaximum(int n) {
        if (n == 1) {
            n = this.get(0);
            if (n == CURRENT_ERA) {
                return this.handleGetLimit(1, 3);
            }
            int[] arrn = ERA_RULES.getStartDate(n + 1, null);
            int n2 = arrn[0];
            int n3 = arrn[1];
            int n4 = arrn[2];
            n = n2 = n2 - ERA_RULES.getStartYear(n) + 1;
            if (n3 == 1) {
                n = n2;
                if (n4 == 1) {
                    n = n2 - 1;
                }
            }
            return n;
        }
        return super.getActualMaximum(n);
    }

    @Override
    protected int getDefaultDayInMonth(int n, int n2) {
        int n3 = this.internalGet(0, CURRENT_ERA);
        int[] arrn = ERA_RULES.getStartDate(n3, null);
        if (n == arrn[0] && n2 == arrn[1] - 1) {
            return arrn[2];
        }
        return super.getDefaultDayInMonth(n, n2);
    }

    @Override
    protected int getDefaultMonthInYear(int n) {
        int n2 = this.internalGet(0, CURRENT_ERA);
        int[] arrn = ERA_RULES.getStartDate(n2, null);
        if (n == arrn[0]) {
            return arrn[1] - 1;
        }
        return super.getDefaultMonthInYear(n);
    }

    @Override
    public String getType() {
        return "japanese";
    }

    @Override
    protected void handleComputeFields(int n) {
        super.handleComputeFields(n);
        n = this.internalGet(19);
        int n2 = ERA_RULES.getEraIndex(n, this.internalGet(2) + 1, this.internalGet(5));
        this.internalSet(0, n2);
        this.internalSet(1, n - ERA_RULES.getStartYear(n2) + 1);
    }

    @Override
    protected int handleGetExtendedYear() {
        int n = this.newerField(19, 1) == 19 && this.newerField(19, 0) == 19 ? this.internalGet(19, 1970) : this.internalGet(1, 1) + ERA_RULES.getStartYear(this.internalGet(0, CURRENT_ERA)) - 1;
        return n;
    }

    @Override
    protected int handleGetLimit(int n, int n2) {
        block4 : {
            block6 : {
                block7 : {
                    block8 : {
                        block5 : {
                            if (n == 0) break block4;
                            if (n != 1) break block5;
                            if (n2 == 0 || n2 == 1) break block6;
                            if (n2 == 2) break block7;
                            if (n2 == 3) break block8;
                        }
                        return super.handleGetLimit(n, n2);
                    }
                    return super.handleGetLimit(n, 3) - ERA_RULES.getStartYear(CURRENT_ERA);
                }
                return 1;
            }
            return 1;
        }
        if (n2 != 0 && n2 != 1) {
            return CURRENT_ERA;
        }
        return 0;
    }

    @Deprecated
    @Override
    public boolean haveDefaultCentury() {
        return false;
    }
}

