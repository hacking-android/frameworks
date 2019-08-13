/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.Utility;
import android.icu.text.UTF16;
import android.icu.text.UnicodeSet;
import android.icu.util.OutputInt;
import java.util.ArrayList;

public class UnicodeSetStringSpan {
    public static final int ALL = 127;
    static final short ALL_CP_CONTAINED = 255;
    public static final int BACK = 16;
    public static final int BACK_UTF16_CONTAINED = 18;
    public static final int BACK_UTF16_NOT_CONTAINED = 17;
    public static final int CONTAINED = 2;
    public static final int FWD = 32;
    public static final int FWD_UTF16_CONTAINED = 34;
    public static final int FWD_UTF16_NOT_CONTAINED = 33;
    static final short LONG_SPAN = 254;
    public static final int NOT_CONTAINED = 1;
    public static final int WITH_COUNT = 64;
    private boolean all;
    private final int maxLength16;
    private OffsetList offsets;
    private boolean someRelevant;
    private short[] spanLengths;
    private UnicodeSet spanNotSet;
    private UnicodeSet spanSet;
    private ArrayList<String> strings;

    public UnicodeSetStringSpan(UnicodeSetStringSpan unicodeSetStringSpan, ArrayList<String> arrayList) {
        this.spanSet = unicodeSetStringSpan.spanSet;
        this.strings = arrayList;
        this.maxLength16 = unicodeSetStringSpan.maxLength16;
        this.someRelevant = unicodeSetStringSpan.someRelevant;
        this.all = true;
        this.spanNotSet = Utility.sameObjects(unicodeSetStringSpan.spanNotSet, unicodeSetStringSpan.spanSet) ? this.spanSet : (UnicodeSet)unicodeSetStringSpan.spanNotSet.clone();
        this.offsets = new OffsetList();
        this.spanLengths = (short[])unicodeSetStringSpan.spanLengths.clone();
    }

    public UnicodeSetStringSpan(UnicodeSet arrs, ArrayList<String> object, int n) {
        int n2;
        int n3;
        int n4;
        this.spanSet = new UnicodeSet(0, 1114111);
        this.strings = object;
        boolean bl = n == 127;
        this.all = bl;
        this.spanSet.retainAll((UnicodeSet)arrs);
        if ((n & 1) != 0) {
            this.spanNotSet = this.spanSet;
        }
        this.offsets = new OffsetList();
        int n5 = this.strings.size();
        int n6 = 0;
        this.someRelevant = false;
        for (n3 = 0; n3 < n5; ++n3) {
            arrs = this.strings.get(n3);
            n2 = arrs.length();
            if (this.spanSet.span((CharSequence)arrs, UnicodeSet.SpanCondition.CONTAINED) < n2) {
                this.someRelevant = true;
            }
            n4 = n6;
            if (n2 > n6) {
                n4 = n2;
            }
            n6 = n4;
        }
        this.maxLength16 = n6;
        if (!this.someRelevant && (n & 64) == 0) {
            return;
        }
        if (this.all) {
            this.spanSet.freeze();
        }
        n3 = this.all ? n5 * 2 : n5;
        this.spanLengths = new short[n3];
        n3 = this.all ? n5 : 0;
        for (n6 = 0; n6 < n5; ++n6) {
            object = this.strings.get(n6);
            n4 = ((String)object).length();
            n2 = this.spanSet.span((CharSequence)object, UnicodeSet.SpanCondition.CONTAINED);
            if (n2 < n4) {
                if ((n & 2) != 0) {
                    if ((n & 32) != 0) {
                        this.spanLengths[n6] = UnicodeSetStringSpan.makeSpanLengthByte(n2);
                    }
                    if ((n & 16) != 0) {
                        n2 = this.spanSet.spanBack((CharSequence)object, n4, UnicodeSet.SpanCondition.CONTAINED);
                        this.spanLengths[n3 + n6] = UnicodeSetStringSpan.makeSpanLengthByte(n4 - n2);
                    }
                } else {
                    arrs = this.spanLengths;
                    arrs[n3 + n6] = (short)(false ? 1 : 0);
                    arrs[n6] = (short)(false ? 1 : 0);
                }
                if ((n & 1) == 0) continue;
                if ((n & 32) != 0) {
                    this.addToSpanNotSet(((String)object).codePointAt(0));
                }
                if ((n & 16) == 0) continue;
                this.addToSpanNotSet(((String)object).codePointBefore(n4));
                continue;
            }
            if (this.all) {
                arrs = this.spanLengths;
                arrs[n3 + n6] = (short)255;
                arrs[n6] = (short)255;
                continue;
            }
            this.spanLengths[n6] = (short)255;
        }
        if (this.all) {
            this.spanNotSet.freeze();
        }
    }

    private void addToSpanNotSet(int n) {
        if (Utility.sameObjects(this.spanNotSet, null) || Utility.sameObjects(this.spanNotSet, this.spanSet)) {
            if (this.spanSet.contains(n)) {
                return;
            }
            this.spanNotSet = this.spanSet.cloneAsThawed();
        }
        this.spanNotSet.add(n);
    }

    static short makeSpanLengthByte(int n) {
        int n2;
        int n3 = n2 = 254;
        if (n < 254) {
            n3 = n = (int)((short)n);
        }
        return (short)n3;
    }

    private static boolean matches16(CharSequence charSequence, int n, String string, int n2) {
        n += n2;
        do {
            int n3 = n2 - 1;
            if (n2 <= 0) break;
            if (charSequence.charAt(--n) != string.charAt(n3)) {
                return false;
            }
            n2 = n3;
        } while (true);
        return true;
    }

    static boolean matches16CPB(CharSequence charSequence, int n, int n2, String string, int n3) {
        boolean bl = UnicodeSetStringSpan.matches16(charSequence, n, string, n3);
        boolean bl2 = true;
        if (!bl || n > 0 && Character.isHighSurrogate(charSequence.charAt(n - 1)) && Character.isLowSurrogate(charSequence.charAt(n)) || n + n3 < n2 && Character.isHighSurrogate(charSequence.charAt(n + n3 - 1)) && Character.isLowSurrogate(charSequence.charAt(n + n3))) {
            bl2 = false;
        }
        return bl2;
    }

    private int spanContainedAndCount(CharSequence charSequence, int n, OutputInt outputInt) {
        synchronized (this) {
            int n2;
            this.offsets.setMaxLength(this.maxLength16);
            int n3 = this.strings.size();
            int n4 = charSequence.length();
            int n5 = n;
            int n6 = 0;
            for (n = n4 - n; n != 0; n -= n2) {
                block13 : {
                    block12 : {
                        n2 = UnicodeSetStringSpan.spanOne(this.spanSet, charSequence, n5, n);
                        if (n2 <= 0) break block12;
                        this.offsets.addOffsetAndCount(n2, n6 + 1);
                    }
                    for (n2 = 0; n2 < n3; ++n2) {
                        String string = this.strings.get(n2);
                        int n7 = string.length();
                        if (n7 > n) continue;
                        if (this.offsets.hasCountAtOffset(n7, n6 + 1) || !UnicodeSetStringSpan.matches16CPB(charSequence, n5, n4, string, n7)) continue;
                        this.offsets.addOffsetAndCount(n7, n6 + 1);
                    }
                    if (!this.offsets.isEmpty()) break block13;
                    outputInt.value = n6;
                    return n5;
                }
                n2 = this.offsets.popMinimum(outputInt);
                n6 = outputInt.value;
                n5 += n2;
            }
            outputInt.value = n6;
            return n5;
        }
    }

    private int spanNot(CharSequence charSequence, int n, OutputInt outputInt) {
        int n2;
        int n3;
        int n4 = charSequence.length();
        int n5 = n;
        int n6 = this.strings.size();
        n = 0;
        do {
            int n7;
            if (outputInt == null) {
                n5 = this.spanNotSet.span(charSequence, n5, UnicodeSet.SpanCondition.NOT_CONTAINED);
            } else {
                n5 = this.spanNotSet.spanAndCount(charSequence, n5, UnicodeSet.SpanCondition.NOT_CONTAINED, outputInt);
                n = n7 = outputInt.value + n;
                outputInt.value = n7;
            }
            if (n5 == n4) {
                return n4;
            }
            n2 = n4 - n5;
            n3 = UnicodeSetStringSpan.spanOne(this.spanSet, charSequence, n5, n2);
            if (n3 > 0) {
                return n5;
            }
            for (n7 = 0; n7 < n6; ++n7) {
                String string;
                int n8;
                if (this.spanLengths[n7] == 255 || (n8 = (string = this.strings.get(n7)).length()) > n2 || !UnicodeSetStringSpan.matches16CPB(charSequence, n5, n4, string, n8)) continue;
                return n5;
            }
            n5 -= n3;
            ++n;
        } while (n2 + n3 != 0);
        if (outputInt != null) {
            outputInt.value = n;
        }
        return n4;
    }

    private int spanNotBack(CharSequence charSequence, int n) {
        int n2;
        int n3;
        int n4 = n;
        int n5 = this.strings.size();
        do {
            if ((n3 = this.spanNotSet.spanBack(charSequence, n4, UnicodeSet.SpanCondition.NOT_CONTAINED)) == 0) {
                return 0;
            }
            n2 = UnicodeSetStringSpan.spanOneBack(this.spanSet, charSequence, n3);
            if (n2 > 0) {
                return n3;
            }
            for (n4 = 0; n4 < n5; ++n4) {
                int n6;
                String string;
                if (this.spanLengths[n4] == 255 || (n6 = (string = this.strings.get(n4)).length()) > n3 || !UnicodeSetStringSpan.matches16CPB(charSequence, n3 - n6, n, string, n6)) continue;
                return n3;
            }
        } while ((n4 = n3 + n2) != 0);
        return 0;
    }

    static int spanOne(UnicodeSet unicodeSet, CharSequence charSequence, int n, int n2) {
        char c = charSequence.charAt(n);
        if (c >= '\ud800' && c <= '\udbff') {
            char c2;
            int n3 = 2;
            if (n2 >= 2 && UTF16.isTrailSurrogate(c2 = charSequence.charAt(n + 1))) {
                n = unicodeSet.contains(Character.toCodePoint(c, c2)) ? n3 : -2;
                return n;
            }
        }
        n = unicodeSet.contains(c) ? 1 : -1;
        return n;
    }

    static int spanOneBack(UnicodeSet unicodeSet, CharSequence charSequence, int n) {
        char c = charSequence.charAt(n - 1);
        if (c >= '\udc00' && c <= '\udfff') {
            char c2;
            int n2 = 2;
            if (n >= 2 && UTF16.isLeadSurrogate(c2 = charSequence.charAt(n - 2))) {
                n = unicodeSet.contains(Character.toCodePoint(c2, c)) ? n2 : -2;
                return n;
            }
        }
        n = unicodeSet.contains(c) ? 1 : -1;
        return n;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private int spanWithStrings(CharSequence var1_1, int var2_3, int var3_4, UnicodeSet.SpanCondition var4_5) {
        // MONITORENTER : this
        var5_5 = 0;
        if (var4_4 == UnicodeSet.SpanCondition.CONTAINED) {
            var5_5 = this.maxLength16;
        }
        this.offsets.setMaxLength(var5_5);
        var6_6 = var1_1.length();
        var7_7 = this.strings.size();
        var2_2 = var3_3 - var2_2;
        var8_8 = var6_6 - var3_3;
        var9_9 = var3_3;
        var3_3 = var5_5;
        block11 : do {
            block36 : {
                if (var4_4 == UnicodeSet.SpanCondition.CONTAINED) {
                    block12 : for (var10_10 = 0; var10_10 < var7_7; ++var10_10) {
                        var5_5 = this.spanLengths[var10_10];
                        if (var5_5 == 255) continue;
                        var11_11 = this.strings.get(var10_10);
                        var12_12 = var11_11.length();
                        var13_13 = var5_5;
                        if (var5_5 >= 254) {
                            var13_13 = var11_11.offsetByCodePoints(var12_12, -1);
                        }
                        var5_5 = var13_13;
                        if (var13_13 > var2_2) {
                            var5_5 = var2_2;
                        }
                        var13_13 = var12_12 - var5_5;
                        do {
                            if (var13_13 > var8_8) continue block12;
                            if (!this.offsets.containsOffset(var13_13) && (var14_14 = UnicodeSetStringSpan.matches16CPB(var1_1, (int)(var9_9 - var5_5), var6_6, var11_11, var12_12))) {
                                if (var13_13 == var8_8) {
                                    // MONITOREXIT : this
                                    return var6_6;
                                }
                                this.offsets.addOffset(var13_13);
                            }
                            if (var5_5 == 0) {
                                continue block12;
                            }
                            --var5_5;
                            ++var13_13;
                        } while (true);
                    }
                } else {
                    var10_10 = 0;
                    var15_15 = 0;
                    var12_12 = 0;
lbl42: // 2 sources:
                    do {
                        if (var15_15 < var7_7) {
                            var13_13 = this.spanLengths[var15_15];
                            var11_11 = this.strings.get(var15_15);
                            var16_16 = var11_11.length();
                            var5_5 = var13_13;
                            if (var13_13 >= 254) {
                                var5_5 = var16_16;
                            }
                            var13_13 = var5_5;
                            if (var5_5 > var2_2) {
                                var13_13 = var2_2;
                            }
                            var17_17 = var13_13;
                            var5_5 = var16_16 - var13_13;
                            break block11;
                        }
                        var5_5 = var3_3;
                        if (var10_10 != 0) break block36;
                        var3_3 = var5_5;
                        if (var12_12 != 0) break block36;
                        break;
                    } while (true);
                }
                if (var2_2 == 0 && var9_9 != false) {
                    if (this.offsets.isEmpty()) {
                        var5_5 = this.spanSet.span(var1_1, (int)var9_9, UnicodeSet.SpanCondition.CONTAINED);
                        var2_2 = var5_5 - var9_9;
                        if (var2_2 == var8_8) {
                            // MONITOREXIT : this
                            return var5_5;
                        }
                        if (var2_2 == 0) {
                            return var5_5;
                        }
                        var9_9 += var2_2;
                        var8_8 -= var2_2;
                        continue;
                    }
                    var2_2 = UnicodeSetStringSpan.spanOne(this.spanSet, var1_1, (int)var9_9, var8_8);
                    if (var2_2 > 0) {
                        if (var2_2 == var8_8) {
                            // MONITOREXIT : this
                            return var6_6;
                        }
                        var9_9 += var2_2;
                        var8_8 -= var2_2;
                        this.offsets.shift(var2_2);
                        var2_2 = 0;
                        continue;
                    }
                } else {
                    var14_14 = this.offsets.isEmpty();
                    if (var14_14) {
                        // MONITOREXIT : this
                        return (int)var9_9;
                    }
                }
                var2_2 = this.offsets.popMinimum(null);
                var9_9 += var2_2;
                var8_8 -= var2_2;
                var2_2 = 0;
                continue;
            }
            var9_9 += var10_10;
            if ((var8_8 -= var10_10) == 0) {
                // MONITOREXIT : this
                return var6_6;
            }
            var2_2 = 0;
            var3_3 = var5_5;
        } while (true);
        for (var13_13 = var17_17; var5_5 <= var8_8 && var13_13 >= var12_12; --var13_13, ++var5_5) {
            if (var13_13 <= var12_12 && var5_5 <= var10_10 || !UnicodeSetStringSpan.matches16CPB(var1_1, (int)(var9_9 - var13_13), var6_6, var11_11, var16_16)) continue;
            var10_10 = var5_5;
            var12_12 = var13_13;
            break;
        }
        ++var15_15;
        ** while (true)
    }

    public boolean contains(int n) {
        return this.spanSet.contains(n);
    }

    public boolean needsStringSpanUTF16() {
        return this.someRelevant;
    }

    public int span(CharSequence charSequence, int n, UnicodeSet.SpanCondition spanCondition) {
        if (spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED) {
            return this.spanNot(charSequence, n, null);
        }
        int n2 = this.spanSet.span(charSequence, n, UnicodeSet.SpanCondition.CONTAINED);
        if (n2 == charSequence.length()) {
            return n2;
        }
        return this.spanWithStrings(charSequence, n, n2, spanCondition);
    }

    public int spanAndCount(CharSequence charSequence, int n, UnicodeSet.SpanCondition object, OutputInt outputInt) {
        if (object == UnicodeSet.SpanCondition.NOT_CONTAINED) {
            return this.spanNot(charSequence, n, outputInt);
        }
        if (object == UnicodeSet.SpanCondition.CONTAINED) {
            return this.spanContainedAndCount(charSequence, n, outputInt);
        }
        int n2 = this.strings.size();
        int n3 = charSequence.length();
        int n4 = n;
        int n5 = 0;
        for (int i = n3 - n; i != 0; i -= n) {
            n = UnicodeSetStringSpan.spanOne(this.spanSet, charSequence, n4, i);
            if (n <= 0) {
                n = 0;
            }
            for (int j = 0; j < n2; ++j) {
                object = this.strings.get(j);
                int n6 = ((String)object).length();
                int n7 = n;
                if (n < n6) {
                    n7 = n;
                    if (n6 <= i) {
                        n7 = n;
                        if (UnicodeSetStringSpan.matches16CPB(charSequence, n4, n3, (String)object, n6)) {
                            n7 = n6;
                        }
                    }
                }
                n = n7;
            }
            if (n == 0) {
                outputInt.value = n5;
                return n4;
            }
            ++n5;
            n4 += n;
        }
        outputInt.value = n5;
        return n4;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public int spanBack(CharSequence var1_1, int var2_3, UnicodeSet.SpanCondition var3_4) {
        var4_4 = var3_3;
        // MONITORENTER : this
        if (var4_4 == UnicodeSet.SpanCondition.NOT_CONTAINED) {
            var2_2 = this.spanNotBack(var1_1, var2_2);
            // MONITOREXIT : this
            return var2_2;
        }
        var5_5 = this.spanSet.spanBack(var1_1, var2_2, UnicodeSet.SpanCondition.CONTAINED);
        if (var5_5 == 0) {
            // MONITOREXIT : this
            return 0;
        }
        var6_6 = var2_2 - var5_5;
        var7_7 = 0;
        if (var4_4 == UnicodeSet.SpanCondition.CONTAINED) {
            var7_7 = this.maxLength16;
        }
        this.offsets.setMaxLength(var7_7);
        var8_8 = this.strings.size();
        var9_9 = 0;
        var10_10 = var5_5;
        var7_7 = var6_6;
        if (this.all) {
            var9_9 = var8_8;
            var7_7 = var6_6;
            var10_10 = var5_5;
        }
        block11 : do {
            block40 : {
                var11_11 = var10_10;
                if (var3_3 == UnicodeSet.SpanCondition.CONTAINED) {
                    block12 : for (var5_5 = 0; var5_5 < var8_8; ++var5_5) {
                        var6_6 = this.spanLengths[var9_9 + var5_5];
                        if (var6_6 == 255) continue;
                        var4_4 = this.strings.get(var5_5);
                        var12_12 = var4_4.length();
                        var10_10 = var6_6;
                        if (var6_6 >= 254) {
                            var10_10 = var12_12 - var4_4.offsetByCodePoints(0, 1);
                        }
                        var6_6 = var10_10;
                        if (var10_10 > var7_7) {
                            var6_6 = var7_7;
                        }
                        var10_10 = var12_12 - var6_6;
                        do {
                            if (var10_10 > var11_11) continue block12;
                            if (!this.offsets.containsOffset(var10_10) && (var13_13 = UnicodeSetStringSpan.matches16CPB(var1_1, var11_11 - var10_10, var2_2, var4_4, var12_12))) {
                                if (var10_10 == var11_11) {
                                    // MONITOREXIT : this
                                    return 0;
                                }
                                this.offsets.addOffset(var10_10);
                            }
                            if (var6_6 == 0) {
                                continue block12;
                            }
                            --var6_6;
                            ++var10_10;
                        } while (true);
                    }
                } else {
                    var14_14 = 0;
                    var12_12 = 0;
                    var5_5 = 0;
lbl55: // 2 sources:
                    do {
                        if (var12_12 < var8_8) {
                            var6_6 = this.spanLengths[var9_9 + var12_12];
                            var4_4 = this.strings.get(var12_12);
                            var15_15 = var4_4.length();
                            var10_10 = var6_6;
                            if (var6_6 >= 254) {
                                var10_10 = var15_15;
                            }
                            var6_6 = var10_10;
                            if (var10_10 > var7_7) {
                                var6_6 = var7_7;
                            }
                            break block11;
                        }
                        if (var14_14 != 0 || var5_5 != 0) break block40;
                        break;
                    } while (true);
                }
                if (var7_7 == 0 && var11_11 != var2_2) {
                    if (this.offsets.isEmpty()) {
                        var10_10 = this.spanSet.spanBack(var1_1, var11_11, UnicodeSet.SpanCondition.CONTAINED);
                        var7_7 = var11_11 - var10_10;
                        if (var10_10 == 0) {
                            // MONITOREXIT : this
                            return var10_10;
                        }
                        if (var7_7 != 0) continue;
                        return var10_10;
                    }
                    var7_7 = UnicodeSetStringSpan.spanOneBack(this.spanSet, var1_1, var11_11);
                    if (var7_7 > 0) {
                        if (var7_7 == var11_11) {
                            // MONITOREXIT : this
                            return 0;
                        }
                        var10_10 = var11_11 - var7_7;
                        this.offsets.shift(var7_7);
                        var7_7 = 0;
                        continue;
                    }
                } else {
                    var13_13 = this.offsets.isEmpty();
                    if (var13_13) {
                        // MONITOREXIT : this
                        return var11_11;
                    }
                }
                var7_7 = this.offsets.popMinimum(null);
                var10_10 = var11_11 - var7_7;
                var7_7 = 0;
                continue;
            }
            var10_10 = var11_11 - var14_14;
            if (var10_10 == 0) {
                // MONITOREXIT : this
                return 0;
            }
            var7_7 = 0;
        } while (true);
        var16_16 = var6_6;
        var10_10 = var15_15 - var6_6;
        var6_6 = var16_16;
        do {
            var16_16 = var14_14;
            var17_17 = var5_5;
            if (var10_10 > var11_11) break;
            if (var6_6 < var5_5) {
                var16_16 = var14_14;
                var17_17 = var5_5;
                break;
            }
            if ((var6_6 > var5_5 || var10_10 > var14_14) && UnicodeSetStringSpan.matches16CPB(var1_1, var11_11 - var10_10, var2_2, var4_4, var15_15)) {
                var16_16 = var10_10;
                var17_17 = var6_6;
                break;
            }
            --var6_6;
            ++var10_10;
        } while (true);
        ++var12_12;
        var14_14 = var16_16;
        var5_5 = var17_17;
        ** while (true)
    }

    private static final class OffsetList {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private int length;
        private int[] list = new int[16];
        private int start;

        public void addOffset(int n) {
            int n2 = this.start + n;
            int[] arrn = this.list;
            n = n2;
            if (n2 >= arrn.length) {
                n = n2 - arrn.length;
            }
            this.list[n] = 1;
            ++this.length;
        }

        public void addOffsetAndCount(int n, int n2) {
            int n3 = this.start + n;
            int[] arrn = this.list;
            n = n3;
            if (n3 >= arrn.length) {
                n = n3 - arrn.length;
            }
            if ((arrn = this.list)[n] == 0) {
                arrn[n] = n2;
                ++this.length;
            } else if (n2 < arrn[n]) {
                arrn[n] = n2;
            }
        }

        public void clear() {
            int n = this.list.length;
            do {
                int n2 = n - 1;
                if (n <= 0) break;
                this.list[n2] = 0;
                n = n2;
            } while (true);
            this.length = 0;
            this.start = 0;
        }

        public boolean containsOffset(int n) {
            int n2 = this.start + n;
            int[] arrn = this.list;
            n = n2;
            if (n2 >= arrn.length) {
                n = n2 - arrn.length;
            }
            boolean bl = this.list[n] != 0;
            return bl;
        }

        public boolean hasCountAtOffset(int n, int n2) {
            int n3 = this.start + n;
            int[] arrn = this.list;
            n = n3;
            if (n3 >= arrn.length) {
                n = n3 - arrn.length;
            }
            boolean bl = (n = this.list[n]) != 0 && n <= n2;
            return bl;
        }

        public boolean isEmpty() {
            boolean bl = this.length == 0;
            return bl;
        }

        public int popMinimum(OutputInt outputInt) {
            int n;
            int n2;
            int[] arrn;
            int n3 = this.start;
            while (++n3 < (arrn = this.list).length) {
                n2 = arrn[n3];
                if (n2 == 0) continue;
                arrn[n3] = 0;
                --this.length;
                int n4 = this.start;
                this.start = n3;
                if (outputInt != null) {
                    outputInt.value = n2;
                }
                return n3 - n4;
            }
            n2 = arrn.length;
            int n5 = this.start;
            n3 = 0;
            while ((n = (arrn = this.list)[n3]) == 0) {
                ++n3;
            }
            arrn[n3] = 0;
            --this.length;
            this.start = n3;
            if (outputInt != null) {
                outputInt.value = n;
            }
            return n2 - n5 + n3;
        }

        public void setMaxLength(int n) {
            if (n > this.list.length) {
                this.list = new int[n];
            }
            this.clear();
        }

        public void shift(int n) {
            int n2 = this.start + n;
            int[] arrn = this.list;
            n = n2;
            if (n2 >= arrn.length) {
                n = n2 - arrn.length;
            }
            if ((arrn = this.list)[n] != 0) {
                arrn[n] = 0;
                --this.length;
            }
            this.start = n;
        }
    }

}

