/*
 * Decompiled with CFR 0.145.
 */
package android.preference;

import android.annotation.UnsupportedAppUsage;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import com.android.internal.R;

@Deprecated
public abstract class DialogPreference
extends Preference
implements DialogInterface.OnClickListener,
DialogInterface.OnDismissListener,
PreferenceManager.OnActivityDestroyListener {
    @UnsupportedAppUsage
    private AlertDialog.Builder mBuilder;
    @UnsupportedAppUsage
    private Dialog mDialog;
    @UnsupportedAppUsage
    private Drawable mDialogIcon;
    private int mDialogLayoutResId;
    @UnsupportedAppUsage
    private CharSequence mDialogMessage;
    @UnsupportedAppUsage
    private CharSequence mDialogTitle;
    private final Runnable mDismissRunnable = new Runnable(){

        @Override
        public void run() {
            DialogPreference.this.mDialog.dismiss();
        }
    };
    @UnsupportedAppUsage
    private CharSequence mNegativeButtonText;
    @UnsupportedAppUsage
    private CharSequence mPositiveButtonText;
    @UnsupportedAppUsage
    private int mWhichButtonClicked;

    public DialogPreference(Context context) {
        this(context, null);
    }

    public DialogPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842897);
    }

    public DialogPreference(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public DialogPreference(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.DialogPreference, n, n2);
        this.mDialogTitle = ((TypedArray)object).getString(0);
        if (this.mDialogTitle == null) {
            this.mDialogTitle = this.getTitle();
        }
        this.mDialogMessage = ((TypedArray)object).getString(1);
        this.mDialogIcon = ((TypedArray)object).getDrawable(2);
        this.mPositiveButtonText = ((TypedArray)object).getString(3);
        this.mNegativeButtonText = ((TypedArray)object).getString(4);
        this.mDialogLayoutResId = ((TypedArray)object).getResourceId(5, this.mDialogLayoutResId);
        ((TypedArray)object).recycle();
    }

    private View getDecorView() {
        Dialog dialog = this.mDialog;
        if (dialog != null && dialog.getWindow() != null) {
            return this.mDialog.getWindow().getDecorView();
        }
        return null;
    }

    private void removeDismissCallbacks() {
        View view = this.getDecorView();
        if (view != null) {
            view.removeCallbacks(this.mDismissRunnable);
        }
    }

    private void requestInputMethod(Dialog dialog) {
        dialog.getWindow().setSoftInputMode(5);
    }

    public Dialog getDialog() {
        return this.mDialog;
    }

    public Drawable getDialogIcon() {
        return this.mDialogIcon;
    }

    public int getDialogLayoutResource() {
        return this.mDialogLayoutResId;
    }

    public CharSequence getDialogMessage() {
        return this.mDialogMessage;
    }

    public CharSequence getDialogTitle() {
        return this.mDialogTitle;
    }

    public CharSequence getNegativeButtonText() {
        return this.mNegativeButtonText;
    }

    public CharSequence getPositiveButtonText() {
        return this.mPositiveButtonText;
    }

    protected boolean needInputMethod() {
        return false;
    }

    @Override
    public void onActivityDestroy() {
        Dialog dialog = this.mDialog;
        if (dialog != null && dialog.isShowing()) {
            this.mDialog.dismiss();
            return;
        }
    }

    protected void onBindDialogView(View object) {
        Object t = ((View)object).findViewById(16908299);
        if (t != null) {
            object = this.getDialogMessage();
            int n = 8;
            if (!TextUtils.isEmpty((CharSequence)object)) {
                if (t instanceof TextView) {
                    ((TextView)t).setText((CharSequence)object);
                }
                n = 0;
            }
            if (((View)t).getVisibility() != n) {
                ((View)t).setVisibility(n);
            }
        }
    }

    @Override
    protected void onClick() {
        Dialog dialog = this.mDialog;
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        this.showDialog(null);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int n) {
        this.mWhichButtonClicked = n;
    }

    protected View onCreateDialogView() {
        if (this.mDialogLayoutResId == 0) {
            return null;
        }
        return LayoutInflater.from(this.mBuilder.getContext()).inflate(this.mDialogLayoutResId, null);
    }

    protected void onDialogClosed(boolean bl) {
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        this.removeDismissCallbacks();
        this.getPreferenceManager().unregisterOnActivityDestroyListener(this);
        this.mDialog = null;
        boolean bl = this.mWhichButtonClicked == -1;
        this.onDialogClosed(bl);
    }

    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
    }

    @Override
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable != null && parcelable.getClass().equals(SavedState.class)) {
            parcelable = (SavedState)parcelable;
            super.onRestoreInstanceState(((AbsSavedState)parcelable).getSuperState());
            if (((SavedState)parcelable).isDialogShowing) {
                this.showDialog(((SavedState)parcelable).dialogBundle);
            }
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        Dialog dialog = this.mDialog;
        if (dialog != null && dialog.isShowing()) {
            parcelable = new SavedState(parcelable);
            ((SavedState)parcelable).isDialogShowing = true;
            ((SavedState)parcelable).dialogBundle = this.mDialog.onSaveInstanceState();
            return parcelable;
        }
        return parcelable;
    }

    void postDismiss() {
        this.removeDismissCallbacks();
        View view = this.getDecorView();
        if (view != null) {
            view.post(this.mDismissRunnable);
        }
    }

    public void setDialogIcon(int n) {
        this.mDialogIcon = this.getContext().getDrawable(n);
    }

    public void setDialogIcon(Drawable drawable2) {
        this.mDialogIcon = drawable2;
    }

    public void setDialogLayoutResource(int n) {
        this.mDialogLayoutResId = n;
    }

    public void setDialogMessage(int n) {
        this.setDialogMessage(this.getContext().getString(n));
    }

    public void setDialogMessage(CharSequence charSequence) {
        this.mDialogMessage = charSequence;
    }

    public void setDialogTitle(int n) {
        this.setDialogTitle(this.getContext().getString(n));
    }

    public void setDialogTitle(CharSequence charSequence) {
        this.mDialogTitle = charSequence;
    }

    public void setNegativeButtonText(int n) {
        this.setNegativeButtonText(this.getContext().getString(n));
    }

    public void setNegativeButtonText(CharSequence charSequence) {
        this.mNegativeButtonText = charSequence;
    }

    public void setPositiveButtonText(int n) {
        this.setPositiveButtonText(this.getContext().getString(n));
    }

    public void setPositiveButtonText(CharSequence charSequence) {
        this.mPositiveButtonText = charSequence;
    }

    protected void showDialog(Bundle bundle) {
        Object object = this.getContext();
        this.mWhichButtonClicked = -2;
        this.mBuilder = new AlertDialog.Builder((Context)object).setTitle(this.mDialogTitle).setIcon(this.mDialogIcon).setPositiveButton(this.mPositiveButtonText, (DialogInterface.OnClickListener)this).setNegativeButton(this.mNegativeButtonText, (DialogInterface.OnClickListener)this);
        object = this.onCreateDialogView();
        if (object != null) {
            this.onBindDialogView((View)object);
            this.mBuilder.setView((View)object);
        } else {
            this.mBuilder.setMessage(this.mDialogMessage);
        }
        this.onPrepareDialogBuilder(this.mBuilder);
        this.getPreferenceManager().registerOnActivityDestroyListener(this);
        this.mDialog = object = this.mBuilder.create();
        if (bundle != null) {
            ((Dialog)object).onRestoreInstanceState(bundle);
        }
        if (this.needInputMethod()) {
            this.requestInputMethod((Dialog)object);
        }
        ((Dialog)object).setOnShowListener(new DialogInterface.OnShowListener(){

            @Override
            public void onShow(DialogInterface dialogInterface) {
                DialogPreference.this.removeDismissCallbacks();
            }
        });
        ((Dialog)object).setOnDismissListener(this);
        ((Dialog)object).show();
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
        Bundle dialogBundle;
        boolean isDialogShowing;

        public SavedState(Parcel parcel) {
            super(parcel);
            int n = parcel.readInt();
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            this.isDialogShowing = bl;
            this.dialogBundle = parcel.readBundle();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt((int)this.isDialogShowing);
            parcel.writeBundle(this.dialogBundle);
        }

    }

}

