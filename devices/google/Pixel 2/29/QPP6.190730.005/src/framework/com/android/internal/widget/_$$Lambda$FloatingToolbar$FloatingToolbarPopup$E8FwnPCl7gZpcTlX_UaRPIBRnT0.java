/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.view.View;
import android.widget.AdapterView;
import com.android.internal.widget.FloatingToolbar;

public final class _$$Lambda$FloatingToolbar$FloatingToolbarPopup$E8FwnPCl7gZpcTlX_UaRPIBRnT0
implements AdapterView.OnItemClickListener {
    private final /* synthetic */ FloatingToolbar.FloatingToolbarPopup f$0;
    private final /* synthetic */ FloatingToolbar.FloatingToolbarPopup.OverflowPanel f$1;

    public /* synthetic */ _$$Lambda$FloatingToolbar$FloatingToolbarPopup$E8FwnPCl7gZpcTlX_UaRPIBRnT0(FloatingToolbar.FloatingToolbarPopup floatingToolbarPopup, FloatingToolbar.FloatingToolbarPopup.OverflowPanel overflowPanel) {
        this.f$0 = floatingToolbarPopup;
        this.f$1 = overflowPanel;
    }

    public final void onItemClick(AdapterView adapterView, View view, int n, long l) {
        this.f$0.lambda$createOverflowPanel$2$FloatingToolbar$FloatingToolbarPopup(this.f$1, adapterView, view, n, l);
    }
}

