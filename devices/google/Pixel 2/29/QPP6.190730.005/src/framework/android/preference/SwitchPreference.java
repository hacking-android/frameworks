/*
 * Decompiled with CFR 0.145.
 */
package android.preference;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.TypedArray;
import android.preference.TwoStatePreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.android.internal.R;

@Deprecated
public class SwitchPreference
extends TwoStatePreference {
    @UnsupportedAppUsage
    private final Listener mListener = new Listener();
    private CharSequence mSwitchOff;
    private CharSequence mSwitchOn;

    public SwitchPreference(Context context) {
        this(context, null);
    }

    public SwitchPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843629);
    }

    public SwitchPreference(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public SwitchPreference(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.SwitchPreference, n, n2);
        this.setSummaryOn(((TypedArray)object).getString(0));
        this.setSummaryOff(((TypedArray)object).getString(1));
        this.setSwitchTextOn(((TypedArray)object).getString(3));
        this.setSwitchTextOff(((TypedArray)object).getString(4));
        this.setDisableDependentsState(((TypedArray)object).getBoolean(2, false));
        ((TypedArray)object).recycle();
    }

    public CharSequence getSwitchTextOff() {
        return this.mSwitchOff;
    }

    public CharSequence getSwitchTextOn() {
        return this.mSwitchOn;
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        Object object = view.findViewById(16908352);
        if (object != null && object instanceof Checkable) {
            if (object instanceof Switch) {
                ((Switch)object).setOnCheckedChangeListener(null);
            }
            ((Checkable)object).setChecked(this.mChecked);
            if (object instanceof Switch) {
                object = (Switch)object;
                ((Switch)object).setTextOn(this.mSwitchOn);
                ((Switch)object).setTextOff(this.mSwitchOff);
                ((CompoundButton)object).setOnCheckedChangeListener(this.mListener);
            }
        }
        this.syncSummaryView(view);
    }

    public void setSwitchTextOff(int n) {
        this.setSwitchTextOff(this.getContext().getString(n));
    }

    public void setSwitchTextOff(CharSequence charSequence) {
        this.mSwitchOff = charSequence;
        this.notifyChanged();
    }

    public void setSwitchTextOn(int n) {
        this.setSwitchTextOn(this.getContext().getString(n));
    }

    public void setSwitchTextOn(CharSequence charSequence) {
        this.mSwitchOn = charSequence;
        this.notifyChanged();
    }

    private class Listener
    implements CompoundButton.OnCheckedChangeListener {
        private Listener() {
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
            if (!SwitchPreference.this.callChangeListener(bl)) {
                compoundButton.setChecked(bl ^ true);
                return;
            }
            SwitchPreference.this.setChecked(bl);
        }
    }

}

