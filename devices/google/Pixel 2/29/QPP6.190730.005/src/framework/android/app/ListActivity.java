/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListActivity
extends Activity {
    protected ListAdapter mAdapter;
    private boolean mFinishedStart = false;
    private Handler mHandler = new Handler();
    protected ListView mList;
    private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
            ListActivity.this.onListItemClick((ListView)adapterView, view, n, l);
        }
    };
    private Runnable mRequestFocus = new Runnable(){

        @Override
        public void run() {
            ListActivity.this.mList.focusableViewAvailable(ListActivity.this.mList);
        }
    };

    private void ensureList() {
        if (this.mList != null) {
            return;
        }
        this.setContentView(17367177);
    }

    public ListAdapter getListAdapter() {
        return this.mAdapter;
    }

    public ListView getListView() {
        this.ensureList();
        return this.mList;
    }

    public long getSelectedItemId() {
        return this.mList.getSelectedItemId();
    }

    public int getSelectedItemPosition() {
        return this.mList.getSelectedItemPosition();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        Object t = this.findViewById(16908292);
        ListView listView = this.mList = (ListView)this.findViewById(16908298);
        if (listView != null) {
            if (t != null) {
                listView.setEmptyView((View)t);
            }
            this.mList.setOnItemClickListener(this.mOnClickListener);
            if (this.mFinishedStart) {
                this.setListAdapter(this.mAdapter);
            }
            this.mHandler.post(this.mRequestFocus);
            this.mFinishedStart = true;
            return;
        }
        throw new RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");
    }

    @Override
    protected void onDestroy() {
        this.mHandler.removeCallbacks(this.mRequestFocus);
        super.onDestroy();
    }

    protected void onListItemClick(ListView listView, View view, int n, long l) {
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
    public void setListAdapter(ListAdapter listAdapter) {
        synchronized (this) {
            this.ensureList();
            this.mAdapter = listAdapter;
            this.mList.setAdapter(listAdapter);
            return;
        }
    }

    public void setSelection(int n) {
        this.mList.setSelection(n);
    }

}

