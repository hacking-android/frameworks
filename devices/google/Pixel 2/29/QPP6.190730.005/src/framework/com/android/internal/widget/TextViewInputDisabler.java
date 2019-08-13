/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.annotation.UnsupportedAppUsage;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.TextView;

public class TextViewInputDisabler {
    private InputFilter[] mDefaultFilters;
    private InputFilter[] mNoInputFilters = new InputFilter[]{new InputFilter(){

        @Override
        public CharSequence filter(CharSequence charSequence, int n, int n2, Spanned spanned, int n3, int n4) {
            return "";
        }
    }};
    private TextView mTextView;

    @UnsupportedAppUsage
    public TextViewInputDisabler(TextView textView) {
        this.mTextView = textView;
        this.mDefaultFilters = this.mTextView.getFilters();
    }

    @UnsupportedAppUsage
    public void setInputEnabled(boolean bl) {
        TextView textView = this.mTextView;
        InputFilter[] arrinputFilter = bl ? this.mDefaultFilters : this.mNoInputFilters;
        textView.setFilters(arrinputFilter);
    }

}

