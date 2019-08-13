/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.util.SparseArray;
import java.util.function.IntFunction;

public final class _$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI
implements IntFunction {
    private final /* synthetic */ SparseArray f$0;

    public /* synthetic */ _$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(SparseArray sparseArray) {
        this.f$0 = sparseArray;
    }

    public final Object apply(int n) {
        return (String)this.f$0.get(n);
    }
}

