/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.util.Log;
import com.android.internal.util.Preconditions;
import java.util.Arrays;

public class ExponentiallyBucketedHistogram {
    private final int[] mData;

    public ExponentiallyBucketedHistogram(int n) {
        this.mData = new int[Preconditions.checkArgumentInRange(n, 1, 31, "numBuckets")];
    }

    public void add(int n) {
        if (n <= 0) {
            int[] arrn = this.mData;
            arrn[0] = arrn[0] + 1;
        } else {
            int[] arrn = this.mData;
            n = Math.min(arrn.length - 1, 32 - Integer.numberOfLeadingZeros(n));
            arrn[n] = arrn[n] + 1;
        }
    }

    public void log(String string2, CharSequence charSequence) {
        charSequence = new StringBuilder(charSequence);
        ((StringBuilder)charSequence).append('[');
        for (int i = 0; i < this.mData.length; ++i) {
            if (i != 0) {
                ((StringBuilder)charSequence).append(", ");
            }
            if (i < this.mData.length - 1) {
                ((StringBuilder)charSequence).append("<");
                ((StringBuilder)charSequence).append(1 << i);
            } else {
                ((StringBuilder)charSequence).append(">=");
                ((StringBuilder)charSequence).append(1 << i - 1);
            }
            ((StringBuilder)charSequence).append(": ");
            ((StringBuilder)charSequence).append(this.mData[i]);
        }
        ((StringBuilder)charSequence).append("]");
        Log.d(string2, ((StringBuilder)charSequence).toString());
    }

    public void reset() {
        Arrays.fill(this.mData, 0);
    }
}

