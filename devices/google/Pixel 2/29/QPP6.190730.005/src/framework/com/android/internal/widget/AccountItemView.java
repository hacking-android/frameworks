/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.widget.AccountViewAdapter;

public class AccountItemView
extends LinearLayout {
    private ImageView mAccountIcon;
    private TextView mAccountName;
    private TextView mAccountNumber;

    public AccountItemView(Context context) {
        this(context, null);
    }

    public AccountItemView(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        object = ((LayoutInflater)((Context)object).getSystemService("layout_inflater")).inflate(17367289, null);
        this.addView((View)object);
        this.initViewItem((View)object);
    }

    private void initViewItem(View view) {
        this.mAccountIcon = (ImageView)view.findViewById(16908294);
        this.mAccountName = (TextView)view.findViewById(16908310);
        this.mAccountNumber = (TextView)view.findViewById(16908304);
    }

    private void setText(TextView textView, String string2) {
        if (TextUtils.isEmpty(string2)) {
            textView.setVisibility(8);
        } else {
            textView.setText(string2);
            textView.setVisibility(0);
        }
    }

    public void setAccountIcon(int n) {
        this.mAccountIcon.setImageResource(n);
    }

    public void setAccountIcon(Drawable drawable2) {
        this.mAccountIcon.setBackgroundDrawable(drawable2);
    }

    public void setAccountName(String string2) {
        this.setText(this.mAccountName, string2);
    }

    public void setAccountNumber(String string2) {
        this.setText(this.mAccountNumber, string2);
    }

    public void setViewItem(AccountViewAdapter.AccountElements accountElements) {
        Drawable drawable2 = accountElements.getDrawable();
        if (drawable2 != null) {
            this.setAccountIcon(drawable2);
        } else {
            this.setAccountIcon(accountElements.getIcon());
        }
        this.setAccountName(accountElements.getName());
        this.setAccountNumber(accountElements.getNumber());
    }
}

