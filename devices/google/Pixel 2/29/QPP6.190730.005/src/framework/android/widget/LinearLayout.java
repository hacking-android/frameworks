/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.TypedArray;
import android.graphics.Canvas;
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
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@RemoteViews.RemoteView
public class LinearLayout
extends ViewGroup {
    public static final int HORIZONTAL = 0;
    @UnsupportedAppUsage
    private static final int INDEX_BOTTOM = 2;
    private static final int INDEX_CENTER_VERTICAL = 0;
    private static final int INDEX_FILL = 3;
    @UnsupportedAppUsage
    private static final int INDEX_TOP = 1;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_GRAVITY_COUNT = 4;
    private static boolean sCompatibilityDone = false;
    private static boolean sRemeasureWeightedChildren = true;
    private final boolean mAllowInconsistentMeasurement;
    @ViewDebug.ExportedProperty(category="layout")
    private boolean mBaselineAligned;
    @ViewDebug.ExportedProperty(category="layout")
    private int mBaselineAlignedChildIndex;
    @ViewDebug.ExportedProperty(category="measurement")
    private int mBaselineChildTop;
    @UnsupportedAppUsage
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;
    @ViewDebug.ExportedProperty(category="measurement", flagMapping={@ViewDebug.FlagToString(equals=-1, mask=-1, name="NONE"), @ViewDebug.FlagToString(equals=0, mask=0, name="NONE"), @ViewDebug.FlagToString(equals=48, mask=48, name="TOP"), @ViewDebug.FlagToString(equals=80, mask=80, name="BOTTOM"), @ViewDebug.FlagToString(equals=3, mask=3, name="LEFT"), @ViewDebug.FlagToString(equals=5, mask=5, name="RIGHT"), @ViewDebug.FlagToString(equals=8388611, mask=8388611, name="START"), @ViewDebug.FlagToString(equals=8388613, mask=8388613, name="END"), @ViewDebug.FlagToString(equals=16, mask=16, name="CENTER_VERTICAL"), @ViewDebug.FlagToString(equals=112, mask=112, name="FILL_VERTICAL"), @ViewDebug.FlagToString(equals=1, mask=1, name="CENTER_HORIZONTAL"), @ViewDebug.FlagToString(equals=7, mask=7, name="FILL_HORIZONTAL"), @ViewDebug.FlagToString(equals=17, mask=17, name="CENTER"), @ViewDebug.FlagToString(equals=119, mask=119, name="FILL"), @ViewDebug.FlagToString(equals=8388608, mask=8388608, name="RELATIVE")}, formatToHexString=true)
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mGravity;
    private int mLayoutDirection;
    @UnsupportedAppUsage
    private int[] mMaxAscent;
    @UnsupportedAppUsage
    private int[] mMaxDescent;
    @ViewDebug.ExportedProperty(category="measurement")
    private int mOrientation;
    private int mShowDividers;
    @ViewDebug.ExportedProperty(category="measurement")
    @UnsupportedAppUsage
    private int mTotalLength;
    @ViewDebug.ExportedProperty(category="layout")
    @UnsupportedAppUsage
    private boolean mUseLargestChild;
    @ViewDebug.ExportedProperty(category="layout")
    private float mWeightSum;

    public LinearLayout(Context context) {
        this(context, null);
    }

    public LinearLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LinearLayout(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public LinearLayout(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        boolean bl;
        boolean bl2 = true;
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        this.mLayoutDirection = -1;
        if (!sCompatibilityDone && context != null) {
            bl = context.getApplicationInfo().targetSdkVersion >= 28;
            sRemeasureWeightedChildren = bl;
            sCompatibilityDone = true;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.LinearLayout, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.LinearLayout, attributeSet, typedArray, n, n2);
        n = typedArray.getInt(1, -1);
        if (n >= 0) {
            this.setOrientation(n);
        }
        if ((n = typedArray.getInt(0, -1)) >= 0) {
            this.setGravity(n);
        }
        if (!(bl = typedArray.getBoolean(2, true))) {
            this.setBaselineAligned(bl);
        }
        this.mWeightSum = typedArray.getFloat(4, -1.0f);
        this.mBaselineAlignedChildIndex = typedArray.getInt(3, -1);
        this.mUseLargestChild = typedArray.getBoolean(6, false);
        this.mShowDividers = typedArray.getInt(7, 0);
        this.mDividerPadding = typedArray.getDimensionPixelSize(8, 0);
        this.setDividerDrawable(typedArray.getDrawable(5));
        bl = context.getApplicationInfo().targetSdkVersion <= 23 ? bl2 : false;
        this.mAllowInconsistentMeasurement = bl;
        typedArray.recycle();
    }

    private boolean allViewsAreGoneBefore(int n) {
        --n;
        while (n >= 0) {
            View view = this.getVirtualChildAt(n);
            if (view != null && view.getVisibility() != 8) {
                return false;
            }
            --n;
        }
        return true;
    }

    private void forceUniformHeight(int n, int n2) {
        int n3 = View.MeasureSpec.makeMeasureSpec(this.getMeasuredHeight(), 1073741824);
        for (int i = 0; i < n; ++i) {
            View view = this.getVirtualChildAt(i);
            if (view == null || view.getVisibility() == 8) continue;
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.height != -1) continue;
            int n4 = layoutParams.width;
            layoutParams.width = view.getMeasuredWidth();
            this.measureChildWithMargins(view, n2, 0, n3, 0);
            layoutParams.width = n4;
        }
    }

    private void forceUniformWidth(int n, int n2) {
        int n3 = View.MeasureSpec.makeMeasureSpec(this.getMeasuredWidth(), 1073741824);
        for (int i = 0; i < n; ++i) {
            View view = this.getVirtualChildAt(i);
            if (view == null || view.getVisibility() == 8) continue;
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.width != -1) continue;
            int n4 = layoutParams.height;
            layoutParams.height = view.getMeasuredHeight();
            this.measureChildWithMargins(view, n3, 0, n2, 0);
            layoutParams.height = n4;
        }
    }

    private View getLastNonGoneChild() {
        for (int i = this.getVirtualChildCount() - 1; i >= 0; --i) {
            View view = this.getVirtualChildAt(i);
            if (view == null || view.getVisibility() == 8) continue;
            return view;
        }
        return null;
    }

    private boolean isShowingDividers() {
        boolean bl = this.mShowDividers != 0 && this.mDivider != null;
        return bl;
    }

    private void setChildFrame(View view, int n, int n2, int n3, int n4) {
        view.layout(n, n2, n + n3, n2 + n4);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    void drawDividersHorizontal(Canvas canvas) {
        Object object;
        Object object2;
        int n;
        int n2 = this.getVirtualChildCount();
        boolean bl = this.isLayoutRtl();
        for (n = 0; n < n2; ++n) {
            object2 = this.getVirtualChildAt(n);
            if (object2 == null || ((View)object2).getVisibility() == 8 || !this.hasDividerBeforeChildAt(n)) continue;
            object = (LayoutParams)((View)object2).getLayoutParams();
            int n3 = bl ? ((View)object2).getRight() + ((LayoutParams)object).rightMargin : ((View)object2).getLeft() - ((LayoutParams)object).leftMargin - this.mDividerWidth;
            this.drawVerticalDivider(canvas, n3);
        }
        if (this.hasDividerBeforeChildAt(n2)) {
            object = this.getLastNonGoneChild();
            if (object == null) {
                n = bl ? this.getPaddingLeft() : this.getWidth() - this.getPaddingRight() - this.mDividerWidth;
            } else {
                object2 = (LayoutParams)((View)object).getLayoutParams();
                n = bl ? ((View)object).getLeft() - ((LayoutParams)object2).leftMargin - this.mDividerWidth : ((View)object).getRight() + ((LayoutParams)object2).rightMargin;
            }
            this.drawVerticalDivider(canvas, n);
        }
    }

    void drawDividersVertical(Canvas canvas) {
        LayoutParams layoutParams;
        View view;
        int n;
        int n2 = this.getVirtualChildCount();
        for (n = 0; n < n2; ++n) {
            view = this.getVirtualChildAt(n);
            if (view == null || view.getVisibility() == 8 || !this.hasDividerBeforeChildAt(n)) continue;
            layoutParams = (LayoutParams)view.getLayoutParams();
            this.drawHorizontalDivider(canvas, view.getTop() - layoutParams.topMargin - this.mDividerHeight);
        }
        if (this.hasDividerBeforeChildAt(n2)) {
            view = this.getLastNonGoneChild();
            if (view == null) {
                n = this.getHeight() - this.getPaddingBottom() - this.mDividerHeight;
            } else {
                layoutParams = (LayoutParams)view.getLayoutParams();
                n = view.getBottom() + layoutParams.bottomMargin;
            }
            this.drawHorizontalDivider(canvas, n);
        }
    }

    void drawHorizontalDivider(Canvas canvas, int n) {
        this.mDivider.setBounds(this.getPaddingLeft() + this.mDividerPadding, n, this.getWidth() - this.getPaddingRight() - this.mDividerPadding, this.mDividerHeight + n);
        this.mDivider.draw(canvas);
    }

    void drawVerticalDivider(Canvas canvas, int n) {
        this.mDivider.setBounds(n, this.getPaddingTop() + this.mDividerPadding, this.mDividerWidth + n, this.getHeight() - this.getPaddingBottom() - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    @Override
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("layout:baselineAligned", this.mBaselineAligned);
        viewHierarchyEncoder.addProperty("layout:baselineAlignedChildIndex", this.mBaselineAlignedChildIndex);
        viewHierarchyEncoder.addProperty("measurement:baselineChildTop", this.mBaselineChildTop);
        viewHierarchyEncoder.addProperty("measurement:orientation", this.mOrientation);
        viewHierarchyEncoder.addProperty("measurement:gravity", this.mGravity);
        viewHierarchyEncoder.addProperty("measurement:totalLength", this.mTotalLength);
        viewHierarchyEncoder.addProperty("layout:totalLength", this.mTotalLength);
        viewHierarchyEncoder.addProperty("layout:useLargestChild", this.mUseLargestChild);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        int n = this.mOrientation;
        if (n == 0) {
            return new LayoutParams(-2, -2);
        }
        if (n == 1) {
            return new LayoutParams(-1, -2);
        }
        return null;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
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
    public CharSequence getAccessibilityClassName() {
        return LinearLayout.class.getName();
    }

    @Override
    public int getBaseline() {
        int n;
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        int n2 = this.getChildCount();
        if (n2 > (n = this.mBaselineAlignedChildIndex)) {
            View view = this.getChildAt(n);
            int n3 = view.getBaseline();
            if (n3 == -1) {
                if (this.mBaselineAlignedChildIndex == 0) {
                    return -1;
                }
                throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
            }
            n2 = n = this.mBaselineChildTop;
            if (this.mOrientation == 1) {
                int n4 = this.mGravity & 112;
                n2 = n;
                if (n4 != 48) {
                    n2 = n4 != 16 ? (n4 != 80 ? n : this.mBottom - this.mTop - this.mPaddingBottom - this.mTotalLength) : n + (this.mBottom - this.mTop - this.mPaddingTop - this.mPaddingBottom - this.mTotalLength) / 2;
                }
            }
            return ((LayoutParams)view.getLayoutParams()).topMargin + n2 + n3;
        }
        throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
    }

    public int getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex;
    }

    int getChildrenSkipCount(View view, int n) {
        return 0;
    }

    public Drawable getDividerDrawable() {
        return this.mDivider;
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    public int getGravity() {
        return this.mGravity;
    }

    int getLocationOffset(View view) {
        return 0;
    }

    int getNextLocationOffset(View view) {
        return 0;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getShowDividers() {
        return this.mShowDividers;
    }

    View getVirtualChildAt(int n) {
        return this.getChildAt(n);
    }

    int getVirtualChildCount() {
        return this.getChildCount();
    }

    public float getWeightSum() {
        return this.mWeightSum;
    }

    protected boolean hasDividerBeforeChildAt(int n) {
        int n2 = this.getVirtualChildCount();
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        if (n == n2) {
            if ((this.mShowDividers & 4) != 0) {
                bl3 = true;
            }
            return bl3;
        }
        if (this.allViewsAreGoneBefore(n)) {
            bl3 = bl;
            if ((this.mShowDividers & 1) != 0) {
                bl3 = true;
            }
            return bl3;
        }
        bl3 = bl2;
        if ((this.mShowDividers & 2) != 0) {
            bl3 = true;
        }
        return bl3;
    }

    public boolean isBaselineAligned() {
        return this.mBaselineAligned;
    }

    public boolean isMeasureWithLargestChildEnabled() {
        return this.mUseLargestChild;
    }

    void layoutHorizontal(int n, int n2, int n3, int n4) {
        int n5;
        int n6;
        boolean bl = this.isLayoutRtl();
        int n7 = this.mPaddingTop;
        int n8 = n4 - n2;
        int n9 = this.mPaddingBottom;
        int n10 = this.mPaddingBottom;
        int n11 = this.getVirtualChildCount();
        int n12 = this.mGravity;
        boolean bl2 = this.mBaselineAligned;
        int[] arrn = this.mMaxAscent;
        int[] arrn2 = this.mMaxDescent;
        int n13 = this.getLayoutDirection();
        n2 = Gravity.getAbsoluteGravity(n12 & 8388615, n13);
        n = n2 != 1 ? (n2 != 5 ? this.mPaddingLeft : this.mPaddingLeft + n3 - n - this.mTotalLength) : this.mPaddingLeft + (n3 - n - this.mTotalLength) / 2;
        if (bl) {
            n6 = n11 - 1;
            n5 = -1;
        } else {
            n6 = 0;
            n5 = 1;
        }
        int n14 = n8;
        n3 = n7;
        n4 = n;
        for (n2 = 0; n2 < n11; ++n2) {
            int n15 = n6 + n5 * n2;
            View view = this.getVirtualChildAt(n15);
            if (view == null) {
                n4 += this.measureNullChild(n15);
                continue;
            }
            if (view.getVisibility() == 8) continue;
            int n16 = view.getMeasuredWidth();
            int n17 = view.getMeasuredHeight();
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            n = bl2 && layoutParams.height != -1 ? view.getBaseline() : -1;
            int n18 = layoutParams.gravity;
            if (n18 < 0) {
                n18 = n12 & 112;
            }
            if ((n18 &= 112) != 16) {
                if (n18 != 48) {
                    if (n18 != 80) {
                        n = n3;
                    } else {
                        n18 = n8 - n9 - n17 - layoutParams.bottomMargin;
                        if (n != -1) {
                            int n19 = view.getMeasuredHeight();
                            n = n18 - (arrn2[2] - (n19 - n));
                        } else {
                            n = n18;
                        }
                    }
                } else {
                    n18 = layoutParams.topMargin + n3;
                    n = n != -1 ? n18 + (arrn[1] - n) : n18;
                }
            } else {
                n = (n8 - n7 - n10 - n17) / 2 + n3 + layoutParams.topMargin - layoutParams.bottomMargin;
            }
            n18 = n4;
            if (this.hasDividerBeforeChildAt(n15)) {
                n18 = n4 + this.mDividerWidth;
            }
            n4 = n18 + layoutParams.leftMargin;
            this.setChildFrame(view, n4 + this.getLocationOffset(view), n, n16, n17);
            n18 = layoutParams.rightMargin;
            n = this.getNextLocationOffset(view);
            n2 += this.getChildrenSkipCount(view, n15);
            n4 += n16 + n18 + n;
        }
    }

    void layoutVertical(int n, int n2, int n3, int n4) {
        int n5 = this.mPaddingLeft;
        int n6 = n3 - n;
        int n7 = this.mPaddingRight;
        int n8 = this.mPaddingRight;
        int n9 = this.getVirtualChildCount();
        int n10 = this.mGravity;
        n = n10 & 112;
        n = n != 16 ? (n != 80 ? this.mPaddingTop : this.mPaddingTop + n4 - n2 - this.mTotalLength) : this.mPaddingTop + (n4 - n2 - this.mTotalLength) / 2;
        n2 = 0;
        n3 = n5;
        do {
            n4 = n3;
            if (n2 >= n9) break;
            View view = this.getVirtualChildAt(n2);
            if (view == null) {
                n += this.measureNullChild(n2);
            } else if (view.getVisibility() != 8) {
                int n11 = view.getMeasuredWidth();
                int n12 = view.getMeasuredHeight();
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                n3 = layoutParams.gravity;
                if (n3 < 0) {
                    n3 = n10 & 8388615;
                }
                n3 = (n3 = Gravity.getAbsoluteGravity(n3, this.getLayoutDirection()) & 7) != 1 ? (n3 != 5 ? layoutParams.leftMargin + n4 : n6 - n7 - n11 - layoutParams.rightMargin) : (n6 - n5 - n8 - n11) / 2 + n4 + layoutParams.leftMargin - layoutParams.rightMargin;
                int n13 = n;
                if (this.hasDividerBeforeChildAt(n2)) {
                    n13 = n + this.mDividerHeight;
                }
                n = n13 + layoutParams.topMargin;
                this.setChildFrame(view, n3, n + this.getLocationOffset(view), n11, n12);
                n3 = layoutParams.bottomMargin;
                n13 = this.getNextLocationOffset(view);
                n2 += this.getChildrenSkipCount(view, n2);
                n += n12 + n3 + n13;
            }
            ++n2;
            n3 = n4;
        } while (true);
    }

    void measureChildBeforeLayout(View view, int n, int n2, int n3, int n4, int n5) {
        this.measureChildWithMargins(view, n2, n3, n4, n5);
    }

    void measureHorizontal(int n, int n2) {
        block54 : {
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            int n8;
            int n9;
            int n10;
            int n11;
            block51 : {
                int n12;
                block53 : {
                    int[] arrn;
                    int[] arrn2;
                    block52 : {
                        Object object;
                        int n13;
                        int n14;
                        Object object2;
                        boolean bl;
                        boolean bl2;
                        int n15;
                        int n16;
                        int n17;
                        float f;
                        int n18;
                        block50 : {
                            this.mTotalLength = 0;
                            n5 = 0;
                            n13 = this.getVirtualChildCount();
                            n12 = View.MeasureSpec.getMode(n);
                            n6 = View.MeasureSpec.getMode(n2);
                            if (this.mMaxAscent == null || this.mMaxDescent == null) {
                                this.mMaxAscent = new int[4];
                                this.mMaxDescent = new int[4];
                            }
                            arrn2 = this.mMaxAscent;
                            arrn = this.mMaxDescent;
                            arrn2[3] = -1;
                            arrn2[2] = -1;
                            arrn2[1] = -1;
                            arrn2[0] = -1;
                            arrn[3] = -1;
                            arrn[2] = -1;
                            arrn[1] = -1;
                            arrn[0] = -1;
                            bl = this.mBaselineAligned;
                            n14 = 0;
                            boolean bl3 = this.mUseLargestChild;
                            bl2 = n12 == 1073741824;
                            n8 = 0;
                            n16 = 0;
                            n7 = 0;
                            n11 = 0;
                            n3 = 0;
                            f = 0.0f;
                            n4 = 1;
                            n9 = Integer.MIN_VALUE;
                            n10 = 0;
                            for (n15 = 0; n15 < n13; ++n15) {
                                View view = this.getVirtualChildAt(n15);
                                if (view == null) {
                                    this.mTotalLength += this.measureNullChild(n15);
                                    n18 = n7;
                                    n7 = n9;
                                    n9 = n18;
                                } else if (view.getVisibility() == 8) {
                                    n15 += this.getChildrenSkipCount(view, n15);
                                    n18 = n7;
                                    n7 = n9;
                                    n9 = n18;
                                } else {
                                    int n19 = n16 + 1;
                                    if (this.hasDividerBeforeChildAt(n15)) {
                                        this.mTotalLength += this.mDividerWidth;
                                    }
                                    object2 = (LayoutParams)view.getLayoutParams();
                                    f += ((LayoutParams)object2).weight;
                                    n16 = ((LayoutParams)object2).width == 0 && ((LayoutParams)object2).weight > 0.0f ? 1 : 0;
                                    if (n12 == 1073741824 && n16 != 0) {
                                        if (bl2) {
                                            this.mTotalLength += ((LayoutParams)object2).leftMargin + ((LayoutParams)object2).rightMargin;
                                        } else {
                                            n16 = this.mTotalLength;
                                            this.mTotalLength = Math.max(n16, ((LayoutParams)object2).leftMargin + n16 + ((LayoutParams)object2).rightMargin);
                                        }
                                        if (bl) {
                                            n16 = View.MeasureSpec.makeSafeMeasureSpec(View.MeasureSpec.getSize(n), 0);
                                            view.measure(n16, View.MeasureSpec.makeSafeMeasureSpec(View.MeasureSpec.getSize(n2), 0));
                                        } else {
                                            n14 = 1;
                                        }
                                    } else {
                                        if (n16 != 0) {
                                            ((LayoutParams)object2).width = -2;
                                        }
                                        n18 = f == 0.0f ? this.mTotalLength : 0;
                                        this.measureChildBeforeLayout(view, n15, n, n18, n2, 0);
                                        n18 = view.getMeasuredWidth();
                                        if (n16 != 0) {
                                            ((LayoutParams)object2).width = 0;
                                            n8 += n18;
                                        }
                                        object = object2;
                                        if (bl2) {
                                            this.mTotalLength += ((LayoutParams)object).leftMargin + n18 + ((LayoutParams)object).rightMargin + this.getNextLocationOffset(view);
                                        } else {
                                            n16 = this.mTotalLength;
                                            this.mTotalLength = Math.max(n16, n16 + n18 + ((LayoutParams)object).leftMargin + ((LayoutParams)object).rightMargin + this.getNextLocationOffset(view));
                                        }
                                        if (bl3) {
                                            n9 = Math.max(n18, n9);
                                        }
                                    }
                                    n16 = n5;
                                    n18 = n11;
                                    int n20 = n15;
                                    n15 = n11 = 0;
                                    n5 = n10;
                                    if (n6 != 1073741824) {
                                        n15 = n11;
                                        n5 = n10;
                                        if (((LayoutParams)object2).height == -1) {
                                            n5 = 1;
                                            n15 = 1;
                                        }
                                    }
                                    n17 = ((LayoutParams)object2).topMargin + ((LayoutParams)object2).bottomMargin;
                                    n10 = view.getMeasuredHeight() + n17;
                                    int n21 = LinearLayout.combineMeasuredStates(n7, view.getMeasuredState());
                                    if (bl && (n7 = view.getBaseline()) != -1) {
                                        n11 = ((LayoutParams)object2).gravity < 0 ? this.mGravity : ((LayoutParams)object2).gravity;
                                        n11 = ((n11 & 112) >> 4 & -2) >> 1;
                                        arrn2[n11] = Math.max(arrn2[n11], n7);
                                        arrn[n11] = Math.max(arrn[n11], n10 - n7);
                                    }
                                    n7 = n17;
                                    n17 = Math.max(n3, n10);
                                    n11 = n4 != 0 && ((LayoutParams)object2).height == -1 ? 1 : 0;
                                    if (((LayoutParams)object2).weight > 0.0f) {
                                        if (n15 != 0) {
                                            n10 = n7;
                                        }
                                        n7 = Math.max(n18, n10);
                                        n3 = n16;
                                    } else {
                                        if (n15 == 0) {
                                            n7 = n10;
                                        }
                                        n3 = Math.max(n16, n7);
                                        n7 = n18;
                                    }
                                    n15 = this.getChildrenSkipCount(view, n20);
                                    n4 = n11;
                                    n11 = n9;
                                    int n22 = n3;
                                    n18 = n7;
                                    n9 = n21;
                                    n15 = n20 + n15;
                                    n16 = n19;
                                    n10 = n5;
                                    n7 = n11;
                                    n3 = n17;
                                    n5 = n22;
                                    n11 = n18;
                                }
                                n18 = n9;
                                n9 = n7;
                                n7 = n18;
                            }
                            n15 = n9;
                            if (n16 > 0 && this.hasDividerBeforeChildAt(n13)) {
                                this.mTotalLength += this.mDividerWidth;
                            }
                            if (arrn2[1] != -1 || arrn2[0] != -1 || arrn2[2] != -1 || arrn2[3] != -1) {
                                n3 = Math.max(n3, Math.max(arrn2[3], Math.max(arrn2[0], Math.max(arrn2[1], arrn2[2]))) + Math.max(arrn[3], Math.max(arrn[0], Math.max(arrn[1], arrn[2]))));
                            }
                            if (bl3) {
                                n9 = n12;
                                if (n9 != Integer.MIN_VALUE && n9 != 0) {
                                    n9 = n3;
                                } else {
                                    this.mTotalLength = 0;
                                    for (n9 = 0; n9 < n13; ++n9) {
                                        object2 = this.getVirtualChildAt(n9);
                                        if (object2 == null) {
                                            this.mTotalLength += this.measureNullChild(n9);
                                            continue;
                                        }
                                        if (((View)object2).getVisibility() == 8) {
                                            n9 += this.getChildrenSkipCount((View)object2, n9);
                                            continue;
                                        }
                                        object = (LayoutParams)((View)object2).getLayoutParams();
                                        if (bl2) {
                                            n16 = this.mTotalLength;
                                            n18 = ((LayoutParams)object).leftMargin;
                                            this.mTotalLength = n16 + (n18 + n15 + ((LayoutParams)object).rightMargin + this.getNextLocationOffset((View)object2));
                                            continue;
                                        }
                                        n16 = this.mTotalLength;
                                        this.mTotalLength = Math.max(n16, n16 + n15 + ((LayoutParams)object).leftMargin + ((LayoutParams)object).rightMargin + this.getNextLocationOffset((View)object2));
                                    }
                                    n9 = n3;
                                }
                            } else {
                                n9 = n3;
                            }
                            this.mTotalLength += this.mPaddingLeft + this.mPaddingRight;
                            n3 = LinearLayout.resolveSizeAndState(Math.max(this.mTotalLength, this.getSuggestedMinimumWidth()), n, 0);
                            n16 = n3 & 16777215;
                            n18 = this.mTotalLength;
                            if (this.mAllowInconsistentMeasurement) {
                                n8 = 0;
                            }
                            n8 = n16 - n18 + n8;
                            if (n14 != 0 || (sRemeasureWeightedChildren || n8 != 0) && f > 0.0f) break block50;
                            n5 = Math.max(n5, n11);
                            if (bl3 && n12 != 1073741824) {
                                n11 = n16;
                                for (n12 = 0; n12 < n13; ++n12) {
                                    object2 = this.getVirtualChildAt(n12);
                                    if (object2 == null || ((View)object2).getVisibility() == 8 || !(((LayoutParams)object2.getLayoutParams()).weight > 0.0f)) continue;
                                    ((View)object2).measure(View.MeasureSpec.makeMeasureSpec(n15, 1073741824), View.MeasureSpec.makeMeasureSpec(((View)object2).getMeasuredHeight(), 1073741824));
                                }
                            }
                            n11 = n7;
                            n7 = n13;
                            n8 = n5;
                            break block51;
                        }
                        float f2 = this.mWeightSum;
                        if (f2 > 0.0f) {
                            f = f2;
                        }
                        arrn2[3] = -1;
                        arrn2[2] = -1;
                        arrn2[1] = -1;
                        arrn2[0] = -1;
                        arrn[3] = -1;
                        arrn[2] = -1;
                        arrn[1] = -1;
                        arrn[0] = -1;
                        n11 = -1;
                        this.mTotalLength = 0;
                        n14 = 0;
                        n9 = n7;
                        n16 = n8;
                        n7 = n5;
                        n5 = n13;
                        n8 = n3;
                        n13 = n14;
                        n14 = n12;
                        n3 = n11;
                        n11 = n15;
                        n12 = n16;
                        while (n13 < n5) {
                            object = this.getVirtualChildAt(n13);
                            if (object != null && ((View)object).getVisibility() != 8) {
                                object2 = (LayoutParams)((View)object).getLayoutParams();
                                f2 = ((LayoutParams)object2).weight;
                                if (f2 > 0.0f) {
                                    n16 = (int)((float)n12 * f2 / f);
                                    n15 = this.mUseLargestChild && n14 != 1073741824 ? n11 : (((LayoutParams)object2).width == 0 && (!this.mAllowInconsistentMeasurement || n14 == 1073741824) ? n16 : ((View)object).getMeasuredWidth() + n16);
                                    ((View)object).measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, n15), 1073741824), LinearLayout.getChildMeasureSpec(n2, this.mPaddingTop + this.mPaddingBottom + ((LayoutParams)object2).topMargin + ((LayoutParams)object2).bottomMargin, ((LayoutParams)object2).height));
                                    n9 = LinearLayout.combineMeasuredStates(n9, ((View)object).getMeasuredState() & -16777216);
                                    n12 -= n16;
                                    f -= f2;
                                }
                                if (bl2) {
                                    this.mTotalLength += ((View)object).getMeasuredWidth() + ((LayoutParams)object2).leftMargin + ((LayoutParams)object2).rightMargin + this.getNextLocationOffset((View)object);
                                } else {
                                    n15 = this.mTotalLength;
                                    this.mTotalLength = Math.max(n15, ((View)object).getMeasuredWidth() + n15 + ((LayoutParams)object2).leftMargin + ((LayoutParams)object2).rightMargin + this.getNextLocationOffset((View)object));
                                }
                                n15 = n6 != 1073741824 && ((LayoutParams)object2).height == -1 ? 1 : 0;
                                n17 = ((LayoutParams)object2).topMargin + ((LayoutParams)object2).bottomMargin;
                                n18 = ((View)object).getMeasuredHeight() + n17;
                                n16 = Math.max(n3, n18);
                                n3 = n15 != 0 ? n17 : n18;
                                n15 = Math.max(n7, n3);
                                n7 = n4 != 0 && ((LayoutParams)object2).height == -1 ? 1 : 0;
                                if (bl && (n4 = ((View)object).getBaseline()) != -1) {
                                    n3 = ((LayoutParams)object2).gravity < 0 ? this.mGravity : ((LayoutParams)object2).gravity;
                                    n3 = ((n3 & 112) >> 4 & -2) >> 1;
                                    arrn2[n3] = Math.max(arrn2[n3], n4);
                                    arrn[n3] = Math.max(arrn[n3], n18 - n4);
                                }
                                n4 = n7;
                                n7 = n15;
                                n3 = n16;
                            }
                            ++n13;
                        }
                        n11 = n8;
                        n8 = n5;
                        this.mTotalLength += this.mPaddingLeft + this.mPaddingRight;
                        if (arrn2[1] != -1 || arrn2[0] != -1 || arrn2[2] != -1) break block52;
                        n5 = n3;
                        if (arrn2[3] == -1) break block53;
                    }
                    n5 = Math.max(n3, Math.max(arrn2[3], Math.max(arrn2[0], Math.max(arrn2[1], arrn2[2]))) + Math.max(arrn[3], Math.max(arrn[0], Math.max(arrn[1], arrn[2]))));
                }
                n12 = n7;
                n3 = n11;
                n7 = n8;
                n8 = n12;
                n11 = n9;
                n9 = n5;
            }
            n5 = n9;
            if (n4 == 0) {
                n5 = n9;
                if (n6 != 1073741824) {
                    n5 = n8;
                }
            }
            this.setMeasuredDimension(n3 | -16777216 & n11, LinearLayout.resolveSizeAndState(Math.max(n5 + (this.mPaddingTop + this.mPaddingBottom), this.getSuggestedMinimumHeight()), n2, n11 << 16));
            if (n10 == 0) break block54;
            this.forceUniformHeight(n7, n);
        }
    }

    int measureNullChild(int n) {
        return 0;
    }

    void measureVertical(int n, int n2) {
        block32 : {
            int n3;
            Object object;
            int n4;
            int n5;
            Object object2;
            this.mTotalLength = 0;
            int n6 = 0;
            float f = 0.0f;
            int n7 = this.getVirtualChildCount();
            int n8 = View.MeasureSpec.getMode(n);
            int n9 = View.MeasureSpec.getMode(n2);
            int n10 = this.mBaselineAlignedChildIndex;
            boolean bl = this.mUseLargestChild;
            int n11 = 0;
            int n12 = 0;
            boolean bl2 = false;
            int n13 = 0;
            int n14 = 0;
            int n15 = 0;
            int n16 = Integer.MIN_VALUE;
            int n17 = 0;
            int n18 = 1;
            for (n5 = 0; n5 < n7; ++n5) {
                object2 = this.getVirtualChildAt(n5);
                if (object2 == null) {
                    this.mTotalLength += this.measureNullChild(n5);
                    continue;
                }
                if (((View)object2).getVisibility() == 8) {
                    n5 += this.getChildrenSkipCount((View)object2, n5);
                    continue;
                }
                int n19 = n12 + 1;
                if (this.hasDividerBeforeChildAt(n5)) {
                    this.mTotalLength += this.mDividerHeight;
                }
                object = (LayoutParams)((View)object2).getLayoutParams();
                f += ((LayoutParams)object).weight;
                n3 = ((LayoutParams)object).height == 0 && ((LayoutParams)object).weight > 0.0f ? 1 : 0;
                if (n9 == 1073741824 && n3 != 0) {
                    n17 = this.mTotalLength;
                    this.mTotalLength = Math.max(n17, ((LayoutParams)object).topMargin + n17 + ((LayoutParams)object).bottomMargin);
                    n17 = 1;
                } else {
                    if (n3 != 0) {
                        ((LayoutParams)object).height = -2;
                    }
                    n12 = f == 0.0f ? this.mTotalLength : 0;
                    Object object3 = object;
                    this.measureChildBeforeLayout((View)object2, n5, n, 0, n2, n12);
                    n4 = ((View)object2).getMeasuredHeight();
                    n12 = n11;
                    if (n3 != 0) {
                        ((LayoutParams)object3).height = 0;
                        n12 = n11 + n4;
                    }
                    n11 = this.mTotalLength;
                    this.mTotalLength = Math.max(n11, n11 + n4 + ((LayoutParams)object3).topMargin + ((LayoutParams)object3).bottomMargin + this.getNextLocationOffset((View)object2));
                    if (bl) {
                        n16 = Math.max(n4, n16);
                        n11 = n12;
                    } else {
                        n11 = n12;
                    }
                }
                n12 = n15;
                if (n10 >= 0 && n10 == n5 + 1) {
                    this.mBaselineChildTop = this.mTotalLength;
                }
                if (n5 < n10 && ((LayoutParams)object).weight > 0.0f) {
                    throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                }
                n3 = 0;
                if (n8 != 1073741824 && ((LayoutParams)object).width == -1) {
                    bl2 = true;
                    n3 = 1;
                }
                int n20 = ((LayoutParams)object).leftMargin + ((LayoutParams)object).rightMargin;
                n4 = ((View)object2).getMeasuredWidth() + n20;
                n13 = Math.max(n13, n4);
                int n21 = LinearLayout.combineMeasuredStates(n14, ((View)object2).getMeasuredState());
                n15 = n18 != 0 && ((LayoutParams)object).width == -1 ? 1 : 0;
                if (((LayoutParams)object).weight > 0.0f) {
                    if (n3 != 0) {
                        n4 = n20;
                    }
                    n18 = Math.max(n6, n4);
                    n14 = n12;
                } else {
                    n18 = n6;
                    if (n3 != 0) {
                        n4 = n20;
                    }
                    n14 = Math.max(n12, n4);
                }
                n12 = n15;
                n5 += this.getChildrenSkipCount((View)object2, n5);
                n3 = n21;
                n6 = n18;
                n15 = n14;
                n18 = n12;
                n12 = n19;
                n14 = n3;
            }
            if (n12 > 0 && this.hasDividerBeforeChildAt(n7)) {
                this.mTotalLength += this.mDividerHeight;
            }
            if (bl && ((n5 = n9) == Integer.MIN_VALUE || n5 == 0)) {
                this.mTotalLength = 0;
                for (n5 = 0; n5 < n7; ++n5) {
                    object = this.getVirtualChildAt(n5);
                    if (object == null) {
                        this.mTotalLength += this.measureNullChild(n5);
                        continue;
                    }
                    if (((View)object).getVisibility() == 8) {
                        n5 += this.getChildrenSkipCount((View)object, n5);
                        continue;
                    }
                    object2 = (LayoutParams)((View)object).getLayoutParams();
                    n12 = this.mTotalLength;
                    this.mTotalLength = Math.max(n12, n12 + n16 + ((LayoutParams)object2).topMargin + ((LayoutParams)object2).bottomMargin + this.getNextLocationOffset((View)object));
                }
            }
            n12 = n9;
            this.mTotalLength += this.mPaddingTop + this.mPaddingBottom;
            n4 = LinearLayout.resolveSizeAndState(Math.max(this.mTotalLength, this.getSuggestedMinimumHeight()), n2, 0);
            n5 = n4 & 16777215;
            n9 = this.mTotalLength;
            if (this.mAllowInconsistentMeasurement) {
                n11 = 0;
            }
            n3 = n5 - n9 + n11;
            if (n17 == 0 && (!sRemeasureWeightedChildren && n3 == 0 || !(f > 0.0f))) {
                n15 = Math.max(n15, n6);
                if (bl && n12 != 1073741824) {
                    n11 = n5;
                    for (n9 = 0; n9 < n7; ++n9) {
                        object = this.getVirtualChildAt(n9);
                        if (object == null || ((View)object).getVisibility() == 8 || !(((LayoutParams)object.getLayoutParams()).weight > 0.0f)) continue;
                        ((View)object).measure(View.MeasureSpec.makeMeasureSpec(((View)object).getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(n16, 1073741824));
                    }
                }
                n11 = n14;
                n14 = n15;
                n15 = n7;
            } else {
                float f2 = this.mWeightSum;
                if (f2 > 0.0f) {
                    f = f2;
                }
                this.mTotalLength = 0;
                n9 = n15;
                n11 = n14;
                n6 = n10;
                n15 = n3;
                n14 = n9;
                for (n5 = 0; n5 < n7; ++n5) {
                    object2 = this.getVirtualChildAt(n5);
                    if (object2 == null || ((View)object2).getVisibility() == 8) continue;
                    object = (LayoutParams)((View)object2).getLayoutParams();
                    f2 = ((LayoutParams)object).weight;
                    if (f2 > 0.0f) {
                        n17 = (int)((float)n15 * f2 / f);
                        n9 = this.mUseLargestChild && n12 != 1073741824 ? n16 : (((LayoutParams)object).height == 0 && (!this.mAllowInconsistentMeasurement || n12 == 1073741824) ? n17 : ((View)object2).getMeasuredHeight() + n17);
                        n9 = View.MeasureSpec.makeMeasureSpec(Math.max(0, n9), 1073741824);
                        ((View)object2).measure(LinearLayout.getChildMeasureSpec(n, this.mPaddingLeft + this.mPaddingRight + ((LayoutParams)object).leftMargin + ((LayoutParams)object).rightMargin, ((LayoutParams)object).width), n9);
                        n11 = LinearLayout.combineMeasuredStates(n11, ((View)object2).getMeasuredState() & -256);
                        f -= f2;
                        n15 -= n17;
                    }
                    n17 = ((LayoutParams)object).leftMargin + ((LayoutParams)object).rightMargin;
                    n3 = ((View)object2).getMeasuredWidth() + n17;
                    n13 = Math.max(n13, n3);
                    n9 = n8 != 1073741824 && ((LayoutParams)object).width == -1 ? 1 : 0;
                    n9 = n9 != 0 ? n17 : n3;
                    n9 = Math.max(n14, n9);
                    n14 = n18 != 0 && ((LayoutParams)object).width == -1 ? 1 : 0;
                    n18 = this.mTotalLength;
                    this.mTotalLength = Math.max(n18, n18 + ((View)object2).getMeasuredHeight() + ((LayoutParams)object).topMargin + ((LayoutParams)object).bottomMargin + this.getNextLocationOffset((View)object2));
                    n18 = n14;
                    n14 = n9;
                }
                n15 = n7;
                this.mTotalLength += this.mPaddingTop + this.mPaddingBottom;
            }
            n16 = n13;
            if (n18 == 0) {
                n16 = n13;
                if (n8 != 1073741824) {
                    n16 = n14;
                }
            }
            this.setMeasuredDimension(LinearLayout.resolveSizeAndState(Math.max(n16 + (this.mPaddingLeft + this.mPaddingRight), this.getSuggestedMinimumWidth()), n, n11), n4);
            if (!bl2) break block32;
            this.forceUniformWidth(n15, n2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (this.mDivider == null) {
            return;
        }
        if (this.mOrientation == 1) {
            this.drawDividersVertical(canvas);
        } else {
            this.drawDividersHorizontal(canvas);
        }
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        if (this.mOrientation == 1) {
            this.layoutVertical(n, n2, n3, n4);
        } else {
            this.layoutHorizontal(n, n2, n3, n4);
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        if (this.mOrientation == 1) {
            this.measureVertical(n, n2);
        } else {
            this.measureHorizontal(n, n2);
        }
    }

    @Override
    public void onRtlPropertiesChanged(int n) {
        super.onRtlPropertiesChanged(n);
        if (n != this.mLayoutDirection) {
            this.mLayoutDirection = n;
            if (this.mOrientation == 0) {
                this.requestLayout();
            }
        }
    }

    @RemotableViewMethod
    public void setBaselineAligned(boolean bl) {
        this.mBaselineAligned = bl;
    }

    @RemotableViewMethod
    public void setBaselineAlignedChildIndex(int n) {
        if (n >= 0 && n < this.getChildCount()) {
            this.mBaselineAlignedChildIndex = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("base aligned child index out of range (0, ");
        stringBuilder.append(this.getChildCount());
        stringBuilder.append(")");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setDividerDrawable(Drawable drawable2) {
        if (drawable2 == this.mDivider) {
            return;
        }
        this.mDivider = drawable2;
        if (drawable2 != null) {
            this.mDividerWidth = drawable2.getIntrinsicWidth();
            this.mDividerHeight = drawable2.getIntrinsicHeight();
        } else {
            this.mDividerWidth = 0;
            this.mDividerHeight = 0;
        }
        this.setWillNotDraw(this.isShowingDividers() ^ true);
        this.requestLayout();
    }

    public void setDividerPadding(int n) {
        if (n == this.mDividerPadding) {
            return;
        }
        this.mDividerPadding = n;
        if (this.isShowingDividers()) {
            this.requestLayout();
            this.invalidate();
        }
    }

    @RemotableViewMethod
    public void setGravity(int n) {
        if (this.mGravity != n) {
            int n2 = n;
            if ((8388615 & n) == 0) {
                n2 = n | 8388611;
            }
            n = n2;
            if ((n2 & 112) == 0) {
                n = n2 | 48;
            }
            this.mGravity = n;
            this.requestLayout();
        }
    }

    @RemotableViewMethod
    public void setHorizontalGravity(int n) {
        int n2 = n & 8388615;
        n = this.mGravity;
        if ((8388615 & n) != n2) {
            this.mGravity = -8388616 & n | n2;
            this.requestLayout();
        }
    }

    @RemotableViewMethod
    public void setMeasureWithLargestChildEnabled(boolean bl) {
        this.mUseLargestChild = bl;
    }

    public void setOrientation(int n) {
        if (this.mOrientation != n) {
            this.mOrientation = n;
            this.requestLayout();
        }
    }

    public void setShowDividers(int n) {
        if (n == this.mShowDividers) {
            return;
        }
        this.mShowDividers = n;
        this.setWillNotDraw(this.isShowingDividers() ^ true);
        this.requestLayout();
    }

    @RemotableViewMethod
    public void setVerticalGravity(int n) {
        int n2 = this.mGravity;
        if ((n2 & 112) != (n &= 112)) {
            this.mGravity = n2 & -113 | n;
            this.requestLayout();
        }
    }

    @RemotableViewMethod
    public void setWeightSum(float f) {
        this.mWeightSum = Math.max(0.0f, f);
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DividerMode {
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        @ViewDebug.ExportedProperty(category="layout", mapping={@ViewDebug.IntToString(from=-1, to="NONE"), @ViewDebug.IntToString(from=0, to="NONE"), @ViewDebug.IntToString(from=48, to="TOP"), @ViewDebug.IntToString(from=80, to="BOTTOM"), @ViewDebug.IntToString(from=3, to="LEFT"), @ViewDebug.IntToString(from=5, to="RIGHT"), @ViewDebug.IntToString(from=8388611, to="START"), @ViewDebug.IntToString(from=8388613, to="END"), @ViewDebug.IntToString(from=16, to="CENTER_VERTICAL"), @ViewDebug.IntToString(from=112, to="FILL_VERTICAL"), @ViewDebug.IntToString(from=1, to="CENTER_HORIZONTAL"), @ViewDebug.IntToString(from=7, to="FILL_HORIZONTAL"), @ViewDebug.IntToString(from=17, to="CENTER"), @ViewDebug.IntToString(from=119, to="FILL")})
        public int gravity = -1;
        @ViewDebug.ExportedProperty(category="layout")
        public float weight;

        public LayoutParams(int n, int n2) {
            super(n, n2);
            this.weight = 0.0f;
        }

        public LayoutParams(int n, int n2, float f) {
            super(n, n2);
            this.weight = f;
        }

        public LayoutParams(Context object, AttributeSet attributeSet) {
            super((Context)object, attributeSet);
            object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.LinearLayout_Layout);
            this.weight = ((TypedArray)object).getFloat(3, 0.0f);
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
            this.weight = layoutParams.weight;
            this.gravity = layoutParams.gravity;
        }

        @Override
        public String debug(String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("LinearLayout.LayoutParams={width=");
            stringBuilder.append(LayoutParams.sizeToString(this.width));
            stringBuilder.append(", height=");
            stringBuilder.append(LayoutParams.sizeToString(this.height));
            stringBuilder.append(" weight=");
            stringBuilder.append(this.weight);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @UnsupportedAppUsage
        @Override
        protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("layout:weight", this.weight);
            viewHierarchyEncoder.addProperty("layout:gravity", this.gravity);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface OrientationMode {
    }

}

