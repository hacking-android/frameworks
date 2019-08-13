/*
 * Decompiled with CFR 0.145.
 */
package android.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.TwoStatePreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import com.android.internal.R;

@Deprecated
public class CheckBoxPreference
extends TwoStatePreference {
    public CheckBoxPreference(Context context) {
        this(context, null);
    }

    public CheckBoxPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842895);
    }

    public CheckBoxPreference(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public CheckBoxPreference(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.CheckBoxPreference, n, n2);
        this.setSummaryOn(((TypedArray)object).getString(0));
        this.setSummaryOff(((TypedArray)object).getString(1));
        this.setDisableDependentsState(((TypedArray)object).getBoolean(2, false));
        ((TypedArray)object).recycle();
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        Object t = view.findViewById(16908289);
        if (t != null && t instanceof Checkable) {
            ((Checkable)t).setChecked(this.mChecked);
        }
        this.syncSummaryView(view);
    }
}

