/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUBinary;
import android.icu.impl.Trie2;
import android.icu.impl.Trie2_16;
import android.icu.text.UTF16;
import android.icu.text.UnicodeSet;
import android.icu.util.ICUUncheckedIOException;
import android.icu.util.ULocale;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Locale;

public final class UCaseProps {
    private static final int ABOVE = 64;
    private static final int CLOSURE_MAX_LENGTH = 15;
    private static final String DATA_FILE_NAME = "ucase.icu";
    private static final String DATA_NAME = "ucase";
    private static final String DATA_TYPE = "icu";
    private static final int DELTA_SHIFT = 7;
    private static final int DOT_MASK = 96;
    private static final int EXCEPTION = 8;
    private static final int EXC_CLOSURE = 6;
    private static final int EXC_CONDITIONAL_FOLD = 32768;
    private static final int EXC_CONDITIONAL_SPECIAL = 16384;
    private static final int EXC_DELTA = 4;
    private static final int EXC_DELTA_IS_NEGATIVE = 1024;
    private static final int EXC_DOT_SHIFT = 7;
    private static final int EXC_DOUBLE_SLOTS = 256;
    private static final int EXC_FOLD = 1;
    private static final int EXC_FULL_MAPPINGS = 7;
    private static final int EXC_LOWER = 0;
    private static final int EXC_NO_SIMPLE_CASE_FOLDING = 512;
    private static final int EXC_SENSITIVE = 2048;
    private static final int EXC_SHIFT = 4;
    private static final int EXC_TITLE = 3;
    private static final int EXC_UPPER = 2;
    private static final int FMT = 1665225541;
    static final int FOLD_CASE_OPTIONS_MASK = 7;
    private static final int FULL_LOWER = 15;
    static final int IGNORABLE = 4;
    public static final UCaseProps INSTANCE;
    private static final int IX_EXC_LENGTH = 3;
    private static final int IX_TOP = 16;
    private static final int IX_TRIE_SIZE = 2;
    private static final int IX_UNFOLD_LENGTH = 4;
    public static final int LOC_DUTCH = 5;
    static final int LOC_GREEK = 4;
    static final int LOC_LITHUANIAN = 3;
    public static final int LOC_ROOT = 1;
    static final int LOC_TURKISH = 2;
    public static final int LOWER = 1;
    public static final int MAX_STRING_LENGTH = 31;
    public static final int NONE = 0;
    private static final int OTHER_ACCENT = 96;
    private static final int SENSITIVE = 16;
    private static final int SOFT_DOTTED = 32;
    public static final int TITLE = 3;
    public static final int TYPE_MASK = 3;
    private static final int UNFOLD_ROWS = 0;
    private static final int UNFOLD_ROW_WIDTH = 1;
    private static final int UNFOLD_STRING_WIDTH = 2;
    public static final int UPPER = 2;
    public static final StringBuilder dummyStringBuilder;
    private static final byte[] flagsOffset;
    private static final String iDot = "i\u0307";
    private static final String iDotAcute = "i\u0307\u0301";
    private static final String iDotGrave = "i\u0307\u0300";
    private static final String iDotTilde = "i\u0307\u0303";
    private static final String iOgonekDot = "\u012f\u0307";
    private static final String jDot = "j\u0307";
    private String exceptions;
    private int[] indexes;
    private Trie2_16 trie;
    private char[] unfold;

    static {
        flagsOffset = new byte[]{0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 4, 5, 5, 6, 5, 6, 6, 7, 5, 6, 6, 7, 6, 7, 7, 8};
        dummyStringBuilder = new StringBuilder();
        try {
            UCaseProps uCaseProps;
            INSTANCE = uCaseProps = new UCaseProps();
            return;
        }
        catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }

    private UCaseProps() throws IOException {
        this.readData(ICUBinary.getRequiredData(DATA_FILE_NAME));
    }

    public static final int getCaseLocale(ULocale uLocale) {
        return UCaseProps.getCaseLocale(uLocale.getLanguage());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static final int getCaseLocale(String string) {
        if (string.length() == 2) {
            if (string.equals("en") || string.charAt(0) > 't') return 1;
            if (string.equals("tr") || string.equals("az")) return 2;
            if (string.equals("el")) {
                return 4;
            }
            if (string.equals("lt")) {
                return 3;
            }
            if (!string.equals("nl")) return 1;
            return 5;
        }
        if (string.length() != 3) return 1;
        if (string.equals("tur") || string.equals("aze")) return 2;
        if (string.equals("ell")) {
            return 4;
        }
        if (string.equals("lit")) {
            return 3;
        }
        if (!string.equals("nld")) return 1;
        return 5;
    }

    public static final int getCaseLocale(Locale locale) {
        return UCaseProps.getCaseLocale(locale.getLanguage());
    }

    static final int getDelta(int n) {
        return (short)n >> 7;
    }

    private static final int getExceptionsOffset(int n) {
        return n >> 4;
    }

    private final int getSlotValue(int n, int n2, int n3) {
        if ((n & 256) == 0) {
            n = UCaseProps.slotOffset(n, n2);
            n = this.exceptions.charAt(n3 + n);
        } else {
            n = n3 + UCaseProps.slotOffset(n, n2) * 2;
            n = this.exceptions.charAt(n) << 16 | this.exceptions.charAt(n + 1);
        }
        return n;
    }

    private final long getSlotValueAndOffset(int n, int n2, int n3) {
        long l;
        if ((n & 256) == 0) {
            n = n3 + UCaseProps.slotOffset(n, n2);
            l = this.exceptions.charAt(n);
        } else {
            n2 = n3 + UCaseProps.slotOffset(n, n2) * 2;
            String string = this.exceptions;
            n = n2 + 1;
            l = string.charAt(n2);
            long l2 = this.exceptions.charAt(n);
            l = l << 16 | l2;
        }
        return (long)n << 32 | l;
    }

    static Trie2_16 getTrie() {
        return UCaseProps.INSTANCE.trie;
    }

    private static final int getTypeAndIgnorableFromProps(int n) {
        return n & 7;
    }

    static final int getTypeFromProps(int n) {
        return n & 3;
    }

    private static final boolean hasSlot(int n, int n2) {
        boolean bl = true;
        if ((1 << n2 & n) == 0) {
            bl = false;
        }
        return bl;
    }

    private final boolean isFollowedByCasedLetter(ContextIterator contextIterator, int n) {
        if (contextIterator == null) {
            return false;
        }
        contextIterator.reset(n);
        while ((n = contextIterator.next()) >= 0) {
            if (((n = this.getTypeOrIgnorable(n)) & 4) != 0) continue;
            return n != 0;
        }
        return false;
    }

    private final boolean isFollowedByDotAbove(ContextIterator contextIterator) {
        int n;
        if (contextIterator == null) {
            return false;
        }
        contextIterator.reset(1);
        while ((n = contextIterator.next()) >= 0) {
            if (n == 775) {
                return true;
            }
            if (this.getDotType(n) == 96) continue;
            return false;
        }
        return false;
    }

    private final boolean isFollowedByMoreAbove(ContextIterator contextIterator) {
        int n;
        if (contextIterator == null) {
            return false;
        }
        contextIterator.reset(1);
        while ((n = contextIterator.next()) >= 0) {
            if ((n = this.getDotType(n)) == 64) {
                return true;
            }
            if (n == 96) continue;
            return false;
        }
        return false;
    }

    private final boolean isPrecededBySoftDotted(ContextIterator contextIterator) {
        int n;
        if (contextIterator == null) {
            return false;
        }
        contextIterator.reset(-1);
        while ((n = contextIterator.next()) >= 0) {
            if ((n = this.getDotType(n)) == 32) {
                return true;
            }
            if (n == 96) continue;
            return false;
        }
        return false;
    }

    private final boolean isPrecededBy_I(ContextIterator contextIterator) {
        int n;
        if (contextIterator == null) {
            return false;
        }
        contextIterator.reset(-1);
        while ((n = contextIterator.next()) >= 0) {
            if (n == 73) {
                return true;
            }
            if (this.getDotType(n) == 96) continue;
            return false;
        }
        return false;
    }

    static final boolean isUpperOrTitleFromProps(int n) {
        boolean bl = (n & 2) != 0;
        return bl;
    }

    static final boolean propsHasException(int n) {
        boolean bl = (n & 8) != 0;
        return bl;
    }

    private final void readData(ByteBuffer byteBuffer) throws IOException {
        ICUBinary.readHeader(byteBuffer, 1665225541, new IsAcceptable());
        int n = byteBuffer.getInt();
        if (n >= 16) {
            int n2;
            this.indexes = new int[n];
            this.indexes[0] = n;
            for (n2 = 1; n2 < n; ++n2) {
                this.indexes[n2] = byteBuffer.getInt();
            }
            this.trie = Trie2_16.createFromSerialized(byteBuffer);
            n = this.indexes[2];
            n2 = this.trie.getSerializedLength();
            if (n2 <= n) {
                ICUBinary.skipBytes(byteBuffer, n - n2);
                n2 = this.indexes[3];
                if (n2 > 0) {
                    this.exceptions = ICUBinary.getString(byteBuffer, n2, 0);
                }
                if ((n2 = this.indexes[4]) > 0) {
                    this.unfold = ICUBinary.getChars(byteBuffer, n2, 0);
                }
                return;
            }
            throw new IOException("ucase.icu: not enough bytes for the trie");
        }
        throw new IOException("indexes[0] too small in ucase.icu");
    }

    private static final byte slotOffset(int n, int n2) {
        return flagsOffset[n & (1 << n2) - 1];
    }

    private final int strcmpMax(String string, int n, int n2) {
        int n3 = string.length();
        int n4 = n2 - n3;
        n2 = 0;
        int n5 = n;
        n = n2;
        n2 = n3;
        do {
            char c = string.charAt(n);
            char[] arrc = this.unfold;
            n3 = n5 + 1;
            if ((n5 = arrc[n5]) == 0) {
                return 1;
            }
            if ((n5 = c - n5) != 0) {
                return n5;
            }
            if (--n2 <= 0) {
                if (n4 != 0 && arrc[n3] != '\u0000') {
                    return -n4;
                }
                return 0;
            }
            ++n;
            n5 = n3;
        } while (true);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final int toUpperOrTitle(int n, ContextIterator object, Appendable appendable, int n2, boolean bl) {
        int n5 = n;
        int n4 = this.trie.get(n);
        if (!UCaseProps.propsHasException(n4)) {
            n2 = UCaseProps.getTypeFromProps(n4) == 1 ? n + UCaseProps.getDelta(n4) : n5;
        } else {
            n5 = UCaseProps.getExceptionsOffset(n4);
            String string = this.exceptions;
            int n3 = n5 + 1;
            char c = string.charAt(n5);
            if ((c & 16384) != 0) {
                if (n2 == 2 && n == 105) {
                    return 304;
                }
                if (n2 == 3 && n == 775 && this.isPrecededBySoftDotted((ContextIterator)object)) {
                    return 0;
                }
            } else if (UCaseProps.hasSlot(c, 7)) {
                long l = this.getSlotValueAndOffset(c, 7, n3);
                n2 = (int)l & 65535;
                int n6 = (int)(l >> 32);
                n5 = n2 >> 4;
                n2 = n6 + 1 + (n2 & 15) + (n5 & 15);
                n5 >>= 4;
                if (bl) {
                    n5 &= 15;
                } else {
                    n2 += n5 & 15;
                    n5 = n5 >> 4 & 15;
                }
                if (n5 != 0) {
                    void var2_5;
                    object = this.exceptions;
                    try {
                        appendable.append((CharSequence)object, n2, n2 + n5);
                        return n5;
                    }
                    catch (IOException iOException) {
                        throw new ICUUncheckedIOException((Throwable)var2_5);
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                    throw new ICUUncheckedIOException((Throwable)var2_5);
                }
            }
            if (UCaseProps.hasSlot(c, 4) && UCaseProps.getTypeFromProps(n4) == 1) {
                n2 = this.getSlotValue(c, 4, n3);
                if ((c & 1024) == 0) {
                    return n += n2;
                }
                n -= n2;
                return n;
            }
            if (!bl && UCaseProps.hasSlot(c, 3)) {
                n2 = 3;
            } else {
                if (!UCaseProps.hasSlot(c, 2)) return n;
                n2 = 2;
            }
            n2 = this.getSlotValue(c, n2, n3);
        }
        if (n2 != n) return n2;
        return n2;
    }

    public final void addCaseClosure(int n, UnicodeSet unicodeSet) {
        int n2 = n;
        if (n2 != 73) {
            if (n2 != 105) {
                if (n2 != 304) {
                    if (n2 != 305) {
                        n = this.trie.get(n2);
                        if (!UCaseProps.propsHasException(n)) {
                            if (UCaseProps.getTypeFromProps(n) != 0 && (n = UCaseProps.getDelta(n)) != 0) {
                                unicodeSet.add(n2 + n);
                            }
                        } else {
                            int n3;
                            long l;
                            int n4 = UCaseProps.getExceptionsOffset(n);
                            String string = this.exceptions;
                            n = n4 + 1;
                            int n5 = string.charAt(n4);
                            n4 = n;
                            for (n3 = 0; n3 <= 3; ++n3) {
                                if (!UCaseProps.hasSlot(n5, n3)) continue;
                                n4 = n;
                                n2 = this.getSlotValue(n5, n3, n4);
                                unicodeSet.add(n2);
                            }
                            if (UCaseProps.hasSlot(n5, 4)) {
                                n4 = n;
                                n3 = this.getSlotValue(n5, 4, n4);
                                n2 = (n5 & 1024) == 0 ? (n2 += n3) : (n2 -= n3);
                                unicodeSet.add(n2);
                            }
                            if (UCaseProps.hasSlot(n5, 6)) {
                                n4 = n;
                                l = this.getSlotValueAndOffset(n5, 6, n4);
                                n3 = (int)l & 15;
                                n2 = (int)(l >> 32) + 1;
                            } else {
                                n3 = 0;
                                n2 = 0;
                            }
                            if (UCaseProps.hasSlot(n5, 7)) {
                                l = this.getSlotValueAndOffset(n5, 7, n);
                                n = (int)l;
                                n4 = (int)(l >> 32);
                                n = 65535 & n;
                                n4 = n4 + 1 + (n & 15);
                                n2 = n >> 4;
                                n5 = n2 & 15;
                                n = n4;
                                if (n5 != 0) {
                                    unicodeSet.add(this.exceptions.substring(n4, n4 + n5));
                                    n = n4 + n5;
                                }
                                n4 = n2 >> 4;
                                n2 = n + (n4 & 15) + (n4 >> 4);
                            }
                            for (n = n2; n < n2 + n3; n += UTF16.getCharCount((int)n4)) {
                                n4 = this.exceptions.codePointAt(n);
                                unicodeSet.add(n4);
                            }
                        }
                        return;
                    }
                    return;
                }
                unicodeSet.add(iDot);
                return;
            }
            unicodeSet.add(73);
            return;
        }
        unicodeSet.add(105);
    }

    public final void addPropertyStarts(UnicodeSet unicodeSet) {
        for (Trie2.Range range : this.trie) {
            if (range.leadSurrogate) break;
            unicodeSet.add(range.startCodePoint);
        }
    }

    public final boolean addStringCaseClosure(String arrc, UnicodeSet unicodeSet) {
        if (this.unfold != null && arrc != null) {
            int n = arrc.length();
            if (n <= 1) {
                return false;
            }
            char[] arrc2 = this.unfold;
            int n2 = arrc2[0];
            char c = arrc2[1];
            int n3 = arrc2[2];
            if (n > n3) {
                return false;
            }
            n = 0;
            while (n < n2) {
                int n4 = (n + n2) / 2;
                int n5 = (n4 + 1) * c;
                int n6 = this.strcmpMax((String)arrc, n5, n3);
                if (n6 == 0) {
                    for (n2 = n3; n2 < c && (arrc = this.unfold)[n5 + n2] != '\u0000'; n2 += UTF16.getCharCount((int)n)) {
                        n = UTF16.charAt(arrc, n5, arrc.length, n2);
                        unicodeSet.add(n);
                        this.addCaseClosure(n, unicodeSet);
                    }
                    return true;
                }
                if (n6 < 0) {
                    n2 = n4;
                    continue;
                }
                n = n4 + 1;
            }
            return false;
        }
        return false;
    }

    public final int fold(int n, int n2) {
        block18 : {
            block15 : {
                int n3;
                int n4;
                block17 : {
                    block16 : {
                        int n5;
                        block14 : {
                            n5 = this.trie.get(n);
                            if (UCaseProps.propsHasException(n5)) break block14;
                            n2 = n;
                            if (UCaseProps.isUpperOrTitleFromProps(n5)) {
                                n2 = n + UCaseProps.getDelta(n5);
                            }
                            break block15;
                        }
                        n3 = UCaseProps.getExceptionsOffset(n5);
                        String string = this.exceptions;
                        n4 = n3 + 1;
                        if ((32768 & (n3 = (int)string.charAt(n3))) != 0) {
                            if ((n2 & 7) == 0) {
                                if (n == 73) {
                                    return 105;
                                }
                                if (n == 304) {
                                    return n;
                                }
                            } else {
                                if (n == 73) {
                                    return 305;
                                }
                                if (n == 304) {
                                    return 105;
                                }
                            }
                        }
                        if ((n3 & 512) != 0) {
                            return n;
                        }
                        if (UCaseProps.hasSlot(n3, 4) && UCaseProps.isUpperOrTitleFromProps(n5)) {
                            n2 = this.getSlotValue(n3, 4, n4);
                            n = (n3 & 1024) == 0 ? (n += n2) : (n -= n2);
                            return n;
                        }
                        if (!UCaseProps.hasSlot(n3, 1)) break block16;
                        n = 1;
                        break block17;
                    }
                    if (!UCaseProps.hasSlot(n3, 0)) break block18;
                    n = 0;
                }
                n2 = this.getSlotValue(n3, n, n4);
            }
            return n2;
        }
        return n;
    }

    public final int getDotType(int n) {
        if (!UCaseProps.propsHasException(n = this.trie.get(n))) {
            return n & 96;
        }
        return this.exceptions.charAt(UCaseProps.getExceptionsOffset(n)) >> 7 & 96;
    }

    public final int getType(int n) {
        return UCaseProps.getTypeFromProps(this.trie.get(n));
    }

    public final int getTypeOrIgnorable(int n) {
        return UCaseProps.getTypeAndIgnorableFromProps(this.trie.get(n));
    }

    public final boolean hasBinaryProperty(int n, int n2) {
        boolean bl;
        boolean bl2;
        block18 : {
            block19 : {
                boolean bl3;
                block20 : {
                    block21 : {
                        block23 : {
                            block22 : {
                                boolean bl4 = false;
                                boolean bl5 = false;
                                boolean bl6 = false;
                                boolean bl7 = false;
                                boolean bl8 = false;
                                bl3 = false;
                                bl2 = false;
                                bl = false;
                                if (n2 == 22) break block18;
                                if (n2 == 27) break block19;
                                if (n2 == 30) break block20;
                                if (n2 == 34) break block21;
                                if (n2 != 55) {
                                    switch (n2) {
                                        default: {
                                            return false;
                                        }
                                        case 53: {
                                            dummyStringBuilder.setLength(0);
                                            if (this.toFullTitle(n, null, dummyStringBuilder, 1) >= 0) {
                                                bl = true;
                                            }
                                            return bl;
                                        }
                                        case 52: {
                                            dummyStringBuilder.setLength(0);
                                            bl = bl4;
                                            if (this.toFullUpper(n, null, dummyStringBuilder, 1) >= 0) {
                                                bl = true;
                                            }
                                            return bl;
                                        }
                                        case 51: {
                                            dummyStringBuilder.setLength(0);
                                            bl = bl5;
                                            if (this.toFullLower(n, null, dummyStringBuilder, 1) >= 0) {
                                                bl = true;
                                            }
                                            return bl;
                                        }
                                        case 50: {
                                            bl = bl6;
                                            if (this.getTypeOrIgnorable(n) >> 2 != 0) {
                                                bl = true;
                                            }
                                            return bl;
                                        }
                                        case 49: 
                                    }
                                    bl = bl7;
                                    if (this.getType(n) != 0) {
                                        bl = true;
                                    }
                                    return bl;
                                }
                                dummyStringBuilder.setLength(0);
                                if (this.toFullLower(n, null, dummyStringBuilder, 1) >= 0 || this.toFullUpper(n, null, dummyStringBuilder, 1) >= 0) break block22;
                                bl = bl8;
                                if (this.toFullTitle(n, null, dummyStringBuilder, 1) < 0) break block23;
                            }
                            bl = true;
                        }
                        return bl;
                    }
                    return this.isCaseSensitive(n);
                }
                bl = bl3;
                if (2 == this.getType(n)) {
                    bl = true;
                }
                return bl;
            }
            return this.isSoftDotted(n);
        }
        bl = bl2;
        if (1 == this.getType(n)) {
            bl = true;
        }
        return bl;
    }

    public final boolean isCaseSensitive(int n) {
        n = this.trie.get(n);
        boolean bl = UCaseProps.propsHasException(n);
        boolean bl2 = true;
        boolean bl3 = true;
        if (!bl) {
            if ((n & 16) == 0) {
                bl3 = false;
            }
            return bl3;
        }
        bl3 = (this.exceptions.charAt(UCaseProps.getExceptionsOffset(n)) & 2048) != 0 ? bl2 : false;
        return bl3;
    }

    public final boolean isSoftDotted(int n) {
        boolean bl = this.getDotType(n) == 32;
        return bl;
    }

    public final int toFullFolding(int n, Appendable appendable, int n2) {
        block25 : {
            block22 : {
                int n3;
                int n4;
                block24 : {
                    block23 : {
                        int n5;
                        block21 : {
                            n4 = n;
                            n5 = this.trie.get(n);
                            if (UCaseProps.propsHasException(n5)) break block21;
                            n2 = n4;
                            if (UCaseProps.isUpperOrTitleFromProps(n5)) {
                                n2 = n + UCaseProps.getDelta(n5);
                            }
                            break block22;
                        }
                        n3 = UCaseProps.getExceptionsOffset(n5);
                        String string = this.exceptions;
                        n4 = n3 + 1;
                        if ((32768 & (n3 = (int)string.charAt(n3))) != 0) {
                            if ((n2 & 7) == 0) {
                                if (n == 73) {
                                    return 105;
                                }
                                if (n == 304) {
                                    try {
                                        appendable.append(iDot);
                                        return 2;
                                    }
                                    catch (IOException iOException) {
                                        throw new ICUUncheckedIOException(iOException);
                                    }
                                }
                            } else {
                                if (n == 73) {
                                    return 305;
                                }
                                if (n == 304) {
                                    return 105;
                                }
                            }
                        } else if (UCaseProps.hasSlot(n3, 7)) {
                            long l = this.getSlotValueAndOffset(n3, 7, n4);
                            int n6 = (int)l & 65535;
                            n2 = (n6 & 15) + ((int)(l >> 32) + 1);
                            if ((n6 = n6 >> 4 & 15) != 0) {
                                try {
                                    appendable.append(this.exceptions, n2, n2 + n6);
                                    return n6;
                                }
                                catch (IOException iOException) {
                                    throw new ICUUncheckedIOException(iOException);
                                }
                            }
                        }
                        if ((n3 & 512) != 0) {
                            return n;
                        }
                        if (UCaseProps.hasSlot(n3, 4) && UCaseProps.isUpperOrTitleFromProps(n5)) {
                            n2 = this.getSlotValue(n3, 4, n4);
                            n = (n3 & 1024) == 0 ? (n += n2) : (n -= n2);
                            return n;
                        }
                        if (!UCaseProps.hasSlot(n3, 1)) break block23;
                        n2 = 1;
                        break block24;
                    }
                    if (!UCaseProps.hasSlot(n3, 0)) break block25;
                    n2 = 0;
                }
                n2 = this.getSlotValue(n3, n2, n4);
            }
            n = n2 == n ? n2 : n2;
            return n;
        }
        return n;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final int toFullLower(int var1_1, ContextIterator var2_2, Appendable var3_6, int var4_7) {
        block18 : {
            block22 : {
                block19 : {
                    block20 : {
                        block21 : {
                            block17 : {
                                var5_8 = var1_1;
                                var6_9 = this.trie.get(var1_1);
                                if (UCaseProps.propsHasException(var6_9)) break block17;
                                var4_7 = var5_8;
                                if (UCaseProps.isUpperOrTitleFromProps(var6_9)) {
                                    var4_7 = var1_1 + UCaseProps.getDelta(var6_9);
                                }
                                break block18;
                            }
                            var7_10 = UCaseProps.getExceptionsOffset(var6_9);
                            var8_11 = this.exceptions;
                            var9_12 = var7_10 + 1;
                            if (((var7_10 = (int)var8_11.charAt(var7_10)) & 16384) == 0) break block19;
                            if (var4_7 != 3 || (var1_1 != 73 && var1_1 != 74 && var1_1 != 302 || !this.isFollowedByMoreAbove(var2_2)) && var1_1 != 204 && var1_1 != 205 && var1_1 != 296) break block20;
                            if (var1_1 == 73) break block21;
                            if (var1_1 == 74) ** GOTO lbl34
                            if (var1_1 == 204) ** GOTO lbl31
                            if (var1_1 == 205) ** GOTO lbl28
                            if (var1_1 == 296) ** GOTO lbl25
                            if (var1_1 != 302) {
                                return 0;
                            }
                            try {
                                var3_6.append("\u012f\u0307");
                                return 2;
lbl25: // 1 sources:
                                var3_6.append("i\u0307\u0303");
                                return 3;
lbl28: // 1 sources:
                                var3_6.append("i\u0307\u0301");
                                return 3;
lbl31: // 1 sources:
                                var3_6.append("i\u0307\u0300");
                                return 3;
lbl34: // 1 sources:
                                var3_6.append("j\u0307");
                                return 2;
                            }
                            catch (IOException var2_3) {
                                throw new ICUUncheckedIOException(var2_3);
                            }
                        }
                        var3_6.append("i\u0307");
                        return 2;
                    }
                    if (var4_7 == 2 && var1_1 == 304) {
                        return 105;
                    }
                    if (var4_7 == 2 && var1_1 == 775 && this.isPrecededBy_I(var2_2)) {
                        return 0;
                    }
                    if (var4_7 == 2 && var1_1 == 73 && !this.isFollowedByDotAbove(var2_2)) {
                        return 305;
                    }
                    if (var1_1 == 304) {
                        try {
                            var3_6.append("i\u0307");
                            return 2;
                        }
                        catch (IOException var2_4) {
                            throw new ICUUncheckedIOException(var2_4);
                        }
                    }
                    if (var1_1 == 931 && !this.isFollowedByCasedLetter(var2_2, 1) && this.isFollowedByCasedLetter(var2_2, -1)) {
                        return 962;
                    }
                    break block22;
                }
                if (UCaseProps.hasSlot(var7_10, 7) && (var4_7 = (int)(var10_13 = this.getSlotValueAndOffset(var7_10, 7, var9_12)) & 15) != 0) {
                    var1_1 = (int)(var10_13 >> 32) + 1;
                    try {
                        var3_6.append(this.exceptions, var1_1, var1_1 + var4_7);
                        return var4_7;
                    }
                    catch (IOException var2_5) {
                        throw new ICUUncheckedIOException(var2_5);
                    }
                }
            }
            if (UCaseProps.hasSlot(var7_10, 4) && UCaseProps.isUpperOrTitleFromProps(var6_9)) {
                var4_7 = this.getSlotValue(var7_10, 4, var9_12);
                if ((var7_10 & 1024) == 0) {
                    return var1_1 += var4_7;
                }
                var1_1 -= var4_7;
                return var1_1;
            }
            var4_7 = var5_8;
            if (UCaseProps.hasSlot(var7_10, 0)) {
                var4_7 = this.getSlotValue(var7_10, 0, var9_12);
            }
        }
        if (var4_7 != var1_1) return var4_7;
        return var4_7;
    }

    public final int toFullTitle(int n, ContextIterator contextIterator, Appendable appendable, int n2) {
        return this.toUpperOrTitle(n, contextIterator, appendable, n2, false);
    }

    public final int toFullUpper(int n, ContextIterator contextIterator, Appendable appendable, int n2) {
        return this.toUpperOrTitle(n, contextIterator, appendable, n2, true);
    }

    public final int tolower(int n) {
        int n2;
        int n3 = this.trie.get(n);
        if (!UCaseProps.propsHasException(n3)) {
            n2 = n;
            if (UCaseProps.isUpperOrTitleFromProps(n3)) {
                n2 = n + UCaseProps.getDelta(n3);
            }
        } else {
            n2 = UCaseProps.getExceptionsOffset(n3);
            String string = this.exceptions;
            int n4 = n2 + 1;
            char c = string.charAt(n2);
            if (UCaseProps.hasSlot(c, 4) && UCaseProps.isUpperOrTitleFromProps(n3)) {
                n2 = this.getSlotValue(c, 4, n4);
                n = (c & 1024) == 0 ? (n += n2) : (n -= n2);
                return n;
            }
            n2 = n;
            if (UCaseProps.hasSlot(c, 0)) {
                n2 = this.getSlotValue(c, 0, n4);
            }
        }
        return n2;
    }

    public final int totitle(int n) {
        block10 : {
            int n2;
            block7 : {
                int n3;
                block9 : {
                    block8 : {
                        int n4;
                        block6 : {
                            n4 = this.trie.get(n);
                            if (UCaseProps.propsHasException(n4)) break block6;
                            n2 = n;
                            if (UCaseProps.getTypeFromProps(n4) == 1) {
                                n2 = n + UCaseProps.getDelta(n4);
                            }
                            break block7;
                        }
                        n3 = UCaseProps.getExceptionsOffset(n4);
                        String string = this.exceptions;
                        n2 = n3 + 1;
                        if (UCaseProps.hasSlot(n3 = (int)string.charAt(n3), 4) && UCaseProps.getTypeFromProps(n4) == 1) {
                            n2 = this.getSlotValue(n3, 4, n2);
                            n = (n3 & 1024) == 0 ? (n += n2) : (n -= n2);
                            return n;
                        }
                        if (!UCaseProps.hasSlot(n3, 3)) break block8;
                        n = 3;
                        break block9;
                    }
                    if (!UCaseProps.hasSlot(n3, 2)) break block10;
                    n = 2;
                }
                n2 = this.getSlotValue(n3, n, n2);
            }
            return n2;
        }
        return n;
    }

    public final int toupper(int n) {
        int n2;
        int n3 = this.trie.get(n);
        if (!UCaseProps.propsHasException(n3)) {
            n2 = n;
            if (UCaseProps.getTypeFromProps(n3) == 1) {
                n2 = n + UCaseProps.getDelta(n3);
            }
        } else {
            n2 = UCaseProps.getExceptionsOffset(n3);
            String string = this.exceptions;
            int n4 = n2 + 1;
            char c = string.charAt(n2);
            if (UCaseProps.hasSlot(c, 4) && UCaseProps.getTypeFromProps(n3) == 1) {
                n2 = this.getSlotValue(c, 4, n4);
                n = (c & 1024) == 0 ? (n += n2) : (n -= n2);
                return n;
            }
            n2 = n;
            if (UCaseProps.hasSlot(c, 2)) {
                n2 = this.getSlotValue(c, 2, n4);
            }
        }
        return n2;
    }

    public static interface ContextIterator {
        public int next();

        public void reset(int var1);
    }

    private static final class IsAcceptable
    implements ICUBinary.Authenticate {
        private IsAcceptable() {
        }

        @Override
        public boolean isDataVersionAcceptable(byte[] arrby) {
            boolean bl = false;
            if (arrby[0] == 4) {
                bl = true;
            }
            return bl;
        }
    }

    static final class LatinCase {
        static final byte EXC = -128;
        static final char LIMIT = '\u0180';
        static final char LONG_S = '\u017f';
        static final byte[] TO_LOWER_NORMAL = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 32, 32, 32, 32, 32, 32, 32, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -128, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -128, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -121, 1, 0, 1, 0, 1, 0, -128};
        static final byte[] TO_LOWER_TR_LT = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, -128, -128, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, -128, -128, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 32, 32, 32, 32, 32, 32, 32, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -128, 0, 1, 0, 1, 0, -128, 0, -128, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -128, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -121, 1, 0, 1, 0, 1, 0, -128};
        static final byte[] TO_UPPER_NORMAL = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, 0, -32, -32, -32, -32, -32, -32, -32, 121, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -128, 0, -1, 0, -1, 0, -1, 0, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, -128, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, 0, -1, 0, -1, 0, -1, -128};
        static final byte[] TO_UPPER_TR = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -32, -32, -32, -32, -32, -32, -32, -32, -128, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, 0, -32, -32, -32, -32, -32, -32, -32, 121, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -128, 0, -1, 0, -1, 0, -1, 0, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, -128, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, 0, -1, 0, -1, 0, -1, -128};

        LatinCase() {
        }
    }

}

