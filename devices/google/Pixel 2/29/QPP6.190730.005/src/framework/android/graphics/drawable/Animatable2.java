/*
 * Decompiled with CFR 0.145.
 */
package android.graphics.drawable;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

public interface Animatable2
extends Animatable {
    public void clearAnimationCallbacks();

    public void registerAnimationCallback(AnimationCallback var1);

    public boolean unregisterAnimationCallback(AnimationCallback var1);

    public static abstract class AnimationCallback {
        public void onAnimationEnd(Drawable drawable2) {
        }

        public void onAnimationStart(Drawable drawable2) {
        }
    }

}

