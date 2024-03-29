/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.util.Pools;
import com.android.internal.widget.OpReorderer;
import com.android.internal.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class AdapterHelper
implements OpReorderer.Callback {
    private static final boolean DEBUG = false;
    static final int POSITION_TYPE_INVISIBLE = 0;
    static final int POSITION_TYPE_NEW_OR_LAID_OUT = 1;
    private static final String TAG = "AHT";
    final Callback mCallback;
    final boolean mDisableRecycler;
    private int mExistingUpdateTypes = 0;
    Runnable mOnItemProcessedCallback;
    final OpReorderer mOpReorderer;
    final ArrayList<UpdateOp> mPendingUpdates = new ArrayList();
    final ArrayList<UpdateOp> mPostponedList = new ArrayList();
    private Pools.Pool<UpdateOp> mUpdateOpPool = new Pools.SimplePool<UpdateOp>(30);

    AdapterHelper(Callback callback) {
        this(callback, false);
    }

    AdapterHelper(Callback callback, boolean bl) {
        this.mCallback = callback;
        this.mDisableRecycler = bl;
        this.mOpReorderer = new OpReorderer(this);
    }

    private void applyAdd(UpdateOp updateOp) {
        this.postponeAndUpdateViewHolders(updateOp);
    }

    private void applyMove(UpdateOp updateOp) {
        this.postponeAndUpdateViewHolders(updateOp);
    }

    private void applyRemove(UpdateOp updateOp) {
        int n = updateOp.positionStart;
        int n2 = 0;
        int n3 = updateOp.positionStart + updateOp.itemCount;
        int n4 = -1;
        int n5 = updateOp.positionStart;
        while (n5 < n3) {
            int n6 = 0;
            int n7 = 0;
            if (this.mCallback.findViewHolder(n5) == null && !this.canFindInPreLayout(n5)) {
                if (n4 == 1) {
                    this.postponeAndUpdateViewHolders(this.obtainUpdateOp(2, n, n2, null));
                    n7 = 1;
                }
                n6 = 0;
            } else {
                if (n4 == 0) {
                    this.dispatchAndUpdateViewHolders(this.obtainUpdateOp(2, n, n2, null));
                    n6 = 1;
                }
                n4 = 1;
                n7 = n6;
                n6 = n4;
            }
            if (n7 != 0) {
                n7 = n5 - n2;
                n3 -= n2;
                n5 = 1;
            } else {
                n7 = n5;
                n5 = ++n2;
            }
            n2 = n5;
            n4 = n6;
            n5 = ++n7;
        }
        UpdateOp updateOp2 = updateOp;
        if (n2 != updateOp.itemCount) {
            this.recycleUpdateOp(updateOp);
            updateOp2 = this.obtainUpdateOp(2, n, n2, null);
        }
        if (n4 == 0) {
            this.dispatchAndUpdateViewHolders(updateOp2);
        } else {
            this.postponeAndUpdateViewHolders(updateOp2);
        }
    }

    private void applyUpdate(UpdateOp updateOp) {
        int n = updateOp.positionStart;
        int n2 = 0;
        int n3 = updateOp.positionStart;
        int n4 = updateOp.itemCount;
        int n5 = -1;
        for (int i = updateOp.positionStart; i < n3 + n4; ++i) {
            int n6;
            int n7;
            int n8;
            if (this.mCallback.findViewHolder(i) == null && !this.canFindInPreLayout(i)) {
                n7 = n;
                n6 = n2;
                if (n5 == 1) {
                    this.postponeAndUpdateViewHolders(this.obtainUpdateOp(4, n, n2, updateOp.payload));
                    n6 = 0;
                    n7 = i;
                }
                n2 = 0;
                n = n7;
                n8 = n6;
                n6 = n2;
            } else {
                n7 = n;
                n8 = n2;
                if (n5 == 0) {
                    this.dispatchAndUpdateViewHolders(this.obtainUpdateOp(4, n, n2, updateOp.payload));
                    n8 = 0;
                    n7 = i;
                }
                n6 = 1;
                n = n7;
            }
            n2 = n8 + 1;
            n5 = n6;
        }
        Object object = updateOp;
        if (n2 != updateOp.itemCount) {
            object = updateOp.payload;
            this.recycleUpdateOp(updateOp);
            object = this.obtainUpdateOp(4, n, n2, object);
        }
        if (n5 == 0) {
            this.dispatchAndUpdateViewHolders((UpdateOp)object);
        } else {
            this.postponeAndUpdateViewHolders((UpdateOp)object);
        }
    }

    private boolean canFindInPreLayout(int n) {
        int n2 = this.mPostponedList.size();
        for (int i = 0; i < n2; ++i) {
            UpdateOp updateOp = this.mPostponedList.get(i);
            if (updateOp.cmd == 8) {
                if (this.findPositionOffset(updateOp.itemCount, i + 1) != n) continue;
                return true;
            }
            if (updateOp.cmd != 1) continue;
            int n3 = updateOp.positionStart;
            int n4 = updateOp.itemCount;
            for (int j = updateOp.positionStart; j < n3 + n4; ++j) {
                if (this.findPositionOffset(j, i + 1) != n) continue;
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void dispatchAndUpdateViewHolders(UpdateOp updateOp) {
        int n;
        int n2;
        Object object;
        int n3;
        int n4;
        int n5;
        if (updateOp.cmd != 1 && updateOp.cmd != 8) {
            n = this.updatePositionWithPostponed(updateOp.positionStart, updateOp.cmd);
            n3 = 1;
            n5 = updateOp.positionStart;
            n2 = updateOp.cmd;
            if (n2 != 2) {
                if (n2 != 4) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("op should be remove or update.");
                    stringBuilder.append(updateOp);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                n4 = 1;
            } else {
                n4 = 0;
            }
        } else {
            throw new IllegalArgumentException("should not dispatch add or move for pre layout");
        }
        for (int i = 1; i < updateOp.itemCount; ++i) {
            int n6 = this.updatePositionWithPostponed(updateOp.positionStart + n4 * i, updateOp.cmd);
            int n7 = 0;
            int n8 = updateOp.cmd;
            int n9 = 0;
            n2 = 0;
            if (n8 != 2) {
                if (n8 != 4) {
                    n2 = n7;
                } else if (n6 == n + 1) {
                    n2 = 1;
                }
            } else {
                n2 = n9;
                if (n6 == n) {
                    n2 = 1;
                }
            }
            if (n2 != 0) {
                n2 = n3 + 1;
            } else {
                object = this.obtainUpdateOp(updateOp.cmd, n, n3, updateOp.payload);
                this.dispatchFirstPassAndUpdateViewHolders((UpdateOp)object, n5);
                this.recycleUpdateOp((UpdateOp)object);
                n2 = n5;
                if (updateOp.cmd == 4) {
                    n2 = n5 + n3;
                }
                n = n6;
                n3 = 1;
                n5 = n2;
                n2 = n3;
            }
            n3 = n2;
        }
        object = updateOp.payload;
        this.recycleUpdateOp(updateOp);
        if (n3 > 0) {
            updateOp = this.obtainUpdateOp(updateOp.cmd, n, n3, object);
            this.dispatchFirstPassAndUpdateViewHolders(updateOp, n5);
            this.recycleUpdateOp(updateOp);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void postponeAndUpdateViewHolders(UpdateOp updateOp) {
        this.mPostponedList.add(updateOp);
        int n = updateOp.cmd;
        if (n == 1) {
            this.mCallback.offsetPositionsForAdd(updateOp.positionStart, updateOp.itemCount);
            return;
        }
        if (n == 2) {
            this.mCallback.offsetPositionsForRemovingLaidOutOrNewView(updateOp.positionStart, updateOp.itemCount);
            return;
        }
        if (n == 4) {
            this.mCallback.markViewHoldersUpdated(updateOp.positionStart, updateOp.itemCount, updateOp.payload);
            return;
        }
        if (n == 8) {
            this.mCallback.offsetPositionsForMove(updateOp.positionStart, updateOp.itemCount);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown update op type for ");
        stringBuilder.append(updateOp);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private int updatePositionWithPostponed(int n, int n2) {
        UpdateOp updateOp;
        int n3 = n;
        for (int i = this.mPostponedList.size() - 1; i >= 0; --i) {
            updateOp = this.mPostponedList.get(i);
            if (updateOp.cmd == 8) {
                int n4;
                if (updateOp.positionStart < updateOp.itemCount) {
                    n4 = updateOp.positionStart;
                    n = updateOp.itemCount;
                } else {
                    n4 = updateOp.itemCount;
                    n = updateOp.positionStart;
                }
                if (n3 >= n4 && n3 <= n) {
                    if (n4 == updateOp.positionStart) {
                        if (n2 == 1) {
                            ++updateOp.itemCount;
                        } else if (n2 == 2) {
                            --updateOp.itemCount;
                        }
                        n = n3 + 1;
                    } else {
                        if (n2 == 1) {
                            ++updateOp.positionStart;
                        } else if (n2 == 2) {
                            --updateOp.positionStart;
                        }
                        n = n3 - 1;
                    }
                } else {
                    n = n3;
                    if (n3 < updateOp.positionStart) {
                        if (n2 == 1) {
                            ++updateOp.positionStart;
                            ++updateOp.itemCount;
                            n = n3;
                        } else {
                            n = n3;
                            if (n2 == 2) {
                                --updateOp.positionStart;
                                --updateOp.itemCount;
                                n = n3;
                            }
                        }
                    }
                }
            } else if (updateOp.positionStart <= n3) {
                if (updateOp.cmd == 1) {
                    n = n3 - updateOp.itemCount;
                } else {
                    n = n3;
                    if (updateOp.cmd == 2) {
                        n = n3 + updateOp.itemCount;
                    }
                }
            } else if (n2 == 1) {
                ++updateOp.positionStart;
                n = n3;
            } else {
                n = n3;
                if (n2 == 2) {
                    --updateOp.positionStart;
                    n = n3;
                }
            }
            n3 = n;
        }
        for (n = this.mPostponedList.size() - 1; n >= 0; --n) {
            updateOp = this.mPostponedList.get(n);
            if (updateOp.cmd == 8) {
                if (updateOp.itemCount != updateOp.positionStart && updateOp.itemCount >= 0) continue;
                this.mPostponedList.remove(n);
                this.recycleUpdateOp(updateOp);
                continue;
            }
            if (updateOp.itemCount > 0) continue;
            this.mPostponedList.remove(n);
            this.recycleUpdateOp(updateOp);
        }
        return n3;
    }

    AdapterHelper addUpdateOp(UpdateOp ... arrupdateOp) {
        Collections.addAll(this.mPendingUpdates, arrupdateOp);
        return this;
    }

    public int applyPendingUpdatesToPosition(int n) {
        int n2 = this.mPendingUpdates.size();
        int n3 = n;
        for (int i = 0; i < n2; ++i) {
            UpdateOp updateOp = this.mPendingUpdates.get(i);
            n = updateOp.cmd;
            if (n != 1) {
                if (n != 2) {
                    if (n != 8) {
                        n = n3;
                    } else if (updateOp.positionStart == n3) {
                        n = updateOp.itemCount;
                    } else {
                        int n4 = n3;
                        if (updateOp.positionStart < n3) {
                            n4 = n3 - 1;
                        }
                        n = n4;
                        if (updateOp.itemCount <= n4) {
                            n = n4 + 1;
                        }
                    }
                } else {
                    n = n3;
                    if (updateOp.positionStart <= n3) {
                        if (updateOp.positionStart + updateOp.itemCount > n3) {
                            return -1;
                        }
                        n = n3 - updateOp.itemCount;
                    }
                }
            } else {
                n = n3;
                if (updateOp.positionStart <= n3) {
                    n = n3 + updateOp.itemCount;
                }
            }
            n3 = n;
        }
        return n3;
    }

    void consumePostponedUpdates() {
        int n = this.mPostponedList.size();
        for (int i = 0; i < n; ++i) {
            this.mCallback.onDispatchSecondPass(this.mPostponedList.get(i));
        }
        this.recycleUpdateOpsAndClearList(this.mPostponedList);
        this.mExistingUpdateTypes = 0;
    }

    void consumeUpdatesInOnePass() {
        this.consumePostponedUpdates();
        int n = this.mPendingUpdates.size();
        for (int i = 0; i < n; ++i) {
            Object object = this.mPendingUpdates.get(i);
            int n2 = ((UpdateOp)object).cmd;
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 != 4) {
                        if (n2 == 8) {
                            this.mCallback.onDispatchSecondPass((UpdateOp)object);
                            this.mCallback.offsetPositionsForMove(((UpdateOp)object).positionStart, ((UpdateOp)object).itemCount);
                        }
                    } else {
                        this.mCallback.onDispatchSecondPass((UpdateOp)object);
                        this.mCallback.markViewHoldersUpdated(((UpdateOp)object).positionStart, ((UpdateOp)object).itemCount, ((UpdateOp)object).payload);
                    }
                } else {
                    this.mCallback.onDispatchSecondPass((UpdateOp)object);
                    this.mCallback.offsetPositionsForRemovingInvisible(((UpdateOp)object).positionStart, ((UpdateOp)object).itemCount);
                }
            } else {
                this.mCallback.onDispatchSecondPass((UpdateOp)object);
                this.mCallback.offsetPositionsForAdd(((UpdateOp)object).positionStart, ((UpdateOp)object).itemCount);
            }
            object = this.mOnItemProcessedCallback;
            if (object == null) continue;
            object.run();
        }
        this.recycleUpdateOpsAndClearList(this.mPendingUpdates);
        this.mExistingUpdateTypes = 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    void dispatchFirstPassAndUpdateViewHolders(UpdateOp updateOp, int n) {
        this.mCallback.onDispatchFirstPass(updateOp);
        int n2 = updateOp.cmd;
        if (n2 != 2) {
            if (n2 != 4) throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
            this.mCallback.markViewHoldersUpdated(n, updateOp.itemCount, updateOp.payload);
            return;
        } else {
            this.mCallback.offsetPositionsForRemovingInvisible(n, updateOp.itemCount);
        }
    }

    int findPositionOffset(int n) {
        return this.findPositionOffset(n, 0);
    }

    int findPositionOffset(int n, int n2) {
        int n3 = this.mPostponedList.size();
        int n4 = n2;
        n2 = n;
        while (n4 < n3) {
            UpdateOp updateOp = this.mPostponedList.get(n4);
            if (updateOp.cmd == 8) {
                if (updateOp.positionStart == n2) {
                    n = updateOp.itemCount;
                } else {
                    int n5 = n2;
                    if (updateOp.positionStart < n2) {
                        n5 = n2 - 1;
                    }
                    n = n5;
                    if (updateOp.itemCount <= n5) {
                        n = n5 + 1;
                    }
                }
            } else {
                n = n2;
                if (updateOp.positionStart <= n2) {
                    if (updateOp.cmd == 2) {
                        if (n2 < updateOp.positionStart + updateOp.itemCount) {
                            return -1;
                        }
                        n = n2 - updateOp.itemCount;
                    } else {
                        n = n2;
                        if (updateOp.cmd == 1) {
                            n = n2 + updateOp.itemCount;
                        }
                    }
                }
            }
            ++n4;
            n2 = n;
        }
        return n2;
    }

    boolean hasAnyUpdateTypes(int n) {
        boolean bl = (this.mExistingUpdateTypes & n) != 0;
        return bl;
    }

    boolean hasPendingUpdates() {
        boolean bl = this.mPendingUpdates.size() > 0;
        return bl;
    }

    boolean hasUpdates() {
        boolean bl = !this.mPostponedList.isEmpty() && !this.mPendingUpdates.isEmpty();
        return bl;
    }

    @Override
    public UpdateOp obtainUpdateOp(int n, int n2, int n3, Object object) {
        UpdateOp updateOp = this.mUpdateOpPool.acquire();
        if (updateOp == null) {
            object = new UpdateOp(n, n2, n3, object);
        } else {
            updateOp.cmd = n;
            updateOp.positionStart = n2;
            updateOp.itemCount = n3;
            updateOp.payload = object;
            object = updateOp;
        }
        return object;
    }

    boolean onItemRangeChanged(int n, int n2, Object object) {
        boolean bl = false;
        if (n2 < 1) {
            return false;
        }
        this.mPendingUpdates.add(this.obtainUpdateOp(4, n, n2, object));
        this.mExistingUpdateTypes |= 4;
        if (this.mPendingUpdates.size() == 1) {
            bl = true;
        }
        return bl;
    }

    boolean onItemRangeInserted(int n, int n2) {
        boolean bl = false;
        if (n2 < 1) {
            return false;
        }
        this.mPendingUpdates.add(this.obtainUpdateOp(1, n, n2, null));
        this.mExistingUpdateTypes |= 1;
        if (this.mPendingUpdates.size() == 1) {
            bl = true;
        }
        return bl;
    }

    boolean onItemRangeMoved(int n, int n2, int n3) {
        boolean bl = false;
        if (n == n2) {
            return false;
        }
        if (n3 == 1) {
            this.mPendingUpdates.add(this.obtainUpdateOp(8, n, n2, null));
            this.mExistingUpdateTypes |= 8;
            if (this.mPendingUpdates.size() == 1) {
                bl = true;
            }
            return bl;
        }
        throw new IllegalArgumentException("Moving more than 1 item is not supported yet");
    }

    boolean onItemRangeRemoved(int n, int n2) {
        boolean bl = false;
        if (n2 < 1) {
            return false;
        }
        this.mPendingUpdates.add(this.obtainUpdateOp(2, n, n2, null));
        this.mExistingUpdateTypes |= 2;
        if (this.mPendingUpdates.size() == 1) {
            bl = true;
        }
        return bl;
    }

    void preProcess() {
        this.mOpReorderer.reorderOps(this.mPendingUpdates);
        int n = this.mPendingUpdates.size();
        for (int i = 0; i < n; ++i) {
            Object object = this.mPendingUpdates.get(i);
            int n2 = ((UpdateOp)object).cmd;
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 != 4) {
                        if (n2 == 8) {
                            this.applyMove((UpdateOp)object);
                        }
                    } else {
                        this.applyUpdate((UpdateOp)object);
                    }
                } else {
                    this.applyRemove((UpdateOp)object);
                }
            } else {
                this.applyAdd((UpdateOp)object);
            }
            object = this.mOnItemProcessedCallback;
            if (object == null) continue;
            object.run();
        }
        this.mPendingUpdates.clear();
    }

    @Override
    public void recycleUpdateOp(UpdateOp updateOp) {
        if (!this.mDisableRecycler) {
            updateOp.payload = null;
            this.mUpdateOpPool.release(updateOp);
        }
    }

    void recycleUpdateOpsAndClearList(List<UpdateOp> list) {
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            this.recycleUpdateOp(list.get(i));
        }
        list.clear();
    }

    void reset() {
        this.recycleUpdateOpsAndClearList(this.mPendingUpdates);
        this.recycleUpdateOpsAndClearList(this.mPostponedList);
        this.mExistingUpdateTypes = 0;
    }

    static interface Callback {
        public RecyclerView.ViewHolder findViewHolder(int var1);

        public void markViewHoldersUpdated(int var1, int var2, Object var3);

        public void offsetPositionsForAdd(int var1, int var2);

        public void offsetPositionsForMove(int var1, int var2);

        public void offsetPositionsForRemovingInvisible(int var1, int var2);

        public void offsetPositionsForRemovingLaidOutOrNewView(int var1, int var2);

        public void onDispatchFirstPass(UpdateOp var1);

        public void onDispatchSecondPass(UpdateOp var1);
    }

    static class UpdateOp {
        static final int ADD = 1;
        static final int MOVE = 8;
        static final int POOL_SIZE = 30;
        static final int REMOVE = 2;
        static final int UPDATE = 4;
        int cmd;
        int itemCount;
        Object payload;
        int positionStart;

        UpdateOp(int n, int n2, int n3, Object object) {
            this.cmd = n;
            this.positionStart = n2;
            this.itemCount = n3;
            this.payload = object;
        }

        String cmdToString() {
            int n = this.cmd;
            if (n != 1) {
                if (n != 2) {
                    if (n != 4) {
                        if (n != 8) {
                            return "??";
                        }
                        return "mv";
                    }
                    return "up";
                }
                return "rm";
            }
            return "add";
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (UpdateOp)object;
                int n = this.cmd;
                if (n != ((UpdateOp)object).cmd) {
                    return false;
                }
                if (n == 8 && Math.abs(this.itemCount - this.positionStart) == 1 && this.itemCount == ((UpdateOp)object).positionStart && this.positionStart == ((UpdateOp)object).itemCount) {
                    return true;
                }
                if (this.itemCount != ((UpdateOp)object).itemCount) {
                    return false;
                }
                if (this.positionStart != ((UpdateOp)object).positionStart) {
                    return false;
                }
                Object object2 = this.payload;
                return !(object2 != null ? !object2.equals(((UpdateOp)object).payload) : ((UpdateOp)object).payload != null);
            }
            return false;
        }

        public int hashCode() {
            return (this.cmd * 31 + this.positionStart) * 31 + this.itemCount;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append("[");
            stringBuilder.append(this.cmdToString());
            stringBuilder.append(",s:");
            stringBuilder.append(this.positionStart);
            stringBuilder.append("c:");
            stringBuilder.append(this.itemCount);
            stringBuilder.append(",p:");
            stringBuilder.append(this.payload);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

}

