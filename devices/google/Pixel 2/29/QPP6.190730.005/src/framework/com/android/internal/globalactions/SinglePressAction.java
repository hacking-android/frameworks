/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.globalactions;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.globalactions.Action;

public abstract class SinglePressAction
implements Action {
    private final Drawable mIcon;
    private final int mIconResId;
    private final CharSequence mMessage;
    private final int mMessageResId;

    protected SinglePressAction(int n, int n2) {
        this.mIconResId = n;
        this.mMessageResId = n2;
        this.mMessage = null;
        this.mIcon = null;
    }

    protected SinglePressAction(int n, Drawable drawable2, CharSequence charSequence) {
        this.mIconResId = n;
        this.mMessageResId = 0;
        this.mMessage = charSequence;
        this.mIcon = drawable2;
    }

    @Override
    public View create(Context object, View view, ViewGroup view2, LayoutInflater object2) {
        view2 = ((LayoutInflater)object2).inflate(17367158, (ViewGroup)view2, false);
        object2 = (ImageView)view2.findViewById(16908294);
        view = (TextView)view2.findViewById(16908299);
        Object object3 = (TextView)view2.findViewById(16909405);
        String string2 = this.getStatus();
        if (object3 != null) {
            if (!TextUtils.isEmpty(string2)) {
                ((TextView)object3).setText(string2);
            } else {
                ((View)object3).setVisibility(8);
            }
        }
        if (object2 != null) {
            object3 = this.mIcon;
            if (object3 != null) {
                ((ImageView)object2).setImageDrawable((Drawable)object3);
                ((ImageView)object2).setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                int n = this.mIconResId;
                if (n != 0) {
                    ((ImageView)object2).setImageDrawable(((Context)object).getDrawable(n));
                }
            }
        }
        if (view != null) {
            object = this.mMessage;
            if (object != null) {
                ((TextView)view).setText((CharSequence)object);
            } else {
                ((TextView)view).setText(this.mMessageResId);
            }
        }
        return view2;
    }

    @Override
    public CharSequence getLabelForAccessibility(Context context) {
        CharSequence charSequence = this.mMessage;
        if (charSequence != null) {
            return charSequence;
        }
        return context.getString(this.mMessageResId);
    }

    public String getStatus() {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public abstract void onPress();
}

