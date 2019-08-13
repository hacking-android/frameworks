/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.cat;

public enum TextColor {
    BLACK(0),
    DARK_GRAY(1),
    DARK_RED(2),
    DARK_YELLOW(3),
    DARK_GREEN(4),
    DARK_CYAN(5),
    DARK_BLUE(6),
    DARK_MAGENTA(7),
    GRAY(8),
    WHITE(9),
    BRIGHT_RED(10),
    BRIGHT_YELLOW(11),
    BRIGHT_GREEN(12),
    BRIGHT_CYAN(13),
    BRIGHT_BLUE(14),
    BRIGHT_MAGENTA(15);
    
    private int mValue;

    private TextColor(int n2) {
        this.mValue = n2;
    }

    public static TextColor fromInt(int n) {
        for (TextColor textColor : TextColor.values()) {
            if (textColor.mValue != n) continue;
            return textColor;
        }
        return null;
    }
}

