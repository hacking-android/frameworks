/*
 * Decompiled with CFR 0.145.
 */
package android.icu.number;

import android.icu.number.CompactNotation;
import android.icu.number.NumberFormatter;
import android.icu.number.ScientificNotation;
import android.icu.number.SimpleNotation;
import android.icu.text.CompactDecimalFormat;

public class Notation {
    private static final CompactNotation COMPACT_LONG;
    private static final CompactNotation COMPACT_SHORT;
    private static final ScientificNotation ENGINEERING;
    private static final ScientificNotation SCIENTIFIC;
    private static final SimpleNotation SIMPLE;

    static {
        SCIENTIFIC = new ScientificNotation(1, false, 1, NumberFormatter.SignDisplay.AUTO);
        ENGINEERING = new ScientificNotation(3, false, 1, NumberFormatter.SignDisplay.AUTO);
        COMPACT_SHORT = new CompactNotation(CompactDecimalFormat.CompactStyle.SHORT);
        COMPACT_LONG = new CompactNotation(CompactDecimalFormat.CompactStyle.LONG);
        SIMPLE = new SimpleNotation();
    }

    Notation() {
    }

    public static CompactNotation compactLong() {
        return COMPACT_LONG;
    }

    public static CompactNotation compactShort() {
        return COMPACT_SHORT;
    }

    public static ScientificNotation engineering() {
        return ENGINEERING;
    }

    public static ScientificNotation scientific() {
        return SCIENTIFIC;
    }

    public static SimpleNotation simple() {
        return SIMPLE;
    }
}

