/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

public interface ExpandableListAdapter {
    public boolean areAllItemsEnabled();

    public Object getChild(int var1, int var2);

    public long getChildId(int var1, int var2);

    public View getChildView(int var1, int var2, boolean var3, View var4, ViewGroup var5);

    public int getChildrenCount(int var1);

    public long getCombinedChildId(long var1, long var3);

    public long getCombinedGroupId(long var1);

    public Object getGroup(int var1);

    public int getGroupCount();

    public long getGroupId(int var1);

    public View getGroupView(int var1, boolean var2, View var3, ViewGroup var4);

    public boolean hasStableIds();

    public boolean isChildSelectable(int var1, int var2);

    public boolean isEmpty();

    public void onGroupCollapsed(int var1);

    public void onGroupExpanded(int var1);

    public void registerDataSetObserver(DataSetObserver var1);

    public void unregisterDataSetObserver(DataSetObserver var1);
}

