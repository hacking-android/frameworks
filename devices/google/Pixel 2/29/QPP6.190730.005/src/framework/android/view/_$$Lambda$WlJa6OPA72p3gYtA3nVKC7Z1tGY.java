/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.view.View;

public final class _$$Lambda$WlJa6OPA72p3gYtA3nVKC7Z1tGY
implements Runnable {
    private final /* synthetic */ View f$0;

    public /* synthetic */ _$$Lambda$WlJa6OPA72p3gYtA3nVKC7Z1tGY(View view) {
        this.f$0 = view;
    }

    @Override
    public final void run() {
        this.f$0.updateSystemGestureExclusionRects();
    }
}

