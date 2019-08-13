/*
 * Decompiled with CFR 0.145.
 */
package android.graphics.drawable;

import android.graphics.drawable.AnimatedImageDrawable;

public final class _$$Lambda$AlQeVq8Y_kfuQeb_JLZ0ueV4DE8
implements Runnable {
    private final /* synthetic */ AnimatedImageDrawable f$0;

    public /* synthetic */ _$$Lambda$AlQeVq8Y_kfuQeb_JLZ0ueV4DE8(AnimatedImageDrawable animatedImageDrawable) {
        this.f$0 = animatedImageDrawable;
    }

    @Override
    public final void run() {
        this.f$0.invalidateSelf();
    }
}

