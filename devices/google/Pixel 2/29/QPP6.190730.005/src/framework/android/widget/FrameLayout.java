/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.widget.RemoteViews;
import com.android.internal.R;
import java.util.ArrayList;

@RemoteViews.RemoteView
public class FrameLayout
extends ViewGroup {
    private static final int DEFAULT_CHILD_GRAVITY = 8388659;
    @ViewDebug.ExportedProperty(category="padding")
    @UnsupportedAppUsage
    private int mForegroundPaddingBottom = 0;
    @ViewDebug.ExportedProperty(category="padding")
    @UnsupportedAppUsage
    private int mForegroundPaddingLeft = 0;
    @ViewDebug.ExportedProperty(category="padding")
    @UnsupportedAppUsage
    private int mForegroundPaddingRight = 0;
    @ViewDebug.ExportedProperty(category="padding")
    @UnsupportedAppUsage
    private int mForegroundPaddingTop = 0;
    private final ArrayList<View> mMatchParentChildren = new ArrayList(1);
    @ViewDebug.ExportedProperty(category="measurement")
    @UnsupportedAppUsage
    boolean mMeasureAllChildren = false;

    public FrameLayout(Context context) {
        super(context);
    }

    public FrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FrameLayout(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public FrameLayout(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.FrameLayout, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.FrameLayout, attributeSet, typedArray, n, n2);
        if (typedArray.getBoolean(0, false)) {
            this.setMeasureAllChildren(true);
        }
        typedArray.recycle();
    }

    private int getPaddingBottomWithForeground() {
        int n = this.isForegroundInsidePadding() ? Math.max(this.mPaddingBottom, this.mForegroundPaddingBottom) : this.mPaddingBottom + this.mForegroundPaddingBottom;
        return n;
    }

    private int getPaddingTopWithForeground() {
        int n = this.isForegroundInsidePadding() ? Math.max(this.mPaddingTop, this.mForegroundPaddingTop) : this.mPaddingTop + this.mForegroundPaddingTop;
        return n;
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("measurement:measureAllChildren", this.mMeasureAllChildren);
        viewHierarchyEncoder.addProperty("padding:foregroundPaddingLeft", this.mForegroundPaddingLeft);
        viewHierarchyEncoder.addProperty("padding:foregroundPaddingTop", this.mForegroundPaddingTop);
        viewHierarchyEncoder.addProperty("padding:foregroundPaddingRight", this.mForegroundPaddingRight);
        viewHierarchyEncoder.addProperty("padding:foregroundPaddingBottom", this.mForegroundPaddingBottom);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (sPreserveMarginParamsInLayoutParamConversion) {
            if (layoutParams instanceof LayoutParams) {
                return new LayoutParams((LayoutParams)layoutParams);
            }
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
            }
        }
        return new LayoutParams(layoutParams);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return FrameLayout.class.getName();
    }

    @Deprecated
    public boolean getConsiderGoneChildrenWhenMeasuring() {
        return this.getMeasureAllChildren();
    }

    public boolean getMeasureAllChildren() {
        return this.mMeasureAllChildren;
    }

    int getPaddingLeftWithForeground() {
        int n = this.isForegroundInsidePadding() ? Math.max(this.mPaddingLeft, this.mForegroundPaddingLeft) : this.mPaddingLeft + this.mForegroundPaddingLeft;
        return n;
    }

    int getPaddingRightWithForeground() {
        int n = this.isForegroundInsidePadding() ? Math.max(this.mPaddingRight, this.mForegroundPaddingRight) : this.mPaddingRight + this.mForegroundPaddingRight;
        return n;
    }

    void layoutChildren(int n, int n2, int n3, int n4, boolean bl) {
        int n5 = this.getChildCount();
        int n6 = this.getPaddingLeftWithForeground();
        n3 = n3 - n - this.getPaddingRightWithForeground();
        int n7 = this.getPaddingTopWithForeground();
        int n8 = n4 - n2 - this.getPaddingBottomWithForeground();
        n4 = n6;
        for (int i = 0; i < n5; ++i) {
            View view = this.getChildAt(i);
            if (view.getVisibility() == 8) continue;
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            n6 = view.getMeasuredWidth();
            int n9 = view.getMeasuredHeight();
            n = n2 = layoutParams.gravity;
            if (n2 == -1) {
                n = 8388659;
            }
            n2 = Gravity.getAbsoluteGravity(n, this.getLayoutDirection());
            n &= 112;
            n2 = (n2 &= 7) != 1 ? (n2 == 5 && !bl ? n3 - n6 - layoutParams.rightMargin : layoutParams.leftMargin + n4) : (n3 - n4 - n6) / 2 + n4 + layoutParams.leftMargin - layoutParams.rightMargin;
            n = n != 16 ? (n != 48 ? (n != 80 ? layoutParams.topMargin + n7 : n8 - n9 - layoutParams.bottomMargin) : n7 + layoutParams.topMargin) : (n8 - n7 - n9) / 2 + n7 + layoutParams.topMargin - layoutParams.bottomMargin;
            view.layout(n2, n, n2 + n6, n + n9);
        }
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        this.layoutChildren(n, n2, n3, n4, false);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3;
        Object object;
        Object object2;
        int n4 = this.getChildCount();
        int n5 = View.MeasureSpec.getMode(n) == 1073741824 && View.MeasureSpec.getMode(n2) == 1073741824 ? 0 : 1;
        this.mMatchParentChildren.clear();
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        for (n3 = 0; n3 < n4; ++n3) {
            int n9;
            int n10;
            int n11;
            block9 : {
                block8 : {
                    object = this.getChildAt(n3);
                    if (this.mMeasureAllChildren) break block8;
                    n11 = n8;
                    n9 = n6;
                    n10 = n7;
                    if (((View)object).getVisibility() == 8) break block9;
                }
                this.measureChildWithMargins((View)object, n, 0, n2, 0);
                object2 = (LayoutParams)((View)object).getLayoutParams();
                n10 = Math.max(n7, ((View)object).getMeasuredWidth() + ((LayoutParams)object2).leftMargin + ((LayoutParams)object2).rightMargin);
                n9 = Math.max(n6, ((View)object).getMeasuredHeight() + ((LayoutParams)object2).topMargin + ((LayoutParams)object2).bottomMargin);
                n11 = FrameLayout.combineMeasuredStates(n8, ((View)object).getMeasuredState());
                if (n5 != 0 && (((LayoutParams)object2).width == -1 || ((LayoutParams)object2).height == -1)) {
                    this.mMatchParentChildren.add((View)object);
                }
            }
            n8 = n11;
            n6 = n9;
            n7 = n10;
        }
        n5 = this.getPaddingLeftWithForeground();
        n3 = this.getPaddingRightWithForeground();
        n6 = Math.max(n6 + (this.getPaddingTopWithForeground() + this.getPaddingBottomWithForeground()), this.getSuggestedMinimumHeight());
        n7 = Math.max(n7 + (n5 + n3), this.getSuggestedMinimumWidth());
        object = this.getForeground();
        n3 = n6;
        n5 = n7;
        if (object != null) {
            n3 = Math.max(n6, ((Drawable)object).getMinimumHeight());
            n5 = Math.max(n7, ((Drawable)object).getMinimumWidth());
        }
        this.setMeasuredDimension(FrameLayout.resolveSizeAndState(n5, n, n8), FrameLayout.resolveSizeAndState(n3, n2, n8 << 16));
        n7 = this.mMatchParentChildren.size();
        if (n7 > 1) {
            for (n8 = 0; n8 < n7; ++n8) {
                object2 = this.mMatchParentChildren.get(n8);
                object = (ViewGroup.MarginLayoutParams)((View)object2).getLayoutParams();
                n5 = ((ViewGroup.MarginLayoutParams)object).width == -1 ? View.MeasureSpec.makeMeasureSpec(Math.max(0, this.getMeasuredWidth() - this.getPaddingLeftWithForeground() - this.getPaddingRightWithForeground() - ((ViewGroup.MarginLayoutParams)object).leftMargin - ((ViewGroup.MarginLayoutParams)object).rightMargin), 1073741824) : FrameLayout.getChildMeasureSpec(n, this.getPaddingLeftWithForeground() + this.getPaddingRightWithForeground() + ((ViewGroup.MarginLayoutParams)object).leftMargin + ((ViewGroup.MarginLayoutParams)object).rightMargin, ((ViewGroup.MarginLayoutParams)object).width);
                n3 = ((ViewGroup.MarginLayoutParams)object).height == -1 ? View.MeasureSpec.makeMeasureSpec(Math.max(0, this.getMeasuredHeight() - this.getPaddingTopWithForeground() - this.getPaddingBottomWithForeground() - ((ViewGroup.MarginLayoutParams)object).topMargin - ((ViewGroup.MarginLayoutParams)object).bottomMargin), 1073741824) : FrameLayout.getChildMeasureSpec(n2, this.getPaddingTopWithForeground() + this.getPaddingBottomWithForeground() + ((ViewGroup.MarginLayoutParams)object).topMargin + ((ViewGroup.MarginLayoutParams)object).bottomMargin, ((ViewGroup.MarginLayoutParams)object).height);
                ((View)object2).measure(n5, n3);
            }
        }
    }

    @RemotableViewMethod
    @Override
    public void setForegroundGravity(int n) {
        if (this.getForegroundGravity() != n) {
            super.setForegroundGravity(n);
            Drawable drawable2 = this.getForeground();
            if (this.getForegroundGravity() == 119 && drawable2 != null) {
                Rect rect = new Rect();
                if (drawable2.getPadding(rect)) {
                    this.mForegroundPaddingLeft = rect.left;
                    this.mForegroundPaddingTop = rect.top;
                    this.mForegroundPaddingRight = rect.right;
                    this.mForegroundPaddingBottom = rect.bottom;
                }
            } else {
                this.mForegroundPaddingLeft = 0;
                this.mForegroundPaddingTop = 0;
                this.mForegroundPaddingRight = 0;
                this.mForegroundPaddingBottom = 0;
            }
            this.requestLayout();
        }
    }

    @RemotableViewMethod
    public void setMeasureAllChildren(boolean bl) {
        this.mMeasureAllChildren = bl;
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public static final int UNSPECIFIED_GRAVITY = -1;
        public int gravity = -1;

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(int n, int n2, int n3) {
            super(n, n2);
            this.gravity = n3;
        }

        public LayoutParams(Context object, AttributeSet attributeSet) {
            super((Context)object, attributeSet);
            object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.FrameLayout_Layout);
            this.gravity = ((TypedArray)object).getInt(0, -1);
            ((TypedArray)object).recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
            this.gravity = layoutParams.gravity;
        }
    }

}

