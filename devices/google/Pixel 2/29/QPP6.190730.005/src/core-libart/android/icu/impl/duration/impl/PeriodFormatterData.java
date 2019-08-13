/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration.impl;

import android.icu.impl.duration.TimeUnit;
import android.icu.impl.duration.impl.DataRecord;
import android.icu.impl.duration.impl.Utils;
import java.io.PrintStream;
import java.util.Arrays;

public class PeriodFormatterData {
    private static final int FORM_DUAL = 2;
    private static final int FORM_HALF_SPELLED = 6;
    private static final int FORM_PAUCAL = 3;
    private static final int FORM_PLURAL = 0;
    private static final int FORM_SINGULAR = 1;
    private static final int FORM_SINGULAR_NO_OMIT = 5;
    private static final int FORM_SINGULAR_SPELLED = 4;
    public static boolean trace = false;
    final DataRecord dr;
    String localeName;

    public PeriodFormatterData(String string, DataRecord dataRecord) {
        this.dr = dataRecord;
        this.localeName = string;
        if (string != null) {
            if (dataRecord != null) {
                return;
            }
            throw new NullPointerException("data record is null");
        }
        throw new NullPointerException("localename is null");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int computeForm(TimeUnit object, int n, int n2, boolean bl) {
        Appendable appendable;
        Appendable appendable2;
        if (trace) {
            appendable = System.err;
            appendable2 = new StringBuilder();
            ((StringBuilder)appendable2).append("pfd.cf unit: ");
            ((StringBuilder)appendable2).append(object);
            ((StringBuilder)appendable2).append(" count: ");
            ((StringBuilder)appendable2).append(n);
            ((StringBuilder)appendable2).append(" cv: ");
            ((StringBuilder)appendable2).append(n2);
            ((StringBuilder)appendable2).append(" dr.pl: ");
            ((StringBuilder)appendable2).append(this.dr.pl);
            ((PrintStream)appendable).println(((StringBuilder)appendable2).toString());
            Thread.dumpStack();
        }
        if (this.dr.pl == 0) {
            return 0;
        }
        int n3 = n / 1000;
        if (n2 != 0 && n2 != 1) {
            if (n2 != 2) {
                n2 = this.dr.decimalHandling;
                if (n2 == 0) return 0;
                if (n2 == 1) return 5;
                if (n2 != 2) {
                    if (n2 != 3) return 0;
                    if (this.dr.pl != 3) return 0;
                    return 3;
                }
                if (n >= 1000) return 0;
                return 5;
            }
            n2 = this.dr.fractionHandling;
            if (n2 == 0) return 0;
            if (n2 != 1 && n2 != 2) {
                if (n2 != 3) throw new IllegalStateException();
                n2 = n / 500;
                if (n2 == 1) return 3;
                if (n2 == 3) {
                    return 3;
                }
            } else {
                n2 = n / 500;
                if (n2 == 1) {
                    if (this.dr.halfNames == null) return 5;
                    if (this.dr.halfNames[((TimeUnit)object).ordinal()] == null) return 5;
                    return 6;
                }
                if ((n2 & 1) == 1) {
                    if (this.dr.pl == 5 && n2 > 21) {
                        return 5;
                    }
                    if (n2 == 3 && this.dr.pl == 1 && this.dr.fractionHandling != 2) {
                        return 0;
                    }
                }
            }
        }
        if (trace && n == 0) {
            appendable2 = System.err;
            appendable = new StringBuilder();
            ((StringBuilder)appendable).append("EZeroHandling = ");
            ((StringBuilder)appendable).append(this.dr.zeroHandling);
            ((PrintStream)appendable2).println(((StringBuilder)appendable).toString());
        }
        if (n == 0 && this.dr.zeroHandling == 1) {
            return 4;
        }
        n2 = 0;
        int n4 = 0;
        n = this.dr.pl;
        if (n == 0) return n2;
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n == 5) {
                            if (n3 == 2) {
                                return 2;
                            }
                            if (n3 == 1) {
                                return 1;
                            }
                            n = n2;
                            if (n3 <= 10) return n;
                            return 5;
                        }
                        appendable = System.err;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("dr.pl is ");
                        ((StringBuilder)object).append(this.dr.pl);
                        ((PrintStream)appendable).println(((StringBuilder)object).toString());
                        throw new IllegalStateException();
                    }
                    if (n3 == 2) {
                        return 2;
                    }
                    if (n3 == 1) {
                        if (!bl) return 1;
                        return 4;
                    }
                    n = n2;
                    if (object != TimeUnit.YEAR) return n;
                    n = n2;
                    if (n3 <= 11) return n;
                    return 5;
                }
                n2 = n = n3 % 100;
                if (n > 20) {
                    n2 = n % 10;
                }
                if (n2 == 1) {
                    return 1;
                }
                n = n4;
                if (n2 <= 1) return n;
                n = n4;
                if (n2 >= 5) return n;
                return 3;
            }
            if (n3 == 2) {
                return 2;
            }
            n = n2;
            if (n3 != 1) return n;
            return 1;
        }
        n = n2;
        if (n3 != 1) return n;
        return 4;
    }

    public boolean allowZero() {
        return this.dr.allowZero;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public int appendCount(TimeUnit var1_1, boolean var2_2, boolean var3_3, int var4_4, int var5_5, boolean var6_6, String var7_7, boolean var8_8, StringBuffer var9_9) {
        block29 : {
            block26 : {
                block27 : {
                    block30 : {
                        block28 : {
                            var10_10 = var5_5;
                            if (var5_5 == 2) {
                                var10_10 = var5_5;
                                if (this.dr.halves == null) {
                                    var10_10 = 0;
                                }
                            }
                            if (!var2_2 && var3_3 && this.dr.digitPrefix != null) {
                                var9_9.append(this.dr.digitPrefix);
                            }
                            var11_11 = var1_1.ordinal();
                            var5_5 = -1;
                            if (var10_10 == 0) break block26;
                            if (var10_10 == 1) break block27;
                            if (var10_10 == 2) break block28;
                            var12_12 = 1;
                            var10_10 = var10_10 != 4 ? (var10_10 != 5 ? var12_12 : 3) : 2;
                            if (!var2_2) {
                                this.appendCountValue(var4_4, 1, var10_10, var9_9);
                            }
                            var1_1 = var7_7;
                            var10_10 = var11_11;
                            break block29;
                        }
                        var10_10 = var4_4 / 500;
                        var12_13 = 0;
                        if (var10_10 != 1 && !var2_2) {
                            this.appendCountValue(var4_4, 1, 0, var9_9);
                        }
                        if ((var10_10 & 1) != 1) break block30;
                        if (var10_10 == 1 && this.dr.halfNames != null && this.dr.halfNames[var11_11] != null) {
                            var9_9.append(var7_7);
                            if (var8_8 == false) return var5_5;
                            return var11_11;
                        }
                        var4_4 = var10_10 == 1 ? 0 : 1;
                        var10_10 = var4_4;
                        if (this.dr.genders != null) {
                            var10_10 = var4_4;
                            if (this.dr.halves.length > 2) {
                                var10_10 = var4_4;
                                if (this.dr.genders[var11_11] == 1) {
                                    var10_10 = var4_4 + 2;
                                }
                            }
                        }
                        var4_4 = this.dr.halfPlacements == null ? var12_13 : this.dr.halfPlacements[var10_10 & 1];
                        var13_14 = this.dr.halves[var10_10];
                        var1_1 = this.dr.measures == null ? null : this.dr.measures[var11_11];
                        if (var4_4 == 0) ** GOTO lbl76
                        if (var4_4 != 1) {
                            if (var4_4 == 2) {
                                if (var1_1 != null) {
                                    var9_9.append((String)var1_1);
                                }
                                if (var6_6 && !var2_2) {
                                    var9_9.append(this.dr.countSep);
                                }
                                var9_9.append(var7_7);
                                var9_9.append(var13_14);
                                if (var8_8 == false) return var5_5;
                                return var11_11;
                            }
                        } else {
                            if (var1_1 == null) {
                                var9_9.append(var7_7);
                                var9_9.append(var13_14);
                                if (var8_8 == false) return var5_5;
                                return var11_11;
                            }
                            var9_9.append((String)var1_1);
                            var9_9.append(var13_14);
                            if (var6_6 && !var2_2) {
                                var9_9.append(this.dr.countSep);
                            }
                            var9_9.append(var7_7);
                            return -1;
lbl76: // 1 sources:
                            var9_9.append(var13_14);
                        }
                    }
                    var1_1 = var7_7;
                    var10_10 = var11_11;
                    break block29;
                }
                if (var1_1 != TimeUnit.MINUTE || this.dr.fiveMinutes == null && this.dr.fifteenMinutes == null || (var4_4 /= 1000) == 0 || var4_4 % 5 != 0) ** GOTO lbl-1000
                if (this.dr.fifteenMinutes != null && (var4_4 == 15 || var4_4 == 45)) {
                    var4_4 = var4_4 == 15 ? 1 : 3;
                    if (!var2_2) {
                        this.appendInteger(var4_4, 1, 10, var9_9);
                    }
                    var1_1 = this.dr.fifteenMinutes;
                    var10_10 = 8;
                } else if (this.dr.fiveMinutes != null) {
                    var4_4 /= 5;
                    if (!var2_2) {
                        this.appendInteger(var4_4, 1, 10, var9_9);
                    }
                    var1_1 = this.dr.fiveMinutes;
                    var10_10 = 9;
                } else lbl-1000: // 2 sources:
                {
                    if (!var2_2) {
                        this.appendInteger(var4_4, 1, 10, var9_9);
                    }
                    var1_1 = var7_7;
                    var10_10 = var11_11;
                }
                break block29;
            }
            var1_1 = var7_7;
            var10_10 = var11_11;
            if (!var2_2) {
                this.appendInteger(var4_4 / 1000, 1, 10, var9_9);
                var10_10 = var11_11;
                var1_1 = var7_7;
            }
        }
        if (!var2_2 && var6_6) {
            var9_9.append(this.dr.countSep);
        }
        if (!var2_2 && this.dr.measures != null && var10_10 < this.dr.measures.length && (var7_7 = this.dr.measures[var10_10]) != null) {
            var9_9.append(var7_7);
        }
        var9_9.append((String)var1_1);
        if (var8_8 == false) return var5_5;
        return var10_10;
    }

    public void appendCountValue(int n, int n2, int n3, StringBuffer stringBuffer) {
        int n4 = n / 1000;
        if (n3 == 0) {
            this.appendInteger(n4, n2, 10, stringBuffer);
            return;
        }
        if (this.dr.requiresDigitSeparator && stringBuffer.length() > 0) {
            stringBuffer.append(' ');
        }
        this.appendDigits(n4, n2, 10, stringBuffer);
        n2 = n % 1000;
        if (n3 == 1) {
            n = n2 / 100;
        } else {
            n = n2;
            if (n3 == 2) {
                n = n2 / 10;
            }
        }
        stringBuffer.append(this.dr.decimalSep);
        this.appendDigits(n, n3, n3, stringBuffer);
        if (this.dr.requiresDigitSeparator) {
            stringBuffer.append(' ');
        }
    }

    public void appendDigits(long l, int n, int n2, StringBuffer stringBuffer) {
        char[] arrc = new char[n2];
        int n3 = n2;
        while (n3 > 0 && l > 0L) {
            arrc[--n3] = (char)((long)this.dr.zero + l % 10L);
            l /= 10L;
        }
        while (n3 > n2 - n) {
            arrc[--n3] = this.dr.zero;
        }
        stringBuffer.append(arrc, n3, n2 - n3);
    }

    public void appendInteger(int n, int n2, int n3, StringBuffer stringBuffer) {
        byte by;
        String string;
        if (this.dr.numberNames != null && n < this.dr.numberNames.length && (string = this.dr.numberNames[n]) != null) {
            stringBuffer.append(string);
            return;
        }
        if (this.dr.requiresDigitSeparator && stringBuffer.length() > 0) {
            stringBuffer.append(' ');
        }
        if ((by = this.dr.numberSystem) != 0) {
            if (by != 1) {
                if (by != 2) {
                    if (by == 3) {
                        stringBuffer.append(Utils.chineseNumber(n, Utils.ChineseDigits.KOREAN));
                    }
                } else {
                    stringBuffer.append(Utils.chineseNumber(n, Utils.ChineseDigits.SIMPLIFIED));
                }
            } else {
                stringBuffer.append(Utils.chineseNumber(n, Utils.ChineseDigits.TRADITIONAL));
            }
        } else {
            this.appendDigits(n, n2, n3, stringBuffer);
        }
        if (this.dr.requiresDigitSeparator) {
            stringBuffer.append(' ');
        }
    }

    public boolean appendPrefix(int n, int n2, StringBuffer stringBuffer) {
        String string;
        DataRecord.ScopeData scopeData;
        if (this.dr.scopeData != null && (scopeData = this.dr.scopeData[n * 3 + n2]) != null && (string = scopeData.prefix) != null) {
            stringBuffer.append(string);
            return scopeData.requiresDigitPrefix;
        }
        return false;
    }

    public void appendSkippedUnit(StringBuffer stringBuffer) {
        if (this.dr.skippedUnitMarker != null) {
            stringBuffer.append(this.dr.skippedUnitMarker);
        }
    }

    public void appendSuffix(int n, int n2, StringBuffer stringBuffer) {
        Object object;
        if (this.dr.scopeData != null && (object = this.dr.scopeData[n * 3 + n2]) != null && (object = ((DataRecord.ScopeData)object).suffix) != null) {
            if (trace) {
                PrintStream printStream = System.out;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("appendSuffix '");
                stringBuilder.append((String)object);
                stringBuilder.append("'");
                printStream.println(stringBuilder.toString());
            }
            stringBuffer.append((String)object);
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean appendUnit(TimeUnit object, int n, int n2, int n3, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, StringBuffer abstractStringBuilder) {
        Object object2;
        int n4 = ((TimeUnit)object).ordinal();
        if (this.dr.requiresSkipMarker != null && this.dr.requiresSkipMarker[n4] && this.dr.skippedUnitMarker != null) {
            if (!bl5 && bl4) {
                ((StringBuffer)abstractStringBuilder).append(this.dr.skippedUnitMarker);
            }
            bl5 = true;
        } else {
            bl5 = false;
        }
        if (n3 != 0) {
            n3 = n3 == 1 ? 1 : 0;
            object2 = this.dr;
            object2 = n3 != 0 ? object2.mediumNames : object2.shortNames;
            if (object2 == null || object2[n4] == null) {
                object2 = this.dr;
                object2 = n3 != 0 ? object2.shortNames : object2.mediumNames;
            }
            if (object2 != null && object2[n4] != null) {
                this.appendCount((TimeUnit)object, false, false, n, n2, bl, object2[n4], bl4, (StringBuffer)abstractStringBuilder);
                return false;
            }
        }
        if (n2 == 2 && this.dr.halfSupport != null && (n3 = this.dr.halfSupport[n4]) != 0 && (n3 == 1 || n3 == 2 && n <= 1000)) {
            n3 = n / 500 * 500;
            n2 = 3;
        } else {
            n3 = n;
        }
        bl3 = bl3 && bl4;
        n = this.computeForm((TimeUnit)object, n3, n2, bl3);
        if (n == 4) {
            if (this.dr.singularNames == null) {
                n = 1;
                object2 = this.dr.pluralNames[n4][1];
            } else {
                object2 = this.dr.singularNames[n4];
            }
        } else if (n == 5) {
            object2 = this.dr.pluralNames[n4][1];
        } else if (n == 6) {
            object2 = this.dr.halfNames[n4];
        } else {
            object2 = this.dr.pluralNames[n4][n];
        }
        if (object2 == null) {
            object2 = this.dr.pluralNames[n4][0];
            n = 0;
        }
        bl3 = n == 4 || n == 6 || this.dr.omitSingularCount && n == 1 || this.dr.omitDualCount && n == 2;
        n = this.appendCount((TimeUnit)object, bl3, bl2, n3, n2, bl, (String)object2, bl4, (StringBuffer)abstractStringBuilder);
        if (!bl4) return bl5;
        if (n < 0) return bl5;
        object2 = null;
        object = object2;
        if (this.dr.rqdSuffixes != null) {
            object = object2;
            if (n < this.dr.rqdSuffixes.length) {
                object = this.dr.rqdSuffixes[n];
            }
        }
        object2 = object;
        if (object == null) {
            object2 = object;
            if (this.dr.optSuffixes != null) {
                object2 = object;
                if (n < this.dr.optSuffixes.length) {
                    object2 = this.dr.optSuffixes[n];
                }
            }
        }
        if (object2 == null) return bl5;
        ((StringBuffer)abstractStringBuilder).append((String)object2);
        return bl5;
        catch (NullPointerException nullPointerException) {
            object = System.out;
            abstractStringBuilder = new StringBuilder();
            ((StringBuilder)abstractStringBuilder).append("Null Pointer in PeriodFormatterData[");
            ((StringBuilder)abstractStringBuilder).append(this.localeName);
            ((StringBuilder)abstractStringBuilder).append("].au px: ");
            ((StringBuilder)abstractStringBuilder).append(n4);
            ((StringBuilder)abstractStringBuilder).append(" form: ");
            ((StringBuilder)abstractStringBuilder).append(n);
            ((StringBuilder)abstractStringBuilder).append(" pn: ");
            ((StringBuilder)abstractStringBuilder).append(Arrays.toString((Object[])this.dr.pluralNames));
            ((PrintStream)object).println(((StringBuilder)abstractStringBuilder).toString());
            throw nullPointerException;
        }
    }

    public boolean appendUnitSeparator(TimeUnit timeUnit, boolean bl, boolean bl2, boolean bl3, StringBuffer stringBuffer) {
        boolean bl4 = false;
        if (bl && this.dr.unitSep != null || this.dr.shortUnitSep != null) {
            if (bl && this.dr.unitSep != null) {
                int n = bl2 ? 2 : 0;
                bl3 = n + bl3;
                stringBuffer.append(this.dr.unitSep[bl3]);
                bl = bl4;
                if (this.dr.unitSepRequiresDP != null) {
                    bl = bl4;
                    if (this.dr.unitSepRequiresDP[bl3]) {
                        bl = true;
                    }
                }
                return bl;
            }
            stringBuffer.append(this.dr.shortUnitSep);
        }
        return false;
    }

    public int pluralization() {
        return this.dr.pl;
    }

    public int useMilliseconds() {
        return this.dr.useMilliseconds;
    }

    public boolean weeksAloneOnly() {
        return this.dr.weeksAloneOnly;
    }
}

