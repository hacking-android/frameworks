/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.ResourceId;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Pools;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.accessibility.AccessibilityEvent;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.RemoteViews;
import com.android.internal.R;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

@RemoteViews.RemoteView
public class RelativeLayout
extends ViewGroup {
    public static final int ABOVE = 2;
    public static final int ALIGN_BASELINE = 4;
    public static final int ALIGN_BOTTOM = 8;
    public static final int ALIGN_END = 19;
    public static final int ALIGN_LEFT = 5;
    public static final int ALIGN_PARENT_BOTTOM = 12;
    public static final int ALIGN_PARENT_END = 21;
    public static final int ALIGN_PARENT_LEFT = 9;
    public static final int ALIGN_PARENT_RIGHT = 11;
    public static final int ALIGN_PARENT_START = 20;
    public static final int ALIGN_PARENT_TOP = 10;
    public static final int ALIGN_RIGHT = 7;
    public static final int ALIGN_START = 18;
    public static final int ALIGN_TOP = 6;
    public static final int BELOW = 3;
    public static final int CENTER_HORIZONTAL = 14;
    public static final int CENTER_IN_PARENT = 13;
    public static final int CENTER_VERTICAL = 15;
    private static final int DEFAULT_WIDTH = 65536;
    public static final int END_OF = 17;
    public static final int LEFT_OF = 0;
    public static final int RIGHT_OF = 1;
    private static final int[] RULES_HORIZONTAL;
    private static final int[] RULES_VERTICAL;
    public static final int START_OF = 16;
    public static final int TRUE = -1;
    private static final int VALUE_NOT_SET = Integer.MIN_VALUE;
    private static final int VERB_COUNT = 22;
    private boolean mAllowBrokenMeasureSpecs = false;
    private View mBaselineView = null;
    private final Rect mContentBounds = new Rect();
    private boolean mDirtyHierarchy;
    private final DependencyGraph mGraph = new DependencyGraph();
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mGravity = 8388659;
    private int mIgnoreGravity;
    private boolean mMeasureVerticalWithPaddingMargin = false;
    private final Rect mSelfBounds = new Rect();
    private View[] mSortedHorizontalChildren;
    private View[] mSortedVerticalChildren;
    private SortedSet<View> mTopToBottomLeftToRightSet = null;

    static {
        RULES_VERTICAL = new int[]{2, 3, 4, 6, 8};
        RULES_HORIZONTAL = new int[]{0, 1, 5, 7, 16, 17, 18, 19};
    }

    public RelativeLayout(Context context) {
        this(context, null);
    }

    public RelativeLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RelativeLayout(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public RelativeLayout(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.initFromAttributes(context, attributeSet, n, n2);
        this.queryCompatibilityModes(context);
    }

    private void applyHorizontalSizeRules(LayoutParams layoutParams, int n, int[] arrn) {
        layoutParams.mLeft = Integer.MIN_VALUE;
        layoutParams.mRight = Integer.MIN_VALUE;
        LayoutParams layoutParams2 = this.getRelatedViewParams(arrn, 0);
        if (layoutParams2 != null) {
            layoutParams.mRight = layoutParams2.mLeft - (layoutParams2.leftMargin + layoutParams.rightMargin);
        } else if (layoutParams.alignWithParent && arrn[0] != 0 && n >= 0) {
            layoutParams.mRight = n - this.mPaddingRight - layoutParams.rightMargin;
        }
        layoutParams2 = this.getRelatedViewParams(arrn, 1);
        if (layoutParams2 != null) {
            layoutParams.mLeft = layoutParams2.mRight + (layoutParams2.rightMargin + layoutParams.leftMargin);
        } else if (layoutParams.alignWithParent && arrn[1] != 0) {
            layoutParams.mLeft = this.mPaddingLeft + layoutParams.leftMargin;
        }
        layoutParams2 = this.getRelatedViewParams(arrn, 5);
        if (layoutParams2 != null) {
            layoutParams.mLeft = layoutParams2.mLeft + layoutParams.leftMargin;
        } else if (layoutParams.alignWithParent && arrn[5] != 0) {
            layoutParams.mLeft = this.mPaddingLeft + layoutParams.leftMargin;
        }
        layoutParams2 = this.getRelatedViewParams(arrn, 7);
        if (layoutParams2 != null) {
            layoutParams.mRight = layoutParams2.mRight - layoutParams.rightMargin;
        } else if (layoutParams.alignWithParent && arrn[7] != 0 && n >= 0) {
            layoutParams.mRight = n - this.mPaddingRight - layoutParams.rightMargin;
        }
        if (arrn[9] != 0) {
            layoutParams.mLeft = this.mPaddingLeft + layoutParams.leftMargin;
        }
        if (arrn[11] != 0 && n >= 0) {
            layoutParams.mRight = n - this.mPaddingRight - layoutParams.rightMargin;
        }
    }

    private void applyVerticalSizeRules(LayoutParams layoutParams, int n, int n2) {
        int[] arrn = layoutParams.getRules();
        int n3 = this.getRelatedViewBaselineOffset(arrn);
        if (n3 != -1) {
            n = n3;
            if (n2 != -1) {
                n = n3 - n2;
            }
            layoutParams.mTop = n;
            layoutParams.mBottom = Integer.MIN_VALUE;
            return;
        }
        layoutParams.mTop = Integer.MIN_VALUE;
        layoutParams.mBottom = Integer.MIN_VALUE;
        LayoutParams layoutParams2 = this.getRelatedViewParams(arrn, 2);
        if (layoutParams2 != null) {
            layoutParams.mBottom = layoutParams2.mTop - (layoutParams2.topMargin + layoutParams.bottomMargin);
        } else if (layoutParams.alignWithParent && arrn[2] != 0 && n >= 0) {
            layoutParams.mBottom = n - this.mPaddingBottom - layoutParams.bottomMargin;
        }
        layoutParams2 = this.getRelatedViewParams(arrn, 3);
        if (layoutParams2 != null) {
            layoutParams.mTop = layoutParams2.mBottom + (layoutParams2.bottomMargin + layoutParams.topMargin);
        } else if (layoutParams.alignWithParent && arrn[3] != 0) {
            layoutParams.mTop = this.mPaddingTop + layoutParams.topMargin;
        }
        layoutParams2 = this.getRelatedViewParams(arrn, 6);
        if (layoutParams2 != null) {
            layoutParams.mTop = layoutParams2.mTop + layoutParams.topMargin;
        } else if (layoutParams.alignWithParent && arrn[6] != 0) {
            layoutParams.mTop = this.mPaddingTop + layoutParams.topMargin;
        }
        layoutParams2 = this.getRelatedViewParams(arrn, 8);
        if (layoutParams2 != null) {
            layoutParams.mBottom = layoutParams2.mBottom - layoutParams.bottomMargin;
        } else if (layoutParams.alignWithParent && arrn[8] != 0 && n >= 0) {
            layoutParams.mBottom = n - this.mPaddingBottom - layoutParams.bottomMargin;
        }
        if (arrn[10] != 0) {
            layoutParams.mTop = this.mPaddingTop + layoutParams.topMargin;
        }
        if (arrn[12] != 0 && n >= 0) {
            layoutParams.mBottom = n - this.mPaddingBottom - layoutParams.bottomMargin;
        }
    }

    private static void centerHorizontal(View view, LayoutParams layoutParams, int n) {
        int n2 = view.getMeasuredWidth();
        n = (n - n2) / 2;
        layoutParams.mLeft = n;
        layoutParams.mRight = n + n2;
    }

    private static void centerVertical(View view, LayoutParams layoutParams, int n) {
        int n2 = view.getMeasuredHeight();
        n = (n - n2) / 2;
        layoutParams.mTop = n;
        layoutParams.mBottom = n + n2;
    }

    private int compareLayoutPosition(LayoutParams layoutParams, LayoutParams layoutParams2) {
        int n = layoutParams.mTop - layoutParams2.mTop;
        if (n != 0) {
            return n;
        }
        return layoutParams.mLeft - layoutParams2.mLeft;
    }

    private int getChildMeasureSpec(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        int n9 = 0;
        int n10 = 0;
        boolean bl = n8 < 0;
        if (bl && !this.mAllowBrokenMeasureSpecs) {
            if (n != Integer.MIN_VALUE && n2 != Integer.MIN_VALUE) {
                n2 = Math.max(0, n2 - n);
                n = 1073741824;
            } else if (n3 >= 0) {
                n2 = n3;
                n = 1073741824;
            } else {
                n2 = 0;
                n = 0;
            }
            return View.MeasureSpec.makeMeasureSpec(n2, n);
        }
        int n11 = n;
        int n12 = n2;
        int n13 = n11;
        if (n11 == Integer.MIN_VALUE) {
            n13 = n6 + n4;
        }
        n4 = n12;
        if (n12 == Integer.MIN_VALUE) {
            n4 = n8 - n7 - n5;
        }
        n5 = n4 - n13;
        n4 = 1073741824;
        if (n != Integer.MIN_VALUE && n2 != Integer.MIN_VALUE) {
            n = n4;
            if (bl) {
                n = 0;
            }
            n3 = Math.max(0, n5);
            n2 = n;
            n = n3;
        } else if (n3 >= 0) {
            n2 = 1073741824;
            n = n5 >= 0 ? Math.min(n5, n3) : n3;
        } else if (n3 == -1) {
            n = n4;
            if (bl) {
                n = 0;
            }
            n3 = Math.max(0, n5);
            n2 = n;
            n = n3;
        } else {
            n2 = n9;
            n = n10;
            if (n3 == -2) {
                if (n5 >= 0) {
                    n2 = Integer.MIN_VALUE;
                    n = n5;
                } else {
                    n2 = 0;
                    n = 0;
                }
            }
        }
        return View.MeasureSpec.makeMeasureSpec(n, n2);
    }

    private View getRelatedView(int[] object, int n) {
        int n2 = object[n];
        if (n2 != 0) {
            object = (DependencyGraph.Node)this.mGraph.mKeyNodes.get(n2);
            if (object == null) {
                return null;
            }
            object = ((DependencyGraph.Node)object).view;
            while (((View)object).getVisibility() == 8) {
                Object object2 = ((LayoutParams)((View)object).getLayoutParams()).getRules(((View)object).getLayoutDirection());
                object2 = (DependencyGraph.Node)this.mGraph.mKeyNodes.get(object2[n]);
                if (object2 != null && object != object2.view) {
                    object = object2.view;
                    continue;
                }
                return null;
            }
            return object;
        }
        return null;
    }

    private int getRelatedViewBaselineOffset(int[] object) {
        int n;
        if ((object = this.getRelatedView((int[])object, 4)) != null && (n = ((View)object).getBaseline()) != -1 && ((View)object).getLayoutParams() instanceof LayoutParams) {
            return ((LayoutParams)((View)object).getLayoutParams()).mTop + n;
        }
        return -1;
    }

    private LayoutParams getRelatedViewParams(int[] object, int n) {
        if ((object = this.getRelatedView((int[])object, n)) != null && ((View)object).getLayoutParams() instanceof LayoutParams) {
            return (LayoutParams)((View)object).getLayoutParams();
        }
        return null;
    }

    private void initFromAttributes(Context context, AttributeSet attributeSet, int n, int n2) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.RelativeLayout, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.RelativeLayout, attributeSet, typedArray, n, n2);
        this.mIgnoreGravity = typedArray.getResourceId(1, -1);
        this.mGravity = typedArray.getInt(0, this.mGravity);
        typedArray.recycle();
    }

    private void measureChild(View view, LayoutParams layoutParams, int n, int n2) {
        view.measure(this.getChildMeasureSpec(layoutParams.mLeft, layoutParams.mRight, layoutParams.width, layoutParams.leftMargin, layoutParams.rightMargin, this.mPaddingLeft, this.mPaddingRight, n), this.getChildMeasureSpec(layoutParams.mTop, layoutParams.mBottom, layoutParams.height, layoutParams.topMargin, layoutParams.bottomMargin, this.mPaddingTop, this.mPaddingBottom, n2));
    }

    private void measureChildHorizontal(View view, LayoutParams layoutParams, int n, int n2) {
        int n3 = this.getChildMeasureSpec(layoutParams.mLeft, layoutParams.mRight, layoutParams.width, layoutParams.leftMargin, layoutParams.rightMargin, this.mPaddingLeft, this.mPaddingRight, n);
        if (n2 < 0 && !this.mAllowBrokenMeasureSpecs) {
            n = layoutParams.height >= 0 ? View.MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824) : View.MeasureSpec.makeMeasureSpec(0, 0);
        } else {
            n = this.mMeasureVerticalWithPaddingMargin ? Math.max(0, n2 - this.mPaddingTop - this.mPaddingBottom - layoutParams.topMargin - layoutParams.bottomMargin) : Math.max(0, n2);
            n2 = layoutParams.height == -1 ? 1073741824 : Integer.MIN_VALUE;
            n = View.MeasureSpec.makeMeasureSpec(n, n2);
        }
        view.measure(n3, n);
    }

    private void positionAtEdge(View view, LayoutParams layoutParams, int n) {
        if (this.isLayoutRtl()) {
            layoutParams.mRight = n - this.mPaddingRight - layoutParams.rightMargin;
            layoutParams.mLeft = layoutParams.mRight - view.getMeasuredWidth();
        } else {
            layoutParams.mLeft = this.mPaddingLeft + layoutParams.leftMargin;
            layoutParams.mRight = layoutParams.mLeft + view.getMeasuredWidth();
        }
    }

    private boolean positionChildHorizontal(View view, LayoutParams layoutParams, int n, boolean bl) {
        int[] arrn = layoutParams.getRules(this.getLayoutDirection());
        int n2 = layoutParams.mLeft;
        boolean bl2 = true;
        if (n2 == Integer.MIN_VALUE && layoutParams.mRight != Integer.MIN_VALUE) {
            layoutParams.mLeft = layoutParams.mRight - view.getMeasuredWidth();
        } else if (layoutParams.mLeft != Integer.MIN_VALUE && layoutParams.mRight == Integer.MIN_VALUE) {
            layoutParams.mRight = layoutParams.mLeft + view.getMeasuredWidth();
        } else if (layoutParams.mLeft == Integer.MIN_VALUE && layoutParams.mRight == Integer.MIN_VALUE) {
            if (arrn[13] == 0 && arrn[14] == 0) {
                this.positionAtEdge(view, layoutParams, n);
            } else {
                if (!bl) {
                    RelativeLayout.centerHorizontal(view, layoutParams, n);
                } else {
                    this.positionAtEdge(view, layoutParams, n);
                }
                return true;
            }
        }
        bl = arrn[21] != 0 ? bl2 : false;
        return bl;
    }

    private boolean positionChildVertical(View view, LayoutParams layoutParams, int n, boolean bl) {
        int[] arrn = layoutParams.getRules();
        int n2 = layoutParams.mTop;
        boolean bl2 = true;
        if (n2 == Integer.MIN_VALUE && layoutParams.mBottom != Integer.MIN_VALUE) {
            layoutParams.mTop = layoutParams.mBottom - view.getMeasuredHeight();
        } else if (layoutParams.mTop != Integer.MIN_VALUE && layoutParams.mBottom == Integer.MIN_VALUE) {
            layoutParams.mBottom = layoutParams.mTop + view.getMeasuredHeight();
        } else if (layoutParams.mTop == Integer.MIN_VALUE && layoutParams.mBottom == Integer.MIN_VALUE) {
            if (arrn[13] == 0 && arrn[15] == 0) {
                layoutParams.mTop = this.mPaddingTop + layoutParams.topMargin;
                layoutParams.mBottom = layoutParams.mTop + view.getMeasuredHeight();
            } else {
                if (!bl) {
                    RelativeLayout.centerVertical(view, layoutParams, n);
                } else {
                    layoutParams.mTop = this.mPaddingTop + layoutParams.topMargin;
                    layoutParams.mBottom = layoutParams.mTop + view.getMeasuredHeight();
                }
                return true;
            }
        }
        bl = arrn[12] != 0 ? bl2 : false;
        return bl;
    }

    private void queryCompatibilityModes(Context context) {
        int n = context.getApplicationInfo().targetSdkVersion;
        boolean bl = true;
        boolean bl2 = n <= 17;
        this.mAllowBrokenMeasureSpecs = bl2;
        bl2 = n >= 18 ? bl : false;
        this.mMeasureVerticalWithPaddingMargin = bl2;
    }

    private void sortChildren() {
        int n = this.getChildCount();
        Object object = this.mSortedVerticalChildren;
        if (object == null || ((View[])object).length != n) {
            this.mSortedVerticalChildren = new View[n];
        }
        if ((object = this.mSortedHorizontalChildren) == null || ((View[])object).length != n) {
            this.mSortedHorizontalChildren = new View[n];
        }
        object = this.mGraph;
        ((DependencyGraph)object).clear();
        for (int i = 0; i < n; ++i) {
            ((DependencyGraph)object).add(this.getChildAt(i));
        }
        ((DependencyGraph)object).getSortedViews(this.mSortedVerticalChildren, RULES_VERTICAL);
        ((DependencyGraph)object).getSortedViews(this.mSortedHorizontalChildren, RULES_HORIZONTAL);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override
    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        if (this.mTopToBottomLeftToRightSet == null) {
            this.mTopToBottomLeftToRightSet = new TreeSet<View>(new TopToBottomLeftToRightComparator());
        }
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            this.mTopToBottomLeftToRightSet.add(this.getChildAt(i));
        }
        for (View view : this.mTopToBottomLeftToRightSet) {
            if (view.getVisibility() != 0 || !view.dispatchPopulateAccessibilityEvent(accessibilityEvent)) continue;
            this.mTopToBottomLeftToRightSet.clear();
            return true;
        }
        this.mTopToBottomLeftToRightSet.clear();
        return false;
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
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
        return RelativeLayout.class.getName();
    }

    @Override
    public int getBaseline() {
        View view = this.mBaselineView;
        int n = view != null ? view.getBaseline() : super.getBaseline();
        return n;
    }

    public int getGravity() {
        return this.mGravity;
    }

    public int getIgnoreGravity() {
        return this.mIgnoreGravity;
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        n2 = this.getChildCount();
        for (n = 0; n < n2; ++n) {
            View view = this.getChildAt(n);
            if (view.getVisibility() == 8) continue;
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            view.layout(layoutParams.mLeft, layoutParams.mTop, layoutParams.mRight, layoutParams.mBottom);
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        Object object;
        Object object2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        LayoutParams layoutParams;
        Object object3;
        int n11;
        boolean bl;
        Object object4;
        boolean bl2;
        Object object5;
        int n12;
        int[] arrn;
        block54 : {
            block53 : {
                if (this.mDirtyHierarchy) {
                    this.mDirtyHierarchy = false;
                    this.sortChildren();
                }
                n3 = -1;
                n8 = -1;
                n11 = 0;
                n4 = 0;
                n7 = View.MeasureSpec.getMode(n);
                n10 = View.MeasureSpec.getMode(n2);
                n6 = View.MeasureSpec.getSize(n);
                n9 = View.MeasureSpec.getSize(n2);
                if (n7 != 0) {
                    n3 = n6;
                }
                if (n10 != 0) {
                    n8 = n9;
                }
                if (n7 == 1073741824) {
                    n11 = n3;
                }
                if (n10 == 1073741824) {
                    n4 = n8;
                }
                object2 = null;
                n9 = this.mGravity & 8388615;
                bl2 = n9 != 8388611 && n9 != 0;
                n9 = this.mGravity & 112;
                bl = n9 != 48 && n9 != 0;
                n12 = 0;
                n5 = 0;
                if (bl2) break block53;
                object = object2;
                if (!bl) break block54;
            }
            n9 = this.mIgnoreGravity;
            object = object2;
            if (n9 != -1) {
                object = this.findViewById(n9);
            }
        }
        boolean bl3 = n7 != 1073741824;
        boolean bl4 = n10 != 1073741824;
        int n13 = this.getLayoutDirection();
        if (this.isLayoutRtl()) {
            n9 = n3;
            if (n3 == -1) {
                n9 = 65536;
            }
        } else {
            n9 = n3;
        }
        object2 = this.mSortedHorizontalChildren;
        n7 = ((View[])object2).length;
        n3 = n10;
        n10 = n7;
        for (n6 = 0; n6 < n10; ++n6) {
            object4 = object2[n6];
            n7 = n12;
            if (((View)object4).getVisibility() != 8) {
                object5 = (LayoutParams)((View)object4).getLayoutParams();
                this.applyHorizontalSizeRules((LayoutParams)object5, n9, ((LayoutParams)object5).getRules(n13));
                this.measureChildHorizontal((View)object4, (LayoutParams)object5, n9, n8);
                n7 = n12;
                if (this.positionChildHorizontal((View)object4, (LayoutParams)object5, n9, bl3)) {
                    n7 = 1;
                }
            }
            n12 = n7;
        }
        object5 = this.mSortedVerticalChildren;
        int n14 = ((View[])object5).length;
        int n15 = this.getContext().getApplicationInfo().targetSdkVersion;
        int n16 = Integer.MIN_VALUE;
        n7 = Integer.MIN_VALUE;
        n3 = n13;
        n6 = Integer.MAX_VALUE;
        int n17 = Integer.MAX_VALUE;
        n10 = n11;
        n13 = n7;
        n7 = n5;
        n11 = n4;
        n5 = n14;
        n14 = n8;
        n4 = n17;
        for (int i = 0; i < n5; ++i) {
            int n18;
            int n19;
            int n20;
            int n21;
            int n22;
            block59 : {
                block55 : {
                    block58 : {
                        block57 : {
                            block56 : {
                                object2 = object5[i];
                                if (((View)object2).getVisibility() == 8) break block55;
                                object4 = (LayoutParams)((View)object2).getLayoutParams();
                                this.applyVerticalSizeRules((LayoutParams)object4, n14, ((View)object2).getBaseline());
                                this.measureChild((View)object2, (LayoutParams)object4, n9, n14);
                                if (this.positionChildVertical((View)object2, (LayoutParams)object4, n14, bl4)) {
                                    n7 = 1;
                                }
                                n8 = bl3 ? (this.isLayoutRtl() ? (n15 < 19 ? Math.max(n10, n9 - ((LayoutParams)object4).mLeft) : Math.max(n10, n9 - ((LayoutParams)object4).mLeft + ((LayoutParams)object4).leftMargin)) : (n15 < 19 ? Math.max(n10, ((LayoutParams)object4).mRight) : Math.max(n10, ((LayoutParams)object4).mRight + ((LayoutParams)object4).rightMargin))) : n10;
                                n10 = n11;
                                if (bl4) {
                                    n10 = n15 < 19 ? Math.max(n11, ((LayoutParams)object4).mBottom) : Math.max(n11, ((LayoutParams)object4).mBottom + ((LayoutParams)object4).bottomMargin);
                                }
                                if (object2 != object) break block56;
                                n11 = n4;
                                n17 = n6;
                                if (!bl) break block57;
                            }
                            n17 = Math.min(n6, ((LayoutParams)object4).mLeft - ((LayoutParams)object4).leftMargin);
                            n11 = Math.min(n4, ((LayoutParams)object4).mTop - ((LayoutParams)object4).topMargin);
                        }
                        if (object2 != object) break block58;
                        n4 = n11;
                        n21 = n8;
                        n18 = n10;
                        n6 = n17;
                        n22 = n7;
                        n20 = n16;
                        n19 = n13;
                        if (!bl2) break block59;
                    }
                    n20 = Math.max(n16, ((LayoutParams)object4).mRight + ((LayoutParams)object4).rightMargin);
                    n19 = Math.max(n13, ((LayoutParams)object4).mBottom + ((LayoutParams)object4).bottomMargin);
                    n4 = n11;
                    n21 = n8;
                    n18 = n10;
                    n6 = n17;
                    n22 = n7;
                    break block59;
                }
                n19 = n13;
                n20 = n16;
                n22 = n7;
                n18 = n11;
                n21 = n10;
            }
            n10 = n21;
            n11 = n18;
            n7 = n22;
            n16 = n20;
            n13 = n19;
        }
        n8 = n5;
        object2 = null;
        object4 = null;
        for (n5 = 0; n5 < n8; ++n5) {
            block60 : {
                Object object6;
                block61 : {
                    object6 = object5[n5];
                    arrn = object4;
                    object3 = object2;
                    if (((View)object6).getVisibility() == 8) break block60;
                    layoutParams = (LayoutParams)((View)object6).getLayoutParams();
                    if (object4 == null || object2 == null) break block61;
                    arrn = object4;
                    object3 = object2;
                    if (this.compareLayoutPosition(layoutParams, (LayoutParams)object2) >= 0) break block60;
                }
                arrn = object6;
                object3 = layoutParams;
            }
            object4 = arrn;
            object2 = object3;
        }
        this.mBaselineView = object4;
        if (bl3) {
            n10 = n5 = n10 + this.mPaddingRight;
            if (this.mLayoutParams != null) {
                n10 = n5;
                if (this.mLayoutParams.width >= 0) {
                    n10 = Math.max(n5, this.mLayoutParams.width);
                }
            }
            n10 = RelativeLayout.resolveSize(Math.max(n10, this.getSuggestedMinimumWidth()), n);
            if (n12 != 0) {
                for (n = 0; n < n8; ++n) {
                    object3 = object5[n];
                    if (((View)object3).getVisibility() == 8) continue;
                    layoutParams = (LayoutParams)((View)object3).getLayoutParams();
                    arrn = layoutParams.getRules(n3);
                    if (arrn[13] == 0 && arrn[14] == 0) {
                        if (arrn[11] == 0) continue;
                        n5 = ((View)object3).getMeasuredWidth();
                        layoutParams.mLeft = n10 - this.mPaddingRight - n5;
                        layoutParams.mRight = layoutParams.mLeft + n5;
                        continue;
                    }
                    RelativeLayout.centerHorizontal((View)object3, layoutParams, n10);
                }
                n = n3;
            } else {
                n = n3;
            }
        } else {
            n = n3;
        }
        if (bl4) {
            n3 = n11 += this.mPaddingBottom;
            if (this.mLayoutParams != null) {
                n3 = n11;
                if (this.mLayoutParams.height >= 0) {
                    n3 = Math.max(n11, this.mLayoutParams.height);
                }
            }
            n11 = RelativeLayout.resolveSize(Math.max(n3, this.getSuggestedMinimumHeight()), n2);
            if (n7 != 0) {
                for (n2 = 0; n2 < n8; ++n2) {
                    object3 = object5[n2];
                    if (((View)object3).getVisibility() == 8) continue;
                    object4 = (LayoutParams)((View)object3).getLayoutParams();
                    object2 = ((LayoutParams)object4).getRules(n);
                    if (object2[13] == false && object2[15] == false) {
                        if (object2[12] == false) continue;
                        n3 = ((View)object3).getMeasuredHeight();
                        ((LayoutParams)object4).mTop = n11 - this.mPaddingBottom - n3;
                        ((LayoutParams)object4).mBottom = ((LayoutParams)object4).mTop + n3;
                        continue;
                    }
                    RelativeLayout.centerVertical((View)object3, (LayoutParams)object4, n11);
                }
            }
        }
        if (bl2 || bl) {
            object2 = this.mSelfBounds;
            ((Rect)object2).set(this.mPaddingLeft, this.mPaddingTop, n10 - this.mPaddingRight, n11 - this.mPaddingBottom);
            object3 = this.mContentBounds;
            Gravity.apply(this.mGravity, n16 - n6, n13 - n4, (Rect)object2, (Rect)object3, n);
            n2 = ((Rect)object3).left - n6;
            n3 = ((Rect)object3).top - n4;
            if (n2 != 0 || n3 != 0) {
                object4 = object;
                object = object3;
                for (n = 0; n < n8; ++n) {
                    object3 = object5[n];
                    if (((View)object3).getVisibility() == 8 || object3 == object4) continue;
                    object3 = (LayoutParams)((View)object3).getLayoutParams();
                    if (bl2) {
                        LayoutParams.access$112((LayoutParams)object3, n2);
                        LayoutParams.access$212((LayoutParams)object3, n2);
                    }
                    if (!bl) continue;
                    LayoutParams.access$412((LayoutParams)object3, n3);
                    LayoutParams.access$312((LayoutParams)object3, n3);
                }
            }
        }
        if (this.isLayoutRtl()) {
            n2 = n9 - n10;
            for (n = 0; n < n8; ++n) {
                object = object5[n];
                if (((View)object).getVisibility() == 8) continue;
                object = (LayoutParams)((View)object).getLayoutParams();
                LayoutParams.access$120((LayoutParams)object, n2);
                LayoutParams.access$220((LayoutParams)object, n2);
            }
        }
        this.setMeasuredDimension(n10, n11);
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        this.mDirtyHierarchy = true;
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
    public void setIgnoreGravity(int n) {
        this.mIgnoreGravity = n;
    }

    @RemotableViewMethod
    public void setVerticalGravity(int n) {
        int n2 = n & 112;
        n = this.mGravity;
        if ((n & 112) != n2) {
            this.mGravity = n & -113 | n2;
            this.requestLayout();
        }
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    private static class DependencyGraph {
        private SparseArray<Node> mKeyNodes = new SparseArray();
        private ArrayList<Node> mNodes = new ArrayList();
        private ArrayDeque<Node> mRoots = new ArrayDeque();

        private DependencyGraph() {
        }

        private ArrayDeque<Node> findRoots(int[] object) {
            int n;
            int[] arrn;
            Cloneable cloneable = this.mKeyNodes;
            ArrayList<Node> arrayList = this.mNodes;
            int n2 = arrayList.size();
            for (n = 0; n < n2; ++n) {
                arrn = arrayList.get(n);
                arrn.dependents.clear();
                arrn.dependencies.clear();
            }
            for (n = 0; n < n2; ++n) {
                Node node = arrayList.get(n);
                arrn = ((LayoutParams)node.view.getLayoutParams()).mRules;
                int n3 = ((int[])object).length;
                for (int i = 0; i < n3; ++i) {
                    Node node2;
                    int n4 = arrn[object[i]];
                    if (n4 <= 0 && !ResourceId.isValid(n4) || (node2 = (Node)((SparseArray)cloneable).get(n4)) == null || node2 == node) continue;
                    node2.dependents.put(node, this);
                    node.dependencies.put(n4, node2);
                }
            }
            cloneable = this.mRoots;
            ((ArrayDeque)cloneable).clear();
            for (n = 0; n < n2; ++n) {
                object = arrayList.get(n);
                if (object.dependencies.size() != 0) continue;
                ((ArrayDeque)cloneable).addLast(object);
            }
            return cloneable;
        }

        void add(View object) {
            int n = ((View)object).getId();
            object = Node.acquire((View)object);
            if (n != -1) {
                this.mKeyNodes.put(n, (Node)object);
            }
            this.mNodes.add((Node)object);
        }

        void clear() {
            ArrayList<Node> arrayList = this.mNodes;
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                arrayList.get(i).release();
            }
            arrayList.clear();
            this.mKeyNodes.clear();
            this.mRoots.clear();
        }

        void getSortedViews(View[] arrview, int ... object) {
            Object object2;
            object = this.findRoots((int[])object);
            int n = 0;
            while ((object2 = (Node)((ArrayDeque)object).pollLast()) != null) {
                Object object3 = ((Node)object2).view;
                int n2 = ((View)object3).getId();
                arrview[n] = object3;
                object2 = ((Node)object2).dependents;
                int n3 = ((ArrayMap)object2).size();
                for (int i = 0; i < n3; ++i) {
                    Node node = (Node)((ArrayMap)object2).keyAt(i);
                    object3 = node.dependencies;
                    ((SparseArray)object3).remove(n2);
                    if (((SparseArray)object3).size() != 0) continue;
                    ((ArrayDeque)object).add(node);
                }
                ++n;
            }
            if (n >= arrview.length) {
                return;
            }
            throw new IllegalStateException("Circular dependencies cannot exist in RelativeLayout");
        }

        static class Node {
            private static final int POOL_LIMIT = 100;
            private static final Pools.SynchronizedPool<Node> sPool = new Pools.SynchronizedPool(100);
            final SparseArray<Node> dependencies = new SparseArray();
            final ArrayMap<Node, DependencyGraph> dependents = new ArrayMap();
            View view;

            Node() {
            }

            static Node acquire(View view) {
                Node node;
                Node node2 = node = sPool.acquire();
                if (node == null) {
                    node2 = new Node();
                }
                node2.view = view;
                return node2;
            }

            void release() {
                this.view = null;
                this.dependents.clear();
                this.dependencies.clear();
                sPool.release(this);
            }
        }

    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        @ViewDebug.ExportedProperty(category="layout")
        public boolean alignWithParent;
        @UnsupportedAppUsage
        private int mBottom;
        private int[] mInitialRules = new int[22];
        private boolean mIsRtlCompatibilityMode = false;
        @UnsupportedAppUsage
        private int mLeft;
        private boolean mNeedsLayoutResolution;
        @UnsupportedAppUsage
        private int mRight;
        @ViewDebug.ExportedProperty(category="layout", indexMapping={@ViewDebug.IntToString(from=2, to="above"), @ViewDebug.IntToString(from=4, to="alignBaseline"), @ViewDebug.IntToString(from=8, to="alignBottom"), @ViewDebug.IntToString(from=5, to="alignLeft"), @ViewDebug.IntToString(from=12, to="alignParentBottom"), @ViewDebug.IntToString(from=9, to="alignParentLeft"), @ViewDebug.IntToString(from=11, to="alignParentRight"), @ViewDebug.IntToString(from=10, to="alignParentTop"), @ViewDebug.IntToString(from=7, to="alignRight"), @ViewDebug.IntToString(from=6, to="alignTop"), @ViewDebug.IntToString(from=3, to="below"), @ViewDebug.IntToString(from=14, to="centerHorizontal"), @ViewDebug.IntToString(from=13, to="center"), @ViewDebug.IntToString(from=15, to="centerVertical"), @ViewDebug.IntToString(from=0, to="leftOf"), @ViewDebug.IntToString(from=1, to="rightOf"), @ViewDebug.IntToString(from=18, to="alignStart"), @ViewDebug.IntToString(from=19, to="alignEnd"), @ViewDebug.IntToString(from=20, to="alignParentStart"), @ViewDebug.IntToString(from=21, to="alignParentEnd"), @ViewDebug.IntToString(from=16, to="startOf"), @ViewDebug.IntToString(from=17, to="endOf")}, mapping={@ViewDebug.IntToString(from=-1, to="true"), @ViewDebug.IntToString(from=0, to="false/NO_ID")}, resolveId=true)
        private int[] mRules = new int[22];
        private boolean mRulesChanged = false;
        @UnsupportedAppUsage
        private int mTop;

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(Context arrn, AttributeSet object) {
            super((Context)arrn, (AttributeSet)object);
            object = arrn.obtainStyledAttributes((AttributeSet)object, R.styleable.RelativeLayout_Layout);
            boolean bl = arrn.getApplicationInfo().targetSdkVersion < 17 || !arrn.getApplicationInfo().hasRtlSupport();
            this.mIsRtlCompatibilityMode = bl;
            int[] arrn2 = this.mRules;
            arrn = this.mInitialRules;
            int n = ((TypedArray)object).getIndexCount();
            block25 : for (int i = 0; i < n; ++i) {
                int n2 = ((TypedArray)object).getIndex(i);
                int n3 = -1;
                switch (n2) {
                    default: {
                        continue block25;
                    }
                    case 22: {
                        if (!((TypedArray)object).getBoolean(n2, false)) {
                            n3 = 0;
                        }
                        arrn2[21] = n3;
                        continue block25;
                    }
                    case 21: {
                        if (!((TypedArray)object).getBoolean(n2, false)) {
                            n3 = 0;
                        }
                        arrn2[20] = n3;
                        continue block25;
                    }
                    case 20: {
                        arrn2[19] = ((TypedArray)object).getResourceId(n2, 0);
                        continue block25;
                    }
                    case 19: {
                        arrn2[18] = ((TypedArray)object).getResourceId(n2, 0);
                        continue block25;
                    }
                    case 18: {
                        arrn2[17] = ((TypedArray)object).getResourceId(n2, 0);
                        continue block25;
                    }
                    case 17: {
                        arrn2[16] = ((TypedArray)object).getResourceId(n2, 0);
                        continue block25;
                    }
                    case 16: {
                        this.alignWithParent = ((TypedArray)object).getBoolean(n2, false);
                        continue block25;
                    }
                    case 15: {
                        if (!((TypedArray)object).getBoolean(n2, false)) {
                            n3 = 0;
                        }
                        arrn2[15] = n3;
                        continue block25;
                    }
                    case 14: {
                        if (!((TypedArray)object).getBoolean(n2, false)) {
                            n3 = 0;
                        }
                        arrn2[14] = n3;
                        continue block25;
                    }
                    case 13: {
                        if (!((TypedArray)object).getBoolean(n2, false)) {
                            n3 = 0;
                        }
                        arrn2[13] = n3;
                        continue block25;
                    }
                    case 12: {
                        if (!((TypedArray)object).getBoolean(n2, false)) {
                            n3 = 0;
                        }
                        arrn2[12] = n3;
                        continue block25;
                    }
                    case 11: {
                        if (!((TypedArray)object).getBoolean(n2, false)) {
                            n3 = 0;
                        }
                        arrn2[11] = n3;
                        continue block25;
                    }
                    case 10: {
                        if (!((TypedArray)object).getBoolean(n2, false)) {
                            n3 = 0;
                        }
                        arrn2[10] = n3;
                        continue block25;
                    }
                    case 9: {
                        if (!((TypedArray)object).getBoolean(n2, false)) {
                            n3 = 0;
                        }
                        arrn2[9] = n3;
                        continue block25;
                    }
                    case 8: {
                        arrn2[8] = ((TypedArray)object).getResourceId(n2, 0);
                        continue block25;
                    }
                    case 7: {
                        arrn2[7] = ((TypedArray)object).getResourceId(n2, 0);
                        continue block25;
                    }
                    case 6: {
                        arrn2[6] = ((TypedArray)object).getResourceId(n2, 0);
                        continue block25;
                    }
                    case 5: {
                        arrn2[5] = ((TypedArray)object).getResourceId(n2, 0);
                        continue block25;
                    }
                    case 4: {
                        arrn2[4] = ((TypedArray)object).getResourceId(n2, 0);
                        continue block25;
                    }
                    case 3: {
                        arrn2[3] = ((TypedArray)object).getResourceId(n2, 0);
                        continue block25;
                    }
                    case 2: {
                        arrn2[2] = ((TypedArray)object).getResourceId(n2, 0);
                        continue block25;
                    }
                    case 1: {
                        arrn2[1] = ((TypedArray)object).getResourceId(n2, 0);
                        continue block25;
                    }
                    case 0: {
                        arrn2[0] = ((TypedArray)object).getResourceId(n2, 0);
                    }
                }
            }
            this.mRulesChanged = true;
            System.arraycopy(arrn2, 0, arrn, 0, 22);
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
            this.mIsRtlCompatibilityMode = layoutParams.mIsRtlCompatibilityMode;
            this.mRulesChanged = layoutParams.mRulesChanged;
            this.alignWithParent = layoutParams.alignWithParent;
            System.arraycopy(layoutParams.mRules, 0, this.mRules, 0, 22);
            System.arraycopy(layoutParams.mInitialRules, 0, this.mInitialRules, 0, 22);
        }

        static /* synthetic */ int access$112(LayoutParams layoutParams, int n) {
            layoutParams.mLeft = n = layoutParams.mLeft + n;
            return n;
        }

        static /* synthetic */ int access$120(LayoutParams layoutParams, int n) {
            layoutParams.mLeft = n = layoutParams.mLeft - n;
            return n;
        }

        static /* synthetic */ int access$212(LayoutParams layoutParams, int n) {
            layoutParams.mRight = n = layoutParams.mRight + n;
            return n;
        }

        static /* synthetic */ int access$220(LayoutParams layoutParams, int n) {
            layoutParams.mRight = n = layoutParams.mRight - n;
            return n;
        }

        static /* synthetic */ int access$312(LayoutParams layoutParams, int n) {
            layoutParams.mBottom = n = layoutParams.mBottom + n;
            return n;
        }

        static /* synthetic */ int access$412(LayoutParams layoutParams, int n) {
            layoutParams.mTop = n = layoutParams.mTop + n;
            return n;
        }

        private boolean hasRelativeRules() {
            int[] arrn = this.mInitialRules;
            boolean bl = arrn[16] != 0 || arrn[17] != 0 || arrn[18] != 0 || arrn[19] != 0 || arrn[20] != 0 || arrn[21] != 0;
            return bl;
        }

        private boolean isRelativeRule(int n) {
            boolean bl = n == 16 || n == 17 || n == 18 || n == 19 || n == 20 || n == 21;
            return bl;
        }

        private void resolveRules(int n) {
            int n2 = 1;
            n = n == 1 ? 1 : 0;
            System.arraycopy(this.mInitialRules, 0, this.mRules, 0, 22);
            boolean bl = this.mIsRtlCompatibilityMode;
            int n3 = 11;
            if (bl) {
                int[] arrn = this.mRules;
                if (arrn[18] != 0) {
                    if (arrn[5] == 0) {
                        arrn[5] = arrn[18];
                    }
                    this.mRules[18] = 0;
                }
                if ((arrn = this.mRules)[19] != 0) {
                    if (arrn[7] == 0) {
                        arrn[7] = arrn[19];
                    }
                    this.mRules[19] = 0;
                }
                if ((arrn = this.mRules)[16] != 0) {
                    if (arrn[0] == 0) {
                        arrn[0] = arrn[16];
                    }
                    this.mRules[16] = 0;
                }
                if ((arrn = this.mRules)[17] != 0) {
                    if (arrn[1] == 0) {
                        arrn[1] = arrn[17];
                    }
                    this.mRules[17] = 0;
                }
                if ((arrn = this.mRules)[20] != 0) {
                    if (arrn[9] == 0) {
                        arrn[9] = arrn[20];
                    }
                    this.mRules[20] = 0;
                }
                if ((arrn = this.mRules)[21] != 0) {
                    if (arrn[11] == 0) {
                        arrn[11] = arrn[21];
                    }
                    this.mRules[21] = 0;
                }
            } else {
                int[] arrn;
                int n4;
                int[] arrn2 = this.mRules;
                if (!(arrn2[18] == 0 && arrn2[19] == 0 || (arrn2 = this.mRules)[5] == 0 && arrn2[7] == 0)) {
                    arrn2 = this.mRules;
                    arrn2[5] = 0;
                    arrn2[7] = 0;
                }
                if ((arrn = this.mRules)[18] != 0) {
                    n4 = n != 0 ? 7 : 5;
                    arrn2 = this.mRules;
                    arrn[n4] = arrn2[18];
                    arrn2[18] = 0;
                }
                if ((arrn2 = this.mRules)[19] != 0) {
                    n4 = n != 0 ? 5 : 7;
                    arrn = this.mRules;
                    arrn2[n4] = arrn[19];
                    arrn[19] = 0;
                }
                if (!((arrn2 = this.mRules)[16] == 0 && arrn2[17] == 0 || (arrn2 = this.mRules)[0] == 0 && arrn2[1] == 0)) {
                    arrn2 = this.mRules;
                    arrn2[0] = 0;
                    arrn2[1] = 0;
                }
                if ((arrn = this.mRules)[16] != 0) {
                    n4 = n != 0 ? 1 : 0;
                    arrn2 = this.mRules;
                    arrn[n4] = arrn2[16];
                    arrn2[16] = 0;
                }
                if ((arrn = this.mRules)[17] != 0) {
                    n4 = n2;
                    if (n != 0) {
                        n4 = 0;
                    }
                    arrn2 = this.mRules;
                    arrn[n4] = arrn2[17];
                    arrn2[17] = 0;
                }
                if (!((arrn2 = this.mRules)[20] == 0 && arrn2[21] == 0 || (arrn2 = this.mRules)[9] == 0 && arrn2[11] == 0)) {
                    arrn2 = this.mRules;
                    arrn2[9] = 0;
                    arrn2[11] = 0;
                }
                if ((arrn2 = this.mRules)[20] != 0) {
                    n4 = n != 0 ? 11 : 9;
                    arrn = this.mRules;
                    arrn2[n4] = arrn[20];
                    arrn[20] = 0;
                }
                if ((arrn = this.mRules)[21] != 0) {
                    n4 = n3;
                    if (n != 0) {
                        n4 = 9;
                    }
                    arrn2 = this.mRules;
                    arrn[n4] = arrn2[21];
                    arrn2[21] = 0;
                }
            }
            this.mRulesChanged = false;
            this.mNeedsLayoutResolution = false;
        }

        private boolean shouldResolveLayoutDirection(int n) {
            boolean bl = !(!this.mNeedsLayoutResolution && !this.hasRelativeRules() || !this.mRulesChanged && n == this.getLayoutDirection());
            return bl;
        }

        public void addRule(int n) {
            this.addRule(n, -1);
        }

        public void addRule(int n, int n2) {
            if (!this.mNeedsLayoutResolution && this.isRelativeRule(n) && this.mInitialRules[n] != 0 && n2 == 0) {
                this.mNeedsLayoutResolution = true;
            }
            this.mRules[n] = n2;
            this.mInitialRules[n] = n2;
            this.mRulesChanged = true;
        }

        @Override
        public String debug(String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("ViewGroup.LayoutParams={ width=");
            stringBuilder.append(LayoutParams.sizeToString(this.width));
            stringBuilder.append(", height=");
            stringBuilder.append(LayoutParams.sizeToString(this.height));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }

        @Override
        protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("layout:alignWithParent", this.alignWithParent);
        }

        public int getRule(int n) {
            return this.mRules[n];
        }

        public int[] getRules() {
            return this.mRules;
        }

        public int[] getRules(int n) {
            this.resolveLayoutDirection(n);
            return this.mRules;
        }

        public void removeRule(int n) {
            this.addRule(n, 0);
        }

        @Override
        public void resolveLayoutDirection(int n) {
            if (this.shouldResolveLayoutDirection(n)) {
                this.resolveRules(n);
            }
            super.resolveLayoutDirection(n);
        }

        public static final class InspectionCompanion
        implements android.view.inspector.InspectionCompanion<LayoutParams> {
            private int mAboveId;
            private int mAlignBaselineId;
            private int mAlignBottomId;
            private int mAlignEndId;
            private int mAlignLeftId;
            private int mAlignParentBottomId;
            private int mAlignParentEndId;
            private int mAlignParentLeftId;
            private int mAlignParentRightId;
            private int mAlignParentStartId;
            private int mAlignParentTopId;
            private int mAlignRightId;
            private int mAlignStartId;
            private int mAlignTopId;
            private int mAlignWithParentIfMissingId;
            private int mBelowId;
            private int mCenterHorizontalId;
            private int mCenterInParentId;
            private int mCenterVerticalId;
            private boolean mPropertiesMapped;
            private int mToEndOfId;
            private int mToLeftOfId;
            private int mToRightOfId;
            private int mToStartOfId;

            @Override
            public void mapProperties(PropertyMapper propertyMapper) {
                this.mPropertiesMapped = true;
                this.mAboveId = propertyMapper.mapResourceId("layout_above", 16843140);
                this.mAlignBaselineId = propertyMapper.mapResourceId("layout_alignBaseline", 16843142);
                this.mAlignBottomId = propertyMapper.mapResourceId("layout_alignBottom", 16843146);
                this.mAlignEndId = propertyMapper.mapResourceId("layout_alignEnd", 16843706);
                this.mAlignLeftId = propertyMapper.mapResourceId("layout_alignLeft", 16843143);
                this.mAlignParentBottomId = propertyMapper.mapBoolean("layout_alignParentBottom", 16843150);
                this.mAlignParentEndId = propertyMapper.mapBoolean("layout_alignParentEnd", 16843708);
                this.mAlignParentLeftId = propertyMapper.mapBoolean("layout_alignParentLeft", 16843147);
                this.mAlignParentRightId = propertyMapper.mapBoolean("layout_alignParentRight", 16843149);
                this.mAlignParentStartId = propertyMapper.mapBoolean("layout_alignParentStart", 16843707);
                this.mAlignParentTopId = propertyMapper.mapBoolean("layout_alignParentTop", 16843148);
                this.mAlignRightId = propertyMapper.mapResourceId("layout_alignRight", 16843145);
                this.mAlignStartId = propertyMapper.mapResourceId("layout_alignStart", 16843705);
                this.mAlignTopId = propertyMapper.mapResourceId("layout_alignTop", 16843144);
                this.mAlignWithParentIfMissingId = propertyMapper.mapBoolean("layout_alignWithParentIfMissing", 16843154);
                this.mBelowId = propertyMapper.mapResourceId("layout_below", 16843141);
                this.mCenterHorizontalId = propertyMapper.mapBoolean("layout_centerHorizontal", 16843152);
                this.mCenterInParentId = propertyMapper.mapBoolean("layout_centerInParent", 16843151);
                this.mCenterVerticalId = propertyMapper.mapBoolean("layout_centerVertical", 16843153);
                this.mToEndOfId = propertyMapper.mapResourceId("layout_toEndOf", 16843704);
                this.mToLeftOfId = propertyMapper.mapResourceId("layout_toLeftOf", 16843138);
                this.mToRightOfId = propertyMapper.mapResourceId("layout_toRightOf", 16843139);
                this.mToStartOfId = propertyMapper.mapResourceId("layout_toStartOf", 16843703);
            }

            @Override
            public void readProperties(LayoutParams layoutParams, PropertyReader propertyReader) {
                if (this.mPropertiesMapped) {
                    int[] arrn = layoutParams.getRules();
                    propertyReader.readResourceId(this.mAboveId, arrn[2]);
                    propertyReader.readResourceId(this.mAlignBaselineId, arrn[4]);
                    propertyReader.readResourceId(this.mAlignBottomId, arrn[8]);
                    propertyReader.readResourceId(this.mAlignEndId, arrn[19]);
                    propertyReader.readResourceId(this.mAlignLeftId, arrn[5]);
                    int n = this.mAlignParentBottomId;
                    boolean bl = arrn[12] == -1;
                    propertyReader.readBoolean(n, bl);
                    n = this.mAlignParentEndId;
                    bl = arrn[21] == -1;
                    propertyReader.readBoolean(n, bl);
                    n = this.mAlignParentLeftId;
                    bl = arrn[9] == -1;
                    propertyReader.readBoolean(n, bl);
                    n = this.mAlignParentRightId;
                    bl = arrn[11] == -1;
                    propertyReader.readBoolean(n, bl);
                    n = this.mAlignParentStartId;
                    bl = arrn[20] == -1;
                    propertyReader.readBoolean(n, bl);
                    n = this.mAlignParentTopId;
                    bl = arrn[10] == -1;
                    propertyReader.readBoolean(n, bl);
                    propertyReader.readResourceId(this.mAlignRightId, arrn[7]);
                    propertyReader.readResourceId(this.mAlignStartId, arrn[18]);
                    propertyReader.readResourceId(this.mAlignTopId, arrn[6]);
                    propertyReader.readBoolean(this.mAlignWithParentIfMissingId, layoutParams.alignWithParent);
                    propertyReader.readResourceId(this.mBelowId, arrn[3]);
                    n = this.mCenterHorizontalId;
                    bl = arrn[14] == -1;
                    propertyReader.readBoolean(n, bl);
                    n = this.mCenterInParentId;
                    bl = arrn[13] == -1;
                    propertyReader.readBoolean(n, bl);
                    n = this.mCenterVerticalId;
                    bl = arrn[15] == -1;
                    propertyReader.readBoolean(n, bl);
                    propertyReader.readResourceId(this.mToEndOfId, arrn[17]);
                    propertyReader.readResourceId(this.mToLeftOfId, arrn[0]);
                    propertyReader.readResourceId(this.mToRightOfId, arrn[1]);
                    propertyReader.readResourceId(this.mToStartOfId, arrn[16]);
                    return;
                }
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
        }

    }

    private class TopToBottomLeftToRightComparator
    implements Comparator<View> {
        private TopToBottomLeftToRightComparator() {
        }

        @Override
        public int compare(View view, View view2) {
            int n = view.getTop() - view2.getTop();
            if (n != 0) {
                return n;
            }
            n = view.getLeft() - view2.getLeft();
            if (n != 0) {
                return n;
            }
            n = view.getHeight() - view2.getHeight();
            if (n != 0) {
                return n;
            }
            n = view.getWidth() - view2.getWidth();
            if (n != 0) {
                return n;
            }
            return 0;
        }
    }

}

