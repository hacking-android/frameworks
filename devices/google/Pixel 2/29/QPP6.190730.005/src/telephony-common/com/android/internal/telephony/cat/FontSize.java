/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.cat;

public enum FontSize {
    NORMAL(0),
    LARGE(1),
    SMALL(2);
    
    private int mValue;

    private FontSize(int n2) {
        this.mValue = n2;
    }

    public static FontSize fromInt(int n) {
        for (FontSize fontSize : FontSize.values()) {
            if (fontSize.mValue != n) continue;
            return fontSize;
        }
        return null;
    }
}

