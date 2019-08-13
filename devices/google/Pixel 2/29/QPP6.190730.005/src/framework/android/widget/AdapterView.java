/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.ViewParent;
import android.view.ViewRootImpl;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityRecord;
import android.view.autofill.AutofillManager;
import android.widget.Adapter;

public abstract class AdapterView<T extends Adapter>
extends ViewGroup {
    public static final int INVALID_POSITION = -1;
    public static final long INVALID_ROW_ID = Long.MIN_VALUE;
    public static final int ITEM_VIEW_TYPE_HEADER_OR_FOOTER = -2;
    public static final int ITEM_VIEW_TYPE_IGNORE = -1;
    static final int SYNC_FIRST_POSITION = 1;
    static final int SYNC_MAX_DURATION_MILLIS = 100;
    static final int SYNC_SELECTED_POSITION = 0;
    boolean mBlockLayoutRequests = false;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123768524L)
    boolean mDataChanged;
    private boolean mDesiredFocusableInTouchModeState;
    private int mDesiredFocusableState = 16;
    private View mEmptyView;
    @ViewDebug.ExportedProperty(category="scrolling")
    @UnsupportedAppUsage
    int mFirstPosition = 0;
    boolean mInLayout = false;
    @ViewDebug.ExportedProperty(category="list")
    int mItemCount;
    private int mLayoutHeight;
    @UnsupportedAppUsage
    boolean mNeedSync = false;
    @ViewDebug.ExportedProperty(category="list")
    @UnsupportedAppUsage
    int mNextSelectedPosition = -1;
    @UnsupportedAppUsage
    long mNextSelectedRowId = Long.MIN_VALUE;
    int mOldItemCount;
    @UnsupportedAppUsage
    int mOldSelectedPosition = -1;
    long mOldSelectedRowId = Long.MIN_VALUE;
    @UnsupportedAppUsage
    OnItemClickListener mOnItemClickListener;
    OnItemLongClickListener mOnItemLongClickListener;
    @UnsupportedAppUsage
    OnItemSelectedListener mOnItemSelectedListener;
    private AdapterView<T> mPendingSelectionNotifier;
    @ViewDebug.ExportedProperty(category="list")
    @UnsupportedAppUsage
    int mSelectedPosition = -1;
    long mSelectedRowId = Long.MIN_VALUE;
    private AdapterView<T> mSelectionNotifier;
    int mSpecificTop;
    long mSyncHeight;
    int mSyncMode;
    @UnsupportedAppUsage
    int mSyncPosition;
    long mSyncRowId = Long.MIN_VALUE;

    public AdapterView(Context context) {
        this(context, null);
    }

    public AdapterView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AdapterView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public AdapterView(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        if (this.getImportantForAccessibility() == 0) {
            this.setImportantForAccessibility(1);
        }
        this.mDesiredFocusableState = this.getFocusable();
        if (this.mDesiredFocusableState == 16) {
            super.setFocusable(0);
        }
    }

    private void dispatchOnItemSelected() {
        this.fireOnSelected();
        this.performAccessibilityActionsOnSelected();
    }

    private void fireOnSelected() {
        if (this.mOnItemSelectedListener == null) {
            return;
        }
        int n = this.getSelectedItemPosition();
        if (n >= 0) {
            View view = this.getSelectedView();
            this.mOnItemSelectedListener.onItemSelected(this, view, n, this.getAdapter().getItemId(n));
        } else {
            this.mOnItemSelectedListener.onNothingSelected(this);
        }
    }

    private boolean isScrollableForAccessibility() {
        T t = this.getAdapter();
        boolean bl = false;
        if (t != null) {
            int n = t.getCount();
            if (n > 0 && (this.getFirstVisiblePosition() > 0 || this.getLastVisiblePosition() < n - 1)) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    private void performAccessibilityActionsOnSelected() {
        if (!AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            return;
        }
        if (this.getSelectedItemPosition() >= 0) {
            this.sendAccessibilityEvent(4);
        }
    }

    private void updateEmptyStatus(boolean bl) {
        if (this.isInFilterMode()) {
            bl = false;
        }
        if (bl) {
            View view = this.mEmptyView;
            if (view != null) {
                view.setVisibility(0);
                this.setVisibility(8);
            } else {
                this.setVisibility(0);
            }
            if (this.mDataChanged) {
                this.onLayout(false, this.mLeft, this.mTop, this.mRight, this.mBottom);
            }
        } else {
            View view = this.mEmptyView;
            if (view != null) {
                view.setVisibility(8);
            }
            this.setVisibility(0);
        }
    }

    @Override
    public void addView(View view) {
        throw new UnsupportedOperationException("addView(View) is not supported in AdapterView");
    }

    @Override
    public void addView(View view, int n) {
        throw new UnsupportedOperationException("addView(View, int) is not supported in AdapterView");
    }

    @Override
    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        throw new UnsupportedOperationException("addView(View, int, LayoutParams) is not supported in AdapterView");
    }

    @Override
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        throw new UnsupportedOperationException("addView(View, LayoutParams) is not supported in AdapterView");
    }

    @Override
    protected boolean canAnimate() {
        boolean bl = super.canAnimate() && this.mItemCount > 0;
        return bl;
    }

    void checkFocus() {
        T t = this.getAdapter();
        boolean bl = true;
        int n = t != null && t.getCount() != 0 ? 0 : 1;
        n = n != 0 && !this.isInFilterMode() ? 0 : 1;
        boolean bl2 = n != 0 && this.mDesiredFocusableInTouchModeState;
        super.setFocusableInTouchMode(bl2);
        n = n != 0 ? this.mDesiredFocusableState : 0;
        super.setFocusable(n);
        if (this.mEmptyView != null) {
            bl2 = bl;
            if (t != null) {
                bl2 = t.isEmpty() ? bl : false;
            }
            this.updateEmptyStatus(bl2);
        }
    }

    void checkSelectionChanged() {
        AdapterView<T> adapterView;
        if (this.mSelectedPosition != this.mOldSelectedPosition || this.mSelectedRowId != this.mOldSelectedRowId) {
            this.selectionChanged();
            this.mOldSelectedPosition = this.mSelectedPosition;
            this.mOldSelectedRowId = this.mSelectedRowId;
        }
        if ((adapterView = this.mPendingSelectionNotifier) != null) {
            ((SelectionNotifier)((Object)adapterView)).run();
        }
    }

    @Override
    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        View view = this.getSelectedView();
        return view != null && view.getVisibility() == 0 && view.dispatchPopulateAccessibilityEvent(accessibilityEvent);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        this.dispatchThawSelfOnly(sparseArray);
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        this.dispatchFreezeSelfOnly(sparseArray);
    }

    @Override
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("scrolling:firstPosition", this.mFirstPosition);
        viewHierarchyEncoder.addProperty("list:nextSelectedPosition", this.mNextSelectedPosition);
        viewHierarchyEncoder.addProperty("list:nextSelectedRowId", this.mNextSelectedRowId);
        viewHierarchyEncoder.addProperty("list:selectedPosition", this.mSelectedPosition);
        viewHierarchyEncoder.addProperty("list:itemCount", this.mItemCount);
    }

    int findSyncPosition() {
        int n = this.mItemCount;
        if (n == 0) {
            return -1;
        }
        long l = this.mSyncRowId;
        int n2 = this.mSyncPosition;
        if (l == Long.MIN_VALUE) {
            return -1;
        }
        n2 = Math.min(n - 1, Math.max(0, n2));
        long l2 = SystemClock.uptimeMillis();
        int n3 = n2;
        int n4 = n2;
        boolean bl = false;
        T t = this.getAdapter();
        if (t == null) {
            return -1;
        }
        while (SystemClock.uptimeMillis() <= l2 + 100L) {
            if (t.getItemId(n2) == l) {
                return n2;
            }
            boolean bl2 = true;
            boolean bl3 = n4 == n - 1;
            if (n3 != 0) {
                bl2 = false;
            }
            if (bl3 && bl2) break;
            if (!(bl2 || bl && !bl3)) {
                if (!bl3 && (bl || bl2)) continue;
                n2 = --n3;
                bl = true;
                continue;
            }
            n2 = ++n4;
            bl = false;
        }
        return -1;
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return AdapterView.class.getName();
    }

    public abstract T getAdapter();

    @ViewDebug.CapturedViewProperty
    public int getCount() {
        return this.mItemCount;
    }

    public View getEmptyView() {
        return this.mEmptyView;
    }

    public int getFirstVisiblePosition() {
        return this.mFirstPosition;
    }

    public Object getItemAtPosition(int n) {
        Object object = this.getAdapter();
        object = object != null && n >= 0 ? object.getItem(n) : null;
        return object;
    }

    public long getItemIdAtPosition(int n) {
        T t = this.getAdapter();
        long l = t != null && n >= 0 ? t.getItemId(n) : Long.MIN_VALUE;
        return l;
    }

    public int getLastVisiblePosition() {
        return this.mFirstPosition + this.getChildCount() - 1;
    }

    public final OnItemClickListener getOnItemClickListener() {
        return this.mOnItemClickListener;
    }

    public final OnItemLongClickListener getOnItemLongClickListener() {
        return this.mOnItemLongClickListener;
    }

    public final OnItemSelectedListener getOnItemSelectedListener() {
        return this.mOnItemSelectedListener;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getPositionForView(View view) {
        try {
            View view2;
            boolean bl;
            while ((view2 = (View)((Object)view.getParent())) != null && !(bl = view2.equals(this))) {
                view = view2;
            }
        }
        catch (ClassCastException classCastException) {
            return -1;
        }
        int n = this.getChildCount();
        int i = 0;
        while (i < n) {
            if (this.getChildAt(i).equals(view)) {
                return this.mFirstPosition + i;
            }
            ++i;
        }
        return -1;
    }

    public Object getSelectedItem() {
        T t = this.getAdapter();
        int n = this.getSelectedItemPosition();
        if (t != null && t.getCount() > 0 && n >= 0) {
            return t.getItem(n);
        }
        return null;
    }

    @ViewDebug.CapturedViewProperty
    public long getSelectedItemId() {
        return this.mNextSelectedRowId;
    }

    @ViewDebug.CapturedViewProperty
    public int getSelectedItemPosition() {
        return this.mNextSelectedPosition;
    }

    public abstract View getSelectedView();

    void handleDataChanged() {
        int n = this.mItemCount;
        int n2 = 0;
        int n3 = 0;
        if (n > 0) {
            int n4 = n3;
            if (this.mNeedSync) {
                this.mNeedSync = false;
                n2 = this.findSyncPosition();
                n4 = n3;
                if (n2 >= 0) {
                    n4 = n3;
                    if (this.lookForSelectablePosition(n2, true) == n2) {
                        this.setNextSelectedPositionInt(n2);
                        n4 = 1;
                    }
                }
            }
            n2 = n4;
            if (n4 == 0) {
                n2 = n3 = this.getSelectedItemPosition();
                if (n3 >= n) {
                    n2 = n - 1;
                }
                n3 = n2;
                if (n2 < 0) {
                    n3 = 0;
                }
                n = n2 = this.lookForSelectablePosition(n3, true);
                if (n2 < 0) {
                    n = this.lookForSelectablePosition(n3, false);
                }
                n2 = n4;
                if (n >= 0) {
                    this.setNextSelectedPositionInt(n);
                    this.checkSelectionChanged();
                    n2 = 1;
                }
            }
        }
        if (n2 == 0) {
            this.mSelectedPosition = -1;
            this.mSelectedRowId = Long.MIN_VALUE;
            this.mNextSelectedPosition = -1;
            this.mNextSelectedRowId = Long.MIN_VALUE;
            this.mNeedSync = false;
            this.checkSelectionChanged();
        }
        this.notifySubtreeAccessibilityStateChangedIfNeeded();
    }

    boolean isInFilterMode() {
        return false;
    }

    int lookForSelectablePosition(int n, boolean bl) {
        return n;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.removeCallbacks((Runnable)((Object)this.mSelectionNotifier));
    }

    @Override
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setScrollable(this.isScrollableForAccessibility());
        View view = this.getSelectedView();
        if (view != null) {
            accessibilityEvent.setEnabled(view.isEnabled());
        }
        accessibilityEvent.setCurrentItemIndex(this.getSelectedItemPosition());
        accessibilityEvent.setFromIndex(this.getFirstVisiblePosition());
        accessibilityEvent.setToIndex(this.getLastVisiblePosition());
        accessibilityEvent.setItemCount(this.getCount());
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        accessibilityNodeInfo.setScrollable(this.isScrollableForAccessibility());
        View view = this.getSelectedView();
        if (view != null) {
            accessibilityNodeInfo.setEnabled(view.isEnabled());
        }
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        this.mLayoutHeight = this.getHeight();
    }

    @Override
    public void onProvideAutofillStructure(ViewStructure viewStructure, int n) {
        super.onProvideAutofillStructure(viewStructure, n);
    }

    @Override
    protected void onProvideStructure(ViewStructure viewStructure, int n, int n2) {
        super.onProvideStructure(viewStructure, n, n2);
        if (n == 1) {
            Object object = this.getAdapter();
            if (object == null) {
                return;
            }
            if ((object = object.getAutofillOptions()) != null) {
                viewStructure.setAutofillOptions((CharSequence[])object);
            }
        }
    }

    @Override
    public boolean onRequestSendAccessibilityEventInternal(View view, AccessibilityEvent accessibilityEvent) {
        if (super.onRequestSendAccessibilityEventInternal(view, accessibilityEvent)) {
            AccessibilityEvent accessibilityEvent2 = AccessibilityEvent.obtain();
            this.onInitializeAccessibilityEvent(accessibilityEvent2);
            view.dispatchPopulateAccessibilityEvent(accessibilityEvent2);
            accessibilityEvent.appendRecord(accessibilityEvent2);
            return true;
        }
        return false;
    }

    public boolean performItemClick(View view, int n, long l) {
        boolean bl;
        if (this.mOnItemClickListener != null) {
            this.playSoundEffect(0);
            this.mOnItemClickListener.onItemClick(this, view, n, l);
            bl = true;
        } else {
            bl = false;
        }
        if (view != null) {
            view.sendAccessibilityEvent(1);
        }
        return bl;
    }

    void rememberSyncState() {
        if (this.getChildCount() > 0) {
            this.mNeedSync = true;
            this.mSyncHeight = this.mLayoutHeight;
            int n = this.mSelectedPosition;
            if (n >= 0) {
                View view = this.getChildAt(n - this.mFirstPosition);
                this.mSyncRowId = this.mNextSelectedRowId;
                this.mSyncPosition = this.mNextSelectedPosition;
                if (view != null) {
                    this.mSpecificTop = view.getTop();
                }
                this.mSyncMode = 0;
            } else {
                View view = this.getChildAt(0);
                T t = this.getAdapter();
                n = this.mFirstPosition;
                this.mSyncRowId = n >= 0 && n < t.getCount() ? t.getItemId(this.mFirstPosition) : -1L;
                this.mSyncPosition = this.mFirstPosition;
                if (view != null) {
                    this.mSpecificTop = view.getTop();
                }
                this.mSyncMode = 1;
            }
        }
    }

    @Override
    public void removeAllViews() {
        throw new UnsupportedOperationException("removeAllViews() is not supported in AdapterView");
    }

    @Override
    public void removeView(View view) {
        throw new UnsupportedOperationException("removeView(View) is not supported in AdapterView");
    }

    @Override
    public void removeViewAt(int n) {
        throw new UnsupportedOperationException("removeViewAt(int) is not supported in AdapterView");
    }

    @UnsupportedAppUsage
    void selectionChanged() {
        AdapterView<T> adapterView;
        this.mPendingSelectionNotifier = null;
        if (this.mOnItemSelectedListener != null || AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            if (!this.mInLayout && !this.mBlockLayoutRequests) {
                this.dispatchOnItemSelected();
            } else {
                adapterView = this.mSelectionNotifier;
                if (adapterView == null) {
                    this.mSelectionNotifier = new SelectionNotifier();
                } else {
                    this.removeCallbacks((Runnable)((Object)adapterView));
                }
                this.post((Runnable)((Object)this.mSelectionNotifier));
            }
        }
        if ((adapterView = this.mContext.getSystemService(AutofillManager.class)) != null) {
            ((AutofillManager)((Object)adapterView)).notifyValueChanged(this);
        }
    }

    public abstract void setAdapter(T var1);

    @RemotableViewMethod
    public void setEmptyView(View view) {
        this.mEmptyView = view;
        boolean bl = true;
        if (view != null && view.getImportantForAccessibility() == 0) {
            view.setImportantForAccessibility(1);
        }
        view = this.getAdapter();
        boolean bl2 = bl;
        if (view != null) {
            bl2 = view.isEmpty() ? bl : false;
        }
        this.updateEmptyStatus(bl2);
    }

    @Override
    public void setFocusable(int n) {
        int n2;
        block5 : {
            block4 : {
                T t = this.getAdapter();
                int n3 = 0;
                n2 = t != null && t.getCount() != 0 ? 0 : 1;
                this.mDesiredFocusableState = n;
                if ((n & 17) == 0) {
                    this.mDesiredFocusableInTouchModeState = false;
                }
                if (n2 == 0) break block4;
                n2 = n3;
                if (!this.isInFilterMode()) break block5;
            }
            n2 = n;
        }
        super.setFocusable(n2);
    }

    @Override
    public void setFocusableInTouchMode(boolean bl) {
        boolean bl2;
        block4 : {
            block5 : {
                T t = this.getAdapter();
                boolean bl3 = false;
                boolean bl4 = t == null || t.getCount() == 0;
                this.mDesiredFocusableInTouchModeState = bl;
                if (bl) {
                    this.mDesiredFocusableState = 1;
                }
                bl2 = bl3;
                if (!bl) break block4;
                if (!bl4) break block5;
                bl2 = bl3;
                if (!this.isInFilterMode()) break block4;
            }
            bl2 = true;
        }
        super.setFocusableInTouchMode(bl2);
    }

    @UnsupportedAppUsage
    void setNextSelectedPositionInt(int n) {
        this.mNextSelectedPosition = n;
        this.mNextSelectedRowId = this.getItemIdAtPosition(n);
        if (this.mNeedSync && this.mSyncMode == 0 && n >= 0) {
            this.mSyncPosition = n;
            this.mSyncRowId = this.mNextSelectedRowId;
        }
    }

    @Override
    public void setOnClickListener(View.OnClickListener onClickListener) {
        throw new RuntimeException("Don't call setOnClickListener for an AdapterView. You probably want setOnItemClickListener instead");
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        if (!this.isLongClickable()) {
            this.setLongClickable(true);
        }
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.mOnItemSelectedListener = onItemSelectedListener;
    }

    @UnsupportedAppUsage
    void setSelectedPositionInt(int n) {
        this.mSelectedPosition = n;
        this.mSelectedRowId = this.getItemIdAtPosition(n);
    }

    public abstract void setSelection(int var1);

    public static class AdapterContextMenuInfo
    implements ContextMenu.ContextMenuInfo {
        public long id;
        public int position;
        public View targetView;

        public AdapterContextMenuInfo(View view, int n, long l) {
            this.targetView = view;
            this.position = n;
            this.id = l;
        }
    }

    class AdapterDataSetObserver
    extends DataSetObserver {
        private Parcelable mInstanceState = null;

        AdapterDataSetObserver() {
        }

        public void clearSavedState() {
            this.mInstanceState = null;
        }

        @Override
        public void onChanged() {
            AdapterView adapterView = AdapterView.this;
            adapterView.mDataChanged = true;
            adapterView.mOldItemCount = adapterView.mItemCount;
            adapterView = AdapterView.this;
            adapterView.mItemCount = adapterView.getAdapter().getCount();
            if (AdapterView.this.getAdapter().hasStableIds() && this.mInstanceState != null && AdapterView.this.mOldItemCount == 0 && AdapterView.this.mItemCount > 0) {
                AdapterView.this.onRestoreInstanceState(this.mInstanceState);
                this.mInstanceState = null;
            } else {
                AdapterView.this.rememberSyncState();
            }
            AdapterView.this.checkFocus();
            AdapterView.this.requestLayout();
        }

        @Override
        public void onInvalidated() {
            AdapterView adapterView = AdapterView.this;
            adapterView.mDataChanged = true;
            if (adapterView.getAdapter().hasStableIds()) {
                this.mInstanceState = AdapterView.this.onSaveInstanceState();
            }
            adapterView = AdapterView.this;
            adapterView.mOldItemCount = adapterView.mItemCount;
            adapterView = AdapterView.this;
            adapterView.mItemCount = 0;
            adapterView.mSelectedPosition = -1;
            adapterView.mSelectedRowId = Long.MIN_VALUE;
            adapterView.mNextSelectedPosition = -1;
            adapterView.mNextSelectedRowId = Long.MIN_VALUE;
            adapterView.mNeedSync = false;
            adapterView.checkFocus();
            AdapterView.this.requestLayout();
        }
    }

    public static interface OnItemClickListener {
        public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4);
    }

    public static interface OnItemLongClickListener {
        public boolean onItemLongClick(AdapterView<?> var1, View var2, int var3, long var4);
    }

    public static interface OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> var1, View var2, int var3, long var4);

        public void onNothingSelected(AdapterView<?> var1);
    }

    private class SelectionNotifier
    implements Runnable {
        private SelectionNotifier() {
        }

        @Override
        public void run() {
            AdapterView.this.mPendingSelectionNotifier = null;
            if (AdapterView.this.mDataChanged && AdapterView.this.getViewRootImpl() != null && AdapterView.this.getViewRootImpl().isLayoutRequested()) {
                if (AdapterView.this.getAdapter() != null) {
                    AdapterView.this.mPendingSelectionNotifier = this;
                }
            } else {
                AdapterView.this.dispatchOnItemSelected();
            }
        }
    }

}

