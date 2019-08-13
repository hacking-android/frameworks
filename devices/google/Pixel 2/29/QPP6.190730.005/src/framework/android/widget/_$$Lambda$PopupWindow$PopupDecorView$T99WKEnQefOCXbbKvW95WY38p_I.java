/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.transition.Transition;
import android.view.View;
import android.widget.PopupWindow;

public final class _$$Lambda$PopupWindow$PopupDecorView$T99WKEnQefOCXbbKvW95WY38p_I
implements Runnable {
    private final /* synthetic */ PopupWindow.PopupDecorView f$0;
    private final /* synthetic */ Transition.TransitionListener f$1;
    private final /* synthetic */ Transition f$2;
    private final /* synthetic */ View f$3;

    public /* synthetic */ _$$Lambda$PopupWindow$PopupDecorView$T99WKEnQefOCXbbKvW95WY38p_I(PopupWindow.PopupDecorView popupDecorView, Transition.TransitionListener transitionListener, Transition transition2, View view) {
        this.f$0 = popupDecorView;
        this.f$1 = transitionListener;
        this.f$2 = transition2;
        this.f$3 = view;
    }

    @Override
    public final void run() {
        this.f$0.lambda$startExitTransition$0$PopupWindow$PopupDecorView(this.f$1, this.f$2, this.f$3);
    }
}

