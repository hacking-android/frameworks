/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.IntTrieBuilder;
import android.icu.impl.PropsVectors;

public class PVecToTrieCompactHandler
implements PropsVectors.CompactHandler {
    public IntTrieBuilder builder;
    public int initialValue;

    @Override
    public void setRowIndexForErrorValue(int n) {
    }

    @Override
    public void setRowIndexForInitialValue(int n) {
        this.initialValue = n;
    }

    @Override
    public void setRowIndexForRange(int n, int n2, int n3) {
        this.builder.setRange(n, n2 + 1, n3, true);
    }

    @Override
    public void startRealValues(int n) {
        if (n <= 65535) {
            n = this.initialValue;
            this.builder = new IntTrieBuilder(null, 100000, n, n, false);
            return;
        }
        throw new IndexOutOfBoundsException();
    }
}

