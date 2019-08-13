/*
 * Decompiled with CFR 0.145.
 */
package android.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.MathUtils;

public class ColorStateListDrawable
extends Drawable
implements Drawable.Callback {
    private ColorDrawable mColorDrawable;
    private boolean mMutated = false;
    private ColorStateListDrawableState mState;

    public ColorStateListDrawable() {
        this.mState = new ColorStateListDrawableState();
        this.initializeColorDrawable();
    }

    public ColorStateListDrawable(ColorStateList colorStateList) {
        this.mState = new ColorStateListDrawableState();
        this.initializeColorDrawable();
        this.setColorStateList(colorStateList);
    }

    private ColorStateListDrawable(ColorStateListDrawableState colorStateListDrawableState) {
        this.mState = colorStateListDrawableState;
        this.initializeColorDrawable();
    }

    private void initializeColorDrawable() {
        this.mColorDrawable = new ColorDrawable();
        this.mColorDrawable.setCallback(this);
        if (this.mState.mTint != null) {
            this.mColorDrawable.setTintList(this.mState.mTint);
        }
        if (this.mState.mBlendMode != DEFAULT_BLEND_MODE) {
            this.mColorDrawable.setTintBlendMode(this.mState.mBlendMode);
        }
    }

    @Override
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        if (this.mState.mColor != null) {
            this.setColorStateList(this.mState.mColor.obtainForTheme(theme));
        }
        if (this.mState.mTint != null) {
            this.setTintList(this.mState.mTint.obtainForTheme(theme));
        }
    }

    @Override
    public boolean canApplyTheme() {
        boolean bl = super.canApplyTheme() || this.mState.canApplyTheme();
        return bl;
    }

    public void clearAlpha() {
        this.mState.mAlpha = -1;
        this.onStateChange(this.getState());
    }

    @Override
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    @Override
    public void draw(Canvas canvas) {
        this.mColorDrawable.draw(canvas);
    }

    @Override
    public int getAlpha() {
        return this.mColorDrawable.getAlpha();
    }

    @Override
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mState.getChangingConfigurations();
    }

    @Override
    public ColorFilter getColorFilter() {
        return this.mColorDrawable.getColorFilter();
    }

    public ColorStateList getColorStateList() {
        if (this.mState.mColor == null) {
            return ColorStateList.valueOf(this.mColorDrawable.getColor());
        }
        return this.mState.mColor;
    }

    @Override
    public Drawable.ConstantState getConstantState() {
        ColorStateListDrawableState colorStateListDrawableState = this.mState;
        colorStateListDrawableState.mChangingConfigurations |= this.getChangingConfigurations() & this.mState.getChangingConfigurations();
        return this.mState;
    }

    @Override
    public Drawable getCurrent() {
        return this.mColorDrawable;
    }

    @Override
    public int getOpacity() {
        return this.mColorDrawable.getOpacity();
    }

    @Override
    public boolean hasFocusStateSpecified() {
        return this.mState.hasFocusStateSpecified();
    }

    @Override
    public void invalidateDrawable(Drawable drawable2) {
        if (drawable2 == this.mColorDrawable && this.getCallback() != null) {
            this.getCallback().invalidateDrawable(this);
        }
    }

    @Override
    public boolean isStateful() {
        return this.mState.isStateful();
    }

    @Override
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mState = new ColorStateListDrawableState(this.mState);
            this.mMutated = true;
        }
        return this;
    }

    @Override
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mColorDrawable.setBounds(rect);
    }

    @Override
    protected boolean onStateChange(int[] arrn) {
        if (this.mState.mColor != null) {
            int n;
            int n2 = n = this.mState.mColor.getColorForState(arrn, this.mState.mColor.getDefaultColor());
            if (this.mState.mAlpha != -1) {
                n2 = 16777215 & n | MathUtils.constrain(this.mState.mAlpha, 0, 255) << 24;
            }
            if (n2 != this.mColorDrawable.getColor()) {
                this.mColorDrawable.setColor(n2);
                this.mColorDrawable.setState(arrn);
                return true;
            }
            return this.mColorDrawable.setState(arrn);
        }
        return false;
    }

    @Override
    public void scheduleDrawable(Drawable drawable2, Runnable runnable, long l) {
        if (drawable2 == this.mColorDrawable && this.getCallback() != null) {
            this.getCallback().scheduleDrawable(this, runnable, l);
        }
    }

    @Override
    public void setAlpha(int n) {
        this.mState.mAlpha = n;
        this.onStateChange(this.getState());
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        this.mColorDrawable.setColorFilter(colorFilter);
    }

    public void setColorStateList(ColorStateList colorStateList) {
        this.mState.mColor = colorStateList;
        this.onStateChange(this.getState());
    }

    @Override
    public void setTintBlendMode(BlendMode blendMode) {
        this.mState.mBlendMode = blendMode;
        this.mColorDrawable.setTintBlendMode(blendMode);
        this.onStateChange(this.getState());
    }

    @Override
    public void setTintList(ColorStateList colorStateList) {
        this.mState.mTint = colorStateList;
        this.mColorDrawable.setTintList(colorStateList);
        this.onStateChange(this.getState());
    }

    @Override
    public void unscheduleDrawable(Drawable drawable2, Runnable runnable) {
        if (drawable2 == this.mColorDrawable && this.getCallback() != null) {
            this.getCallback().unscheduleDrawable(this, runnable);
        }
    }

    static final class ColorStateListDrawableState
    extends Drawable.ConstantState {
        int mAlpha = -1;
        BlendMode mBlendMode = Drawable.DEFAULT_BLEND_MODE;
        int mChangingConfigurations = 0;
        ColorStateList mColor = null;
        ColorStateList mTint = null;

        ColorStateListDrawableState() {
        }

        ColorStateListDrawableState(ColorStateListDrawableState colorStateListDrawableState) {
            this.mColor = colorStateListDrawableState.mColor;
            this.mTint = colorStateListDrawableState.mTint;
            this.mAlpha = colorStateListDrawableState.mAlpha;
            this.mBlendMode = colorStateListDrawableState.mBlendMode;
            this.mChangingConfigurations = colorStateListDrawableState.mChangingConfigurations;
        }

        @Override
        public boolean canApplyTheme() {
            ColorStateList colorStateList = this.mColor;
            boolean bl = colorStateList != null && colorStateList.canApplyTheme() || (colorStateList = this.mTint) != null && colorStateList.canApplyTheme();
            return bl;
        }

        @Override
        public int getChangingConfigurations() {
            int n = this.mChangingConfigurations;
            ColorStateList colorStateList = this.mColor;
            int n2 = 0;
            int n3 = colorStateList != null ? colorStateList.getChangingConfigurations() : 0;
            colorStateList = this.mTint;
            if (colorStateList != null) {
                n2 = colorStateList.getChangingConfigurations();
            }
            return n | n3 | n2;
        }

        public boolean hasFocusStateSpecified() {
            ColorStateList colorStateList = this.mColor;
            boolean bl = colorStateList != null && colorStateList.hasFocusStateSpecified() || (colorStateList = this.mTint) != null && colorStateList.hasFocusStateSpecified();
            return bl;
        }

        public boolean isStateful() {
            ColorStateList colorStateList = this.mColor;
            boolean bl = colorStateList != null && colorStateList.isStateful() || (colorStateList = this.mTint) != null && colorStateList.isStateful();
            return bl;
        }

        @Override
        public Drawable newDrawable() {
            return new ColorStateListDrawable(this);
        }
    }

}

