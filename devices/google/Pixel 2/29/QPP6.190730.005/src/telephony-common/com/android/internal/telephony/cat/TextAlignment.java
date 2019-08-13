/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.cat;

public enum TextAlignment {
    LEFT(0),
    CENTER(1),
    RIGHT(2),
    DEFAULT(3);
    
    private int mValue;

    private TextAlignment(int n2) {
        this.mValue = n2;
    }

    public static TextAlignment fromInt(int n) {
        for (TextAlignment textAlignment : TextAlignment.values()) {
            if (textAlignment.mValue != n) continue;
            return textAlignment;
        }
        return null;
    }
}

