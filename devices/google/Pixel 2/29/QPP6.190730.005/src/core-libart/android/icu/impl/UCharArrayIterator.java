/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.text.UCharacterIterator;

public final class UCharArrayIterator
extends UCharacterIterator {
    private final int limit;
    private int pos;
    private final int start;
    private final char[] text;

    public UCharArrayIterator(char[] arrc, int n, int n2) {
        if (n >= 0 && n2 <= arrc.length && n <= n2) {
            this.text = arrc;
            this.start = n;
            this.limit = n2;
            this.pos = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("start: ");
        stringBuilder.append(n);
        stringBuilder.append(" or limit: ");
        stringBuilder.append(n2);
        stringBuilder.append(" out of range [0, ");
        stringBuilder.append(arrc.length);
        stringBuilder.append(")");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public Object clone() {
        try {
            Object object = super.clone();
            return object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    @Override
    public int current() {
        int n = this.pos;
        n = n < this.limit ? this.text[n] : -1;
        return n;
    }

    @Override
    public int getIndex() {
        return this.pos - this.start;
    }

    @Override
    public int getLength() {
        return this.limit - this.start;
    }

    @Override
    public int getText(char[] arrc, int n) {
        int n2 = this.limit;
        int n3 = this.start;
        System.arraycopy(this.text, n3, arrc, n, n2 -= n3);
        return n2;
    }

    @Override
    public int next() {
        int n = this.pos;
        if (n < this.limit) {
            char[] arrc = this.text;
            this.pos = n + 1;
            n = arrc[n];
        } else {
            n = -1;
        }
        return n;
    }

    @Override
    public int previous() {
        int n = this.pos;
        if (n > this.start) {
            char[] arrc = this.text;
            this.pos = --n;
            n = arrc[n];
        } else {
            n = -1;
        }
        return n;
    }

    @Override
    public void setIndex(int n) {
        int n2;
        int n3;
        if (n >= 0 && n <= (n2 = this.limit) - (n3 = this.start)) {
            this.pos = n3 + n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("index: ");
        stringBuilder.append(n);
        stringBuilder.append(" out of range [0, ");
        stringBuilder.append(this.limit - this.start);
        stringBuilder.append(")");
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }
}

