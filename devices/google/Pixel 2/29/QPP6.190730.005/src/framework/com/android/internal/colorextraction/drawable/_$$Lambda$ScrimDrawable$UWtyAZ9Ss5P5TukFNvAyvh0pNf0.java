/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.colorextraction.drawable;

import android.animation.ValueAnimator;
import com.android.internal.colorextraction.drawable.ScrimDrawable;

public final class _$$Lambda$ScrimDrawable$UWtyAZ9Ss5P5TukFNvAyvh0pNf0
implements ValueAnimator.AnimatorUpdateListener {
    private final /* synthetic */ ScrimDrawable f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$ScrimDrawable$UWtyAZ9Ss5P5TukFNvAyvh0pNf0(ScrimDrawable scrimDrawable, int n, int n2) {
        this.f$0 = scrimDrawable;
        this.f$1 = n;
        this.f$2 = n2;
    }

    @Override
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f$0.lambda$setColor$0$ScrimDrawable(this.f$1, this.f$2, valueAnimator);
    }
}

