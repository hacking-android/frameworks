/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.Grego;
import android.icu.impl.ICUDebug;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.Utility;
import android.icu.impl.ZoneMeta;
import android.icu.util.AnnualTimeZoneRule;
import android.icu.util.BasicTimeZone;
import android.icu.util.DateTimeRule;
import android.icu.util.InitialTimeZoneRule;
import android.icu.util.SimpleTimeZone;
import android.icu.util.TimeArrayTimeZoneRule;
import android.icu.util.TimeZone;
import android.icu.util.TimeZoneRule;
import android.icu.util.TimeZoneTransition;
import android.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Date;
import java.util.MissingResourceException;

public class OlsonTimeZone
extends BasicTimeZone {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean DEBUG = ICUDebug.enabled("olson");
    private static final int MAX_OFFSET_SECONDS = 86400;
    private static final int SECONDS_PER_DAY = 86400;
    private static final String ZONEINFORES = "zoneinfo64";
    private static final int currentSerialVersion = 1;
    static final long serialVersionUID = -6281977362477515376L;
    private volatile String canonicalID = null;
    private double finalStartMillis = Double.MAX_VALUE;
    private int finalStartYear = Integer.MAX_VALUE;
    private SimpleTimeZone finalZone = null;
    private transient SimpleTimeZone finalZoneWithStartYear;
    private transient TimeZoneTransition firstFinalTZTransition;
    private transient TimeZoneTransition firstTZTransition;
    private transient int firstTZTransitionIdx;
    private transient TimeArrayTimeZoneRule[] historicRules;
    private transient InitialTimeZoneRule initialRule;
    private volatile transient boolean isFrozen = false;
    private int serialVersionOnStream = 1;
    private int transitionCount;
    private transient boolean transitionRulesInitialized;
    private long[] transitionTimes64;
    private int typeCount;
    private byte[] typeMapData;
    private int[] typeOffsets;

    public OlsonTimeZone(UResourceBundle uResourceBundle, UResourceBundle uResourceBundle2, String string) {
        super(string);
        this.construct(uResourceBundle, uResourceBundle2);
    }

    public OlsonTimeZone(String string) {
        super(string);
        Object object = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", ZONEINFORES, ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        this.construct((UResourceBundle)object, ZoneMeta.openOlsonResource((UResourceBundle)object, string));
        object = this.finalZone;
        if (object != null) {
            ((SimpleTimeZone)object).setID(string);
        }
    }

    private void construct(UResourceBundle object, UResourceBundle uResourceBundle) {
        long[] arrl = "Invalid Format";
        if (object != null && uResourceBundle != null) {
            Object[] arrobject;
            IllegalArgumentException illegalArgumentException;
            Object object2;
            int[] arrn;
            Object object3;
            block46 : {
                block45 : {
                    if (DEBUG) {
                        arrobject = System.out;
                        object3 = new StringBuilder();
                        object3.append("OlsonTimeZone(");
                        object3.append(uResourceBundle.getKey());
                        object3.append(")");
                        arrobject.println(object3.toString());
                    }
                    object2 = null;
                    object3 = null;
                    arrobject = null;
                    this.transitionCount = 0;
                    arrn = uResourceBundle.get("transPre32").getIntVector();
                    arrobject = arrn;
                    if (arrn.length % 2 != 0) break block45;
                    arrobject = arrn;
                    this.transitionCount += arrn.length / 2;
                }
                arrobject = arrn;
                arrobject = arrn;
                illegalArgumentException = new IllegalArgumentException("Invalid Format");
                arrobject = arrn;
                try {
                    throw illegalArgumentException;
                }
                catch (MissingResourceException missingResourceException) {
                    arrn = arrobject;
                }
                arrobject = object3;
                object3 = uResourceBundle.get("trans").getIntVector();
                arrobject = object3;
                try {
                    this.transitionCount += ((int[])object3).length;
                    arrobject = object3;
                }
                catch (MissingResourceException missingResourceException) {
                    // empty catch block
                }
                object3 = object2;
                object3 = object2 = uResourceBundle.get("transPost32").getIntVector();
                if (((int[])object2).length % 2 != 0) break block46;
                object3 = object2;
                this.transitionCount += ((int[])object2).length / 2;
                object3 = object2;
            }
            object3 = object2;
            object3 = object2;
            illegalArgumentException = new IllegalArgumentException("Invalid Format");
            object3 = object2;
            try {
                throw illegalArgumentException;
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            int n = this.transitionCount;
            if (n > 0) {
                long l;
                this.transitionTimes64 = new long[n];
                int n2 = 0;
                n = 0;
                if (arrn != null) {
                    n2 = 0;
                    object2 = arrl;
                    while (n2 < arrn.length / 2) {
                        arrl = this.transitionTimes64;
                        l = arrn[n2 * 2];
                        arrl[n] = (long)arrn[n2 * 2 + 1] & 0xFFFFFFFFL | (l & 0xFFFFFFFFL) << 32;
                        ++n2;
                        ++n;
                    }
                } else {
                    object2 = "Invalid Format";
                    n = n2;
                }
                n2 = n;
                if (arrobject != null) {
                    int n3 = 0;
                    do {
                        n2 = n++;
                        if (n3 >= arrobject.length) break;
                        this.transitionTimes64[n] = (long)arrobject[n3];
                        ++n3;
                    } while (true);
                }
                if (object3 != null) {
                    n = 0;
                    while (n < ((int[])object3).length / 2) {
                        arrn = this.transitionTimes64;
                        l = object3[n * 2];
                        arrn[n2] = (int)((long)object3[n * 2 + 1] & 0xFFFFFFFFL | (l & 0xFFFFFFFFL) << 32);
                        ++n;
                        ++n2;
                    }
                }
            } else {
                object2 = "Invalid Format";
                this.transitionTimes64 = null;
            }
            this.typeOffsets = uResourceBundle.get("typeOffsets").getIntVector();
            arrobject = this.typeOffsets;
            if (arrobject.length >= 2 && arrobject.length <= 32766 && arrobject.length % 2 == 0) {
                block48 : {
                    block47 : {
                        this.typeCount = arrobject.length / 2;
                        if (this.transitionCount > 0) {
                            this.typeMapData = uResourceBundle.get("typeMap").getBinary(null);
                            arrobject = this.typeMapData;
                            if (arrobject == null || arrobject.length != this.transitionCount) {
                                throw new IllegalArgumentException((String)object2);
                            }
                        } else {
                            this.typeMapData = null;
                        }
                        this.finalZone = null;
                        this.finalStartYear = Integer.MAX_VALUE;
                        this.finalStartMillis = Double.MAX_VALUE;
                        arrobject = null;
                        object3 = uResourceBundle.getString("finalRule");
                        arrobject = object3;
                        n = uResourceBundle.get("finalRaw").getInt();
                        arrobject = object3;
                        arrn = OlsonTimeZone.loadRule((UResourceBundle)object, (String)object3).getIntVector();
                        if (arrn == null) break block47;
                        arrobject = object3;
                        if (arrn.length != 11) break block47;
                        arrobject = object3;
                        arrobject = object3;
                        object = new SimpleTimeZone(n * 1000, "", arrn[0], arrn[1], arrn[2], arrn[3] * 1000, arrn[4], arrn[5], arrn[6], arrn[7], arrn[8] * 1000, arrn[9], arrn[10] * 1000);
                        arrobject = object3;
                        this.finalZone = object;
                        arrobject = object3;
                        this.finalStartYear = uResourceBundle.get("finalYear").getInt();
                        arrobject = object3;
                        this.finalStartMillis = Grego.fieldsToDay(this.finalStartYear, 0, 1) * 86400000L;
                    }
                    arrobject = object3;
                    arrobject = object3;
                    object = new IllegalArgumentException((String)object2);
                    arrobject = object3;
                    try {
                        throw object;
                    }
                    catch (MissingResourceException missingResourceException) {
                        if (arrobject != null) break block48;
                    }
                    return;
                }
                throw new IllegalArgumentException((String)object2);
            }
            throw new IllegalArgumentException((String)object2);
        }
        throw new IllegalArgumentException();
    }

    private void constructEmpty() {
        this.transitionCount = 0;
        this.transitionTimes64 = null;
        this.typeMapData = null;
        this.typeCount = 1;
        this.typeOffsets = new int[]{0, 0};
        this.finalZone = null;
        this.finalStartYear = Integer.MAX_VALUE;
        this.finalStartMillis = Double.MAX_VALUE;
        this.transitionRulesInitialized = false;
    }

    private int dstOffsetAt(int n) {
        n = n >= 0 ? this.getInt(this.typeMapData[n]) * 2 : 0;
        return this.typeOffsets[n + 1];
    }

    private void getHistoricalOffset(long l, boolean bl, int n, int n2, int[] arrn) {
        if (this.transitionCount != 0) {
            long l2 = Grego.floorDivide(l, 1000L);
            if (!bl && l2 < this.transitionTimes64[0]) {
                arrn[0] = this.initialRawOffset() * 1000;
                arrn[1] = this.initialDstOffset() * 1000;
            } else {
                int n3;
                for (n3 = this.transitionCount - 1; n3 >= 0; --n3) {
                    long l3;
                    l = l3 = this.transitionTimes64[n3];
                    if (bl) {
                        l = l3;
                        if (l2 >= l3 - 86400L) {
                            int n4 = this.zoneOffsetAt(n3 - 1);
                            boolean bl2 = this.dstOffsetAt(n3 - 1) != 0;
                            int n5 = this.zoneOffsetAt(n3);
                            boolean bl3 = this.dstOffsetAt(n3) != 0;
                            boolean bl4 = bl2 && !bl3;
                            bl2 = !bl2 && bl3;
                            l = n5 - n4 >= 0 ? ((n & 3) == 1 && bl4 || (n & 3) == 3 && bl2 ? l3 + (long)n4 : ((n & 3) == 1 && bl2 || (n & 3) == 3 && bl4 ? l3 + (long)n5 : ((n & 12) == 12 ? l3 + (long)n4 : l3 + (long)n5))) : ((n2 & 3) == 1 && bl4 || (n2 & 3) == 3 && bl2 ? l3 + (long)n5 : ((n2 & 3) == 1 && bl2 || (n2 & 3) == 3 && bl4 ? l3 + (long)n4 : ((n2 & 12) == 4 ? l3 + (long)n4 : l3 + (long)n5)));
                        }
                    }
                    if (l2 >= l) break;
                }
                arrn[0] = this.rawOffsetAt(n3) * 1000;
                arrn[1] = this.dstOffsetAt(n3) * 1000;
            }
        } else {
            arrn[0] = this.initialRawOffset() * 1000;
            arrn[1] = this.initialDstOffset() * 1000;
        }
    }

    private int getInt(byte by) {
        return by & 255;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void initTransitionRules() {
        // MONITORENTER : this
        var1_1 = this.transitionRulesInitialized;
        if (var1_1) {
            // MONITOREXIT : this
            return;
        }
        this.initialRule = null;
        this.firstTZTransition = null;
        this.firstFinalTZTransition = null;
        this.historicRules = null;
        this.firstTZTransitionIdx = 0;
        this.finalZoneWithStartYear = null;
        var2_2 = new StringBuilder();
        var2_2.append(this.getID());
        var2_2.append("(STD)");
        var3_3 = var2_2.toString();
        var2_2 = new StringBuilder();
        var2_2.append(this.getID());
        var2_2.append("(DST)");
        var2_2 = var2_2.toString();
        var4_4 = this.initialRawOffset();
        var5_5 = this.initialDstOffset() * 1000;
        var7_7 = var5_5 == 0 ? var3_3 : var2_2;
        var6_6 = new InitialTimeZoneRule((String)var7_7, var4_4 * 1000, var5_5);
        this.initialRule = var6_6;
        if (this.transitionCount <= 0) ** GOTO lbl41
        for (var5_5 = 0; var5_5 < this.transitionCount && this.getInt(this.typeMapData[var5_5]) == 0; ++this.firstTZTransitionIdx, ++var5_5) {
        }
        if (var5_5 == this.transitionCount) ** GOTO lbl41
        var6_6 = new long[this.transitionCount];
        var5_5 = 0;
        do {
            if (var5_5 < this.typeCount) {
                var8_8 = 0;
            } else {
                var5_5 = this.getInt(this.typeMapData[this.firstTZTransitionIdx]);
                this.firstTZTransition = var2_2 = new TimeZoneTransition(this.transitionTimes64[this.firstTZTransitionIdx] * 1000L, this.initialRule, this.historicRules[var5_5]);
lbl41: // 3 sources:
                if (this.finalZone != null) {
                    var10_10 = (long)this.finalStartMillis;
                    if (this.finalZone.useDaylightTime()) {
                        this.finalZoneWithStartYear = (SimpleTimeZone)this.finalZone.clone();
                        this.finalZoneWithStartYear.setStartYear(this.finalStartYear);
                        var2_2 = this.finalZoneWithStartYear.getNextTransition(var10_10, false);
                        var3_3 = var2_2.getTo();
                        var10_10 = var2_2.getTime();
                    } else {
                        this.finalZoneWithStartYear = this.finalZone;
                        var3_3 = new TimeArrayTimeZoneRule(this.finalZone.getID(), this.finalZone.getRawOffset(), 0, new long[]{var10_10}, 2);
                    }
                    var2_2 = null;
                    if (this.transitionCount > 0) {
                        var2_2 = this.historicRules[this.getInt(this.typeMapData[this.transitionCount - 1])];
                    }
                    var7_7 = var2_2;
                    if (var2_2 == null) {
                        var7_7 = this.initialRule;
                    }
                    this.firstFinalTZTransition = var2_2 = new TimeZoneTransition(var10_10, (TimeZoneRule)var7_7, (TimeZoneRule)var3_3);
                }
                this.transitionRulesInitialized = true;
                // MONITOREXIT : this
                return;
            }
            for (var9_9 = this.firstTZTransitionIdx; var9_9 < this.transitionCount; ++var9_9) {
                if (var5_5 == this.getInt(this.typeMapData[var9_9])) {
                    var10_10 = this.transitionTimes64[var9_9] * 1000L;
                    var4_4 = var8_8;
                    if ((double)var10_10 < this.finalStartMillis) {
                        var6_6[var8_8] = var10_10;
                        var4_4 = var8_8 + 1;
                    }
                } else {
                    var4_4 = var8_8;
                }
                var8_8 = var4_4;
            }
            if (var8_8 > 0) {
                var12_11 = new long[var8_8];
                System.arraycopy(var6_6, 0, var12_11, 0, var8_8);
                var4_4 = this.typeOffsets[var5_5 * 2];
                var9_9 = this.typeOffsets[var5_5 * 2 + 1] * 1000;
                if (this.historicRules == null) {
                    this.historicRules = new TimeArrayTimeZoneRule[this.typeCount];
                }
                var13_12 = this.historicRules;
                var7_7 = var9_9 == 0 ? var3_3 : var2_2;
                var13_12[var5_5] = new TimeArrayTimeZoneRule((String)var7_7, var4_4 * 1000, var9_9, var12_11, 2);
            }
            ++var5_5;
        } while (true);
    }

    private int initialDstOffset() {
        return this.typeOffsets[1];
    }

    private int initialRawOffset() {
        return this.typeOffsets[0];
    }

    private static UResourceBundle loadRule(UResourceBundle uResourceBundle, String string) {
        return uResourceBundle.get("Rules").get(string);
    }

    private int rawOffsetAt(int n) {
        n = n >= 0 ? this.getInt(this.typeMapData[n]) * 2 : 0;
        return this.typeOffsets[n];
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        ((ObjectInputStream)object).defaultReadObject();
        if (this.serialVersionOnStream < 1) {
            boolean bl = false;
            object = this.getID();
            boolean bl2 = bl;
            if (object != null) {
                try {
                    UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", ZONEINFORES, ICUResourceBundle.ICU_DATA_CLASS_LOADER);
                    this.construct(uResourceBundle, ZoneMeta.openOlsonResource(uResourceBundle, (String)object));
                    if (this.finalZone != null) {
                        this.finalZone.setID((String)object);
                    }
                    bl2 = true;
                }
                catch (Exception exception) {
                    bl2 = bl;
                }
            }
            if (!bl2) {
                this.constructEmpty();
            }
        }
        this.transitionRulesInitialized = false;
    }

    private int zoneOffsetAt(int n) {
        n = n >= 0 ? this.getInt(this.typeMapData[n]) * 2 : 0;
        int[] arrn = this.typeOffsets;
        return arrn[n] + arrn[n + 1];
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
        OlsonTimeZone olsonTimeZone = (OlsonTimeZone)super.cloneAsThawed();
        SimpleTimeZone simpleTimeZone = this.finalZone;
        if (simpleTimeZone != null) {
            simpleTimeZone.setID(this.getID());
            olsonTimeZone.finalZone = (SimpleTimeZone)this.finalZone.clone();
        }
        olsonTimeZone.isFrozen = false;
        return olsonTimeZone;
    }

    @Override
    public boolean equals(Object object) {
        SimpleTimeZone simpleTimeZone;
        SimpleTimeZone simpleTimeZone2;
        boolean bl = super.equals(object);
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (OlsonTimeZone)object;
        if (Utility.arrayEquals(this.typeMapData, (Object)((OlsonTimeZone)object).typeMapData) || this.finalStartYear == ((OlsonTimeZone)object).finalStartYear && (this.finalZone == null && ((OlsonTimeZone)object).finalZone == null || (simpleTimeZone2 = this.finalZone) != null && (simpleTimeZone = ((OlsonTimeZone)object).finalZone) != null && simpleTimeZone2.equals(simpleTimeZone) && this.transitionCount == ((OlsonTimeZone)object).transitionCount && this.typeCount == ((OlsonTimeZone)object).typeCount && Utility.arrayEquals((Object)this.transitionTimes64, (Object)((OlsonTimeZone)object).transitionTimes64) && Utility.arrayEquals(this.typeOffsets, (Object)((OlsonTimeZone)object).typeOffsets) && Utility.arrayEquals(this.typeMapData, (Object)((OlsonTimeZone)object).typeMapData))) {
            bl2 = true;
        }
        return bl2;
    }

    @Override
    public TimeZone freeze() {
        this.isFrozen = true;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getCanonicalID() {
        if (this.canonicalID != null) return this.canonicalID;
        synchronized (this) {
            if (this.canonicalID != null) return this.canonicalID;
            this.canonicalID = OlsonTimeZone.getCanonicalID(this.getID());
            if (this.canonicalID != null) return this.canonicalID;
            this.canonicalID = this.getID();
            return this.canonicalID;
        }
    }

    @Override
    public int getDSTSavings() {
        SimpleTimeZone simpleTimeZone = this.finalZone;
        if (simpleTimeZone != null) {
            return simpleTimeZone.getDSTSavings();
        }
        return super.getDSTSavings();
    }

    @Override
    public TimeZoneTransition getNextTransition(long l, boolean bl) {
        this.initTransitionRules();
        if (this.finalZone != null) {
            if (bl && l == this.firstFinalTZTransition.getTime()) {
                return this.firstFinalTZTransition;
            }
            if (l >= this.firstFinalTZTransition.getTime()) {
                if (this.finalZone.useDaylightTime()) {
                    return this.finalZoneWithStartYear.getNextTransition(l, bl);
                }
                return null;
            }
        }
        if (this.historicRules != null) {
            int n;
            long l2;
            for (n = this.transitionCount - 1; n >= this.firstTZTransitionIdx && l <= (l2 = this.transitionTimes64[n] * 1000L) && (bl || l != l2); --n) {
            }
            if (n == this.transitionCount - 1) {
                return this.firstFinalTZTransition;
            }
            if (n < this.firstTZTransitionIdx) {
                return this.firstTZTransition;
            }
            TimeArrayTimeZoneRule timeArrayTimeZoneRule = this.historicRules[this.getInt(this.typeMapData[n + 1])];
            TimeArrayTimeZoneRule timeArrayTimeZoneRule2 = this.historicRules[this.getInt(this.typeMapData[n])];
            l = this.transitionTimes64[n + 1] * 1000L;
            if (timeArrayTimeZoneRule2.getName().equals(timeArrayTimeZoneRule.getName()) && timeArrayTimeZoneRule2.getRawOffset() == timeArrayTimeZoneRule.getRawOffset() && timeArrayTimeZoneRule2.getDSTSavings() == timeArrayTimeZoneRule.getDSTSavings()) {
                return this.getNextTransition(l, false);
            }
            return new TimeZoneTransition(l, timeArrayTimeZoneRule2, timeArrayTimeZoneRule);
        }
        return null;
    }

    @Override
    public int getOffset(int n, int n2, int n3, int n4, int n5, int n6) {
        if (n3 >= 0 && n3 <= 11) {
            return this.getOffset(n, n2, n3, n4, n5, n6, Grego.monthLength(n2, n3));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Month is not in the legal range: ");
        stringBuilder.append(n3);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public int getOffset(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        if (n == 1 || n == 0) {
            if (n3 >= 0 && n3 <= 11 && n4 >= 1 && n4 <= n7 && n5 >= 1 && n5 <= 7 && n6 >= 0 && n6 < 86400000 && n7 >= 28 && n7 <= 31) {
                int[] arrn;
                if (n == 0) {
                    n2 = -n2;
                }
                if ((arrn = this.finalZone) != null && n2 >= this.finalStartYear) {
                    return arrn.getOffset(n, n2, n3, n4, n5, n6);
                }
                long l = Grego.fieldsToDay(n2, n3, n4);
                long l2 = n6;
                arrn = new int[2];
                this.getHistoricalOffset(l * 86400000L + l2, true, 3, 1, arrn);
                return arrn[0] + arrn[1];
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void getOffset(long l, boolean bl, int[] arrn) {
        SimpleTimeZone simpleTimeZone = this.finalZone;
        if (simpleTimeZone != null && (double)l >= this.finalStartMillis) {
            simpleTimeZone.getOffset(l, bl, arrn);
        } else {
            this.getHistoricalOffset(l, bl, 4, 12, arrn);
        }
    }

    @Override
    public void getOffsetFromLocal(long l, int n, int n2, int[] arrn) {
        SimpleTimeZone simpleTimeZone = this.finalZone;
        if (simpleTimeZone != null && (double)l >= this.finalStartMillis) {
            simpleTimeZone.getOffsetFromLocal(l, n, n2, arrn);
        } else {
            this.getHistoricalOffset(l, true, n, n2, arrn);
        }
    }

    @Override
    public TimeZoneTransition getPreviousTransition(long l, boolean bl) {
        this.initTransitionRules();
        if (this.finalZone != null) {
            if (bl && l == this.firstFinalTZTransition.getTime()) {
                return this.firstFinalTZTransition;
            }
            if (l > this.firstFinalTZTransition.getTime()) {
                if (this.finalZone.useDaylightTime()) {
                    return this.finalZoneWithStartYear.getPreviousTransition(l, bl);
                }
                return this.firstFinalTZTransition;
            }
        }
        if (this.historicRules != null) {
            int n;
            long l2;
            for (n = this.transitionCount - 1; !(n < this.firstTZTransitionIdx || l > (l2 = this.transitionTimes64[n] * 1000L) || bl && l == l2); --n) {
            }
            int n2 = this.firstTZTransitionIdx;
            if (n < n2) {
                return null;
            }
            if (n == n2) {
                return this.firstTZTransition;
            }
            TimeArrayTimeZoneRule timeArrayTimeZoneRule = this.historicRules[this.getInt(this.typeMapData[n])];
            TimeArrayTimeZoneRule timeArrayTimeZoneRule2 = this.historicRules[this.getInt(this.typeMapData[n - 1])];
            l = this.transitionTimes64[n] * 1000L;
            if (timeArrayTimeZoneRule2.getName().equals(timeArrayTimeZoneRule.getName()) && timeArrayTimeZoneRule2.getRawOffset() == timeArrayTimeZoneRule.getRawOffset() && timeArrayTimeZoneRule2.getDSTSavings() == timeArrayTimeZoneRule.getDSTSavings()) {
                return this.getPreviousTransition(l, false);
            }
            return new TimeZoneTransition(l, timeArrayTimeZoneRule2, timeArrayTimeZoneRule);
        }
        return null;
    }

    @Override
    public int getRawOffset() {
        int[] arrn = new int[2];
        this.getOffset(System.currentTimeMillis(), false, arrn);
        return arrn[0];
    }

    @Override
    public TimeZoneRule[] getTimeZoneRules() {
        TimeZoneRule[] arrtimeZoneRule;
        Object object;
        int n;
        this.initTransitionRules();
        int n2 = 1;
        int n3 = 1;
        if (this.historicRules != null) {
            n = 0;
            do {
                arrtimeZoneRule = this.historicRules;
                n2 = n3;
                if (n >= arrtimeZoneRule.length) break;
                n2 = n3;
                if (arrtimeZoneRule[n] != null) {
                    n2 = n3 + 1;
                }
                ++n;
                n3 = n2;
            } while (true);
        }
        arrtimeZoneRule = this.finalZone;
        n3 = n2;
        if (arrtimeZoneRule != null) {
            n3 = arrtimeZoneRule.useDaylightTime() ? n2 + 2 : n2 + 1;
        }
        arrtimeZoneRule = new TimeZoneRule[n3];
        n3 = 0 + 1;
        arrtimeZoneRule[0] = this.initialRule;
        n2 = n3;
        if (this.historicRules != null) {
            n = 0;
            do {
                object = this.historicRules;
                n2 = n3;
                if (n >= ((TimeArrayTimeZoneRule[])object).length) break;
                n2 = n3;
                if (object[n] != null) {
                    arrtimeZoneRule[n3] = object[n];
                    n2 = n3 + 1;
                }
                ++n;
                n3 = n2;
            } while (true);
        }
        if ((object = this.finalZone) != null) {
            if (((SimpleTimeZone)object).useDaylightTime()) {
                object = this.finalZoneWithStartYear.getTimeZoneRules();
                n3 = n2 + 1;
                arrtimeZoneRule[n2] = object[1];
                arrtimeZoneRule[n3] = object[2];
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append(this.getID());
                ((StringBuilder)object).append("(STD)");
                arrtimeZoneRule[n2] = new TimeArrayTimeZoneRule(((StringBuilder)object).toString(), this.finalZone.getRawOffset(), 0, new long[]{(long)this.finalStartMillis}, 2);
            }
        }
        return arrtimeZoneRule;
    }

    @Override
    public boolean hasSameRules(TimeZone timeZone) {
        block11 : {
            OlsonTimeZone olsonTimeZone;
            block10 : {
                block9 : {
                    if (this == timeZone) {
                        return true;
                    }
                    if (!super.hasSameRules(timeZone)) {
                        return false;
                    }
                    if (!(timeZone instanceof OlsonTimeZone)) {
                        return false;
                    }
                    olsonTimeZone = (OlsonTimeZone)timeZone;
                    timeZone = this.finalZone;
                    if (timeZone != null) break block9;
                    if (olsonTimeZone.finalZone != null) {
                        return false;
                    }
                    break block10;
                }
                SimpleTimeZone simpleTimeZone = olsonTimeZone.finalZone;
                if (simpleTimeZone == null || this.finalStartYear != olsonTimeZone.finalStartYear || !((SimpleTimeZone)timeZone).hasSameRules(simpleTimeZone)) break block11;
            }
            return this.transitionCount == olsonTimeZone.transitionCount && Arrays.equals(this.transitionTimes64, olsonTimeZone.transitionTimes64) && this.typeCount == olsonTimeZone.typeCount && Arrays.equals(this.typeMapData, olsonTimeZone.typeMapData) && Arrays.equals(this.typeOffsets, olsonTimeZone.typeOffsets);
            {
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int n = this.finalStartYear;
        int n2 = this.transitionCount;
        int n3 = this.typeCount;
        long l = n ^ (n >>> 4) + n2 ^ (n2 >>> 6) + n3;
        long l2 = n3 >>> 8;
        long l3 = Double.doubleToLongBits(this.finalStartMillis);
        long[] arrl = this.finalZone;
        n = arrl == null ? 0 : arrl.hashCode();
        n = n3 = (int)(l ^ l2 + l3 + (long)n + (long)super.hashCode());
        if (this.transitionTimes64 != null) {
            n2 = 0;
            do {
                arrl = this.transitionTimes64;
                n = n3;
                if (n2 >= arrl.length) break;
                n3 = (int)((long)n3 + (arrl[n2] ^ arrl[n2] >>> 8));
                ++n2;
            } while (true);
        }
        for (n3 = 0; n3 < (arrl = this.typeOffsets).length; ++n3) {
            n2 = (int)arrl[n3];
            n += arrl[n3] >>> 8 ^ n2;
        }
        n2 = n;
        if (this.typeMapData != null) {
            n3 = 0;
            do {
                arrl = this.typeMapData;
                n2 = n;
                if (n3 >= arrl.length) break;
                n += arrl[n3] & 255;
                ++n3;
            } while (true);
        }
        return n2;
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
        int n;
        long l = System.currentTimeMillis();
        SimpleTimeZone simpleTimeZone = this.finalZone;
        if (simpleTimeZone != null) {
            if (simpleTimeZone.useDaylightTime()) {
                return true;
            }
            if ((double)l >= this.finalStartMillis) {
                return false;
            }
        }
        l = Grego.floorDivide(l, 1000L);
        if (this.dstOffsetAt(n) != 0) {
            return true;
        }
        for (int i = n = this.transitionCount - 1; i >= 0 && this.transitionTimes64[i] > l; --i) {
            if (this.dstOffsetAt(i - 1) == 0) continue;
            return true;
        }
        return false;
    }

    @Override
    public void setID(String string) {
        if (!this.isFrozen()) {
            SimpleTimeZone simpleTimeZone;
            if (this.canonicalID == null) {
                this.canonicalID = OlsonTimeZone.getCanonicalID(this.getID());
                if (this.canonicalID == null) {
                    this.canonicalID = this.getID();
                }
            }
            if ((simpleTimeZone = this.finalZone) != null) {
                simpleTimeZone.setID(string);
            }
            super.setID(string);
            this.transitionRulesInitialized = false;
            return;
        }
        throw new UnsupportedOperationException("Attempt to modify a frozen OlsonTimeZone instance.");
    }

    @Override
    public void setRawOffset(int n) {
        if (!this.isFrozen()) {
            if (this.getRawOffset() == n) {
                return;
            }
            long l = System.currentTimeMillis();
            if ((double)l < this.finalStartMillis) {
                Object object;
                SimpleTimeZone simpleTimeZone = new SimpleTimeZone(n, this.getID());
                boolean bl = this.useDaylightTime();
                if (bl) {
                    Object object2;
                    Object object3;
                    object = object3 = this.getSimpleTimeZoneRulesNear(l);
                    if (((TimeZoneRule[])object3).length != 3) {
                        object2 = this.getPreviousTransition(l, false);
                        object = object3;
                        if (object2 != null) {
                            object = this.getSimpleTimeZoneRulesNear(((TimeZoneTransition)object2).getTime() - 1L);
                        }
                    }
                    if (((int[])object).length == 3 && object[1] instanceof AnnualTimeZoneRule && object[2] instanceof AnnualTimeZoneRule) {
                        int n2;
                        object3 = (AnnualTimeZoneRule)object[1];
                        object2 = (AnnualTimeZoneRule)object[2];
                        n = ((TimeZoneRule)object3).getRawOffset() + ((TimeZoneRule)object3).getDSTSavings();
                        if (n > (n2 = ((TimeZoneRule)object2).getRawOffset() + ((TimeZoneRule)object2).getDSTSavings())) {
                            object = ((AnnualTimeZoneRule)object3).getRule();
                            object3 = ((AnnualTimeZoneRule)object2).getRule();
                            n -= n2;
                        } else {
                            object = ((AnnualTimeZoneRule)object2).getRule();
                            object3 = ((AnnualTimeZoneRule)object3).getRule();
                            n = n2 - n;
                        }
                        simpleTimeZone.setStartRule(((DateTimeRule)object).getRuleMonth(), ((DateTimeRule)object).getRuleWeekInMonth(), ((DateTimeRule)object).getRuleDayOfWeek(), ((DateTimeRule)object).getRuleMillisInDay());
                        simpleTimeZone.setEndRule(((DateTimeRule)object3).getRuleMonth(), ((DateTimeRule)object3).getRuleWeekInMonth(), ((DateTimeRule)object3).getRuleDayOfWeek(), ((DateTimeRule)object3).getRuleMillisInDay());
                        simpleTimeZone.setDSTSavings(n);
                    } else {
                        simpleTimeZone.setStartRule(0, 1, 0);
                        simpleTimeZone.setEndRule(11, 31, 86399999);
                    }
                }
                object = Grego.timeToFields(l, null);
                this.finalStartYear = object[0];
                this.finalStartMillis = Grego.fieldsToDay(object[0], 0, 1);
                if (bl) {
                    simpleTimeZone.setStartYear(this.finalStartYear);
                }
                this.finalZone = simpleTimeZone;
            } else {
                this.finalZone.setRawOffset(n);
            }
            this.transitionRulesInitialized = false;
            return;
        }
        throw new UnsupportedOperationException("Attempt to modify a frozen OlsonTimeZone instance.");
    }

    public String toString() {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Object.super.toString());
        stringBuilder.append('[');
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("transitionCount=");
        stringBuilder2.append(this.transitionCount);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(",typeCount=");
        stringBuilder2.append(this.typeCount);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append(",transitionTimes=");
        if (this.transitionTimes64 != null) {
            stringBuilder.append('[');
            for (n = 0; n < this.transitionTimes64.length; ++n) {
                if (n > 0) {
                    stringBuilder.append(',');
                }
                stringBuilder.append(Long.toString(this.transitionTimes64[n]));
            }
            stringBuilder.append(']');
        } else {
            stringBuilder.append("null");
        }
        stringBuilder.append(",typeOffsets=");
        if (this.typeOffsets != null) {
            stringBuilder.append('[');
            for (n = 0; n < this.typeOffsets.length; ++n) {
                if (n > 0) {
                    stringBuilder.append(',');
                }
                stringBuilder.append(Integer.toString(this.typeOffsets[n]));
            }
            stringBuilder.append(']');
        } else {
            stringBuilder.append("null");
        }
        stringBuilder.append(",typeMapData=");
        if (this.typeMapData != null) {
            stringBuilder.append('[');
            for (n = 0; n < this.typeMapData.length; ++n) {
                if (n > 0) {
                    stringBuilder.append(',');
                }
                stringBuilder.append(Byte.toString(this.typeMapData[n]));
            }
        } else {
            stringBuilder.append("null");
        }
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(",finalStartYear=");
        stringBuilder2.append(this.finalStartYear);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(",finalStartMillis=");
        stringBuilder2.append(this.finalStartMillis);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(",finalZone=");
        stringBuilder2.append(this.finalZone);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public boolean useDaylightTime() {
        long l = System.currentTimeMillis();
        int[] arrn = this.finalZone;
        boolean bl = false;
        if (arrn != null && (double)l >= this.finalStartMillis) {
            boolean bl2 = bl;
            if (arrn != null) {
                bl2 = bl;
                if (arrn.useDaylightTime()) {
                    bl2 = true;
                }
            }
            return bl2;
        }
        arrn = Grego.timeToFields(l, null);
        l = Grego.fieldsToDay(arrn[0], 0, 1) * 86400L;
        long l2 = Grego.fieldsToDay(arrn[0] + 1, 0, 1);
        for (int i = 0; i < this.transitionCount && (arrn = this.transitionTimes64)[i] < l2 * 86400L; ++i) {
            if ((arrn[i] < l || this.dstOffsetAt(i) == 0) && (this.transitionTimes64[i] <= l || i <= 0 || this.dstOffsetAt(i - 1) == 0)) continue;
            return true;
        }
        return false;
    }
}

