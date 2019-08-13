/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.UResource;
import android.icu.util.ICUException;
import android.icu.util.ULocale;
import java.util.HashMap;
import java.util.Map;

public final class DayPeriodRules {
    private static final DayPeriodRulesData DATA = DayPeriodRules.loadData();
    private DayPeriod[] dayPeriodForHour = new DayPeriod[24];
    private boolean hasMidnight = false;
    private boolean hasNoon = false;

    private DayPeriodRules() {
    }

    private void add(int n, int n2, DayPeriod dayPeriod) {
        while (n != n2) {
            int n3 = n;
            if (n == 24) {
                n3 = 0;
            }
            this.dayPeriodForHour[n3] = dayPeriod;
            n = n3 + 1;
        }
    }

    private int getEndHourForDayPeriod(DayPeriod dayPeriod) {
        if (dayPeriod == DayPeriod.MIDNIGHT) {
            return 0;
        }
        if (dayPeriod == DayPeriod.NOON) {
            return 12;
        }
        DayPeriod[] arrdayPeriod = this.dayPeriodForHour;
        if (arrdayPeriod[0] == dayPeriod && arrdayPeriod[23] == dayPeriod) {
            for (int i = 1; i <= 22; ++i) {
                if (this.dayPeriodForHour[i] == dayPeriod) continue;
                return i;
            }
        } else {
            for (int i = 23; i >= 0; --i) {
                if (this.dayPeriodForHour[i] != dayPeriod) continue;
                return i + 1;
            }
        }
        throw new IllegalArgumentException();
    }

    public static DayPeriodRules getInstance(ULocale object) {
        Object object2;
        block3 : {
            Integer n;
            Object object3 = object = ((ULocale)object).getBaseName();
            if (((String)object).isEmpty()) {
                object3 = "root";
            }
            object = null;
            do {
                object2 = object;
                if (object != null) break block3;
                n = DayPeriodRules.DATA.localesToRuleSetNumMap.get(object3);
                object2 = n;
                if (n != null) break block3;
                object3 = object2 = ULocale.getFallback((String)object3);
                object = n;
            } while (!((String)object2).isEmpty());
            object2 = n;
        }
        if (object2 != null && DayPeriodRules.DATA.rules[(Integer)object2] != null) {
            return DayPeriodRules.DATA.rules[(Integer)object2];
        }
        return null;
    }

    private int getStartHourForDayPeriod(DayPeriod dayPeriod) throws IllegalArgumentException {
        if (dayPeriod == DayPeriod.MIDNIGHT) {
            return 0;
        }
        if (dayPeriod == DayPeriod.NOON) {
            return 12;
        }
        DayPeriod[] arrdayPeriod = this.dayPeriodForHour;
        if (arrdayPeriod[0] == dayPeriod && arrdayPeriod[23] == dayPeriod) {
            for (int i = 22; i >= 1; --i) {
                if (this.dayPeriodForHour[i] == dayPeriod) continue;
                return i + 1;
            }
        } else {
            for (int i = 0; i <= 23; ++i) {
                if (this.dayPeriodForHour[i] != dayPeriod) continue;
                return i;
            }
        }
        throw new IllegalArgumentException();
    }

    private static DayPeriodRulesData loadData() {
        DayPeriodRulesData dayPeriodRulesData = new DayPeriodRulesData();
        ICUResourceBundle iCUResourceBundle = ICUResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "dayPeriods", ICUResourceBundle.ICU_DATA_CLASS_LOADER, true);
        iCUResourceBundle.getAllItemsWithFallback("rules", new DayPeriodRulesCountSink(dayPeriodRulesData));
        dayPeriodRulesData.rules = new DayPeriodRules[dayPeriodRulesData.maxRuleSetNum + 1];
        iCUResourceBundle.getAllItemsWithFallback("", new DayPeriodRulesDataSink(dayPeriodRulesData));
        return dayPeriodRulesData;
    }

    private static int parseSetNum(String string) {
        if (string.startsWith("set")) {
            return Integer.parseInt(string.substring(3));
        }
        throw new ICUException("Set number should start with \"set\".");
    }

    public DayPeriod getDayPeriodForHour(int n) {
        return this.dayPeriodForHour[n];
    }

    public double getMidPointForDayPeriod(DayPeriod dayPeriod) {
        double d;
        int n = this.getStartHourForDayPeriod(dayPeriod);
        int n2 = this.getEndHourForDayPeriod(dayPeriod);
        double d2 = d = (double)(n + n2) / 2.0;
        if (n > n2) {
            d2 = d += 12.0;
            if (d >= 24.0) {
                d2 = d - 24.0;
            }
        }
        return d2;
    }

    public boolean hasMidnight() {
        return this.hasMidnight;
    }

    public boolean hasNoon() {
        return this.hasNoon;
    }

    private static enum CutoffType {
        BEFORE,
        AFTER,
        FROM,
        AT;
        

        private static CutoffType fromStringOrNull(CharSequence charSequence) {
            if ("from".contentEquals(charSequence)) {
                return FROM;
            }
            if ("before".contentEquals(charSequence)) {
                return BEFORE;
            }
            if ("after".contentEquals(charSequence)) {
                return AFTER;
            }
            if ("at".contentEquals(charSequence)) {
                return AT;
            }
            return null;
        }
    }

    public static enum DayPeriod {
        MIDNIGHT,
        NOON,
        MORNING1,
        AFTERNOON1,
        EVENING1,
        NIGHT1,
        MORNING2,
        AFTERNOON2,
        EVENING2,
        NIGHT2,
        AM,
        PM;
        
        public static DayPeriod[] VALUES;

        static {
            VALUES = DayPeriod.values();
        }

        private static DayPeriod fromStringOrNull(CharSequence charSequence) {
            if ("midnight".contentEquals(charSequence)) {
                return MIDNIGHT;
            }
            if ("noon".contentEquals(charSequence)) {
                return NOON;
            }
            if ("morning1".contentEquals(charSequence)) {
                return MORNING1;
            }
            if ("afternoon1".contentEquals(charSequence)) {
                return AFTERNOON1;
            }
            if ("evening1".contentEquals(charSequence)) {
                return EVENING1;
            }
            if ("night1".contentEquals(charSequence)) {
                return NIGHT1;
            }
            if ("morning2".contentEquals(charSequence)) {
                return MORNING2;
            }
            if ("afternoon2".contentEquals(charSequence)) {
                return AFTERNOON2;
            }
            if ("evening2".contentEquals(charSequence)) {
                return EVENING2;
            }
            if ("night2".contentEquals(charSequence)) {
                return NIGHT2;
            }
            if ("am".contentEquals(charSequence)) {
                return AM;
            }
            if ("pm".contentEquals(charSequence)) {
                return PM;
            }
            return null;
        }
    }

    private static class DayPeriodRulesCountSink
    extends UResource.Sink {
        private DayPeriodRulesData data;

        private DayPeriodRulesCountSink(DayPeriodRulesData dayPeriodRulesData) {
            this.data = dayPeriodRulesData;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                int n2 = DayPeriodRules.parseSetNum(key.toString());
                if (n2 > this.data.maxRuleSetNum) {
                    this.data.maxRuleSetNum = n2;
                }
                ++n;
            }
        }
    }

    private static final class DayPeriodRulesData {
        Map<String, Integer> localesToRuleSetNumMap = new HashMap<String, Integer>();
        int maxRuleSetNum = -1;
        DayPeriodRules[] rules;

        private DayPeriodRulesData() {
        }
    }

    private static final class DayPeriodRulesDataSink
    extends UResource.Sink {
        private CutoffType cutoffType;
        private int[] cutoffs = new int[25];
        private DayPeriodRulesData data;
        private DayPeriod period;
        private int ruleSetNum;

        private DayPeriodRulesDataSink(DayPeriodRulesData dayPeriodRulesData) {
            this.data = dayPeriodRulesData;
        }

        private void addCutoff(CutoffType cutoffType, String arrn) {
            if (cutoffType != null) {
                int n = DayPeriodRulesDataSink.parseHour((String)arrn);
                arrn = this.cutoffs;
                arrn[n] = arrn[n] | 1 << cutoffType.ordinal();
                return;
            }
            throw new ICUException("Cutoff type not recognized.");
        }

        private static int parseHour(String string) {
            int n = string.indexOf(58);
            if (n >= 0 && string.substring(n).equals(":00")) {
                string = string.substring(0, n);
                if (n != 1 && n != 2) {
                    throw new ICUException("Cutoff time must begin with h: or hh:");
                }
                n = Integer.parseInt(string);
                if (n >= 0 && n <= 24) {
                    return n;
                }
                throw new ICUException("Cutoff hour must be between 0 and 24, inclusive.");
            }
            throw new ICUException("Cutoff time must end in \":00\".");
        }

        private void processRules(UResource.Table table, UResource.Key key, UResource.Value value) {
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                int n2;
                int n3;
                DayPeriod[] arrdayPeriod;
                block9 : {
                    this.ruleSetNum = DayPeriodRules.parseSetNum(key.toString());
                    this.data.rules[this.ruleSetNum] = new DayPeriodRules();
                    arrdayPeriod = value.getTable();
                    n3 = 0;
                    do {
                        boolean bl = arrdayPeriod.getKeyAndValue(n3, key, value);
                        int n4 = 0;
                        if (!bl) break block9;
                        this.period = DayPeriod.fromStringOrNull(key);
                        if (this.period == null) break;
                        int[] arrn = value.getTable();
                        n4 = 0;
                        while (arrn.getKeyAndValue(n4, key, value)) {
                            if (value.getType() == 0) {
                                this.addCutoff(CutoffType.fromStringOrNull(key), value.getString());
                            } else {
                                this.cutoffType = CutoffType.fromStringOrNull(key);
                                UResource.Array array = value.getArray();
                                int n5 = array.getSize();
                                for (n2 = 0; n2 < n5; ++n2) {
                                    array.getValue(n2, value);
                                    this.addCutoff(this.cutoffType, value.getString());
                                }
                            }
                            ++n4;
                        }
                        this.setDayPeriodForHoursFromCutoffs();
                        for (n4 = 0; n4 < (arrn = this.cutoffs).length; ++n4) {
                            arrn[n4] = 0;
                        }
                        ++n3;
                    } while (true);
                    throw new ICUException("Unknown day period in data.");
                }
                arrdayPeriod = this.data.rules[this.ruleSetNum].dayPeriodForHour;
                n2 = arrdayPeriod.length;
                for (n3 = n4; n3 < n2; ++n3) {
                    if (arrdayPeriod[n3] != null) {
                        continue;
                    }
                    throw new ICUException("Rules in data don't cover all 24 hours (they should).");
                }
                ++n;
            }
        }

        private void setDayPeriodForHoursFromCutoffs() {
            DayPeriodRules dayPeriodRules = this.data.rules[this.ruleSetNum];
            block0 : for (int i = 0; i <= 24; ++i) {
                if ((this.cutoffs[i] & 1 << CutoffType.AT.ordinal()) > 0) {
                    if (i == 0 && this.period == DayPeriod.MIDNIGHT) {
                        dayPeriodRules.hasMidnight = true;
                    } else if (i == 12 && this.period == DayPeriod.NOON) {
                        dayPeriodRules.hasNoon = true;
                    } else {
                        throw new ICUException("AT cutoff must only be set for 0:00 or 12:00.");
                    }
                }
                if ((this.cutoffs[i] & 1 << CutoffType.FROM.ordinal()) <= 0 && (this.cutoffs[i] & 1 << CutoffType.AFTER.ordinal()) <= 0) continue;
                int n = i + 1;
                while (n != i) {
                    int n2 = n;
                    if (n == 25) {
                        n2 = 0;
                    }
                    if ((this.cutoffs[n2] & 1 << CutoffType.BEFORE.ordinal()) > 0) {
                        dayPeriodRules.add(i, n2, this.period);
                        continue block0;
                    }
                    n = n2 + 1;
                }
                throw new ICUException("FROM/AFTER cutoffs must have a matching BEFORE cutoff.");
            }
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                if (key.contentEquals("locales")) {
                    UResource.Table table2 = value.getTable();
                    int n2 = 0;
                    while (table2.getKeyAndValue(n2, key, value)) {
                        int n3 = DayPeriodRules.parseSetNum(value.getString());
                        this.data.localesToRuleSetNumMap.put(key.toString(), n3);
                        ++n2;
                    }
                } else if (key.contentEquals("rules")) {
                    this.processRules(value.getTable(), key, value);
                }
                ++n;
            }
        }
    }

}

