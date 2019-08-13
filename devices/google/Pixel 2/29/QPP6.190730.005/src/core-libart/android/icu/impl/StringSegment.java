/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.Utility;
import android.icu.lang.UCharacter;
import android.icu.text.UnicodeSet;

public class StringSegment
implements CharSequence {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private int end;
    private boolean foldCase;
    private int start;
    private final String str;

    public StringSegment(String string, boolean bl) {
        this.str = string;
        this.start = 0;
        this.end = string.length();
        this.foldCase = bl;
    }

    private static final boolean codePointsEqual(int n, int n2, boolean bl) {
        boolean bl2 = true;
        if (n == n2) {
            return true;
        }
        if (!bl) {
            return false;
        }
        bl = UCharacter.foldCase(n, true) == UCharacter.foldCase(n2, true) ? bl2 : false;
        return bl;
    }

    private int getPrefixLengthInternal(CharSequence charSequence, boolean bl) {
        int n;
        int n2;
        for (n2 = 0; n2 < Math.min(this.length(), charSequence.length()) && StringSegment.codePointsEqual(n = Character.codePointAt(this, n2), Character.codePointAt(charSequence, n2), bl); n2 += Character.charCount((int)n)) {
        }
        return n2;
    }

    public void adjustOffset(int n) {
        this.start += n;
    }

    public void adjustOffsetByCodePoint() {
        this.start += Character.charCount(this.getCodePoint());
    }

    @Override
    public char charAt(int n) {
        return this.str.charAt(this.start + n);
    }

    public int codePointAt(int n) {
        return this.str.codePointAt(this.start + n);
    }

    public boolean equals(Object object) {
        if (!(object instanceof CharSequence)) {
            return false;
        }
        return Utility.charSequenceEquals(this, (CharSequence)object);
    }

    public int getCaseSensitivePrefixLength(CharSequence charSequence) {
        return this.getPrefixLengthInternal(charSequence, false);
    }

    public int getCodePoint() {
        int n;
        char c;
        char c2 = this.str.charAt(this.start);
        if (Character.isHighSurrogate(c2) && (n = this.start) + 1 < this.end && Character.isLowSurrogate(c = this.str.charAt(n + 1))) {
            return Character.toCodePoint(c2, c);
        }
        return c2;
    }

    public int getCommonPrefixLength(CharSequence charSequence) {
        return this.getPrefixLengthInternal(charSequence, this.foldCase);
    }

    public int getOffset() {
        return this.start;
    }

    public int hashCode() {
        return Utility.charSequenceHashCode(this);
    }

    @Override
    public int length() {
        return this.end - this.start;
    }

    public void resetLength() {
        this.end = this.str.length();
    }

    public void setLength(int n) {
        this.end = this.start + n;
    }

    public void setOffset(int n) {
        this.start = n;
    }

    public boolean startsWith(int n) {
        return StringSegment.codePointsEqual(this.getCodePoint(), n, this.foldCase);
    }

    public boolean startsWith(UnicodeSet unicodeSet) {
        int n = this.getCodePoint();
        if (n == -1) {
            return false;
        }
        return unicodeSet.contains(n);
    }

    public boolean startsWith(CharSequence charSequence) {
        if (charSequence != null && charSequence.length() != 0 && this.length() != 0) {
            return StringSegment.codePointsEqual(Character.codePointAt(this, 0), Character.codePointAt(charSequence, 0), this.foldCase);
        }
        return false;
    }

    @Override
    public CharSequence subSequence(int n, int n2) {
        String string = this.str;
        int n3 = this.start;
        return string.subSequence(n + n3, n3 + n2);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.str.substring(0, this.start));
        stringBuilder.append("[");
        stringBuilder.append(this.str.substring(this.start, this.end));
        stringBuilder.append("]");
        stringBuilder.append(this.str.substring(this.end));
        return stringBuilder.toString();
    }
}

