/*
 * Decompiled with CFR 0.145.
 */
package android.icu.lang;

import android.icu.lang.UCharacterEnums;

public final class UCharacterCategory
implements UCharacterEnums.ECharacterCategory {
    private UCharacterCategory() {
    }

    public static String toString(int n) {
        switch (n) {
            default: {
                return "Unassigned";
            }
            case 29: {
                return "Punctuation, Final quote";
            }
            case 28: {
                return "Punctuation, Initial quote";
            }
            case 27: {
                return "Symbol, Other";
            }
            case 26: {
                return "Symbol, Modifier";
            }
            case 25: {
                return "Symbol, Currency";
            }
            case 24: {
                return "Symbol, Math";
            }
            case 23: {
                return "Punctuation, Other";
            }
            case 22: {
                return "Punctuation, Connector";
            }
            case 21: {
                return "Punctuation, Close";
            }
            case 20: {
                return "Punctuation, Open";
            }
            case 19: {
                return "Punctuation, Dash";
            }
            case 18: {
                return "Other, Surrogate";
            }
            case 17: {
                return "Other, Private Use";
            }
            case 16: {
                return "Other, Format";
            }
            case 15: {
                return "Other, Control";
            }
            case 14: {
                return "Separator, Paragraph";
            }
            case 13: {
                return "Separator, Line";
            }
            case 12: {
                return "Separator, Space";
            }
            case 11: {
                return "Number, Other";
            }
            case 10: {
                return "Number, Letter";
            }
            case 9: {
                return "Number, Decimal Digit";
            }
            case 8: {
                return "Mark, Spacing Combining";
            }
            case 7: {
                return "Mark, Enclosing";
            }
            case 6: {
                return "Mark, Non-Spacing";
            }
            case 5: {
                return "Letter, Other";
            }
            case 4: {
                return "Letter, Modifier";
            }
            case 3: {
                return "Letter, Titlecase";
            }
            case 2: {
                return "Letter, Lowercase";
            }
            case 1: 
        }
        return "Letter, Uppercase";
    }
}

