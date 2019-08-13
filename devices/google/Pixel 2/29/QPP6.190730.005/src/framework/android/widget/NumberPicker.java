/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.icu.LocaleData
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.CompatibilityInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import com.android.internal.R;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import libcore.icu.LocaleData;

public class NumberPicker
extends LinearLayout {
    private static final int DEFAULT_LAYOUT_RESOURCE_ID = 17367216;
    private static final long DEFAULT_LONG_PRESS_UPDATE_INTERVAL = 300L;
    private static final char[] DIGIT_CHARACTERS;
    private static final int SELECTOR_ADJUSTMENT_DURATION_MILLIS = 800;
    private static final int SELECTOR_MAX_FLING_VELOCITY_ADJUSTMENT = 8;
    @UnsupportedAppUsage
    private static final int SELECTOR_MIDDLE_ITEM_INDEX = 1;
    @UnsupportedAppUsage
    private static final int SELECTOR_WHEEL_ITEM_COUNT = 3;
    private static final int SIZE_UNSPECIFIED = -1;
    private static final int SNAP_SCROLL_DURATION = 300;
    private static final float TOP_AND_BOTTOM_FADING_EDGE_STRENGTH = 0.9f;
    private static final int UNSCALED_DEFAULT_SELECTION_DIVIDERS_DISTANCE = 48;
    private static final int UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT = 2;
    private static final TwoDigitFormatter sTwoDigitFormatter;
    private AccessibilityNodeProviderImpl mAccessibilityNodeProvider;
    private final Scroller mAdjustScroller;
    private BeginSoftInputOnLongPressCommand mBeginSoftInputOnLongPressCommand;
    private int mBottomSelectionDividerBottom;
    private ChangeCurrentByOneFromLongPressCommand mChangeCurrentByOneFromLongPressCommand;
    private final boolean mComputeMaxWidth;
    private int mCurrentScrollOffset;
    private final ImageButton mDecrementButton;
    private boolean mDecrementVirtualButtonPressed;
    private String[] mDisplayedValues;
    @UnsupportedAppUsage
    private final Scroller mFlingScroller;
    private Formatter mFormatter;
    private final boolean mHasSelectorWheel;
    private boolean mHideWheelUntilFocused;
    private boolean mIgnoreMoveEvents;
    private final ImageButton mIncrementButton;
    private boolean mIncrementVirtualButtonPressed;
    private int mInitialScrollOffset = Integer.MIN_VALUE;
    @UnsupportedAppUsage
    private final EditText mInputText;
    private long mLastDownEventTime;
    private float mLastDownEventY;
    private float mLastDownOrMoveEventY;
    private int mLastHandledDownDpadKeyCode = -1;
    private int mLastHoveredChildVirtualViewId;
    private long mLongPressUpdateInterval = 300L;
    private final int mMaxHeight;
    @UnsupportedAppUsage
    private int mMaxValue;
    private int mMaxWidth;
    @UnsupportedAppUsage
    private int mMaximumFlingVelocity;
    @UnsupportedAppUsage
    private final int mMinHeight;
    private int mMinValue;
    @UnsupportedAppUsage
    private final int mMinWidth;
    private int mMinimumFlingVelocity;
    private OnScrollListener mOnScrollListener;
    @UnsupportedAppUsage
    private OnValueChangeListener mOnValueChangeListener;
    private boolean mPerformClickOnTap;
    private final PressedStateHelper mPressedStateHelper;
    private int mPreviousScrollerY;
    private int mScrollState = 0;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private final Drawable mSelectionDivider;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mSelectionDividerHeight;
    private final int mSelectionDividersDistance;
    private int mSelectorElementHeight;
    private final SparseArray<String> mSelectorIndexToStringCache = new SparseArray();
    @UnsupportedAppUsage
    private final int[] mSelectorIndices = new int[3];
    private int mSelectorTextGapHeight;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private final Paint mSelectorWheelPaint;
    private SetSelectionCommand mSetSelectionCommand;
    private final int mSolidColor;
    @UnsupportedAppUsage
    private final int mTextSize;
    private int mTopSelectionDividerTop;
    private int mTouchSlop;
    private int mValue;
    private VelocityTracker mVelocityTracker;
    private final Drawable mVirtualButtonPressedDrawable;
    private boolean mWrapSelectorWheel;
    private boolean mWrapSelectorWheelPreferred = true;

    static {
        sTwoDigitFormatter = new TwoDigitFormatter();
        DIGIT_CHARACTERS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '\u0660', '\u0661', '\u0662', '\u0663', '\u0664', '\u0665', '\u0666', '\u0667', '\u0668', '\u0669', '\u06f0', '\u06f1', '\u06f2', '\u06f3', '\u06f4', '\u06f5', '\u06f6', '\u06f7', '\u06f8', '\u06f9', '\u0966', '\u0967', '\u0968', '\u0969', '\u096a', '\u096b', '\u096c', '\u096d', '\u096e', '\u096f', '\u09e6', '\u09e7', '\u09e8', '\u09e9', '\u09ea', '\u09eb', '\u09ec', '\u09ed', '\u09ee', '\u09ef', '\u0ce6', '\u0ce7', '\u0ce8', '\u0ce9', '\u0cea', '\u0ceb', '\u0cec', '\u0ced', '\u0cee', '\u0cef'};
    }

    public NumberPicker(Context context) {
        this(context, null);
    }

    public NumberPicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16844068);
    }

    public NumberPicker(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public NumberPicker(Context object, AttributeSet object2, int n, int n2) {
        super((Context)object, (AttributeSet)object2, n, n2);
        int n3;
        Object object3 = ((Context)object).obtainStyledAttributes((AttributeSet)object2, R.styleable.NumberPicker, n, n2);
        this.saveAttributeDataForStyleable((Context)object, R.styleable.NumberPicker, (AttributeSet)object2, (TypedArray)object3, n, n2);
        n = ((TypedArray)object3).getResourceId(3, 17367216);
        boolean bl = n != 17367216;
        this.mHasSelectorWheel = bl;
        this.mHideWheelUntilFocused = ((TypedArray)object3).getBoolean(2, false);
        this.mSolidColor = ((TypedArray)object3).getColor(0, 0);
        object2 = ((TypedArray)object3).getDrawable(8);
        if (object2 != null) {
            ((Drawable)object2).setCallback(this);
            ((Drawable)object2).setLayoutDirection(this.getLayoutDirection());
            if (((Drawable)object2).isStateful()) {
                ((Drawable)object2).setState(this.getDrawableState());
            }
        }
        this.mSelectionDivider = object2;
        this.mSelectionDividerHeight = ((TypedArray)object3).getDimensionPixelSize(1, (int)TypedValue.applyDimension(1, 2.0f, this.getResources().getDisplayMetrics()));
        this.mSelectionDividersDistance = ((TypedArray)object3).getDimensionPixelSize(9, (int)TypedValue.applyDimension(1, 48.0f, this.getResources().getDisplayMetrics()));
        this.mMinHeight = ((TypedArray)object3).getDimensionPixelSize(6, -1);
        this.mMaxHeight = ((TypedArray)object3).getDimensionPixelSize(4, -1);
        n2 = this.mMinHeight;
        if (n2 != -1 && (n3 = this.mMaxHeight) != -1 && n2 > n3) {
            throw new IllegalArgumentException("minHeight > maxHeight");
        }
        this.mMinWidth = ((TypedArray)object3).getDimensionPixelSize(7, -1);
        this.mMaxWidth = ((TypedArray)object3).getDimensionPixelSize(5, -1);
        n2 = this.mMinWidth;
        if (n2 != -1 && (n3 = this.mMaxWidth) != -1 && n2 > n3) {
            throw new IllegalArgumentException("minWidth > maxWidth");
        }
        bl = this.mMaxWidth == -1;
        this.mComputeMaxWidth = bl;
        this.mVirtualButtonPressedDrawable = ((TypedArray)object3).getDrawable(10);
        ((TypedArray)object3).recycle();
        this.mPressedStateHelper = new PressedStateHelper();
        this.setWillNotDraw(this.mHasSelectorWheel ^ true);
        ((LayoutInflater)this.getContext().getSystemService("layout_inflater")).inflate(n, (ViewGroup)this, true);
        object3 = new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                NumberPicker.this.hideSoftInput();
                NumberPicker.this.mInputText.clearFocus();
                if (view.getId() == 16909015) {
                    NumberPicker.this.changeValueByOne(true);
                } else {
                    NumberPicker.this.changeValueByOne(false);
                }
            }
        };
        object2 = new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View view) {
                NumberPicker.this.hideSoftInput();
                NumberPicker.this.mInputText.clearFocus();
                if (view.getId() == 16909015) {
                    NumberPicker.this.postChangeCurrentByOneFromLongPress(true, 0L);
                } else {
                    NumberPicker.this.postChangeCurrentByOneFromLongPress(false, 0L);
                }
                return true;
            }
        };
        if (!this.mHasSelectorWheel) {
            this.mIncrementButton = (ImageButton)this.findViewById(16909015);
            this.mIncrementButton.setOnClickListener((View.OnClickListener)object3);
            this.mIncrementButton.setOnLongClickListener((View.OnLongClickListener)object2);
        } else {
            this.mIncrementButton = null;
        }
        if (!this.mHasSelectorWheel) {
            this.mDecrementButton = (ImageButton)this.findViewById(16908870);
            this.mDecrementButton.setOnClickListener((View.OnClickListener)object3);
            this.mDecrementButton.setOnLongClickListener((View.OnLongClickListener)object2);
        } else {
            this.mDecrementButton = null;
        }
        this.mInputText = (EditText)this.findViewById(16909185);
        this.mInputText.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View view, boolean bl) {
                if (bl) {
                    NumberPicker.this.mInputText.selectAll();
                } else {
                    NumberPicker.this.mInputText.setSelection(0, 0);
                    NumberPicker.this.validateInputTextView(view);
                }
            }
        });
        this.mInputText.setFilters(new InputFilter[]{new InputTextFilter()});
        this.mInputText.setAccessibilityLiveRegion(1);
        this.mInputText.setRawInputType(2);
        this.mInputText.setImeOptions(6);
        object = ViewConfiguration.get((Context)object);
        this.mTouchSlop = ((ViewConfiguration)object).getScaledTouchSlop();
        this.mMinimumFlingVelocity = ((ViewConfiguration)object).getScaledMinimumFlingVelocity();
        this.mMaximumFlingVelocity = ((ViewConfiguration)object).getScaledMaximumFlingVelocity() / 8;
        this.mTextSize = (int)this.mInputText.getTextSize();
        object = new Paint();
        ((Paint)object).setAntiAlias(true);
        ((Paint)object).setTextAlign(Paint.Align.CENTER);
        ((Paint)object).setTextSize(this.mTextSize);
        ((Paint)object).setTypeface(this.mInputText.getTypeface());
        ((Paint)object).setColor(this.mInputText.getTextColors().getColorForState(ENABLED_STATE_SET, -1));
        this.mSelectorWheelPaint = object;
        this.mFlingScroller = new Scroller(this.getContext(), null, true);
        this.mAdjustScroller = new Scroller(this.getContext(), new DecelerateInterpolator(2.5f));
        this.updateInputTextView();
        if (this.getImportantForAccessibility() == 0) {
            this.setImportantForAccessibility(1);
        }
        if (this.getFocusable() == 16) {
            this.setFocusable(1);
            this.setFocusableInTouchMode(true);
        }
    }

    static /* synthetic */ boolean access$1280(NumberPicker numberPicker, int n) {
        boolean bl;
        numberPicker.mIncrementVirtualButtonPressed = bl = (byte)(numberPicker.mIncrementVirtualButtonPressed ^ n);
        return bl;
    }

    static /* synthetic */ boolean access$1680(NumberPicker numberPicker, int n) {
        boolean bl;
        numberPicker.mDecrementVirtualButtonPressed = bl = (byte)(numberPicker.mDecrementVirtualButtonPressed ^ n);
        return bl;
    }

    @UnsupportedAppUsage
    private void changeValueByOne(boolean bl) {
        if (this.mHasSelectorWheel) {
            this.hideSoftInput();
            if (!this.moveToFinalScrollerPosition(this.mFlingScroller)) {
                this.moveToFinalScrollerPosition(this.mAdjustScroller);
            }
            this.mPreviousScrollerY = 0;
            if (bl) {
                this.mFlingScroller.startScroll(0, 0, 0, -this.mSelectorElementHeight, 300);
            } else {
                this.mFlingScroller.startScroll(0, 0, 0, this.mSelectorElementHeight, 300);
            }
            this.invalidate();
        } else if (bl) {
            this.setValueInternal(this.mValue + 1, true);
        } else {
            this.setValueInternal(this.mValue - 1, true);
        }
    }

    private void decrementSelectorIndices(int[] arrn) {
        int n;
        int n2;
        for (n = arrn.length - 1; n > 0; --n) {
            arrn[n] = arrn[n - 1];
        }
        n = n2 = arrn[1] - 1;
        if (this.mWrapSelectorWheel) {
            n = n2;
            if (n2 < this.mMinValue) {
                n = this.mMaxValue;
            }
        }
        arrn[0] = n;
        this.ensureCachedScrollSelectorValue(n);
    }

    private void ensureCachedScrollSelectorValue(int n) {
        Object object;
        SparseArray<String> sparseArray = this.mSelectorIndexToStringCache;
        if (sparseArray.get(n) != null) {
            return;
        }
        int n2 = this.mMinValue;
        object = n >= n2 && n <= this.mMaxValue ? ((object = this.mDisplayedValues) != null ? object[n - n2] : this.formatNumber(n)) : "";
        sparseArray.put(n, (String)object);
    }

    private boolean ensureScrollWheelAdjusted() {
        int n = this.mInitialScrollOffset - this.mCurrentScrollOffset;
        if (n != 0) {
            this.mPreviousScrollerY = 0;
            int n2 = Math.abs(n);
            int n3 = this.mSelectorElementHeight;
            int n4 = n;
            if (n2 > n3 / 2) {
                n4 = n3;
                if (n > 0) {
                    n4 = -n3;
                }
                n4 = n + n4;
            }
            this.mAdjustScroller.startScroll(0, 0, 0, n4, 800);
            this.invalidate();
            return true;
        }
        return false;
    }

    private void fling(int n) {
        this.mPreviousScrollerY = 0;
        if (n > 0) {
            this.mFlingScroller.fling(0, 0, 0, n, 0, 0, 0, Integer.MAX_VALUE);
        } else {
            this.mFlingScroller.fling(0, Integer.MAX_VALUE, 0, n, 0, 0, 0, Integer.MAX_VALUE);
        }
        this.invalidate();
    }

    private String formatNumber(int n) {
        Object object = this.mFormatter;
        object = object != null ? object.format(n) : NumberPicker.formatNumberWithLocale(n);
        return object;
    }

    private static String formatNumberWithLocale(int n) {
        return String.format(Locale.getDefault(), "%d", n);
    }

    private int getSelectedPos(String string2) {
        if (this.mDisplayedValues == null) {
            try {
                int n = Integer.parseInt(string2);
                return n;
            }
            catch (NumberFormatException numberFormatException) {}
        } else {
            int n;
            for (n = 0; n < this.mDisplayedValues.length; ++n) {
                string2 = string2.toLowerCase();
                if (!this.mDisplayedValues[n].toLowerCase().startsWith(string2)) continue;
                return this.mMinValue + n;
            }
            try {
                n = Integer.parseInt(string2);
                return n;
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return this.mMinValue;
    }

    @UnsupportedAppUsage
    public static final Formatter getTwoDigitFormatter() {
        return sTwoDigitFormatter;
    }

    private int getWrappedSelectorIndex(int n) {
        int n2 = this.mMaxValue;
        if (n > n2) {
            int n3 = this.mMinValue;
            return n3 + (n - n2) % (n2 - n3) - 1;
        }
        int n4 = this.mMinValue;
        if (n < n4) {
            return n2 - (n4 - n) % (n2 - n4) + 1;
        }
        return n;
    }

    private void hideSoftInput() {
        InputMethodManager inputMethodManager = this.getContext().getSystemService(InputMethodManager.class);
        if (inputMethodManager != null && inputMethodManager.isActive(this.mInputText)) {
            inputMethodManager.hideSoftInputFromWindow(this.getWindowToken(), 0);
        }
        if (this.mHasSelectorWheel) {
            this.mInputText.setVisibility(4);
        }
    }

    private void incrementSelectorIndices(int[] arrn) {
        int n;
        int n2;
        for (n = 0; n < arrn.length - 1; ++n) {
            arrn[n] = arrn[n + 1];
        }
        n = n2 = arrn[arrn.length - 2] + 1;
        if (this.mWrapSelectorWheel) {
            n = n2;
            if (n2 > this.mMaxValue) {
                n = this.mMinValue;
            }
        }
        arrn[arrn.length - 1] = n;
        this.ensureCachedScrollSelectorValue(n);
    }

    private void initializeFadingEdges() {
        this.setVerticalFadingEdgeEnabled(true);
        this.setFadingEdgeLength((this.mBottom - this.mTop - this.mTextSize) / 2);
    }

    private void initializeSelectorWheel() {
        this.initializeSelectorWheelIndices();
        int[] arrn = this.mSelectorIndices;
        int n = arrn.length;
        int n2 = this.mTextSize;
        this.mSelectorTextGapHeight = (int)((float)(this.mBottom - this.mTop - n * n2) / (float)arrn.length + 0.5f);
        this.mSelectorElementHeight = this.mTextSize + this.mSelectorTextGapHeight;
        this.mCurrentScrollOffset = this.mInitialScrollOffset = this.mInputText.getBaseline() + this.mInputText.getTop() - this.mSelectorElementHeight * 1;
        this.updateInputTextView();
    }

    @UnsupportedAppUsage
    private void initializeSelectorWheelIndices() {
        this.mSelectorIndexToStringCache.clear();
        int[] arrn = this.mSelectorIndices;
        int n = this.getValue();
        for (int i = 0; i < this.mSelectorIndices.length; ++i) {
            int n2;
            int n3 = n2 = i - 1 + n;
            if (this.mWrapSelectorWheel) {
                n3 = this.getWrappedSelectorIndex(n2);
            }
            arrn[i] = n3;
            this.ensureCachedScrollSelectorValue(arrn[i]);
        }
    }

    private int makeMeasureSpec(int n, int n2) {
        if (n2 == -1) {
            return n;
        }
        int n3 = View.MeasureSpec.getSize(n);
        int n4 = View.MeasureSpec.getMode(n);
        if (n4 != Integer.MIN_VALUE) {
            if (n4 != 0) {
                if (n4 == 1073741824) {
                    return n;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown measure mode: ");
                stringBuilder.append(n4);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            return View.MeasureSpec.makeMeasureSpec(n2, 1073741824);
        }
        return View.MeasureSpec.makeMeasureSpec(Math.min(n3, n2), 1073741824);
    }

    private boolean moveToFinalScrollerPosition(Scroller scroller) {
        scroller.forceFinished(true);
        int n = scroller.getFinalY() - scroller.getCurrY();
        int n2 = this.mCurrentScrollOffset;
        int n3 = this.mSelectorElementHeight;
        n3 = this.mInitialScrollOffset - (n2 + n) % n3;
        if (n3 != 0) {
            int n4 = Math.abs(n3);
            int n5 = this.mSelectorElementHeight;
            n2 = n3;
            if (n4 > n5 / 2) {
                n2 = n3 > 0 ? n3 - n5 : n3 + n5;
            }
            this.scrollBy(0, n + n2);
            return true;
        }
        return false;
    }

    private void notifyChange(int n, int n2) {
        OnValueChangeListener onValueChangeListener = this.mOnValueChangeListener;
        if (onValueChangeListener != null) {
            onValueChangeListener.onValueChange(this, n, this.mValue);
        }
    }

    private void onScrollStateChange(int n) {
        if (this.mScrollState == n) {
            return;
        }
        this.mScrollState = n;
        OnScrollListener onScrollListener = this.mOnScrollListener;
        if (onScrollListener != null) {
            onScrollListener.onScrollStateChange(this, n);
        }
    }

    private void onScrollerFinished(Scroller scroller) {
        if (scroller == this.mFlingScroller) {
            this.ensureScrollWheelAdjusted();
            this.updateInputTextView();
            this.onScrollStateChange(0);
        } else if (this.mScrollState != 1) {
            this.updateInputTextView();
        }
    }

    private void postBeginSoftInputOnLongPressCommand() {
        BeginSoftInputOnLongPressCommand beginSoftInputOnLongPressCommand = this.mBeginSoftInputOnLongPressCommand;
        if (beginSoftInputOnLongPressCommand == null) {
            this.mBeginSoftInputOnLongPressCommand = new BeginSoftInputOnLongPressCommand();
        } else {
            this.removeCallbacks(beginSoftInputOnLongPressCommand);
        }
        this.postDelayed(this.mBeginSoftInputOnLongPressCommand, ViewConfiguration.getLongPressTimeout());
    }

    private void postChangeCurrentByOneFromLongPress(boolean bl, long l) {
        ChangeCurrentByOneFromLongPressCommand changeCurrentByOneFromLongPressCommand = this.mChangeCurrentByOneFromLongPressCommand;
        if (changeCurrentByOneFromLongPressCommand == null) {
            this.mChangeCurrentByOneFromLongPressCommand = new ChangeCurrentByOneFromLongPressCommand();
        } else {
            this.removeCallbacks(changeCurrentByOneFromLongPressCommand);
        }
        this.mChangeCurrentByOneFromLongPressCommand.setStep(bl);
        this.postDelayed(this.mChangeCurrentByOneFromLongPressCommand, l);
    }

    private void postSetSelectionCommand(int n, int n2) {
        if (this.mSetSelectionCommand == null) {
            this.mSetSelectionCommand = new SetSelectionCommand(this.mInputText);
        }
        this.mSetSelectionCommand.post(n, n2);
    }

    private void removeAllCallbacks() {
        Runnable runnable = this.mChangeCurrentByOneFromLongPressCommand;
        if (runnable != null) {
            this.removeCallbacks(runnable);
        }
        if ((runnable = this.mSetSelectionCommand) != null) {
            ((SetSelectionCommand)runnable).cancel();
        }
        if ((runnable = this.mBeginSoftInputOnLongPressCommand) != null) {
            this.removeCallbacks(runnable);
        }
        this.mPressedStateHelper.cancel();
    }

    private void removeBeginSoftInputCommand() {
        BeginSoftInputOnLongPressCommand beginSoftInputOnLongPressCommand = this.mBeginSoftInputOnLongPressCommand;
        if (beginSoftInputOnLongPressCommand != null) {
            this.removeCallbacks(beginSoftInputOnLongPressCommand);
        }
    }

    private void removeChangeCurrentByOneFromLongPress() {
        ChangeCurrentByOneFromLongPressCommand changeCurrentByOneFromLongPressCommand = this.mChangeCurrentByOneFromLongPressCommand;
        if (changeCurrentByOneFromLongPressCommand != null) {
            this.removeCallbacks(changeCurrentByOneFromLongPressCommand);
        }
    }

    private int resolveSizeAndStateRespectingMinSize(int n, int n2, int n3) {
        if (n != -1) {
            return NumberPicker.resolveSizeAndState(Math.max(n, n2), n3, 0);
        }
        return n2;
    }

    private void setValueInternal(int n, boolean bl) {
        if (this.mValue == n) {
            return;
        }
        n = this.mWrapSelectorWheel ? this.getWrappedSelectorIndex(n) : Math.min(Math.max(n, this.mMinValue), this.mMaxValue);
        int n2 = this.mValue;
        this.mValue = n;
        if (this.mScrollState != 2) {
            this.updateInputTextView();
        }
        if (bl) {
            this.notifyChange(n2, n);
        }
        this.initializeSelectorWheelIndices();
        this.invalidate();
    }

    private void showSoftInput() {
        InputMethodManager inputMethodManager = this.getContext().getSystemService(InputMethodManager.class);
        if (inputMethodManager != null) {
            if (this.mHasSelectorWheel) {
                this.mInputText.setVisibility(0);
            }
            this.mInputText.requestFocus();
            inputMethodManager.showSoftInput(this.mInputText, 0);
        }
    }

    private void tryComputeMaxWidth() {
        int n;
        int n2;
        if (!this.mComputeMaxWidth) {
            return;
        }
        int n3 = 0;
        String[] arrstring = this.mDisplayedValues;
        if (arrstring == null) {
            float f = 0.0f;
            for (n3 = 0; n3 <= 9; ++n3) {
                float f2 = this.mSelectorWheelPaint.measureText(NumberPicker.formatNumberWithLocale(n3));
                float f3 = f;
                if (f2 > f) {
                    f3 = f2;
                }
                f = f3;
            }
            n2 = 0;
            for (n3 = this.mMaxValue; n3 > 0; n3 /= 10) {
                ++n2;
            }
            n = (int)((float)n2 * f);
        } else {
            int n4 = arrstring.length;
            n2 = 0;
            do {
                n = n3;
                if (n2 >= n4) break;
                float f = this.mSelectorWheelPaint.measureText(this.mDisplayedValues[n2]);
                n = n3;
                if (f > (float)n3) {
                    n = (int)f;
                }
                ++n2;
                n3 = n;
            } while (true);
        }
        n2 = n + (this.mInputText.getPaddingLeft() + this.mInputText.getPaddingRight());
        if (this.mMaxWidth != n2) {
            n3 = this.mMinWidth;
            this.mMaxWidth = n2 > n3 ? n2 : n3;
            this.invalidate();
        }
    }

    private boolean updateInputTextView() {
        Editable editable;
        Object object = this.mDisplayedValues;
        object = object == null ? this.formatNumber(this.mValue) : object[this.mValue - this.mMinValue];
        if (!TextUtils.isEmpty((CharSequence)object) && !((String)object).equals((editable = this.mInputText.getText()).toString())) {
            this.mInputText.setText((CharSequence)object);
            if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
                AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain(16);
                this.mInputText.onInitializeAccessibilityEvent(accessibilityEvent);
                this.mInputText.onPopulateAccessibilityEvent(accessibilityEvent);
                accessibilityEvent.setFromIndex(0);
                accessibilityEvent.setRemovedCount(editable.length());
                accessibilityEvent.setAddedCount(((String)object).length());
                accessibilityEvent.setBeforeText(editable);
                accessibilityEvent.setSource(this, 2);
                this.requestSendAccessibilityEvent(this, accessibilityEvent);
            }
            return true;
        }
        return false;
    }

    private void updateWrapSelectorWheel() {
        int n = this.mMaxValue;
        int n2 = this.mMinValue;
        int n3 = this.mSelectorIndices.length;
        boolean bl = true;
        if ((n3 = n - n2 >= n3 ? 1 : 0) == 0 || !this.mWrapSelectorWheelPreferred) {
            bl = false;
        }
        this.mWrapSelectorWheel = bl;
    }

    private void validateInputTextView(View object) {
        if (TextUtils.isEmpty((CharSequence)(object = String.valueOf(((TextView)object).getText())))) {
            this.updateInputTextView();
        } else {
            this.setValueInternal(this.getSelectedPos(((String)object).toString()), true);
        }
    }

    @Override
    public void computeScroll() {
        Scroller scroller;
        Scroller scroller2 = scroller = this.mFlingScroller;
        if (scroller.isFinished()) {
            scroller2 = scroller = this.mAdjustScroller;
            if (scroller.isFinished()) {
                return;
            }
        }
        scroller2.computeScrollOffset();
        int n = scroller2.getCurrY();
        if (this.mPreviousScrollerY == 0) {
            this.mPreviousScrollerY = scroller2.getStartY();
        }
        this.scrollBy(0, n - this.mPreviousScrollerY);
        this.mPreviousScrollerY = n;
        if (scroller2.isFinished()) {
            this.onScrollerFinished(scroller2);
        } else {
            this.invalidate();
        }
    }

    @Override
    protected int computeVerticalScrollExtent() {
        return this.getHeight();
    }

    @Override
    protected int computeVerticalScrollOffset() {
        return this.mCurrentScrollOffset;
    }

    @Override
    protected int computeVerticalScrollRange() {
        return (this.mMaxValue - this.mMinValue + 1) * this.mSelectorElementHeight;
    }

    @Override
    protected boolean dispatchHoverEvent(MotionEvent object) {
        if (!this.mHasSelectorWheel) {
            return super.dispatchHoverEvent((MotionEvent)object);
        }
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            int n = (int)((MotionEvent)object).getY();
            n = n < this.mTopSelectionDividerTop ? 3 : (n > this.mBottomSelectionDividerBottom ? 1 : 2);
            int n2 = ((MotionEvent)object).getActionMasked();
            object = (AccessibilityNodeProviderImpl)this.getAccessibilityNodeProvider();
            if (n2 != 7) {
                if (n2 != 9) {
                    if (n2 == 10) {
                        ((AccessibilityNodeProviderImpl)object).sendAccessibilityEventForVirtualView(n, 256);
                        this.mLastHoveredChildVirtualViewId = -1;
                    }
                } else {
                    ((AccessibilityNodeProviderImpl)object).sendAccessibilityEventForVirtualView(n, 128);
                    this.mLastHoveredChildVirtualViewId = n;
                    ((AccessibilityNodeProviderImpl)object).performAction(n, 64, null);
                }
            } else {
                n2 = this.mLastHoveredChildVirtualViewId;
                if (n2 != n && n2 != -1) {
                    ((AccessibilityNodeProviderImpl)object).sendAccessibilityEventForVirtualView(n2, 256);
                    ((AccessibilityNodeProviderImpl)object).sendAccessibilityEventForVirtualView(n, 128);
                    this.mLastHoveredChildVirtualViewId = n;
                    ((AccessibilityNodeProviderImpl)object).performAction(n, 64, null);
                }
            }
        }
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int n;
        block10 : {
            block8 : {
                block9 : {
                    block7 : {
                        n = keyEvent.getKeyCode();
                        if (n == 19 || n == 20) break block7;
                        if (n == 23 || n == 66) {
                            this.removeAllCallbacks();
                        }
                        break block8;
                    }
                    if (!this.mHasSelectorWheel) break block8;
                    int n2 = keyEvent.getAction();
                    if (n2 == 0) break block9;
                    if (n2 == 1 && this.mLastHandledDownDpadKeyCode == n) {
                        this.mLastHandledDownDpadKeyCode = -1;
                        return true;
                    }
                    break block8;
                }
                if (this.mWrapSelectorWheel || (n == 20 ? this.getValue() < this.getMaxValue() : this.getValue() > this.getMinValue())) break block10;
            }
            return super.dispatchKeyEvent(keyEvent);
        }
        this.requestFocus();
        this.mLastHandledDownDpadKeyCode = n;
        this.removeAllCallbacks();
        if (this.mFlingScroller.isFinished()) {
            boolean bl = n == 20;
            this.changeValueByOne(bl);
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        if (n == 1 || n == 3) {
            this.removeAllCallbacks();
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        if (n == 1 || n == 3) {
            this.removeAllCallbacks();
        }
        return super.dispatchTrackballEvent(motionEvent);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable2 = this.mSelectionDivider;
        if (drawable2 != null && drawable2.isStateful() && drawable2.setState(this.getDrawableState())) {
            this.invalidateDrawable(drawable2);
        }
    }

    @Override
    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        if (!this.mHasSelectorWheel) {
            return super.getAccessibilityNodeProvider();
        }
        if (this.mAccessibilityNodeProvider == null) {
            this.mAccessibilityNodeProvider = new AccessibilityNodeProviderImpl();
        }
        return this.mAccessibilityNodeProvider;
    }

    @Override
    protected float getBottomFadingEdgeStrength() {
        return 0.9f;
    }

    public CharSequence getDisplayedValueForCurrentSelection() {
        return this.mSelectorIndexToStringCache.get(this.getValue());
    }

    public String[] getDisplayedValues() {
        return this.mDisplayedValues;
    }

    public int getMaxValue() {
        return this.mMaxValue;
    }

    public int getMinValue() {
        return this.mMinValue;
    }

    public int getSelectionDividerHeight() {
        return this.mSelectionDividerHeight;
    }

    @Override
    public int getSolidColor() {
        return this.mSolidColor;
    }

    public int getTextColor() {
        return this.mSelectorWheelPaint.getColor();
    }

    public float getTextSize() {
        return this.mSelectorWheelPaint.getTextSize();
    }

    @Override
    protected float getTopFadingEdgeStrength() {
        return 0.9f;
    }

    public int getValue() {
        return this.mValue;
    }

    public boolean getWrapSelectorWheel() {
        return this.mWrapSelectorWheel;
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable2 = this.mSelectionDivider;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.removeAllCallbacks();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int n;
        int n2;
        Object object;
        if (!this.mHasSelectorWheel) {
            super.onDraw(canvas);
            return;
        }
        boolean bl = this.mHideWheelUntilFocused ? this.hasFocus() : true;
        float f = (this.mRight - this.mLeft) / 2;
        float f2 = this.mCurrentScrollOffset;
        if (bl && (object = this.mVirtualButtonPressedDrawable) != null && this.mScrollState == 0) {
            if (this.mDecrementVirtualButtonPressed) {
                ((Drawable)object).setState(PRESSED_STATE_SET);
                this.mVirtualButtonPressedDrawable.setBounds(0, 0, this.mRight, this.mTopSelectionDividerTop);
                this.mVirtualButtonPressedDrawable.draw(canvas);
            }
            if (this.mIncrementVirtualButtonPressed) {
                this.mVirtualButtonPressedDrawable.setState(PRESSED_STATE_SET);
                this.mVirtualButtonPressedDrawable.setBounds(0, this.mBottomSelectionDividerBottom, this.mRight, this.mBottom);
                this.mVirtualButtonPressedDrawable.draw(canvas);
            }
        }
        object = this.mSelectorIndices;
        for (n2 = 0; n2 < ((int[])object).length; ++n2) {
            n = object[n2];
            String string2 = this.mSelectorIndexToStringCache.get(n);
            if (bl && n2 != 1 || n2 == 1 && this.mInputText.getVisibility() != 0) {
                canvas.drawText(string2, f, f2, this.mSelectorWheelPaint);
            }
            f2 += (float)this.mSelectorElementHeight;
        }
        if (bl && (object = this.mSelectionDivider) != null) {
            n2 = this.mTopSelectionDividerTop;
            n = this.mSelectionDividerHeight;
            ((Drawable)object).setBounds(0, n2, this.mRight, n + n2);
            this.mSelectionDivider.draw(canvas);
            n2 = this.mBottomSelectionDividerBottom;
            n = this.mSelectionDividerHeight;
            this.mSelectionDivider.setBounds(0, n2 - n, this.mRight, n2);
            this.mSelectionDivider.draw(canvas);
        }
    }

    @Override
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setClassName(NumberPicker.class.getName());
        accessibilityEvent.setScrollable(true);
        accessibilityEvent.setScrollY((this.mMinValue + this.mValue) * this.mSelectorElementHeight);
        accessibilityEvent.setMaxScrollY((this.mMaxValue - this.mMinValue) * this.mSelectorElementHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.mHasSelectorWheel && this.isEnabled()) {
            float f;
            if (motionEvent.getActionMasked() != 0) {
                return false;
            }
            this.removeAllCallbacks();
            this.hideSoftInput();
            this.mLastDownEventY = f = motionEvent.getY();
            this.mLastDownOrMoveEventY = f;
            this.mLastDownEventTime = motionEvent.getEventTime();
            this.mIgnoreMoveEvents = false;
            this.mPerformClickOnTap = false;
            f = this.mLastDownEventY;
            if (f < (float)this.mTopSelectionDividerTop) {
                if (this.mScrollState == 0) {
                    this.mPressedStateHelper.buttonPressDelayed(2);
                }
            } else if (f > (float)this.mBottomSelectionDividerBottom && this.mScrollState == 0) {
                this.mPressedStateHelper.buttonPressDelayed(1);
            }
            this.getParent().requestDisallowInterceptTouchEvent(true);
            if (!this.mFlingScroller.isFinished()) {
                this.mFlingScroller.forceFinished(true);
                this.mAdjustScroller.forceFinished(true);
                this.onScrollStateChange(0);
            } else if (!this.mAdjustScroller.isFinished()) {
                this.mFlingScroller.forceFinished(true);
                this.mAdjustScroller.forceFinished(true);
            } else {
                f = this.mLastDownEventY;
                if (f < (float)this.mTopSelectionDividerTop) {
                    this.postChangeCurrentByOneFromLongPress(false, ViewConfiguration.getLongPressTimeout());
                } else if (f > (float)this.mBottomSelectionDividerBottom) {
                    this.postChangeCurrentByOneFromLongPress(true, ViewConfiguration.getLongPressTimeout());
                } else {
                    this.mPerformClickOnTap = true;
                    this.postBeginSoftInputOnLongPressCommand();
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        if (!this.mHasSelectorWheel) {
            super.onLayout(bl, n, n2, n3, n4);
            return;
        }
        n4 = this.getMeasuredWidth();
        n3 = this.getMeasuredHeight();
        n2 = this.mInputText.getMeasuredWidth();
        n = this.mInputText.getMeasuredHeight();
        n4 = (n4 - n2) / 2;
        n3 = (n3 - n) / 2;
        this.mInputText.layout(n4, n3, n4 + n2, n3 + n);
        if (bl) {
            this.initializeSelectorWheel();
            this.initializeFadingEdges();
            n2 = this.getHeight();
            n = this.mSelectionDividersDistance;
            n2 = (n2 - n) / 2;
            n3 = this.mSelectionDividerHeight;
            this.mTopSelectionDividerTop = n2 - n3;
            this.mBottomSelectionDividerBottom = this.mTopSelectionDividerTop + n3 * 2 + n;
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        if (!this.mHasSelectorWheel) {
            super.onMeasure(n, n2);
            return;
        }
        super.onMeasure(this.makeMeasureSpec(n, this.mMaxWidth), this.makeMeasureSpec(n2, this.mMaxHeight));
        this.setMeasuredDimension(this.resolveSizeAndStateRespectingMinSize(this.mMinWidth, this.getMeasuredWidth(), n), this.resolveSizeAndStateRespectingMinSize(this.mMinHeight, this.getMeasuredHeight(), n2));
    }

    @Override
    public void onResolveDrawables(int n) {
        super.onResolveDrawables(n);
        Drawable drawable2 = this.mSelectionDivider;
        if (drawable2 != null) {
            drawable2.setLayoutDirection(n);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.isEnabled() && this.mHasSelectorWheel) {
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement(motionEvent);
            int n = motionEvent.getActionMasked();
            if (n != 1) {
                if (n == 2 && !this.mIgnoreMoveEvents) {
                    float f = motionEvent.getY();
                    if (this.mScrollState != 1) {
                        if ((int)Math.abs(f - this.mLastDownEventY) > this.mTouchSlop) {
                            this.removeAllCallbacks();
                            this.onScrollStateChange(1);
                        }
                    } else {
                        this.scrollBy(0, (int)(f - this.mLastDownOrMoveEventY));
                        this.invalidate();
                    }
                    this.mLastDownOrMoveEventY = f;
                }
            } else {
                this.removeBeginSoftInputCommand();
                this.removeChangeCurrentByOneFromLongPress();
                this.mPressedStateHelper.cancel();
                VelocityTracker velocityTracker = this.mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
                n = (int)velocityTracker.getYVelocity();
                if (Math.abs(n) > this.mMinimumFlingVelocity) {
                    this.fling(n);
                    this.onScrollStateChange(2);
                } else {
                    n = (int)motionEvent.getY();
                    int n2 = (int)Math.abs((float)n - this.mLastDownEventY);
                    long l = motionEvent.getEventTime();
                    long l2 = this.mLastDownEventTime;
                    if (n2 <= this.mTouchSlop && l - l2 < (long)ViewConfiguration.getTapTimeout()) {
                        if (this.mPerformClickOnTap) {
                            this.mPerformClickOnTap = false;
                            this.performClick();
                        } else if ((n = n / this.mSelectorElementHeight - 1) > 0) {
                            this.changeValueByOne(true);
                            this.mPressedStateHelper.buttonTapped(1);
                        } else if (n < 0) {
                            this.changeValueByOne(false);
                            this.mPressedStateHelper.buttonTapped(2);
                        }
                    } else {
                        this.ensureScrollWheelAdjusted();
                    }
                    this.onScrollStateChange(0);
                }
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean performClick() {
        if (!this.mHasSelectorWheel) {
            return super.performClick();
        }
        if (!super.performClick()) {
            this.showSoftInput();
        }
        return true;
    }

    @Override
    public boolean performLongClick() {
        if (!this.mHasSelectorWheel) {
            return super.performLongClick();
        }
        if (!super.performLongClick()) {
            this.showSoftInput();
            this.mIgnoreMoveEvents = true;
        }
        return true;
    }

    @Override
    public void scrollBy(int n, int n2) {
        int[] arrn = this.mSelectorIndices;
        n = this.mCurrentScrollOffset;
        if (!this.mWrapSelectorWheel && n2 > 0 && arrn[1] <= this.mMinValue) {
            this.mCurrentScrollOffset = this.mInitialScrollOffset;
            return;
        }
        if (!this.mWrapSelectorWheel && n2 < 0 && arrn[1] >= this.mMaxValue) {
            this.mCurrentScrollOffset = this.mInitialScrollOffset;
            return;
        }
        this.mCurrentScrollOffset += n2;
        while ((n2 = this.mCurrentScrollOffset) - this.mInitialScrollOffset > this.mSelectorTextGapHeight) {
            this.mCurrentScrollOffset = n2 - this.mSelectorElementHeight;
            this.decrementSelectorIndices(arrn);
            this.setValueInternal(arrn[1], true);
            if (this.mWrapSelectorWheel || arrn[1] > this.mMinValue) continue;
            this.mCurrentScrollOffset = this.mInitialScrollOffset;
        }
        while ((n2 = this.mCurrentScrollOffset) - this.mInitialScrollOffset < -this.mSelectorTextGapHeight) {
            this.mCurrentScrollOffset = n2 + this.mSelectorElementHeight;
            this.incrementSelectorIndices(arrn);
            this.setValueInternal(arrn[1], true);
            if (this.mWrapSelectorWheel || arrn[1] < this.mMaxValue) continue;
            this.mCurrentScrollOffset = this.mInitialScrollOffset;
        }
        if (n != n2) {
            this.onScrollChanged(0, n2, 0, n);
        }
    }

    public void setDisplayedValues(String[] arrstring) {
        if (this.mDisplayedValues == arrstring) {
            return;
        }
        this.mDisplayedValues = arrstring;
        if (this.mDisplayedValues != null) {
            this.mInputText.setRawInputType(524289);
        } else {
            this.mInputText.setRawInputType(2);
        }
        this.updateInputTextView();
        this.initializeSelectorWheelIndices();
        this.tryComputeMaxWidth();
    }

    @Override
    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        if (!this.mHasSelectorWheel) {
            this.mIncrementButton.setEnabled(bl);
        }
        if (!this.mHasSelectorWheel) {
            this.mDecrementButton.setEnabled(bl);
        }
        this.mInputText.setEnabled(bl);
    }

    public void setFormatter(Formatter formatter) {
        if (formatter == this.mFormatter) {
            return;
        }
        this.mFormatter = formatter;
        this.initializeSelectorWheelIndices();
        this.updateInputTextView();
    }

    public void setMaxValue(int n) {
        if (this.mMaxValue == n) {
            return;
        }
        if (n >= 0) {
            this.mMaxValue = n;
            if ((n = this.mMaxValue) < this.mValue) {
                this.mValue = n;
            }
            this.updateWrapSelectorWheel();
            this.initializeSelectorWheelIndices();
            this.updateInputTextView();
            this.tryComputeMaxWidth();
            this.invalidate();
            return;
        }
        throw new IllegalArgumentException("maxValue must be >= 0");
    }

    public void setMinValue(int n) {
        if (this.mMinValue == n) {
            return;
        }
        if (n >= 0) {
            this.mMinValue = n;
            if ((n = this.mMinValue) > this.mValue) {
                this.mValue = n;
            }
            this.updateWrapSelectorWheel();
            this.initializeSelectorWheelIndices();
            this.updateInputTextView();
            this.tryComputeMaxWidth();
            this.invalidate();
            return;
        }
        throw new IllegalArgumentException("minValue must be >= 0");
    }

    public void setOnLongPressUpdateInterval(long l) {
        this.mLongPressUpdateInterval = l;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    public void setOnValueChangedListener(OnValueChangeListener onValueChangeListener) {
        this.mOnValueChangeListener = onValueChangeListener;
    }

    public void setSelectionDividerHeight(int n) {
        this.mSelectionDividerHeight = n;
        this.invalidate();
    }

    public void setTextColor(int n) {
        this.mSelectorWheelPaint.setColor(n);
        this.mInputText.setTextColor(n);
        this.invalidate();
    }

    public void setTextSize(float f) {
        this.mSelectorWheelPaint.setTextSize(f);
        this.mInputText.setTextSize(0, f);
        this.invalidate();
    }

    public void setValue(int n) {
        this.setValueInternal(n, false);
    }

    public void setWrapSelectorWheel(boolean bl) {
        this.mWrapSelectorWheelPreferred = bl;
        this.updateWrapSelectorWheel();
    }

    class AccessibilityNodeProviderImpl
    extends AccessibilityNodeProvider {
        private static final int UNDEFINED = Integer.MIN_VALUE;
        private static final int VIRTUAL_VIEW_ID_DECREMENT = 3;
        private static final int VIRTUAL_VIEW_ID_INCREMENT = 1;
        private static final int VIRTUAL_VIEW_ID_INPUT = 2;
        private int mAccessibilityFocusedView = Integer.MIN_VALUE;
        private final int[] mTempArray = new int[2];
        private final Rect mTempRect = new Rect();

        AccessibilityNodeProviderImpl() {
        }

        private AccessibilityNodeInfo createAccessibilityNodeInfoForNumberPicker(int n, int n2, int n3, int n4) {
            AccessibilityNodeInfo accessibilityNodeInfo = AccessibilityNodeInfo.obtain();
            accessibilityNodeInfo.setClassName(NumberPicker.class.getName());
            accessibilityNodeInfo.setPackageName(NumberPicker.this.mContext.getPackageName());
            accessibilityNodeInfo.setSource(NumberPicker.this);
            if (this.hasVirtualDecrementButton()) {
                accessibilityNodeInfo.addChild(NumberPicker.this, 3);
            }
            accessibilityNodeInfo.addChild(NumberPicker.this, 2);
            if (this.hasVirtualIncrementButton()) {
                accessibilityNodeInfo.addChild(NumberPicker.this, 1);
            }
            accessibilityNodeInfo.setParent((View)((Object)NumberPicker.this.getParentForAccessibility()));
            accessibilityNodeInfo.setEnabled(NumberPicker.this.isEnabled());
            accessibilityNodeInfo.setScrollable(true);
            float f = NumberPicker.this.getContext().getResources().getCompatibilityInfo().applicationScale;
            Rect rect = this.mTempRect;
            rect.set(n, n2, n3, n4);
            rect.scale(f);
            accessibilityNodeInfo.setBoundsInParent(rect);
            accessibilityNodeInfo.setVisibleToUser(NumberPicker.this.isVisibleToUser());
            int[] arrn = this.mTempArray;
            NumberPicker.this.getLocationOnScreen(arrn);
            rect.offset(arrn[0], arrn[1]);
            rect.scale(f);
            accessibilityNodeInfo.setBoundsInScreen(rect);
            if (this.mAccessibilityFocusedView != -1) {
                accessibilityNodeInfo.addAction(64);
            }
            if (this.mAccessibilityFocusedView == -1) {
                accessibilityNodeInfo.addAction(128);
            }
            if (NumberPicker.this.isEnabled()) {
                if (NumberPicker.this.getWrapSelectorWheel() || NumberPicker.this.getValue() < NumberPicker.this.getMaxValue()) {
                    accessibilityNodeInfo.addAction(4096);
                }
                if (NumberPicker.this.getWrapSelectorWheel() || NumberPicker.this.getValue() > NumberPicker.this.getMinValue()) {
                    accessibilityNodeInfo.addAction(8192);
                }
            }
            return accessibilityNodeInfo;
        }

        private AccessibilityNodeInfo createAccessibilityNodeInfoForVirtualButton(int n, String object, int n2, int n3, int n4, int n5) {
            AccessibilityNodeInfo accessibilityNodeInfo = AccessibilityNodeInfo.obtain();
            accessibilityNodeInfo.setClassName(Button.class.getName());
            accessibilityNodeInfo.setPackageName(NumberPicker.this.mContext.getPackageName());
            accessibilityNodeInfo.setSource(NumberPicker.this, n);
            accessibilityNodeInfo.setParent(NumberPicker.this);
            accessibilityNodeInfo.setText((CharSequence)object);
            accessibilityNodeInfo.setClickable(true);
            accessibilityNodeInfo.setLongClickable(true);
            accessibilityNodeInfo.setEnabled(NumberPicker.this.isEnabled());
            object = this.mTempRect;
            ((Rect)object).set(n2, n3, n4, n5);
            accessibilityNodeInfo.setVisibleToUser(NumberPicker.this.isVisibleToUser((Rect)object));
            accessibilityNodeInfo.setBoundsInParent((Rect)object);
            int[] arrn = this.mTempArray;
            NumberPicker.this.getLocationOnScreen(arrn);
            ((Rect)object).offset(arrn[0], arrn[1]);
            accessibilityNodeInfo.setBoundsInScreen((Rect)object);
            if (this.mAccessibilityFocusedView != n) {
                accessibilityNodeInfo.addAction(64);
            }
            if (this.mAccessibilityFocusedView == n) {
                accessibilityNodeInfo.addAction(128);
            }
            if (NumberPicker.this.isEnabled()) {
                accessibilityNodeInfo.addAction(16);
            }
            return accessibilityNodeInfo;
        }

        private AccessibilityNodeInfo createAccessibiltyNodeInfoForInputText(int n, int n2, int n3, int n4) {
            AccessibilityNodeInfo accessibilityNodeInfo = NumberPicker.this.mInputText.createAccessibilityNodeInfo();
            accessibilityNodeInfo.setSource(NumberPicker.this, 2);
            if (this.mAccessibilityFocusedView != 2) {
                accessibilityNodeInfo.addAction(64);
            }
            if (this.mAccessibilityFocusedView == 2) {
                accessibilityNodeInfo.addAction(128);
            }
            Rect rect = this.mTempRect;
            rect.set(n, n2, n3, n4);
            accessibilityNodeInfo.setVisibleToUser(NumberPicker.this.isVisibleToUser(rect));
            accessibilityNodeInfo.setBoundsInParent(rect);
            int[] arrn = this.mTempArray;
            NumberPicker.this.getLocationOnScreen(arrn);
            rect.offset(arrn[0], arrn[1]);
            accessibilityNodeInfo.setBoundsInScreen(rect);
            return accessibilityNodeInfo;
        }

        private void findAccessibilityNodeInfosByTextInChild(String string2, int n, List<AccessibilityNodeInfo> list) {
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        String string3 = this.getVirtualDecrementButtonText();
                        if (!TextUtils.isEmpty(string3) && string3.toString().toLowerCase().contains(string2)) {
                            list.add(this.createAccessibilityNodeInfo(3));
                        }
                        return;
                    }
                } else {
                    Editable editable = NumberPicker.this.mInputText.getText();
                    if (!TextUtils.isEmpty(editable) && editable.toString().toLowerCase().contains(string2)) {
                        list.add(this.createAccessibilityNodeInfo(2));
                        return;
                    }
                    editable = NumberPicker.this.mInputText.getText();
                    if (!TextUtils.isEmpty(editable) && editable.toString().toLowerCase().contains(string2)) {
                        list.add(this.createAccessibilityNodeInfo(2));
                        return;
                    }
                }
                return;
            }
            String string4 = this.getVirtualIncrementButtonText();
            if (!TextUtils.isEmpty(string4) && string4.toString().toLowerCase().contains(string2)) {
                list.add(this.createAccessibilityNodeInfo(1));
            }
        }

        private String getVirtualDecrementButtonText() {
            int n;
            int n2 = n = NumberPicker.this.mValue - 1;
            if (NumberPicker.this.mWrapSelectorWheel) {
                n2 = NumberPicker.this.getWrappedSelectorIndex(n);
            }
            if (n2 >= NumberPicker.this.mMinValue) {
                String string2 = NumberPicker.this.mDisplayedValues == null ? NumberPicker.this.formatNumber(n2) : NumberPicker.this.mDisplayedValues[n2 - NumberPicker.this.mMinValue];
                return string2;
            }
            return null;
        }

        private String getVirtualIncrementButtonText() {
            int n;
            int n2 = n = NumberPicker.this.mValue + 1;
            if (NumberPicker.this.mWrapSelectorWheel) {
                n2 = NumberPicker.this.getWrappedSelectorIndex(n);
            }
            if (n2 <= NumberPicker.this.mMaxValue) {
                String string2 = NumberPicker.this.mDisplayedValues == null ? NumberPicker.this.formatNumber(n2) : NumberPicker.this.mDisplayedValues[n2 - NumberPicker.this.mMinValue];
                return string2;
            }
            return null;
        }

        private boolean hasVirtualDecrementButton() {
            boolean bl = NumberPicker.this.getWrapSelectorWheel() || NumberPicker.this.getValue() > NumberPicker.this.getMinValue();
            return bl;
        }

        private boolean hasVirtualIncrementButton() {
            boolean bl = NumberPicker.this.getWrapSelectorWheel() || NumberPicker.this.getValue() < NumberPicker.this.getMaxValue();
            return bl;
        }

        private void sendAccessibilityEventForVirtualButton(int n, int n2, String object) {
            if (AccessibilityManager.getInstance(NumberPicker.this.mContext).isEnabled()) {
                AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain(n2);
                accessibilityEvent.setClassName(Button.class.getName());
                accessibilityEvent.setPackageName(NumberPicker.this.mContext.getPackageName());
                accessibilityEvent.getText().add((CharSequence)object);
                accessibilityEvent.setEnabled(NumberPicker.this.isEnabled());
                accessibilityEvent.setSource(NumberPicker.this, n);
                object = NumberPicker.this;
                ((ViewGroup)object).requestSendAccessibilityEvent((View)object, accessibilityEvent);
            }
        }

        private void sendAccessibilityEventForVirtualText(int n) {
            if (AccessibilityManager.getInstance(NumberPicker.this.mContext).isEnabled()) {
                AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain(n);
                NumberPicker.this.mInputText.onInitializeAccessibilityEvent(accessibilityEvent);
                NumberPicker.this.mInputText.onPopulateAccessibilityEvent(accessibilityEvent);
                accessibilityEvent.setSource(NumberPicker.this, 2);
                NumberPicker numberPicker = NumberPicker.this;
                numberPicker.requestSendAccessibilityEvent(numberPicker, accessibilityEvent);
            }
        }

        @Override
        public AccessibilityNodeInfo createAccessibilityNodeInfo(int n) {
            if (n != -1) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            return super.createAccessibilityNodeInfo(n);
                        }
                        return this.createAccessibilityNodeInfoForVirtualButton(3, this.getVirtualDecrementButtonText(), NumberPicker.this.mScrollX, NumberPicker.this.mScrollY, NumberPicker.this.mScrollX + (NumberPicker.this.mRight - NumberPicker.this.mLeft), NumberPicker.this.mTopSelectionDividerTop + NumberPicker.this.mSelectionDividerHeight);
                    }
                    return this.createAccessibiltyNodeInfoForInputText(NumberPicker.this.mScrollX, NumberPicker.this.mTopSelectionDividerTop + NumberPicker.this.mSelectionDividerHeight, NumberPicker.this.mScrollX + (NumberPicker.this.mRight - NumberPicker.this.mLeft), NumberPicker.this.mBottomSelectionDividerBottom - NumberPicker.this.mSelectionDividerHeight);
                }
                return this.createAccessibilityNodeInfoForVirtualButton(1, this.getVirtualIncrementButtonText(), NumberPicker.this.mScrollX, NumberPicker.this.mBottomSelectionDividerBottom - NumberPicker.this.mSelectionDividerHeight, NumberPicker.this.mScrollX + (NumberPicker.this.mRight - NumberPicker.this.mLeft), NumberPicker.this.mScrollY + (NumberPicker.this.mBottom - NumberPicker.this.mTop));
            }
            return this.createAccessibilityNodeInfoForNumberPicker(NumberPicker.this.mScrollX, NumberPicker.this.mScrollY, NumberPicker.this.mScrollX + (NumberPicker.this.mRight - NumberPicker.this.mLeft), NumberPicker.this.mScrollY + (NumberPicker.this.mBottom - NumberPicker.this.mTop));
        }

        @Override
        public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String string2, int n) {
            if (TextUtils.isEmpty(string2)) {
                return Collections.emptyList();
            }
            String string3 = string2.toLowerCase();
            ArrayList<AccessibilityNodeInfo> arrayList = new ArrayList<AccessibilityNodeInfo>();
            if (n != -1) {
                if (n != 1 && n != 2 && n != 3) {
                    return super.findAccessibilityNodeInfosByText(string2, n);
                }
                this.findAccessibilityNodeInfosByTextInChild(string3, n, arrayList);
                return arrayList;
            }
            this.findAccessibilityNodeInfosByTextInChild(string3, 3, arrayList);
            this.findAccessibilityNodeInfosByTextInChild(string3, 2, arrayList);
            this.findAccessibilityNodeInfosByTextInChild(string3, 1, arrayList);
            return arrayList;
        }

        @Override
        public boolean performAction(int n, int n2, Bundle object) {
            block38 : {
                block39 : {
                    block40 : {
                        block41 : {
                            block37 : {
                                block34 : {
                                    block35 : {
                                        block36 : {
                                            boolean bl = false;
                                            if (n == -1) break block34;
                                            if (n == 1) break block35;
                                            if (n == 2) break block36;
                                            if (n == 3) {
                                                if (n2 != 16) {
                                                    if (n2 != 64) {
                                                        if (n2 != 128) {
                                                            return false;
                                                        }
                                                        if (this.mAccessibilityFocusedView == n) {
                                                            this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                                                            this.sendAccessibilityEventForVirtualView(n, 65536);
                                                            object = NumberPicker.this;
                                                            ((View)object).invalidate(0, 0, ((NumberPicker)object).mRight, NumberPicker.this.mTopSelectionDividerTop);
                                                            return true;
                                                        }
                                                        return false;
                                                    }
                                                    if (this.mAccessibilityFocusedView != n) {
                                                        this.mAccessibilityFocusedView = n;
                                                        this.sendAccessibilityEventForVirtualView(n, 32768);
                                                        object = NumberPicker.this;
                                                        ((View)object).invalidate(0, 0, ((NumberPicker)object).mRight, NumberPicker.this.mTopSelectionDividerTop);
                                                        return true;
                                                    }
                                                    return false;
                                                }
                                                if (NumberPicker.this.isEnabled()) {
                                                    if (n == 1) {
                                                        bl = true;
                                                    }
                                                    NumberPicker.this.changeValueByOne(bl);
                                                    this.sendAccessibilityEventForVirtualView(n, 1);
                                                    return true;
                                                }
                                                return false;
                                            }
                                            break block37;
                                        }
                                        if (n2 != 1) {
                                            if (n2 != 2) {
                                                if (n2 != 16) {
                                                    if (n2 != 32) {
                                                        if (n2 != 64) {
                                                            if (n2 != 128) {
                                                                return NumberPicker.this.mInputText.performAccessibilityAction(n2, (Bundle)object);
                                                            }
                                                            if (this.mAccessibilityFocusedView == n) {
                                                                this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                                                                this.sendAccessibilityEventForVirtualView(n, 65536);
                                                                NumberPicker.this.mInputText.invalidate();
                                                                return true;
                                                            }
                                                            return false;
                                                        }
                                                        if (this.mAccessibilityFocusedView != n) {
                                                            this.mAccessibilityFocusedView = n;
                                                            this.sendAccessibilityEventForVirtualView(n, 32768);
                                                            NumberPicker.this.mInputText.invalidate();
                                                            return true;
                                                        }
                                                        return false;
                                                    }
                                                    if (NumberPicker.this.isEnabled()) {
                                                        NumberPicker.this.performLongClick();
                                                        return true;
                                                    }
                                                    return false;
                                                }
                                                if (NumberPicker.this.isEnabled()) {
                                                    NumberPicker.this.performClick();
                                                    return true;
                                                }
                                                return false;
                                            }
                                            if (NumberPicker.this.isEnabled() && NumberPicker.this.mInputText.isFocused()) {
                                                NumberPicker.this.mInputText.clearFocus();
                                                return true;
                                            }
                                            return false;
                                        }
                                        if (NumberPicker.this.isEnabled() && !NumberPicker.this.mInputText.isFocused()) {
                                            return NumberPicker.this.mInputText.requestFocus();
                                        }
                                        return false;
                                    }
                                    if (n2 != 16) {
                                        if (n2 != 64) {
                                            if (n2 != 128) {
                                                return false;
                                            }
                                            if (this.mAccessibilityFocusedView == n) {
                                                this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                                                this.sendAccessibilityEventForVirtualView(n, 65536);
                                                object = NumberPicker.this;
                                                ((View)object).invalidate(0, ((NumberPicker)object).mBottomSelectionDividerBottom, NumberPicker.this.mRight, NumberPicker.this.mBottom);
                                                return true;
                                            }
                                            return false;
                                        }
                                        if (this.mAccessibilityFocusedView != n) {
                                            this.mAccessibilityFocusedView = n;
                                            this.sendAccessibilityEventForVirtualView(n, 32768);
                                            object = NumberPicker.this;
                                            ((View)object).invalidate(0, ((NumberPicker)object).mBottomSelectionDividerBottom, NumberPicker.this.mRight, NumberPicker.this.mBottom);
                                            return true;
                                        }
                                        return false;
                                    }
                                    if (NumberPicker.this.isEnabled()) {
                                        NumberPicker.this.changeValueByOne(true);
                                        this.sendAccessibilityEventForVirtualView(n, 1);
                                        return true;
                                    }
                                    return false;
                                }
                                if (n2 == 64) break block38;
                                if (n2 == 128) break block39;
                                if (n2 == 4096) break block40;
                                if (n2 == 8192) break block41;
                            }
                            return super.performAction(n, n2, (Bundle)object);
                        }
                        if (NumberPicker.this.isEnabled() && (NumberPicker.this.getWrapSelectorWheel() || NumberPicker.this.getValue() > NumberPicker.this.getMinValue())) {
                            NumberPicker.this.changeValueByOne(false);
                            return true;
                        }
                        return false;
                    }
                    if (NumberPicker.this.isEnabled() && (NumberPicker.this.getWrapSelectorWheel() || NumberPicker.this.getValue() < NumberPicker.this.getMaxValue())) {
                        NumberPicker.this.changeValueByOne(true);
                        return true;
                    }
                    return false;
                }
                if (this.mAccessibilityFocusedView == n) {
                    this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                    NumberPicker.this.clearAccessibilityFocus();
                    return true;
                }
                return false;
            }
            if (this.mAccessibilityFocusedView != n) {
                this.mAccessibilityFocusedView = n;
                NumberPicker.this.requestAccessibilityFocus();
                return true;
            }
            return false;
        }

        public void sendAccessibilityEventForVirtualView(int n, int n2) {
            if (n != 1) {
                if (n != 2) {
                    if (n == 3 && this.hasVirtualDecrementButton()) {
                        this.sendAccessibilityEventForVirtualButton(n, n2, this.getVirtualDecrementButtonText());
                    }
                } else {
                    this.sendAccessibilityEventForVirtualText(n2);
                }
            } else if (this.hasVirtualIncrementButton()) {
                this.sendAccessibilityEventForVirtualButton(n, n2, this.getVirtualIncrementButtonText());
            }
        }
    }

    class BeginSoftInputOnLongPressCommand
    implements Runnable {
        BeginSoftInputOnLongPressCommand() {
        }

        @Override
        public void run() {
            NumberPicker.this.performLongClick();
        }
    }

    class ChangeCurrentByOneFromLongPressCommand
    implements Runnable {
        private boolean mIncrement;

        ChangeCurrentByOneFromLongPressCommand() {
        }

        private void setStep(boolean bl) {
            this.mIncrement = bl;
        }

        @Override
        public void run() {
            NumberPicker.this.changeValueByOne(this.mIncrement);
            NumberPicker numberPicker = NumberPicker.this;
            numberPicker.postDelayed(this, numberPicker.mLongPressUpdateInterval);
        }
    }

    public static class CustomEditText
    extends EditText {
        public CustomEditText(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        @Override
        public void onEditorAction(int n) {
            super.onEditorAction(n);
            if (n == 6) {
                this.clearFocus();
            }
        }
    }

    public static interface Formatter {
        public String format(int var1);
    }

    class InputTextFilter
    extends NumberKeyListener {
        InputTextFilter() {
        }

        @Override
        public CharSequence filter(CharSequence arrstring, int n, int n2, Spanned charSequence, int n3, int n4) {
            if (NumberPicker.this.mSetSelectionCommand != null) {
                NumberPicker.this.mSetSelectionCommand.cancel();
            }
            Object object = NumberPicker.this.mDisplayedValues;
            int n5 = 0;
            if (object == null) {
                CharSequence charSequence2 = super.filter((CharSequence)arrstring, n, n2, (Spanned)charSequence, n3, n4);
                object = charSequence2;
                if (charSequence2 == null) {
                    object = arrstring.subSequence(n, n2);
                }
                arrstring = new StringBuilder();
                arrstring.append(String.valueOf(charSequence.subSequence(0, n3)));
                arrstring.append(object);
                arrstring.append((Object)charSequence.subSequence(n4, charSequence.length()));
                arrstring = arrstring.toString();
                if ("".equals(arrstring)) {
                    return arrstring;
                }
                if (NumberPicker.this.getSelectedPos((String)arrstring) <= NumberPicker.this.mMaxValue && arrstring.length() <= String.valueOf(NumberPicker.this.mMaxValue).length()) {
                    return object;
                }
                return "";
            }
            if (TextUtils.isEmpty((CharSequence)(arrstring = String.valueOf(arrstring.subSequence(n, n2))))) {
                return "";
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(String.valueOf(charSequence.subSequence(0, n3)));
            ((StringBuilder)object).append(arrstring);
            ((StringBuilder)object).append((Object)charSequence.subSequence(n4, charSequence.length()));
            object = ((StringBuilder)object).toString();
            String string2 = String.valueOf(object).toLowerCase();
            arrstring = NumberPicker.this.mDisplayedValues;
            n2 = arrstring.length;
            for (n = n5; n < n2; ++n) {
                charSequence = arrstring[n];
                if (!((String)charSequence).toLowerCase().startsWith(string2)) continue;
                NumberPicker.this.postSetSelectionCommand(((String)object).length(), ((String)charSequence).length());
                return ((String)charSequence).subSequence(n3, ((String)charSequence).length());
            }
            return "";
        }

        @Override
        protected char[] getAcceptedChars() {
            return DIGIT_CHARACTERS;
        }

        @Override
        public int getInputType() {
            return 1;
        }
    }

    public static interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        public void onScrollStateChange(NumberPicker var1, int var2);

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface ScrollState {
        }

    }

    public static interface OnValueChangeListener {
        public void onValueChange(NumberPicker var1, int var2, int var3);
    }

    class PressedStateHelper
    implements Runnable {
        public static final int BUTTON_DECREMENT = 2;
        public static final int BUTTON_INCREMENT = 1;
        private final int MODE_PRESS;
        private final int MODE_TAPPED;
        private int mManagedButton;
        private int mMode;

        PressedStateHelper() {
            this.MODE_PRESS = 1;
            this.MODE_TAPPED = 2;
        }

        public void buttonPressDelayed(int n) {
            this.cancel();
            this.mMode = 1;
            this.mManagedButton = n;
            NumberPicker.this.postDelayed(this, ViewConfiguration.getTapTimeout());
        }

        public void buttonTapped(int n) {
            this.cancel();
            this.mMode = 2;
            this.mManagedButton = n;
            NumberPicker.this.post(this);
        }

        public void cancel() {
            NumberPicker numberPicker;
            this.mMode = 0;
            this.mManagedButton = 0;
            NumberPicker.this.removeCallbacks(this);
            if (NumberPicker.this.mIncrementVirtualButtonPressed) {
                NumberPicker.this.mIncrementVirtualButtonPressed = false;
                numberPicker = NumberPicker.this;
                numberPicker.invalidate(0, numberPicker.mBottomSelectionDividerBottom, NumberPicker.this.mRight, NumberPicker.this.mBottom);
            }
            NumberPicker.this.mDecrementVirtualButtonPressed = false;
            if (NumberPicker.this.mDecrementVirtualButtonPressed) {
                numberPicker = NumberPicker.this;
                numberPicker.invalidate(0, 0, numberPicker.mRight, NumberPicker.this.mTopSelectionDividerTop);
            }
        }

        @Override
        public void run() {
            int n = this.mMode;
            if (n != 1) {
                if (n == 2) {
                    n = this.mManagedButton;
                    if (n != 1) {
                        if (n == 2) {
                            if (!NumberPicker.this.mDecrementVirtualButtonPressed) {
                                NumberPicker.this.postDelayed(this, ViewConfiguration.getPressedStateDuration());
                            }
                            NumberPicker.access$1680(NumberPicker.this, 1);
                            NumberPicker numberPicker = NumberPicker.this;
                            numberPicker.invalidate(0, 0, numberPicker.mRight, NumberPicker.this.mTopSelectionDividerTop);
                        }
                    } else {
                        if (!NumberPicker.this.mIncrementVirtualButtonPressed) {
                            NumberPicker.this.postDelayed(this, ViewConfiguration.getPressedStateDuration());
                        }
                        NumberPicker.access$1280(NumberPicker.this, 1);
                        NumberPicker numberPicker = NumberPicker.this;
                        numberPicker.invalidate(0, numberPicker.mBottomSelectionDividerBottom, NumberPicker.this.mRight, NumberPicker.this.mBottom);
                    }
                }
            } else {
                n = this.mManagedButton;
                if (n != 1) {
                    if (n == 2) {
                        NumberPicker.this.mDecrementVirtualButtonPressed = true;
                        NumberPicker numberPicker = NumberPicker.this;
                        numberPicker.invalidate(0, 0, numberPicker.mRight, NumberPicker.this.mTopSelectionDividerTop);
                    }
                } else {
                    NumberPicker.this.mIncrementVirtualButtonPressed = true;
                    NumberPicker numberPicker = NumberPicker.this;
                    numberPicker.invalidate(0, numberPicker.mBottomSelectionDividerBottom, NumberPicker.this.mRight, NumberPicker.this.mBottom);
                }
            }
        }
    }

    private static class SetSelectionCommand
    implements Runnable {
        private final EditText mInputText;
        private boolean mPosted;
        private int mSelectionEnd;
        private int mSelectionStart;

        public SetSelectionCommand(EditText editText) {
            this.mInputText = editText;
        }

        public void cancel() {
            if (this.mPosted) {
                this.mInputText.removeCallbacks(this);
                this.mPosted = false;
            }
        }

        public void post(int n, int n2) {
            this.mSelectionStart = n;
            this.mSelectionEnd = n2;
            if (!this.mPosted) {
                this.mInputText.post(this);
                this.mPosted = true;
            }
        }

        @Override
        public void run() {
            this.mPosted = false;
            this.mInputText.setSelection(this.mSelectionStart, this.mSelectionEnd);
        }
    }

    private static class TwoDigitFormatter
    implements Formatter {
        final Object[] mArgs = new Object[1];
        final StringBuilder mBuilder = new StringBuilder();
        java.util.Formatter mFmt;
        char mZeroDigit;

        TwoDigitFormatter() {
            this.init(Locale.getDefault());
        }

        private java.util.Formatter createFormatter(Locale locale) {
            return new java.util.Formatter(this.mBuilder, locale);
        }

        private static char getZeroDigit(Locale locale) {
            return LocaleData.get((Locale)locale).zeroDigit;
        }

        private void init(Locale locale) {
            this.mFmt = this.createFormatter(locale);
            this.mZeroDigit = TwoDigitFormatter.getZeroDigit(locale);
        }

        @Override
        public String format(int n) {
            Serializable serializable = Locale.getDefault();
            if (this.mZeroDigit != TwoDigitFormatter.getZeroDigit((Locale)serializable)) {
                this.init((Locale)serializable);
            }
            this.mArgs[0] = n;
            serializable = this.mBuilder;
            ((StringBuilder)serializable).delete(0, ((StringBuilder)serializable).length());
            this.mFmt.format("%02d", this.mArgs);
            return this.mFmt.toString();
        }
    }

}

