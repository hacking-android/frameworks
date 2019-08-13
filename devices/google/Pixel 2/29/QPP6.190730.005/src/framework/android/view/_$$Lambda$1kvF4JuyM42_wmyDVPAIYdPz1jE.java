/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.view.RenderNodeAnimator;

public final class _$$Lambda$1kvF4JuyM42_wmyDVPAIYdPz1jE
implements Runnable {
    private final /* synthetic */ RenderNodeAnimator f$0;

    public /* synthetic */ _$$Lambda$1kvF4JuyM42_wmyDVPAIYdPz1jE(RenderNodeAnimator renderNodeAnimator) {
        this.f$0 = renderNodeAnimator;
    }

    @Override
    public final void run() {
        this.f$0.onFinished();
    }
}

