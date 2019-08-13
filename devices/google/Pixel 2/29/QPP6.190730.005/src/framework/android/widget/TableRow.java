/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.widget.LinearLayout;
import com.android.internal.R;

public class TableRow
extends LinearLayout {
    private ChildrenTracker mChildrenTracker;
    private SparseIntArray mColumnToChildIndex;
    private int[] mColumnWidths;
    private int[] mConstrainedColumnWidths;
    private int mNumColumns = 0;

    public TableRow(Context context) {
        super(context);
        this.initTableRow();
    }

    public TableRow(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initTableRow();
    }

    private void initTableRow() {
        ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.mOnHierarchyChangeListener;
        this.mChildrenTracker = new ChildrenTracker();
        if (onHierarchyChangeListener != null) {
            this.mChildrenTracker.setOnHierarchyChangeListener(onHierarchyChangeListener);
        }
        super.setOnHierarchyChangeListener(this.mChildrenTracker);
    }

    private void mapIndexAndColumns() {
        if (this.mColumnToChildIndex == null) {
            int n = 0;
            int n2 = this.getChildCount();
            SparseIntArray sparseIntArray = this.mColumnToChildIndex = new SparseIntArray();
            for (int i = 0; i < n2; ++i) {
                LayoutParams layoutParams = (LayoutParams)this.getChildAt(i).getLayoutParams();
                int n3 = n;
                if (layoutParams.column >= n) {
                    n3 = layoutParams.column;
                }
                n = 0;
                while (n < layoutParams.span) {
                    sparseIntArray.put(n3, i);
                    ++n;
                    ++n3;
                }
                n = n3;
            }
            this.mNumColumns = n;
        }
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
        return TableRow.class.getName();
    }

    @Override
    int getChildrenSkipCount(View view, int n) {
        return ((LayoutParams)view.getLayoutParams()).span - 1;
    }

    int[] getColumnsWidths(int n, int n2) {
        int n3 = this.getVirtualChildCount();
        Object object = this.mColumnWidths;
        if (object == null || n3 != ((int[])object).length) {
            this.mColumnWidths = new int[n3];
        }
        int[] arrn = this.mColumnWidths;
        for (int i = 0; i < n3; ++i) {
            View view = this.getVirtualChildAt(i);
            if (view != null && view.getVisibility() != 8) {
                object = (LayoutParams)view.getLayoutParams();
                if (object.span == 1) {
                    int n4 = object.width;
                    n4 = n4 != -2 ? (n4 != -1 ? View.MeasureSpec.makeMeasureSpec(object.width, 1073741824) : View.MeasureSpec.makeSafeMeasureSpec(View.MeasureSpec.getSize(n2), 0)) : TableRow.getChildMeasureSpec(n, 0, -2);
                    view.measure(n4, n4);
                    arrn[i] = view.getMeasuredWidth() + object.leftMargin + object.rightMargin;
                    continue;
                }
                arrn[i] = 0;
                continue;
            }
            arrn[i] = 0;
        }
        return arrn;
    }

    @Override
    int getLocationOffset(View view) {
        return ((LayoutParams)view.getLayoutParams()).mOffset[0];
    }

    @Override
    int getNextLocationOffset(View view) {
        return ((LayoutParams)view.getLayoutParams()).mOffset[1];
    }

    @Override
    public View getVirtualChildAt(int n) {
        if (this.mColumnToChildIndex == null) {
            this.mapIndexAndColumns();
        }
        if ((n = this.mColumnToChildIndex.get(n, -1)) != -1) {
            return this.getChildAt(n);
        }
        return null;
    }

    @Override
    public int getVirtualChildCount() {
        if (this.mColumnToChildIndex == null) {
            this.mapIndexAndColumns();
        }
        return this.mNumColumns;
    }

    @Override
    void measureChildBeforeLayout(View arrn, int n, int n2, int n3, int n4, int n5) {
        if (this.mConstrainedColumnWidths != null) {
            LayoutParams layoutParams = (LayoutParams)arrn.getLayoutParams();
            int n6 = 1073741824;
            n2 = 0;
            int n7 = layoutParams.span;
            int[] arrn2 = this.mConstrainedColumnWidths;
            for (n3 = 0; n3 < n7; ++n3) {
                n2 += arrn2[n + n3];
            }
            n3 = layoutParams.gravity;
            boolean bl = Gravity.isHorizontal(n3);
            n = n6;
            if (bl) {
                n = Integer.MIN_VALUE;
            }
            arrn.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, n2 - layoutParams.leftMargin - layoutParams.rightMargin), n), TableRow.getChildMeasureSpec(n4, this.mPaddingTop + this.mPaddingBottom + layoutParams.topMargin + layoutParams.bottomMargin + n5, layoutParams.height));
            if (bl) {
                n = arrn.getMeasuredWidth();
                LayoutParams.access$200((LayoutParams)layoutParams)[1] = n2 - n;
                n = Gravity.getAbsoluteGravity(n3, this.getLayoutDirection()) & 7;
                if (n != 1) {
                    if (n != 3 && n == 5) {
                        LayoutParams.access$200((LayoutParams)layoutParams)[0] = layoutParams.mOffset[1];
                    }
                } else {
                    LayoutParams.access$200((LayoutParams)layoutParams)[0] = layoutParams.mOffset[1] / 2;
                }
            } else {
                arrn = layoutParams.mOffset;
                LayoutParams.access$200((LayoutParams)layoutParams)[1] = 0;
                arrn[0] = 0;
            }
        } else {
            super.measureChildBeforeLayout((View)arrn, n, n2, n3, n4, n5);
        }
    }

    @Override
    int measureNullChild(int n) {
        return this.mConstrainedColumnWidths[n];
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        this.layoutHorizontal(n, n2, n3, n4);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        this.measureHorizontal(n, n2);
    }

    void setColumnCollapsed(int n, boolean bl) {
        View view = this.getVirtualChildAt(n);
        if (view != null) {
            n = bl ? 8 : 0;
            view.setVisibility(n);
        }
    }

    void setColumnsWidthConstraints(int[] arrn) {
        if (arrn != null && arrn.length >= this.getVirtualChildCount()) {
            this.mConstrainedColumnWidths = arrn;
            return;
        }
        throw new IllegalArgumentException("columnWidths should be >= getVirtualChildCount()");
    }

    @Override
    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.mChildrenTracker.setOnHierarchyChangeListener(onHierarchyChangeListener);
    }

    private class ChildrenTracker
    implements ViewGroup.OnHierarchyChangeListener {
        private ViewGroup.OnHierarchyChangeListener listener;

        private ChildrenTracker() {
        }

        private void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
            this.listener = onHierarchyChangeListener;
        }

        @Override
        public void onChildViewAdded(View view, View view2) {
            TableRow.this.mColumnToChildIndex = null;
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.listener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewAdded(view, view2);
            }
        }

        @Override
        public void onChildViewRemoved(View view, View view2) {
            TableRow.this.mColumnToChildIndex = null;
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.listener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewRemoved(view, view2);
            }
        }
    }

    public static class LayoutParams
    extends LinearLayout.LayoutParams {
        private static final int LOCATION = 0;
        private static final int LOCATION_NEXT = 1;
        @ViewDebug.ExportedProperty(category="layout")
        public int column;
        private int[] mOffset = new int[2];
        @ViewDebug.ExportedProperty(category="layout")
        public int span;

        public LayoutParams() {
            super(-1, -2);
            this.column = -1;
            this.span = 1;
        }

        public LayoutParams(int n) {
            this();
            this.column = n;
        }

        public LayoutParams(int n, int n2) {
            super(n, n2);
            this.column = -1;
            this.span = 1;
        }

        public LayoutParams(int n, int n2, float f) {
            super(n, n2, f);
            this.column = -1;
            this.span = 1;
        }

        public LayoutParams(Context object, AttributeSet attributeSet) {
            super((Context)object, attributeSet);
            object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.TableRow_Cell);
            this.column = ((TypedArray)object).getInt(0, -1);
            this.span = ((TypedArray)object).getInt(1, 1);
            if (this.span <= 1) {
                this.span = 1;
            }
            ((TypedArray)object).recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        @Override
        protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("layout:column", this.column);
            viewHierarchyEncoder.addProperty("layout:span", this.span);
        }

        @Override
        protected void setBaseAttributes(TypedArray typedArray, int n, int n2) {
            this.width = typedArray.hasValue(n) ? typedArray.getLayoutDimension(n, "layout_width") : -1;
            this.height = typedArray.hasValue(n2) ? typedArray.getLayoutDimension(n2, "layout_height") : -2;
        }
    }

}

