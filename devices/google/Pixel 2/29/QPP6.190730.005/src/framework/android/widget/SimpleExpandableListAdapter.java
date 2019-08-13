/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.List;
import java.util.Map;

public class SimpleExpandableListAdapter
extends BaseExpandableListAdapter {
    private List<? extends List<? extends Map<String, ?>>> mChildData;
    private String[] mChildFrom;
    private int mChildLayout;
    private int[] mChildTo;
    private int mCollapsedGroupLayout;
    private int mExpandedGroupLayout;
    private List<? extends Map<String, ?>> mGroupData;
    private String[] mGroupFrom;
    private int[] mGroupTo;
    private LayoutInflater mInflater;
    private int mLastChildLayout;

    public SimpleExpandableListAdapter(Context context, List<? extends Map<String, ?>> list, int n, int n2, String[] arrstring, int[] arrn, List<? extends List<? extends Map<String, ?>>> list2, int n3, int n4, String[] arrstring2, int[] arrn2) {
        this.mGroupData = list;
        this.mExpandedGroupLayout = n;
        this.mCollapsedGroupLayout = n2;
        this.mGroupFrom = arrstring;
        this.mGroupTo = arrn;
        this.mChildData = list2;
        this.mChildLayout = n3;
        this.mLastChildLayout = n4;
        this.mChildFrom = arrstring2;
        this.mChildTo = arrn2;
        this.mInflater = (LayoutInflater)context.getSystemService("layout_inflater");
    }

    public SimpleExpandableListAdapter(Context context, List<? extends Map<String, ?>> list, int n, int n2, String[] arrstring, int[] arrn, List<? extends List<? extends Map<String, ?>>> list2, int n3, String[] arrstring2, int[] arrn2) {
        this(context, list, n, n2, arrstring, arrn, list2, n3, n3, arrstring2, arrn2);
    }

    public SimpleExpandableListAdapter(Context context, List<? extends Map<String, ?>> list, int n, String[] arrstring, int[] arrn, List<? extends List<? extends Map<String, ?>>> list2, int n2, String[] arrstring2, int[] arrn2) {
        this(context, list, n, n, arrstring, arrn, list2, n2, n2, arrstring2, arrn2);
    }

    private void bindView(View view, Map<String, ?> map, String[] arrstring, int[] arrn) {
        int n = arrn.length;
        for (int i = 0; i < n; ++i) {
            TextView textView = (TextView)view.findViewById(arrn[i]);
            if (textView == null) continue;
            textView.setText((String)map.get(arrstring[i]));
        }
    }

    @Override
    public Object getChild(int n, int n2) {
        return this.mChildData.get(n).get(n2);
    }

    @Override
    public long getChildId(int n, int n2) {
        return n2;
    }

    @Override
    public View getChildView(int n, int n2, boolean bl, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.newChildView(bl, viewGroup);
        }
        this.bindView(view, this.mChildData.get(n).get(n2), this.mChildFrom, this.mChildTo);
        return view;
    }

    @Override
    public int getChildrenCount(int n) {
        return this.mChildData.get(n).size();
    }

    @Override
    public Object getGroup(int n) {
        return this.mGroupData.get(n);
    }

    @Override
    public int getGroupCount() {
        return this.mGroupData.size();
    }

    @Override
    public long getGroupId(int n) {
        return n;
    }

    @Override
    public View getGroupView(int n, boolean bl, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.newGroupView(bl, viewGroup);
        }
        this.bindView(view, this.mGroupData.get(n), this.mGroupFrom, this.mGroupTo);
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int n, int n2) {
        return true;
    }

    public View newChildView(boolean bl, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = this.mInflater;
        int n = bl ? this.mLastChildLayout : this.mChildLayout;
        return layoutInflater.inflate(n, viewGroup, false);
    }

    public View newGroupView(boolean bl, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = this.mInflater;
        int n = bl ? this.mExpandedGroupLayout : this.mCollapsedGroupLayout;
        return layoutInflater.inflate(n, viewGroup, false);
    }
}

