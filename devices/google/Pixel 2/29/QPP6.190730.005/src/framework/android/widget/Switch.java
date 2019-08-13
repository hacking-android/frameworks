/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.ColorStateList;
import android.content.res.CompatibilityInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Insets;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.AllCapsTransformationMethod;
import android.text.method.TransformationMethod2;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatProperty;
import android.util.MathUtils;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import com.android.internal.R;
import java.util.List;

public class Switch
extends CompoundButton {
    private static final int[] CHECKED_STATE_SET = new int[]{16842912};
    private static final int MONOSPACE = 3;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int THUMB_ANIMATION_DURATION = 250;
    private static final FloatProperty<Switch> THUMB_POS = new FloatProperty<Switch>("thumbPos"){

        @Override
        public Float get(Switch switch_) {
            return Float.valueOf(switch_.mThumbPosition);
        }

        @Override
        public void setValue(Switch switch_, float f) {
            switch_.setThumbPosition(f);
        }
    };
    private static final int TOUCH_MODE_DOWN = 1;
    private static final int TOUCH_MODE_DRAGGING = 2;
    private static final int TOUCH_MODE_IDLE = 0;
    private boolean mHasThumbTint = false;
    private boolean mHasThumbTintMode = false;
    private boolean mHasTrackTint = false;
    private boolean mHasTrackTintMode = false;
    private int mMinFlingVelocity;
    @UnsupportedAppUsage
    private Layout mOffLayout;
    @UnsupportedAppUsage
    private Layout mOnLayout;
    private ObjectAnimator mPositionAnimator;
    private boolean mShowText;
    private boolean mSplitTrack;
    private int mSwitchBottom;
    @UnsupportedAppUsage
    private int mSwitchHeight;
    private int mSwitchLeft;
    @UnsupportedAppUsage
    private int mSwitchMinWidth;
    private int mSwitchPadding;
    private int mSwitchRight;
    private int mSwitchTop;
    private TransformationMethod2 mSwitchTransformationMethod;
    @UnsupportedAppUsage
    private int mSwitchWidth;
    private final Rect mTempRect = new Rect();
    private ColorStateList mTextColors;
    private CharSequence mTextOff;
    private CharSequence mTextOn;
    private TextPaint mTextPaint = new TextPaint(1);
    private BlendMode mThumbBlendMode = null;
    @UnsupportedAppUsage
    private Drawable mThumbDrawable;
    private float mThumbPosition;
    private int mThumbTextPadding;
    private ColorStateList mThumbTintList = null;
    @UnsupportedAppUsage
    private int mThumbWidth;
    private int mTouchMode;
    private int mTouchSlop;
    private float mTouchX;
    private float mTouchY;
    private BlendMode mTrackBlendMode = null;
    @UnsupportedAppUsage
    private Drawable mTrackDrawable;
    private ColorStateList mTrackTintList = null;
    private boolean mUseFallbackLineSpacing;
    private VelocityTracker mVelocityTracker = VelocityTracker.obtain();

    public Switch(Context context) {
        this(context, null);
    }

    public Switch(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843839);
    }

    public Switch(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public Switch(Context object, AttributeSet object2, int n, int n2) {
        super((Context)object, (AttributeSet)object2, n, n2);
        Object object3 = this.getResources();
        this.mTextPaint.density = object3.getDisplayMetrics().density;
        this.mTextPaint.setCompatibilityScaling(object3.getCompatibilityInfo().applicationScale);
        object3 = ((Context)object).obtainStyledAttributes((AttributeSet)object2, R.styleable.Switch, n, n2);
        this.saveAttributeDataForStyleable((Context)object, R.styleable.Switch, (AttributeSet)object2, (TypedArray)object3, n, n2);
        this.mThumbDrawable = ((TypedArray)object3).getDrawable(2);
        object2 = this.mThumbDrawable;
        if (object2 != null) {
            ((Drawable)object2).setCallback(this);
        }
        this.mTrackDrawable = ((TypedArray)object3).getDrawable(4);
        object2 = this.mTrackDrawable;
        if (object2 != null) {
            ((Drawable)object2).setCallback(this);
        }
        this.mTextOn = ((TypedArray)object3).getText(0);
        this.mTextOff = ((TypedArray)object3).getText(1);
        this.mShowText = ((TypedArray)object3).getBoolean(11, true);
        this.mThumbTextPadding = ((TypedArray)object3).getDimensionPixelSize(7, 0);
        this.mSwitchMinWidth = ((TypedArray)object3).getDimensionPixelSize(5, 0);
        this.mSwitchPadding = ((TypedArray)object3).getDimensionPixelSize(6, 0);
        this.mSplitTrack = ((TypedArray)object3).getBoolean(8, false);
        boolean bl = object.getApplicationInfo().targetSdkVersion >= 28;
        this.mUseFallbackLineSpacing = bl;
        object2 = ((TypedArray)object3).getColorStateList(9);
        if (object2 != null) {
            this.mThumbTintList = object2;
            this.mHasThumbTint = true;
        }
        if (this.mThumbBlendMode != (object2 = Drawable.parseBlendMode(((TypedArray)object3).getInt(10, -1), null))) {
            this.mThumbBlendMode = object2;
            this.mHasThumbTintMode = true;
        }
        if (this.mHasThumbTint || this.mHasThumbTintMode) {
            this.applyThumbTint();
        }
        if ((object2 = ((TypedArray)object3).getColorStateList(12)) != null) {
            this.mTrackTintList = object2;
            this.mHasTrackTint = true;
        }
        if (this.mTrackBlendMode != (object2 = Drawable.parseBlendMode(((TypedArray)object3).getInt(13, -1), null))) {
            this.mTrackBlendMode = object2;
            this.mHasTrackTintMode = true;
        }
        if (this.mHasTrackTint || this.mHasTrackTintMode) {
            this.applyTrackTint();
        }
        if ((n = ((TypedArray)object3).getResourceId(3, 0)) != 0) {
            this.setSwitchTextAppearance((Context)object, n);
        }
        ((TypedArray)object3).recycle();
        object = ViewConfiguration.get((Context)object);
        this.mTouchSlop = ((ViewConfiguration)object).getScaledTouchSlop();
        this.mMinFlingVelocity = ((ViewConfiguration)object).getScaledMinimumFlingVelocity();
        this.refreshDrawableState();
        this.setChecked(this.isChecked());
    }

    private void animateThumbToCheckedState(boolean bl) {
        float f = bl ? 1.0f : 0.0f;
        this.mPositionAnimator = ObjectAnimator.ofFloat(this, THUMB_POS, f);
        this.mPositionAnimator.setDuration(250L);
        this.mPositionAnimator.setAutoCancel(true);
        this.mPositionAnimator.start();
    }

    private void applyThumbTint() {
        if (this.mThumbDrawable != null && (this.mHasThumbTint || this.mHasThumbTintMode)) {
            this.mThumbDrawable = this.mThumbDrawable.mutate();
            if (this.mHasThumbTint) {
                this.mThumbDrawable.setTintList(this.mThumbTintList);
            }
            if (this.mHasThumbTintMode) {
                this.mThumbDrawable.setTintBlendMode(this.mThumbBlendMode);
            }
            if (this.mThumbDrawable.isStateful()) {
                this.mThumbDrawable.setState(this.getDrawableState());
            }
        }
    }

    private void applyTrackTint() {
        if (this.mTrackDrawable != null && (this.mHasTrackTint || this.mHasTrackTintMode)) {
            this.mTrackDrawable = this.mTrackDrawable.mutate();
            if (this.mHasTrackTint) {
                this.mTrackDrawable.setTintList(this.mTrackTintList);
            }
            if (this.mHasTrackTintMode) {
                this.mTrackDrawable.setTintBlendMode(this.mTrackBlendMode);
            }
            if (this.mTrackDrawable.isStateful()) {
                this.mTrackDrawable.setState(this.getDrawableState());
            }
        }
    }

    @UnsupportedAppUsage
    private void cancelPositionAnimator() {
        ObjectAnimator objectAnimator = this.mPositionAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    private void cancelSuperTouch(MotionEvent motionEvent) {
        motionEvent = MotionEvent.obtain(motionEvent);
        motionEvent.setAction(3);
        super.onTouchEvent(motionEvent);
        motionEvent.recycle();
    }

    private boolean getTargetCheckedState() {
        boolean bl = this.mThumbPosition > 0.5f;
        return bl;
    }

    private int getThumbOffset() {
        float f = this.isLayoutRtl() ? 1.0f - this.mThumbPosition : this.mThumbPosition;
        return (int)((float)this.getThumbScrollRange() * f + 0.5f);
    }

    private int getThumbScrollRange() {
        Object object = this.mTrackDrawable;
        if (object != null) {
            Rect rect = this.mTempRect;
            ((Drawable)object).getPadding(rect);
            object = this.mThumbDrawable;
            object = object != null ? ((Drawable)object).getOpticalInsets() : Insets.NONE;
            return this.mSwitchWidth - this.mThumbWidth - rect.left - rect.right - ((Insets)object).left - ((Insets)object).right;
        }
        return 0;
    }

    private boolean hitThumb(float f, float f2) {
        Drawable drawable2 = this.mThumbDrawable;
        boolean bl = false;
        if (drawable2 == null) {
            return false;
        }
        int n = this.getThumbOffset();
        this.mThumbDrawable.getPadding(this.mTempRect);
        int n2 = this.mSwitchTop;
        int n3 = this.mTouchSlop;
        int n4 = this.mSwitchLeft + n - n3;
        n = this.mThumbWidth;
        int n5 = this.mTempRect.left;
        int n6 = this.mTempRect.right;
        int n7 = this.mTouchSlop;
        int n8 = this.mSwitchBottom;
        boolean bl2 = bl;
        if (f > (float)n4) {
            bl2 = bl;
            if (f < (float)(n + n4 + n5 + n6 + n7)) {
                bl2 = bl;
                if (f2 > (float)(n2 - n3)) {
                    bl2 = bl;
                    if (f2 < (float)(n8 + n7)) {
                        bl2 = true;
                    }
                }
            }
        }
        return bl2;
    }

    private Layout makeLayout(CharSequence charSequence) {
        TransformationMethod2 transformationMethod2 = this.mSwitchTransformationMethod;
        if (transformationMethod2 != null) {
            charSequence = transformationMethod2.getTransformation(charSequence, this);
        }
        int n = (int)Math.ceil(Layout.getDesiredWidth(charSequence, 0, charSequence.length(), this.mTextPaint, this.getTextDirectionHeuristic()));
        return StaticLayout.Builder.obtain(charSequence, 0, charSequence.length(), this.mTextPaint, n).setUseLineSpacingFromFallbacks(this.mUseFallbackLineSpacing).build();
    }

    private void setSwitchTypefaceByIndex(int n, int n2) {
        Typeface typeface = null;
        if (n != 1) {
            if (n != 2) {
                if (n == 3) {
                    typeface = Typeface.MONOSPACE;
                }
            } else {
                typeface = Typeface.SERIF;
            }
        } else {
            typeface = Typeface.SANS_SERIF;
        }
        this.setSwitchTypeface(typeface, n2);
    }

    @UnsupportedAppUsage
    private void setThumbPosition(float f) {
        this.mThumbPosition = f;
        this.invalidate();
    }

    private void stopDrag(MotionEvent motionEvent) {
        this.mTouchMode = 0;
        int n = motionEvent.getAction();
        boolean bl = true;
        n = n == 1 && this.isEnabled() ? 1 : 0;
        boolean bl2 = this.isChecked();
        if (n != 0) {
            this.mVelocityTracker.computeCurrentVelocity(1000);
            float f = this.mVelocityTracker.getXVelocity();
            if (Math.abs(f) > (float)this.mMinFlingVelocity) {
                if (!(this.isLayoutRtl() ? f < 0.0f : f > 0.0f)) {
                    bl = false;
                }
            } else {
                bl = this.getTargetCheckedState();
            }
        } else {
            bl = bl2;
        }
        if (bl != bl2) {
            this.playSoundEffect(0);
        }
        this.setChecked(bl);
        this.cancelSuperTouch(motionEvent);
    }

    @Override
    public void draw(Canvas canvas) {
        Rect rect = this.mTempRect;
        int n = this.mSwitchLeft;
        int n2 = this.mSwitchTop;
        int n3 = this.mSwitchRight;
        int n4 = this.mSwitchBottom;
        int n5 = this.getThumbOffset() + n;
        Object object = this.mThumbDrawable;
        object = object != null ? ((Drawable)object).getOpticalInsets() : Insets.NONE;
        Drawable drawable2 = this.mTrackDrawable;
        int n6 = n5;
        if (drawable2 != null) {
            drawable2.getPadding(rect);
            int n7 = n5 + rect.left;
            n5 = n2;
            int n8 = n4;
            int n9 = n;
            int n10 = n5;
            int n11 = n3;
            int n12 = n8;
            if (object != Insets.NONE) {
                n6 = n;
                if (((Insets)object).left > rect.left) {
                    n6 = n + (((Insets)object).left - rect.left);
                }
                n = n5;
                if (((Insets)object).top > rect.top) {
                    n = n5 + (((Insets)object).top - rect.top);
                }
                n5 = n3;
                if (((Insets)object).right > rect.right) {
                    n5 = n3 - (((Insets)object).right - rect.right);
                }
                n9 = n6;
                n10 = n;
                n11 = n5;
                n12 = n8;
                if (((Insets)object).bottom > rect.bottom) {
                    n12 = n8 - (((Insets)object).bottom - rect.bottom);
                    n11 = n5;
                    n10 = n;
                    n9 = n6;
                }
            }
            this.mTrackDrawable.setBounds(n9, n10, n11, n12);
            n6 = n7;
        }
        if ((object = this.mThumbDrawable) != null) {
            ((Drawable)object).getPadding(rect);
            n3 = n6 - rect.left;
            n6 = this.mThumbWidth + n6 + rect.right;
            this.mThumbDrawable.setBounds(n3, n2, n6, n4);
            object = this.getBackground();
            if (object != null) {
                ((Drawable)object).setHotspotBounds(n3, n2, n6, n4);
            }
        }
        super.draw(canvas);
    }

    @Override
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable2 = this.mThumbDrawable;
        if (drawable2 != null) {
            drawable2.setHotspot(f, f2);
        }
        if ((drawable2 = this.mTrackDrawable) != null) {
            drawable2.setHotspot(f, f2);
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] arrn = this.getDrawableState();
        boolean bl = false;
        Drawable drawable2 = this.mThumbDrawable;
        boolean bl2 = bl;
        if (drawable2 != null) {
            bl2 = bl;
            if (drawable2.isStateful()) {
                bl2 = false | drawable2.setState(arrn);
            }
        }
        drawable2 = this.mTrackDrawable;
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

    @Override
    public CharSequence getAccessibilityClassName() {
        return Switch.class.getName();
    }

    @Override
    public int getCompoundPaddingLeft() {
        int n;
        if (!this.isLayoutRtl()) {
            return super.getCompoundPaddingLeft();
        }
        int n2 = n = super.getCompoundPaddingLeft() + this.mSwitchWidth;
        if (!TextUtils.isEmpty(this.getText())) {
            n2 = n + this.mSwitchPadding;
        }
        return n2;
    }

    @Override
    public int getCompoundPaddingRight() {
        int n;
        if (this.isLayoutRtl()) {
            return super.getCompoundPaddingRight();
        }
        int n2 = n = super.getCompoundPaddingRight() + this.mSwitchWidth;
        if (!TextUtils.isEmpty(this.getText())) {
            n2 = n + this.mSwitchPadding;
        }
        return n2;
    }

    public boolean getShowText() {
        return this.mShowText;
    }

    public boolean getSplitTrack() {
        return this.mSplitTrack;
    }

    public int getSwitchMinWidth() {
        return this.mSwitchMinWidth;
    }

    public int getSwitchPadding() {
        return this.mSwitchPadding;
    }

    public CharSequence getTextOff() {
        return this.mTextOff;
    }

    public CharSequence getTextOn() {
        return this.mTextOn;
    }

    public Drawable getThumbDrawable() {
        return this.mThumbDrawable;
    }

    public int getThumbTextPadding() {
        return this.mThumbTextPadding;
    }

    public BlendMode getThumbTintBlendMode() {
        return this.mThumbBlendMode;
    }

    public ColorStateList getThumbTintList() {
        return this.mThumbTintList;
    }

    public PorterDuff.Mode getThumbTintMode() {
        Enum enum_ = this.getThumbTintBlendMode();
        enum_ = enum_ != null ? BlendMode.blendModeToPorterDuffMode((BlendMode)enum_) : null;
        return enum_;
    }

    public Drawable getTrackDrawable() {
        return this.mTrackDrawable;
    }

    public BlendMode getTrackTintBlendMode() {
        return this.mTrackBlendMode;
    }

    public ColorStateList getTrackTintList() {
        return this.mTrackTintList;
    }

    public PorterDuff.Mode getTrackTintMode() {
        Enum enum_ = this.getTrackTintBlendMode();
        enum_ = enum_ != null ? BlendMode.blendModeToPorterDuffMode((BlendMode)enum_) : null;
        return enum_;
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Object object = this.mThumbDrawable;
        if (object != null) {
            ((Drawable)object).jumpToCurrentState();
        }
        if ((object = this.mTrackDrawable) != null) {
            ((Drawable)object).jumpToCurrentState();
        }
        if ((object = this.mPositionAnimator) != null && ((ValueAnimator)object).isStarted()) {
            this.mPositionAnimator.end();
            this.mPositionAnimator = null;
        }
    }

    @Override
    protected int[] onCreateDrawableState(int n) {
        int[] arrn = super.onCreateDrawableState(n + 1);
        if (this.isChecked()) {
            Switch.mergeDrawableStates(arrn, CHECKED_STATE_SET);
        }
        return arrn;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int[] arrn;
        int n;
        super.onDraw(canvas);
        Object object = this.mTempRect;
        Object object2 = this.mTrackDrawable;
        if (object2 != null) {
            ((Drawable)object2).getPadding((Rect)object);
        } else {
            ((Rect)object).setEmpty();
        }
        int n2 = this.mSwitchTop;
        int n3 = this.mSwitchBottom;
        int n4 = ((Rect)object).top;
        int n5 = ((Rect)object).bottom;
        Object object3 = this.mThumbDrawable;
        if (object2 != null) {
            if (this.mSplitTrack && object3 != null) {
                arrn = ((Drawable)object3).getOpticalInsets();
                ((Drawable)object3).copyBounds((Rect)object);
                ((Rect)object).left += arrn.left;
                ((Rect)object).right -= arrn.right;
                n = canvas.save();
                canvas.clipRect((Rect)object, Region.Op.DIFFERENCE);
                ((Drawable)object2).draw(canvas);
                canvas.restoreToCount(n);
            } else {
                ((Drawable)object2).draw(canvas);
            }
        }
        int n6 = canvas.save();
        if (object3 != null) {
            ((Drawable)object3).draw(canvas);
        }
        if ((object = this.getTargetCheckedState() ? this.mOnLayout : this.mOffLayout) != null) {
            arrn = this.getDrawableState();
            object2 = this.mTextColors;
            if (object2 != null) {
                this.mTextPaint.setColor(((ColorStateList)object2).getColorForState(arrn, 0));
            }
            this.mTextPaint.drawableState = arrn;
            if (object3 != null) {
                object3 = ((Drawable)object3).getBounds();
                n = ((Rect)object3).left + ((Rect)object3).right;
            } else {
                n = this.getWidth();
            }
            int n7 = ((Layout)object).getWidth() / 2;
            n2 = (n4 + n2 + (n3 - n5)) / 2;
            n4 = ((Layout)object).getHeight() / 2;
            canvas.translate((n /= 2) - n7, n2 - n4);
            ((Layout)object).draw(canvas);
        }
        canvas.restoreToCount(n6);
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        CharSequence charSequence = this.isChecked() ? this.mTextOn : this.mTextOff;
        if (!TextUtils.isEmpty(charSequence)) {
            CharSequence charSequence2 = accessibilityNodeInfo.getText();
            if (TextUtils.isEmpty(charSequence2)) {
                accessibilityNodeInfo.setText(charSequence);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(charSequence2);
                stringBuilder.append(' ');
                stringBuilder.append(charSequence);
                accessibilityNodeInfo.setText(stringBuilder);
            }
        }
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        n = 0;
        n2 = 0;
        if (this.mThumbDrawable != null) {
            Rect rect = this.mTempRect;
            Object object = this.mTrackDrawable;
            if (object != null) {
                ((Drawable)object).getPadding(rect);
            } else {
                rect.setEmpty();
            }
            object = this.mThumbDrawable.getOpticalInsets();
            n = Math.max(0, ((Insets)object).left - rect.left);
            n2 = Math.max(0, ((Insets)object).right - rect.right);
        }
        if (this.isLayoutRtl()) {
            n3 = this.getPaddingLeft() + n;
            n4 = this.mSwitchWidth + n3 - n - n2;
        } else {
            n4 = this.getWidth() - this.getPaddingRight() - n2;
            n3 = n4 - this.mSwitchWidth + n + n2;
        }
        n = this.getGravity() & 112;
        if (n != 16) {
            if (n != 80) {
                n = this.getPaddingTop();
                n2 = this.mSwitchHeight + n;
            } else {
                n2 = this.getHeight() - this.getPaddingBottom();
                n = n2 - this.mSwitchHeight;
            }
        } else {
            n = (this.getPaddingTop() + this.getHeight() - this.getPaddingBottom()) / 2;
            n2 = this.mSwitchHeight;
            n -= n2 / 2;
            n2 += n;
        }
        this.mSwitchLeft = n3;
        this.mSwitchTop = n;
        this.mSwitchBottom = n2;
        this.mSwitchRight = n4;
    }

    @Override
    public void onMeasure(int n, int n2) {
        int n3;
        int n4;
        if (this.mShowText) {
            if (this.mOnLayout == null) {
                this.mOnLayout = this.makeLayout(this.mTextOn);
            }
            if (this.mOffLayout == null) {
                this.mOffLayout = this.makeLayout(this.mTextOff);
            }
        }
        Object object = this.mTempRect;
        Drawable drawable2 = this.mThumbDrawable;
        if (drawable2 != null) {
            drawable2.getPadding((Rect)object);
            n3 = this.mThumbDrawable.getIntrinsicWidth() - ((Rect)object).left - ((Rect)object).right;
            n4 = this.mThumbDrawable.getIntrinsicHeight();
        } else {
            n3 = 0;
            n4 = 0;
        }
        int n5 = this.mShowText ? Math.max(this.mOnLayout.getWidth(), this.mOffLayout.getWidth()) + this.mThumbTextPadding * 2 : 0;
        this.mThumbWidth = Math.max(n5, n3);
        drawable2 = this.mTrackDrawable;
        if (drawable2 != null) {
            drawable2.getPadding((Rect)object);
            n3 = this.mTrackDrawable.getIntrinsicHeight();
        } else {
            ((Rect)object).setEmpty();
            n3 = 0;
        }
        int n6 = ((Rect)object).left;
        int n7 = ((Rect)object).right;
        object = this.mThumbDrawable;
        int n8 = n6;
        n5 = n7;
        if (object != null) {
            object = ((Drawable)object).getOpticalInsets();
            n8 = Math.max(n6, ((Insets)object).left);
            n5 = Math.max(n7, ((Insets)object).right);
        }
        n5 = Math.max(this.mSwitchMinWidth, this.mThumbWidth * 2 + n8 + n5);
        n4 = Math.max(n3, n4);
        this.mSwitchWidth = n5;
        this.mSwitchHeight = n4;
        super.onMeasure(n, n2);
        if (this.getMeasuredHeight() < n4) {
            this.setMeasuredDimension(this.getMeasuredWidthAndState(), n4);
        }
    }

    @Override
    public void onPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEventInternal(accessibilityEvent);
        CharSequence charSequence = this.isChecked() ? this.mTextOn : this.mTextOff;
        if (charSequence != null) {
            accessibilityEvent.getText().add(charSequence);
        }
    }

    @Override
    protected void onProvideStructure(ViewStructure viewStructure, int n, int n2) {
        CharSequence charSequence = this.isChecked() ? this.mTextOn : this.mTextOff;
        if (!TextUtils.isEmpty(charSequence)) {
            CharSequence charSequence2 = viewStructure.getText();
            if (TextUtils.isEmpty(charSequence2)) {
                viewStructure.setText(charSequence);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(charSequence2);
                stringBuilder.append(' ');
                stringBuilder.append(charSequence);
                viewStructure.setText(stringBuilder);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        block12 : {
            block9 : {
                block10 : {
                    int n;
                    block11 : {
                        this.mVelocityTracker.addMovement(motionEvent);
                        n = motionEvent.getActionMasked();
                        if (n == 0) break block9;
                        if (n == 1) break block10;
                        if (n == 2) break block11;
                        if (n == 3) break block10;
                        break block12;
                    }
                    n = this.mTouchMode;
                    if (n != 0) {
                        if (n != 1) {
                            if (n == 2) {
                                float f = motionEvent.getX();
                                n = this.getThumbScrollRange();
                                float f2 = f - this.mTouchX;
                                f2 = n != 0 ? (f2 /= (float)n) : (f2 > 0.0f ? 1.0f : -1.0f);
                                float f3 = f2;
                                if (this.isLayoutRtl()) {
                                    f3 = -f2;
                                }
                                if ((f2 = MathUtils.constrain(this.mThumbPosition + f3, 0.0f, 1.0f)) != this.mThumbPosition) {
                                    this.mTouchX = f;
                                    this.setThumbPosition(f2);
                                }
                                return true;
                            }
                        } else {
                            float f = motionEvent.getX();
                            float f4 = motionEvent.getY();
                            if (Math.abs(f - this.mTouchX) > (float)this.mTouchSlop || Math.abs(f4 - this.mTouchY) > (float)this.mTouchSlop) {
                                this.mTouchMode = 2;
                                this.getParent().requestDisallowInterceptTouchEvent(true);
                                this.mTouchX = f;
                                this.mTouchY = f4;
                                return true;
                            }
                        }
                    }
                    break block12;
                }
                if (this.mTouchMode == 2) {
                    this.stopDrag(motionEvent);
                    super.onTouchEvent(motionEvent);
                    return true;
                }
                this.mTouchMode = 0;
                this.mVelocityTracker.clear();
                break block12;
            }
            float f = motionEvent.getX();
            float f5 = motionEvent.getY();
            if (this.isEnabled() && this.hitThumb(f, f5)) {
                this.mTouchMode = 1;
                this.mTouchX = f;
                this.mTouchY = f5;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override
    public void setChecked(boolean bl) {
        super.setChecked(bl);
        bl = this.isChecked();
        if (this.isAttachedToWindow() && this.isLaidOut()) {
            this.animateThumbToCheckedState(bl);
        } else {
            this.cancelPositionAnimator();
            float f = bl ? 1.0f : 0.0f;
            this.setThumbPosition(f);
        }
    }

    public void setShowText(boolean bl) {
        if (this.mShowText != bl) {
            this.mShowText = bl;
            this.requestLayout();
        }
    }

    public void setSplitTrack(boolean bl) {
        this.mSplitTrack = bl;
        this.invalidate();
    }

    public void setSwitchMinWidth(int n) {
        this.mSwitchMinWidth = n;
        this.requestLayout();
    }

    public void setSwitchPadding(int n) {
        this.mSwitchPadding = n;
        this.requestLayout();
    }

    public void setSwitchTextAppearance(Context object, int n) {
        TypedArray typedArray = ((Context)object).obtainStyledAttributes(n, R.styleable.TextAppearance);
        object = typedArray.getColorStateList(3);
        this.mTextColors = object != null ? object : this.getTextColors();
        n = typedArray.getDimensionPixelSize(0, 0);
        if (n != 0 && (float)n != this.mTextPaint.getTextSize()) {
            this.mTextPaint.setTextSize(n);
            this.requestLayout();
        }
        this.setSwitchTypefaceByIndex(typedArray.getInt(1, -1), typedArray.getInt(2, -1));
        if (typedArray.getBoolean(11, false)) {
            this.mSwitchTransformationMethod = new AllCapsTransformationMethod(this.getContext());
            this.mSwitchTransformationMethod.setLengthChangesAllowed(true);
        } else {
            this.mSwitchTransformationMethod = null;
        }
        typedArray.recycle();
    }

    public void setSwitchTypeface(Typeface typeface) {
        if (this.mTextPaint.getTypeface() != typeface) {
            this.mTextPaint.setTypeface(typeface);
            this.requestLayout();
            this.invalidate();
        }
    }

    public void setSwitchTypeface(Typeface object, int n) {
        float f = 0.0f;
        boolean bl = false;
        if (n > 0) {
            object = object == null ? Typeface.defaultFromStyle(n) : Typeface.create((Typeface)object, n);
            this.setSwitchTypeface((Typeface)object);
            int n2 = object != null ? ((Typeface)object).getStyle() : 0;
            n = n2 & n;
            object = this.mTextPaint;
            if ((n & 1) != 0) {
                bl = true;
            }
            ((Paint)object).setFakeBoldText(bl);
            object = this.mTextPaint;
            if ((n & 2) != 0) {
                f = -0.25f;
            }
            ((Paint)object).setTextSkewX(f);
        } else {
            this.mTextPaint.setFakeBoldText(false);
            this.mTextPaint.setTextSkewX(0.0f);
            this.setSwitchTypeface((Typeface)object);
        }
    }

    public void setTextOff(CharSequence charSequence) {
        this.mTextOff = charSequence;
        this.requestLayout();
    }

    public void setTextOn(CharSequence charSequence) {
        this.mTextOn = charSequence;
        this.requestLayout();
    }

    public void setThumbDrawable(Drawable drawable2) {
        Drawable drawable3 = this.mThumbDrawable;
        if (drawable3 != null) {
            drawable3.setCallback(null);
        }
        this.mThumbDrawable = drawable2;
        if (drawable2 != null) {
            drawable2.setCallback(this);
        }
        this.requestLayout();
    }

    public void setThumbResource(int n) {
        this.setThumbDrawable(this.getContext().getDrawable(n));
    }

    public void setThumbTextPadding(int n) {
        this.mThumbTextPadding = n;
        this.requestLayout();
    }

    public void setThumbTintBlendMode(BlendMode blendMode) {
        this.mThumbBlendMode = blendMode;
        this.mHasThumbTintMode = true;
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

    public void setTrackDrawable(Drawable drawable2) {
        Drawable drawable3 = this.mTrackDrawable;
        if (drawable3 != null) {
            drawable3.setCallback(null);
        }
        this.mTrackDrawable = drawable2;
        if (drawable2 != null) {
            drawable2.setCallback(this);
        }
        this.requestLayout();
    }

    public void setTrackResource(int n) {
        this.setTrackDrawable(this.getContext().getDrawable(n));
    }

    public void setTrackTintBlendMode(BlendMode blendMode) {
        this.mTrackBlendMode = blendMode;
        this.mHasTrackTintMode = true;
        this.applyTrackTint();
    }

    public void setTrackTintList(ColorStateList colorStateList) {
        this.mTrackTintList = colorStateList;
        this.mHasTrackTint = true;
        this.applyTrackTint();
    }

    public void setTrackTintMode(PorterDuff.Mode enum_) {
        enum_ = enum_ != null ? BlendMode.fromValue(((PorterDuff.Mode)enum_).nativeInt) : null;
        this.setTrackTintBlendMode((BlendMode)enum_);
    }

    @Override
    public void toggle() {
        this.setChecked(this.isChecked() ^ true);
    }

    @Override
    protected boolean verifyDrawable(Drawable drawable2) {
        boolean bl = super.verifyDrawable(drawable2) || drawable2 == this.mThumbDrawable || drawable2 == this.mTrackDrawable;
        return bl;
    }

}

