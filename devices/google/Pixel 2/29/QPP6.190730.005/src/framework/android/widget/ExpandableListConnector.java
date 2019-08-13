/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.database.DataSetObserver;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListPosition;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.HeterogeneousExpandableList;
import java.util.ArrayList;
import java.util.Collections;

class ExpandableListConnector
extends BaseAdapter
implements Filterable {
    private final DataSetObserver mDataSetObserver = new MyDataSetObserver();
    private ArrayList<GroupMetadata> mExpGroupMetadataList = new ArrayList();
    private ExpandableListAdapter mExpandableListAdapter;
    private int mMaxExpGroupCount = Integer.MAX_VALUE;
    private int mTotalExpChildrenCount;

    public ExpandableListConnector(ExpandableListAdapter expandableListAdapter) {
        this.setExpandableListAdapter(expandableListAdapter);
    }

    private void refreshExpGroupMetadataList(boolean bl, boolean bl2) {
        GroupMetadata groupMetadata;
        int n;
        int n2;
        int n3;
        ArrayList<GroupMetadata> arrayList = this.mExpGroupMetadataList;
        int n4 = arrayList.size();
        int n5 = 0;
        this.mTotalExpChildrenCount = 0;
        int n6 = n4;
        if (bl2) {
            n2 = 0;
            for (n = n4 - 1; n >= 0; --n) {
                groupMetadata = arrayList.get(n);
                int n7 = this.findGroupPosition(groupMetadata.gId, groupMetadata.gPos);
                int n8 = n4;
                n3 = n2;
                if (n7 != groupMetadata.gPos) {
                    n6 = n4;
                    if (n7 == -1) {
                        arrayList.remove(n);
                        n6 = n4 - 1;
                    }
                    groupMetadata.gPos = n7;
                    n8 = n6;
                    n3 = n2;
                    if (n2 == 0) {
                        n3 = 1;
                        n8 = n6;
                    }
                }
                n4 = n8;
                n2 = n3;
            }
            n6 = n4;
            if (n2 != 0) {
                Collections.sort(arrayList);
                n6 = n4;
            }
        }
        n2 = 0;
        n = n5;
        for (n4 = 0; n4 < n6; ++n4) {
            groupMetadata = arrayList.get(n4);
            n3 = groupMetadata.lastChildFlPos != -1 && !bl ? groupMetadata.lastChildFlPos - groupMetadata.flPos : this.mExpandableListAdapter.getChildrenCount(groupMetadata.gPos);
            this.mTotalExpChildrenCount += n3;
            n2 = groupMetadata.gPos;
            groupMetadata.flPos = n += groupMetadata.gPos - n2;
            groupMetadata.lastChildFlPos = n += n3;
        }
    }

    @Override
    public boolean areAllItemsEnabled() {
        return this.mExpandableListAdapter.areAllItemsEnabled();
    }

    boolean collapseGroup(int n) {
        ExpandableListPosition expandableListPosition = ExpandableListPosition.obtain(2, n, -1, -1);
        PositionMetadata positionMetadata = this.getFlattenedPos(expandableListPosition);
        expandableListPosition.recycle();
        if (positionMetadata == null) {
            return false;
        }
        boolean bl = this.collapseGroup(positionMetadata);
        positionMetadata.recycle();
        return bl;
    }

    boolean collapseGroup(PositionMetadata positionMetadata) {
        if (positionMetadata.groupMetadata == null) {
            return false;
        }
        this.mExpGroupMetadataList.remove(positionMetadata.groupMetadata);
        this.refreshExpGroupMetadataList(false, false);
        this.notifyDataSetChanged();
        this.mExpandableListAdapter.onGroupCollapsed(positionMetadata.groupMetadata.gPos);
        return true;
    }

    boolean expandGroup(int n) {
        ExpandableListPosition expandableListPosition = ExpandableListPosition.obtain(2, n, -1, -1);
        PositionMetadata positionMetadata = this.getFlattenedPos(expandableListPosition);
        expandableListPosition.recycle();
        boolean bl = this.expandGroup(positionMetadata);
        positionMetadata.recycle();
        return bl;
    }

    boolean expandGroup(PositionMetadata positionMetadata) {
        if (positionMetadata.position.groupPos >= 0) {
            GroupMetadata groupMetadata;
            if (this.mMaxExpGroupCount == 0) {
                return false;
            }
            if (positionMetadata.groupMetadata != null) {
                return false;
            }
            if (this.mExpGroupMetadataList.size() >= this.mMaxExpGroupCount) {
                groupMetadata = this.mExpGroupMetadataList.get(0);
                int n = this.mExpGroupMetadataList.indexOf(groupMetadata);
                this.collapseGroup(groupMetadata.gPos);
                if (positionMetadata.groupInsertIndex > n) {
                    --positionMetadata.groupInsertIndex;
                }
            }
            groupMetadata = GroupMetadata.obtain(-1, -1, positionMetadata.position.groupPos, this.mExpandableListAdapter.getGroupId(positionMetadata.position.groupPos));
            this.mExpGroupMetadataList.add(positionMetadata.groupInsertIndex, groupMetadata);
            this.refreshExpGroupMetadataList(false, false);
            this.notifyDataSetChanged();
            this.mExpandableListAdapter.onGroupExpanded(groupMetadata.gPos);
            return true;
        }
        throw new RuntimeException("Need group");
    }

    int findGroupPosition(long l, int n) {
        int n2 = this.mExpandableListAdapter.getGroupCount();
        if (n2 == 0) {
            return -1;
        }
        if (l == Long.MIN_VALUE) {
            return -1;
        }
        n = Math.min(n2 - 1, Math.max(0, n));
        long l2 = SystemClock.uptimeMillis();
        int n3 = n;
        int n4 = n;
        boolean bl = false;
        ExpandableListAdapter expandableListAdapter = this.getAdapter();
        if (expandableListAdapter == null) {
            return -1;
        }
        while (SystemClock.uptimeMillis() <= l2 + 100L) {
            if (expandableListAdapter.getGroupId(n) == l) {
                return n;
            }
            boolean bl2 = true;
            boolean bl3 = n4 == n2 - 1;
            if (n3 != 0) {
                bl2 = false;
            }
            if (bl3 && bl2) break;
            if (!(bl2 || bl && !bl3)) {
                if (!bl3 && (bl || bl2)) continue;
                n = --n3;
                bl = true;
                continue;
            }
            n = ++n4;
            bl = false;
        }
        return -1;
    }

    ExpandableListAdapter getAdapter() {
        return this.mExpandableListAdapter;
    }

    @Override
    public int getCount() {
        return this.mExpandableListAdapter.getGroupCount() + this.mTotalExpChildrenCount;
    }

    ArrayList<GroupMetadata> getExpandedGroupMetadataList() {
        return this.mExpGroupMetadataList;
    }

    @Override
    public Filter getFilter() {
        ExpandableListAdapter expandableListAdapter = this.getAdapter();
        if (expandableListAdapter instanceof Filterable) {
            return ((Filterable)((Object)expandableListAdapter)).getFilter();
        }
        return null;
    }

    PositionMetadata getFlattenedPos(ExpandableListPosition expandableListPosition) {
        GroupMetadata groupMetadata;
        ArrayList<GroupMetadata> arrayList = this.mExpGroupMetadataList;
        int n = arrayList.size();
        int n2 = 0;
        int n3 = n - 1;
        int n4 = 0;
        if (n == 0) {
            return PositionMetadata.obtain(expandableListPosition.groupPos, expandableListPosition.type, expandableListPosition.groupPos, expandableListPosition.childPos, null, 0);
        }
        while (n2 <= n3) {
            n = (n3 - n2) / 2 + n2;
            groupMetadata = arrayList.get(n);
            if (expandableListPosition.groupPos > groupMetadata.gPos) {
                n2 = n + 1;
                n4 = n;
                continue;
            }
            if (expandableListPosition.groupPos < groupMetadata.gPos) {
                n3 = n - 1;
                n4 = n;
                continue;
            }
            n4 = n;
            if (expandableListPosition.groupPos != groupMetadata.gPos) continue;
            if (expandableListPosition.type == 2) {
                return PositionMetadata.obtain(groupMetadata.flPos, expandableListPosition.type, expandableListPosition.groupPos, expandableListPosition.childPos, groupMetadata, n);
            }
            if (expandableListPosition.type == 1) {
                return PositionMetadata.obtain(groupMetadata.flPos + expandableListPosition.childPos + 1, expandableListPosition.type, expandableListPosition.groupPos, expandableListPosition.childPos, groupMetadata, n);
            }
            return null;
        }
        if (expandableListPosition.type != 2) {
            return null;
        }
        if (n2 > n4) {
            groupMetadata = arrayList.get(n2 - 1);
            return PositionMetadata.obtain(groupMetadata.lastChildFlPos + (expandableListPosition.groupPos - groupMetadata.gPos), expandableListPosition.type, expandableListPosition.groupPos, expandableListPosition.childPos, null, n2);
        }
        if (n3 < n4) {
            n2 = n3 + 1;
            groupMetadata = arrayList.get(n2);
            return PositionMetadata.obtain(groupMetadata.flPos - (groupMetadata.gPos - expandableListPosition.groupPos), expandableListPosition.type, expandableListPosition.groupPos, expandableListPosition.childPos, null, n2);
        }
        return null;
    }

    @Override
    public Object getItem(int n) {
        block4 : {
            PositionMetadata positionMetadata;
            Object object;
            block3 : {
                block2 : {
                    positionMetadata = this.getUnflattenedPos(n);
                    if (positionMetadata.position.type != 2) break block2;
                    object = this.mExpandableListAdapter.getGroup(positionMetadata.position.groupPos);
                    break block3;
                }
                if (positionMetadata.position.type != 1) break block4;
                object = this.mExpandableListAdapter.getChild(positionMetadata.position.groupPos, positionMetadata.position.childPos);
            }
            positionMetadata.recycle();
            return object;
        }
        throw new RuntimeException("Flat list position is of unknown type");
    }

    @Override
    public long getItemId(int n) {
        block4 : {
            PositionMetadata positionMetadata;
            long l;
            block3 : {
                long l2;
                block2 : {
                    positionMetadata = this.getUnflattenedPos(n);
                    l2 = this.mExpandableListAdapter.getGroupId(positionMetadata.position.groupPos);
                    if (positionMetadata.position.type != 2) break block2;
                    l = this.mExpandableListAdapter.getCombinedGroupId(l2);
                    break block3;
                }
                if (positionMetadata.position.type != 1) break block4;
                l = this.mExpandableListAdapter.getChildId(positionMetadata.position.groupPos, positionMetadata.position.childPos);
                l = this.mExpandableListAdapter.getCombinedChildId(l2, l);
            }
            positionMetadata.recycle();
            return l;
        }
        throw new RuntimeException("Flat list position is of unknown type");
    }

    @Override
    public int getItemViewType(int n) {
        PositionMetadata positionMetadata = this.getUnflattenedPos(n);
        ExpandableListPosition expandableListPosition = positionMetadata.position;
        Object object = this.mExpandableListAdapter;
        if (object instanceof HeterogeneousExpandableList) {
            object = (HeterogeneousExpandableList)object;
            n = expandableListPosition.type == 2 ? object.getGroupType(expandableListPosition.groupPos) : object.getChildType(expandableListPosition.groupPos, expandableListPosition.childPos) + object.getGroupTypeCount();
        } else {
            n = expandableListPosition.type == 2 ? 0 : 1;
        }
        positionMetadata.recycle();
        return n;
    }

    PositionMetadata getUnflattenedPos(int n) {
        block10 : {
            int n2;
            int n3;
            block9 : {
                ArrayList<GroupMetadata> arrayList;
                GroupMetadata groupMetadata;
                int n4;
                block8 : {
                    arrayList = this.mExpGroupMetadataList;
                    int n5 = arrayList.size();
                    n3 = 0;
                    n2 = n5 - 1;
                    n4 = 0;
                    if (n5 == 0) {
                        return PositionMetadata.obtain(n, 2, n, -1, null, 0);
                    }
                    while (n3 <= n2) {
                        n5 = (n2 - n3) / 2 + n3;
                        groupMetadata = arrayList.get(n5);
                        if (n > groupMetadata.lastChildFlPos) {
                            n3 = n5 + 1;
                            n4 = n5;
                            continue;
                        }
                        if (n < groupMetadata.flPos) {
                            n2 = n5 - 1;
                            n4 = n5;
                            continue;
                        }
                        if (n == groupMetadata.flPos) {
                            return PositionMetadata.obtain(n, 2, groupMetadata.gPos, -1, groupMetadata, n5);
                        }
                        n4 = n5;
                        if (n > groupMetadata.lastChildFlPos) continue;
                        n3 = groupMetadata.flPos;
                        return PositionMetadata.obtain(n, 1, groupMetadata.gPos, n - (n3 + 1), groupMetadata, n5);
                    }
                    if (n3 <= n4) break block8;
                    groupMetadata = arrayList.get(n3 - 1);
                    n4 = groupMetadata.lastChildFlPos;
                    n2 = groupMetadata.gPos;
                    n2 = n - n4 + n2;
                    break block9;
                }
                if (n2 >= n4) break block10;
                n3 = n2 + 1;
                groupMetadata = arrayList.get(n3);
                n4 = groupMetadata.gPos;
                n2 = groupMetadata.flPos;
                n2 = n4 - (n2 - n);
            }
            return PositionMetadata.obtain(n, 2, n2, -1, null, n3);
        }
        throw new RuntimeException("Unknown state");
    }

    @Override
    public View getView(int n, View view, ViewGroup viewGroup) {
        block6 : {
            PositionMetadata positionMetadata;
            block5 : {
                block4 : {
                    positionMetadata = this.getUnflattenedPos(n);
                    if (positionMetadata.position.type != 2) break block4;
                    view = this.mExpandableListAdapter.getGroupView(positionMetadata.position.groupPos, positionMetadata.isExpanded(), view, viewGroup);
                    break block5;
                }
                int n2 = positionMetadata.position.type;
                boolean bl = true;
                if (n2 != 1) break block6;
                if (positionMetadata.groupMetadata.lastChildFlPos != n) {
                    bl = false;
                }
                view = this.mExpandableListAdapter.getChildView(positionMetadata.position.groupPos, positionMetadata.position.childPos, bl, view, viewGroup);
            }
            positionMetadata.recycle();
            return view;
        }
        throw new RuntimeException("Flat list position is of unknown type");
    }

    @Override
    public int getViewTypeCount() {
        Object object = this.mExpandableListAdapter;
        if (object instanceof HeterogeneousExpandableList) {
            object = (HeterogeneousExpandableList)object;
            return object.getGroupTypeCount() + object.getChildTypeCount();
        }
        return 2;
    }

    @Override
    public boolean hasStableIds() {
        return this.mExpandableListAdapter.hasStableIds();
    }

    @Override
    public boolean isEmpty() {
        ExpandableListAdapter expandableListAdapter = this.getAdapter();
        boolean bl = expandableListAdapter != null ? expandableListAdapter.isEmpty() : true;
        return bl;
    }

    @Override
    public boolean isEnabled(int n) {
        PositionMetadata positionMetadata = this.getUnflattenedPos(n);
        ExpandableListPosition expandableListPosition = positionMetadata.position;
        boolean bl = expandableListPosition.type == 1 ? this.mExpandableListAdapter.isChildSelectable(expandableListPosition.groupPos, expandableListPosition.childPos) : true;
        positionMetadata.recycle();
        return bl;
    }

    public boolean isGroupExpanded(int n) {
        for (int i = this.mExpGroupMetadataList.size() - 1; i >= 0; --i) {
            if (this.mExpGroupMetadataList.get((int)i).gPos != n) continue;
            return true;
        }
        return false;
    }

    public void setExpandableListAdapter(ExpandableListAdapter expandableListAdapter) {
        ExpandableListAdapter expandableListAdapter2 = this.mExpandableListAdapter;
        if (expandableListAdapter2 != null) {
            expandableListAdapter2.unregisterDataSetObserver(this.mDataSetObserver);
        }
        this.mExpandableListAdapter = expandableListAdapter;
        expandableListAdapter.registerDataSetObserver(this.mDataSetObserver);
    }

    void setExpandedGroupMetadataList(ArrayList<GroupMetadata> arrayList) {
        ExpandableListAdapter expandableListAdapter;
        if (arrayList != null && (expandableListAdapter = this.mExpandableListAdapter) != null) {
            int n = expandableListAdapter.getGroupCount();
            for (int i = arrayList.size() - 1; i >= 0; --i) {
                if (arrayList.get((int)i).gPos < n) continue;
                return;
            }
            this.mExpGroupMetadataList = arrayList;
            this.refreshExpGroupMetadataList(true, false);
            return;
        }
    }

    public void setMaxExpGroupCount(int n) {
        this.mMaxExpGroupCount = n;
    }

    static class GroupMetadata
    implements Parcelable,
    Comparable<GroupMetadata> {
        public static final Parcelable.Creator<GroupMetadata> CREATOR = new Parcelable.Creator<GroupMetadata>(){

            @Override
            public GroupMetadata createFromParcel(Parcel parcel) {
                return GroupMetadata.obtain(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readLong());
            }

            public GroupMetadata[] newArray(int n) {
                return new GroupMetadata[n];
            }
        };
        static final int REFRESH = -1;
        int flPos;
        long gId;
        int gPos;
        int lastChildFlPos;

        private GroupMetadata() {
        }

        static GroupMetadata obtain(int n, int n2, int n3, long l) {
            GroupMetadata groupMetadata = new GroupMetadata();
            groupMetadata.flPos = n;
            groupMetadata.lastChildFlPos = n2;
            groupMetadata.gPos = n3;
            groupMetadata.gId = l;
            return groupMetadata;
        }

        @Override
        public int compareTo(GroupMetadata groupMetadata) {
            if (groupMetadata != null) {
                return this.gPos - groupMetadata.gPos;
            }
            throw new IllegalArgumentException();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.flPos);
            parcel.writeInt(this.lastChildFlPos);
            parcel.writeInt(this.gPos);
            parcel.writeLong(this.gId);
        }

    }

    protected class MyDataSetObserver
    extends DataSetObserver {
        protected MyDataSetObserver() {
        }

        @Override
        public void onChanged() {
            ExpandableListConnector.this.refreshExpGroupMetadataList(true, true);
            ExpandableListConnector.this.notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            ExpandableListConnector.this.refreshExpGroupMetadataList(true, true);
            ExpandableListConnector.this.notifyDataSetInvalidated();
        }
    }

    public static class PositionMetadata {
        private static final int MAX_POOL_SIZE = 5;
        private static ArrayList<PositionMetadata> sPool = new ArrayList(5);
        public int groupInsertIndex;
        public GroupMetadata groupMetadata;
        public ExpandableListPosition position;

        private PositionMetadata() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private static PositionMetadata getRecycledOrCreate() {
            PositionMetadata positionMetadata;
            ArrayList<PositionMetadata> arrayList = sPool;
            synchronized (arrayList) {
                if (sPool.size() <= 0) return new PositionMetadata();
                positionMetadata = sPool.remove(0);
            }
            positionMetadata.resetState();
            return positionMetadata;
        }

        static PositionMetadata obtain(int n, int n2, int n3, int n4, GroupMetadata groupMetadata, int n5) {
            PositionMetadata positionMetadata = PositionMetadata.getRecycledOrCreate();
            positionMetadata.position = ExpandableListPosition.obtain(n2, n3, n4, n);
            positionMetadata.groupMetadata = groupMetadata;
            positionMetadata.groupInsertIndex = n5;
            return positionMetadata;
        }

        private void resetState() {
            ExpandableListPosition expandableListPosition = this.position;
            if (expandableListPosition != null) {
                expandableListPosition.recycle();
                this.position = null;
            }
            this.groupMetadata = null;
            this.groupInsertIndex = 0;
        }

        public boolean isExpanded() {
            boolean bl = this.groupMetadata != null;
            return bl;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void recycle() {
            this.resetState();
            ArrayList<PositionMetadata> arrayList = sPool;
            synchronized (arrayList) {
                if (sPool.size() < 5) {
                    sPool.add(this);
                }
                return;
            }
        }
    }

}

