/*
 * Decompiled with CFR 0.145.
 */
package android.graphics.drawable;

import android.graphics.drawable.AnimatedVectorDrawable;

public final class _$$Lambda$AnimatedVectorDrawable$VectorDrawableAnimatorRT$PzjgSeyQweoFjbEZJP80UteZqm8
implements Runnable {
    private final /* synthetic */ AnimatedVectorDrawable.VectorDrawableAnimatorRT f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$AnimatedVectorDrawable$VectorDrawableAnimatorRT$PzjgSeyQweoFjbEZJP80UteZqm8(AnimatedVectorDrawable.VectorDrawableAnimatorRT vectorDrawableAnimatorRT, int n) {
        this.f$0 = vectorDrawableAnimatorRT;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        AnimatedVectorDrawable.VectorDrawableAnimatorRT.lambda$callOnFinished$0(this.f$0, this.f$1);
    }
}

