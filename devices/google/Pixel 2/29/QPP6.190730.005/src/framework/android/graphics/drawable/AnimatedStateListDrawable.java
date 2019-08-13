/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.graphics.drawable;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.UnsupportedAppUsage;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LongSparseLongArray;
import android.util.SparseIntArray;
import android.util.StateSet;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimatedStateListDrawable
extends StateListDrawable {
    private static final String ELEMENT_ITEM = "item";
    private static final String ELEMENT_TRANSITION = "transition";
    private static final String LOGTAG = AnimatedStateListDrawable.class.getSimpleName();
    private boolean mMutated;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private AnimatedStateListState mState;
    private Transition mTransition;
    private int mTransitionFromIndex = -1;
    private int mTransitionToIndex = -1;

    public AnimatedStateListDrawable() {
        this(null, null);
    }

    private AnimatedStateListDrawable(AnimatedStateListState animatedStateListState, Resources resources) {
        super(null);
        this.setConstantState(new AnimatedStateListState(animatedStateListState, this, resources));
        this.onStateChange(this.getState());
        this.jumpToCurrentState();
    }

    private void inflateChildElements(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int n;
        int n2;
        int n3 = xmlPullParser.getDepth() + 1;
        while ((n2 = xmlPullParser.next()) != 1 && ((n = xmlPullParser.getDepth()) >= n3 || n2 != 3)) {
            if (n2 != 2 || n > n3) continue;
            if (xmlPullParser.getName().equals(ELEMENT_ITEM)) {
                this.parseItem(resources, xmlPullParser, attributeSet, theme);
                continue;
            }
            if (!xmlPullParser.getName().equals(ELEMENT_TRANSITION)) continue;
            this.parseTransition(resources, xmlPullParser, attributeSet, theme);
        }
    }

    private void init() {
        this.onStateChange(this.getState());
    }

    private int parseItem(Resources object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        Object object2 = AnimatedStateListDrawable.obtainAttributes((Resources)object, theme, attributeSet, R.styleable.AnimatedStateListDrawableItem);
        int n = ((TypedArray)object2).getResourceId(0, 0);
        Drawable drawable2 = ((TypedArray)object2).getDrawable(1);
        ((TypedArray)object2).recycle();
        int[] arrn = this.extractStateSet(attributeSet);
        object2 = drawable2;
        if (drawable2 == null) {
            int n2;
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
        return this.mState.addStateSet(arrn, (Drawable)object2, n);
    }

    private int parseTransition(Resources object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        Object object2 = AnimatedStateListDrawable.obtainAttributes((Resources)object, theme, attributeSet, R.styleable.AnimatedStateListDrawableTransition);
        int n = ((TypedArray)object2).getResourceId(2, 0);
        int n2 = ((TypedArray)object2).getResourceId(1, 0);
        boolean bl = ((TypedArray)object2).getBoolean(3, false);
        Drawable drawable2 = ((TypedArray)object2).getDrawable(0);
        ((TypedArray)object2).recycle();
        object2 = drawable2;
        if (drawable2 == null) {
            int n3;
            while ((n3 = xmlPullParser.next()) == 4) {
            }
            if (n3 == 2) {
                object2 = Drawable.createFromXmlInner((Resources)object, xmlPullParser, attributeSet, theme);
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
                ((StringBuilder)object).append(": <transition> tag requires a 'drawable' attribute or child tag defining a drawable");
                throw new XmlPullParserException(((StringBuilder)object).toString());
            }
        }
        return this.mState.addTransition(n, n2, (Drawable)object2, bl);
    }

    private boolean selectTransition(int n) {
        block8 : {
            block12 : {
                Object object;
                int n2;
                block10 : {
                    block11 : {
                        int n3;
                        AnimatedStateListState animatedStateListState;
                        int n4;
                        boolean bl;
                        block9 : {
                            object = this.mTransition;
                            if (object != null) {
                                if (n == this.mTransitionToIndex) {
                                    return true;
                                }
                                if (n == this.mTransitionFromIndex && ((Transition)object).canReverse()) {
                                    ((Transition)object).reverse();
                                    this.mTransitionToIndex = this.mTransitionFromIndex;
                                    this.mTransitionFromIndex = n;
                                    return true;
                                }
                                n2 = this.mTransitionToIndex;
                                ((Transition)object).stop();
                            } else {
                                n2 = this.getCurrentIndex();
                            }
                            this.mTransition = null;
                            this.mTransitionFromIndex = -1;
                            this.mTransitionToIndex = -1;
                            animatedStateListState = this.mState;
                            n3 = animatedStateListState.getKeyframeIdAt(n2);
                            n4 = animatedStateListState.getKeyframeIdAt(n);
                            if (n4 == 0 || n3 == 0) break block8;
                            int n5 = animatedStateListState.indexOfTransition(n3, n4);
                            if (n5 < 0) {
                                return false;
                            }
                            bl = animatedStateListState.transitionHasReversibleFlag(n3, n4);
                            this.selectDrawable(n5);
                            object = this.getCurrent();
                            if (!(object instanceof AnimationDrawable)) break block9;
                            boolean bl2 = animatedStateListState.isTransitionReversed(n3, n4);
                            object = new AnimationDrawableTransition((AnimationDrawable)object, bl2, bl);
                            break block10;
                        }
                        if (!(object instanceof AnimatedVectorDrawable)) break block11;
                        boolean bl3 = animatedStateListState.isTransitionReversed(n3, n4);
                        object = new AnimatedVectorDrawableTransition((AnimatedVectorDrawable)object, bl3, bl);
                        break block10;
                    }
                    if (!(object instanceof Animatable)) break block12;
                    object = new AnimatableTransition((Animatable)object);
                }
                ((Transition)object).start();
                this.mTransition = object;
                this.mTransitionFromIndex = n2;
                this.mTransitionToIndex = n;
                return true;
            }
            return false;
        }
        return false;
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        AnimatedStateListState animatedStateListState = this.mState;
        animatedStateListState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        animatedStateListState.mAnimThemeAttrs = typedArray.extractThemeAttrs();
        animatedStateListState.setVariablePadding(typedArray.getBoolean(2, animatedStateListState.mVariablePadding));
        animatedStateListState.setConstantSize(typedArray.getBoolean(3, animatedStateListState.mConstantSize));
        animatedStateListState.setEnterFadeDuration(typedArray.getInt(4, animatedStateListState.mEnterFadeDuration));
        animatedStateListState.setExitFadeDuration(typedArray.getInt(5, animatedStateListState.mExitFadeDuration));
        this.setDither(typedArray.getBoolean(0, animatedStateListState.mDither));
        this.setAutoMirrored(typedArray.getBoolean(6, animatedStateListState.mAutoMirrored));
    }

    public void addState(int[] arrn, Drawable drawable2, int n) {
        if (drawable2 != null) {
            this.mState.addStateSet(arrn, drawable2, n);
            this.onStateChange(this.getState());
            return;
        }
        throw new IllegalArgumentException("Drawable must not be null");
    }

    public <T extends Drawable> void addTransition(int n, int n2, T t, boolean bl) {
        if (t != null) {
            this.mState.addTransition(n, n2, t, bl);
            return;
        }
        throw new IllegalArgumentException("Transition drawable must not be null");
    }

    @Override
    public void applyTheme(Resources.Theme object) {
        super.applyTheme((Resources.Theme)object);
        AnimatedStateListState animatedStateListState = this.mState;
        if (animatedStateListState != null && animatedStateListState.mAnimThemeAttrs != null) {
            object = ((Resources.Theme)object).resolveAttributes(animatedStateListState.mAnimThemeAttrs, R.styleable.AnimatedRotateDrawable);
            this.updateStateFromTypedArray((TypedArray)object);
            ((TypedArray)object).recycle();
            this.init();
            return;
        }
    }

    @Override
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    @Override
    AnimatedStateListState cloneConstantState() {
        return new AnimatedStateListState(this.mState, this, null);
    }

    @Override
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArray = AnimatedStateListDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.AnimatedStateListDrawable);
        super.inflateWithAttributes(resources, xmlPullParser, typedArray, 1);
        this.updateStateFromTypedArray(typedArray);
        this.updateDensity(resources);
        typedArray.recycle();
        this.inflateChildElements(resources, xmlPullParser, attributeSet, theme);
        this.init();
    }

    @Override
    public boolean isStateful() {
        return true;
    }

    @Override
    public void jumpToCurrentState() {
        super.jumpToCurrentState();
        Transition transition2 = this.mTransition;
        if (transition2 != null) {
            transition2.stop();
            this.mTransition = null;
            this.selectDrawable(this.mTransitionToIndex);
            this.mTransitionToIndex = -1;
            this.mTransitionFromIndex = -1;
        }
    }

    @Override
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mState.mutate();
            this.mMutated = true;
        }
        return this;
    }

    @Override
    protected boolean onStateChange(int[] arrn) {
        int n = this.mState.indexOfKeyframe(arrn);
        boolean bl = n != this.getCurrentIndex() && (this.selectTransition(n) || this.selectDrawable(n));
        Drawable drawable2 = this.getCurrent();
        boolean bl2 = bl;
        if (drawable2 != null) {
            bl2 = bl | drawable2.setState(arrn);
        }
        return bl2;
    }

    @Override
    protected void setConstantState(DrawableContainer.DrawableContainerState drawableContainerState) {
        super.setConstantState(drawableContainerState);
        if (drawableContainerState instanceof AnimatedStateListState) {
            this.mState = (AnimatedStateListState)drawableContainerState;
        }
    }

    @Override
    public boolean setVisible(boolean bl, boolean bl2) {
        boolean bl3 = super.setVisible(bl, bl2);
        if (this.mTransition != null && (bl3 || bl2)) {
            if (bl) {
                this.mTransition.start();
            } else {
                this.jumpToCurrentState();
            }
        }
        return bl3;
    }

    private static class AnimatableTransition
    extends Transition {
        private final Animatable mA;

        public AnimatableTransition(Animatable animatable) {
            this.mA = animatable;
        }

        @Override
        public void start() {
            this.mA.start();
        }

        @Override
        public void stop() {
            this.mA.stop();
        }
    }

    static class AnimatedStateListState
    extends StateListDrawable.StateListState {
        private static final long REVERSED_BIT = 0x100000000L;
        private static final long REVERSIBLE_FLAG_BIT = 0x200000000L;
        int[] mAnimThemeAttrs;
        @UnsupportedAppUsage
        SparseIntArray mStateIds;
        @UnsupportedAppUsage
        LongSparseLongArray mTransitions;

        AnimatedStateListState(AnimatedStateListState animatedStateListState, AnimatedStateListDrawable animatedStateListDrawable, Resources resources) {
            super(animatedStateListState, animatedStateListDrawable, resources);
            if (animatedStateListState != null) {
                this.mAnimThemeAttrs = animatedStateListState.mAnimThemeAttrs;
                this.mTransitions = animatedStateListState.mTransitions;
                this.mStateIds = animatedStateListState.mStateIds;
            } else {
                this.mTransitions = new LongSparseLongArray();
                this.mStateIds = new SparseIntArray();
            }
        }

        private static long generateTransitionKey(int n, int n2) {
            return (long)n << 32 | (long)n2;
        }

        int addStateSet(int[] arrn, Drawable drawable2, int n) {
            int n2 = super.addStateSet(arrn, drawable2);
            this.mStateIds.put(n2, n);
            return n2;
        }

        int addTransition(int n, int n2, Drawable drawable2, boolean bl) {
            int n3;
            block1 : {
                n3 = super.addChild(drawable2);
                long l = AnimatedStateListState.generateTransitionKey(n, n2);
                long l2 = 0L;
                if (bl) {
                    l2 = 0x200000000L;
                }
                this.mTransitions.append(l, (long)n3 | l2);
                if (!bl) break block1;
                l = AnimatedStateListState.generateTransitionKey(n2, n);
                this.mTransitions.append(l, (long)n3 | 0x100000000L | l2);
            }
            return n3;
        }

        @Override
        public boolean canApplyTheme() {
            boolean bl = this.mAnimThemeAttrs != null || super.canApplyTheme();
            return bl;
        }

        int getKeyframeIdAt(int n) {
            int n2 = 0;
            n = n < 0 ? n2 : this.mStateIds.get(n, 0);
            return n;
        }

        int indexOfKeyframe(int[] arrn) {
            int n = super.indexOfStateSet(arrn);
            if (n >= 0) {
                return n;
            }
            return super.indexOfStateSet(StateSet.WILD_CARD);
        }

        int indexOfTransition(int n, int n2) {
            long l = AnimatedStateListState.generateTransitionKey(n, n2);
            return (int)this.mTransitions.get(l, -1L);
        }

        boolean isTransitionReversed(int n, int n2) {
            long l = AnimatedStateListState.generateTransitionKey(n, n2);
            boolean bl = (this.mTransitions.get(l, -1L) & 0x100000000L) != 0L;
            return bl;
        }

        @Override
        void mutate() {
            this.mTransitions = this.mTransitions.clone();
            this.mStateIds = this.mStateIds.clone();
        }

        @Override
        public Drawable newDrawable() {
            return new AnimatedStateListDrawable(this, null);
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new AnimatedStateListDrawable(this, resources);
        }

        boolean transitionHasReversibleFlag(int n, int n2) {
            long l = AnimatedStateListState.generateTransitionKey(n, n2);
            boolean bl = (this.mTransitions.get(l, -1L) & 0x200000000L) != 0L;
            return bl;
        }
    }

    private static class AnimatedVectorDrawableTransition
    extends Transition {
        private final AnimatedVectorDrawable mAvd;
        private final boolean mHasReversibleFlag;
        private final boolean mReversed;

        public AnimatedVectorDrawableTransition(AnimatedVectorDrawable animatedVectorDrawable, boolean bl, boolean bl2) {
            this.mAvd = animatedVectorDrawable;
            this.mReversed = bl;
            this.mHasReversibleFlag = bl2;
        }

        @Override
        public boolean canReverse() {
            boolean bl = this.mAvd.canReverse() && this.mHasReversibleFlag;
            return bl;
        }

        @Override
        public void reverse() {
            if (this.canReverse()) {
                this.mAvd.reverse();
            } else {
                Log.w(LOGTAG, "Can't reverse, either the reversible is set to false, or the AnimatedVectorDrawable can't reverse");
            }
        }

        @Override
        public void start() {
            if (this.mReversed) {
                this.reverse();
            } else {
                this.mAvd.start();
            }
        }

        @Override
        public void stop() {
            this.mAvd.stop();
        }
    }

    private static class AnimationDrawableTransition
    extends Transition {
        private final ObjectAnimator mAnim;
        private final boolean mHasReversibleFlag;

        public AnimationDrawableTransition(AnimationDrawable object, boolean bl, boolean bl2) {
            int n = ((AnimationDrawable)object).getNumberOfFrames();
            int n2 = bl ? n - 1 : 0;
            if (bl) {
                n = 0;
            }
            FrameInterpolator frameInterpolator = new FrameInterpolator((AnimationDrawable)object, bl);
            object = ObjectAnimator.ofInt(object, "currentIndex", n2, --n);
            ((ObjectAnimator)object).setAutoCancel(true);
            ((ObjectAnimator)object).setDuration(frameInterpolator.getTotalDuration());
            ((ValueAnimator)object).setInterpolator(frameInterpolator);
            this.mHasReversibleFlag = bl2;
            this.mAnim = object;
        }

        @Override
        public boolean canReverse() {
            return this.mHasReversibleFlag;
        }

        @Override
        public void reverse() {
            this.mAnim.reverse();
        }

        @Override
        public void start() {
            this.mAnim.start();
        }

        @Override
        public void stop() {
            this.mAnim.cancel();
        }
    }

    private static class FrameInterpolator
    implements TimeInterpolator {
        private int[] mFrameTimes;
        private int mFrames;
        private int mTotalDuration;

        public FrameInterpolator(AnimationDrawable animationDrawable, boolean bl) {
            this.updateFrames(animationDrawable, bl);
        }

        @Override
        public float getInterpolation(float f) {
            int n;
            int n2 = (int)((float)this.mTotalDuration * f + 0.5f);
            int n3 = this.mFrames;
            int[] arrn = this.mFrameTimes;
            for (n = 0; n < n3 && n2 >= arrn[n]; n2 -= arrn[n], ++n) {
            }
            f = n < n3 ? (float)n2 / (float)this.mTotalDuration : 0.0f;
            return (float)n / (float)n3 + f;
        }

        public int getTotalDuration() {
            return this.mTotalDuration;
        }

        public int updateFrames(AnimationDrawable animationDrawable, boolean bl) {
            int n;
            this.mFrames = n = animationDrawable.getNumberOfFrames();
            int[] arrn = this.mFrameTimes;
            if (arrn == null || arrn.length < n) {
                this.mFrameTimes = new int[n];
            }
            arrn = this.mFrameTimes;
            int n2 = 0;
            for (int i = 0; i < n; ++i) {
                int n3 = bl ? n - i - 1 : i;
                arrn[i] = n3 = animationDrawable.getDuration(n3);
                n2 += n3;
            }
            this.mTotalDuration = n2;
            return n2;
        }
    }

    private static abstract class Transition {
        private Transition() {
        }

        public boolean canReverse() {
            return false;
        }

        public void reverse() {
        }

        public abstract void start();

        public abstract void stop();
    }

}

