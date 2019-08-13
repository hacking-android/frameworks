/*
 * Decompiled with CFR 0.145.
 */
package android.preference;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;

@Deprecated
public class EditTextPreference
extends DialogPreference {
    @UnsupportedAppUsage
    private EditText mEditText;
    private String mText;
    private boolean mTextSet;

    public EditTextPreference(Context context) {
        this(context, null);
    }

    public EditTextPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842898);
    }

    public EditTextPreference(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public EditTextPreference(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.mEditText = new EditText(context, attributeSet);
        this.mEditText.setId(16908291);
        this.mEditText.setEnabled(true);
    }

    public EditText getEditText() {
        return this.mEditText;
    }

    public String getText() {
        return this.mText;
    }

    @Override
    protected boolean needInputMethod() {
        return true;
    }

    protected void onAddEditTextToDialogView(View view, EditText editText) {
        if ((view = (ViewGroup)view.findViewById(16908890)) != null) {
            ((ViewGroup)view).addView((View)editText, -1, -2);
        }
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        EditText editText = this.mEditText;
        editText.setText(this.getText());
        ViewParent viewParent = editText.getParent();
        if (viewParent != view) {
            if (viewParent != null) {
                ((ViewGroup)viewParent).removeView(editText);
            }
            this.onAddEditTextToDialogView(view, editText);
        }
    }

    @Override
    protected void onDialogClosed(boolean bl) {
        String string2;
        super.onDialogClosed(bl);
        if (bl && this.callChangeListener(string2 = this.mEditText.getText().toString())) {
            this.setText(string2);
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray typedArray, int n) {
        return typedArray.getString(n);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable != null && parcelable.getClass().equals(SavedState.class)) {
            parcelable = (SavedState)parcelable;
            super.onRestoreInstanceState(((AbsSavedState)parcelable).getSuperState());
            this.setText(((SavedState)parcelable).text);
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
        ((SavedState)parcelable).text = this.getText();
        return parcelable;
    }

    @Override
    protected void onSetInitialValue(boolean bl, Object object) {
        object = bl ? this.getPersistedString(this.mText) : (String)object;
        this.setText((String)object);
    }

    public void setText(String string2) {
        boolean bl = TextUtils.equals(this.mText, string2) ^ true;
        if (bl || !this.mTextSet) {
            this.mText = string2;
            this.mTextSet = true;
            this.persistString(string2);
            if (bl) {
                this.notifyDependencyChange(this.shouldDisableDependents());
                this.notifyChanged();
            }
        }
    }

    @Override
    public boolean shouldDisableDependents() {
        boolean bl = TextUtils.isEmpty(this.mText) || super.shouldDisableDependents();
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
        String text;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.text = parcel.readString();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeString(this.text);
        }

    }

}

