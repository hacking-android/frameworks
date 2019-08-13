/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.view.View;
import android.widget.ImageButton;
import com.android.internal.widget.FloatingToolbar;

public final class _$$Lambda$FloatingToolbar$FloatingToolbarPopup$_uEfRwR__1oHxMvRVdmbNRdukDM
implements View.OnClickListener {
    private final /* synthetic */ FloatingToolbar.FloatingToolbarPopup f$0;
    private final /* synthetic */ ImageButton f$1;

    public /* synthetic */ _$$Lambda$FloatingToolbar$FloatingToolbarPopup$_uEfRwR__1oHxMvRVdmbNRdukDM(FloatingToolbar.FloatingToolbarPopup floatingToolbarPopup, ImageButton imageButton) {
        this.f$0 = floatingToolbarPopup;
        this.f$1 = imageButton;
    }

    @Override
    public final void onClick(View view) {
        this.f$0.lambda$createOverflowButton$1$FloatingToolbar$FloatingToolbarPopup(this.f$1, view);
    }
}

