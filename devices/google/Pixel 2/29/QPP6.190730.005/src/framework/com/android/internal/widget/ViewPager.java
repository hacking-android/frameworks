/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.MathUtils;
import android.view.AbsSavedState;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.Scroller;
import com.android.internal.widget.PagerAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ViewPager
extends ViewGroup {
    private static final int CLOSE_ENOUGH = 2;
    private static final Comparator<ItemInfo> COMPARATOR;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_GUTTER_SIZE = 16;
    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private static final int DRAW_ORDER_DEFAULT = 0;
    private static final int DRAW_ORDER_FORWARD = 1;
    private static final int DRAW_ORDER_REVERSE = 2;
    private static final int INVALID_POINTER = -1;
    private static final int[] LAYOUT_ATTRS;
    private static final int MAX_SCROLL_X = 16777216;
    private static final int MAX_SETTLE_DURATION = 600;
    private static final int MIN_DISTANCE_FOR_FLING = 25;
    private static final int MIN_FLING_VELOCITY = 400;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "ViewPager";
    private static final boolean USE_CACHE = false;
    private static final Interpolator sInterpolator;
    private static final ViewPositionComparator sPositionComparator;
    private int mActivePointerId = -1;
    private PagerAdapter mAdapter;
    private OnAdapterChangeListener mAdapterChangeListener;
    private int mBottomPageBounds;
    private boolean mCalledSuper;
    private int mChildHeightMeasureSpec;
    private int mChildWidthMeasureSpec;
    private final int mCloseEnough;
    private int mCurItem;
    private int mDecorChildCount;
    private final int mDefaultGutterSize;
    private int mDrawingOrder;
    private ArrayList<View> mDrawingOrderedChildren;
    private final Runnable mEndScrollRunnable = new Runnable(){

        @Override
        public void run() {
            ViewPager.this.setScrollState(0);
            ViewPager.this.populate();
        }
    };
    private int mExpectedAdapterCount;
    private boolean mFirstLayout = true;
    private float mFirstOffset = -3.4028235E38f;
    private final int mFlingDistance;
    private int mGutterSize;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private OnPageChangeListener mInternalPageChangeListener;
    private boolean mIsBeingDragged;
    private boolean mIsUnableToDrag;
    private final ArrayList<ItemInfo> mItems = new ArrayList();
    private float mLastMotionX;
    private float mLastMotionY;
    private float mLastOffset = Float.MAX_VALUE;
    private final EdgeEffect mLeftEdge;
    private int mLeftIncr = -1;
    private Drawable mMarginDrawable;
    private final int mMaximumVelocity;
    private final int mMinimumVelocity;
    private PagerObserver mObserver;
    private int mOffscreenPageLimit = 1;
    private OnPageChangeListener mOnPageChangeListener;
    private int mPageMargin;
    private PageTransformer mPageTransformer;
    private boolean mPopulatePending;
    private Parcelable mRestoredAdapterState = null;
    private ClassLoader mRestoredClassLoader = null;
    private int mRestoredCurItem = -1;
    private final EdgeEffect mRightEdge;
    private int mScrollState = 0;
    private final Scroller mScroller;
    private boolean mScrollingCacheEnabled;
    private final ItemInfo mTempItem = new ItemInfo();
    private final Rect mTempRect = new Rect();
    private int mTopPageBounds;
    private final int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    static {
        LAYOUT_ATTRS = new int[]{16842931};
        COMPARATOR = new Comparator<ItemInfo>(){

            @Override
            public int compare(ItemInfo itemInfo, ItemInfo itemInfo2) {
                return itemInfo.position - itemInfo2.position;
            }
        };
        sInterpolator = new Interpolator(){

            @Override
            public float getInterpolation(float f) {
                return (f -= 1.0f) * f * f * f * f + 1.0f;
            }
        };
        sPositionComparator = new ViewPositionComparator();
    }

    public ViewPager(Context context) {
        this(context, null);
    }

    public ViewPager(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ViewPager(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ViewPager(Context context, AttributeSet object, int n, int n2) {
        super(context, (AttributeSet)object, n, n2);
        this.setWillNotDraw(false);
        this.setDescendantFocusability(262144);
        this.setFocusable(true);
        this.mScroller = new Scroller(context, sInterpolator);
        object = ViewConfiguration.get(context);
        float f = context.getResources().getDisplayMetrics().density;
        this.mTouchSlop = ((ViewConfiguration)object).getScaledPagingTouchSlop();
        this.mMinimumVelocity = (int)(400.0f * f);
        this.mMaximumVelocity = ((ViewConfiguration)object).getScaledMaximumFlingVelocity();
        this.mLeftEdge = new EdgeEffect(context);
        this.mRightEdge = new EdgeEffect(context);
        this.mFlingDistance = (int)(25.0f * f);
        this.mCloseEnough = (int)(2.0f * f);
        this.mDefaultGutterSize = (int)(16.0f * f);
        if (this.getImportantForAccessibility() == 0) {
            this.setImportantForAccessibility(1);
        }
    }

    private void calculatePageOffsets(ItemInfo itemInfo, int n, ItemInfo itemInfo2) {
        float f;
        int n2;
        float f2;
        int n3;
        int n4 = this.mAdapter.getCount();
        int n5 = this.getPaddedWidth();
        float f3 = n5 > 0 ? (float)this.mPageMargin / (float)n5 : 0.0f;
        if (itemInfo2 != null) {
            n5 = itemInfo2.position;
            if (n5 < itemInfo.position) {
                n2 = 0;
                f2 = itemInfo2.offset + itemInfo2.widthFactor + f3;
                ++n5;
                while (n5 <= itemInfo.position && n2 < this.mItems.size()) {
                    itemInfo2 = this.mItems.get(n2);
                    do {
                        f = f2;
                        n3 = n5;
                        if (n5 <= itemInfo2.position) break;
                        f = f2;
                        n3 = n5;
                        if (n2 >= this.mItems.size() - 1) break;
                        itemInfo2 = this.mItems.get(++n2);
                    } while (true);
                    while (n3 < itemInfo2.position) {
                        f += this.mAdapter.getPageWidth(n3) + f3;
                        ++n3;
                    }
                    itemInfo2.offset = f;
                    f2 = f + (itemInfo2.widthFactor + f3);
                    n5 = n3 + 1;
                }
            } else if (n5 > itemInfo.position) {
                n2 = this.mItems.size() - 1;
                f2 = itemInfo2.offset;
                --n5;
                while (n5 >= itemInfo.position && n2 >= 0) {
                    itemInfo2 = this.mItems.get(n2);
                    do {
                        f = f2;
                        n3 = n5;
                        if (n5 >= itemInfo2.position) break;
                        f = f2;
                        n3 = n5;
                        if (n2 <= 0) break;
                        itemInfo2 = this.mItems.get(--n2);
                    } while (true);
                    while (n3 > itemInfo2.position) {
                        f -= this.mAdapter.getPageWidth(n3) + f3;
                        --n3;
                    }
                    itemInfo2.offset = f2 = f - (itemInfo2.widthFactor + f3);
                    n5 = n3 - 1;
                }
            }
        }
        n3 = this.mItems.size();
        f = itemInfo.offset;
        n5 = itemInfo.position - 1;
        f2 = itemInfo.position == 0 ? itemInfo.offset : -3.4028235E38f;
        this.mFirstOffset = f2;
        f2 = itemInfo.position == n4 - 1 ? itemInfo.offset + itemInfo.widthFactor - 1.0f : Float.MAX_VALUE;
        this.mLastOffset = f2;
        n2 = n - 1;
        f2 = f;
        while (n2 >= 0) {
            itemInfo2 = this.mItems.get(n2);
            while (n5 > itemInfo2.position) {
                f2 -= this.mAdapter.getPageWidth(n5) + f3;
                --n5;
            }
            itemInfo2.offset = f2 -= itemInfo2.widthFactor + f3;
            if (itemInfo2.position == 0) {
                this.mFirstOffset = f2;
            }
            --n2;
            --n5;
        }
        f2 = itemInfo.offset + itemInfo.widthFactor + f3;
        n2 = itemInfo.position + 1;
        n5 = n + 1;
        n = n2;
        while (n5 < n3) {
            itemInfo = this.mItems.get(n5);
            while (n < itemInfo.position) {
                f2 += this.mAdapter.getPageWidth(n) + f3;
                ++n;
            }
            if (itemInfo.position == n4 - 1) {
                this.mLastOffset = itemInfo.widthFactor + f2 - 1.0f;
            }
            itemInfo.offset = f2;
            f2 += itemInfo.widthFactor + f3;
            ++n5;
            ++n;
        }
    }

    private boolean canScroll() {
        PagerAdapter pagerAdapter = this.mAdapter;
        boolean bl = true;
        if (pagerAdapter == null || pagerAdapter.getCount() <= 1) {
            bl = false;
        }
        return bl;
    }

    private void completeScroll(boolean bl) {
        int n;
        boolean bl2 = this.mScrollState == 2;
        if (bl2) {
            this.setScrollingCacheEnabled(false);
            this.mScroller.abortAnimation();
            n = this.getScrollX();
            int n2 = this.getScrollY();
            int n3 = this.mScroller.getCurrX();
            int n4 = this.mScroller.getCurrY();
            if (n != n3 || n2 != n4) {
                this.scrollTo(n3, n4);
            }
        }
        this.mPopulatePending = false;
        for (n = 0; n < this.mItems.size(); ++n) {
            ItemInfo itemInfo = this.mItems.get(n);
            if (!itemInfo.scrolling) continue;
            bl2 = true;
            itemInfo.scrolling = false;
        }
        if (bl2) {
            if (bl) {
                this.postOnAnimation(this.mEndScrollRunnable);
            } else {
                this.mEndScrollRunnable.run();
            }
        }
    }

    private int determineTargetPage(int n, float f, int n2, int n3) {
        if (Math.abs(n3) > this.mFlingDistance && Math.abs(n2) > this.mMinimumVelocity) {
            n2 = n2 < 0 ? this.mLeftIncr : 0;
            n -= n2;
        } else {
            float f2 = n >= this.mCurItem ? 0.4f : 0.6f;
            n = (int)((float)n - (float)this.mLeftIncr * (f + f2));
        }
        n2 = n;
        if (this.mItems.size() > 0) {
            ItemInfo itemInfo = this.mItems.get(0);
            Object object = this.mItems;
            object = ((ArrayList)object).get(((ArrayList)object).size() - 1);
            n2 = MathUtils.constrain(n, itemInfo.position, ((ItemInfo)object).position);
        }
        return n2;
    }

    private void enableLayers(boolean bl) {
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            int n2 = bl ? 2 : 0;
            this.getChildAt(i).setLayerType(n2, null);
        }
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private Rect getChildRectInPagerCoordinates(Rect object, View view) {
        Rect rect = object;
        if (object == null) {
            rect = new Rect();
        }
        if (view == null) {
            rect.set(0, 0, 0, 0);
            return rect;
        }
        rect.left = view.getLeft();
        rect.right = view.getRight();
        rect.top = view.getTop();
        rect.bottom = view.getBottom();
        for (object = view.getParent(); object instanceof ViewGroup && object != this; object = object.getParent()) {
            object = (ViewGroup)object;
            rect.left += ((View)object).getLeft();
            rect.right += ((View)object).getRight();
            rect.top += ((View)object).getTop();
            rect.bottom += ((View)object).getBottom();
        }
        return rect;
    }

    private int getLeftEdgeForItem(int n) {
        ItemInfo itemInfo = this.infoForPosition(n);
        if (itemInfo == null) {
            return 0;
        }
        n = this.getPaddedWidth();
        int n2 = (int)((float)n * MathUtils.constrain(itemInfo.offset, this.mFirstOffset, this.mLastOffset));
        if (this.isLayoutRtl()) {
            return 16777216 - (int)((float)n * itemInfo.widthFactor + 0.5f) - n2;
        }
        return n2;
    }

    private int getPaddedWidth() {
        return this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight();
    }

    private int getScrollStart() {
        if (this.isLayoutRtl()) {
            return 16777216 - this.getScrollX();
        }
        return this.getScrollX();
    }

    private ItemInfo infoForFirstVisiblePage() {
        int n = this.getScrollStart();
        int n2 = this.getPaddedWidth();
        float f = 0.0f;
        float f2 = n2 > 0 ? (float)n / (float)n2 : 0.0f;
        if (n2 > 0) {
            f = (float)this.mPageMargin / (float)n2;
        }
        int n3 = -1;
        float f3 = 0.0f;
        float f4 = 0.0f;
        boolean bl = true;
        ItemInfo itemInfo = null;
        int n4 = this.mItems.size();
        n2 = 0;
        while (n2 < n4) {
            ItemInfo itemInfo2 = this.mItems.get(n2);
            int n5 = n2;
            ItemInfo itemInfo3 = itemInfo2;
            if (!bl) {
                n5 = n2;
                itemInfo3 = itemInfo2;
                if (itemInfo2.position != n3 + 1) {
                    itemInfo3 = this.mTempItem;
                    itemInfo3.offset = f3 + f4 + f;
                    itemInfo3.position = n3 + 1;
                    itemInfo3.widthFactor = this.mAdapter.getPageWidth(itemInfo3.position);
                    n5 = n2 - 1;
                }
            }
            f3 = itemInfo3.offset;
            if (!bl && !(f2 >= f3)) {
                return itemInfo;
            }
            if (!(f2 < itemInfo3.widthFactor + f3 + f) && n5 != this.mItems.size() - 1) {
                bl = false;
                n3 = itemInfo3.position;
                f4 = itemInfo3.widthFactor;
                n2 = n5 + 1;
                itemInfo = itemInfo3;
                continue;
            }
            return itemInfo3;
        }
        return itemInfo;
    }

    private boolean isGutterDrag(float f, float f2) {
        boolean bl = f < (float)this.mGutterSize && f2 > 0.0f || f > (float)(this.getWidth() - this.mGutterSize) && f2 < 0.0f;
        return bl;
    }

    private void onSecondaryPointerUp(MotionEvent object) {
        int n = ((MotionEvent)object).getActionIndex();
        if (((MotionEvent)object).getPointerId(n) == this.mActivePointerId) {
            n = n == 0 ? 1 : 0;
            this.mLastMotionX = ((MotionEvent)object).getX(n);
            this.mActivePointerId = ((MotionEvent)object).getPointerId(n);
            object = this.mVelocityTracker;
            if (object != null) {
                ((VelocityTracker)object).clear();
            }
        }
    }

    private boolean pageScrolled(int n) {
        if (this.mItems.size() == 0) {
            this.mCalledSuper = false;
            this.onPageScrolled(0, 0.0f, 0);
            if (this.mCalledSuper) {
                return false;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        if (this.isLayoutRtl()) {
            n = 16777216 - n;
        }
        ItemInfo itemInfo = this.infoForFirstVisiblePage();
        int n2 = this.getPaddedWidth();
        int n3 = this.mPageMargin;
        float f = (float)n3 / (float)n2;
        int n4 = itemInfo.position;
        f = ((float)n / (float)n2 - itemInfo.offset) / (itemInfo.widthFactor + f);
        n = (int)((float)(n2 + n3) * f);
        this.mCalledSuper = false;
        this.onPageScrolled(n4, f, n);
        if (this.mCalledSuper) {
            return true;
        }
        throw new IllegalStateException("onPageScrolled did not call superclass implementation");
    }

    private boolean performDrag(float f) {
        EdgeEffect edgeEffect;
        EdgeEffect edgeEffect2;
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        int n = this.getPaddedWidth();
        float f2 = this.mLastMotionX;
        this.mLastMotionX = f;
        if (this.isLayoutRtl()) {
            edgeEffect2 = this.mRightEdge;
            edgeEffect = this.mLeftEdge;
        } else {
            edgeEffect2 = this.mLeftEdge;
            edgeEffect = this.mRightEdge;
        }
        f = (float)this.getScrollX() + (f2 - f);
        if (this.isLayoutRtl()) {
            f = 1.6777216E7f - f;
        }
        Object object = this.mItems.get(0);
        boolean bl4 = ((ItemInfo)object).position == 0;
        f2 = bl4 ? ((ItemInfo)object).offset * (float)n : (float)n * this.mFirstOffset;
        object = this.mItems;
        object = (ItemInfo)((ArrayList)object).get(((ArrayList)object).size() - 1);
        boolean bl5 = ((ItemInfo)object).position == this.mAdapter.getCount() - 1;
        float f3 = bl5 ? ((ItemInfo)object).offset * (float)n : (float)n * this.mLastOffset;
        if (f < f2) {
            if (bl4) {
                edgeEffect2.onPull(Math.abs(f2 - f) / (float)n);
                bl3 = true;
            }
            f = f2;
        } else if (f > f3) {
            bl3 = bl;
            if (bl5) {
                edgeEffect.onPull(Math.abs(f - f3) / (float)n);
                bl3 = true;
            }
            f = f3;
        } else {
            bl3 = bl2;
        }
        if (this.isLayoutRtl()) {
            f = 1.6777216E7f - f;
        }
        this.mLastMotionX += f - (float)((int)f);
        this.scrollTo((int)f, this.getScrollY());
        this.pageScrolled((int)f);
        return bl3;
    }

    private void recomputeScrollPosition(int n, int n2, int n3, int n4) {
        if (n2 > 0 && !this.mItems.isEmpty()) {
            int n5 = this.getPaddingLeft();
            int n6 = this.getPaddingRight();
            int n7 = this.getPaddingLeft();
            int n8 = this.getPaddingRight();
            float f = (float)this.getScrollX() / (float)(n2 - n7 - n8 + n4);
            n4 = (int)((float)(n - n5 - n6 + n3) * f);
            this.scrollTo(n4, this.getScrollY());
            if (!this.mScroller.isFinished()) {
                n2 = this.mScroller.getDuration();
                n3 = this.mScroller.timePassed();
                ItemInfo itemInfo = this.infoForPosition(this.mCurItem);
                this.mScroller.startScroll(n4, 0, (int)(itemInfo.offset * (float)n), 0, n2 - n3);
            }
        } else {
            ItemInfo itemInfo = this.infoForPosition(this.mCurItem);
            float f = itemInfo != null ? Math.min(itemInfo.offset, this.mLastOffset) : 0.0f;
            if ((n = (int)((float)(n - this.getPaddingLeft() - this.getPaddingRight()) * f)) != this.getScrollX()) {
                this.completeScroll(false);
                this.scrollTo(n, this.getScrollY());
            }
        }
    }

    private void removeNonDecorViews() {
        int n = 0;
        while (n < this.getChildCount()) {
            int n2 = n;
            if (!((LayoutParams)this.getChildAt((int)n).getLayoutParams()).isDecor) {
                this.removeViewAt(n);
                n2 = n - 1;
            }
            n = n2 + 1;
        }
    }

    private void requestParentDisallowInterceptTouchEvent(boolean bl) {
        ViewParent viewParent = this.getParent();
        if (viewParent != null) {
            viewParent.requestDisallowInterceptTouchEvent(bl);
        }
    }

    private void scrollToItem(int n, boolean bl, int n2, boolean bl2) {
        int n3 = this.getLeftEdgeForItem(n);
        if (bl) {
            OnPageChangeListener onPageChangeListener;
            this.smoothScrollTo(n3, 0, n2);
            if (bl2 && (onPageChangeListener = this.mOnPageChangeListener) != null) {
                onPageChangeListener.onPageSelected(n);
            }
            if (bl2 && (onPageChangeListener = this.mInternalPageChangeListener) != null) {
                onPageChangeListener.onPageSelected(n);
            }
        } else {
            OnPageChangeListener onPageChangeListener;
            if (bl2 && (onPageChangeListener = this.mOnPageChangeListener) != null) {
                onPageChangeListener.onPageSelected(n);
            }
            if (bl2 && (onPageChangeListener = this.mInternalPageChangeListener) != null) {
                onPageChangeListener.onPageSelected(n);
            }
            this.completeScroll(false);
            this.scrollTo(n3, 0);
            this.pageScrolled(n3);
        }
    }

    private void setScrollState(int n) {
        OnPageChangeListener onPageChangeListener;
        if (this.mScrollState == n) {
            return;
        }
        this.mScrollState = n;
        if (this.mPageTransformer != null) {
            boolean bl = n != 0;
            this.enableLayers(bl);
        }
        if ((onPageChangeListener = this.mOnPageChangeListener) != null) {
            onPageChangeListener.onPageScrollStateChanged(n);
        }
    }

    private void setScrollingCacheEnabled(boolean bl) {
        if (this.mScrollingCacheEnabled != bl) {
            this.mScrollingCacheEnabled = bl;
        }
    }

    private void sortChildDrawingOrder() {
        if (this.mDrawingOrder != 0) {
            ArrayList<View> arrayList = this.mDrawingOrderedChildren;
            if (arrayList == null) {
                this.mDrawingOrderedChildren = new ArrayList();
            } else {
                arrayList.clear();
            }
            int n = this.getChildCount();
            for (int i = 0; i < n; ++i) {
                arrayList = this.getChildAt(i);
                this.mDrawingOrderedChildren.add((View)((Object)arrayList));
            }
            Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
        }
    }

    @Override
    public void addFocusables(ArrayList<View> arrayList, int n, int n2) {
        int n3 = arrayList.size();
        int n4 = this.getDescendantFocusability();
        if (n4 != 393216) {
            for (int i = 0; i < this.getChildCount(); ++i) {
                ItemInfo itemInfo;
                View view = this.getChildAt(i);
                if (view.getVisibility() != 0 || (itemInfo = this.infoForChild(view)) == null || itemInfo.position != this.mCurItem) continue;
                view.addFocusables(arrayList, n, n2);
            }
        }
        if (n4 != 262144 || n3 == arrayList.size()) {
            if (!this.isFocusable()) {
                return;
            }
            if ((n2 & 1) == 1 && this.isInTouchMode() && !this.isFocusableInTouchMode()) {
                return;
            }
            arrayList.add(this);
        }
    }

    ItemInfo addNewItem(int n, int n2) {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.position = n;
        itemInfo.object = this.mAdapter.instantiateItem(this, n);
        itemInfo.widthFactor = this.mAdapter.getPageWidth(n);
        if (n2 >= 0 && n2 < this.mItems.size()) {
            this.mItems.add(n2, itemInfo);
        } else {
            this.mItems.add(itemInfo);
        }
        return itemInfo;
    }

    @Override
    public void addTouchables(ArrayList<View> arrayList) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            ItemInfo itemInfo;
            View view = this.getChildAt(i);
            if (view.getVisibility() != 0 || (itemInfo = this.infoForChild(view)) == null || itemInfo.position != this.mCurItem) continue;
            view.addTouchables(arrayList);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        ViewGroup.LayoutParams layoutParams2 = layoutParams;
        if (!this.checkLayoutParams(layoutParams)) {
            layoutParams2 = this.generateLayoutParams(layoutParams);
        }
        layoutParams = (LayoutParams)layoutParams2;
        ((LayoutParams)layoutParams).isDecor |= view instanceof Decor;
        if (this.mInLayout) {
            if (((LayoutParams)layoutParams).isDecor) throw new IllegalStateException("Cannot add pager decor view during layout");
            ((LayoutParams)layoutParams).needsMeasure = true;
            this.addViewInLayout(view, n, layoutParams2);
            return;
        } else {
            super.addView(view, n, layoutParams2);
        }
    }

    public boolean arrowScroll(int n) {
        boolean bl;
        block14 : {
            block15 : {
                block16 : {
                    boolean bl2;
                    block13 : {
                        int n2;
                        Object object;
                        int n3;
                        View view = this.findFocus();
                        if (view == this) {
                            object = null;
                        } else {
                            object = view;
                            if (view != null) {
                                n3 = 0;
                                object = view.getParent();
                                do {
                                    n2 = n3;
                                    if (!(object instanceof ViewGroup)) break;
                                    if (object == this) {
                                        n2 = 1;
                                        break;
                                    }
                                    object = object.getParent();
                                } while (true);
                                object = view;
                                if (n2 == 0) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append(view.getClass().getSimpleName());
                                    object = view.getParent();
                                    while (object instanceof ViewGroup) {
                                        stringBuilder.append(" => ");
                                        stringBuilder.append(object.getClass().getSimpleName());
                                        object = object.getParent();
                                    }
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("arrowScroll tried to find focus based on non-child current focused view ");
                                    ((StringBuilder)object).append(stringBuilder.toString());
                                    Log.e(TAG, ((StringBuilder)object).toString());
                                    object = null;
                                }
                            }
                        }
                        bl2 = false;
                        bl = false;
                        view = FocusFinder.getInstance().findNextFocus(this, (View)object, n);
                        if (view == null || view == object) break block13;
                        if (n == 17) {
                            n2 = this.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)view).left;
                            n3 = this.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)object).left;
                            bl = object != null && n2 >= n3 ? this.pageLeft() : view.requestFocus();
                        } else if (n == 66) {
                            n2 = this.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)view).left;
                            n3 = this.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)object).left;
                            bl = object != null && n2 <= n3 ? this.pageRight() : view.requestFocus();
                        }
                        break block14;
                    }
                    if (n == 17 || n == 1) break block15;
                    if (n == 66) break block16;
                    bl = bl2;
                    if (n != 2) break block14;
                }
                bl = this.pageRight();
                break block14;
            }
            bl = this.pageLeft();
        }
        if (bl) {
            this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(n));
        }
        return bl;
    }

    protected boolean canScroll(View view, boolean bl, int n, int n2, int n3) {
        boolean bl2 = view instanceof ViewGroup;
        boolean bl3 = true;
        if (bl2) {
            ViewGroup viewGroup = (ViewGroup)view;
            int n4 = view.getScrollX();
            int n5 = view.getScrollY();
            for (int i = viewGroup.getChildCount() - 1; i >= 0; --i) {
                View view2 = viewGroup.getChildAt(i);
                if (n2 + n4 < view2.getLeft() || n2 + n4 >= view2.getRight() || n3 + n5 < view2.getTop() || n3 + n5 >= view2.getBottom() || !this.canScroll(view2, true, n, n2 + n4 - view2.getLeft(), n3 + n5 - view2.getTop())) continue;
                return true;
            }
        }
        bl = bl && view.canScrollHorizontally(-n) ? bl3 : false;
        return bl;
    }

    @Override
    public boolean canScrollHorizontally(int n) {
        PagerAdapter pagerAdapter = this.mAdapter;
        boolean bl = false;
        boolean bl2 = false;
        if (pagerAdapter == null) {
            return false;
        }
        int n2 = this.getPaddedWidth();
        int n3 = this.getScrollX();
        if (n < 0) {
            if (n3 > (int)((float)n2 * this.mFirstOffset)) {
                bl2 = true;
            }
            return bl2;
        }
        if (n > 0) {
            bl2 = bl;
            if (n3 < (int)((float)n2 * this.mLastOffset)) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        boolean bl = layoutParams instanceof LayoutParams && super.checkLayoutParams(layoutParams);
        return bl;
    }

    @Override
    public void computeScroll() {
        if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
            int n = this.getScrollX();
            int n2 = this.getScrollY();
            int n3 = this.mScroller.getCurrX();
            int n4 = this.mScroller.getCurrY();
            if (n != n3 || n2 != n4) {
                this.scrollTo(n3, n4);
                if (!this.pageScrolled(n3)) {
                    this.mScroller.abortAnimation();
                    this.scrollTo(0, n4);
                }
            }
            this.postInvalidateOnAnimation();
            return;
        }
        this.completeScroll(true);
    }

    void dataSetChanged() {
        Object object;
        int n;
        this.mExpectedAdapterCount = n = this.mAdapter.getCount();
        int n2 = this.mItems.size() < this.mOffscreenPageLimit * 2 + 1 && this.mItems.size() < n ? 1 : 0;
        int n3 = this.mCurItem;
        int n4 = 0;
        int n5 = 0;
        while (n5 < this.mItems.size()) {
            int n6;
            int n7;
            int n8;
            object = this.mItems.get(n5);
            int n9 = this.mAdapter.getItemPosition(((ItemInfo)object).object);
            if (n9 == -1) {
                n7 = n3;
                n6 = n4;
                n8 = n5;
            } else if (n9 == -2) {
                this.mItems.remove(n5);
                n9 = n5 - 1;
                n5 = n4;
                if (n4 == 0) {
                    this.mAdapter.startUpdate(this);
                    n5 = 1;
                }
                this.mAdapter.destroyItem(this, ((ItemInfo)object).position, ((ItemInfo)object).object);
                n2 = 1;
                n7 = n3;
                n6 = n5;
                n8 = n9;
                if (this.mCurItem == ((ItemInfo)object).position) {
                    n7 = Math.max(0, Math.min(this.mCurItem, n - 1));
                    n2 = 1;
                    n6 = n5;
                    n8 = n9;
                }
            } else {
                n7 = n3;
                n6 = n4;
                n8 = n5;
                if (((ItemInfo)object).position != n9) {
                    if (((ItemInfo)object).position == this.mCurItem) {
                        n3 = n9;
                    }
                    ((ItemInfo)object).position = n9;
                    n2 = 1;
                    n8 = n5;
                    n6 = n4;
                    n7 = n3;
                }
            }
            n5 = n8 + 1;
            n3 = n7;
            n4 = n6;
        }
        if (n4 != 0) {
            this.mAdapter.finishUpdate(this);
        }
        Collections.sort(this.mItems, COMPARATOR);
        if (n2 != 0) {
            n4 = this.getChildCount();
            for (n2 = 0; n2 < n4; ++n2) {
                object = (LayoutParams)this.getChildAt(n2).getLayoutParams();
                if (((LayoutParams)object).isDecor) continue;
                ((LayoutParams)object).widthFactor = 0.0f;
            }
            this.setCurrentItemInternal(n3, false, true);
            this.requestLayout();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean bl = super.dispatchKeyEvent(keyEvent) || this.executeKeyEvent(keyEvent);
        return bl;
    }

    float distanceInfluenceForSnapDuration(float f) {
        return (float)Math.sin((float)((double)(f - 0.5f) * 0.4712389167638204));
    }

    @Override
    public void draw(Canvas canvas) {
        PagerAdapter pagerAdapter;
        super.draw(canvas);
        int n = 0;
        int n2 = 0;
        int n3 = this.getOverScrollMode();
        if (n3 != 0 && (n3 != 1 || (pagerAdapter = this.mAdapter) == null || pagerAdapter.getCount() <= 1)) {
            this.mLeftEdge.finish();
            this.mRightEdge.finish();
        } else {
            if (!this.mLeftEdge.isFinished()) {
                n = canvas.save();
                n2 = this.getHeight() - this.getPaddingTop() - this.getPaddingBottom();
                n3 = this.getWidth();
                canvas.rotate(270.0f);
                canvas.translate(-n2 + this.getPaddingTop(), this.mFirstOffset * (float)n3);
                this.mLeftEdge.setSize(n2, n3);
                n2 = false | this.mLeftEdge.draw(canvas);
                canvas.restoreToCount(n);
            }
            n = n2;
            if (!this.mRightEdge.isFinished()) {
                n3 = canvas.save();
                int n4 = this.getWidth();
                int n5 = this.getHeight();
                int n6 = this.getPaddingTop();
                n = this.getPaddingBottom();
                canvas.rotate(90.0f);
                canvas.translate(-this.getPaddingTop(), -(this.mLastOffset + 1.0f) * (float)n4);
                this.mRightEdge.setSize(n5 - n6 - n, n4);
                n = n2 | this.mRightEdge.draw(canvas);
                canvas.restoreToCount(n3);
            }
        }
        if (n != 0) {
            this.postInvalidateOnAnimation();
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable2 = this.mMarginDrawable;
        if (drawable2 != null && drawable2.isStateful() && drawable2.setState(this.getDrawableState())) {
            this.invalidateDrawable(drawable2);
        }
    }

    public boolean executeKeyEvent(KeyEvent keyEvent) {
        boolean bl;
        boolean bl2 = bl = false;
        if (keyEvent.getAction() == 0) {
            int n = keyEvent.getKeyCode();
            if (n != 21) {
                if (n != 22) {
                    if (n != 61) {
                        bl2 = bl;
                    } else if (keyEvent.hasNoModifiers()) {
                        bl2 = this.arrowScroll(2);
                    } else {
                        bl2 = bl;
                        if (keyEvent.hasModifiers(1)) {
                            bl2 = this.arrowScroll(1);
                        }
                    }
                } else {
                    bl2 = this.arrowScroll(66);
                }
            } else {
                bl2 = this.arrowScroll(17);
            }
        }
        return bl2;
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return this.generateDefaultLayoutParams();
    }

    public PagerAdapter getAdapter() {
        return this.mAdapter;
    }

    @Override
    protected int getChildDrawingOrder(int n, int n2) {
        block0 : {
            if (this.mDrawingOrder != 2) break block0;
            n2 = n - 1 - n2;
        }
        return ((LayoutParams)this.mDrawingOrderedChildren.get((int)n2).getLayoutParams()).childIndex;
    }

    public Object getCurrent() {
        Object object = this.infoForPosition(this.getCurrentItem());
        object = object == null ? null : ((ItemInfo)object).object;
        return object;
    }

    @UnsupportedAppUsage
    public int getCurrentItem() {
        return this.mCurItem;
    }

    public int getOffscreenPageLimit() {
        return this.mOffscreenPageLimit;
    }

    public int getPageMargin() {
        return this.mPageMargin;
    }

    ItemInfo infoForAnyChild(View view) {
        ViewParent viewParent;
        while ((viewParent = view.getParent()) != this) {
            if (viewParent != null && viewParent instanceof View) {
                view = (View)((Object)viewParent);
                continue;
            }
            return null;
        }
        return this.infoForChild(view);
    }

    ItemInfo infoForChild(View view) {
        for (int i = 0; i < this.mItems.size(); ++i) {
            ItemInfo itemInfo = this.mItems.get(i);
            if (!this.mAdapter.isViewFromObject(view, itemInfo.object)) continue;
            return itemInfo;
        }
        return null;
    }

    ItemInfo infoForPosition(int n) {
        for (int i = 0; i < this.mItems.size(); ++i) {
            ItemInfo itemInfo = this.mItems.get(i);
            if (itemInfo.position != n) continue;
            return itemInfo;
        }
        return null;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        this.removeCallbacks(this.mEndScrollRunnable);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPageMargin > 0 && this.mMarginDrawable != null && this.mItems.size() > 0 && this.mAdapter != null) {
            int n = this.getScrollX();
            int n2 = this.getWidth();
            float f = (float)this.mPageMargin / (float)n2;
            int n3 = 0;
            Object object = this.mItems.get(0);
            float f2 = ((ItemInfo)object).offset;
            int n4 = this.mItems.size();
            int n5 = this.mItems.get((int)(n4 - 1)).position;
            for (int i = object.position; i < n5; ++i) {
                float f3;
                while (i > ((ItemInfo)object).position && n3 < n4) {
                    object = this.mItems;
                    object = (ItemInfo)((ArrayList)object).get(++n3);
                }
                if (i == ((ItemInfo)object).position) {
                    f2 = ((ItemInfo)object).offset;
                    f3 = ((ItemInfo)object).widthFactor;
                } else {
                    f3 = this.mAdapter.getPageWidth(i);
                }
                float f4 = (float)n2 * f2;
                f4 = this.isLayoutRtl() ? 1.6777216E7f - f4 : (float)n2 * f3 + f4;
                f2 = f2 + f3 + f;
                int n6 = this.mPageMargin;
                if ((float)n6 + f4 > (float)n) {
                    this.mMarginDrawable.setBounds((int)f4, this.mTopPageBounds, (int)((float)n6 + f4 + 0.5f), this.mBottomPageBounds);
                    this.mMarginDrawable.draw(canvas);
                }
                if (f4 > (float)(n + n2)) break;
            }
        }
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        PagerAdapter pagerAdapter;
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(ViewPager.class.getName());
        accessibilityEvent.setScrollable(this.canScroll());
        if (accessibilityEvent.getEventType() == 4096 && (pagerAdapter = this.mAdapter) != null) {
            accessibilityEvent.setItemCount(pagerAdapter.getCount());
            accessibilityEvent.setFromIndex(this.mCurItem);
            accessibilityEvent.setToIndex(this.mCurItem);
        }
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(ViewPager.class.getName());
        accessibilityNodeInfo.setScrollable(this.canScroll());
        if (this.canScrollHorizontally(1)) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT);
        }
        if (this.canScrollHorizontally(-1)) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent object) {
        int n = ((MotionEvent)object).getAction() & 255;
        if (n != 3 && n != 1) {
            if (n != 0) {
                if (this.mIsBeingDragged) {
                    return true;
                }
                if (this.mIsUnableToDrag) {
                    return false;
                }
            }
            if (n != 0) {
                if (n != 2) {
                    if (n == 6) {
                        this.onSecondaryPointerUp((MotionEvent)object);
                    }
                } else {
                    n = this.mActivePointerId;
                    if (n != -1) {
                        n = ((MotionEvent)object).findPointerIndex(n);
                        float f = ((MotionEvent)object).getX(n);
                        float f2 = f - this.mLastMotionX;
                        float f3 = Math.abs(f2);
                        float f4 = ((MotionEvent)object).getY(n);
                        float f5 = Math.abs(f4 - this.mInitialMotionY);
                        if (f2 != 0.0f && !this.isGutterDrag(this.mLastMotionX, f2) && this.canScroll(this, false, (int)f2, (int)f, (int)f4)) {
                            this.mLastMotionX = f;
                            this.mLastMotionY = f4;
                            this.mIsUnableToDrag = true;
                            return false;
                        }
                        if (f3 > (float)this.mTouchSlop && 0.5f * f3 > f5) {
                            this.mIsBeingDragged = true;
                            this.requestParentDisallowInterceptTouchEvent(true);
                            this.setScrollState(1);
                            f3 = f2 > 0.0f ? this.mInitialMotionX + (float)this.mTouchSlop : this.mInitialMotionX - (float)this.mTouchSlop;
                            this.mLastMotionX = f3;
                            this.mLastMotionY = f4;
                            this.setScrollingCacheEnabled(true);
                        } else if (f5 > (float)this.mTouchSlop) {
                            this.mIsUnableToDrag = true;
                        }
                        if (this.mIsBeingDragged && this.performDrag(f)) {
                            this.postInvalidateOnAnimation();
                        }
                    }
                }
            } else {
                float f;
                this.mInitialMotionX = f = ((MotionEvent)object).getX();
                this.mLastMotionX = f;
                this.mInitialMotionY = f = ((MotionEvent)object).getY();
                this.mLastMotionY = f;
                this.mActivePointerId = ((MotionEvent)object).getPointerId(0);
                this.mIsUnableToDrag = false;
                this.mScroller.computeScrollOffset();
                if (this.mScrollState == 2 && Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) > this.mCloseEnough) {
                    this.mScroller.abortAnimation();
                    this.mPopulatePending = false;
                    this.populate();
                    this.mIsBeingDragged = true;
                    this.requestParentDisallowInterceptTouchEvent(true);
                    this.setScrollState(1);
                } else {
                    this.completeScroll(false);
                    this.mIsBeingDragged = false;
                }
            }
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement((MotionEvent)object);
            return this.mIsBeingDragged;
        }
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        this.mActivePointerId = -1;
        object = this.mVelocityTracker;
        if (object != null) {
            ((VelocityTracker)object).recycle();
            this.mVelocityTracker = null;
        }
        return false;
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        int n5;
        Object object;
        int n6;
        int n7;
        LayoutParams layoutParams;
        int n8;
        int n9;
        int n10 = this.getChildCount();
        int n11 = n3 - n;
        int n12 = n4 - n2;
        n = this.getPaddingLeft();
        n2 = this.getPaddingTop();
        int n13 = this.getPaddingRight();
        n4 = this.getPaddingBottom();
        int n14 = this.getScrollX();
        int n15 = 0;
        for (n6 = 0; n6 < n10; ++n6) {
            object = this.getChildAt(n6);
            n3 = n;
            n8 = n2;
            n7 = n13;
            n9 = n4;
            n5 = n15;
            if (((View)object).getVisibility() != 8) {
                layoutParams = (LayoutParams)((View)object).getLayoutParams();
                if (layoutParams.isDecor) {
                    n3 = layoutParams.gravity & 7;
                    n7 = layoutParams.gravity & 112;
                    if (n3 != 1) {
                        if (n3 != 3) {
                            if (n3 != 5) {
                                n3 = n;
                                n8 = n;
                            } else {
                                n3 = n11 - n13 - ((View)object).getMeasuredWidth();
                                n13 += ((View)object).getMeasuredWidth();
                                n8 = n;
                            }
                        } else {
                            n3 = n;
                            n8 = n + ((View)object).getMeasuredWidth();
                        }
                    } else {
                        n3 = Math.max((n11 - ((View)object).getMeasuredWidth()) / 2, n);
                        n8 = n;
                    }
                    if (n7 != 16) {
                        if (n7 != 48) {
                            if (n7 != 80) {
                                n = n2;
                            } else {
                                n = n12 - n4 - ((View)object).getMeasuredHeight();
                                n4 += ((View)object).getMeasuredHeight();
                            }
                        } else {
                            n = n2;
                            n2 += ((View)object).getMeasuredHeight();
                        }
                    } else {
                        n = Math.max((n12 - ((View)object).getMeasuredHeight()) / 2, n2);
                    }
                    ((View)object).layout(n3 += n14, n, ((View)object).getMeasuredWidth() + n3, n + ((View)object).getMeasuredHeight());
                    n5 = n15 + 1;
                    n3 = n8;
                    n8 = n2;
                    n7 = n13;
                    n9 = n4;
                } else {
                    n5 = n15;
                    n9 = n4;
                    n7 = n13;
                    n8 = n2;
                    n3 = n;
                }
            }
            n = n3;
            n2 = n8;
            n13 = n7;
            n4 = n9;
            n15 = n5;
        }
        n9 = n11 - n - n13;
        n3 = n11;
        n6 = n10;
        for (n8 = 0; n8 < n6; ++n8) {
            View view = this.getChildAt(n8);
            if (view.getVisibility() == 8) continue;
            layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.isDecor || (object = this.infoForChild(view)) == null) continue;
            if (layoutParams.needsMeasure) {
                layoutParams.needsMeasure = false;
                view.measure(View.MeasureSpec.makeMeasureSpec((int)((float)n9 * layoutParams.widthFactor), 1073741824), View.MeasureSpec.makeMeasureSpec(n12 - n2 - n4, 1073741824));
            }
            n5 = view.getMeasuredWidth();
            n7 = (int)((float)n9 * ((ItemInfo)object).offset);
            n7 = this.isLayoutRtl() ? 16777216 - n13 - n7 - n5 : n + n7;
            view.layout(n7, n2, n7 + n5, n2 + view.getMeasuredHeight());
        }
        this.mTopPageBounds = n2;
        this.mBottomPageBounds = n12 - n4;
        this.mDecorChildCount = n15;
        if (this.mFirstLayout) {
            this.scrollToItem(this.mCurItem, false, 0, false);
        }
        this.mFirstLayout = false;
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3;
        Object object;
        Object object2;
        this.setMeasuredDimension(ViewPager.getDefaultSize(0, n), ViewPager.getDefaultSize(0, n2));
        int n4 = this.getMeasuredWidth();
        int n5 = n4 / 10;
        this.mGutterSize = Math.min(n5, this.mDefaultGutterSize);
        n = n4 - this.getPaddingLeft() - this.getPaddingRight();
        n2 = this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom();
        int n6 = this.getChildCount();
        for (int i = 0; i < n6; ++i) {
            int n7;
            object = this.getChildAt(i);
            if (((View)object).getVisibility() != 8) {
                object2 = (LayoutParams)((View)object).getLayoutParams();
                if (object2 != null && ((LayoutParams)object2).isDecor) {
                    int n8;
                    n3 = ((LayoutParams)object2).gravity & 7;
                    int n9 = ((LayoutParams)object2).gravity & 112;
                    int n10 = Integer.MIN_VALUE;
                    n7 = Integer.MIN_VALUE;
                    n9 = n9 != 48 && n9 != 80 ? 0 : 1;
                    boolean bl = n3 == 3 || n3 == 5;
                    if (n9 != 0) {
                        n3 = 1073741824;
                    } else {
                        n3 = n10;
                        if (bl) {
                            n7 = 1073741824;
                            n3 = n10;
                        }
                    }
                    if (((LayoutParams)object2).width != -2) {
                        n8 = 1073741824;
                        n3 = ((LayoutParams)object2).width != -1 ? ((LayoutParams)object2).width : n;
                    } else {
                        n10 = n;
                        n8 = n3;
                        n3 = n10;
                    }
                    if (((LayoutParams)object2).height != -2) {
                        if (((LayoutParams)object2).height != -1) {
                            n7 = ((LayoutParams)object2).height;
                            n10 = 1073741824;
                        } else {
                            n10 = 1073741824;
                            n7 = n2;
                        }
                    } else {
                        n10 = n7;
                        n7 = n2;
                    }
                    ((View)object).measure(View.MeasureSpec.makeMeasureSpec(n3, n8), View.MeasureSpec.makeMeasureSpec(n7, n10));
                    if (n9 != 0) {
                        n7 = n2 - ((View)object).getMeasuredHeight();
                        n3 = n;
                    } else {
                        n3 = n;
                        n7 = n2;
                        if (bl) {
                            n3 = n - ((View)object).getMeasuredWidth();
                            n7 = n2;
                        }
                    }
                } else {
                    n3 = n;
                    n7 = n2;
                }
            } else {
                n7 = n2;
                n3 = n;
            }
            n = n3;
            n2 = n7;
        }
        this.mChildWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(n, 1073741824);
        this.mChildHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(n2, 1073741824);
        this.mInLayout = true;
        this.populate();
        this.mInLayout = false;
        n3 = this.getChildCount();
        for (n2 = 0; n2 < n3; ++n2) {
            object2 = this.getChildAt(n2);
            if (((View)object2).getVisibility() == 8 || (object = (LayoutParams)((View)object2).getLayoutParams()) != null && ((LayoutParams)object).isDecor) continue;
            ((View)object2).measure(View.MeasureSpec.makeMeasureSpec((int)((float)n * ((LayoutParams)object).widthFactor), 1073741824), this.mChildHeightMeasureSpec);
        }
    }

    protected void onPageScrolled(int n, float f, int n2) {
        Object object;
        int n3;
        if (this.mDecorChildCount > 0) {
            int n4 = this.getScrollX();
            n3 = this.getPaddingLeft();
            int n5 = this.getPaddingRight();
            int n6 = this.getWidth();
            int n7 = this.getChildCount();
            for (int i = 0; i < n7; ++i) {
                int n8;
                int n9;
                object = this.getChildAt(i);
                LayoutParams layoutParams = (LayoutParams)((View)object).getLayoutParams();
                if (!layoutParams.isDecor) {
                    n9 = n3;
                    n8 = n5;
                } else {
                    n9 = layoutParams.gravity & 7;
                    if (n9 != 1) {
                        if (n9 != 3) {
                            if (n9 != 5) {
                                n9 = n3;
                            } else {
                                n9 = n6 - n5 - ((View)object).getMeasuredWidth();
                                n5 += ((View)object).getMeasuredWidth();
                            }
                        } else {
                            n9 = n3;
                            n3 += ((View)object).getWidth();
                        }
                    } else {
                        n9 = Math.max((n6 - ((View)object).getMeasuredWidth()) / 2, n3);
                    }
                    int n10 = n9 + n4 - ((View)object).getLeft();
                    n9 = n3;
                    n8 = n5;
                    if (n10 != 0) {
                        ((View)object).offsetLeftAndRight(n10);
                        n8 = n5;
                        n9 = n3;
                    }
                }
                n3 = n9;
                n5 = n8;
            }
        }
        if ((object = this.mOnPageChangeListener) != null) {
            object.onPageScrolled(n, f, n2);
        }
        if ((object = this.mInternalPageChangeListener) != null) {
            object.onPageScrolled(n, f, n2);
        }
        if (this.mPageTransformer != null) {
            n2 = this.getScrollX();
            n3 = this.getChildCount();
            for (n = 0; n < n3; ++n) {
                object = this.getChildAt(n);
                if (((LayoutParams)object.getLayoutParams()).isDecor) continue;
                f = (float)(((View)object).getLeft() - n2) / (float)this.getPaddedWidth();
                this.mPageTransformer.transformPage((View)object, f);
            }
        }
        this.mCalledSuper = true;
    }

    @Override
    protected boolean onRequestFocusInDescendants(int n, Rect rect) {
        int n2;
        int n3;
        int n4 = this.getChildCount();
        if ((n & 2) != 0) {
            n3 = 0;
            n2 = 1;
        } else {
            n3 = n4 - 1;
            n2 = -1;
            n4 = -1;
        }
        while (n3 != n4) {
            ItemInfo itemInfo;
            View view = this.getChildAt(n3);
            if (view.getVisibility() == 0 && (itemInfo = this.infoForChild(view)) != null && itemInfo.position == this.mCurItem && view.requestFocus(n, rect)) {
                return true;
            }
            n3 += n2;
        }
        return false;
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(((AbsSavedState)parcelable).getSuperState());
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter != null) {
            pagerAdapter.restoreState(((SavedState)parcelable).adapterState, ((SavedState)parcelable).loader);
            this.setCurrentItemInternal(((SavedState)parcelable).position, false, true);
        } else {
            this.mRestoredCurItem = ((SavedState)parcelable).position;
            this.mRestoredAdapterState = ((SavedState)parcelable).adapterState;
            this.mRestoredClassLoader = ((SavedState)parcelable).loader;
        }
    }

    @Override
    public void onRtlPropertiesChanged(int n) {
        super.onRtlPropertiesChanged(n);
        this.mLeftIncr = n == 0 ? -1 : 1;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.position = this.mCurItem;
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter != null) {
            savedState.adapterState = pagerAdapter.saveState();
        }
        return savedState;
    }

    @Override
    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        if (n != n3) {
            n2 = this.mPageMargin;
            this.recomputeScrollPosition(n, n3, n2, n2);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 && motionEvent.getEdgeFlags() != 0) {
            return false;
        }
        Object object = this.mAdapter;
        if (object != null && ((PagerAdapter)object).getCount() != 0) {
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement(motionEvent);
            int n = motionEvent.getAction();
            int n2 = 0;
            if ((n &= 255) != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            if (n != 5) {
                                if (n == 6) {
                                    this.onSecondaryPointerUp(motionEvent);
                                    this.mLastMotionX = motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId));
                                }
                            } else {
                                n = motionEvent.getActionIndex();
                                this.mLastMotionX = motionEvent.getX(n);
                                this.mActivePointerId = motionEvent.getPointerId(n);
                            }
                        } else if (this.mIsBeingDragged) {
                            this.scrollToItem(this.mCurItem, true, 0, false);
                            this.mActivePointerId = -1;
                            this.endDrag();
                            this.mLeftEdge.onRelease();
                            this.mRightEdge.onRelease();
                            n2 = 1;
                        }
                    } else {
                        if (!this.mIsBeingDragged) {
                            n = motionEvent.findPointerIndex(this.mActivePointerId);
                            float f = motionEvent.getX(n);
                            float f2 = Math.abs(f - this.mLastMotionX);
                            float f3 = motionEvent.getY(n);
                            float f4 = Math.abs(f3 - this.mLastMotionY);
                            if (f2 > (float)this.mTouchSlop && f2 > f4) {
                                this.mIsBeingDragged = true;
                                this.requestParentDisallowInterceptTouchEvent(true);
                                f4 = this.mInitialMotionX;
                                f = f - f4 > 0.0f ? f4 + (float)this.mTouchSlop : f4 - (float)this.mTouchSlop;
                                this.mLastMotionX = f;
                                this.mLastMotionY = f3;
                                this.setScrollState(1);
                                this.setScrollingCacheEnabled(true);
                                object = this.getParent();
                                if (object != null) {
                                    object.requestDisallowInterceptTouchEvent(true);
                                }
                            }
                        }
                        if (this.mIsBeingDragged) {
                            n2 = false | this.performDrag(motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId)));
                        }
                    }
                } else if (this.mIsBeingDragged) {
                    object = this.mVelocityTracker;
                    ((VelocityTracker)object).computeCurrentVelocity(1000, this.mMaximumVelocity);
                    n = (int)((VelocityTracker)object).getXVelocity(this.mActivePointerId);
                    this.mPopulatePending = true;
                    float f = (float)this.getScrollStart() / (float)this.getPaddedWidth();
                    object = this.infoForFirstVisiblePage();
                    n2 = ((ItemInfo)object).position;
                    f = this.isLayoutRtl() ? (((ItemInfo)object).offset - f) / ((ItemInfo)object).widthFactor : (f - ((ItemInfo)object).offset) / ((ItemInfo)object).widthFactor;
                    this.setCurrentItemInternal(this.determineTargetPage(n2, f, n, (int)(motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId)) - this.mInitialMotionX)), true, true, n);
                    this.mActivePointerId = -1;
                    this.endDrag();
                    this.mLeftEdge.onRelease();
                    this.mRightEdge.onRelease();
                    n2 = 1;
                }
            } else {
                float f;
                this.mScroller.abortAnimation();
                this.mPopulatePending = false;
                this.populate();
                this.mInitialMotionX = f = motionEvent.getX();
                this.mLastMotionX = f;
                this.mInitialMotionY = f = motionEvent.getY();
                this.mLastMotionY = f;
                this.mActivePointerId = motionEvent.getPointerId(0);
            }
            if (n2 != 0) {
                this.postInvalidateOnAnimation();
            }
            return true;
        }
        return false;
    }

    boolean pageLeft() {
        return this.setCurrentItemInternal(this.mCurItem + this.mLeftIncr, true, false);
    }

    boolean pageRight() {
        return this.setCurrentItemInternal(this.mCurItem - this.mLeftIncr, true, false);
    }

    @Override
    public boolean performAccessibilityAction(int n, Bundle bundle) {
        if (super.performAccessibilityAction(n, bundle)) {
            return true;
        }
        if (n != 4096) {
            if (n != 8192 && n != 16908345) {
                if (n != 16908347) {
                    return false;
                }
            } else {
                if (this.canScrollHorizontally(-1)) {
                    this.setCurrentItem(this.mCurItem - 1);
                    return true;
                }
                return false;
            }
        }
        if (this.canScrollHorizontally(1)) {
            this.setCurrentItem(this.mCurItem + 1);
            return true;
        }
        return false;
    }

    public void populate() {
        this.populate(this.mCurItem);
    }

    void populate(int n) {
        String string2;
        Object object;
        int n2;
        int n3 = this.mCurItem;
        if (n3 != n) {
            n3 = n3 < n ? 66 : 17;
            object = this.infoForPosition(this.mCurItem);
            this.mCurItem = n;
            n2 = n3;
        } else {
            n2 = 2;
            object = null;
        }
        if (this.mAdapter == null) {
            this.sortChildDrawingOrder();
            return;
        }
        if (this.mPopulatePending) {
            this.sortChildDrawingOrder();
            return;
        }
        if (this.getWindowToken() == null) {
            return;
        }
        this.mAdapter.startUpdate(this);
        int n4 = this.mOffscreenPageLimit;
        int n5 = Math.max(0, this.mCurItem - n4);
        int n6 = this.mAdapter.getCount();
        int n7 = Math.min(n6 - 1, this.mCurItem + n4);
        if (n6 == this.mExpectedAdapterCount) {
            Object object2;
            Object object3;
            Object object4 = null;
            n = 0;
            do {
                object3 = object4;
                if (n >= this.mItems.size()) break;
                object2 = this.mItems.get(n);
                if (((ItemInfo)object2).position >= this.mCurItem) {
                    object3 = object4;
                    if (((ItemInfo)object2).position != this.mCurItem) break;
                    object3 = object2;
                    break;
                }
                ++n;
            } while (true);
            object4 = object3;
            if (object3 == null) {
                object4 = object3;
                if (n6 > 0) {
                    object4 = this.addNewItem(this.mCurItem, n);
                }
            }
            if (object4 != null) {
                float f;
                int n8;
                float f2 = 0.0f;
                int n9 = n - 1;
                object3 = n9 >= 0 ? this.mItems.get(n9) : null;
                int n10 = this.getPaddedWidth();
                if (n10 <= 0) {
                    f = 0.0f;
                } else {
                    f = ((ItemInfo)object4).widthFactor;
                    f = (float)this.getPaddingLeft() / (float)n10 + (2.0f - f);
                }
                object2 = object3;
                int n11 = n;
                float f3 = f;
                for (n8 = this.mCurItem - 1; n8 >= 0; --n8) {
                    if (f2 >= f3 && n8 < n5) {
                        if (object2 == null) break;
                        n = n11;
                        f = f2;
                        n3 = n9;
                        object3 = object2;
                        if (n8 == ((ItemInfo)object2).position) {
                            n = n11;
                            f = f2;
                            n3 = n9;
                            object3 = object2;
                            if (!((ItemInfo)object2).scrolling) {
                                this.mItems.remove(n9);
                                this.mAdapter.destroyItem(this, n8, ((ItemInfo)object2).object);
                                n3 = n9 - 1;
                                n = n11 - 1;
                                object3 = n3 >= 0 ? this.mItems.get(n3) : null;
                                f = f2;
                            }
                        }
                    } else if (object2 != null && n8 == ((ItemInfo)object2).position) {
                        f = f2 + ((ItemInfo)object2).widthFactor;
                        n3 = n9 - 1;
                        object3 = n3 >= 0 ? this.mItems.get(n3) : null;
                        n = n11;
                    } else {
                        f = f2 + this.addNewItem((int)n8, (int)(n9 + 1)).widthFactor;
                        n = n11 + 1;
                        object3 = n9 >= 0 ? this.mItems.get(n9) : null;
                        n3 = n9;
                    }
                    n11 = n;
                    f2 = f;
                    n9 = n3;
                    object2 = object3;
                }
                f = ((ItemInfo)object4).widthFactor;
                n = n11 + 1;
                if (f < 2.0f) {
                    object3 = n < this.mItems.size() ? this.mItems.get(n) : null;
                    f3 = n10 <= 0 ? 0.0f : (float)this.getPaddingRight() / (float)n10 + 2.0f;
                    n3 = n5;
                    n9 = n4;
                    for (n8 = this.mCurItem + 1; n8 < n6; ++n8) {
                        if (f >= f3 && n8 > n7) {
                            if (object3 == null) break;
                            if (n8 != ((ItemInfo)object3).position || ((ItemInfo)object3).scrolling) continue;
                            this.mItems.remove(n);
                            this.mAdapter.destroyItem(this, n8, ((ItemInfo)object3).object);
                            if (n < this.mItems.size()) {
                                object3 = this.mItems.get(n);
                                continue;
                            }
                            object3 = null;
                            continue;
                        }
                        if (object3 != null && n8 == ((ItemInfo)object3).position) {
                            f += ((ItemInfo)object3).widthFactor;
                            if (++n < this.mItems.size()) {
                                object3 = this.mItems.get(n);
                                continue;
                            }
                            object3 = null;
                            continue;
                        }
                        object3 = this.addNewItem(n8, n);
                        f += ((ItemInfo)object3).widthFactor;
                        object3 = ++n < this.mItems.size() ? this.mItems.get(n) : null;
                    }
                }
                this.calculatePageOffsets((ItemInfo)object4, n11, (ItemInfo)object);
            }
            object = this.mAdapter;
            n = this.mCurItem;
            object3 = object4 != null ? ((ItemInfo)object4).object : null;
            ((PagerAdapter)object).setPrimaryItem(this, n, object3);
            this.mAdapter.finishUpdate(this);
            n3 = this.getChildCount();
            for (n = 0; n < n3; ++n) {
                object = this.getChildAt(n);
                object3 = (LayoutParams)((View)object).getLayoutParams();
                ((LayoutParams)object3).childIndex = n;
                if (((LayoutParams)object3).isDecor || ((LayoutParams)object3).widthFactor != 0.0f || (object = this.infoForChild((View)object)) == null) continue;
                ((LayoutParams)object3).widthFactor = ((ItemInfo)object).widthFactor;
                ((LayoutParams)object3).position = ((ItemInfo)object).position;
            }
            this.sortChildDrawingOrder();
            if (this.hasFocus() && ((object3 = (object = this.findFocus()) != null ? this.infoForAnyChild((View)object) : null) == null || ((ItemInfo)object3).position != this.mCurItem)) {
                for (n = 0; n < this.getChildCount(); ++n) {
                    object4 = this.getChildAt(n);
                    object3 = this.infoForChild((View)object4);
                    if (object3 == null || ((ItemInfo)object3).position != this.mCurItem) continue;
                    if (object == null) {
                        object3 = null;
                    } else {
                        object3 = this.mTempRect;
                        ((View)object).getFocusedRect(this.mTempRect);
                        this.offsetDescendantRectToMyCoords((View)object, this.mTempRect);
                        this.offsetRectIntoDescendantCoords((View)object4, this.mTempRect);
                    }
                    if (((View)object4).requestFocus(n2, (Rect)object3)) break;
                }
            }
            return;
        }
        try {
            string2 = this.getResources().getResourceName(this.getId());
        }
        catch (Resources.NotFoundException notFoundException) {
            string2 = Integer.toHexString(this.getId());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: ");
        ((StringBuilder)object).append(this.mExpectedAdapterCount);
        ((StringBuilder)object).append(", found: ");
        ((StringBuilder)object).append(n6);
        ((StringBuilder)object).append(" Pager id: ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" Pager class: ");
        ((StringBuilder)object).append(this.getClass());
        ((StringBuilder)object).append(" Problematic adapter: ");
        ((StringBuilder)object).append(this.mAdapter.getClass());
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    @Override
    public void removeView(View view) {
        if (this.mInLayout) {
            this.removeViewInLayout(view);
        } else {
            super.removeView(view);
        }
    }

    public void setAdapter(PagerAdapter pagerAdapter) {
        OnAdapterChangeListener onAdapterChangeListener;
        Object object = this.mAdapter;
        if (object != null) {
            ((PagerAdapter)object).unregisterDataSetObserver(this.mObserver);
            this.mAdapter.startUpdate(this);
            for (int i = 0; i < this.mItems.size(); ++i) {
                object = this.mItems.get(i);
                this.mAdapter.destroyItem(this, ((ItemInfo)object).position, ((ItemInfo)object).object);
            }
            this.mAdapter.finishUpdate(this);
            this.mItems.clear();
            this.removeNonDecorViews();
            this.mCurItem = 0;
            this.scrollTo(0, 0);
        }
        object = this.mAdapter;
        this.mAdapter = pagerAdapter;
        this.mExpectedAdapterCount = 0;
        if (this.mAdapter != null) {
            if (this.mObserver == null) {
                this.mObserver = new PagerObserver();
            }
            this.mAdapter.registerDataSetObserver(this.mObserver);
            this.mPopulatePending = false;
            boolean bl = this.mFirstLayout;
            this.mFirstLayout = true;
            this.mExpectedAdapterCount = this.mAdapter.getCount();
            if (this.mRestoredCurItem >= 0) {
                this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
                this.setCurrentItemInternal(this.mRestoredCurItem, false, true);
                this.mRestoredCurItem = -1;
                this.mRestoredAdapterState = null;
                this.mRestoredClassLoader = null;
            } else if (!bl) {
                this.populate();
            } else {
                this.requestLayout();
            }
        }
        if ((onAdapterChangeListener = this.mAdapterChangeListener) != null && object != pagerAdapter) {
            onAdapterChangeListener.onAdapterChanged((PagerAdapter)object, pagerAdapter);
        }
    }

    public void setCurrentItem(int n) {
        this.mPopulatePending = false;
        this.setCurrentItemInternal(n, this.mFirstLayout ^ true, false);
    }

    public void setCurrentItem(int n, boolean bl) {
        this.mPopulatePending = false;
        this.setCurrentItemInternal(n, bl, false);
    }

    boolean setCurrentItemInternal(int n, boolean bl, boolean bl2) {
        return this.setCurrentItemInternal(n, bl, bl2, 0);
    }

    boolean setCurrentItemInternal(int n, boolean bl, boolean bl2, int n2) {
        Object object = this.mAdapter;
        boolean bl3 = false;
        if (object != null && ((PagerAdapter)object).getCount() > 0) {
            int n3 = MathUtils.constrain(n, 0, this.mAdapter.getCount() - 1);
            if (!bl2 && this.mCurItem == n3 && this.mItems.size() != 0) {
                this.setScrollingCacheEnabled(false);
                return false;
            }
            n = this.mCurItem;
            int n4 = this.mOffscreenPageLimit;
            if (n3 > n + n4 || n3 < n - n4) {
                for (n = 0; n < this.mItems.size(); ++n) {
                    this.mItems.get((int)n).scrolling = true;
                }
            }
            bl2 = bl3;
            if (this.mCurItem != n3) {
                bl2 = true;
            }
            if (this.mFirstLayout) {
                this.mCurItem = n3;
                if (bl2 && (object = this.mOnPageChangeListener) != null) {
                    object.onPageSelected(n3);
                }
                if (bl2 && (object = this.mInternalPageChangeListener) != null) {
                    object.onPageSelected(n3);
                }
                this.requestLayout();
            } else {
                this.populate(n3);
                this.scrollToItem(n3, bl, n2, bl2);
            }
            return true;
        }
        this.setScrollingCacheEnabled(false);
        return false;
    }

    OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener onPageChangeListener) {
        OnPageChangeListener onPageChangeListener2 = this.mInternalPageChangeListener;
        this.mInternalPageChangeListener = onPageChangeListener;
        return onPageChangeListener2;
    }

    public void setOffscreenPageLimit(int n) {
        int n2 = n;
        if (n < 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Requested offscreen page limit ");
            stringBuilder.append(n);
            stringBuilder.append(" too small; defaulting to ");
            stringBuilder.append(1);
            Log.w(TAG, stringBuilder.toString());
            n2 = 1;
        }
        if (n2 != this.mOffscreenPageLimit) {
            this.mOffscreenPageLimit = n2;
            this.populate();
        }
    }

    void setOnAdapterChangeListener(OnAdapterChangeListener onAdapterChangeListener) {
        this.mAdapterChangeListener = onAdapterChangeListener;
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    public void setPageMargin(int n) {
        int n2 = this.mPageMargin;
        this.mPageMargin = n;
        int n3 = this.getWidth();
        this.recomputeScrollPosition(n3, n3, n, n2);
        this.requestLayout();
    }

    public void setPageMarginDrawable(int n) {
        this.setPageMarginDrawable(this.getContext().getDrawable(n));
    }

    public void setPageMarginDrawable(Drawable drawable2) {
        this.mMarginDrawable = drawable2;
        if (drawable2 != null) {
            this.refreshDrawableState();
        }
        boolean bl = drawable2 == null;
        this.setWillNotDraw(bl);
        this.invalidate();
    }

    public void setPageTransformer(boolean bl, PageTransformer pageTransformer) {
        int n = 1;
        boolean bl2 = pageTransformer != null;
        boolean bl3 = this.mPageTransformer != null;
        boolean bl4 = bl2 != bl3;
        this.mPageTransformer = pageTransformer;
        this.setChildrenDrawingOrderEnabled(bl2);
        if (bl2) {
            if (bl) {
                n = 2;
            }
            this.mDrawingOrder = n;
        } else {
            this.mDrawingOrder = 0;
        }
        if (bl4) {
            this.populate();
        }
    }

    void smoothScrollTo(int n, int n2) {
        this.smoothScrollTo(n, n2, 0);
    }

    void smoothScrollTo(int n, int n2, int n3) {
        if (this.getChildCount() == 0) {
            this.setScrollingCacheEnabled(false);
            return;
        }
        int n4 = this.getScrollX();
        int n5 = this.getScrollY();
        int n6 = n - n4;
        if (n6 == 0 && (n2 -= n5) == 0) {
            this.completeScroll(false);
            this.populate();
            this.setScrollState(0);
            return;
        }
        this.setScrollingCacheEnabled(true);
        this.setScrollState(2);
        n = this.getPaddedWidth();
        int n7 = n / 2;
        float f = Math.min(1.0f, (float)Math.abs(n6) * 1.0f / (float)n);
        float f2 = n7;
        float f3 = n7;
        f = this.distanceInfluenceForSnapDuration(f);
        n3 = Math.abs(n3);
        if (n3 > 0) {
            n = Math.round(Math.abs((f2 + f3 * f) / (float)n3) * 1000.0f) * 4;
        } else {
            f2 = n;
            f3 = this.mAdapter.getPageWidth(this.mCurItem);
            n = (int)((1.0f + (float)Math.abs(n6) / ((float)this.mPageMargin + f2 * f3)) * 100.0f);
        }
        n = Math.min(n, 600);
        this.mScroller.startScroll(n4, n5, n6, n2, n);
        this.postInvalidateOnAnimation();
    }

    @Override
    protected boolean verifyDrawable(Drawable drawable2) {
        boolean bl = super.verifyDrawable(drawable2) || drawable2 == this.mMarginDrawable;
        return bl;
    }

    static interface Decor {
    }

    static class ItemInfo {
        Object object;
        float offset;
        int position;
        boolean scrolling;
        float widthFactor;

        ItemInfo() {
        }
    }

    public static class LayoutParams
    extends ViewGroup.LayoutParams {
        int childIndex;
        public int gravity;
        public boolean isDecor;
        boolean needsMeasure;
        int position;
        float widthFactor = 0.0f;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(Context object, AttributeSet attributeSet) {
            super((Context)object, attributeSet);
            object = ((Context)object).obtainStyledAttributes(attributeSet, LAYOUT_ATTRS);
            this.gravity = ((TypedArray)object).getInteger(0, 48);
            ((TypedArray)object).recycle();
        }
    }

    static interface OnAdapterChangeListener {
        public void onAdapterChanged(PagerAdapter var1, PagerAdapter var2);
    }

    public static interface OnPageChangeListener {
        @UnsupportedAppUsage
        public void onPageScrollStateChanged(int var1);

        @UnsupportedAppUsage
        public void onPageScrolled(int var1, float var2, int var3);

        @UnsupportedAppUsage
        public void onPageSelected(int var1);
    }

    public static interface PageTransformer {
        public void transformPage(View var1, float var2);
    }

    private class PagerObserver
    extends DataSetObserver {
        private PagerObserver() {
        }

        @Override
        public void onChanged() {
            ViewPager.this.dataSetChanged();
        }

        @Override
        public void onInvalidated() {
            ViewPager.this.dataSetChanged();
        }
    }

    public static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            @Override
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        Parcelable adapterState;
        ClassLoader loader;
        int position;

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            ClassLoader classLoader2 = classLoader;
            if (classLoader == null) {
                classLoader2 = this.getClass().getClassLoader();
            }
            this.position = parcel.readInt();
            this.adapterState = parcel.readParcelable(classLoader2);
            this.loader = classLoader2;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("FragmentPager.SavedState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" position=");
            stringBuilder.append(this.position);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.position);
            parcel.writeParcelable(this.adapterState, n);
        }

    }

    public static class SimpleOnPageChangeListener
    implements OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int n) {
        }

        @Override
        public void onPageScrolled(int n, float f, int n2) {
        }

        @Override
        public void onPageSelected(int n) {
        }
    }

    static class ViewPositionComparator
    implements Comparator<View> {
        ViewPositionComparator() {
        }

        @Override
        public int compare(View object, View object2) {
            object = (LayoutParams)((View)object).getLayoutParams();
            object2 = (LayoutParams)((View)object2).getLayoutParams();
            if (((LayoutParams)object).isDecor != ((LayoutParams)object2).isDecor) {
                int n = ((LayoutParams)object).isDecor ? 1 : -1;
                return n;
            }
            return ((LayoutParams)object).position - ((LayoutParams)object2).position;
        }
    }

}

