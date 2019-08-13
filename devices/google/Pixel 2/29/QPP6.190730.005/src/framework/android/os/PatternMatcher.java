/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.proto.ProtoOutputStream;
import java.util.Arrays;

public class PatternMatcher
implements Parcelable {
    public static final Parcelable.Creator<PatternMatcher> CREATOR;
    private static final int MAX_PATTERN_STORAGE = 2048;
    private static final int NO_MATCH = -1;
    private static final int PARSED_MODIFIER_ONE_OR_MORE = -8;
    private static final int PARSED_MODIFIER_RANGE_START = -5;
    private static final int PARSED_MODIFIER_RANGE_STOP = -6;
    private static final int PARSED_MODIFIER_ZERO_OR_MORE = -7;
    private static final int PARSED_TOKEN_CHAR_ANY = -4;
    private static final int PARSED_TOKEN_CHAR_SET_INVERSE_START = -2;
    private static final int PARSED_TOKEN_CHAR_SET_START = -1;
    private static final int PARSED_TOKEN_CHAR_SET_STOP = -3;
    public static final int PATTERN_ADVANCED_GLOB = 3;
    public static final int PATTERN_LITERAL = 0;
    public static final int PATTERN_PREFIX = 1;
    public static final int PATTERN_SIMPLE_GLOB = 2;
    private static final String TAG = "PatternMatcher";
    private static final int TOKEN_TYPE_ANY = 1;
    private static final int TOKEN_TYPE_INVERSE_SET = 3;
    private static final int TOKEN_TYPE_LITERAL = 0;
    private static final int TOKEN_TYPE_SET = 2;
    private static final int[] sParsedPatternScratch;
    private final int[] mParsedPattern;
    private final String mPattern;
    private final int mType;

    static {
        sParsedPatternScratch = new int[2048];
        CREATOR = new Parcelable.Creator<PatternMatcher>(){

            @Override
            public PatternMatcher createFromParcel(Parcel parcel) {
                return new PatternMatcher(parcel);
            }

            public PatternMatcher[] newArray(int n) {
                return new PatternMatcher[n];
            }
        };
    }

    public PatternMatcher(Parcel parcel) {
        this.mPattern = parcel.readString();
        this.mType = parcel.readInt();
        this.mParsedPattern = parcel.createIntArray();
    }

    public PatternMatcher(String string2, int n) {
        this.mPattern = string2;
        this.mType = n;
        this.mParsedPattern = this.mType == 3 ? PatternMatcher.parseAndVerifyAdvancedPattern(string2) : null;
    }

    private static boolean isParsedModifier(int n) {
        boolean bl = n == -8 || n == -7 || n == -6 || n == -5;
        return bl;
    }

    static boolean matchAdvancedPattern(int[] arrn, String string2) {
        boolean bl;
        int n = 0;
        int n2 = arrn.length;
        int n3 = string2.length();
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        do {
            int n7;
            int n8;
            bl = true;
            if (n >= n2) break;
            int n9 = arrn[n];
            if (n9 != -4) {
                if (n9 != -2 && n9 != -1) {
                    n4 = n + 1;
                    n9 = n;
                    n8 = 0;
                    n = n4;
                    n7 = n5;
                } else {
                    n5 = n9 == -1 ? 2 : 3;
                    n4 = n;
                    while ((n7 = n4 + 1) < n2 && arrn[n7] != -3) {
                        n4 = n7;
                    }
                    n4 = n7 + 1;
                    n9 = n + 1;
                    --n7;
                    n = n4;
                    n8 = n5;
                }
            } else {
                ++n;
                n8 = 1;
                n7 = n5;
                n9 = n4;
            }
            if (n >= n2) {
                n4 = 1;
                n5 = 1;
            } else {
                n5 = arrn[n];
                if (n5 != -8) {
                    if (n5 != -7) {
                        if (n5 != -5) {
                            n4 = 1;
                            n5 = 1;
                        } else {
                            n4 = arrn[++n];
                            n5 = arrn[++n];
                            n += 2;
                        }
                    } else {
                        ++n;
                        n4 = 0;
                        n5 = Integer.MAX_VALUE;
                    }
                } else {
                    ++n;
                    n4 = 1;
                    n5 = Integer.MAX_VALUE;
                }
            }
            if (n4 > n5) {
                return false;
            }
            if ((n5 = PatternMatcher.matchChars(string2, n6, n3, n8, n4, n5, arrn, n9, n7)) == -1) {
                return false;
            }
            n6 += n5;
            n4 = n9;
            n5 = n7;
        } while (true);
        if (n < n2 || n6 < n3) {
            bl = false;
        }
        return bl;
    }

    private static boolean matchChar(String string2, int n, int n2, int n3, int[] arrn, int n4, int n5) {
        boolean bl = false;
        if (n >= n2) {
            return false;
        }
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        return false;
                    }
                    for (n2 = n4; n2 < n5; n2 += 2) {
                        n3 = string2.charAt(n);
                        if (n3 < arrn[n2] || n3 > arrn[n2 + 1]) continue;
                        return false;
                    }
                    return true;
                }
                for (n2 = n4; n2 < n5; n2 += 2) {
                    n3 = string2.charAt(n);
                    if (n3 < arrn[n2] || n3 > arrn[n2 + 1]) continue;
                    return true;
                }
                return false;
            }
            return true;
        }
        if (string2.charAt(n) == arrn[n4]) {
            bl = true;
        }
        return bl;
    }

    private static int matchChars(String string2, int n, int n2, int n3, int n4, int n5, int[] arrn, int n6, int n7) {
        int n8;
        for (n8 = 0; n8 < n5 && PatternMatcher.matchChar(string2, n + n8, n2, n3, arrn, n6, n7); ++n8) {
        }
        n = n8 < n4 ? -1 : n8;
        return n;
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean matchGlobPattern(String string2, String string3) {
        int n = string2.length();
        boolean bl = false;
        if (n <= 0) {
            if (string3.length() > 0) return bl;
            return true;
        }
        int n2 = string3.length();
        int n3 = 0;
        int n4 = 0;
        int n5 = string2.charAt(0);
        do {
            int n6;
            block14 : {
                int n7;
                block12 : {
                    block13 : {
                        block10 : {
                            block11 : {
                                if (n3 >= n || n4 >= n2) break block10;
                                n7 = n5;
                                int n8 = n3 + 1;
                                n6 = n8 < n ? string2.charAt(n8) : 0;
                                char c = n7 == 92 ? (char)'\u0001' : '\u0000';
                                n3 = n8;
                                n5 = n6;
                                if (c != '\u0000') {
                                    n3 = n8 + 1;
                                    n5 = n3 < n ? (int)string2.charAt(n3) : 0;
                                    n7 = n6;
                                }
                                if (n5 != 42) break block11;
                                n6 = n4;
                                if (c != '\u0000') break block12;
                                n6 = n4;
                                if (n7 != 46) break block12;
                                if (n3 >= n - 1) {
                                    return true;
                                }
                                n7 = n3 + 1;
                                c = string2.charAt(n7);
                                n6 = n7;
                                n5 = n4;
                                n3 = c;
                                if (c == '\\') {
                                    n6 = n7 + 1;
                                    n5 = n6 < n ? (int)string2.charAt(n6) : 0;
                                    n3 = n5;
                                    n5 = n4;
                                }
                                break block13;
                            }
                            if (n7 != 46 && string3.charAt(n4) != n7) {
                                return false;
                            }
                            n6 = n4 + 1;
                            n4 = n3;
                            break block14;
                        }
                        if (n3 >= n && n4 >= n2) {
                            return true;
                        }
                        if (n3 != n - 2) return false;
                        if (string2.charAt(n3) != '.') return false;
                        if (string2.charAt(n3 + 1) != '*') return false;
                        return true;
                    }
                    while (string3.charAt(n5) != n3) {
                        n5 = n4 = n5 + 1;
                        if (n4 < n2) continue;
                        n5 = n4;
                        break;
                    }
                    if (n5 == n2) {
                        return false;
                    }
                    n4 = ++n6 < n ? (int)string2.charAt(n6) : 0;
                    n3 = n4;
                    n4 = n6;
                    n6 = ++n5;
                    n5 = n3;
                    break block14;
                }
                while (string3.charAt(n6) == n7) {
                    n6 = n4 = n6 + 1;
                    if (n4 < n2) continue;
                    n6 = n4;
                    break;
                }
                n4 = ++n3 < n ? (int)string2.charAt(n3) : 0;
                n5 = n4;
                n4 = n3;
            }
            n3 = n4;
            n4 = n6;
        } while (true);
    }

    static boolean matchPattern(String string2, String string3, int[] arrn, int n) {
        if (string2 == null) {
            return false;
        }
        if (n == 0) {
            return string3.equals(string2);
        }
        if (n == 1) {
            return string2.startsWith(string3);
        }
        if (n == 2) {
            return PatternMatcher.matchGlobPattern(string3, string2);
        }
        if (n == 3) {
            return PatternMatcher.matchAdvancedPattern(arrn, string2);
        }
        return false;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    static int[] parseAndVerifyAdvancedPattern(String var0) {
        // MONITORENTER : android.os.PatternMatcher.class
        var1_14 = 0;
        var2_15 = var0.length();
        var3_16 = 0;
        var4_17 = false;
        var5_18 = false;
        var6_19 = 0;
        while (var1_14 < var2_15) {
            block49 : {
                block50 : {
                    block48 : {
                        block47 : {
                            block44 : {
                                block45 : {
                                    block46 : {
                                        if (var3_16 > 2045) {
                                            var0_11 = new IllegalArgumentException("Pattern is too large!");
                                            throw var0_11;
                                        }
                                        var7_20 = var0.charAt(var1_14);
                                        var8_21 = 0;
                                        if (var7_20 == 42) ** GOTO lbl-1000
                                        if (var7_20 == 43) break block44;
                                        if (var7_20 == 46) ** GOTO lbl-1000
                                        if (var7_20 == 123) break block45;
                                        if (var7_20 == 125) break block46;
                                        switch (var7_20) {
                                            default: {
                                                var8_21 = 1;
                                                break block47;
                                            }
                                            case 93: {
                                                if (var4_17) ** GOTO lbl27
                                                var8_21 = 1;
                                                break block47;
lbl27: // 1 sources:
                                                var6_19 = PatternMatcher.sParsedPatternScratch[var3_16 - 1];
                                                if (var6_19 == -1 || var6_19 == -2) ** GOTO lbl36
                                                var9_22 = PatternMatcher.sParsedPatternScratch;
                                                var10_23 = var3_16 + 1;
                                                var9_22[var3_16] = -3;
                                                var6_19 = 0;
                                                var4_17 = false;
                                                var3_16 = var10_23;
                                                break block47;
lbl36: // 1 sources:
                                                var0_1 = new IllegalArgumentException("You must define characters in a set.");
                                                throw var0_1;
                                            }
                                            case 92: {
                                                if (var1_14 + 1 >= var2_15) {
                                                    var0_2 = new IllegalArgumentException("Escape found at end of pattern!");
                                                    throw var0_2;
                                                }
                                                var7_20 = var0.charAt(++var1_14);
                                                var8_21 = 1;
                                                break block47;
                                            }
                                            case 91: {
                                                if (!var4_17) ** GOTO lbl49
                                                var8_21 = 1;
                                                break block47;
lbl49: // 1 sources:
                                                if (var0.charAt(var1_14 + 1) == '^') {
                                                    PatternMatcher.sParsedPatternScratch[var3_16] = -2;
                                                    ++var1_14;
                                                    ++var3_16;
                                                } else {
                                                    PatternMatcher.sParsedPatternScratch[var3_16] = -1;
                                                    ++var3_16;
                                                }
                                                ++var1_14;
                                                var4_17 = true;
                                                ** break;
lbl59: // 1 sources:
                                                break;
                                            }
                                        }
                                        continue;
                                    }
                                    if (var5_18) {
                                        var9_22 = PatternMatcher.sParsedPatternScratch;
                                        var10_23 = var3_16 + 1;
                                        var9_22[var3_16] = -6;
                                        var5_18 = false;
                                        var3_16 = var10_23;
                                    }
                                    break block47;
                                }
                                if (!var4_17) {
                                    if (var3_16 != 0 && !PatternMatcher.isParsedModifier(PatternMatcher.sParsedPatternScratch[var3_16 - 1])) {
                                        var9_22 = PatternMatcher.sParsedPatternScratch;
                                        var10_23 = var3_16 + 1;
                                        var9_22[var3_16] = -5;
                                        var5_18 = true;
                                        ++var1_14;
                                        var3_16 = var10_23;
                                    } else {
                                        var0_3 = new IllegalArgumentException("Modifier must follow a token.");
                                        throw var0_3;
                                    }
                                }
                                break block47;
lbl-1000: // 1 sources:
                                {
                                    if (!var4_17) {
                                        var9_22 = PatternMatcher.sParsedPatternScratch;
                                        var10_23 = var3_16 + 1;
                                        var9_22[var3_16] = -4;
                                        var3_16 = var10_23;
                                    }
                                }
                                break block47;
                            }
                            if (!var4_17) {
                                if (var3_16 != 0 && !PatternMatcher.isParsedModifier(PatternMatcher.sParsedPatternScratch[var3_16 - 1])) {
                                    var9_22 = PatternMatcher.sParsedPatternScratch;
                                    var10_23 = var3_16 + 1;
                                    var9_22[var3_16] = -8;
                                    var3_16 = var10_23;
                                } else {
                                    var0_4 = new IllegalArgumentException("Modifier must follow a token.");
                                    throw var0_4;
                                }
                            }
                            break block47;
lbl-1000: // 1 sources:
                            {
                                if (!var4_17) {
                                    if (var3_16 != 0 && !PatternMatcher.isParsedModifier(PatternMatcher.sParsedPatternScratch[var3_16 - 1])) {
                                        var9_22 = PatternMatcher.sParsedPatternScratch;
                                        var10_23 = var3_16 + 1;
                                        var9_22[var3_16] = -7;
                                        var3_16 = var10_23;
                                    } else {
                                        var0_5 = new IllegalArgumentException("Modifier must follow a token.");
                                        throw var0_5;
                                    }
                                }
                            }
                        }
                        if (!var4_17) break block48;
                        if (var6_19 != 0) {
                            var9_22 = PatternMatcher.sParsedPatternScratch;
                            var6_19 = var3_16 + 1;
                            var9_22[var3_16] = var7_20;
                            var8_21 = 0;
                            var3_16 = var6_19;
                        } else if (var1_14 + 2 < var2_15 && var0.charAt(var1_14 + 1) == '-' && var0.charAt(var1_14 + 2) != ']') {
                            var9_22 = PatternMatcher.sParsedPatternScratch;
                            var6_19 = var3_16 + 1;
                            var9_22[var3_16] = var7_20;
                            ++var1_14;
                            var8_21 = 1;
                            var3_16 = var6_19;
                        } else {
                            var9_22 = PatternMatcher.sParsedPatternScratch;
                            var8_21 = var3_16 + 1;
                            var9_22[var3_16] = var7_20;
                            PatternMatcher.sParsedPatternScratch[var8_21] = var7_20;
                            var3_16 = var8_21 + 1;
                            var8_21 = var6_19;
                        }
                        break block49;
                    }
                    if (!var5_18) break block50;
                    var7_20 = var0.indexOf(125, var1_14);
                    if (var7_20 < 0) {
                        var0_10 = new IllegalArgumentException("Range not ended with '}'");
                        throw var0_10;
                    }
                    var9_22 = var0.substring(var1_14, var7_20);
                    var1_14 = var9_22.indexOf(44);
                    if (var1_14 >= 0) ** GOTO lbl144
                    try {
                        block51 : {
                            var1_14 = var8_21 = Integer.parseInt((String)var9_22);
                            break block51;
lbl144: // 1 sources:
                            var8_21 = Integer.parseInt(var9_22.substring(0, var1_14));
                            var1_14 = var1_14 == var9_22.length() - 1 ? Integer.MAX_VALUE : Integer.parseInt(var9_22.substring(var1_14 + 1));
                        }
                        if (var8_21 > var1_14) {
                            var0_7 = new IllegalArgumentException("Range quantifier minimum is greater than maximum");
                            throw var0_7;
                        }
                        var9_22 = PatternMatcher.sParsedPatternScratch;
                        var10_23 = var3_16 + 1;
                        var9_22[var3_16] = var8_21;
                        try {
                            var9_22 = PatternMatcher.sParsedPatternScratch;
                        }
                        catch (NumberFormatException var0_6) {}
                        var9_22[var10_23] = var1_14;
                        var1_14 = var7_20;
                        var3_16 = var10_23 + 1;
                        continue;
                    }
                    catch (NumberFormatException var0_8) {
                        // empty catch block
                    }
                    var9_22 = new IllegalArgumentException("Range number format incorrect", (Throwable)var0_9);
                    throw var9_22;
                }
                if (var8_21 != 0) {
                    var9_22 = PatternMatcher.sParsedPatternScratch;
                    var10_23 = var3_16 + 1;
                    var9_22[var3_16] = var7_20;
                    var8_21 = var6_19;
                    var3_16 = var10_23;
                } else {
                    var8_21 = var6_19;
                }
            }
            ++var1_14;
            var6_19 = var8_21;
        }
        if (!var4_17) {
            var0_12 = Arrays.copyOf(PatternMatcher.sParsedPatternScratch, var3_16);
            // MONITOREXIT : android.os.PatternMatcher.class
            return var0_12;
        }
        var0_13 = new IllegalArgumentException("Set was not terminated!");
        throw var0_13;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public final String getPath() {
        return this.mPattern;
    }

    public final int getType() {
        return this.mType;
    }

    public boolean match(String string2) {
        return PatternMatcher.matchPattern(string2, this.mPattern, this.mParsedPattern, this.mType);
    }

    public String toString() {
        String string2 = "? ";
        int n = this.mType;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        string2 = "ADVANCED: ";
                    }
                } else {
                    string2 = "GLOB: ";
                }
            } else {
                string2 = "PREFIX: ";
            }
        } else {
            string2 = "LITERAL: ";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PatternMatcher{");
        stringBuilder.append(string2);
        stringBuilder.append(this.mPattern);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mPattern);
        parcel.writeInt(this.mType);
        parcel.writeIntArray(this.mParsedPattern);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1138166333441L, this.mPattern);
        protoOutputStream.write(1159641169922L, this.mType);
        protoOutputStream.end(l);
    }

}

