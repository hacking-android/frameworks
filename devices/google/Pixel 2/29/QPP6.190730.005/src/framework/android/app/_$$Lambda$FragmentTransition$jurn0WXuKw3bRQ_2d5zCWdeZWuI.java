/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Fragment;
import android.app.FragmentTransition;
import android.graphics.Rect;
import android.util.ArrayMap;
import android.view.View;

public final class _$$Lambda$FragmentTransition$jurn0WXuKw3bRQ_2d5zCWdeZWuI
implements Runnable {
    private final /* synthetic */ Fragment f$0;
    private final /* synthetic */ Fragment f$1;
    private final /* synthetic */ boolean f$2;
    private final /* synthetic */ ArrayMap f$3;
    private final /* synthetic */ View f$4;
    private final /* synthetic */ Rect f$5;

    public /* synthetic */ _$$Lambda$FragmentTransition$jurn0WXuKw3bRQ_2d5zCWdeZWuI(Fragment fragment, Fragment fragment2, boolean bl, ArrayMap arrayMap, View view, Rect rect) {
        this.f$0 = fragment;
        this.f$1 = fragment2;
        this.f$2 = bl;
        this.f$3 = arrayMap;
        this.f$4 = view;
        this.f$5 = rect;
    }

    @Override
    public final void run() {
        FragmentTransition.lambda$configureSharedElementsReordered$2(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5);
    }
}

