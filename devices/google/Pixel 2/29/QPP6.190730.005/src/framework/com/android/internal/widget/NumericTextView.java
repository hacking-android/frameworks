/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.KeyEvent;
import android.widget.TextView;

public class NumericTextView
extends TextView {
    private static final double LOG_RADIX = Math.log(10.0);
    private static final int RADIX = 10;
    private int mCount;
    private OnValueChangedListener mListener;
    private int mMaxCount = 2;
    private int mMaxValue = 99;
    private int mMinValue = 0;
    private int mPreviousValue;
    private boolean mShowLeadingZeroes = true;
    private int mValue;

    @UnsupportedAppUsage
    public NumericTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setHintTextColor(this.getTextColors().getColorForState(StateSet.get(0), 0));
        this.setFocusable(true);
    }

    private boolean handleKeyUp(int n) {
        block13 : {
            boolean bl;
            CharSequence charSequence;
            block12 : {
                block11 : {
                    bl = false;
                    if (n != 67) break block11;
                    n = this.mCount;
                    if (n > 0) {
                        this.mValue /= 10;
                        this.mCount = n - 1;
                    }
                    break block12;
                }
                if (!NumericTextView.isKeyCodeNumeric(n)) break block13;
                if (this.mCount < this.mMaxCount) {
                    n = NumericTextView.numericKeyCodeToInt(n);
                    if ((n = this.mValue * 10 + n) <= this.mMaxValue) {
                        this.mValue = n;
                        ++this.mCount;
                    }
                }
            }
            if (this.mCount > 0) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("%0");
                ((StringBuilder)charSequence).append(this.mCount);
                ((StringBuilder)charSequence).append("d");
                charSequence = String.format(((StringBuilder)charSequence).toString(), this.mValue);
            } else {
                charSequence = "";
            }
            this.setText(charSequence);
            if (this.mListener != null) {
                boolean bl2 = this.mValue >= this.mMinValue;
                if (this.mCount >= this.mMaxCount || this.mValue * 10 > this.mMaxValue) {
                    bl = true;
                }
                this.mListener.onValueChanged(this, this.mValue, bl2, bl);
            }
            return true;
        }
        return false;
    }

    private static boolean isKeyCodeNumeric(int n) {
        boolean bl = n == 7 || n == 8 || n == 9 || n == 10 || n == 11 || n == 12 || n == 13 || n == 14 || n == 15 || n == 16;
        return bl;
    }

    private static int numericKeyCodeToInt(int n) {
        return n - 7;
    }

    private void updateDisplayedValue() {
        CharSequence charSequence;
        if (this.mShowLeadingZeroes) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("%0");
            ((StringBuilder)charSequence).append(this.mMaxCount);
            ((StringBuilder)charSequence).append("d");
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = "%d";
        }
        this.setText(String.format((String)charSequence, this.mValue));
    }

    private void updateMinimumWidth() {
        CharSequence charSequence = this.getText();
        int n = 0;
        for (int i = 0; i < this.mMaxValue; ++i) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("%0");
            stringBuilder.append(this.mMaxCount);
            stringBuilder.append("d");
            this.setText(String.format(stringBuilder.toString(), i));
            this.measure(0, 0);
            int n2 = this.getMeasuredWidth();
            int n3 = n;
            if (n2 > n) {
                n3 = n2;
            }
            n = n3;
        }
        this.setText(charSequence);
        this.setMinWidth(n);
        this.setMinimumWidth(n);
    }

    public final OnValueChangedListener getOnDigitEnteredListener() {
        return this.mListener;
    }

    public final int getRangeMaximum() {
        return this.mMaxValue;
    }

    public final int getRangeMinimum() {
        return this.mMinValue;
    }

    public final boolean getShowLeadingZeroes() {
        return this.mShowLeadingZeroes;
    }

    public final int getValue() {
        return this.mValue;
    }

    @Override
    protected void onFocusChanged(boolean bl, int n, Rect object) {
        super.onFocusChanged(bl, n, (Rect)object);
        if (bl) {
            this.mPreviousValue = this.mValue;
            this.mValue = 0;
            this.mCount = 0;
            this.setHint(this.getText());
            this.setText("");
        } else {
            int n2;
            if (this.mCount == 0) {
                this.mValue = this.mPreviousValue;
                this.setText(this.getHint());
                this.setHint("");
            }
            if ((n = this.mValue) < (n2 = this.mMinValue)) {
                this.mValue = n2;
            }
            this.setValue(this.mValue);
            object = this.mListener;
            if (object != null) {
                object.onValueChanged(this, this.mValue, true, true);
            }
        }
    }

    @Override
    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        boolean bl = NumericTextView.isKeyCodeNumeric(n) || n == 67 || super.onKeyDown(n, keyEvent);
        return bl;
    }

    @Override
    public boolean onKeyMultiple(int n, int n2, KeyEvent keyEvent) {
        boolean bl = NumericTextView.isKeyCodeNumeric(n) || n == 67 || super.onKeyMultiple(n, n2, keyEvent);
        return bl;
    }

    @Override
    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        boolean bl = this.handleKeyUp(n) || super.onKeyUp(n, keyEvent);
        return bl;
    }

    public final void setOnDigitEnteredListener(OnValueChangedListener onValueChangedListener) {
        this.mListener = onValueChangedListener;
    }

    public final void setRange(int n, int n2) {
        if (this.mMinValue != n) {
            this.mMinValue = n;
        }
        if (this.mMaxValue != n2) {
            this.mMaxValue = n2;
            this.mMaxCount = (int)(Math.log(n2) / LOG_RADIX) + 1;
            this.updateMinimumWidth();
            this.updateDisplayedValue();
        }
    }

    public final void setShowLeadingZeroes(boolean bl) {
        if (this.mShowLeadingZeroes != bl) {
            this.mShowLeadingZeroes = bl;
            this.updateDisplayedValue();
        }
    }

    public final void setValue(int n) {
        if (this.mValue != n) {
            this.mValue = n;
            this.updateDisplayedValue();
        }
    }

    public static interface OnValueChangedListener {
        public void onValueChanged(NumericTextView var1, int var2, boolean var3, boolean var4);
    }

}

