/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.cat;

import com.android.internal.telephony.cat.FontSize;
import com.android.internal.telephony.cat.TextAlignment;
import com.android.internal.telephony.cat.TextColor;

public class TextAttribute {
    public TextAlignment align;
    public boolean bold;
    public TextColor color;
    public boolean italic;
    public int length;
    public FontSize size;
    public int start;
    public boolean strikeThrough;
    public boolean underlined;

    public TextAttribute(int n, int n2, TextAlignment textAlignment, FontSize fontSize, boolean bl, boolean bl2, boolean bl3, boolean bl4, TextColor textColor) {
        this.start = n;
        this.length = n2;
        this.align = textAlignment;
        this.size = fontSize;
        this.bold = bl;
        this.italic = bl2;
        this.underlined = bl3;
        this.strikeThrough = bl4;
        this.color = textColor;
    }
}

