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
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListConnector;
import android.widget.ExpandableListPosition;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.List;

public class ExpandableListView
extends ListView {
    public static final int CHILD_INDICATOR_INHERIT = -1;
    private static final int[] CHILD_LAST_STATE_SET;
    private static final int[] EMPTY_STATE_SET;
    private static final int[] GROUP_EMPTY_STATE_SET;
    private static final int[] GROUP_EXPANDED_EMPTY_STATE_SET;
    private static final int[] GROUP_EXPANDED_STATE_SET;
    @UnsupportedAppUsage
    private static final int[][] GROUP_STATE_SETS;
    private static final int INDICATOR_UNDEFINED = -2;
    private static final long PACKED_POSITION_INT_MASK_CHILD = -1L;
    private static final long PACKED_POSITION_INT_MASK_GROUP = Integer.MAX_VALUE;
    private static final long PACKED_POSITION_MASK_CHILD = 0xFFFFFFFFL;
    private static final long PACKED_POSITION_MASK_GROUP = 9223372032559808512L;
    private static final long PACKED_POSITION_MASK_TYPE = Long.MIN_VALUE;
    private static final long PACKED_POSITION_SHIFT_GROUP = 32L;
    private static final long PACKED_POSITION_SHIFT_TYPE = 63L;
    public static final int PACKED_POSITION_TYPE_CHILD = 1;
    public static final int PACKED_POSITION_TYPE_GROUP = 0;
    public static final int PACKED_POSITION_TYPE_NULL = 2;
    public static final long PACKED_POSITION_VALUE_NULL = 0xFFFFFFFFL;
    private ExpandableListAdapter mAdapter;
    @UnsupportedAppUsage
    private Drawable mChildDivider;
    private Drawable mChildIndicator;
    private int mChildIndicatorEnd;
    private int mChildIndicatorLeft;
    private int mChildIndicatorRight;
    private int mChildIndicatorStart;
    @UnsupportedAppUsage
    private ExpandableListConnector mConnector;
    @UnsupportedAppUsage
    private Drawable mGroupIndicator;
    private int mIndicatorEnd;
    @UnsupportedAppUsage
    private int mIndicatorLeft;
    private final Rect mIndicatorRect = new Rect();
    @UnsupportedAppUsage
    private int mIndicatorRight;
    private int mIndicatorStart;
    @UnsupportedAppUsage
    private OnChildClickListener mOnChildClickListener;
    @UnsupportedAppUsage
    private OnGroupClickListener mOnGroupClickListener;
    @UnsupportedAppUsage
    private OnGroupCollapseListener mOnGroupCollapseListener;
    @UnsupportedAppUsage
    private OnGroupExpandListener mOnGroupExpandListener;

    static {
        EMPTY_STATE_SET = new int[0];
        GROUP_EXPANDED_STATE_SET = new int[]{16842920};
        GROUP_EMPTY_STATE_SET = new int[]{16842921};
        GROUP_EXPANDED_EMPTY_STATE_SET = new int[]{16842920, 16842921};
        GROUP_STATE_SETS = new int[][]{EMPTY_STATE_SET, GROUP_EXPANDED_STATE_SET, GROUP_EMPTY_STATE_SET, GROUP_EXPANDED_EMPTY_STATE_SET};
        CHILD_LAST_STATE_SET = new int[]{16842918};
    }

    public ExpandableListView(Context context) {
        this(context, null);
    }

    public ExpandableListView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842863);
    }

    public ExpandableListView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ExpandableListView(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        TypedArray typedArray = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.ExpandableListView, n, n2);
        this.saveAttributeDataForStyleable((Context)object, R.styleable.ExpandableListView, attributeSet, typedArray, n, n2);
        this.mGroupIndicator = typedArray.getDrawable(0);
        this.mChildIndicator = typedArray.getDrawable(1);
        this.mIndicatorLeft = typedArray.getDimensionPixelSize(2, 0);
        this.mIndicatorRight = typedArray.getDimensionPixelSize(3, 0);
        if (this.mIndicatorRight == 0 && (object = this.mGroupIndicator) != null) {
            this.mIndicatorRight = this.mIndicatorLeft + ((Drawable)object).getIntrinsicWidth();
        }
        this.mChildIndicatorLeft = typedArray.getDimensionPixelSize(4, -1);
        this.mChildIndicatorRight = typedArray.getDimensionPixelSize(5, -1);
        this.mChildDivider = typedArray.getDrawable(6);
        if (!this.isRtlCompatibilityMode()) {
            this.mIndicatorStart = typedArray.getDimensionPixelSize(7, -2);
            this.mIndicatorEnd = typedArray.getDimensionPixelSize(8, -2);
            this.mChildIndicatorStart = typedArray.getDimensionPixelSize(9, -1);
            this.mChildIndicatorEnd = typedArray.getDimensionPixelSize(10, -1);
        }
        typedArray.recycle();
    }

    private int getAbsoluteFlatPosition(int n) {
        return this.getHeaderViewsCount() + n;
    }

    private long getChildOrGroupId(ExpandableListPosition expandableListPosition) {
        if (expandableListPosition.type == 1) {
            return this.mAdapter.getChildId(expandableListPosition.groupPos, expandableListPosition.childPos);
        }
        return this.mAdapter.getGroupId(expandableListPosition.groupPos);
    }

    private int getFlatPositionForConnector(int n) {
        return n - this.getHeaderViewsCount();
    }

    private Drawable getIndicator(ExpandableListConnector.PositionMetadata arrn) {
        Drawable drawable2;
        int n = arrn.position.type;
        int n2 = 2;
        if (n == 2) {
            Drawable drawable3;
            drawable2 = drawable3 = this.mGroupIndicator;
            if (drawable3 != null) {
                drawable2 = drawable3;
                if (drawable3.isStateful()) {
                    n = arrn.groupMetadata != null && arrn.groupMetadata.lastChildFlPos != arrn.groupMetadata.flPos ? 0 : 1;
                    int n3 = arrn.isExpanded();
                    n = n != 0 ? n2 : 0;
                    drawable3.setState(GROUP_STATE_SETS[n | n3]);
                    drawable2 = drawable3;
                }
            }
        } else {
            Drawable drawable4;
            drawable2 = drawable4 = this.mChildIndicator;
            if (drawable4 != null) {
                drawable2 = drawable4;
                if (drawable4.isStateful()) {
                    arrn = arrn.position.flatListPos == arrn.groupMetadata.lastChildFlPos ? CHILD_LAST_STATE_SET : EMPTY_STATE_SET;
                    drawable4.setState(arrn);
                    drawable2 = drawable4;
                }
            }
        }
        return drawable2;
    }

    public static int getPackedPositionChild(long l) {
        if (l == 0xFFFFFFFFL) {
            return -1;
        }
        if ((l & Long.MIN_VALUE) != Long.MIN_VALUE) {
            return -1;
        }
        return (int)(0xFFFFFFFFL & l);
    }

    public static long getPackedPositionForChild(int n, int n2) {
        return ((long)n & Integer.MAX_VALUE) << 32 | Long.MIN_VALUE | (long)n2 & -1L;
    }

    public static long getPackedPositionForGroup(int n) {
        return ((long)n & Integer.MAX_VALUE) << 32;
    }

    public static int getPackedPositionGroup(long l) {
        if (l == 0xFFFFFFFFL) {
            return -1;
        }
        return (int)((9223372032559808512L & l) >> 32);
    }

    public static int getPackedPositionType(long l) {
        if (l == 0xFFFFFFFFL) {
            return 2;
        }
        int n = (l & Long.MIN_VALUE) == Long.MIN_VALUE ? 1 : 0;
        return n;
    }

    private boolean hasRtlSupport() {
        return this.mContext.getApplicationInfo().hasRtlSupport();
    }

    private boolean isHeaderOrFooterPosition(int n) {
        int n2 = this.mItemCount;
        int n3 = this.getFooterViewsCount();
        boolean bl = n < this.getHeaderViewsCount() || n >= n2 - n3;
        return bl;
    }

    private boolean isRtlCompatibilityMode() {
        boolean bl = this.mContext.getApplicationInfo().targetSdkVersion < 17 || !this.hasRtlSupport();
        return bl;
    }

    private void resolveChildIndicator() {
        if (this.isLayoutRtl()) {
            int n = this.mChildIndicatorStart;
            if (n >= -1) {
                this.mChildIndicatorRight = n;
            }
            if ((n = this.mChildIndicatorEnd) >= -1) {
                this.mChildIndicatorLeft = n;
            }
        } else {
            int n = this.mChildIndicatorStart;
            if (n >= -1) {
                this.mChildIndicatorLeft = n;
            }
            if ((n = this.mChildIndicatorEnd) >= -1) {
                this.mChildIndicatorRight = n;
            }
        }
    }

    private void resolveIndicator() {
        Drawable drawable2;
        if (this.isLayoutRtl()) {
            int n = this.mIndicatorStart;
            if (n >= 0) {
                this.mIndicatorRight = n;
            }
            if ((n = this.mIndicatorEnd) >= 0) {
                this.mIndicatorLeft = n;
            }
        } else {
            int n = this.mIndicatorStart;
            if (n >= 0) {
                this.mIndicatorLeft = n;
            }
            if ((n = this.mIndicatorEnd) >= 0) {
                this.mIndicatorRight = n;
            }
        }
        if (this.mIndicatorRight == 0 && (drawable2 = this.mGroupIndicator) != null) {
            this.mIndicatorRight = this.mIndicatorLeft + drawable2.getIntrinsicWidth();
        }
    }

    public boolean collapseGroup(int n) {
        boolean bl = this.mConnector.collapseGroup(n);
        OnGroupCollapseListener onGroupCollapseListener = this.mOnGroupCollapseListener;
        if (onGroupCollapseListener != null) {
            onGroupCollapseListener.onGroupCollapse(n);
        }
        return bl;
    }

    @Override
    ContextMenu.ContextMenuInfo createContextMenuInfo(View view, int n, long l) {
        if (this.isHeaderOrFooterPosition(n)) {
            return new AdapterView.AdapterContextMenuInfo(view, n, l);
        }
        n = this.getFlatPositionForConnector(n);
        ExpandableListConnector.PositionMetadata positionMetadata = this.mConnector.getUnflattenedPos(n);
        ExpandableListPosition expandableListPosition = positionMetadata.position;
        l = this.getChildOrGroupId(expandableListPosition);
        long l2 = expandableListPosition.getPackedPosition();
        positionMetadata.recycle();
        return new ExpandableListContextMenuInfo(view, l2, l);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int n;
        int n2;
        super.dispatchDraw(canvas);
        if (this.mChildIndicator == null && this.mGroupIndicator == null) {
            return;
        }
        int n3 = 0;
        boolean bl = (this.mGroupFlags & 34) == 34;
        if (bl) {
            n3 = canvas.save();
            n2 = this.mScrollX;
            n = this.mScrollY;
            canvas.clipRect(this.mPaddingLeft + n2, this.mPaddingTop + n, this.mRight + n2 - this.mLeft - this.mPaddingRight, this.mBottom + n - this.mTop - this.mPaddingBottom);
        }
        int n4 = this.getHeaderViewsCount();
        int n5 = this.mItemCount - this.getFooterViewsCount() - n4 - 1;
        int n6 = this.mBottom;
        n2 = -4;
        Rect rect = this.mIndicatorRect;
        int n7 = this.getChildCount();
        int n8 = 0;
        n = this.mFirstPosition - n4;
        while (n8 < n7) {
            if (n >= 0) {
                if (n > n5) break;
                Object object = this.getChildAt(n8);
                int n9 = ((View)object).getTop();
                int n10 = ((View)object).getBottom();
                if (n10 >= 0 && n9 <= n6) {
                    object = this.mConnector.getUnflattenedPos(n);
                    boolean bl2 = this.isLayoutRtl();
                    int n11 = this.getWidth();
                    if (object.position.type != n2) {
                        if (object.position.type == 1) {
                            n2 = this.mChildIndicatorLeft;
                            if (n2 == -1) {
                                n2 = this.mIndicatorLeft;
                            }
                            rect.left = n2;
                            n2 = this.mChildIndicatorRight;
                            if (n2 == -1) {
                                n2 = this.mIndicatorRight;
                            }
                            rect.right = n2;
                        } else {
                            rect.left = this.mIndicatorLeft;
                            rect.right = this.mIndicatorRight;
                        }
                        if (bl2) {
                            n2 = rect.left;
                            rect.left = n11 - rect.right;
                            rect.right = n11 - n2;
                            rect.left -= this.mPaddingRight;
                            rect.right -= this.mPaddingRight;
                        } else {
                            rect.left += this.mPaddingLeft;
                            rect.right += this.mPaddingLeft;
                        }
                        n2 = object.position.type;
                    }
                    if (rect.left != rect.right) {
                        if (this.mStackFromBottom) {
                            rect.top = n9;
                            rect.bottom = n10;
                        } else {
                            rect.top = n9;
                            rect.bottom = n10;
                        }
                        Drawable drawable2 = this.getIndicator((ExpandableListConnector.PositionMetadata)object);
                        if (drawable2 != null) {
                            drawable2.setBounds(rect);
                            drawable2.draw(canvas);
                        }
                    }
                    ((ExpandableListConnector.PositionMetadata)object).recycle();
                }
            }
            ++n8;
            ++n;
        }
        if (bl) {
            canvas.restoreToCount(n3);
        }
    }

    @Override
    void drawDivider(Canvas canvas, Rect rect, int n) {
        int n2 = this.mFirstPosition + n;
        if (n2 >= 0) {
            n = this.getFlatPositionForConnector(n2);
            ExpandableListConnector.PositionMetadata positionMetadata = this.mConnector.getUnflattenedPos(n);
            if (!(positionMetadata.position.type == 1 || positionMetadata.isExpanded() && positionMetadata.groupMetadata.lastChildFlPos != positionMetadata.groupMetadata.flPos)) {
                positionMetadata.recycle();
            } else {
                Drawable drawable2 = this.mChildDivider;
                drawable2.setBounds(rect);
                drawable2.draw(canvas);
                positionMetadata.recycle();
                return;
            }
        }
        super.drawDivider(canvas, rect, n2);
    }

    public boolean expandGroup(int n) {
        return this.expandGroup(n, false);
    }

    public boolean expandGroup(int n, boolean bl) {
        Object object = ExpandableListPosition.obtain(2, n, -1, -1);
        ExpandableListConnector.PositionMetadata positionMetadata = this.mConnector.getFlattenedPos((ExpandableListPosition)object);
        ((ExpandableListPosition)object).recycle();
        boolean bl2 = this.mConnector.expandGroup(positionMetadata);
        object = this.mOnGroupExpandListener;
        if (object != null) {
            object.onGroupExpand(n);
        }
        if (bl) {
            int n2 = positionMetadata.position.flatListPos;
            n2 = this.getHeaderViewsCount() + n2;
            this.smoothScrollToPosition(this.mAdapter.getChildrenCount(n) + n2, n2);
        }
        positionMetadata.recycle();
        return bl2;
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return ExpandableListView.class.getName();
    }

    @Override
    public ListAdapter getAdapter() {
        return super.getAdapter();
    }

    public ExpandableListAdapter getExpandableListAdapter() {
        return this.mAdapter;
    }

    public long getExpandableListPosition(int n) {
        if (this.isHeaderOrFooterPosition(n)) {
            return 0xFFFFFFFFL;
        }
        n = this.getFlatPositionForConnector(n);
        ExpandableListConnector.PositionMetadata positionMetadata = this.mConnector.getUnflattenedPos(n);
        long l = positionMetadata.position.getPackedPosition();
        positionMetadata.recycle();
        return l;
    }

    public int getFlatListPosition(long l) {
        ExpandableListPosition expandableListPosition = ExpandableListPosition.obtainPosition(l);
        ExpandableListConnector.PositionMetadata positionMetadata = this.mConnector.getFlattenedPos(expandableListPosition);
        expandableListPosition.recycle();
        int n = positionMetadata.position.flatListPos;
        positionMetadata.recycle();
        return this.getAbsoluteFlatPosition(n);
    }

    public long getSelectedId() {
        long l = this.getSelectedPosition();
        if (l == 0xFFFFFFFFL) {
            return -1L;
        }
        int n = ExpandableListView.getPackedPositionGroup(l);
        if (ExpandableListView.getPackedPositionType(l) == 0) {
            return this.mAdapter.getGroupId(n);
        }
        return this.mAdapter.getChildId(n, ExpandableListView.getPackedPositionChild(l));
    }

    public long getSelectedPosition() {
        return this.getExpandableListPosition(this.getSelectedItemPosition());
    }

    boolean handleItemClick(View object, int n, long l) {
        boolean bl;
        ExpandableListConnector.PositionMetadata positionMetadata = this.mConnector.getUnflattenedPos(n);
        l = this.getChildOrGroupId(positionMetadata.position);
        if (positionMetadata.position.type == 2) {
            OnGroupClickListener onGroupClickListener = this.mOnGroupClickListener;
            if (onGroupClickListener != null && onGroupClickListener.onGroupClick(this, (View)object, positionMetadata.position.groupPos, l)) {
                positionMetadata.recycle();
                return true;
            }
            if (positionMetadata.isExpanded()) {
                this.mConnector.collapseGroup(positionMetadata);
                this.playSoundEffect(0);
                object = this.mOnGroupCollapseListener;
                if (object != null) {
                    object.onGroupCollapse(positionMetadata.position.groupPos);
                }
            } else {
                this.mConnector.expandGroup(positionMetadata);
                this.playSoundEffect(0);
                object = this.mOnGroupExpandListener;
                if (object != null) {
                    object.onGroupExpand(positionMetadata.position.groupPos);
                }
                n = positionMetadata.position.groupPos;
                int n2 = positionMetadata.position.flatListPos;
                n2 = this.getHeaderViewsCount() + n2;
                this.smoothScrollToPosition(this.mAdapter.getChildrenCount(n) + n2, n2);
            }
            bl = true;
        } else {
            if (this.mOnChildClickListener != null) {
                this.playSoundEffect(0);
                return this.mOnChildClickListener.onChildClick(this, (View)object, positionMetadata.position.groupPos, positionMetadata.position.childPos, l);
            }
            bl = false;
        }
        positionMetadata.recycle();
        return bl;
    }

    public boolean isGroupExpanded(int n) {
        return this.mConnector.isGroupExpanded(n);
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(((AbsSavedState)parcelable).getSuperState());
        if (this.mConnector != null && ((SavedState)parcelable).expandedGroupMetadataList != null) {
            this.mConnector.setExpandedGroupMetadataList(((SavedState)parcelable).expandedGroupMetadataList);
        }
    }

    @Override
    public void onRtlPropertiesChanged(int n) {
        this.resolveIndicator();
        this.resolveChildIndicator();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        Object object = this.mConnector;
        object = object != null ? ((ExpandableListConnector)object).getExpandedGroupMetadataList() : null;
        return new SavedState(parcelable, (ArrayList<ExpandableListConnector.GroupMetadata>)object);
    }

    @Override
    public boolean performItemClick(View view, int n, long l) {
        if (this.isHeaderOrFooterPosition(n)) {
            return super.performItemClick(view, n, l);
        }
        return this.handleItemClick(view, this.getFlatPositionForConnector(n), l);
    }

    @Override
    public void setAdapter(ExpandableListAdapter expandableListAdapter) {
        this.mAdapter = expandableListAdapter;
        this.mConnector = expandableListAdapter != null ? new ExpandableListConnector(expandableListAdapter) : null;
        super.setAdapter(this.mConnector);
    }

    @Override
    public void setAdapter(ListAdapter listAdapter) {
        throw new RuntimeException("For ExpandableListView, use setAdapter(ExpandableListAdapter) instead of setAdapter(ListAdapter)");
    }

    public void setChildDivider(Drawable drawable2) {
        this.mChildDivider = drawable2;
    }

    public void setChildIndicator(Drawable drawable2) {
        this.mChildIndicator = drawable2;
    }

    public void setChildIndicatorBounds(int n, int n2) {
        this.mChildIndicatorLeft = n;
        this.mChildIndicatorRight = n2;
        this.resolveChildIndicator();
    }

    public void setChildIndicatorBoundsRelative(int n, int n2) {
        this.mChildIndicatorStart = n;
        this.mChildIndicatorEnd = n2;
        this.resolveChildIndicator();
    }

    public void setGroupIndicator(Drawable drawable2) {
        this.mGroupIndicator = drawable2;
        if (this.mIndicatorRight == 0 && (drawable2 = this.mGroupIndicator) != null) {
            this.mIndicatorRight = this.mIndicatorLeft + drawable2.getIntrinsicWidth();
        }
    }

    public void setIndicatorBounds(int n, int n2) {
        this.mIndicatorLeft = n;
        this.mIndicatorRight = n2;
        this.resolveIndicator();
    }

    public void setIndicatorBoundsRelative(int n, int n2) {
        this.mIndicatorStart = n;
        this.mIndicatorEnd = n2;
        this.resolveIndicator();
    }

    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        this.mOnChildClickListener = onChildClickListener;
    }

    public void setOnGroupClickListener(OnGroupClickListener onGroupClickListener) {
        this.mOnGroupClickListener = onGroupClickListener;
    }

    public void setOnGroupCollapseListener(OnGroupCollapseListener onGroupCollapseListener) {
        this.mOnGroupCollapseListener = onGroupCollapseListener;
    }

    public void setOnGroupExpandListener(OnGroupExpandListener onGroupExpandListener) {
        this.mOnGroupExpandListener = onGroupExpandListener;
    }

    @Override
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        super.setOnItemClickListener(onItemClickListener);
    }

    public boolean setSelectedChild(int n, int n2, boolean bl) {
        ExpandableListConnector.PositionMetadata positionMetadata;
        ExpandableListPosition expandableListPosition = ExpandableListPosition.obtainChildPosition(n, n2);
        ExpandableListConnector.PositionMetadata positionMetadata2 = positionMetadata = this.mConnector.getFlattenedPos(expandableListPosition);
        if (positionMetadata == null) {
            if (!bl) {
                return false;
            }
            this.expandGroup(n);
            positionMetadata2 = this.mConnector.getFlattenedPos(expandableListPosition);
            if (positionMetadata2 == null) {
                throw new IllegalStateException("Could not find child");
            }
        }
        super.setSelection(this.getAbsoluteFlatPosition(positionMetadata2.position.flatListPos));
        expandableListPosition.recycle();
        positionMetadata2.recycle();
        return true;
    }

    public void setSelectedGroup(int n) {
        ExpandableListPosition expandableListPosition = ExpandableListPosition.obtainGroupPosition(n);
        ExpandableListConnector.PositionMetadata positionMetadata = this.mConnector.getFlattenedPos(expandableListPosition);
        expandableListPosition.recycle();
        super.setSelection(this.getAbsoluteFlatPosition(positionMetadata.position.flatListPos));
        positionMetadata.recycle();
    }

    public static class ExpandableListContextMenuInfo
    implements ContextMenu.ContextMenuInfo {
        public long id;
        public long packedPosition;
        public View targetView;

        public ExpandableListContextMenuInfo(View view, long l, long l2) {
            this.targetView = view;
            this.packedPosition = l;
            this.id = l2;
        }
    }

    public static interface OnChildClickListener {
        public boolean onChildClick(ExpandableListView var1, View var2, int var3, int var4, long var5);
    }

    public static interface OnGroupClickListener {
        public boolean onGroupClick(ExpandableListView var1, View var2, int var3, long var4);
    }

    public static interface OnGroupCollapseListener {
        public void onGroupCollapse(int var1);
    }

    public static interface OnGroupExpandListener {
        public void onGroupExpand(int var1);
    }

    static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        ArrayList<ExpandableListConnector.GroupMetadata> expandedGroupMetadataList;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.expandedGroupMetadataList = new ArrayList();
            parcel.readList(this.expandedGroupMetadataList, ExpandableListConnector.class.getClassLoader());
        }

        SavedState(Parcelable parcelable, ArrayList<ExpandableListConnector.GroupMetadata> arrayList) {
            super(parcelable);
            this.expandedGroupMetadataList = arrayList;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeList(this.expandedGroupMetadataList);
        }

    }

}

