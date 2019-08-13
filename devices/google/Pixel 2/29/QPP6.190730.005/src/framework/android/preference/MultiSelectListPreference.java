/*
 * Decompiled with CFR 0.145.
 */
package android.preference;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import com.android.internal.R;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Deprecated
public class MultiSelectListPreference
extends DialogPreference {
    private CharSequence[] mEntries;
    private CharSequence[] mEntryValues;
    private Set<String> mNewValues = new HashSet<String>();
    private boolean mPreferenceChanged;
    private Set<String> mValues = new HashSet<String>();

    public MultiSelectListPreference(Context context) {
        this(context, null);
    }

    public MultiSelectListPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842897);
    }

    public MultiSelectListPreference(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public MultiSelectListPreference(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.MultiSelectListPreference, n, n2);
        this.mEntries = ((TypedArray)object).getTextArray(0);
        this.mEntryValues = ((TypedArray)object).getTextArray(1);
        ((TypedArray)object).recycle();
    }

    static /* synthetic */ boolean access$076(MultiSelectListPreference multiSelectListPreference, int n) {
        boolean bl;
        multiSelectListPreference.mPreferenceChanged = bl = (byte)(multiSelectListPreference.mPreferenceChanged | n);
        return bl;
    }

    private boolean[] getSelectedItems() {
        CharSequence[] arrcharSequence = this.mEntryValues;
        int n = arrcharSequence.length;
        Set<String> set = this.mValues;
        boolean[] arrbl = new boolean[n];
        for (int i = 0; i < n; ++i) {
            arrbl[i] = set.contains(arrcharSequence[i].toString());
        }
        return arrbl;
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

    public CharSequence[] getEntryValues() {
        return this.mEntryValues;
    }

    public Set<String> getValues() {
        return this.mValues;
    }

    @Override
    protected void onDialogClosed(boolean bl) {
        Set<String> set;
        super.onDialogClosed(bl);
        if (bl && this.mPreferenceChanged && this.callChangeListener(set = this.mNewValues)) {
            this.setValues(set);
        }
        this.mPreferenceChanged = false;
    }

    @Override
    protected Object onGetDefaultValue(TypedArray arrcharSequence, int n) {
        arrcharSequence = arrcharSequence.getTextArray(n);
        int n2 = arrcharSequence.length;
        HashSet<String> hashSet = new HashSet<String>();
        for (n = 0; n < n2; ++n) {
            hashSet.add(arrcharSequence[n].toString());
        }
        return hashSet;
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);
        if (this.mEntries != null && this.mEntryValues != null) {
            boolean[] arrbl = this.getSelectedItems();
            builder.setMultiChoiceItems(this.mEntries, arrbl, new DialogInterface.OnMultiChoiceClickListener(){

                @Override
                public void onClick(DialogInterface object, int n, boolean bl) {
                    if (bl) {
                        object = MultiSelectListPreference.this;
                        MultiSelectListPreference.access$076((MultiSelectListPreference)object, (int)((MultiSelectListPreference)object).mNewValues.add(MultiSelectListPreference.this.mEntryValues[n].toString()));
                    } else {
                        object = MultiSelectListPreference.this;
                        MultiSelectListPreference.access$076((MultiSelectListPreference)object, (int)((MultiSelectListPreference)object).mNewValues.remove(MultiSelectListPreference.this.mEntryValues[n].toString()));
                    }
                }
            });
            this.mNewValues.clear();
            this.mNewValues.addAll(this.mValues);
            return;
        }
        throw new IllegalStateException("MultiSelectListPreference requires an entries array and an entryValues array.");
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        if (this.isPersistent()) {
            return parcelable;
        }
        parcelable = new SavedState(parcelable);
        ((SavedState)parcelable).values = this.getValues();
        return parcelable;
    }

    @Override
    protected void onSetInitialValue(boolean bl, Object set) {
        set = bl ? this.getPersistedStringSet(this.mValues) : (Set)set;
        this.setValues(set);
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

    public void setValues(Set<String> set) {
        this.mValues.clear();
        this.mValues.addAll(set);
        this.persistStringSet(set);
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
        Set<String> values;

        public SavedState(Parcel arrstring) {
            super((Parcel)arrstring);
            this.values = new HashSet<String>();
            arrstring = arrstring.readStringArray();
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                this.values.add(arrstring[i]);
            }
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeStringArray(this.values.toArray(new String[0]));
        }

    }

}

