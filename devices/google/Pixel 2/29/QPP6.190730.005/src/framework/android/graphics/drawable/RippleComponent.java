/*
 * Decompiled with CFR 0.145.
 */
package android.graphics.drawable;

import android.graphics.Rect;
import android.graphics.drawable.RippleDrawable;

abstract class RippleComponent {
    protected final Rect mBounds;
    protected float mDensityScale;
    private boolean mHasMaxRadius;
    protected final RippleDrawable mOwner;
    protected float mTargetRadius;

    public RippleComponent(RippleDrawable rippleDrawable, Rect rect) {
        this.mOwner = rippleDrawable;
        this.mBounds = rect;
    }

    private static float getTargetRadius(Rect rect) {
        float f = (float)rect.width() / 2.0f;
        float f2 = (float)rect.height() / 2.0f;
        return (float)Math.sqrt(f * f + f2 * f2);
    }

    public void getBounds(Rect rect) {
        int n = (int)Math.ceil(this.mTargetRadius);
        rect.set(-n, -n, n, n);
    }

    protected final void invalidateSelf() {
        this.mOwner.invalidateSelf(false);
    }

    public void onBoundsChange() {
        if (!this.mHasMaxRadius) {
            this.mTargetRadius = RippleComponent.getTargetRadius(this.mBounds);
            this.onTargetRadiusChanged(this.mTargetRadius);
        }
    }

    protected final void onHotspotBoundsChanged() {
        if (!this.mHasMaxRadius) {
            this.mTargetRadius = RippleComponent.getTargetRadius(this.mBounds);
            this.onTargetRadiusChanged(this.mTargetRadius);
        }
    }

    protected void onTargetRadiusChanged(float f) {
    }

    public final void setup(float f, int n) {
        if (f >= 0.0f) {
            this.mHasMaxRadius = true;
            this.mTargetRadius = f;
        } else {
            this.mTargetRadius = RippleComponent.getTargetRadius(this.mBounds);
        }
        this.mDensityScale = (float)n * 0.00625f;
        this.onTargetRadiusChanged(this.mTargetRadius);
    }
}

