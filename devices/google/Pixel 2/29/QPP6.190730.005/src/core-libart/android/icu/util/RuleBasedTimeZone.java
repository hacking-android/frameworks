/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.Grego;
import android.icu.util.AnnualTimeZoneRule;
import android.icu.util.BasicTimeZone;
import android.icu.util.InitialTimeZoneRule;
import android.icu.util.TimeZone;
import android.icu.util.TimeZoneRule;
import android.icu.util.TimeZoneTransition;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class RuleBasedTimeZone
extends BasicTimeZone {
    private static final long serialVersionUID = 7580833058949327935L;
    private AnnualTimeZoneRule[] finalRules;
    private List<TimeZoneRule> historicRules;
    private transient List<TimeZoneTransition> historicTransitions;
    private final InitialTimeZoneRule initialRule;
    private volatile transient boolean isFrozen = false;
    private transient boolean upToDate;

    public RuleBasedTimeZone(String string, InitialTimeZoneRule initialTimeZoneRule) {
        super(string);
        this.initialRule = initialTimeZoneRule;
    }

    private void complete() {
        if (this.upToDate) {
            return;
        }
        Object object = this.finalRules;
        if (object != null && object[1] == null) {
            throw new IllegalStateException("Incomplete final rules");
        }
        if (this.historicRules != null || this.finalRules != null) {
            long l;
            long l2;
            Object object2;
            AnnualTimeZoneRule[] arrannualTimeZoneRule;
            block30 : {
                arrannualTimeZoneRule = this.initialRule;
                l2 = -184303902528000000L;
                object = this.historicRules;
                if (object != null) {
                    BitSet bitSet = new BitSet(object.size());
                    do {
                        long l3;
                        long l4;
                        int n;
                        Serializable serializable;
                        int n2 = arrannualTimeZoneRule.getRawOffset();
                        int n3 = arrannualTimeZoneRule.getDSTSavings();
                        l = 183882168921600000L;
                        object = null;
                        for (n = 0; n < this.historicRules.size(); ++n) {
                            if (bitSet.get(n)) {
                                object2 = object;
                                l4 = l;
                            } else {
                                serializable = this.historicRules.get(n);
                                Date date = ((TimeZoneRule)serializable).getNextStart(l2, n2, n3, false);
                                if (date == null) {
                                    bitSet.set(n);
                                    object2 = object;
                                    l4 = l;
                                } else {
                                    object2 = object;
                                    l4 = l;
                                    if (serializable != arrannualTimeZoneRule) {
                                        if (((TimeZoneRule)serializable).getName().equals(arrannualTimeZoneRule.getName()) && ((TimeZoneRule)serializable).getRawOffset() == arrannualTimeZoneRule.getRawOffset() && ((TimeZoneRule)serializable).getDSTSavings() == arrannualTimeZoneRule.getDSTSavings()) {
                                            object2 = object;
                                            l4 = l;
                                        } else {
                                            l3 = date.getTime();
                                            object2 = object;
                                            l4 = l;
                                            if (l3 < l) {
                                                l4 = l3;
                                                object2 = serializable;
                                            }
                                        }
                                    }
                                }
                            }
                            object = object2;
                            l = l4;
                        }
                        if (object == null) {
                            boolean bl;
                            boolean bl2 = true;
                            n = 0;
                            do {
                                bl = bl2;
                                if (n >= this.historicRules.size()) break;
                                if (!bitSet.get(n)) {
                                    bl = false;
                                    break;
                                }
                                ++n;
                            } while (true);
                            if (bl) break block30;
                        }
                        if (this.finalRules != null) {
                            for (n = 0; n < 2; ++n) {
                                object2 = this.finalRules;
                                if (object2[n] == arrannualTimeZoneRule) {
                                    object2 = object;
                                    l4 = l;
                                } else {
                                    serializable = object2[n].getNextStart(l2, n2, n3, false);
                                    object2 = object;
                                    l4 = l;
                                    if (serializable != null) {
                                        l3 = ((Date)serializable).getTime();
                                        object2 = object;
                                        l4 = l;
                                        if (l3 < l) {
                                            l4 = l3;
                                            object2 = this.finalRules[n];
                                        }
                                    }
                                }
                                object = object2;
                                l = l4;
                            }
                        }
                        if (object != null) {
                            if (this.historicTransitions == null) {
                                this.historicTransitions = new ArrayList<TimeZoneTransition>();
                            }
                            this.historicTransitions.add(new TimeZoneTransition(l, (TimeZoneRule)arrannualTimeZoneRule, (TimeZoneRule)object));
                            arrannualTimeZoneRule = object;
                            l2 = l;
                            continue;
                        }
                        break block30;
                        break;
                    } while (true);
                }
                l2 = -184303902528000000L;
            }
            if (this.finalRules != null) {
                if (this.historicTransitions == null) {
                    this.historicTransitions = new ArrayList<TimeZoneTransition>();
                }
                object = this.finalRules[0].getNextStart(l2, arrannualTimeZoneRule.getRawOffset(), arrannualTimeZoneRule.getDSTSavings(), false);
                object2 = this.finalRules[1].getNextStart(l2, arrannualTimeZoneRule.getRawOffset(), arrannualTimeZoneRule.getDSTSavings(), false);
                if (((Date)object2).after((Date)object)) {
                    this.historicTransitions.add(new TimeZoneTransition(((Date)object).getTime(), (TimeZoneRule)arrannualTimeZoneRule, this.finalRules[0]));
                    arrannualTimeZoneRule = this.finalRules[1].getNextStart(((Date)object).getTime(), this.finalRules[0].getRawOffset(), this.finalRules[0].getDSTSavings(), false);
                    object = this.historicTransitions;
                    l = arrannualTimeZoneRule.getTime();
                    arrannualTimeZoneRule = this.finalRules;
                    object.add(new TimeZoneTransition(l, arrannualTimeZoneRule[0], arrannualTimeZoneRule[1]));
                } else {
                    this.historicTransitions.add(new TimeZoneTransition(((Date)object2).getTime(), (TimeZoneRule)arrannualTimeZoneRule, this.finalRules[1]));
                    arrannualTimeZoneRule = this.finalRules[0].getNextStart(((Date)object2).getTime(), this.finalRules[1].getRawOffset(), this.finalRules[1].getDSTSavings(), false);
                    object = this.historicTransitions;
                    l = arrannualTimeZoneRule.getTime();
                    arrannualTimeZoneRule = this.finalRules;
                    object.add(new TimeZoneTransition(l, arrannualTimeZoneRule[1], arrannualTimeZoneRule[0]));
                }
            }
        }
        this.upToDate = true;
    }

    private TimeZoneRule findRuleInFinal(long l, boolean bl, int n, int n2) {
        Object object = this.finalRules;
        if (object != null && ((AnnualTimeZoneRule[])object).length == 2 && object[0] != null && object[1] != null) {
            long l2;
            long l3 = l2 = l;
            if (bl) {
                l3 = l2 - (long)RuleBasedTimeZone.getLocalDelta(object[1].getRawOffset(), this.finalRules[1].getDSTSavings(), this.finalRules[0].getRawOffset(), this.finalRules[0].getDSTSavings(), n, n2);
            }
            object = this.finalRules;
            object = object[0].getPreviousStart(l3, object[1].getRawOffset(), this.finalRules[1].getDSTSavings(), true);
            l = l2 = l;
            if (bl) {
                l = l2 - (long)RuleBasedTimeZone.getLocalDelta(this.finalRules[0].getRawOffset(), this.finalRules[0].getDSTSavings(), this.finalRules[1].getRawOffset(), this.finalRules[1].getDSTSavings(), n, n2);
            }
            Object object2 = this.finalRules;
            object2 = object2[1].getPreviousStart(l, object2[0].getRawOffset(), this.finalRules[0].getDSTSavings(), true);
            if (object != null && object2 != null) {
                object = ((Date)object).after((Date)object2) ? this.finalRules[0] : this.finalRules[1];
                return object;
            }
            if (object != null) {
                return this.finalRules[0];
            }
            if (object2 != null) {
                return this.finalRules[1];
            }
            return null;
        }
        return null;
    }

    private static int getLocalDelta(int n, int n2, int n3, int n4, int n5, int n6) {
        n += n2;
        n3 += n4;
        boolean bl = false;
        boolean bl2 = n2 != 0 && n4 == 0;
        boolean bl3 = bl;
        if (n2 == 0) {
            bl3 = bl;
            if (n4 != 0) {
                bl3 = true;
            }
        }
        if (n3 - n >= 0) {
            if (!((n5 & 3) == 1 && bl2 || (n5 & 3) == 3 && bl3)) {
                if ((n5 & 3) == 1 && bl3 || (n5 & 3) == 3 && bl2) {
                    n = n3;
                } else if ((n5 & 12) != 12) {
                    n = n3;
                }
            }
        } else if ((n6 & 3) == 1 && bl2 || (n6 & 3) == 3 && bl3) {
            n = n3;
        } else if (!((n6 & 3) == 1 && bl3 || (n6 & 3) == 3 && bl2 || (n6 & 12) == 4)) {
            n = n3;
        }
        return n;
    }

    private void getOffset(long l, boolean bl, int n, int n2, int[] arrn) {
        int n3;
        this.complete();
        TimeZoneRule timeZoneRule = null;
        Object object = this.historicTransitions;
        if (object == null) {
            object = this.initialRule;
        } else if (l < RuleBasedTimeZone.getTransitionTime(object.get(0), bl, n, n2)) {
            object = this.initialRule;
        } else if (l > RuleBasedTimeZone.getTransitionTime(this.historicTransitions.get(n3), bl, n, n2)) {
            if (this.finalRules != null) {
                timeZoneRule = this.findRuleInFinal(l, bl, n, n2);
            }
            object = timeZoneRule;
            if (timeZoneRule == null) {
                object = this.historicTransitions.get(n3).getTo();
            }
        } else {
            int n4;
            for (n4 = n3 = this.historicTransitions.size() - 1; n4 >= 0 && l < RuleBasedTimeZone.getTransitionTime(this.historicTransitions.get(n4), bl, n, n2); --n4) {
            }
            object = this.historicTransitions.get(n4).getTo();
        }
        arrn[0] = ((TimeZoneRule)object).getRawOffset();
        arrn[1] = ((TimeZoneRule)object).getDSTSavings();
    }

    private static long getTransitionTime(TimeZoneTransition timeZoneTransition, boolean bl, int n, int n2) {
        long l;
        long l2 = l = timeZoneTransition.getTime();
        if (bl) {
            l2 = l + (long)RuleBasedTimeZone.getLocalDelta(timeZoneTransition.getFrom().getRawOffset(), timeZoneTransition.getFrom().getDSTSavings(), timeZoneTransition.getTo().getRawOffset(), timeZoneTransition.getTo().getDSTSavings(), n, n2);
        }
        return l2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void addTransitionRule(TimeZoneRule timeZoneRule) {
        if (this.isFrozen()) throw new UnsupportedOperationException("Attempt to modify a frozen RuleBasedTimeZone instance.");
        if (!timeZoneRule.isTransitionRule()) throw new IllegalArgumentException("Rule must be a transition rule");
        if (timeZoneRule instanceof AnnualTimeZoneRule && ((AnnualTimeZoneRule)timeZoneRule).getEndYear() == Integer.MAX_VALUE) {
            AnnualTimeZoneRule[] arrannualTimeZoneRule = this.finalRules;
            if (arrannualTimeZoneRule == null) {
                this.finalRules = new AnnualTimeZoneRule[2];
                this.finalRules[0] = (AnnualTimeZoneRule)timeZoneRule;
            } else {
                if (arrannualTimeZoneRule[1] != null) throw new IllegalStateException("Too many final rules");
                arrannualTimeZoneRule[1] = (AnnualTimeZoneRule)timeZoneRule;
            }
        } else {
            if (this.historicRules == null) {
                this.historicRules = new ArrayList<TimeZoneRule>();
            }
            this.historicRules.add(timeZoneRule);
        }
        this.upToDate = false;
    }

    @Override
    public Object clone() {
        if (this.isFrozen()) {
            return this;
        }
        return this.cloneAsThawed();
    }

    @Override
    public TimeZone cloneAsThawed() {
        RuleBasedTimeZone ruleBasedTimeZone = (RuleBasedTimeZone)super.cloneAsThawed();
        AnnualTimeZoneRule[] arrannualTimeZoneRule = this.historicRules;
        if (arrannualTimeZoneRule != null) {
            ruleBasedTimeZone.historicRules = new ArrayList<TimeZoneRule>((Collection<TimeZoneRule>)arrannualTimeZoneRule);
        }
        if ((arrannualTimeZoneRule = this.finalRules) != null) {
            ruleBasedTimeZone.finalRules = (AnnualTimeZoneRule[])arrannualTimeZoneRule.clone();
        }
        ruleBasedTimeZone.isFrozen = false;
        return ruleBasedTimeZone;
    }

    @Override
    public TimeZone freeze() {
        this.complete();
        this.isFrozen = true;
        return this;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public TimeZoneTransition getNextTransition(long l, boolean bl) {
        int n;
        Object object;
        this.complete();
        Object object2 = this.historicTransitions;
        if (object2 == null) {
            return null;
        }
        int n2 = 0;
        long l2 = ((TimeZoneTransition)(object2 = object2.get(0))).getTime();
        if (!(l2 > l || bl && l2 == l)) {
            n = this.historicTransitions.size() - 1;
            object2 = this.historicTransitions.get(n);
            l2 = ((TimeZoneTransition)object2).getTime();
            if (bl && l2 == l) {
                n = n2;
            } else if (l2 <= l) {
                object2 = this.finalRules;
                if (object2 == null) return null;
                object2 = object2[0].getNextStart(l, object2[1].getRawOffset(), this.finalRules[1].getDSTSavings(), bl);
                object = this.finalRules;
                if (((Date)(object = ((AnnualTimeZoneRule)object[1]).getNextStart(l, ((TimeZoneRule)object[0]).getRawOffset(), this.finalRules[0].getDSTSavings(), bl))).after((Date)object2)) {
                    l = ((Date)object2).getTime();
                    object2 = this.finalRules;
                    object2 = new TimeZoneTransition(l, (TimeZoneRule)object2[1], (TimeZoneRule)object2[0]);
                } else {
                    l = ((Date)object).getTime();
                    object2 = this.finalRules;
                    object2 = new TimeZoneTransition(l, (TimeZoneRule)object2[0], (TimeZoneRule)object2[1]);
                }
                n = 1;
            } else {
                block11 : {
                    --n;
                    while (n > 0) {
                        object = this.historicTransitions.get(n);
                        l2 = ((TimeZoneTransition)object).getTime();
                        if (l2 >= l && (bl || l2 != l)) {
                            --n;
                            object2 = object;
                            continue;
                        }
                        l = l2;
                        break block11;
                    }
                    l = l2;
                }
                n = n2;
            }
        } else {
            n = n2;
        }
        TimeZoneRule timeZoneRule = ((TimeZoneTransition)object2).getFrom();
        TimeZoneRule timeZoneRule2 = ((TimeZoneTransition)object2).getTo();
        object = object2;
        if (timeZoneRule.getRawOffset() != timeZoneRule2.getRawOffset()) return object;
        object = object2;
        if (timeZoneRule.getDSTSavings() != timeZoneRule2.getDSTSavings()) return object;
        if (n == 0) return this.getNextTransition(((TimeZoneTransition)object2).getTime(), false);
        return null;
    }

    @Override
    public int getOffset(int n, int n2, int n3, int n4, int n5, int n6) {
        n = n == 0 ? 1 - n2 : n2;
        long l = Grego.fieldsToDay(n, n3, n4);
        long l2 = n6;
        int[] arrn = new int[2];
        this.getOffset(l * 86400000L + l2, true, 3, 1, arrn);
        return arrn[0] + arrn[1];
    }

    @Override
    public void getOffset(long l, boolean bl, int[] arrn) {
        this.getOffset(l, bl, 4, 12, arrn);
    }

    @Deprecated
    @Override
    public void getOffsetFromLocal(long l, int n, int n2, int[] arrn) {
        this.getOffset(l, true, n, n2, arrn);
    }

    @Override
    public TimeZoneTransition getPreviousTransition(long l, boolean bl) {
        Object object;
        this.complete();
        Object object2 = this.historicTransitions;
        if (object2 == null) {
            return null;
        }
        object2 = object2.get(0);
        long l2 = ((TimeZoneTransition)object2).getTime();
        if (!bl || l2 != l) {
            if (l2 >= l) {
                return null;
            }
            int n = this.historicTransitions.size() - 1;
            object2 = this.historicTransitions.get(n);
            l2 = ((TimeZoneTransition)object2).getTime();
            if (!bl || l2 != l) {
                if (l2 < l) {
                    object = this.finalRules;
                    if (object != null) {
                        object2 = object[0].getPreviousStart(l, object[1].getRawOffset(), this.finalRules[1].getDSTSavings(), bl);
                        object = this.finalRules;
                        if (((Date)(object = object[1].getPreviousStart(l, object[0].getRawOffset(), this.finalRules[0].getDSTSavings(), bl))).before((Date)object2)) {
                            l = ((Date)object2).getTime();
                            object2 = this.finalRules;
                            object2 = new TimeZoneTransition(l, (TimeZoneRule)object2[1], (TimeZoneRule)object2[0]);
                        } else {
                            l = ((Date)object).getTime();
                            object2 = this.finalRules;
                            object2 = new TimeZoneTransition(l, (TimeZoneRule)object2[0], (TimeZoneRule)object2[1]);
                        }
                    }
                } else {
                    --n;
                    while (!(n < 0 || (l2 = ((TimeZoneTransition)(object2 = this.historicTransitions.get(n))).getTime()) < l || bl && l2 == l)) {
                        --n;
                    }
                }
            }
        }
        TimeZoneRule timeZoneRule = ((TimeZoneTransition)object2).getFrom();
        TimeZoneRule timeZoneRule2 = ((TimeZoneTransition)object2).getTo();
        object = object2;
        if (timeZoneRule.getRawOffset() == timeZoneRule2.getRawOffset()) {
            object = object2;
            if (timeZoneRule.getDSTSavings() == timeZoneRule2.getDSTSavings()) {
                object = this.getPreviousTransition(((TimeZoneTransition)object2).getTime(), false);
            }
        }
        return object;
    }

    @Override
    public int getRawOffset() {
        long l = System.currentTimeMillis();
        int[] arrn = new int[2];
        this.getOffset(l, false, arrn);
        return arrn[0];
    }

    @Override
    public TimeZoneRule[] getTimeZoneRules() {
        AnnualTimeZoneRule[] arrannualTimeZoneRule;
        int n = 1;
        TimeZoneRule[] arrtimeZoneRule = this.historicRules;
        if (arrtimeZoneRule != null) {
            n = 1 + arrtimeZoneRule.size();
        }
        arrtimeZoneRule = this.finalRules;
        int n2 = n;
        if (arrtimeZoneRule != null) {
            n2 = arrtimeZoneRule[1] != null ? n + 2 : n + 1;
        }
        arrtimeZoneRule = new TimeZoneRule[n2];
        arrtimeZoneRule[0] = this.initialRule;
        n2 = 1;
        n = 1;
        if (this.historicRules != null) {
            do {
                n2 = ++n;
                if (n >= this.historicRules.size() + 1) break;
                arrtimeZoneRule[n] = this.historicRules.get(n - 1);
            } while (true);
        }
        if ((arrannualTimeZoneRule = this.finalRules) != null) {
            arrtimeZoneRule[n2] = arrannualTimeZoneRule[0];
            if (arrannualTimeZoneRule[1] != null) {
                arrtimeZoneRule[n2 + 1] = arrannualTimeZoneRule[1];
            }
        }
        return arrtimeZoneRule;
    }

    @Override
    public boolean hasSameRules(TimeZone timeZone) {
        block14 : {
            block17 : {
                block16 : {
                    block15 : {
                        Object object;
                        int n;
                        Object object2;
                        block13 : {
                            block12 : {
                                if (this == timeZone) {
                                    return true;
                                }
                                if (!(timeZone instanceof RuleBasedTimeZone)) {
                                    return false;
                                }
                                timeZone = (RuleBasedTimeZone)timeZone;
                                if (!this.initialRule.isEquivalentTo(((RuleBasedTimeZone)timeZone).initialRule)) {
                                    return false;
                                }
                                if (this.finalRules == null || ((RuleBasedTimeZone)timeZone).finalRules == null) break block12;
                                for (n = 0; n < ((AnnualTimeZoneRule[])(object2 = this.finalRules)).length; ++n) {
                                    if (object2[n] == null && ((RuleBasedTimeZone)timeZone).finalRules[n] == null || (object2 = this.finalRules)[n] != null && (object = ((RuleBasedTimeZone)timeZone).finalRules)[n] != null && ((AnnualTimeZoneRule)object2[n]).isEquivalentTo(object[n])) {
                                        continue;
                                    }
                                    return false;
                                }
                                break block13;
                            }
                            if (this.finalRules != null || ((RuleBasedTimeZone)timeZone).finalRules != null) break block14;
                        }
                        if ((object2 = this.historicRules) == null || ((RuleBasedTimeZone)timeZone).historicRules == null) break block15;
                        if (object2.size() != ((RuleBasedTimeZone)timeZone).historicRules.size()) {
                            return false;
                        }
                        for (TimeZoneRule timeZoneRule : this.historicRules) {
                            block11 : {
                                int n2 = 0;
                                object = ((RuleBasedTimeZone)timeZone).historicRules.iterator();
                                do {
                                    n = n2;
                                    if (!object.hasNext()) break block11;
                                } while (!timeZoneRule.isEquivalentTo((TimeZoneRule)object.next()));
                                n = 1;
                            }
                            if (n != 0) continue;
                            return false;
                        }
                        break block16;
                    }
                    if (this.historicRules != null || ((RuleBasedTimeZone)timeZone).historicRules != null) break block17;
                }
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean inDaylightTime(Date date) {
        int[] arrn = new int[2];
        this.getOffset(date.getTime(), false, arrn);
        boolean bl = true;
        if (arrn[1] == 0) {
            bl = false;
        }
        return bl;
    }

    @Override
    public boolean isFrozen() {
        return this.isFrozen;
    }

    @Override
    public boolean observesDaylightTime() {
        long l = System.currentTimeMillis();
        Object object = new int[2];
        this.getOffset(l, false, (int[])object);
        if (object[1] != 0) {
            return true;
        }
        object = this.finalRules;
        object = object == null ? null : new BitSet(((int[])object).length);
        do {
            TimeZoneTransition timeZoneTransition;
            block8 : {
                block7 : {
                    AnnualTimeZoneRule[] arrannualTimeZoneRule;
                    if ((timeZoneTransition = this.getNextTransition(l, false)) == null) break block7;
                    TimeZoneRule timeZoneRule = timeZoneTransition.getTo();
                    if (timeZoneRule.getDSTSavings() != 0) {
                        return true;
                    }
                    if (object == null) break block8;
                    for (int i = 0; i < (arrannualTimeZoneRule = this.finalRules).length; ++i) {
                        if (!arrannualTimeZoneRule[i].equals(timeZoneRule)) continue;
                        ((BitSet)object).set(i);
                    }
                    if (((BitSet)object).cardinality() != this.finalRules.length) break block8;
                }
                return false;
            }
            l = timeZoneTransition.getTime();
        } while (true);
    }

    @Override
    public void setRawOffset(int n) {
        throw new UnsupportedOperationException("setRawOffset in RuleBasedTimeZone is not supported.");
    }

    @Override
    public boolean useDaylightTime() {
        long l = System.currentTimeMillis();
        Object object = new int[2];
        this.getOffset(l, false, (int[])object);
        if (object[1] != 0) {
            return true;
        }
        object = this.getNextTransition(l, false);
        return object != null && ((TimeZoneTransition)object).getTo().getDSTSavings() != 0;
    }
}

