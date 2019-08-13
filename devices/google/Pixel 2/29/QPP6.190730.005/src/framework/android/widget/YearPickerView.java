/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.util.Calendar
 */
package android.widget;

import android.content.Context;
import android.content.res.Resources;
import android.icu.util.Calendar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

class YearPickerView
extends ListView {
    private final YearAdapter mAdapter;
    private final int mChildSize;
    private OnYearSelectedListener mOnYearSelectedListener;
    private final int mViewSize;

    public YearPickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842868);
    }

    public YearPickerView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public YearPickerView(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        this.setLayoutParams(new AbsListView.LayoutParams(-1, -2));
        object = ((Context)object).getResources();
        this.mViewSize = ((Resources)object).getDimensionPixelOffset(17105105);
        this.mChildSize = ((Resources)object).getDimensionPixelOffset(17105106);
        this.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                n = YearPickerView.this.mAdapter.getYearForPosition(n);
                YearPickerView.this.mAdapter.setSelection(n);
                if (YearPickerView.this.mOnYearSelectedListener != null) {
                    YearPickerView.this.mOnYearSelectedListener.onYearChanged(YearPickerView.this, n);
                }
            }
        });
        this.mAdapter = new YearAdapter(this.getContext());
        this.setAdapter(this.mAdapter);
    }

    public int getFirstPositionOffset() {
        View view = this.getChildAt(0);
        if (view == null) {
            return 0;
        }
        return view.getTop();
    }

    @Override
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        if (accessibilityEvent.getEventType() == 4096) {
            accessibilityEvent.setFromIndex(0);
            accessibilityEvent.setToIndex(0);
        }
    }

    public void setOnYearSelectedListener(OnYearSelectedListener onYearSelectedListener) {
        this.mOnYearSelectedListener = onYearSelectedListener;
    }

    public void setRange(Calendar calendar, Calendar calendar2) {
        this.mAdapter.setRange(calendar, calendar2);
    }

    public void setSelectionCentered(int n) {
        this.setSelectionFromTop(n, this.mViewSize / 2 - this.mChildSize / 2);
    }

    public void setYear(final int n) {
        this.mAdapter.setSelection(n);
        this.post(new Runnable(){

            @Override
            public void run() {
                int n2 = YearPickerView.this.mAdapter.getPositionForYear(n);
                if (n2 >= 0 && n2 < YearPickerView.this.getCount()) {
                    YearPickerView.this.setSelectionCentered(n2);
                }
            }
        });
    }

    public static interface OnYearSelectedListener {
        public void onYearChanged(YearPickerView var1, int var2);
    }

    private static class YearAdapter
    extends BaseAdapter {
        private static final int ITEM_LAYOUT = 17367341;
        private static final int ITEM_TEXT_ACTIVATED_APPEARANCE = 16974768;
        private static final int ITEM_TEXT_APPEARANCE = 16974767;
        private int mActivatedYear;
        private int mCount;
        private final LayoutInflater mInflater;
        private int mMinYear;

        public YearAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }

        @Override
        public int getCount() {
            return this.mCount;
        }

        @Override
        public Integer getItem(int n) {
            return this.getYearForPosition(n);
        }

        @Override
        public long getItemId(int n) {
            return this.getYearForPosition(n);
        }

        @Override
        public int getItemViewType(int n) {
            return 0;
        }

        public int getPositionForYear(int n) {
            return n - this.mMinYear;
        }

        @Override
        public View getView(int n, View view, ViewGroup viewGroup) {
            boolean bl = true;
            boolean bl2 = view == null;
            view = bl2 ? (TextView)this.mInflater.inflate(17367341, viewGroup, false) : (TextView)view;
            int n2 = this.getYearForPosition(n);
            if (this.mActivatedYear != n2) {
                bl = false;
            }
            if (bl2 || view.isActivated() != bl) {
                n = bl ? 16974768 : 16974767;
                ((TextView)view).setTextAppearance(n);
                view.setActivated(bl);
            }
            ((TextView)view).setText(Integer.toString(n2));
            return view;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        public int getYearForPosition(int n) {
            return this.mMinYear + n;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean isEnabled(int n) {
            return true;
        }

        public void setRange(Calendar calendar, Calendar calendar2) {
            int n = calendar.get(1);
            int n2 = calendar2.get(1) - n + 1;
            if (this.mMinYear != n || this.mCount != n2) {
                this.mMinYear = n;
                this.mCount = n2;
                this.notifyDataSetInvalidated();
            }
        }

        public boolean setSelection(int n) {
            if (this.mActivatedYear != n) {
                this.mActivatedYear = n;
                this.notifyDataSetChanged();
                return true;
            }
            return false;
        }
    }

}

