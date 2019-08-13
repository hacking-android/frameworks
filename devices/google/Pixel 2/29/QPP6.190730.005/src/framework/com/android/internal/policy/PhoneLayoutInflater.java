/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.policy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

public class PhoneLayoutInflater
extends LayoutInflater {
    private static final String[] sClassPrefixList = new String[]{"android.widget.", "android.webkit.", "android.app."};

    public PhoneLayoutInflater(Context context) {
        super(context);
    }

    protected PhoneLayoutInflater(LayoutInflater layoutInflater, Context context) {
        super(layoutInflater, context);
    }

    @Override
    public LayoutInflater cloneInContext(Context context) {
        return new PhoneLayoutInflater(this, context);
    }

    @Override
    protected View onCreateView(String string2, AttributeSet attributeSet) throws ClassNotFoundException {
        for (String classNotFoundException : sClassPrefixList) {
            try {
                View view = this.createView(string2, classNotFoundException, attributeSet);
                if (view == null) continue;
                return view;
            }
            catch (ClassNotFoundException classNotFoundException2) {
                // empty catch block
            }
        }
        return super.onCreateView(string2, attributeSet);
    }
}

