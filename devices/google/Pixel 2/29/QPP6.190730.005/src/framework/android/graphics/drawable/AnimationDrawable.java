/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.graphics.drawable;

import android.annotation.UnsupportedAppUsage;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.SystemClock;
import android.util.AttributeSet;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimationDrawable
extends DrawableContainer
implements Runnable,
Animatable {
    private boolean mAnimating;
    private AnimationState mAnimationState;
    @UnsupportedAppUsage
    private int mCurFrame = 0;
    private boolean mMutated;
    private boolean mRunning;

    public AnimationDrawable() {
        this(null, null);
    }

    private AnimationDrawable(AnimationState animationState, Resources resources) {
        this.setConstantState(new AnimationState(animationState, this, resources));
        if (animationState != null) {
            this.setFrame(0, true, false);
        }
    }

    private void inflateChildElements(Resources object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int n;
        int n2;
        int n3 = xmlPullParser.getDepth() + 1;
        while ((n2 = xmlPullParser.next()) != 1 && ((n = xmlPullParser.getDepth()) >= n3 || n2 != 3)) {
            if (n2 != 2 || n > n3 || !xmlPullParser.getName().equals("item")) continue;
            Object object2 = AnimationDrawable.obtainAttributes((Resources)object, theme, attributeSet, R.styleable.AnimationDrawableItem);
            n = ((TypedArray)object2).getInt(0, -1);
            if (n >= 0) {
                Drawable drawable2 = ((TypedArray)object2).getDrawable(1);
                ((TypedArray)object2).recycle();
                object2 = drawable2;
                if (drawable2 == null) {
                    while ((n2 = xmlPullParser.next()) == 4) {
                    }
                    if (n2 == 2) {
                        object2 = Drawable.createFromXmlInner((Resources)object, xmlPullParser, attributeSet, theme);
                    } else {
                        object = new StringBuilder();
                        ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
                        ((StringBuilder)object).append(": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
                        throw new XmlPullParserException(((StringBuilder)object).toString());
                    }
                }
                this.mAnimationState.addFrame((Drawable)object2, n);
                if (object2 == null) continue;
                ((Drawable)object2).setCallback(this);
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
            ((StringBuilder)object).append(": <item> tag requires a 'duration' attribute");
            throw new XmlPullParserException(((StringBuilder)object).toString());
        }
    }

    private void nextFrame(boolean bl) {
        int n = this.mCurFrame;
        boolean bl2 = true;
        int n2 = n + 1;
        int n3 = this.mAnimationState.getChildCount();
        n = this.mAnimationState.mOneShot && n2 >= n3 - 1 ? 1 : 0;
        int n4 = n2;
        if (!this.mAnimationState.mOneShot) {
            n4 = n2;
            if (n2 >= n3) {
                n4 = 0;
            }
        }
        if (n != 0) {
            bl2 = false;
        }
        this.setFrame(n4, bl, bl2);
    }

    private void setFrame(int n, boolean bl, boolean bl2) {
        if (n >= this.mAnimationState.getChildCount()) {
            return;
        }
        this.mAnimating = bl2;
        this.mCurFrame = n;
        this.selectDrawable(n);
        if (bl || bl2) {
            this.unscheduleSelf(this);
        }
        if (bl2) {
            this.mCurFrame = n;
            this.mRunning = true;
            this.scheduleSelf(this, SystemClock.uptimeMillis() + (long)this.mAnimationState.mDurations[n]);
        }
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        AnimationState animationState = this.mAnimationState;
        animationState.mVariablePadding = typedArray.getBoolean(1, animationState.mVariablePadding);
        animationState = this.mAnimationState;
        animationState.mOneShot = typedArray.getBoolean(2, animationState.mOneShot);
    }

    public void addFrame(Drawable drawable2, int n) {
        this.mAnimationState.addFrame(drawable2, n);
        if (!this.mRunning) {
            this.setFrame(0, true, false);
        }
    }

    @Override
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    @Override
    AnimationState cloneConstantState() {
        return new AnimationState(this.mAnimationState, this, null);
    }

    public int getDuration(int n) {
        return this.mAnimationState.mDurations[n];
    }

    public Drawable getFrame(int n) {
        return this.mAnimationState.getChild(n);
    }

    public int getNumberOfFrames() {
        return this.mAnimationState.getChildCount();
    }

    @Override
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArray = AnimationDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.AnimationDrawable);
        super.inflateWithAttributes(resources, xmlPullParser, typedArray, 0);
        this.updateStateFromTypedArray(typedArray);
        this.updateDensity(resources);
        typedArray.recycle();
        this.inflateChildElements(resources, xmlPullParser, attributeSet, theme);
        this.setFrame(0, true, false);
    }

    public boolean isOneShot() {
        return this.mAnimationState.mOneShot;
    }

    @Override
    public boolean isRunning() {
        return this.mRunning;
    }

    @Override
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mAnimationState.mutate();
            this.mMutated = true;
        }
        return this;
    }

    @Override
    public void run() {
        this.nextFrame(false);
    }

    @Override
    protected void setConstantState(DrawableContainer.DrawableContainerState drawableContainerState) {
        super.setConstantState(drawableContainerState);
        if (drawableContainerState instanceof AnimationState) {
            this.mAnimationState = (AnimationState)drawableContainerState;
        }
    }

    public void setOneShot(boolean bl) {
        this.mAnimationState.mOneShot = bl;
    }

    @Override
    public boolean setVisible(boolean bl, boolean bl2) {
        boolean bl3 = super.setVisible(bl, bl2);
        if (bl) {
            if (bl2 || bl3) {
                int n = 0;
                int n2 = !bl2 && (this.mRunning || this.mAnimationState.mOneShot) && this.mCurFrame < this.mAnimationState.getChildCount() ? 0 : 1;
                n2 = n2 != 0 ? n : this.mCurFrame;
                this.setFrame(n2, true, this.mAnimating);
            }
        } else {
            this.unscheduleSelf(this);
        }
        return bl3;
    }

    @Override
    public void start() {
        boolean bl = true;
        this.mAnimating = true;
        if (!this.isRunning()) {
            if (this.mAnimationState.getChildCount() <= 1 && this.mAnimationState.mOneShot) {
                bl = false;
            }
            this.setFrame(0, false, bl);
        }
    }

    @Override
    public void stop() {
        this.mAnimating = false;
        if (this.isRunning()) {
            this.mCurFrame = 0;
            this.unscheduleSelf(this);
        }
    }

    @Override
    public void unscheduleSelf(Runnable runnable) {
        this.mRunning = false;
        super.unscheduleSelf(runnable);
    }

    private static final class AnimationState
    extends DrawableContainer.DrawableContainerState {
        private int[] mDurations;
        private boolean mOneShot = false;

        AnimationState(AnimationState animationState, AnimationDrawable animationDrawable, Resources resources) {
            super(animationState, animationDrawable, resources);
            if (animationState != null) {
                this.mDurations = animationState.mDurations;
                this.mOneShot = animationState.mOneShot;
            } else {
                this.mDurations = new int[this.getCapacity()];
                this.mOneShot = false;
            }
        }

        private void mutate() {
            this.mDurations = (int[])this.mDurations.clone();
        }

        public void addFrame(Drawable drawable2, int n) {
            int n2 = super.addChild(drawable2);
            this.mDurations[n2] = n;
        }

        @Override
        public void growArray(int n, int n2) {
            super.growArray(n, n2);
            int[] arrn = new int[n2];
            System.arraycopy(this.mDurations, 0, arrn, 0, n);
            this.mDurations = arrn;
        }

        @Override
        public Drawable newDrawable() {
            return new AnimationDrawable(this, null);
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new AnimationDrawable(this, resources);
        }
    }

}

