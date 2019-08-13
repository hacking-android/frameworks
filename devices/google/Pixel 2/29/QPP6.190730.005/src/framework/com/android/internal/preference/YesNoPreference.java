/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.AbsSavedState;

public class YesNoPreference
extends DialogPreference {
    private boolean mWasPositiveResult;

    public YesNoPreference(Context context) {
        this(context, null);
    }

    public YesNoPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842896);
    }

    public YesNoPreference(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public YesNoPreference(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    public boolean getValue() {
        return this.mWasPositiveResult;
    }

    @Override
    protected void onDialogClosed(boolean bl) {
        super.onDialogClosed(bl);
        if (this.callChangeListener(bl)) {
            this.setValue(bl);
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray typedArray, int n) {
        return typedArray.getBoolean(n, false);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(((AbsSavedState)parcelable).getSuperState());
        this.setValue(((SavedState)parcelable).wasPositiveResult);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        if (this.isPersistent()) {
            return parcelable;
        }
        parcelable = new SavedState(parcelable);
        ((SavedState)parcelable).wasPositiveResult = this.getValue();
        return parcelable;
    }

    @Override
    protected void onSetInitialValue(boolean bl, Object object) {
        bl = bl ? this.getPersistedBoolean(this.mWasPositiveResult) : ((Boolean)object).booleanValue();
        this.setValue(bl);
    }

    public void setValue(boolean bl) {
        this.mWasPositiveResult = bl;
        this.persistBoolean(bl);
        this.notifyDependencyChange(bl ^ true);
    }

    @Override
    public boolean shouldDisableDependents() {
        boolean bl = !this.mWasPositiveResult || super.shouldDisableDependents();
        return bl;
    }

    private static class SavedState
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
        boolean wasPositiveResult;

        public SavedState(Parcel parcel) {
            super(parcel);
            int n = parcel.readInt();
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            this.wasPositiveResult = bl;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt((int)this.wasPositiveResult);
        }

    }

}

