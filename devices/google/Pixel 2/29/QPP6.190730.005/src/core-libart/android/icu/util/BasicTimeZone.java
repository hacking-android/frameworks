/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.Grego;
import android.icu.util.AnnualTimeZoneRule;
import android.icu.util.DateTimeRule;
import android.icu.util.InitialTimeZoneRule;
import android.icu.util.TimeArrayTimeZoneRule;
import android.icu.util.TimeZone;
import android.icu.util.TimeZoneRule;
import android.icu.util.TimeZoneTransition;
import java.util.BitSet;
import java.util.Date;
import java.util.LinkedList;

public abstract class BasicTimeZone
extends TimeZone {
    @Deprecated
    protected static final int FORMER_LATTER_MASK = 12;
    @Deprecated
    public static final int LOCAL_DST = 3;
    @Deprecated
    public static final int LOCAL_FORMER = 4;
    @Deprecated
    public static final int LOCAL_LATTER = 12;
    @Deprecated
    public static final int LOCAL_STD = 1;
    private static final long MILLIS_PER_YEAR = 31536000000L;
    @Deprecated
    protected static final int STD_DST_MASK = 3;
    private static final long serialVersionUID = -3204278532246180932L;

    protected BasicTimeZone() {
    }

    @Deprecated
    protected BasicTimeZone(String string) {
        super(string);
    }

    public abstract TimeZoneTransition getNextTransition(long var1, boolean var3);

    @Deprecated
    public void getOffsetFromLocal(long l, int n, int n2, int[] arrn) {
        throw new IllegalStateException("Not implemented");
    }

    public abstract TimeZoneTransition getPreviousTransition(long var1, boolean var3);

    public TimeZoneRule[] getSimpleTimeZoneRulesNear(long l) {
        Object object;
        Object object2 = null;
        Object object3 = null;
        Object object4 = this.getNextTransition(l, false);
        if (object4 != null) {
            object = ((TimeZoneTransition)object4).getFrom().getName();
            int n = ((TimeZoneTransition)object4).getFrom().getRawOffset();
            int n2 = ((TimeZoneTransition)object4).getFrom().getDSTSavings();
            long l2 = ((TimeZoneTransition)object4).getTime();
            if ((((TimeZoneTransition)object4).getFrom().getDSTSavings() == 0 && ((TimeZoneTransition)object4).getTo().getDSTSavings() != 0 || ((TimeZoneTransition)object4).getFrom().getDSTSavings() != 0 && ((TimeZoneTransition)object4).getTo().getDSTSavings() == 0) && l + 31536000000L > l2) {
                Object object5 = new AnnualTimeZoneRule[2];
                object3 = Grego.timeToFields((long)((TimeZoneTransition)object4).getFrom().getRawOffset() + l2 + (long)((TimeZoneTransition)object4).getFrom().getDSTSavings(), null);
                int n3 = Grego.getDayOfWeekInMonth(object3[0], object3[1], object3[2]);
                object2 = new DateTimeRule(object3[1], n3, object3[3], object3[5], 0);
                object5[0] = new AnnualTimeZoneRule(((TimeZoneTransition)object4).getTo().getName(), n, ((TimeZoneTransition)object4).getTo().getDSTSavings(), (DateTimeRule)object2, object3[0], Integer.MAX_VALUE);
                if (((TimeZoneTransition)object4).getTo().getRawOffset() == n) {
                    object2 = this.getNextTransition(l2, false);
                    if (object2 != null && (((TimeZoneTransition)object2).getFrom().getDSTSavings() == 0 && ((TimeZoneTransition)object2).getTo().getDSTSavings() != 0 || ((TimeZoneTransition)object2).getFrom().getDSTSavings() != 0 && ((TimeZoneTransition)object2).getTo().getDSTSavings() == 0) && l2 + 31536000000L > ((TimeZoneTransition)object2).getTime()) {
                        long l3 = ((TimeZoneTransition)object2).getTime();
                        long l4 = ((TimeZoneTransition)object2).getFrom().getRawOffset();
                        object3 = Grego.timeToFields((long)((TimeZoneTransition)object2).getFrom().getDSTSavings() + (l3 + l4), (int[])object3);
                        n3 = Grego.getDayOfWeekInMonth(object3[0], object3[1], object3[2]);
                        object4 = new DateTimeRule(object3[1], n3, object3[3], object3[5], 0);
                        AnnualTimeZoneRule annualTimeZoneRule = new AnnualTimeZoneRule(((TimeZoneTransition)object2).getTo().getName(), ((TimeZoneTransition)object2).getTo().getRawOffset(), ((TimeZoneTransition)object2).getTo().getDSTSavings(), (DateTimeRule)object4, object3[0] - 1, Integer.MAX_VALUE);
                        object4 = annualTimeZoneRule.getPreviousStart(l, ((TimeZoneTransition)object2).getFrom().getRawOffset(), ((TimeZoneTransition)object2).getFrom().getDSTSavings(), true);
                        if (object4 != null && ((Date)object4).getTime() <= l && n == ((TimeZoneTransition)object2).getTo().getRawOffset() && n2 == ((TimeZoneTransition)object2).getTo().getDSTSavings()) {
                            object5[1] = annualTimeZoneRule;
                        }
                    }
                } else {
                    object2 = object4;
                }
                object2 = object5;
                if (object2[1] == null && (object5 = this.getPreviousTransition(l, true)) != null && (((TimeZoneTransition)object5).getFrom().getDSTSavings() == 0 && ((TimeZoneTransition)object5).getTo().getDSTSavings() != 0 || ((TimeZoneTransition)object5).getFrom().getDSTSavings() != 0 && ((TimeZoneTransition)object5).getTo().getDSTSavings() == 0)) {
                    object3 = Grego.timeToFields(((TimeZoneTransition)object5).getTime() + (long)((TimeZoneTransition)object5).getFrom().getRawOffset() + (long)((TimeZoneTransition)object5).getFrom().getDSTSavings(), (int[])object3);
                    n3 = Grego.getDayOfWeekInMonth(object3[0], object3[1], object3[2]);
                    object3 = new DateTimeRule(object3[1], n3, object3[3], object3[5], 0);
                    object3 = new AnnualTimeZoneRule(((TimeZoneTransition)object5).getTo().getName(), n, n2, (DateTimeRule)object3, ((AnnualTimeZoneRule)object2[0]).getStartYear() - 1, Integer.MAX_VALUE);
                    if (((AnnualTimeZoneRule)object3).getNextStart(l, ((TimeZoneTransition)object5).getFrom().getRawOffset(), ((TimeZoneTransition)object5).getFrom().getDSTSavings(), false).getTime() > l2) {
                        object2[1] = object3;
                    }
                }
                if (object2[1] == null) {
                    object3 = null;
                    object2 = object;
                    object = object3;
                } else {
                    object3 = ((TimeZoneRule)object2[0]).getName();
                    n = ((TimeZoneRule)object2[0]).getRawOffset();
                    n2 = ((TimeZoneRule)object2[0]).getDSTSavings();
                    object = object2;
                    object2 = object3;
                }
            } else {
                object2 = object;
                object = object3;
            }
            object3 = new InitialTimeZoneRule((String)object2, n, n2);
            object2 = object;
            object = object3;
        } else {
            object = this.getPreviousTransition(l, true);
            if (object != null) {
                object = new InitialTimeZoneRule(object.getTo().getName(), object.getTo().getRawOffset(), object.getTo().getDSTSavings());
            } else {
                object = new int[2];
                this.getOffset(l, false, (int[])object);
                object = new InitialTimeZoneRule(this.getID(), object[0], object[1]);
            }
        }
        if (object2 == null) {
            object2 = new TimeZoneRule[]{(int)object};
            object = object2;
        } else {
            object = new TimeZoneRule[]{object, (TimeZoneRule)object2[0], (TimeZoneRule)object2[1]};
        }
        return object;
    }

    public abstract TimeZoneRule[] getTimeZoneRules();

    public TimeZoneRule[] getTimeZoneRules(long l) {
        int n;
        TimeZoneRule[] arrtimeZoneRule = this.getTimeZoneRules();
        Object object = this.getPreviousTransition(l, true);
        if (object == null) {
            return arrtimeZoneRule;
        }
        BitSet bitSet = new BitSet(arrtimeZoneRule.length);
        LinkedList<Object> linkedList = new LinkedList<Object>();
        object = new InitialTimeZoneRule(object.getTo().getName(), object.getTo().getRawOffset(), object.getTo().getDSTSavings());
        linkedList.add(object);
        boolean bl = false;
        bitSet.set(0);
        for (n = 1; n < arrtimeZoneRule.length; ++n) {
            if (arrtimeZoneRule[n].getNextStart(l, object.getRawOffset(), object.getDSTSavings(), false) != null) continue;
            bitSet.set(n);
        }
        long l2 = l;
        n = 0;
        boolean bl2 = false;
        do {
            long l3;
            int n2;
            block21 : {
                long[] arrl;
                long[] arrl2;
                if (n != 0 && bl2 || (arrl2 = this.getNextTransition(l2, bl)) == null) {
                    return linkedList.toArray(new TimeZoneRule[linkedList.size()]);
                }
                l2 = l3 = arrl2.getTime();
                object = arrl2.getTo();
                for (n2 = 1; n2 < arrtimeZoneRule.length && !arrtimeZoneRule[n2].equals(object); ++n2) {
                }
                if (n2 >= arrtimeZoneRule.length) break;
                if (bitSet.get(n2)) continue;
                if (object instanceof TimeArrayTimeZoneRule) {
                    object = (TimeArrayTimeZoneRule)object;
                    l2 = l;
                    do {
                        if ((arrl2 = this.getNextTransition(l2, bl)) == null || arrl2.getTo().equals(object)) {
                            if (arrl2 != null) {
                                if (object.getFirstStart(arrl2.getFrom().getRawOffset(), arrl2.getFrom().getDSTSavings()).getTime() > l) {
                                    linkedList.add(object);
                                } else {
                                    int n3;
                                    arrl = object.getStartTimes();
                                    int n4 = object.getTimeType();
                                    for (n3 = 0; n3 < arrl.length; ++n3) {
                                        l2 = arrl[n3];
                                        if (n4 == 1) {
                                            l2 -= (long)arrl2.getFrom().getRawOffset();
                                        }
                                        long l4 = l2;
                                        if (n4 == 0) {
                                            l4 = l2 - (long)arrl2.getFrom().getDSTSavings();
                                        }
                                        if (l4 > l) break;
                                    }
                                    if ((n4 = arrl.length - n3) > 0) {
                                        arrl2 = new long[n4];
                                        System.arraycopy(arrl, n3, arrl2, 0, n4);
                                        linkedList.add(new TimeArrayTimeZoneRule(object.getName(), object.getRawOffset(), object.getDSTSavings(), arrl2, object.getTimeType()));
                                    }
                                }
                            }
                            break block21;
                        }
                        l2 = arrl2.getTime();
                        bl = false;
                    } while (true);
                }
                if (object instanceof AnnualTimeZoneRule) {
                    if ((object = (AnnualTimeZoneRule)object).getFirstStart(arrl2.getFrom().getRawOffset(), arrl2.getFrom().getDSTSavings()).getTime() == arrl2.getTime()) {
                        linkedList.add(object);
                    } else {
                        int[] arrn = new int[6];
                        arrl = object;
                        Grego.timeToFields(arrl2.getTime(), arrn);
                        linkedList.add(new AnnualTimeZoneRule(arrl.getName(), arrl.getRawOffset(), arrl.getDSTSavings(), arrl.getRule(), arrn[0], arrl.getEndYear()));
                    }
                    if (object.getEndYear() == Integer.MAX_VALUE) {
                        if (object.getDSTSavings() == 0) {
                            n = 1;
                        } else {
                            bl2 = true;
                        }
                    }
                }
            }
            bitSet.set(n2);
            bl = false;
            l2 = l3;
        } while (true);
        throw new IllegalStateException("The rule was not found");
    }

    public boolean hasEquivalentTransitions(TimeZone timeZone, long l, long l2) {
        return this.hasEquivalentTransitions(timeZone, l, l2, false);
    }

    public boolean hasEquivalentTransitions(TimeZone timeZone, long l, long l2, boolean bl) {
        if (this == timeZone) {
            return true;
        }
        if (!(timeZone instanceof BasicTimeZone)) {
            return false;
        }
        Object object = new int[2];
        Object object2 = new int[2];
        this.getOffset(l, false, (int[])object);
        timeZone.getOffset(l, false, (int[])object2);
        if (bl ? object[0] + object[1] != object2[0] + object2[1] || object[1] != 0 && object2[1] == 0 || object[1] == 0 && object2[1] != 0 : object[0] != object2[0] || object[1] != object2[1]) {
            return false;
        }
        do {
            object2 = this.getNextTransition(l, false);
            TimeZoneTransition timeZoneTransition = ((BasicTimeZone)timeZone).getNextTransition(l, false);
            Object object3 = object2;
            Object object4 = timeZoneTransition;
            if (bl) {
                do {
                    object = timeZoneTransition;
                    if (object2 == null) break;
                    object = timeZoneTransition;
                    if (((TimeZoneTransition)object2).getTime() > l2) break;
                    object = timeZoneTransition;
                    if (((TimeZoneTransition)object2).getFrom().getRawOffset() + ((TimeZoneTransition)object2).getFrom().getDSTSavings() != ((TimeZoneTransition)object2).getTo().getRawOffset() + ((TimeZoneTransition)object2).getTo().getDSTSavings()) break;
                    object = timeZoneTransition;
                    if (((TimeZoneTransition)object2).getFrom().getDSTSavings() == 0) break;
                    object = timeZoneTransition;
                    if (((TimeZoneTransition)object2).getTo().getDSTSavings() == 0) break;
                    object2 = this.getNextTransition(((TimeZoneTransition)object2).getTime(), false);
                } while (true);
                do {
                    object3 = object2;
                    object4 = object;
                    if (object == null) break;
                    object3 = object2;
                    object4 = object;
                    if (((TimeZoneTransition)object).getTime() > l2) break;
                    object3 = object2;
                    object4 = object;
                    if (((TimeZoneTransition)object).getFrom().getRawOffset() + ((TimeZoneTransition)object).getFrom().getDSTSavings() != ((TimeZoneTransition)object).getTo().getRawOffset() + ((TimeZoneTransition)object).getTo().getDSTSavings()) break;
                    object3 = object2;
                    object4 = object;
                    if (((TimeZoneTransition)object).getFrom().getDSTSavings() == 0) break;
                    object3 = object2;
                    object4 = object;
                    if (((TimeZoneTransition)object).getTo().getDSTSavings() == 0) break;
                    object = ((BasicTimeZone)timeZone).getNextTransition(((TimeZoneTransition)object).getTime(), false);
                } while (true);
            }
            boolean bl2 = false;
            boolean bl3 = false;
            boolean bl4 = bl2;
            if (object3 != null) {
                bl4 = bl2;
                if (((TimeZoneTransition)object3).getTime() <= l2) {
                    bl4 = true;
                }
            }
            bl2 = bl3;
            if (object4 != null) {
                bl2 = bl3;
                if (((TimeZoneTransition)object4).getTime() <= l2) {
                    bl2 = true;
                }
            }
            if (!bl4 && !bl2) {
                return true;
            }
            if (!bl4 || !bl2) break;
            if (((TimeZoneTransition)object3).getTime() != ((TimeZoneTransition)object4).getTime()) {
                return false;
            }
            if (bl ? ((TimeZoneTransition)object3).getTo().getRawOffset() + ((TimeZoneTransition)object3).getTo().getDSTSavings() != ((TimeZoneTransition)object4).getTo().getRawOffset() + ((TimeZoneTransition)object4).getTo().getDSTSavings() || ((TimeZoneTransition)object3).getTo().getDSTSavings() != 0 && ((TimeZoneTransition)object4).getTo().getDSTSavings() == 0 || ((TimeZoneTransition)object3).getTo().getDSTSavings() == 0 && ((TimeZoneTransition)object4).getTo().getDSTSavings() != 0 : ((TimeZoneTransition)object3).getTo().getRawOffset() != ((TimeZoneTransition)object4).getTo().getRawOffset() || ((TimeZoneTransition)object3).getTo().getDSTSavings() != ((TimeZoneTransition)object4).getTo().getDSTSavings()) {
                return false;
            }
            l = ((TimeZoneTransition)object3).getTime();
        } while (true);
        return false;
    }
}

