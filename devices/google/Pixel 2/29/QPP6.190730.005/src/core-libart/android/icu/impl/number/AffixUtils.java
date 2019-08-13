/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.number.NumberStringBuilder;
import android.icu.text.NumberFormat;
import android.icu.text.UnicodeSet;

public class AffixUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int STATE_AFTER_QUOTE = 3;
    private static final int STATE_BASE = 0;
    private static final int STATE_FIFTH_CURR = 8;
    private static final int STATE_FIRST_CURR = 4;
    private static final int STATE_FIRST_QUOTE = 1;
    private static final int STATE_FOURTH_CURR = 7;
    private static final int STATE_INSIDE_QUOTE = 2;
    private static final int STATE_OVERFLOW_CURR = 9;
    private static final int STATE_SECOND_CURR = 5;
    private static final int STATE_THIRD_CURR = 6;
    private static final int TYPE_CODEPOINT = 0;
    public static final int TYPE_CURRENCY_DOUBLE = -6;
    public static final int TYPE_CURRENCY_OVERFLOW = -15;
    public static final int TYPE_CURRENCY_QUAD = -8;
    public static final int TYPE_CURRENCY_QUINT = -9;
    public static final int TYPE_CURRENCY_SINGLE = -5;
    public static final int TYPE_CURRENCY_TRIPLE = -7;
    public static final int TYPE_MINUS_SIGN = -1;
    public static final int TYPE_PERCENT = -3;
    public static final int TYPE_PERMILLE = -4;
    public static final int TYPE_PLUS_SIGN = -2;

    public static boolean containsOnlySymbolsAndIgnorables(CharSequence charSequence, UnicodeSet unicodeSet) {
        if (charSequence == null) {
            return true;
        }
        long l = 0L;
        while (AffixUtils.hasNext(l, charSequence)) {
            int n = AffixUtils.getTypeOrCp(l = AffixUtils.nextToken(l, charSequence));
            if (n < 0 || unicodeSet.contains(n)) continue;
            return false;
        }
        return true;
    }

    public static boolean containsType(CharSequence charSequence, int n) {
        if (charSequence != null && charSequence.length() != 0) {
            long l = 0L;
            while (AffixUtils.hasNext(l, charSequence)) {
                long l2;
                l = l2 = AffixUtils.nextToken(l, charSequence);
                if (AffixUtils.getTypeOrCp(l2) != n) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    public static int escape(CharSequence charSequence, StringBuilder stringBuilder) {
        int n;
        if (charSequence == null) {
            return 0;
        }
        int n2 = 0;
        int n3 = stringBuilder.length();
        for (int i = 0; i < charSequence.length(); i += Character.charCount((int)n)) {
            n = Character.codePointAt(charSequence, i);
            if (n != 37) {
                if (n != 39) {
                    if (n != 43 && n != 45 && n != 164 && n != 8240) {
                        if (n2 == 2) {
                            stringBuilder.append('\'');
                            stringBuilder.appendCodePoint(n);
                            n2 = 0;
                            continue;
                        }
                        stringBuilder.appendCodePoint(n);
                        continue;
                    }
                } else {
                    stringBuilder.append("''");
                    continue;
                }
            }
            if (n2 == 0) {
                stringBuilder.append('\'');
                stringBuilder.appendCodePoint(n);
                n2 = 2;
                continue;
            }
            stringBuilder.appendCodePoint(n);
        }
        if (n2 == 2) {
            stringBuilder.append('\'');
        }
        return stringBuilder.length() - n3;
    }

    public static String escape(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        AffixUtils.escape(charSequence, stringBuilder);
        return stringBuilder.toString();
    }

    public static int estimateLength(CharSequence charSequence) {
        int n;
        if (charSequence == null) {
            return 0;
        }
        int n2 = 0;
        int n3 = 0;
        for (int i = 0; i < charSequence.length(); i += Character.charCount((int)n)) {
            n = Character.codePointAt(charSequence, i);
            if (n2 != 0) {
                if (n2 != 1) {
                    if (n2 != 2) {
                        if (n2 == 3) {
                            if (n == 39) {
                                ++n3;
                                n2 = 2;
                                continue;
                            }
                            ++n3;
                            continue;
                        }
                        throw new AssertionError();
                    }
                    if (n == 39) {
                        n2 = 3;
                        continue;
                    }
                    ++n3;
                    continue;
                }
                if (n == 39) {
                    ++n3;
                    n2 = 0;
                    continue;
                }
                ++n3;
                n2 = 2;
                continue;
            }
            if (n == 39) {
                n2 = 1;
                continue;
            }
            ++n3;
        }
        if (n2 != 1 && n2 != 2) {
            return n3;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unterminated quote: \"");
        stringBuilder.append((Object)charSequence);
        stringBuilder.append("\"");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static int getCodePoint(long l) {
        return (int)(l >>> 40);
    }

    public static final NumberFormat.Field getFieldForType(int n) {
        if (n != -15) {
            switch (n) {
                default: {
                    throw new AssertionError();
                }
                case -1: {
                    return NumberFormat.Field.SIGN;
                }
                case -2: {
                    return NumberFormat.Field.SIGN;
                }
                case -3: {
                    return NumberFormat.Field.PERCENT;
                }
                case -4: {
                    return NumberFormat.Field.PERMILLE;
                }
                case -5: {
                    return NumberFormat.Field.CURRENCY;
                }
                case -6: {
                    return NumberFormat.Field.CURRENCY;
                }
                case -7: {
                    return NumberFormat.Field.CURRENCY;
                }
                case -8: {
                    return NumberFormat.Field.CURRENCY;
                }
                case -9: 
            }
            return NumberFormat.Field.CURRENCY;
        }
        return NumberFormat.Field.CURRENCY;
    }

    private static int getOffset(long l) {
        return (int)(-1L & l);
    }

    private static int getState(long l) {
        return (int)(l >>> 36 & 15L);
    }

    private static int getType(long l) {
        return (int)(l >>> 32 & 15L);
    }

    private static int getTypeOrCp(long l) {
        int n = AffixUtils.getType(l);
        n = n == 0 ? AffixUtils.getCodePoint(l) : -n;
        return n;
    }

    public static boolean hasCurrencySymbols(CharSequence charSequence) {
        if (charSequence != null && charSequence.length() != 0) {
            long l = 0L;
            while (AffixUtils.hasNext(l, charSequence)) {
                int n = AffixUtils.getTypeOrCp(l = AffixUtils.nextToken(l, charSequence));
                if (n >= 0 || AffixUtils.getFieldForType(n) != NumberFormat.Field.CURRENCY) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    private static boolean hasNext(long l, CharSequence charSequence) {
        int n = AffixUtils.getState(l);
        int n2 = AffixUtils.getOffset(l);
        boolean bl = false;
        if (n == 2 && n2 == charSequence.length() - 1 && charSequence.charAt(n2) == '\'') {
            return false;
        }
        if (n != 0) {
            return true;
        }
        if (n2 < charSequence.length()) {
            bl = true;
        }
        return bl;
    }

    public static void iterateWithConsumer(CharSequence charSequence, TokenConsumer tokenConsumer) {
        long l = 0L;
        while (AffixUtils.hasNext(l, charSequence)) {
            l = AffixUtils.nextToken(l, charSequence);
            tokenConsumer.consumeToken(AffixUtils.getTypeOrCp(l));
        }
    }

    private static long makeTag(int n, int n2, int n3, int n4) {
        long l = n;
        long l2 = -((long)n2);
        long l3 = n3;
        long l4 = n4;
        return 0L | l | l2 << 32 | l3 << 36 | l4 << 40;
    }

    private static long nextToken(long l, CharSequence charSequence) {
        int n = AffixUtils.getOffset(l);
        int n2 = AffixUtils.getState(l);
        block23 : while (n < charSequence.length()) {
            int n3 = Character.codePointAt(charSequence, n);
            int n4 = Character.charCount(n3);
            switch (n2) {
                default: {
                    throw new AssertionError();
                }
                case 9: {
                    if (n3 == 164) {
                        n += n4;
                        continue block23;
                    }
                    return AffixUtils.makeTag(n, -15, 0, 0);
                }
                case 8: {
                    if (n3 == 164) {
                        n2 = 9;
                        n += n4;
                        continue block23;
                    }
                    return AffixUtils.makeTag(n, -9, 0, 0);
                }
                case 7: {
                    if (n3 == 164) {
                        n2 = 8;
                        n += n4;
                        continue block23;
                    }
                    return AffixUtils.makeTag(n, -8, 0, 0);
                }
                case 6: {
                    if (n3 == 164) {
                        n2 = 7;
                        n += n4;
                        continue block23;
                    }
                    return AffixUtils.makeTag(n, -7, 0, 0);
                }
                case 5: {
                    if (n3 == 164) {
                        n2 = 6;
                        n += n4;
                        continue block23;
                    }
                    return AffixUtils.makeTag(n, -6, 0, 0);
                }
                case 4: {
                    if (n3 == 164) {
                        n2 = 5;
                        n += n4;
                        continue block23;
                    }
                    return AffixUtils.makeTag(n, -5, 0, 0);
                }
                case 3: {
                    if (n3 == 39) {
                        return AffixUtils.makeTag(n + n4, 0, 2, n3);
                    }
                    n2 = 0;
                    continue block23;
                }
                case 2: {
                    if (n3 == 39) {
                        n2 = 3;
                        n += n4;
                        continue block23;
                    }
                    return AffixUtils.makeTag(n + n4, 0, 2, n3);
                }
                case 1: {
                    if (n3 == 39) {
                        return AffixUtils.makeTag(n + n4, 0, 0, n3);
                    }
                    return AffixUtils.makeTag(n + n4, 0, 2, n3);
                }
                case 0: 
            }
            if (n3 != 37) {
                if (n3 != 39) {
                    if (n3 != 43) {
                        if (n3 != 45) {
                            if (n3 != 164) {
                                if (n3 != 8240) {
                                    return AffixUtils.makeTag(n + n4, 0, 0, n3);
                                }
                                return AffixUtils.makeTag(n + n4, -4, 0, 0);
                            }
                            n2 = 4;
                            n += n4;
                            continue;
                        }
                        return AffixUtils.makeTag(n + n4, -1, 0, 0);
                    }
                    return AffixUtils.makeTag(n + n4, -2, 0, 0);
                }
                n2 = 1;
                n += n4;
                continue;
            }
            return AffixUtils.makeTag(n + n4, -3, 0, 0);
        }
        switch (n2) {
            default: {
                throw new AssertionError();
            }
            case 9: {
                return AffixUtils.makeTag(n, -15, 0, 0);
            }
            case 8: {
                return AffixUtils.makeTag(n, -9, 0, 0);
            }
            case 7: {
                return AffixUtils.makeTag(n, -8, 0, 0);
            }
            case 6: {
                return AffixUtils.makeTag(n, -7, 0, 0);
            }
            case 5: {
                return AffixUtils.makeTag(n, -6, 0, 0);
            }
            case 4: {
                return AffixUtils.makeTag(n, -5, 0, 0);
            }
            case 3: {
                return -1L;
            }
            case 1: 
            case 2: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unterminated quote in pattern affix: \"");
                stringBuilder.append((Object)charSequence);
                stringBuilder.append("\"");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case 0: 
        }
        return -1L;
    }

    public static String replaceType(CharSequence charSequence, int n, char c) {
        if (charSequence != null && charSequence.length() != 0) {
            char[] arrc = charSequence.toString().toCharArray();
            long l = 0L;
            while (AffixUtils.hasNext(l, charSequence)) {
                long l2;
                l = l2 = AffixUtils.nextToken(l, charSequence);
                if (AffixUtils.getTypeOrCp(l2) != n) continue;
                arrc[AffixUtils.getOffset((long)l2) - 1] = c;
                l = l2;
            }
            return new String(arrc);
        }
        return "";
    }

    public static int unescape(CharSequence charSequence, NumberStringBuilder numberStringBuilder, int n, SymbolProvider symbolProvider) {
        int n2 = 0;
        long l = 0L;
        while (AffixUtils.hasNext(l, charSequence)) {
            int n3 = AffixUtils.getTypeOrCp(l = AffixUtils.nextToken(l, charSequence));
            if (n3 == -15) {
                n2 += numberStringBuilder.insertCodePoint(n + n2, 65533, NumberFormat.Field.CURRENCY);
                continue;
            }
            if (n3 < 0) {
                n2 += numberStringBuilder.insert(n + n2, symbolProvider.getSymbol(n3), AffixUtils.getFieldForType(n3));
                continue;
            }
            n2 += numberStringBuilder.insertCodePoint(n + n2, n3, null);
        }
        return n2;
    }

    public static int unescapedCount(CharSequence charSequence, boolean bl, SymbolProvider symbolProvider) {
        int n = 0;
        long l = 0L;
        while (AffixUtils.hasNext(l, charSequence)) {
            int n2 = AffixUtils.getTypeOrCp(l = AffixUtils.nextToken(l, charSequence));
            if (n2 == -15) {
                ++n;
                continue;
            }
            if (n2 < 0) {
                CharSequence charSequence2 = symbolProvider.getSymbol(n2);
                n2 = bl ? charSequence2.length() : Character.codePointCount(charSequence2, 0, charSequence2.length());
                n += n2;
                continue;
            }
            n2 = bl ? Character.charCount(n2) : 1;
            n += n2;
        }
        return n;
    }

    public static interface SymbolProvider {
        public CharSequence getSymbol(int var1);
    }

    public static interface TokenConsumer {
        public void consumeToken(int var1);
    }

}

