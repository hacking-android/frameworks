/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Insets;
import android.view.WindowInsets;

public interface WindowInsetsAnimationListener {
    public void onFinished(InsetsAnimation var1);

    public WindowInsets onProgress(WindowInsets var1);

    public void onStarted(InsetsAnimation var1);

    public static class InsetsAnimation {
        private final Insets mLowerBound;
        private final int mTypeMask;
        private final Insets mUpperBound;

        InsetsAnimation(int n, Insets insets, Insets insets2) {
            this.mTypeMask = n;
            this.mLowerBound = insets;
            this.mUpperBound = insets2;
        }

        public Insets getLowerBound() {
            return this.mLowerBound;
        }

        public int getTypeMask() {
            return this.mTypeMask;
        }

        public Insets getUpperBound() {
            return this.mUpperBound;
        }
    }

}

