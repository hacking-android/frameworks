/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.content.UndoOperation;
import android.content.UndoOwner;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArrayMap;
import java.util.ArrayList;

public class UndoManager {
    public static final int MERGE_MODE_ANY = 2;
    public static final int MERGE_MODE_NONE = 0;
    public static final int MERGE_MODE_UNIQUE = 1;
    private int mCommitId = 1;
    private int mHistorySize = 20;
    private boolean mInUndo;
    private boolean mMerged;
    private int mNextSavedIdx;
    private final ArrayMap<String, UndoOwner> mOwners = new ArrayMap(1);
    private final ArrayList<UndoState> mRedos = new ArrayList();
    private UndoOwner[] mStateOwners;
    private int mStateSeq;
    private final ArrayList<UndoState> mUndos = new ArrayList();
    private int mUpdateCount;
    private UndoState mWorking;

    private void createWorkingState() {
        int n = this.mCommitId;
        this.mCommitId = n + 1;
        this.mWorking = new UndoState(this, n);
        if (this.mCommitId < 0) {
            this.mCommitId = 1;
        }
    }

    private void pushWorkingState() {
        int n = this.mUndos.size() + 1;
        if (this.mWorking.hasData()) {
            this.mUndos.add(this.mWorking);
            this.forgetRedos(null, -1);
            this.mWorking.commit();
            if (n >= 2) {
                this.mUndos.get(n - 2).makeExecuted();
            }
        } else {
            this.mWorking.destroy();
        }
        this.mWorking = null;
        int n2 = this.mHistorySize;
        if (n2 >= 0 && n > n2) {
            this.forgetUndos(null, n - n2);
        }
    }

    @UnsupportedAppUsage
    public void addOperation(UndoOperation<?> undoOperation, int n) {
        if (this.mWorking != null) {
            if (undoOperation.getOwner().mManager == this) {
                UndoState undoState;
                if (!(n == 0 || this.mMerged || this.mWorking.hasData() || (undoState = this.getTopUndo(null)) == null || n != 2 && undoState.hasMultipleOwners() || !undoState.canMerge() || !undoState.hasOperation(undoOperation.getOwner()))) {
                    this.mWorking.destroy();
                    this.mWorking = undoState;
                    this.mUndos.remove(undoState);
                    this.mMerged = true;
                }
                this.mWorking.addOperation(undoOperation);
                return;
            }
            throw new IllegalArgumentException("Given operation's owner is not in this undo manager.");
        }
        throw new IllegalStateException("Must be called during an update");
    }

    @UnsupportedAppUsage
    public void beginUpdate(CharSequence charSequence) {
        if (!this.mInUndo) {
            if (this.mUpdateCount <= 0) {
                this.createWorkingState();
                this.mMerged = false;
                this.mUpdateCount = 0;
            }
            this.mWorking.updateLabel(charSequence);
            ++this.mUpdateCount;
            return;
        }
        throw new IllegalStateException("Can't being update while performing undo/redo");
    }

    @UnsupportedAppUsage
    public int commitState(UndoOwner undoOwner) {
        UndoState undoState = this.mWorking;
        if (undoState != null && undoState.hasData()) {
            if (undoOwner == null || this.mWorking.hasOperation(undoOwner)) {
                this.mWorking.setCanMerge(false);
                int n = this.mWorking.getCommitId();
                this.pushWorkingState();
                this.createWorkingState();
                this.mMerged = true;
                return n;
            }
        } else {
            undoState = this.getTopUndo(null);
            if (undoState != null && (undoOwner == null || undoState.hasOperation(undoOwner))) {
                undoState.setCanMerge(false);
                return undoState.getCommitId();
            }
        }
        return -1;
    }

    @UnsupportedAppUsage
    public int countRedos(UndoOwner[] arrundoOwner) {
        if (arrundoOwner == null) {
            return this.mRedos.size();
        }
        int n = 0;
        int n2 = 0;
        while ((n2 = this.findNextState(this.mRedos, arrundoOwner, n2)) >= 0) {
            ++n;
            ++n2;
        }
        return n;
    }

    @UnsupportedAppUsage
    public int countUndos(UndoOwner[] arrundoOwner) {
        if (arrundoOwner == null) {
            return this.mUndos.size();
        }
        int n = 0;
        int n2 = 0;
        while ((n2 = this.findNextState(this.mUndos, arrundoOwner, n2)) >= 0) {
            ++n;
            ++n2;
        }
        return n;
    }

    @UnsupportedAppUsage
    public void endUpdate() {
        if (this.mWorking != null) {
            --this.mUpdateCount;
            if (this.mUpdateCount == 0) {
                this.pushWorkingState();
            }
            return;
        }
        throw new IllegalStateException("Must be called during an update");
    }

    int findNextState(ArrayList<UndoState> arrayList, UndoOwner[] arrundoOwner, int n) {
        int n2 = arrayList.size();
        int n3 = n;
        if (n < 0) {
            n3 = 0;
        }
        if (n3 >= n2) {
            return -1;
        }
        if (arrundoOwner == null) {
            return n3;
        }
        for (n = n3; n < n2; ++n) {
            if (!this.matchOwners(arrayList.get(n), arrundoOwner)) continue;
            return n;
        }
        return -1;
    }

    int findPrevState(ArrayList<UndoState> arrayList, UndoOwner[] arrundoOwner, int n) {
        int n2 = arrayList.size();
        int n3 = n;
        if (n == -1) {
            n3 = n2 - 1;
        }
        if (n3 >= n2) {
            return -1;
        }
        if (arrundoOwner == null) {
            return n3;
        }
        for (n = n3; n >= 0; --n) {
            if (!this.matchOwners(arrayList.get(n), arrundoOwner)) continue;
            return n;
        }
        return -1;
    }

    @UnsupportedAppUsage
    public int forgetRedos(UndoOwner[] arrundoOwner, int n) {
        int n2 = n;
        if (n < 0) {
            n2 = this.mRedos.size();
        }
        int n3 = 0;
        n = 0;
        while (n < this.mRedos.size() && n3 < n2) {
            UndoState undoState = this.mRedos.get(n);
            if (n2 > 0 && this.matchOwners(undoState, arrundoOwner)) {
                undoState.destroy();
                this.mRedos.remove(n);
                ++n3;
                continue;
            }
            ++n;
        }
        return n3;
    }

    @UnsupportedAppUsage
    public int forgetUndos(UndoOwner[] arrundoOwner, int n) {
        int n2 = n;
        if (n < 0) {
            n2 = this.mUndos.size();
        }
        int n3 = 0;
        n = 0;
        while (n < this.mUndos.size() && n3 < n2) {
            UndoState undoState = this.mUndos.get(n);
            if (n2 > 0 && this.matchOwners(undoState, arrundoOwner)) {
                undoState.destroy();
                this.mUndos.remove(n);
                ++n3;
                continue;
            }
            ++n;
        }
        return n3;
    }

    public int getHistorySize() {
        return this.mHistorySize;
    }

    public UndoOperation<?> getLastOperation(int n) {
        return this.getLastOperation(null, null, n);
    }

    public UndoOperation<?> getLastOperation(UndoOwner undoOwner, int n) {
        return this.getLastOperation(null, undoOwner, n);
    }

    @UnsupportedAppUsage
    public <T extends UndoOperation> T getLastOperation(Class<T> class_, UndoOwner undoOwner, int n) {
        UndoState undoState = this.mWorking;
        if (undoState != null) {
            T t;
            if (!(n == 0 || this.mMerged || undoState.hasData() || (undoState = this.getTopUndo(null)) == null || n != 2 && undoState.hasMultipleOwners() || !undoState.canMerge() || (t = undoState.getLastOperation(class_, undoOwner)) == null || !((UndoOperation)t).allowMerge())) {
                this.mWorking.destroy();
                this.mWorking = undoState;
                this.mUndos.remove(undoState);
                this.mMerged = true;
                return t;
            }
            return this.mWorking.getLastOperation(class_, undoOwner);
        }
        throw new IllegalStateException("Must be called during an update");
    }

    @UnsupportedAppUsage
    public UndoOwner getOwner(String charSequence, Object object) {
        if (charSequence != null) {
            if (object != null) {
                UndoOwner undoOwner = this.mOwners.get(charSequence);
                if (undoOwner != null) {
                    if (undoOwner.mData != object) {
                        if (undoOwner.mData == null) {
                            undoOwner.mData = object;
                        } else {
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append("Owner ");
                            ((StringBuilder)charSequence).append(undoOwner);
                            ((StringBuilder)charSequence).append(" already exists with data ");
                            ((StringBuilder)charSequence).append(undoOwner.mData);
                            ((StringBuilder)charSequence).append(" but giving different data ");
                            ((StringBuilder)charSequence).append(object);
                            throw new IllegalStateException(((StringBuilder)charSequence).toString());
                        }
                    }
                    return undoOwner;
                }
                undoOwner = new UndoOwner((String)charSequence, this);
                undoOwner.mData = object;
                this.mOwners.put((String)charSequence, undoOwner);
                return undoOwner;
            }
            throw new NullPointerException("data can't be null");
        }
        throw new NullPointerException("tag can't be null");
    }

    public CharSequence getRedoLabel(UndoOwner[] object) {
        object = (object = this.getTopRedo((UndoOwner[])object)) != null ? ((UndoState)object).getLabel() : null;
        return object;
    }

    UndoState getTopRedo(UndoOwner[] object) {
        int n = this.mRedos.size();
        Object var3_3 = null;
        if (n <= 0) {
            return null;
        }
        n = this.findPrevState(this.mRedos, (UndoOwner[])object, -1);
        object = var3_3;
        if (n >= 0) {
            object = this.mRedos.get(n);
        }
        return object;
    }

    UndoState getTopUndo(UndoOwner[] object) {
        int n = this.mUndos.size();
        Object var3_3 = null;
        if (n <= 0) {
            return null;
        }
        n = this.findPrevState(this.mUndos, (UndoOwner[])object, -1);
        object = var3_3;
        if (n >= 0) {
            object = this.mUndos.get(n);
        }
        return object;
    }

    public CharSequence getUndoLabel(UndoOwner[] object) {
        object = (object = this.getTopUndo((UndoOwner[])object)) != null ? ((UndoState)object).getLabel() : null;
        return object;
    }

    public int getUpdateNestingLevel() {
        return this.mUpdateCount;
    }

    public boolean hasOperation(UndoOwner undoOwner) {
        UndoState undoState = this.mWorking;
        if (undoState != null) {
            return undoState.hasOperation(undoOwner);
        }
        throw new IllegalStateException("Must be called during an update");
    }

    @UnsupportedAppUsage
    public boolean isInUndo() {
        return this.mInUndo;
    }

    public boolean isInUpdate() {
        boolean bl = this.mUpdateCount > 0;
        return bl;
    }

    boolean matchOwners(UndoState undoState, UndoOwner[] arrundoOwner) {
        if (arrundoOwner == null) {
            return true;
        }
        for (int i = 0; i < arrundoOwner.length; ++i) {
            if (!undoState.matchOwner(arrundoOwner[i])) continue;
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage
    public int redo(UndoOwner[] arrundoOwner, int n) {
        if (this.mWorking == null) {
            int n2 = 0;
            int n3 = -1;
            this.mInUndo = true;
            while (n > 0) {
                int n4;
                n3 = n4 = this.findPrevState(this.mRedos, arrundoOwner, n3);
                if (n4 < 0) break;
                UndoState undoState = this.mRedos.remove(n3);
                undoState.redo();
                this.mUndos.add(undoState);
                --n;
                ++n2;
            }
            this.mInUndo = false;
            return n2;
        }
        throw new IllegalStateException("Can't be called during an update");
    }

    void removeOwner(UndoOwner undoOwner) {
    }

    @UnsupportedAppUsage
    public void restoreInstanceState(Parcel parcel, ClassLoader classLoader) {
        if (this.mUpdateCount <= 0) {
            int n;
            this.forgetUndos(null, -1);
            this.forgetRedos(null, -1);
            this.mHistorySize = parcel.readInt();
            this.mStateOwners = new UndoOwner[parcel.readInt()];
            while ((n = parcel.readInt()) != 0) {
                UndoState undoState = new UndoState(this, parcel, classLoader);
                if (n == 1) {
                    this.mUndos.add(0, undoState);
                    continue;
                }
                this.mRedos.add(0, undoState);
            }
            return;
        }
        throw new IllegalStateException("Can't save state while updating");
    }

    UndoOwner restoreOwner(Parcel parcel) {
        int n = parcel.readInt();
        Object object = this.mStateOwners[n];
        UndoOwner undoOwner = object;
        if (object == null) {
            object = parcel.readString();
            int n2 = parcel.readInt();
            undoOwner = new UndoOwner((String)object, this);
            undoOwner.mOpCount = n2;
            this.mStateOwners[n] = undoOwner;
            this.mOwners.put((String)object, undoOwner);
        }
        return undoOwner;
    }

    @UnsupportedAppUsage
    public void saveInstanceState(Parcel parcel) {
        if (this.mUpdateCount <= 0) {
            ++this.mStateSeq;
            if (this.mStateSeq <= 0) {
                this.mStateSeq = 0;
            }
            this.mNextSavedIdx = 0;
            parcel.writeInt(this.mHistorySize);
            parcel.writeInt(this.mOwners.size());
            int n = this.mUndos.size();
            while (n > 0) {
                parcel.writeInt(1);
                this.mUndos.get(--n).writeToParcel(parcel);
            }
            n = this.mRedos.size();
            while (n > 0) {
                parcel.writeInt(2);
                this.mRedos.get(--n).writeToParcel(parcel);
            }
            parcel.writeInt(0);
            return;
        }
        throw new IllegalStateException("Can't save state while updating");
    }

    void saveOwner(UndoOwner undoOwner, Parcel parcel) {
        int n = undoOwner.mStateSeq;
        int n2 = this.mStateSeq;
        if (n == n2) {
            parcel.writeInt(undoOwner.mSavedIdx);
        } else {
            undoOwner.mStateSeq = n2;
            undoOwner.mSavedIdx = this.mNextSavedIdx++;
            parcel.writeInt(undoOwner.mSavedIdx);
            parcel.writeString(undoOwner.mTag);
            parcel.writeInt(undoOwner.mOpCount);
        }
    }

    public void setHistorySize(int n) {
        this.mHistorySize = n;
        if (this.mHistorySize >= 0 && this.countUndos(null) > this.mHistorySize) {
            this.forgetUndos(null, this.countUndos(null) - this.mHistorySize);
        }
    }

    @UnsupportedAppUsage
    public void setUndoLabel(CharSequence charSequence) {
        UndoState undoState = this.mWorking;
        if (undoState != null) {
            undoState.setLabel(charSequence);
            return;
        }
        throw new IllegalStateException("Must be called during an update");
    }

    public void suggestUndoLabel(CharSequence charSequence) {
        UndoState undoState = this.mWorking;
        if (undoState != null) {
            undoState.updateLabel(charSequence);
            return;
        }
        throw new IllegalStateException("Must be called during an update");
    }

    public boolean uncommitState(int n, UndoOwner undoOwner) {
        UndoState undoState = this.mWorking;
        if (undoState != null && undoState.getCommitId() == n) {
            if (undoOwner == null || this.mWorking.hasOperation(undoOwner)) {
                return this.mWorking.setCanMerge(true);
            }
        } else {
            undoState = this.getTopUndo(null);
            if (undoState != null && (undoOwner == null || undoState.hasOperation(undoOwner)) && undoState.getCommitId() == n) {
                return undoState.setCanMerge(true);
            }
        }
        return false;
    }

    @UnsupportedAppUsage
    public int undo(UndoOwner[] arrundoOwner, int n) {
        if (this.mWorking == null) {
            int n2 = 0;
            int n3 = -1;
            this.mInUndo = true;
            UndoState undoState = this.getTopUndo(null);
            int n4 = n2;
            int n5 = n3;
            int n6 = n;
            if (undoState != null) {
                undoState.makeExecuted();
                n6 = n;
                n5 = n3;
                n4 = n2;
            }
            while (n6 > 0) {
                n5 = n = this.findPrevState(this.mUndos, arrundoOwner, n5);
                if (n < 0) break;
                undoState = this.mUndos.remove(n5);
                undoState.undo();
                this.mRedos.add(undoState);
                --n6;
                ++n4;
            }
            this.mInUndo = false;
            return n4;
        }
        throw new IllegalStateException("Can't be called during an update");
    }

    static final class UndoState {
        private boolean mCanMerge;
        private final int mCommitId;
        private boolean mExecuted;
        private CharSequence mLabel;
        private final UndoManager mManager;
        private final ArrayList<UndoOperation<?>> mOperations = new ArrayList();
        private ArrayList<UndoOperation<?>> mRecent;

        UndoState(UndoManager undoManager, int n) {
            this.mCanMerge = true;
            this.mManager = undoManager;
            this.mCommitId = n;
        }

        UndoState(UndoManager object, Parcel parcel, ClassLoader classLoader) {
            boolean bl = true;
            this.mCanMerge = true;
            this.mManager = object;
            this.mCommitId = parcel.readInt();
            boolean bl2 = parcel.readInt() != 0;
            this.mCanMerge = bl2;
            bl2 = parcel.readInt() != 0 ? bl : false;
            this.mExecuted = bl2;
            this.mLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            int n = parcel.readInt();
            for (int i = 0; i < n; ++i) {
                UndoOwner undoOwner = this.mManager.restoreOwner(parcel);
                object = (UndoOperation)parcel.readParcelable(classLoader);
                ((UndoOperation)object).mOwner = undoOwner;
                this.mOperations.add((UndoOperation<?>)object);
            }
        }

        void addOperation(UndoOperation<?> object) {
            if (!this.mOperations.contains(object)) {
                this.mOperations.add((UndoOperation<?>)object);
                if (this.mRecent == null) {
                    this.mRecent = new ArrayList();
                    this.mRecent.add((UndoOperation<?>)object);
                }
                object = ((UndoOperation)object).mOwner;
                ++((UndoOwner)object).mOpCount;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Already holds ");
            stringBuilder.append(object);
            throw new IllegalStateException(stringBuilder.toString());
        }

        boolean canMerge() {
            boolean bl = this.mCanMerge && !this.mExecuted;
            return bl;
        }

        void commit() {
            ArrayList<UndoOperation<?>> arrayList = this.mRecent;
            int n = arrayList != null ? arrayList.size() : 0;
            for (int i = 0; i < n; ++i) {
                this.mRecent.get(i).commit();
            }
            this.mRecent = null;
        }

        int countOperations() {
            return this.mOperations.size();
        }

        void destroy() {
            for (int i = this.mOperations.size() - 1; i >= 0; --i) {
                UndoOwner undoOwner = this.mOperations.get((int)i).mOwner;
                --undoOwner.mOpCount;
                if (undoOwner.mOpCount > 0) continue;
                if (undoOwner.mOpCount >= 0) {
                    this.mManager.removeOwner(undoOwner);
                    continue;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Underflow of op count on owner ");
                stringBuilder.append(undoOwner);
                stringBuilder.append(" in op ");
                stringBuilder.append(this.mOperations.get(i));
                throw new IllegalStateException(stringBuilder.toString());
            }
        }

        int getCommitId() {
            return this.mCommitId;
        }

        CharSequence getLabel() {
            return this.mLabel;
        }

        <T extends UndoOperation> T getLastOperation(Class<T> object, UndoOwner undoOwner) {
            int n = this.mOperations.size();
            UndoOperation<?> undoOperation = null;
            if (object == null && undoOwner == null) {
                object = undoOperation;
                if (n > 0) {
                    object = this.mOperations.get(n - 1);
                }
                return (T)object;
            }
            --n;
            while (n >= 0) {
                undoOperation = this.mOperations.get(n);
                if (undoOwner != null && undoOperation.getOwner() != undoOwner) {
                    --n;
                    continue;
                }
                if (object != null && undoOperation.getClass() != object) {
                    return null;
                }
                return (T)undoOperation;
            }
            return null;
        }

        boolean hasData() {
            for (int i = this.mOperations.size() - 1; i >= 0; --i) {
                if (!this.mOperations.get(i).hasData()) continue;
                return true;
            }
            return false;
        }

        boolean hasMultipleOwners() {
            int n = this.mOperations.size();
            if (n <= 1) {
                return false;
            }
            UndoOwner undoOwner = this.mOperations.get(0).getOwner();
            for (int i = 1; i < n; ++i) {
                if (this.mOperations.get(i).getOwner() == undoOwner) continue;
                return true;
            }
            return false;
        }

        boolean hasOperation(UndoOwner undoOwner) {
            int n = this.mOperations.size();
            boolean bl = false;
            if (undoOwner == null) {
                if (n != 0) {
                    bl = true;
                }
                return bl;
            }
            for (int i = 0; i < n; ++i) {
                if (this.mOperations.get(i).getOwner() != undoOwner) continue;
                return true;
            }
            return false;
        }

        void makeExecuted() {
            this.mExecuted = true;
        }

        boolean matchOwner(UndoOwner undoOwner) {
            for (int i = this.mOperations.size() - 1; i >= 0; --i) {
                if (!this.mOperations.get(i).matchOwner(undoOwner)) continue;
                return true;
            }
            return false;
        }

        void redo() {
            int n = this.mOperations.size();
            for (int i = 0; i < n; ++i) {
                this.mOperations.get(i).redo();
            }
        }

        boolean setCanMerge(boolean bl) {
            if (bl && this.mExecuted) {
                return false;
            }
            this.mCanMerge = bl;
            return true;
        }

        void setLabel(CharSequence charSequence) {
            this.mLabel = charSequence;
        }

        void undo() {
            for (int i = this.mOperations.size() - 1; i >= 0; --i) {
                this.mOperations.get(i).undo();
            }
        }

        void updateLabel(CharSequence charSequence) {
            if (this.mLabel != null) {
                this.mLabel = charSequence;
            }
        }

        void writeToParcel(Parcel parcel) {
            if (this.mRecent == null) {
                parcel.writeInt(this.mCommitId);
                parcel.writeInt((int)this.mCanMerge);
                parcel.writeInt((int)this.mExecuted);
                TextUtils.writeToParcel(this.mLabel, parcel, 0);
                int n = this.mOperations.size();
                parcel.writeInt(n);
                for (int i = 0; i < n; ++i) {
                    UndoOperation<?> undoOperation = this.mOperations.get(i);
                    this.mManager.saveOwner(undoOperation.mOwner, parcel);
                    parcel.writeParcelable(undoOperation, 0);
                }
                return;
            }
            throw new IllegalStateException("Can't save state before committing");
        }
    }

}

