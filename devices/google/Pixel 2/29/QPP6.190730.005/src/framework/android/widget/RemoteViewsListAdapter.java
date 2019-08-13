/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RemoteViews;
import java.util.ArrayList;

public class RemoteViewsListAdapter
extends BaseAdapter {
    private Context mContext;
    private ArrayList<RemoteViews> mRemoteViewsList;
    private int mViewTypeCount;
    private ArrayList<Integer> mViewTypes = new ArrayList();

    public RemoteViewsListAdapter(Context context, ArrayList<RemoteViews> arrayList, int n) {
        this.mContext = context;
        this.mRemoteViewsList = arrayList;
        this.mViewTypeCount = n;
        this.init();
    }

    private void init() {
        int n;
        if (this.mRemoteViewsList == null) {
            return;
        }
        this.mViewTypes.clear();
        for (RemoteViews remoteViews : this.mRemoteViewsList) {
            if (this.mViewTypes.contains(remoteViews.getLayoutId())) continue;
            this.mViewTypes.add(remoteViews.getLayoutId());
        }
        int n2 = this.mViewTypes.size();
        if (n2 <= (n = this.mViewTypeCount) && n >= 1) {
            return;
        }
        throw new RuntimeException("Invalid view type count -- view type count must be >= 1and must be as large as the total number of distinct view types");
    }

    @Override
    public int getCount() {
        ArrayList<RemoteViews> arrayList = this.mRemoteViewsList;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int n) {
        return null;
    }

    @Override
    public long getItemId(int n) {
        return n;
    }

    @Override
    public int getItemViewType(int n) {
        if (n < this.getCount()) {
            n = this.mRemoteViewsList.get(n).getLayoutId();
            return this.mViewTypes.indexOf(n);
        }
        return 0;
    }

    @Override
    public View getView(int n, View view, ViewGroup viewGroup) {
        if (n < this.getCount()) {
            RemoteViews remoteViews = this.mRemoteViewsList.get(n);
            remoteViews.addFlags(2);
            if (view != null && view.getId() == remoteViews.getLayoutId()) {
                remoteViews.reapply(this.mContext, view);
            } else {
                view = remoteViews.apply(this.mContext, viewGroup);
            }
            return view;
        }
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return this.mViewTypeCount;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public void setViewsList(ArrayList<RemoteViews> arrayList) {
        this.mRemoteViewsList = arrayList;
        this.init();
        this.notifyDataSetChanged();
    }
}

