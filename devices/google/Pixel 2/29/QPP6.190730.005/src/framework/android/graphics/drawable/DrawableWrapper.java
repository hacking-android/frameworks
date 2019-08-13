/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.graphics.drawable;

import android.annotation.UnsupportedAppUsage;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Insets;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public abstract class DrawableWrapper
extends Drawable
implements Drawable.Callback {
    private Drawable mDrawable;
    private boolean mMutated;
    @UnsupportedAppUsage
    private DrawableWrapperState mState;

    public DrawableWrapper(Drawable drawable2) {
        this.mState = null;
        this.setDrawable(drawable2);
    }

    DrawableWrapper(DrawableWrapperState drawableWrapperState, Resources resources) {
        this.mState = drawableWrapperState;
        this.updateLocalState(resources);
    }

    private void inflateChildDrawable(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int n;
        Drawable drawable2 = null;
        int n2 = xmlPullParser.getDepth();
        while ((n = xmlPullParser.next()) != 1 && (n != 3 || xmlPullParser.getDepth() > n2)) {
            if (n != 2) continue;
            drawable2 = Drawable.createFromXmlInnerForDensity(resources, xmlPullParser, attributeSet, this.mState.mSrcDensityOverride, theme);
        }
        if (drawable2 != null) {
            this.setDrawable(drawable2);
        }
    }

    private void updateLocalState(Resources resources) {
        DrawableWrapperState drawableWrapperState = this.mState;
        if (drawableWrapperState != null && drawableWrapperState.mDrawableState != null) {
            this.setDrawable(this.mState.mDrawableState.newDrawable(resources));
        }
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        DrawableWrapperState drawableWrapperState = this.mState;
        if (drawableWrapperState == null) {
            return;
        }
        drawableWrapperState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        drawableWrapperState.mThemeAttrs = typedArray.extractThemeAttrs();
        if (typedArray.hasValueOrEmpty(0)) {
            this.setDrawable(typedArray.getDrawable(0));
        }
    }

    @Override
    public void applyTheme(Resources.Theme object) {
        super.applyTheme((Resources.Theme)object);
        Object object2 = this.mDrawable;
        if (object2 != null && ((Drawable)object2).canApplyTheme()) {
            this.mDrawable.applyTheme((Resources.Theme)object);
        }
        if ((object2 = this.mState) == null) {
            return;
        }
        int n = object.getResources().getDisplayMetrics().densityDpi;
        if (n == 0) {
            n = 160;
        }
        ((DrawableWrapperState)object2).setDensity(n);
        if (((DrawableWrapperState)object2).mThemeAttrs != null) {
            object = ((Resources.Theme)object).resolveAttributes(((DrawableWrapperState)object2).mThemeAttrs, R.styleable.DrawableWrapper);
            this.updateStateFromTypedArray((TypedArray)object);
            ((TypedArray)object).recycle();
        }
    }

    @Override
    public boolean canApplyTheme() {
        DrawableWrapperState drawableWrapperState = this.mState;
        boolean bl = drawableWrapperState != null && drawableWrapperState.canApplyTheme() || super.canApplyTheme();
        return bl;
    }

    @Override
    public void clearMutated() {
        super.clearMutated();
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null) {
            drawable2.clearMutated();
        }
        this.mMutated = false;
    }

    @Override
    public void draw(Canvas canvas) {
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
    }

    @Override
    public int getAlpha() {
        Drawable drawable2 = this.mDrawable;
        int n = drawable2 != null ? drawable2.getAlpha() : 255;
        return n;
    }

    @Override
    public int getChangingConfigurations() {
        int n = super.getChangingConfigurations();
        DrawableWrapperState drawableWrapperState = this.mState;
        int n2 = drawableWrapperState != null ? drawableWrapperState.getChangingConfigurations() : 0;
        return n | n2 | this.mDrawable.getChangingConfigurations();
    }

    @Override
    public ColorFilter getColorFilter() {
        Drawable drawable2 = this.getDrawable();
        if (drawable2 != null) {
            return drawable2.getColorFilter();
        }
        return super.getColorFilter();
    }

    @Override
    public Drawable.ConstantState getConstantState() {
        DrawableWrapperState drawableWrapperState = this.mState;
        if (drawableWrapperState != null && drawableWrapperState.canConstantState()) {
            this.mState.mChangingConfigurations = this.getChangingConfigurations();
            return this.mState;
        }
        return null;
    }

    public Drawable getDrawable() {
        return this.mDrawable;
    }

    @Override
    public void getHotspotBounds(Rect rect) {
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null) {
            drawable2.getHotspotBounds(rect);
        } else {
            rect.set(this.getBounds());
        }
    }

    @Override
    public int getIntrinsicHeight() {
        Drawable drawable2 = this.mDrawable;
        int n = drawable2 != null ? drawable2.getIntrinsicHeight() : -1;
        return n;
    }

    @Override
    public int getIntrinsicWidth() {
        Drawable drawable2 = this.mDrawable;
        int n = drawable2 != null ? drawable2.getIntrinsicWidth() : -1;
        return n;
    }

    @Override
    public int getOpacity() {
        Drawable drawable2 = this.mDrawable;
        int n = drawable2 != null ? drawable2.getOpacity() : -2;
        return n;
    }

    @Override
    public Insets getOpticalInsets() {
        Object object = this.mDrawable;
        object = object != null ? ((Drawable)object).getOpticalInsets() : Insets.NONE;
        return object;
    }

    @Override
    public void getOutline(Outline outline) {
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null) {
            drawable2.getOutline(outline);
        } else {
            super.getOutline(outline);
        }
    }

    @Override
    public boolean getPadding(Rect rect) {
        Drawable drawable2 = this.mDrawable;
        boolean bl = drawable2 != null && drawable2.getPadding(rect);
        return bl;
    }

    @Override
    public boolean hasFocusStateSpecified() {
        Drawable drawable2 = this.mDrawable;
        boolean bl = drawable2 != null && drawable2.hasFocusStateSpecified();
        return bl;
    }

    @Override
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        Object object = this.mState;
        if (object == null) {
            return;
        }
        int n = resources.getDisplayMetrics().densityDpi;
        if (n == 0) {
            n = 160;
        }
        ((DrawableWrapperState)object).setDensity(n);
        ((DrawableWrapperState)object).mSrcDensityOverride = this.mSrcDensityOverride;
        object = DrawableWrapper.obtainAttributes(resources, theme, attributeSet, R.styleable.DrawableWrapper);
        this.updateStateFromTypedArray((TypedArray)object);
        ((TypedArray)object).recycle();
        this.inflateChildDrawable(resources, xmlPullParser, attributeSet, theme);
    }

    @Override
    public void invalidateDrawable(Drawable object) {
        object = this.getCallback();
        if (object != null) {
            object.invalidateDrawable(this);
        }
    }

    @Override
    public boolean isStateful() {
        Drawable drawable2 = this.mDrawable;
        boolean bl = drawable2 != null && drawable2.isStateful();
        return bl;
    }

    @Override
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            DrawableWrapperState drawableWrapperState;
            this.mState = this.mutateConstantState();
            Object object = this.mDrawable;
            if (object != null) {
                ((Drawable)object).mutate();
            }
            if ((drawableWrapperState = this.mState) != null) {
                object = this.mDrawable;
                object = object != null ? ((Drawable)object).getConstantState() : null;
                drawableWrapperState.mDrawableState = object;
            }
            this.mMutated = true;
        }
        return this;
    }

    DrawableWrapperState mutateConstantState() {
        return this.mState;
    }

    @Override
    protected void onBoundsChange(Rect rect) {
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null) {
            drawable2.setBounds(rect);
        }
    }

    @Override
    public boolean onLayoutDirectionChanged(int n) {
        Drawable drawable2 = this.mDrawable;
        boolean bl = drawable2 != null && drawable2.setLayoutDirection(n);
        return bl;
    }

    @Override
    protected boolean onLevelChange(int n) {
        Drawable drawable2 = this.mDrawable;
        boolean bl = drawable2 != null && drawable2.setLevel(n);
        return bl;
    }

    @Override
    protected boolean onStateChange(int[] arrn) {
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null && drawable2.isStateful()) {
            boolean bl = this.mDrawable.setState(arrn);
            if (bl) {
                this.onBoundsChange(this.getBounds());
            }
            return bl;
        }
        return false;
    }

    @Override
    public void scheduleDrawable(Drawable object, Runnable runnable, long l) {
        object = this.getCallback();
        if (object != null) {
            object.scheduleDrawable(this, runnable, l);
        }
    }

    @Override
    public void setAlpha(int n) {
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null) {
            drawable2.setAlpha(n);
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null) {
            drawable2.setColorFilter(colorFilter);
        }
    }

    public void setDrawable(Drawable drawable2) {
        Object object = this.mDrawable;
        if (object != null) {
            ((Drawable)object).setCallback(null);
        }
        this.mDrawable = drawable2;
        if (drawable2 != null) {
            drawable2.setCallback(this);
            drawable2.setVisible(this.isVisible(), true);
            drawable2.setState(this.getState());
            drawable2.setLevel(this.getLevel());
            drawable2.setBounds(this.getBounds());
            drawable2.setLayoutDirection(this.getLayoutDirection());
            object = this.mState;
            if (object != null) {
                ((DrawableWrapperState)object).mDrawableState = drawable2.getConstantState();
            }
        }
        this.invalidateSelf();
    }

    @Override
    public void setHotspot(float f, float f2) {
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null) {
            drawable2.setHotspot(f, f2);
        }
    }

    @Override
    public void setHotspotBounds(int n, int n2, int n3, int n4) {
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null) {
            drawable2.setHotspotBounds(n, n2, n3, n4);
        }
    }

    @Override
    public void setTintBlendMode(BlendMode blendMode) {
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null) {
            drawable2.setTintBlendMode(blendMode);
        }
    }

    @Override
    public void setTintList(ColorStateList colorStateList) {
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null) {
            drawable2.setTintList(colorStateList);
        }
    }

    @Override
    public boolean setVisible(boolean bl, boolean bl2) {
        boolean bl3 = super.setVisible(bl, bl2);
        Drawable drawable2 = this.mDrawable;
        boolean bl4 = drawable2 != null && drawable2.setVisible(bl, bl2);
        return bl3 | bl4;
    }

    @Override
    public void setXfermode(Xfermode xfermode) {
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null) {
            drawable2.setXfermode(xfermode);
        }
    }

    @Override
    public void unscheduleDrawable(Drawable object, Runnable runnable) {
        object = this.getCallback();
        if (object != null) {
            object.unscheduleDrawable(this, runnable);
        }
    }

    static abstract class DrawableWrapperState
    extends Drawable.ConstantState {
        int mChangingConfigurations;
        int mDensity;
        Drawable.ConstantState mDrawableState;
        int mSrcDensityOverride;
        private int[] mThemeAttrs;

        DrawableWrapperState(DrawableWrapperState drawableWrapperState, Resources resources) {
            int n;
            int n2 = 160;
            this.mDensity = 160;
            this.mSrcDensityOverride = 0;
            if (drawableWrapperState != null) {
                this.mThemeAttrs = drawableWrapperState.mThemeAttrs;
                this.mChangingConfigurations = drawableWrapperState.mChangingConfigurations;
                this.mDrawableState = drawableWrapperState.mDrawableState;
                this.mSrcDensityOverride = drawableWrapperState.mSrcDensityOverride;
            }
            if ((n = resources != null ? resources.getDisplayMetrics().densityDpi : (drawableWrapperState != null ? drawableWrapperState.mDensity : 0)) == 0) {
                n = n2;
            }
            this.mDensity = n;
        }

        @Override
        public boolean canApplyTheme() {
            Drawable.ConstantState constantState;
            boolean bl = this.mThemeAttrs != null || (constantState = this.mDrawableState) != null && constantState.canApplyTheme() || super.canApplyTheme();
            return bl;
        }

        public boolean canConstantState() {
            boolean bl = this.mDrawableState != null;
            return bl;
        }

        @Override
        public int getChangingConfigurations() {
            int n = this.mChangingConfigurations;
            Drawable.ConstantState constantState = this.mDrawableState;
            int n2 = constantState != null ? constantState.getChangingConfigurations() : 0;
            return n | n2;
        }

        @Override
        public Drawable newDrawable() {
            return this.newDrawable(null);
        }

        @Override
        public abstract Drawable newDrawable(Resources var1);

        void onDensityChanged(int n, int n2) {
        }

        public final void setDensity(int n) {
            if (this.mDensity != n) {
                int n2 = this.mDensity;
                this.mDensity = n;
                this.onDensityChanged(n2, n);
            }
        }
    }

}

