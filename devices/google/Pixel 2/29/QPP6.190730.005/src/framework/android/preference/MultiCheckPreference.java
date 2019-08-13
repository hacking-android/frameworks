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
import android.view.AbsSavedState;
import com.android.internal.R;
import java.util.Arrays;

@Deprecated
public class MultiCheckPreference
extends DialogPreference {
    private CharSequence[] mEntries;
    private String[] mEntryValues;
    private boolean[] mOrigValues;
    private boolean[] mSetValues;
    private String mSummary;

    public MultiCheckPreference(Context context) {
        this(context, null);
    }

    public MultiCheckPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842897);
    }

    public MultiCheckPreference(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public MultiCheckPreference(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        TypedArray typedArray = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.ListPreference, n, n2);
        this.mEntries = typedArray.getTextArray(0);
        CharSequence[] arrcharSequence = this.mEntries;
        if (arrcharSequence != null) {
            this.setEntries(arrcharSequence);
        }
        this.setEntryValuesCS(typedArray.getTextArray(1));
        typedArray.recycle();
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.Preference, 0, 0);
        this.mSummary = ((TypedArray)object).getString(7);
        ((TypedArray)object).recycle();
    }

    static /* synthetic */ boolean[] access$000(MultiCheckPreference multiCheckPreference) {
        return multiCheckPreference.mSetValues;
    }

    private void setEntryValuesCS(CharSequence[] arrcharSequence) {
        this.setValues(null);
        if (arrcharSequence != null) {
            this.mEntryValues = new String[arrcharSequence.length];
            for (int i = 0; i < arrcharSequence.length; ++i) {
                this.mEntryValues[i] = arrcharSequence[i].toString();
            }
        }
    }

    public int findIndexOfValue(String string2) {
        String[] arrstring;
        if (string2 != null && (arrstring = this.mEntryValues) != null) {
            for (int i = arrstring.length - 1; i >= 0; --i) {
                if (!this.mEntryValues[i].equals(string2)) continue;
                return i;
            }
        }
        return -1;
    }

    public CharSequence[] getEntries() {
        return this.mEntries;
    }

    public String[] getEntryValues() {
        return this.mEntryValues;
    }

    @Override
    public CharSequence getSummary() {
        String string2 = this.mSummary;
        if (string2 == null) {
            return super.getSummary();
        }
        return string2;
    }

    public boolean getValue(int n) {
        return this.mSetValues[n];
    }

    public boolean[] getValues() {
        return this.mSetValues;
    }

    @Override
    protected void onDialogClosed(boolean bl) {
        super.onDialogClosed(bl);
        if (bl && this.callChangeListener(this.getValues())) {
            return;
        }
        boolean[] arrbl = this.mOrigValues;
        boolean[] arrbl2 = this.mSetValues;
        System.arraycopy(arrbl, 0, arrbl2, 0, arrbl2.length);
    }

    @Override
    protected Object onGetDefaultValue(TypedArray typedArray, int n) {
        return typedArray.getString(n);
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);
        if (this.mEntries != null && this.mEntryValues != null) {
            boolean[] arrbl = this.mSetValues;
            this.mOrigValues = Arrays.copyOf(arrbl, arrbl.length);
            builder.setMultiChoiceItems(this.mEntries, this.mSetValues, new DialogInterface.OnMultiChoiceClickListener(){

                @Override
                public void onClick(DialogInterface dialogInterface, int n, boolean bl) {
                    MultiCheckPreference.access$000((MultiCheckPreference)MultiCheckPreference.this)[n] = bl;
                }
            });
            return;
        }
        throw new IllegalStateException("ListPreference requires an entries array and an entryValues array.");
    }

    @Override
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable != null && parcelable.getClass().equals(SavedState.class)) {
            parcelable = (SavedState)parcelable;
            super.onRestoreInstanceState(((AbsSavedState)parcelable).getSuperState());
            this.setValues(((SavedState)parcelable).values);
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
        ((SavedState)parcelable).values = this.getValues();
        return parcelable;
    }

    @Override
    protected void onSetInitialValue(boolean bl, Object object) {
    }

    public void setEntries(int n) {
        this.setEntries(this.getContext().getResources().getTextArray(n));
    }

    public void setEntries(CharSequence[] arrcharSequence) {
        this.mEntries = arrcharSequence;
        this.mSetValues = new boolean[arrcharSequence.length];
        this.mOrigValues = new boolean[arrcharSequence.length];
    }

    public void setEntryValues(int n) {
        this.setEntryValuesCS(this.getContext().getResources().getTextArray(n));
    }

    public void setEntryValues(String[] arrstring) {
        this.mEntryValues = arrstring;
        Arrays.fill(this.mSetValues, false);
        Arrays.fill(this.mOrigValues, false);
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

    public void setValue(int n, boolean bl) {
        this.mSetValues[n] = bl;
    }

    public void setValues(boolean[] arrbl) {
        boolean[] arrbl2 = this.mSetValues;
        if (arrbl2 != null) {
            Arrays.fill(arrbl2, false);
            Arrays.fill(this.mOrigValues, false);
            if (arrbl != null) {
                arrbl2 = this.mSetValues;
                int n = arrbl.length < arrbl2.length ? arrbl.length : arrbl2.length;
                System.arraycopy(arrbl, 0, arrbl2, 0, n);
            }
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
        boolean[] values;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.values = parcel.createBooleanArray();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeBooleanArray(this.values);
        }

    }

}

