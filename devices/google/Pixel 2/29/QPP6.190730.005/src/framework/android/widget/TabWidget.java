/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import com.android.internal.R;

public class TabWidget
extends LinearLayout
implements View.OnFocusChangeListener {
    private final Rect mBounds = new Rect();
    @UnsupportedAppUsage
    private boolean mDrawBottomStrips = true;
    private int[] mImposedTabWidths;
    private int mImposedTabsHeight = -1;
    private Drawable mLeftStrip;
    private Drawable mRightStrip;
    @UnsupportedAppUsage
    private int mSelectedTab = -1;
    private OnTabSelectionChanged mSelectionChangedListener;
    private boolean mStripMoved;

    public TabWidget(Context context) {
        this(context, null);
    }

    public TabWidget(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842883);
    }

    public TabWidget(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public TabWidget(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TabWidget, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.TabWidget, attributeSet, typedArray, n, n2);
        this.mDrawBottomStrips = typedArray.getBoolean(3, this.mDrawBottomStrips);
        n = context.getApplicationInfo().targetSdkVersion <= 4 ? 1 : 0;
        this.mLeftStrip = typedArray.hasValueOrEmpty(1) ? typedArray.getDrawable(1) : (n != 0 ? context.getDrawable(17303658) : context.getDrawable(17303657));
        this.mRightStrip = typedArray.hasValueOrEmpty(2) ? typedArray.getDrawable(2) : (n != 0 ? context.getDrawable(17303660) : context.getDrawable(17303659));
        typedArray.recycle();
        this.setChildrenDrawingOrderEnabled(true);
    }

    @Override
    public void addView(View view) {
        if (view.getLayoutParams() == null) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -1, 1.0f);
            layoutParams.setMargins(0, 0, 0, 0);
            view.setLayoutParams(layoutParams);
        }
        view.setFocusable(true);
        view.setClickable(true);
        if (view.getPointerIcon() == null) {
            view.setPointerIcon(PointerIcon.getSystemIcon(this.getContext(), 1002));
        }
        super.addView(view);
        view.setOnClickListener(new TabClickListener(this.getTabCount() - 1));
    }

    @Override
    public void childDrawableStateChanged(View view) {
        if (this.getTabCount() > 0 && view == this.getChildTabViewAt(this.mSelectedTab)) {
            this.invalidate();
        }
        super.childDrawableStateChanged(view);
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.getTabCount() == 0) {
            return;
        }
        if (!this.mDrawBottomStrips) {
            return;
        }
        View view = this.getChildTabViewAt(this.mSelectedTab);
        Drawable drawable2 = this.mLeftStrip;
        Drawable drawable3 = this.mRightStrip;
        if (drawable2 != null) {
            drawable2.setState(view.getDrawableState());
        }
        if (drawable3 != null) {
            drawable3.setState(view.getDrawableState());
        }
        if (this.mStripMoved) {
            Rect rect = this.mBounds;
            rect.left = view.getLeft();
            rect.right = view.getRight();
            int n = this.getHeight();
            if (drawable2 != null) {
                drawable2.setBounds(Math.min(0, rect.left - drawable2.getIntrinsicWidth()), n - drawable2.getIntrinsicHeight(), rect.left, n);
            }
            if (drawable3 != null) {
                drawable3.setBounds(rect.right, n - drawable3.getIntrinsicHeight(), Math.max(this.getWidth(), rect.right + drawable3.getIntrinsicWidth()), n);
            }
            this.mStripMoved = false;
        }
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        if (drawable3 != null) {
            drawable3.draw(canvas);
        }
    }

    public void focusCurrentTab(int n) {
        int n2 = this.mSelectedTab;
        this.setCurrentTab(n);
        if (n2 != n) {
            this.getChildTabViewAt(n).requestFocus();
        }
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return TabWidget.class.getName();
    }

    @Override
    protected int getChildDrawingOrder(int n, int n2) {
        int n3 = this.mSelectedTab;
        if (n3 == -1) {
            return n2;
        }
        if (n2 == n - 1) {
            return n3;
        }
        if (n2 >= n3) {
            return n2 + 1;
        }
        return n2;
    }

    public View getChildTabViewAt(int n) {
        return this.getChildAt(n);
    }

    public Drawable getLeftStripDrawable() {
        return this.mLeftStrip;
    }

    public Drawable getRightStripDrawable() {
        return this.mRightStrip;
    }

    public int getTabCount() {
        return this.getChildCount();
    }

    public boolean isStripEnabled() {
        return this.mDrawBottomStrips;
    }

    @Override
    void measureChildBeforeLayout(View view, int n, int n2, int n3, int n4, int n5) {
        int n6 = n2;
        int n7 = n4;
        if (!this.isMeasureWithLargestChildEnabled()) {
            n6 = n2;
            n7 = n4;
            if (this.mImposedTabsHeight >= 0) {
                n6 = View.MeasureSpec.makeMeasureSpec(this.mImposedTabWidths[n] + n3, 1073741824);
                n7 = View.MeasureSpec.makeMeasureSpec(this.mImposedTabsHeight, 1073741824);
            }
        }
        super.measureChildBeforeLayout(view, n, n6, n3, n7, n5);
    }

    @Override
    void measureHorizontal(int n, int n2) {
        if (View.MeasureSpec.getMode(n) == 0) {
            super.measureHorizontal(n, n2);
            return;
        }
        int n3 = View.MeasureSpec.getSize(n);
        int n4 = View.MeasureSpec.makeSafeMeasureSpec(n3, 0);
        this.mImposedTabsHeight = -1;
        super.measureHorizontal(n4, n2);
        int n5 = this.getMeasuredWidth() - n3;
        if (n5 > 0) {
            int n6 = this.getChildCount();
            n4 = 0;
            for (n3 = 0; n3 < n6; ++n3) {
                if (this.getChildAt(n3).getVisibility() == 8) continue;
                ++n4;
            }
            if (n4 > 0) {
                Object object = this.mImposedTabWidths;
                if (object == null || ((int[])object).length != n6) {
                    this.mImposedTabWidths = new int[n6];
                }
                for (n3 = 0; n3 < n6; ++n3) {
                    int n7;
                    object = this.getChildAt(n3);
                    if (((View)object).getVisibility() == 8) continue;
                    int n8 = ((View)object).getMeasuredWidth();
                    this.mImposedTabWidths[n3] = n7 = Math.max(0, n8 - n5 / n4);
                    n5 -= n8 - n7;
                    --n4;
                    this.mImposedTabsHeight = Math.max(this.mImposedTabsHeight, ((View)object).getMeasuredHeight());
                }
            }
        }
        super.measureHorizontal(n, n2);
    }

    @Override
    public void onFocusChange(View view, boolean bl) {
    }

    @Override
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setItemCount(this.getTabCount());
        accessibilityEvent.setCurrentItemIndex(this.mSelectedTab);
    }

    @Override
    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int n) {
        if (!this.isEnabled()) {
            return null;
        }
        return super.onResolvePointerIcon(motionEvent, n);
    }

    @Override
    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        this.mStripMoved = true;
        super.onSizeChanged(n, n2, n3, n4);
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        this.mSelectedTab = -1;
    }

    public void setCurrentTab(int n) {
        int n2;
        if (n >= 0 && n < this.getTabCount() && n != (n2 = this.mSelectedTab)) {
            if (n2 != -1) {
                this.getChildTabViewAt(n2).setSelected(false);
            }
            this.mSelectedTab = n;
            this.getChildTabViewAt(this.mSelectedTab).setSelected(true);
            this.mStripMoved = true;
            return;
        }
    }

    public void setDividerDrawable(int n) {
        this.setDividerDrawable(this.mContext.getDrawable(n));
    }

    @Override
    public void setDividerDrawable(Drawable drawable2) {
        super.setDividerDrawable(drawable2);
    }

    @Override
    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        int n = this.getTabCount();
        for (int i = 0; i < n; ++i) {
            this.getChildTabViewAt(i).setEnabled(bl);
        }
    }

    public void setLeftStripDrawable(int n) {
        this.setLeftStripDrawable(this.mContext.getDrawable(n));
    }

    public void setLeftStripDrawable(Drawable drawable2) {
        this.mLeftStrip = drawable2;
        this.requestLayout();
        this.invalidate();
    }

    public void setRightStripDrawable(int n) {
        this.setRightStripDrawable(this.mContext.getDrawable(n));
    }

    public void setRightStripDrawable(Drawable drawable2) {
        this.mRightStrip = drawable2;
        this.requestLayout();
        this.invalidate();
    }

    public void setStripEnabled(boolean bl) {
        this.mDrawBottomStrips = bl;
        this.invalidate();
    }

    @UnsupportedAppUsage
    void setTabSelectionListener(OnTabSelectionChanged onTabSelectionChanged) {
        this.mSelectionChangedListener = onTabSelectionChanged;
    }

    static interface OnTabSelectionChanged {
        public void onTabSelectionChanged(int var1, boolean var2);
    }

    private class TabClickListener
    implements View.OnClickListener {
        private final int mTabIndex;

        private TabClickListener(int n) {
            this.mTabIndex = n;
        }

        @Override
        public void onClick(View view) {
            TabWidget.this.mSelectionChangedListener.onTabSelectionChanged(this.mTabIndex, true);
        }
    }

}

