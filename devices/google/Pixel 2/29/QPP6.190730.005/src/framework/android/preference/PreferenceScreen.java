/*
 * Decompiled with CFR 0.145.
 */
package android.preference;

import android.annotation.UnsupportedAppUsage;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.preference.PreferenceGroupAdapter;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.R;

@Deprecated
public final class PreferenceScreen
extends PreferenceGroup
implements AdapterView.OnItemClickListener,
DialogInterface.OnDismissListener {
    private Dialog mDialog;
    private Drawable mDividerDrawable;
    private boolean mDividerSpecified;
    private int mLayoutResId = 17367244;
    @UnsupportedAppUsage
    private ListView mListView;
    @UnsupportedAppUsage
    private ListAdapter mRootAdapter;

    @UnsupportedAppUsage
    public PreferenceScreen(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet, 16842891);
        object = ((Context)object).obtainStyledAttributes(null, R.styleable.PreferenceScreen, 16842891, 0);
        this.mLayoutResId = ((TypedArray)object).getResourceId(1, this.mLayoutResId);
        if (((TypedArray)object).hasValueOrEmpty(0)) {
            this.mDividerDrawable = ((TypedArray)object).getDrawable(0);
            this.mDividerSpecified = true;
        }
        ((TypedArray)object).recycle();
    }

    private void showDialog(Bundle bundle) {
        Object object = this.getContext();
        View view = this.mListView;
        if (view != null) {
            ((ListView)view).setAdapter(null);
        }
        view = ((LayoutInflater)((Context)object).getSystemService("layout_inflater")).inflate(this.mLayoutResId, null);
        Object t = view.findViewById(16908310);
        this.mListView = (ListView)view.findViewById(16908298);
        if (this.mDividerSpecified) {
            this.mListView.setDivider(this.mDividerDrawable);
        }
        this.bind(this.mListView);
        CharSequence charSequence = this.getTitle();
        this.mDialog = object = new Dialog((Context)object, ((Context)object).getThemeResId());
        if (TextUtils.isEmpty(charSequence)) {
            if (t != null) {
                ((View)t).setVisibility(8);
            }
            ((Dialog)object).getWindow().requestFeature(1);
        } else if (t instanceof TextView) {
            ((TextView)t).setText(charSequence);
            ((View)t).setVisibility(0);
        } else {
            ((Dialog)object).setTitle(charSequence);
        }
        ((Dialog)object).setContentView(view);
        ((Dialog)object).setOnDismissListener(this);
        if (bundle != null) {
            ((Dialog)object).onRestoreInstanceState(bundle);
        }
        this.getPreferenceManager().addPreferencesScreen((DialogInterface)object);
        ((Dialog)object).show();
    }

    public void bind(ListView listView) {
        listView.setOnItemClickListener(this);
        listView.setAdapter(this.getRootAdapter());
        this.onAttachedToActivity();
    }

    public Dialog getDialog() {
        return this.mDialog;
    }

    public ListAdapter getRootAdapter() {
        if (this.mRootAdapter == null) {
            this.mRootAdapter = this.onCreateRootAdapter();
        }
        return this.mRootAdapter;
    }

    @Override
    protected boolean isOnSameScreenAsChildren() {
        return false;
    }

    @Override
    protected void onClick() {
        if (this.getIntent() == null && this.getFragment() == null && this.getPreferenceCount() != 0) {
            this.showDialog(null);
            return;
        }
    }

    protected ListAdapter onCreateRootAdapter() {
        return new PreferenceGroupAdapter(this);
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        this.mDialog = null;
        this.getPreferenceManager().removePreferencesScreen(dialogInterface);
    }

    public void onItemClick(AdapterView object, View view, int n, long l) {
        int n2 = n;
        if (object instanceof ListView) {
            n2 = n - ((ListView)object).getHeaderViewsCount();
        }
        if (!((object = this.getRootAdapter().getItem(n2)) instanceof Preference)) {
            return;
        }
        ((Preference)object).performClick(this);
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
            ((SavedState)parcelable).dialogBundle = dialog.onSaveInstanceState();
            return parcelable;
        }
        return parcelable;
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

