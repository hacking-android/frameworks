/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.os.Trace;
import android.view.View;
import com.android.internal.widget.AdapterHelper;
import com.android.internal.widget.ChildHelper;
import com.android.internal.widget.RecyclerView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

final class GapWorker
implements Runnable {
    static final ThreadLocal<GapWorker> sGapWorker = new ThreadLocal();
    static Comparator<Task> sTaskComparator = new Comparator<Task>(){

        @Override
        public int compare(Task task, Task task2) {
            int n;
            RecyclerView recyclerView = task.view;
            int n2 = 1;
            int n3 = 1;
            int n4 = recyclerView == null ? 1 : 0;
            if (n4 != (n = task2.view == null)) {
                n4 = task.view == null ? n3 : -1;
                return n4;
            }
            if (task.immediate != task2.immediate) {
                n4 = n2;
                if (task.immediate) {
                    n4 = -1;
                }
                return n4;
            }
            n4 = task2.viewVelocity - task.viewVelocity;
            if (n4 != 0) {
                return n4;
            }
            n4 = task.distanceToItem - task2.distanceToItem;
            if (n4 != 0) {
                return n4;
            }
            return 0;
        }
    };
    long mFrameIntervalNs;
    long mPostTimeNs;
    ArrayList<RecyclerView> mRecyclerViews = new ArrayList();
    private ArrayList<Task> mTasks = new ArrayList();

    GapWorker() {
    }

    private void buildTaskList() {
        Object object;
        int n;
        int n2 = this.mRecyclerViews.size();
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            object = this.mRecyclerViews.get(n);
            ((RecyclerView)object).mPrefetchRegistry.collectPrefetchPositionsFromView((RecyclerView)object, false);
            n3 += object.mPrefetchRegistry.mCount;
        }
        this.mTasks.ensureCapacity(n3);
        n3 = 0;
        for (n = 0; n < n2; ++n) {
            RecyclerView recyclerView = this.mRecyclerViews.get(n);
            LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = recyclerView.mPrefetchRegistry;
            int n4 = Math.abs(layoutPrefetchRegistryImpl.mPrefetchDx) + Math.abs(layoutPrefetchRegistryImpl.mPrefetchDy);
            for (int i = 0; i < layoutPrefetchRegistryImpl.mCount * 2; i += 2) {
                if (n3 >= this.mTasks.size()) {
                    object = new Task();
                    this.mTasks.add((Task)object);
                } else {
                    object = this.mTasks.get(n3);
                }
                int n5 = layoutPrefetchRegistryImpl.mPrefetchArray[i + 1];
                boolean bl = n5 <= n4;
                ((Task)object).immediate = bl;
                ((Task)object).viewVelocity = n4;
                ((Task)object).distanceToItem = n5;
                ((Task)object).view = recyclerView;
                ((Task)object).position = layoutPrefetchRegistryImpl.mPrefetchArray[i];
                ++n3;
            }
        }
        Collections.sort(this.mTasks, sTaskComparator);
    }

    private void flushTaskWithDeadline(Task object, long l) {
        long l2 = ((Task)object).immediate ? Long.MAX_VALUE : l;
        object = this.prefetchPositionWithDeadline(((Task)object).view, ((Task)object).position, l2);
        if (object != null && ((RecyclerView.ViewHolder)object).mNestedRecyclerView != null) {
            this.prefetchInnerRecyclerViewWithDeadline((RecyclerView)((RecyclerView.ViewHolder)object).mNestedRecyclerView.get(), l);
        }
    }

    private void flushTasksWithDeadline(long l) {
        for (int i = 0; i < this.mTasks.size(); ++i) {
            Task task = this.mTasks.get(i);
            if (task.view == null) break;
            this.flushTaskWithDeadline(task, l);
            task.clear();
        }
    }

    static boolean isPrefetchPositionAttached(RecyclerView recyclerView, int n) {
        int n2 = recyclerView.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n2; ++i) {
            RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(recyclerView.mChildHelper.getUnfilteredChildAt(i));
            if (viewHolder.mPosition != n || viewHolder.isInvalid()) continue;
            return true;
        }
        return false;
    }

    private void prefetchInnerRecyclerViewWithDeadline(RecyclerView recyclerView, long l) {
        if (recyclerView == null) {
            return;
        }
        if (recyclerView.mDataSetHasChangedAfterLayout && recyclerView.mChildHelper.getUnfilteredChildCount() != 0) {
            recyclerView.removeAndRecycleViews();
        }
        LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = recyclerView.mPrefetchRegistry;
        layoutPrefetchRegistryImpl.collectPrefetchPositionsFromView(recyclerView, true);
        if (layoutPrefetchRegistryImpl.mCount != 0) {
            int n;
            try {
                Trace.beginSection("RV Nested Prefetch");
                recyclerView.mState.prepareForNestedPrefetch(recyclerView.mAdapter);
                n = 0;
            }
            catch (Throwable throwable) {
                Trace.endSection();
                throw throwable;
            }
            do {
                if (n >= layoutPrefetchRegistryImpl.mCount * 2) break;
                this.prefetchPositionWithDeadline(recyclerView, layoutPrefetchRegistryImpl.mPrefetchArray[n], l);
                n += 2;
            } while (true);
            Trace.endSection();
        }
    }

    private RecyclerView.ViewHolder prefetchPositionWithDeadline(RecyclerView object, int n, long l) {
        if (GapWorker.isPrefetchPositionAttached((RecyclerView)object, n)) {
            return null;
        }
        RecyclerView.Recycler recycler = ((RecyclerView)object).mRecycler;
        object = recycler.tryGetViewHolderForPositionByDeadline(n, false, l);
        if (object != null) {
            if (((RecyclerView.ViewHolder)object).isBound()) {
                recycler.recycleView(((RecyclerView.ViewHolder)object).itemView);
            } else {
                recycler.addViewHolderToRecycledViewPool((RecyclerView.ViewHolder)object, false);
            }
        }
        return object;
    }

    public void add(RecyclerView recyclerView) {
        this.mRecyclerViews.add(recyclerView);
    }

    void postFromTraversal(RecyclerView recyclerView, int n, int n2) {
        if (recyclerView.isAttachedToWindow() && this.mPostTimeNs == 0L) {
            this.mPostTimeNs = recyclerView.getNanoTime();
            recyclerView.post(this);
        }
        recyclerView.mPrefetchRegistry.setPrefetchVector(n, n2);
    }

    void prefetch(long l) {
        this.buildTaskList();
        this.flushTasksWithDeadline(l);
    }

    public void remove(RecyclerView recyclerView) {
        this.mRecyclerViews.remove(recyclerView);
    }

    @Override
    public void run() {
        long l;
        block6 : {
            block5 : {
                Trace.beginSection("RV Prefetch");
                boolean bl = this.mRecyclerViews.isEmpty();
                if (!bl) break block5;
                this.mPostTimeNs = 0L;
                Trace.endSection();
                return;
            }
            l = TimeUnit.MILLISECONDS.toNanos(this.mRecyclerViews.get(0).getDrawingTime());
            if (l != 0L) break block6;
            this.mPostTimeNs = 0L;
            Trace.endSection();
            return;
        }
        try {
            this.prefetch(this.mFrameIntervalNs + l);
            return;
        }
        finally {
            this.mPostTimeNs = 0L;
            Trace.endSection();
        }
    }

    static class LayoutPrefetchRegistryImpl
    implements RecyclerView.LayoutManager.LayoutPrefetchRegistry {
        int mCount;
        int[] mPrefetchArray;
        int mPrefetchDx;
        int mPrefetchDy;

        LayoutPrefetchRegistryImpl() {
        }

        @Override
        public void addPosition(int n, int n2) {
            if (n2 >= 0) {
                int n3 = this.mCount * 2;
                int[] arrn = this.mPrefetchArray;
                if (arrn == null) {
                    this.mPrefetchArray = new int[4];
                    Arrays.fill(this.mPrefetchArray, -1);
                } else if (n3 >= arrn.length) {
                    arrn = this.mPrefetchArray;
                    this.mPrefetchArray = new int[n3 * 2];
                    System.arraycopy(arrn, 0, this.mPrefetchArray, 0, arrn.length);
                }
                arrn = this.mPrefetchArray;
                arrn[n3] = n;
                arrn[n3 + 1] = n2;
                ++this.mCount;
                return;
            }
            throw new IllegalArgumentException("Pixel distance must be non-negative");
        }

        void clearPrefetchPositions() {
            int[] arrn = this.mPrefetchArray;
            if (arrn != null) {
                Arrays.fill(arrn, -1);
            }
        }

        void collectPrefetchPositionsFromView(RecyclerView recyclerView, boolean bl) {
            this.mCount = 0;
            Object object = this.mPrefetchArray;
            if (object != null) {
                Arrays.fill((int[])object, -1);
            }
            object = recyclerView.mLayout;
            if (recyclerView.mAdapter != null && object != null && ((RecyclerView.LayoutManager)object).isItemPrefetchEnabled()) {
                if (bl) {
                    if (!recyclerView.mAdapterHelper.hasPendingUpdates()) {
                        ((RecyclerView.LayoutManager)object).collectInitialPrefetchPositions(recyclerView.mAdapter.getItemCount(), this);
                    }
                } else if (!recyclerView.hasPendingAdapterUpdates()) {
                    ((RecyclerView.LayoutManager)object).collectAdjacentPrefetchPositions(this.mPrefetchDx, this.mPrefetchDy, recyclerView.mState, this);
                }
                if (this.mCount > ((RecyclerView.LayoutManager)object).mPrefetchMaxCountObserved) {
                    ((RecyclerView.LayoutManager)object).mPrefetchMaxCountObserved = this.mCount;
                    ((RecyclerView.LayoutManager)object).mPrefetchMaxObservedInInitialPrefetch = bl;
                    recyclerView.mRecycler.updateViewCacheSize();
                }
            }
        }

        boolean lastPrefetchIncludedPosition(int n) {
            if (this.mPrefetchArray != null) {
                int n2 = this.mCount;
                for (int i = 0; i < n2 * 2; i += 2) {
                    if (this.mPrefetchArray[i] != n) continue;
                    return true;
                }
            }
            return false;
        }

        void setPrefetchVector(int n, int n2) {
            this.mPrefetchDx = n;
            this.mPrefetchDy = n2;
        }
    }

    static class Task {
        public int distanceToItem;
        public boolean immediate;
        public int position;
        public RecyclerView view;
        public int viewVelocity;

        Task() {
        }

        public void clear() {
            this.immediate = false;
            this.viewVelocity = 0;
            this.distanceToItem = 0;
            this.view = null;
            this.position = 0;
        }
    }

}

