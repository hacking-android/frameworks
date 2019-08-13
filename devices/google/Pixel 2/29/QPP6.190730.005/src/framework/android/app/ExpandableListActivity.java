/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

public class ExpandableListActivity
extends Activity
implements View.OnCreateContextMenuListener,
ExpandableListView.OnChildClickListener,
ExpandableListView.OnGroupCollapseListener,
ExpandableListView.OnGroupExpandListener {
    ExpandableListAdapter mAdapter;
    boolean mFinishedStart = false;
    ExpandableListView mList;

    private void ensureList() {
        if (this.mList != null) {
            return;
        }
        this.setContentView(17367041);
    }

    public ExpandableListAdapter getExpandableListAdapter() {
        return this.mAdapter;
    }

    public ExpandableListView getExpandableListView() {
        this.ensureList();
        return this.mList;
    }

    public long getSelectedId() {
        return this.mList.getSelectedId();
    }

    public long getSelectedPosition() {
        return this.mList.getSelectedPosition();
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int n, int n2, long l) {
        return false;
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        Object t = this.findViewById(16908292);
        ExpandableListView expandableListView = this.mList = (ExpandableListView)this.findViewById(16908298);
        if (expandableListView != null) {
            if (t != null) {
                expandableListView.setEmptyView((View)t);
            }
            this.mList.setOnChildClickListener(this);
            this.mList.setOnGroupExpandListener(this);
            this.mList.setOnGroupCollapseListener(this);
            if (this.mFinishedStart) {
                this.setListAdapter(this.mAdapter);
            }
            this.mFinishedStart = true;
            return;
        }
        throw new RuntimeException("Your content must have a ExpandableListView whose id attribute is 'android.R.id.list'");
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
    }

    @Override
    public void onGroupCollapse(int n) {
    }

    @Override
    public void onGroupExpand(int n) {
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        this.ensureList();
        super.onRestoreInstanceState(bundle);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setListAdapter(ExpandableListAdapter expandableListAdapter) {
        synchronized (this) {
            this.ensureList();
            this.mAdapter = expandableListAdapter;
            this.mList.setAdapter(expandableListAdapter);
            return;
        }
    }

    public boolean setSelectedChild(int n, int n2, boolean bl) {
        return this.mList.setSelectedChild(n, n2, bl);
    }

    public void setSelectedGroup(int n) {
        this.mList.setSelectedGroup(n);
    }
}

