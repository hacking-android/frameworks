/*
 * Decompiled with CFR 0.145.
 */
package android.preference;

import android.annotation.UnsupportedAppUsage;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import com.android.internal.R;

@Deprecated
public class ListPreference
extends DialogPreference {
    @UnsupportedAppUsage
    private int mClickedDialogEntryIndex;
    private CharSequence[] mEntries;
    private CharSequence[] mEntryValues;
    private String mSummary;
    private String mValue;
    private boolean mValueSet;

    public ListPreference(Context context) {
        this(context, null);
    }

    public ListPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842897);
    }

    public ListPreference(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ListPreference(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        TypedArray typedArray = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.ListPreference, n, n2);
        this.mEntries = typedArray.getTextArray(0);
        this.mEntryValues = typedArray.getTextArray(1);
        typedArray.recycle();
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.Preference, n, n2);
        this.mSummary = ((TypedArray)object).getString(7);
        ((TypedArray)object).recycle();
    }

    private int getValueIndex() {
        return this.findIndexOfValue(this.mValue);
    }

    public int findIndexOfValue(String string2) {
        CharSequence[] arrcharSequence;
        if (string2 != null && (arrcharSequence = this.mEntryValues) != null) {
            for (int i = arrcharSequence.length - 1; i >= 0; --i) {
                if (!this.mEntryValues[i].equals(string2)) continue;
                return i;
            }
        }
        return -1;
    }

    public CharSequence[] getEntries() {
        return this.mEntries;
    }

    public CharSequence getEntry() {
        Object object;
        int n = this.getValueIndex();
        object = n >= 0 && (object = this.mEntries) != null ? object[n] : null;
        return object;
    }

    public CharSequence[] getEntryValues() {
        return this.mEntryValues;
    }

    @Override
    public CharSequence getSummary() {
        CharSequence charSequence = this.getEntry();
        String string2 = this.mSummary;
        if (string2 == null) {
            return super.getSummary();
        }
        if (charSequence == null) {
            charSequence = "";
        }
        return String.format(string2, charSequence);
    }

    public String getValue() {
        return this.mValue;
    }

    @Override
    protected void onDialogClosed(boolean bl) {
        int n;
        Object object;
        super.onDialogClosed(bl);
        if (bl && (n = this.mClickedDialogEntryIndex) >= 0 && (object = this.mEntryValues) != null && this.callChangeListener(object = object[n].toString())) {
            this.setValue((String)object);
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray typedArray, int n) {
        return typedArray.getString(n);
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);
        if (this.mEntries != null && this.mEntryValues != null) {
            this.mClickedDialogEntryIndex = this.getValueIndex();
            builder.setSingleChoiceItems(this.mEntries, this.mClickedDialogEntryIndex, new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialogInterface, int n) {
                    ListPreference.this.mClickedDialogEntryIndex = n;
                    ListPreference.this.onClick(dialogInterface, -1);
                    ListPreference.this.postDismiss();
                }
            });
            builder.setPositiveButton(null, null);
            return;
        }
        throw new IllegalStateException("ListPreference requires an entries array and an entryValues array.");
    }

    @Override
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable != null && parcelable.getClass().equals(SavedState.class)) {
            parcelable = (SavedState)parcelable;
            super.onRestoreInstanceState(((AbsSavedState)parcelable).getSuperState());
            this.setValue(((SavedState)parcelable).value);
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
        ((SavedState)parcelable).value = this.getValue();
        return parcelable;
    }

    @Override
    protected void onSetInitialValue(boolean bl, Object object) {
        object = bl ? this.getPersistedString(this.mValue) : (String)object;
        this.setValue((String)object);
    }

    public void setEntries(int n) {
        this.setEntries(this.getContext().getResources().getTextArray(n));
    }

    public void setEntries(CharSequence[] arrcharSequence) {
        this.mEntries = arrcharSequence;
    }

    public void setEntryValues(int n) {
        this.setEntryValues(this.getContext().getResources().getTextArray(n));
    }

    public void setEntryValues(CharSequence[] arrcharSequence) {
        this.mEntryValues = arrcharSequence;
    }

    @Override
    public void setSummary(CharSequence charSequence) {
        super.setSummary(charSequence);
        if (charSequence == null && this.mSummary != null) {
            this.mSummary = null;
        } else if (charSequence != null && !charSequence.equals(this.mSummary)) {
            this.mSummary = charSequence.toString();
        }
    }

    public void setValue(String string2) {
        boolean bl = TextUtils.equals(this.mValue, string2) ^ true;
        if (bl || !this.mValueSet) {
            this.mValue = string2;
            this.mValueSet = true;
            this.persistString(string2);
            if (bl) {
                this.notifyChanged();
            }
        }
    }

    public void setValueIndex(int n) {
        CharSequence[] arrcharSequence = this.mEntryValues;
        if (arrcharSequence != null) {
            this.setValue(arrcharSequence[n].toString());
        }
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
        String value;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.value = parcel.readString();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeString(this.value);
        }

    }

}

