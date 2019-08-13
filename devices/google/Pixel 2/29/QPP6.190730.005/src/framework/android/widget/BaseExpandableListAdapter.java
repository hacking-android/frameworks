/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.widget.ExpandableListAdapter;
import android.widget.HeterogeneousExpandableList;

public abstract class BaseExpandableListAdapter
implements ExpandableListAdapter,
HeterogeneousExpandableList {
    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public int getChildType(int n, int n2) {
        return 0;
    }

    @Override
    public int getChildTypeCount() {
        return 1;
    }

    @Override
    public long getCombinedChildId(long l, long l2) {
        return (Integer.MAX_VALUE & l) << 32 | Long.MIN_VALUE | -1L & l2;
    }

    @Override
    public long getCombinedGroupId(long l) {
        return (Integer.MAX_VALUE & l) << 32;
    }

    @Override
    public int getGroupType(int n) {
        return 0;
    }

    @Override
    public int getGroupTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        boolean bl = this.getGroupCount() == 0;
        return bl;
    }

    public void notifyDataSetChanged() {
        this.mDataSetObservable.notifyChanged();
    }

    public void notifyDataSetInvalidated() {
        this.mDataSetObservable.notifyInvalidated();
    }

    @Override
    public void onGroupCollapsed(int n) {
    }

    @Override
    public void onGroupExpanded(int n) {
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        this.mDataSetObservable.registerObserver(dataSetObserver);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        this.mDataSetObservable.unregisterObserver(dataSetObserver);
    }
}

