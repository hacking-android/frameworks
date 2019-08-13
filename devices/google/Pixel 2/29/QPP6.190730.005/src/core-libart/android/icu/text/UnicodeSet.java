/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.BMPSet;
import android.icu.impl.CharacterPropertiesImpl;
import android.icu.impl.PatternProps;
import android.icu.impl.RuleCharacterIterator;
import android.icu.impl.SortedSetRelation;
import android.icu.impl.StringRange;
import android.icu.impl.UCaseProps;
import android.icu.impl.UPropertyAliases;
import android.icu.impl.UnicodeSetStringSpan;
import android.icu.impl.Utility;
import android.icu.lang.CharSequences;
import android.icu.lang.CharacterProperties;
import android.icu.lang.UCharacter;
import android.icu.lang.UScript;
import android.icu.text.BreakIterator;
import android.icu.text.Replaceable;
import android.icu.text.SymbolTable;
import android.icu.text.UTF16;
import android.icu.text.UnicodeFilter;
import android.icu.text.UnicodeMatcher;
import android.icu.text.UnicodeSetIterator;
import android.icu.util.Freezable;
import android.icu.util.ICUUncheckedIOException;
import android.icu.util.OutputInt;
import android.icu.util.ULocale;
import android.icu.util.VersionInfo;
import java.io.IOException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;

public class UnicodeSet
extends UnicodeFilter
implements Iterable<String>,
Comparable<UnicodeSet>,
Freezable<UnicodeSet> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int ADD_CASE_MAPPINGS = 4;
    public static final UnicodeSet ALL_CODE_POINTS;
    private static final String ANY_ID = "ANY";
    private static final String ASCII_ID = "ASCII";
    private static final String ASSIGNED = "Assigned";
    public static final int CASE = 2;
    public static final int CASE_INSENSITIVE = 2;
    public static final UnicodeSet EMPTY;
    private static final SortedSet<String> EMPTY_STRINGS;
    private static final int HIGH = 1114112;
    public static final int IGNORE_SPACE = 1;
    private static final int INITIAL_CAPACITY = 25;
    private static final int LAST0_START = 0;
    private static final int LAST1_RANGE = 1;
    private static final int LAST2_SET = 2;
    private static final int LOW = 0;
    private static final int MAX_DEPTH = 100;
    private static final int MAX_LENGTH = 1114113;
    public static final int MAX_VALUE = 1114111;
    public static final int MIN_VALUE = 0;
    private static final int MODE0_NONE = 0;
    private static final int MODE1_INBRACKET = 1;
    private static final int MODE2_OUTBRACKET = 2;
    private static final VersionInfo NO_VERSION;
    private static final int SETMODE0_NONE = 0;
    private static final int SETMODE1_UNICODESET = 1;
    private static final int SETMODE2_PROPERTYPAT = 2;
    private static final int SETMODE3_PREPARSED = 3;
    private static XSymbolTable XSYMBOL_TABLE;
    private volatile BMPSet bmpSet;
    private int[] buffer;
    private int len;
    private int[] list;
    private String pat = null;
    private int[] rangeList;
    private volatile UnicodeSetStringSpan stringSpan;
    SortedSet<String> strings = EMPTY_STRINGS;

    static {
        EMPTY_STRINGS = Collections.unmodifiableSortedSet(new TreeSet());
        EMPTY = new UnicodeSet().freeze();
        ALL_CODE_POINTS = new UnicodeSet(0, 1114111).freeze();
        XSYMBOL_TABLE = null;
        NO_VERSION = VersionInfo.getInstance(0, 0, 0, 0);
    }

    public UnicodeSet() {
        this.list = new int[25];
        this.list[0] = 1114112;
        this.len = 1;
    }

    public UnicodeSet(int n, int n2) {
        this();
        this.add(n, n2);
    }

    public UnicodeSet(UnicodeSet unicodeSet) {
        this.set(unicodeSet);
    }

    public UnicodeSet(String string) {
        this();
        this.applyPattern(string, null, null, 1);
    }

    public UnicodeSet(String string, int n) {
        this();
        this.applyPattern(string, null, null, n);
    }

    public UnicodeSet(String string, ParsePosition parsePosition, SymbolTable symbolTable) {
        this();
        this.applyPattern(string, parsePosition, symbolTable, 1);
    }

    public UnicodeSet(String string, ParsePosition parsePosition, SymbolTable symbolTable, int n) {
        this();
        this.applyPattern(string, parsePosition, symbolTable, n);
    }

    public UnicodeSet(String string, boolean bl) {
        this();
        this.applyPattern(string, null, null, (int)bl);
    }

    public UnicodeSet(int ... arrn) {
        if ((arrn.length & 1) == 0) {
            this.list = new int[arrn.length + 1];
            this.len = this.list.length;
            int n = -1;
            int n2 = 0;
            while (n2 < arrn.length) {
                int n3 = arrn[n2];
                if (n < n3) {
                    int[] arrn2 = this.list;
                    int n4 = n2 + 1;
                    arrn2[n2] = n3;
                    n = arrn[n4] + 1;
                    if (n3 < n) {
                        n2 = n;
                        arrn2[n4] = n;
                        n = n2;
                        n2 = ++n4;
                        continue;
                    }
                    throw new IllegalArgumentException("Must be monotonically increasing.");
                }
                throw new IllegalArgumentException("Must be monotonically increasing.");
            }
            this.list[n2] = 1114112;
            return;
        }
        throw new IllegalArgumentException("Must have even number of integers");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static <T extends Appendable> T _appendToPat(T var0, int var1_2, boolean var2_3) {
        block7 : {
            if (var2_3) {
                if (!Utility.isUnprintable(var1_2) || !Utility.escapeUnprintable(var0, var1_2)) break block7;
                return var0;
            }
        }
        if (var1_2 == 36 || var1_2 == 38 || var1_2 == 45 || var1_2 == 58 || var1_2 == 123 || var1_2 == 125) ** GOTO lbl-1000
        switch (var1_2) {
            default: {
                try {
                    if (!PatternProps.isWhiteSpace(var1_2)) break;
                    var0.append('\\');
                    break;
                }
                catch (IOException var0_1) {
                    throw new ICUUncheckedIOException(var0_1);
                }
            }
            case 91: 
            case 92: 
            case 93: 
            case 94: lbl-1000: // 2 sources:
            {
                var0.append('\\');
            }
        }
        UnicodeSet.appendCodePoint(var0, var1_2);
        return var0;
    }

    private static <T extends Appendable> T _appendToPat(T t, String string, boolean bl) {
        int n;
        for (int i = 0; i < string.length(); i += Character.charCount((int)n)) {
            n = string.codePointAt(i);
            UnicodeSet._appendToPat(t, n, bl);
        }
        return t;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private <T extends Appendable> T _toPattern(T var1_1, boolean var2_3) {
        var3_4 = this.pat;
        if (var3_4 == null) {
            return this.appendNewPattern(var1_1, var2_3, true);
        }
        if (var2_3) ** GOTO lbl9
        try {
            var1_1.append(var3_4);
            return var1_1;
lbl9: // 1 sources:
            var4_5 = false;
            var5_6 = 0;
            while (var5_6 < this.pat.length()) {
                var6_7 = this.pat.codePointAt(var5_6);
                var5_6 += Character.charCount(var6_7);
                if (Utility.isUnprintable(var6_7)) {
                    Utility.escapeUnprintable(var1_1, var6_7);
                    var4_5 = false;
                    continue;
                }
                if (!var4_5 && var6_7 == 92) {
                    var4_5 = true;
                    continue;
                }
                if (var4_5) {
                    var1_1.append('\\');
                }
                UnicodeSet.appendCodePoint(var1_1, var6_7);
                var4_5 = false;
            }
            if (var4_5 == false) return var1_1;
            var1_1.append('\\');
        }
        catch (IOException var1_2) {
            throw new ICUUncheckedIOException(var1_2);
        }
        return var1_1;
    }

    private UnicodeSet add(int[] arrn, int n, int n2) {
        this.ensureBufferCapacity(this.len + n);
        int n3 = 0;
        int[] arrn2 = this.list;
        int n4 = 0 + 1;
        n = arrn2[0];
        int n5 = 0 + 1;
        int n6 = arrn[0];
        int n7 = n2;
        n2 = n;
        do {
            block14 : {
                block24 : {
                    block16 : {
                        block23 : {
                            block22 : {
                                block10 : {
                                    block21 : {
                                        block20 : {
                                            block11 : {
                                                block19 : {
                                                    block18 : {
                                                        block12 : {
                                                            block17 : {
                                                                block15 : {
                                                                    block13 : {
                                                                        if (n7 == 0) break block10;
                                                                        if (n7 == 1) break block11;
                                                                        if (n7 == 2) break block12;
                                                                        if (n7 == 3) break block13;
                                                                        n = n7;
                                                                        break block14;
                                                                    }
                                                                    if (n6 > n2) break block15;
                                                                    if (n2 == 1114112) break block16;
                                                                    arrn2 = this.buffer;
                                                                    n = n3 + 1;
                                                                    arrn2[n3] = n2;
                                                                    break block17;
                                                                }
                                                                if (n6 == 1114112) break block16;
                                                                arrn2 = this.buffer;
                                                                n = n3 + 1;
                                                                arrn2[n3] = n6;
                                                            }
                                                            n2 = this.list[n4];
                                                            n6 = arrn[n5];
                                                            n7 = n7 ^ 1 ^ 2;
                                                            ++n5;
                                                            ++n4;
                                                            n3 = n;
                                                            n = n7;
                                                            break block14;
                                                        }
                                                        if (n6 >= n2) break block18;
                                                        this.buffer[n3] = n6;
                                                        n6 = arrn[n5];
                                                        n = n7 ^ 2;
                                                        ++n5;
                                                        ++n3;
                                                        break block14;
                                                    }
                                                    if (n2 >= n6) break block19;
                                                    n2 = this.list[n4];
                                                    n = n7 ^ 1;
                                                    ++n4;
                                                    break block14;
                                                }
                                                if (n2 == 1114112) break block16;
                                                n2 = this.list[n4];
                                                n6 = arrn[n5];
                                                n = n7 ^ 1 ^ 2;
                                                ++n5;
                                                ++n4;
                                                break block14;
                                            }
                                            if (n2 >= n6) break block20;
                                            this.buffer[n3] = n2;
                                            n2 = this.list[n4];
                                            n = n7 ^ 1;
                                            ++n4;
                                            ++n3;
                                            break block14;
                                        }
                                        if (n6 >= n2) break block21;
                                        n6 = arrn[n5];
                                        n = n7 ^ 2;
                                        ++n5;
                                        break block14;
                                    }
                                    if (n2 == 1114112) break block16;
                                    n2 = this.list[n4];
                                    n6 = arrn[n5];
                                    n = n7 ^ 1 ^ 2;
                                    ++n5;
                                    ++n4;
                                    break block14;
                                }
                                if (n2 >= n6) break block22;
                                if (n3 > 0 && n2 <= (arrn2 = this.buffer)[n3 - 1]) {
                                    n = this.list[n4];
                                    n2 = UnicodeSet.max(n, arrn2[--n3]);
                                } else {
                                    this.buffer[n3] = n2;
                                    n2 = this.list[n4];
                                    ++n3;
                                }
                                ++n4;
                                n = n7 ^ 1;
                                break block14;
                            }
                            if (n6 >= n2) break block23;
                            if (n3 > 0 && n6 <= (arrn2 = this.buffer)[n3 - 1]) {
                                n = arrn[n5];
                                n6 = UnicodeSet.max(n, arrn2[--n3]);
                            } else {
                                this.buffer[n3] = n6;
                                n6 = arrn[n5];
                                ++n3;
                            }
                            ++n5;
                            n = n7 ^ 2;
                            break block14;
                        }
                        if (n2 != 1114112) break block24;
                    }
                    arrn = this.buffer;
                    arrn[n3] = 1114112;
                    this.len = n3 + 1;
                    arrn2 = this.list;
                    this.list = arrn;
                    this.buffer = arrn2;
                    this.pat = null;
                    return this;
                }
                if (n3 > 0 && n2 <= (arrn2 = this.buffer)[n3 - 1]) {
                    n = this.list[n4];
                    n2 = UnicodeSet.max(n, arrn2[--n3]);
                } else {
                    this.buffer[n3] = n2;
                    n2 = this.list[n4];
                    ++n3;
                }
                ++n4;
                n6 = arrn[n5];
                n = n7 ^ 1 ^ 2;
                ++n5;
            }
            n7 = n;
        } while (true);
    }

    public static <T, U extends Collection<T>> U addAllTo(Iterable<T> object, U u) {
        object = object.iterator();
        while (object.hasNext()) {
            u.add(object.next());
        }
        return u;
    }

    public static <T> T[] addAllTo(Iterable<T> object, T[] arrT) {
        int n = 0;
        object = object.iterator();
        while (object.hasNext()) {
            arrT[n] = object.next();
            ++n;
        }
        return arrT;
    }

    private static final void addCaseMapping(UnicodeSet unicodeSet, int n, StringBuilder stringBuilder) {
        if (n >= 0) {
            if (n > 31) {
                unicodeSet.add(n);
            } else {
                unicodeSet.add(stringBuilder.toString());
                stringBuilder.setLength(0);
            }
        }
    }

    private void addString(CharSequence charSequence) {
        if (this.strings == EMPTY_STRINGS) {
            this.strings = new TreeSet<String>();
        }
        this.strings.add(charSequence.toString());
    }

    private final UnicodeSet add_unchecked(int n) {
        if (n >= 0 && n <= 1114111) {
            int n2 = this.findCodePoint(n);
            if ((n2 & 1) != 0) {
                return this;
            }
            int[] arrn = this.list;
            if (n == arrn[n2] - 1) {
                arrn[n2] = n;
                if (n == 1114111) {
                    this.ensureCapacity(this.len + 1);
                    arrn = this.list;
                    int n3 = this.len;
                    this.len = n3 + 1;
                    arrn[n3] = 1114112;
                }
                if (n2 > 0 && n == (arrn = this.list)[n2 - 1]) {
                    System.arraycopy(arrn, n2 + 1, arrn, n2 - 1, this.len - n2 - 1);
                    this.len -= 2;
                }
            } else if (n2 > 0 && n == arrn[n2 - 1]) {
                n = n2 - 1;
                arrn[n] = arrn[n] + 1;
            } else {
                int n4 = this.len;
                arrn = this.list;
                if (n4 + 2 > arrn.length) {
                    arrn = new int[this.nextCapacity(n4 + 2)];
                    if (n2 != 0) {
                        System.arraycopy(this.list, 0, arrn, 0, n2);
                    }
                    System.arraycopy(this.list, n2, arrn, n2 + 2, this.len - n2);
                    this.list = arrn;
                } else {
                    System.arraycopy(arrn, n2, arrn, n2 + 2, n4 - n2);
                }
                arrn = this.list;
                arrn[n2] = n;
                arrn[n2 + 1] = n + 1;
                this.len += 2;
            }
            this.pat = null;
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid code point U+");
        stringBuilder.append(Utility.hex(n, 6));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private UnicodeSet add_unchecked(int n, int n2) {
        if (n >= 0 && n <= 1114111) {
            if (n2 >= 0 && n2 <= 1114111) {
                if (n < n2) {
                    int n3 = n2 + 1;
                    int n4 = this.len;
                    if ((n4 & 1) != 0 && (n4 = n4 == 1 ? -2 : this.list[n4 - 2]) <= n) {
                        this.checkFrozen();
                        if (n4 == n) {
                            int[] arrn = this.list;
                            n = this.len;
                            arrn[n - 2] = n3;
                            if (n3 == 1114112) {
                                this.len = n - 1;
                            }
                        } else {
                            int[] arrn = this.list;
                            n2 = this.len;
                            arrn[n2 - 1] = n;
                            if (n3 < 1114112) {
                                this.ensureCapacity(n2 + 2);
                                arrn = this.list;
                                n = this.len;
                                this.len = n + 1;
                                arrn[n] = n3;
                                n = this.len;
                                this.len = n + 1;
                                arrn[n] = 1114112;
                            } else {
                                this.ensureCapacity(n2 + 1);
                                arrn = this.list;
                                n = this.len;
                                this.len = n + 1;
                                arrn[n] = 1114112;
                            }
                        }
                        this.pat = null;
                        return this;
                    }
                    this.add(this.range(n, n2), 2, 0);
                } else if (n == n2) {
                    this.add(n);
                }
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid code point U+");
            stringBuilder.append(Utility.hex(n2, 6));
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid code point U+");
        stringBuilder.append(Utility.hex(n, 6));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static void append(Appendable appendable, CharSequence charSequence) {
        try {
            appendable.append(charSequence);
            return;
        }
        catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void appendCodePoint(Appendable var0, int var1_2) {
        if (var1_2 > 65535) ** GOTO lbl7
        var2_3 = (char)var1_2;
        try {
            var0.append(var2_3);
            return;
lbl7: // 1 sources:
            var0.append(UTF16.getLeadSurrogate(var1_2)).append(UTF16.getTrailSurrogate(var1_2));
            return;
        }
        catch (IOException var0_1) {
            throw new ICUUncheckedIOException(var0_1);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private <T extends Appendable> T appendNewPattern(T var1_1, boolean var2_3, boolean var3_4) {
        block11 : {
            block10 : {
                var1_1.append('[');
                var4_5 = this.getRangeCount();
                if (var4_5 <= 1 || this.getRangeStart(0) != 0 || this.getRangeEnd(var4_5 - 1) != 1114111) break block10;
                var1_1.append('^');
                for (var5_6 = 1; var5_6 < var4_5; ++var5_6) {
                    var6_7 = this.getRangeEnd(var5_6 - 1) + 1;
                    var7_8 = this.getRangeStart(var5_6) - 1;
                    UnicodeSet._appendToPat(var1_1, var6_7, var2_3);
                    if (var6_7 == var7_8) continue;
                    if (var6_7 + 1 != var7_8) {
                        var1_1.append('-');
                    }
                    UnicodeSet._appendToPat(var1_1, var7_8, var2_3);
                }
                break block11;
            }
            for (var5_6 = 0; var5_6 < var4_5; ++var5_6) {
                var6_7 = this.getRangeStart(var5_6);
                var7_8 = this.getRangeEnd(var5_6);
                UnicodeSet._appendToPat(var1_1, var6_7, var2_3);
                if (var6_7 == var7_8) continue;
                if (var6_7 + 1 != var7_8) {
                    var1_1.append('-');
                }
                UnicodeSet._appendToPat(var1_1, var7_8, var2_3);
                continue;
            }
        }
        if (!var3_4) ** GOTO lbl47
        try {
            if (this.hasStrings()) {
                for (String var9_10 : this.strings) {
                    var1_1.append('{');
                    UnicodeSet._appendToPat(var1_1, var9_10, var2_3);
                    var1_1.append('}');
                }
            }
lbl47: // 4 sources:
            var1_1.append(']');
        }
        catch (IOException var1_2) {
            throw new ICUUncheckedIOException(var1_2);
        }
        return var1_1;
    }

    private void applyFilter(Filter filter, UnicodeSet unicodeSet) {
        this.clear();
        int n = -1;
        int n2 = unicodeSet.getRangeCount();
        for (int i = 0; i < n2; ++i) {
            int n3 = unicodeSet.getRangeEnd(i);
            for (int j = unicodeSet.getRangeStart((int)i); j <= n3; ++j) {
                int n4;
                if (filter.contains(j)) {
                    n4 = n;
                    if (n < 0) {
                        n4 = j;
                    }
                } else {
                    n4 = n;
                    if (n >= 0) {
                        this.add_unchecked(n, j - 1);
                        n4 = -1;
                    }
                }
                n = n4;
            }
        }
        if (n >= 0) {
            this.add_unchecked(n, 1114111);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void applyPattern(RuleCharacterIterator var1_1, SymbolTable var2_2, Appendable var3_3, int var4_4, int var5_5) {
        if (var5_5 > 100) {
            UnicodeSet.syntaxError(var1_1, "Pattern nested too deeply");
        }
        var6_6 = (var4_4 & 1) != 0 ? 3 | 4 : 3;
        var7_7 = new StringBuilder();
        var8_8 = null;
        var9_9 = null;
        var10_10 = 0;
        var11_11 = 0;
        this.clear();
        var12_12 = 0;
        var13_13 = 0;
        var14_14 = 0;
        var15_15 = null;
        var16_17 = false;
        var17_18 = null;
        var18_19 = var13_13;
        do {
            block72 : {
                block83 : {
                    block73 : {
                        block77 : {
                            block78 : {
                                block79 : {
                                    block80 : {
                                        block81 : {
                                            block82 : {
                                                block75 : {
                                                    block76 : {
                                                        block71 : {
                                                            block74 : {
                                                                var19_20 = var2_2;
                                                                if (var11_11 == 2 || var1_1.atEnd()) break block73;
                                                                var13_13 = 0;
                                                                var20_21 = false;
                                                                var21_22 = null;
                                                                if (!UnicodeSet.resemblesPropertyPattern(var1_1, var6_6)) break block74;
                                                                var22_23 = 2;
                                                                break block71;
                                                            }
                                                            var9_9 = var1_1.getPos(var9_9);
                                                            var13_13 = var1_1.next(var6_6);
                                                            var20_21 = var1_1.isEscaped();
                                                            if (var13_13 != 91 || var20_21) ** GOTO lbl57
                                                            if (var11_11 == 1) {
                                                                var1_1.setPos(var9_9);
                                                                var22_23 = 1;
                                                            } else {
                                                                var11_11 = 1;
                                                                var7_7.append('[');
                                                                var9_9 = var1_1.getPos(var9_9);
                                                                var13_13 = var1_1.next(var6_6);
                                                                var20_21 = var1_1.isEscaped();
                                                                if (var13_13 == 94 && !var20_21) {
                                                                    var16_17 = true;
                                                                    var7_7.append('^');
                                                                    var9_9 = var1_1.getPos(var9_9);
                                                                    var13_13 = var1_1.next(var6_6);
                                                                    var1_1.isEscaped();
                                                                }
                                                                if (var13_13 == 45) {
                                                                    var20_21 = true;
                                                                    var11_11 = 1;
                                                                    var22_23 = 0;
                                                                } else {
                                                                    var1_1.setPos(var9_9);
                                                                    continue;
lbl57: // 1 sources:
                                                                    if (var19_20 != null && (var23_24 = var19_20.lookupMatcher(var13_13)) != null) {
                                                                        try {
                                                                            var23_24 = (UnicodeSet)var23_24;
                                                                            var21_22 = var23_24;
                                                                            var22_23 = 3;
                                                                            break block71;
                                                                        }
                                                                        catch (ClassCastException var23_25) {
                                                                            UnicodeSet.syntaxError(var1_1, "Syntax error");
                                                                        }
                                                                    }
                                                                    var22_23 = 0;
                                                                }
                                                            }
                                                        }
                                                        if (var22_23 == 0) break block75;
                                                        if (var10_10 == 1) {
                                                            if (var18_19 != 0) {
                                                                UnicodeSet.syntaxError(var1_1, "Char expected after operator");
                                                            }
                                                            this.add_unchecked(var14_14, var14_14);
                                                            UnicodeSet._appendToPat(var7_7, var14_14, false);
                                                            var10_10 = 0;
                                                            var18_19 = var13_13 = 0;
                                                        } else {
                                                            var18_19 = var13_13 = var18_19;
                                                        }
                                                        if (var18_19 == 45 || var18_19 == 38) {
                                                            var7_7.append((char)var18_19);
                                                        }
                                                        if (var21_22 == null) {
                                                            var21_22 = var8_8;
                                                            if (var8_8 == null) {
                                                                var21_22 = new UnicodeSet();
                                                            }
                                                            var8_8 = var21_22;
                                                        }
                                                        if (var22_23 != 1) {
                                                            if (var22_23 != 2) {
                                                                if (var22_23 == 3) {
                                                                    var21_22._toPattern(var7_7, false);
                                                                }
                                                            } else {
                                                                var1_1.skipIgnored(var6_6);
                                                                UnicodeSet.super.applyPropertyPattern(var1_1, var7_7, (SymbolTable)var19_20);
                                                            }
                                                        } else {
                                                            var21_22.applyPattern(var1_1, var2_2, var7_7, var4_4, var5_5 + 1);
                                                        }
                                                        var10_10 = var18_19;
                                                        var13_13 = 1;
                                                        var12_12 = 1;
                                                        if (var11_11 != 0) break block76;
                                                        this.set((UnicodeSet)var21_22);
                                                        var11_11 = 2;
                                                        var12_12 = var13_13;
                                                        break block73;
                                                    }
                                                    if (var10_10 != 0) {
                                                        if (var10_10 != 38) {
                                                            if (var10_10 == 45) {
                                                                this.removeAll((UnicodeSet)var21_22);
                                                            }
                                                        } else {
                                                            this.retainAll((UnicodeSet)var21_22);
                                                        }
                                                    } else {
                                                        this.addAll((UnicodeSet)var21_22);
                                                    }
                                                    var13_13 = 0;
                                                    var10_10 = 2;
                                                    var18_19 = var13_13;
                                                    continue;
                                                }
                                                if (var11_11 == 0) {
                                                    UnicodeSet.syntaxError(var1_1, "Missing '['");
                                                }
                                                if (var20_21) break block77;
                                                if (var13_13 == 36) break block78;
                                                if (var13_13 == 38) break block79;
                                                if (var13_13 == 45) break block80;
                                                if (var13_13 == 123) break block81;
                                                if (var13_13 == 93) break block82;
                                                if (var13_13 == 94) {
                                                    UnicodeSet.syntaxError(var1_1, "'^' not after '['");
                                                }
                                                break block77;
                                            }
                                            if (var10_10 == 1) {
                                                this.add_unchecked(var14_14, var14_14);
                                                UnicodeSet._appendToPat(var7_7, var14_14, false);
                                            }
                                            if (var18_19 == 45) {
                                                this.add_unchecked(var18_19, var18_19);
                                                var7_7.append((char)var18_19);
                                            } else if (var18_19 == 38) {
                                                UnicodeSet.syntaxError(var1_1, "Trailing '&'");
                                            }
                                            var7_7.append(']');
                                            var11_11 = 2;
                                            continue;
                                        }
                                        if (var18_19 != 0 && var18_19 != 45) {
                                            UnicodeSet.syntaxError(var1_1, "Missing operand after operator");
                                        }
                                        if (var10_10 == 1) {
                                            this.add_unchecked(var14_14, var14_14);
                                            UnicodeSet._appendToPat(var7_7, var14_14, false);
                                        }
                                        var10_10 = 0;
                                        if (var17_18 == null) {
                                            var17_18 = new StringBuilder();
                                        } else {
                                            var17_18.setLength(0);
                                        }
                                        var22_23 = 0;
                                        break block83;
                                    }
                                    var19_20 = var8_8;
                                    var21_22 = var17_18;
                                    var22_23 = var13_13;
                                    if (var18_19 == 0) {
                                        if (var10_10 != 0) {
                                            var13_13 = (char)var13_13;
                                            var8_8 = var19_20;
                                            var17_18 = var21_22;
                                            var18_19 = var13_13;
                                            continue;
                                        }
                                        if (var15_15 != null) {
                                            var13_13 = (char)var13_13;
                                            var8_8 = var19_20;
                                            var17_18 = var21_22;
                                            var18_19 = var13_13;
                                            continue;
                                        }
                                        this.add_unchecked(var13_13, var13_13);
                                        var22_23 = var1_1.next(var6_6);
                                        var20_21 = var1_1.isEscaped();
                                        if (var22_23 == 93 && !var20_21) {
                                            var7_7.append("-]");
                                            var11_11 = 2;
                                            var17_18 = var21_22;
                                            var8_8 = var19_20;
                                            continue;
                                        }
                                    }
                                    UnicodeSet.syntaxError(var1_1, "'-' not after char, string, or set");
                                    var13_13 = var22_23;
                                    break block77;
                                }
                                if (var10_10 == 2 && var18_19 == 0) {
                                    var18_19 = var13_13 = (int)((char)var13_13);
                                    continue;
                                }
                                UnicodeSet.syntaxError(var1_1, "'&' not after set");
                                break block77;
                            }
                            var9_9 = var1_1.getPos(var9_9);
                            var22_23 = var1_1.next(var6_6);
                            var20_21 = var1_1.isEscaped();
                            var13_13 = var22_23 == 93 && var20_21 == false ? 1 : 0;
                            if (var2_2 == null && var13_13 == 0) {
                                var13_13 = 36;
                                var1_1.setPos(var9_9);
                            } else {
                                if (var13_13 != 0 && var18_19 == 0) {
                                    if (var10_10 == 1) {
                                        this.add_unchecked(var14_14, var14_14);
                                        UnicodeSet._appendToPat(var7_7, var14_14, false);
                                    }
                                    this.add_unchecked(65535);
                                    var12_12 = 1;
                                    var7_7.append('$');
                                    var7_7.append(']');
                                    var11_11 = 2;
                                    continue;
                                }
                                UnicodeSet.syntaxError(var1_1, "Unquoted '$'");
                                var13_13 = var22_23;
                            }
                        }
                        if (var10_10 != 0) {
                            if (var10_10 != 1) {
                                if (var10_10 != 2) {
                                    var22_23 = var18_19;
                                    var13_13 = var14_14;
                                } else {
                                    if (var18_19 != 0) {
                                        UnicodeSet.syntaxError(var1_1, "Set expected after operator");
                                    }
                                    var10_10 = 1;
                                    var22_23 = var18_19;
                                }
                            } else if (var18_19 == 45) {
                                if (var15_15 != null) {
                                    UnicodeSet.syntaxError(var1_1, "Invalid range");
                                }
                                if (var14_14 >= var13_13) {
                                    UnicodeSet.syntaxError(var1_1, "Invalid range");
                                }
                                this.add_unchecked(var14_14, var13_13);
                                UnicodeSet._appendToPat(var7_7, var14_14, false);
                                var7_7.append((char)var18_19);
                                UnicodeSet._appendToPat(var7_7, var13_13, false);
                                var22_23 = 0;
                                var10_10 = 0;
                                var13_13 = var14_14;
                            } else {
                                this.add_unchecked(var14_14, var14_14);
                                UnicodeSet._appendToPat(var7_7, var14_14, false);
                                var22_23 = var18_19;
                            }
                        } else {
                            if (var18_19 == 45 && var15_15 != null) {
                                UnicodeSet.syntaxError(var1_1, "Invalid range");
                            }
                            var15_15 = null;
                            var10_10 = 1;
                            var22_23 = var18_19;
                        }
                        var18_19 = var22_23;
                        var14_14 = var13_13;
                        continue;
                    }
                    if (var11_11 != 2) {
                        UnicodeSet.syntaxError(var1_1, "Missing ']'");
                    }
                    var1_1.skipIgnored(var6_6);
                    if ((var4_4 & 2) != 0) {
                        this.closeOver(2);
                    }
                    if (var16_17) {
                        this.complement();
                    }
                    if (var12_12 != 0) {
                        UnicodeSet.append(var3_3, var7_7.toString());
                        return;
                    }
                    this.appendNewPattern(var3_3, false, true);
                    return;
                }
                while (!var1_1.atEnd()) {
                    var13_13 = var1_1.next(var6_6);
                    var20_21 = var1_1.isEscaped();
                    if (var13_13 == 125 && !var20_21) {
                        var13_13 = 1;
                        break block72;
                    }
                    UnicodeSet.appendCodePoint((Appendable)var17_18, var13_13);
                }
                var13_13 = var22_23;
            }
            if (var17_18.length() < 1 || var13_13 == 0) {
                UnicodeSet.syntaxError(var1_1, "Invalid multicharacter string");
            }
            var19_20 = var17_18.toString();
            if (var18_19 == 45) {
                var21_22 = var15_15 == null ? "" : var15_15;
                var22_23 = CharSequences.getSingleCodePoint((CharSequence)var21_22);
                var13_13 = CharSequences.getSingleCodePoint((CharSequence)var19_20);
                if (var22_23 != Integer.MAX_VALUE && var13_13 != Integer.MAX_VALUE) {
                    this.add(var22_23, var13_13);
                } else {
                    if (this.strings == UnicodeSet.EMPTY_STRINGS) {
                        this.strings = new TreeSet<String>();
                    }
                    try {
                        StringRange.expand((String)var15_15, (String)var19_20, true, this.strings);
                    }
                    catch (Exception var15_16) {
                        UnicodeSet.syntaxError(var1_1, var15_16.getMessage());
                    }
                }
                var15_15 = null;
                var13_13 = 0;
            } else {
                this.add((CharSequence)var19_20);
                var15_15 = var19_20;
                var13_13 = var18_19;
            }
            var7_7.append('{');
            UnicodeSet._appendToPat(var7_7, (String)var19_20, false);
            var7_7.append('}');
            var18_19 = var13_13;
        } while (true);
    }

    private UnicodeSet applyPropertyPattern(String string, ParsePosition parsePosition, SymbolTable symbolTable) {
        block16 : {
            int n;
            int n2;
            int n3;
            String string2;
            int n4;
            int n5;
            int n6;
            boolean bl;
            block15 : {
                int n7;
                block14 : {
                    n7 = parsePosition.getIndex();
                    if (n7 + 5 > string.length()) {
                        return null;
                    }
                    bl = false;
                    n5 = 0;
                    n3 = 0;
                    n = 0;
                    n4 = 2;
                    if (!string.regionMatches(n7, "[:", 0, 2)) break block14;
                    boolean bl2 = true;
                    n6 = n7 = PatternProps.skipWhiteSpace(string, n7 + 2);
                    bl = bl2;
                    n = n5;
                    n2 = n3;
                    if (n7 < string.length()) {
                        n6 = n7;
                        bl = bl2;
                        n = n5;
                        n2 = n3;
                        if (string.charAt(n7) == '^') {
                            n6 = n7 + 1;
                            n2 = 1;
                            bl = bl2;
                            n = n5;
                        }
                    }
                    break block15;
                }
                if (!string.regionMatches(true, n7, "\\p", 0, 2) && !string.regionMatches(n7, "\\N", 0, 2)) {
                    return null;
                }
                n5 = string.charAt(n7 + 1);
                n2 = n5 == 80 ? 1 : 0;
                n6 = n2;
                n2 = n;
                if (n5 == 78) {
                    n2 = 1;
                }
                n = n2;
                n2 = PatternProps.skipWhiteSpace(string, n7 + 2);
                if (n2 == string.length() || string.charAt(n2) != '{') break block16;
                n5 = n2 + 1;
                n2 = n6;
                n6 = n5;
            }
            if ((n5 = string.indexOf(string2 = bl ? ":]" : "}", n6)) < 0) {
                return null;
            }
            n3 = string.indexOf(61, n6);
            if (n3 >= 0 && n3 < n5 && n == 0) {
                string2 = string.substring(n6, n3);
                String string3 = string.substring(n3 + 1, n5);
                string = string2;
                string2 = string3;
            } else {
                String string4 = string.substring(n6, n5);
                string2 = "";
                string = string4;
                if (n != 0) {
                    string2 = string4;
                    string = "na";
                }
            }
            this.applyPropertyAlias(string, string2, symbolTable);
            if (n2 != 0) {
                this.complement();
            }
            n2 = bl ? n4 : 1;
            parsePosition.setIndex(n2 + n5);
            return this;
        }
        return null;
    }

    private void applyPropertyPattern(RuleCharacterIterator ruleCharacterIterator, Appendable appendable, SymbolTable symbolTable) {
        String string = ruleCharacterIterator.lookahead();
        ParsePosition parsePosition = new ParsePosition(0);
        this.applyPropertyPattern(string, parsePosition, symbolTable);
        if (parsePosition.getIndex() == 0) {
            UnicodeSet.syntaxError(ruleCharacterIterator, "Invalid property pattern");
        }
        ruleCharacterIterator.jumpahead(parsePosition.getIndex());
        UnicodeSet.append(appendable, string.substring(0, parsePosition.getIndex()));
    }

    private void checkFrozen() {
        if (!this.isFrozen()) {
            return;
        }
        throw new UnsupportedOperationException("Attempt to modify frozen object");
    }

    public static int compare(int n, CharSequence charSequence) {
        return -CharSequences.compare(charSequence, n);
    }

    public static int compare(CharSequence charSequence, int n) {
        return CharSequences.compare(charSequence, n);
    }

    public static <T extends Comparable<T>> int compare(Iterable<T> iterable, Iterable<T> iterable2) {
        return UnicodeSet.compare(iterable.iterator(), iterable2.iterator());
    }

    public static <T extends Comparable<T>> int compare(Collection<T> collection, Collection<T> collection2, ComparisonStyle comparisonStyle) {
        int n;
        if (comparisonStyle != ComparisonStyle.LEXICOGRAPHIC && (n = collection.size() - collection2.size()) != 0) {
            int n2 = 0;
            int n3 = 1;
            n = n < 0 ? 1 : 0;
            if (comparisonStyle == ComparisonStyle.SHORTER_FIRST) {
                n2 = 1;
            }
            if (n == n2) {
                n3 = -1;
            }
            return n3;
        }
        return UnicodeSet.compare(collection, collection2);
    }

    @Deprecated
    public static <T extends Comparable<T>> int compare(Iterator<T> iterator, Iterator<T> iterator2) {
        int n;
        do {
            if (!iterator.hasNext()) {
                n = iterator2.hasNext() ? -1 : 0;
                return n;
            }
            if (iterator2.hasNext()) continue;
            return 1;
        } while ((n = ((Comparable)iterator.next()).compareTo((Comparable)iterator2.next())) == 0);
        return n;
    }

    private boolean containsAll(String string, int n) {
        if (n >= string.length()) {
            return true;
        }
        int n2 = UTF16.charAt(string, n);
        if (this.contains(n2) && this.containsAll(string, UTF16.getCharCount(n2) + n)) {
            return true;
        }
        for (String string2 : this.strings) {
            if (!string.startsWith(string2, n) || !this.containsAll(string, string2.length() + n)) continue;
            return true;
        }
        return false;
    }

    private void ensureBufferCapacity(int n) {
        int[] arrn;
        int n2 = n;
        if (n > 1114113) {
            n2 = 1114113;
        }
        if ((arrn = this.buffer) != null && n2 <= arrn.length) {
            return;
        }
        this.buffer = new int[this.nextCapacity(n2)];
    }

    private void ensureCapacity(int n) {
        int n2 = n;
        if (n > 1114113) {
            n2 = 1114113;
        }
        if (n2 <= this.list.length) {
            return;
        }
        int[] arrn = new int[this.nextCapacity(n2)];
        System.arraycopy(this.list, 0, arrn, 0, this.len);
        this.list = arrn;
    }

    private final int findCodePoint(int n) {
        int[] arrn = this.list;
        if (n < arrn[0]) {
            return 0;
        }
        int n2 = this.len;
        if (n2 >= 2 && n >= arrn[n2 - 2]) {
            return n2 - 1;
        }
        int n3 = 0;
        n2 = this.len - 1;
        int n4;
        while ((n4 = n3 + n2 >>> 1) != n3) {
            if (n < this.list[n4]) {
                n2 = n4;
                continue;
            }
            n3 = n4;
        }
        return n2;
    }

    public static UnicodeSet from(CharSequence charSequence) {
        return new UnicodeSet().add(charSequence);
    }

    public static UnicodeSet fromAll(CharSequence charSequence) {
        return new UnicodeSet().addAll(charSequence);
    }

    @Deprecated
    public static XSymbolTable getDefaultXSymbolTable() {
        return XSYMBOL_TABLE;
    }

    private static int getSingleCP(CharSequence charSequence) {
        if (charSequence.length() >= 1) {
            if (charSequence.length() > 2) {
                return -1;
            }
            if (charSequence.length() == 1) {
                return charSequence.charAt(0);
            }
            int n = UTF16.charAt(charSequence, 0);
            if (n > 65535) {
                return n;
            }
            return -1;
        }
        throw new IllegalArgumentException("Can't use zero-length strings in UnicodeSet");
    }

    @Deprecated
    public static int getSingleCodePoint(CharSequence charSequence) {
        return CharSequences.getSingleCodePoint(charSequence);
    }

    private static int matchRest(Replaceable replaceable, int n, int n2, String string) {
        int n3;
        int n4 = string.length();
        if (n < n2) {
            n2 = n3 = n2 - n;
            if (n3 > n4) {
                n2 = n4;
            }
            for (n4 = 1; n4 < n2; ++n4) {
                if (replaceable.charAt(n + n4) == string.charAt(n4)) continue;
                return 0;
            }
            n3 = n2;
        } else {
            n2 = n3 = n - n2;
            if (n3 > n4) {
                n2 = n4;
            }
            int n5 = 1;
            do {
                n3 = n2;
                if (n5 >= n2) break;
                if (replaceable.charAt(n - n5) != string.charAt(n4 - 1 - n5)) {
                    return 0;
                }
                ++n5;
            } while (true);
        }
        return n3;
    }

    private static int matchesAt(CharSequence charSequence, int n, CharSequence charSequence2) {
        int n2 = charSequence2.length();
        if (charSequence.length() + n > n2) {
            return -1;
        }
        int n3 = 0;
        while (n3 < n2) {
            if (charSequence2.charAt(n3) != charSequence.charAt(n)) {
                return -1;
            }
            ++n3;
            ++n;
        }
        return n3;
    }

    private static final int max(int n, int n2) {
        if (n <= n2) {
            n = n2;
        }
        return n;
    }

    private static String mungeCharName(String string) {
        String string2 = PatternProps.trimWhiteSpace(string);
        string = null;
        for (int i = 0; i < string2.length(); ++i) {
            int n = string2.charAt(i);
            CharSequence charSequence = string;
            int n2 = n;
            if (PatternProps.isWhiteSpace(n)) {
                if (string == null) {
                    charSequence = new StringBuilder().append(string2, 0, i);
                } else {
                    charSequence = string;
                    if (((StringBuilder)((Object)string)).charAt(((StringBuilder)((Object)string)).length() - 1) == ' ') continue;
                }
                n2 = n = 32;
            }
            string = charSequence;
            if (charSequence == null) continue;
            ((StringBuilder)charSequence).append((char)n2);
            string = charSequence;
        }
        string = string == null ? string2 : ((StringBuilder)((Object)string)).toString();
        return string;
    }

    private int nextCapacity(int n) {
        int n2;
        if (n < 25) {
            return n + 25;
        }
        if (n <= 2500) {
            return n * 5;
        }
        n = n2 = n * 2;
        if (n2 > 1114113) {
            n = 1114113;
        }
        return n;
    }

    private int[] range(int n, int n2) {
        int[] arrn = this.rangeList;
        if (arrn == null) {
            this.rangeList = new int[]{n, n2 + 1, 1114112};
        } else {
            arrn[0] = n;
            arrn[1] = n2 + 1;
        }
        return this.rangeList;
    }

    public static boolean resemblesPattern(String string, int n) {
        boolean bl = n + 1 < string.length() && string.charAt(n) == '[' || UnicodeSet.resemblesPropertyPattern(string, n);
        return bl;
    }

    private static boolean resemblesPropertyPattern(RuleCharacterIterator ruleCharacterIterator, int n) {
        boolean bl = false;
        int n2 = n & -3;
        Object object = ruleCharacterIterator.getPos(null);
        n = ruleCharacterIterator.next(n2);
        if (n == 91 || n == 92) {
            n2 = ruleCharacterIterator.next(n2 & -5);
            bl = false;
            if (n == 91) {
                if (n2 == 58) {
                    bl = true;
                }
            } else if (n2 == 78 || n2 == 112 || n2 == 80) {
                bl = true;
            }
        }
        ruleCharacterIterator.setPos(object);
        return bl;
    }

    private static boolean resemblesPropertyPattern(String string, int n) {
        int n2 = string.length();
        boolean bl = false;
        if (n + 5 > n2) {
            return false;
        }
        if (string.regionMatches(n, "[:", 0, 2) || string.regionMatches(true, n, "\\p", 0, 2) || string.regionMatches(n, "\\N", 0, 2)) {
            bl = true;
        }
        return bl;
    }

    private UnicodeSet retain(int[] arrn, int n, int n2) {
        this.ensureBufferCapacity(this.len + n);
        int n3 = 0;
        int[] arrn2 = this.list;
        int n4 = 0 + 1;
        int n5 = arrn2[0];
        int n6 = 0 + 1;
        int n7 = arrn[0];
        n = n2;
        n2 = n5;
        do {
            block22 : {
                block21 : {
                    block20 : {
                        if (n == 0) break block20;
                        if (n != 1) {
                            if (n != 2) {
                                if (n != 3) continue;
                                if (n2 < n7) {
                                    this.buffer[n3] = n2;
                                    n2 = this.list[n4];
                                    n ^= 1;
                                    ++n4;
                                    ++n3;
                                    continue;
                                }
                                if (n7 < n2) {
                                    this.buffer[n3] = n7;
                                    n7 = arrn[n6];
                                    n ^= 2;
                                    ++n6;
                                    ++n3;
                                    continue;
                                }
                                if (n2 != 1114112) {
                                    this.buffer[n3] = n2;
                                    n2 = this.list[n4];
                                    n7 = arrn[n6];
                                    n = n ^ 1 ^ 2;
                                    ++n6;
                                    ++n4;
                                    ++n3;
                                    continue;
                                }
                            } else {
                                if (n7 < n2) {
                                    n7 = arrn[n6];
                                    n ^= 2;
                                    ++n6;
                                    continue;
                                }
                                if (n2 < n7) {
                                    this.buffer[n3] = n2;
                                    n2 = this.list[n4];
                                    n ^= 1;
                                    ++n4;
                                    ++n3;
                                    continue;
                                }
                                if (n2 != 1114112) {
                                    n2 = this.list[n4];
                                    n7 = arrn[n6];
                                    n = n ^ 1 ^ 2;
                                    ++n6;
                                    ++n4;
                                    continue;
                                }
                            }
                        } else {
                            if (n2 < n7) {
                                n2 = this.list[n4];
                                n ^= 1;
                                ++n4;
                                continue;
                            }
                            if (n7 < n2) {
                                this.buffer[n3] = n7;
                                n7 = arrn[n6];
                                n ^= 2;
                                ++n6;
                                ++n3;
                                continue;
                            }
                            if (n2 != 1114112) {
                                n2 = this.list[n4];
                                n7 = arrn[n6];
                                n = n ^ 1 ^ 2;
                                ++n6;
                                ++n4;
                                continue;
                            }
                        }
                        break block21;
                    }
                    if (n2 < n7) {
                        n2 = this.list[n4];
                        n ^= 1;
                        ++n4;
                        continue;
                    }
                    if (n7 < n2) {
                        n7 = arrn[n6];
                        n ^= 2;
                        ++n6;
                        continue;
                    }
                    if (n2 != 1114112) break block22;
                }
                arrn2 = this.buffer;
                arrn2[n3] = 1114112;
                this.len = n3 + 1;
                arrn = this.list;
                this.list = arrn2;
                this.buffer = arrn;
                this.pat = null;
                return this;
            }
            this.buffer[n3] = n2;
            n2 = this.list[n4];
            n7 = arrn[n6];
            n = n ^ 1 ^ 2;
            ++n6;
            ++n4;
            ++n3;
        } while (true);
    }

    @Deprecated
    public static void setDefaultXSymbolTable(XSymbolTable xSymbolTable) {
        CharacterPropertiesImpl.clear();
        XSYMBOL_TABLE = xSymbolTable;
    }

    private int spanCodePointsAndCount(CharSequence charSequence, int n, SpanCondition spanCondition, OutputInt outputInt) {
        int n2;
        boolean bl = spanCondition != SpanCondition.NOT_CONTAINED;
        int n3 = n;
        int n4 = charSequence.length();
        n = 0;
        while (bl == this.contains(n2 = Character.codePointAt(charSequence, n3))) {
            int n5 = n + 1;
            n3 = n2 = n3 + Character.charCount(n2);
            n = n5;
            if (n2 < n4) continue;
            n = n5;
            n3 = n2;
            break;
        }
        if (outputInt != null) {
            outputInt.value = n;
        }
        return n3;
    }

    private static void syntaxError(RuleCharacterIterator ruleCharacterIterator, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Error: ");
        stringBuilder.append(string);
        stringBuilder.append(" at \"");
        stringBuilder.append(Utility.escape(ruleCharacterIterator.toString()));
        stringBuilder.append('\"');
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static String[] toArray(UnicodeSet unicodeSet) {
        return UnicodeSet.addAllTo(unicodeSet, new String[unicodeSet.size()]);
    }

    private UnicodeSet xor(int[] arrn, int n, int n2) {
        this.ensureBufferCapacity(this.len + n);
        int n3 = 0;
        int[] arrn2 = this.list;
        int n4 = 0 + 1;
        int n5 = arrn2[0];
        if (n2 != 1 && n2 != 2) {
            n2 = 0 + 1;
            n = arrn[0];
        } else if (arrn[0] == 0) {
            n2 = 0 + 1;
            n = arrn[n2];
        } else {
            n2 = 0;
            n = 0;
        }
        do {
            if (n5 < n) {
                this.buffer[n3] = n5;
                n5 = this.list[n4];
                ++n4;
                ++n3;
                continue;
            }
            if (n < n5) {
                this.buffer[n3] = n;
                n = arrn[n2];
                ++n2;
                ++n3;
                continue;
            }
            if (n5 == 1114112) break;
            n5 = this.list[n4];
            n = arrn[n2];
            ++n2;
            ++n4;
        } while (true);
        arrn2 = this.buffer;
        arrn2[n3] = 1114112;
        this.len = n3 + 1;
        arrn = this.list;
        this.list = arrn2;
        this.buffer = arrn;
        this.pat = null;
        return this;
    }

    public StringBuffer _generatePattern(StringBuffer stringBuffer, boolean bl) {
        return this._generatePattern(stringBuffer, bl, true);
    }

    public StringBuffer _generatePattern(StringBuffer stringBuffer, boolean bl, boolean bl2) {
        return this.appendNewPattern(stringBuffer, bl, bl2);
    }

    public final UnicodeSet add(int n) {
        this.checkFrozen();
        return this.add_unchecked(n);
    }

    public UnicodeSet add(int n, int n2) {
        this.checkFrozen();
        return this.add_unchecked(n, n2);
    }

    public final UnicodeSet add(CharSequence charSequence) {
        this.checkFrozen();
        int n = UnicodeSet.getSingleCP(charSequence);
        if (n < 0) {
            if (!this.strings.contains(charSequence = charSequence.toString())) {
                this.addString(charSequence);
                this.pat = null;
            }
        } else {
            this.add_unchecked(n, n);
        }
        return this;
    }

    public UnicodeSet add(Iterable<?> iterable) {
        return this.addAll(iterable);
    }

    public UnicodeSet addAll(int n, int n2) {
        this.checkFrozen();
        return this.add_unchecked(n, n2);
    }

    public UnicodeSet addAll(UnicodeSet unicodeSet) {
        this.checkFrozen();
        this.add(unicodeSet.list, unicodeSet.len, 0);
        if (unicodeSet.hasStrings()) {
            SortedSet<String> sortedSet = this.strings;
            if (sortedSet == EMPTY_STRINGS) {
                this.strings = new TreeSet<String>(unicodeSet.strings);
            } else {
                sortedSet.addAll(unicodeSet.strings);
            }
        }
        return this;
    }

    public final UnicodeSet addAll(CharSequence charSequence) {
        int n;
        this.checkFrozen();
        for (int i = 0; i < charSequence.length(); i += UTF16.getCharCount((int)n)) {
            n = UTF16.charAt(charSequence, i);
            this.add_unchecked(n, n);
        }
        return this;
    }

    public UnicodeSet addAll(Iterable<?> object) {
        this.checkFrozen();
        object = object.iterator();
        while (object.hasNext()) {
            this.add(object.next().toString());
        }
        return this;
    }

    public <T extends CharSequence> UnicodeSet addAll(T ... arrT) {
        this.checkFrozen();
        int n = arrT.length;
        for (int i = 0; i < n; ++i) {
            this.add((CharSequence)arrT[i]);
        }
        return this;
    }

    public <T extends Collection<String>> T addAllTo(T t) {
        return UnicodeSet.addAllTo(this, t);
    }

    public String[] addAllTo(String[] arrstring) {
        return UnicodeSet.addAllTo(this, arrstring);
    }

    @Deprecated
    public UnicodeSet addBridges(UnicodeSet unicodeSet) {
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(new UnicodeSet(this).complement());
        while (unicodeSetIterator.nextRange()) {
            if (unicodeSetIterator.codepoint == 0 || unicodeSetIterator.codepoint == UnicodeSetIterator.IS_STRING || unicodeSetIterator.codepointEnd == 1114111 || !unicodeSet.contains(unicodeSetIterator.codepoint, unicodeSetIterator.codepointEnd)) continue;
            this.add(unicodeSetIterator.codepoint, unicodeSetIterator.codepointEnd);
        }
        return this;
    }

    @Override
    public void addMatchSetTo(UnicodeSet unicodeSet) {
        unicodeSet.addAll(this);
    }

    public UnicodeSet applyIntPropertyValue(int n, int n2) {
        block11 : {
            block8 : {
                block10 : {
                    block9 : {
                        block7 : {
                            if (n != 8192) break block7;
                            UnicodeSet unicodeSet = CharacterPropertiesImpl.getInclusionsForProperty(n);
                            this.applyFilter(new GeneralCategoryMaskFilter(n2), unicodeSet);
                            break block8;
                        }
                        if (n != 28672) break block9;
                        UnicodeSet unicodeSet = CharacterPropertiesImpl.getInclusionsForProperty(n);
                        this.applyFilter(new ScriptExtensionsFilter(n2), unicodeSet);
                        break block8;
                    }
                    if (n < 0 || n >= 65) break block10;
                    if (n2 != 0 && n2 != 1) {
                        this.clear();
                    } else {
                        this.set(CharacterProperties.getBinaryPropertySet(n));
                        if (n2 == 0) {
                            this.complement();
                        }
                    }
                    break block8;
                }
                if (4096 > n || n >= 4121) break block11;
                UnicodeSet unicodeSet = CharacterPropertiesImpl.getInclusionsForProperty(n);
                this.applyFilter(new IntPropertyFilter(n, n2), unicodeSet);
            }
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unsupported property ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final UnicodeSet applyPattern(String string) {
        this.checkFrozen();
        return this.applyPattern(string, null, null, 1);
    }

    public UnicodeSet applyPattern(String string, int n) {
        this.checkFrozen();
        return this.applyPattern(string, null, null, n);
    }

    @Deprecated
    public UnicodeSet applyPattern(String string, ParsePosition object, SymbolTable symbolTable, int n) {
        int n2 = object == null ? 1 : 0;
        if (n2 != 0) {
            object = new ParsePosition(0);
        }
        StringBuilder stringBuilder = new StringBuilder();
        RuleCharacterIterator ruleCharacterIterator = new RuleCharacterIterator(string, symbolTable, (ParsePosition)object);
        this.applyPattern(ruleCharacterIterator, symbolTable, stringBuilder, n, 0);
        if (ruleCharacterIterator.inVariable()) {
            UnicodeSet.syntaxError(ruleCharacterIterator, "Extra chars in variable value");
        }
        this.pat = stringBuilder.toString();
        if (n2 != 0) {
            int n3;
            n2 = n3 = ((ParsePosition)object).getIndex();
            if ((n & 1) != 0) {
                n2 = PatternProps.skipWhiteSpace(string, n3);
            }
            if (n2 != string.length()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Parse of \"");
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append("\" failed at ");
                ((StringBuilder)object).append(n2);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
        }
        return this;
    }

    public UnicodeSet applyPattern(String string, boolean bl) {
        this.checkFrozen();
        return this.applyPattern(string, null, null, (int)bl);
    }

    public UnicodeSet applyPropertyAlias(String string, String string2) {
        return this.applyPropertyAlias(string, string2, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public UnicodeSet applyPropertyAlias(String string, String string2, SymbolTable object) {
        int n;
        int n2;
        this.checkFrozen();
        boolean bl = false;
        if (object != null && object instanceof XSymbolTable && ((XSymbolTable)object).applyPropertyAlias(string, string2, this)) {
            return this;
        }
        object = XSYMBOL_TABLE;
        if (object != null && ((XSymbolTable)object).applyPropertyAlias(string, string2, this)) {
            return this;
        }
        if (string2.length() > 0) {
            n = n2 = UCharacter.getPropertyEnum(string);
            if (n2 == 4101) {
                n = 8192;
            }
            if (n >= 0 && n < 65 || n >= 4096 && n < 4121 || n >= 8192 && n < 8193) {
                try {
                    n2 = UCharacter.getPropertyValueEnum(n, string2);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    if (n != 4098 && n != 4112) {
                        if (n != 4113) throw illegalArgumentException;
                    }
                    if ((n2 = Integer.parseInt(PatternProps.trimWhiteSpace(string2))) < 0) throw illegalArgumentException;
                    if (n2 > 255) throw illegalArgumentException;
                }
            } else {
                if (n == 12288) {
                    this.applyFilter(new NumericValueFilter(Double.parseDouble(PatternProps.trimWhiteSpace(string2))), CharacterPropertiesImpl.getInclusionsForProperty(n));
                    return this;
                }
                if (n == 16384) {
                    this.applyFilter(new VersionFilter(VersionInfo.getInstance(UnicodeSet.mungeCharName(string2))), CharacterPropertiesImpl.getInclusionsForProperty(n));
                    return this;
                }
                if (n == 16389) {
                    n2 = UCharacter.getCharFromExtendedName(UnicodeSet.mungeCharName(string2));
                    if (n2 == -1) throw new IllegalArgumentException("Invalid character name");
                    this.clear();
                    this.add_unchecked(n2);
                    return this;
                }
                if (n == 16395) throw new IllegalArgumentException("Unicode_1_Name (na1) not supported");
                if (n != 28672) throw new IllegalArgumentException("Unsupported property");
                n2 = UCharacter.getPropertyValueEnum(4106, string2);
            }
        } else {
            object = UPropertyAliases.INSTANCE;
            n2 = ((UPropertyAliases)object).getPropertyValueEnum(8192, string);
            if (n2 == -1) {
                n2 = ((UPropertyAliases)object).getPropertyValueEnum(4106, string);
                if (n2 == -1) {
                    n2 = n = ((UPropertyAliases)object).getPropertyEnum(string);
                    if (n == -1) {
                        n2 = -1;
                    }
                    if (n2 >= 0 && n2 < 65) {
                        int n3 = 1;
                        n = n2;
                        n2 = n3;
                    } else {
                        if (n2 != -1) throw new IllegalArgumentException("Missing property value");
                        if (UPropertyAliases.compare(ANY_ID, string) == 0) {
                            this.set(0, 1114111);
                            return this;
                        }
                        if (UPropertyAliases.compare(ASCII_ID, string) == 0) {
                            this.set(0, 127);
                            return this;
                        }
                        if (UPropertyAliases.compare(ASSIGNED, string) != 0) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Invalid property alias: ");
                            ((StringBuilder)object).append(string);
                            ((StringBuilder)object).append("=");
                            ((StringBuilder)object).append(string2);
                            throw new IllegalArgumentException(((StringBuilder)object).toString());
                        }
                        bl = true;
                        n = 8192;
                        n2 = 1;
                    }
                } else {
                    n = 4106;
                }
            } else {
                n = 8192;
            }
        }
        this.applyIntPropertyValue(n, n2);
        if (!bl) return this;
        this.complement();
        return this;
    }

    public int charAt(int n) {
        if (n >= 0) {
            int n2 = this.len;
            int n3 = 0;
            while (n3 < (n2 & -2)) {
                int[] arrn = this.list;
                int n4 = n3 + 1;
                int n5 = arrn[n3];
                n3 = arrn[n4] - n5;
                if (n < n3) {
                    return n5 + n;
                }
                n -= n3;
                n3 = n4 + 1;
            }
        }
        return -1;
    }

    public UnicodeSet clear() {
        this.checkFrozen();
        this.list[0] = 1114112;
        this.len = 1;
        this.pat = null;
        if (this.hasStrings()) {
            this.strings.clear();
        }
        return this;
    }

    public Object clone() {
        if (this.isFrozen()) {
            return this;
        }
        return new UnicodeSet(this);
    }

    @Override
    public UnicodeSet cloneAsThawed() {
        UnicodeSet unicodeSet = new UnicodeSet(this);
        return unicodeSet;
    }

    public UnicodeSet closeOver(int n) {
        this.checkFrozen();
        if ((n & 6) != 0) {
            UCaseProps uCaseProps = UCaseProps.INSTANCE;
            UnicodeSet unicodeSet = new UnicodeSet(this);
            Object object = ULocale.ROOT;
            if ((n & 2) != 0 && unicodeSet.hasStrings()) {
                unicodeSet.strings.clear();
            }
            int n2 = this.getRangeCount();
            StringBuilder charSequence2 = new StringBuilder();
            for (int i = 0; i < n2; ++i) {
                int n3;
                int n4 = this.getRangeEnd(i);
                if ((n & 2) != 0) {
                    for (n3 = this.getRangeStart((int)i); n3 <= n4; ++n3) {
                        uCaseProps.addCaseClosure(n3, unicodeSet);
                    }
                    continue;
                }
                while (n3 <= n4) {
                    UnicodeSet.addCaseMapping(unicodeSet, uCaseProps.toFullLower(n3, null, charSequence2, 1), charSequence2);
                    UnicodeSet.addCaseMapping(unicodeSet, uCaseProps.toFullTitle(n3, null, charSequence2, 1), charSequence2);
                    UnicodeSet.addCaseMapping(unicodeSet, uCaseProps.toFullUpper(n3, null, charSequence2, 1), charSequence2);
                    UnicodeSet.addCaseMapping(unicodeSet, uCaseProps.toFullFolding(n3, charSequence2, 0), charSequence2);
                    ++n3;
                }
            }
            if (this.hasStrings()) {
                if ((n & 2) != 0) {
                    object = this.strings.iterator();
                    while (object.hasNext()) {
                        String string = UCharacter.foldCase((String)object.next(), 0);
                        if (uCaseProps.addStringCaseClosure(string, unicodeSet)) continue;
                        unicodeSet.add(string);
                    }
                } else {
                    BreakIterator breakIterator = BreakIterator.getWordInstance((ULocale)object);
                    for (String string : this.strings) {
                        unicodeSet.add(UCharacter.toLowerCase((ULocale)object, string));
                        unicodeSet.add(UCharacter.toTitleCase((ULocale)object, string, breakIterator));
                        unicodeSet.add(UCharacter.toUpperCase((ULocale)object, string));
                        unicodeSet.add(UCharacter.foldCase(string, 0));
                    }
                }
            }
            this.set(unicodeSet);
        }
        return this;
    }

    public UnicodeSet compact() {
        this.checkFrozen();
        int n = this.len;
        Object object = this.list;
        if (n + 7 < ((int[])object).length) {
            this.list = Arrays.copyOf((int[])object, n);
        }
        this.rangeList = null;
        this.buffer = null;
        object = this.strings;
        if (object != EMPTY_STRINGS && object.isEmpty()) {
            this.strings = EMPTY_STRINGS;
        }
        return this;
    }

    @Override
    public int compareTo(UnicodeSet unicodeSet) {
        return this.compareTo(unicodeSet, ComparisonStyle.SHORTER_FIRST);
    }

    public int compareTo(UnicodeSet unicodeSet, ComparisonStyle arrn) {
        int n;
        int[] arrn2 = ComparisonStyle.LEXICOGRAPHIC;
        int n2 = -1;
        int n3 = 0;
        if (arrn != arrn2 && (n = this.size() - unicodeSet.size()) != 0) {
            n = n < 0 ? 1 : 0;
            if (arrn == ComparisonStyle.SHORTER_FIRST) {
                n3 = 1;
            }
            if (n != n3) {
                n2 = 1;
            }
            return n2;
        }
        n = 0;
        do {
            arrn = this.list;
            n3 = arrn[n];
            arrn2 = unicodeSet.list;
            if ((n3 -= arrn2[n]) != 0) {
                if (arrn[n] == 1114112) {
                    if (!this.hasStrings()) {
                        return 1;
                    }
                    return UnicodeSet.compare(this.strings.first(), unicodeSet.list[n]);
                }
                if (arrn2[n] == 1114112) {
                    if (!unicodeSet.hasStrings()) {
                        return -1;
                    }
                    n = UnicodeSet.compare(unicodeSet.strings.first(), this.list[n]);
                    if (n <= 0) {
                        n2 = n < 0 ? 1 : 0;
                    }
                    return n2;
                }
                n2 = (n & 1) == 0 ? n3 : -n3;
                return n2;
            }
            if (arrn[n] == 1114112) {
                return UnicodeSet.compare(this.strings, unicodeSet.strings);
            }
            ++n;
        } while (true);
    }

    @Override
    public int compareTo(Iterable<String> iterable) {
        return UnicodeSet.compare(this, iterable);
    }

    public UnicodeSet complement() {
        this.checkFrozen();
        int[] arrn = this.list;
        if (arrn[0] == 0) {
            System.arraycopy(arrn, 1, arrn, 0, this.len - 1);
            --this.len;
        } else {
            this.ensureCapacity(this.len + 1);
            arrn = this.list;
            System.arraycopy(arrn, 0, arrn, 1, this.len);
            this.list[0] = 0;
            ++this.len;
        }
        this.pat = null;
        return this;
    }

    public final UnicodeSet complement(int n) {
        return this.complement(n, n);
    }

    public UnicodeSet complement(int n, int n2) {
        this.checkFrozen();
        if (n >= 0 && n <= 1114111) {
            if (n2 >= 0 && n2 <= 1114111) {
                if (n <= n2) {
                    this.xor(this.range(n, n2), 2, 0);
                }
                this.pat = null;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid code point U+");
            stringBuilder.append(Utility.hex(n2, 6));
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid code point U+");
        stringBuilder.append(Utility.hex(n, 6));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final UnicodeSet complement(CharSequence charSequence) {
        this.checkFrozen();
        int n = UnicodeSet.getSingleCP(charSequence);
        if (n < 0) {
            if (this.strings.contains(charSequence = charSequence.toString())) {
                this.strings.remove(charSequence);
            } else {
                this.addString(charSequence);
            }
            this.pat = null;
        } else {
            this.complement(n, n);
        }
        return this;
    }

    public UnicodeSet complementAll(UnicodeSet unicodeSet) {
        this.checkFrozen();
        this.xor(unicodeSet.list, unicodeSet.len, 0);
        if (unicodeSet.hasStrings()) {
            SortedSet<String> sortedSet = this.strings;
            if (sortedSet == EMPTY_STRINGS) {
                this.strings = new TreeSet<String>(unicodeSet.strings);
            } else {
                SortedSetRelation.doOperation(sortedSet, 5, unicodeSet.strings);
            }
        }
        return this;
    }

    public final UnicodeSet complementAll(CharSequence charSequence) {
        return this.complementAll(UnicodeSet.fromAll(charSequence));
    }

    @Override
    public boolean contains(int n) {
        if (n >= 0 && n <= 1114111) {
            if (this.bmpSet != null) {
                return this.bmpSet.contains(n);
            }
            if (this.stringSpan != null) {
                return this.stringSpan.contains(n);
            }
            boolean bl = (this.findCodePoint(n) & 1) != 0;
            return bl;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid code point U+");
        stringBuilder.append(Utility.hex(n, 6));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public boolean contains(int n, int n2) {
        if (n >= 0 && n <= 1114111) {
            if (n2 >= 0 && n2 <= 1114111) {
                boolean bl = ((n = this.findCodePoint(n)) & 1) != 0 && n2 < this.list[n];
                return bl;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid code point U+");
            stringBuilder.append(Utility.hex(n2, 6));
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid code point U+");
        stringBuilder.append(Utility.hex(n, 6));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final boolean contains(CharSequence charSequence) {
        int n = UnicodeSet.getSingleCP(charSequence);
        if (n < 0) {
            return this.strings.contains(charSequence.toString());
        }
        return this.contains(n);
    }

    public boolean containsAll(UnicodeSet unicodeSet) {
        int[] arrn = unicodeSet.list;
        int n = 1;
        int n2 = 1;
        int n3 = 0;
        int n4 = 0;
        int n5 = this.len;
        int n6 = unicodeSet.len - 1;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        do {
            int n11;
            int n12;
            block10 : {
                block11 : {
                    block9 : {
                        block7 : {
                            block8 : {
                                n11 = n3;
                                if (n == 0) break block7;
                                if (n3 < n5 - 1) break block8;
                                if (n2 == 0 || n4 < n6) {
                                    return false;
                                }
                                break block9;
                            }
                            int[] arrn2 = this.list;
                            n = n3 + 1;
                            n7 = arrn2[n3];
                            n9 = arrn2[n];
                            n11 = n + 1;
                        }
                        n12 = n4;
                        if (n2 == 0) break block10;
                        if (n4 < n6) break block11;
                    }
                    return this.strings.containsAll(unicodeSet.strings);
                }
                n2 = n4 + 1;
                n8 = arrn[n4];
                n10 = arrn[n2];
                n12 = n2 + 1;
            }
            if (n8 >= n9) {
                n = 1;
                n2 = 0;
                n3 = n11;
                n4 = n12;
                continue;
            }
            if (n8 < n7 || n10 > n9) break;
            n = 0;
            n2 = 1;
            n3 = n11;
            n4 = n12;
        } while (true);
        return false;
    }

    public <T extends CharSequence> boolean containsAll(Iterable<T> object) {
        object = object.iterator();
        while (object.hasNext()) {
            if (this.contains((CharSequence)object.next())) continue;
            return false;
        }
        return true;
    }

    public boolean containsAll(String string) {
        int n;
        for (int i = 0; i < string.length(); i += UTF16.getCharCount((int)n)) {
            n = UTF16.charAt(string, i);
            if (this.contains(n)) continue;
            if (!this.hasStrings()) {
                return false;
            }
            return this.containsAll(string, 0);
        }
        return true;
    }

    public boolean containsNone(int n, int n2) {
        if (n >= 0 && n <= 1114111) {
            if (n2 >= 0 && n2 <= 1114111) {
                boolean bl;
                int n3;
                int[] arrn;
                int n4 = -1;
                do {
                    arrn = this.list;
                    bl = true;
                    n4 = n3 = n4 + 1;
                } while (n >= arrn[n3]);
                if ((n3 & 1) != 0 || n2 >= arrn[n3]) {
                    bl = false;
                }
                return bl;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid code point U+");
            stringBuilder.append(Utility.hex(n2, 6));
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid code point U+");
        stringBuilder.append(Utility.hex(n, 6));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public boolean containsNone(UnicodeSet unicodeSet) {
        int[] arrn = unicodeSet.list;
        int n = 1;
        int n2 = 1;
        int n3 = 0;
        int n4 = 0;
        int n5 = this.len;
        int n6 = unicodeSet.len;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        do {
            int n11;
            int n12;
            block7 : {
                block8 : {
                    block6 : {
                        block5 : {
                            n11 = n3;
                            if (n == 0) break block5;
                            if (n3 >= n5 - 1) break block6;
                            int[] arrn2 = this.list;
                            n = n3 + 1;
                            n7 = arrn2[n3];
                            n9 = arrn2[n];
                            n11 = n + 1;
                        }
                        n12 = n4;
                        if (n2 == 0) break block7;
                        if (n4 < n6 - 1) break block8;
                    }
                    return SortedSetRelation.hasRelation(this.strings, 5, unicodeSet.strings);
                }
                n2 = n4 + 1;
                n8 = arrn[n4];
                n10 = arrn[n2];
                n12 = n2 + 1;
            }
            if (n8 >= n9) {
                n = 1;
                n2 = 0;
                n3 = n11;
                n4 = n12;
                continue;
            }
            if (n7 < n10) break;
            n = 0;
            n2 = 1;
            n3 = n11;
            n4 = n12;
        } while (true);
        return false;
    }

    public boolean containsNone(CharSequence charSequence) {
        boolean bl = this.span(charSequence, SpanCondition.NOT_CONTAINED) == charSequence.length();
        return bl;
    }

    public <T extends CharSequence> boolean containsNone(Iterable<T> object) {
        object = object.iterator();
        while (object.hasNext()) {
            if (!this.contains((CharSequence)object.next())) continue;
            return false;
        }
        return true;
    }

    public final boolean containsSome(int n, int n2) {
        return this.containsNone(n, n2) ^ true;
    }

    public final boolean containsSome(UnicodeSet unicodeSet) {
        return this.containsNone(unicodeSet) ^ true;
    }

    public final boolean containsSome(CharSequence charSequence) {
        return this.containsNone(charSequence) ^ true;
    }

    public final <T extends CharSequence> boolean containsSome(Iterable<T> iterable) {
        return this.containsNone(iterable) ^ true;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        object = (UnicodeSet)object;
        if (this.len != ((UnicodeSet)object).len) {
            return false;
        }
        int n = 0;
        do {
            block8 : {
                if (n >= this.len) break;
                if (this.list[n] == ((UnicodeSet)object).list[n]) break block8;
                return false;
            }
            ++n;
        } while (true);
        try {
            boolean bl = this.strings.equals(((UnicodeSet)object).strings);
            return bl;
        }
        catch (Exception exception) {
            return false;
        }
    }

    @Deprecated
    public int findIn(CharSequence charSequence, int n, boolean bl) {
        int n2;
        while (n < charSequence.length() && this.contains(n2 = UTF16.charAt(charSequence, n)) == bl) {
            n += UTF16.getCharCount(n2);
        }
        return n;
    }

    @Deprecated
    public int findLastIn(CharSequence charSequence, int n, boolean bl) {
        block1 : {
            int n2;
            int n3 = -1;
            --n;
            while (n >= 0 && this.contains(n2 = UTF16.charAt(charSequence, n)) == bl) {
                n -= UTF16.getCharCount(n2);
            }
            if (n >= 0) break block1;
            n = n3;
        }
        return n;
    }

    @Override
    public UnicodeSet freeze() {
        if (!this.isFrozen()) {
            this.compact();
            if (this.hasStrings()) {
                this.stringSpan = new UnicodeSetStringSpan(this, new ArrayList<String>(this.strings), 127);
            }
            if (this.stringSpan == null || !this.stringSpan.needsStringSpanUTF16()) {
                this.bmpSet = new BMPSet(this.list, this.len);
            }
        }
        return this;
    }

    public int getRangeCount() {
        return this.len / 2;
    }

    public int getRangeEnd(int n) {
        return this.list[n * 2 + 1] - 1;
    }

    public int getRangeStart(int n) {
        return this.list[n * 2];
    }

    @Deprecated
    public String getRegexEquivalent() {
        if (!this.hasStrings()) {
            return this.toString();
        }
        StringBuilder stringBuilder = new StringBuilder("(?:");
        this.appendNewPattern(stringBuilder, true, false);
        for (String string : this.strings) {
            stringBuilder.append('|');
            UnicodeSet._appendToPat(stringBuilder, string, true);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    boolean hasStrings() {
        return this.strings.isEmpty() ^ true;
    }

    public int hashCode() {
        int n = this.len;
        for (int i = 0; i < this.len; ++i) {
            n = n * 1000003 + this.list[i];
        }
        return n;
    }

    public int indexOf(int n) {
        if (n >= 0 && n <= 1114111) {
            int n2 = 0;
            int n3 = 0;
            do {
                int[] arrn = this.list;
                int n4 = n2 + 1;
                if (n < (n2 = arrn[n2])) {
                    return -1;
                }
                int n5 = arrn[n4];
                if (n < n5) {
                    return n3 + n - n2;
                }
                n3 += n5 - n2;
                n2 = n4 + 1;
            } while (true);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid code point U+");
        stringBuilder.append(Utility.hex(n, 6));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public boolean isEmpty() {
        int n = this.len;
        boolean bl = true;
        if (n != 1 || this.hasStrings()) {
            bl = false;
        }
        return bl;
    }

    @Override
    public boolean isFrozen() {
        boolean bl = this.bmpSet != null || this.stringSpan != null;
        return bl;
    }

    @Override
    public Iterator<String> iterator() {
        return new UnicodeSetIterator2(this);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public int matches(Replaceable var1_1, int[] var2_2, int var3_3, boolean var4_4) {
        var5_5 = var2_2[0];
        var6_6 = 2;
        if (var5_5 == var3_3) {
            if (this.contains(65535) == false) return 0;
            var3_3 = var6_6;
            if (var4_4 == false) return var3_3;
            return 1;
        }
        if (this.hasStrings() == false) return super.matches(var1_1, var2_2, var3_3, var4_4);
        var7_7 = var2_2[0] < var3_3;
        var8_8 = var1_1.charAt(var2_2[0]);
        var6_6 = 0;
        var9_9 = this.strings.iterator();
        do lbl-1000: // 5 sources:
        {
            var5_5 = var6_6;
            if (!var9_9.hasNext()) break;
            var10_10 = (String)var9_9.next();
            var5_5 = var7_7 != false ? 0 : var10_10.length() - 1;
            var5_5 = var10_10.charAt(var5_5);
            if (var7_7 && var5_5 > var8_8) {
                var5_5 = var6_6;
                break;
            }
            if (var5_5 != var8_8) ** GOTO lbl-1000
            var11_11 = UnicodeSet.matchRest(var1_1, var2_2[0], var3_3, var10_10);
            if (var4_4 && var11_11 == (var5_5 = var7_7 != false ? var3_3 - var2_2[0] : var2_2[0] - var3_3)) {
                return 1;
            }
            if (var11_11 != var10_10.length()) ** GOTO lbl-1000
            var5_5 = var6_6;
            if (var11_11 > var6_6) {
                var5_5 = var11_11;
            }
            var6_6 = var5_5;
            if (!var7_7) ** GOTO lbl-1000
            var6_6 = var5_5;
        } while (var11_11 >= var5_5);
        if (var5_5 == 0) return super.matches(var1_1, var2_2, var3_3, var4_4);
        var6_6 = var2_2[0];
        var3_3 = var7_7 != false ? var5_5 : -var5_5;
        var2_2[0] = var6_6 + var3_3;
        return 2;
    }

    @Deprecated
    public int matchesAt(CharSequence charSequence, int n) {
        int n2;
        int n3;
        int n4;
        block5 : {
            String string;
            Iterator iterator;
            block4 : {
                n2 = n4 = -1;
                if (!this.hasStrings()) break block5;
                n3 = charSequence.charAt(n);
                string = null;
                iterator = this.strings.iterator();
                do {
                    n2 = n4;
                    if (!iterator.hasNext()) break block4;
                } while ((n2 = (int)(string = (String)iterator.next()).charAt(0)) < n3 || n2 <= n3);
                n2 = n4;
                break block5;
            }
            while (n2 <= (n4 = UnicodeSet.matchesAt(charSequence, n, string))) {
                n2 = n4;
                if (!iterator.hasNext()) break;
                string = (String)iterator.next();
            }
        }
        n4 = n2;
        if (n2 < 2) {
            n3 = UTF16.charAt(charSequence, n);
            n4 = n2;
            if (this.contains(n3)) {
                n4 = UTF16.getCharCount(n3);
            }
        }
        return n + n4;
    }

    @Override
    public boolean matchesIndexValue(int n) {
        for (int i = 0; i < this.getRangeCount(); ++i) {
            int n2;
            int n3 = this.getRangeStart(i);
            if (!((n3 & -256) == ((n2 = this.getRangeEnd(i)) & -256) ? (n3 & 255) <= n && n <= (n2 & 255) : (n3 & 255) <= n || n <= (n2 & 255))) continue;
            return true;
        }
        if (this.hasStrings()) {
            Iterator iterator = this.strings.iterator();
            while (iterator.hasNext()) {
                if ((UTF16.charAt((String)iterator.next(), 0) & 255) != n) continue;
                return true;
            }
        }
        return false;
    }

    public Iterable<EntryRange> ranges() {
        return new EntryRangeIterable();
    }

    public final UnicodeSet remove(int n) {
        return this.remove(n, n);
    }

    public UnicodeSet remove(int n, int n2) {
        this.checkFrozen();
        if (n >= 0 && n <= 1114111) {
            if (n2 >= 0 && n2 <= 1114111) {
                if (n <= n2) {
                    this.retain(this.range(n, n2), 2, 2);
                }
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid code point U+");
            stringBuilder.append(Utility.hex(n2, 6));
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid code point U+");
        stringBuilder.append(Utility.hex(n, 6));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final UnicodeSet remove(CharSequence charSequence) {
        int n = UnicodeSet.getSingleCP(charSequence);
        if (n < 0) {
            this.checkFrozen();
            charSequence = charSequence.toString();
            if (this.strings.contains(charSequence)) {
                this.strings.remove(charSequence);
                this.pat = null;
            }
        } else {
            this.remove(n, n);
        }
        return this;
    }

    public UnicodeSet removeAll(UnicodeSet unicodeSet) {
        this.checkFrozen();
        this.retain(unicodeSet.list, unicodeSet.len, 2);
        if (this.hasStrings() && unicodeSet.hasStrings()) {
            this.strings.removeAll(unicodeSet.strings);
        }
        return this;
    }

    public final UnicodeSet removeAll(CharSequence charSequence) {
        return this.removeAll(UnicodeSet.fromAll(charSequence));
    }

    public <T extends CharSequence> UnicodeSet removeAll(Iterable<T> object) {
        this.checkFrozen();
        object = object.iterator();
        while (object.hasNext()) {
            this.remove((CharSequence)object.next());
        }
        return this;
    }

    public final UnicodeSet removeAllStrings() {
        this.checkFrozen();
        if (this.hasStrings()) {
            this.strings.clear();
            this.pat = null;
        }
        return this;
    }

    public final UnicodeSet retain(int n) {
        return this.retain(n, n);
    }

    public UnicodeSet retain(int n, int n2) {
        this.checkFrozen();
        if (n >= 0 && n <= 1114111) {
            if (n2 >= 0 && n2 <= 1114111) {
                if (n <= n2) {
                    this.retain(this.range(n, n2), 2, 0);
                } else {
                    this.clear();
                }
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid code point U+");
            stringBuilder.append(Utility.hex(n2, 6));
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid code point U+");
        stringBuilder.append(Utility.hex(n, 6));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final UnicodeSet retain(CharSequence charSequence) {
        int n = UnicodeSet.getSingleCP(charSequence);
        if (n < 0) {
            this.checkFrozen();
            charSequence = charSequence.toString();
            if (this.strings.contains(charSequence) && this.size() == 1) {
                return this;
            }
            this.clear();
            this.addString(charSequence);
            this.pat = null;
        } else {
            this.retain(n, n);
        }
        return this;
    }

    public UnicodeSet retainAll(UnicodeSet unicodeSet) {
        this.checkFrozen();
        this.retain(unicodeSet.list, unicodeSet.len, 0);
        if (this.hasStrings()) {
            if (!unicodeSet.hasStrings()) {
                this.strings.clear();
            } else {
                this.strings.retainAll(unicodeSet.strings);
            }
        }
        return this;
    }

    public final UnicodeSet retainAll(CharSequence charSequence) {
        return this.retainAll(UnicodeSet.fromAll(charSequence));
    }

    public <T extends CharSequence> UnicodeSet retainAll(Iterable<T> iterable) {
        this.checkFrozen();
        UnicodeSet unicodeSet = new UnicodeSet();
        unicodeSet.addAll(iterable);
        this.retainAll(unicodeSet);
        return this;
    }

    public UnicodeSet set(int n, int n2) {
        this.checkFrozen();
        this.clear();
        this.complement(n, n2);
        return this;
    }

    public UnicodeSet set(UnicodeSet unicodeSet) {
        this.checkFrozen();
        this.list = Arrays.copyOf(unicodeSet.list, unicodeSet.len);
        this.len = unicodeSet.len;
        this.pat = unicodeSet.pat;
        this.strings = unicodeSet.hasStrings() ? new TreeSet<String>(unicodeSet.strings) : EMPTY_STRINGS;
        return this;
    }

    public int size() {
        int n = 0;
        int n2 = this.getRangeCount();
        for (int i = 0; i < n2; ++i) {
            n += this.getRangeEnd(i) - this.getRangeStart(i) + 1;
        }
        return this.strings.size() + n;
    }

    public int span(CharSequence charSequence, int n, SpanCondition spanCondition) {
        UnicodeSetStringSpan unicodeSetStringSpan;
        int n2;
        int n3 = charSequence.length();
        if (n < 0) {
            n2 = 0;
        } else {
            n2 = n;
            if (n >= n3) {
                return n3;
            }
        }
        if (this.bmpSet != null) {
            return this.bmpSet.span(charSequence, n2, spanCondition, null);
        }
        if (this.stringSpan != null) {
            return this.stringSpan.span(charSequence, n2, spanCondition);
        }
        if (this.hasStrings() && (unicodeSetStringSpan = new UnicodeSetStringSpan(this, new ArrayList<String>(this.strings), n = spanCondition == SpanCondition.NOT_CONTAINED ? 33 : 34)).needsStringSpanUTF16()) {
            return unicodeSetStringSpan.span(charSequence, n2, spanCondition);
        }
        return this.spanCodePointsAndCount(charSequence, n2, spanCondition, null);
    }

    public int span(CharSequence charSequence, SpanCondition spanCondition) {
        return this.span(charSequence, 0, spanCondition);
    }

    @Deprecated
    public int spanAndCount(CharSequence charSequence, int n, SpanCondition spanCondition, OutputInt outputInt) {
        if (outputInt != null) {
            int n2;
            int n3 = charSequence.length();
            if (n < 0) {
                n2 = 0;
            } else {
                n2 = n;
                if (n >= n3) {
                    return n3;
                }
            }
            if (this.stringSpan != null) {
                return this.stringSpan.spanAndCount(charSequence, n2, spanCondition, outputInt);
            }
            if (this.bmpSet != null) {
                return this.bmpSet.span(charSequence, n2, spanCondition, outputInt);
            }
            if (this.hasStrings()) {
                n = spanCondition == SpanCondition.NOT_CONTAINED ? 33 : 34;
                return new UnicodeSetStringSpan(this, new ArrayList<String>(this.strings), n | 64).spanAndCount(charSequence, n2, spanCondition, outputInt);
            }
            return this.spanCodePointsAndCount(charSequence, n2, spanCondition, outputInt);
        }
        throw new IllegalArgumentException("outCount must not be null");
    }

    public int spanBack(CharSequence charSequence, int n, SpanCondition spanCondition) {
        UnicodeSetStringSpan unicodeSetStringSpan;
        boolean bl = false;
        if (n <= 0) {
            return 0;
        }
        int n2 = n;
        if (n > charSequence.length()) {
            n2 = charSequence.length();
        }
        if (this.bmpSet != null) {
            return this.bmpSet.spanBack(charSequence, n2, spanCondition);
        }
        if (this.stringSpan != null) {
            return this.stringSpan.spanBack(charSequence, n2, spanCondition);
        }
        if (this.hasStrings() && (unicodeSetStringSpan = new UnicodeSetStringSpan(this, new ArrayList<String>(this.strings), n = spanCondition == SpanCondition.NOT_CONTAINED ? 17 : 18)).needsStringSpanUTF16()) {
            return unicodeSetStringSpan.spanBack(charSequence, n2, spanCondition);
        }
        if (spanCondition != SpanCondition.NOT_CONTAINED) {
            bl = true;
        }
        n = n2;
        while (bl == this.contains(n2 = Character.codePointBefore(charSequence, n))) {
            n = n2 = n - Character.charCount(n2);
            if (n2 > 0) continue;
            n = n2;
            break;
        }
        return n;
    }

    public int spanBack(CharSequence charSequence, SpanCondition spanCondition) {
        return this.spanBack(charSequence, charSequence.length(), spanCondition);
    }

    public Collection<String> strings() {
        if (this.hasStrings()) {
            return Collections.unmodifiableSortedSet(this.strings);
        }
        return EMPTY_STRINGS;
    }

    @Deprecated
    public String stripFrom(CharSequence charSequence, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        while (n < charSequence.length()) {
            int n2 = this.findIn(charSequence, n, bl ^ true);
            stringBuilder.append(charSequence.subSequence(n, n2));
            n = this.findIn(charSequence, n2, bl);
        }
        return stringBuilder.toString();
    }

    @Override
    public String toPattern(boolean bl) {
        String string = this.pat;
        if (string != null && !bl) {
            return string;
        }
        return this._toPattern(new StringBuilder(), bl).toString();
    }

    public String toString() {
        return this.toPattern(true);
    }

    public static enum ComparisonStyle {
        SHORTER_FIRST,
        LEXICOGRAPHIC,
        LONGER_FIRST;
        
    }

    public static class EntryRange {
        public int codepoint;
        public int codepointEnd;

        EntryRange() {
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            int n = this.codepoint;
            if (n == this.codepointEnd) {
                stringBuilder = (StringBuilder)UnicodeSet._appendToPat(stringBuilder, n, false);
            } else {
                stringBuilder = (StringBuilder)UnicodeSet._appendToPat(stringBuilder, n, false);
                stringBuilder.append('-');
                stringBuilder = (StringBuilder)UnicodeSet._appendToPat(stringBuilder, this.codepointEnd, false);
            }
            return stringBuilder.toString();
        }
    }

    private class EntryRangeIterable
    implements Iterable<EntryRange> {
        private EntryRangeIterable() {
        }

        @Override
        public Iterator<EntryRange> iterator() {
            return new EntryRangeIterator();
        }
    }

    private class EntryRangeIterator
    implements Iterator<EntryRange> {
        int pos;
        EntryRange result = new EntryRange();

        private EntryRangeIterator() {
        }

        @Override
        public boolean hasNext() {
            int n = this.pos;
            int n2 = UnicodeSet.this.len;
            boolean bl = true;
            if (n >= n2 - 1) {
                bl = false;
            }
            return bl;
        }

        @Override
        public EntryRange next() {
            if (this.pos < UnicodeSet.this.len - 1) {
                EntryRange entryRange = this.result;
                int[] arrn = UnicodeSet.this.list;
                int n = this.pos;
                this.pos = n + 1;
                entryRange.codepoint = arrn[n];
                entryRange = this.result;
                arrn = UnicodeSet.this.list;
                n = this.pos;
                this.pos = n + 1;
                entryRange.codepointEnd = arrn[n] - 1;
                return this.result;
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static interface Filter {
        public boolean contains(int var1);
    }

    private static final class GeneralCategoryMaskFilter
    implements Filter {
        int mask;

        GeneralCategoryMaskFilter(int n) {
            this.mask = n;
        }

        @Override
        public boolean contains(int n) {
            n = UCharacter.getType(n);
            boolean bl = true;
            if ((1 << n & this.mask) == 0) {
                bl = false;
            }
            return bl;
        }
    }

    private static final class IntPropertyFilter
    implements Filter {
        int prop;
        int value;

        IntPropertyFilter(int n, int n2) {
            this.prop = n;
            this.value = n2;
        }

        @Override
        public boolean contains(int n) {
            boolean bl = UCharacter.getIntPropertyValue(n, this.prop) == this.value;
            return bl;
        }
    }

    private static final class NumericValueFilter
    implements Filter {
        double value;

        NumericValueFilter(double d) {
            this.value = d;
        }

        @Override
        public boolean contains(int n) {
            boolean bl = UCharacter.getUnicodeNumericValue(n) == this.value;
            return bl;
        }
    }

    private static final class ScriptExtensionsFilter
    implements Filter {
        int script;

        ScriptExtensionsFilter(int n) {
            this.script = n;
        }

        @Override
        public boolean contains(int n) {
            return UScript.hasScript(n, this.script);
        }
    }

    public static enum SpanCondition {
        NOT_CONTAINED,
        CONTAINED,
        SIMPLE,
        CONDITION_COUNT;
        
    }

    private static class UnicodeSetIterator2
    implements Iterator<String> {
        private char[] buffer;
        private int current;
        private int item;
        private int len;
        private int limit;
        private int[] sourceList;
        private SortedSet<String> sourceStrings;
        private Iterator<String> stringIterator;

        UnicodeSetIterator2(UnicodeSet arrn) {
            this.len = ((UnicodeSet)arrn).len - 1;
            if (this.len > 0) {
                this.sourceStrings = arrn.strings;
                arrn = this.sourceList = ((UnicodeSet)arrn).list;
                int n = this.item;
                this.item = n + 1;
                this.current = arrn[n];
                n = this.item;
                this.item = n + 1;
                this.limit = arrn[n];
            } else {
                this.stringIterator = arrn.strings.iterator();
                this.sourceList = null;
            }
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.sourceList != null || this.stringIterator.hasNext();
            return bl;
        }

        @Override
        public String next() {
            int[] arrn = this.sourceList;
            if (arrn == null) {
                return this.stringIterator.next();
            }
            int n = this.current;
            this.current = n + 1;
            if (this.current >= this.limit) {
                int n2 = this.item;
                if (n2 >= this.len) {
                    this.stringIterator = this.sourceStrings.iterator();
                    this.sourceList = null;
                } else {
                    this.item = n2 + 1;
                    this.current = arrn[n2];
                    n2 = this.item;
                    this.item = n2 + 1;
                    this.limit = arrn[n2];
                }
            }
            if (n <= 65535) {
                return String.valueOf((char)n);
            }
            if (this.buffer == null) {
                this.buffer = new char[2];
            }
            arrn = this.buffer;
            arrn[0] = (char)(((n -= 65536) >>> 10) + 55296);
            arrn[1] = (char)((n & 1023) + 56320);
            return String.valueOf((char[])arrn);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class VersionFilter
    implements Filter {
        VersionInfo version;

        VersionFilter(VersionInfo versionInfo) {
            this.version = versionInfo;
        }

        @Override
        public boolean contains(int n) {
            VersionInfo versionInfo = UCharacter.getAge(n);
            boolean bl = !Utility.sameObjects(versionInfo, NO_VERSION) && versionInfo.compareTo(this.version) <= 0;
            return bl;
        }
    }

    public static abstract class XSymbolTable
    implements SymbolTable {
        public boolean applyPropertyAlias(String string, String string2, UnicodeSet unicodeSet) {
            return false;
        }

        @Override
        public char[] lookup(String string) {
            return null;
        }

        @Override
        public UnicodeMatcher lookupMatcher(int n) {
            return null;
        }

        @Override
        public String parseReference(String string, ParsePosition parsePosition, int n) {
            return null;
        }
    }

}

