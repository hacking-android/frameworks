/*
 * Decompiled with CFR 0.145.
 */
package android.app.usage;

import android.util.LongSparseArray;
import android.util.Slog;

public class TimeSparseArray<E>
extends LongSparseArray<E> {
    private static final String TAG = TimeSparseArray.class.getSimpleName();
    private boolean mWtfReported;

    public int closestIndexOnOrAfter(long l) {
        int n = this.size();
        int n2 = 0;
        int n3 = n - 1;
        int n4 = -1;
        long l2 = -1L;
        while (n2 <= n3) {
            n4 = n2 + (n3 - n2) / 2;
            l2 = this.keyAt(n4);
            if (l > l2) {
                n2 = n4 + 1;
                continue;
            }
            if (l < l2) {
                n3 = n4 - 1;
                continue;
            }
            return n4;
        }
        if (l < l2) {
            return n4;
        }
        if (l > l2 && n2 < n) {
            return n2;
        }
        return -1;
    }

    public int closestIndexOnOrBefore(long l) {
        int n = this.closestIndexOnOrAfter(l);
        if (n < 0) {
            return this.size() - 1;
        }
        if (this.keyAt(n) == l) {
            return n;
        }
        return n - 1;
    }

    @Override
    public void put(long l, E e) {
        if (this.indexOfKey(l) >= 0 && !this.mWtfReported) {
            String string2 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Overwriting value ");
            stringBuilder.append(this.get(l));
            stringBuilder.append(" by ");
            stringBuilder.append(e);
            Slog.wtf(string2, stringBuilder.toString());
            this.mWtfReported = true;
        }
        super.put(l, e);
    }
}

