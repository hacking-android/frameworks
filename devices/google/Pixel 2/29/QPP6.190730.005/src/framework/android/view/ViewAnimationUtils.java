/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.animation.Animator;
import android.animation.RevealAnimator;
import android.view.View;

public final class ViewAnimationUtils {
    private ViewAnimationUtils() {
    }

    public static Animator createCircularReveal(View view, int n, int n2, float f, float f2) {
        return new RevealAnimator(view, n, n2, f, f2);
    }
}

