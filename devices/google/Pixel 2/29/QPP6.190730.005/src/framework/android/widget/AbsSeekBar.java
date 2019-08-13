/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Insets;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ProgressBar;
import com.android.internal.R;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class AbsSeekBar
extends ProgressBar {
    private static final int NO_ALPHA = 255;
    @UnsupportedAppUsage
    private float mDisabledAlpha;
    private final List<Rect> mGestureExclusionRects = new ArrayList<Rect>();
    private boolean mHasThumbBlendMode = false;
    private boolean mHasThumbTint = false;
    private boolean mHasTickMarkBlendMode = false;
    private boolean mHasTickMarkTint = false;
    @UnsupportedAppUsage
    private boolean mIsDragging;
    @UnsupportedAppUsage
    boolean mIsUserSeekable = true;
    private int mKeyProgressIncrement = 1;
    private int mScaledTouchSlop;
    @UnsupportedAppUsage
    private boolean mSplitTrack;
    private final Rect mTempRect = new Rect();
    @UnsupportedAppUsage
    private Drawable mThumb;
    private BlendMode mThumbBlendMode = null;
    private int mThumbOffset;
    private final Rect mThumbRect = new Rect();
    private ColorStateList mThumbTintList = null;
    private Drawable mTickMark;
    private BlendMode mTickMarkBlendMode = null;
    private ColorStateList mTickMarkTintList = null;
    private float mTouchDownX;
    @UnsupportedAppUsage
    float mTouchProgressOffset;
    private List<Rect> mUserGestureExclusionRects = Collections.emptyList();

    public AbsSeekBar(Context context) {
        super(context);
    }

    public AbsSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AbsSeekBar(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public AbsSeekBar(Context context, AttributeSet object, int n, int n2) {
        super(context, (AttributeSet)object, n, n2);
        TypedArray typedArray = context.obtainStyledAttributes((AttributeSet)object, R.styleable.SeekBar, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.SeekBar, (AttributeSet)object, typedArray, n, n2);
        this.setThumb(typedArray.getDrawable(0));
        if (typedArray.hasValue(4)) {
            this.mThumbBlendMode = Drawable.parseBlendMode(typedArray.getInt(4, -1), this.mThumbBlendMode);
            this.mHasThumbBlendMode = true;
        }
        if (typedArray.hasValue(3)) {
            this.mThumbTintList = typedArray.getColorStateList(3);
            this.mHasThumbTint = true;
        }
        this.setTickMark(typedArray.getDrawable(5));
        if (typedArray.hasValue(7)) {
            this.mTickMarkBlendMode = Drawable.parseBlendMode(typedArray.getInt(7, -1), this.mTickMarkBlendMode);
            this.mHasTickMarkBlendMode = true;
        }
        if (typedArray.hasValue(6)) {
            this.mTickMarkTintList = typedArray.getColorStateList(6);
            this.mHasTickMarkTint = true;
        }
        this.mSplitTrack = typedArray.getBoolean(2, false);
        this.setThumbOffset(typedArray.getDimensionPixelOffset(1, this.getThumbOffset()));
        boolean bl = typedArray.getBoolean(8, true);
        typedArray.recycle();
        if (bl) {
            object = context.obtainStyledAttributes((AttributeSet)object, R.styleable.Theme, 0, 0);
            this.mDisabledAlpha = ((TypedArray)object).getFloat(3, 0.5f);
            ((TypedArray)object).recycle();
        } else {
            this.mDisabledAlpha = 1.0f;
        }
        this.applyThumbTint();
        this.applyTickMarkTint();
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private void applyThumbTint() {
        if (this.mThumb != null && (this.mHasThumbTint || this.mHasThumbBlendMode)) {
            this.mThumb = this.mThumb.mutate();
            if (this.mHasThumbTint) {
                this.mThumb.setTintList(this.mThumbTintList);
            }
            if (this.mHasThumbBlendMode) {
                this.mThumb.setTintBlendMode(this.mThumbBlendMode);
            }
            if (this.mThumb.isStateful()) {
                this.mThumb.setState(this.getDrawableState());
            }
        }
    }

    private void applyTickMarkTint() {
        if (this.mTickMark != null && (this.mHasTickMarkTint || this.mHasTickMarkBlendMode)) {
            this.mTickMark = this.mTickMark.mutate();
            if (this.mHasTickMarkTint) {
                this.mTickMark.setTintList(this.mTickMarkTintList);
            }
            if (this.mHasTickMarkBlendMode) {
                this.mTickMark.setTintBlendMode(this.mTickMarkBlendMode);
            }
            if (this.mTickMark.isStateful()) {
                this.mTickMark.setState(this.getDrawableState());
            }
        }
    }

    private void attemptClaimDrag() {
        if (this.mParent != null) {
            this.mParent.requestDisallowInterceptTouchEvent(true);
        }
    }

    private float getScale() {
        int n = this.getMin();
        int n2 = this.getMax() - n;
        float f = n2 > 0 ? (float)(this.getProgress() - n) / (float)n2 : 0.0f;
        return f;
    }

    private void setHotspot(float f, float f2) {
        Drawable drawable2 = this.getBackground();
        if (drawable2 != null) {
            drawable2.setHotspot(f, f2);
        }
    }

    private void setThumbPos(int n, Drawable drawable2, float f, int n2) {
        Object object;
        int n3 = this.mPaddingLeft;
        int n4 = this.mPaddingRight;
        int n5 = drawable2.getIntrinsicWidth();
        int n6 = drawable2.getIntrinsicHeight();
        n3 = n - n3 - n4 - n5 + this.mThumbOffset * 2;
        n4 = (int)((float)n3 * f + 0.5f);
        if (n2 == Integer.MIN_VALUE) {
            object = drawable2.getBounds();
            n2 = ((Rect)object).top;
            n = ((Rect)object).bottom;
        } else {
            n = n2;
            n6 = n2 + n6;
            n2 = n;
            n = n6;
        }
        if (this.isLayoutRtl() && this.mMirrorForRtl) {
            n4 = n3 - n4;
        }
        n3 = n4 + n5;
        object = this.getBackground();
        if (object != null) {
            n6 = this.mPaddingLeft - this.mThumbOffset;
            n5 = this.mPaddingTop;
            ((Drawable)object).setHotspotBounds(n4 + n6, n2 + n5, n3 + n6, n + n5);
        }
        drawable2.setBounds(n4, n2, n3, n);
        this.updateGestureExclusionRects();
    }

    private void startDrag(MotionEvent motionEvent) {
        this.setPressed(true);
        Drawable drawable2 = this.mThumb;
        if (drawable2 != null) {
            this.invalidate(drawable2.getBounds());
        }
        this.onStartTrackingTouch();
        this.trackTouchEvent(motionEvent);
        this.attemptClaimDrag();
    }

    @UnsupportedAppUsage
    private void trackTouchEvent(MotionEvent motionEvent) {
        float f;
        int n = Math.round(motionEvent.getX());
        int n2 = Math.round(motionEvent.getY());
        int n3 = this.getWidth();
        int n4 = n3 - this.mPaddingLeft - this.mPaddingRight;
        float f2 = 0.0f;
        if (this.isLayoutRtl() && this.mMirrorForRtl) {
            if (n > n3 - this.mPaddingRight) {
                f = 0.0f;
            } else if (n < this.mPaddingLeft) {
                f = 1.0f;
            } else {
                f = (float)(n4 - n + this.mPaddingLeft) / (float)n4;
                f2 = this.mTouchProgressOffset;
            }
        } else if (n < this.mPaddingLeft) {
            f = 0.0f;
        } else if (n > n3 - this.mPaddingRight) {
            f = 1.0f;
        } else {
            f = (float)(n - this.mPaddingLeft) / (float)n4;
            f2 = this.mTouchProgressOffset;
        }
        float f3 = this.getMax() - this.getMin();
        float f4 = this.getMin();
        this.setHotspot(n, n2);
        this.setProgressInternal(Math.round(f2 + (f3 * f + f4)), true, false);
    }

    private void updateGestureExclusionRects() {
        Drawable drawable2 = this.mThumb;
        if (drawable2 == null) {
            super.setSystemGestureExclusionRects(this.mUserGestureExclusionRects);
            return;
        }
        this.mGestureExclusionRects.clear();
        drawable2.copyBounds(this.mThumbRect);
        this.mGestureExclusionRects.add(this.mThumbRect);
        this.mGestureExclusionRects.addAll(this.mUserGestureExclusionRects);
        super.setSystemGestureExclusionRects(this.mGestureExclusionRects);
    }

    private void updateThumbAndTrackPos(int n, int n2) {
        int n3;
        int n4 = n2 - this.mPaddingTop - this.mPaddingBottom;
        Drawable drawable2 = this.getCurrentDrawable();
        Drawable drawable3 = this.mThumb;
        n2 = drawable3 == null ? 0 : drawable3.getIntrinsicHeight();
        if (n2 > (n3 = Math.min(this.mMaxHeight, n4))) {
            int n5 = (n4 - n2) / 2;
            n4 = (n2 - n3) / 2 + n5;
            n2 = n5;
        } else {
            int n6;
            n4 = n6 = (n4 - n3) / 2;
            n2 = n6 + (n3 - n2) / 2;
        }
        if (drawable2 != null) {
            drawable2.setBounds(0, n4, n - this.mPaddingRight - this.mPaddingLeft, n4 + n3);
        }
        if (drawable3 != null) {
            this.setThumbPos(n, drawable3, this.getScale(), n2);
        }
    }

    boolean canUserSetProgress() {
        boolean bl = !this.isIndeterminate() && this.isEnabled();
        return bl;
    }

    @UnsupportedAppUsage
    void drawThumb(Canvas canvas) {
        if (this.mThumb != null) {
            int n = canvas.save();
            canvas.translate(this.mPaddingLeft - this.mThumbOffset, this.mPaddingTop);
            this.mThumb.draw(canvas);
            canvas.restoreToCount(n);
        }
    }

    protected void drawTickMarks(Canvas canvas) {
        if (this.mTickMark != null) {
            int n = this.getMax() - this.getMin();
            int n2 = 1;
            if (n > 1) {
                int n3 = this.mTickMark.getIntrinsicWidth();
                int n4 = this.mTickMark.getIntrinsicHeight();
                n3 = n3 >= 0 ? (n3 /= 2) : 1;
                if (n4 >= 0) {
                    n2 = n4 / 2;
                }
                this.mTickMark.setBounds(-n3, -n2, n3, n2);
                float f = (float)(this.getWidth() - this.mPaddingLeft - this.mPaddingRight) / (float)n;
                n2 = canvas.save();
                canvas.translate(this.mPaddingLeft, this.getHeight() / 2);
                for (n3 = 0; n3 <= n; ++n3) {
                    this.mTickMark.draw(canvas);
                    canvas.translate(f, 0.0f);
                }
                canvas.restoreToCount(n2);
            }
        }
    }

    @Override
    void drawTrack(Canvas canvas) {
        Drawable drawable2 = this.mThumb;
        if (drawable2 != null && this.mSplitTrack) {
            Insets insets = drawable2.getOpticalInsets();
            Rect rect = this.mTempRect;
            drawable2.copyBounds(rect);
            rect.offset(this.mPaddingLeft - this.mThumbOffset, this.mPaddingTop);
            rect.left += insets.left;
            rect.right -= insets.right;
            int n = canvas.save();
            canvas.clipRect(rect, Region.Op.DIFFERENCE);
            super.drawTrack(canvas);
            this.drawTickMarks(canvas);
            canvas.restoreToCount(n);
        } else {
            super.drawTrack(canvas);
            this.drawTickMarks(canvas);
        }
    }

    @Override
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable2 = this.mThumb;
        if (drawable2 != null) {
            drawable2.setHotspot(f, f2);
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable2 = this.getProgressDrawable();
        if (drawable2 != null && this.mDisabledAlpha < 1.0f) {
            int n = this.isEnabled() ? 255 : (int)(this.mDisabledAlpha * 255.0f);
            drawable2.setAlpha(n);
        }
        if ((drawable2 = this.mThumb) != null && drawable2.isStateful() && drawable2.setState(this.getDrawableState())) {
            this.invalidateDrawable(drawable2);
        }
        if ((drawable2 = this.mTickMark) != null && drawable2.isStateful() && drawable2.setState(this.getDrawableState())) {
            this.invalidateDrawable(drawable2);
        }
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return AbsSeekBar.class.getName();
    }

    public int getKeyProgressIncrement() {
        return this.mKeyProgressIncrement;
    }

    public boolean getSplitTrack() {
        return this.mSplitTrack;
    }

    public Drawable getThumb() {
        return this.mThumb;
    }

    public int getThumbOffset() {
        return this.mThumbOffset;
    }

    public BlendMode getThumbTintBlendMode() {
        return this.mThumbBlendMode;
    }

    public ColorStateList getThumbTintList() {
        return this.mThumbTintList;
    }

    public PorterDuff.Mode getThumbTintMode() {
        Enum enum_ = this.mThumbBlendMode;
        enum_ = enum_ != null ? BlendMode.blendModeToPorterDuffMode((BlendMode)enum_) : null;
        return enum_;
    }

    public Drawable getTickMark() {
        return this.mTickMark;
    }

    public BlendMode getTickMarkTintBlendMode() {
        return this.mTickMarkBlendMode;
    }

    public ColorStateList getTickMarkTintList() {
        return this.mTickMarkTintList;
    }

    public PorterDuff.Mode getTickMarkTintMode() {
        Enum enum_ = this.mTickMarkBlendMode;
        enum_ = enum_ != null ? BlendMode.blendModeToPorterDuffMode((BlendMode)enum_) : null;
        return enum_;
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable2 = this.mThumb;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
        if ((drawable2 = this.mTickMark) != null) {
            drawable2.jumpToCurrentState();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        synchronized (this) {
            super.onDraw(canvas);
            this.drawThumb(canvas);
            return;
        }
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (this.isEnabled()) {
            int n = this.getProgress();
            if (n > this.getMin()) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
            }
            if (n < this.getMax()) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
            }
        }
    }

    void onKeyChange() {
    }

    @Override
    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        block2 : {
            int n2;
            block4 : {
                int n3;
                block3 : {
                    if (!this.isEnabled()) break block2;
                    n3 = this.mKeyProgressIncrement;
                    if (n == 21) break block3;
                    n2 = n3;
                    if (n == 22) break block4;
                    if (n == 69) break block3;
                    n2 = n3;
                    if (n == 70) break block4;
                    n2 = n3;
                    if (n == 81) break block4;
                    break block2;
                }
                n2 = -n3;
            }
            if (this.isLayoutRtl()) {
                n2 = -n2;
            }
            if (this.setProgressInternal(this.getProgress() + n2, true, true)) {
                this.onKeyChange();
                return true;
            }
        }
        return super.onKeyDown(n, keyEvent);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void onMeasure(int n, int n2) {
        synchronized (this) {
            Drawable drawable2 = this.getCurrentDrawable();
            int n3 = this.mThumb == null ? 0 : this.mThumb.getIntrinsicHeight();
            int n4 = 0;
            int n5 = 0;
            if (drawable2 != null) {
                n4 = Math.max(this.mMinWidth, Math.min(this.mMaxWidth, drawable2.getIntrinsicWidth()));
                n5 = Math.max(n3, Math.max(this.mMinHeight, Math.min(this.mMaxHeight, drawable2.getIntrinsicHeight())));
            }
            int n6 = this.mPaddingLeft;
            int n7 = this.mPaddingRight;
            int n8 = this.mPaddingTop;
            n3 = this.mPaddingBottom;
            this.setMeasuredDimension(AbsSeekBar.resolveSizeAndState(n4 + (n6 + n7), n, 0), AbsSeekBar.resolveSizeAndState(n5 + (n8 + n3), n2, 0));
            return;
        }
    }

    @Override
    public void onResolveDrawables(int n) {
        super.onResolveDrawables(n);
        Drawable drawable2 = this.mThumb;
        if (drawable2 != null) {
            drawable2.setLayoutDirection(n);
        }
    }

    @Override
    public void onRtlPropertiesChanged(int n) {
        super.onRtlPropertiesChanged(n);
        Drawable drawable2 = this.mThumb;
        if (drawable2 != null) {
            this.setThumbPos(this.getWidth(), drawable2, this.getScale(), Integer.MIN_VALUE);
            this.invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this.updateThumbAndTrackPos(n, n2);
    }

    void onStartTrackingTouch() {
        this.mIsDragging = true;
    }

    void onStopTrackingTouch() {
        this.mIsDragging = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mIsUserSeekable && this.isEnabled()) {
            int n = motionEvent.getAction();
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n == 3) {
                            if (this.mIsDragging) {
                                this.onStopTrackingTouch();
                                this.setPressed(false);
                            }
                            this.invalidate();
                        }
                    } else if (this.mIsDragging) {
                        this.trackTouchEvent(motionEvent);
                    } else if (Math.abs(motionEvent.getX() - this.mTouchDownX) > (float)this.mScaledTouchSlop) {
                        this.startDrag(motionEvent);
                    }
                } else {
                    if (this.mIsDragging) {
                        this.trackTouchEvent(motionEvent);
                        this.onStopTrackingTouch();
                        this.setPressed(false);
                    } else {
                        this.onStartTrackingTouch();
                        this.trackTouchEvent(motionEvent);
                        this.onStopTrackingTouch();
                    }
                    this.invalidate();
                }
            } else if (this.isInScrollingContainer()) {
                this.mTouchDownX = motionEvent.getX();
            } else {
                this.startDrag(motionEvent);
            }
            return true;
        }
        return false;
    }

    @Override
    void onVisualProgressChanged(int n, float f) {
        Drawable drawable2;
        super.onVisualProgressChanged(n, f);
        if (n == 16908301 && (drawable2 = this.mThumb) != null) {
            this.setThumbPos(this.getWidth(), drawable2, f, Integer.MIN_VALUE);
            this.invalidate();
        }
    }

    @Override
    public boolean performAccessibilityActionInternal(int n, Bundle bundle) {
        int n2;
        if (super.performAccessibilityActionInternal(n, bundle)) {
            return true;
        }
        if (!this.isEnabled()) {
            return false;
        }
        if (n != 4096 && n != 8192) {
            if (n != 16908349) {
                return false;
            }
            if (!this.canUserSetProgress()) {
                return false;
            }
            if (bundle != null && bundle.containsKey("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE")) {
                return this.setProgressInternal((int)bundle.getFloat("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE"), true, true);
            }
            return false;
        }
        if (!this.canUserSetProgress()) {
            return false;
        }
        int n3 = n2 = Math.max(1, Math.round((float)(this.getMax() - this.getMin()) / 20.0f));
        if (n == 8192) {
            n3 = -n2;
        }
        if (this.setProgressInternal(this.getProgress() + n3, true, true)) {
            this.onKeyChange();
            return true;
        }
        return false;
    }

    public void setKeyProgressIncrement(int n) {
        if (n < 0) {
            n = -n;
        }
        this.mKeyProgressIncrement = n;
    }

    @Override
    public void setMax(int n) {
        synchronized (this) {
            super.setMax(n);
            n = this.getMax() - this.getMin();
            if (this.mKeyProgressIncrement == 0 || n / this.mKeyProgressIncrement > 20) {
                this.setKeyProgressIncrement(Math.max(1, Math.round((float)n / 20.0f)));
            }
            return;
        }
    }

    @Override
    public void setMin(int n) {
        synchronized (this) {
            super.setMin(n);
            n = this.getMax() - this.getMin();
            if (this.mKeyProgressIncrement == 0 || n / this.mKeyProgressIncrement > 20) {
                this.setKeyProgressIncrement(Math.max(1, Math.round((float)n / 20.0f)));
            }
            return;
        }
    }

    public void setSplitTrack(boolean bl) {
        this.mSplitTrack = bl;
        this.invalidate();
    }

    @Override
    public void setSystemGestureExclusionRects(List<Rect> list) {
        Preconditions.checkNotNull(list, "rects must not be null");
        this.mUserGestureExclusionRects = list;
        this.updateGestureExclusionRects();
    }

    public void setThumb(Drawable drawable2) {
        boolean bl;
        Drawable drawable3 = this.mThumb;
        if (drawable3 != null && drawable2 != drawable3) {
            drawable3.setCallback(null);
            bl = true;
        } else {
            bl = false;
        }
        if (drawable2 != null) {
            drawable2.setCallback(this);
            if (this.canResolveLayoutDirection()) {
                drawable2.setLayoutDirection(this.getLayoutDirection());
            }
            this.mThumbOffset = drawable2.getIntrinsicWidth() / 2;
            if (bl && (drawable2.getIntrinsicWidth() != this.mThumb.getIntrinsicWidth() || drawable2.getIntrinsicHeight() != this.mThumb.getIntrinsicHeight())) {
                this.requestLayout();
            }
        }
        this.mThumb = drawable2;
        this.applyThumbTint();
        this.invalidate();
        if (bl) {
            this.updateThumbAndTrackPos(this.getWidth(), this.getHeight());
            if (drawable2 != null && drawable2.isStateful()) {
                drawable2.setState(this.getDrawableState());
            }
        }
    }

    public void setThumbOffset(int n) {
        this.mThumbOffset = n;
        this.invalidate();
    }

    public void setThumbTintBlendMode(BlendMode blendMode) {
        this.mThumbBlendMode = blendMode;
        this.mHasThumbBlendMode = true;
        this.applyThumbTint();
    }

    public void setThumbTintList(ColorStateList colorStateList) {
        this.mThumbTintList = colorStateList;
        this.mHasThumbTint = true;
        this.applyThumbTint();
    }

    public void setThumbTintMode(PorterDuff.Mode enum_) {
        enum_ = enum_ != null ? BlendMode.fromValue(((PorterDuff.Mode)enum_).nativeInt) : null;
        this.setThumbTintBlendMode((BlendMode)enum_);
    }

    public void setTickMark(Drawable drawable2) {
        Drawable drawable3 = this.mTickMark;
        if (drawable3 != null) {
            drawable3.setCallback(null);
        }
        this.mTickMark = drawable2;
        if (drawable2 != null) {
            drawable2.setCallback(this);
            drawable2.setLayoutDirection(this.getLayoutDirection());
            if (drawable2.isStateful()) {
                drawable2.setState(this.getDrawableState());
            }
            this.applyTickMarkTint();
        }
        this.invalidate();
    }

    public void setTickMarkTintBlendMode(BlendMode blendMode) {
        this.mTickMarkBlendMode = blendMode;
        this.mHasTickMarkBlendMode = true;
        this.applyTickMarkTint();
    }

    public void setTickMarkTintList(ColorStateList colorStateList) {
        this.mTickMarkTintList = colorStateList;
        this.mHasTickMarkTint = true;
        this.applyTickMarkTint();
    }

    public void setTickMarkTintMode(PorterDuff.Mode enum_) {
        enum_ = enum_ != null ? BlendMode.fromValue(((PorterDuff.Mode)enum_).nativeInt) : null;
        this.setTickMarkTintBlendMode((BlendMode)enum_);
    }

    @Override
    protected boolean verifyDrawable(Drawable drawable2) {
        boolean bl = drawable2 == this.mThumb || drawable2 == this.mTickMark || super.verifyDrawable(drawable2);
        return bl;
    }
}

