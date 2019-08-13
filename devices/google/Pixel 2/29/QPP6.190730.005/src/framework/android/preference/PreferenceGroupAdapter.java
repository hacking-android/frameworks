/*
 * Decompiled with CFR 0.145.
 */
package android.preference;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Deprecated
public class PreferenceGroupAdapter
extends BaseAdapter
implements Preference.OnPreferenceChangeInternalListener {
    private static final String TAG = "PreferenceGroupAdapter";
    private static ViewGroup.LayoutParams sWrapperLayoutParams = new ViewGroup.LayoutParams(-1, -2);
    private Handler mHandler = new Handler();
    private boolean mHasReturnedViewTypeCount = false;
    private Drawable mHighlightedDrawable;
    private int mHighlightedPosition = -1;
    private volatile boolean mIsSyncing = false;
    private PreferenceGroup mPreferenceGroup;
    private ArrayList<PreferenceLayout> mPreferenceLayouts;
    private List<Preference> mPreferenceList;
    private Runnable mSyncRunnable = new Runnable(){

        @Override
        public void run() {
            PreferenceGroupAdapter.this.syncMyPreferences();
        }
    };
    private PreferenceLayout mTempPreferenceLayout = new PreferenceLayout();

    public PreferenceGroupAdapter(PreferenceGroup preferenceGroup) {
        this.mPreferenceGroup = preferenceGroup;
        this.mPreferenceGroup.setOnPreferenceChangeInternalListener(this);
        this.mPreferenceList = new ArrayList<Preference>();
        this.mPreferenceLayouts = new ArrayList();
        this.syncMyPreferences();
    }

    private void addPreferenceClassName(Preference comparable) {
        int n = Collections.binarySearch(this.mPreferenceLayouts, comparable = this.createPreferenceLayout((Preference)comparable, null));
        if (n < 0) {
            this.mPreferenceLayouts.add(n * -1 - 1, (PreferenceLayout)comparable);
        }
    }

    private PreferenceLayout createPreferenceLayout(Preference preference, PreferenceLayout preferenceLayout) {
        if (preferenceLayout == null) {
            preferenceLayout = new PreferenceLayout();
        }
        preferenceLayout.name = preference.getClass().getName();
        preferenceLayout.resId = preference.getLayoutResource();
        preferenceLayout.widgetResId = preference.getWidgetLayoutResource();
        return preferenceLayout;
    }

    private void flattenPreferenceGroup(List<Preference> list, PreferenceGroup preferenceGroup) {
        preferenceGroup.sortPreferences();
        int n = preferenceGroup.getPreferenceCount();
        for (int i = 0; i < n; ++i) {
            PreferenceGroup preferenceGroup2;
            Preference preference = preferenceGroup.getPreference(i);
            list.add(preference);
            if (!this.mHasReturnedViewTypeCount && preference.isRecycleEnabled()) {
                this.addPreferenceClassName(preference);
            }
            if (preference instanceof PreferenceGroup && (preferenceGroup2 = (PreferenceGroup)preference).isOnSameScreenAsChildren()) {
                this.flattenPreferenceGroup(list, preferenceGroup2);
            }
            preference.setOnPreferenceChangeInternalListener(this);
        }
    }

    private int getHighlightItemViewType() {
        return this.getViewTypeCount() - 1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void syncMyPreferences() {
        synchronized (this) {
            if (this.mIsSyncing) {
                return;
            }
            this.mIsSyncing = true;
        }
        ArrayList<Preference> arrayList = new ArrayList<Preference>(this.mPreferenceList.size());
        this.flattenPreferenceGroup(arrayList, this.mPreferenceGroup);
        this.mPreferenceList = arrayList;
        this.notifyDataSetChanged();
        synchronized (this) {
            this.mIsSyncing = false;
            this.notifyAll();
            return;
        }
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public int getCount() {
        return this.mPreferenceList.size();
    }

    @UnsupportedAppUsage
    @Override
    public Preference getItem(int n) {
        if (n >= 0 && n < this.getCount()) {
            return this.mPreferenceList.get(n);
        }
        return null;
    }

    @Override
    public long getItemId(int n) {
        if (n >= 0 && n < this.getCount()) {
            return this.getItem(n).getId();
        }
        return Long.MIN_VALUE;
    }

    @Override
    public int getItemViewType(int n) {
        Preference preference;
        if (n == this.mHighlightedPosition) {
            return this.getHighlightItemViewType();
        }
        if (!this.mHasReturnedViewTypeCount) {
            this.mHasReturnedViewTypeCount = true;
        }
        if (!(preference = this.getItem(n)).isRecycleEnabled()) {
            return -1;
        }
        this.mTempPreferenceLayout = this.createPreferenceLayout(preference, this.mTempPreferenceLayout);
        n = Collections.binarySearch(this.mPreferenceLayouts, this.mTempPreferenceLayout);
        if (n < 0) {
            return -1;
        }
        return n;
    }

    @Override
    public View getView(int n, View object, ViewGroup viewGroup) {
        Object object2 = this.getItem(n);
        this.mTempPreferenceLayout = this.createPreferenceLayout((Preference)object2, this.mTempPreferenceLayout);
        if (Collections.binarySearch(this.mPreferenceLayouts, this.mTempPreferenceLayout) < 0 || this.getItemViewType(n) == this.getHighlightItemViewType()) {
            object = null;
        }
        object = object2 = ((Preference)object2).getView((View)object, viewGroup);
        if (n == this.mHighlightedPosition) {
            object = object2;
            if (this.mHighlightedDrawable != null) {
                object = new FrameLayout(viewGroup.getContext());
                ((View)object).setLayoutParams(sWrapperLayoutParams);
                ((View)object).setBackgroundDrawable(this.mHighlightedDrawable);
                ((ViewGroup)object).addView((View)object2);
            }
        }
        return object;
    }

    @Override
    public int getViewTypeCount() {
        if (!this.mHasReturnedViewTypeCount) {
            this.mHasReturnedViewTypeCount = true;
        }
        return Math.max(1, this.mPreferenceLayouts.size()) + 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isEnabled(int n) {
        if (n >= 0 && n < this.getCount()) {
            return this.getItem(n).isSelectable();
        }
        return true;
    }

    @Override
    public void onPreferenceChange(Preference preference) {
        this.notifyDataSetChanged();
    }

    @Override
    public void onPreferenceHierarchyChange(Preference preference) {
        this.mHandler.removeCallbacks(this.mSyncRunnable);
        this.mHandler.post(this.mSyncRunnable);
    }

    public void setHighlighted(int n) {
        this.mHighlightedPosition = n;
    }

    public void setHighlightedDrawable(Drawable drawable2) {
        this.mHighlightedDrawable = drawable2;
    }

    private static class PreferenceLayout
    implements Comparable<PreferenceLayout> {
        private String name;
        private int resId;
        private int widgetResId;

        private PreferenceLayout() {
        }

        @Override
        public int compareTo(PreferenceLayout preferenceLayout) {
            int n = this.name.compareTo(preferenceLayout.name);
            if (n == 0) {
                n = this.resId;
                int n2 = preferenceLayout.resId;
                if (n == n2) {
                    n = this.widgetResId;
                    n2 = preferenceLayout.widgetResId;
                    if (n == n2) {
                        return 0;
                    }
                    return n - n2;
                }
                return n - n2;
            }
            return n;
        }
    }

}

