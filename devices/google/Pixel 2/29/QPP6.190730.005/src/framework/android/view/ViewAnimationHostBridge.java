/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.RenderNode;
import android.view.NativeVectorDrawableAnimator;
import android.view.View;
import android.view.ViewRootImpl;

public class ViewAnimationHostBridge
implements RenderNode.AnimationHost {
    private final View mView;

    public ViewAnimationHostBridge(View view) {
        this.mView = view;
    }

    @Override
    public boolean isAttached() {
        boolean bl = this.mView.mAttachInfo != null;
        return bl;
    }

    @Override
    public void registerAnimatingRenderNode(RenderNode renderNode) {
        this.mView.mAttachInfo.mViewRootImpl.registerAnimatingRenderNode(renderNode);
    }

    @Override
    public void registerVectorDrawableAnimator(NativeVectorDrawableAnimator nativeVectorDrawableAnimator) {
        this.mView.mAttachInfo.mViewRootImpl.registerVectorDrawableAnimator(nativeVectorDrawableAnimator);
    }
}

