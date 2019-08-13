/*
 * Decompiled with CFR 0.145.
 */
package android.view.animation;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.List;

public class AnimationSet
extends Animation {
    private static final int PROPERTY_CHANGE_BOUNDS_MASK = 128;
    private static final int PROPERTY_DURATION_MASK = 32;
    private static final int PROPERTY_FILL_AFTER_MASK = 1;
    private static final int PROPERTY_FILL_BEFORE_MASK = 2;
    private static final int PROPERTY_MORPH_MATRIX_MASK = 64;
    private static final int PROPERTY_REPEAT_MODE_MASK = 4;
    private static final int PROPERTY_SHARE_INTERPOLATOR_MASK = 16;
    private static final int PROPERTY_START_OFFSET_MASK = 8;
    private ArrayList<Animation> mAnimations = new ArrayList();
    private boolean mDirty;
    private int mFlags = 0;
    private boolean mHasAlpha;
    private long mLastEnd;
    private long[] mStoredOffsets;
    private Transformation mTempTransformation = new Transformation();

    public AnimationSet(Context context, AttributeSet object) {
        super(context, (AttributeSet)object);
        object = context.obtainStyledAttributes((AttributeSet)object, R.styleable.AnimationSet);
        this.setFlag(16, ((TypedArray)object).getBoolean(1, true));
        this.init();
        if (context.getApplicationInfo().targetSdkVersion >= 14) {
            if (((TypedArray)object).hasValue(0)) {
                this.mFlags |= 32;
            }
            if (((TypedArray)object).hasValue(2)) {
                this.mFlags = 2 | this.mFlags;
            }
            if (((TypedArray)object).hasValue(3)) {
                this.mFlags |= 1;
            }
            if (((TypedArray)object).hasValue(5)) {
                this.mFlags |= 4;
            }
            if (((TypedArray)object).hasValue(4)) {
                this.mFlags |= 8;
            }
        }
        ((TypedArray)object).recycle();
    }

    public AnimationSet(boolean bl) {
        this.setFlag(16, bl);
        this.init();
    }

    private void init() {
        this.mStartTime = 0L;
    }

    private void setFlag(int n, boolean bl) {
        this.mFlags = bl ? (this.mFlags |= n) : (this.mFlags &= n);
    }

    public void addAnimation(Animation animation) {
        this.mAnimations.add(animation);
        int n = this.mFlags;
        int n2 = 0;
        n = (n & 64) == 0 ? 1 : 0;
        if (n != 0 && animation.willChangeTransformationMatrix()) {
            this.mFlags |= 64;
        }
        n = n2;
        if ((this.mFlags & 128) == 0) {
            n = 1;
        }
        if (n != 0 && animation.willChangeBounds()) {
            this.mFlags |= 128;
        }
        if ((this.mFlags & 32) == 32) {
            this.mLastEnd = this.mStartOffset + this.mDuration;
        } else if (this.mAnimations.size() == 1) {
            this.mDuration = animation.getStartOffset() + animation.getDuration();
            this.mLastEnd = this.mStartOffset + this.mDuration;
        } else {
            this.mLastEnd = Math.max(this.mLastEnd, this.mStartOffset + animation.getStartOffset() + animation.getDuration());
            this.mDuration = this.mLastEnd - this.mStartOffset;
        }
        this.mDirty = true;
    }

    @Override
    protected AnimationSet clone() throws CloneNotSupportedException {
        AnimationSet animationSet = (AnimationSet)super.clone();
        animationSet.mTempTransformation = new Transformation();
        animationSet.mAnimations = new ArrayList();
        int n = this.mAnimations.size();
        ArrayList<Animation> arrayList = this.mAnimations;
        for (int i = 0; i < n; ++i) {
            animationSet.mAnimations.add(arrayList.get(i).clone());
        }
        return animationSet;
    }

    @Override
    public long computeDurationHint() {
        long l = 0L;
        int n = this.mAnimations.size();
        ArrayList<Animation> arrayList = this.mAnimations;
        --n;
        while (n >= 0) {
            long l2 = arrayList.get(n).computeDurationHint();
            long l3 = l;
            if (l2 > l) {
                l3 = l2;
            }
            --n;
            l = l3;
        }
        return l;
    }

    public List<Animation> getAnimations() {
        return this.mAnimations;
    }

    @Override
    public long getDuration() {
        long l;
        ArrayList<Animation> arrayList = this.mAnimations;
        int n = arrayList.size();
        long l2 = 0L;
        int n2 = (this.mFlags & 32) == 32 ? 1 : 0;
        if (n2 != 0) {
            l = this.mDuration;
        } else {
            n2 = 0;
            do {
                l = l2;
                if (n2 >= n) break;
                l2 = Math.max(l2, arrayList.get(n2).getDuration());
                ++n2;
            } while (true);
        }
        return l;
    }

    @Override
    public long getStartTime() {
        long l = Long.MAX_VALUE;
        int n = this.mAnimations.size();
        ArrayList<Animation> arrayList = this.mAnimations;
        for (int i = 0; i < n; ++i) {
            l = Math.min(l, arrayList.get(i).getStartTime());
        }
        return l;
    }

    @Override
    public boolean getTransformation(long l, Transformation transformation) {
        int n = this.mAnimations.size();
        ArrayList<Animation> arrayList = this.mAnimations;
        Transformation transformation2 = this.mTempTransformation;
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = true;
        transformation.clear();
        --n;
        do {
            boolean bl4 = true;
            if (n < 0) break;
            Animation animation = arrayList.get(n);
            transformation2.clear();
            boolean bl5 = animation.getTransformation(l, transformation2, this.getScaleFactor()) || bl;
            bl = bl5;
            transformation.compose(transformation2);
            bl2 = bl2 || animation.hasStarted();
            bl5 = animation.hasEnded() && bl3 ? bl4 : false;
            --n;
            bl3 = bl5;
        } while (true);
        if (bl2 && !this.mStarted) {
            this.dispatchAnimationStart();
            this.mStarted = true;
        }
        if (bl3 != this.mEnded) {
            this.dispatchAnimationEnd();
            this.mEnded = bl3;
        }
        return bl;
    }

    @Override
    public boolean hasAlpha() {
        if (this.mDirty) {
            this.mHasAlpha = false;
            this.mDirty = false;
            int n = this.mAnimations.size();
            ArrayList<Animation> arrayList = this.mAnimations;
            for (int i = 0; i < n; ++i) {
                if (!arrayList.get(i).hasAlpha()) continue;
                this.mHasAlpha = true;
                break;
            }
        }
        return this.mHasAlpha;
    }

    @Override
    public void initialize(int n, int n2, int n3, int n4) {
        boolean bl;
        Object object;
        int n5;
        boolean bl2;
        int n6;
        long l;
        long l2;
        boolean bl3;
        int n7;
        boolean bl4;
        boolean bl5;
        ArrayList<Animation> arrayList;
        boolean bl6;
        Interpolator interpolator2;
        boolean bl7;
        block14 : {
            long[] arrl;
            block12 : {
                block13 : {
                    super.initialize(n, n2, n3, n4);
                    n6 = this.mFlags;
                    bl3 = false;
                    n6 = (n6 & 32) == 32 ? 1 : 0;
                    bl2 = (this.mFlags & 1) == 1;
                    bl7 = (this.mFlags & 2) == 2;
                    bl4 = (this.mFlags & 4) == 4;
                    bl = (this.mFlags & 16) == 16;
                    if ((this.mFlags & 8) == 8) {
                        bl3 = true;
                    }
                    if (bl) {
                        this.ensureInterpolator();
                    }
                    arrayList = this.mAnimations;
                    n7 = arrayList.size();
                    l2 = this.mDuration;
                    bl6 = this.mFillAfter;
                    bl5 = this.mFillBefore;
                    n5 = this.mRepeatMode;
                    interpolator2 = this.mInterpolator;
                    l = this.mStartOffset;
                    object = this.mStoredOffsets;
                    if (!bl3) break block12;
                    if (object == null) break block13;
                    arrl = object;
                    if (((long[])object).length == n7) break block14;
                }
                this.mStoredOffsets = arrl = new long[n7];
                break block14;
            }
            arrl = object;
            if (object != null) {
                this.mStoredOffsets = null;
                arrl = null;
            }
        }
        for (int i = 0; i < n7; ++i) {
            object = arrayList.get(i);
            if (n6 != 0) {
                ((Animation)object).setDuration(l2);
            }
            if (bl2) {
                ((Animation)object).setFillAfter(bl6);
            }
            if (bl7) {
                ((Animation)object).setFillBefore(bl5);
            }
            if (bl4) {
                ((Animation)object).setRepeatMode(n5);
            }
            if (bl) {
                ((Animation)object).setInterpolator(interpolator2);
            }
            if (bl3) {
                long l3 = ((Animation)object).getStartOffset();
                ((Animation)object).setStartOffset(l3 + l);
                arrl[i] = l3;
            }
            ((Animation)object).initialize(n, n2, n3, n4);
        }
    }

    @Override
    public void initializeInvalidateRegion(int n, int n2, int n3, int n4) {
        Object object = this.mPreviousRegion;
        ((RectF)object).set(n, n2, n3, n4);
        ((RectF)object).inset(-1.0f, -1.0f);
        if (this.mFillBefore) {
            n = this.mAnimations.size();
            object = this.mAnimations;
            Transformation transformation = this.mTempTransformation;
            Transformation transformation2 = this.mPreviousTransformation;
            --n;
            while (n >= 0) {
                Animation animation = (Animation)((ArrayList)object).get(n);
                if (!animation.isFillEnabled() || animation.getFillBefore() || animation.getStartOffset() == 0L) {
                    transformation.clear();
                    Interpolator interpolator2 = animation.mInterpolator;
                    float f = 0.0f;
                    if (interpolator2 != null) {
                        f = interpolator2.getInterpolation(0.0f);
                    }
                    animation.applyTransformation(f, transformation);
                    transformation2.compose(transformation);
                }
                --n;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.restoreChildrenStartOffset();
    }

    void restoreChildrenStartOffset() {
        long[] arrl = this.mStoredOffsets;
        if (arrl == null) {
            return;
        }
        ArrayList<Animation> arrayList = this.mAnimations;
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).setStartOffset(arrl[i]);
        }
    }

    @Override
    public void restrictDuration(long l) {
        super.restrictDuration(l);
        ArrayList<Animation> arrayList = this.mAnimations;
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).restrictDuration(l);
        }
    }

    @Override
    public void scaleCurrentDuration(float f) {
        ArrayList<Animation> arrayList = this.mAnimations;
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).scaleCurrentDuration(f);
        }
    }

    @Override
    public void setDuration(long l) {
        this.mFlags |= 32;
        super.setDuration(l);
        this.mLastEnd = this.mStartOffset + this.mDuration;
    }

    @Override
    public void setFillAfter(boolean bl) {
        this.mFlags |= 1;
        super.setFillAfter(bl);
    }

    @Override
    public void setFillBefore(boolean bl) {
        this.mFlags |= 2;
        super.setFillBefore(bl);
    }

    @Override
    public void setRepeatMode(int n) {
        this.mFlags |= 4;
        super.setRepeatMode(n);
    }

    @Override
    public void setStartOffset(long l) {
        this.mFlags |= 8;
        super.setStartOffset(l);
    }

    @Override
    public void setStartTime(long l) {
        super.setStartTime(l);
        int n = this.mAnimations.size();
        ArrayList<Animation> arrayList = this.mAnimations;
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).setStartTime(l);
        }
    }

    @Override
    public boolean willChangeBounds() {
        boolean bl = (this.mFlags & 128) == 128;
        return bl;
    }

    @Override
    public boolean willChangeTransformationMatrix() {
        boolean bl = (this.mFlags & 64) == 64;
        return bl;
    }
}

