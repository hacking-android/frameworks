/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package com.android.internal.graphics.drawable;

import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.util.AttributeSet;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimationScaleListDrawable
extends DrawableContainer
implements Animatable {
    private static final String TAG = "AnimationScaleListDrawable";
    private AnimationScaleListState mAnimationScaleListState;
    private boolean mMutated;

    public AnimationScaleListDrawable() {
        this(null, null);
    }

    private AnimationScaleListDrawable(AnimationScaleListState animationScaleListState, Resources resources) {
        this.setConstantState(new AnimationScaleListState(animationScaleListState, this, resources));
        this.onStateChange(this.getState());
    }

    private void inflateChildElements(Resources object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int n;
        int n2;
        AnimationScaleListState animationScaleListState = this.mAnimationScaleListState;
        int n3 = xmlPullParser.getDepth() + 1;
        while ((n2 = xmlPullParser.next()) != 1 && ((n = xmlPullParser.getDepth()) >= n3 || n2 != 3)) {
            if (n2 != 2 || n > n3 || !xmlPullParser.getName().equals("item")) continue;
            Object object2 = AnimationScaleListDrawable.obtainAttributes((Resources)object, theme, attributeSet, R.styleable.AnimationScaleListDrawableItem);
            Drawable drawable2 = ((TypedArray)object2).getDrawable(0);
            ((TypedArray)object2).recycle();
            object2 = drawable2;
            if (drawable2 == null) {
                while ((n = xmlPullParser.next()) == 4) {
                }
                if (n == 2) {
                    object2 = Drawable.createFromXmlInner((Resources)object, xmlPullParser, attributeSet, theme);
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
                    ((StringBuilder)object).append(": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
                    throw new XmlPullParserException(((StringBuilder)object).toString());
                }
            }
            animationScaleListState.addDrawable((Drawable)object2);
        }
    }

    @Override
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        this.onStateChange(this.getState());
    }

    @Override
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    @Override
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArray = AnimationScaleListDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.AnimationScaleListDrawable);
        this.updateDensity(resources);
        typedArray.recycle();
        this.inflateChildElements(resources, xmlPullParser, attributeSet, theme);
        this.onStateChange(this.getState());
    }

    @Override
    public boolean isRunning() {
        boolean bl = false;
        Drawable drawable2 = this.getCurrent();
        boolean bl2 = bl;
        if (drawable2 != null) {
            bl2 = bl;
            if (drawable2 instanceof Animatable) {
                bl2 = ((Animatable)((Object)drawable2)).isRunning();
            }
        }
        return bl2;
    }

    @Override
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mAnimationScaleListState.mutate();
            this.mMutated = true;
        }
        return this;
    }

    @Override
    protected boolean onStateChange(int[] arrn) {
        boolean bl = super.onStateChange(arrn);
        bl = this.selectDrawable(this.mAnimationScaleListState.getCurrentDrawableIndexBasedOnScale()) || bl;
        return bl;
    }

    @Override
    protected void setConstantState(DrawableContainer.DrawableContainerState drawableContainerState) {
        super.setConstantState(drawableContainerState);
        if (drawableContainerState instanceof AnimationScaleListState) {
            this.mAnimationScaleListState = (AnimationScaleListState)drawableContainerState;
        }
    }

    @Override
    public void start() {
        Drawable drawable2 = this.getCurrent();
        if (drawable2 != null && drawable2 instanceof Animatable) {
            ((Animatable)((Object)drawable2)).start();
        }
    }

    @Override
    public void stop() {
        Drawable drawable2 = this.getCurrent();
        if (drawable2 != null && drawable2 instanceof Animatable) {
            ((Animatable)((Object)drawable2)).stop();
        }
    }

    static class AnimationScaleListState
    extends DrawableContainer.DrawableContainerState {
        int mAnimatableDrawableIndex = -1;
        int mStaticDrawableIndex = -1;
        int[] mThemeAttrs = null;

        AnimationScaleListState(AnimationScaleListState animationScaleListState, AnimationScaleListDrawable animationScaleListDrawable, Resources resources) {
            super(animationScaleListState, animationScaleListDrawable, resources);
            if (animationScaleListState != null) {
                this.mThemeAttrs = animationScaleListState.mThemeAttrs;
                this.mStaticDrawableIndex = animationScaleListState.mStaticDrawableIndex;
                this.mAnimatableDrawableIndex = animationScaleListState.mAnimatableDrawableIndex;
            }
        }

        int addDrawable(Drawable drawable2) {
            int n = this.addChild(drawable2);
            if (drawable2 instanceof Animatable) {
                this.mAnimatableDrawableIndex = n;
            } else {
                this.mStaticDrawableIndex = n;
            }
            return n;
        }

        @Override
        public boolean canApplyTheme() {
            boolean bl = this.mThemeAttrs != null || super.canApplyTheme();
            return bl;
        }

        public int getCurrentDrawableIndexBasedOnScale() {
            if (ValueAnimator.getDurationScale() == 0.0f) {
                return this.mStaticDrawableIndex;
            }
            return this.mAnimatableDrawableIndex;
        }

        void mutate() {
            Object object = this.mThemeAttrs;
            object = object != null ? (int[])object.clone() : null;
            this.mThemeAttrs = object;
        }

        @Override
        public Drawable newDrawable() {
            return new AnimationScaleListDrawable(this, null);
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new AnimationScaleListDrawable(this, resources);
        }
    }

}

