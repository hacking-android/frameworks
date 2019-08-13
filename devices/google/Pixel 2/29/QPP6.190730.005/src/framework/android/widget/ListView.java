/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.util.SparseBooleanArray;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.RemotableViewMethod;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.ViewParent;
import android.view.ViewRootImpl;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Checkable;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.RemoteViews;
import com.android.internal.R;
import com.google.android.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@RemoteViews.RemoteView
public class ListView
extends AbsListView {
    private static final float MAX_SCROLL_FACTOR = 0.33f;
    private static final int MIN_SCROLL_PREVIEW_PIXELS = 2;
    static final int NO_POSITION = -1;
    static final String TAG = "ListView";
    @UnsupportedAppUsage
    private boolean mAreAllItemsSelectable = true;
    private final ArrowScrollFocusResult mArrowScrollFocusResult = new ArrowScrollFocusResult();
    @UnsupportedAppUsage
    Drawable mDivider;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    int mDividerHeight;
    private boolean mDividerIsOpaque;
    private Paint mDividerPaint;
    private FocusSelector mFocusSelector;
    private boolean mFooterDividersEnabled;
    @UnsupportedAppUsage
    ArrayList<FixedViewInfo> mFooterViewInfos = Lists.newArrayList();
    private boolean mHeaderDividersEnabled;
    @UnsupportedAppUsage
    ArrayList<FixedViewInfo> mHeaderViewInfos = Lists.newArrayList();
    private boolean mIsCacheColorOpaque;
    private boolean mItemsCanFocus = false;
    Drawable mOverScrollFooter;
    Drawable mOverScrollHeader;
    private final Rect mTempRect = new Rect();

    public ListView(Context context) {
        this(context, null);
    }

    public ListView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842868);
    }

    public ListView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ListView(Context object, AttributeSet arrcharSequence, int n, int n2) {
        super((Context)object, (AttributeSet)arrcharSequence, n, n2);
        TypedArray typedArray = ((Context)object).obtainStyledAttributes((AttributeSet)arrcharSequence, R.styleable.ListView, n, n2);
        this.saveAttributeDataForStyleable((Context)object, R.styleable.ListView, (AttributeSet)arrcharSequence, typedArray, n, n2);
        arrcharSequence = typedArray.getTextArray(0);
        if (arrcharSequence != null) {
            this.setAdapter(new ArrayAdapter<CharSequence>((Context)object, 17367043, arrcharSequence));
        }
        if ((object = typedArray.getDrawable(1)) != null) {
            this.setDivider((Drawable)object);
        }
        if ((object = typedArray.getDrawable(5)) != null) {
            this.setOverscrollHeader((Drawable)object);
        }
        if ((object = typedArray.getDrawable(6)) != null) {
            this.setOverscrollFooter((Drawable)object);
        }
        if (typedArray.hasValueOrEmpty(2) && (n = typedArray.getDimensionPixelSize(2, 0)) != 0) {
            this.setDividerHeight(n);
        }
        this.mHeaderDividersEnabled = typedArray.getBoolean(3, true);
        this.mFooterDividersEnabled = typedArray.getBoolean(4, true);
        typedArray.recycle();
    }

    private View addViewAbove(View view, int n) {
        View view2 = this.obtainView(--n, this.mIsScrap);
        this.setupChild(view2, n, view.getTop() - this.mDividerHeight, false, this.mListPadding.left, false, this.mIsScrap[0]);
        return view2;
    }

    private View addViewBelow(View view, int n) {
        View view2 = this.obtainView(++n, this.mIsScrap);
        this.setupChild(view2, n, view.getBottom() + this.mDividerHeight, true, this.mListPadding.left, false, this.mIsScrap[0]);
        return view2;
    }

    private void adjustViewsUpOrDown() {
        int n = this.getChildCount();
        if (n > 0) {
            int n2;
            if (!this.mStackFromBottom) {
                int n3 = n2 = this.getChildAt(0).getTop() - this.mListPadding.top;
                if (this.mFirstPosition != 0) {
                    n3 = n2 - this.mDividerHeight;
                }
                n2 = n3;
                if (n3 < 0) {
                    n2 = 0;
                }
            } else {
                int n4 = n2 = this.getChildAt(n - 1).getBottom() - (this.getHeight() - this.mListPadding.bottom);
                if (this.mFirstPosition + n < this.mItemCount) {
                    n4 = n2 + this.mDividerHeight;
                }
                n2 = n4;
                if (n4 > 0) {
                    n2 = 0;
                }
            }
            if (n2 != 0) {
                this.offsetChildrenTopAndBottom(-n2);
            }
        }
    }

    private int amountToScroll(int n, int n2) {
        int n3;
        int n4 = this.getHeight() - this.mListPadding.bottom;
        int n5 = this.mListPadding.top;
        int n6 = this.getChildCount();
        if (n == 130) {
            int n7 = n6 - 1;
            n = n6;
            if (n2 != -1) {
                n7 = n2 - this.mFirstPosition;
                n = n6;
            }
            while (n <= n7) {
                this.addViewBelow(this.getChildAt(n - 1), this.mFirstPosition + n - 1);
                ++n;
            }
            int n8 = this.mFirstPosition;
            View view = this.getChildAt(n7);
            n5 = n6 = n4;
            if (n8 + n7 < this.mItemCount - 1) {
                n5 = n6 - this.getArrowScrollPreviewLength();
            }
            if (view.getBottom() <= n5) {
                return 0;
            }
            if (n2 != -1 && n5 - view.getTop() >= this.getMaxScrollAmount()) {
                return 0;
            }
            n2 = n7 = view.getBottom() - n5;
            if (this.mFirstPosition + n == this.mItemCount) {
                n2 = Math.min(n7, this.getChildAt(n - 1).getBottom() - n4);
            }
            return Math.min(n2, this.getMaxScrollAmount());
        }
        n = 0;
        if (n2 != -1) {
            n = n2 - this.mFirstPosition;
        }
        while (n < 0) {
            this.addViewAbove(this.getChildAt(0), this.mFirstPosition);
            --this.mFirstPosition;
            n = n2 - this.mFirstPosition;
        }
        n4 = this.mFirstPosition;
        View view = this.getChildAt(n);
        n6 = n3 = n5;
        if (n4 + n > 0) {
            n6 = n3 + this.getArrowScrollPreviewLength();
        }
        if (view.getTop() >= n6) {
            return 0;
        }
        if (n2 != -1 && view.getBottom() - n6 >= this.getMaxScrollAmount()) {
            return 0;
        }
        n = n2 = n6 - view.getTop();
        if (this.mFirstPosition == 0) {
            n = Math.min(n2, n5 - this.getChildAt(0).getTop());
        }
        return Math.min(n, this.getMaxScrollAmount());
    }

    private int amountToScrollToNewFocus(int n, View view, int n2) {
        int n3 = 0;
        view.getDrawingRect(this.mTempRect);
        this.offsetDescendantRectToMyCoords(view, this.mTempRect);
        if (n == 33) {
            n = n3;
            if (this.mTempRect.top < this.mListPadding.top) {
                n = n3 = this.mListPadding.top - this.mTempRect.top;
                if (n2 > 0) {
                    n = n3 + this.getArrowScrollPreviewLength();
                }
            }
        } else {
            int n4 = this.getHeight() - this.mListPadding.bottom;
            n = n3;
            if (this.mTempRect.bottom > n4) {
                n = n3 = this.mTempRect.bottom - n4;
                if (n2 < this.mItemCount - 1) {
                    n = n3 + this.getArrowScrollPreviewLength();
                }
            }
        }
        return n;
    }

    private ArrowScrollFocusResult arrowScrollFocused(int n) {
        int n2;
        int n3;
        int n4;
        View view = this.getSelectedView();
        if (view != null && view.hasFocus()) {
            view = view.findFocus();
            view = FocusFinder.getInstance().findNextFocus(this, view, n);
        } else {
            n3 = 1;
            n4 = 1;
            if (n == 130) {
                if (this.mFirstPosition <= 0) {
                    n4 = 0;
                }
                n3 = this.mListPadding.top;
                n4 = n4 != 0 ? this.getArrowScrollPreviewLength() : 0;
                n4 = n3 + n4;
                if (view != null && view.getTop() > n4) {
                    n4 = view.getTop();
                }
                this.mTempRect.set(0, n4, 0, n4);
            } else {
                n4 = this.mFirstPosition + this.getChildCount() - 1 < this.mItemCount ? n3 : 0;
                n3 = this.getHeight();
                n2 = this.mListPadding.bottom;
                n4 = n4 != 0 ? this.getArrowScrollPreviewLength() : 0;
                n4 = n3 - n2 - n4;
                if (view != null && view.getBottom() < n4) {
                    n4 = view.getBottom();
                }
                this.mTempRect.set(0, n4, 0, n4);
            }
            view = FocusFinder.getInstance().findNextFocusFromRect(this, this.mTempRect, n);
        }
        if (view != null) {
            n4 = this.positionOfNewFocus(view);
            if (this.mSelectedPosition != -1 && n4 != this.mSelectedPosition && (n3 = this.lookForSelectablePositionOnScreen(n)) != -1 && (n == 130 && n3 < n4 || n == 33 && n3 > n4)) {
                return null;
            }
            n2 = this.amountToScrollToNewFocus(n, view, n4);
            if (n2 < (n3 = this.getMaxScrollAmount())) {
                view.requestFocus(n);
                this.mArrowScrollFocusResult.populate(n4, n2);
                return this.mArrowScrollFocusResult;
            }
            if (this.distanceToView(view) < n3) {
                view.requestFocus(n);
                this.mArrowScrollFocusResult.populate(n4, n3);
                return this.mArrowScrollFocusResult;
            }
        }
        return null;
    }

    private boolean arrowScrollImpl(int n) {
        if (this.getChildCount() <= 0) {
            return false;
        }
        View view = this.getSelectedView();
        int n2 = this.mSelectedPosition;
        int n3 = this.nextSelectedPositionForDirection(view, n2, n);
        int n4 = this.amountToScroll(n, n3);
        Object object = this.mItemsCanFocus ? this.arrowScrollFocused(n) : null;
        if (object != null) {
            n3 = ((ArrowScrollFocusResult)object).getSelectedPosition();
            n4 = ((ArrowScrollFocusResult)object).getAmountToScroll();
        }
        boolean bl = object != null;
        View view2 = view;
        if (n3 != -1) {
            boolean bl2 = object != null;
            this.handleNewSelectionChange(view, n, n3, bl2);
            this.setSelectedPositionInt(n3);
            this.setNextSelectedPositionInt(n3);
            view2 = this.getSelectedView();
            n2 = n3;
            if (this.mItemsCanFocus && object == null && (view = this.getFocusedChild()) != null) {
                view.clearFocus();
            }
            bl = true;
            this.checkSelectionChanged();
        }
        if (n4 > 0) {
            n = n == 33 ? n4 : -n4;
            this.scrollListItemsBy(n);
            bl = true;
        }
        if (this.mItemsCanFocus && object == null && view2 != null && view2.hasFocus() && (object = view2.findFocus()) != null && (!this.isViewAncestorOf((View)object, this) || this.distanceToView((View)object) > 0)) {
            ((View)object).clearFocus();
        }
        object = view2;
        if (n3 == -1) {
            object = view2;
            if (view2 != null) {
                object = view2;
                if (!this.isViewAncestorOf(view2, this)) {
                    object = null;
                    this.hideSelector();
                    this.mResurrectToPosition = -1;
                }
            }
        }
        if (bl) {
            if (object != null) {
                this.positionSelectorLikeFocus(n2, (View)object);
                this.mSelectedTop = ((View)object).getTop();
            }
            if (!this.awakenScrollBars()) {
                this.invalidate();
            }
            this.invokeOnItemScrollListener();
            return true;
        }
        return false;
    }

    private void clearRecycledState(ArrayList<FixedViewInfo> arrayList) {
        if (arrayList != null) {
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                ViewGroup.LayoutParams layoutParams = arrayList.get((int)i).view.getLayoutParams();
                if (!this.checkLayoutParams(layoutParams)) continue;
                ((AbsListView.LayoutParams)layoutParams).recycledHeaderFooter = false;
            }
        }
    }

    private boolean commonKey(int n, int n2, KeyEvent keyEvent) {
        block39 : {
            boolean bl;
            int n3;
            int n4;
            block40 : {
                boolean bl2;
                block41 : {
                    block42 : {
                        block43 : {
                            block44 : {
                                block45 : {
                                    block46 : {
                                        if (this.mAdapter == null || !this.isAttachedToWindow()) break block39;
                                        if (this.mDataChanged) {
                                            this.layoutChildren();
                                        }
                                        bl = false;
                                        n3 = keyEvent.getAction();
                                        bl2 = bl;
                                        if (KeyEvent.isConfirmKey(n)) {
                                            bl2 = bl;
                                            if (keyEvent.hasNoModifiers()) {
                                                bl2 = bl;
                                                if (n3 != 1) {
                                                    bl2 = bl = this.resurrectSelectionIfNeeded();
                                                    if (!bl) {
                                                        bl2 = bl;
                                                        if (keyEvent.getRepeatCount() == 0) {
                                                            bl2 = bl;
                                                            if (this.getChildCount() > 0) {
                                                                this.keyPressed();
                                                                bl2 = true;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        bl = bl2;
                                        n4 = n2;
                                        if (bl2) break block40;
                                        bl = bl2;
                                        n4 = n2;
                                        if (n3 == 1) break block40;
                                        if (n == 61) break block41;
                                        if (n == 92) break block42;
                                        if (n == 93) break block43;
                                        if (n == 122) break block44;
                                        if (n == 123) break block45;
                                        block0 : switch (n) {
                                            default: {
                                                bl = bl2;
                                                n4 = n2;
                                                break block40;
                                            }
                                            case 22: {
                                                bl = bl2;
                                                n4 = n2;
                                                if (keyEvent.hasNoModifiers()) {
                                                    bl = this.handleHorizontalFocusWithinListItem(66);
                                                    n4 = n2;
                                                }
                                                break block40;
                                            }
                                            case 21: {
                                                bl = bl2;
                                                n4 = n2;
                                                if (keyEvent.hasNoModifiers()) {
                                                    bl = this.handleHorizontalFocusWithinListItem(17);
                                                    n4 = n2;
                                                }
                                                break block40;
                                            }
                                            case 20: {
                                                if (keyEvent.hasNoModifiers()) {
                                                    bl = bl2 = this.resurrectSelectionIfNeeded();
                                                    n4 = n2;
                                                    if (!bl2) {
                                                        int n5 = n2;
                                                        do {
                                                            n4 = n5 - 1;
                                                            bl = bl2;
                                                            n2 = n4;
                                                            if (n5 <= 0) break block0;
                                                            bl = bl2;
                                                            n2 = n4;
                                                            if (!this.arrowScroll(130)) break block0;
                                                            bl2 = true;
                                                            n5 = n4;
                                                        } while (true);
                                                    }
                                                } else {
                                                    bl = bl2;
                                                    n4 = n2;
                                                    if (keyEvent.hasModifiers(2)) {
                                                        bl = this.resurrectSelectionIfNeeded() || this.fullScroll(130);
                                                        n4 = n2;
                                                    }
                                                }
                                                break block40;
                                            }
                                            case 19: {
                                                if (!keyEvent.hasNoModifiers()) break block46;
                                                bl = bl2 = this.resurrectSelectionIfNeeded();
                                                n4 = n2;
                                                if (!bl2) {
                                                    int n6 = n2;
                                                    do {
                                                        n4 = n6 - 1;
                                                        bl = bl2;
                                                        n2 = n4;
                                                        if (n6 <= 0) break block0;
                                                        bl = bl2;
                                                        n2 = n4;
                                                        if (!this.arrowScroll(33)) break block0;
                                                        bl2 = true;
                                                        n6 = n4;
                                                    } while (true);
                                                }
                                                break block40;
                                            }
                                        }
                                        n4 = n2;
                                        break block40;
                                    }
                                    bl = bl2;
                                    n4 = n2;
                                    if (keyEvent.hasModifiers(2)) {
                                        bl = this.resurrectSelectionIfNeeded() || this.fullScroll(33);
                                        n4 = n2;
                                    }
                                    break block40;
                                }
                                bl = bl2;
                                n4 = n2;
                                if (keyEvent.hasNoModifiers()) {
                                    bl = this.resurrectSelectionIfNeeded() || this.fullScroll(130);
                                    n4 = n2;
                                }
                                break block40;
                            }
                            bl = bl2;
                            n4 = n2;
                            if (keyEvent.hasNoModifiers()) {
                                bl = this.resurrectSelectionIfNeeded() || this.fullScroll(33);
                                n4 = n2;
                            }
                            break block40;
                        }
                        if (keyEvent.hasNoModifiers()) {
                            bl = this.resurrectSelectionIfNeeded() || this.pageScroll(130);
                            n4 = n2;
                        } else {
                            bl = bl2;
                            n4 = n2;
                            if (keyEvent.hasModifiers(2)) {
                                bl = this.resurrectSelectionIfNeeded() || this.fullScroll(130);
                                n4 = n2;
                            }
                        }
                        break block40;
                    }
                    if (keyEvent.hasNoModifiers()) {
                        bl = this.resurrectSelectionIfNeeded() || this.pageScroll(33);
                        n4 = n2;
                    } else {
                        bl = bl2;
                        n4 = n2;
                        if (keyEvent.hasModifiers(2)) {
                            bl = this.resurrectSelectionIfNeeded() || this.fullScroll(33);
                            n4 = n2;
                        }
                    }
                    break block40;
                }
                if (keyEvent.hasNoModifiers()) {
                    bl = this.resurrectSelectionIfNeeded() || this.arrowScroll(130);
                    n4 = n2;
                } else {
                    bl = bl2;
                    n4 = n2;
                    if (keyEvent.hasModifiers(1)) {
                        bl = this.resurrectSelectionIfNeeded() || this.arrowScroll(33);
                        n4 = n2;
                    }
                }
            }
            if (bl) {
                return true;
            }
            if (this.sendToTextFilter(n, n4, keyEvent)) {
                return true;
            }
            if (n3 != 0) {
                if (n3 != 1) {
                    if (n3 != 2) {
                        return false;
                    }
                    return super.onKeyMultiple(n, n4, keyEvent);
                }
                return super.onKeyUp(n, keyEvent);
            }
            return super.onKeyDown(n, keyEvent);
        }
        return false;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private void correctTooHigh(int n) {
        if (this.mFirstPosition + n - 1 == this.mItemCount - 1 && n > 0) {
            n = this.getChildAt(n - 1).getBottom();
            int n2 = this.mBottom - this.mTop - this.mListPadding.bottom - n;
            View view = this.getChildAt(0);
            int n3 = view.getTop();
            if (n2 > 0 && (this.mFirstPosition > 0 || n3 < this.mListPadding.top)) {
                n = n2;
                if (this.mFirstPosition == 0) {
                    n = Math.min(n2, this.mListPadding.top - n3);
                }
                this.offsetChildrenTopAndBottom(n);
                if (this.mFirstPosition > 0) {
                    this.fillUp(this.mFirstPosition - 1, view.getTop() - this.mDividerHeight);
                    this.adjustViewsUpOrDown();
                }
            }
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private void correctTooLow(int n) {
        if (this.mFirstPosition == 0 && n > 0) {
            int n2 = this.getChildAt(0).getTop();
            int n3 = this.mListPadding.top;
            int n4 = this.mBottom - this.mTop - this.mListPadding.bottom;
            n2 -= n3;
            View view = this.getChildAt(n - 1);
            int n5 = view.getBottom();
            n3 = this.mFirstPosition + n - 1;
            if (n2 > 0) {
                if (n3 >= this.mItemCount - 1 && n5 <= n4) {
                    if (n3 == this.mItemCount - 1) {
                        this.adjustViewsUpOrDown();
                    }
                } else {
                    n = n2;
                    if (n3 == this.mItemCount - 1) {
                        n = Math.min(n2, n5 - n4);
                    }
                    this.offsetChildrenTopAndBottom(-n);
                    if (n3 < this.mItemCount - 1) {
                        this.fillDown(n3 + 1, view.getBottom() + this.mDividerHeight);
                        this.adjustViewsUpOrDown();
                    }
                }
            }
        }
    }

    private int distanceToView(View view) {
        int n = 0;
        view.getDrawingRect(this.mTempRect);
        this.offsetDescendantRectToMyCoords(view, this.mTempRect);
        int n2 = this.mBottom - this.mTop - this.mListPadding.bottom;
        if (this.mTempRect.bottom < this.mListPadding.top) {
            n = this.mListPadding.top - this.mTempRect.bottom;
        } else if (this.mTempRect.top > n2) {
            n = this.mTempRect.top - n2;
        }
        return n;
    }

    private void fillAboveAndBelow(View view, int n) {
        int n2 = this.mDividerHeight;
        if (!this.mStackFromBottom) {
            this.fillUp(n - 1, view.getTop() - n2);
            this.adjustViewsUpOrDown();
            this.fillDown(n + 1, view.getBottom() + n2);
        } else {
            this.fillDown(n + 1, view.getBottom() + n2);
            this.adjustViewsUpOrDown();
            this.fillUp(n - 1, view.getTop() - n2);
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    private View fillDown(int n, int n2) {
        View view = null;
        int n3 = this.mBottom - this.mTop;
        View view2 = view;
        int n4 = n3;
        int n5 = n;
        int n6 = n2;
        if ((this.mGroupFlags & 34) == 34) {
            n4 = n3 - this.mListPadding.bottom;
            n6 = n2;
            n5 = n;
            view2 = view;
        }
        do {
            boolean bl = true;
            if (n6 >= n4 || n5 >= this.mItemCount) break;
            if (n5 != this.mSelectedPosition) {
                bl = false;
            }
            view = this.makeAndAddView(n5, n6, true, this.mListPadding.left, bl);
            n6 = view.getBottom() + this.mDividerHeight;
            if (bl) {
                view2 = view;
            }
            ++n5;
        } while (true);
        this.setVisibleRangeHint(this.mFirstPosition, this.mFirstPosition + this.getChildCount() - 1);
        return view2;
    }

    private View fillFromMiddle(int n, int n2) {
        int n3 = n2 - n;
        n2 = this.reconcileSelectedPosition();
        View view = this.makeAndAddView(n2, n, true, this.mListPadding.left, true);
        this.mFirstPosition = n2;
        n = view.getMeasuredHeight();
        if (n <= n3) {
            view.offsetTopAndBottom((n3 - n) / 2);
        }
        this.fillAboveAndBelow(view, n2);
        if (!this.mStackFromBottom) {
            this.correctTooHigh(this.getChildCount());
        } else {
            this.correctTooLow(this.getChildCount());
        }
        return view;
    }

    private View fillFromSelection(int n, int n2, int n3) {
        int n4 = this.getVerticalFadingEdgeLength();
        int n5 = this.mSelectedPosition;
        n2 = this.getTopSelectionPixel(n2, n4, n5);
        n3 = this.getBottomSelectionPixel(n3, n4, n5);
        View view = this.makeAndAddView(n5, n, true, this.mListPadding.left, true);
        if (view.getBottom() > n3) {
            view.offsetTopAndBottom(-Math.min(view.getTop() - n2, view.getBottom() - n3));
        } else if (view.getTop() < n2) {
            view.offsetTopAndBottom(Math.min(n2 - view.getTop(), n3 - view.getBottom()));
        }
        this.fillAboveAndBelow(view, n5);
        if (!this.mStackFromBottom) {
            this.correctTooHigh(this.getChildCount());
        } else {
            this.correctTooLow(this.getChildCount());
        }
        return view;
    }

    private View fillFromTop(int n) {
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mSelectedPosition);
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mItemCount - 1);
        if (this.mFirstPosition < 0) {
            this.mFirstPosition = 0;
        }
        return this.fillDown(this.mFirstPosition, n);
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    private View fillSpecific(int n, int n2) {
        View view;
        View view2;
        boolean bl = n == this.mSelectedPosition;
        View view3 = this.makeAndAddView(n, n2, true, this.mListPadding.left, bl);
        this.mFirstPosition = n;
        n2 = this.mDividerHeight;
        if (!this.mStackFromBottom) {
            view2 = this.fillUp(n - 1, view3.getTop() - n2);
            this.adjustViewsUpOrDown();
            view = this.fillDown(n + 1, view3.getBottom() + n2);
            n = this.getChildCount();
            if (n > 0) {
                this.correctTooHigh(n);
            }
        } else {
            View view4 = this.fillDown(n + 1, view3.getBottom() + n2);
            this.adjustViewsUpOrDown();
            View view5 = this.fillUp(n - 1, view3.getTop() - n2);
            n = this.getChildCount();
            view2 = view5;
            view = view4;
            if (n > 0) {
                this.correctTooLow(n);
                view = view4;
                view2 = view5;
            }
        }
        if (bl) {
            return view3;
        }
        if (view2 != null) {
            return view2;
        }
        return view;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    private View fillUp(int n, int n2) {
        View view = null;
        int n3 = 0;
        View view2 = view;
        int n4 = n;
        int n5 = n2;
        if ((this.mGroupFlags & 34) == 34) {
            n3 = this.mListPadding.top;
            n5 = n2;
            n4 = n;
            view2 = view;
        }
        do {
            boolean bl = true;
            if (n5 <= n3 || n4 < 0) break;
            if (n4 != this.mSelectedPosition) {
                bl = false;
            }
            view = this.makeAndAddView(n4, n5, false, this.mListPadding.left, bl);
            n5 = view.getTop() - this.mDividerHeight;
            if (bl) {
                view2 = view;
            }
            --n4;
        } while (true);
        this.mFirstPosition = n4 + 1;
        this.setVisibleRangeHint(this.mFirstPosition, this.mFirstPosition + this.getChildCount() - 1);
        return view2;
    }

    private int getArrowScrollPreviewLength() {
        return Math.max(2, this.getVerticalFadingEdgeLength());
    }

    private int getBottomSelectionPixel(int n, int n2, int n3) {
        int n4 = n;
        if (n3 != this.mItemCount - 1) {
            n4 = n - n2;
        }
        return n4;
    }

    private int getTopSelectionPixel(int n, int n2, int n3) {
        int n4;
        n = n4 = n;
        if (n3 > 0) {
            n = n4 + n2;
        }
        return n;
    }

    private boolean handleHorizontalFocusWithinListItem(int n) {
        Object object;
        if (n != 17 && n != 66) {
            throw new IllegalArgumentException("direction must be one of {View.FOCUS_LEFT, View.FOCUS_RIGHT}");
        }
        int n2 = this.getChildCount();
        if (this.mItemsCanFocus && n2 > 0 && this.mSelectedPosition != -1 && (object = this.getSelectedView()) != null && ((View)object).hasFocus() && object instanceof ViewGroup) {
            View view = ((View)object).findFocus();
            View view2 = FocusFinder.getInstance().findNextFocus((ViewGroup)object, view, n);
            if (view2 != null) {
                object = this.mTempRect;
                if (view != null) {
                    view.getFocusedRect((Rect)object);
                    this.offsetDescendantRectToMyCoords(view, (Rect)object);
                    this.offsetRectIntoDescendantCoords(view2, (Rect)object);
                } else {
                    object = null;
                }
                if (view2.requestFocus(n, (Rect)object)) {
                    return true;
                }
            }
            if ((object = FocusFinder.getInstance().findNextFocus((ViewGroup)this.getRootView(), view, n)) != null) {
                return this.isViewAncestorOf((View)object, this);
            }
        }
        return false;
    }

    private void handleNewSelectionChange(View view, int n, int n2, boolean bl) {
        if (n2 != -1) {
            View view2;
            int n3 = 0;
            int n4 = this.mSelectedPosition - this.mFirstPosition;
            n2 -= this.mFirstPosition;
            if (n == 33) {
                n3 = n4;
                view2 = this.getChildAt(n2);
                n = 1;
                n4 = n2;
                n2 = n3;
            } else {
                view2 = view;
                view = this.getChildAt(n2);
                n = n3;
            }
            n3 = this.getChildCount();
            boolean bl2 = true;
            if (view2 != null) {
                boolean bl3 = !bl && n != 0;
                view2.setSelected(bl3);
                this.measureAndAdjustDown(view2, n4, n3);
            }
            if (view != null) {
                bl = !bl && n == 0 ? bl2 : false;
                view.setSelected(bl);
                this.measureAndAdjustDown(view, n2, n3);
            }
            return;
        }
        throw new IllegalArgumentException("newSelectedPosition needs to be valid");
    }

    @UnsupportedAppUsage
    private boolean isDirectChildHeaderOrFooter(View view) {
        int n;
        ArrayList<FixedViewInfo> arrayList = this.mHeaderViewInfos;
        int n2 = arrayList.size();
        for (n = 0; n < n2; ++n) {
            if (view != arrayList.get((int)n).view) continue;
            return true;
        }
        arrayList = this.mFooterViewInfos;
        n2 = arrayList.size();
        for (n = 0; n < n2; ++n) {
            if (view != arrayList.get((int)n).view) continue;
            return true;
        }
        return false;
    }

    private boolean isViewAncestorOf(View object, View view) {
        boolean bl = true;
        if (object == view) {
            return true;
        }
        if (!((object = ((View)object).getParent()) instanceof ViewGroup) || !this.isViewAncestorOf((View)object, view)) {
            bl = false;
        }
        return bl;
    }

    private int lookForSelectablePositionOnScreen(int n) {
        block10 : {
            block9 : {
                int n2;
                block8 : {
                    n2 = this.mFirstPosition;
                    if (n != 130) break block8;
                    int n3 = this.mSelectedPosition != -1 ? this.mSelectedPosition + 1 : n2;
                    if (n3 >= this.mAdapter.getCount()) {
                        return -1;
                    }
                    n = n3;
                    if (n3 < n2) {
                        n = n2;
                    }
                    n3 = this.getLastVisiblePosition();
                    Adapter adapter = this.getAdapter();
                    while (n <= n3) {
                        if (adapter.isEnabled(n) && this.getChildAt(n - n2).getVisibility() == 0) {
                            return n;
                        }
                        ++n;
                    }
                    break block9;
                }
                int n4 = this.getChildCount() + n2 - 1;
                n = this.mSelectedPosition != -1 ? this.mSelectedPosition - 1 : this.getChildCount() + n2 - 1;
                if (n >= 0 && n < this.mAdapter.getCount()) {
                    int n5 = n;
                    if (n > n4) {
                        n5 = n4;
                    }
                    Adapter adapter = this.getAdapter();
                    while (n5 >= n2) {
                        if (adapter.isEnabled(n5) && this.getChildAt(n5 - n2).getVisibility() == 0) {
                            return n5;
                        }
                        --n5;
                    }
                }
                break block10;
            }
            return -1;
        }
        return -1;
    }

    @UnsupportedAppUsage
    private View makeAndAddView(int n, int n2, boolean bl, int n3, boolean bl2) {
        View view;
        if (!this.mDataChanged && (view = this.mRecycler.getActiveView(n)) != null) {
            this.setupChild(view, n, n2, bl, n3, bl2, true);
            return view;
        }
        view = this.obtainView(n, this.mIsScrap);
        this.setupChild(view, n, n2, bl, n3, bl2, this.mIsScrap[0]);
        return view;
    }

    private void measureAndAdjustDown(View view, int n, int n2) {
        int n3 = view.getHeight();
        this.measureItem(view);
        if (view.getMeasuredHeight() != n3) {
            this.relayoutMeasuredItem(view);
            int n4 = view.getMeasuredHeight();
            ++n;
            while (n < n2) {
                this.getChildAt(n).offsetTopAndBottom(n4 - n3);
                ++n;
            }
        }
    }

    private void measureItem(View view) {
        ViewGroup.LayoutParams layoutParams;
        ViewGroup.LayoutParams layoutParams2 = layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams2 = new ViewGroup.LayoutParams(-1, -2);
        }
        int n = ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, this.mListPadding.left + this.mListPadding.right, layoutParams2.width);
        int n2 = layoutParams2.height;
        n2 = n2 > 0 ? View.MeasureSpec.makeMeasureSpec(n2, 1073741824) : View.MeasureSpec.makeSafeMeasureSpec(this.getMeasuredHeight(), 0);
        view.measure(n, n2);
    }

    private void measureScrapChild(View view, int n, int n2, int n3) {
        AbsListView.LayoutParams layoutParams;
        AbsListView.LayoutParams layoutParams2 = layoutParams = (AbsListView.LayoutParams)view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams2 = (AbsListView.LayoutParams)this.generateDefaultLayoutParams();
            view.setLayoutParams(layoutParams2);
        }
        layoutParams2.viewType = this.mAdapter.getItemViewType(n);
        layoutParams2.isEnabled = this.mAdapter.isEnabled(n);
        layoutParams2.forceAdd = true;
        n2 = ViewGroup.getChildMeasureSpec(n2, this.mListPadding.left + this.mListPadding.right, layoutParams2.width);
        n = layoutParams2.height;
        n = n > 0 ? View.MeasureSpec.makeMeasureSpec(n, 1073741824) : View.MeasureSpec.makeSafeMeasureSpec(n3, 0);
        view.measure(n2, n);
        view.forceLayout();
    }

    private View moveSelection(View view, View view2, int n, int n2, int n3) {
        int n4 = this.getVerticalFadingEdgeLength();
        int n5 = this.mSelectedPosition;
        int n6 = this.getTopSelectionPixel(n2, n4, n5);
        n4 = this.getBottomSelectionPixel(n2, n4, n5);
        if (n > 0) {
            view2 = this.makeAndAddView(n5 - 1, view.getTop(), true, this.mListPadding.left, false);
            n = this.mDividerHeight;
            view = this.makeAndAddView(n5, view2.getBottom() + n, true, this.mListPadding.left, true);
            if (view.getBottom() > n4) {
                n5 = view.getTop();
                int n7 = view.getBottom();
                n2 = (n3 - n2) / 2;
                n2 = Math.min(Math.min(n5 - n6, n7 - n4), n2);
                view2.offsetTopAndBottom(-n2);
                view.offsetTopAndBottom(-n2);
            }
            if (!this.mStackFromBottom) {
                this.fillUp(this.mSelectedPosition - 2, view.getTop() - n);
                this.adjustViewsUpOrDown();
                this.fillDown(this.mSelectedPosition + 1, view.getBottom() + n);
            } else {
                this.fillDown(this.mSelectedPosition + 1, view.getBottom() + n);
                this.adjustViewsUpOrDown();
                this.fillUp(this.mSelectedPosition - 2, view.getTop() - n);
            }
        } else if (n < 0) {
            view = view2 != null ? this.makeAndAddView(n5, view2.getTop(), true, this.mListPadding.left, true) : this.makeAndAddView(n5, view.getTop(), false, this.mListPadding.left, true);
            if (view.getTop() < n6) {
                n = view.getTop();
                int n8 = view.getBottom();
                n2 = (n3 - n2) / 2;
                view.offsetTopAndBottom(Math.min(Math.min(n6 - n, n4 - n8), n2));
            }
            this.fillAboveAndBelow(view, n5);
        } else {
            n = view.getTop();
            view = this.makeAndAddView(n5, n, true, this.mListPadding.left, true);
            if (n < n2 && view.getBottom() < n2 + 20) {
                view.offsetTopAndBottom(n2 - view.getTop());
            }
            this.fillAboveAndBelow(view, n5);
        }
        return view;
    }

    private final int nextSelectedPositionForDirection(View view, int n, int n2) {
        block6 : {
            boolean bl;
            block5 : {
                block3 : {
                    block4 : {
                        bl = true;
                        if (n2 != 130) break block3;
                        int n3 = this.getHeight();
                        int n4 = this.mListPadding.bottom;
                        if (view == null || view.getBottom() > n3 - n4) break block4;
                        n = n != -1 && n >= this.mFirstPosition ? ++n : this.mFirstPosition;
                        break block5;
                    }
                    return -1;
                }
                int n5 = this.mListPadding.top;
                if (view != null && view.getTop() >= n5) {
                    n5 = this.mFirstPosition + this.getChildCount() - 1;
                    n = n != -1 && n <= n5 ? --n : n5;
                }
                break block6;
            }
            if (n >= 0 && n < this.mAdapter.getCount()) {
                if (n2 != 130) {
                    bl = false;
                }
                return this.lookForSelectablePosition(n, bl);
            }
            return -1;
        }
        return -1;
    }

    private int positionOfNewFocus(View view) {
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            if (!this.isViewAncestorOf(view, this.getChildAt(i))) continue;
            return this.mFirstPosition + i;
        }
        throw new IllegalArgumentException("newFocus is not a child of any of the children of the list!");
    }

    private void relayoutMeasuredItem(View view) {
        int n = view.getMeasuredWidth();
        int n2 = view.getMeasuredHeight();
        int n3 = this.mListPadding.left;
        int n4 = view.getTop();
        view.layout(n3, n4, n3 + n, n4 + n2);
    }

    private void removeFixedViewInfo(View view, ArrayList<FixedViewInfo> arrayList) {
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            if (arrayList.get((int)i).view != view) continue;
            arrayList.remove(i);
            break;
        }
    }

    private void removeUnusedFixedViews(List<FixedViewInfo> list) {
        if (list == null) {
            return;
        }
        for (int i = list.size() - 1; i >= 0; --i) {
            View view = list.get((int)i).view;
            AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams)view.getLayoutParams();
            if (view.getParent() != null || layoutParams == null || !layoutParams.recycledHeaderFooter) continue;
            this.removeDetachedView(view, false);
            layoutParams.recycledHeaderFooter = false;
        }
    }

    @UnsupportedAppUsage
    private void scrollListItemsBy(int n) {
        this.offsetChildrenTopAndBottom(n);
        int n2 = this.getHeight() - this.mListPadding.bottom;
        int n3 = this.mListPadding.top;
        AbsListView.RecycleBin recycleBin = this.mRecycler;
        if (n < 0) {
            int n4;
            n = this.getChildCount();
            View view = this.getChildAt(n - 1);
            while (view.getBottom() < n2 && (n4 = this.mFirstPosition + n - 1) < this.mItemCount - 1) {
                view = this.addViewBelow(view, n4);
                ++n;
            }
            if (view.getBottom() < n2) {
                this.offsetChildrenTopAndBottom(n2 - view.getBottom());
            }
            view = this.getChildAt(0);
            while (view.getBottom() < n3) {
                if (recycleBin.shouldRecycleViewType(((AbsListView.LayoutParams)view.getLayoutParams()).viewType)) {
                    recycleBin.addScrapView(view, this.mFirstPosition);
                }
                this.detachViewFromParent(view);
                view = this.getChildAt(0);
                ++this.mFirstPosition;
            }
        } else {
            View view = this.getChildAt(0);
            while (view.getTop() > n3 && this.mFirstPosition > 0) {
                view = this.addViewAbove(view, this.mFirstPosition);
                --this.mFirstPosition;
            }
            if (view.getTop() > n3) {
                this.offsetChildrenTopAndBottom(n3 - view.getTop());
            }
            n = this.getChildCount() - 1;
            view = this.getChildAt(n);
            while (view.getTop() > n2) {
                if (recycleBin.shouldRecycleViewType(((AbsListView.LayoutParams)view.getLayoutParams()).viewType)) {
                    recycleBin.addScrapView(view, this.mFirstPosition + n);
                }
                this.detachViewFromParent(view);
                view = this.getChildAt(--n);
            }
        }
        recycleBin.fullyDetachScrapViews();
        this.removeUnusedFixedViews(this.mHeaderViewInfos);
        this.removeUnusedFixedViews(this.mFooterViewInfos);
    }

    private void setupChild(View view, int n, int n2, boolean bl, int n3, boolean bl2, boolean bl3) {
        AbsListView.LayoutParams layoutParams;
        Trace.traceBegin(8L, "setupListItem");
        bl2 = bl2 && this.shouldShowSelector();
        int n4 = bl2 != view.isSelected() ? 1 : 0;
        int n5 = this.mTouchMode;
        boolean bl4 = n5 > 0 && n5 < 3 && this.mMotionPosition == n;
        boolean bl5 = bl4 != view.isPressed();
        n5 = bl3 && n4 == 0 && !view.isLayoutRequested() ? 0 : 1;
        AbsListView.LayoutParams layoutParams2 = layoutParams = (AbsListView.LayoutParams)view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams2 = (AbsListView.LayoutParams)this.generateDefaultLayoutParams();
        }
        layoutParams2.viewType = this.mAdapter.getItemViewType(n);
        layoutParams2.isEnabled = this.mAdapter.isEnabled(n);
        if (n4 != 0) {
            view.setSelected(bl2);
        }
        if (bl5) {
            view.setPressed(bl4);
        }
        if (this.mChoiceMode != 0 && this.mCheckStates != null) {
            if (view instanceof Checkable) {
                ((Checkable)((Object)view)).setChecked(this.mCheckStates.get(n));
            } else if (this.getContext().getApplicationInfo().targetSdkVersion >= 11) {
                view.setActivated(this.mCheckStates.get(n));
            }
        }
        n4 = -1;
        if (bl3 && !layoutParams2.forceAdd || layoutParams2.recycledHeaderFooter && layoutParams2.viewType == -2) {
            if (!bl) {
                n4 = 0;
            }
            this.attachViewToParent(view, n4, layoutParams2);
            if (bl3 && ((AbsListView.LayoutParams)view.getLayoutParams()).scrappedFromPosition != n) {
                view.jumpDrawablesToCurrentState();
            }
        } else {
            layoutParams2.forceAdd = false;
            if (layoutParams2.viewType == -2) {
                layoutParams2.recycledHeaderFooter = true;
            }
            if (!bl) {
                n4 = 0;
            }
            this.addViewInLayout(view, n4, layoutParams2, true);
            view.resolveRtlPropertiesIfNeeded();
        }
        if (n5 != 0) {
            n4 = ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, this.mListPadding.left + this.mListPadding.right, layoutParams2.width);
            n = layoutParams2.height;
            n = n > 0 ? View.MeasureSpec.makeMeasureSpec(n, 1073741824) : View.MeasureSpec.makeSafeMeasureSpec(this.getMeasuredHeight(), 0);
            view.measure(n4, n);
        } else {
            this.cleanupLayoutState(view);
        }
        n = view.getMeasuredWidth();
        n4 = view.getMeasuredHeight();
        if (!bl) {
            n2 -= n4;
        }
        if (n5 != 0) {
            view.layout(n3, n2, n3 + n, n2 + n4);
        } else {
            view.offsetLeftAndRight(n3 - view.getLeft());
            view.offsetTopAndBottom(n2 - view.getTop());
        }
        if (this.mCachingStarted && !view.isDrawingCacheEnabled()) {
            view.setDrawingCacheEnabled(true);
        }
        Trace.traceEnd(8L);
    }

    private boolean shouldAdjustHeightForDivider(int n) {
        int n2 = this.mDividerHeight;
        Drawable drawable2 = this.mOverScrollHeader;
        Object object = this.mOverScrollFooter;
        int n3 = drawable2 != null ? 1 : 0;
        int n4 = object != null ? 1 : 0;
        if ((n2 = n2 > 0 && this.mDivider != null ? 1 : 0) != 0) {
            n2 = this.isOpaque() && !super.isOpaque() ? 1 : 0;
            int n5 = this.mItemCount;
            int n6 = this.getHeaderViewsCount();
            int n7 = n5 - this.mFooterViewInfos.size();
            boolean bl = n < n6;
            boolean bl2 = n >= n7;
            boolean bl3 = this.mHeaderDividersEnabled;
            boolean bl4 = this.mFooterDividersEnabled;
            if (bl3 || !bl) {
                if (bl4 || !bl2) {
                    object = this.mAdapter;
                    if (!this.mStackFromBottom) {
                        n3 = n == n5 - 1 ? 1 : 0;
                        if (n4 == 0 || n3 == 0) {
                            n4 = n + 1;
                            if (object.isEnabled(n) && (bl3 || !bl && n4 >= n6)) {
                                if (n3 != 0 || object.isEnabled(n4) && (bl4 || !bl2 && n4 < n7)) {
                                    return true;
                                }
                            }
                            if (n2 != 0) {
                                return true;
                            }
                        }
                    } else {
                        n4 = n3 != 0 ? 1 : 0;
                        if ((n4 = n == n4 ? 1 : 0) == 0) {
                            n3 = n - 1;
                            if (object.isEnabled(n) && (bl3 || !bl && n3 >= n6)) {
                                if (n4 != 0 || object.isEnabled(n3) && (bl4 || !bl2 && n3 < n7)) {
                                    return true;
                                }
                            }
                            if (n2 != 0) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean showingBottomFadingEdge() {
        int n = this.getChildCount();
        int n2 = this.getChildAt(n - 1).getBottom();
        int n3 = this.mFirstPosition;
        boolean bl = true;
        int n4 = this.mScrollY;
        int n5 = this.getHeight();
        int n6 = this.mListPadding.bottom;
        boolean bl2 = bl;
        if (n3 + n - 1 >= this.mItemCount - 1) {
            bl2 = n2 < n4 + n5 - n6 ? bl : false;
        }
        return bl2;
    }

    private boolean showingTopFadingEdge() {
        int n = this.mScrollY;
        int n2 = this.mListPadding.top;
        int n3 = this.mFirstPosition;
        boolean bl = false;
        if (n3 > 0 || this.getChildAt(0).getTop() > n + n2) {
            bl = true;
        }
        return bl;
    }

    public void addFooterView(View view) {
        this.addFooterView(view, null, true);
    }

    public void addFooterView(View view, Object object, boolean bl) {
        if (view.getParent() != null && view.getParent() != this && Log.isLoggable(TAG, 5)) {
            Log.w(TAG, "The specified child already has a parent. You must call removeView() on the child's parent first.");
        }
        FixedViewInfo fixedViewInfo = new FixedViewInfo();
        fixedViewInfo.view = view;
        fixedViewInfo.data = object;
        fixedViewInfo.isSelectable = bl;
        this.mFooterViewInfos.add(fixedViewInfo);
        this.mAreAllItemsSelectable &= bl;
        if (this.mAdapter != null) {
            if (!(this.mAdapter instanceof HeaderViewListAdapter)) {
                this.wrapHeaderListAdapterInternal();
            }
            if (this.mDataSetObserver != null) {
                this.mDataSetObserver.onChanged();
            }
        }
    }

    public void addHeaderView(View view) {
        this.addHeaderView(view, null, true);
    }

    public void addHeaderView(View view, Object object, boolean bl) {
        if (view.getParent() != null && view.getParent() != this && Log.isLoggable(TAG, 5)) {
            Log.w(TAG, "The specified child already has a parent. You must call removeView() on the child's parent first.");
        }
        FixedViewInfo fixedViewInfo = new FixedViewInfo();
        fixedViewInfo.view = view;
        fixedViewInfo.data = object;
        fixedViewInfo.isSelectable = bl;
        this.mHeaderViewInfos.add(fixedViewInfo);
        this.mAreAllItemsSelectable &= bl;
        if (this.mAdapter != null) {
            if (!(this.mAdapter instanceof HeaderViewListAdapter)) {
                this.wrapHeaderListAdapterInternal();
            }
            if (this.mDataSetObserver != null) {
                this.mDataSetObserver.onChanged();
            }
        }
    }

    public boolean areFooterDividersEnabled() {
        return this.mFooterDividersEnabled;
    }

    public boolean areHeaderDividersEnabled() {
        return this.mHeaderDividersEnabled;
    }

    @UnsupportedAppUsage
    boolean arrowScroll(int n) {
        boolean bl;
        block4 : {
            this.mInLayout = true;
            bl = this.arrowScrollImpl(n);
            if (!bl) break block4;
            this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(n));
        }
        return bl;
        finally {
            this.mInLayout = false;
        }
    }

    @Override
    protected boolean canAnimate() {
        boolean bl = super.canAnimate() && this.mItemCount > 0;
        return bl;
    }

    protected void dispatchDataSetObserverOnChangedInternal() {
        if (this.mDataSetObserver != null) {
            this.mDataSetObserver.onChanged();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        block25 : {
            boolean bl;
            int n;
            boolean bl2;
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            ListAdapter listAdapter;
            int n8;
            int n9;
            boolean bl3;
            boolean bl4;
            Rect rect;
            int n10;
            int n11;
            boolean bl5;
            Drawable drawable2;
            Paint paint;
            block27 : {
                Drawable drawable3;
                Object object;
                int n12;
                block26 : {
                    if (this.mCachingStarted) {
                        this.mCachingActive = true;
                    }
                    n8 = this.mDividerHeight;
                    object = this.mOverScrollHeader;
                    drawable3 = this.mOverScrollFooter;
                    n9 = object != null ? 1 : 0;
                    bl4 = drawable3 != null;
                    bl2 = n8 > 0 && this.mDivider != null;
                    if (!bl2 && n9 == 0 && !bl4) break block25;
                    rect = this.mTempRect;
                    rect.left = this.mPaddingLeft;
                    rect.right = this.mRight - this.mLeft - this.mPaddingRight;
                    n5 = this.getChildCount();
                    n7 = this.getHeaderViewsCount();
                    n12 = this.mItemCount;
                    n3 = n12 - this.mFooterViewInfos.size();
                    bl3 = this.mHeaderDividersEnabled;
                    bl = this.mFooterDividersEnabled;
                    n4 = this.mFirstPosition;
                    boolean bl6 = this.mAreAllItemsSelectable;
                    listAdapter = this.mAdapter;
                    bl5 = this.isOpaque() && !super.isOpaque();
                    if (bl5 && this.mDividerPaint == null && this.mIsCacheColorOpaque) {
                        this.mDividerPaint = new Paint();
                        this.mDividerPaint.setColor(this.getCacheColorHint());
                    }
                    paint = this.mDividerPaint;
                    n = this.mGroupFlags;
                    if ((n & 34) == 34) {
                        n = this.mListPadding.top;
                        n10 = this.mListPadding.bottom;
                    } else {
                        n10 = 0;
                        n = 0;
                    }
                    n11 = this.mBottom - this.mTop - n10 + this.mScrollY;
                    if (!this.mStackFromBottom) break block26;
                    drawable2 = drawable3;
                    n2 = this.mScrollY;
                    if (n5 > 0 && n9 != 0) {
                        rect.top = n2;
                        rect.bottom = this.getChildAt(0).getTop();
                        this.drawOverscrollHeader(canvas, (Drawable)object, rect);
                    }
                    n9 = n9 != 0 ? 1 : 0;
                    n10 = n9;
                    n6 = n9;
                    n9 = n;
                    n = n10;
                    n10 = n4;
                    break block27;
                }
                n = this.mScrollY;
                if (n5 > 0 && n < 0) {
                    if (n9 != 0) {
                        rect.bottom = 0;
                        rect.top = n;
                        this.drawOverscrollHeader(canvas, (Drawable)object, rect);
                    } else if (bl2) {
                        rect.bottom = 0;
                        rect.top = -n8;
                        this.drawDivider(canvas, rect, -1);
                    }
                }
                int n13 = 0;
                n = n11;
                Drawable drawable4 = object;
                for (n10 = 0; n10 < n5; ++n10) {
                    int n14 = n4 + n10;
                    boolean bl7 = n14 < n7;
                    boolean bl8 = n14 >= n3;
                    if (!bl3 && bl7 || !bl && bl8) continue;
                    n11 = this.getChildAt(n10).getBottom();
                    n13 = n10 == n5 - 1 ? 1 : 0;
                    if (bl2 && n11 < n) {
                        if (bl4 && n13 != 0) {
                            n13 = n11;
                            continue;
                        }
                        int n15 = n14 + 1;
                        object = listAdapter;
                        if (object.isEnabled(n14) && (bl3 || !bl7 && n15 >= n7) && (n13 != 0 || object.isEnabled(n15) && (bl || !bl8 && n15 < n3))) {
                            rect.top = n11;
                            rect.bottom = n11 + n8;
                            this.drawDivider(canvas, rect, n10);
                            n13 = n11;
                            continue;
                        }
                        if (bl5) {
                            rect.top = n11;
                            rect.bottom = n11 + n8;
                            canvas.drawRect(rect, paint);
                            n13 = n11;
                            continue;
                        }
                        n13 = n11;
                        continue;
                    }
                    n13 = n11;
                }
                n9 = this.mBottom + this.mScrollY;
                if (bl4 && n4 + n5 == n12 && n9 > n13) {
                    rect.top = n13;
                    rect.bottom = n9;
                    this.drawOverscrollFooter(canvas, drawable3, rect);
                }
                break block25;
            }
            while (n6 < n5) {
                int n16 = n10 + n6;
                boolean bl9 = n16 < n7;
                boolean bl10 = n16 >= n3;
                if (!(!bl3 && bl9 || !bl && bl10)) {
                    int n17 = this.getChildAt(n6).getTop();
                    if (bl2 && n17 > n9) {
                        n4 = n9;
                        n9 = n6 == n ? 1 : 0;
                        int n18 = n16 - 1;
                        if (listAdapter.isEnabled(n16) && (bl3 || !bl9 && n18 >= n7) && (n9 != 0 || listAdapter.isEnabled(n18) && (bl || !bl10 && n18 < n3))) {
                            rect.top = n17 - n8;
                            rect.bottom = n17;
                            this.drawDivider(canvas, rect, n6 - 1);
                            n9 = n4;
                        } else {
                            n9 = n4;
                            if (bl5) {
                                rect.top = n17 - n8;
                                rect.bottom = n17;
                                canvas.drawRect(rect, paint);
                                n9 = n4;
                            }
                        }
                    }
                }
                ++n6;
            }
            if (n5 > 0 && n2 > 0) {
                if (bl4) {
                    rect.top = n9 = this.mBottom;
                    rect.bottom = n9 + n2;
                    this.drawOverscrollFooter(canvas, drawable2, rect);
                } else if (bl2) {
                    rect.top = n11;
                    rect.bottom = n11 + n8;
                    this.drawDivider(canvas, rect, -1);
                }
            }
        }
        super.dispatchDraw(canvas);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean bl;
        boolean bl2 = bl = super.dispatchKeyEvent(keyEvent);
        if (!bl) {
            bl2 = bl;
            if (this.getFocusedChild() != null) {
                bl2 = bl;
                if (keyEvent.getAction() == 0) {
                    bl2 = this.onKeyDown(keyEvent.getKeyCode(), keyEvent);
                }
            }
        }
        return bl2;
    }

    @Override
    protected boolean drawChild(Canvas canvas, View view, long l) {
        boolean bl = super.drawChild(canvas, view, l);
        if (this.mCachingActive && view.mCachingFailed) {
            this.mCachingActive = false;
        }
        return bl;
    }

    void drawDivider(Canvas canvas, Rect rect, int n) {
        Drawable drawable2 = this.mDivider;
        drawable2.setBounds(rect);
        drawable2.draw(canvas);
    }

    void drawOverscrollFooter(Canvas canvas, Drawable drawable2, Rect rect) {
        int n = drawable2.getMinimumHeight();
        canvas.save();
        canvas.clipRect(rect);
        if (rect.bottom - rect.top < n) {
            rect.bottom = rect.top + n;
        }
        drawable2.setBounds(rect);
        drawable2.draw(canvas);
        canvas.restore();
    }

    void drawOverscrollHeader(Canvas canvas, Drawable drawable2, Rect rect) {
        int n = drawable2.getMinimumHeight();
        canvas.save();
        canvas.clipRect(rect);
        if (rect.bottom - rect.top < n) {
            rect.top = rect.bottom - n;
        }
        drawable2.setBounds(rect);
        drawable2.draw(canvas);
        canvas.restore();
    }

    @Override
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("recycleOnMeasure", this.recycleOnMeasure());
    }

    @Override
    void fillGap(boolean bl) {
        int n = this.getChildCount();
        if (bl) {
            int n2 = 0;
            if ((this.mGroupFlags & 34) == 34) {
                n2 = this.getListPaddingTop();
            }
            if (n > 0) {
                n2 = this.getChildAt(n - 1).getBottom() + this.mDividerHeight;
            }
            this.fillDown(this.mFirstPosition + n, n2);
            this.correctTooHigh(this.getChildCount());
        } else {
            int n3 = 0;
            if ((this.mGroupFlags & 34) == 34) {
                n3 = this.getListPaddingBottom();
            }
            n3 = n > 0 ? this.getChildAt(0).getTop() - this.mDividerHeight : this.getHeight() - n3;
            this.fillUp(this.mFirstPosition - 1, n3);
            this.correctTooLow(this.getChildCount());
        }
    }

    @Override
    int findMotionRow(int n) {
        block4 : {
            int n2 = this.getChildCount();
            if (n2 <= 0) break block4;
            if (!this.mStackFromBottom) {
                for (int i = 0; i < n2; ++i) {
                    if (n > this.getChildAt(i).getBottom()) continue;
                    return this.mFirstPosition + i;
                }
            } else {
                for (int i = n2 - 1; i >= 0; --i) {
                    if (n < this.getChildAt(i).getTop()) continue;
                    return this.mFirstPosition + i;
                }
            }
        }
        return -1;
    }

    View findViewByPredicateInHeadersOrFooters(ArrayList<FixedViewInfo> arrayList, Predicate<View> predicate, View view) {
        if (arrayList != null) {
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                View view2 = arrayList.get((int)i).view;
                if (view2 == view || view2.isRootNamespace() || (view2 = view2.findViewByPredicate(predicate)) == null) continue;
                return view2;
            }
        }
        return null;
    }

    @Override
    protected <T extends View> T findViewByPredicateTraversal(Predicate<View> object, View view) {
        Object t;
        Object object2 = t = super.findViewByPredicateTraversal((Predicate<View>)object, view);
        if (t == null) {
            object2 = this.findViewByPredicateInHeadersOrFooters(this.mHeaderViewInfos, (Predicate<View>)object, view);
            if (object2 != null) {
                return object2;
            }
            object = this.findViewByPredicateInHeadersOrFooters(this.mFooterViewInfos, (Predicate<View>)object, view);
            object2 = object;
            if (object != null) {
                return (T)object;
            }
        }
        return object2;
    }

    View findViewInHeadersOrFooters(ArrayList<FixedViewInfo> arrayList, int n) {
        if (arrayList != null) {
            int n2 = arrayList.size();
            for (int i = 0; i < n2; ++i) {
                View view = arrayList.get((int)i).view;
                if (view.isRootNamespace() || (view = view.findViewById(n)) == null) continue;
                return view;
            }
        }
        return null;
    }

    @Override
    protected <T extends View> T findViewTraversal(int n) {
        Object object;
        Object object2 = object = super.findViewTraversal(n);
        if (object == null) {
            object2 = this.findViewInHeadersOrFooters(this.mHeaderViewInfos, n);
            if (object2 != null) {
                return object2;
            }
            object2 = object = this.findViewInHeadersOrFooters(this.mFooterViewInfos, n);
            if (object != null) {
                return object;
            }
        }
        return object2;
    }

    View findViewWithTagInHeadersOrFooters(ArrayList<FixedViewInfo> arrayList, Object object) {
        if (arrayList != null) {
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                View view = arrayList.get((int)i).view;
                if (view.isRootNamespace() || (view = view.findViewWithTag(object)) == null) continue;
                return view;
            }
        }
        return null;
    }

    @Override
    protected <T extends View> T findViewWithTagTraversal(Object object) {
        Object t;
        Object object2 = t = super.findViewWithTagTraversal(object);
        if (t == null) {
            object2 = this.findViewWithTagInHeadersOrFooters(this.mHeaderViewInfos, object);
            if (object2 != null) {
                return object2;
            }
            object = this.findViewWithTagInHeadersOrFooters(this.mFooterViewInfos, object);
            object2 = object;
            if (object != null) {
                return (T)object;
            }
        }
        return object2;
    }

    boolean fullScroll(int n) {
        boolean bl;
        boolean bl2 = false;
        if (n == 33) {
            bl = bl2;
            if (this.mSelectedPosition != 0) {
                n = this.lookForSelectablePositionAfter(this.mSelectedPosition, 0, true);
                if (n >= 0) {
                    this.mLayoutMode = 1;
                    this.setSelectionInt(n);
                    this.invokeOnItemScrollListener();
                }
                bl = true;
            }
        } else {
            bl = bl2;
            if (n == 130) {
                n = this.mItemCount - 1;
                bl = bl2;
                if (this.mSelectedPosition < n) {
                    if ((n = this.lookForSelectablePositionAfter(this.mSelectedPosition, n, false)) >= 0) {
                        this.mLayoutMode = 3;
                        this.setSelectionInt(n);
                        this.invokeOnItemScrollListener();
                    }
                    bl = true;
                }
            }
        }
        if (bl && !this.awakenScrollBars()) {
            this.awakenScrollBars();
            this.invalidate();
        }
        return bl;
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return ListView.class.getName();
    }

    @Override
    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    @Deprecated
    public long[] getCheckItemIds() {
        if (this.mAdapter != null && this.mAdapter.hasStableIds()) {
            return this.getCheckedItemIds();
        }
        if (this.mChoiceMode != 0 && this.mCheckStates != null && this.mAdapter != null) {
            long[] arrl = this.mCheckStates;
            int n = arrl.size();
            long[] arrl2 = new long[n];
            ListAdapter listAdapter = this.mAdapter;
            int n2 = 0;
            for (int i = 0; i < n; ++i) {
                int n3 = n2;
                if (arrl.valueAt(i)) {
                    arrl2[n2] = listAdapter.getItemId(arrl.keyAt(i));
                    n3 = n2 + 1;
                }
                n2 = n3;
            }
            if (n2 == n) {
                return arrl2;
            }
            arrl = new long[n2];
            System.arraycopy(arrl2, 0, arrl, 0, n2);
            return arrl;
        }
        return new long[0];
    }

    public Drawable getDivider() {
        return this.mDivider;
    }

    public int getDividerHeight() {
        return this.mDividerHeight;
    }

    @Override
    public int getFooterViewsCount() {
        return this.mFooterViewInfos.size();
    }

    @Override
    public int getHeaderViewsCount() {
        return this.mHeaderViewInfos.size();
    }

    @UnsupportedAppUsage
    @Override
    int getHeightForPosition(int n) {
        int n2 = super.getHeightForPosition(n);
        if (this.shouldAdjustHeightForDivider(n)) {
            return this.mDividerHeight + n2;
        }
        return n2;
    }

    public boolean getItemsCanFocus() {
        return this.mItemsCanFocus;
    }

    public int getMaxScrollAmount() {
        return (int)((float)(this.mBottom - this.mTop) * 0.33f);
    }

    public Drawable getOverscrollFooter() {
        return this.mOverScrollFooter;
    }

    public Drawable getOverscrollHeader() {
        return this.mOverScrollHeader;
    }

    @Override
    public boolean isOpaque() {
        boolean bl = this.mCachingActive && this.mIsCacheColorOpaque && this.mDividerIsOpaque && this.hasOpaqueScrollbars() || super.isOpaque();
        if (bl) {
            int n = this.mListPadding != null ? this.mListPadding.top : this.mPaddingTop;
            View view = this.getChildAt(0);
            if (view != null && view.getTop() <= n) {
                int n2 = this.getHeight();
                n = this.mListPadding != null ? this.mListPadding.bottom : this.mPaddingBottom;
                view = this.getChildAt(this.getChildCount() - 1);
                if (view == null || view.getBottom() < n2 - n) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return bl;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected void layoutChildren() {
        block70 : {
            block71 : {
                block78 : {
                    block75 : {
                        block77 : {
                            block76 : {
                                block74 : {
                                    block72 : {
                                        block73 : {
                                            var1_1 = this.mBlockLayoutRequests;
                                            if (var1_1) {
                                                return;
                                            }
                                            this.mBlockLayoutRequests = true;
                                            super.layoutChildren();
                                            this.invalidate();
                                            if (this.mAdapter == null) {
                                                this.resetList();
                                                this.invokeOnItemScrollListener();
                                                var2_2 = this.mFocusSelector;
                                                if (var2_2 != null) {
                                                    var2_2.onLayoutComplete();
                                                }
                                                if (var1_1 != false) return;
                                                this.mBlockLayoutRequests = false;
                                                return;
                                            }
                                            var3_5 = this.mListPadding.top;
                                            var4_6 = this.mBottom - this.mTop - this.mListPadding.bottom;
                                            var5_7 = this.getChildCount();
                                            var6_8 = 0;
                                            var7_9 = null;
                                            var8_10 = this.mLayoutMode;
                                            if (var8_10 == 1) break block72;
                                            if (var8_10 == 2) break block73;
                                            if (var8_10 == 3 || var8_10 == 4 || var8_10 == 5) break block72;
                                            var8_10 = this.mSelectedPosition - this.mFirstPosition;
                                            var2_3 = var7_9;
                                            if (var8_10 >= 0) {
                                                var2_3 = var7_9;
                                                if (var8_10 < var5_7) {
                                                    var2_3 = this.getChildAt(var8_10);
                                                }
                                            }
                                            var7_9 = this.getChildAt(0);
                                            if (this.mNextSelectedPosition >= 0) {
                                                var6_8 = this.mNextSelectedPosition - this.mSelectedPosition;
                                            }
                                            var9_11 = this.getChildAt(var8_10 + var6_8);
                                            break block74;
                                        }
                                        var6_8 = this.mNextSelectedPosition - this.mFirstPosition;
                                        if (var6_8 >= 0 && var6_8 < var5_7) {
                                            var9_11 = this.getChildAt(var6_8);
                                            var6_8 = 0;
                                            var2_3 = null;
                                            var7_9 = null;
                                        } else {
                                            var6_8 = 0;
                                            var2_3 = null;
                                            var7_9 = null;
                                            var9_11 = null;
                                        }
                                        break block74;
                                    }
                                    var6_8 = 0;
                                    var2_3 = null;
                                    var7_9 = null;
                                    var9_11 = null;
                                }
                                var10_12 = this.mDataChanged;
                                if (var10_12) {
                                    this.handleDataChanged();
                                }
                                if (this.mItemCount == 0) {
                                    this.resetList();
                                    this.invokeOnItemScrollListener();
                                    var2_3 = this.mFocusSelector;
                                    if (var2_3 != null) {
                                        var2_3.onLayoutComplete();
                                    }
                                    if (var1_1 != false) return;
                                    this.mBlockLayoutRequests = false;
                                    return;
                                }
                                if (this.mItemCount != this.mAdapter.getCount()) break block70;
                                this.setSelectedPositionInt(this.mNextSelectedPosition);
                                var11_13 = null;
                                var12_14 = null;
                                var13_15 = this.getViewRootImpl();
                                if (var13_15 == null || (var14_16 = var13_15.getAccessibilityFocusedHost()) == null || (var15_17 = this.getAccessibilityFocusedChild((View)var14_16)) == null) break block75;
                                if (!var10_12 || this.isDirectChildHeaderOrFooter(var15_17)) break block76;
                                var16_18 = var11_13;
                                var17_19 = var12_14;
                                if (!var15_17.hasTransientState()) break block77;
                                var16_18 = var11_13;
                                var17_19 = var12_14;
                                if (!this.mAdapterHasStableIds) break block77;
                            }
                            var17_19 = var14_16;
                            var16_18 = var13_15.getAccessibilityFocusedVirtualView();
                        }
                        var8_10 = this.getPositionForView(var15_17);
                        var12_14 = var17_19;
                        var14_16 = var16_18;
                        break block78;
                    }
                    var14_16 = null;
                    var12_14 = null;
                    var8_10 = -1;
                }
                var17_19 = null;
                var16_18 = null;
                var11_13 = this.getFocusedChild();
                if (var11_13 != null) {
                    if (!var10_12 || this.isDirectChildHeaderOrFooter((View)var11_13) || var11_13.hasTransientState() || this.mAdapterHasStableIds) {
                        var15_17 = this.findFocus();
                        var17_19 = var11_13;
                        var16_18 = var15_17;
                        if (var15_17 != null) {
                            var15_17.dispatchStartTemporaryDetach();
                            var16_18 = var15_17;
                            var17_19 = var11_13;
                        }
                    }
                    this.requestFocus();
                    var11_13 = var16_18;
                    var16_18 = var17_19;
                    var17_19 = var11_13;
                } else {
                    var16_18 = null;
                    var17_19 = null;
                }
                var18_20 = this.mFirstPosition;
                var11_13 = this.mRecycler;
                if (!var10_12) break block71;
                var19_21 = 0;
            }
            var11_13.fillActiveViews(var5_7, var18_20);
            do {
                block80 : {
                    block79 : {
                        this.detachAllViewsFromParent();
                        var11_13.removeSkippedScrap();
                        switch (this.mLayoutMode) {
                            default: {
                                if (var5_7 == 0) {
                                    var10_12 = this.mStackFromBottom;
                                    ** break;
                                }
                                break block79;
                            }
                            case 6: {
                                var2_3 = this.moveSelection((View)var2_3, var9_11, var6_8, var3_5, var4_6);
                                break;
                            }
                            case 5: {
                                var2_3 = this.fillSpecific(this.mSyncPosition, this.mSpecificTop);
                                break;
                            }
                            case 4: {
                                var6_8 = this.reconcileSelectedPosition();
                                var2_3 = this.fillSpecific(var6_8, this.mSpecificTop);
                                if (var2_3 != null || this.mFocusSelector == null || (var7_9 = this.mFocusSelector.setupFocusIfValid(var6_8)) == null) break;
                                this.post((Runnable)var7_9);
                                break;
                            }
                            case 3: {
                                var2_3 = this.fillUp(this.mItemCount - 1, var4_6);
                                this.adjustViewsUpOrDown();
                                break;
                            }
                            case 2: {
                                if (var9_11 != null) {
                                    var2_3 = this.fillFromSelection(var9_11.getTop(), var3_5, var4_6);
                                    break;
                                }
                                var2_3 = this.fillFromMiddle(var3_5, var4_6);
                                break;
                            }
                            case 1: {
                                this.mFirstPosition = 0;
                                var2_3 = this.fillFromTop(var3_5);
                                this.adjustViewsUpOrDown();
                                break;
                            }
                        }
                        break block80;
lbl160: // 1 sources:
                        if (!var10_12) {
                            this.setSelectedPositionInt(this.lookForSelectablePosition(0, true));
                            var2_3 = this.fillFromTop(var3_5);
                        } else {
                            this.setSelectedPositionInt(this.lookForSelectablePosition(this.mItemCount - 1, false));
                            var2_3 = this.fillUp(this.mItemCount - 1, var4_6);
                        }
                        break block80;
                    }
                    if (this.mSelectedPosition >= 0 && this.mSelectedPosition < this.mItemCount) {
                        var19_21 = this.mSelectedPosition;
                        var6_8 = var2_3 == null ? var3_5 : var2_3.getTop();
                        var2_3 = this.fillSpecific(var19_21, var6_8);
                    } else if (this.mFirstPosition < this.mItemCount) {
                        var6_8 = this.mFirstPosition;
                        if (var7_9 != null) {
                            var3_5 = var7_9.getTop();
                        }
                        var2_3 = this.fillSpecific(var6_8, var3_5);
                    } else {
                        var2_3 = this.fillSpecific(0, var3_5);
                    }
                }
                var11_13.scrapActiveViews();
                this.removeUnusedFixedViews(this.mHeaderViewInfos);
                this.removeUnusedFixedViews(this.mFooterViewInfos);
                if (var2_3 == null) ** GOTO lbl208
                if (!this.mItemsCanFocus || !this.hasFocus() || var2_3.hasFocus()) ** GOTO lbl204
                if (var2_3 != var16_18 || var17_19 == null) ** GOTO lbl191
                break;
            } while (true);
            {
                block82 : {
                    block81 : {
                        if (var17_19.requestFocus()) ** GOTO lbl-1000
lbl191: // 2 sources:
                        if (var2_3.requestFocus()) lbl-1000: // 2 sources:
                        {
                            var6_8 = 1;
                        } else {
                            var6_8 = 0;
                        }
                        if (var6_8 == 0) {
                            var7_9 = this.getFocusedChild();
                            if (var7_9 != null) {
                                var7_9.clearFocus();
                            }
                            this.positionSelector(-1, (View)var2_3);
                        } else {
                            var2_3.setSelected(false);
                            this.mSelectorRect.setEmpty();
                        }
                        break block81;
lbl204: // 1 sources:
                        this.positionSelector(-1, (View)var2_3);
                    }
                    this.mSelectedTop = var2_3.getTop();
                    break block82;
lbl208: // 1 sources:
                    var6_8 = this.mTouchMode != 1 && this.mTouchMode != 2 ? 0 : 1;
                    if (var6_8 != 0) {
                        var2_3 = this.getChildAt(this.mMotionPosition - this.mFirstPosition);
                        if (var2_3 != null) {
                            this.positionSelector(this.mMotionPosition, (View)var2_3);
                        }
                    } else if (this.mSelectorPosition != -1) {
                        var2_3 = this.getChildAt(this.mSelectorPosition - this.mFirstPosition);
                        if (var2_3 != null) {
                            this.positionSelector(this.mSelectorPosition, (View)var2_3);
                        }
                    } else {
                        this.mSelectedTop = 0;
                        this.mSelectorRect.setEmpty();
                    }
                    if (this.hasFocus() && var17_19 != null) {
                        var17_19.requestFocus();
                    }
                }
                if (var13_15 != null && var13_15.getAccessibilityFocusedHost() == null) {
                    if (var12_14 != null && var12_14.isAttachedToWindow()) {
                        var2_3 = var12_14.getAccessibilityNodeProvider();
                        if (var14_16 != null && var2_3 != null) {
                            var2_3.performAction(AccessibilityNodeInfo.getVirtualDescendantId(var14_16.getSourceNodeId()), 64, null);
                        } else {
                            var12_14.requestAccessibilityFocus();
                        }
                    } else if (var8_10 != -1 && (var2_3 = this.getChildAt(MathUtils.constrain(var8_10 - this.mFirstPosition, 0, this.getChildCount() - 1))) != null) {
                        var2_3.requestAccessibilityFocus();
                    }
                }
                if (var17_19 != null && var17_19.getWindowToken() != null) {
                    var17_19.dispatchFinishTemporaryDetach();
                }
                this.mLayoutMode = 0;
                this.mDataChanged = false;
                if (this.mPositionScrollAfterLayout != null) {
                    this.post(this.mPositionScrollAfterLayout);
                    this.mPositionScrollAfterLayout = null;
                }
                this.mNeedSync = false;
                this.setNextSelectedPositionInt(this.mSelectedPosition);
                this.updateScrollIndicators();
                if (this.mItemCount > 0) {
                    this.checkSelectionChanged();
                }
                this.invokeOnItemScrollListener();
                return;
            }
        }
        try {
            var7_9 = new StringBuilder();
            var7_9.append("The content of the adapter has changed but ListView did not receive a notification. Make sure the content of your adapter is not modified from a background thread, but only from the UI thread. Make sure your adapter calls notifyDataSetChanged() when its content changes. [in ListView(");
            var7_9.append(this.getId());
            var7_9.append(", ");
            var7_9.append(this.getClass());
            var7_9.append(") with Adapter(");
            var7_9.append(this.mAdapter.getClass());
            var7_9.append(")]");
            var2_3 = new IllegalStateException(var7_9.toString());
            throw var2_3;
        }
        finally {
            var2_3 = this.mFocusSelector;
            if (var2_3 != null) {
                var2_3.onLayoutComplete();
            }
            if (!var1_1) {
                this.mBlockLayoutRequests = false;
            }
        }
        do {
            if (var19_21 >= var5_7) ** continue;
            var11_13.addScrapView(this.getChildAt(var19_21), var18_20 + var19_21);
            ++var19_21;
        } while (true);
    }

    @UnsupportedAppUsage
    @Override
    int lookForSelectablePosition(int n, boolean bl) {
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null && !this.isInTouchMode()) {
            int n2 = listAdapter.getCount();
            int n3 = n;
            if (!this.mAreAllItemsSelectable) {
                if (bl) {
                    n = Math.max(0, n);
                    do {
                        n3 = n;
                        if (n < n2) {
                            n3 = n;
                            if (!listAdapter.isEnabled(n)) {
                                ++n;
                                continue;
                            }
                        }
                        break;
                    } while (true);
                } else {
                    n = Math.min(n, n2 - 1);
                    do {
                        n3 = n;
                        if (n < 0) break;
                        n3 = n;
                        if (listAdapter.isEnabled(n)) break;
                        --n;
                    } while (true);
                }
            }
            if (n3 >= 0 && n3 < n2) {
                return n3;
            }
            return -1;
        }
        return -1;
    }

    int lookForSelectablePositionAfter(int n, int n2, boolean bl) {
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null && !this.isInTouchMode()) {
            int n3 = this.lookForSelectablePosition(n2, bl);
            if (n3 != -1) {
                return n3;
            }
            int n4 = listAdapter.getCount();
            n3 = MathUtils.constrain(n, -1, n4 - 1);
            if (bl) {
                for (n = Math.min((int)(n2 - 1), (int)(n4 - 1)); n > n3 && !listAdapter.isEnabled(n); --n) {
                }
                n2 = n;
                if (n <= n3) {
                    return -1;
                }
            } else {
                for (n = Math.max((int)0, (int)(n2 + 1)); n < n3 && !listAdapter.isEnabled(n); ++n) {
                }
                n2 = n;
                if (n >= n3) {
                    return -1;
                }
            }
            return n2;
        }
        return -1;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    final int measureHeightOfChildren(int n, int n2, int n3, int n4, int n5) {
        Object object = this.mAdapter;
        if (object == null) {
            return this.mListPadding.top + this.mListPadding.bottom;
        }
        int n6 = this.mListPadding.top + this.mListPadding.bottom;
        int n7 = this.mDividerHeight;
        int n8 = 0;
        int n9 = n3 == -1 ? object.getCount() - 1 : n3;
        AbsListView.RecycleBin recycleBin = this.mRecycler;
        boolean bl = this.recycleOnMeasure();
        boolean[] arrbl = this.mIsScrap;
        int n10 = n2;
        n2 = n8;
        n3 = n6;
        while (n10 <= n9) {
            object = this.obtainView(n10, arrbl);
            this.measureScrapChild((View)object, n10, n, n4);
            n6 = n3;
            if (n10 > 0) {
                n6 = n3 + n7;
            }
            if (bl && recycleBin.shouldRecycleViewType(((AbsListView.LayoutParams)object.getLayoutParams()).viewType)) {
                recycleBin.addScrapView((View)object, -1);
            }
            if ((n3 = n6 + ((View)object).getMeasuredHeight()) >= n4) {
                if (n5 < 0 || n10 <= n5 || n2 <= 0 || n3 == n4) {
                    n2 = n4;
                }
                return n2;
            }
            n6 = n2;
            if (n5 >= 0) {
                n6 = n2;
                if (n10 >= n5) {
                    n6 = n3;
                }
            }
            ++n10;
            n2 = n6;
        }
        return n3;
    }

    @Override
    protected void onDetachedFromWindow() {
        FocusSelector focusSelector = this.mFocusSelector;
        if (focusSelector != null) {
            this.removeCallbacks(focusSelector);
            this.mFocusSelector = null;
        }
        super.onDetachedFromWindow();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int n = this.getChildCount();
        if (n > 0) {
            for (int i = 0; i < n; ++i) {
                this.addHeaderView(this.getChildAt(i));
            }
            this.removeAllViews();
        }
    }

    @Override
    protected void onFocusChanged(boolean bl, int n, Rect rect) {
        super.onFocusChanged(bl, n, rect);
        ListAdapter listAdapter = this.mAdapter;
        int n2 = -1;
        int n3 = 0;
        int n4 = 0;
        int n5 = n2;
        int n6 = n3;
        if (listAdapter != null) {
            n5 = n2;
            n6 = n3;
            if (bl) {
                n5 = n2;
                n6 = n3;
                if (rect != null) {
                    rect.offset(this.mScrollX, this.mScrollY);
                    if (listAdapter.getCount() < this.getChildCount() + this.mFirstPosition) {
                        this.mLayoutMode = 0;
                        this.layoutChildren();
                    }
                    Rect rect2 = this.mTempRect;
                    int n7 = Integer.MAX_VALUE;
                    int n8 = this.getChildCount();
                    int n9 = this.mFirstPosition;
                    n3 = 0;
                    do {
                        n5 = n2;
                        n6 = n4;
                        if (n3 >= n8) break;
                        if (!listAdapter.isEnabled(n9 + n3)) {
                            n5 = n7;
                        } else {
                            View view = this.getChildAt(n3);
                            view.getDrawingRect(rect2);
                            this.offsetDescendantRectToMyCoords(view, rect2);
                            n6 = ListView.getDistance(rect, rect2, n);
                            n5 = n7;
                            if (n6 < n7) {
                                n5 = n6;
                                n2 = n3;
                                n4 = view.getTop();
                            }
                        }
                        ++n3;
                        n7 = n5;
                    } while (true);
                }
            }
        }
        if (n5 >= 0) {
            this.setSelectionFromTop(this.mFirstPosition + n5, n6);
        } else {
            this.requestLayout();
        }
    }

    @Override
    public void onInitializeAccessibilityNodeInfoForItem(View object, int n, AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoForItem((View)object, n, accessibilityNodeInfo);
        object = (AbsListView.LayoutParams)((View)object).getLayoutParams();
        boolean bl = object != null && ((AbsListView.LayoutParams)object).viewType == -2;
        accessibilityNodeInfo.setCollectionItemInfo(AccessibilityNodeInfo.CollectionItemInfo.obtain(n, 1, 0, 1, bl, this.isItemChecked(n)));
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        int n = this.getCount();
        accessibilityNodeInfo.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(n, 1, false, this.getSelectionModeForAccessibility()));
        if (n > 0) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION);
        }
    }

    @Override
    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        return this.commonKey(n, 1, keyEvent);
    }

    @Override
    public boolean onKeyMultiple(int n, int n2, KeyEvent keyEvent) {
        return this.commonKey(n, n2, keyEvent);
    }

    @Override
    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        return this.commonKey(n, 1, keyEvent);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        block6 : {
            int n9;
            int n10;
            int n11;
            block7 : {
                super.onMeasure(n, n2);
                n5 = View.MeasureSpec.getMode(n);
                n7 = View.MeasureSpec.getMode(n2);
                n4 = View.MeasureSpec.getSize(n);
                n6 = View.MeasureSpec.getSize(n2);
                n11 = 0;
                n10 = 0;
                n9 = 0;
                n2 = this.mAdapter == null ? 0 : this.mAdapter.getCount();
                this.mItemCount = n2;
                n8 = n11;
                n2 = n10;
                n3 = n9;
                if (this.mItemCount <= 0) break block6;
                if (n5 == 0) break block7;
                n8 = n11;
                n2 = n10;
                n3 = n9;
                if (n7 != 0) break block6;
            }
            View view = this.obtainView(0, this.mIsScrap);
            this.measureScrapChild(view, 0, n, n6);
            n11 = view.getMeasuredWidth();
            n9 = view.getMeasuredHeight();
            n10 = ListView.combineMeasuredStates(0, view.getMeasuredState());
            n8 = n11;
            n2 = n9;
            n3 = n10;
            if (this.recycleOnMeasure()) {
                n8 = n11;
                n2 = n9;
                n3 = n10;
                if (this.mRecycler.shouldRecycleViewType(((AbsListView.LayoutParams)view.getLayoutParams()).viewType)) {
                    this.mRecycler.addScrapView(view, 0);
                    n3 = n10;
                    n2 = n9;
                    n8 = n11;
                }
            }
        }
        n8 = n5 == 0 ? this.mListPadding.left + this.mListPadding.right + n8 + this.getVerticalScrollbarWidth() : -16777216 & n3 | n4;
        n2 = n7 == 0 ? this.mListPadding.top + this.mListPadding.bottom + n2 + this.getVerticalFadingEdgeLength() * 2 : n6;
        n3 = n2;
        if (n7 == Integer.MIN_VALUE) {
            n3 = this.measureHeightOfChildren(n, 0, -1, n2, -1);
        }
        this.setMeasuredDimension(n8, n3);
        this.mWidthMeasureSpec = n;
    }

    @Override
    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        View view;
        if (this.getChildCount() > 0 && (view = this.getFocusedChild()) != null) {
            int n5 = this.mFirstPosition;
            int n6 = this.indexOfChild(view);
            int n7 = Math.max(0, view.getBottom() - (n2 - this.mPaddingTop));
            int n8 = view.getTop();
            if (this.mFocusSelector == null) {
                this.mFocusSelector = new FocusSelector();
            }
            this.post(this.mFocusSelector.setupForSetSelection(n5 + n6, n8 - n7));
        }
        super.onSizeChanged(n, n2, n3, n4);
    }

    boolean pageScroll(int n) {
        block9 : {
            boolean bl;
            block8 : {
                block7 : {
                    if (n != 33) break block7;
                    n = Math.max(0, this.mSelectedPosition - this.getChildCount() - 1);
                    bl = false;
                    break block8;
                }
                if (n != 130) break block9;
                n = Math.min(this.mItemCount - 1, this.mSelectedPosition + this.getChildCount() - 1);
                bl = true;
            }
            if (n >= 0 && (n = this.lookForSelectablePositionAfter(this.mSelectedPosition, n, bl)) >= 0) {
                this.mLayoutMode = 4;
                this.mSpecificTop = this.mPaddingTop + this.getVerticalFadingEdgeLength();
                if (bl && n > this.mItemCount - this.getChildCount()) {
                    this.mLayoutMode = 3;
                }
                if (!bl && n < this.getChildCount()) {
                    this.mLayoutMode = 1;
                }
                this.setSelectionInt(n);
                this.invokeOnItemScrollListener();
                if (!this.awakenScrollBars()) {
                    this.invalidate();
                }
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean performAccessibilityActionInternal(int n, Bundle bundle) {
        if (super.performAccessibilityActionInternal(n, bundle)) {
            return true;
        }
        if (n == 16908343) {
            int n2 = bundle.getInt("android.view.accessibility.action.ARGUMENT_ROW_INT", -1);
            n = Math.min(n2, this.getCount() - 1);
            if (n2 >= 0) {
                this.smoothScrollToPosition(n);
                return true;
            }
        }
        return false;
    }

    @ViewDebug.ExportedProperty(category="list")
    protected boolean recycleOnMeasure() {
        return true;
    }

    public boolean removeFooterView(View view) {
        if (this.mFooterViewInfos.size() > 0) {
            boolean bl;
            boolean bl2 = bl = false;
            if (this.mAdapter != null) {
                bl2 = bl;
                if (((HeaderViewListAdapter)this.mAdapter).removeFooter(view)) {
                    if (this.mDataSetObserver != null) {
                        this.mDataSetObserver.onChanged();
                    }
                    bl2 = true;
                }
            }
            this.removeFixedViewInfo(view, this.mFooterViewInfos);
            return bl2;
        }
        return false;
    }

    public boolean removeHeaderView(View view) {
        if (this.mHeaderViewInfos.size() > 0) {
            boolean bl;
            boolean bl2 = bl = false;
            if (this.mAdapter != null) {
                bl2 = bl;
                if (((HeaderViewListAdapter)this.mAdapter).removeHeader(view)) {
                    if (this.mDataSetObserver != null) {
                        this.mDataSetObserver.onChanged();
                    }
                    bl2 = true;
                }
            }
            this.removeFixedViewInfo(view, this.mHeaderViewInfos);
            return bl2;
        }
        return false;
    }

    @Override
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean bl) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        block14 : {
            block15 : {
                block12 : {
                    block13 : {
                        n6 = rect.top;
                        rect.offset(view.getLeft(), view.getTop());
                        rect.offset(-view.getScrollX(), -view.getScrollY());
                        n3 = this.getHeight();
                        n5 = this.getScrollY();
                        n4 = n5 + n3;
                        n2 = this.getVerticalFadingEdgeLength();
                        n = n5;
                        if (!this.showingTopFadingEdge()) break block12;
                        if (this.mSelectedPosition > 0) break block13;
                        n = n5;
                        if (n6 <= n2) break block12;
                    }
                    n = n5 + n2;
                }
                n6 = this.getChildAt(this.getChildCount() - 1).getBottom();
                boolean bl2 = this.showingBottomFadingEdge();
                bl = true;
                n5 = n4;
                if (!bl2) break block14;
                if (this.mSelectedPosition < this.mItemCount - 1) break block15;
                n5 = n4;
                if (rect.bottom >= n6 - n2) break block14;
            }
            n5 = n4 - n2;
        }
        n2 = 0;
        if (rect.bottom > n5 && rect.top > n) {
            n4 = rect.height() > n3 ? 0 + (rect.top - n) : 0 + (rect.bottom - n5);
            n4 = Math.min(n4, n6 - n5);
        } else {
            n4 = n2;
            if (rect.top < n) {
                n4 = n2;
                if (rect.bottom < n5) {
                    n4 = rect.height() > n3 ? 0 - (n5 - rect.bottom) : 0 - (n - rect.top);
                    n4 = Math.max(n4, this.getChildAt(0).getTop() - n);
                }
            }
        }
        if (n4 == 0) {
            bl = false;
        }
        if (bl) {
            this.scrollListItemsBy(-n4);
            this.positionSelector(-1, view);
            this.mSelectedTop = view.getTop();
            this.invalidate();
        }
        return bl;
    }

    @Override
    void resetList() {
        this.clearRecycledState(this.mHeaderViewInfos);
        this.clearRecycledState(this.mFooterViewInfos);
        super.resetList();
        this.mLayoutMode = 0;
    }

    @Override
    public void setAdapter(ListAdapter listAdapter) {
        if (this.mAdapter != null && this.mDataSetObserver != null) {
            this.mAdapter.unregisterDataSetObserver((DataSetObserver)((Object)this.mDataSetObserver));
        }
        this.resetList();
        this.mRecycler.clear();
        this.mAdapter = this.mHeaderViewInfos.size() <= 0 && this.mFooterViewInfos.size() <= 0 ? listAdapter : this.wrapHeaderListAdapterInternal(this.mHeaderViewInfos, this.mFooterViewInfos, listAdapter);
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        super.setAdapter(listAdapter);
        if (this.mAdapter != null) {
            this.mAreAllItemsSelectable = this.mAdapter.areAllItemsEnabled();
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
            this.checkFocus();
            this.mDataSetObserver = new AbsListView.AdapterDataSetObserver();
            this.mAdapter.registerDataSetObserver((DataSetObserver)((Object)this.mDataSetObserver));
            this.mRecycler.setViewTypeCount(this.mAdapter.getViewTypeCount());
            int n = this.mStackFromBottom ? this.lookForSelectablePosition(this.mItemCount - 1, false) : this.lookForSelectablePosition(0, true);
            this.setSelectedPositionInt(n);
            this.setNextSelectedPositionInt(n);
            if (this.mItemCount == 0) {
                this.checkSelectionChanged();
            }
        } else {
            this.mAreAllItemsSelectable = true;
            this.checkFocus();
            this.checkSelectionChanged();
        }
        this.requestLayout();
    }

    @Override
    public void setCacheColorHint(int n) {
        boolean bl = n >>> 24 == 255;
        this.mIsCacheColorOpaque = bl;
        if (bl) {
            if (this.mDividerPaint == null) {
                this.mDividerPaint = new Paint();
            }
            this.mDividerPaint.setColor(n);
        }
        super.setCacheColorHint(n);
    }

    public void setDivider(Drawable drawable2) {
        boolean bl = false;
        this.mDividerHeight = drawable2 != null ? drawable2.getIntrinsicHeight() : 0;
        this.mDivider = drawable2;
        if (drawable2 == null || drawable2.getOpacity() == -1) {
            bl = true;
        }
        this.mDividerIsOpaque = bl;
        this.requestLayout();
        this.invalidate();
    }

    public void setDividerHeight(int n) {
        this.mDividerHeight = n;
        this.requestLayout();
        this.invalidate();
    }

    public void setFooterDividersEnabled(boolean bl) {
        this.mFooterDividersEnabled = bl;
        this.invalidate();
    }

    public void setHeaderDividersEnabled(boolean bl) {
        this.mHeaderDividersEnabled = bl;
        this.invalidate();
    }

    public void setItemsCanFocus(boolean bl) {
        this.mItemsCanFocus = bl;
        if (!bl) {
            this.setDescendantFocusability(393216);
        }
    }

    public void setOverscrollFooter(Drawable drawable2) {
        this.mOverScrollFooter = drawable2;
        this.invalidate();
    }

    public void setOverscrollHeader(Drawable drawable2) {
        this.mOverScrollHeader = drawable2;
        if (this.mScrollY < 0) {
            this.invalidate();
        }
    }

    @RemotableViewMethod(asyncImpl="setRemoteViewsAdapterAsync")
    @Override
    public void setRemoteViewsAdapter(Intent intent) {
        super.setRemoteViewsAdapter(intent);
    }

    @Override
    public void setSelection(int n) {
        this.setSelectionFromTop(n, 0);
    }

    public void setSelectionAfterHeaderView() {
        int n = this.getHeaderViewsCount();
        if (n > 0) {
            this.mNextSelectedPosition = 0;
            return;
        }
        if (this.mAdapter != null) {
            this.setSelection(n);
        } else {
            this.mNextSelectedPosition = n;
            this.mLayoutMode = 2;
        }
    }

    @UnsupportedAppUsage
    @Override
    void setSelectionInt(int n) {
        this.setNextSelectedPositionInt(n);
        boolean bl = false;
        int n2 = this.mSelectedPosition;
        boolean bl2 = bl;
        if (n2 >= 0) {
            if (n == n2 - 1) {
                bl2 = true;
            } else {
                bl2 = bl;
                if (n == n2 + 1) {
                    bl2 = true;
                }
            }
        }
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        this.layoutChildren();
        if (bl2) {
            this.awakenScrollBars();
        }
    }

    @RemotableViewMethod
    @Override
    public void smoothScrollByOffset(int n) {
        super.smoothScrollByOffset(n);
    }

    @RemotableViewMethod
    @Override
    public void smoothScrollToPosition(int n) {
        super.smoothScrollToPosition(n);
    }

    @UnsupportedAppUsage
    @Override
    boolean trackMotionScroll(int n, int n2) {
        boolean bl = super.trackMotionScroll(n, n2);
        this.removeUnusedFixedViews(this.mHeaderViewInfos);
        this.removeUnusedFixedViews(this.mFooterViewInfos);
        return bl;
    }

    protected HeaderViewListAdapter wrapHeaderListAdapterInternal(ArrayList<FixedViewInfo> arrayList, ArrayList<FixedViewInfo> arrayList2, ListAdapter listAdapter) {
        return new HeaderViewListAdapter(arrayList, arrayList2, listAdapter);
    }

    protected void wrapHeaderListAdapterInternal() {
        this.mAdapter = this.wrapHeaderListAdapterInternal(this.mHeaderViewInfos, this.mFooterViewInfos, this.mAdapter);
    }

    private static class ArrowScrollFocusResult {
        private int mAmountToScroll;
        private int mSelectedPosition;

        private ArrowScrollFocusResult() {
        }

        public int getAmountToScroll() {
            return this.mAmountToScroll;
        }

        public int getSelectedPosition() {
            return this.mSelectedPosition;
        }

        void populate(int n, int n2) {
            this.mSelectedPosition = n;
            this.mAmountToScroll = n2;
        }
    }

    public class FixedViewInfo {
        public Object data;
        public boolean isSelectable;
        public View view;
    }

    private class FocusSelector
    implements Runnable {
        private static final int STATE_REQUEST_FOCUS = 3;
        private static final int STATE_SET_SELECTION = 1;
        private static final int STATE_WAIT_FOR_LAYOUT = 2;
        private int mAction;
        private int mPosition;
        private int mPositionTop;

        private FocusSelector() {
        }

        void onLayoutComplete() {
            if (this.mAction == 2) {
                this.mAction = -1;
            }
        }

        @Override
        public void run() {
            int n = this.mAction;
            if (n == 1) {
                ListView.this.setSelectionFromTop(this.mPosition, this.mPositionTop);
                this.mAction = 2;
            } else if (n == 3) {
                int n2 = this.mPosition;
                n = ListView.this.mFirstPosition;
                View view = ListView.this.getChildAt(n2 - n);
                if (view != null) {
                    view.requestFocus();
                }
                this.mAction = -1;
            }
        }

        Runnable setupFocusIfValid(int n) {
            if (this.mAction == 2 && n == this.mPosition) {
                this.mAction = 3;
                return this;
            }
            return null;
        }

        FocusSelector setupForSetSelection(int n, int n2) {
            this.mPosition = n;
            this.mPositionTop = n2;
            this.mAction = 1;
            return this;
        }
    }

}

