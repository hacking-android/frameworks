/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.number.DecimalFormatProperties;
import android.icu.impl.number.Modifier;
import android.icu.impl.number.NumberStringBuilder;
import android.icu.text.NumberFormat;

public class Padder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String FALLBACK_PADDING_STRING = " ";
    public static final Padder NONE = new Padder(null, -1, null);
    String paddingString;
    PadPosition position;
    int targetWidth;

    public Padder(String object, int n, PadPosition padPosition) {
        if (object == null) {
            object = FALLBACK_PADDING_STRING;
        }
        this.paddingString = object;
        this.targetWidth = n;
        object = padPosition == null ? PadPosition.BEFORE_PREFIX : padPosition;
        this.position = object;
    }

    private static int addPaddingHelper(String string, int n, NumberStringBuilder numberStringBuilder, int n2) {
        for (int i = 0; i < n; ++i) {
            numberStringBuilder.insert(n2, string, null);
        }
        return string.length() * n;
    }

    public static Padder codePoints(int n, int n2, PadPosition padPosition) {
        if (n2 >= 0) {
            return new Padder(String.valueOf(Character.toChars(n)), n2, padPosition);
        }
        throw new IllegalArgumentException("Padding width must not be negative");
    }

    public static Padder forProperties(DecimalFormatProperties decimalFormatProperties) {
        return new Padder(decimalFormatProperties.getPadString(), decimalFormatProperties.getFormatWidth(), decimalFormatProperties.getPadPosition());
    }

    public static Padder none() {
        return NONE;
    }

    public boolean isValid() {
        boolean bl = this.targetWidth > 0;
        return bl;
    }

    public int padAndApply(Modifier modifier, Modifier modifier2, NumberStringBuilder numberStringBuilder, int n, int n2) {
        int n3 = modifier.getCodePointCount();
        int n4 = modifier2.getCodePointCount();
        n4 = this.targetWidth - (n3 + n4) - numberStringBuilder.codePointCount();
        n3 = 0;
        if (n4 <= 0) {
            n3 = 0 + modifier.apply(numberStringBuilder, n, n2);
            return n3 + modifier2.apply(numberStringBuilder, n, n2 + n3);
        }
        if (this.position == PadPosition.AFTER_PREFIX) {
            n3 = 0 + Padder.addPaddingHelper(this.paddingString, n4, numberStringBuilder, n);
        } else if (this.position == PadPosition.BEFORE_SUFFIX) {
            n3 = 0 + Padder.addPaddingHelper(this.paddingString, n4, numberStringBuilder, n2 + 0);
        }
        n3 += modifier.apply(numberStringBuilder, n, n2 + n3);
        n3 += modifier2.apply(numberStringBuilder, n, n2 + n3);
        if (this.position == PadPosition.BEFORE_PREFIX) {
            n = n3 + Padder.addPaddingHelper(this.paddingString, n4, numberStringBuilder, n);
        } else {
            n = n3;
            if (this.position == PadPosition.AFTER_SUFFIX) {
                n = n3 + Padder.addPaddingHelper(this.paddingString, n4, numberStringBuilder, n2 + n3);
            }
        }
        return n;
    }

    public static enum PadPosition {
        BEFORE_PREFIX,
        AFTER_PREFIX,
        BEFORE_SUFFIX,
        AFTER_SUFFIX;
        

        public static PadPosition fromOld(int n) {
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n == 3) {
                            return AFTER_SUFFIX;
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Don't know how to map ");
                        stringBuilder.append(n);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                    return BEFORE_SUFFIX;
                }
                return AFTER_PREFIX;
            }
            return BEFORE_PREFIX;
        }

        public int toOld() {
            int n = 1.$SwitchMap$android$icu$impl$number$Padder$PadPosition[this.ordinal()];
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return -1;
                        }
                        return 3;
                    }
                    return 2;
                }
                return 1;
            }
            return 0;
        }
    }

}

