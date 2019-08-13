/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.Grego;
import android.icu.util.AnnualTimeZoneRule;
import android.icu.util.BasicTimeZone;
import android.icu.util.DateTimeRule;
import android.icu.util.GregorianCalendar;
import android.icu.util.InitialTimeZoneRule;
import android.icu.util.STZInfo;
import android.icu.util.TimeZone;
import android.icu.util.TimeZoneRule;
import android.icu.util.TimeZoneTransition;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;

public class SimpleTimeZone
extends BasicTimeZone {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int DOM_MODE = 1;
    private static final int DOW_GE_DOM_MODE = 3;
    private static final int DOW_IN_MONTH_MODE = 2;
    private static final int DOW_LE_DOM_MODE = 4;
    public static final int STANDARD_TIME = 1;
    public static final int UTC_TIME = 2;
    public static final int WALL_TIME = 0;
    private static final long serialVersionUID = -7034676239311322769L;
    private static final byte[] staticMonthLength = new byte[]{31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private int dst = 3600000;
    private transient AnnualTimeZoneRule dstRule;
    private int endDay;
    private int endDayOfWeek;
    private int endMode;
    private int endMonth;
    private int endTime;
    private int endTimeMode;
    private transient TimeZoneTransition firstTransition;
    private transient InitialTimeZoneRule initialRule;
    private volatile transient boolean isFrozen = false;
    private int raw;
    private int startDay;
    private int startDayOfWeek;
    private int startMode;
    private int startMonth;
    private int startTime;
    private int startTimeMode;
    private int startYear;
    private transient AnnualTimeZoneRule stdRule;
    private transient boolean transitionRulesInitialized;
    private boolean useDaylight;
    private STZInfo xinfo = null;

    public SimpleTimeZone(int n, String string) {
        super(string);
        this.construct(n, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3600000);
    }

    public SimpleTimeZone(int n, String string, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        super(string);
        this.construct(n, n2, n3, n4, n5, 0, n6, n7, n8, n9, 0, 3600000);
    }

    public SimpleTimeZone(int n, String string, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
        super(string);
        this.construct(n, n2, n3, n4, n5, 0, n6, n7, n8, n9, 0, n10);
    }

    public SimpleTimeZone(int n, String string, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12) {
        super(string);
        this.construct(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12);
    }

    /*
     * Exception decompiling
     */
    private int compareToRule(int var1_1, int var2_2, int var3_3, int var4_4, int var5_5, int var6_6, int var7_7, int var8_8, int var9_9, int var10_10, int var11_11, int var12_12) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: CONTINUE without a while class org.benf.cfr.reader.bytecode.analysis.parse.statement.AssignmentSimple
        // org.benf.cfr.reader.bytecode.analysis.parse.statement.GotoStatement.getTargetStartBlock(GotoStatement.java:87)
        // org.benf.cfr.reader.bytecode.analysis.parse.statement.IfStatement.getStructuredStatement(IfStatement.java:103)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.getStructuredStatementPlaceHolder(Op03SimpleStatement.java:503)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:598)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private void construct(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12) {
        this.raw = n;
        this.startMonth = n2;
        this.startDay = n3;
        this.startDayOfWeek = n4;
        this.startTime = n5;
        this.startTimeMode = n6;
        this.endMonth = n7;
        this.endDay = n8;
        this.endDayOfWeek = n9;
        this.endTime = n10;
        this.endTimeMode = n11;
        this.dst = n12;
        this.startYear = 0;
        this.startMode = 1;
        this.endMode = 1;
        this.decodeRules();
        if (n12 != 0) {
            return;
        }
        throw new IllegalArgumentException();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void decodeEndRule() {
        int n;
        boolean bl = this.startDay != 0 && this.endDay != 0;
        this.useDaylight = bl;
        if (this.useDaylight && this.dst == 0) {
            this.dst = 86400000;
        }
        if ((n = this.endDay) == 0) return;
        int n2 = this.endMonth;
        if (n2 < 0 || n2 > 11) throw new IllegalArgumentException();
        n2 = this.endTime;
        if (n2 < 0 || n2 > 86400000 || (n2 = this.endTimeMode) < 0 || n2 > 2) throw new IllegalArgumentException();
        n2 = this.endDayOfWeek;
        if (n2 == 0) {
            this.endMode = 1;
        } else {
            if (n2 > 0) {
                this.endMode = 2;
            } else {
                this.endDayOfWeek = -n2;
                if (n > 0) {
                    this.endMode = 3;
                } else {
                    this.endDay = -n;
                    this.endMode = 4;
                }
            }
            if (this.endDayOfWeek > 7) throw new IllegalArgumentException();
        }
        if (this.endMode == 2) {
            n = this.endDay;
            if (n >= -5 && n <= 5) return;
            throw new IllegalArgumentException();
        }
        n = this.endDay;
        if (n >= 1 && n <= staticMonthLength[this.endMonth]) return;
        throw new IllegalArgumentException();
    }

    private void decodeRules() {
        this.decodeStartRule();
        this.decodeEndRule();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void decodeStartRule() {
        int n;
        boolean bl = this.startDay != 0 && this.endDay != 0;
        this.useDaylight = bl;
        if (this.useDaylight && this.dst == 0) {
            this.dst = 86400000;
        }
        if ((n = this.startDay) == 0) return;
        int n2 = this.startMonth;
        if (n2 < 0 || n2 > 11) throw new IllegalArgumentException();
        n2 = this.startTime;
        if (n2 < 0 || n2 > 86400000 || (n2 = this.startTimeMode) < 0 || n2 > 2) throw new IllegalArgumentException();
        n2 = this.startDayOfWeek;
        if (n2 == 0) {
            this.startMode = 1;
        } else {
            if (n2 > 0) {
                this.startMode = 2;
            } else {
                this.startDayOfWeek = -n2;
                if (n > 0) {
                    this.startMode = 3;
                } else {
                    this.startDay = -n;
                    this.startMode = 4;
                }
            }
            if (this.startDayOfWeek > 7) throw new IllegalArgumentException();
        }
        if (this.startMode == 2) {
            n = this.startDay;
            if (n >= -5 && n <= 5) return;
            throw new IllegalArgumentException();
        }
        n = this.startDay;
        if (n >= 1 && n <= staticMonthLength[this.startMonth]) return;
        throw new IllegalArgumentException();
    }

    private int getOffset(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        block4 : {
            int n9;
            block5 : {
                block7 : {
                    block6 : {
                        if (n != 1 && n != 0 || n3 < 0 || n3 > 11 || n4 < 1 || n4 > n7 || n5 < 1 || n5 > 7 || n6 < 0 || n6 >= 86400000 || n7 < 28 || n7 > 31 || n8 < 28 || n8 > 31) break block4;
                        n9 = this.raw;
                        if (!this.useDaylight || n2 < this.startYear || n != 1) break block5;
                        n2 = this.startMonth > this.endMonth ? 1 : 0;
                        n = this.startTimeMode == 2 ? -this.raw : 0;
                        int n10 = this.startMode;
                        int n11 = this.startMonth;
                        int n12 = this.startDayOfWeek;
                        int n13 = this.startDay;
                        int n14 = this.startTime;
                        n12 = this.compareToRule(n3, n7, n8, n4, n5, n6, n, n10, n11, n12, n13, n14);
                        n13 = 0;
                        n = n12 >= 0 ? 1 : 0;
                        if (n2 != n) {
                            n = this.endTimeMode;
                            n = n == 0 ? this.dst : (n == 2 ? -this.raw : 0);
                            n13 = this.compareToRule(n3, n7, n8, n4, n5, n6, n, this.endMode, this.endMonth, this.endDayOfWeek, this.endDay, this.endTime);
                        }
                        if (n2 == 0 && n12 >= 0 && n13 < 0) break block6;
                        n = n9;
                        if (n2 == 0) break block7;
                        if (n12 >= 0) break block6;
                        n = n9;
                        if (n13 >= 0) break block7;
                    }
                    n = n9 + this.dst;
                }
                return n;
            }
            return n9;
        }
        throw new IllegalArgumentException();
    }

    private STZInfo getSTZInfo() {
        if (this.xinfo == null) {
            this.xinfo = new STZInfo();
        }
        return this.xinfo;
    }

    private boolean idEquals(String string, String string2) {
        if (string == null && string2 == null) {
            return true;
        }
        if (string != null && string2 != null) {
            return string.equals(string2);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void initTransitionRules() {
        synchronized (this) {
            boolean bl = this.transitionRulesInitialized;
            if (bl) {
                return;
            }
            if (this.useDaylight) {
                Object object = null;
                int n = this.startTimeMode == 1 ? 1 : (this.startTimeMode == 2 ? 2 : 0);
                int n2 = this.startMode;
                if (n2 != 1) {
                    if (n2 != 2) {
                        if (n2 != 3) {
                            if (n2 == 4) {
                                object = new DateTimeRule(this.startMonth, this.startDay, this.startDayOfWeek, false, this.startTime, n);
                            }
                        } else {
                            object = new DateTimeRule(this.startMonth, this.startDay, this.startDayOfWeek, true, this.startTime, n);
                        }
                    } else {
                        object = new DateTimeRule(this.startMonth, this.startDay, this.startDayOfWeek, this.startTime, n);
                    }
                } else {
                    object = new DateTimeRule(this.startMonth, this.startDay, this.startTime, n);
                }
                Serializable serializable = new StringBuilder();
                ((StringBuilder)serializable).append(this.getID());
                ((StringBuilder)serializable).append("(DST)");
                Serializable serializable2 = new AnnualTimeZoneRule(((StringBuilder)serializable).toString(), this.getRawOffset(), this.getDSTSavings(), (DateTimeRule)object, this.startYear, Integer.MAX_VALUE);
                this.dstRule = serializable2;
                long l = this.dstRule.getFirstStart(this.getRawOffset(), 0).getTime();
                n = this.endTimeMode == 1 ? 1 : (this.endTimeMode == 2 ? 2 : 0);
                n2 = this.endMode;
                if (n2 != 1) {
                    if (n2 != 2) {
                        if (n2 != 3) {
                            if (n2 == 4) {
                                object = new DateTimeRule(this.endMonth, this.endDay, this.endDayOfWeek, false, this.endTime, n);
                            }
                        } else {
                            object = new DateTimeRule(this.endMonth, this.endDay, this.endDayOfWeek, true, this.endTime, n);
                        }
                    } else {
                        object = new DateTimeRule(this.endMonth, this.endDay, this.endDayOfWeek, this.endTime, n);
                    }
                } else {
                    object = new DateTimeRule(this.endMonth, this.endDay, this.endTime, n);
                }
                serializable2 = new StringBuilder();
                ((StringBuilder)serializable2).append(this.getID());
                ((StringBuilder)serializable2).append("(STD)");
                serializable = new AnnualTimeZoneRule(((StringBuilder)serializable2).toString(), this.getRawOffset(), 0, (DateTimeRule)object, this.startYear, Integer.MAX_VALUE);
                this.stdRule = serializable;
                long l2 = this.stdRule.getFirstStart(this.getRawOffset(), this.dstRule.getDSTSavings()).getTime();
                if (l2 < l) {
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append(this.getID());
                    ((StringBuilder)serializable).append("(DST)");
                    object = new InitialTimeZoneRule(((StringBuilder)serializable).toString(), this.getRawOffset(), this.dstRule.getDSTSavings());
                    this.initialRule = object;
                    this.firstTransition = object = new TimeZoneTransition(l2, this.initialRule, this.stdRule);
                } else {
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append(this.getID());
                    ((StringBuilder)serializable).append("(STD)");
                    object = new InitialTimeZoneRule(((StringBuilder)serializable).toString(), this.getRawOffset(), 0);
                    this.initialRule = object;
                    this.firstTransition = object = new TimeZoneTransition(l, this.initialRule, this.dstRule);
                }
            } else {
                InitialTimeZoneRule initialTimeZoneRule;
                this.initialRule = initialTimeZoneRule = new InitialTimeZoneRule(this.getID(), this.getRawOffset(), 0);
            }
            this.transitionRulesInitialized = true;
            return;
        }
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        ((ObjectInputStream)object).defaultReadObject();
        object = this.xinfo;
        if (object != null) {
            ((STZInfo)object).applyTo(this);
        }
    }

    private void setEndRule(int n, int n2, int n3, int n4, int n5) {
        this.endMonth = n;
        this.endDay = n2;
        this.endDayOfWeek = n3;
        this.endTime = n4;
        this.endTimeMode = n5;
        this.decodeEndRule();
        this.transitionRulesInitialized = false;
    }

    private void setEndRule(int n, int n2, int n3, int n4, int n5, boolean bl) {
        if (!bl) {
            n2 = -n2;
        }
        this.setEndRule(n, n2, -n3, n4, n5);
    }

    private void setStartRule(int n, int n2, int n3, int n4, int n5) {
        this.startMonth = n;
        this.startDay = n2;
        this.startDayOfWeek = n3;
        this.startTime = n4;
        this.startTimeMode = n5;
        this.decodeStartRule();
        this.transitionRulesInitialized = false;
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
        SimpleTimeZone simpleTimeZone = (SimpleTimeZone)super.cloneAsThawed();
        simpleTimeZone.isFrozen = false;
        return simpleTimeZone;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (SimpleTimeZone)object;
            if (this.raw != ((SimpleTimeZone)object).raw || this.useDaylight != ((SimpleTimeZone)object).useDaylight || !this.idEquals(this.getID(), ((TimeZone)object).getID()) || this.useDaylight && (this.dst != ((SimpleTimeZone)object).dst || this.startMode != ((SimpleTimeZone)object).startMode || this.startMonth != ((SimpleTimeZone)object).startMonth || this.startDay != ((SimpleTimeZone)object).startDay || this.startDayOfWeek != ((SimpleTimeZone)object).startDayOfWeek || this.startTime != ((SimpleTimeZone)object).startTime || this.startTimeMode != ((SimpleTimeZone)object).startTimeMode || this.endMode != ((SimpleTimeZone)object).endMode || this.endMonth != ((SimpleTimeZone)object).endMonth || this.endDay != ((SimpleTimeZone)object).endDay || this.endDayOfWeek != ((SimpleTimeZone)object).endDayOfWeek || this.endTime != ((SimpleTimeZone)object).endTime || this.endTimeMode != ((SimpleTimeZone)object).endTimeMode || this.startYear != ((SimpleTimeZone)object).startYear)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public TimeZone freeze() {
        this.isFrozen = true;
        return this;
    }

    @Override
    public int getDSTSavings() {
        return this.dst;
    }

    @Override
    public TimeZoneTransition getNextTransition(long l, boolean bl) {
        if (!this.useDaylight) {
            return null;
        }
        this.initTransitionRules();
        long l2 = this.firstTransition.getTime();
        if (!(l < l2 || bl && l == l2)) {
            Date date = this.stdRule.getNextStart(l, this.dstRule.getRawOffset(), this.dstRule.getDSTSavings(), bl);
            Date date2 = this.dstRule.getNextStart(l, this.stdRule.getRawOffset(), this.stdRule.getDSTSavings(), bl);
            if (date != null && (date2 == null || date.before(date2))) {
                return new TimeZoneTransition(date.getTime(), this.dstRule, this.stdRule);
            }
            if (date2 != null && (date == null || date2.before(date))) {
                return new TimeZoneTransition(date2.getTime(), this.stdRule, this.dstRule);
            }
            return null;
        }
        return this.firstTransition;
    }

    @Override
    public int getOffset(int n, int n2, int n3, int n4, int n5, int n6) {
        if (n3 >= 0 && n3 <= 11) {
            return this.getOffset(n, n2, n3, n4, n5, n6, Grego.monthLength(n2, n3));
        }
        throw new IllegalArgumentException();
    }

    @Deprecated
    public int getOffset(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        if (n3 >= 0 && n3 <= 11) {
            return this.getOffset(n, n2, n3, n4, n5, n6, Grego.monthLength(n2, n3), Grego.previousMonthLength(n2, n3));
        }
        throw new IllegalArgumentException();
    }

    @Deprecated
    @Override
    public void getOffsetFromLocal(long l, int n, int n2, int[] arrn) {
        int[] arrn2;
        boolean bl;
        block9 : {
            long l2;
            block10 : {
                boolean bl2;
                block7 : {
                    block8 : {
                        l2 = l;
                        arrn[0] = this.getRawOffset();
                        arrn2 = new int[6];
                        Grego.timeToFields(l2, arrn2);
                        arrn[1] = this.getOffset(1, arrn2[0], arrn2[1], arrn2[2], arrn2[3], arrn2[5]) - arrn[0];
                        bl2 = false;
                        if (arrn[1] <= 0) break block7;
                        if ((n & 3) == 1) break block8;
                        l = l2;
                        bl = bl2;
                        if ((n & 3) == 3) break block9;
                        l = l2;
                        bl = bl2;
                        if ((n & 12) == 12) break block9;
                    }
                    l = l2 - (long)this.getDSTSavings();
                    bl = true;
                    break block9;
                }
                if ((n2 & 3) == 3) break block10;
                l = l2;
                bl = bl2;
                if ((n2 & 3) == 1) break block9;
                l = l2;
                bl = bl2;
                if ((n2 & 12) != 4) break block9;
            }
            l = l2 - (long)this.getDSTSavings();
            bl = true;
        }
        if (bl) {
            Grego.timeToFields(l, arrn2);
            arrn[1] = this.getOffset(1, arrn2[0], arrn2[1], arrn2[2], arrn2[3], arrn2[5]) - arrn[0];
        }
    }

    @Override
    public TimeZoneTransition getPreviousTransition(long l, boolean bl) {
        if (!this.useDaylight) {
            return null;
        }
        this.initTransitionRules();
        long l2 = this.firstTransition.getTime();
        if (l >= l2 && (bl || l != l2)) {
            Date date = this.stdRule.getPreviousStart(l, this.dstRule.getRawOffset(), this.dstRule.getDSTSavings(), bl);
            Date date2 = this.dstRule.getPreviousStart(l, this.stdRule.getRawOffset(), this.stdRule.getDSTSavings(), bl);
            if (date != null && (date2 == null || date.after(date2))) {
                return new TimeZoneTransition(date.getTime(), this.dstRule, this.stdRule);
            }
            if (date2 != null && (date == null || date2.after(date))) {
                return new TimeZoneTransition(date2.getTime(), this.stdRule, this.dstRule);
            }
            return null;
        }
        return null;
    }

    @Override
    public int getRawOffset() {
        return this.raw;
    }

    @Override
    public TimeZoneRule[] getTimeZoneRules() {
        this.initTransitionRules();
        int n = this.useDaylight ? 3 : 1;
        TimeZoneRule[] arrtimeZoneRule = new TimeZoneRule[n];
        arrtimeZoneRule[0] = this.initialRule;
        if (this.useDaylight) {
            arrtimeZoneRule[1] = this.stdRule;
            arrtimeZoneRule[2] = this.dstRule;
        }
        return arrtimeZoneRule;
    }

    @Override
    public boolean hasSameRules(TimeZone timeZone) {
        boolean bl;
        boolean bl2 = true;
        if (this == timeZone) {
            return true;
        }
        if (!(timeZone instanceof SimpleTimeZone)) {
            return false;
        }
        if ((timeZone = (SimpleTimeZone)timeZone) == null || this.raw != ((SimpleTimeZone)timeZone).raw || (bl = this.useDaylight) != ((SimpleTimeZone)timeZone).useDaylight || bl && (this.dst != ((SimpleTimeZone)timeZone).dst || this.startMode != ((SimpleTimeZone)timeZone).startMode || this.startMonth != ((SimpleTimeZone)timeZone).startMonth || this.startDay != ((SimpleTimeZone)timeZone).startDay || this.startDayOfWeek != ((SimpleTimeZone)timeZone).startDayOfWeek || this.startTime != ((SimpleTimeZone)timeZone).startTime || this.startTimeMode != ((SimpleTimeZone)timeZone).startTimeMode || this.endMode != ((SimpleTimeZone)timeZone).endMode || this.endMonth != ((SimpleTimeZone)timeZone).endMonth || this.endDay != ((SimpleTimeZone)timeZone).endDay || this.endDayOfWeek != ((SimpleTimeZone)timeZone).endDayOfWeek || this.endTime != ((SimpleTimeZone)timeZone).endTime || this.endTimeMode != ((SimpleTimeZone)timeZone).endTimeMode || this.startYear != ((SimpleTimeZone)timeZone).startYear)) {
            bl2 = false;
        }
        return bl2;
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        int n2 = this.raw;
        boolean bl = this.useDaylight;
        n2 = n = n + n2 ^ (n2 >>> 8) + (bl ^ true);
        if (!bl) {
            int n3 = this.dst;
            int n4 = this.startMode;
            int n5 = this.startMonth;
            int n6 = this.startDay;
            int n7 = this.startDayOfWeek;
            int n8 = this.startTime;
            int n9 = this.startTimeMode;
            int n10 = this.endMode;
            int n11 = this.endMonth;
            int n12 = this.endDay;
            int n13 = this.endDayOfWeek;
            int n14 = this.endTime;
            int n15 = this.endTimeMode;
            n2 = this.startYear;
            n2 = n + (n3 ^ (n3 >>> 10) + n4 ^ (n4 >>> 11) + n5 ^ (n5 >>> 12) + n6 ^ (n6 >>> 13) + n7 ^ (n7 >>> 14) + n8 ^ (n8 >>> 15) + n9 ^ (n9 >>> 16) + n10 ^ (n10 >>> 17) + n11 ^ (n11 >>> 18) + n12 ^ (n12 >>> 19) + n13 ^ (n13 >>> 20) + n14 ^ (n14 >>> 21) + n15 ^ (n15 >>> 22) + n2 ^ n2 >>> 23);
        }
        return n2;
    }

    @Override
    public boolean inDaylightTime(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(this);
        gregorianCalendar.setTime(date);
        return gregorianCalendar.inDaylightTime();
    }

    @Override
    public boolean isFrozen() {
        return this.isFrozen;
    }

    @Override
    public boolean observesDaylightTime() {
        return this.useDaylight;
    }

    public void setDSTSavings(int n) {
        if (!this.isFrozen()) {
            if (n != 0) {
                this.dst = n;
                this.transitionRulesInitialized = false;
                return;
            }
            throw new IllegalArgumentException();
        }
        throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
    }

    public void setEndRule(int n, int n2, int n3) {
        if (!this.isFrozen()) {
            this.getSTZInfo().setEnd(n, -1, -1, n3, n2, false);
            this.setEndRule(n, n2, 0, n3);
            return;
        }
        throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
    }

    public void setEndRule(int n, int n2, int n3, int n4) {
        if (!this.isFrozen()) {
            this.getSTZInfo().setEnd(n, n2, n3, n4, -1, false);
            this.setEndRule(n, n2, n3, n4, 0);
            return;
        }
        throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
    }

    public void setEndRule(int n, int n2, int n3, int n4, boolean bl) {
        if (!this.isFrozen()) {
            this.getSTZInfo().setEnd(n, -1, n3, n4, n2, bl);
            this.setEndRule(n, n2, n3, n4, 0, bl);
            return;
        }
        throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
    }

    @Override
    public void setID(String string) {
        if (!this.isFrozen()) {
            super.setID(string);
            this.transitionRulesInitialized = false;
            return;
        }
        throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
    }

    @Override
    public void setRawOffset(int n) {
        if (!this.isFrozen()) {
            this.raw = n;
            this.transitionRulesInitialized = false;
            return;
        }
        throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
    }

    public void setStartRule(int n, int n2, int n3) {
        if (!this.isFrozen()) {
            this.getSTZInfo().setStart(n, -1, -1, n3, n2, false);
            this.setStartRule(n, n2, 0, n3, 0);
            return;
        }
        throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
    }

    public void setStartRule(int n, int n2, int n3, int n4) {
        if (!this.isFrozen()) {
            this.getSTZInfo().setStart(n, n2, n3, n4, -1, false);
            this.setStartRule(n, n2, n3, n4, 0);
            return;
        }
        throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
    }

    public void setStartRule(int n, int n2, int n3, int n4, boolean bl) {
        if (!this.isFrozen()) {
            this.getSTZInfo().setStart(n, -1, n3, n4, n2, bl);
            if (!bl) {
                n2 = -n2;
            }
            this.setStartRule(n, n2, -n3, n4, 0);
            return;
        }
        throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
    }

    public void setStartYear(int n) {
        if (!this.isFrozen()) {
            this.getSTZInfo().sy = n;
            this.startYear = n;
            this.transitionRulesInitialized = false;
            return;
        }
        throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SimpleTimeZone: ");
        stringBuilder.append(this.getID());
        return stringBuilder.toString();
    }

    @Override
    public boolean useDaylightTime() {
        return this.useDaylight;
    }
}

