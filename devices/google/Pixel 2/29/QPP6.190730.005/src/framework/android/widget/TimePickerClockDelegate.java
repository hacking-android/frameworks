/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.DecimalFormatSymbols
 */
package android.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.icu.text.DecimalFormatSymbols;
import android.os.IBinder;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.text.style.TtsSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.StateSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityRecord;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.RadialTimePickerView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextInputTimePickerView;
import android.widget.TextView;
import android.widget.TimePicker;
import com.android.internal.R;
import com.android.internal.widget.NumericTextView;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

class TimePickerClockDelegate
extends TimePicker.AbstractTimePickerDelegate {
    private static final int AM = 0;
    private static final int[] ATTRS_DISABLED_ALPHA;
    private static final int[] ATTRS_TEXT_COLOR;
    private static final long DELAY_COMMIT_MILLIS = 2000L;
    private static final int FROM_EXTERNAL_API = 0;
    private static final int FROM_INPUT_PICKER = 2;
    private static final int FROM_RADIAL_PICKER = 1;
    private static final int HOURS_IN_HALF_DAY = 12;
    private static final int HOUR_INDEX = 0;
    private static final int MINUTE_INDEX = 1;
    private static final int PM = 1;
    private boolean mAllowAutoAdvance;
    private final RadioButton mAmLabel;
    private final View mAmPmLayout;
    private final View.OnClickListener mClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                default: {
                    return;
                }
                case 16909243: {
                    TimePickerClockDelegate.this.setAmOrPm(1);
                    break;
                }
                case 16909116: {
                    TimePickerClockDelegate.this.setCurrentItemShowing(1, true, true);
                    break;
                }
                case 16908993: {
                    TimePickerClockDelegate.this.setCurrentItemShowing(0, true, true);
                    break;
                }
                case 16908724: {
                    TimePickerClockDelegate.this.setAmOrPm(0);
                }
            }
            TimePickerClockDelegate.this.tryVibrate();
        }
    };
    private final Runnable mCommitHour = new Runnable(){

        @Override
        public void run() {
            TimePickerClockDelegate timePickerClockDelegate = TimePickerClockDelegate.this;
            timePickerClockDelegate.setHour(timePickerClockDelegate.mHourView.getValue());
        }
    };
    private final Runnable mCommitMinute = new Runnable(){

        @Override
        public void run() {
            TimePickerClockDelegate timePickerClockDelegate = TimePickerClockDelegate.this;
            timePickerClockDelegate.setMinute(timePickerClockDelegate.mMinuteView.getValue());
        }
    };
    private int mCurrentHour;
    private int mCurrentMinute;
    private final NumericTextView.OnValueChangedListener mDigitEnteredListener = new NumericTextView.OnValueChangedListener(){

        @Override
        public void onValueChanged(NumericTextView numericTextView, int n, boolean bl, boolean bl2) {
            block9 : {
                NumericTextView numericTextView2;
                Runnable runnable;
                block8 : {
                    block7 : {
                        if (numericTextView != TimePickerClockDelegate.this.mHourView) break block7;
                        runnable = TimePickerClockDelegate.this.mCommitHour;
                        numericTextView2 = numericTextView.isFocused() ? TimePickerClockDelegate.this.mMinuteView : null;
                        break block8;
                    }
                    if (numericTextView != TimePickerClockDelegate.this.mMinuteView) break block9;
                    runnable = TimePickerClockDelegate.this.mCommitMinute;
                    numericTextView2 = null;
                }
                numericTextView.removeCallbacks(runnable);
                if (bl) {
                    if (bl2) {
                        runnable.run();
                        if (numericTextView2 != null) {
                            numericTextView2.requestFocus();
                        }
                    } else {
                        numericTextView.postDelayed(runnable, 2000L);
                    }
                }
                return;
            }
        }
    };
    private final View.OnFocusChangeListener mFocusListener = new View.OnFocusChangeListener(){

        @Override
        public void onFocusChange(View view, boolean bl) {
            if (bl) {
                switch (view.getId()) {
                    default: {
                        return;
                    }
                    case 16909243: {
                        TimePickerClockDelegate.this.setAmOrPm(1);
                        break;
                    }
                    case 16909116: {
                        TimePickerClockDelegate.this.setCurrentItemShowing(1, true, true);
                        break;
                    }
                    case 16908993: {
                        TimePickerClockDelegate.this.setCurrentItemShowing(0, true, true);
                        break;
                    }
                    case 16908724: {
                        TimePickerClockDelegate.this.setAmOrPm(0);
                    }
                }
                TimePickerClockDelegate.this.tryVibrate();
            }
        }
    };
    private boolean mHourFormatShowLeadingZero;
    private boolean mHourFormatStartsAtZero;
    private final NumericTextView mHourView;
    private boolean mIs24Hour;
    private boolean mIsAmPmAtLeft = false;
    private boolean mIsAmPmAtTop = false;
    private boolean mIsEnabled = true;
    private boolean mLastAnnouncedIsHour;
    private CharSequence mLastAnnouncedText;
    private final NumericTextView mMinuteView;
    private final RadialTimePickerView.OnValueSelectedListener mOnValueSelectedListener = new RadialTimePickerView.OnValueSelectedListener(){

        @Override
        public void onValueSelected(int n, int n2, boolean bl) {
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            if (n != 0) {
                if (n != 1) {
                    n5 = n4;
                } else {
                    if (TimePickerClockDelegate.this.getMinute() != n2) {
                        n5 = 1;
                    }
                    TimePickerClockDelegate.this.setMinuteInternal(n2, 1, true);
                }
            } else {
                n = n3;
                if (TimePickerClockDelegate.this.getHour() != n2) {
                    n = 1;
                }
                n3 = TimePickerClockDelegate.this.mAllowAutoAdvance && bl ? 1 : 0;
                Object object = TimePickerClockDelegate.this;
                bl = n3 == 0;
                ((TimePickerClockDelegate)object).setHourInternal(n2, 1, bl, true);
                n5 = n;
                if (n3 != 0) {
                    TimePickerClockDelegate.this.setCurrentItemShowing(1, true, false);
                    n2 = TimePickerClockDelegate.this.getLocalizedHour(n2);
                    object = TimePickerClockDelegate.this.mDelegator;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(n2);
                    stringBuilder.append(". ");
                    stringBuilder.append(TimePickerClockDelegate.this.mSelectMinutes);
                    ((View)object).announceForAccessibility(stringBuilder.toString());
                    n5 = n;
                }
            }
            if (TimePickerClockDelegate.this.mOnTimeChangedListener != null && n5 != 0) {
                TimePickerClockDelegate.this.mOnTimeChangedListener.onTimeChanged(TimePickerClockDelegate.this.mDelegator, TimePickerClockDelegate.this.getHour(), TimePickerClockDelegate.this.getMinute());
            }
        }
    };
    private final TextInputTimePickerView.OnValueTypedListener mOnValueTypedListener = new TextInputTimePickerView.OnValueTypedListener(){

        @Override
        public void onValueChanged(int n, int n2) {
            if (n != 0) {
                if (n != 1) {
                    if (n == 2) {
                        TimePickerClockDelegate.this.setAmOrPm(n2);
                    }
                } else {
                    TimePickerClockDelegate.this.setMinuteInternal(n2, 2, true);
                }
            } else {
                TimePickerClockDelegate.this.setHourInternal(n2, 2, false, true);
            }
        }
    };
    private final RadioButton mPmLabel;
    private boolean mRadialPickerModeEnabled = true;
    private final View mRadialTimePickerHeader;
    private final ImageButton mRadialTimePickerModeButton;
    private final String mRadialTimePickerModeEnabledDescription;
    private final RadialTimePickerView mRadialTimePickerView;
    private final String mSelectHours;
    private final String mSelectMinutes;
    private final TextView mSeparatorView;
    private final Calendar mTempCalendar;
    private final View mTextInputPickerHeader;
    private final String mTextInputPickerModeEnabledDescription;
    private final TextInputTimePickerView mTextInputPickerView;

    static {
        ATTRS_TEXT_COLOR = new int[]{16842904};
        ATTRS_DISABLED_ALPHA = new int[]{16842803};
    }

    public TimePickerClockDelegate(TimePicker object, Context context, AttributeSet attributeSet, int n, int n2) {
        super((TimePicker)object, context);
        TypedArray typedArray = this.mContext.obtainStyledAttributes(attributeSet, R.styleable.TimePicker, n, n2);
        Object object2 = (LayoutInflater)this.mContext.getSystemService("layout_inflater");
        Object object3 = this.mContext.getResources();
        this.mSelectHours = ((Resources)object3).getString(17040980);
        this.mSelectMinutes = ((Resources)object3).getString(17040984);
        object2 = ((LayoutInflater)object2).inflate(typedArray.getResourceId(12, 17367324), (ViewGroup)object);
        ((View)object2).setSaveFromParentEnabled(false);
        this.mRadialTimePickerHeader = ((View)object2).findViewById(16909465);
        this.mRadialTimePickerHeader.setOnTouchListener(new NearestTouchDelegate());
        this.mHourView = (NumericTextView)((View)object2).findViewById(16908993);
        this.mHourView.setOnClickListener(this.mClickListener);
        this.mHourView.setOnFocusChangeListener(this.mFocusListener);
        this.mHourView.setOnDigitEnteredListener(this.mDigitEnteredListener);
        this.mHourView.setAccessibilityDelegate(new ClickActionDelegate(context, 17040980));
        this.mSeparatorView = (TextView)((View)object2).findViewById(16909337);
        this.mMinuteView = (NumericTextView)((View)object2).findViewById(16909116);
        this.mMinuteView.setOnClickListener(this.mClickListener);
        this.mMinuteView.setOnFocusChangeListener(this.mFocusListener);
        this.mMinuteView.setOnDigitEnteredListener(this.mDigitEnteredListener);
        this.mMinuteView.setAccessibilityDelegate(new ClickActionDelegate(context, 17040984));
        this.mMinuteView.setRange(0, 59);
        this.mAmPmLayout = ((View)object2).findViewById(16908726);
        this.mAmPmLayout.setOnTouchListener(new NearestTouchDelegate());
        object = TimePicker.getAmPmStrings(context);
        this.mAmLabel = (RadioButton)this.mAmPmLayout.findViewById(16908724);
        this.mAmLabel.setText(TimePickerClockDelegate.obtainVerbatim(object[0]));
        this.mAmLabel.setOnClickListener(this.mClickListener);
        TimePickerClockDelegate.ensureMinimumTextWidth(this.mAmLabel);
        this.mPmLabel = (RadioButton)this.mAmPmLayout.findViewById(16909243);
        this.mPmLabel.setText(TimePickerClockDelegate.obtainVerbatim(object[1]));
        this.mPmLabel.setOnClickListener(this.mClickListener);
        TimePickerClockDelegate.ensureMinimumTextWidth(this.mPmLabel);
        object = null;
        int n3 = typedArray.getResourceId(1, 0);
        if (n3 != 0) {
            object3 = this.mContext.obtainStyledAttributes(null, ATTRS_TEXT_COLOR, 0, n3);
            object = this.applyLegacyColorFixes(((TypedArray)object3).getColorStateList(0));
            ((TypedArray)object3).recycle();
        }
        object3 = object;
        if (object == null) {
            object3 = typedArray.getColorStateList(11);
        }
        this.mTextInputPickerHeader = ((View)object2).findViewById(16909023);
        if (object3 != null) {
            this.mHourView.setTextColor((ColorStateList)object3);
            this.mSeparatorView.setTextColor((ColorStateList)object3);
            this.mMinuteView.setTextColor((ColorStateList)object3);
            this.mAmLabel.setTextColor((ColorStateList)object3);
            this.mPmLabel.setTextColor((ColorStateList)object3);
        }
        if (typedArray.hasValueOrEmpty(0)) {
            this.mRadialTimePickerHeader.setBackground(typedArray.getDrawable(0));
            this.mTextInputPickerHeader.setBackground(typedArray.getDrawable(0));
        }
        typedArray.recycle();
        this.mRadialTimePickerView = (RadialTimePickerView)((View)object2).findViewById(16909268);
        this.mRadialTimePickerView.applyAttributes(attributeSet, n, n2);
        this.mRadialTimePickerView.setOnValueSelectedListener(this.mOnValueSelectedListener);
        this.mTextInputPickerView = (TextInputTimePickerView)((View)object2).findViewById(16909026);
        this.mTextInputPickerView.setListener(this.mOnValueTypedListener);
        this.mRadialTimePickerModeButton = (ImageButton)((View)object2).findViewById(16909480);
        this.mRadialTimePickerModeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                TimePickerClockDelegate.this.toggleRadialPickerMode();
            }
        });
        this.mRadialTimePickerModeEnabledDescription = context.getResources().getString(17041142);
        this.mTextInputPickerModeEnabledDescription = context.getResources().getString(17041143);
        this.mAllowAutoAdvance = true;
        this.updateHourFormat();
        this.mTempCalendar = Calendar.getInstance(this.mLocale);
        this.initialize(this.mTempCalendar.get(11), this.mTempCalendar.get(12), this.mIs24Hour, 0);
    }

    private ColorStateList applyLegacyColorFixes(ColorStateList colorStateList) {
        if (colorStateList != null && !colorStateList.hasState(16843518)) {
            int n;
            int n2;
            if (colorStateList.hasState(16842913)) {
                n = colorStateList.getColorForState(StateSet.get(10), 0);
                n2 = colorStateList.getColorForState(StateSet.get(8), 0);
            } else {
                n = colorStateList.getDefaultColor();
                n2 = this.multiplyAlphaComponent(n, this.mContext.obtainStyledAttributes(ATTRS_DISABLED_ALPHA).getFloat(0, 0.3f));
            }
            if (n != 0 && n2 != 0) {
                return new ColorStateList(new int[][]{{16843518}, new int[0]}, new int[]{n, n2});
            }
            return null;
        }
        return colorStateList;
    }

    private static void ensureMinimumTextWidth(TextView textView) {
        textView.measure(0, 0);
        int n = textView.getMeasuredWidth();
        textView.setMinWidth(n);
        textView.setMinimumWidth(n);
    }

    private int getCurrentItemShowing() {
        return this.mRadialTimePickerView.getCurrentItemShowing();
    }

    private static String getHourMinSeparatorFromPattern(String charSequence) {
        boolean bl = false;
        for (int i = 0; i < ((String)charSequence).length(); ++i) {
            char c = ((String)charSequence).charAt(i);
            if (c == ' ') continue;
            if (c != '\'') {
                if (c != 'H' && c != 'K' && c != 'h' && c != 'k') {
                    if (!bl) continue;
                    return Character.toString(((String)charSequence).charAt(i));
                }
                bl = true;
                continue;
            }
            if (!bl) continue;
            charSequence = new SpannableStringBuilder(((String)charSequence).substring(i));
            return ((SpannableStringBuilder)charSequence).subSequence(0, DateFormat.appendQuotedText((SpannableStringBuilder)charSequence, 0)).toString();
        }
        return ":";
    }

    private int getLocalizedHour(int n) {
        int n2 = n;
        if (!this.mIs24Hour) {
            n2 = n % 12;
        }
        n = n2;
        if (!this.mHourFormatStartsAtZero) {
            n = n2;
            if (n2 == 0) {
                n = this.mIs24Hour ? 24 : 12;
            }
        }
        return n;
    }

    private void initialize(int n, int n2, boolean bl, int n3) {
        this.mCurrentHour = n;
        this.mCurrentMinute = n2;
        this.mIs24Hour = bl;
        this.updateUI(n3);
    }

    private static int lastIndexOfAny(String string2, char[] arrc) {
        int n = arrc.length;
        if (n > 0) {
            for (int i = string2.length() - 1; i >= 0; --i) {
                char c = string2.charAt(i);
                for (int j = 0; j < n; ++j) {
                    if (c != arrc[j]) continue;
                    return i;
                }
            }
        }
        return -1;
    }

    private int multiplyAlphaComponent(int n, float f) {
        return (int)((float)(n >> 24 & 255) * f + 0.5f) << 24 | 16777215 & n;
    }

    static final CharSequence obtainVerbatim(String string2) {
        return new SpannableStringBuilder().append((CharSequence)string2, new TtsSpan.VerbatimBuilder(string2).build(), 0);
    }

    private void onTimeChanged() {
        this.mDelegator.sendAccessibilityEvent(4);
        if (this.mOnTimeChangedListener != null) {
            this.mOnTimeChangedListener.onTimeChanged(this.mDelegator, this.getHour(), this.getMinute());
        }
        if (this.mAutoFillChangeListener != null) {
            this.mAutoFillChangeListener.onTimeChanged(this.mDelegator, this.getHour(), this.getMinute());
        }
    }

    private void setAmOrPm(int n) {
        this.updateAmPmLabelStates(n);
        if (this.mRadialTimePickerView.setAmOrPm(n)) {
            this.mCurrentHour = this.getHour();
            this.updateTextInputPicker();
            if (this.mOnTimeChangedListener != null) {
                this.mOnTimeChangedListener.onTimeChanged(this.mDelegator, this.getHour(), this.getMinute());
            }
        }
    }

    private void setAmPmStart(boolean bl) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)this.mAmPmLayout.getLayoutParams();
        if (layoutParams.getRule(1) == 0 && layoutParams.getRule(0) == 0) {
            if (layoutParams.getRule(3) != 0 || layoutParams.getRule(2) != 0) {
                int n;
                if (this.mIsAmPmAtTop == bl) {
                    return;
                }
                if (bl) {
                    n = layoutParams.getRule(3);
                    layoutParams.removeRule(3);
                    layoutParams.addRule(2, n);
                } else {
                    n = layoutParams.getRule(2);
                    layoutParams.removeRule(2);
                    layoutParams.addRule(3, n);
                }
                Object t = this.mRadialTimePickerHeader.findViewById(n);
                int n2 = ((View)t).getPaddingTop();
                n = ((View)t).getPaddingBottom();
                ((View)t).setPadding(((View)t).getPaddingLeft(), n, ((View)t).getPaddingRight(), n2);
                this.mIsAmPmAtTop = bl;
            }
        } else {
            int n = (int)(this.mContext.getResources().getDisplayMetrics().density * 8.0f);
            boolean bl2 = TextUtils.getLayoutDirectionFromLocale(this.mLocale) == 0 ? bl : bl ^ true;
            if (bl2) {
                layoutParams.removeRule(1);
                layoutParams.addRule(0, this.mHourView.getId());
            } else {
                layoutParams.removeRule(0);
                layoutParams.addRule(1, this.mMinuteView.getId());
            }
            if (bl) {
                layoutParams.setMarginStart(0);
                layoutParams.setMarginEnd(n);
            } else {
                layoutParams.setMarginStart(n);
                layoutParams.setMarginEnd(0);
            }
            this.mIsAmPmAtLeft = bl2;
        }
        this.mAmPmLayout.setLayoutParams(layoutParams);
    }

    private void setCurrentItemShowing(int n, boolean bl, boolean bl2) {
        this.mRadialTimePickerView.setCurrentItemShowing(n, bl);
        if (n == 0) {
            if (bl2) {
                this.mDelegator.announceForAccessibility(this.mSelectHours);
            }
        } else if (bl2) {
            this.mDelegator.announceForAccessibility(this.mSelectMinutes);
        }
        NumericTextView numericTextView = this.mHourView;
        bl2 = false;
        bl = n == 0;
        numericTextView.setActivated(bl);
        numericTextView = this.mMinuteView;
        bl = bl2;
        if (n == 1) {
            bl = true;
        }
        numericTextView.setActivated(bl);
    }

    private void setHourInternal(int n, int n2, boolean bl, boolean bl2) {
        if (this.mCurrentHour == n) {
            return;
        }
        this.resetAutofilledValue();
        this.mCurrentHour = n;
        this.updateHeaderHour(n, bl);
        this.updateHeaderAmPm();
        int n3 = 1;
        if (n2 != 1) {
            this.mRadialTimePickerView.setCurrentHour(n);
            RadialTimePickerView radialTimePickerView = this.mRadialTimePickerView;
            if (n < 12) {
                n3 = 0;
            }
            radialTimePickerView.setAmOrPm(n3);
        }
        if (n2 != 2) {
            this.updateTextInputPicker();
        }
        this.mDelegator.invalidate();
        if (bl2) {
            this.onTimeChanged();
        }
    }

    private void setMinuteInternal(int n, int n2, boolean bl) {
        if (this.mCurrentMinute == n) {
            return;
        }
        this.resetAutofilledValue();
        this.mCurrentMinute = n;
        this.updateHeaderMinute(n, true);
        if (n2 != 1) {
            this.mRadialTimePickerView.setCurrentMinute(n);
        }
        if (n2 != 2) {
            this.updateTextInputPicker();
        }
        this.mDelegator.invalidate();
        if (bl) {
            this.onTimeChanged();
        }
    }

    private void toggleRadialPickerMode() {
        if (this.mRadialPickerModeEnabled) {
            this.mRadialTimePickerView.setVisibility(8);
            this.mRadialTimePickerHeader.setVisibility(8);
            this.mTextInputPickerHeader.setVisibility(0);
            this.mTextInputPickerView.setVisibility(0);
            this.mRadialTimePickerModeButton.setImageResource(17301794);
            this.mRadialTimePickerModeButton.setContentDescription(this.mRadialTimePickerModeEnabledDescription);
            this.mRadialPickerModeEnabled = false;
        } else {
            this.mRadialTimePickerView.setVisibility(0);
            this.mRadialTimePickerHeader.setVisibility(0);
            this.mTextInputPickerHeader.setVisibility(8);
            this.mTextInputPickerView.setVisibility(8);
            this.mRadialTimePickerModeButton.setImageResource(17301871);
            this.mRadialTimePickerModeButton.setContentDescription(this.mTextInputPickerModeEnabledDescription);
            this.updateTextInputPicker();
            InputMethodManager inputMethodManager = this.mContext.getSystemService(InputMethodManager.class);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(this.mDelegator.getWindowToken(), 0);
            }
            this.mRadialPickerModeEnabled = true;
        }
    }

    private void tryAnnounceForAccessibility(CharSequence charSequence, boolean bl) {
        if (this.mLastAnnouncedIsHour != bl || !charSequence.equals(this.mLastAnnouncedText)) {
            this.mDelegator.announceForAccessibility(charSequence);
            this.mLastAnnouncedText = charSequence;
            this.mLastAnnouncedIsHour = bl;
        }
    }

    private void tryVibrate() {
        this.mDelegator.performHapticFeedback(4);
    }

    private void updateAmPmLabelStates(int n) {
        boolean bl = false;
        boolean bl2 = n == 0;
        this.mAmLabel.setActivated(bl2);
        this.mAmLabel.setChecked(bl2);
        bl2 = bl;
        if (n == 1) {
            bl2 = true;
        }
        this.mPmLabel.setActivated(bl2);
        this.mPmLabel.setChecked(bl2);
    }

    private void updateHeaderAmPm() {
        if (this.mIs24Hour) {
            this.mAmPmLayout.setVisibility(8);
        } else {
            this.setAmPmStart(DateFormat.getBestDateTimePattern(this.mLocale, "hm").startsWith("a"));
            int n = this.mCurrentHour < 12 ? 0 : 1;
            this.updateAmPmLabelStates(n);
        }
    }

    private void updateHeaderHour(int n, boolean bl) {
        n = this.getLocalizedHour(n);
        this.mHourView.setValue(n);
        if (bl) {
            this.tryAnnounceForAccessibility(this.mHourView.getText(), true);
        }
    }

    private void updateHeaderMinute(int n, boolean bl) {
        this.mMinuteView.setValue(n);
        if (bl) {
            this.tryAnnounceForAccessibility(this.mMinuteView.getText(), false);
        }
    }

    private void updateHeaderSeparator() {
        Locale locale = this.mLocale;
        String string2 = this.mIs24Hour ? "Hm" : "hm";
        string2 = TimePickerClockDelegate.getHourMinSeparatorFromPattern(DateFormat.getBestDateTimePattern(locale, string2));
        this.mSeparatorView.setText(string2);
        this.mTextInputPickerView.updateSeparator(string2);
    }

    private void updateHourFormat() {
        int n;
        boolean bl;
        Object object;
        int n2;
        block4 : {
            char c;
            Locale locale = this.mLocale;
            object = this.mIs24Hour ? "Hm" : "hm";
            object = DateFormat.getBestDateTimePattern(locale, (String)object);
            int n3 = object.length();
            boolean bl2 = false;
            int n4 = 0;
            n2 = 0;
            do {
                bl = bl2;
                n = n4;
                if (n2 >= n3) break block4;
                c = object.charAt(n2);
                if (c == 'H' || c == 'h' || c == 'K' || c == 'k') break;
                ++n2;
            } while (true);
            n4 = c;
            bl = bl2;
            n = n4;
            if (n2 + 1 < n3) {
                bl = bl2;
                n = n4;
                if (c == object.charAt(n2 + 1)) {
                    bl = true;
                    n = n4;
                }
            }
        }
        this.mHourFormatShowLeadingZero = bl;
        bl = n == 75 || n == 72;
        this.mHourFormatStartsAtZero = bl;
        n = true ^ this.mHourFormatStartsAtZero;
        n2 = this.mIs24Hour ? 23 : 11;
        this.mHourView.setRange(n, n2 + n);
        this.mHourView.setShowLeadingZeroes(this.mHourFormatShowLeadingZero);
        object = DecimalFormatSymbols.getInstance((Locale)this.mLocale).getDigitStrings();
        n = 0;
        for (n2 = 0; n2 < 10; ++n2) {
            n = Math.max(n, object[n2].length());
        }
        this.mTextInputPickerView.setHourFormat(n * 2);
    }

    private void updateRadialPicker(int n) {
        this.mRadialTimePickerView.initialize(this.mCurrentHour, this.mCurrentMinute, this.mIs24Hour);
        this.setCurrentItemShowing(n, false, true);
    }

    private void updateTextInputPicker() {
        TextInputTimePickerView textInputTimePickerView = this.mTextInputPickerView;
        int n = this.getLocalizedHour(this.mCurrentHour);
        int n2 = this.mCurrentMinute;
        int n3 = this.mCurrentHour < 12 ? 0 : 1;
        textInputTimePickerView.updateTextInputValues(n, n2, n3, this.mIs24Hour, this.mHourFormatStartsAtZero);
    }

    private void updateUI(int n) {
        this.updateHeaderAmPm();
        this.updateHeaderHour(this.mCurrentHour, false);
        this.updateHeaderSeparator();
        this.updateHeaderMinute(this.mCurrentMinute, false);
        this.updateRadialPicker(n);
        this.updateTextInputPicker();
        this.mDelegator.invalidate();
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        this.onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    @Override
    public View getAmView() {
        return this.mAmLabel;
    }

    @Override
    public int getBaseline() {
        return -1;
    }

    @Override
    public int getHour() {
        int n = this.mRadialTimePickerView.getCurrentHour();
        if (this.mIs24Hour) {
            return n;
        }
        if (this.mRadialTimePickerView.getAmOrPm() == 1) {
            return n % 12 + 12;
        }
        return n % 12;
    }

    @Override
    public View getHourView() {
        return this.mHourView;
    }

    @Override
    public int getMinute() {
        return this.mRadialTimePickerView.getCurrentMinute();
    }

    @Override
    public View getMinuteView() {
        return this.mMinuteView;
    }

    @Override
    public View getPmView() {
        return this.mPmLabel;
    }

    @Override
    public boolean is24Hour() {
        return this.mIs24Hour;
    }

    @Override
    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    @Override
    public void onPopulateAccessibilityEvent(AccessibilityEvent object) {
        int n = this.mIs24Hour ? 1 | 128 : 1 | 64;
        this.mTempCalendar.set(11, this.getHour());
        this.mTempCalendar.set(12, this.getMinute());
        String string2 = DateUtils.formatDateTime(this.mContext, this.mTempCalendar.getTimeInMillis(), n);
        String string3 = this.mRadialTimePickerView.getCurrentItemShowing() == 0 ? this.mSelectHours : this.mSelectMinutes;
        object = ((AccessibilityRecord)object).getText();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" ");
        stringBuilder.append(string3);
        object.add(stringBuilder.toString());
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof TimePicker.AbstractTimePickerDelegate.SavedState) {
            parcelable = (TimePicker.AbstractTimePickerDelegate.SavedState)parcelable;
            this.initialize(((TimePicker.AbstractTimePickerDelegate.SavedState)parcelable).getHour(), ((TimePicker.AbstractTimePickerDelegate.SavedState)parcelable).getMinute(), ((TimePicker.AbstractTimePickerDelegate.SavedState)parcelable).is24HourMode(), ((TimePicker.AbstractTimePickerDelegate.SavedState)parcelable).getCurrentItemShowing());
            this.mRadialTimePickerView.invalidate();
        }
    }

    @Override
    public Parcelable onSaveInstanceState(Parcelable parcelable) {
        return new TimePicker.AbstractTimePickerDelegate.SavedState(parcelable, this.getHour(), this.getMinute(), this.is24Hour(), this.getCurrentItemShowing());
    }

    @Override
    public void setDate(int n, int n2) {
        this.setHourInternal(n, 0, true, false);
        this.setMinuteInternal(n2, 0, false);
        this.onTimeChanged();
    }

    @Override
    public void setEnabled(boolean bl) {
        this.mHourView.setEnabled(bl);
        this.mMinuteView.setEnabled(bl);
        this.mAmLabel.setEnabled(bl);
        this.mPmLabel.setEnabled(bl);
        this.mRadialTimePickerView.setEnabled(bl);
        this.mIsEnabled = bl;
    }

    @Override
    public void setHour(int n) {
        this.setHourInternal(n, 0, true, true);
    }

    @Override
    public void setIs24Hour(boolean bl) {
        if (this.mIs24Hour != bl) {
            this.mIs24Hour = bl;
            this.mCurrentHour = this.getHour();
            this.updateHourFormat();
            this.updateUI(this.mRadialTimePickerView.getCurrentItemShowing());
        }
    }

    @Override
    public void setMinute(int n) {
        this.setMinuteInternal(n, 0, true);
    }

    @Override
    public boolean validateInput() {
        return this.mTextInputPickerView.validateInput();
    }

    @Retention(value=RetentionPolicy.SOURCE)
    private static @interface ChangeSource {
    }

    private static class ClickActionDelegate
    extends View.AccessibilityDelegate {
        private final AccessibilityNodeInfo.AccessibilityAction mClickAction;

        public ClickActionDelegate(Context context, int n) {
            this.mClickAction = new AccessibilityNodeInfo.AccessibilityAction(16, context.getString(n));
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.addAction(this.mClickAction);
        }
    }

    private static class NearestTouchDelegate
    implements View.OnTouchListener {
        private View mInitialTouchTarget;

        private NearestTouchDelegate() {
        }

        private View findNearestChild(ViewGroup viewGroup, int n, int n2) {
            View view = null;
            int n3 = Integer.MAX_VALUE;
            int n4 = viewGroup.getChildCount();
            for (int i = 0; i < n4; ++i) {
                View view2 = viewGroup.getChildAt(i);
                int n5 = n - (view2.getLeft() + view2.getWidth() / 2);
                int n6 = n2 - (view2.getTop() + view2.getHeight() / 2);
                n5 = n5 * n5 + n6 * n6;
                n6 = n3;
                if (n3 > n5) {
                    view = view2;
                    n6 = n5;
                }
                n3 = n6;
            }
            return view;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            View view2;
            int n = motionEvent.getActionMasked();
            if (n == 0) {
                this.mInitialTouchTarget = view instanceof ViewGroup ? this.findNearestChild((ViewGroup)view, (int)motionEvent.getX(), (int)motionEvent.getY()) : null;
            }
            if ((view2 = this.mInitialTouchTarget) == null) {
                return false;
            }
            float f = view.getScrollX() - view2.getLeft();
            float f2 = view.getScrollY() - view2.getTop();
            motionEvent.offsetLocation(f, f2);
            boolean bl = view2.dispatchTouchEvent(motionEvent);
            motionEvent.offsetLocation(-f, -f2);
            if (n == 1 || n == 3) {
                this.mInitialTouchTarget = null;
            }
            return bl;
        }
    }

}

