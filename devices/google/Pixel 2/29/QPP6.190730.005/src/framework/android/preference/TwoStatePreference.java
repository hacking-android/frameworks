/*
 * Decompiled with CFR 0.145.
 */
package android.preference;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import android.view.View;
import android.widget.TextView;

@Deprecated
public abstract class TwoStatePreference
extends Preference {
    boolean mChecked;
    private boolean mCheckedSet;
    private boolean mDisableDependentsState;
    private CharSequence mSummaryOff;
    private CharSequence mSummaryOn;

    public TwoStatePreference(Context context) {
        this(context, null);
    }

    public TwoStatePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TwoStatePreference(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public TwoStatePreference(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    public boolean getDisableDependentsState() {
        return this.mDisableDependentsState;
    }

    public CharSequence getSummaryOff() {
        return this.mSummaryOff;
    }

    public CharSequence getSummaryOn() {
        return this.mSummaryOn;
    }

    public boolean isChecked() {
        return this.mChecked;
    }

    @Override
    protected void onClick() {
        super.onClick();
        boolean bl = this.isChecked() ^ true;
        if (this.callChangeListener(bl)) {
            this.setChecked(bl);
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray typedArray, int n) {
        return typedArray.getBoolean(n, false);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable != null && parcelable.getClass().equals(SavedState.class)) {
            parcelable = (SavedState)parcelable;
            super.onRestoreInstanceState(((AbsSavedState)parcelable).getSuperState());
            this.setChecked(((SavedState)parcelable).checked);
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        if (this.isPersistent()) {
            return parcelable;
        }
        parcelable = new SavedState(parcelable);
        ((SavedState)parcelable).checked = this.isChecked();
        return parcelable;
    }

    @Override
    protected void onSetInitialValue(boolean bl, Object object) {
        bl = bl ? this.getPersistedBoolean(this.mChecked) : ((Boolean)object).booleanValue();
        this.setChecked(bl);
    }

    public void setChecked(boolean bl) {
        boolean bl2 = this.mChecked != bl;
        if (bl2 || !this.mCheckedSet) {
            this.mChecked = bl;
            this.mCheckedSet = true;
            this.persistBoolean(bl);
            if (bl2) {
                this.notifyDependencyChange(this.shouldDisableDependents());
                this.notifyChanged();
            }
        }
    }

    public void setDisableDependentsState(boolean bl) {
        this.mDisableDependentsState = bl;
    }

    public void setSummaryOff(int n) {
        this.setSummaryOff(this.getContext().getString(n));
    }

    public void setSummaryOff(CharSequence charSequence) {
        this.mSummaryOff = charSequence;
        if (!this.isChecked()) {
            this.notifyChanged();
        }
    }

    public void setSummaryOn(int n) {
        this.setSummaryOn(this.getContext().getString(n));
    }

    public void setSummaryOn(CharSequence charSequence) {
        this.mSummaryOn = charSequence;
        if (this.isChecked()) {
            this.notifyChanged();
        }
    }

    @Override
    public boolean shouldDisableDependents() {
        boolean bl = this.mDisableDependentsState;
        boolean bl2 = true;
        bl = bl ? this.mChecked : !this.mChecked;
        boolean bl3 = bl2;
        if (!bl) {
            bl3 = super.shouldDisableDependents() ? bl2 : false;
        }
        return bl3;
    }

    @UnsupportedAppUsage
    void syncSummaryView(View view) {
        if ((view = (TextView)view.findViewById(16908304)) != null) {
            int n;
            int n2 = 1;
            if (this.mChecked && !TextUtils.isEmpty(this.mSummaryOn)) {
                ((TextView)view).setText(this.mSummaryOn);
                n = 0;
            } else {
                n = n2;
                if (!this.mChecked) {
                    n = n2;
                    if (!TextUtils.isEmpty(this.mSummaryOff)) {
                        ((TextView)view).setText(this.mSummaryOff);
                        n = 0;
                    }
                }
            }
            n2 = n;
            if (n != 0) {
                CharSequence charSequence = this.getSummary();
                n2 = n;
                if (!TextUtils.isEmpty(charSequence)) {
                    ((TextView)view).setText(charSequence);
                    n2 = 0;
                }
            }
            n = 8;
            if (n2 == 0) {
                n = 0;
            }
            if (n != view.getVisibility()) {
                view.setVisibility(n);
            }
        }
    }

    static class SavedState
    extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        boolean checked;

        public SavedState(Parcel parcel) {
            super(parcel);
            int n = parcel.readInt();
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            this.checked = bl;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt((int)this.checked);
        }

    }

}

