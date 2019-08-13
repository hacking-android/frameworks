/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.content.Context;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import com.android.internal.widget.LinearSmoothScroller;
import com.android.internal.widget.OrientationHelper;
import com.android.internal.widget.RecyclerView;
import com.android.internal.widget.ScrollbarHelper;
import com.android.internal.widget.helper.ItemTouchHelper;
import java.util.List;

public class LinearLayoutManager
extends RecyclerView.LayoutManager
implements ItemTouchHelper.ViewDropHandler,
RecyclerView.SmoothScroller.ScrollVectorProvider {
    static final boolean DEBUG = false;
    public static final int HORIZONTAL = 0;
    public static final int INVALID_OFFSET = Integer.MIN_VALUE;
    private static final float MAX_SCROLL_FACTOR = 0.33333334f;
    private static final String TAG = "LinearLayoutManager";
    public static final int VERTICAL = 1;
    final AnchorInfo mAnchorInfo = new AnchorInfo();
    private int mInitialItemPrefetchCount = 2;
    private boolean mLastStackFromEnd;
    private final LayoutChunkResult mLayoutChunkResult = new LayoutChunkResult();
    private LayoutState mLayoutState;
    int mOrientation;
    OrientationHelper mOrientationHelper;
    SavedState mPendingSavedState = null;
    int mPendingScrollPosition = -1;
    int mPendingScrollPositionOffset = Integer.MIN_VALUE;
    private boolean mRecycleChildrenOnDetach;
    private boolean mReverseLayout = false;
    boolean mShouldReverseLayout = false;
    private boolean mSmoothScrollbarEnabled = true;
    private boolean mStackFromEnd = false;

    public LinearLayoutManager(Context context) {
        this(context, 1, false);
    }

    public LinearLayoutManager(Context context, int n, boolean bl) {
        this.setOrientation(n);
        this.setReverseLayout(bl);
        this.setAutoMeasureEnabled(true);
    }

    public LinearLayoutManager(Context object, AttributeSet attributeSet, int n, int n2) {
        object = LinearLayoutManager.getProperties((Context)object, attributeSet, n, n2);
        this.setOrientation(((RecyclerView.LayoutManager.Properties)object).orientation);
        this.setReverseLayout(((RecyclerView.LayoutManager.Properties)object).reverseLayout);
        this.setStackFromEnd(((RecyclerView.LayoutManager.Properties)object).stackFromEnd);
        this.setAutoMeasureEnabled(true);
    }

    private int computeScrollExtent(RecyclerView.State state) {
        if (this.getChildCount() == 0) {
            return 0;
        }
        this.ensureLayoutState();
        return ScrollbarHelper.computeScrollExtent(state, this.mOrientationHelper, this.findFirstVisibleChildClosestToStart(this.mSmoothScrollbarEnabled ^ true, true), this.findFirstVisibleChildClosestToEnd(this.mSmoothScrollbarEnabled ^ true, true), this, this.mSmoothScrollbarEnabled);
    }

    private int computeScrollOffset(RecyclerView.State state) {
        if (this.getChildCount() == 0) {
            return 0;
        }
        this.ensureLayoutState();
        return ScrollbarHelper.computeScrollOffset(state, this.mOrientationHelper, this.findFirstVisibleChildClosestToStart(this.mSmoothScrollbarEnabled ^ true, true), this.findFirstVisibleChildClosestToEnd(this.mSmoothScrollbarEnabled ^ true, true), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
    }

    private int computeScrollRange(RecyclerView.State state) {
        if (this.getChildCount() == 0) {
            return 0;
        }
        this.ensureLayoutState();
        return ScrollbarHelper.computeScrollRange(state, this.mOrientationHelper, this.findFirstVisibleChildClosestToStart(this.mSmoothScrollbarEnabled ^ true, true), this.findFirstVisibleChildClosestToEnd(this.mSmoothScrollbarEnabled ^ true, true), this, this.mSmoothScrollbarEnabled);
    }

    private View findFirstReferenceChild(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return this.findReferenceChild(recycler, state, 0, this.getChildCount(), state.getItemCount());
    }

    private View findFirstVisibleChildClosestToEnd(boolean bl, boolean bl2) {
        if (this.mShouldReverseLayout) {
            return this.findOneVisibleChild(0, this.getChildCount(), bl, bl2);
        }
        return this.findOneVisibleChild(this.getChildCount() - 1, -1, bl, bl2);
    }

    private View findFirstVisibleChildClosestToStart(boolean bl, boolean bl2) {
        if (this.mShouldReverseLayout) {
            return this.findOneVisibleChild(this.getChildCount() - 1, -1, bl, bl2);
        }
        return this.findOneVisibleChild(0, this.getChildCount(), bl, bl2);
    }

    private View findLastReferenceChild(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return this.findReferenceChild(recycler, state, this.getChildCount() - 1, -1, state.getItemCount());
    }

    private View findReferenceChildClosestToEnd(RecyclerView.Recycler object, RecyclerView.State state) {
        object = this.mShouldReverseLayout ? this.findFirstReferenceChild((RecyclerView.Recycler)object, state) : this.findLastReferenceChild((RecyclerView.Recycler)object, state);
        return object;
    }

    private View findReferenceChildClosestToStart(RecyclerView.Recycler object, RecyclerView.State state) {
        object = this.mShouldReverseLayout ? this.findLastReferenceChild((RecyclerView.Recycler)object, state) : this.findFirstReferenceChild((RecyclerView.Recycler)object, state);
        return object;
    }

    private int fixLayoutEndGap(int n, RecyclerView.Recycler recycler, RecyclerView.State state, boolean bl) {
        int n2 = this.mOrientationHelper.getEndAfterPadding() - n;
        if (n2 > 0) {
            n2 = -this.scrollBy(-n2, recycler, state);
            if (bl && (n = this.mOrientationHelper.getEndAfterPadding() - (n + n2)) > 0) {
                this.mOrientationHelper.offsetChildren(n);
                return n + n2;
            }
            return n2;
        }
        return 0;
    }

    private int fixLayoutStartGap(int n, RecyclerView.Recycler recycler, RecyclerView.State state, boolean bl) {
        int n2 = n - this.mOrientationHelper.getStartAfterPadding();
        if (n2 > 0) {
            n2 = -this.scrollBy(n2, recycler, state);
            if (bl && (n = n + n2 - this.mOrientationHelper.getStartAfterPadding()) > 0) {
                this.mOrientationHelper.offsetChildren(-n);
                return n2 - n;
            }
            return n2;
        }
        return 0;
    }

    private View getChildClosestToEnd() {
        int n = this.mShouldReverseLayout ? 0 : this.getChildCount() - 1;
        return this.getChildAt(n);
    }

    private View getChildClosestToStart() {
        int n = this.mShouldReverseLayout ? this.getChildCount() - 1 : 0;
        return this.getChildAt(n);
    }

    private void layoutForPredictiveAnimations(RecyclerView.Recycler recycler, RecyclerView.State state, int n, int n2) {
        if (state.willRunPredictiveAnimations() && this.getChildCount() != 0 && !state.isPreLayout() && this.supportsPredictiveItemAnimations()) {
            int n3 = 0;
            int n4 = 0;
            Object object = recycler.getScrapList();
            int n5 = object.size();
            int n6 = this.getPosition(this.getChildAt(0));
            for (int i = 0; i < n5; ++i) {
                RecyclerView.ViewHolder viewHolder = object.get(i);
                if (viewHolder.isRemoved()) continue;
                int n7 = viewHolder.getLayoutPosition();
                int n8 = 1;
                boolean bl = n7 < n6;
                if (bl != this.mShouldReverseLayout) {
                    n8 = -1;
                }
                if (n8 == -1) {
                    n3 += this.mOrientationHelper.getDecoratedMeasurement(viewHolder.itemView);
                    continue;
                }
                n4 += this.mOrientationHelper.getDecoratedMeasurement(viewHolder.itemView);
            }
            this.mLayoutState.mScrapList = object;
            if (n3 > 0) {
                this.updateLayoutStateToFillStart(this.getPosition(this.getChildClosestToStart()), n);
                object = this.mLayoutState;
                ((LayoutState)object).mExtra = n3;
                ((LayoutState)object).mAvailable = 0;
                ((LayoutState)object).assignPositionFromScrapList();
                this.fill(recycler, this.mLayoutState, state, false);
            }
            if (n4 > 0) {
                this.updateLayoutStateToFillEnd(this.getPosition(this.getChildClosestToEnd()), n2);
                object = this.mLayoutState;
                ((LayoutState)object).mExtra = n4;
                ((LayoutState)object).mAvailable = 0;
                ((LayoutState)object).assignPositionFromScrapList();
                this.fill(recycler, this.mLayoutState, state, false);
            }
            this.mLayoutState.mScrapList = null;
            return;
        }
    }

    private void logChildren() {
        Log.d(TAG, "internal representation of views on the screen");
        for (int i = 0; i < this.getChildCount(); ++i) {
            View view = this.getChildAt(i);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("item ");
            stringBuilder.append(this.getPosition(view));
            stringBuilder.append(", coord:");
            stringBuilder.append(this.mOrientationHelper.getDecoratedStart(view));
            Log.d(TAG, stringBuilder.toString());
        }
        Log.d(TAG, "==============");
    }

    private void recycleByLayoutState(RecyclerView.Recycler recycler, LayoutState layoutState) {
        if (layoutState.mRecycle && !layoutState.mInfinite) {
            if (layoutState.mLayoutDirection == -1) {
                this.recycleViewsFromEnd(recycler, layoutState.mScrollingOffset);
            } else {
                this.recycleViewsFromStart(recycler, layoutState.mScrollingOffset);
            }
            return;
        }
    }

    private void recycleChildren(RecyclerView.Recycler recycler, int n, int n2) {
        if (n == n2) {
            return;
        }
        if (n2 > n) {
            --n2;
            while (n2 >= n) {
                this.removeAndRecycleViewAt(n2, recycler);
                --n2;
            }
        } else {
            while (n > n2) {
                this.removeAndRecycleViewAt(n, recycler);
                --n;
            }
        }
    }

    private void recycleViewsFromEnd(RecyclerView.Recycler recycler, int n) {
        int n2 = this.getChildCount();
        if (n < 0) {
            return;
        }
        int n3 = this.mOrientationHelper.getEnd() - n;
        if (this.mShouldReverseLayout) {
            for (n = 0; n < n2; ++n) {
                View view = this.getChildAt(n);
                if (this.mOrientationHelper.getDecoratedStart(view) >= n3 && this.mOrientationHelper.getTransformedStartWithDecoration(view) >= n3) {
                    continue;
                }
                this.recycleChildren(recycler, 0, n);
                return;
            }
        } else {
            for (n = n2 - 1; n >= 0; --n) {
                View view = this.getChildAt(n);
                if (this.mOrientationHelper.getDecoratedStart(view) >= n3 && this.mOrientationHelper.getTransformedStartWithDecoration(view) >= n3) {
                    continue;
                }
                this.recycleChildren(recycler, n2 - 1, n);
                return;
            }
        }
    }

    private void recycleViewsFromStart(RecyclerView.Recycler recycler, int n) {
        if (n < 0) {
            return;
        }
        int n2 = this.getChildCount();
        if (this.mShouldReverseLayout) {
            for (int i = n2 - 1; i >= 0; --i) {
                View view = this.getChildAt(i);
                if (this.mOrientationHelper.getDecoratedEnd(view) <= n && this.mOrientationHelper.getTransformedEndWithDecoration(view) <= n) {
                    continue;
                }
                this.recycleChildren(recycler, n2 - 1, i);
                return;
            }
        } else {
            for (int i = 0; i < n2; ++i) {
                View view = this.getChildAt(i);
                if (this.mOrientationHelper.getDecoratedEnd(view) <= n && this.mOrientationHelper.getTransformedEndWithDecoration(view) <= n) {
                    continue;
                }
                this.recycleChildren(recycler, 0, i);
                return;
            }
        }
    }

    private void resolveShouldLayoutReverse() {
        this.mShouldReverseLayout = this.mOrientation != 1 && this.isLayoutRTL() ? this.mReverseLayout ^ true : this.mReverseLayout;
    }

    private boolean updateAnchorFromChildren(RecyclerView.Recycler object, RecyclerView.State state, AnchorInfo anchorInfo) {
        int n = this.getChildCount();
        int n2 = 0;
        if (n == 0) {
            return false;
        }
        View view = this.getFocusedChild();
        if (view != null && anchorInfo.isViewValidAsAnchor(view, state)) {
            anchorInfo.assignFromViewAndKeepVisibleRect(view);
            return true;
        }
        if (this.mLastStackFromEnd != this.mStackFromEnd) {
            return false;
        }
        object = anchorInfo.mLayoutFromEnd ? this.findReferenceChildClosestToEnd((RecyclerView.Recycler)object, state) : this.findReferenceChildClosestToStart((RecyclerView.Recycler)object, state);
        if (object != null) {
            anchorInfo.assignFromView((View)object);
            if (!state.isPreLayout() && this.supportsPredictiveItemAnimations()) {
                if (this.mOrientationHelper.getDecoratedStart((View)object) >= this.mOrientationHelper.getEndAfterPadding() || this.mOrientationHelper.getDecoratedEnd((View)object) < this.mOrientationHelper.getStartAfterPadding()) {
                    n2 = 1;
                }
                if (n2 != 0) {
                    n2 = anchorInfo.mLayoutFromEnd ? this.mOrientationHelper.getEndAfterPadding() : this.mOrientationHelper.getStartAfterPadding();
                    anchorInfo.mCoordinate = n2;
                }
            }
            return true;
        }
        return false;
    }

    private boolean updateAnchorFromPendingData(RecyclerView.State object, AnchorInfo anchorInfo) {
        int n;
        boolean bl = ((RecyclerView.State)object).isPreLayout();
        boolean bl2 = false;
        if (!bl && (n = this.mPendingScrollPosition) != -1) {
            if (n >= 0 && n < ((RecyclerView.State)object).getItemCount()) {
                anchorInfo.mPosition = this.mPendingScrollPosition;
                object = this.mPendingSavedState;
                if (object != null && ((SavedState)object).hasValidAnchor()) {
                    anchorInfo.mLayoutFromEnd = this.mPendingSavedState.mAnchorLayoutFromEnd;
                    anchorInfo.mCoordinate = anchorInfo.mLayoutFromEnd ? this.mOrientationHelper.getEndAfterPadding() - this.mPendingSavedState.mAnchorOffset : this.mOrientationHelper.getStartAfterPadding() + this.mPendingSavedState.mAnchorOffset;
                    return true;
                }
                if (this.mPendingScrollPositionOffset == Integer.MIN_VALUE) {
                    object = this.findViewByPosition(this.mPendingScrollPosition);
                    if (object != null) {
                        if (this.mOrientationHelper.getDecoratedMeasurement((View)object) > this.mOrientationHelper.getTotalSpace()) {
                            anchorInfo.assignCoordinateFromPadding();
                            return true;
                        }
                        if (this.mOrientationHelper.getDecoratedStart((View)object) - this.mOrientationHelper.getStartAfterPadding() < 0) {
                            anchorInfo.mCoordinate = this.mOrientationHelper.getStartAfterPadding();
                            anchorInfo.mLayoutFromEnd = false;
                            return true;
                        }
                        if (this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd((View)object) < 0) {
                            anchorInfo.mCoordinate = this.mOrientationHelper.getEndAfterPadding();
                            anchorInfo.mLayoutFromEnd = true;
                            return true;
                        }
                        n = anchorInfo.mLayoutFromEnd ? this.mOrientationHelper.getDecoratedEnd((View)object) + this.mOrientationHelper.getTotalSpaceChange() : this.mOrientationHelper.getDecoratedStart((View)object);
                        anchorInfo.mCoordinate = n;
                    } else {
                        if (this.getChildCount() > 0) {
                            n = this.getPosition(this.getChildAt(0));
                            bl = this.mPendingScrollPosition < n;
                            if (bl == this.mShouldReverseLayout) {
                                bl2 = true;
                            }
                            anchorInfo.mLayoutFromEnd = bl2;
                        }
                        anchorInfo.assignCoordinateFromPadding();
                    }
                    return true;
                }
                anchorInfo.mLayoutFromEnd = bl = this.mShouldReverseLayout;
                anchorInfo.mCoordinate = bl ? this.mOrientationHelper.getEndAfterPadding() - this.mPendingScrollPositionOffset : this.mOrientationHelper.getStartAfterPadding() + this.mPendingScrollPositionOffset;
                return true;
            }
            this.mPendingScrollPosition = -1;
            this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
            return false;
        }
        return false;
    }

    private void updateAnchorInfoForLayout(RecyclerView.Recycler recycler, RecyclerView.State state, AnchorInfo anchorInfo) {
        if (this.updateAnchorFromPendingData(state, anchorInfo)) {
            return;
        }
        if (this.updateAnchorFromChildren(recycler, state, anchorInfo)) {
            return;
        }
        anchorInfo.assignCoordinateFromPadding();
        int n = this.mStackFromEnd ? state.getItemCount() - 1 : 0;
        anchorInfo.mPosition = n;
    }

    private void updateLayoutState(int n, int n2, boolean bl, RecyclerView.State object) {
        this.mLayoutState.mInfinite = this.resolveIsInfinite();
        this.mLayoutState.mExtra = this.getExtraLayoutSpace((RecyclerView.State)object);
        object = this.mLayoutState;
        ((LayoutState)object).mLayoutDirection = n;
        int n3 = -1;
        if (n == 1) {
            ((LayoutState)object).mExtra += this.mOrientationHelper.getEndPadding();
            View view = this.getChildClosestToEnd();
            object = this.mLayoutState;
            if (!this.mShouldReverseLayout) {
                n3 = 1;
            }
            ((LayoutState)object).mItemDirection = n3;
            this.mLayoutState.mCurrentPosition = this.getPosition(view) + this.mLayoutState.mItemDirection;
            this.mLayoutState.mOffset = this.mOrientationHelper.getDecoratedEnd(view);
            n = this.mOrientationHelper.getDecoratedEnd(view) - this.mOrientationHelper.getEndAfterPadding();
        } else {
            object = this.getChildClosestToStart();
            LayoutState layoutState = this.mLayoutState;
            layoutState.mExtra += this.mOrientationHelper.getStartAfterPadding();
            layoutState = this.mLayoutState;
            if (this.mShouldReverseLayout) {
                n3 = 1;
            }
            layoutState.mItemDirection = n3;
            this.mLayoutState.mCurrentPosition = this.getPosition((View)object) + this.mLayoutState.mItemDirection;
            this.mLayoutState.mOffset = this.mOrientationHelper.getDecoratedStart((View)object);
            n = -this.mOrientationHelper.getDecoratedStart((View)object) + this.mOrientationHelper.getStartAfterPadding();
        }
        object = this.mLayoutState;
        ((LayoutState)object).mAvailable = n2;
        if (bl) {
            ((LayoutState)object).mAvailable -= n;
        }
        this.mLayoutState.mScrollingOffset = n;
    }

    private void updateLayoutStateToFillEnd(int n, int n2) {
        this.mLayoutState.mAvailable = this.mOrientationHelper.getEndAfterPadding() - n2;
        LayoutState layoutState = this.mLayoutState;
        int n3 = this.mShouldReverseLayout ? -1 : 1;
        layoutState.mItemDirection = n3;
        layoutState = this.mLayoutState;
        layoutState.mCurrentPosition = n;
        layoutState.mLayoutDirection = 1;
        layoutState.mOffset = n2;
        layoutState.mScrollingOffset = Integer.MIN_VALUE;
    }

    private void updateLayoutStateToFillEnd(AnchorInfo anchorInfo) {
        this.updateLayoutStateToFillEnd(anchorInfo.mPosition, anchorInfo.mCoordinate);
    }

    private void updateLayoutStateToFillStart(int n, int n2) {
        this.mLayoutState.mAvailable = n2 - this.mOrientationHelper.getStartAfterPadding();
        LayoutState layoutState = this.mLayoutState;
        layoutState.mCurrentPosition = n;
        n = this.mShouldReverseLayout ? 1 : -1;
        layoutState.mItemDirection = n;
        layoutState = this.mLayoutState;
        layoutState.mLayoutDirection = -1;
        layoutState.mOffset = n2;
        layoutState.mScrollingOffset = Integer.MIN_VALUE;
    }

    private void updateLayoutStateToFillStart(AnchorInfo anchorInfo) {
        this.updateLayoutStateToFillStart(anchorInfo.mPosition, anchorInfo.mCoordinate);
    }

    @Override
    public void assertNotInLayoutOrScroll(String string2) {
        if (this.mPendingSavedState == null) {
            super.assertNotInLayoutOrScroll(string2);
        }
    }

    @Override
    public boolean canScrollHorizontally() {
        boolean bl = this.mOrientation == 0;
        return bl;
    }

    @Override
    public boolean canScrollVertically() {
        int n = this.mOrientation;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    @Override
    public void collectAdjacentPrefetchPositions(int n, int n2, RecyclerView.State state, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        if (this.mOrientation != 0) {
            n = n2;
        }
        if (this.getChildCount() != 0 && n != 0) {
            n2 = n > 0 ? 1 : -1;
            this.updateLayoutState(n2, Math.abs(n), true, state);
            this.collectPrefetchPositionsForLayoutState(state, this.mLayoutState, layoutPrefetchRegistry);
            return;
        }
    }

    @Override
    public void collectInitialPrefetchPositions(int n, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        boolean bl;
        int n2;
        SavedState savedState = this.mPendingSavedState;
        int n3 = -1;
        if (savedState != null && savedState.hasValidAnchor()) {
            bl = this.mPendingSavedState.mAnchorLayoutFromEnd;
            n2 = this.mPendingSavedState.mAnchorPosition;
        } else {
            this.resolveShouldLayoutReverse();
            bl = this.mShouldReverseLayout;
            n2 = this.mPendingScrollPosition == -1 ? (bl ? n - 1 : 0) : this.mPendingScrollPosition;
        }
        if (!bl) {
            n3 = 1;
        }
        int n4 = n2;
        for (n2 = 0; n2 < this.mInitialItemPrefetchCount && n4 >= 0 && n4 < n; n4 += n3, ++n2) {
            layoutPrefetchRegistry.addPosition(n4, 0);
        }
    }

    void collectPrefetchPositionsForLayoutState(RecyclerView.State state, LayoutState layoutState, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int n = layoutState.mCurrentPosition;
        if (n >= 0 && n < state.getItemCount()) {
            layoutPrefetchRegistry.addPosition(n, layoutState.mScrollingOffset);
        }
    }

    @Override
    public int computeHorizontalScrollExtent(RecyclerView.State state) {
        return this.computeScrollExtent(state);
    }

    @Override
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return this.computeScrollOffset(state);
    }

    @Override
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        return this.computeScrollRange(state);
    }

    @Override
    public PointF computeScrollVectorForPosition(int n) {
        if (this.getChildCount() == 0) {
            return null;
        }
        boolean bl = false;
        int n2 = this.getPosition(this.getChildAt(0));
        int n3 = 1;
        if (n < n2) {
            bl = true;
        }
        n = n3;
        if (bl != this.mShouldReverseLayout) {
            n = -1;
        }
        if (this.mOrientation == 0) {
            return new PointF(n, 0.0f);
        }
        return new PointF(0.0f, n);
    }

    @Override
    public int computeVerticalScrollExtent(RecyclerView.State state) {
        return this.computeScrollExtent(state);
    }

    @Override
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        return this.computeScrollOffset(state);
    }

    @Override
    public int computeVerticalScrollRange(RecyclerView.State state) {
        return this.computeScrollRange(state);
    }

    int convertFocusDirectionToLayoutDirection(int n) {
        int n2 = -1;
        int n3 = 1;
        int n4 = 1;
        if (n != 1) {
            if (n != 2) {
                if (n != 17) {
                    if (n != 33) {
                        if (n != 66) {
                            if (n != 130) {
                                return Integer.MIN_VALUE;
                            }
                            n = this.mOrientation == 1 ? n4 : Integer.MIN_VALUE;
                            return n;
                        }
                        n = this.mOrientation == 0 ? n3 : Integer.MIN_VALUE;
                        return n;
                    }
                    if (this.mOrientation != 1) {
                        n2 = Integer.MIN_VALUE;
                    }
                    return n2;
                }
                if (this.mOrientation != 0) {
                    n2 = Integer.MIN_VALUE;
                }
                return n2;
            }
            if (this.mOrientation == 1) {
                return 1;
            }
            if (this.isLayoutRTL()) {
                return -1;
            }
            return 1;
        }
        if (this.mOrientation == 1) {
            return -1;
        }
        if (this.isLayoutRTL()) {
            return 1;
        }
        return -1;
    }

    LayoutState createLayoutState() {
        return new LayoutState();
    }

    void ensureLayoutState() {
        if (this.mLayoutState == null) {
            this.mLayoutState = this.createLayoutState();
        }
        if (this.mOrientationHelper == null) {
            this.mOrientationHelper = OrientationHelper.createOrientationHelper(this, this.mOrientation);
        }
    }

    int fill(RecyclerView.Recycler recycler, LayoutState layoutState, RecyclerView.State state, boolean bl) {
        int n = layoutState.mAvailable;
        if (layoutState.mScrollingOffset != Integer.MIN_VALUE) {
            if (layoutState.mAvailable < 0) {
                layoutState.mScrollingOffset += layoutState.mAvailable;
            }
            this.recycleByLayoutState(recycler, layoutState);
        }
        int n2 = layoutState.mAvailable + layoutState.mExtra;
        LayoutChunkResult layoutChunkResult = this.mLayoutChunkResult;
        while ((layoutState.mInfinite || n2 > 0) && layoutState.hasMore(state)) {
            int n3;
            block9 : {
                block8 : {
                    layoutChunkResult.resetInternal();
                    this.layoutChunk(recycler, state, layoutState, layoutChunkResult);
                    if (layoutChunkResult.mFinished) break;
                    layoutState.mOffset += layoutChunkResult.mConsumed * layoutState.mLayoutDirection;
                    if (!layoutChunkResult.mIgnoreConsumed || this.mLayoutState.mScrapList != null) break block8;
                    n3 = n2;
                    if (state.isPreLayout()) break block9;
                }
                layoutState.mAvailable -= layoutChunkResult.mConsumed;
                n3 = n2 - layoutChunkResult.mConsumed;
            }
            if (layoutState.mScrollingOffset != Integer.MIN_VALUE) {
                layoutState.mScrollingOffset += layoutChunkResult.mConsumed;
                if (layoutState.mAvailable < 0) {
                    layoutState.mScrollingOffset += layoutState.mAvailable;
                }
                this.recycleByLayoutState(recycler, layoutState);
            }
            n2 = n3;
            if (!bl) continue;
            n2 = n3;
            if (!layoutChunkResult.mFocusable) continue;
        }
        return n - layoutState.mAvailable;
    }

    public int findFirstCompletelyVisibleItemPosition() {
        View view = this.findOneVisibleChild(0, this.getChildCount(), true, false);
        int n = view == null ? -1 : this.getPosition(view);
        return n;
    }

    public int findFirstVisibleItemPosition() {
        View view = this.findOneVisibleChild(0, this.getChildCount(), false, true);
        int n = view == null ? -1 : this.getPosition(view);
        return n;
    }

    public int findLastCompletelyVisibleItemPosition() {
        int n = this.getChildCount();
        int n2 = -1;
        View view = this.findOneVisibleChild(n - 1, -1, true, false);
        if (view != null) {
            n2 = this.getPosition(view);
        }
        return n2;
    }

    public int findLastVisibleItemPosition() {
        int n = this.getChildCount();
        int n2 = -1;
        View view = this.findOneVisibleChild(n - 1, -1, false, true);
        if (view != null) {
            n2 = this.getPosition(view);
        }
        return n2;
    }

    View findOneVisibleChild(int n, int n2, boolean bl, boolean bl2) {
        this.ensureLayoutState();
        int n3 = this.mOrientationHelper.getStartAfterPadding();
        int n4 = this.mOrientationHelper.getEndAfterPadding();
        int n5 = n2 > n ? 1 : -1;
        View view = null;
        while (n != n2) {
            View view2 = this.getChildAt(n);
            int n6 = this.mOrientationHelper.getDecoratedStart(view2);
            int n7 = this.mOrientationHelper.getDecoratedEnd(view2);
            View view3 = view;
            if (n6 < n4) {
                view3 = view;
                if (n7 > n3) {
                    if (bl) {
                        if (n6 >= n3 && n7 <= n4) {
                            return view2;
                        }
                        view3 = view;
                        if (bl2) {
                            view3 = view;
                            if (view == null) {
                                view3 = view2;
                            }
                        }
                    } else {
                        return view2;
                    }
                }
            }
            n += n5;
            view = view3;
        }
        return view;
    }

    View findReferenceChild(RecyclerView.Recycler object, RecyclerView.State state, int n, int n2, int n3) {
        this.ensureLayoutState();
        state = null;
        object = null;
        int n4 = this.mOrientationHelper.getStartAfterPadding();
        int n5 = this.mOrientationHelper.getEndAfterPadding();
        int n6 = n2 > n ? 1 : -1;
        while (n != n2) {
            View view = this.getChildAt(n);
            int n7 = this.getPosition(view);
            Object object2 = state;
            Object object3 = object;
            if (n7 >= 0) {
                object2 = state;
                object3 = object;
                if (n7 < n3) {
                    if (((RecyclerView.LayoutParams)view.getLayoutParams()).isItemRemoved()) {
                        object2 = state;
                        object3 = object;
                        if (state == null) {
                            object2 = view;
                            object3 = object;
                        }
                    } else {
                        if (this.mOrientationHelper.getDecoratedStart(view) < n5 && this.mOrientationHelper.getDecoratedEnd(view) >= n4) {
                            return view;
                        }
                        object2 = state;
                        object3 = object;
                        if (object == null) {
                            object3 = view;
                            object2 = state;
                        }
                    }
                }
            }
            n += n6;
            state = object2;
            object = object3;
        }
        if (object == null) {
            object = state;
        }
        return object;
    }

    @Override
    public View findViewByPosition(int n) {
        View view;
        int n2 = this.getChildCount();
        if (n2 == 0) {
            return null;
        }
        int n3 = n - this.getPosition(this.getChildAt(0));
        if (n3 >= 0 && n3 < n2 && this.getPosition(view = this.getChildAt(n3)) == n) {
            return view;
        }
        return super.findViewByPosition(n);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    protected int getExtraLayoutSpace(RecyclerView.State state) {
        if (state.hasTargetScrollPosition()) {
            return this.mOrientationHelper.getTotalSpace();
        }
        return 0;
    }

    public int getInitialItemPrefetchCount() {
        return this.mInitialItemPrefetchCount;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public boolean getRecycleChildrenOnDetach() {
        return this.mRecycleChildrenOnDetach;
    }

    public boolean getReverseLayout() {
        return this.mReverseLayout;
    }

    public boolean getStackFromEnd() {
        return this.mStackFromEnd;
    }

    protected boolean isLayoutRTL() {
        int n = this.getLayoutDirection();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public boolean isSmoothScrollbarEnabled() {
        return this.mSmoothScrollbarEnabled;
    }

    void layoutChunk(RecyclerView.Recycler object, RecyclerView.State object2, LayoutState layoutState, LayoutChunkResult layoutChunkResult) {
        int n;
        int n2;
        int n3;
        int n4;
        if ((object = layoutState.next((RecyclerView.Recycler)object)) == null) {
            layoutChunkResult.mFinished = true;
            return;
        }
        object2 = (RecyclerView.LayoutParams)((View)object).getLayoutParams();
        if (layoutState.mScrapList == null) {
            boolean bl = this.mShouldReverseLayout;
            boolean bl2 = layoutState.mLayoutDirection == -1;
            if (bl == bl2) {
                this.addView((View)object);
            } else {
                this.addView((View)object, 0);
            }
        } else {
            boolean bl = this.mShouldReverseLayout;
            boolean bl3 = layoutState.mLayoutDirection == -1;
            if (bl == bl3) {
                this.addDisappearingView((View)object);
            } else {
                this.addDisappearingView((View)object, 0);
            }
        }
        this.measureChildWithMargins((View)object, 0, 0);
        layoutChunkResult.mConsumed = this.mOrientationHelper.getDecoratedMeasurement((View)object);
        if (this.mOrientation == 1) {
            if (this.isLayoutRTL()) {
                n2 = this.getWidth() - this.getPaddingRight();
                n = n2 - this.mOrientationHelper.getDecoratedMeasurementInOther((View)object);
            } else {
                n = this.getPaddingLeft();
                n2 = this.mOrientationHelper.getDecoratedMeasurementInOther((View)object) + n;
            }
            if (layoutState.mLayoutDirection == -1) {
                n3 = layoutState.mOffset;
                int n5 = layoutState.mOffset;
                int n6 = layoutChunkResult.mConsumed;
                n4 = n2;
                n2 = n5 - n6;
            } else {
                n3 = layoutState.mOffset;
                int n7 = layoutState.mOffset;
                int n8 = layoutChunkResult.mConsumed;
                n4 = n2;
                n2 = n3;
                n3 = n7 + n8;
            }
        } else {
            n2 = this.getPaddingTop();
            n = this.mOrientationHelper.getDecoratedMeasurementInOther((View)object) + n2;
            if (layoutState.mLayoutDirection == -1) {
                n4 = layoutState.mOffset;
                int n9 = layoutState.mOffset;
                int n10 = layoutChunkResult.mConsumed;
                n3 = n;
                n = n9 - n10;
            } else {
                int n11 = layoutState.mOffset;
                n4 = layoutState.mOffset;
                n3 = layoutChunkResult.mConsumed;
                n4 += n3;
                n3 = n;
                n = n11;
            }
        }
        this.layoutDecoratedWithMargins((View)object, n, n2, n4, n3);
        if (((RecyclerView.LayoutParams)object2).isItemRemoved() || ((RecyclerView.LayoutParams)object2).isItemChanged()) {
            layoutChunkResult.mIgnoreConsumed = true;
        }
        layoutChunkResult.mFocusable = ((View)object).isFocusable();
    }

    void onAnchorReady(RecyclerView.Recycler recycler, RecyclerView.State state, AnchorInfo anchorInfo, int n) {
    }

    @Override
    public void onDetachedFromWindow(RecyclerView recyclerView, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(recyclerView, recycler);
        if (this.mRecycleChildrenOnDetach) {
            this.removeAndRecycleAllViews(recycler);
            recycler.clear();
        }
    }

    @Override
    public View onFocusSearchFailed(View view, int n, RecyclerView.Recycler object, RecyclerView.State state) {
        this.resolveShouldLayoutReverse();
        if (this.getChildCount() == 0) {
            return null;
        }
        if ((n = this.convertFocusDirectionToLayoutDirection(n)) == Integer.MIN_VALUE) {
            return null;
        }
        this.ensureLayoutState();
        view = n == -1 ? this.findReferenceChildClosestToStart((RecyclerView.Recycler)object, state) : this.findReferenceChildClosestToEnd((RecyclerView.Recycler)object, state);
        if (view == null) {
            return null;
        }
        this.ensureLayoutState();
        this.updateLayoutState(n, (int)((float)this.mOrientationHelper.getTotalSpace() * 0.33333334f), false, state);
        LayoutState layoutState = this.mLayoutState;
        layoutState.mScrollingOffset = Integer.MIN_VALUE;
        layoutState.mRecycle = false;
        this.fill((RecyclerView.Recycler)object, layoutState, state, true);
        object = n == -1 ? this.getChildClosestToStart() : this.getChildClosestToEnd();
        if (object != view && ((View)object).isFocusable()) {
            return object;
        }
        return null;
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (this.getChildCount() > 0) {
            accessibilityEvent.setFromIndex(this.findFirstVisibleItemPosition());
            accessibilityEvent.setToIndex(this.findLastVisibleItemPosition());
        }
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int n;
        int n2;
        Object object = this.mPendingSavedState;
        int n3 = -1;
        if ((object != null || this.mPendingScrollPosition != -1) && state.getItemCount() == 0) {
            this.removeAndRecycleAllViews(recycler);
            return;
        }
        object = this.mPendingSavedState;
        if (object != null && ((SavedState)object).hasValidAnchor()) {
            this.mPendingScrollPosition = this.mPendingSavedState.mAnchorPosition;
        }
        this.ensureLayoutState();
        this.mLayoutState.mRecycle = false;
        this.resolveShouldLayoutReverse();
        if (!this.mAnchorInfo.mValid || this.mPendingScrollPosition != -1 || this.mPendingSavedState != null) {
            this.mAnchorInfo.reset();
            object = this.mAnchorInfo;
            ((AnchorInfo)object).mLayoutFromEnd = this.mShouldReverseLayout ^ this.mStackFromEnd;
            this.updateAnchorInfoForLayout(recycler, state, (AnchorInfo)object);
            this.mAnchorInfo.mValid = true;
        }
        int n4 = this.getExtraLayoutSpace(state);
        if (this.mLayoutState.mLastScrollDelta >= 0) {
            n2 = 0;
        } else {
            n2 = n4;
            n4 = 0;
        }
        int n5 = n2 + this.mOrientationHelper.getStartAfterPadding();
        n2 = n = n4 + this.mOrientationHelper.getEndPadding();
        n4 = n5;
        if (state.isPreLayout()) {
            int n6 = this.mPendingScrollPosition;
            n2 = n;
            n4 = n5;
            if (n6 != -1) {
                n2 = n;
                n4 = n5;
                if (this.mPendingScrollPositionOffset != Integer.MIN_VALUE) {
                    object = this.findViewByPosition(n6);
                    n2 = n;
                    n4 = n5;
                    if (object != null) {
                        if (this.mShouldReverseLayout) {
                            n4 = this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd((View)object) - this.mPendingScrollPositionOffset;
                        } else {
                            n2 = this.mOrientationHelper.getDecoratedStart((View)object);
                            n4 = this.mOrientationHelper.getStartAfterPadding();
                            n4 = this.mPendingScrollPositionOffset - (n2 - n4);
                        }
                        if (n4 > 0) {
                            n4 = n5 + n4;
                            n2 = n;
                        } else {
                            n2 = n - n4;
                            n4 = n5;
                        }
                    }
                }
            }
        }
        if (this.mAnchorInfo.mLayoutFromEnd) {
            if (this.mShouldReverseLayout) {
                n3 = 1;
            }
        } else if (!this.mShouldReverseLayout) {
            n3 = 1;
        }
        this.onAnchorReady(recycler, state, this.mAnchorInfo, n3);
        this.detachAndScrapAttachedViews(recycler);
        this.mLayoutState.mInfinite = this.resolveIsInfinite();
        this.mLayoutState.mIsPreLayout = state.isPreLayout();
        if (this.mAnchorInfo.mLayoutFromEnd) {
            this.updateLayoutStateToFillStart(this.mAnchorInfo);
            object = this.mLayoutState;
            ((LayoutState)object).mExtra = n4;
            this.fill(recycler, (LayoutState)object, state, false);
            n3 = this.mLayoutState.mOffset;
            n = this.mLayoutState.mCurrentPosition;
            n4 = n2;
            if (this.mLayoutState.mAvailable > 0) {
                n4 = n2 + this.mLayoutState.mAvailable;
            }
            this.updateLayoutStateToFillEnd(this.mAnchorInfo);
            object = this.mLayoutState;
            ((LayoutState)object).mExtra = n4;
            ((LayoutState)object).mCurrentPosition += this.mLayoutState.mItemDirection;
            this.fill(recycler, this.mLayoutState, state, false);
            n5 = this.mLayoutState.mOffset;
            n4 = n3;
            if (this.mLayoutState.mAvailable > 0) {
                n4 = this.mLayoutState.mAvailable;
                this.updateLayoutStateToFillStart(n, n3);
                object = this.mLayoutState;
                ((LayoutState)object).mExtra = n4;
                this.fill(recycler, (LayoutState)object, state, false);
                n4 = this.mLayoutState.mOffset;
            }
            n2 = n4;
            n4 = n5;
        } else {
            this.updateLayoutStateToFillEnd(this.mAnchorInfo);
            object = this.mLayoutState;
            ((LayoutState)object).mExtra = n2;
            this.fill(recycler, (LayoutState)object, state, false);
            n3 = this.mLayoutState.mOffset;
            n = this.mLayoutState.mCurrentPosition;
            n2 = n4;
            if (this.mLayoutState.mAvailable > 0) {
                n2 = n4 + this.mLayoutState.mAvailable;
            }
            this.updateLayoutStateToFillStart(this.mAnchorInfo);
            object = this.mLayoutState;
            ((LayoutState)object).mExtra = n2;
            ((LayoutState)object).mCurrentPosition += this.mLayoutState.mItemDirection;
            this.fill(recycler, this.mLayoutState, state, false);
            n2 = n5 = this.mLayoutState.mOffset;
            n4 = n3;
            if (this.mLayoutState.mAvailable > 0) {
                n4 = this.mLayoutState.mAvailable;
                this.updateLayoutStateToFillEnd(n, n3);
                object = this.mLayoutState;
                ((LayoutState)object).mExtra = n4;
                this.fill(recycler, (LayoutState)object, state, false);
                n4 = this.mLayoutState.mOffset;
                n2 = n5;
            }
        }
        n5 = n2;
        n3 = n4;
        if (this.getChildCount() > 0) {
            if (this.mShouldReverseLayout ^ this.mStackFromEnd) {
                n3 = this.fixLayoutEndGap(n4, recycler, state, true);
                n5 = n2 + n3;
                n2 = this.fixLayoutStartGap(n5, recycler, state, false);
                n5 += n2;
                n3 = n4 + n3 + n2;
            } else {
                n3 = this.fixLayoutStartGap(n2, recycler, state, true);
                n = this.fixLayoutEndGap(n4 += n3, recycler, state, false);
                n5 = n2 + n3 + n;
                n3 = n4 + n;
            }
        }
        this.layoutForPredictiveAnimations(recycler, state, n5, n3);
        if (!state.isPreLayout()) {
            this.mOrientationHelper.onLayoutComplete();
        } else {
            this.mAnchorInfo.reset();
        }
        this.mLastStackFromEnd = this.mStackFromEnd;
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.mPendingSavedState = null;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mAnchorInfo.reset();
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.mPendingSavedState = (SavedState)parcelable;
            this.requestLayout();
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            return new SavedState(savedState);
        }
        savedState = new SavedState();
        if (this.getChildCount() > 0) {
            boolean bl;
            this.ensureLayoutState();
            savedState.mAnchorLayoutFromEnd = bl = this.mLastStackFromEnd ^ this.mShouldReverseLayout;
            if (bl) {
                View view = this.getChildClosestToEnd();
                savedState.mAnchorOffset = this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(view);
                savedState.mAnchorPosition = this.getPosition(view);
            } else {
                View view = this.getChildClosestToStart();
                savedState.mAnchorPosition = this.getPosition(view);
                savedState.mAnchorOffset = this.mOrientationHelper.getDecoratedStart(view) - this.mOrientationHelper.getStartAfterPadding();
            }
        } else {
            savedState.invalidateAnchor();
        }
        return savedState;
    }

    @Override
    public void prepareForDrop(View view, View view2, int n, int n2) {
        this.assertNotInLayoutOrScroll("Cannot drop a view during a scroll or layout calculation");
        this.ensureLayoutState();
        this.resolveShouldLayoutReverse();
        n = this.getPosition(view);
        n2 = this.getPosition(view2);
        n = n < n2 ? 1 : -1;
        if (this.mShouldReverseLayout) {
            if (n == 1) {
                this.scrollToPositionWithOffset(n2, this.mOrientationHelper.getEndAfterPadding() - (this.mOrientationHelper.getDecoratedStart(view2) + this.mOrientationHelper.getDecoratedMeasurement(view)));
            } else {
                this.scrollToPositionWithOffset(n2, this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(view2));
            }
        } else if (n == -1) {
            this.scrollToPositionWithOffset(n2, this.mOrientationHelper.getDecoratedStart(view2));
        } else {
            this.scrollToPositionWithOffset(n2, this.mOrientationHelper.getDecoratedEnd(view2) - this.mOrientationHelper.getDecoratedMeasurement(view));
        }
    }

    boolean resolveIsInfinite() {
        boolean bl = this.mOrientationHelper.getMode() == 0 && this.mOrientationHelper.getEnd() == 0;
        return bl;
    }

    int scrollBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.getChildCount() != 0 && n != 0) {
            this.mLayoutState.mRecycle = true;
            this.ensureLayoutState();
            int n2 = n > 0 ? 1 : -1;
            int n3 = Math.abs(n);
            this.updateLayoutState(n2, n3, true, state);
            int n4 = this.mLayoutState.mScrollingOffset + this.fill(recycler, this.mLayoutState, state, false);
            if (n4 < 0) {
                return 0;
            }
            if (n3 > n4) {
                n = n2 * n4;
            }
            this.mOrientationHelper.offsetChildren(-n);
            this.mLayoutState.mLastScrollDelta = n;
            return n;
        }
        return 0;
    }

    @Override
    public int scrollHorizontallyBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 1) {
            return 0;
        }
        return this.scrollBy(n, recycler, state);
    }

    @Override
    public void scrollToPosition(int n) {
        this.mPendingScrollPosition = n;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            savedState.invalidateAnchor();
        }
        this.requestLayout();
    }

    public void scrollToPositionWithOffset(int n, int n2) {
        this.mPendingScrollPosition = n;
        this.mPendingScrollPositionOffset = n2;
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            savedState.invalidateAnchor();
        }
        this.requestLayout();
    }

    @Override
    public int scrollVerticallyBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 0) {
            return 0;
        }
        return this.scrollBy(n, recycler, state);
    }

    public void setInitialPrefetchItemCount(int n) {
        this.mInitialItemPrefetchCount = n;
    }

    public void setOrientation(int n) {
        if (n != 0 && n != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid orientation:");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.assertNotInLayoutOrScroll(null);
        if (n == this.mOrientation) {
            return;
        }
        this.mOrientation = n;
        this.mOrientationHelper = null;
        this.requestLayout();
    }

    public void setRecycleChildrenOnDetach(boolean bl) {
        this.mRecycleChildrenOnDetach = bl;
    }

    public void setReverseLayout(boolean bl) {
        this.assertNotInLayoutOrScroll(null);
        if (bl == this.mReverseLayout) {
            return;
        }
        this.mReverseLayout = bl;
        this.requestLayout();
    }

    public void setSmoothScrollbarEnabled(boolean bl) {
        this.mSmoothScrollbarEnabled = bl;
    }

    public void setStackFromEnd(boolean bl) {
        this.assertNotInLayoutOrScroll(null);
        if (this.mStackFromEnd == bl) {
            return;
        }
        this.mStackFromEnd = bl;
        this.requestLayout();
    }

    @Override
    boolean shouldMeasureTwice() {
        boolean bl = this.getHeightMode() != 1073741824 && this.getWidthMode() != 1073741824 && this.hasFlexibleChildInBothOrientations();
        return bl;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView object, RecyclerView.State state, int n) {
        object = new LinearSmoothScroller(((View)object).getContext());
        ((RecyclerView.SmoothScroller)object).setTargetPosition(n);
        this.startSmoothScroll((RecyclerView.SmoothScroller)object);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        boolean bl = this.mPendingSavedState == null && this.mLastStackFromEnd == this.mStackFromEnd;
        return bl;
    }

    void validateChildOrder() {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("validating child count ");
        ((StringBuilder)object).append(this.getChildCount());
        Log.d(TAG, ((StringBuilder)object).toString());
        if (this.getChildCount() < 1) {
            return;
        }
        boolean bl = false;
        boolean bl2 = false;
        int n = this.getPosition(this.getChildAt(0));
        int n2 = this.mOrientationHelper.getDecoratedStart(this.getChildAt(0));
        if (this.mShouldReverseLayout) {
            for (int i = 1; i < this.getChildCount(); ++i) {
                object = this.getChildAt(i);
                int n3 = this.getPosition((View)object);
                int n4 = this.mOrientationHelper.getDecoratedStart((View)object);
                if (n3 < n) {
                    this.logChildren();
                    object = new StringBuilder();
                    ((StringBuilder)object).append("detected invalid position. loc invalid? ");
                    if (n4 < n2) {
                        bl2 = true;
                    }
                    ((StringBuilder)object).append(bl2);
                    throw new RuntimeException(((StringBuilder)object).toString());
                }
                if (n4 <= n2) {
                    continue;
                }
                this.logChildren();
                throw new RuntimeException("detected invalid location");
            }
        } else {
            for (int i = 1; i < this.getChildCount(); ++i) {
                object = this.getChildAt(i);
                int n5 = this.getPosition((View)object);
                int n6 = this.mOrientationHelper.getDecoratedStart((View)object);
                if (n5 < n) {
                    this.logChildren();
                    object = new StringBuilder();
                    ((StringBuilder)object).append("detected invalid position. loc invalid? ");
                    bl2 = bl;
                    if (n6 < n2) {
                        bl2 = true;
                    }
                    ((StringBuilder)object).append(bl2);
                    throw new RuntimeException(((StringBuilder)object).toString());
                }
                if (n6 >= n2) {
                    continue;
                }
                this.logChildren();
                throw new RuntimeException("detected invalid location");
            }
        }
    }

    class AnchorInfo {
        int mCoordinate;
        boolean mLayoutFromEnd;
        int mPosition;
        boolean mValid;

        AnchorInfo() {
            this.reset();
        }

        void assignCoordinateFromPadding() {
            int n = this.mLayoutFromEnd ? LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding() : LinearLayoutManager.this.mOrientationHelper.getStartAfterPadding();
            this.mCoordinate = n;
        }

        public void assignFromView(View view) {
            this.mCoordinate = this.mLayoutFromEnd ? LinearLayoutManager.this.mOrientationHelper.getDecoratedEnd(view) + LinearLayoutManager.this.mOrientationHelper.getTotalSpaceChange() : LinearLayoutManager.this.mOrientationHelper.getDecoratedStart(view);
            this.mPosition = LinearLayoutManager.this.getPosition(view);
        }

        public void assignFromViewAndKeepVisibleRect(View view) {
            int n = LinearLayoutManager.this.mOrientationHelper.getTotalSpaceChange();
            if (n >= 0) {
                this.assignFromView(view);
                return;
            }
            this.mPosition = LinearLayoutManager.this.getPosition(view);
            if (this.mLayoutFromEnd) {
                int n2 = LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding() - n - LinearLayoutManager.this.mOrientationHelper.getDecoratedEnd(view);
                this.mCoordinate = LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding() - n2;
                if (n2 > 0) {
                    int n3 = LinearLayoutManager.this.mOrientationHelper.getDecoratedMeasurement(view);
                    n = this.mCoordinate;
                    int n4 = LinearLayoutManager.this.mOrientationHelper.getStartAfterPadding();
                    if ((n = n - n3 - (Math.min(LinearLayoutManager.this.mOrientationHelper.getDecoratedStart(view) - n4, 0) + n4)) < 0) {
                        this.mCoordinate += Math.min(n2, -n);
                    }
                }
            } else {
                int n5 = LinearLayoutManager.this.mOrientationHelper.getDecoratedStart(view);
                int n6 = n5 - LinearLayoutManager.this.mOrientationHelper.getStartAfterPadding();
                this.mCoordinate = n5;
                if (n6 > 0) {
                    int n7 = LinearLayoutManager.this.mOrientationHelper.getDecoratedMeasurement(view);
                    int n8 = LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding();
                    int n9 = LinearLayoutManager.this.mOrientationHelper.getDecoratedEnd(view);
                    n = LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding() - Math.min(0, n8 - n - n9) - (n7 + n5);
                    if (n < 0) {
                        this.mCoordinate -= Math.min(n6, -n);
                    }
                }
            }
        }

        boolean isViewValidAsAnchor(View object, RecyclerView.State state) {
            boolean bl = !((RecyclerView.LayoutParams)(object = (RecyclerView.LayoutParams)((View)object).getLayoutParams())).isItemRemoved() && ((RecyclerView.LayoutParams)object).getViewLayoutPosition() >= 0 && ((RecyclerView.LayoutParams)object).getViewLayoutPosition() < state.getItemCount();
            return bl;
        }

        void reset() {
            this.mPosition = -1;
            this.mCoordinate = Integer.MIN_VALUE;
            this.mLayoutFromEnd = false;
            this.mValid = false;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AnchorInfo{mPosition=");
            stringBuilder.append(this.mPosition);
            stringBuilder.append(", mCoordinate=");
            stringBuilder.append(this.mCoordinate);
            stringBuilder.append(", mLayoutFromEnd=");
            stringBuilder.append(this.mLayoutFromEnd);
            stringBuilder.append(", mValid=");
            stringBuilder.append(this.mValid);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

    protected static class LayoutChunkResult {
        public int mConsumed;
        public boolean mFinished;
        public boolean mFocusable;
        public boolean mIgnoreConsumed;

        protected LayoutChunkResult() {
        }

        void resetInternal() {
            this.mConsumed = 0;
            this.mFinished = false;
            this.mIgnoreConsumed = false;
            this.mFocusable = false;
        }
    }

    static class LayoutState {
        static final int INVALID_LAYOUT = Integer.MIN_VALUE;
        static final int ITEM_DIRECTION_HEAD = -1;
        static final int ITEM_DIRECTION_TAIL = 1;
        static final int LAYOUT_END = 1;
        static final int LAYOUT_START = -1;
        static final int SCROLLING_OFFSET_NaN = Integer.MIN_VALUE;
        static final String TAG = "LLM#LayoutState";
        int mAvailable;
        int mCurrentPosition;
        int mExtra = 0;
        boolean mInfinite;
        boolean mIsPreLayout = false;
        int mItemDirection;
        int mLastScrollDelta;
        int mLayoutDirection;
        int mOffset;
        boolean mRecycle = true;
        List<RecyclerView.ViewHolder> mScrapList = null;
        int mScrollingOffset;

        LayoutState() {
        }

        private View nextViewFromScrapList() {
            int n = this.mScrapList.size();
            for (int i = 0; i < n; ++i) {
                View view = this.mScrapList.get((int)i).itemView;
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
                if (layoutParams.isItemRemoved() || this.mCurrentPosition != layoutParams.getViewLayoutPosition()) continue;
                this.assignPositionFromScrapList(view);
                return view;
            }
            return null;
        }

        public void assignPositionFromScrapList() {
            this.assignPositionFromScrapList(null);
        }

        public void assignPositionFromScrapList(View view) {
            this.mCurrentPosition = (view = this.nextViewInLimitedList(view)) == null ? -1 : ((RecyclerView.LayoutParams)view.getLayoutParams()).getViewLayoutPosition();
        }

        boolean hasMore(RecyclerView.State state) {
            int n = this.mCurrentPosition;
            boolean bl = n >= 0 && n < state.getItemCount();
            return bl;
        }

        void log() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("avail:");
            stringBuilder.append(this.mAvailable);
            stringBuilder.append(", ind:");
            stringBuilder.append(this.mCurrentPosition);
            stringBuilder.append(", dir:");
            stringBuilder.append(this.mItemDirection);
            stringBuilder.append(", offset:");
            stringBuilder.append(this.mOffset);
            stringBuilder.append(", layoutDir:");
            stringBuilder.append(this.mLayoutDirection);
            Log.d(TAG, stringBuilder.toString());
        }

        View next(RecyclerView.Recycler object) {
            if (this.mScrapList != null) {
                return this.nextViewFromScrapList();
            }
            object = ((RecyclerView.Recycler)object).getViewForPosition(this.mCurrentPosition);
            this.mCurrentPosition += this.mItemDirection;
            return object;
        }

        public View nextViewInLimitedList(View view) {
            View view2;
            int n = this.mScrapList.size();
            View view3 = null;
            int n2 = Integer.MAX_VALUE;
            int n3 = 0;
            do {
                view2 = view3;
                if (n3 >= n) break;
                View view4 = this.mScrapList.get((int)n3).itemView;
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view4.getLayoutParams();
                view2 = view3;
                int n4 = n2;
                if (view4 != view) {
                    if (layoutParams.isItemRemoved()) {
                        view2 = view3;
                        n4 = n2;
                    } else {
                        int n5 = (layoutParams.getViewLayoutPosition() - this.mCurrentPosition) * this.mItemDirection;
                        if (n5 < 0) {
                            view2 = view3;
                            n4 = n2;
                        } else {
                            view2 = view3;
                            n4 = n2;
                            if (n5 < n2) {
                                view3 = view4;
                                n4 = n5;
                                view2 = view3;
                                if (n5 == 0) {
                                    view2 = view3;
                                    break;
                                }
                            }
                        }
                    }
                }
                ++n3;
                view3 = view2;
                n2 = n4;
            } while (true);
            return view2;
        }
    }

    public static class SavedState
    implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        boolean mAnchorLayoutFromEnd;
        int mAnchorOffset;
        int mAnchorPosition;

        public SavedState() {
        }

        SavedState(Parcel parcel) {
            this.mAnchorPosition = parcel.readInt();
            this.mAnchorOffset = parcel.readInt();
            int n = parcel.readInt();
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            this.mAnchorLayoutFromEnd = bl;
        }

        public SavedState(SavedState savedState) {
            this.mAnchorPosition = savedState.mAnchorPosition;
            this.mAnchorOffset = savedState.mAnchorOffset;
            this.mAnchorLayoutFromEnd = savedState.mAnchorLayoutFromEnd;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        boolean hasValidAnchor() {
            boolean bl = this.mAnchorPosition >= 0;
            return bl;
        }

        void invalidateAnchor() {
            this.mAnchorPosition = -1;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mAnchorPosition);
            parcel.writeInt(this.mAnchorOffset);
            parcel.writeInt((int)this.mAnchorLayoutFromEnd);
        }

    }

}

