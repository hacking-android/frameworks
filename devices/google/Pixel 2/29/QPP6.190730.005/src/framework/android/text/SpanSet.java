/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.annotation.UnsupportedAppUsage;
import android.text.Spanned;
import java.lang.reflect.Array;
import java.util.Arrays;

public class SpanSet<E> {
    private final Class<? extends E> classType;
    int numberOfSpans;
    int[] spanEnds;
    int[] spanFlags;
    int[] spanStarts;
    @UnsupportedAppUsage
    E[] spans;

    SpanSet(Class<? extends E> class_) {
        this.classType = class_;
        this.numberOfSpans = 0;
    }

    int getNextTransition(int n, int n2) {
        for (int i = 0; i < this.numberOfSpans; ++i) {
            int n3 = this.spanStarts[i];
            int n4 = this.spanEnds[i];
            int n5 = n2;
            if (n3 > n) {
                n5 = n2;
                if (n3 < n2) {
                    n5 = n3;
                }
            }
            n2 = n5;
            if (n4 <= n) continue;
            n2 = n5;
            if (n4 >= n5) continue;
            n2 = n4;
        }
        return n2;
    }

    public boolean hasSpansIntersecting(int n, int n2) {
        for (int i = 0; i < this.numberOfSpans; ++i) {
            if (this.spanStarts[i] >= n2 || this.spanEnds[i] <= n) continue;
            return true;
        }
        return false;
    }

    public void init(Spanned spanned, int n, int n2) {
        Object object;
        E[] arrE = spanned.getSpans(n, n2, this.classType);
        int n3 = arrE.length;
        if (n3 > 0 && ((object = this.spans) == null || ((E[])object).length < n3)) {
            this.spans = (Object[])Array.newInstance(this.classType, n3);
            this.spanStarts = new int[n3];
            this.spanEnds = new int[n3];
            this.spanFlags = new int[n3];
        }
        n2 = this.numberOfSpans;
        this.numberOfSpans = 0;
        for (n = 0; n < n3; ++n) {
            int n4;
            object = arrE[n];
            int n5 = spanned.getSpanStart(object);
            if (n5 == (n4 = spanned.getSpanEnd(object))) continue;
            int n6 = spanned.getSpanFlags(object);
            E[] arrE2 = this.spans;
            int n7 = this.numberOfSpans;
            arrE2[n7] = object;
            this.spanStarts[n7] = n5;
            this.spanEnds[n7] = n4;
            this.spanFlags[n7] = n6;
            this.numberOfSpans = n7 + 1;
        }
        n = this.numberOfSpans;
        if (n < n2) {
            Arrays.fill(this.spans, n, n2, null);
        }
    }

    public void recycle() {
        Object[] arrobject = this.spans;
        if (arrobject != null) {
            Arrays.fill(arrobject, 0, this.numberOfSpans, null);
        }
    }
}

