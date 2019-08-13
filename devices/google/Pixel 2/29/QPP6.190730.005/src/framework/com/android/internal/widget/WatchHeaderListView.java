/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.function.Predicate;

public class WatchHeaderListView
extends ListView {
    private View mTopPanel;

    public WatchHeaderListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public WatchHeaderListView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    public WatchHeaderListView(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    private void wrapAdapterIfNecessary() {
        Adapter adapter = this.getAdapter();
        if (adapter != null && this.mTopPanel != null) {
            if (!(adapter instanceof WatchHeaderListAdapter)) {
                this.wrapHeaderListAdapterInternal();
            }
            ((WatchHeaderListAdapter)this.getAdapter()).setTopPanel(this.mTopPanel);
            this.dispatchDataSetObserverOnChangedInternal();
        }
    }

    @Override
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (this.mTopPanel == null) {
            this.setTopPanel(view);
            return;
        }
        throw new IllegalStateException("WatchHeaderListView can host only one header");
    }

    @Override
    protected <T extends View> T findViewByPredicateTraversal(Predicate<View> predicate, View view) {
        View view2;
        Object t = super.findViewByPredicateTraversal(predicate, view);
        if (t == null && (view2 = this.mTopPanel) != null && view2 != view && !view2.isRootNamespace()) {
            return this.mTopPanel.findViewByPredicate(predicate);
        }
        return t;
    }

    protected View findViewTraversal(int n) {
        View view;
        Object t = super.findViewTraversal(n);
        if (t == null && (view = this.mTopPanel) != null && !view.isRootNamespace()) {
            return this.mTopPanel.findViewById(n);
        }
        return t;
    }

    protected View findViewWithTagTraversal(Object object) {
        View view;
        Object t = super.findViewWithTagTraversal(object);
        if (t == null && (view = this.mTopPanel) != null && !view.isRootNamespace()) {
            return this.mTopPanel.findViewWithTag(object);
        }
        return t;
    }

    @Override
    public int getHeaderViewsCount() {
        int n;
        if (this.mTopPanel == null) {
            n = super.getHeaderViewsCount();
        } else {
            int n2 = super.getHeaderViewsCount();
            n = this.mTopPanel.getVisibility() == 8 ? 0 : 1;
            n = n2 + n;
        }
        return n;
    }

    @Override
    public void setAdapter(ListAdapter listAdapter) {
        super.setAdapter(listAdapter);
        this.wrapAdapterIfNecessary();
    }

    public void setTopPanel(View view) {
        this.mTopPanel = view;
        this.wrapAdapterIfNecessary();
    }

    @Override
    protected HeaderViewListAdapter wrapHeaderListAdapterInternal(ArrayList<ListView.FixedViewInfo> arrayList, ArrayList<ListView.FixedViewInfo> arrayList2, ListAdapter listAdapter) {
        return new WatchHeaderListAdapter(arrayList, arrayList2, listAdapter);
    }

    private static class WatchHeaderListAdapter
    extends HeaderViewListAdapter {
        private View mTopPanel;

        public WatchHeaderListAdapter(ArrayList<ListView.FixedViewInfo> arrayList, ArrayList<ListView.FixedViewInfo> arrayList2, ListAdapter listAdapter) {
            super(arrayList, arrayList2, listAdapter);
        }

        private int getTopPanelCount() {
            View view = this.mTopPanel;
            int n = view != null && view.getVisibility() != 8 ? 1 : 0;
            return n;
        }

        @Override
        public boolean areAllItemsEnabled() {
            boolean bl = this.getTopPanelCount() == 0 && super.areAllItemsEnabled();
            return bl;
        }

        @Override
        public int getCount() {
            return super.getCount() + this.getTopPanelCount();
        }

        @Override
        public Object getItem(int n) {
            int n2 = this.getTopPanelCount();
            Object object = n < n2 ? null : super.getItem(n - n2);
            return object;
        }

        @Override
        public long getItemId(int n) {
            int n2 = this.getHeadersCount() + this.getTopPanelCount();
            if (this.getWrappedAdapter() != null && n >= n2 && (n -= n2) < this.getWrappedAdapter().getCount()) {
                return this.getWrappedAdapter().getItemId(n);
            }
            return -1L;
        }

        @Override
        public int getItemViewType(int n) {
            int n2 = this.getHeadersCount() + this.getTopPanelCount();
            if (this.getWrappedAdapter() != null && n >= n2 && (n -= n2) < this.getWrappedAdapter().getCount()) {
                return this.getWrappedAdapter().getItemViewType(n);
            }
            return -2;
        }

        @Override
        public View getView(int n, View view, ViewGroup viewGroup) {
            int n2 = this.getTopPanelCount();
            view = n < n2 ? this.mTopPanel : super.getView(n - n2, view, viewGroup);
            return view;
        }

        @Override
        public boolean isEnabled(int n) {
            int n2 = this.getTopPanelCount();
            boolean bl = n < n2 ? false : super.isEnabled(n - n2);
            return bl;
        }

        public void setTopPanel(View view) {
            this.mTopPanel = view;
        }
    }

}

