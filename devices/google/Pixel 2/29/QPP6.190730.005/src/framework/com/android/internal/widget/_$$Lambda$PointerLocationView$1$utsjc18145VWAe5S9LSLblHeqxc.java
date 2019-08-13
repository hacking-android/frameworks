/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.graphics.Region;
import com.android.internal.widget.PointerLocationView;

public final class _$$Lambda$PointerLocationView$1$utsjc18145VWAe5S9LSLblHeqxc
implements Runnable {
    private final /* synthetic */ PointerLocationView.1 f$0;
    private final /* synthetic */ Region f$1;

    public /* synthetic */ _$$Lambda$PointerLocationView$1$utsjc18145VWAe5S9LSLblHeqxc(PointerLocationView.1 var1_1, Region region) {
        this.f$0 = var1_1;
        this.f$1 = region;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onSystemGestureExclusionChanged$0$PointerLocationView$1(this.f$1);
    }
}

