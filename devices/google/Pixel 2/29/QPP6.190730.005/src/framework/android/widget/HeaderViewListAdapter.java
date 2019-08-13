/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.WrapperListAdapter;
import java.util.ArrayList;
import java.util.Iterator;

public class HeaderViewListAdapter
implements WrapperListAdapter,
Filterable {
    static final ArrayList<ListView.FixedViewInfo> EMPTY_INFO_LIST = new ArrayList();
    @UnsupportedAppUsage
    private final ListAdapter mAdapter;
    boolean mAreAllFixedViewsSelectable;
    @UnsupportedAppUsage
    ArrayList<ListView.FixedViewInfo> mFooterViewInfos;
    @UnsupportedAppUsage
    ArrayList<ListView.FixedViewInfo> mHeaderViewInfos;
    private final boolean mIsFilterable;

    public HeaderViewListAdapter(ArrayList<ListView.FixedViewInfo> arrayList, ArrayList<ListView.FixedViewInfo> arrayList2, ListAdapter listAdapter) {
        this.mAdapter = listAdapter;
        this.mIsFilterable = listAdapter instanceof Filterable;
        this.mHeaderViewInfos = arrayList == null ? EMPTY_INFO_LIST : arrayList;
        this.mFooterViewInfos = arrayList2 == null ? EMPTY_INFO_LIST : arrayList2;
        boolean bl = this.areAllListInfosSelectable(this.mHeaderViewInfos) && this.areAllListInfosSelectable(this.mFooterViewInfos);
        this.mAreAllFixedViewsSelectable = bl;
    }

    private boolean areAllListInfosSelectable(ArrayList<ListView.FixedViewInfo> object) {
        if (object != null) {
            object = ((ArrayList)object).iterator();
            while (object.hasNext()) {
                if (((ListView.FixedViewInfo)object.next()).isSelectable) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        ListAdapter listAdapter = this.mAdapter;
        boolean bl = true;
        if (listAdapter != null) {
            if (!this.mAreAllFixedViewsSelectable || !listAdapter.areAllItemsEnabled()) {
                bl = false;
            }
            return bl;
        }
        return true;
    }

    @Override
    public int getCount() {
        if (this.mAdapter != null) {
            return this.getFootersCount() + this.getHeadersCount() + this.mAdapter.getCount();
        }
        return this.getFootersCount() + this.getHeadersCount();
    }

    @Override
    public Filter getFilter() {
        if (this.mIsFilterable) {
            return ((Filterable)((Object)this.mAdapter)).getFilter();
        }
        return null;
    }

    public int getFootersCount() {
        return this.mFooterViewInfos.size();
    }

    public int getHeadersCount() {
        return this.mHeaderViewInfos.size();
    }

    @Override
    public Object getItem(int n) {
        int n2 = this.getHeadersCount();
        if (n < n2) {
            return this.mHeaderViewInfos.get((int)n).data;
        }
        int n3 = n - n2;
        n = 0;
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null) {
            n = n2 = listAdapter.getCount();
            if (n3 < n2) {
                return this.mAdapter.getItem(n3);
            }
        }
        return this.mFooterViewInfos.get((int)(n3 - n)).data;
    }

    @Override
    public long getItemId(int n) {
        int n2 = this.getHeadersCount();
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null && n >= n2 && (n -= n2) < listAdapter.getCount()) {
            return this.mAdapter.getItemId(n);
        }
        return -1L;
    }

    @Override
    public int getItemViewType(int n) {
        int n2 = this.getHeadersCount();
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null && n >= n2 && (n -= n2) < listAdapter.getCount()) {
            return this.mAdapter.getItemViewType(n);
        }
        return -2;
    }

    @Override
    public View getView(int n, View view, ViewGroup viewGroup) {
        int n2 = this.getHeadersCount();
        if (n < n2) {
            return this.mHeaderViewInfos.get((int)n).view;
        }
        int n3 = n - n2;
        n = 0;
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null) {
            n = n2 = listAdapter.getCount();
            if (n3 < n2) {
                return this.mAdapter.getView(n3, view, viewGroup);
            }
        }
        return this.mFooterViewInfos.get((int)(n3 - n)).view;
    }

    @Override
    public int getViewTypeCount() {
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null) {
            return listAdapter.getViewTypeCount();
        }
        return 1;
    }

    @Override
    public ListAdapter getWrappedAdapter() {
        return this.mAdapter;
    }

    @Override
    public boolean hasStableIds() {
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null) {
            return listAdapter.hasStableIds();
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        ListAdapter listAdapter = this.mAdapter;
        boolean bl = listAdapter == null || listAdapter.isEmpty();
        return bl;
    }

    @Override
    public boolean isEnabled(int n) {
        int n2 = this.getHeadersCount();
        if (n < n2) {
            return this.mHeaderViewInfos.get((int)n).isSelectable;
        }
        int n3 = n - n2;
        n = 0;
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null) {
            n = n2 = listAdapter.getCount();
            if (n3 < n2) {
                return this.mAdapter.isEnabled(n3);
            }
        }
        return this.mFooterViewInfos.get((int)(n3 - n)).isSelectable;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null) {
            listAdapter.registerDataSetObserver(dataSetObserver);
        }
    }

    public boolean removeFooter(View view) {
        int n = 0;
        do {
            int n2 = this.mFooterViewInfos.size();
            boolean bl = false;
            if (n >= n2) break;
            if (this.mFooterViewInfos.get((int)n).view == view) {
                this.mFooterViewInfos.remove(n);
                if (this.areAllListInfosSelectable(this.mHeaderViewInfos) && this.areAllListInfosSelectable(this.mFooterViewInfos)) {
                    bl = true;
                }
                this.mAreAllFixedViewsSelectable = bl;
                return true;
            }
            ++n;
        } while (true);
        return false;
    }

    public boolean removeHeader(View view) {
        int n = 0;
        do {
            int n2 = this.mHeaderViewInfos.size();
            boolean bl = false;
            if (n >= n2) break;
            if (this.mHeaderViewInfos.get((int)n).view == view) {
                this.mHeaderViewInfos.remove(n);
                if (this.areAllListInfosSelectable(this.mHeaderViewInfos) && this.areAllListInfosSelectable(this.mFooterViewInfos)) {
                    bl = true;
                }
                this.mAreAllFixedViewsSelectable = bl;
                return true;
            }
            ++n;
        } while (true);
        return false;
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null) {
            listAdapter.unregisterDataSetObserver(dataSetObserver);
        }
    }
}

