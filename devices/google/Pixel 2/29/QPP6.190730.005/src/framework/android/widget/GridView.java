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
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.RemotableViewMethod;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.Checkable;
import android.widget.ListAdapter;
import android.widget.RemoteViews;
import com.android.internal.R;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@RemoteViews.RemoteView
public class GridView
extends AbsListView {
    public static final int AUTO_FIT = -1;
    public static final int NO_STRETCH = 0;
    public static final int STRETCH_COLUMN_WIDTH = 2;
    public static final int STRETCH_SPACING = 1;
    public static final int STRETCH_SPACING_UNIFORM = 3;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=117521079L)
    private int mColumnWidth;
    private int mGravity = 8388611;
    @UnsupportedAppUsage
    private int mHorizontalSpacing = 0;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=117521080L)
    private int mNumColumns = -1;
    private View mReferenceView = null;
    private View mReferenceViewInSelectedRow = null;
    @UnsupportedAppUsage
    private int mRequestedColumnWidth;
    @UnsupportedAppUsage
    private int mRequestedHorizontalSpacing;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123769395L)
    private int mRequestedNumColumns;
    private int mStretchMode = 2;
    private final Rect mTempRect = new Rect();
    @UnsupportedAppUsage
    private int mVerticalSpacing = 0;

    public GridView(Context context) {
        this(context, null);
    }

    public GridView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842865);
    }

    public GridView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public GridView(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.GridView, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.GridView, attributeSet, typedArray, n, n2);
        this.setHorizontalSpacing(typedArray.getDimensionPixelOffset(1, 0));
        this.setVerticalSpacing(typedArray.getDimensionPixelOffset(2, 0));
        n = typedArray.getInt(3, 2);
        if (n >= 0) {
            this.setStretchMode(n);
        }
        if ((n = typedArray.getDimensionPixelOffset(4, -1)) > 0) {
            this.setColumnWidth(n);
        }
        this.setNumColumns(typedArray.getInt(5, 1));
        n = typedArray.getInt(0, -1);
        if (n >= 0) {
            this.setGravity(n);
        }
        typedArray.recycle();
    }

    private void adjustForBottomFadingEdge(View view, int n, int n2) {
        if (view.getBottom() > n2) {
            this.offsetChildrenTopAndBottom(-Math.min(view.getTop() - n, view.getBottom() - n2));
        }
    }

    private void adjustForTopFadingEdge(View view, int n, int n2) {
        if (view.getTop() < n) {
            this.offsetChildrenTopAndBottom(Math.min(n - view.getTop(), n2 - view.getBottom()));
        }
    }

    private void adjustViewsUpOrDown() {
        int n = this.getChildCount();
        if (n > 0) {
            int n2;
            if (!this.mStackFromBottom) {
                int n3 = n2 = this.getChildAt(0).getTop() - this.mListPadding.top;
                if (this.mFirstPosition != 0) {
                    n3 = n2 - this.mVerticalSpacing;
                }
                n2 = n3;
                if (n3 < 0) {
                    n2 = 0;
                }
            } else {
                int n4 = n2 = this.getChildAt(n - 1).getBottom() - (this.getHeight() - this.mListPadding.bottom);
                if (this.mFirstPosition + n < this.mItemCount) {
                    n4 = n2 + this.mVerticalSpacing;
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

    private boolean commonKey(int n, int n2, KeyEvent keyEvent) {
        boolean bl;
        int n3;
        block42 : {
            boolean bl2;
            block43 : {
                block44 : {
                    block45 : {
                        block46 : {
                            block47 : {
                                if (this.mAdapter == null) {
                                    return false;
                                }
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
                                if (bl2) break block42;
                                bl = bl2;
                                if (n3 == 1) break block42;
                                if (n == 61) break block43;
                                if (n == 92) break block44;
                                if (n == 93) break block45;
                                if (n == 122) break block46;
                                if (n == 123) break block47;
                                switch (n) {
                                    default: {
                                        bl = bl2;
                                        break;
                                    }
                                    case 22: {
                                        bl = bl2;
                                        if (keyEvent.hasNoModifiers()) {
                                            if (!this.resurrectSelectionIfNeeded() && !this.arrowScroll(66)) {
                                                bl = false;
                                                break;
                                            }
                                            bl = true;
                                            break;
                                        }
                                        break block42;
                                    }
                                    case 21: {
                                        bl = bl2;
                                        if (keyEvent.hasNoModifiers()) {
                                            if (!this.resurrectSelectionIfNeeded() && !this.arrowScroll(17)) {
                                                bl = false;
                                                break;
                                            }
                                            bl = true;
                                            break;
                                        }
                                        break block42;
                                    }
                                    case 20: {
                                        if (keyEvent.hasNoModifiers()) {
                                            if (!this.resurrectSelectionIfNeeded() && !this.arrowScroll(130)) {
                                                bl = false;
                                                break;
                                            }
                                            bl = true;
                                            break;
                                        }
                                        bl = bl2;
                                        if (keyEvent.hasModifiers(2)) {
                                            if (!this.resurrectSelectionIfNeeded() && !this.fullScroll(130)) {
                                                bl = false;
                                                break;
                                            }
                                            bl = true;
                                            break;
                                        }
                                        break block42;
                                    }
                                    case 19: {
                                        if (keyEvent.hasNoModifiers()) {
                                            if (!this.resurrectSelectionIfNeeded() && !this.arrowScroll(33)) {
                                                bl = false;
                                                break;
                                            }
                                            bl = true;
                                            break;
                                        }
                                        bl = bl2;
                                        if (keyEvent.hasModifiers(2)) {
                                            if (!this.resurrectSelectionIfNeeded() && !this.fullScroll(33)) {
                                                bl = false;
                                                break;
                                            }
                                            bl = true;
                                            break;
                                        }
                                        break block42;
                                    }
                                }
                                break block42;
                            }
                            bl = bl2;
                            if (keyEvent.hasNoModifiers()) {
                                bl = this.resurrectSelectionIfNeeded() || this.fullScroll(130);
                            }
                            break block42;
                        }
                        bl = bl2;
                        if (keyEvent.hasNoModifiers()) {
                            bl = this.resurrectSelectionIfNeeded() || this.fullScroll(33);
                        }
                        break block42;
                    }
                    if (keyEvent.hasNoModifiers()) {
                        bl = this.resurrectSelectionIfNeeded() || this.pageScroll(130);
                    } else {
                        bl = bl2;
                        if (keyEvent.hasModifiers(2)) {
                            bl = this.resurrectSelectionIfNeeded() || this.fullScroll(130);
                        }
                    }
                    break block42;
                }
                if (keyEvent.hasNoModifiers()) {
                    bl = this.resurrectSelectionIfNeeded() || this.pageScroll(33);
                } else {
                    bl = bl2;
                    if (keyEvent.hasModifiers(2)) {
                        bl = this.resurrectSelectionIfNeeded() || this.fullScroll(33);
                    }
                }
                break block42;
            }
            if (keyEvent.hasNoModifiers()) {
                bl = this.resurrectSelectionIfNeeded() || this.sequenceScroll(2);
            } else {
                bl = bl2;
                if (keyEvent.hasModifiers(1)) {
                    bl = this.resurrectSelectionIfNeeded() || this.sequenceScroll(1);
                }
            }
        }
        if (bl) {
            return true;
        }
        if (this.sendToTextFilter(n, n2, keyEvent)) {
            return true;
        }
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 != 2) {
                    return false;
                }
                return super.onKeyMultiple(n, n2, keyEvent);
            }
            return super.onKeyUp(n, keyEvent);
        }
        return super.onKeyDown(n, keyEvent);
    }

    private void correctTooHigh(int n, int n2, int n3) {
        int n4 = this.mFirstPosition;
        int n5 = 1;
        if (n4 + n3 - 1 == this.mItemCount - 1 && n3 > 0) {
            n3 = this.getChildAt(n3 - 1).getBottom();
            n4 = this.mBottom - this.mTop - this.mListPadding.bottom - n3;
            View view = this.getChildAt(0);
            int n6 = view.getTop();
            if (n4 > 0 && (this.mFirstPosition > 0 || n6 < this.mListPadding.top)) {
                n3 = n4;
                if (this.mFirstPosition == 0) {
                    n3 = Math.min(n4, this.mListPadding.top - n6);
                }
                this.offsetChildrenTopAndBottom(n3);
                if (this.mFirstPosition > 0) {
                    n3 = this.mFirstPosition;
                    if (this.mStackFromBottom) {
                        n = n5;
                    }
                    this.fillUp(n3 - n, view.getTop() - n2);
                    this.adjustViewsUpOrDown();
                }
            }
        }
    }

    private void correctTooLow(int n, int n2, int n3) {
        if (this.mFirstPosition == 0 && n3 > 0) {
            int n4 = this.getChildAt(0).getTop();
            int n5 = this.mListPadding.top;
            int n6 = this.mBottom - this.mTop - this.mListPadding.bottom;
            n5 = n4 - n5;
            View view = this.getChildAt(n3 - 1);
            int n7 = view.getBottom();
            int n8 = this.mFirstPosition;
            n4 = 1;
            n8 = n8 + n3 - 1;
            if (n5 > 0 && (n8 < this.mItemCount - 1 || n7 > n6)) {
                n3 = n5;
                if (n8 == this.mItemCount - 1) {
                    n3 = Math.min(n5, n7 - n6);
                }
                this.offsetChildrenTopAndBottom(-n3);
                if (n8 < this.mItemCount - 1) {
                    if (!this.mStackFromBottom) {
                        n = n4;
                    }
                    this.fillDown(n + n8, view.getBottom() + n2);
                    this.adjustViewsUpOrDown();
                }
            }
        }
    }

    @UnsupportedAppUsage
    private boolean determineColumns(int n) {
        int n2 = this.mRequestedHorizontalSpacing;
        int n3 = this.mStretchMode;
        int n4 = this.mRequestedColumnWidth;
        boolean bl = false;
        boolean bl2 = false;
        int n5 = this.mRequestedNumColumns;
        this.mNumColumns = n5 == -1 ? (n4 > 0 ? (n + n2) / (n4 + n2) : 2) : n5;
        if (this.mNumColumns <= 0) {
            this.mNumColumns = 1;
        }
        if (n3 != 0) {
            n5 = this.mNumColumns;
            if ((n = n - n5 * n4 - (n5 - 1) * n2) < 0) {
                bl2 = true;
            }
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 == 3) {
                        this.mColumnWidth = n4;
                        n4 = this.mNumColumns;
                        this.mHorizontalSpacing = n4 > 1 ? n / (n4 + 1) + n2 : n2 + n;
                    }
                } else {
                    this.mColumnWidth = n / this.mNumColumns + n4;
                    this.mHorizontalSpacing = n2;
                }
            } else {
                this.mColumnWidth = n4;
                n4 = this.mNumColumns;
                this.mHorizontalSpacing = n4 > 1 ? n / (n4 - 1) + n2 : n2 + n;
            }
        } else {
            this.mColumnWidth = n4;
            this.mHorizontalSpacing = n2;
            bl2 = bl;
        }
        return bl2;
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
        while (n6 < n4 && n5 < this.mItemCount) {
            view = this.makeRow(n5, n6, true);
            if (view != null) {
                view2 = view;
            }
            n6 = this.mReferenceView.getBottom() + this.mVerticalSpacing;
            n5 += this.mNumColumns;
        }
        this.setVisibleRangeHint(this.mFirstPosition, this.mFirstPosition + this.getChildCount() - 1);
        return view2;
    }

    private View fillFromBottom(int n, int n2) {
        n = Math.min(Math.max(n, this.mSelectedPosition), this.mItemCount - 1);
        n = this.mItemCount - 1 - n;
        return this.fillUp(this.mItemCount - 1 - (n - n % this.mNumColumns), n2);
    }

    private View fillFromSelection(int n, int n2, int n3) {
        int n4 = this.getVerticalFadingEdgeLength();
        int n5 = this.mSelectedPosition;
        int n6 = this.mNumColumns;
        int n7 = this.mVerticalSpacing;
        int n8 = -1;
        if (!this.mStackFromBottom) {
            n5 -= n5 % n6;
        } else {
            n8 = this.mItemCount - 1 - n5;
            n8 = this.mItemCount - 1 - (n8 - n8 % n6);
            n5 = Math.max(0, n8 - n6 + 1);
        }
        int n9 = this.getTopSelectionPixel(n2, n4, n5);
        n3 = this.getBottomSelectionPixel(n3, n4, n6, n5);
        n2 = this.mStackFromBottom ? n8 : n5;
        View view = this.makeRow(n2, n, true);
        this.mFirstPosition = n5;
        View view2 = this.mReferenceView;
        this.adjustForTopFadingEdge(view2, n9, n3);
        this.adjustForBottomFadingEdge(view2, n9, n3);
        if (!this.mStackFromBottom) {
            this.fillUp(n5 - n6, view2.getTop() - n7);
            this.adjustViewsUpOrDown();
            this.fillDown(n5 + n6, view2.getBottom() + n7);
        } else {
            this.fillDown(n8 + n6, view2.getBottom() + n7);
            this.adjustViewsUpOrDown();
            this.fillUp(n5 - 1, view2.getTop() - n7);
        }
        return view;
    }

    private View fillFromTop(int n) {
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mSelectedPosition);
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mItemCount - 1);
        if (this.mFirstPosition < 0) {
            this.mFirstPosition = 0;
        }
        this.mFirstPosition -= this.mFirstPosition % this.mNumColumns;
        return this.fillDown(this.mFirstPosition, n);
    }

    private View fillSelection(int n, int n2) {
        int n3 = this.reconcileSelectedPosition();
        int n4 = this.mNumColumns;
        int n5 = this.mVerticalSpacing;
        int n6 = -1;
        if (!this.mStackFromBottom) {
            n3 -= n3 % n4;
        } else {
            n3 = this.mItemCount - 1 - n3;
            n6 = this.mItemCount - 1 - (n3 - n3 % n4);
            n3 = Math.max(0, n6 - n4 + 1);
        }
        int n7 = this.getVerticalFadingEdgeLength();
        int n8 = this.getTopSelectionPixel(n, n7, n3);
        int n9 = this.mStackFromBottom ? n6 : n3;
        View view = this.makeRow(n9, n8, true);
        this.mFirstPosition = n3;
        View view2 = this.mReferenceView;
        if (!this.mStackFromBottom) {
            this.fillDown(n3 + n4, view2.getBottom() + n5);
            this.pinToBottom(n2);
            this.fillUp(n3 - n4, view2.getTop() - n5);
            this.adjustViewsUpOrDown();
        } else {
            this.offsetChildrenTopAndBottom(this.getBottomSelectionPixel(n2, n7, n4, n3) - view2.getBottom());
            this.fillUp(n3 - 1, view2.getTop() - n5);
            this.pinToTop(n);
            this.fillDown(n6 + n4, view2.getBottom() + n5);
            this.adjustViewsUpOrDown();
        }
        return view;
    }

    private View fillSpecific(int n, int n2) {
        View view;
        int n3 = this.mNumColumns;
        int n4 = -1;
        if (!this.mStackFromBottom) {
            n -= n % n3;
        } else {
            n = this.mItemCount - 1 - n;
            n4 = this.mItemCount - 1 - (n - n % n3);
            n = Math.max(0, n4 - n3 + 1);
        }
        int n5 = this.mStackFromBottom ? n4 : n;
        View view2 = this.makeRow(n5, n2, true);
        this.mFirstPosition = n;
        View view3 = this.mReferenceView;
        if (view3 == null) {
            return null;
        }
        n2 = this.mVerticalSpacing;
        if (!this.mStackFromBottom) {
            view = this.fillUp(n - n3, view3.getTop() - n2);
            this.adjustViewsUpOrDown();
            view3 = this.fillDown(n + n3, view3.getBottom() + n2);
            n = this.getChildCount();
            if (n > 0) {
                this.correctTooHigh(n3, n2, n);
            }
        } else {
            View view4 = this.fillDown(n4 + n3, view3.getBottom() + n2);
            this.adjustViewsUpOrDown();
            View view5 = this.fillUp(n - 1, view3.getTop() - n2);
            n = this.getChildCount();
            view = view5;
            view3 = view4;
            if (n > 0) {
                this.correctTooLow(n3, n2, n);
                view3 = view4;
                view = view5;
            }
        }
        if (view2 != null) {
            return view2;
        }
        if (view != null) {
            return view;
        }
        return view3;
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
        while (n5 > n3 && n4 >= 0) {
            view = this.makeRow(n4, n5, false);
            if (view != null) {
                view2 = view;
            }
            n5 = this.mReferenceView.getTop() - this.mVerticalSpacing;
            this.mFirstPosition = n4;
            n4 -= this.mNumColumns;
        }
        if (this.mStackFromBottom) {
            this.mFirstPosition = Math.max(0, n4 + 1);
        }
        this.setVisibleRangeHint(this.mFirstPosition, this.mFirstPosition + this.getChildCount() - 1);
        return view2;
    }

    private int getBottomSelectionPixel(int n, int n2, int n3, int n4) {
        int n5 = n;
        if (n4 + n3 - 1 < this.mItemCount - 1) {
            n5 = n - n2;
        }
        return n5;
    }

    private int getTopSelectionPixel(int n, int n2, int n3) {
        int n4 = n;
        if (n3 > 0) {
            n4 = n + n2;
        }
        return n4;
    }

    private boolean isCandidateSelection(int n, int n2) {
        int n3;
        int n4 = this.getChildCount();
        int n5 = n4 - 1 - n;
        boolean bl = this.mStackFromBottom;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        boolean bl6 = false;
        boolean bl7 = false;
        if (!bl) {
            n5 = this.mNumColumns;
            n3 = n - n % n5;
            n5 = Math.min(n5 + n3 - 1, n4);
        } else {
            n3 = this.mNumColumns;
            n5 = n4 - 1 - (n5 - n5 % n3);
            n3 = Math.max(0, n5 - n3 + 1);
        }
        if (n2 != 1) {
            if (n2 != 2) {
                if (n2 != 17) {
                    if (n2 != 33) {
                        if (n2 != 66) {
                            if (n2 == 130) {
                                if (n3 == 0) {
                                    bl7 = true;
                                }
                                return bl7;
                            }
                            throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT, FOCUS_FORWARD, FOCUS_BACKWARD}.");
                        }
                        bl7 = bl2;
                        if (n == n3) {
                            bl7 = true;
                        }
                        return bl7;
                    }
                    bl7 = bl3;
                    if (n5 == n4 - 1) {
                        bl7 = true;
                    }
                    return bl7;
                }
                bl7 = bl4;
                if (n == n5) {
                    bl7 = true;
                }
                return bl7;
            }
            bl7 = bl5;
            if (n == n3) {
                bl7 = bl5;
                if (n3 == 0) {
                    bl7 = true;
                }
            }
            return bl7;
        }
        bl7 = bl6;
        if (n == n5) {
            bl7 = bl6;
            if (n5 == n4 - 1) {
                bl7 = true;
            }
        }
        return bl7;
    }

    private View makeAndAddView(int n, int n2, boolean bl, int n3, boolean bl2, int n4) {
        View view;
        if (!this.mDataChanged && (view = this.mRecycler.getActiveView(n)) != null) {
            this.setupChild(view, n, n2, bl, n3, bl2, true, n4);
            return view;
        }
        view = this.obtainView(n, this.mIsScrap);
        this.setupChild(view, n, n2, bl, n3, bl2, this.mIsScrap[0], n4);
        return view;
    }

    private View makeRow(int n, int n2, boolean bl) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7 = this.mColumnWidth;
        int n8 = this.mHorizontalSpacing;
        boolean bl2 = this.isLayoutRtl();
        if (bl2) {
            n5 = this.getWidth();
            n3 = this.mListPadding.right;
            n4 = this.mStretchMode == 3 ? n8 : 0;
            n3 = n5 - n3 - n7 - n4;
        } else {
            n3 = this.mListPadding.left;
            n4 = this.mStretchMode == 3 ? n8 : 0;
            n3 += n4;
        }
        if (!this.mStackFromBottom) {
            n4 = Math.min(n + this.mNumColumns, this.mItemCount);
        } else {
            n4 = n + 1;
            if (n4 - (n = Math.max(0, n - this.mNumColumns + 1)) < (n6 = this.mNumColumns)) {
                n5 = bl2 ? -1 : 1;
                n3 += n5 * ((n6 - (n4 - n)) * (n7 + n8));
            }
        }
        boolean bl3 = this.shouldShowSelector();
        boolean bl4 = this.touchModeDrawsInPressedState();
        n6 = this.mSelectedPosition;
        n5 = bl2 ? -1 : 1;
        View view = null;
        View view2 = null;
        for (int i = n; i < n4; ++i) {
            View view3;
            block11 : {
                block12 : {
                    bl2 = i == n6;
                    int n9 = bl ? -1 : i - n;
                    view2 = this.makeAndAddView(i, n2, bl, n3, bl2, n9);
                    n3 = n9 = n3 + n5 * n7;
                    if (i < n4 - 1) {
                        n3 = n9 + n5 * n8;
                    }
                    view3 = view;
                    if (!bl2) break block11;
                    if (bl3) break block12;
                    view3 = view;
                    if (!bl4) break block11;
                }
                view3 = view2;
            }
            view = view3;
        }
        this.mReferenceView = view2;
        if (view != null) {
            this.mReferenceViewInSelectedRow = this.mReferenceView;
        }
        return view;
    }

    private View moveSelection(int n, int n2, int n3) {
        View view;
        View view2;
        int n4;
        int n5 = this.getVerticalFadingEdgeLength();
        int n6 = this.mSelectedPosition;
        int n7 = this.mNumColumns;
        int n8 = this.mVerticalSpacing;
        int n9 = -1;
        boolean bl = this.mStackFromBottom;
        int n10 = 0;
        int n11 = 0;
        if (!bl) {
            n4 = n6 - n - (n6 - n) % n7;
            n = n6 - n6 % n7;
        } else {
            n9 = this.mItemCount - 1 - n6;
            n9 = this.mItemCount - 1 - (n9 - n9 % n7);
            n4 = Math.max(0, n9 - n7 + 1);
            n = this.mItemCount - 1 - (n6 - n);
            n6 = Math.max(0, this.mItemCount - 1 - (n - n % n7) - n7 + 1);
            n = n4;
            n4 = n6;
        }
        int n12 = n - n4;
        n4 = this.getTopSelectionPixel(n2, n5, n);
        n6 = this.getBottomSelectionPixel(n3, n5, n7, n);
        this.mFirstPosition = n;
        if (n12 > 0) {
            view2 = this.mReferenceViewInSelectedRow;
            n2 = view2 == null ? n11 : view2.getBottom();
            n3 = this.mStackFromBottom ? n9 : n;
            view2 = this.makeRow(n3, n2 + n8, true);
            view = this.mReferenceView;
            this.adjustForBottomFadingEdge(view, n4, n6);
        } else if (n12 < 0) {
            view2 = this.mReferenceViewInSelectedRow;
            n2 = view2 == null ? 0 : view2.getTop();
            n3 = this.mStackFromBottom ? n9 : n;
            view2 = this.makeRow(n3, n2 - n8, false);
            view = this.mReferenceView;
            this.adjustForTopFadingEdge(view, n4, n6);
        } else {
            view2 = this.mReferenceViewInSelectedRow;
            n2 = view2 == null ? n10 : view2.getTop();
            n3 = this.mStackFromBottom ? n9 : n;
            view2 = this.makeRow(n3, n2, true);
            view = this.mReferenceView;
        }
        if (!this.mStackFromBottom) {
            this.fillUp(n - n7, view.getTop() - n8);
            this.adjustViewsUpOrDown();
            this.fillDown(n + n7, view.getBottom() + n8);
        } else {
            this.fillDown(n9 + n7, view.getBottom() + n8);
            this.adjustViewsUpOrDown();
            this.fillUp(n - 1, view.getTop() - n8);
        }
        return view2;
    }

    private void pinToBottom(int n) {
        int n2 = this.getChildCount();
        if (this.mFirstPosition + n2 == this.mItemCount && (n -= this.getChildAt(n2 - 1).getBottom()) > 0) {
            this.offsetChildrenTopAndBottom(n);
        }
    }

    private void pinToTop(int n) {
        if (this.mFirstPosition == 0 && (n -= this.getChildAt(0).getTop()) < 0) {
            this.offsetChildrenTopAndBottom(n);
        }
    }

    private void setupChild(View view, int n, int n2, boolean bl, int n3, boolean bl2, boolean bl3, int n4) {
        AbsListView.LayoutParams layoutParams;
        Trace.traceBegin(8L, "setupGridItem");
        bl2 = bl2 && this.shouldShowSelector();
        int n5 = bl2 != view.isSelected() ? 1 : 0;
        int n6 = this.mTouchMode;
        boolean bl4 = n6 > 0 && n6 < 3 && this.mMotionPosition == n;
        boolean bl5 = bl4 != view.isPressed();
        n6 = bl3 && n5 == 0 && !view.isLayoutRequested() ? 0 : 1;
        AbsListView.LayoutParams layoutParams2 = layoutParams = (AbsListView.LayoutParams)view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams2 = (AbsListView.LayoutParams)this.generateDefaultLayoutParams();
        }
        layoutParams2.viewType = this.mAdapter.getItemViewType(n);
        layoutParams2.isEnabled = this.mAdapter.isEnabled(n);
        if (n5 != 0) {
            view.setSelected(bl2);
            if (bl2) {
                this.requestFocus();
            }
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
        if (bl3 && !layoutParams2.forceAdd) {
            this.attachViewToParent(view, n4, layoutParams2);
            if (!bl3 || ((AbsListView.LayoutParams)view.getLayoutParams()).scrappedFromPosition != n) {
                view.jumpDrawablesToCurrentState();
            }
        } else {
            layoutParams2.forceAdd = false;
            this.addViewInLayout(view, n4, layoutParams2, true);
        }
        if (n6 != 0) {
            n = ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(0, 0), 0, layoutParams2.height);
            view.measure(ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(this.mColumnWidth, 1073741824), 0, layoutParams2.width), n);
        } else {
            this.cleanupLayoutState(view);
        }
        n4 = view.getMeasuredWidth();
        n5 = view.getMeasuredHeight();
        if (!bl) {
            n2 -= n5;
        }
        n = this.getLayoutDirection();
        n = Gravity.getAbsoluteGravity(this.mGravity, n) & 7;
        n = n != 1 ? (n != 3 ? (n != 5 ? n3 : n3 + this.mColumnWidth - n4) : n3) : n3 + (this.mColumnWidth - n4) / 2;
        if (n6 != 0) {
            view.layout(n, n2, n + n4, n2 + n5);
        } else {
            view.offsetLeftAndRight(n - view.getLeft());
            view.offsetTopAndBottom(n2 - view.getTop());
        }
        if (this.mCachingStarted && !view.isDrawingCacheEnabled()) {
            view.setDrawingCacheEnabled(true);
        }
        Trace.traceEnd(8L);
    }

    boolean arrowScroll(int n) {
        boolean bl;
        block13 : {
            int n2;
            block14 : {
                boolean bl2;
                boolean bl3;
                int n3;
                block12 : {
                    int n4;
                    n2 = this.mSelectedPosition;
                    int n5 = this.mNumColumns;
                    bl2 = false;
                    if (!this.mStackFromBottom) {
                        n4 = n2 / n5 * n5;
                        n3 = Math.min(n4 + n5 - 1, this.mItemCount - 1);
                    } else {
                        n3 = this.mItemCount;
                        n3 = this.mItemCount - 1 - (n3 - 1 - n2) / n5 * n5;
                        n4 = Math.max(0, n3 - n5 + 1);
                    }
                    if (n != 33) {
                        if (n == 130 && n3 < this.mItemCount - 1) {
                            this.mLayoutMode = 6;
                            this.setSelectionInt(Math.min(n2 + n5, this.mItemCount - 1));
                            bl2 = true;
                        }
                    } else if (n4 > 0) {
                        this.mLayoutMode = 6;
                        this.setSelectionInt(Math.max(0, n2 - n5));
                        bl2 = true;
                    }
                    bl3 = this.isLayoutRtl();
                    if (n2 <= n4 || (n != 17 || bl3) && (n != 66 || !bl3)) break block12;
                    this.mLayoutMode = 6;
                    this.setSelectionInt(Math.max(0, n2 - 1));
                    bl = true;
                    break block13;
                }
                bl = bl2;
                if (n2 >= n3) break block13;
                if (n == 17 && bl3) break block14;
                bl = bl2;
                if (n != 66) break block13;
                bl = bl2;
                if (bl3) break block13;
            }
            this.mLayoutMode = 6;
            this.setSelectionInt(Math.min(n2 + 1, this.mItemCount - 1));
            bl = true;
        }
        if (bl) {
            this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(n));
            this.invokeOnItemScrollListener();
        }
        if (bl) {
            this.awakenScrollBars();
        }
        return bl;
    }

    @Override
    protected void attachLayoutAnimationParameters(View object, ViewGroup.LayoutParams layoutParams, int n, int n2) {
        int n3;
        GridLayoutAnimationController.AnimationParameters animationParameters = (GridLayoutAnimationController.AnimationParameters)layoutParams.layoutAnimationParameters;
        object = animationParameters;
        if (animationParameters == null) {
            layoutParams.layoutAnimationParameters = object = new GridLayoutAnimationController.AnimationParameters();
        }
        ((GridLayoutAnimationController.AnimationParameters)object).count = n2;
        ((GridLayoutAnimationController.AnimationParameters)object).index = n;
        ((GridLayoutAnimationController.AnimationParameters)object).columnsCount = n3 = this.mNumColumns;
        ((GridLayoutAnimationController.AnimationParameters)object).rowsCount = n2 / n3;
        if (!this.mStackFromBottom) {
            n2 = this.mNumColumns;
            ((GridLayoutAnimationController.AnimationParameters)object).column = n % n2;
            ((GridLayoutAnimationController.AnimationParameters)object).row = n / n2;
        } else {
            n2 = n2 - 1 - n;
            n = this.mNumColumns;
            ((GridLayoutAnimationController.AnimationParameters)object).column = n - 1 - n2 % n;
            ((GridLayoutAnimationController.AnimationParameters)object).row = ((GridLayoutAnimationController.AnimationParameters)object).rowsCount - 1 - n2 / this.mNumColumns;
        }
    }

    @Override
    protected int computeVerticalScrollExtent() {
        int n = this.getChildCount();
        if (n > 0) {
            int n2 = this.mNumColumns;
            int n3 = (n + n2 - 1) / n2 * 100;
            View view = this.getChildAt(0);
            int n4 = view.getTop();
            int n5 = view.getHeight();
            n2 = n3;
            if (n5 > 0) {
                n2 = n3 + n4 * 100 / n5;
            }
            view = this.getChildAt(n - 1);
            n5 = view.getBottom();
            n = view.getHeight();
            n3 = n2;
            if (n > 0) {
                n3 = n2 - (n5 - this.getHeight()) * 100 / n;
            }
            return n3;
        }
        return 0;
    }

    @Override
    protected int computeVerticalScrollOffset() {
        if (this.mFirstPosition >= 0 && this.getChildCount() > 0) {
            View view = this.getChildAt(0);
            int n = view.getTop();
            int n2 = view.getHeight();
            if (n2 > 0) {
                int n3 = this.mNumColumns;
                int n4 = (this.mItemCount + n3 - 1) / n3;
                int n5 = this.isStackFromBottom() ? n4 * n3 - this.mItemCount : 0;
                return Math.max((this.mFirstPosition + n5) / n3 * 100 - n * 100 / n2 + (int)((float)this.mScrollY / (float)this.getHeight() * (float)n4 * 100.0f), 0);
            }
        }
        return 0;
    }

    @Override
    protected int computeVerticalScrollRange() {
        int n;
        int n2 = this.mNumColumns;
        int n3 = (this.mItemCount + n2 - 1) / n2;
        n2 = n = Math.max(n3 * 100, 0);
        if (this.mScrollY != 0) {
            n2 = n + Math.abs((int)((float)this.mScrollY / (float)this.getHeight() * (float)n3 * 100.0f));
        }
        return n2;
    }

    @Override
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("numColumns", this.getNumColumns());
    }

    @Override
    void fillGap(boolean bl) {
        int n = this.mNumColumns;
        int n2 = this.mVerticalSpacing;
        int n3 = this.getChildCount();
        if (bl) {
            int n4;
            int n5 = 0;
            if ((this.mGroupFlags & 34) == 34) {
                n5 = this.getListPaddingTop();
            }
            if (n3 > 0) {
                n5 = this.getChildAt(n3 - 1).getBottom() + n2;
            }
            n3 = n4 = this.mFirstPosition + n3;
            if (this.mStackFromBottom) {
                n3 = n4 + (n - 1);
            }
            this.fillDown(n3, n5);
            this.correctTooHigh(n, n2, this.getChildCount());
        } else {
            int n6 = 0;
            if ((this.mGroupFlags & 34) == 34) {
                n6 = this.getListPaddingBottom();
            }
            n6 = n3 > 0 ? this.getChildAt(0).getTop() - n2 : this.getHeight() - n6;
            n3 = this.mFirstPosition;
            n3 = !this.mStackFromBottom ? (n3 -= n) : --n3;
            this.fillUp(n3, n6);
            this.correctTooLow(n, n2, this.getChildCount());
        }
    }

    @Override
    int findMotionRow(int n) {
        block4 : {
            int n2 = this.getChildCount();
            if (n2 <= 0) break block4;
            int n3 = this.mNumColumns;
            if (!this.mStackFromBottom) {
                for (int i = 0; i < n2; i += n3) {
                    if (n > this.getChildAt(i).getBottom()) continue;
                    return this.mFirstPosition + i;
                }
            } else {
                for (int i = n2 - 1; i >= 0; i -= n3) {
                    if (n < this.getChildAt(i).getTop()) continue;
                    return this.mFirstPosition + i;
                }
            }
        }
        return -1;
    }

    boolean fullScroll(int n) {
        boolean bl = false;
        if (n == 33) {
            this.mLayoutMode = 2;
            this.setSelectionInt(0);
            this.invokeOnItemScrollListener();
            bl = true;
        } else if (n == 130) {
            this.mLayoutMode = 2;
            this.setSelectionInt(this.mItemCount - 1);
            this.invokeOnItemScrollListener();
            bl = true;
        }
        if (bl) {
            this.awakenScrollBars();
        }
        return bl;
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return GridView.class.getName();
    }

    @Override
    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    public int getColumnWidth() {
        return this.mColumnWidth;
    }

    public int getGravity() {
        return this.mGravity;
    }

    public int getHorizontalSpacing() {
        return this.mHorizontalSpacing;
    }

    @ViewDebug.ExportedProperty
    public int getNumColumns() {
        return this.mNumColumns;
    }

    public int getRequestedColumnWidth() {
        return this.mRequestedColumnWidth;
    }

    public int getRequestedHorizontalSpacing() {
        return this.mRequestedHorizontalSpacing;
    }

    public int getStretchMode() {
        return this.mStretchMode;
    }

    public int getVerticalSpacing() {
        return this.mVerticalSpacing;
    }

    /*
     * Exception decompiling
     */
    @Override
    protected void layoutChildren() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[TRYBLOCK]], but top level block is 17[CASE]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    int lookForSelectablePosition(int n, boolean bl) {
        if (this.mAdapter != null && !this.isInTouchMode()) {
            if (n >= 0 && n < this.mItemCount) {
                return n;
            }
            return -1;
        }
        return -1;
    }

    @Override
    protected void onFocusChanged(boolean bl, int n, Rect rect) {
        int n2;
        super.onFocusChanged(bl, n, rect);
        int n3 = n2 = -1;
        if (bl) {
            n3 = n2;
            if (rect != null) {
                rect.offset(this.mScrollX, this.mScrollY);
                Rect rect2 = this.mTempRect;
                int n4 = Integer.MAX_VALUE;
                int n5 = this.getChildCount();
                int n6 = 0;
                do {
                    n3 = n2;
                    if (n6 >= n5) break;
                    if (!this.isCandidateSelection(n6, n)) {
                        n3 = n4;
                    } else {
                        View view = this.getChildAt(n6);
                        view.getDrawingRect(rect2);
                        this.offsetDescendantRectToMyCoords(view, rect2);
                        int n7 = GridView.getDistance(rect, rect2, n);
                        n3 = n4;
                        if (n7 < n4) {
                            n3 = n7;
                            n2 = n6;
                        }
                    }
                    ++n6;
                    n4 = n3;
                } while (true);
            }
        }
        if (n3 >= 0) {
            this.setSelection(this.mFirstPosition + n3);
        } else {
            this.requestLayout();
        }
    }

    @Override
    public void onInitializeAccessibilityNodeInfoForItem(View object, int n, AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoForItem((View)object, n, accessibilityNodeInfo);
        int n2 = this.getCount();
        int n3 = this.getNumColumns();
        int n4 = n2 / n3;
        if (!this.mStackFromBottom) {
            n2 = n % n3;
            n4 = n / n3;
        } else {
            n2 = n2 - 1 - n;
            n4 = n4 - 1 - n2 / n3;
            n2 = n3 - 1 - n2 % n3;
        }
        object = (AbsListView.LayoutParams)((View)object).getLayoutParams();
        boolean bl = object != null && ((AbsListView.LayoutParams)object).viewType == -2;
        accessibilityNodeInfo.setCollectionItemInfo(AccessibilityNodeInfo.CollectionItemInfo.obtain(n4, 1, n2, 1, bl, this.isItemChecked(n)));
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        int n = this.getNumColumns();
        int n2 = this.getCount() / n;
        accessibilityNodeInfo.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(n2, n, false, this.getSelectionModeForAccessibility()));
        if (n > 0 || n2 > 0) {
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
        block12 : {
            int n4;
            block13 : {
                super.onMeasure(n, n2);
                int n5 = View.MeasureSpec.getMode(n);
                int n6 = View.MeasureSpec.getMode(n2);
                n4 = View.MeasureSpec.getSize(n);
                int n7 = View.MeasureSpec.getSize(n2);
                if (n5 == 0) {
                    n4 = this.mColumnWidth;
                    n4 = n4 > 0 ? n4 + this.mListPadding.left + this.mListPadding.right : this.mListPadding.left + this.mListPadding.right;
                    n4 = this.getVerticalScrollbarWidth() + n4;
                }
                boolean bl = this.determineColumns(n4 - this.mListPadding.left - this.mListPadding.right);
                int n8 = 0;
                n3 = this.mAdapter == null ? 0 : this.mAdapter.getCount();
                this.mItemCount = n3;
                int n9 = this.mItemCount;
                if (n9 > 0) {
                    AbsListView.LayoutParams layoutParams;
                    View view = this.obtainView(0, this.mIsScrap);
                    AbsListView.LayoutParams layoutParams2 = layoutParams = (AbsListView.LayoutParams)view.getLayoutParams();
                    if (layoutParams == null) {
                        layoutParams2 = (AbsListView.LayoutParams)this.generateDefaultLayoutParams();
                        view.setLayoutParams(layoutParams2);
                    }
                    layoutParams2.viewType = this.mAdapter.getItemViewType(0);
                    layoutParams2.isEnabled = this.mAdapter.isEnabled(0);
                    layoutParams2.forceAdd = true;
                    n2 = GridView.getChildMeasureSpec(View.MeasureSpec.makeSafeMeasureSpec(View.MeasureSpec.getSize(n2), 0), 0, layoutParams2.height);
                    view.measure(GridView.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(this.mColumnWidth, 1073741824), 0, layoutParams2.width), n2);
                    n2 = view.getMeasuredHeight();
                    GridView.combineMeasuredStates(0, view.getMeasuredState());
                    n8 = n2;
                    if (this.mRecycler.shouldRecycleViewType(layoutParams2.viewType)) {
                        this.mRecycler.addScrapView(view, -1);
                        n8 = n2;
                    }
                }
                n2 = n7;
                if (n6 == 0) {
                    n2 = this.mListPadding.top + this.mListPadding.bottom + n8 + this.getVerticalFadingEdgeLength() * 2;
                }
                n3 = n2;
                if (n6 == Integer.MIN_VALUE) {
                    n3 = this.mListPadding.top + this.mListPadding.bottom;
                    int n10 = this.mNumColumns;
                    n6 = 0;
                    do {
                        n7 = n3;
                        if (n6 >= n9) break;
                        n3 = n7 = n3 + n8;
                        if (n6 + n10 < n9) {
                            n3 = n7 + this.mVerticalSpacing;
                        }
                        if (n3 >= n2) {
                            n7 = n2;
                            break;
                        }
                        n6 += n10;
                    } while (true);
                    n3 = n7;
                }
                n2 = n4;
                if (n5 != Integer.MIN_VALUE) break block12;
                n7 = this.mRequestedNumColumns;
                n2 = n4;
                if (n7 == -1) break block12;
                if (this.mColumnWidth * n7 + (n7 - 1) * this.mHorizontalSpacing + this.mListPadding.left + this.mListPadding.right > n4) break block13;
                n2 = n4;
                if (!bl) break block12;
            }
            n2 = n4 | 16777216;
        }
        this.setMeasuredDimension(n2, n3);
        this.mWidthMeasureSpec = n;
    }

    boolean pageScroll(int n) {
        int n2 = -1;
        if (n == 33) {
            n2 = Math.max(0, this.mSelectedPosition - this.getChildCount());
        } else if (n == 130) {
            n2 = Math.min(this.mItemCount - 1, this.mSelectedPosition + this.getChildCount());
        }
        if (n2 >= 0) {
            this.setSelectionInt(n2);
            this.invokeOnItemScrollListener();
            this.awakenScrollBars();
            return true;
        }
        return false;
    }

    @Override
    public boolean performAccessibilityActionInternal(int n, Bundle bundle) {
        if (super.performAccessibilityActionInternal(n, bundle)) {
            return true;
        }
        if (n == 16908343) {
            int n2 = this.getNumColumns();
            n = bundle.getInt("android.view.accessibility.action.ARGUMENT_ROW_INT", -1);
            n2 = Math.min(n * n2, this.getCount() - 1);
            if (n >= 0) {
                this.smoothScrollToPosition(n2);
                return true;
            }
        }
        return false;
    }

    @UnsupportedAppUsage
    boolean sequenceScroll(int n) {
        int n2;
        int n3;
        int n4 = this.mSelectedPosition;
        int n5 = this.mNumColumns;
        int n6 = this.mItemCount;
        boolean bl = this.mStackFromBottom;
        int n7 = 0;
        int n8 = 0;
        if (!bl) {
            n3 = n4 / n5 * n5;
            n2 = Math.min(n3 + n5 - 1, n6 - 1);
        } else {
            n2 = n6 - 1 - (n6 - 1 - n4) / n5 * n5;
            n3 = Math.max(0, n2 - n5 + 1);
        }
        bl = false;
        n5 = 0;
        if (n != 1) {
            if (n == 2 && n4 < n6 - 1) {
                this.mLayoutMode = 6;
                this.setSelectionInt(n4 + 1);
                bl = true;
                n5 = n8;
                if (n4 == n2) {
                    n5 = 1;
                }
            }
        } else if (n4 > 0) {
            this.mLayoutMode = 6;
            this.setSelectionInt(n4 - 1);
            bl = true;
            n5 = n7;
            if (n4 == n3) {
                n5 = 1;
            }
        }
        if (bl) {
            this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(n));
            this.invokeOnItemScrollListener();
        }
        if (n5 != 0) {
            this.awakenScrollBars();
        }
        return bl;
    }

    @Override
    public void setAdapter(ListAdapter listAdapter) {
        if (this.mAdapter != null && this.mDataSetObserver != null) {
            this.mAdapter.unregisterDataSetObserver((DataSetObserver)((Object)this.mDataSetObserver));
        }
        this.resetList();
        this.mRecycler.clear();
        this.mAdapter = listAdapter;
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        super.setAdapter(listAdapter);
        if (this.mAdapter != null) {
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
            this.mDataChanged = true;
            this.checkFocus();
            this.mDataSetObserver = new AbsListView.AdapterDataSetObserver();
            this.mAdapter.registerDataSetObserver((DataSetObserver)((Object)this.mDataSetObserver));
            this.mRecycler.setViewTypeCount(this.mAdapter.getViewTypeCount());
            int n = this.mStackFromBottom ? this.lookForSelectablePosition(this.mItemCount - 1, false) : this.lookForSelectablePosition(0, true);
            this.setSelectedPositionInt(n);
            this.setNextSelectedPositionInt(n);
            this.checkSelectionChanged();
        } else {
            this.checkFocus();
            this.checkSelectionChanged();
        }
        this.requestLayout();
    }

    public void setColumnWidth(int n) {
        if (n != this.mRequestedColumnWidth) {
            this.mRequestedColumnWidth = n;
            this.requestLayoutIfNecessary();
        }
    }

    public void setGravity(int n) {
        if (this.mGravity != n) {
            this.mGravity = n;
            this.requestLayoutIfNecessary();
        }
    }

    public void setHorizontalSpacing(int n) {
        if (n != this.mRequestedHorizontalSpacing) {
            this.mRequestedHorizontalSpacing = n;
            this.requestLayoutIfNecessary();
        }
    }

    public void setNumColumns(int n) {
        if (n != this.mRequestedNumColumns) {
            this.mRequestedNumColumns = n;
            this.requestLayoutIfNecessary();
        }
    }

    @RemotableViewMethod(asyncImpl="setRemoteViewsAdapterAsync")
    @Override
    public void setRemoteViewsAdapter(Intent intent) {
        super.setRemoteViewsAdapter(intent);
    }

    @Override
    public void setSelection(int n) {
        if (!this.isInTouchMode()) {
            this.setNextSelectedPositionInt(n);
        } else {
            this.mResurrectToPosition = n;
        }
        this.mLayoutMode = 2;
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        this.requestLayout();
    }

    @Override
    void setSelectionInt(int n) {
        int n2;
        int n3 = this.mNextSelectedPosition;
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        this.setNextSelectedPositionInt(n);
        this.layoutChildren();
        n = this.mStackFromBottom ? this.mItemCount - 1 - this.mNextSelectedPosition : this.mNextSelectedPosition;
        if (this.mStackFromBottom) {
            n3 = this.mItemCount - 1 - n3;
        }
        if (n / (n2 = this.mNumColumns) != n3 / n2) {
            this.awakenScrollBars();
        }
    }

    public void setStretchMode(int n) {
        if (n != this.mStretchMode) {
            this.mStretchMode = n;
            this.requestLayoutIfNecessary();
        }
    }

    public void setVerticalSpacing(int n) {
        if (n != this.mVerticalSpacing) {
            this.mVerticalSpacing = n;
            this.requestLayoutIfNecessary();
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

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface StretchMode {
    }

}

