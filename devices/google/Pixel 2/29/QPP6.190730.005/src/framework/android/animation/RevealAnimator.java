/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

import android.view.RenderNodeAnimator;
import android.view.View;

public class RevealAnimator
extends RenderNodeAnimator {
    private View mClipView;

    public RevealAnimator(View view, int n, int n2, float f, float f2) {
        super(n, n2, f, f2);
        this.mClipView = view;
        this.setTarget(this.mClipView);
    }

    @Override
    protected void onFinished() {
        this.mClipView.setRevealClip(false, 0.0f, 0.0f, 0.0f);
        super.onFinished();
    }
}

