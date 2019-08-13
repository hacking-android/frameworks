/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

public abstract class BaseAdapter
implements ListAdapter,
SpinnerAdapter {
    private CharSequence[] mAutofillOptions;
    @UnsupportedAppUsage
    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        return this.mAutofillOptions;
    }

    @Override
    public View getDropDownView(int n, View view, ViewGroup viewGroup) {
        return this.getView(n, view, viewGroup);
    }

    @Override
    public int getItemViewType(int n) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        boolean bl = this.getCount() == 0;
        return bl;
    }

    @Override
    public boolean isEnabled(int n) {
        return true;
    }

    public void notifyDataSetChanged() {
        this.mDataSetObservable.notifyChanged();
    }

    public void notifyDataSetInvalidated() {
        this.mDataSetObservable.notifyInvalidated();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        this.mDataSetObservable.registerObserver(dataSetObserver);
    }

    public void setAutofillOptions(CharSequence ... arrcharSequence) {
        this.mAutofillOptions = arrcharSequence;
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        this.mDataSetObservable.unregisterObserver(dataSetObserver);
    }
}

