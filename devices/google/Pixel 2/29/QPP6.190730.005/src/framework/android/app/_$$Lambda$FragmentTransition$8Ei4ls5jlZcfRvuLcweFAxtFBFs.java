/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Fragment;
import android.app.FragmentTransition;
import android.transition.Transition;
import android.view.View;
import java.util.ArrayList;

public final class _$$Lambda$FragmentTransition$8Ei4ls5jlZcfRvuLcweFAxtFBFs
implements Runnable {
    private final /* synthetic */ Transition f$0;
    private final /* synthetic */ View f$1;
    private final /* synthetic */ Fragment f$2;
    private final /* synthetic */ ArrayList f$3;
    private final /* synthetic */ ArrayList f$4;
    private final /* synthetic */ ArrayList f$5;
    private final /* synthetic */ Transition f$6;

    public /* synthetic */ _$$Lambda$FragmentTransition$8Ei4ls5jlZcfRvuLcweFAxtFBFs(Transition transition2, View view, Fragment fragment, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, Transition transition3) {
        this.f$0 = transition2;
        this.f$1 = view;
        this.f$2 = fragment;
        this.f$3 = arrayList;
        this.f$4 = arrayList2;
        this.f$5 = arrayList3;
        this.f$6 = transition3;
    }

    @Override
    public final void run() {
        FragmentTransition.lambda$scheduleTargetChange$1(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6);
    }
}

