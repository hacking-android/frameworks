/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import com.android.internal.R;
import java.util.regex.Pattern;

public class TableLayout
extends LinearLayout {
    private SparseBooleanArray mCollapsedColumns;
    private boolean mInitialized;
    private int[] mMaxWidths;
    private PassThroughHierarchyChangeListener mPassThroughListener;
    private boolean mShrinkAllColumns;
    private SparseBooleanArray mShrinkableColumns;
    private boolean mStretchAllColumns;
    private SparseBooleanArray mStretchableColumns;

    public TableLayout(Context context) {
        super(context);
        this.initTableLayout();
    }

    public TableLayout(Context object, AttributeSet object2) {
        super((Context)object, (AttributeSet)object2);
        object = ((Context)object).obtainStyledAttributes((AttributeSet)object2, R.styleable.TableLayout);
        object2 = ((TypedArray)object).getString(0);
        if (object2 != null) {
            if (((String)object2).charAt(0) == '*') {
                this.mStretchAllColumns = true;
            } else {
                this.mStretchableColumns = TableLayout.parseColumns((String)object2);
            }
        }
        if ((object2 = ((TypedArray)object).getString(1)) != null) {
            if (((String)object2).charAt(0) == '*') {
                this.mShrinkAllColumns = true;
            } else {
                this.mShrinkableColumns = TableLayout.parseColumns((String)object2);
            }
        }
        if ((object2 = ((TypedArray)object).getString(2)) != null) {
            this.mCollapsedColumns = TableLayout.parseColumns((String)object2);
        }
        ((TypedArray)object).recycle();
        this.initTableLayout();
    }

    private void findLargestCells(int n, int n2) {
        boolean bl = true;
        int n3 = this.getChildCount();
        for (int i = 0; i < n3; ++i) {
            int[] arrn;
            int[] arrn2 = this.getChildAt(i);
            if (arrn2.getVisibility() == 8 || !(arrn2 instanceof TableRow)) continue;
            arrn2 = (TableRow)arrn2;
            arrn2.getLayoutParams().height = -2;
            arrn2 = arrn2.getColumnsWidths(n, n2);
            int n4 = arrn2.length;
            if (bl) {
                arrn = this.mMaxWidths;
                if (arrn == null || arrn.length != n4) {
                    this.mMaxWidths = new int[n4];
                }
                System.arraycopy(arrn2, 0, this.mMaxWidths, 0, n4);
                bl = false;
                continue;
            }
            int n5 = this.mMaxWidths.length;
            int n6 = n4 - n5;
            if (n6 > 0) {
                arrn = this.mMaxWidths;
                this.mMaxWidths = new int[n4];
                System.arraycopy(arrn, 0, this.mMaxWidths, 0, arrn.length);
                System.arraycopy(arrn2, arrn.length, this.mMaxWidths, arrn.length, n6);
            }
            arrn = this.mMaxWidths;
            n6 = Math.min(n5, n4);
            for (n4 = 0; n4 < n6; ++n4) {
                arrn[n4] = Math.max(arrn[n4], arrn2[n4]);
            }
        }
    }

    private void initTableLayout() {
        if (this.mCollapsedColumns == null) {
            this.mCollapsedColumns = new SparseBooleanArray();
        }
        if (this.mStretchableColumns == null) {
            this.mStretchableColumns = new SparseBooleanArray();
        }
        if (this.mShrinkableColumns == null) {
            this.mShrinkableColumns = new SparseBooleanArray();
        }
        this.setOrientation(1);
        this.mPassThroughListener = new PassThroughHierarchyChangeListener();
        super.setOnHierarchyChangeListener(this.mPassThroughListener);
        this.mInitialized = true;
    }

    private void mutateColumnsWidth(SparseBooleanArray sparseBooleanArray, boolean bl, int n, int n2) {
        int n3 = 0;
        int[] arrn = this.mMaxWidths;
        int n4 = arrn.length;
        int n5 = bl ? n4 : sparseBooleanArray.size();
        int n6 = (n - n2) / n5;
        n2 = this.getChildCount();
        for (n = 0; n < n2; ++n) {
            View view = this.getChildAt(n);
            if (!(view instanceof TableRow)) continue;
            view.forceLayout();
        }
        if (!bl) {
            n2 = n3;
            for (n = 0; n < n5; ++n) {
                int n7 = sparseBooleanArray.keyAt(n);
                n3 = n2;
                if (sparseBooleanArray.valueAt(n)) {
                    if (n7 < n4) {
                        arrn[n7] = arrn[n7] + n6;
                        n3 = n2;
                    } else {
                        n3 = n2 + 1;
                    }
                }
                n2 = n3;
            }
            if (n2 > 0 && n2 < n5) {
                n2 = n2 * n6 / (n5 - n2);
                for (n = 0; n < n5; ++n) {
                    n3 = sparseBooleanArray.keyAt(n);
                    if (!sparseBooleanArray.valueAt(n) || n3 >= n4) continue;
                    arrn[n3] = n2 > arrn[n3] ? 0 : arrn[n3] + n2;
                }
            }
            return;
        }
        for (n = 0; n < n5; ++n) {
            arrn[n] = arrn[n] + n6;
        }
    }

    private static SparseBooleanArray parseColumns(String arrstring) {
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        for (String string2 : Pattern.compile("\\s*,\\s*").split((CharSequence)arrstring)) {
            int n = Integer.parseInt(string2);
            if (n < 0) continue;
            try {
                sparseBooleanArray.put(n, true);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return sparseBooleanArray;
    }

    private void requestRowsLayout() {
        if (this.mInitialized) {
            int n = this.getChildCount();
            for (int i = 0; i < n; ++i) {
                this.getChildAt(i).requestLayout();
            }
        }
    }

    private void shrinkAndStretchColumns(int n) {
        int[] arrn = this.mMaxWidths;
        if (arrn == null) {
            return;
        }
        int n2 = 0;
        int n3 = arrn.length;
        for (int i = 0; i < n3; ++i) {
            n2 += arrn[i];
        }
        if (n2 > (n = View.MeasureSpec.getSize(n) - this.mPaddingLeft - this.mPaddingRight) && (this.mShrinkAllColumns || this.mShrinkableColumns.size() > 0)) {
            this.mutateColumnsWidth(this.mShrinkableColumns, this.mShrinkAllColumns, n, n2);
        } else if (n2 < n && (this.mStretchAllColumns || this.mStretchableColumns.size() > 0)) {
            this.mutateColumnsWidth(this.mStretchableColumns, this.mStretchAllColumns, n, n2);
        }
    }

    private void trackCollapsedColumns(View view) {
        if (view instanceof TableRow) {
            view = (TableRow)view;
            SparseBooleanArray sparseBooleanArray = this.mCollapsedColumns;
            int n = sparseBooleanArray.size();
            for (int i = 0; i < n; ++i) {
                int n2 = sparseBooleanArray.keyAt(i);
                boolean bl = sparseBooleanArray.valueAt(i);
                if (!bl) continue;
                ((TableRow)view).setColumnCollapsed(n2, bl);
            }
        }
    }

    @Override
    public void addView(View view) {
        super.addView(view);
        this.requestRowsLayout();
    }

    @Override
    public void addView(View view, int n) {
        super.addView(view, n);
        this.requestRowsLayout();
    }

    @Override
    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, n, layoutParams);
        this.requestRowsLayout();
    }

    @Override
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, layoutParams);
        this.requestRowsLayout();
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override
    protected LinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    @Override
    protected LinearLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return TableLayout.class.getName();
    }

    public boolean isColumnCollapsed(int n) {
        return this.mCollapsedColumns.get(n);
    }

    public boolean isColumnShrinkable(int n) {
        boolean bl = this.mShrinkAllColumns || this.mShrinkableColumns.get(n);
        return bl;
    }

    public boolean isColumnStretchable(int n) {
        boolean bl = this.mStretchAllColumns || this.mStretchableColumns.get(n);
        return bl;
    }

    public boolean isShrinkAllColumns() {
        return this.mShrinkAllColumns;
    }

    public boolean isStretchAllColumns() {
        return this.mStretchAllColumns;
    }

    @Override
    void measureChildBeforeLayout(View view, int n, int n2, int n3, int n4, int n5) {
        if (view instanceof TableRow) {
            ((TableRow)view).setColumnsWidthConstraints(this.mMaxWidths);
        }
        super.measureChildBeforeLayout(view, n, n2, n3, n4, n5);
    }

    @Override
    void measureVertical(int n, int n2) {
        this.findLargestCells(n, n2);
        this.shrinkAndStretchColumns(n);
        super.measureVertical(n, n2);
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        this.layoutVertical(n, n2, n3, n4);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        this.measureVertical(n, n2);
    }

    @Override
    public void requestLayout() {
        if (this.mInitialized) {
            int n = this.getChildCount();
            for (int i = 0; i < n; ++i) {
                this.getChildAt(i).forceLayout();
            }
        }
        super.requestLayout();
    }

    public void setColumnCollapsed(int n, boolean bl) {
        this.mCollapsedColumns.put(n, bl);
        int n2 = this.getChildCount();
        for (int i = 0; i < n2; ++i) {
            View view = this.getChildAt(i);
            if (!(view instanceof TableRow)) continue;
            ((TableRow)view).setColumnCollapsed(n, bl);
        }
        this.requestRowsLayout();
    }

    public void setColumnShrinkable(int n, boolean bl) {
        this.mShrinkableColumns.put(n, bl);
        this.requestRowsLayout();
    }

    public void setColumnStretchable(int n, boolean bl) {
        this.mStretchableColumns.put(n, bl);
        this.requestRowsLayout();
    }

    @Override
    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.mPassThroughListener.mOnHierarchyChangeListener = onHierarchyChangeListener;
    }

    public void setShrinkAllColumns(boolean bl) {
        this.mShrinkAllColumns = bl;
    }

    public void setStretchAllColumns(boolean bl) {
        this.mStretchAllColumns = bl;
    }

    public static class LayoutParams
    extends LinearLayout.LayoutParams {
        public LayoutParams() {
            super(-1, -2);
        }

        public LayoutParams(int n, int n2) {
            super(-1, n2);
        }

        public LayoutParams(int n, int n2, float f) {
            super(-1, n2, f);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.width = -1;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.width = -1;
            if (marginLayoutParams instanceof LayoutParams) {
                this.weight = ((LayoutParams)marginLayoutParams).weight;
            }
        }

        @Override
        protected void setBaseAttributes(TypedArray typedArray, int n, int n2) {
            this.width = -1;
            this.height = typedArray.hasValue(n2) ? typedArray.getLayoutDimension(n2, "layout_height") : -2;
        }
    }

    private class PassThroughHierarchyChangeListener
    implements ViewGroup.OnHierarchyChangeListener {
        private ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;

        private PassThroughHierarchyChangeListener() {
        }

        @Override
        public void onChildViewAdded(View view, View view2) {
            TableLayout.this.trackCollapsedColumns(view2);
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.mOnHierarchyChangeListener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewAdded(view, view2);
            }
        }

        @Override
        public void onChildViewRemoved(View view, View view2) {
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.mOnHierarchyChangeListener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewRemoved(view, view2);
            }
        }
    }

}

