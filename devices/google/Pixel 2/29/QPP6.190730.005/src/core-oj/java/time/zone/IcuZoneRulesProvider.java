/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.util.AnnualTimeZoneRule
 *  android.icu.util.BasicTimeZone
 *  android.icu.util.DateTimeRule
 *  android.icu.util.InitialTimeZoneRule
 *  android.icu.util.TimeZone
 *  android.icu.util.TimeZone$SystemTimeZoneType
 *  android.icu.util.TimeZoneRule
 *  android.icu.util.TimeZoneTransition
 *  libcore.util.BasicLruCache
 */
package java.time.zone;

import android.icu.util.AnnualTimeZoneRule;
import android.icu.util.BasicTimeZone;
import android.icu.util.DateTimeRule;
import android.icu.util.InitialTimeZoneRule;
import android.icu.util.TimeZone;
import android.icu.util.TimeZoneRule;
import android.icu.util.TimeZoneTransition;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneOffsetTransitionRule;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesException;
import java.time.zone.ZoneRulesProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import libcore.util.BasicLruCache;

public class IcuZoneRulesProvider
extends ZoneRulesProvider {
    private static final int MAX_TRANSITIONS = 10000;
    private static final int SECONDS_IN_DAY = 86400;
    private final BasicLruCache<String, ZoneRules> cache = new ZoneRulesCache(8);

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    static ZoneRules generateZoneRules(String var0) {
        block6 : {
            var1_1 = TimeZone.getFrozenTimeZone((String)var0);
            var2_2 = var1_1 instanceof BasicTimeZone;
            var3_3 = new StringBuilder();
            var3_3.append("Unexpected time zone class ");
            var3_3.append(var1_1.getClass());
            IcuZoneRulesProvider.verify(var2_2, var0, var3_3.toString());
            var4_4 = (BasicTimeZone)var1_1;
            var5_5 = var4_4.getTimeZoneRules();
            var6_6 = (InitialTimeZoneRule)var5_5[0];
            var7_7 = IcuZoneRulesProvider.millisToOffset(var6_6.getRawOffset());
            var8_8 = IcuZoneRulesProvider.millisToOffset(var6_6.getRawOffset() + var6_6.getDSTSavings());
            var9_9 = new ArrayList<ZoneOffsetTransition>();
            var10_10 = new ArrayList<ZoneOffsetTransition>();
            var11_11 = new ArrayList<ZoneOffsetTransitionRule>();
            var12_12 = 0;
            var13_13 = null;
            var3_3 = null;
            var14_14 = var4_4.getNextTransition(Long.MIN_VALUE, false);
            var15_15 = 1;
            while (var14_14 != null) {
                var16_16 = var14_14.getFrom();
                var17_17 = var14_14.getTo();
                var2_2 = false;
                if (var16_16.getRawOffset() != var17_17.getRawOffset()) {
                    var9_9.add(new ZoneOffsetTransition(TimeUnit.MILLISECONDS.toSeconds(var14_14.getTime()), IcuZoneRulesProvider.millisToOffset(var16_16.getRawOffset()), IcuZoneRulesProvider.millisToOffset(var17_17.getRawOffset())));
                    var2_2 = true;
                }
                if ((var18_18 = var16_16.getRawOffset() + var16_16.getDSTSavings()) != (var19_19 = var17_17.getRawOffset() + var17_17.getDSTSavings())) {
                    var10_10.add(new ZoneOffsetTransition(TimeUnit.MILLISECONDS.toSeconds(var14_14.getTime()), IcuZoneRulesProvider.millisToOffset(var18_18), IcuZoneRulesProvider.millisToOffset(var19_19)));
                    var2_2 = true;
                }
                IcuZoneRulesProvider.verify(var2_2, var0, "Transition changed neither total nor raw offset.");
                if (!(var17_17 instanceof AnnualTimeZoneRule)) ** GOTO lbl49
                if (var13_13 == null) {
                    var12_12 = var16_16.getDSTSavings();
                    var13_13 = (AnnualTimeZoneRule)var17_17;
                    var2_2 = var13_13.getEndYear() == Integer.MAX_VALUE;
                    IcuZoneRulesProvider.verify(var2_2, var0, "AnnualTimeZoneRule is not permanent.");
                } else {
                    var3_3 = (AnnualTimeZoneRule)var17_17;
                    var2_2 = var3_3.getEndYear() == Integer.MAX_VALUE;
                    IcuZoneRulesProvider.verify(var2_2, var0, "AnnualTimeZoneRule is not permanent.");
                    var2_2 = var4_4.getNextTransition(var14_14.getTime(), false).getTo() == var13_13;
                    IcuZoneRulesProvider.verify(var2_2, var0, "Unexpected rule after 2 AnnualTimeZoneRules.");
                    var2_2 = false;
                    break block6;
lbl49: // 1 sources:
                    var2_2 = var13_13 == null;
                    IcuZoneRulesProvider.verify(var2_2, var0, "Unexpected rule after AnnualTimeZoneRule.");
                }
                var2_2 = var15_15 <= 10000;
                IcuZoneRulesProvider.verify(var2_2, var0, "More than 10000 transitions.");
                var14_14 = var4_4.getNextTransition(var14_14.getTime(), false);
                ++var15_15;
            }
            var2_2 = false;
        }
        if (var13_13 == null) return ZoneRules.of(var7_7, var8_8, var9_9, var10_10, var11_11);
        if (var3_3 != null) {
            var2_2 = true;
        }
        IcuZoneRulesProvider.verify(var2_2, var0, "Only one AnnualTimeZoneRule.");
        var11_11.add(IcuZoneRulesProvider.toZoneOffsetTransitionRule(var13_13, var12_12));
        var11_11.add(IcuZoneRulesProvider.toZoneOffsetTransitionRule((AnnualTimeZoneRule)var3_3, var13_13.getDSTSavings()));
        return ZoneRules.of(var7_7, var8_8, var9_9, var10_10, var11_11);
    }

    private static ZoneOffset millisToOffset(int n) {
        return ZoneOffset.ofTotalSeconds((int)TimeUnit.MILLISECONDS.toSeconds(n));
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private static ZoneOffsetTransitionRule toZoneOffsetTransitionRule(AnnualTimeZoneRule object, int n) {
        boolean bl;
        LocalTime localTime;
        void var2_6;
        DateTimeRule dateTimeRule = object.getRule();
        Month month = Month.JANUARY.plus(dateTimeRule.getRuleMonth());
        DayOfWeek dayOfWeek = DayOfWeek.SATURDAY.plus(dateTimeRule.getRuleDayOfWeek());
        int n2 = dateTimeRule.getDateRuleType();
        if (n2 != 0) {
            if (n2 == 1) {
                throw new ZoneRulesException("Date rule type DOW is unsupported");
            }
            if (n2 != 2) {
                if (n2 != 3) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unexpected date rule type: ");
                    ((StringBuilder)object).append(dateTimeRule.getDateRuleType());
                    throw new ZoneRulesException(((StringBuilder)object).toString());
                }
                n2 = -month.maxLength() + dateTimeRule.getRuleDayOfMonth() - 1;
            } else {
                n2 = dateTimeRule.getRuleDayOfMonth();
            }
        } else {
            n2 = dateTimeRule.getRuleDayOfMonth();
            dayOfWeek = null;
        }
        int n3 = (int)TimeUnit.MILLISECONDS.toSeconds(dateTimeRule.getRuleMillisInDay());
        if (n3 == 86400) {
            localTime = LocalTime.MIDNIGHT;
            bl = true;
        } else {
            localTime = LocalTime.ofSecondOfDay(n3);
            bl = false;
        }
        n3 = dateTimeRule.getTimeRuleType();
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 != 2) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unexpected time rule type ");
                    ((StringBuilder)object).append(dateTimeRule.getTimeRuleType());
                    throw new ZoneRulesException(((StringBuilder)object).toString());
                }
                ZoneOffsetTransitionRule.TimeDefinition timeDefinition = ZoneOffsetTransitionRule.TimeDefinition.UTC;
            } else {
                ZoneOffsetTransitionRule.TimeDefinition timeDefinition = ZoneOffsetTransitionRule.TimeDefinition.STANDARD;
            }
        } else {
            ZoneOffsetTransitionRule.TimeDefinition timeDefinition = ZoneOffsetTransitionRule.TimeDefinition.WALL;
        }
        ZoneOffset zoneOffset = IcuZoneRulesProvider.millisToOffset(object.getRawOffset());
        ZoneOffset zoneOffset2 = IcuZoneRulesProvider.millisToOffset(object.getRawOffset() + n);
        return ZoneOffsetTransitionRule.of(month, n2, dayOfWeek, localTime, bl, (ZoneOffsetTransitionRule.TimeDefinition)var2_6, zoneOffset, zoneOffset2, IcuZoneRulesProvider.millisToOffset(object.getRawOffset() + object.getDSTSavings()));
    }

    private static void verify(boolean bl, String string, String string2) {
        if (bl) {
            return;
        }
        throw new ZoneRulesException(String.format("Failed verification of zone %s: %s", string, string2));
    }

    @Override
    protected ZoneRules provideRules(String string, boolean bl) {
        return (ZoneRules)this.cache.get((Object)string);
    }

    @Override
    protected NavigableMap<String, ZoneRules> provideVersions(String string) {
        return new TreeMap<String, ZoneRules>(Collections.singletonMap(TimeZone.getTZDataVersion(), this.provideRules(string, false)));
    }

    @Override
    protected Set<String> provideZoneIds() {
        HashSet<String> hashSet = new HashSet<String>(TimeZone.getAvailableIDs((TimeZone.SystemTimeZoneType)TimeZone.SystemTimeZoneType.ANY, null, null));
        hashSet.remove("GMT+0");
        hashSet.remove("GMT-0");
        return hashSet;
    }

    private static class ZoneRulesCache
    extends BasicLruCache<String, ZoneRules> {
        ZoneRulesCache(int n) {
            super(n);
        }

        protected ZoneRules create(String string) {
            String string2 = TimeZone.getCanonicalID((String)string);
            if (!string2.equals(string)) {
                return (ZoneRules)this.get((Object)string2);
            }
            return IcuZoneRulesProvider.generateZoneRules(string);
        }
    }

}

