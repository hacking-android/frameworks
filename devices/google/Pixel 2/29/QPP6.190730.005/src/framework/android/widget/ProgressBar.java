/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.MathUtils;
import android.util.Pools;
import android.view.AbsSavedState;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewHierarchyEncoder;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.RemoteViews;
import com.android.internal.R;
import java.util.ArrayList;

@RemoteViews.RemoteView
public class ProgressBar
extends View {
    private static final int MAX_LEVEL = 10000;
    private static final int PROGRESS_ANIM_DURATION = 80;
    private static final DecelerateInterpolator PROGRESS_ANIM_INTERPOLATOR = new DecelerateInterpolator();
    private static final int TIMEOUT_SEND_ACCESSIBILITY_EVENT = 200;
    private final FloatProperty<ProgressBar> VISUAL_PROGRESS;
    private AccessibilityEventSender mAccessibilityEventSender;
    private boolean mAggregatedIsVisible;
    private AlphaAnimation mAnimation;
    private boolean mAttached;
    private int mBehavior;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private Drawable mCurrentDrawable;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124052713L)
    private int mDuration;
    private boolean mHasAnimation;
    private boolean mInDrawing;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private boolean mIndeterminate;
    private Drawable mIndeterminateDrawable;
    private Interpolator mInterpolator;
    private int mMax;
    @UnsupportedAppUsage(maxTargetSdk=28)
    int mMaxHeight;
    private boolean mMaxInitialized;
    int mMaxWidth;
    private int mMin;
    @UnsupportedAppUsage(maxTargetSdk=28)
    int mMinHeight;
    private boolean mMinInitialized;
    @UnsupportedAppUsage(maxTargetSdk=28)
    int mMinWidth;
    @UnsupportedAppUsage
    boolean mMirrorForRtl;
    private boolean mNoInvalidate;
    @UnsupportedAppUsage(trackingBug=124049927L)
    private boolean mOnlyIndeterminate;
    private int mProgress;
    private Drawable mProgressDrawable;
    private ProgressTintInfo mProgressTintInfo;
    private final ArrayList<RefreshData> mRefreshData;
    private boolean mRefreshIsPosted;
    private RefreshProgressRunnable mRefreshProgressRunnable;
    int mSampleWidth;
    private int mSecondaryProgress;
    private boolean mShouldStartAnimationDrawable;
    private Transformation mTransformation;
    private long mUiThreadId;
    private float mVisualProgress;

    public ProgressBar(Context context) {
        this(context, null);
    }

    public ProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842871);
    }

    public ProgressBar(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ProgressBar(Context object, AttributeSet object2, int n, int n2) {
        super((Context)object, (AttributeSet)object2, n, n2);
        boolean bl = false;
        this.mSampleWidth = 0;
        this.mMirrorForRtl = false;
        this.mRefreshData = new ArrayList();
        this.VISUAL_PROGRESS = new FloatProperty<ProgressBar>("visual_progress"){

            @Override
            public Float get(ProgressBar progressBar) {
                return Float.valueOf(progressBar.mVisualProgress);
            }

            @Override
            public void setValue(ProgressBar progressBar, float f) {
                progressBar.setVisualProgress(16908301, f);
                progressBar.mVisualProgress = f;
            }
        };
        this.mUiThreadId = Thread.currentThread().getId();
        this.initProgressBar();
        TypedArray typedArray = ((Context)object).obtainStyledAttributes((AttributeSet)object2, R.styleable.ProgressBar, n, n2);
        this.saveAttributeDataForStyleable((Context)object, R.styleable.ProgressBar, (AttributeSet)object2, typedArray, n, n2);
        this.mNoInvalidate = true;
        object2 = typedArray.getDrawable(8);
        if (object2 != null) {
            if (ProgressBar.needsTileify((Drawable)object2)) {
                this.setProgressDrawableTiled((Drawable)object2);
            } else {
                this.setProgressDrawable((Drawable)object2);
            }
        }
        this.mDuration = typedArray.getInt(9, this.mDuration);
        this.mMinWidth = typedArray.getDimensionPixelSize(11, this.mMinWidth);
        this.mMaxWidth = typedArray.getDimensionPixelSize(0, this.mMaxWidth);
        this.mMinHeight = typedArray.getDimensionPixelSize(12, this.mMinHeight);
        this.mMaxHeight = typedArray.getDimensionPixelSize(1, this.mMaxHeight);
        this.mBehavior = typedArray.getInt(10, this.mBehavior);
        n = typedArray.getResourceId(13, 17432587);
        if (n > 0) {
            this.setInterpolator((Context)object, n);
        }
        this.setMin(typedArray.getInt(26, this.mMin));
        this.setMax(typedArray.getInt(2, this.mMax));
        this.setProgress(typedArray.getInt(3, this.mProgress));
        this.setSecondaryProgress(typedArray.getInt(4, this.mSecondaryProgress));
        object = typedArray.getDrawable(7);
        if (object != null) {
            if (ProgressBar.needsTileify((Drawable)object)) {
                this.setIndeterminateDrawableTiled((Drawable)object);
            } else {
                this.setIndeterminateDrawable((Drawable)object);
            }
        }
        this.mOnlyIndeterminate = typedArray.getBoolean(6, this.mOnlyIndeterminate);
        this.mNoInvalidate = false;
        if (this.mOnlyIndeterminate || typedArray.getBoolean(5, this.mIndeterminate)) {
            bl = true;
        }
        this.setIndeterminate(bl);
        this.mMirrorForRtl = typedArray.getBoolean(15, this.mMirrorForRtl);
        if (typedArray.hasValue(17)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mProgressBlendMode = Drawable.parseBlendMode(typedArray.getInt(17, -1), null);
            this.mProgressTintInfo.mHasProgressTintMode = true;
        }
        if (typedArray.hasValue(16)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mProgressTintList = typedArray.getColorStateList(16);
            this.mProgressTintInfo.mHasProgressTint = true;
        }
        if (typedArray.hasValue(19)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mProgressBackgroundBlendMode = Drawable.parseBlendMode(typedArray.getInt(19, -1), null);
            this.mProgressTintInfo.mHasProgressBackgroundTintMode = true;
        }
        if (typedArray.hasValue(18)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mProgressBackgroundTintList = typedArray.getColorStateList(18);
            this.mProgressTintInfo.mHasProgressBackgroundTint = true;
        }
        if (typedArray.hasValue(21)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mSecondaryProgressBlendMode = Drawable.parseBlendMode(typedArray.getInt(21, -1), null);
            this.mProgressTintInfo.mHasSecondaryProgressTintMode = true;
        }
        if (typedArray.hasValue(20)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mSecondaryProgressTintList = typedArray.getColorStateList(20);
            this.mProgressTintInfo.mHasSecondaryProgressTint = true;
        }
        if (typedArray.hasValue(23)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mIndeterminateBlendMode = Drawable.parseBlendMode(typedArray.getInt(23, -1), null);
            this.mProgressTintInfo.mHasIndeterminateTintMode = true;
        }
        if (typedArray.hasValue(22)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mIndeterminateTintList = typedArray.getColorStateList(22);
            this.mProgressTintInfo.mHasIndeterminateTint = true;
        }
        typedArray.recycle();
        this.applyProgressTints();
        this.applyIndeterminateTint();
        if (this.getImportantForAccessibility() == 0) {
            this.setImportantForAccessibility(1);
        }
    }

    private void applyIndeterminateTint() {
        if (this.mIndeterminateDrawable != null && this.mProgressTintInfo != null) {
            ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
            if (progressTintInfo.mHasIndeterminateTint || progressTintInfo.mHasIndeterminateTintMode) {
                this.mIndeterminateDrawable = this.mIndeterminateDrawable.mutate();
                if (progressTintInfo.mHasIndeterminateTint) {
                    this.mIndeterminateDrawable.setTintList(progressTintInfo.mIndeterminateTintList);
                }
                if (progressTintInfo.mHasIndeterminateTintMode) {
                    this.mIndeterminateDrawable.setTintBlendMode(progressTintInfo.mIndeterminateBlendMode);
                }
                if (this.mIndeterminateDrawable.isStateful()) {
                    this.mIndeterminateDrawable.setState(this.getDrawableState());
                }
            }
        }
    }

    private void applyPrimaryProgressTint() {
        Drawable drawable2;
        if ((this.mProgressTintInfo.mHasProgressTint || this.mProgressTintInfo.mHasProgressTintMode) && (drawable2 = this.getTintTarget(16908301, true)) != null) {
            if (this.mProgressTintInfo.mHasProgressTint) {
                drawable2.setTintList(this.mProgressTintInfo.mProgressTintList);
            }
            if (this.mProgressTintInfo.mHasProgressTintMode) {
                drawable2.setTintBlendMode(this.mProgressTintInfo.mProgressBlendMode);
            }
            if (drawable2.isStateful()) {
                drawable2.setState(this.getDrawableState());
            }
        }
    }

    private void applyProgressBackgroundTint() {
        Drawable drawable2;
        if ((this.mProgressTintInfo.mHasProgressBackgroundTint || this.mProgressTintInfo.mHasProgressBackgroundTintMode) && (drawable2 = this.getTintTarget(16908288, false)) != null) {
            if (this.mProgressTintInfo.mHasProgressBackgroundTint) {
                drawable2.setTintList(this.mProgressTintInfo.mProgressBackgroundTintList);
            }
            if (this.mProgressTintInfo.mHasProgressBackgroundTintMode) {
                drawable2.setTintBlendMode(this.mProgressTintInfo.mProgressBackgroundBlendMode);
            }
            if (drawable2.isStateful()) {
                drawable2.setState(this.getDrawableState());
            }
        }
    }

    private void applyProgressTints() {
        if (this.mProgressDrawable != null && this.mProgressTintInfo != null) {
            this.applyPrimaryProgressTint();
            this.applyProgressBackgroundTint();
            this.applySecondaryProgressTint();
        }
    }

    private void applySecondaryProgressTint() {
        Drawable drawable2;
        if ((this.mProgressTintInfo.mHasSecondaryProgressTint || this.mProgressTintInfo.mHasSecondaryProgressTintMode) && (drawable2 = this.getTintTarget(16908303, false)) != null) {
            if (this.mProgressTintInfo.mHasSecondaryProgressTint) {
                drawable2.setTintList(this.mProgressTintInfo.mSecondaryProgressTintList);
            }
            if (this.mProgressTintInfo.mHasSecondaryProgressTintMode) {
                drawable2.setTintBlendMode(this.mProgressTintInfo.mSecondaryProgressBlendMode);
            }
            if (drawable2.isStateful()) {
                drawable2.setState(this.getDrawableState());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void doRefreshProgress(int n, int n2, boolean bl, boolean bl2, boolean bl3) {
        synchronized (this) {
            int n3 = this.mMax - this.mMin;
            float f = n3 > 0 ? (float)(n2 - this.mMin) / (float)n3 : 0.0f;
            n3 = n == 16908301 ? 1 : 0;
            if (n3 != 0 && bl3) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, this.VISUAL_PROGRESS, f);
                objectAnimator.setAutoCancel(true);
                objectAnimator.setDuration(80L);
                objectAnimator.setInterpolator(PROGRESS_ANIM_INTERPOLATOR);
                objectAnimator.start();
            } else {
                this.setVisualProgress(n, f);
            }
            if (n3 != 0 && bl2) {
                this.onProgressRefresh(f, bl, n2);
            }
            return;
        }
    }

    private Drawable getTintTarget(int n, boolean bl) {
        Drawable drawable2 = null;
        Drawable drawable3 = null;
        Drawable drawable4 = this.mProgressDrawable;
        if (drawable4 != null) {
            this.mProgressDrawable = drawable4.mutate();
            if (drawable4 instanceof LayerDrawable) {
                drawable3 = ((LayerDrawable)drawable4).findDrawableByLayerId(n);
            }
            drawable2 = drawable3;
            if (bl) {
                drawable2 = drawable3;
                if (drawable3 == null) {
                    drawable2 = drawable4;
                }
            }
        }
        return drawable2;
    }

    private void initProgressBar() {
        this.mMin = 0;
        this.mMax = 100;
        this.mProgress = 0;
        this.mSecondaryProgress = 0;
        this.mIndeterminate = false;
        this.mOnlyIndeterminate = false;
        this.mDuration = 4000;
        this.mBehavior = 1;
        this.mMinWidth = 24;
        this.mMaxWidth = 48;
        this.mMinHeight = 24;
        this.mMaxHeight = 48;
    }

    private static boolean needsTileify(Drawable drawable2) {
        if (drawable2 instanceof LayerDrawable) {
            drawable2 = (LayerDrawable)drawable2;
            int n = ((LayerDrawable)drawable2).getNumberOfLayers();
            for (int i = 0; i < n; ++i) {
                if (!ProgressBar.needsTileify(((LayerDrawable)drawable2).getDrawable(i))) continue;
                return true;
            }
            return false;
        }
        if (drawable2 instanceof StateListDrawable) {
            drawable2 = (StateListDrawable)drawable2;
            int n = ((StateListDrawable)drawable2).getStateCount();
            for (int i = 0; i < n; ++i) {
                if (!ProgressBar.needsTileify(((StateListDrawable)drawable2).getStateDrawable(i))) continue;
                return true;
            }
            return false;
        }
        return drawable2 instanceof BitmapDrawable;
    }

    @UnsupportedAppUsage
    private void refreshProgress(int n, int n2, boolean bl, boolean bl2) {
        synchronized (this) {
            if (this.mUiThreadId == Thread.currentThread().getId()) {
                this.doRefreshProgress(n, n2, bl, true, bl2);
            } else {
                Object object;
                if (this.mRefreshProgressRunnable == null) {
                    this.mRefreshProgressRunnable = object = new RefreshProgressRunnable();
                }
                object = RefreshData.obtain(n, n2, bl, bl2);
                this.mRefreshData.add((RefreshData)object);
                if (this.mAttached && !this.mRefreshIsPosted) {
                    this.post(this.mRefreshProgressRunnable);
                    this.mRefreshIsPosted = true;
                }
            }
            return;
        }
    }

    private void scheduleAccessibilityEventSender() {
        AccessibilityEventSender accessibilityEventSender = this.mAccessibilityEventSender;
        if (accessibilityEventSender == null) {
            this.mAccessibilityEventSender = new AccessibilityEventSender();
        } else {
            this.removeCallbacks(accessibilityEventSender);
        }
        this.postDelayed(this.mAccessibilityEventSender, 200L);
    }

    private void setVisualProgress(int n, float f) {
        Drawable drawable2;
        this.mVisualProgress = f;
        Drawable drawable3 = drawable2 = this.mCurrentDrawable;
        if (drawable2 instanceof LayerDrawable) {
            drawable3 = drawable2 = ((LayerDrawable)drawable2).findDrawableByLayerId(n);
            if (drawable2 == null) {
                drawable3 = this.mCurrentDrawable;
            }
        }
        if (drawable3 != null) {
            drawable3.setLevel((int)(10000.0f * f));
        } else {
            this.invalidate();
        }
        this.onVisualProgressChanged(n, f);
    }

    private void swapCurrentDrawable(Drawable drawable2) {
        Drawable drawable3 = this.mCurrentDrawable;
        this.mCurrentDrawable = drawable2;
        if (drawable3 != this.mCurrentDrawable) {
            if (drawable3 != null) {
                drawable3.setVisible(false, false);
            }
            if ((drawable2 = this.mCurrentDrawable) != null) {
                boolean bl = this.getWindowVisibility() == 0 && this.isShown();
                drawable2.setVisible(bl, false);
            }
        }
    }

    @UnsupportedAppUsage
    private Drawable tileify(Drawable drawable2, boolean bl) {
        if (drawable2 instanceof LayerDrawable) {
            int n;
            drawable2 = (LayerDrawable)drawable2;
            int n2 = ((LayerDrawable)drawable2).getNumberOfLayers();
            Object object = new Drawable[n2];
            for (n = 0; n < n2; ++n) {
                int n3 = ((LayerDrawable)drawable2).getId(n);
                Drawable drawable3 = ((LayerDrawable)drawable2).getDrawable(n);
                bl = n3 == 16908301 || n3 == 16908303;
                object[n] = this.tileify(drawable3, bl);
            }
            object = new LayerDrawable((Drawable[])object);
            for (n = 0; n < n2; ++n) {
                ((LayerDrawable)object).setId(n, ((LayerDrawable)drawable2).getId(n));
                ((LayerDrawable)object).setLayerGravity(n, ((LayerDrawable)drawable2).getLayerGravity(n));
                ((LayerDrawable)object).setLayerWidth(n, ((LayerDrawable)drawable2).getLayerWidth(n));
                ((LayerDrawable)object).setLayerHeight(n, ((LayerDrawable)drawable2).getLayerHeight(n));
                ((LayerDrawable)object).setLayerInsetLeft(n, ((LayerDrawable)drawable2).getLayerInsetLeft(n));
                ((LayerDrawable)object).setLayerInsetRight(n, ((LayerDrawable)drawable2).getLayerInsetRight(n));
                ((LayerDrawable)object).setLayerInsetTop(n, ((LayerDrawable)drawable2).getLayerInsetTop(n));
                ((LayerDrawable)object).setLayerInsetBottom(n, ((LayerDrawable)drawable2).getLayerInsetBottom(n));
                ((LayerDrawable)object).setLayerInsetStart(n, ((LayerDrawable)drawable2).getLayerInsetStart(n));
                ((LayerDrawable)object).setLayerInsetEnd(n, ((LayerDrawable)drawable2).getLayerInsetEnd(n));
            }
            return object;
        }
        if (drawable2 instanceof StateListDrawable) {
            StateListDrawable stateListDrawable = (StateListDrawable)drawable2;
            drawable2 = new StateListDrawable();
            int n = stateListDrawable.getStateCount();
            for (int i = 0; i < n; ++i) {
                ((StateListDrawable)drawable2).addState(stateListDrawable.getStateSet(i), this.tileify(stateListDrawable.getStateDrawable(i), bl));
            }
            return drawable2;
        }
        if (drawable2 instanceof BitmapDrawable) {
            drawable2 = (BitmapDrawable)drawable2.getConstantState().newDrawable(this.getResources());
            ((BitmapDrawable)drawable2).setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
            if (this.mSampleWidth <= 0) {
                this.mSampleWidth = ((BitmapDrawable)drawable2).getIntrinsicWidth();
            }
            if (bl) {
                return new ClipDrawable(drawable2, 3, 1);
            }
            return drawable2;
        }
        return drawable2;
    }

    private Drawable tileifyIndeterminate(Drawable drawable2) {
        Drawable drawable3 = drawable2;
        if (drawable2 instanceof AnimationDrawable) {
            AnimationDrawable animationDrawable = (AnimationDrawable)drawable2;
            int n = animationDrawable.getNumberOfFrames();
            drawable3 = new AnimationDrawable();
            ((AnimationDrawable)drawable3).setOneShot(animationDrawable.isOneShot());
            for (int i = 0; i < n; ++i) {
                drawable2 = this.tileify(animationDrawable.getFrame(i), true);
                drawable2.setLevel(10000);
                ((AnimationDrawable)drawable3).addFrame(drawable2, animationDrawable.getDuration(i));
            }
            drawable3.setLevel(10000);
        }
        return drawable3;
    }

    private void updateDrawableBounds(int n, int n2) {
        int n3 = n - (this.mPaddingRight + this.mPaddingLeft);
        int n4 = n2 - (this.mPaddingTop + this.mPaddingBottom);
        n = n3;
        n2 = n4;
        int n5 = 0;
        int n6 = 0;
        Drawable drawable2 = this.mIndeterminateDrawable;
        int n7 = n;
        int n8 = n2;
        if (drawable2 != null) {
            int n9 = n;
            n8 = n2;
            int n10 = n5;
            n7 = n6;
            if (this.mOnlyIndeterminate) {
                n9 = n;
                n8 = n2;
                n10 = n5;
                n7 = n6;
                if (!(drawable2 instanceof AnimationDrawable)) {
                    n7 = drawable2.getIntrinsicWidth();
                    n8 = this.mIndeterminateDrawable.getIntrinsicHeight();
                    float f = (float)n7 / (float)n8;
                    float f2 = (float)n3 / (float)n4;
                    n9 = n;
                    n8 = n2;
                    n10 = n5;
                    n7 = n6;
                    if (f != f2) {
                        if (f2 > f) {
                            n = (int)((float)n4 * f);
                            n7 = (n3 - n) / 2;
                            n9 = n7 + n;
                            n8 = n2;
                            n10 = n5;
                        } else {
                            n2 = (int)((float)n3 * (1.0f / f));
                            n10 = (n4 - n2) / 2;
                            n8 = n10 + n2;
                            n7 = n6;
                            n9 = n;
                        }
                    }
                }
            }
            n = n9;
            n2 = n7;
            if (this.isLayoutRtl()) {
                n = n9;
                n2 = n7;
                if (this.mMirrorForRtl) {
                    n2 = n3 - n9;
                    n = n3 - n7;
                }
            }
            this.mIndeterminateDrawable.setBounds(n2, n10, n, n8);
            n7 = n;
        }
        if ((drawable2 = this.mProgressDrawable) != null) {
            drawable2.setBounds(0, 0, n7, n8);
        }
    }

    private void updateDrawableState() {
        int[] arrn = this.getDrawableState();
        boolean bl = false;
        Drawable drawable2 = this.mProgressDrawable;
        boolean bl2 = bl;
        if (drawable2 != null) {
            bl2 = bl;
            if (drawable2.isStateful()) {
                bl2 = false | drawable2.setState(arrn);
            }
        }
        drawable2 = this.mIndeterminateDrawable;
        bl = bl2;
        if (drawable2 != null) {
            bl = bl2;
            if (drawable2.isStateful()) {
                bl = bl2 | drawable2.setState(arrn);
            }
        }
        if (bl) {
            this.invalidate();
        }
    }

    void drawTrack(Canvas canvas) {
        Drawable drawable2 = this.mCurrentDrawable;
        if (drawable2 != null) {
            int n = canvas.save();
            if (this.isLayoutRtl() && this.mMirrorForRtl) {
                canvas.translate(this.getWidth() - this.mPaddingRight, this.mPaddingTop);
                canvas.scale(-1.0f, 1.0f);
            } else {
                canvas.translate(this.mPaddingLeft, this.mPaddingTop);
            }
            long l = this.getDrawingTime();
            if (this.mHasAnimation) {
                this.mAnimation.getTransformation(l, this.mTransformation);
                float f = this.mTransformation.getAlpha();
                try {
                    this.mInDrawing = true;
                    drawable2.setLevel((int)(10000.0f * f));
                    this.postInvalidateOnAnimation();
                }
                finally {
                    this.mInDrawing = false;
                }
            }
            drawable2.draw(canvas);
            canvas.restoreToCount(n);
            if (this.mShouldStartAnimationDrawable && drawable2 instanceof Animatable) {
                ((Animatable)((Object)drawable2)).start();
                this.mShouldStartAnimationDrawable = false;
            }
        }
    }

    @Override
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable2 = this.mProgressDrawable;
        if (drawable2 != null) {
            drawable2.setHotspot(f, f2);
        }
        if ((drawable2 = this.mIndeterminateDrawable) != null) {
            drawable2.setHotspot(f, f2);
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.updateDrawableState();
    }

    @Override
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("progress:max", this.getMax());
        viewHierarchyEncoder.addProperty("progress:progress", this.getProgress());
        viewHierarchyEncoder.addProperty("progress:secondaryProgress", this.getSecondaryProgress());
        viewHierarchyEncoder.addProperty("progress:indeterminate", this.isIndeterminate());
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return ProgressBar.class.getName();
    }

    public Drawable getCurrentDrawable() {
        return this.mCurrentDrawable;
    }

    Shape getDrawableShape() {
        return new RoundRectShape(new float[]{5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f}, null, null);
    }

    public Drawable getIndeterminateDrawable() {
        return this.mIndeterminateDrawable;
    }

    public BlendMode getIndeterminateTintBlendMode() {
        Object object = this.mProgressTintInfo;
        object = object != null ? object.mIndeterminateBlendMode : null;
        return object;
    }

    public ColorStateList getIndeterminateTintList() {
        Object object = this.mProgressTintInfo;
        object = object != null ? ((ProgressTintInfo)object).mIndeterminateTintList : null;
        return object;
    }

    public PorterDuff.Mode getIndeterminateTintMode() {
        Enum enum_ = this.getIndeterminateTintBlendMode();
        enum_ = enum_ != null ? BlendMode.blendModeToPorterDuffMode((BlendMode)enum_) : null;
        return enum_;
    }

    public Interpolator getInterpolator() {
        return this.mInterpolator;
    }

    @ViewDebug.ExportedProperty(category="progress")
    public int getMax() {
        synchronized (this) {
            int n = this.mMax;
            return n;
        }
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    @ViewDebug.ExportedProperty(category="progress")
    public int getMin() {
        synchronized (this) {
            int n = this.mMin;
            return n;
        }
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    public boolean getMirrorForRtl() {
        return this.mMirrorForRtl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @ViewDebug.ExportedProperty(category="progress")
    public int getProgress() {
        synchronized (this) {
            block4 : {
                if (!this.mIndeterminate) break block4;
                return 0;
            }
            return this.mProgress;
        }
    }

    public BlendMode getProgressBackgroundTintBlendMode() {
        Object object = this.mProgressTintInfo;
        object = object != null ? object.mProgressBackgroundBlendMode : null;
        return object;
    }

    public ColorStateList getProgressBackgroundTintList() {
        Object object = this.mProgressTintInfo;
        object = object != null ? ((ProgressTintInfo)object).mProgressBackgroundTintList : null;
        return object;
    }

    public PorterDuff.Mode getProgressBackgroundTintMode() {
        Enum enum_ = this.getProgressBackgroundTintBlendMode();
        enum_ = enum_ != null ? BlendMode.blendModeToPorterDuffMode((BlendMode)enum_) : null;
        return enum_;
    }

    public Drawable getProgressDrawable() {
        return this.mProgressDrawable;
    }

    public BlendMode getProgressTintBlendMode() {
        Object object = this.mProgressTintInfo;
        object = object != null ? object.mProgressBlendMode : null;
        return object;
    }

    public ColorStateList getProgressTintList() {
        Object object = this.mProgressTintInfo;
        object = object != null ? ((ProgressTintInfo)object).mProgressTintList : null;
        return object;
    }

    public PorterDuff.Mode getProgressTintMode() {
        Enum enum_ = this.getProgressTintBlendMode();
        enum_ = enum_ != null ? BlendMode.blendModeToPorterDuffMode((BlendMode)enum_) : null;
        return enum_;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @ViewDebug.ExportedProperty(category="progress")
    public int getSecondaryProgress() {
        synchronized (this) {
            block4 : {
                if (!this.mIndeterminate) break block4;
                return 0;
            }
            return this.mSecondaryProgress;
        }
    }

    public BlendMode getSecondaryProgressTintBlendMode() {
        Object object = this.mProgressTintInfo;
        object = object != null ? object.mSecondaryProgressBlendMode : null;
        return object;
    }

    public ColorStateList getSecondaryProgressTintList() {
        Object object = this.mProgressTintInfo;
        object = object != null ? ((ProgressTintInfo)object).mSecondaryProgressTintList : null;
        return object;
    }

    public PorterDuff.Mode getSecondaryProgressTintMode() {
        Enum enum_ = this.getSecondaryProgressTintBlendMode();
        enum_ = enum_ != null ? BlendMode.blendModeToPorterDuffMode((BlendMode)enum_) : null;
        return enum_;
    }

    public final void incrementProgressBy(int n) {
        synchronized (this) {
            this.setProgress(this.mProgress + n);
            return;
        }
    }

    public final void incrementSecondaryProgressBy(int n) {
        synchronized (this) {
            this.setSecondaryProgress(this.mSecondaryProgress + n);
            return;
        }
    }

    @Override
    public void invalidateDrawable(Drawable object) {
        if (!this.mInDrawing) {
            if (this.verifyDrawable((Drawable)object)) {
                object = ((Drawable)object).getBounds();
                int n = this.mScrollX + this.mPaddingLeft;
                int n2 = this.mScrollY + this.mPaddingTop;
                this.invalidate(((Rect)object).left + n, ((Rect)object).top + n2, ((Rect)object).right + n, ((Rect)object).bottom + n2);
            } else {
                super.invalidateDrawable((Drawable)object);
            }
        }
    }

    public boolean isAnimating() {
        boolean bl = this.isIndeterminate() && this.getWindowVisibility() == 0 && this.isShown();
        return bl;
    }

    @ViewDebug.ExportedProperty(category="progress")
    public boolean isIndeterminate() {
        synchronized (this) {
            boolean bl = this.mIndeterminate;
            return bl;
        }
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable2 = this.mProgressDrawable;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
        if ((drawable2 = this.mIndeterminateDrawable) != null) {
            drawable2.jumpToCurrentState();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mIndeterminate) {
            this.startAnimation();
        }
        if (this.mRefreshData != null) {
            synchronized (this) {
                int n = this.mRefreshData.size();
                for (int i = 0; i < n; ++i) {
                    RefreshData refreshData = this.mRefreshData.get(i);
                    this.doRefreshProgress(refreshData.id, refreshData.progress, refreshData.fromUser, true, refreshData.animate);
                    refreshData.recycle();
                }
                this.mRefreshData.clear();
            }
        }
        this.mAttached = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        Runnable runnable;
        if (this.mIndeterminate) {
            this.stopAnimation();
        }
        if ((runnable = this.mRefreshProgressRunnable) != null) {
            this.removeCallbacks(runnable);
            this.mRefreshIsPosted = false;
        }
        if ((runnable = this.mAccessibilityEventSender) != null) {
            this.removeCallbacks(runnable);
        }
        super.onDetachedFromWindow();
        this.mAttached = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        synchronized (this) {
            super.onDraw(canvas);
            this.drawTrack(canvas);
            return;
        }
    }

    @Override
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setItemCount(this.mMax - this.mMin);
        accessibilityEvent.setCurrentItemIndex(this.mProgress);
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (!this.isIndeterminate()) {
            accessibilityNodeInfo.setRangeInfo(AccessibilityNodeInfo.RangeInfo.obtain(0, this.getMin(), this.getMax(), this.getProgress()));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void onMeasure(int n, int n2) {
        synchronized (this) {
            int n3 = 0;
            int n4 = 0;
            Drawable drawable2 = this.mCurrentDrawable;
            if (drawable2 != null) {
                n3 = Math.max(this.mMinWidth, Math.min(this.mMaxWidth, drawable2.getIntrinsicWidth()));
                n4 = Math.max(this.mMinHeight, Math.min(this.mMaxHeight, drawable2.getIntrinsicHeight()));
            }
            this.updateDrawableState();
            int n5 = this.mPaddingLeft;
            int n6 = this.mPaddingRight;
            int n7 = this.mPaddingTop;
            int n8 = this.mPaddingBottom;
            this.setMeasuredDimension(ProgressBar.resolveSizeAndState(n3 + (n5 + n6), n, 0), ProgressBar.resolveSizeAndState(n4 + (n7 + n8), n2, 0));
            return;
        }
    }

    void onProgressRefresh(float f, boolean bl, int n) {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            this.scheduleAccessibilityEventSender();
        }
    }

    @Override
    public void onResolveDrawables(int n) {
        Drawable drawable2 = this.mCurrentDrawable;
        if (drawable2 != null) {
            drawable2.setLayoutDirection(n);
        }
        if ((drawable2 = this.mIndeterminateDrawable) != null) {
            drawable2.setLayoutDirection(n);
        }
        if ((drawable2 = this.mProgressDrawable) != null) {
            drawable2.setLayoutDirection(n);
        }
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(((AbsSavedState)parcelable).getSuperState());
        this.setProgress(((SavedState)parcelable).progress);
        this.setSecondaryProgress(((SavedState)parcelable).secondaryProgress);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.progress = this.mProgress;
        savedState.secondaryProgress = this.mSecondaryProgress;
        return savedState;
    }

    @Override
    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        this.updateDrawableBounds(n, n2);
    }

    @Override
    public void onVisibilityAggregated(boolean bl) {
        super.onVisibilityAggregated(bl);
        if (bl != this.mAggregatedIsVisible) {
            Drawable drawable2;
            this.mAggregatedIsVisible = bl;
            if (this.mIndeterminate) {
                if (bl) {
                    this.startAnimation();
                } else {
                    this.stopAnimation();
                }
            }
            if ((drawable2 = this.mCurrentDrawable) != null) {
                drawable2.setVisible(bl, false);
            }
        }
    }

    void onVisualProgressChanged(int n, float f) {
    }

    @Override
    public void postInvalidate() {
        if (!this.mNoInvalidate) {
            super.postInvalidate();
        }
    }

    @RemotableViewMethod
    public void setIndeterminate(boolean bl) {
        synchronized (this) {
            if (!(this.mOnlyIndeterminate && this.mIndeterminate || bl == this.mIndeterminate)) {
                this.mIndeterminate = bl;
                if (bl) {
                    this.swapCurrentDrawable(this.mIndeterminateDrawable);
                    this.startAnimation();
                } else {
                    this.swapCurrentDrawable(this.mProgressDrawable);
                    this.stopAnimation();
                }
            }
            return;
        }
    }

    public void setIndeterminateDrawable(Drawable drawable2) {
        Drawable drawable3 = this.mIndeterminateDrawable;
        if (drawable3 != drawable2) {
            if (drawable3 != null) {
                drawable3.setCallback(null);
                this.unscheduleDrawable(this.mIndeterminateDrawable);
            }
            this.mIndeterminateDrawable = drawable2;
            if (drawable2 != null) {
                drawable2.setCallback(this);
                drawable2.setLayoutDirection(this.getLayoutDirection());
                if (drawable2.isStateful()) {
                    drawable2.setState(this.getDrawableState());
                }
                this.applyIndeterminateTint();
            }
            if (this.mIndeterminate) {
                this.swapCurrentDrawable(drawable2);
                this.postInvalidate();
            }
        }
    }

    public void setIndeterminateDrawableTiled(Drawable drawable2) {
        Drawable drawable3 = drawable2;
        if (drawable2 != null) {
            drawable3 = this.tileifyIndeterminate(drawable2);
        }
        this.setIndeterminateDrawable(drawable3);
    }

    public void setIndeterminateTintBlendMode(BlendMode blendMode) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        progressTintInfo.mIndeterminateBlendMode = blendMode;
        progressTintInfo.mHasIndeterminateTintMode = true;
        this.applyIndeterminateTint();
    }

    @RemotableViewMethod
    public void setIndeterminateTintList(ColorStateList colorStateList) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        progressTintInfo.mIndeterminateTintList = colorStateList;
        progressTintInfo.mHasIndeterminateTint = true;
        this.applyIndeterminateTint();
    }

    public void setIndeterminateTintMode(PorterDuff.Mode enum_) {
        enum_ = enum_ != null ? BlendMode.fromValue(((PorterDuff.Mode)enum_).nativeInt) : null;
        this.setIndeterminateTintBlendMode((BlendMode)enum_);
    }

    public void setInterpolator(Context context, int n) {
        this.setInterpolator(AnimationUtils.loadInterpolator(context, n));
    }

    public void setInterpolator(Interpolator interpolator2) {
        this.mInterpolator = interpolator2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RemotableViewMethod
    public void setMax(int n) {
        synchronized (this) {
            int n2 = n;
            if (this.mMinInitialized) {
                n2 = n;
                if (n < this.mMin) {
                    n2 = this.mMin;
                }
            }
            this.mMaxInitialized = true;
            if (this.mMinInitialized && n2 != this.mMax) {
                this.mMax = n2;
                this.postInvalidate();
                if (this.mProgress > n2) {
                    this.mProgress = n2;
                }
                this.refreshProgress(16908301, this.mProgress, false, false);
            } else {
                this.mMax = n2;
            }
            return;
        }
    }

    public void setMaxHeight(int n) {
        this.mMaxHeight = n;
        this.requestLayout();
    }

    public void setMaxWidth(int n) {
        this.mMaxWidth = n;
        this.requestLayout();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RemotableViewMethod
    public void setMin(int n) {
        synchronized (this) {
            int n2 = n;
            if (this.mMaxInitialized) {
                n2 = n;
                if (n > this.mMax) {
                    n2 = this.mMax;
                }
            }
            this.mMinInitialized = true;
            if (this.mMaxInitialized && n2 != this.mMin) {
                this.mMin = n2;
                this.postInvalidate();
                if (this.mProgress < n2) {
                    this.mProgress = n2;
                }
                this.refreshProgress(16908301, this.mProgress, false, false);
            } else {
                this.mMin = n2;
            }
            return;
        }
    }

    public void setMinHeight(int n) {
        this.mMinHeight = n;
        this.requestLayout();
    }

    public void setMinWidth(int n) {
        this.mMinWidth = n;
        this.requestLayout();
    }

    @RemotableViewMethod
    public void setProgress(int n) {
        synchronized (this) {
            this.setProgressInternal(n, false, false);
            return;
        }
    }

    public void setProgress(int n, boolean bl) {
        this.setProgressInternal(n, false, bl);
    }

    public void setProgressBackgroundTintBlendMode(BlendMode blendMode) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        progressTintInfo.mProgressBackgroundBlendMode = blendMode;
        progressTintInfo.mHasProgressBackgroundTintMode = true;
        if (this.mProgressDrawable != null) {
            this.applyProgressBackgroundTint();
        }
    }

    @RemotableViewMethod
    public void setProgressBackgroundTintList(ColorStateList colorStateList) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        progressTintInfo.mProgressBackgroundTintList = colorStateList;
        progressTintInfo.mHasProgressBackgroundTint = true;
        if (this.mProgressDrawable != null) {
            this.applyProgressBackgroundTint();
        }
    }

    public void setProgressBackgroundTintMode(PorterDuff.Mode enum_) {
        enum_ = enum_ != null ? BlendMode.fromValue(((PorterDuff.Mode)enum_).nativeInt) : null;
        this.setProgressBackgroundTintBlendMode((BlendMode)enum_);
    }

    public void setProgressDrawable(Drawable drawable2) {
        Drawable drawable3 = this.mProgressDrawable;
        if (drawable3 != drawable2) {
            if (drawable3 != null) {
                drawable3.setCallback(null);
                this.unscheduleDrawable(this.mProgressDrawable);
            }
            this.mProgressDrawable = drawable2;
            if (drawable2 != null) {
                int n;
                drawable2.setCallback(this);
                drawable2.setLayoutDirection(this.getLayoutDirection());
                if (drawable2.isStateful()) {
                    drawable2.setState(this.getDrawableState());
                }
                if (this.mMaxHeight < (n = drawable2.getMinimumHeight())) {
                    this.mMaxHeight = n;
                    this.requestLayout();
                }
                this.applyProgressTints();
            }
            if (!this.mIndeterminate) {
                this.swapCurrentDrawable(drawable2);
                this.postInvalidate();
            }
            this.updateDrawableBounds(this.getWidth(), this.getHeight());
            this.updateDrawableState();
            this.doRefreshProgress(16908301, this.mProgress, false, false, false);
            this.doRefreshProgress(16908303, this.mSecondaryProgress, false, false, false);
        }
    }

    public void setProgressDrawableTiled(Drawable drawable2) {
        Drawable drawable3 = drawable2;
        if (drawable2 != null) {
            drawable3 = this.tileify(drawable2, false);
        }
        this.setProgressDrawable(drawable3);
    }

    @RemotableViewMethod
    @UnsupportedAppUsage
    boolean setProgressInternal(int n, boolean bl, boolean bl2) {
        synchronized (this) {
            block6 : {
                block5 : {
                    boolean bl3 = this.mIndeterminate;
                    if (!bl3) break block5;
                    return false;
                }
                n = MathUtils.constrain(n, this.mMin, this.mMax);
                int n2 = this.mProgress;
                if (n != n2) break block6;
                return false;
            }
            this.mProgress = n;
            this.refreshProgress(16908301, this.mProgress, bl, bl2);
            return true;
        }
    }

    public void setProgressTintBlendMode(BlendMode blendMode) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        progressTintInfo.mProgressBlendMode = blendMode;
        progressTintInfo.mHasProgressTintMode = true;
        if (this.mProgressDrawable != null) {
            this.applyPrimaryProgressTint();
        }
    }

    @RemotableViewMethod
    public void setProgressTintList(ColorStateList colorStateList) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        progressTintInfo.mProgressTintList = colorStateList;
        progressTintInfo.mHasProgressTint = true;
        if (this.mProgressDrawable != null) {
            this.applyPrimaryProgressTint();
        }
    }

    public void setProgressTintMode(PorterDuff.Mode enum_) {
        enum_ = enum_ != null ? BlendMode.fromValue(((PorterDuff.Mode)enum_).nativeInt) : null;
        this.setProgressTintBlendMode((BlendMode)enum_);
    }

    @RemotableViewMethod
    public void setSecondaryProgress(int n) {
        synchronized (this) {
            block8 : {
                boolean bl = this.mIndeterminate;
                if (!bl) break block8;
                return;
            }
            int n2 = n;
            if (n < this.mMin) {
                n2 = this.mMin;
            }
            n = n2;
            if (n2 > this.mMax) {
                n = this.mMax;
            }
            if (n != this.mSecondaryProgress) {
                this.mSecondaryProgress = n;
                this.refreshProgress(16908303, this.mSecondaryProgress, false, false);
            }
            return;
        }
    }

    public void setSecondaryProgressTintBlendMode(BlendMode blendMode) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        progressTintInfo.mSecondaryProgressBlendMode = blendMode;
        progressTintInfo.mHasSecondaryProgressTintMode = true;
        if (this.mProgressDrawable != null) {
            this.applySecondaryProgressTint();
        }
    }

    public void setSecondaryProgressTintList(ColorStateList colorStateList) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        progressTintInfo.mSecondaryProgressTintList = colorStateList;
        progressTintInfo.mHasSecondaryProgressTint = true;
        if (this.mProgressDrawable != null) {
            this.applySecondaryProgressTint();
        }
    }

    public void setSecondaryProgressTintMode(PorterDuff.Mode enum_) {
        enum_ = enum_ != null ? BlendMode.fromValue(((PorterDuff.Mode)enum_).nativeInt) : null;
        this.setSecondaryProgressTintBlendMode((BlendMode)enum_);
    }

    @UnsupportedAppUsage
    void startAnimation() {
        if (this.getVisibility() == 0 && this.getWindowVisibility() == 0) {
            if (this.mIndeterminateDrawable instanceof Animatable) {
                this.mShouldStartAnimationDrawable = true;
                this.mHasAnimation = false;
            } else {
                Object object;
                this.mHasAnimation = true;
                if (this.mInterpolator == null) {
                    this.mInterpolator = new LinearInterpolator();
                }
                if ((object = this.mTransformation) == null) {
                    this.mTransformation = new Transformation();
                } else {
                    ((Transformation)object).clear();
                }
                object = this.mAnimation;
                if (object == null) {
                    this.mAnimation = new AlphaAnimation(0.0f, 1.0f);
                } else {
                    ((Animation)object).reset();
                }
                this.mAnimation.setRepeatMode(this.mBehavior);
                this.mAnimation.setRepeatCount(-1);
                this.mAnimation.setDuration(this.mDuration);
                this.mAnimation.setInterpolator(this.mInterpolator);
                this.mAnimation.setStartTime(-1L);
            }
            this.postInvalidate();
            return;
        }
    }

    @UnsupportedAppUsage
    void stopAnimation() {
        this.mHasAnimation = false;
        Drawable drawable2 = this.mIndeterminateDrawable;
        if (drawable2 instanceof Animatable) {
            ((Animatable)((Object)drawable2)).stop();
            this.mShouldStartAnimationDrawable = false;
        }
        this.postInvalidate();
    }

    @Override
    protected boolean verifyDrawable(Drawable drawable2) {
        boolean bl = drawable2 == this.mProgressDrawable || drawable2 == this.mIndeterminateDrawable || super.verifyDrawable(drawable2);
        return bl;
    }

    private class AccessibilityEventSender
    implements Runnable {
        private AccessibilityEventSender() {
        }

        @Override
        public void run() {
            ProgressBar.this.sendAccessibilityEvent(4);
        }
    }

    private static class ProgressTintInfo {
        boolean mHasIndeterminateTint;
        boolean mHasIndeterminateTintMode;
        boolean mHasProgressBackgroundTint;
        boolean mHasProgressBackgroundTintMode;
        boolean mHasProgressTint;
        boolean mHasProgressTintMode;
        boolean mHasSecondaryProgressTint;
        boolean mHasSecondaryProgressTintMode;
        BlendMode mIndeterminateBlendMode;
        ColorStateList mIndeterminateTintList;
        BlendMode mProgressBackgroundBlendMode;
        ColorStateList mProgressBackgroundTintList;
        BlendMode mProgressBlendMode;
        ColorStateList mProgressTintList;
        BlendMode mSecondaryProgressBlendMode;
        ColorStateList mSecondaryProgressTintList;

        private ProgressTintInfo() {
        }
    }

    private static class RefreshData {
        private static final int POOL_MAX = 24;
        private static final Pools.SynchronizedPool<RefreshData> sPool = new Pools.SynchronizedPool(24);
        public boolean animate;
        public boolean fromUser;
        public int id;
        public int progress;

        private RefreshData() {
        }

        public static RefreshData obtain(int n, int n2, boolean bl, boolean bl2) {
            RefreshData refreshData;
            RefreshData refreshData2 = refreshData = sPool.acquire();
            if (refreshData == null) {
                refreshData2 = new RefreshData();
            }
            refreshData2.id = n;
            refreshData2.progress = n2;
            refreshData2.fromUser = bl;
            refreshData2.animate = bl2;
            return refreshData2;
        }

        public void recycle() {
            sPool.release(this);
        }
    }

    private class RefreshProgressRunnable
    implements Runnable {
        private RefreshProgressRunnable() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            ProgressBar progressBar = ProgressBar.this;
            synchronized (progressBar) {
                int n = ProgressBar.this.mRefreshData.size();
                int n2 = 0;
                do {
                    if (n2 >= n) {
                        ProgressBar.this.mRefreshData.clear();
                        ProgressBar.this.mRefreshIsPosted = false;
                        return;
                    }
                    RefreshData refreshData = (RefreshData)ProgressBar.this.mRefreshData.get(n2);
                    ProgressBar.this.doRefreshProgress(refreshData.id, refreshData.progress, refreshData.fromUser, true, refreshData.animate);
                    refreshData.recycle();
                    ++n2;
                } while (true);
            }
        }
    }

    static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        int progress;
        int secondaryProgress;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.progress = parcel.readInt();
            this.secondaryProgress = parcel.readInt();
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.progress);
            parcel.writeInt(this.secondaryProgress);
        }

    }

}

