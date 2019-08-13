/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.view.animation;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Handler;
import android.os.SystemProperties;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import com.android.internal.R;
import dalvik.system.CloseGuard;

public abstract class Animation
implements Cloneable {
    public static final int ABSOLUTE = 0;
    public static final int INFINITE = -1;
    public static final int RELATIVE_TO_PARENT = 2;
    public static final int RELATIVE_TO_SELF = 1;
    public static final int RESTART = 1;
    public static final int REVERSE = 2;
    public static final int START_ON_FIRST_FRAME = -1;
    public static final int ZORDER_BOTTOM = -1;
    public static final int ZORDER_NORMAL = 0;
    public static final int ZORDER_TOP = 1;
    private final CloseGuard guard = CloseGuard.get();
    private int mBackgroundColor;
    boolean mCycleFlip = false;
    long mDuration;
    boolean mEnded = false;
    boolean mFillAfter = false;
    boolean mFillBefore = true;
    boolean mFillEnabled = false;
    private boolean mHasRoundedCorners;
    boolean mInitialized = false;
    Interpolator mInterpolator;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=117519981L)
    private AnimationListener mListener;
    private Handler mListenerHandler;
    private boolean mMore = true;
    private Runnable mOnEnd;
    private Runnable mOnRepeat;
    private Runnable mOnStart;
    private boolean mOneMoreTime = true;
    @UnsupportedAppUsage
    RectF mPreviousRegion = new RectF();
    @UnsupportedAppUsage
    Transformation mPreviousTransformation = new Transformation();
    @UnsupportedAppUsage
    RectF mRegion = new RectF();
    int mRepeatCount = 0;
    int mRepeatMode = 1;
    int mRepeated = 0;
    private float mScaleFactor = 1.0f;
    private boolean mShowWallpaper;
    long mStartOffset;
    long mStartTime = -1L;
    boolean mStarted = false;
    @UnsupportedAppUsage
    Transformation mTransformation = new Transformation();
    private int mZAdjustment;

    public Animation() {
        this.ensureInterpolator();
    }

    public Animation(Context context, AttributeSet object) {
        object = context.obtainStyledAttributes((AttributeSet)object, R.styleable.Animation);
        this.setDuration(((TypedArray)object).getInt(2, 0));
        this.setStartOffset(((TypedArray)object).getInt(5, 0));
        this.setFillEnabled(((TypedArray)object).getBoolean(9, this.mFillEnabled));
        this.setFillBefore(((TypedArray)object).getBoolean(3, this.mFillBefore));
        this.setFillAfter(((TypedArray)object).getBoolean(4, this.mFillAfter));
        this.setRepeatCount(((TypedArray)object).getInt(6, this.mRepeatCount));
        this.setRepeatMode(((TypedArray)object).getInt(7, 1));
        this.setZAdjustment(((TypedArray)object).getInt(8, 0));
        this.setBackgroundColor(((TypedArray)object).getInt(0, 0));
        this.setDetachWallpaper(((TypedArray)object).getBoolean(10, false));
        this.setShowWallpaper(((TypedArray)object).getBoolean(12, false));
        this.setHasRoundedCorners(((TypedArray)object).getBoolean(11, false));
        int n = ((TypedArray)object).getResourceId(1, 0);
        ((TypedArray)object).recycle();
        if (n > 0) {
            this.setInterpolator(context, n);
        }
        this.ensureInterpolator();
    }

    private void fireAnimationEnd() {
        if (this.hasAnimationListener()) {
            Handler handler = this.mListenerHandler;
            if (handler == null) {
                this.dispatchAnimationEnd();
            } else {
                handler.postAtFrontOfQueue(this.mOnEnd);
            }
        }
    }

    private void fireAnimationRepeat() {
        if (this.hasAnimationListener()) {
            Handler handler = this.mListenerHandler;
            if (handler == null) {
                this.dispatchAnimationRepeat();
            } else {
                handler.postAtFrontOfQueue(this.mOnRepeat);
            }
        }
    }

    private void fireAnimationStart() {
        if (this.hasAnimationListener()) {
            Handler handler = this.mListenerHandler;
            if (handler == null) {
                this.dispatchAnimationStart();
            } else {
                handler.postAtFrontOfQueue(this.mOnStart);
            }
        }
    }

    private boolean hasAnimationListener() {
        boolean bl = this.mListener != null;
        return bl;
    }

    private boolean isCanceled() {
        boolean bl = this.mStartTime == Long.MIN_VALUE;
        return bl;
    }

    protected void applyTransformation(float f, Transformation transformation) {
    }

    public void cancel() {
        if (this.mStarted && !this.mEnded) {
            this.fireAnimationEnd();
            this.mEnded = true;
            this.guard.close();
        }
        this.mStartTime = Long.MIN_VALUE;
        this.mOneMoreTime = false;
        this.mMore = false;
    }

    protected Animation clone() throws CloneNotSupportedException {
        Animation animation = (Animation)super.clone();
        animation.mPreviousRegion = new RectF();
        animation.mRegion = new RectF();
        animation.mTransformation = new Transformation();
        animation.mPreviousTransformation = new Transformation();
        return animation;
    }

    public long computeDurationHint() {
        return (this.getStartOffset() + this.getDuration()) * (long)(this.getRepeatCount() + 1);
    }

    @UnsupportedAppUsage
    public void detach() {
        if (this.mStarted && !this.mEnded) {
            this.mEnded = true;
            this.guard.close();
            this.fireAnimationEnd();
        }
    }

    void dispatchAnimationEnd() {
        AnimationListener animationListener = this.mListener;
        if (animationListener != null) {
            animationListener.onAnimationEnd(this);
        }
    }

    void dispatchAnimationRepeat() {
        AnimationListener animationListener = this.mListener;
        if (animationListener != null) {
            animationListener.onAnimationRepeat(this);
        }
    }

    void dispatchAnimationStart() {
        AnimationListener animationListener = this.mListener;
        if (animationListener != null) {
            animationListener.onAnimationStart(this);
        }
    }

    protected void ensureInterpolator() {
        if (this.mInterpolator == null) {
            this.mInterpolator = new AccelerateDecelerateInterpolator();
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.guard != null) {
                this.guard.warnIfOpen();
            }
            return;
        }
        finally {
            super.finalize();
        }
    }

    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    public boolean getDetachWallpaper() {
        return true;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public boolean getFillAfter() {
        return this.mFillAfter;
    }

    public boolean getFillBefore() {
        return this.mFillBefore;
    }

    public Interpolator getInterpolator() {
        return this.mInterpolator;
    }

    @UnsupportedAppUsage
    public void getInvalidateRegion(int n, int n2, int n3, int n4, RectF object, Transformation transformation) {
        RectF rectF = this.mRegion;
        Object object2 = this.mPreviousRegion;
        ((RectF)object).set(n, n2, n3, n4);
        transformation.getMatrix().mapRect((RectF)object);
        ((RectF)object).inset(-1.0f, -1.0f);
        rectF.set((RectF)object);
        ((RectF)object).union((RectF)object2);
        ((RectF)object2).set(rectF);
        object2 = this.mTransformation;
        object = this.mPreviousTransformation;
        ((Transformation)object2).set(transformation);
        transformation.set((Transformation)object);
        ((Transformation)object).set((Transformation)object2);
    }

    public int getRepeatCount() {
        return this.mRepeatCount;
    }

    public int getRepeatMode() {
        return this.mRepeatMode;
    }

    protected float getScaleFactor() {
        return this.mScaleFactor;
    }

    public boolean getShowWallpaper() {
        return this.mShowWallpaper;
    }

    public long getStartOffset() {
        return this.mStartOffset;
    }

    public long getStartTime() {
        return this.mStartTime;
    }

    public boolean getTransformation(long l, Transformation transformation) {
        if (this.mStartTime == -1L) {
            this.mStartTime = l;
        }
        long l2 = this.getStartOffset();
        long l3 = this.mDuration;
        float f = l3 != 0L ? (float)(l - (this.mStartTime + l2)) / (float)l3 : (l < this.mStartTime ? 0.0f : 1.0f);
        boolean bl = f >= 1.0f || this.isCanceled();
        boolean bl2 = !bl;
        this.mMore = bl2;
        float f2 = f;
        if (!this.mFillEnabled) {
            f2 = Math.max(Math.min(f, 1.0f), 0.0f);
        }
        if ((f2 >= 0.0f || this.mFillBefore) && (f2 <= 1.0f || this.mFillAfter)) {
            if (!this.mStarted) {
                this.fireAnimationStart();
                this.mStarted = true;
                if (NoImagePreloadHolder.USE_CLOSEGUARD) {
                    this.guard.open("cancel or detach or getTransformation");
                }
            }
            f = f2;
            if (this.mFillEnabled) {
                f = Math.max(Math.min(f2, 1.0f), 0.0f);
            }
            f2 = f;
            if (this.mCycleFlip) {
                f2 = 1.0f - f;
            }
            this.applyTransformation(this.mInterpolator.getInterpolation(f2), transformation);
        }
        if (bl) {
            if (this.mRepeatCount != this.mRepeated && !this.isCanceled()) {
                if (this.mRepeatCount > 0) {
                    ++this.mRepeated;
                }
                if (this.mRepeatMode == 2) {
                    this.mCycleFlip ^= true;
                }
                this.mStartTime = -1L;
                this.mMore = true;
                this.fireAnimationRepeat();
            } else if (!this.mEnded) {
                this.mEnded = true;
                this.guard.close();
                this.fireAnimationEnd();
            }
        }
        if (!this.mMore && this.mOneMoreTime) {
            this.mOneMoreTime = false;
            return true;
        }
        return this.mMore;
    }

    public boolean getTransformation(long l, Transformation transformation, float f) {
        this.mScaleFactor = f;
        return this.getTransformation(l, transformation);
    }

    public int getZAdjustment() {
        return this.mZAdjustment;
    }

    public boolean hasAlpha() {
        return false;
    }

    public boolean hasEnded() {
        return this.mEnded;
    }

    public boolean hasRoundedCorners() {
        return this.mHasRoundedCorners;
    }

    public boolean hasStarted() {
        return this.mStarted;
    }

    public void initialize(int n, int n2, int n3, int n4) {
        this.reset();
        this.mInitialized = true;
    }

    @UnsupportedAppUsage
    public void initializeInvalidateRegion(int n, int n2, int n3, int n4) {
        Object object = this.mPreviousRegion;
        ((RectF)object).set(n, n2, n3, n4);
        ((RectF)object).inset(-1.0f, -1.0f);
        if (this.mFillBefore) {
            object = this.mPreviousTransformation;
            this.applyTransformation(this.mInterpolator.getInterpolation(0.0f), (Transformation)object);
        }
    }

    public boolean isFillEnabled() {
        return this.mFillEnabled;
    }

    public boolean isInitialized() {
        return this.mInitialized;
    }

    public void reset() {
        this.mPreviousRegion.setEmpty();
        this.mPreviousTransformation.clear();
        this.mInitialized = false;
        this.mCycleFlip = false;
        this.mRepeated = 0;
        this.mMore = true;
        this.mOneMoreTime = true;
        this.mListenerHandler = null;
    }

    protected float resolveSize(int n, float f, int n2, int n3) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return f;
                }
                return (float)n3 * f;
            }
            return (float)n2 * f;
        }
        return f;
    }

    public void restrictDuration(long l) {
        long l2;
        long l3 = this.mStartOffset;
        if (l3 > l) {
            this.mStartOffset = l;
            this.mDuration = 0L;
            this.mRepeatCount = 0;
            return;
        }
        long l4 = l2 = this.mDuration + l3;
        if (l2 > l) {
            this.mDuration = l - l3;
            l4 = l;
        }
        if (this.mDuration <= 0L) {
            this.mDuration = 0L;
            this.mRepeatCount = 0;
            return;
        }
        int n = this.mRepeatCount;
        if (n < 0 || (long)n > l || (long)n * l4 > l) {
            this.mRepeatCount = (int)(l / l4) - 1;
            if (this.mRepeatCount < 0) {
                this.mRepeatCount = 0;
            }
        }
    }

    public void scaleCurrentDuration(float f) {
        this.mDuration = (long)((float)this.mDuration * f);
        this.mStartOffset = (long)((float)this.mStartOffset * f);
    }

    public void setAnimationListener(AnimationListener animationListener) {
        this.mListener = animationListener;
    }

    public void setBackgroundColor(int n) {
        this.mBackgroundColor = n;
    }

    public void setDetachWallpaper(boolean bl) {
    }

    public void setDuration(long l) {
        if (l >= 0L) {
            this.mDuration = l;
            return;
        }
        throw new IllegalArgumentException("Animation duration cannot be negative");
    }

    public void setFillAfter(boolean bl) {
        this.mFillAfter = bl;
    }

    public void setFillBefore(boolean bl) {
        this.mFillBefore = bl;
    }

    public void setFillEnabled(boolean bl) {
        this.mFillEnabled = bl;
    }

    public void setHasRoundedCorners(boolean bl) {
        this.mHasRoundedCorners = bl;
    }

    public void setInterpolator(Context context, int n) {
        this.setInterpolator(AnimationUtils.loadInterpolator(context, n));
    }

    public void setInterpolator(Interpolator interpolator2) {
        this.mInterpolator = interpolator2;
    }

    public void setListenerHandler(Handler handler) {
        if (this.mListenerHandler == null) {
            this.mOnStart = new Runnable(){

                @Override
                public void run() {
                    Animation.this.dispatchAnimationStart();
                }
            };
            this.mOnRepeat = new Runnable(){

                @Override
                public void run() {
                    Animation.this.dispatchAnimationRepeat();
                }
            };
            this.mOnEnd = new Runnable(){

                @Override
                public void run() {
                    Animation.this.dispatchAnimationEnd();
                }
            };
        }
        this.mListenerHandler = handler;
    }

    public void setRepeatCount(int n) {
        int n2 = n;
        if (n < 0) {
            n2 = -1;
        }
        this.mRepeatCount = n2;
    }

    public void setRepeatMode(int n) {
        this.mRepeatMode = n;
    }

    public void setShowWallpaper(boolean bl) {
        this.mShowWallpaper = bl;
    }

    public void setStartOffset(long l) {
        this.mStartOffset = l;
    }

    public void setStartTime(long l) {
        this.mStartTime = l;
        this.mEnded = false;
        this.mStarted = false;
        this.mCycleFlip = false;
        this.mRepeated = 0;
        this.mMore = true;
    }

    public void setZAdjustment(int n) {
        this.mZAdjustment = n;
    }

    public void start() {
        this.setStartTime(-1L);
    }

    public void startNow() {
        this.setStartTime(AnimationUtils.currentAnimationTimeMillis());
    }

    public boolean willChangeBounds() {
        return true;
    }

    public boolean willChangeTransformationMatrix() {
        return true;
    }

    public static interface AnimationListener {
        public void onAnimationEnd(Animation var1);

        public void onAnimationRepeat(Animation var1);

        public void onAnimationStart(Animation var1);
    }

    protected static class Description {
        public int type;
        public float value;

        protected Description() {
        }

        static Description parseValue(TypedValue typedValue) {
            Description description = new Description();
            if (typedValue == null) {
                description.type = 0;
                description.value = 0.0f;
            } else {
                if (typedValue.type == 6) {
                    int n = typedValue.data;
                    int n2 = 1;
                    if ((n & 15) == 1) {
                        n2 = 2;
                    }
                    description.type = n2;
                    description.value = TypedValue.complexToFloat(typedValue.data);
                    return description;
                }
                if (typedValue.type == 4) {
                    description.type = 0;
                    description.value = typedValue.getFloat();
                    return description;
                }
                if (typedValue.type >= 16 && typedValue.type <= 31) {
                    description.type = 0;
                    description.value = typedValue.data;
                    return description;
                }
            }
            description.type = 0;
            description.value = 0.0f;
            return description;
        }
    }

    private static class NoImagePreloadHolder {
        public static final boolean USE_CLOSEGUARD = SystemProperties.getBoolean("log.closeguard.Animation", false);

        private NoImagePreloadHolder() {
        }
    }

}

