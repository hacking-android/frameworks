/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

public interface Adapter {
    public static final int IGNORE_ITEM_VIEW_TYPE = -1;
    public static final int NO_SELECTION = Integer.MIN_VALUE;

    default public CharSequence[] getAutofillOptions() {
        return null;
    }

    public int getCount();

    public Object getItem(int var1);

    public long getItemId(int var1);

    public int getItemViewType(int var1);

    public View getView(int var1, View var2, ViewGroup var3);

    public int getViewTypeCount();

    public boolean hasStableIds();

    public boolean isEmpty();

    public void registerDataSetObserver(DataSetObserver var1);

    public void unregisterDataSetObserver(DataSetObserver var1);
}

