/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Fragment;
import android.app.FragmentTransition;
import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.ArrayMap;
import android.view.View;
import java.util.ArrayList;

public final class _$$Lambda$FragmentTransition$Ip0LktADPhG_3ouNBXgzufWpFfY
implements Runnable {
    private final /* synthetic */ ArrayMap f$0;
    private final /* synthetic */ TransitionSet f$1;
    private final /* synthetic */ Rect f$10;
    private final /* synthetic */ FragmentTransition.FragmentContainerTransition f$2;
    private final /* synthetic */ ArrayList f$3;
    private final /* synthetic */ View f$4;
    private final /* synthetic */ Fragment f$5;
    private final /* synthetic */ Fragment f$6;
    private final /* synthetic */ boolean f$7;
    private final /* synthetic */ ArrayList f$8;
    private final /* synthetic */ Transition f$9;

    public /* synthetic */ _$$Lambda$FragmentTransition$Ip0LktADPhG_3ouNBXgzufWpFfY(ArrayMap arrayMap, TransitionSet transitionSet, FragmentTransition.FragmentContainerTransition fragmentContainerTransition, ArrayList arrayList, View view, Fragment fragment, Fragment fragment2, boolean bl, ArrayList arrayList2, Transition transition2, Rect rect) {
        this.f$0 = arrayMap;
        this.f$1 = transitionSet;
        this.f$2 = fragmentContainerTransition;
        this.f$3 = arrayList;
        this.f$4 = view;
        this.f$5 = fragment;
        this.f$6 = fragment2;
        this.f$7 = bl;
        this.f$8 = arrayList2;
        this.f$9 = transition2;
        this.f$10 = rect;
    }

    @Override
    public final void run() {
        FragmentTransition.lambda$configureSharedElementsOrdered$3(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, this.f$7, this.f$8, this.f$9, this.f$10);
    }
}

