/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.widget.ExpandableListView;
import java.util.ArrayList;

class ExpandableListPosition {
    public static final int CHILD = 1;
    public static final int GROUP = 2;
    private static final int MAX_POOL_SIZE = 5;
    private static ArrayList<ExpandableListPosition> sPool = new ArrayList(5);
    public int childPos;
    int flatListPos;
    public int groupPos;
    public int type;

    private ExpandableListPosition() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static ExpandableListPosition getRecycledOrCreate() {
        ExpandableListPosition expandableListPosition;
        ArrayList<ExpandableListPosition> arrayList = sPool;
        synchronized (arrayList) {
            if (sPool.size() <= 0) return new ExpandableListPosition();
            expandableListPosition = sPool.remove(0);
        }
        expandableListPosition.resetState();
        return expandableListPosition;
    }

    static ExpandableListPosition obtain(int n, int n2, int n3, int n4) {
        ExpandableListPosition expandableListPosition = ExpandableListPosition.getRecycledOrCreate();
        expandableListPosition.type = n;
        expandableListPosition.groupPos = n2;
        expandableListPosition.childPos = n3;
        expandableListPosition.flatListPos = n4;
        return expandableListPosition;
    }

    static ExpandableListPosition obtainChildPosition(int n, int n2) {
        return ExpandableListPosition.obtain(1, n, n2, 0);
    }

    static ExpandableListPosition obtainGroupPosition(int n) {
        return ExpandableListPosition.obtain(2, n, 0, 0);
    }

    static ExpandableListPosition obtainPosition(long l) {
        if (l == 0xFFFFFFFFL) {
            return null;
        }
        ExpandableListPosition expandableListPosition = ExpandableListPosition.getRecycledOrCreate();
        expandableListPosition.groupPos = ExpandableListView.getPackedPositionGroup(l);
        if (ExpandableListView.getPackedPositionType(l) == 1) {
            expandableListPosition.type = 1;
            expandableListPosition.childPos = ExpandableListView.getPackedPositionChild(l);
        } else {
            expandableListPosition.type = 2;
        }
        return expandableListPosition;
    }

    private void resetState() {
        this.groupPos = 0;
        this.childPos = 0;
        this.flatListPos = 0;
        this.type = 0;
    }

    long getPackedPosition() {
        if (this.type == 1) {
            return ExpandableListView.getPackedPositionForChild(this.groupPos, this.childPos);
        }
        return ExpandableListView.getPackedPositionForGroup(this.groupPos);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void recycle() {
        ArrayList<ExpandableListPosition> arrayList = sPool;
        synchronized (arrayList) {
            if (sPool.size() < 5) {
                sPool.add(this);
            }
            return;
        }
    }
}

