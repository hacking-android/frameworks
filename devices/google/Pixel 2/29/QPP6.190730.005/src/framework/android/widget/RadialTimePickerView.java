/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.IntArray;
import android.util.Log;
import android.util.MathUtils;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.internal.R;
import com.android.internal.widget.ExploreByTouchHelper;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;
import java.util.Locale;

public class RadialTimePickerView
extends View {
    private static final int AM = 0;
    private static final int ANIM_DURATION_NORMAL = 500;
    private static final int ANIM_DURATION_TOUCH = 60;
    private static final float[] COS_30;
    private static final int DEGREES_FOR_ONE_HOUR = 30;
    private static final int DEGREES_FOR_ONE_MINUTE = 6;
    public static final int HOURS = 0;
    private static final int HOURS_INNER = 2;
    private static final int HOURS_IN_CIRCLE = 12;
    private static final int[] HOURS_NUMBERS;
    private static final int[] HOURS_NUMBERS_24;
    public static final int MINUTES = 1;
    private static final int MINUTES_IN_CIRCLE = 60;
    private static final int[] MINUTES_NUMBERS;
    private static final int MISSING_COLOR = -65281;
    private static final int NUM_POSITIONS = 12;
    private static final int PM = 1;
    private static final int SELECTOR_CIRCLE = 0;
    private static final int SELECTOR_DOT = 1;
    private static final int SELECTOR_LINE = 2;
    private static final float[] SIN_30;
    private static final int[] SNAP_PREFER_30S_MAP;
    private static final String TAG = "RadialTimePickerView";
    private final FloatProperty<RadialTimePickerView> HOURS_TO_MINUTES = new FloatProperty<RadialTimePickerView>("hoursToMinutes"){

        @Override
        public Float get(RadialTimePickerView radialTimePickerView) {
            return Float.valueOf(radialTimePickerView.mHoursToMinutes);
        }

        @Override
        public void setValue(RadialTimePickerView radialTimePickerView, float f) {
            radialTimePickerView.mHoursToMinutes = f;
            radialTimePickerView.invalidate();
        }
    };
    private int mAmOrPm;
    private int mCenterDotRadius;
    boolean mChangedDuringTouch = false;
    private int mCircleRadius;
    private float mDisabledAlpha;
    private int mHalfwayDist;
    private final String[] mHours12Texts = new String[12];
    private float mHoursToMinutes;
    private ObjectAnimator mHoursToMinutesAnimator;
    private final String[] mInnerHours24Texts = new String[12];
    private String[] mInnerTextHours;
    private final float[] mInnerTextX = new float[12];
    private final float[] mInnerTextY = new float[12];
    private boolean mInputEnabled = true;
    private boolean mIs24HourMode;
    private boolean mIsOnInnerCircle;
    private OnValueSelectedListener mListener;
    private int mMaxDistForOuterNumber;
    private int mMinDistForInnerNumber;
    private String[] mMinutesText;
    private final String[] mMinutesTexts = new String[12];
    private final String[] mOuterHours24Texts = new String[12];
    private String[] mOuterTextHours;
    private final float[][] mOuterTextX = new float[2][12];
    private final float[][] mOuterTextY = new float[2][12];
    private final Paint[] mPaint = new Paint[2];
    private final Paint mPaintBackground = new Paint();
    private final Paint mPaintCenter = new Paint();
    private final Paint[] mPaintSelector = new Paint[3];
    private final int[] mSelectionDegrees = new int[2];
    private int mSelectorColor;
    private int mSelectorDotColor;
    private int mSelectorDotRadius;
    private final Path mSelectorPath = new Path();
    private int mSelectorRadius;
    private int mSelectorStroke;
    private boolean mShowHours;
    private final ColorStateList[] mTextColor = new ColorStateList[3];
    private final int[] mTextInset = new int[3];
    private final int[] mTextSize = new int[3];
    private final RadialPickerTouchHelper mTouchHelper;
    private final Typeface mTypeface;
    private int mXCenter;
    private int mYCenter;

    static {
        HOURS_NUMBERS = new int[]{12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        HOURS_NUMBERS_24 = new int[]{0, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
        MINUTES_NUMBERS = new int[]{0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55};
        SNAP_PREFER_30S_MAP = new int[361];
        COS_30 = new float[12];
        SIN_30 = new float[12];
        RadialTimePickerView.preparePrefer30sMap();
        double d = 1.5707963267948966;
        for (int i = 0; i < 12; ++i) {
            RadialTimePickerView.COS_30[i] = (float)Math.cos(d);
            RadialTimePickerView.SIN_30[i] = (float)Math.sin(d);
            d += 0.5235987755982988;
        }
    }

    public RadialTimePickerView(Context context) {
        this(context, null);
    }

    public RadialTimePickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843933);
    }

    public RadialTimePickerView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public RadialTimePickerView(Context object, AttributeSet object2, int n, int n2) {
        super((Context)object, (AttributeSet)object2);
        this.applyAttributes((AttributeSet)object2, n, n2);
        object2 = new TypedValue();
        ((Context)object).getTheme().resolveAttribute(16842803, (TypedValue)object2, true);
        this.mDisabledAlpha = ((TypedValue)object2).getFloat();
        this.mTypeface = Typeface.create("sans-serif", 0);
        this.mPaint[0] = new Paint();
        this.mPaint[0].setAntiAlias(true);
        this.mPaint[0].setTextAlign(Paint.Align.CENTER);
        this.mPaint[1] = new Paint();
        this.mPaint[1].setAntiAlias(true);
        this.mPaint[1].setTextAlign(Paint.Align.CENTER);
        this.mPaintCenter.setAntiAlias(true);
        this.mPaintSelector[0] = new Paint();
        this.mPaintSelector[0].setAntiAlias(true);
        this.mPaintSelector[1] = new Paint();
        this.mPaintSelector[1].setAntiAlias(true);
        this.mPaintSelector[2] = new Paint();
        this.mPaintSelector[2].setAntiAlias(true);
        this.mPaintSelector[2].setStrokeWidth(2.0f);
        this.mPaintBackground.setAntiAlias(true);
        object = this.getResources();
        this.mSelectorRadius = ((Resources)object).getDimensionPixelSize(17105470);
        this.mSelectorStroke = ((Resources)object).getDimensionPixelSize(17105471);
        this.mSelectorDotRadius = ((Resources)object).getDimensionPixelSize(17105469);
        this.mCenterDotRadius = ((Resources)object).getDimensionPixelSize(17105461);
        this.mTextSize[0] = ((Resources)object).getDimensionPixelSize(17105476);
        this.mTextSize[1] = ((Resources)object).getDimensionPixelSize(17105476);
        this.mTextSize[2] = ((Resources)object).getDimensionPixelSize(17105475);
        this.mTextInset[0] = ((Resources)object).getDimensionPixelSize(17105474);
        this.mTextInset[1] = ((Resources)object).getDimensionPixelSize(17105474);
        this.mTextInset[2] = ((Resources)object).getDimensionPixelSize(17105473);
        this.mShowHours = true;
        this.mHoursToMinutes = 0.0f;
        this.mIs24HourMode = false;
        this.mAmOrPm = 0;
        this.mTouchHelper = new RadialPickerTouchHelper();
        this.setAccessibilityDelegate(this.mTouchHelper);
        if (this.getImportantForAccessibility() == 0) {
            this.setImportantForAccessibility(1);
        }
        this.initHoursAndMinutesText();
        this.initData();
        object = Calendar.getInstance(Locale.getDefault());
        n = ((Calendar)object).get(11);
        n2 = ((Calendar)object).get(12);
        this.setCurrentHourInternal(n, false, false);
        this.setCurrentMinuteInternal(n2, false);
        this.setHapticFeedbackEnabled(true);
    }

    private void animatePicker(boolean bl, long l) {
        float f = bl ? 0.0f : 1.0f;
        if (this.mHoursToMinutes == f) {
            ObjectAnimator objectAnimator = this.mHoursToMinutesAnimator;
            if (objectAnimator != null && objectAnimator.isStarted()) {
                this.mHoursToMinutesAnimator.cancel();
                this.mHoursToMinutesAnimator = null;
            }
            return;
        }
        this.mHoursToMinutesAnimator = ObjectAnimator.ofFloat(this, this.HOURS_TO_MINUTES, f);
        this.mHoursToMinutesAnimator.setAutoCancel(true);
        this.mHoursToMinutesAnimator.setDuration(l);
        this.mHoursToMinutesAnimator.start();
    }

    private static void calculatePositions(Paint paint, float f, float f2, float f3, float f4, float[] arrf, float[] arrf2) {
        paint.setTextSize(f4);
        f4 = (paint.descent() + paint.ascent()) / 2.0f;
        for (int i = 0; i < 12; ++i) {
            arrf[i] = f2 - COS_30[i] * f;
            arrf2[i] = f3 - f4 - SIN_30[i] * f;
        }
    }

    private void calculatePositionsHours() {
        float f = this.mCircleRadius - this.mTextInset[0];
        RadialTimePickerView.calculatePositions(this.mPaint[0], f, this.mXCenter, this.mYCenter, this.mTextSize[0], this.mOuterTextX[0], this.mOuterTextY[0]);
        if (this.mIs24HourMode) {
            int n = this.mCircleRadius;
            int n2 = this.mTextInset[2];
            RadialTimePickerView.calculatePositions(this.mPaint[0], n - n2, this.mXCenter, this.mYCenter, this.mTextSize[2], this.mInnerTextX, this.mInnerTextY);
        }
    }

    private void calculatePositionsMinutes() {
        float f = this.mCircleRadius - this.mTextInset[1];
        RadialTimePickerView.calculatePositions(this.mPaint[1], f, this.mXCenter, this.mYCenter, this.mTextSize[1], this.mOuterTextX[1], this.mOuterTextY[1]);
    }

    private void drawCenter(Canvas canvas, float f) {
        this.mPaintCenter.setAlpha((int)(255.0f * f + 0.5f));
        canvas.drawCircle(this.mXCenter, this.mYCenter, this.mCenterDotRadius, this.mPaintCenter);
    }

    private void drawCircleBackground(Canvas canvas) {
        canvas.drawCircle(this.mXCenter, this.mYCenter, this.mCircleRadius, this.mPaintBackground);
    }

    private void drawHours(Canvas canvas, Path path, float f) {
        int n = (int)((1.0f - this.mHoursToMinutes) * 255.0f * f + 0.5f);
        if (n > 0) {
            canvas.save(2);
            canvas.clipPath(path, Region.Op.DIFFERENCE);
            this.drawHoursClipped(canvas, n, false);
            canvas.restore();
            canvas.save(2);
            canvas.clipPath(path, Region.Op.INTERSECT);
            this.drawHoursClipped(canvas, n, true);
            canvas.restore();
        }
    }

    private void drawHoursClipped(Canvas canvas, int n, boolean bl) {
        float f = this.mTextSize[0];
        float[] arrf = this.mTypeface;
        Object object = this.mTextColor[0];
        Object object2 = this.mOuterTextHours;
        float[] arrf2 = this.mOuterTextX[0];
        Object[] arrobject = this.mOuterTextY[0];
        Object object3 = this.mPaint[0];
        boolean bl2 = bl && !this.mIsOnInnerCircle;
        this.drawTextElements(canvas, f, (Typeface)arrf, (ColorStateList)object, (String[])object2, arrf2, (float[])arrobject, (Paint)object3, n, bl2, this.mSelectionDegrees[0], bl);
        if (this.mIs24HourMode && (arrobject = this.mInnerTextHours) != null) {
            f = this.mTextSize[2];
            object3 = this.mTypeface;
            object2 = this.mTextColor[2];
            arrf2 = this.mInnerTextX;
            arrf = this.mInnerTextY;
            object = this.mPaint[0];
            bl2 = bl && this.mIsOnInnerCircle;
            this.drawTextElements(canvas, f, (Typeface)object3, (ColorStateList)object2, (String[])arrobject, arrf2, arrf, (Paint)object, n, bl2, this.mSelectionDegrees[0], bl);
        }
    }

    private void drawMinutes(Canvas canvas, Path path, float f) {
        int n = (int)(this.mHoursToMinutes * 255.0f * f + 0.5f);
        if (n > 0) {
            canvas.save(2);
            canvas.clipPath(path, Region.Op.DIFFERENCE);
            this.drawMinutesClipped(canvas, n, false);
            canvas.restore();
            canvas.save(2);
            canvas.clipPath(path, Region.Op.INTERSECT);
            this.drawMinutesClipped(canvas, n, true);
            canvas.restore();
        }
    }

    private void drawMinutesClipped(Canvas canvas, int n, boolean bl) {
        this.drawTextElements(canvas, this.mTextSize[1], this.mTypeface, this.mTextColor[1], this.mMinutesText, this.mOuterTextX[1], this.mOuterTextY[1], this.mPaint[1], n, bl, this.mSelectionDegrees[1], bl);
    }

    private void drawSelector(Canvas canvas, Path object) {
        int n = this.mIsOnInnerCircle ? 2 : 0;
        int n2 = this.mTextInset[n];
        Object object2 = this.mSelectionDegrees;
        int n3 = object2[n % 2];
        n = object2[n % 2];
        float f = 1.0f;
        float f2 = n % 30 != 0 ? 1.0f : 0.0f;
        int n4 = this.mTextInset[1];
        object2 = this.mSelectionDegrees;
        int n5 = object2[1];
        if (object2[1] % 30 == 0) {
            f = 0.0f;
        }
        n = this.mSelectorRadius;
        float f3 = (float)this.mCircleRadius - MathUtils.lerp(n2, n4, this.mHoursToMinutes);
        double d = Math.toRadians(MathUtils.lerpDeg(n3, n5, this.mHoursToMinutes));
        float f4 = (float)this.mXCenter + (float)Math.sin(d) * f3;
        float f5 = (float)this.mYCenter - (float)Math.cos(d) * f3;
        object2 = this.mPaintSelector[0];
        ((Paint)object2).setColor(this.mSelectorColor);
        canvas.drawCircle(f4, f5, n, (Paint)object2);
        if (object != null) {
            ((Path)object).reset();
            ((Path)object).addCircle(f4, f5, n, Path.Direction.CCW);
        }
        if ((f2 = MathUtils.lerp(f2, f, this.mHoursToMinutes)) > 0.0f) {
            object = this.mPaintSelector[1];
            ((Paint)object).setColor(this.mSelectorDotColor);
            canvas.drawCircle(f4, f5, (float)this.mSelectorDotRadius * f2, (Paint)object);
        }
        double d2 = Math.sin(d);
        d = Math.cos(d);
        f = f3 - (float)n;
        n = this.mXCenter;
        n5 = this.mCenterDotRadius;
        n2 = (int)((double)n5 * d2);
        n3 = this.mYCenter;
        n5 = (int)((double)n5 * d);
        f2 = (int)((double)f * d2) + (n + n2);
        f = n3 - n5 - (int)((double)f * d);
        object = this.mPaintSelector[2];
        ((Paint)object).setColor(this.mSelectorColor);
        ((Paint)object).setStrokeWidth(this.mSelectorStroke);
        canvas.drawLine(this.mXCenter, this.mYCenter, f2, f, (Paint)object);
    }

    private void drawTextElements(Canvas canvas, float f, Typeface typeface, ColorStateList colorStateList, String[] arrstring, float[] arrf, float[] arrf2, Paint paint, int n, boolean bl, int n2, boolean bl2) {
        paint.setTextSize(f);
        paint.setTypeface(typeface);
        f = (float)n2 / 30.0f;
        int n3 = (int)f;
        int n4 = (int)Math.ceil(f);
        for (n2 = 0; n2 < 12; ++n2) {
            int n5 = n3 != n2 && n4 % 12 != n2 ? 0 : 1;
            if (bl2 && n5 == 0) continue;
            n5 = bl && n5 != 0 ? 32 : 0;
            n5 = colorStateList.getColorForState(StateSet.get(8 | n5), 0);
            paint.setColor(n5);
            paint.setAlpha(this.getMultipliedAlpha(n5, n));
            canvas.drawText(arrstring[n2], arrf[n2], arrf2[n2], paint);
        }
    }

    private int getDegreesForHour(int n) {
        int n2;
        if (this.mIs24HourMode) {
            n2 = n;
            if (n >= 12) {
                n2 = n - 12;
            }
        } else {
            n2 = n;
            if (n == 12) {
                n2 = 0;
            }
        }
        return n2 * 30;
    }

    private int getDegreesForMinute(int n) {
        return n * 6;
    }

    private int getDegreesFromXY(float f, float f2, boolean bl) {
        int n;
        int n2;
        if (this.mIs24HourMode && this.mShowHours) {
            n2 = this.mMinDistForInnerNumber;
            n = this.mMaxDistForOuterNumber;
        } else {
            boolean bl2 = this.mShowHours;
            int n3 = this.mCircleRadius - this.mTextInset[bl2 ^ true];
            n2 = this.mSelectorRadius;
            n = n3 + n2;
            n2 = n3 - n2;
        }
        double d = f - (float)this.mXCenter;
        double d2 = f2 - (float)this.mYCenter;
        double d3 = Math.sqrt(d * d + d2 * d2);
        if (!(d3 < (double)n2 || bl && d3 > (double)n)) {
            n = (int)(Math.toDegrees(Math.atan2(d2, d) + 1.5707963267948966) + 0.5);
            if (n < 0) {
                return n + 360;
            }
            return n;
        }
        return -1;
    }

    private int getHourForDegrees(int n, boolean bl) {
        int n2 = n / 30 % 12;
        if (this.mIs24HourMode) {
            if (!bl && n2 == 0) {
                n = 12;
            } else {
                n = n2;
                if (bl) {
                    n = n2;
                    if (n2 != 0) {
                        n = n2 + 12;
                    }
                }
            }
        } else {
            n = n2;
            if (this.mAmOrPm == 1) {
                n = n2 + 12;
            }
        }
        return n;
    }

    private boolean getInnerCircleForHour(int n) {
        boolean bl = this.mIs24HourMode && (n == 0 || n > 12);
        return bl;
    }

    private boolean getInnerCircleFromXY(float f, float f2) {
        boolean bl = this.mIs24HourMode;
        boolean bl2 = false;
        if (bl && this.mShowHours) {
            double d = f - (float)this.mXCenter;
            double d2 = f2 - (float)this.mYCenter;
            if (Math.sqrt(d * d + d2 * d2) <= (double)this.mHalfwayDist) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    private int getMinuteForDegrees(int n) {
        return n / 6;
    }

    private int getMultipliedAlpha(int n, int n2) {
        return (int)((double)Color.alpha(n) * ((double)n2 / 255.0) + 0.5);
    }

    private boolean handleTouchInput(float f, float f2, boolean bl, boolean bl2) {
        int n;
        int n2;
        boolean bl3 = this.getInnerCircleFromXY(f, f2);
        int n3 = this.getDegreesFromXY(f, f2, false);
        if (n3 == -1) {
            return false;
        }
        this.animatePicker(this.mShowHours, 60L);
        if (this.mShowHours) {
            n2 = RadialTimePickerView.snapOnly30s(n3, 0) % 360;
            n3 = this.mIsOnInnerCircle == bl3 && this.mSelectionDegrees[0] == n2 ? 0 : 1;
            this.mIsOnInnerCircle = bl3;
            this.mSelectionDegrees[0] = n2;
            n2 = 0;
            n = this.getCurrentHour();
        } else {
            n2 = RadialTimePickerView.snapPrefer30s(n3) % 360;
            n3 = this.mSelectionDegrees[1] != n2 ? 1 : 0;
            this.mSelectionDegrees[1] = n2;
            n2 = 1;
            n = this.getCurrentMinute();
        }
        if (n3 == 0 && !bl && !bl2) {
            return false;
        }
        OnValueSelectedListener onValueSelectedListener = this.mListener;
        if (onValueSelectedListener != null) {
            onValueSelectedListener.onValueSelected(n2, n, bl2);
        }
        if (n3 != 0 || bl) {
            this.performHapticFeedback(4);
            this.invalidate();
        }
        return true;
    }

    private void initData() {
        if (this.mIs24HourMode) {
            this.mOuterTextHours = this.mOuterHours24Texts;
            this.mInnerTextHours = this.mInnerHours24Texts;
        } else {
            String[] arrstring = this.mHours12Texts;
            this.mOuterTextHours = arrstring;
            this.mInnerTextHours = arrstring;
        }
        this.mMinutesText = this.mMinutesTexts;
    }

    private void initHoursAndMinutesText() {
        for (int i = 0; i < 12; ++i) {
            this.mHours12Texts[i] = String.format("%d", HOURS_NUMBERS[i]);
            this.mInnerHours24Texts[i] = String.format("%02d", HOURS_NUMBERS_24[i]);
            this.mOuterHours24Texts[i] = String.format("%d", HOURS_NUMBERS[i]);
            this.mMinutesTexts[i] = String.format("%02d", MINUTES_NUMBERS[i]);
        }
    }

    private static void preparePrefer30sMap() {
        int n = 0;
        int n2 = 1;
        int n3 = 8;
        for (int i = 0; i < 361; ++i) {
            RadialTimePickerView.SNAP_PREFER_30S_MAP[i] = n;
            if (n2 == n3) {
                n3 = (n += 6) == 360 ? 7 : (n % 30 == 0 ? 14 : 4);
                n2 = 1;
                continue;
            }
            ++n2;
        }
    }

    private void setCurrentHourInternal(int n, boolean bl, boolean bl2) {
        OnValueSelectedListener onValueSelectedListener;
        this.mSelectionDegrees[0] = n % 12 * 30;
        int n2 = n != 0 && n % 24 >= 12 ? 1 : 0;
        boolean bl3 = this.getInnerCircleForHour(n);
        if (this.mAmOrPm != n2 || this.mIsOnInnerCircle != bl3) {
            this.mAmOrPm = n2;
            this.mIsOnInnerCircle = bl3;
            this.initData();
            this.mTouchHelper.invalidateRoot();
        }
        this.invalidate();
        if (bl && (onValueSelectedListener = this.mListener) != null) {
            onValueSelectedListener.onValueSelected(0, n, bl2);
        }
    }

    private void setCurrentMinuteInternal(int n, boolean bl) {
        OnValueSelectedListener onValueSelectedListener;
        this.mSelectionDegrees[1] = n % 60 * 6;
        this.invalidate();
        if (bl && (onValueSelectedListener = this.mListener) != null) {
            onValueSelectedListener.onValueSelected(1, n, false);
        }
    }

    private void showPicker(boolean bl, boolean bl2) {
        if (this.mShowHours == bl) {
            return;
        }
        this.mShowHours = bl;
        if (bl2) {
            this.animatePicker(bl, 500L);
        } else {
            ObjectAnimator objectAnimator = this.mHoursToMinutesAnimator;
            if (objectAnimator != null && objectAnimator.isStarted()) {
                this.mHoursToMinutesAnimator.cancel();
                this.mHoursToMinutesAnimator = null;
            }
            float f = bl ? 0.0f : 1.0f;
            this.mHoursToMinutes = f;
        }
        this.initData();
        this.invalidate();
        this.mTouchHelper.invalidateRoot();
    }

    private static int snapOnly30s(int n, int n2) {
        int n3 = n / 30 * 30;
        int n4 = n3 + 30;
        if (n2 == 1) {
            n = n4;
        } else if (n2 == -1) {
            n2 = n3;
            if (n == n3) {
                n2 = n3 - 30;
            }
            n = n2;
        } else {
            n = n - n3 < n4 - n ? n3 : n4;
        }
        return n;
    }

    private static int snapPrefer30s(int n) {
        int[] arrn = SNAP_PREFER_30S_MAP;
        if (arrn == null) {
            return -1;
        }
        return arrn[n];
    }

    void applyAttributes(AttributeSet object, int n, int n2) {
        Context context = this.getContext();
        TypedArray typedArray = this.getContext().obtainStyledAttributes((AttributeSet)object, R.styleable.TimePicker, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.TimePicker, (AttributeSet)object, typedArray, n, n2);
        object = typedArray.getColorStateList(3);
        ColorStateList colorStateList = typedArray.getColorStateList(9);
        ColorStateList[] arrcolorStateList = this.mTextColor;
        if (object == null) {
            object = ColorStateList.valueOf(-65281);
        }
        arrcolorStateList[0] = object;
        arrcolorStateList = this.mTextColor;
        object = colorStateList == null ? ColorStateList.valueOf(-65281) : colorStateList;
        arrcolorStateList[2] = object;
        object = this.mTextColor;
        object[1] = object[0];
        object = typedArray.getColorStateList(5);
        n = object != null ? ((ColorStateList)object).getColorForState(StateSet.get(40), 0) : -65281;
        this.mPaintCenter.setColor(n);
        object = StateSet.get(40);
        this.mSelectorColor = n;
        this.mSelectorDotColor = this.mTextColor[0].getColorForState((int[])object, 0);
        this.mPaintBackground.setColor(typedArray.getColor(4, context.getColor(17170999)));
        typedArray.recycle();
    }

    @Override
    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        if (this.mTouchHelper.dispatchHoverEvent(motionEvent)) {
            return true;
        }
        return super.dispatchHoverEvent(motionEvent);
    }

    public int getAmOrPm() {
        return this.mAmOrPm;
    }

    public int getCurrentHour() {
        return this.getHourForDegrees(this.mSelectionDegrees[0], this.mIsOnInnerCircle);
    }

    public int getCurrentItemShowing() {
        return this.mShowHours ^ true;
    }

    public int getCurrentMinute() {
        return this.getMinuteForDegrees(this.mSelectionDegrees[1]);
    }

    public void initialize(int n, int n2, boolean bl) {
        if (this.mIs24HourMode != bl) {
            this.mIs24HourMode = bl;
            this.initData();
        }
        this.setCurrentHourInternal(n, false, false);
        this.setCurrentMinuteInternal(n2, false);
    }

    @Override
    public void onDraw(Canvas canvas) {
        float f = this.mInputEnabled ? 1.0f : this.mDisabledAlpha;
        this.drawCircleBackground(canvas);
        Path path = this.mSelectorPath;
        this.drawSelector(canvas, path);
        this.drawHours(canvas, path, f);
        this.drawMinutes(canvas, path, f);
        this.drawCenter(canvas, f);
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        if (!bl) {
            return;
        }
        this.mXCenter = this.getWidth() / 2;
        this.mYCenter = this.getHeight() / 2;
        n3 = this.mCircleRadius = Math.min(this.mXCenter, this.mYCenter);
        int[] arrn = this.mTextInset;
        n = arrn[2];
        n2 = this.mSelectorRadius;
        this.mMinDistForInnerNumber = n3 - n - n2;
        this.mMaxDistForOuterNumber = n3 - arrn[0] + n2;
        this.mHalfwayDist = n3 - (arrn[0] + arrn[2]) / 2;
        this.calculatePositionsHours();
        this.calculatePositionsMinutes();
        this.mTouchHelper.invalidateRoot();
    }

    @Override
    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int n) {
        if (!this.isEnabled()) {
            return null;
        }
        if (this.getDegreesFromXY(motionEvent.getX(), motionEvent.getY(), false) != -1) {
            return PointerIcon.getSystemIcon(this.getContext(), 1002);
        }
        return super.onResolvePointerIcon(motionEvent, n);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mInputEnabled) {
            return true;
        }
        int n = motionEvent.getActionMasked();
        if (n == 2 || n == 1 || n == 0) {
            boolean bl;
            boolean bl2 = false;
            boolean bl3 = false;
            if (n == 0) {
                this.mChangedDuringTouch = false;
                bl = bl2;
            } else {
                bl = bl2;
                if (n == 1) {
                    boolean bl4 = true;
                    bl = bl2;
                    bl3 = bl4;
                    if (!this.mChangedDuringTouch) {
                        bl = true;
                        bl3 = bl4;
                    }
                }
            }
            this.mChangedDuringTouch |= this.handleTouchInput(motionEvent.getX(), motionEvent.getY(), bl, bl3);
        }
        return true;
    }

    public boolean setAmOrPm(int n) {
        if (this.mAmOrPm != n && !this.mIs24HourMode) {
            this.mAmOrPm = n;
            this.invalidate();
            this.mTouchHelper.invalidateRoot();
            return true;
        }
        return false;
    }

    public void setCurrentHour(int n) {
        this.setCurrentHourInternal(n, true, false);
    }

    public void setCurrentItemShowing(int n, boolean bl) {
        if (n != 0) {
            if (n != 1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("ClockView does not support showing item ");
                stringBuilder.append(n);
                Log.e(TAG, stringBuilder.toString());
            } else {
                this.showMinutes(bl);
            }
        } else {
            this.showHours(bl);
        }
    }

    public void setCurrentMinute(int n) {
        this.setCurrentMinuteInternal(n, true);
    }

    public void setInputEnabled(boolean bl) {
        this.mInputEnabled = bl;
        this.invalidate();
    }

    public void setOnValueSelectedListener(OnValueSelectedListener onValueSelectedListener) {
        this.mListener = onValueSelectedListener;
    }

    public void showHours(boolean bl) {
        this.showPicker(true, bl);
    }

    public void showMinutes(boolean bl) {
        this.showPicker(false, bl);
    }

    static interface OnValueSelectedListener {
        public void onValueSelected(int var1, int var2, boolean var3);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface PickerType {
    }

    private class RadialPickerTouchHelper
    extends ExploreByTouchHelper {
        private final int MASK_TYPE;
        private final int MASK_VALUE;
        private final int MINUTE_INCREMENT;
        private final int SHIFT_TYPE;
        private final int SHIFT_VALUE;
        private final int TYPE_HOUR;
        private final int TYPE_MINUTE;
        private final Rect mTempRect;

        public RadialPickerTouchHelper() {
            super(RadialTimePickerView.this);
            this.mTempRect = new Rect();
            this.TYPE_HOUR = 1;
            this.TYPE_MINUTE = 2;
            this.SHIFT_TYPE = 0;
            this.MASK_TYPE = 15;
            this.SHIFT_VALUE = 8;
            this.MASK_VALUE = 255;
            this.MINUTE_INCREMENT = 5;
        }

        private void adjustPicker(int n) {
            int n2;
            int n3;
            int n4;
            int n5;
            if (RadialTimePickerView.this.mShowHours) {
                n2 = 1;
                n4 = RadialTimePickerView.this.getCurrentHour();
                if (RadialTimePickerView.this.mIs24HourMode) {
                    n5 = 0;
                    n3 = 23;
                } else {
                    n4 = this.hour24To12(n4);
                    n5 = 1;
                    n3 = 12;
                }
            } else {
                n2 = 5;
                n4 = RadialTimePickerView.this.getCurrentMinute() / 5;
                n5 = 0;
                n3 = 55;
            }
            n = MathUtils.constrain((n4 + n) * n2, n5, n3);
            if (RadialTimePickerView.this.mShowHours) {
                RadialTimePickerView.this.setCurrentHour(n);
            } else {
                RadialTimePickerView.this.setCurrentMinute(n);
            }
        }

        private void getBoundsForVirtualView(int n, Rect rect) {
            float f;
            float f2;
            float f3;
            int n2 = this.getTypeFromId(n);
            n = this.getValueFromId(n);
            if (n2 == 1) {
                if (RadialTimePickerView.this.getInnerCircleForHour(n)) {
                    f2 = RadialTimePickerView.this.mCircleRadius - RadialTimePickerView.this.mTextInset[2];
                    f = RadialTimePickerView.this.mSelectorRadius;
                } else {
                    f2 = RadialTimePickerView.this.mCircleRadius - RadialTimePickerView.this.mTextInset[0];
                    f = RadialTimePickerView.this.mSelectorRadius;
                }
                f3 = RadialTimePickerView.this.getDegreesForHour(n);
            } else if (n2 == 2) {
                f2 = RadialTimePickerView.this.mCircleRadius - RadialTimePickerView.this.mTextInset[1];
                f3 = RadialTimePickerView.this.getDegreesForMinute(n);
                f = RadialTimePickerView.this.mSelectorRadius;
            } else {
                f2 = 0.0f;
                f3 = 0.0f;
                f = 0.0f;
            }
            double d = Math.toRadians(f3);
            f3 = (float)RadialTimePickerView.this.mXCenter + (float)Math.sin(d) * f2;
            f2 = (float)RadialTimePickerView.this.mYCenter - (float)Math.cos(d) * f2;
            rect.set((int)(f3 - f), (int)(f2 - f), (int)(f3 + f), (int)(f2 + f));
        }

        private int getCircularDiff(int n, int n2, int n3) {
            block0 : {
                if ((n = Math.abs(n - n2)) <= n3 / 2) break block0;
                n = n3 - n;
            }
            return n;
        }

        private int getTypeFromId(int n) {
            return n >>> 0 & 15;
        }

        private int getValueFromId(int n) {
            return n >>> 8 & 255;
        }

        private CharSequence getVirtualViewDescription(int n, int n2) {
            String string2 = n != 1 && n != 2 ? null : Integer.toString(n2);
            return string2;
        }

        private int getVirtualViewIdAfter(int n, int n2) {
            if (n == 1) {
                int n3 = n2 + 1;
                n2 = RadialTimePickerView.this.mIs24HourMode ? 23 : 12;
                if (n3 <= n2) {
                    return this.makeId(n, n3);
                }
            } else if (n == 2) {
                int n4 = RadialTimePickerView.this.getCurrentMinute();
                int n5 = n2 - n2 % 5 + 5;
                if (n2 < n4 && n5 > n4) {
                    return this.makeId(n, n4);
                }
                if (n5 < 60) {
                    return this.makeId(n, n5);
                }
            }
            return Integer.MIN_VALUE;
        }

        private int hour12To24(int n, int n2) {
            int n3 = n;
            if (n == 12) {
                n = n3;
                if (n2 == 0) {
                    n = 0;
                }
            } else {
                n = n3;
                if (n2 == 1) {
                    n = n3 + 12;
                }
            }
            return n;
        }

        private int hour24To12(int n) {
            if (n == 0) {
                return 12;
            }
            if (n > 12) {
                return n - 12;
            }
            return n;
        }

        private boolean isVirtualViewSelected(int n, int n2) {
            boolean bl = false;
            boolean bl2 = false;
            if (n == 1) {
                if (RadialTimePickerView.this.getCurrentHour() == n2) {
                    bl2 = true;
                }
            } else if (n == 2) {
                bl2 = bl;
                if (RadialTimePickerView.this.getCurrentMinute() == n2) {
                    bl2 = true;
                }
            } else {
                bl2 = false;
            }
            return bl2;
        }

        private int makeId(int n, int n2) {
            return n << 0 | n2 << 8;
        }

        @Override
        protected int getVirtualViewAt(float f, float f2) {
            int n;
            int n2 = RadialTimePickerView.this.getDegreesFromXY(f, f2, true);
            if (n2 != -1) {
                int n3 = RadialTimePickerView.snapOnly30s(n2, 0) % 360;
                if (RadialTimePickerView.this.mShowHours) {
                    boolean bl = RadialTimePickerView.this.getInnerCircleFromXY(f, f2);
                    n = RadialTimePickerView.this.getHourForDegrees(n3, bl);
                    if (!RadialTimePickerView.this.mIs24HourMode) {
                        n = this.hour24To12(n);
                    }
                    n = this.makeId(1, n);
                } else {
                    n = RadialTimePickerView.this.getCurrentMinute();
                    n2 = RadialTimePickerView.this.getMinuteForDegrees(n2);
                    n3 = RadialTimePickerView.this.getMinuteForDegrees(n3);
                    if (this.getCircularDiff(n, n2, 60) >= this.getCircularDiff(n3, n2, 60)) {
                        n = n3;
                    }
                    n = this.makeId(2, n);
                }
            } else {
                n = Integer.MIN_VALUE;
            }
            return n;
        }

        @Override
        protected void getVisibleVirtualViews(IntArray intArray) {
            if (RadialTimePickerView.this.mShowHours) {
                boolean bl = RadialTimePickerView.this.mIs24HourMode;
                int n = RadialTimePickerView.this.mIs24HourMode ? 23 : 12;
                for (int i = bl ^ true; i <= n; ++i) {
                    intArray.add(this.makeId(1, i));
                }
            } else {
                int n = RadialTimePickerView.this.getCurrentMinute();
                for (int i = 0; i < 60; i += 5) {
                    intArray.add(this.makeId(2, i));
                    if (n <= i || n >= i + 5) continue;
                    intArray.add(this.makeId(2, n));
                }
            }
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
        }

        @Override
        protected boolean onPerformActionForVirtualView(int n, int n2, Bundle bundle) {
            if (n2 == 16) {
                n2 = this.getTypeFromId(n);
                n = this.getValueFromId(n);
                if (n2 == 1) {
                    if (!RadialTimePickerView.this.mIs24HourMode) {
                        n = this.hour12To24(n, RadialTimePickerView.this.mAmOrPm);
                    }
                    RadialTimePickerView.this.setCurrentHour(n);
                    return true;
                }
                if (n2 == 2) {
                    RadialTimePickerView.this.setCurrentMinute(n);
                    return true;
                }
            }
            return false;
        }

        @Override
        protected void onPopulateEventForVirtualView(int n, AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setClassName(this.getClass().getName());
            accessibilityEvent.setContentDescription(this.getVirtualViewDescription(this.getTypeFromId(n), this.getValueFromId(n)));
        }

        @Override
        protected void onPopulateNodeForVirtualView(int n, AccessibilityNodeInfo accessibilityNodeInfo) {
            accessibilityNodeInfo.setClassName(this.getClass().getName());
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
            int n2 = this.getTypeFromId(n);
            int n3 = this.getValueFromId(n);
            accessibilityNodeInfo.setContentDescription(this.getVirtualViewDescription(n2, n3));
            this.getBoundsForVirtualView(n, this.mTempRect);
            accessibilityNodeInfo.setBoundsInParent(this.mTempRect);
            accessibilityNodeInfo.setSelected(this.isVirtualViewSelected(n2, n3));
            n = this.getVirtualViewIdAfter(n2, n3);
            if (n != Integer.MIN_VALUE) {
                accessibilityNodeInfo.setTraversalBefore(RadialTimePickerView.this, n);
            }
        }

        @Override
        public boolean performAccessibilityAction(View view, int n, Bundle bundle) {
            if (super.performAccessibilityAction(view, n, bundle)) {
                return true;
            }
            if (n != 4096) {
                if (n != 8192) {
                    return false;
                }
                this.adjustPicker(-1);
                return true;
            }
            this.adjustPicker(1);
            return true;
        }
    }

}

