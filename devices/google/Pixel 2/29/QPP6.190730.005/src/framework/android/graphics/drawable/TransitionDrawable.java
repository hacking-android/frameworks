/*
 * Decompiled with CFR 0.145.
 */
package android.graphics.drawable;

import android.annotation.UnsupportedAppUsage;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.SystemClock;

public class TransitionDrawable
extends LayerDrawable
implements Drawable.Callback {
    private static final int TRANSITION_NONE = 2;
    private static final int TRANSITION_RUNNING = 1;
    private static final int TRANSITION_STARTING = 0;
    @UnsupportedAppUsage
    private int mAlpha = 0;
    @UnsupportedAppUsage
    private boolean mCrossFade;
    private int mDuration;
    private int mFrom;
    private int mOriginalDuration;
    private boolean mReverse;
    private long mStartTimeMillis;
    @UnsupportedAppUsage
    private int mTo;
    private int mTransitionState = 2;

    TransitionDrawable() {
        this(new TransitionState(null, null, null), (Resources)null);
    }

    private TransitionDrawable(TransitionState transitionState, Resources resources) {
        super(transitionState, resources);
    }

    private TransitionDrawable(TransitionState transitionState, Drawable[] arrdrawable) {
        super(arrdrawable, transitionState);
    }

    public TransitionDrawable(Drawable[] arrdrawable) {
        this(new TransitionState(null, null, null), arrdrawable);
    }

    @Override
    LayerDrawable.LayerState createConstantState(LayerDrawable.LayerState layerState, Resources resources) {
        return new TransitionState((TransitionState)layerState, this, resources);
    }

    @Override
    public void draw(Canvas canvas) {
        boolean bl = true;
        int n = this.mTransitionState;
        if (n != 0) {
            if (n == 1 && this.mStartTimeMillis >= 0L) {
                float f = (float)(SystemClock.uptimeMillis() - this.mStartTimeMillis) / (float)this.mDuration;
                bl = f >= 1.0f;
                f = Math.min(f, 1.0f);
                n = this.mFrom;
                this.mAlpha = (int)((float)n + (float)(this.mTo - n) * f);
            }
        } else {
            this.mStartTimeMillis = SystemClock.uptimeMillis();
            bl = false;
            this.mTransitionState = 1;
        }
        n = this.mAlpha;
        boolean bl2 = this.mCrossFade;
        Object object = this.mLayerState.mChildren;
        if (bl) {
            if (!bl2 || n == 0) {
                object[0].mDrawable.draw(canvas);
            }
            if (n == 255) {
                object[1].mDrawable.draw(canvas);
            }
            return;
        }
        Drawable drawable2 = object[0].mDrawable;
        if (bl2) {
            drawable2.setAlpha(255 - n);
        }
        drawable2.draw(canvas);
        if (bl2) {
            drawable2.setAlpha(255);
        }
        if (n > 0) {
            object = object[1].mDrawable;
            ((Drawable)object).setAlpha(n);
            ((Drawable)object).draw(canvas);
            ((Drawable)object).setAlpha(255);
        }
        if (!bl) {
            this.invalidateSelf();
        }
    }

    public boolean isCrossFadeEnabled() {
        return this.mCrossFade;
    }

    public void resetTransition() {
        this.mAlpha = 0;
        this.mTransitionState = 2;
        this.invalidateSelf();
    }

    public void reverseTransition(int n) {
        long l = SystemClock.uptimeMillis();
        long l2 = this.mStartTimeMillis;
        long l3 = this.mDuration;
        int n2 = 255;
        if (l - l2 > l3) {
            if (this.mTo == 0) {
                this.mFrom = 0;
                this.mTo = 255;
                this.mAlpha = 0;
                this.mReverse = false;
            } else {
                this.mFrom = 255;
                this.mTo = 0;
                this.mAlpha = 255;
                this.mReverse = true;
            }
            this.mOriginalDuration = n;
            this.mDuration = n;
            this.mTransitionState = 0;
            this.invalidateSelf();
            return;
        }
        this.mReverse ^= true;
        this.mFrom = this.mAlpha;
        n = n2;
        if (this.mReverse) {
            n = 0;
        }
        this.mTo = n;
        l2 = this.mReverse ? l - this.mStartTimeMillis : (long)this.mOriginalDuration - (l - this.mStartTimeMillis);
        this.mDuration = (int)l2;
        this.mTransitionState = 0;
    }

    public void setCrossFadeEnabled(boolean bl) {
        this.mCrossFade = bl;
    }

    public void showSecondLayer() {
        this.mAlpha = 255;
        this.mReverse = false;
        this.mTransitionState = 2;
        this.invalidateSelf();
    }

    public void startTransition(int n) {
        this.mFrom = 0;
        this.mTo = 255;
        this.mAlpha = 0;
        this.mOriginalDuration = n;
        this.mDuration = n;
        this.mReverse = false;
        this.mTransitionState = 0;
        this.invalidateSelf();
    }

    static class TransitionState
    extends LayerDrawable.LayerState {
        TransitionState(TransitionState transitionState, TransitionDrawable transitionDrawable, Resources resources) {
            super(transitionState, transitionDrawable, resources);
        }

        @Override
        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }

        @Override
        public Drawable newDrawable() {
            return new TransitionDrawable(this, null);
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new TransitionDrawable(this, resources);
        }
    }

}

